package com.refresh.pos.ui.inventory;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.refresh.pos.R;
import com.refresh.pos.domain.ProductCategory.CategoryProduct;
import com.refresh.pos.domain.inventory.Inventory;
import com.refresh.pos.domain.inventory.Product;
import com.refresh.pos.domain.inventory.ProductCatalog;
import com.refresh.pos.domain.sale.Register;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.ui.MainActivity;
import com.refresh.pos.ui.component.UpdatableFragment;
import com.refresh.pos.ui.sale.ReportFragment;
import com.refresh.pos.ui.sale.SaleFragment;
import com.refresh.pos.ui.main.MainFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecyclerViewAdapterCate extends RecyclerView.Adapter<RecyclerViewAdapterCate.ViewHolder> {


    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<Map<String, String>> categoryList;
    private List<CategoryProduct> list;
    private Context mContext;
    private FragmentManager fragmentManager;
    // Interface Object

    // Interface Object
    private FragmentCommunication adapterInterface;







    public RecyclerViewAdapterCate(Context mContext, ArrayList<Map<String, String>> categoryList, MainFragment mainFragment, FragmentCommunication adapterInterface) {

        this.categoryList = categoryList;
        this.mContext = mContext;

        // Initialize your interface to send updates to fragment.
        this.adapterInterface = adapterInterface;



    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_category_product, parent, false);



        return new ViewHolder(view,adapterInterface);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.setIsRecyclable(false);

        String name = categoryList.get(position).get("name");
        holder.name.setText(name);


//

//        Bundle bundle = new Bundle();
//        bundle.putString("name",current.getName());
//        inventoryFragment.setArguments(bundle);


    }


    @Override
    public int getItemCount() {

        return categoryList.size();
    }

    // Your interface to send data to your fragment
    public interface FragmentCommunication{

        void OnItemClicked(String item_id);

    }



    public class ViewHolder extends RecyclerView.ViewHolder{


         TextView name;


        public ViewHolder(View itemView,  final RecyclerViewAdapterCate.FragmentCommunication adapterInterface) {
            super(itemView);

            name = itemView.findViewById(R.id.category_name);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    adapterInterface.OnItemClicked(categoryList.get(getAdapterPosition()).get("cate_product_id"));

                }
            });

        }



    }

}