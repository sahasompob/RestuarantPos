package com.refresh.pos.ui.setting;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.refresh.pos.R;
import com.refresh.pos.domain.ProductCategory.CateProductCatolog;
import com.refresh.pos.domain.ProductCategory.CategoryProduct;
import com.refresh.pos.domain.ProductCategory.ProductCategory;
import com.refresh.pos.techicalservices.CategoryProduct.CateProductDaoAndroid;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.ui.component.UpdatableFragment;
import com.refresh.pos.ui.inventory.AddCategoryProductDialogFragment;
import com.refresh.pos.ui.inventory.FragmentCommunication;
import com.refresh.pos.ui.inventory.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * A dialog of adding a Product.
 *
 * @author Refresh Team
 */
@SuppressLint("ValidFragment")
public class CategoryFragment extends UpdatableFragment {



    private RecyclerView CategoryRecycleview;
    private ArrayList<Map<String, String>> categoryList;
    private CateProductCatolog cateProductCatolog;
    private Button addCategory;
    private Resources res;
    private UpdatableFragment fragment;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            cateProductCatolog = ProductCategory.getInstance().getCateProductCatalog();
        } catch (NoDaoSetException e) {
            e.printStackTrace();
        }

        View v = inflater.inflate(R.layout.layout_category, container,
                false);

        CategoryRecycleview = (RecyclerView) v.findViewById(R.id.category_listview);
        addCategory = (Button) v.findViewById(R.id.addCategoryButton);
//        addCategory_btn = (Button) view.findViewById(R.id.add_category_food_btn);


        res = getResources();

        initUI();








        return v;
    }


    /**
     * Construct a new
     */
    private void initUI() {

        InitCategoryProduct((ArrayList<CategoryProduct>) cateProductCatolog.getAllCategoryProduct());
//        addCategory_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPopupCategory(view);
//            }
//        });


        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                addCategoryPopup(v);

            }
        });
    }


//    public void showCategoryList(ArrayList<CategoryProduct> list) {
//
//        categoryList = new ArrayList<Map<String, String>>();
//
//        for (CategoryProduct categoryProduct : list) {
//            categoryList.add(categoryProduct.toMap());
//
//        }
//
//        RecyclerViewAdapterCate testAdapter = new RecyclerViewAdapterCate(getContext(), categoryList, CategoryFragment.this, (RecyclerViewAdapterCate.FragmentCommunication) adapterInterface);
//        categoryListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        categoryListView.setAdapter(testAdapter);
//
//    }


    /**
     * Clear all box
     */
    private void clearAllBox() {


    }


    private void InitCategoryProduct(ArrayList<CategoryProduct> list) {

        categoryList = new ArrayList<Map<String, String>>();
        for(CategoryProduct categoryProduct : list) {
            categoryList.add(categoryProduct.toMap());
        }

        CategoryAdapter testAdapter = new CategoryAdapter(getContext(), categoryList,categorySettingInterface);
        CategoryRecycleview.setLayoutManager(new GridLayoutManager(getActivity(),6));
        CategoryRecycleview.setAdapter(testAdapter);


    }


    CategoryAdapter.CategorySettingInterface categorySettingInterface = new CategoryAdapter.CategorySettingInterface() {
        @Override
        public void OnItemClicked(int position, String id, String name,String image,String gpk,String sequence,String type,String parent_id) {

            Bundle bundle = new Bundle();
            bundle.putString("id",id);
            bundle.putString("gpk",gpk);
            bundle.putString("sequence",sequence);
            bundle.putString("type",type);
            bundle.putString("parent_id",parent_id);
            bundle.putString("name",name);
            bundle.putString("image",image);

            UpdateCategoryDialogFragment d = new UpdateCategoryDialogFragment(fragment);
            d.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                    refresh();

                }
            });
            d.setArguments(bundle);
            d.show(getFragmentManager(), "AddTableDialogFragment");
        }
    };


    public void addCategoryPopup(View anchorView) {

        AddCategoryProductDialogFragment d = new AddCategoryProductDialogFragment(fragment);
        d.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                refresh();

            }
        });
        d.show(getFragmentManager(), "AddCategory");
    }


    public void refresh(){

        InitCategoryProduct((ArrayList<CategoryProduct>) cateProductCatolog.getAllCategoryProduct());


    }

    @Override

        public void update() {


    }
}
