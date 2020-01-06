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
import com.refresh.pos.domain.inventory.Inventory;
import com.refresh.pos.domain.inventory.ProductCatalog;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.techicalservices.inventory.InventoryDaoAndroid;
import com.refresh.pos.ui.component.UpdatableFragment;
import com.refresh.pos.ui.inventory.MySingleton;

import org.json.JSONException;
import org.json.JSONObject;

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

public class UpdateGroupToppingDialogFragment extends DialogFragment {
	private String idGroupTopping;
	private String image;
	private String name;
	private String gpk;
	private String status;
	private EditText name_edt;
	private EditText image_edt;
	private EditText gpk_edt;
	private CateProductCatolog cateProductCatolog;
	private Button updateGroup;
	private Button clearGroup;
	private UpdatableFragment fragment;
	private Resources res;
	private TableCatolog tableCatolog;
	private ProductCatalog productCatalog;

	private DialogInterface.OnDismissListener onDismissListener;



	/**
	 * Construct a new AddProductDialogFragment
	 * @param fragment
	 */
	public UpdateGroupToppingDialogFragment(UpdatableFragment fragment) {
		
		super();
		this.fragment = fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		try {
			productCatalog = Inventory.getInstance().getProductCatalog();
		} catch (NoDaoSetException e) {
			e.printStackTrace();
		}
		
		View v = inflater.inflate(R.layout.layout_update_group_topping, container,
				false);
		
		res = getResources();

		name_edt = (EditText) v.findViewById(R.id.name_edt);
		gpk_edt = (EditText) v.findViewById(R.id.gpk_edt);
		image_edt = (EditText) v.findViewById(R.id.image_edt);
		updateGroup = (Button) v.findViewById(R.id.updateGroup);
		clearGroup = (Button) v.findViewById(R.id.clearGroup);

		idGroupTopping = getArguments().getString("id");
		name = getArguments().getString("name");
		gpk = getArguments().getString("gpk");
		image = getArguments().getString("image");
		status = getArguments().getString("status");

		initUI();
		return v;
	}

	/**
	 * Construct a new 
	 */
	private void initUI() {

		gpk_edt.setText(gpk);
		name_edt.setText(name);
		image_edt.setText(image);

//		scanButton.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				IntentIntegratorSupportV4 scanIntegrator = new IntentIntegratorSupportV4(AddCategoryProductDialogFragment.this);
//				scanIntegrator.initiateScan();
//			}
//		});

		updateGroup.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				if (name_edt.getText().toString().equals("")) {

					Toast.makeText(getActivity().getBaseContext(),
							res.getString(R.string.please_input_all), Toast.LENGTH_SHORT)
							.show();

				} else {

						if (checkNetworkConnection()){
							final String token = "9bdtrkt9w7apuatehl6o";


							final JSONObject toppingGroupData = new JSONObject();


							String gpk = gpk_edt.getText().toString();
//					int sequence = 0;
							String name = name_edt.getText().toString();
							String image = image_edt.getText().toString();
							String status = "use";




							try {

								toppingGroupData.put("gpk",gpk);
								toppingGroupData.put("name",name);
								toppingGroupData.put("image",image);
								toppingGroupData.put("status",status);

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}




							final JSONObject GroupToppingObject = new JSONObject();

							try {

								GroupToppingObject.put("token",token);
								GroupToppingObject.put("data",toppingGroupData);


							} catch (JSONException e) {
								e.printStackTrace();

							}


							JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
									InventoryDaoAndroid.SERVER_URL_TOPPING_GROUP_PUT, GroupToppingObject,

									new Response.Listener<JSONObject>() {

										@Override
										public void onResponse(JSONObject response) {

											saveToServer();
											Log.d(TAG, response.toString());
											Log.d(TAG, GroupToppingObject.toString());
										}

									}, new Response.ErrorListener() {

								@Override
								public void onErrorResponse(VolleyError error) {

									Log.d(TAG, "Error: " + error.getMessage());
									Log.d(TAG, GroupToppingObject.toString());
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

							saveToLocalStorage();

						}

				}
			}
		});



		clearGroup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(name_edt.getText().toString().equals("")){
					UpdateGroupToppingDialogFragment.this.dismiss();
				} else {
					clearAllBox();
				}
			}
		});
	}

	/**
	 * Clear all box
	 */
	private void clearAllBox() {
		name_edt.setText("");

	}

	private void saveToServer(){

		int id = Integer.parseInt(idGroupTopping);
		String gpk = gpk_edt.getText().toString();
		String name = name_edt.getText().toString();
		String image = image_edt.getText().toString();
		String statusObject = status;


		productCatalog.editGroupTopping(id,name,image);
		UpdateGroupToppingDialogFragment.this.dismiss();

	}


	private void saveToLocalStorage(){

		int id = Integer.parseInt(idGroupTopping);
		String gpk = gpk_edt.getText().toString();
		String name = name_edt.getText().toString();
		String image = image_edt.getText().toString();
		String statusObject = status;


		productCatalog.editGroupTopping(id,name,image);
		UpdateGroupToppingDialogFragment.this.dismiss();

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
