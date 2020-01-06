package com.refresh.pos.ui.inventory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
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
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentIntegratorSupportV4;
import com.google.zxing.integration.android.IntentResult;
import com.refresh.pos.R;
import com.refresh.pos.domain.ProductCategory.CateProductCatolog;
import com.refresh.pos.domain.ProductCategory.CategoryProduct;
import com.refresh.pos.domain.ProductCategory.ProductCategory;
import com.refresh.pos.domain.inventory.Inventory;
import com.refresh.pos.domain.inventory.ProductCatalog;
import com.refresh.pos.domain.sale.SaleLastRow;
import com.refresh.pos.techicalservices.CategoryProduct.CateProductDaoAndroid;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.techicalservices.inventory.InventoryDaoAndroid;
import com.refresh.pos.ui.component.UpdatableFragment;
import com.refresh.pos.ui.setting.CategoryFragment;
import com.refresh.pos.ui.setting.DialogCategoryFragment;
import com.refresh.pos.ui.setting.OpenProductFragmentDialog;

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

public class AddCategoryProductDialogFragment extends DialogFragment {

	private EditText name_category;
	private CateProductCatolog cateProductCatolog;
	private EditText image_box;
	private EditText type_category;
	private Button confirmAddCategory;
	private Button clearAddCategory;
	private UpdatableFragment fragment;
	private Resources res;
	private ArrayList<Map<String, String>> categoryList;
	private DialogInterface.OnDismissListener onDismissListener;
    /**
	 * Construct a new AddProductDialogFragment
	 * @param fragment
	 */
	public AddCategoryProductDialogFragment(UpdatableFragment fragment) {
		
		super();
		this.fragment = fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		try {
			cateProductCatolog = ProductCategory.getInstance().getCateProductCatalog();
		} catch (NoDaoSetException e) {
			e.printStackTrace();
		}
		
		View v = inflater.inflate(R.layout.layout_add_category_product, container,
				false);
		
		res = getResources();

		name_category = (EditText) v.findViewById(R.id.name_category);
		image_box = (EditText) v.findViewById(R.id.image_box);
//		type_category = (EditText) v.findViewById(R.id.type_category);
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

				if (name_category.getText().toString().equals("")
					|| image_box.getText().toString().equals("")) {
					
					Toast.makeText(getActivity().getBaseContext(),
							res.getString(R.string.please_input_all), Toast.LENGTH_SHORT)
							.show();
					
				} else {

					if (checkNetworkConnection()){

						saveToServerSuccess();

						final String token = "9bdtrkt9w7apuatehl6o";

						getLatCategory((ArrayList<CategoryProduct>) cateProductCatolog.getLastRow());


						final JSONObject categoryData = new JSONObject();

						for(int ii = 0; ii < categoryList.size(); ii++){

							 int cate_product_id = Integer.parseInt(categoryList.get(ii).get("cate_product_id"));
							 String gpk = categoryList.get(ii).get("gpk");
							 int sequence = Integer.parseInt(categoryList.get(ii).get("sequence"));
							 int parent_id  = Integer.parseInt(categoryList.get(ii).get("paren_id"));
							 String name = categoryList.get(ii).get("name");
							 int sync = Integer.parseInt(categoryList.get(ii).get("sync"));
							 String image= categoryList.get(ii).get("image");
							 String status =categoryList.get(ii).get("status");
							 String type =categoryList.get(ii).get("type");

							try {

								categoryData.put("gpk",gpk);
								categoryData.put("sequence",cate_product_id);
								categoryData.put("parent_id","0");
								categoryData.put("name",name);
								categoryData.put("type",type);
								categoryData.put("image",image);
								categoryData.put("sync","0");

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}


						}

						final JSONObject CategoryObject = new JSONObject();

						try {

							CategoryObject.put("token",token);
							CategoryObject.put("data",categoryData);


						} catch (JSONException e) {
							e.printStackTrace();

						}


						Toast.makeText(getActivity().getBaseContext(),

								" CategoryObject = " + CategoryObject,
								Toast.LENGTH_SHORT).show();

						JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
								CateProductDaoAndroid.SERVER_URL_POST, CategoryObject,

								new Response.Listener<JSONObject>() {

									@Override
									public void onResponse(JSONObject response) {


										Log.d(TAG, response.toString());
										Log.d(TAG, CategoryObject.toString());
									}

								}, new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError error) {

								Log.d(TAG, "Error: " + error.getMessage());
								Log.d(TAG, CategoryObject.toString());
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

				if(image_box.getText().toString().equals("") && name_category.getText().toString().equals("") && type_category.getText().toString().equals("")){
					AddCategoryProductDialogFragment.this.dismiss();
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
		name_category.setText("");
		image_box.setText("");
	}


	private void getLatCategory(ArrayList<CategoryProduct> list) {
		categoryList = new ArrayList<Map<String, String>>();
		for (CategoryProduct categoryProduct : list) {
			categoryList.add(categoryProduct.toMap());

		}
	}

	private void saveToServerSuccess(){


		String SALTCHARS = "abcdefghijklmnopqrstuvwxyz1234567890";
		StringBuilder salt = new StringBuilder();

		Random rnd = new Random();
		while (salt.length() < 5) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}

		String saltStr = salt.toString();

		int syn = InventoryDaoAndroid.SYNC_STATUS_OK;
		String gpk = saltStr;
		int sequence = 0;
		String name = name_category.getText().toString();
		int parent_id = 0;
		String image = image_box.getText().toString();
		String status = "use";
		String type_name = name_category.getText().toString();
		boolean success = cateProductCatolog.addCateProduct(gpk,sequence,parent_id,name,syn,image,status,type_name);

		if (success) {

			clearAllBox();
			AddCategoryProductDialogFragment.this.dismiss();



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
        while (salt.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }

        String saltStr = salt.toString();


        int syn = InventoryDaoAndroid.SYNC_STATUS_FAILED;
        String gpk = saltStr;
        int sequence = 0;
        String name = name_category.getText().toString();
        int parent_id = 0;
        String image = image_box.getText().toString();
        String status = "use";
        String type_name = name_category.getText().toString();
        boolean success = cateProductCatolog.addCateProduct(gpk,sequence,parent_id,name,syn,image,status,type_name);

        if (success) {
            clearAllBox();
            AddCategoryProductDialogFragment.this.dismiss();


        } else {

            Toast.makeText(getActivity().getBaseContext(),
                    res.getString(R.string.fail),
                    Toast.LENGTH_SHORT).show();
        }
    }


	public boolean checkNetworkConnection(){

		ConnectivityManager connectivityManager =(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return (networkInfo!=null && networkInfo.isConnected());
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
			name_category.setText(scanContent);
		} else {
			Toast.makeText(getActivity().getBaseContext(),
					res.getString(R.string.fail),
					Toast.LENGTH_SHORT).show();
		}
	}


}
