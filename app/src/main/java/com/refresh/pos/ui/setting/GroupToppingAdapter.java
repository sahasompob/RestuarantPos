package com.refresh.pos.ui.setting;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.refresh.pos.R;

import java.util.ArrayList;
import java.util.Map;

public class GroupToppingAdapter extends RecyclerView.Adapter<GroupToppingAdapter.ViewHolder> {


    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<Map<String, String>> groupToppingList;
    private Context mContext;
    private CategoryFragment fragment;
    private FragmentManager fragmentManager;
    // Interface Object

    private GroupToppingSettingInterface groupToppingSettingInterface;









    public GroupToppingAdapter(Context mContext, ArrayList<Map<String, String>> groupToppingList,GroupToppingSettingInterface groupToppingSettingInterface) {

        this.groupToppingList = groupToppingList;
        this.mContext = mContext;
        this.groupToppingSettingInterface = groupToppingSettingInterface;




    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_group_topping_setting, parent, false);



        return new ViewHolder(view,groupToppingSettingInterface);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.setIsRecyclable(false);

        String name = groupToppingList.get(position).get("name");
        holder.group_topping_name.setText(name);


//

//        Bundle bundle = new Bundle();
//        bundle.putString("name",current.getName());
//        inventoryFragment.setArguments(bundle);


    }


    @Override
    public int getItemCount() {

        return groupToppingList.size();
    }


    // Your interface to send data to your fragment
    public interface GroupToppingSettingInterface{

        void OnItemClicked(int position, String id, String name,String gpk,String image,String status);

    }


    public class ViewHolder extends RecyclerView.ViewHolder{


         TextView group_topping_name;


        public ViewHolder(View itemView, final GroupToppingSettingInterface tableSettingInterface) {
            super(itemView);

            group_topping_name = itemView.findViewById(R.id.group_topping_name);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    tableSettingInterface.OnItemClicked(
                            getAdapterPosition(),
                            groupToppingList.get(getAdapterPosition()).get("id"),
                            groupToppingList.get(getAdapterPosition()).get("name"),
                            groupToppingList.get(getAdapterPosition()).get("gpk"),
                            groupToppingList.get(getAdapterPosition()).get("image"),
                            groupToppingList.get(getAdapterPosition()).get("status")
                    );
                    return false;
                }
            });

        }



    }

}