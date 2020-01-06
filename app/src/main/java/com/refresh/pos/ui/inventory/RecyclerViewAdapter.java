package com.refresh.pos.ui.inventory;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.refresh.pos.R;
import com.refresh.pos.domain.DateTimeStrategy;
import com.refresh.pos.domain.inventory.Inventory;
import com.refresh.pos.domain.inventory.Product;
import com.refresh.pos.domain.inventory.ProductCatalog;
import com.refresh.pos.domain.sale.Register;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.techicalservices.inventory.InventoryDaoAndroid;
import com.refresh.pos.ui.helper.ItemTouchHelperAdapter;
import com.refresh.pos.ui.helper.ItemTouchHelperViewHolder;
import com.refresh.pos.ui.helper.OnStartDragListener;
import com.refresh.pos.ui.main.MainFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements ItemTouchHelperAdapter {


    private static final String TAG = "RecyclerViewAdapter";
    private Register register;
    private ProductCatalog productCatalog;
    private ArrayList<Map<String, String>> productList;
    private Context mContext;
    private MainFragment mainFragment;

    // Interface Object
    private FragmentCommunication adapterInterface;

    private final OnStartDragListener mDragStartListener;
    private  GestureDetector gestureDetector;
    private String tableNumber;
    private String tableStatus;
    private ProductInterface productInterface;




    public RecyclerViewAdapter(Context mContext, ArrayList<Map<String, String>> productList, MainFragment mainFragment, OnStartDragListener dragStartListener, String tableNumber, String tableStatus,ProductInterface productInterface) {


        try {
            productCatalog = Inventory.getInstance().getProductCatalog();
            register = Register.getInstance();
        } catch (NoDaoSetException e) {
            e.printStackTrace();
        }

        this.productList = productList;
        this.mContext = mContext;
        this.mainFragment= mainFragment;

        // Initialize your interface to send updates to fragment.
        this.productInterface = productInterface;
        this.tableNumber = tableNumber;
        this.tableStatus = tableStatus;

        mDragStartListener = dragStartListener;

    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_inventory, parent, false);


        return new ViewHolder(view,productInterface);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.setIsRecyclable(false);


        final int test = Integer.parseInt(productList.get(position).get("id").toString());

//        // You can pass any data here that is defined in your interface's params
//            adapterInterface.OnItemClicked(3);

        int syc_status = Integer.parseInt(productList.get(position).get("syncProduct").toString());

        if (syc_status==InventoryDaoAndroid.SYNC_STATUS_OK){

            holder.imageView.setBackgroundColor(Color.GREEN);
            holder.test_name.setText(productList.get(position).get("name"));



           holder.cardView.setOnTouchListener(new View.OnTouchListener() {
               @Override
               public boolean onTouch(View v, MotionEvent motionEvent) {
                   final int action = motionEvent.getActionMasked();

                   if (action == MotionEvent.ACTION_DOWN) {
                       mDragStartListener.onStartDrag(holder);

                   }else {


                       if (tableNumber.equals("")){


                       }else {
                           if (tableStatus.equals("busy")){

                               int number = Integer.parseInt(tableNumber);

//                               register.addItem_temp(productCatalog.getProductById(test), 1, DateTimeStrategy.getCurrentTime(),number,"busy");
//                               register.holdOrderSale(DateTimeStrategy.getCurrentTime(),number,"busy");
//                               mainFragment.editOrder();

                           }else {

                               int number = Integer.parseInt(tableNumber);

//                               register.addItem_temp(productCatalog.getProductById(test), 1, DateTimeStrategy.getCurrentTime(),number,"busy");
//                               register.holdOrderSale(DateTimeStrategy.getCurrentTime(),number,"busy");
                               mainFragment.orderNew();
                           }



                       }


                   }
                   return false;
               }


           });

        }else {

            holder.test_name.setText(productList.get(position).get("name"));

            holder.cardView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent motionEvent) {
                    final int action = motionEvent.getActionMasked();

                    if (action == MotionEvent.ACTION_DOWN) {

                        mDragStartListener.onStartDrag(holder);

                    }else {


                        if (tableNumber.equals("")){

//                            Toast.makeText(v.getContext(), "Table is empty" , Toast.LENGTH_SHORT).show();

                        }else {

                            if (tableStatus.equals("busy")){
                                int number = Integer.parseInt(tableNumber);

//                                register.addItem_temp(productCatalog.getProductById(test), 1, DateTimeStrategy.getCurrentTime(),number,"busy");
//                                mainFragment.editOrder();

                            }else {

                                int number = Integer.parseInt(tableNumber);
//                                Toast.makeText(v.getContext(), "test = " + test , Toast.LENGTH_SHORT).show();

//                                register.addItem_temp(productCatalog.getProductById(test), 1, DateTimeStrategy.getCurrentTime(),number,"busy");
                                mainFragment.orderNew();
                            }

                        }
                    }
                    return false;
                }
            });

//            holder.cardView.setOnClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    return false;
//                }
//
////                @Override
////                public void onClick(View view) {
////
////
////                        int id = Integer.parseInt(productList.get(position).get("id").toString());
////
//////                        register.addItem(productCatalog.getProductById(id), 1);
//////                    register.addItem_temp(productCatalog.getProductById(id), 1, DateTimeStrategy.getCurrentTime(),3);
////
////
////
////                }
//            });
//
//            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//                    Toast.makeText(view.getContext(), "Long click", Toast.LENGTH_SHORT).show();
//
//                    return true;// returning true instead of false, works for me
//                }
//            });
        }

    }



    @Override
    public int getItemCount() {

        return productList.size();
    }

    // Your interface to send data to your fragment
    public interface ProductInterface{

        void OnItemClicked(String id, int position,String name, String topping_group);

    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {

        Collections.swap(productList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);

//        String name = (productList.get(toPosition).get("name").toString());
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        productList.remove(position);
        notifyItemRemoved(position);

    }





    public class ViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

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
                            productList.get(getAdapterPosition()).get("topping_group")

                    );

                }
            });

        }


        @Override
        public void onItemSelected() {


            cardView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {

            cardView.setBackgroundColor(Color.WHITE);


        }
    }

}