package com.refresh.pos.ui.setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.refresh.pos.domain.ProductCategory.CateProductCatolog;
import com.refresh.pos.domain.Table.TableCatolog;
import com.refresh.pos.domain.Table.TableLedger;
import com.refresh.pos.domain.inventory.GroupToppingProduct;
import com.refresh.pos.domain.inventory.Inventory;
import com.refresh.pos.domain.inventory.ProductCatalog;
import com.refresh.pos.techicalservices.CategoryProduct.CateProductDaoAndroid;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.techicalservices.inventory.InventoryDaoAndroid;
import com.refresh.pos.ui.component.UpdatableFragment;
import com.refresh.pos.ui.inventory.MySingleton;

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

public class AddToppingDialogFragment extends DialogFragment {
	private EditText gpk_edt;
	private EditText name_edt;
	private EditText price_edt;
	private CateProductCatolog cateProductCatolog;
	private Button confirmAddCategory;
	private Button clearAddCategory;
	private UpdatableFragment fragment;
	private Resources res;
	private TableCatolog tableCatolog;
	private ProductCatalog productCatalog;
	private Spinner groupToppingSpinner;
	private ArrayList<Map<String, String>> groupToppingList;
	private RecyclerView group_topping_listview;
	private DialogInterface.OnDismissListener onDismissListener;
	private int groupToppingId;



	/**
	 * Construct a new AddProductDialogFragment
	 * @param fragment
	 */
	public AddToppingDialogFragment(UpdatableFragment fragment) {
		
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
		
		View v = inflater.inflate(R.layout.layout_add_topping, container,
				false);
		
		res = getResources();
		gpk_edt = (EditText) v.findViewById(R.id.gpk_edt);
		name_edt = (EditText) v.findViewById(R.id.name_edt);
		price_edt = (EditText) v.findViewById(R.id.price_edt);
		groupToppingSpinner = (Spinner) v.findViewById(R.id.group_topping);
		confirmAddCategory = (Button) v.findViewById(R.id.confirmAddCategory);
		clearAddCategory = (Button) v.findViewById(R.id.clearAddCategory);
		group_topping_listview = (RecyclerView) v.findViewById(R.id.group_topping_listview);

		initUI();
		return v;
	}

	/**
	 * Construct a new 
	 */
	private void initUI() {

		String SALTCHARS = "abcdefghijklmnopqrstuvwxyz1234567890";
		StringBuilder salt = new StringBuilder();

		Random rnd = new Random();
		while (salt.length() < 5) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}

		String saltStr = salt.toString();
		gpk_edt.setText(saltStr);

        showGroupToppingSpinner((ArrayList<GroupToppingProduct>) productCatalog.getAllGroupTopping());


//		scanButton.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				IntentIntegratorSupportV4 scanIntegrator = new IntentIntegratorSupportV4(AddCategoryProductDialogFragment.this);
//				scanIntegrator.initiateScan();
//			}
//		});

		confirmAddCategory.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				if (name_edt.getText().toString().equals("")) {

					Toast.makeText(getActivity().getBaseContext(),
							res.getString(R.string.please_input_all), Toast.LENGTH_SHORT)
							.show();

				} else {

					if (checkNetworkConnection()){

						final String token = "9bdtrkt9w7apuatehl6o";


						final JSONObject toppingData = new JSONObject();

						String gpk = gpk_edt.getText().toString();
						String name = name_edt.getText().toString();
						double price = Double.parseDouble(price_edt.getText().toString());
						String status = "use";
						String image = "jpg";
						int group_topping = groupToppingId;
						String groupID = String.valueOf(group_topping);



							try {

								toppingData.put("gpk",gpk);
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


						JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
								InventoryDaoAndroid.SERVER_URL_TOPPING_POST, ToppingObject,

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



		clearAddCategory.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(name_edt.getText().toString().equals("")){
					AddToppingDialogFragment.this.dismiss();
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


	private void saveToServer(){


		String gpk = gpk_edt.getText().toString();
		String name = name_edt.getText().toString();
		double price = Double.parseDouble(price_edt.getText().toString());
		String status = "use";
		String image = "jpg";
		int group_topping = groupToppingId;

		boolean success = productCatalog.addTopping(gpk,group_topping,name,price,image,status);

		if (success) {

			Toast.makeText(getActivity().getBaseContext(),
					res.getString(R.string.success),
					Toast.LENGTH_SHORT).show();

//						Toast.makeText(getActivity().getBaseContext(),
//								"Success",
//								Toast.LENGTH_SHORT).show();

			clearAllBox();
			AddToppingDialogFragment.this.dismiss();



		} else {
			Toast.makeText(getActivity().getBaseContext(),
					res.getString(R.string.fail),
					Toast.LENGTH_SHORT).show();
		}
	}


	private void saveToLocalStorage(){

		String gpk = gpk_edt.getText().toString();
		String name = name_edt.getText().toString();
		double price = Double.parseDouble(price_edt.getText().toString());
		String status = "use";
		String image = "jpg";
		int group_topping = groupToppingId;


		boolean success = productCatalog.addTopping(gpk,group_topping,name,price,image,status);

		if (success) {

			Toast.makeText(getActivity().getBaseContext(),
					res.getString(R.string.success),
					Toast.LENGTH_SHORT).show();

//						Toast.makeText(getActivity().getBaseContext(),
//								"Success",
//								Toast.LENGTH_SHORT).show();

			clearAllBox();
			AddToppingDialogFragment.this.dismiss();



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
