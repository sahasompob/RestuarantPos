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
import com.refresh.pos.domain.inventory.Inventory;
import com.refresh.pos.domain.inventory.Product;
import com.refresh.pos.domain.inventory.ProductCatalog;
import com.refresh.pos.domain.inventory.ToppingProduct;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.ui.component.UpdatableFragment;
import com.refresh.pos.ui.inventory.AddProductDialogFragment;
import com.refresh.pos.ui.inventory.InventoryFragment;
import com.refresh.pos.ui.inventory.RecyclerViewAdapterProduct;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class ToppingFragment extends UpdatableFragment {


    private UpdatableFragment invenroryfragment;
    private ProductCatalog productCatalog;
    private ArrayList<Map<String, String>> toppingList;
    private Button topping_btn;
    private RecyclerView toppingRecycleview;



    public ToppingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_topping, container, false);


        toppingRecycleview = (RecyclerView) view.findViewById(R.id.topping_listview);
        topping_btn = (Button) view.findViewById(R.id.topping_btn);

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
        showToppingList((ArrayList<ToppingProduct>) productCatalog.getAllTopping());


        topping_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addTopping(v);

            }
        });



    }

    private void showToppingList(ArrayList<ToppingProduct> list) {

        toppingList = new ArrayList<Map<String, String>>();
        for(ToppingProduct toppingProduct : list) {

            toppingList.add(toppingProduct.toMap());

        }


        ToppingAdapter testAdapter = new ToppingAdapter(getContext(), toppingList,toppingSettingInterface);
        toppingRecycleview.setAdapter(testAdapter);
        toppingRecycleview.setLayoutManager(new GridLayoutManager(getActivity(),6));
//        testAdapter.notifyDataSetChanged();

//			testAdapter.notifyDataSetChanged();

    }



    ToppingAdapter.ToppingSettingInterface toppingSettingInterface = new ToppingAdapter.ToppingSettingInterface() {
        @Override
        public void OnItemClicked(int position, String id, String groupId, String name , String price , String gpk) {

            Toast.makeText(getActivity(), "Id = " + id +" GroupId = " + groupId + " name = " + name + " Price = " + price,
                    Toast.LENGTH_LONG).show();

            Bundle bundle = new Bundle();
            bundle.putString("id",id);
            bundle.putString("name",name);
            bundle.putString("groupId",groupId);
            bundle.putString("price",price);
            bundle.putString("gpk",gpk);

            UpdateToppingDialogFragment d = new UpdateToppingDialogFragment(invenroryfragment);
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



    public void addTopping(View anchorView) {

        AddToppingDialogFragment addProduct = new AddToppingDialogFragment(invenroryfragment);
        addProduct.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                refresh();

            }
        });
        addProduct.show(getFragmentManager(), "AddProduct");



    }



    public void refresh(){

        showToppingList((ArrayList<ToppingProduct>) productCatalog.getAllTopping());

    }



    @Override
    public void update() {

    }
}
