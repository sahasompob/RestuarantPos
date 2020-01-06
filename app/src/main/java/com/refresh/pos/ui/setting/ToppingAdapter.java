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

public class ToppingAdapter extends RecyclerView.Adapter<ToppingAdapter.ViewHolder> {


    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<Map<String, String>> toppingList;
    private Context mContext;
    private CategoryFragment fragment;
    private FragmentManager fragmentManager;
    private ToppingSettingInterface toppingSettingInterface;

    // Interface Object









    public ToppingAdapter(Context mContext, ArrayList<Map<String, String>> toppingList,ToppingSettingInterface toppingSettingInterface) {

        this.toppingList = toppingList;
        this.mContext = mContext;
        this.toppingSettingInterface = toppingSettingInterface;




    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_topping_setting, parent, false);



        return new ViewHolder(view,toppingSettingInterface);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.setIsRecyclable(false);

        String name = toppingList.get(position).get("name");
        holder.topping_name.setText(name);


//

//        Bundle bundle = new Bundle();
//        bundle.putString("name",current.getName());
//        inventoryFragment.setArguments(bundle);


    }


    @Override
    public int getItemCount() {

        return toppingList.size();
    }


    // Your interface to send data to your fragment
    public interface ToppingSettingInterface{

        void OnItemClicked(int position, String id, String groupId, String name , String price, String gpk);

    }


    public class ViewHolder extends RecyclerView.ViewHolder{


         TextView topping_name;


        public ViewHolder(View itemView, final ToppingSettingInterface toppingSettingInterface) {
            super(itemView);

            topping_name = itemView.findViewById(R.id.topping_name);


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    toppingSettingInterface.OnItemClicked(
                            getAdapterPosition(),
                            toppingList.get(getAdapterPosition()).get("id"),
                            toppingList.get(getAdapterPosition()).get("groupId"),
                            toppingList.get(getAdapterPosition()).get("name"),
                            toppingList.get(getAdapterPosition()).get("price"),
                            toppingList.get(getAdapterPosition()).get("gpk")

                    );
                    return false;
                }
            });

        }



    }

}