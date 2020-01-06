package com.refresh.pos.ui.sale;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.refresh.pos.R;
import com.refresh.pos.domain.inventory.Inventory;
import com.refresh.pos.domain.inventory.ProductCatalog;
import com.refresh.pos.domain.sale.Register;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.ui.MainActivity;
import com.refresh.pos.ui.component.UpdatableFragment;
import com.refresh.pos.ui.main.MainFragment;
import com.refresh.pos.ui.main.SaleInterface;

import java.util.ArrayList;
import java.util.Map;

public class RecyclerViewAdapterSale extends RecyclerView.Adapter<RecyclerViewAdapterSale.ViewHolder> {


    private static final String TAG = "RecyclerViewAdapterSale";
    private MainFragment mainFragment;
    private SaleInterface saleInterface;





    //vars
    private ArrayList<Map<String, String>> saleList;
    private Context mContext;


    public RecyclerViewAdapterSale(Context mContext, ArrayList<Map<String, String>> saleList,MainFragment mainFragment, SaleInterface saleInterface ) {


        this.saleList = saleList;
        this.mContext = mContext;
        this.mainFragment = mainFragment;
        this.saleInterface = saleInterface;

    }
//
//    public RecyclerViewAdapterSale(Activity dialogContext) {
//// Here we're getting the activity's context,
//// by setting the adapter on the activity with (this)
//        this.dialogContext=dialogContext;
//
//    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_lineitem, parent, false);


        return new ViewHolder(view,saleInterface);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

            holder.setIsRecyclable(false);
            holder.name.setText(saleList.get(position).get("name"));
            holder.quantity.setText(saleList.get(position).get("quantity"));
            holder.price.setText(saleList.get(position).get("price"));
            holder.topping_price.setText(saleList.get(position).get("topping_price"));
            String topping_name = saleList.get(position).get("topping_name");

            if (topping_name.equals("")){


            }else {

                holder.toppingName.setVisibility(View.VISIBLE);
                holder.toppingName.setText(topping_name);
            }


//            String[] testloop = topping_name.split(",");
//
//            for(int i = 0; i < testloop.length; i++) {
//
//                holder.toppingName.setText(testloop[0]);
//
//            }





    }


    @Override
    public int getItemCount() {

        return saleList.size();
    }

    // Your interface to send data to your fragment
    public interface SaleInterface{

        void OnItemClicked(String id, String name, String qty, String price, int position, String topping,String topping_group);

    }



    public class ViewHolder extends RecyclerView.ViewHolder{


        private TextView name;
        private TextView quantity;
        private TextView price;
        private TextView topping_price;
        private TextView toppingName;


        public ViewHolder(View itemView, final SaleInterface saleInterface) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.price);
            topping_price = itemView.findViewById(R.id.topping_price);
            toppingName = itemView.findViewById(R.id.toppingName);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    saleInterface.OnItemClicked(saleList.get(getAdapterPosition()).get("id"),
                            saleList.get(getAdapterPosition()).get("name"),
                            saleList.get(getAdapterPosition()).get("quantity"),
                            saleList.get(getAdapterPosition()).get("price"),
                            getAdapterPosition(),
                            saleList.get(getAdapterPosition()).get("topping_name"),
                            saleList.get(getAdapterPosition()).get("topping_group")


                    );

                }
            });


        }




    }

}