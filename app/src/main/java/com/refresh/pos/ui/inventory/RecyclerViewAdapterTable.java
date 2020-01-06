package com.refresh.pos.ui.inventory;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.refresh.pos.R;
import com.refresh.pos.domain.ProductCategory.CategoryProduct;
import com.refresh.pos.ui.sale.OpenTableFragmentDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecyclerViewAdapterTable extends RecyclerView.Adapter<RecyclerViewAdapterTable.ViewHolder> {


    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<Map<String, String>> tableList;
    private Context mContext;

    // Interface Object

    // Interface Object
    private TableInterface tableInterface;







    public RecyclerViewAdapterTable(Context mContext, ArrayList<Map<String, String>> tableList,TableInterface tableInterface) {

        this.tableList = tableList;
        this.mContext = mContext;
        this.tableInterface = tableInterface;



    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_table, parent, false);



        return new ViewHolder(view,tableInterface);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.setIsRecyclable(false);

        String name = tableList.get(position).get("table_name");
        String status_service = tableList.get(position).get("status_service");
        holder.table_name.setText(name);

        if (status_service.equals("busy")){

            holder.table_name.setBackgroundColor(Color.RED);

//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//
//                public void onClick(View view) {
//
//                    tableInterface.OnItemClicked(tableList.get(position).get("table_id"));
//
//
//                }
//
//            });




        }else {

//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//
//                public void onClick(View view) {
//
//
//                    tableInterface.OnItemClicked(tableList.get(position).get("table_id"));
//
//
//                }
//
//            });
        }




    }


    @Override
    public int getItemCount() {

        return tableList.size();
    }

    // Your interface to send data to your fragment
    // Your interface to send data to your fragment
    public interface TableInterface{

        void OnItemClicked(int position,String tableNo,String status_service);

    }



    public class ViewHolder extends RecyclerView.ViewHolder{


         TextView table_name;
         CardView cardView;


        public ViewHolder(final View itemView, final TableInterface tableInterface) {
            super(itemView);

            table_name = itemView.findViewById(R.id.table_name);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    tableInterface.OnItemClicked(getAdapterPosition(),
                            tableList.get(getAdapterPosition()).get("table_id"),
                            tableList.get(getAdapterPosition()).get("status_service"));




                }
            });

        }



    }

}