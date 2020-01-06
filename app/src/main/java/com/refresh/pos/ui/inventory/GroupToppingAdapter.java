package com.refresh.pos.ui.inventory;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.refresh.pos.R;
import com.refresh.pos.domain.inventory.GroupToppingProduct;
import com.refresh.pos.domain.inventory.ToppingProduct;
import com.refresh.pos.ui.setting.CategoryFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroupToppingAdapter extends RecyclerView.Adapter<GroupToppingAdapter.ViewHolder> {


    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<Map<String, String>> toppingList;
    private List<GroupToppingProduct> mtoppingProducts;
    private Context mContext;
    private CategoryFragment fragment;
    private FragmentManager fragmentManager;

    // Interface Object









    public GroupToppingAdapter(Context mContext, List<GroupToppingProduct> toppingProducts) {

        mtoppingProducts=toppingProducts;
        this.mContext = mContext;


    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_grouptp, parent, false);



        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.setIsRecyclable(false);

        final GroupToppingProduct model = mtoppingProducts.get(position);

        final String name = model.getName();
        holder.group_name.setText(name);
        holder.cardview_id.setBackgroundResource(model.isSelected() ? R.drawable.btn_green: R.drawable.btn_white);
        holder.cardview_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setSelected(!model.isSelected());
                holder.cardview_id.setBackgroundResource(model.isSelected() ? R.drawable.btn_green : R.drawable.btn_white);

            }
        });

//

//        Bundle bundle = new Bundle();
//        bundle.putString("name",current.getName());
//        inventoryFragment.setArguments(bundle);


    }


    @Override
    public int getItemCount() {

        return mtoppingProducts.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder{


            TextView group_name;
            CardView cardview_id;


        public ViewHolder(View itemView) {
            super(itemView);

            group_name = itemView.findViewById(R.id.group_name);
            cardview_id = itemView.findViewById(R.id.cardview_id);


//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    adapterInterface.OnItemClicked(tableList.get(getAdapterPosition()).get("table_id"));
//
//
//
//
//
//                }
//            });

        }



    }

}