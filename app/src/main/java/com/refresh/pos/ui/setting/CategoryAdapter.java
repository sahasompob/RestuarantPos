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
import com.refresh.pos.domain.ProductCategory.CategoryProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {


    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<Map<String, String>> categoryList;
    private Context mContext;
    // Interface Object
    private CategorySettingInterface categorySettingInterface;








    public CategoryAdapter(Context mContext, ArrayList<Map<String, String>> categoryList,CategorySettingInterface categorySettingInterface) {

        this.categoryList = categoryList;
        this.mContext = mContext;
        this.categorySettingInterface = categorySettingInterface;



    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_category_product_setting, parent, false);



        return new ViewHolder(view,categorySettingInterface);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.setIsRecyclable(false);

        String name = categoryList.get(position).get("name");
        holder.category_name.setText(name);




    }


    @Override
    public int getItemCount() {

        return categoryList.size();
    }

    // Your interface to send data to your fragment
    public interface CategorySettingInterface{

        void OnItemClicked(int position, String id, String name,String image,String gpk,String sequence,String type,String parent_id);

    }


    public class ViewHolder extends RecyclerView.ViewHolder{


         TextView category_name;


        public ViewHolder(View itemView, final CategorySettingInterface categorySettingInterface) {
            super(itemView);

            category_name = itemView.findViewById(R.id.category_name);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    categorySettingInterface.OnItemClicked(
                            getAdapterPosition(),
                            categoryList.get(getAdapterPosition()).get("cate_product_id"),
                            categoryList.get(getAdapterPosition()).get("name"),
                            categoryList.get(getAdapterPosition()).get("image"),
                            categoryList.get(getAdapterPosition()).get("gpk"),
                            categoryList.get(getAdapterPosition()).get("sequence"),
                            categoryList.get(getAdapterPosition()).get("type"),
                            categoryList.get(getAdapterPosition()).get("paren_id")
                    );
                    return false;
                }
            });


        }



    }

}