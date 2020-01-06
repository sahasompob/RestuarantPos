package com.refresh.pos.ui.setting;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.refresh.pos.R;
import com.refresh.pos.domain.ProductCategory.CategoryProduct;
import com.refresh.pos.domain.inventory.Inventory;
import com.refresh.pos.domain.inventory.Product;
import com.refresh.pos.domain.inventory.ProductCatalog;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.ui.component.UpdatableFragment;
import com.refresh.pos.ui.inventory.AddProductDialogFragment;
import com.refresh.pos.ui.inventory.InventoryFragment;
import com.refresh.pos.ui.inventory.RecyclerViewAdapterProduct;
import com.refresh.pos.ui.setting.OpenProductFragmentDialog;
import com.refresh.pos.ui.sale.OpenTableFragmentDialog;
import com.refresh.pos.ui.sale.ReportFragment;
import com.refresh.pos.ui.sale.SaleFragment;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class ProductFragment extends UpdatableFragment {


    private InventoryFragment invenroryfragment;
    private ProductCatalog productCatalog;
    private ArrayList<Map<String, String>> productRecycleView;
    private Button add_new_product;
    private RecyclerView openAddProductRecycleview;



    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_product_fragment, container, false);


        openAddProductRecycleview = (RecyclerView) view.findViewById(R.id.product_list);
        add_new_product = (Button) view.findViewById(R.id.add_new_product);

        try {

            productCatalog = Inventory.getInstance().getProductCatalog();

        } catch (NoDaoSetException e) {
            e.printStackTrace();
        }


        initUI();
        // Inflate the layout for this fragment
        return view;
    }

    private void initUI() {

        showProductList((ArrayList<Product>) productCatalog.getAllProduct());

        add_new_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProduct(view);
            }
        });

    }

    private void showProductList(ArrayList<Product> list) {

        productRecycleView = new ArrayList<Map<String, String>>();
        for(Product product : list) {

            productRecycleView.add(product.toMap());

        }


        RecyclerViewAdapterProduct testAdapter = new RecyclerViewAdapterProduct(getContext(), productRecycleView,invenroryfragment,productInterface);
        openAddProductRecycleview.setAdapter(testAdapter);
        openAddProductRecycleview.setLayoutManager(new GridLayoutManager(getActivity(),6));
//        testAdapter.notifyDataSetChanged();

//			testAdapter.notifyDataSetChanged();

    }


    RecyclerViewAdapterProduct.ProductInterface productInterface = new RecyclerViewAdapterProduct.ProductInterface() {
        @Override
        public void OnItemClicked(String id, int position,String name, String topping_group, String price, String gpk, String code, String cate_id, String cost , String image ,String sync) {



            Bundle bundle = new Bundle();
            bundle.putString("id",id);
            bundle.putString("name",name);
            bundle.putString("topping_group",topping_group);
            bundle.putString("price",price);
            bundle.putString("gpk",gpk);
            bundle.putString("code",code);
            bundle.putString("cate_id",cate_id);
            bundle.putString("cost",cost);
            bundle.putString("image",image);
            bundle.putString("sync",sync);


            UpdateProductDialogFragment d = new UpdateProductDialogFragment(invenroryfragment);
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



    public void openProduct(View anchorView) {

        AddProductDialogFragment addProduct = new AddProductDialogFragment(invenroryfragment);
        addProduct.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                refresh();

            }
        });


        addProduct.show(getFragmentManager(), "AddProduct");



    }



    public void refresh(){

        showProductList((ArrayList<Product>) productCatalog.getAllProduct());

    }



    @Override
    public void update() {

    }
}
