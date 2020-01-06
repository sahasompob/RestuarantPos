package com.refresh.pos.ui.setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.refresh.pos.R;
import com.refresh.pos.domain.ProductCategory.CateProductCatolog;
import com.refresh.pos.domain.Table.TableCatolog;
import com.refresh.pos.domain.Table.TableLedger;
import com.refresh.pos.domain.Table.Table_Detail;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.techicalservices.Table.TableDaoAndroid;
import com.refresh.pos.ui.component.UpdatableFragment;
import com.refresh.pos.ui.inventory.MySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

/**
 * A dialog of adding a Product.
 * 
 * @author Refresh Team
 *
 */
@SuppressLint("ValidFragment")

public class UpdateTableDialogFragment extends DialogFragment {

    private String idTable;
    private String name;
	private String gpk;
	private String sync;
	private String image;
	private String status;
	private EditText name_table;
	private Button updateTable;
	private Button clearTable;
	private UpdatableFragment fragment;
	private Resources res;
	private TableCatolog tableCatolog;
	private DialogInterface.OnDismissListener onDismissListener;



	/**
	 * Construct a new AddProductDialogFragment
	 * @param fragment
	 */
	public UpdateTableDialogFragment(UpdatableFragment fragment) {
		
		super();
		this.fragment = fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		try {
			tableCatolog = TableLedger.getInstance().getTableCatalog();
		} catch (NoDaoSetException e) {
			e.printStackTrace();
		}
		
		View v = inflater.inflate(R.layout.layout_update_table, container,
				false);
		
		res = getResources();

		name_table = (EditText) v.findViewById(R.id.detail_table);

		updateTable = (Button) v.findViewById(R.id.updateTable);
		clearTable = (Button) v.findViewById(R.id.clearTable);

        idTable = getArguments().getString("id");
        name = getArguments().getString("name");
		gpk = getArguments().getString("gpk");
		sync = getArguments().getString("sync");
		image = getArguments().getString("image");
		status = getArguments().getString("status");






		initUI();
		return v;
	}

	/**
	 * Construct a new 
	 */
	private void initUI() {

        name_table.setText(name);


		updateTable.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				if (name_table.getText().toString().equals("")) {

					Toast.makeText(getActivity().getBaseContext(),
							res.getString(R.string.please_input_all), Toast.LENGTH_SHORT)
							.show();

				} else {


					if (checkNetworkConnection()){

						final String token = "9bdtrkt9w7apuatehl6o";

						final JSONObject tableData = new JSONObject();

							int id = Integer.parseInt(idTable);
							int sequence = Integer.parseInt(idTable);
							String gpkObject = gpk;
							String name = name_table.getText().toString();
							String statusObject = status;
							String syncObject = sync;
							String imageObject = image;

							try {

								tableData.put("gpk",gpkObject);
								tableData.put("sequence",sequence);
								tableData.put("name",name);
								tableData.put("image",imageObject);
								tableData.put("sync",syncObject);
								tableData.put("status",statusObject);


							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}




						final JSONObject tableObject = new JSONObject();

						try {

							tableObject.put("token",token);
							tableObject.put("data",tableData);



						} catch (JSONException e) {
							e.printStackTrace();

						}


						Toast.makeText(getActivity().getBaseContext(),
								" tableObject =  " + tableObject,
								Toast.LENGTH_SHORT).show();


						JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
								TableDaoAndroid.SERVER_PUT, tableObject,

								new Response.Listener<JSONObject>() {

									@Override
									public void onResponse(JSONObject response) {

										saveToServer();
										Log.d(TAG, response.toString());
										Log.d(TAG, tableObject.toString());
									}

								}, new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError error) {

								Log.d(TAG, "Error: " + error.getMessage());
								Log.d(TAG, tableObject.toString());
							}

						})

						{
							/**
							 * Passing some request headers
							 */
							@Override
							public Map<String, String> getHeaders() throws AuthFailureError {
								HashMap<String, String> headers = new HashMap<String, String>();
								//headers.put("Content-Type", "application/json");
								headers.put("Content-Type", "application/json");
								return headers;
							}
						};

						MySingleton.getInstance(getContext()).addtoRequestQue(jsonObjReq);





					}




				}
			}
		});



		clearTable.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(name_table.getText().toString().equals("")){
					UpdateTableDialogFragment.this.dismiss();
				} else {
					clearAllBox();
				}
			}
		});
	}


	private void saveToServer(){

		String name = name_table.getText().toString();
		int id = Integer.parseInt(idTable);
		tableCatolog.editTable_Detail(id,name);
		UpdateTableDialogFragment.this.dismiss();

	}


	private void saveToLocalStorage(){

		String name = name_table.getText().toString();
		int id = Integer.parseInt(idTable);
		tableCatolog.editTable_Detail(id,name);
		UpdateTableDialogFragment.this.dismiss();

	}

	/**
	 * Clear all box
	 */
	private void clearAllBox() {
		name_table.setText("");

	}


//	private void refreshFragment(){
//
//		UpdatableFragment categoryFrament = new CategoryFragment();
//		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//		fragmentManager.beginTransaction().replace(R.id.CateLayoutout,categoryFrament,categoryFrament.getTag()).commit();
//	}

	public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
		this.onDismissListener = onDismissListener;
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
		if (onDismissListener != null) {
			onDismissListener.onDismiss(dialog);
		}
	}

	public boolean checkNetworkConnection(){

		ConnectivityManager connectivityManager =(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return (networkInfo!=null && networkInfo.isConnected());
	}

}
