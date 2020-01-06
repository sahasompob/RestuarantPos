package com.refresh.pos.ui.setting;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
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
import com.refresh.pos.domain.Table.TableCatolog;
import com.refresh.pos.domain.Table.TableLedger;
import com.refresh.pos.domain.Table.Table_Detail;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.ui.component.UpdatableFragment;
import com.refresh.pos.ui.inventory.AddCategoryProductDialogFragment;
import com.refresh.pos.ui.inventory.AddTableDialogFragment;

import java.util.ArrayList;
import java.util.Map;

/**
 * A dialog of adding a Product.
 *
 * @author Refresh Team
 */
@SuppressLint("ValidFragment")
public class TableFragment extends UpdatableFragment {



    private RecyclerView tableRecycleview;
    private ArrayList<Map<String, String>> numberTable;
    private TableCatolog tableCatolog;
    private Button addTableButton;
    private Resources res;
    private UpdatableFragment fragment;







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            tableCatolog = TableLedger.getInstance().getTableCatalog();
        } catch (NoDaoSetException e) {
            e.printStackTrace();
        }

        View v = inflater.inflate(R.layout.layout_table, container,
                false);

        tableRecycleview = (RecyclerView) v.findViewById(R.id.table_listview);
        addTableButton = (Button) v.findViewById(R.id.addTableButton);
//        addCategory_btn = (Button) view.findViewById(R.id.add_category_food_btn);


        res = getResources();

        initUI();








        return v;
    }


    /**
     * Construct a new
     */
    private void initUI() {

        showTableList((ArrayList<Table_Detail>) tableCatolog.getAllTable());
        //        addCategory_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPopupCategory(view);
//            }
//        });


        addTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                addTablePopup(v);

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


    private void showTableList(ArrayList<Table_Detail> list) {

        numberTable = new ArrayList<Map<String, String>>();
        for(Table_Detail table_detail : list) {
            numberTable.add(table_detail.toMap());
        }

        TableAdapter testAdapter = new TableAdapter(getContext(), numberTable,tableSettingInterface);
        tableRecycleview.setLayoutManager(new GridLayoutManager(getActivity(),6));
        tableRecycleview.setAdapter(testAdapter);
//			testAdapter.notifyDataSetChanged();

    }

    TableAdapter.TableSettingInterface tableSettingInterface = new TableAdapter.TableSettingInterface() {
        @Override
        public void OnItemClicked(int position, String id, String name, String gpk, String sync, String image, String status) {

            Bundle bundle = new Bundle();
            bundle.putString("id",id);
            bundle.putString("name",name);
            bundle.putString("gpk",gpk);
            bundle.putString("sync",sync);
            bundle.putString("image",image);
            bundle.putString("status",status);



            UpdateTableDialogFragment d = new UpdateTableDialogFragment(fragment);
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




    public void addTablePopup(View anchorView) {

        AddTableDialogFragment d = new AddTableDialogFragment(fragment);
        d.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                refresh();

            }
        });
        d.show(getFragmentManager(), "AddTableDialogFragment");


    }


    public void refresh(){

        showTableList((ArrayList<Table_Detail>) tableCatolog.getAllTable());


    }

    @Override

        public void update() {


    }


}
