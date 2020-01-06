package com.refresh.pos.ui.inventory;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.android.volley.toolbox.StringRequest;
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
import com.refresh.pos.domain.inventory.ToppingProduct;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.techicalservices.inventory.InventoryDaoAndroid;
import com.refresh.pos.ui.component.UpdatableFragment;
import com.refresh.pos.ui.main.ProductToppingAdapter;
import com.refresh.pos.ui.setting.CategorySpinnerAdapter;
import com.refresh.pos.ui.setting.GroupToppingSpinnerAdapter;
import com.refresh.pos.ui.setting.OpenProductFragmentDialog;
import com.refresh.pos.ui.setting.ProductFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class AddProductDialogFragment extends DialogFragment {


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
	private String groupToppingId;
	private int CategoryId;

	private RecyclerView groupTopping;
	private Spinner categorySpinner;
	private ArrayList<Map<String, String>> groupToppingList;
	private ArrayList<Map<String, String>> CategoryList;

	private CheckBox checkbox_tp;
	private CateProductCatolog cateProductCatolog;
	private List<GroupToppingProduct> mModelList;





	private DialogInterface.OnDismissListener onDismissListener;

	/**
	 * Construct a new AddProductDialogFragment
     * @param fragment
     */
	public AddProductDialogFragment( UpdatableFragment fragment) {
		
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
		
		View v = inflater.inflate(R.layout.layout_addproduct, container,
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
		groupTopping = (RecyclerView) v.findViewById(R.id.groupTopping);
		categorySpinner = (Spinner) v.findViewById(R.id.categorySpinner);



		initUI();
		return v;
	}

	/**
	 * Construct a new 
	 */
	private void initUI() {

		showCategorySpinner((ArrayList<CategoryProduct>) cateProductCatolog.getAllCategoryProduct());
		showGroupToppingSpinner((ArrayList<GroupToppingProduct>) productCatalog.getAllGroupTopping());


		String SALTCHARS = "1234567890";
		StringBuilder salt = new StringBuilder();

		Random rnd = new Random();
		while (salt.length() < 3) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}

		String saltStr = salt.toString();
		barcodeBox.setText(saltStr);


		String SALTCHARST = "abcdefghijklmnopqrstuvwxyz1234567890";
		StringBuilder salts = new StringBuilder();
		Random rnds = new Random();
		while (salts.length() < 5) { // length of the random string.
			int indexs = (int) (rnds.nextFloat() * SALTCHARST.length());
			salts.append(SALTCHARST.charAt(indexs));
		}

		String gpk_txt = salts.toString();
		gpkText.setText(gpk_txt);


		checkbox_tp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				if (isChecked){

					groupToppingId = "";
					groupTopping.setVisibility(View.GONE);

				}else {

					groupTopping.setVisibility(View.VISIBLE);
					Toast.makeText(getActivity().getBaseContext(),
							" UnCheck!",
							Toast.LENGTH_SHORT).show();
				}


			}
		});



		scanButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				IntentIntegratorSupportV4 scanIntegrator = new IntentIntegratorSupportV4(AddProductDialogFragment.this);
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


						String toppingGroup = "";
						String toppingGroup2 = "";
						String group_topping_id = "";
						String group_topping_id2 = "";
						String strSeparator = ",";


						for (GroupToppingProduct model : mModelList) {

							if (model.isSelected()) {

								toppingGroup += model.getId()+ ":";
								group_topping_id += model.getId()+ strSeparator;

								group_topping_id2 = group_topping_id.substring(0,group_topping_id.length()-1);
								toppingGroup2 = toppingGroup.substring(0,toppingGroup.length()-1);

							}
						}

						Toast.makeText(getActivity().getBaseContext(),
								group_topping_id2,
								Toast.LENGTH_SHORT).show();



						final JSONObject Object = new JSONObject();

						int syn = InventoryDaoAndroid.SYNC_STATUS_FAILED;
						String name = nameBox.getText().toString();
						String code = barcodeBox.getText().toString();
						String image = "";
//		String image = imageBox.getText().toString();
						String gpk  = gpkText.getText().toString();
						int	category = CategoryId;;
						String	topping_group = toppingGroup2;
						double price = Double.parseDouble(priceBox.getText().toString());
						double cost = price;
//		double cost = Double.parseDouble(costBox.getText().toString());
						String status = "use";


						try {

							Object.put("gpk",gpk);
							Object.put("cat_id",category);
							Object.put("topping",topping_group);
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



						JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
								InventoryDaoAndroid.SERVER_URL_POST, ProductObject,

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
					AddProductDialogFragment.this.dismiss();
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

		String toppingGroup = "";
		String toppingGroup2 = "";
		String group_topping_id = "";
		String group_topping_id2 = "";
		String strSeparator = ",";


		for (GroupToppingProduct model : mModelList) {

			if (model.isSelected()) {

				toppingGroup += model.getId()+ ":";
				toppingGroup2 = toppingGroup.substring(0,toppingGroup.length()-1);
				group_topping_id += model.getId()+ strSeparator;
				group_topping_id2 = group_topping_id.substring(0,group_topping_id.length()-1);



			}
		}



		String name = nameBox.getText().toString();
		String code = barcodeBox.getText().toString();
		String image = "";
//		String image = imageBox.getText().toString();
		String gpk  = gpkText.getText().toString();
		int	category = CategoryId;
		String	topping_group = group_topping_id2;
		double price = Double.parseDouble(priceBox.getText().toString());
		double cost = 0;
//		double cost = Double.parseDouble(costBox.getText().toString());
		String status = "use";
		int syn = InventoryDaoAndroid.SYNC_STATUS_OK;

		boolean success = productCatalog.addProduct(category,topping_group,name,image,cost,price,status,code,gpk,syn);


		if (success) {

			Toast.makeText(getActivity().getBaseContext(),
					res.getString(R.string.success) + ", "
							+ nameBox.getText().toString(),
					Toast.LENGTH_SHORT).show();

            clearAllBox();
			AddProductDialogFragment.this.dismiss();

		} else {
			Toast.makeText(getActivity().getBaseContext(),
					res.getString(R.string.fail),
					Toast.LENGTH_SHORT).show();
		}
	}


	private void saveToLocalStorage(){

		String toppingGroup = "";
		String toppingGroup2 = "";
		String group_topping_id = "";
		String group_topping_id2 = "";
		String strSeparator = ",";


		for (GroupToppingProduct model : mModelList) {

			if (model.isSelected()) {

				toppingGroup += model.getId()+ ":";
				group_topping_id += model.getId()+ strSeparator;

				group_topping_id2 = group_topping_id.substring(0,group_topping_id.length()-1);
				toppingGroup2 = toppingGroup.substring(0,toppingGroup.length()-1);


			}
		}



		int syn = InventoryDaoAndroid.SYNC_STATUS_FAILED;
		String name = nameBox.getText().toString();
		String code = barcodeBox.getText().toString();
		String image = "";
//		String image = imageBox.getText().toString();
		String gpk  = gpkText.getText().toString();
		int	category = CategoryId;;
		String	topping_group = group_topping_id2;
		double price = Double.parseDouble(priceBox.getText().toString());
		double cost = 0;
//		double cost = Double.parseDouble(costBox.getText().toString());
		String status = "use";


		boolean success = productCatalog.addProduct(category,topping_group,name,image,cost,price,status,code,gpk,syn);

		if (success) {
//			String test = DatabaseContents.TABLE_PRODUCT_CATALOG.toString();
//			Toast.makeText(getActivity().getBaseContext(),
//					test + ", "
//							+ nameBox.getText().toString() +"gpk = "+ saltStr,
//					Toast.LENGTH_SHORT).show();

			clearAllBox();
			AddProductDialogFragment.this.dismiss();

		} else {

			Toast.makeText(getActivity().getBaseContext(),
					res.getString(R.string.fail),
					Toast.LENGTH_SHORT).show();
		}
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

		mModelList = new ArrayList<>();

		for(GroupToppingProduct groupToppingProduct : list) {

			mModelList.add(new GroupToppingProduct(groupToppingProduct.getId(),groupToppingProduct.getGpk(),groupToppingProduct.getName(),
					groupToppingProduct.getImage(),groupToppingProduct.getStatus()));

		}

		GroupToppingAdapter testAdapter = new GroupToppingAdapter(getContext(), mModelList);
		groupTopping.setAdapter(testAdapter);
		groupTopping.setLayoutManager(new GridLayoutManager(getActivity(),2));
	}
}
