package com.refresh.pos.ui.setting;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.refresh.pos.R;
import com.refresh.pos.domain.inventory.Inventory;
import com.refresh.pos.domain.inventory.ProductCatalog;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.ui.component.UpdatableFragment;
import com.refresh.pos.ui.inventory.InventoryFragment;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class LanguageFragment extends UpdatableFragment {


    private InventoryFragment invenroryfragment;
    private ProductCatalog productCatalog;
    private ArrayList<Map<String, String>> productRecycleView;
    private Button language_btn;
    private RecyclerView openAddProductRecycleview;



    public LanguageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_language, container, false);


//        openAddProductRecycleview = (RecyclerView) view.findViewById(R.id.product_list);
        language_btn = (Button) view.findViewById(R.id.language_btn);

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
//        showProductList((ArrayList<Product>) productCatalog.getAllProduct());



    }

//    private void showProductList(ArrayList<Product> list) {
//
//        productRecycleView = new ArrayList<Map<String, String>>();
//        for(Product product : list) {
//
//            productRecycleView.add(product.toMap());
//
//        }
//
//
//        RecyclerViewAdapterProduct testAdapter = new RecyclerViewAdapterProduct(getContext(), productRecycleView,invenroryfragment);
//        openAddProductRecycleview.setAdapter(testAdapter);
//        openAddProductRecycleview.setLayoutManager(new GridLayoutManager(getActivity(),6));
////        testAdapter.notifyDataSetChanged();
//
////			testAdapter.notifyDataSetChanged();
//
//    }


//    public void openProduct(View anchorView) {
//
//        AddProductDialogFragment addProduct = new AddProductDialogFragment(invenroryfragment);
//        addProduct.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//
//                refresh();
//
//            }
//        });
//        addProduct.show(getFragmentManager(), "AddProduct");
//
//
//
//    }



//    public void refresh(){
//
//        showProductList((ArrayList<Product>) productCatalog.getAllProduct());
//
//    }



    @Override
    public void update() {

    }
}
