package com.refresh.pos.ui.setting;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.refresh.pos.R;

import java.util.ArrayList;
import java.util.Map;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {


    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<Map<String, String>> tableList;
    private Context mContext;
    private TableSettingInterface tableSettingInterface;
    // Interface Object









    public TableAdapter(Context mContext, ArrayList<Map<String, String>> tableList, TableSettingInterface tableSettingInterface) {

        this.tableList = tableList;
        this.mContext = mContext;
        this.tableSettingInterface = tableSettingInterface;





    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_table_setting, parent, false);



        return new ViewHolder(view,tableSettingInterface);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.setIsRecyclable(false);

        String name = tableList.get(position).get("table_name");
        holder.table_name.setText(name);

//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//
//                return false;
//            }
//        });


//

//        Bundle bundle = new Bundle();
//        bundle.putString("name",current.getName());
//        inventoryFragment.setArguments(bundle);


    }


    @Override
    public int getItemCount() {

        return tableList.size();
    }

    // Your interface to send data to your fragment
    public interface TableSettingInterface{

        void OnItemClicked(int position, String id, String name, String gpk, String sync, String image, String status);

    }



    public class ViewHolder extends RecyclerView.ViewHolder{


         TextView table_name;


        public ViewHolder(View itemView, final TableSettingInterface tableSettingInterface) {
            super(itemView);

            table_name = itemView.findViewById(R.id.table_name);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    tableSettingInterface.OnItemClicked(
                            getAdapterPosition(),
                            tableList.get(getAdapterPosition()).get("table_id"),
                            tableList.get(getAdapterPosition()).get("table_name"),
                            tableList.get(getAdapterPosition()).get("gpk"),
                            tableList.get(getAdapterPosition()).get("sync"),
                            tableList.get(getAdapterPosition()).get("image"),
                            tableList.get(getAdapterPosition()).get("status")
                    );
                    return false;
                }
            });

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