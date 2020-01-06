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
import com.refresh.pos.domain.ProductCategory.ProductCategory;
import com.refresh.pos.domain.Table.TableCatolog;
import com.refresh.pos.domain.inventory.Inventory;
import com.refresh.pos.domain.inventory.ProductCatalog;
import com.refresh.pos.techicalservices.CategoryProduct.CateProductDaoAndroid;
import com.refresh.pos.techicalservices.NoDaoSetException;
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

public class UpdateCategoryDialogFragment extends DialogFragment {
	private String idCategory;
	private String image;
	private String name;
	private String gpk;
	private String sequence;
	private String type;
	private String parent_id;
	private EditText name_category;
	private EditText image_box;
	private CateProductCatolog cateProductCatolog;
	private Button updateAddCategory;
	private Button clearCategory;
	private UpdatableFragment fragment;
	private Resources res;

	private DialogInterface.OnDismissListener onDismissListener;



	/**
	 * Construct a new AddProductDialogFragment
	 * @param fragment
	 */
	public UpdateCategoryDialogFragment(UpdatableFragment fragment) {
		
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
		
		View v = inflater.inflate(R.layout.layout_update_category_product, container,
				false);
		
		res = getResources();

		name_category = (EditText) v.findViewById(R.id.name_category);
		image_box = (EditText) v.findViewById(R.id.image_box);
		updateAddCategory = (Button) v.findViewById(R.id.updateAddCategory);
		clearCategory = (Button) v.findViewById(R.id.clearCategory);

		idCategory = getArguments().getString("id");
		name = getArguments().getString("name");
		gpk = getArguments().getString("gpk");
		sequence = getArguments().getString("sequence");
		type = getArguments().getString("type");
		image = getArguments().getString("image");
		parent_id = getArguments().getString("parent_id");

		initUI();
		return v;
	}

	/**
	 * Construct a new 
	 */
	private void initUI() {



		name_category.setText(name);
		image_box.setText(image);

//		scanButton.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				IntentIntegratorSupportV4 scanIntegrator = new IntentIntegratorSupportV4(AddCategoryProductDialogFragment.this);
//				scanIntegrator.initiateScan();
//			}
//		});

		updateAddCategory.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				if (name_category.getText().toString().equals("")) {

					Toast.makeText(getActivity().getBaseContext(),
							res.getString(R.string.please_input_all), Toast.LENGTH_SHORT)
							.show();

				} else {

					if (checkNetworkConnection()){

						final String token = "9bdtrkt9w7apuatehl6o";


						final JSONObject categoryData = new JSONObject();

						try {

							int sequence_id = Integer.parseInt(idCategory);
//							int parentId = Integer.parseInt(parent_id);

							String nameCate = name_category.getText().toString();
							String imageCate = image_box.getText().toString();


							categoryData.put("gpk",gpk);
							categoryData.put("sequence",sequence_id);
							categoryData.put("parent_id",parent_id);
							categoryData.put("name",nameCate);
							categoryData.put("type",type);
							categoryData.put("image",imageCate);
							categoryData.put("sync","0");

						} catch (JSONException e) {
							e.printStackTrace();

						}



						final JSONObject CategoryObject = new JSONObject();

						try {

							CategoryObject.put("token",token);
							CategoryObject.put("data",categoryData);


						} catch (JSONException e) {
							e.printStackTrace();

						}

						Toast.makeText(getActivity().getBaseContext(),
								CategoryObject.toString()+" // CategoryObject",
								Toast.LENGTH_SHORT)
								.show();


						JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
								CateProductDaoAndroid.SERVER_URL_PUT, CategoryObject,

								new Response.Listener<JSONObject>() {

									@Override
									public void onResponse(JSONObject response) {

										updateToServer();
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



					}else{


						Toast.makeText(getActivity().getBaseContext(),
								" Fail Connect Server",
								Toast.LENGTH_SHORT).show();

						updateToLocalStorage();
					}


				}
			}
		});



		clearCategory.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(name_category.getText().toString().equals("")){
					UpdateCategoryDialogFragment.this.dismiss();
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

	}


	private void updateToServer(){

		int id = Integer.parseInt(idCategory);
//					String pk = "ct002";
//					int sequence = 0;
		String name = name_category.getText().toString();
		String image = image_box.getText().toString();
		String status = "use";


//					boolean success = cateProductCatolog.addCateProduct(nameBox
//							.getText().toString(), barcodeBox.getText()
//							.toString(), Double.parseDouble(priceBox.getText()
//							.toString()));

		cateProductCatolog.editCategory(id,name,image);
		UpdateCategoryDialogFragment.this.dismiss();
	}


	private void updateToLocalStorage(){

		int id = Integer.parseInt(idCategory);
//					String pk = "ct002";
//					int sequence = 0;
		String name = name_category.getText().toString();
		String image = image_box.getText().toString();
		String status = "use";


//					boolean success = cateProductCatolog.addCateProduct(nameBox
//							.getText().toString(), barcodeBox.getText()
//							.toString(), Double.parseDouble(priceBox.getText()
//							.toString()));

		cateProductCatolog.editCategory(id,name,image);
		UpdateCategoryDialogFragment.this.dismiss();

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
