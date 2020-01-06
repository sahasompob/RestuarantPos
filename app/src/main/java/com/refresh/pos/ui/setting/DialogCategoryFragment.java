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
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.ui.component.UpdatableFragment;
import com.refresh.pos.ui.inventory.AddCategoryProductDialogFragment;
import com.refresh.pos.ui.inventory.AddTableDialogFragment;
import com.refresh.pos.ui.inventory.RecyclerViewAdapterTable;

import java.util.ArrayList;
import java.util.Map;

/**
 * A dialog shows the total change and confirmation for Sale.
 * @author Refresh Team
 *
 */
@SuppressLint("ValidFragment")

public class DialogCategoryFragment extends DialogFragment  {


	private RecyclerView CategoryRecycleview;
	private ArrayList<Map<String, String>> categoryList;
	private CateProductCatolog cateProductCatolog;
	private Button addCategory;
    private UpdatableFragment fragment;



	public DialogCategoryFragment() {
		super();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


		
		View v = inflater.inflate(R.layout.dialog_catgory_setting, container,false);

		CategoryRecycleview = (RecyclerView) v.findViewById(R.id.category_listview);
		addCategory = (Button) v.findViewById(R.id.addCategoryButton);

		try {

			cateProductCatolog = ProductCategory.getInstance().getCateProductCatalog();

		} catch (NoDaoSetException e) {
			e.printStackTrace();
		}


        initUI();

		return v;
	}



	private void initUI() {

		showCategoryList((ArrayList<CategoryProduct>) cateProductCatolog.getAllCategoryProduct());


		addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


				addCategoryPopup(v);

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




	private void showCategoryList(ArrayList<CategoryProduct> list) {

		categoryList = new ArrayList<Map<String, String>>();
		for(CategoryProduct categoryProduct : list) {
			categoryList.add(categoryProduct.toMap());
		}

//		CategoryAdapter testAdapter = new CategoryAdapter(getContext(), categoryList);
//		CategoryRecycleview.setLayoutManager(new GridLayoutManager(getActivity(),5));
//		CategoryRecycleview.setAdapter(testAdapter);


	}






    public void addCategoryPopup(View anchorView) {

		DialogCategoryFragment.this.dismiss();


		AddCategoryProductDialogFragment newFragment = new AddCategoryProductDialogFragment(fragment);
        newFragment.show(getFragmentManager(), "AddTableDialogFragment");

    }


}
