package com.refresh.pos.ui.inventory;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.refresh.pos.R;
import com.refresh.pos.domain.inventory.Inventory;
import com.refresh.pos.domain.inventory.ProductCatalog;
import com.refresh.pos.domain.sale.Register;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.techicalservices.inventory.InventoryDaoAndroid;

import java.util.ArrayList;
import java.util.Map;

public class RecyclerViewAdapterProduct extends RecyclerView.Adapter<RecyclerViewAdapterProduct.ViewHolder> {


    private static final String TAG = "RecyclerViewAdapter";
    private Register register;
    private ProductCatalog productCatalog;
    private ArrayList<Map<String, String>> productList;
    private Context mContext;
    private InventoryFragment invenroryfragment;

    // Interface Object
    private ProductInterface productInterface;




    public RecyclerViewAdapterProduct(Context mContext, ArrayList<Map<String, String>> productList, InventoryFragment invenroryfragment,ProductInterface productInterface) {


        try {

            productCatalog = Inventory.getInstance().getProductCatalog();
            register = Register.getInstance();
        } catch (NoDaoSetException e) {
            e.printStackTrace();
        }

        this.productList = productList;
        this.mContext = mContext;
        this.invenroryfragment= invenroryfragment;
        this.productInterface = productInterface;



    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_add_product, parent, false);


        return new ViewHolder(view,productInterface);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
            holder.setIsRecyclable(false);

        int syc_status = Integer.parseInt(productList.get(position).get("syncProduct").toString());

        if (syc_status==InventoryDaoAndroid.SYNC_STATUS_OK){
            holder.imageView.setBackgroundColor(Color.GREEN);
            holder.test_name.setText(productList.get(position).get("name"));


        }else {

            holder.test_name.setText(productList.get(position).get("name"));


        }




    }



    @Override
    public int getItemCount() {

        return productList.size();
    }


    // Your interface to send data to your fragment
    public interface ProductInterface{

        void OnItemClicked(String id, int position,String name, String topping_group, String price, String gpk,String code, String cate_id, String cost , String image ,String sync);

    }


    public class ViewHolder extends RecyclerView.ViewHolder{


        private ImageView imageView;
        private TextView test_name;
        private CardView cardView;



        public ViewHolder(View itemView, final ProductInterface productInterface) {
            super(itemView);

            test_name = itemView.findViewById(R.id.test_name);
            cardView = itemView.findViewById(R.id.cardview_id);
            imageView = itemView.findViewById(R.id.book_img_id);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    productInterface.OnItemClicked(productList.get(getAdapterPosition()).get("id"),
                            getAdapterPosition(),
                            productList.get(getAdapterPosition()).get("name"),
                            productList.get(getAdapterPosition()).get("topping_group"),
                            productList.get(getAdapterPosition()).get("unitPrice"),
                            productList.get(getAdapterPosition()).get("gpk"),
                            productList.get(getAdapterPosition()).get("code"),
                            productList.get(getAdapterPosition()).get("cate_id"),
                            productList.get(getAdapterPosition()).get("cost"),
                            productList.get(getAdapterPosition()).get("image"),
                            productList.get(getAdapterPosition()).get("syncProduct")

                    );

                }
            });


        }





    }

}