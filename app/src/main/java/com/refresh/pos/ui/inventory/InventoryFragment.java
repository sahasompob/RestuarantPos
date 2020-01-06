package com.refresh.pos.ui.inventory;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.refresh.pos.domain.inventory.Inventory;
import com.refresh.pos.domain.inventory.Product;
import com.refresh.pos.domain.inventory.ProductCatalog;
import com.refresh.pos.domain.sale.Register;
import com.refresh.pos.techicalservices.CategoryProduct.CateProductDaoAndroid;
import com.refresh.pos.techicalservices.DatabaseExecutor;
import com.refresh.pos.techicalservices.Demo;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.techicalservices.inventory.InventoryDaoAndroid;
import com.refresh.pos.ui.MainActivity;
import com.refresh.pos.ui.component.UpdatableFragment;
import com.refresh.pos.ui.helper.OnStartDragListener;
import com.refresh.pos.ui.helper.SimpleItemTouchHelperCallback;
import com.refresh.pos.ui.setting.SettingActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * UI for Inventory, shows list of Product in the ProductCatalog.
 * Also use for a sale process of adding Product into sale.
 * 
 * @author Refresh Team
 *
 */
@SuppressLint("ValidFragment")
public class InventoryFragment extends UpdatableFragment implements OnStartDragListener {

													//	Product LAYOUT //

	protected static final int SEARCH_LIMIT = 0;

	private RecyclerView recyclerListView;
	private ListView inventoryListView;
	private RecyclerView categoryListView;
	private ProductCatalog productCatalog;
	private CateProductCatolog cateProductCatolog;
	private List<Map<String, String>> inventoryList;
	private ArrayList<Map<String, String>> categoryListFromSqlite;
	private ArrayList<Map<String, String>> categoryList;
	private ArrayList<Map<String, String>> productRecycleView;
	private Button addProductButton;
	private EditText searchBox;
	private Button scanButton;
	private Button addCategoryBtn;
	private Button settingBtn;
	private RelativeLayout relativeLayout;
	private ViewPager viewPager;
	private Register register;
	private MainActivity main;
	private CardView allCategory;

	private UpdatableFragment saleFragment;
	private Resources res;

	private ItemTouchHelper mItemTouchHelper;

	private int tableNo;







	/**
	 * Construct a new InventoryFragment.
	 * @param saleFragment
	 */
	public InventoryFragment(UpdatableFragment saleFragment) {
		super();

		this.saleFragment = saleFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

//		String strtext=getArguments().getString("message");
//			String nametest = getArguments().getString("name");
//			textview.setText(nametest);


		try {
			productCatalog = Inventory.getInstance().getProductCatalog();
			cateProductCatolog = ProductCategory.getInstance().getCateProductCatalog();
			register = Register.getInstance();
//



		} catch (NoDaoSetException e) {
			e.printStackTrace();
		}

		View view = inflater.inflate(R.layout.layout_inventory, container, false);


		res = getResources();
//		inventoryListView = (ListView) view.findViewById(R.id.productListView);
		categoryListView = (RecyclerView) view.findViewById(R.id.category_listview);

		addProductButton = (Button) view.findViewById(R.id.addProductButton);
		settingBtn = (Button) view.findViewById(R.id.setting_btn);
		scanButton = (Button) view.findViewById(R.id.scanButton);
		searchBox = (EditText) view.findViewById(R.id.searchBox);
		recyclerListView =(RecyclerView) view.findViewById(R.id.productRecycleview);
		allCategory = (CardView) view.findViewById(R.id.category_cardview_id);




//		if(bundle != null){
//			Toast.makeText(getActivity(), bundle.get("key").toString() +" 555555 ", Toast.LENGTH_LONG).show();




//		}else{


//		}
		initUI();
		InitProduct();
		InitCategoryProduct();

		testLoop((ArrayList<CategoryProduct>) cateProductCatolog.getAllCategoryProduct());
		return view;
	}

	/**
	 * Initiate this UI.
	 */


	private void initUI() {


		addProductButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Add eiei",
						Toast.LENGTH_LONG).show();
//				showPopup(v);
			}
		});



		searchBox.addTextChangedListener(new TextWatcher(){
			public void afterTextChanged(Editable s) {
				if (s.length() >= SEARCH_LIMIT) {
					search();
				}
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after){}
			public void onTextChanged(CharSequence s, int start, int before, int count){}
		});


		settingBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent intent = new Intent(getActivity(), SettingActivity.class);
				startActivity(intent);

			}
		});


		scanButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				IntentIntegratorSupportV4 scanIntegrator = new IntentIntegratorSupportV4(InventoryFragment.this);
				scanIntegrator.initiateScan();
			}
		});

		allCategory.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				searchBox.setText("");
			}
		});

	}



	public boolean checkNetworkConnection(){

		ConnectivityManager connectivityManager =(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return (networkInfo!=null && networkInfo.isConnected());
	}



	private void testLoop(ArrayList<CategoryProduct> list) {

		categoryListFromSqlite = new ArrayList<Map<String, String>>();
		for (CategoryProduct categoryProduct : list) {
			categoryListFromSqlite.add(categoryProduct.toMap());

		}

	}


		private void showCategoryList(ArrayList<CategoryProduct> list) {

		categoryList = new ArrayList<Map<String, String>>();
		for(CategoryProduct categoryProduct : list) {
			categoryList.add(categoryProduct.toMap());
		}


//			RecyclerViewAdapterCate testAdapter = new RecyclerViewAdapterCate(getContext(), categoryList,InventoryFragment.this,adapterInterface);
//			categoryListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//			categoryListView.setAdapter(testAdapter);
//			testAdapter.notifyDataSetChanged();

	}


	private void showRecycleList(ArrayList<Product> list) {

		productRecycleView = new ArrayList<Map<String, String>>();
		for(Product product : list) {
			productRecycleView.add(product.toMap());


		}



//		RecyclerViewAdapter testAdapter = new RecyclerViewAdapter(getContext(), productRecycleView,InventoryFragment.this,this,tableNo);
//		recyclerListView.setHasFixedSize(true);
//		recyclerListView.setLayoutManager(new GridLayoutManager(getActivity(),6));
//		recyclerListView.setAdapter(testAdapter);
//
//		ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(testAdapter);
//		mItemTouchHelper = new ItemTouchHelper(callback);
//		mItemTouchHelper.attachToRecyclerView(recyclerListView);



	}

	// This is the interface declared inside your adapter.
	RecyclerViewAdapterCate.FragmentCommunication adapterInterface = new RecyclerViewAdapterCate.FragmentCommunication() {
		@Override
		public void OnItemClicked(String item_id) {

			searchBox.setText(item_id);
		}
	};



	private void InitProduct(){

		String token = "9bdtrkt9w7apuatehl6o";

		JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, InventoryDaoAndroid.SERVER_URL+token, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							String Response = response.getString("message");


							if (Response.equals("success")){

								ArrayList DataFromSqlite = new ArrayList();
								for (int ii = 0; ii < productRecycleView.size(); ii++) {

									DataFromSqlite.add(productRecycleView.get(ii).get("name"));

								}

//								Log.d("categoryListFromSqlite", "categoryListFromSqlite " + myArrlis + " Successfully_InventoryFragment.");

								JSONArray jsonArray = response.getJSONArray("data");
								for (int i = 0; i < jsonArray.length(); i++) {

									JSONObject product = jsonArray.getJSONObject(i);

									if (DataFromSqlite.contains(product.getString("name"))){

										Log.d("Result", "ข้อมูลซ้ำใน Sqlite" );


									}else {

										String code = product.getString("code");
										String name = product.getString("name");
										int	category = product.getInt("cat_id");
										int	topping_group = product.getInt("cat_id");
										double price = product.getDouble("price");;
										double cost = product.getDouble("cost");
										String image = product.getString("image");
										String gpk  = product.getString("gpk");
										String status = "use";
//										String cat_name = product.getString("cat_name");
										int	sync = 1;

//									productCatalog.addProduct(category,topping_group,name,image,cost,price,status,code,gpk,sync);
//										update();

									}


//									fragment.update();

//										Toast.makeText(getActivity().getBaseContext(), name,
//												Toast.LENGTH_SHORT).show();




								}




							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				error.printStackTrace();
			}
		});

		MySingleton.getInstance(getContext()).addtoRequestQue(request);


	}


	private void InitCategoryProduct() {

		String token = "9bdtrkt9w7apuatehl6o";
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, CateProductDaoAndroid.SERVER_URL+token, null,

				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							String Response = response.getString("message");

							if (Response.equals("success")) {


								ArrayList myArrlis = new ArrayList();
								for (int ii = 0; ii < categoryListFromSqlite.size(); ii++) {

									myArrlis.add(categoryListFromSqlite.get(ii).get("name"));

								}

								Log.d("categoryListFromSqlite", "categoryListFromSqlite " + myArrlis + " SuccessfullyC.");


								JSONArray jsonArray = response.getJSONArray("data");
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject product = jsonArray.getJSONObject(i);

									if (myArrlis.contains(product.getString("name"))) {

										Log.d("Result", "Duplicate data " + myArrlis );

									}else {

										String gpk = product.getString("gpk");
										int parent_id = product.getInt("parent_id");
										int sequence = product.getInt("sequence");
										String name = product.getString("name");
										int sync = product.getInt("sync");
										String image = product.getString("image");
										String type = product.getString("type");
										String status = product.getString("status");


										cateProductCatolog.addCateProduct(gpk,sequence,parent_id,name,sync,image,status,type);

										Log.d("Result", "Saving "+ name + " .......");


									}

//                                    showCategoryList((ArrayList<CategoryProduct>) cateProductCatolog.getAllCategoryProduct());



								}



							}


						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				error.printStackTrace();
			}
		});

		MySingleton.getInstance(getContext()).addtoRequestQue(request);


	}



	/**
	 * Search.
	 */

	private void search() {
		String search = searchBox.getText().toString();
//		String testsearch = searchBox.getText().toString();

		if (search.equals("/demo")) {
			testAddProduct();
			searchBox.setText("");
		} else if (search.equals("/clear")) {
			DatabaseExecutor.getInstance().dropAllData();
			searchBox.setText("");
		}
		else if (search.equals("")) {
			showRecycleList((ArrayList<Product>) productCatalog.getAllProduct());
			showCategoryList((ArrayList<CategoryProduct>) cateProductCatolog.getAllCategoryProduct());
		} else {
			List<Product> result = productCatalog.searchProduct(search);
//			List<CategoryProduct> result2 = cateProductCatolog.searchCateProduct(search);
			showRecycleList((ArrayList<Product>) result);
			if (result.isEmpty()) {

			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);

		if (scanningResult != null) {
			String scanContent = scanningResult.getContents();
			searchBox.setText(scanContent);
		} else {
			Toast.makeText(getActivity().getBaseContext(), res.getString(R.string.fail),
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Test adding product
	 */
	protected void testAddProduct() {
		Demo.testProduct(getActivity());
		Toast.makeText(getActivity().getBaseContext(), res.getString(R.string.success),
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * Show popup.
//	 * @param anchorView
	 */
//	public void showPopup(View anchorView) {
//		AddProductDialogFragment newFragment = new AddProductDialogFragment(getContext(), InventoryFragment.this);
//		newFragment.show(getFragmentManager(), "");
//	}



	public void test() {

		saleFragment.update();
	}



	@Override
	public void update() {


        search();
	}

	@Override
	public void onResume() {
		super.onResume();
		update();
	}


	@Override
	public void onStartDrag(RecyclerView.ViewHolder viewHolder) {

		mItemTouchHelper.startDrag(viewHolder);

	}
}