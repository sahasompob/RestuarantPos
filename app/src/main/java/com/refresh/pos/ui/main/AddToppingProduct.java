package com.refresh.pos.ui.main;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.refresh.pos.R;
import com.refresh.pos.domain.DateTimeStrategy;
import com.refresh.pos.domain.ProductCategory.CateProductCatolog;
import com.refresh.pos.domain.Table.TableCatolog;
import com.refresh.pos.domain.inventory.GroupToppingProduct;
import com.refresh.pos.domain.inventory.Inventory;
import com.refresh.pos.domain.inventory.ProductCatalog;
import com.refresh.pos.domain.inventory.ToppingProduct;
import com.refresh.pos.domain.sale.Register;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.ui.component.UpdatableFragment;
import com.refresh.pos.ui.setting.GroupToppingSpinnerAdapter;
import com.refresh.pos.ui.setting.ToppingAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A dialog of adding a Product.
 * 
 * @author Refresh Team
 *
 */
@SuppressLint("ValidFragment")

public class AddToppingProduct extends DialogFragment {


	private CateProductCatolog cateProductCatolog;
	private Button confirmAddCategory;
	private Button clearAddCategory;
	private MainFragment fragment;
	private Resources res;
	private ProductCatalog productCatalog;
	private DialogInterface.OnDismissListener onDismissListener;
	private ArrayList<Map<String, String>> toppingList;
	private RecyclerView toppingRecycleview;
	private String idProduct;
	private String name;
	private String position;
	private String topping_id;
    private String tableNo;
    private Register register;
    private List<ToppingProduct> mModelList;
    private TextView productName;
    private EditText quantityBox;





    /**
	 * Construct a new AddProductDialogFragment
	 * @param fragment
	 */
	public AddToppingProduct(MainFragment fragment) {
		
		super();
		this.fragment = fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		try {
			productCatalog = Inventory.getInstance().getProductCatalog();
            register = Register.getInstance();
		} catch (NoDaoSetException e) {
			e.printStackTrace();
		}
		
		View v = inflater.inflate(R.layout.layout_add_product_tp, container,
				false);
		
		res = getResources();

		confirmAddCategory = (Button) v.findViewById(R.id.confirmAddCategory);
		clearAddCategory = (Button) v.findViewById(R.id.clearAddCategory);
		toppingRecycleview = (RecyclerView) v.findViewById(R.id.topping_list);
		productName = (TextView) v.findViewById(R.id.name_product);
        quantityBox = (EditText) v.findViewById(R.id.quantityBox);



		idProduct = getArguments().getString("idProduct");
		name = getArguments().getString("name");
		position = getArguments().getString("position");
		topping_id = getArguments().getString("topping_group");
        tableNo = getArguments().getString("tableNo");

		productName.setText(name);

		initUI();
		return v;
	}

	/**
	 * Construct a new 
	 */
	private void initUI() {

		quantityBox.setText("1");
//        List<ToppingProduct> list = null;
//        list = (productCatalog.getToppingByGroupId(2));

		showToppingMultiList();
        showToppingList(productCatalog.getToppingByGroupId(topping_id));






//		scanButton.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				IntentIntegratorSupportV4 scanIntegrator = new IntentIntegratorSupportV4(AddCategoryProductDialogFragment.this);
//				scanIntegrator.initiateScan();
//			}
//		});

		confirmAddCategory.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {


                int productID = Integer.parseInt(idProduct);
                int numberTable = Integer.parseInt(tableNo);
                String topping_group = topping_id;

				String topping = "";
				String topping_name = "";
				String topping_name2 = "";
				String strSeparator = ",";
				int totalToppingPrice = 0;


				for (ToppingProduct model : mModelList) {

					if (model.isSelected()) {

						topping += model.getId()+":"+ model.getName()+":"+model.getPrice()+ strSeparator;
						topping_name += model.getName()+ strSeparator;
						totalToppingPrice+= model.getPrice();

						topping_name2 = topping_name.substring(0,topping_name.length()-1);


					}
				}



                String quantityBox_text = quantityBox.getText().toString();
                int quantity = Integer.parseInt(quantityBox_text);



                register.addItem_temp(productCatalog.getProductById(productID),
						quantity,
						DateTimeStrategy.getCurrentTime(),
						numberTable,
						"busy",
						topping_group,
						topping,
						topping_name2,
						totalToppingPrice);

                register.holdOrderSale(DateTimeStrategy.getCurrentTime(),numberTable,"busy");

//				Toast.makeText(getActivity().getBaseContext(),
//						numberTable + " "
//								+"",
//						Toast.LENGTH_SHORT).show();


                    clearAllBox();
                    fragment.test();
                    AddToppingProduct.this.dismiss();





			}
		});



		clearAddCategory.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				if(name_edt.getText().toString().equals("")){
//					AddToppingProduct.this.dismiss();
//				} else {
//					clearAllBox();
//				}
			}
		});
	}

	/**
	 * Clear all box
	 */
	private void clearAllBox() {
//		name_edt.setText("");

	}

	private void showToppingMultiList() {

		String test = "1,2";
		String[] testloop = test.split(",");



		for (int ii = 0; ii < testloop.length; ii++){

			int aaa = Integer.parseInt(testloop[ii]);

			Toast.makeText(getActivity().getBaseContext(),
					"aaa = " + aaa,
					Toast.LENGTH_SHORT).show();

//			showToppingList(productCatalog.getToppingByGroupId(id));

		}


	}


	private void showToppingList(List<ToppingProduct> list) {

        mModelList = new ArrayList<>();


		for(ToppingProduct toppingProduct : list) {

			mModelList.add(new ToppingProduct(toppingProduct.getId(),toppingProduct.getGpk(),
					                          toppingProduct.getGroupId(),toppingProduct.getName(),
											  toppingProduct.getPrice(),toppingProduct.getImage(),toppingProduct.getStatus()));

		}

		ProductToppingAdapter testAdapter = new ProductToppingAdapter(getContext(), mModelList);
		toppingRecycleview.setAdapter(testAdapter);
		toppingRecycleview.setLayoutManager(new GridLayoutManager(getActivity(),2));

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




}
