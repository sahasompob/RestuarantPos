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
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.refresh.pos.R;
import com.refresh.pos.domain.inventory.GroupToppingProduct;
import com.refresh.pos.domain.inventory.Inventory;
import com.refresh.pos.domain.inventory.ProductCatalog;
import com.refresh.pos.domain.inventory.ToppingProduct;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.ui.component.UpdatableFragment;
import com.refresh.pos.ui.inventory.InventoryFragment;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class GroupToppingFragment extends UpdatableFragment {


    private InventoryFragment fragment;
    private ProductCatalog productCatalog;
    private ArrayList<Map<String, String>> groupToppingList;
    private Button group_topping_btn;
    private RecyclerView group_topping_listview;



    public GroupToppingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_group_topping, container, false);


        group_topping_listview = (RecyclerView) view.findViewById(R.id.group_topping_listview);
        group_topping_btn = (Button) view.findViewById(R.id.group_topping_btn);

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
        showGroupToppingList((ArrayList<GroupToppingProduct>) productCatalog.getAllGroupTopping());


        group_topping_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addTopping(v);

            }
        });



    }

    private void showGroupToppingList(ArrayList<GroupToppingProduct> list) {

        groupToppingList = new ArrayList<Map<String, String>>();
        for(GroupToppingProduct groupToppingProduct : list) {

            groupToppingList.add(groupToppingProduct.toMap());

        }


        GroupToppingAdapter testAdapter = new GroupToppingAdapter(getContext(), groupToppingList,groupToppingSettingInterface);
        group_topping_listview.setAdapter(testAdapter);
        group_topping_listview.setLayoutManager(new GridLayoutManager(getActivity(),6));
//        testAdapter.notifyDataSetChanged();

//			testAdapter.notifyDataSetChanged();



    }


    GroupToppingAdapter.GroupToppingSettingInterface groupToppingSettingInterface = new GroupToppingAdapter.GroupToppingSettingInterface() {
        @Override
        public void OnItemClicked(int position, String id, String name,String gpk,String image,String status) {

            Bundle bundle = new Bundle();
            bundle.putString("id",id);
            bundle.putString("gpk",gpk);
            bundle.putString("name",name);
            bundle.putString("image",image);
            bundle.putString("status",status);

            UpdateGroupToppingDialogFragment d = new UpdateGroupToppingDialogFragment(fragment);
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

        AddGroupToppingDialogFragment addGroupToppingDialogFragment = new AddGroupToppingDialogFragment(fragment);
        addGroupToppingDialogFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                refresh();

            }
        });

        addGroupToppingDialogFragment.show(getFragmentManager(), "AddProduct");



    }



    public void refresh(){

        showGroupToppingList((ArrayList<GroupToppingProduct>) productCatalog.getAllGroupTopping());

    }



    @Override
    public void update() {

    }
}
