package com.refresh.pos.ui.setting;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentIntegratorSupportV4;
import com.google.zxing.integration.android.IntentResult;
import com.refresh.pos.R;
import com.refresh.pos.domain.ProductCategory.CateProductCatolog;
import com.refresh.pos.domain.ProductCategory.CategoryProduct;
import com.refresh.pos.domain.ProductCategory.ProductCategory;
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
import java.util.Random;

import static com.android.volley.VolleyLog.TAG;

/**
 * A dialog of adding a Product.
 * 
 * @author Refresh Team
 *
 */
@SuppressLint("ValidFragment")
public class UpdateProductDialogFragment extends DialogFragment {


	private String id;
	private String name;
	private String topping_group;
	private String price;
	private String gpk;
	private String code;
	private String cate_id;
	private String cost;
	private String image;
	private String sync;

	private EditText barcodeBox;
	private EditText imageBox;
	private EditText costBox;
	private TextView gpkText;
	private EditText cateBox;
	private ProductCatalog productCatalog;
//	private CateProductCatolog cateProductCatolog;

	private Button scanButton;
	private EditText priceBox;
	private EditText nameBox;
	private Button confirmButton;
	private Button clearButton;
	private UpdatableFragment fragment;
	private Resources res;
	private int groupToppingId;
	private int CategoryId;

	private Spinner groupToppingSpinner;
	private Spinner categorySpinner;
	private ArrayList<Map<String, String>> groupToppingList;
	private ArrayList<Map<String, String>> CategoryList;

	private CheckBox checkbox_tp;
	private CateProductCatolog cateProductCatolog;





	private DialogInterface.OnDismissListener onDismissListener;

	/**
	 * Construct a new AddProductDialogFragment
     * @param fragment
     */
	public UpdateProductDialogFragment(UpdatableFragment fragment) {
		
		super();
		this.fragment = fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		try {
			productCatalog = Inventory.getInstance().getProductCatalog();
			cateProductCatolog = ProductCategory.getInstance().getCateProductCatalog();
		} catch (NoDaoSetException e) {
			e.printStackTrace();
		}
		
		View v = inflater.inflate(R.layout.layout_update_product, container,
				false);
		
		res = getResources();

		checkbox_tp = (CheckBox) v.findViewById(R.id.checkbox_tp);
		barcodeBox = (EditText) v.findViewById(R.id.barcodeBox);
		scanButton = (Button) v.findViewById(R.id.scanButton);
		priceBox = (EditText) v.findViewById(R.id.priceBox);
		nameBox = (EditText) v.findViewById(R.id.nameBox);
		costBox = (EditText) v.findViewById(R.id.costBox);
		imageBox = (EditText) v.findViewById(R.id.imageBox);
		gpkText = (TextView) v.findViewById(R.id.gpk_txt);
		confirmButton = (Button) v.findViewById(R.id.confirmButton);
		clearButton = (Button) v.findViewById(R.id.clearButton);
		groupToppingSpinner = (Spinner) v.findViewById(R.id.group_topping_add);
		categorySpinner = (Spinner) v.findViewById(R.id.categorySpinner);




		id = getArguments().getString("id");
		name = getArguments().getString("name");
		topping_group = getArguments().getString("topping_group");
		price = getArguments().getString("price");
		gpk = getArguments().getString("gpk");
		code = getArguments().getString("code");
		cate_id = getArguments().getString("cate_id");
		cost = getArguments().getString("cost");
		image = getArguments().getString("image");
		sync = getArguments().getString("sync");



		Toast.makeText(getActivity(), "UPDATE //" +"Id = " + id +" topping_group = " + topping_group + " name = " + name + " Price = " + price + " gpk = " + gpk + " code = " + code + " cate_id = "
						+ cate_id + " cost = " + cost + " image = " + image + " sync = " + sync,
				Toast.LENGTH_LONG).show();


		initUI();
		return v;
	}

	/**
	 * Construct a new 
	 */
	private void initUI() {

		showCategorySpinner((ArrayList<CategoryProduct>) cateProductCatolog.getAllCategoryProduct());
		showGroupToppingSpinner((ArrayList<GroupToppingProduct>) productCatalog.getAllGroupTopping());


		nameBox.setText(name);
		priceBox.setText(price);
		barcodeBox.setText(code);
		gpkText.setText(gpk);


		checkbox_tp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				if (isChecked){

					groupToppingId = 0;
					groupToppingSpinner.setVisibility(View.GONE);

				}else {
					groupToppingSpinner.setVisibility(View.VISIBLE);
					Toast.makeText(getActivity().getBaseContext(),
							" UnCheck!",
							Toast.LENGTH_SHORT).show();
				}


			}
		});



		scanButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				IntentIntegratorSupportV4 scanIntegrator = new IntentIntegratorSupportV4(UpdateProductDialogFragment.this);
				scanIntegrator.initiateScan();
			}
		});

		confirmButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				if (nameBox.getText().toString().equals("")
					|| barcodeBox.getText().toString().equals("")
					|| priceBox.getText().toString().equals("")) {
					
					Toast.makeText(getActivity().getBaseContext(),
							res.getString(R.string.please_input_all), Toast.LENGTH_SHORT)
							.show();

				} else {
					final String token = "9bdtrkt9w7apuatehl6o";

					if (checkNetworkConnection()){

						final JSONObject Object = new JSONObject();

						int syn = InventoryDaoAndroid.SYNC_STATUS_FAILED;
						String name = nameBox.getText().toString();
						String code = barcodeBox.getText().toString();
						String image = "";
//		String image = imageBox.getText().toString();
						String gpk  = gpkText.getText().toString();
						int	category = CategoryId;;
						int	topping_group = groupToppingId;
						double price = Double.parseDouble(priceBox.getText().toString());
						double cost = price;
//		double cost = Double.parseDouble(costBox.getText().toString());
						String status = "use";


						try {

							Object.put("gpk",gpk);
							Object.put("cat_id",category);
							Object.put("topping","");
							Object.put("code",code);
							Object.put("name",name);
							Object.put("price",price);
							Object.put("cost",cost);
							Object.put("image","");
							Object.put("sync",syn);



						} catch (JSONException e) {
							e.printStackTrace();

						}




						final JSONObject ProductObject = new JSONObject();

						try {

							ProductObject.put("token",token);
							ProductObject.put("data",Object);


						} catch (JSONException e) {
							e.printStackTrace();

						}


						Toast.makeText(getActivity().getBaseContext(),
								" ProductObject = " + ProductObject,
								Toast.LENGTH_SHORT).show();

						Log.d(TAG, ProductObject.toString());



						JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
								InventoryDaoAndroid.SERVER_URL_PUT, ProductObject,

								new Response.Listener<JSONObject>() {

									@Override
									public void onResponse(JSONObject response) {

										saveToServerSuccess();
										Log.d(TAG, response.toString());
									}

								}, new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError error) {

								Log.d(TAG, "Error: " + error.getMessage());
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
								" Fail Connect",
								Toast.LENGTH_SHORT).show();

							saveToLocalStorage();

					}

				}
			}
		});


		
		clearButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(barcodeBox.getText().toString().equals("") && nameBox.getText().toString().equals("") && priceBox.getText().toString().equals("")){
					UpdateProductDialogFragment.this.dismiss();
				} else {
					clearAllBox();
				}
			}
		});
	}

	public boolean checkNetworkConnection(){

		ConnectivityManager connectivityManager =(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return (networkInfo!=null && networkInfo.isConnected());
	}

	/**
	 * Clear all box
	 */
	private void clearAllBox() {
		barcodeBox.setText("");
		nameBox.setText("");
		priceBox.setText("");
	}



	private void saveToServerSuccess(){

		int idProduct = Integer.parseInt(id);
		String name = nameBox.getText().toString();
		String code = barcodeBox.getText().toString();
		String image = "";
//		String image = imageBox.getText().toString();
		String gpk  = gpkText.getText().toString();
		int	category = CategoryId;;
		int	topping_group = groupToppingId;
		double price = Double.parseDouble(priceBox.getText().toString());
		double cost = 0;
//		double cost = Double.parseDouble(costBox.getText().toString());
		String status = "use";
		int syn = InventoryDaoAndroid.SYNC_STATUS_OK;

		productCatalog.editProduct(idProduct,category,topping_group,name,image,cost,price,status,code,gpk,syn);
		clearAllBox();
		UpdateProductDialogFragment.this.dismiss();



	}


	private void saveToLocalStorage(){

		int idProduct = Integer.parseInt(id);
		String name = nameBox.getText().toString();
		String code = barcodeBox.getText().toString();
		String image = "";
//		String image = imageBox.getText().toString();
		String gpk  = gpkText.getText().toString();
		int	category = CategoryId;;
		int	topping_group = groupToppingId;
		double price = Double.parseDouble(priceBox.getText().toString());
		double cost = 0;
//		double cost = Double.parseDouble(costBox.getText().toString());
		String status = "use";
		int syn = InventoryDaoAndroid.SYNC_STATUS_OK;

		productCatalog.editProduct(idProduct,category,topping_group,name,image,cost,price,status,code,gpk,syn);
		clearAllBox();
		UpdateProductDialogFragment.this.dismiss();

	}


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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);

		if (scanningResult != null) {
			String scanContent = scanningResult.getContents();
			barcodeBox.setText(scanContent);
		} else {
			Toast.makeText(getActivity().getBaseContext(),
					res.getString(R.string.fail),
					Toast.LENGTH_SHORT).show();
		}
	}


	private void showCategorySpinner(ArrayList<CategoryProduct> list) {

		CategoryList = new ArrayList<Map<String, String>>();
		for(CategoryProduct categoryProduct : list) {

			CategoryList.add(categoryProduct.toMap());

		}

		CategorySpinnerAdapter customAdapter = new CategorySpinnerAdapter(getContext(), CategoryList);
		categorySpinner.setAdapter(customAdapter);

		categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				Object item = parent.getItemAtPosition(pos);

				String test_id = CategoryList.get(pos).get("cate_product_id");
				CategoryId = Integer.parseInt(test_id);
			}
			public void onNothingSelected(AdapterView<?> parent) {

				CategoryId = 1;
			}
		});

	}
	private void showGroupToppingSpinner(ArrayList<GroupToppingProduct> list) {

		groupToppingList = new ArrayList<Map<String, String>>();
		for (GroupToppingProduct groupToppingProduct : list) {

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

				groupToppingId = 1;
			}
		});

	}
}
