package com.refresh.pos.ui.setting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.refresh.pos.R;
import com.refresh.pos.domain.ProductCategory.CateProductCatolog;
import com.refresh.pos.domain.ProductCategory.CategoryProduct;
import com.refresh.pos.domain.ProductCategory.ProductCategory;
import com.refresh.pos.domain.inventory.Inventory;
import com.refresh.pos.domain.inventory.Product;
import com.refresh.pos.domain.inventory.ProductCatalog;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.techicalservices.inventory.InventoryDao;
import com.refresh.pos.ui.component.UpdatableFragment;
import com.refresh.pos.ui.inventory.AddProductDialogFragment;
import com.refresh.pos.ui.inventory.AddTableDialogFragment;
import com.refresh.pos.ui.inventory.InventoryFragment;
//import com.refresh.pos.ui.inventory.RecyclerViewAdapter;
import com.refresh.pos.ui.inventory.RecyclerViewAdapterProduct;
import com.refresh.pos.ui.inventory.RecyclerViewAdapterTable;
import com.refresh.pos.ui.setting.ProductFragment;

import java.util.ArrayList;
import java.util.Map;

/**
 * A dialog shows the total change and confirmation for Sale.
 * @author Refresh Team
 *
 */
@SuppressLint("ValidFragment")
public class OpenProductFragmentDialog extends DialogFragment  {

	View v = null;
	private RecyclerView openAddProductRecycleview;
	private CateProductCatolog cateProductCatolog;
	private Button add_new_product;
    private UpdatableFragment fragment;
    private UpdatableFragment productFragment;

    private InventoryFragment invenroryfragment;
	private ProductCatalog productCatalog;
	private ArrayList<Map<String, String>> productRecycleView;





	public OpenProductFragmentDialog() {
		super();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


		
		v = inflater.inflate(R.layout.dialog_open_add_product, container,false);

		openAddProductRecycleview = (RecyclerView) v.findViewById(R.id.add_product);
        add_new_product = (Button) v.findViewById(R.id.add_new_product);

		try {

			productCatalog = Inventory.getInstance().getProductCatalog();

		} catch (NoDaoSetException e) {
			e.printStackTrace();
		}


        initUI();

		return v;
	}



	private void initUI() {



		showProductList((ArrayList<Product>) productCatalog.getAllProduct());

        add_new_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProductDialog(view);
            }
        });


	}

//
//	private void showList(List<LineItem> list) {
//		lineitemList = new ArrayList<Map<String, String>>();
//		for(LineItem line : list) {
//			lineitemList.add(line.toMap());
//		}
////
////		SimpleAdapter sAdap = new SimpleAdapter(SaleDetailActivity.this, lineitemList,
////				R.layout.listview_lineitem, new String[]{"name","quantity","price"}, new int[] {R.id.name,R.id.quantity,R.id.price});
////		lineitemListView.setAdapter(sAdap);
//	}

	/**
	 * End
	 */
	// This is the interface declared inside your adapter.
//	RecyclerViewAdapterTable.FragmentCommunication adapterInterface = new RecyclerViewAdapterTable.FragmentCommunication() {
//		@Override
//		public void OnItemClicked(String item_id) {
//
////			Toast.makeText(getContext(), item_id, Toast.LENGTH_SHORT).show();
//
//			Intent i = new Intent()
//					.putExtra("data",item_id);
//			getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
//			OpenProductFragmentDialog.this.dismiss();
////			searchBox.setText(item_id);
//
//
//		}
//	};


	private void showProductList(ArrayList<Product> list) {

		productRecycleView = new ArrayList<Map<String, String>>();
		for(Product product : list) {

			productRecycleView.add(product.toMap());

		}



//
//		RecyclerViewAdapterProduct testAdapter = new RecyclerViewAdapterProduct(getContext(), productRecycleView,invenroryfragment);
//        openAddProductRecycleview.setAdapter(testAdapter);
//        openAddProductRecycleview.setLayoutManager(new GridLayoutManager(getActivity(),4));
////        testAdapter.notifyDataSetChanged();

//			testAdapter.notifyDataSetChanged();

	}






    public void addProductDialog(View anchorView) {

		OpenProductFragmentDialog.this.dismiss();
        AddProductDialogFragment newFragment = new AddProductDialogFragment(fragment);
        newFragment.show(getFragmentManager(), "");

    }


	public void update() {

        productFragment.update();

	}




}
