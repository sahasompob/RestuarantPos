package com.refresh.pos.ui.inventory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.refresh.pos.R;
import com.refresh.pos.domain.ProductCategory.CateProductCatolog;
import com.refresh.pos.domain.ProductCategory.ProductCategory;
import com.refresh.pos.domain.Table.TableCatolog;
import com.refresh.pos.domain.Table.TableLedger;
import com.refresh.pos.domain.Table.Table_Detail;
import com.refresh.pos.techicalservices.CategoryProduct.CateProductDaoAndroid;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.techicalservices.Table.TableDaoAndroid;
import com.refresh.pos.ui.component.UpdatableFragment;
import com.refresh.pos.ui.sale.OpenTableFragmentDialog;
import com.refresh.pos.ui.setting.DialogTableFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.android.volley.VolleyLog.TAG;

/**
 * A dialog of adding a Product.
 * 
 * @author Refresh Team
 *
 */
@SuppressLint("ValidFragment")

public class AddTableDialogFragment extends DialogFragment {

	private EditText name_table;
	private CateProductCatolog cateProductCatolog;
	private Button confirmAddCategory;
	private Button clearAddCategory;
	private UpdatableFragment fragment;
	private Resources res;
	private TableCatolog tableCatolog;
	private DialogInterface.OnDismissListener onDismissListener;
	private ArrayList<Map<String, String>> tableList;




	/**
	 * Construct a new AddProductDialogFragment
	 * @param fragment
	 */
	public AddTableDialogFragment(UpdatableFragment fragment) {
		
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
		
		View v = inflater.inflate(R.layout.layout_add_table, container,
				false);
		
		res = getResources();

		name_table = (EditText) v.findViewById(R.id.detail_table);

		confirmAddCategory = (Button) v.findViewById(R.id.confirmAddCategory);
		clearAddCategory = (Button) v.findViewById(R.id.clearAddCategory);

		initUI();
		return v;
	}

	/**
	 * Construct a new 
	 */
	private void initUI() {
//		scanButton.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				IntentIntegratorSupportV4 scanIntegrator = new IntentIntegratorSupportV4(AddCategoryProductDialogFragment.this);
//				scanIntegrator.initiateScan();
//			}
//		});

		confirmAddCategory.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				if (name_table.getText().toString().equals("")) {

					Toast.makeText(getActivity().getBaseContext(),
							res.getString(R.string.please_input_all), Toast.LENGTH_SHORT)
							.show();

				} else {

					if (checkNetworkConnection()){

						final String token = "9bdtrkt9w7apuatehl6o";

						saveToServer();

						getLastTable((ArrayList<Table_Detail>) tableCatolog.getLastRow());

                        final JSONObject tableData = new JSONObject();

                        for(int ii = 0; ii < tableList.size(); ii++){

                            int sequence = Integer.parseInt(tableList.get(ii).get("table_id"));
							String gpk = tableList.get(ii).get("gpk");
                            String table_name = tableList.get(ii).get("table_name");
                            String status = tableList.get(ii).get("status");
							String sync = tableList.get(ii).get("sync");
							String image = tableList.get(ii).get("image");

                            try {

                                tableData.put("gpk",gpk);
                                tableData.put("sequence",sequence);
                                tableData.put("name",table_name);
                                tableData.put("image",image);
								tableData.put("sync",sync);
								tableData.put("status",status);


                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }


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


						JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
								TableDaoAndroid.SERVER_POST, tableObject,

								new Response.Listener<JSONObject>() {

									@Override
									public void onResponse(JSONObject response) {

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





					}else {

						Toast.makeText(getActivity().getBaseContext(),
								" Fail Connect Server",
								Toast.LENGTH_SHORT).show();

						saveToLocalStorage();
					}



				}
			}
		});



		clearAddCategory.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(name_table.getText().toString().equals("")){
					AddTableDialogFragment.this.dismiss();
				} else {
					clearAllBox();
				}
			}
		});
	}

	private void getLastTable(ArrayList<Table_Detail> list) {
		tableList = new ArrayList<Map<String, String>>();
		for (Table_Detail table_detail : list) {
			tableList.add(table_detail.toMap());

		}
	}
	/**
	 * Clear all box
	 */
	private void clearAllBox() {
		name_table.setText("");

	}

	private void saveToServer(){

		String SALTCHARS = "abcdefghijklmnopqrstuvwxyz1234567890";
		StringBuilder salt = new StringBuilder();

		Random rnd = new Random();
		while (salt.length() < 3) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}

		String saltStr = salt.toString();

		int sequence = 1;
		String gpk = saltStr;
		String name = name_table.getText().toString();
		String status = "use";
		int sync =  0;
		String status_service = "empty";

		boolean success = tableCatolog.addTable(sequence,name,gpk,status,sync,status_service);

		if (success) {

			Toast.makeText(getActivity().getBaseContext(),
					res.getString(R.string.success) + ", "
							+ name_table.getText().toString(),
					Toast.LENGTH_SHORT).show();

//						Toast.makeText(getActivity().getBaseContext(),
//								"Success",
//								Toast.LENGTH_SHORT).show();

			clearAllBox();
			AddTableDialogFragment.this.dismiss();



		} else {

			Toast.makeText(getActivity().getBaseContext(),
					res.getString(R.string.fail),
					Toast.LENGTH_SHORT).show();
		}

	}


	private void saveToLocalStorage(){

		String SALTCHARS = "abcdefghijklmnopqrstuvwxyz1234567890";
		StringBuilder salt = new StringBuilder();

		Random rnd = new Random();
		while (salt.length() < 3) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}

		String saltStr = salt.toString();
		int sequence = 1;
		String gpk = saltStr;
		String name = name_table.getText().toString();
		String status = "use";
		int sync =  0;
		String status_service = "empty";

		boolean success = tableCatolog.addTable(sequence,name,gpk,status,sync,status_service);

		if (success) {

			Toast.makeText(getActivity().getBaseContext(),
					res.getString(R.string.success) + ", "
							+ name_table.getText().toString(),
					Toast.LENGTH_SHORT).show();

//						Toast.makeText(getActivity().getBaseContext(),
//								"Success",
//								Toast.LENGTH_SHORT).show();

			clearAllBox();
			AddTableDialogFragment.this.dismiss();



		} else {

			Toast.makeText(getActivity().getBaseContext(),
					res.getString(R.string.fail),
					Toast.LENGTH_SHORT).show();
		}

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
