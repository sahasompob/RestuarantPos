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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.refresh.pos.R;
import com.refresh.pos.domain.Table.TableCatolog;
import com.refresh.pos.domain.Table.TableLedger;
import com.refresh.pos.domain.inventory.GroupToppingProduct;
import com.refresh.pos.domain.inventory.Inventory;
import com.refresh.pos.domain.inventory.ProductCatalog;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.techicalservices.inventory.InventoryDaoAndroid;
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

public class UpdateToppingDialogFragment extends DialogFragment {

    private String idTopping;
	private String gpk;
    private String groupId;
    private String name;
	private String price;
	private EditText name_edt;
	private EditText price_edt;
	private Button updateTable;
	private Button clearTable;
	private UpdatableFragment fragment;
	private Spinner groupToppingSpinner;
	private ArrayList<Map<String, String>> groupToppingList;
	private int groupToppingId;
	private Resources res;
	private TableCatolog tableCatolog;
	private DialogInterface.OnDismissListener onDismissListener;
	private ProductCatalog productCatalog;




	/**
	 * Construct a new AddProductDialogFragment
	 * @param fragment
	 */
	public UpdateToppingDialogFragment(UpdatableFragment fragment) {
		
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
		
		View v = inflater.inflate(R.layout.layout_update_topping, container,
				false);
		
		res = getResources();

		name_edt = (EditText) v.findViewById(R.id.name_edt);
		price_edt = (EditText) v.findViewById(R.id.price_edt);

		updateTable = (Button) v.findViewById(R.id.updateTopping);
		clearTable = (Button) v.findViewById(R.id.clearAddTopping);
		groupToppingSpinner = (Spinner) v.findViewById(R.id.group_topping);


		idTopping = getArguments().getString("id");
		groupId = getArguments().getString("groupId");
        name = getArguments().getString("name");
		price = getArguments().getString("price");
		gpk = getArguments().getString("gpk");




		initUI();
		return v;
	}

	/**
	 * Construct a new 
	 */
	private void initUI() {

		showGroupToppingSpinner((ArrayList<GroupToppingProduct>) productCatalog.getAllGroupTopping());

		name_edt.setText(name);
		price_edt.setText(price);

//		scanButton.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				IntentIntegratorSupportV4 scanIntegrator = new IntentIntegratorSupportV4(AddCategoryProductDialogFragment.this);
//				scanIntegrator.initiateScan();
//			}
//		});

		updateTable.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				if (name_edt.getText().toString().equals("")) {

					Toast.makeText(getActivity().getBaseContext(),
							res.getString(R.string.please_input_all), Toast.LENGTH_SHORT)
							.show();

				} else {

					if (checkNetworkConnection()){

						final String token = "9bdtrkt9w7apuatehl6o";


						final JSONObject toppingData = new JSONObject();

						String stringGPK = gpk;
						String name = name_edt.getText().toString();
						double price = Double.parseDouble(price_edt.getText().toString());
						String status = "use";
						String image = "jpg";
						int group_topping = groupToppingId;
						String groupID = String.valueOf(group_topping);



						try {

							toppingData.put("gpk",stringGPK);
							toppingData.put("group_id",groupID);
							toppingData.put("name",name);
							toppingData.put("price",price);
							toppingData.put("sync","0");
							toppingData.put("status",status);

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}




						final JSONObject ToppingObject = new JSONObject();

						try {

							ToppingObject.put("token",token);
							ToppingObject.put("data",toppingData);


						} catch (JSONException e) {
							e.printStackTrace();

						}


						JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
								InventoryDaoAndroid.SERVER_URL_TOPPING_PUT, ToppingObject,

								new Response.Listener<JSONObject>() {

									@Override
									public void onResponse(JSONObject response) {

										saveToServer();
										Log.d(TAG, response.toString());
										Log.d(TAG, ToppingObject.toString());
									}

								}, new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError error) {

								Log.d(TAG, "Error: " + error.getMessage());
								Log.d(TAG, ToppingObject.toString());
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



		clearTable.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(name_edt.getText().toString().equals("")){
					UpdateToppingDialogFragment.this.dismiss();
				} else {
					clearAllBox();
				}
			}
		});
	}

	private void saveToServer(){
		//					String pk = "ct002";
//					int sequence = 0;
		int id = Integer.parseInt(idTopping);
		String name = name_edt.getText().toString();
		String stringGPK = gpk;
		double price = Double.parseDouble(price_edt.getText().toString());
		int group_topping = groupToppingId;


//					boolean success = cateProductCatolog.addCateProduct(nameBox
//							.getText().toString(), barcodeBox.getText()
//							.toString(), Double.parseDouble(priceBox.getText()
//							.toString()));


		productCatalog.editTopping(id,group_topping,name,price);
		UpdateToppingDialogFragment.this.dismiss();

	}

	private void saveToLocalStorage(){


		int id = Integer.parseInt(idTopping);
		String name = name_edt.getText().toString();
		String stringGPK = gpk;
		double price = Double.parseDouble(price_edt.getText().toString());
		int group_topping = groupToppingId;

		productCatalog.editTopping(id,group_topping,name,price);
		UpdateToppingDialogFragment.this.dismiss();

	}
	/**
	 * Clear all box
	 */
	private void clearAllBox() {
		name_edt.setText("");

	}


	private void showGroupToppingSpinner(ArrayList<GroupToppingProduct> list) {

		groupToppingList = new ArrayList<Map<String, String>>();
		for(GroupToppingProduct groupToppingProduct : list) {

			groupToppingList.add(groupToppingProduct.toMap());

		}

		GroupToppingSpinnerAdapter customAdapter = new GroupToppingSpinnerAdapter(getContext(), groupToppingList);
		groupToppingSpinner.setAdapter(customAdapter);

		groupToppingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				Object item = parent.getItemAtPosition(pos);

				String test_id = groupToppingList.get(pos).get("id");
				groupToppingId = Integer.parseInt(test_id);
			}
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

	}

	public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
		this.onDismissListener = onDismissListener;
	}

	public boolean checkNetworkConnection(){

		ConnectivityManager connectivityManager =(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return (networkInfo!=null && networkInfo.isConnected());
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
		if (onDismissListener != null) {
			onDismissListener.onDismiss(dialog);
		}
	}
}
