package com.refresh.pos.ui.main;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.zxing.integration.android.IntentIntegratorSupportV4;
import com.refresh.pos.R;
import com.refresh.pos.domain.DateTimeStrategy;
import com.refresh.pos.domain.ProductCategory.CateProductCatolog;
import com.refresh.pos.domain.ProductCategory.CategoryProduct;
import com.refresh.pos.domain.ProductCategory.ProductCategory;
import com.refresh.pos.domain.inventory.Inventory;
import com.refresh.pos.domain.inventory.LineItem;
import com.refresh.pos.domain.inventory.LineItemTemp;
import com.refresh.pos.domain.inventory.Product;
import com.refresh.pos.domain.inventory.ProductCatalog;
import com.refresh.pos.domain.sale.Register;
import com.refresh.pos.domain.sale.Sale;
import com.refresh.pos.domain.sale.SaleLedger;
import com.refresh.pos.techicalservices.CategoryProduct.CateProductDaoAndroid;
import com.refresh.pos.techicalservices.Database;
import com.refresh.pos.techicalservices.DatabaseExecutor;
import com.refresh.pos.techicalservices.Demo;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.techicalservices.inventory.InventoryDaoAndroid;
import com.refresh.pos.techicalservices.sale.SaleDao;
import com.refresh.pos.ui.MainActivity;
import com.refresh.pos.ui.component.UpdatableFragment;
import com.refresh.pos.ui.helper.OnStartDragListener;
import com.refresh.pos.ui.helper.SimpleItemTouchHelperCallback;
import com.refresh.pos.ui.inventory.MySingleton;
import com.refresh.pos.ui.inventory.RecyclerViewAdapter;
import com.refresh.pos.ui.inventory.RecyclerViewAdapterCate;
import com.refresh.pos.ui.inventory.RecyclerViewAdapterTable;
import com.refresh.pos.ui.sale.EditFragmentDialog;
import com.refresh.pos.ui.sale.OpenTableFragmentDialog;
import com.refresh.pos.ui.sale.PaymentFragmentDialog;
import com.refresh.pos.ui.sale.RecyclerViewAdapterSale;
import com.refresh.pos.ui.setting.SettingActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class MainFragment extends UpdatableFragment implements OnStartDragListener {


                                        //	Product LAYOUT //


    protected static final int SEARCH_LIMIT = 0;


    private UpdatableFragment mainFragment;
    private ProductCatalog productCatalog;
    private CateProductCatolog cateProductCatolog;
    private Register register;
    private Resources res;
    private Button addProductButton;
    private EditText searchBox;
    private Button scanButton;
    private Button addCategoryBtn;
    private Button settingBtn;
    private CardView allCategory;
    private RecyclerView recyclerListView;
    private RecyclerView categoryListView;
    private ArrayList<Map<String, String>> categoryListFromSqlite;
    private ArrayList<Map<String, String>> categoryList;
    private ArrayList<Map<String, String>> productRecycleView;
    private ItemTouchHelper mItemTouchHelper;




    //	SALE LAYOUT //

    private int numberTable;
    private String tableNumber;
    private ArrayList<Map<String, String>> saleList;
    private ArrayList<Map<String, String>> saleListTemp;
    private RecyclerView saleListView;
    private Button clearButton;
    private TextView tvTable;
    public TextView textNoTable;
    public TextView textNoTable2;
    public TextView text_invoice_ta;
    private TextView tvName;
    private TextView totalPrice;
    private Button printButton;
    private Button quicklyButton;
    private Button endButton;
    private Button saveButtton;
    private UpdatableFragment reportFragment;
    private Button table_button;
    public static final int OPEN_TABLE_DIALOG = 1; // adding this line
    private Sale sale;
    private int saleId;
    private SaleLedger saleLedger;
    Database database;
    private String statusTable;






    public MainFragment() {
        super();

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            productCatalog = Inventory.getInstance().getProductCatalog();
            cateProductCatolog = ProductCategory.getInstance().getCateProductCatalog();
            register = Register.getInstance();
            saleLedger = SaleLedger.getInstance();

//

        } catch (NoDaoSetException e) {
            e.printStackTrace();
        }
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        res = getResources();
//		inventoryListView = (ListView) view.findViewById(R.id.productListView);
        categoryListView = (RecyclerView) view.findViewById(R.id.category_listview);

        addProductButton = (Button) view.findViewById(R.id.addProductButton);
        settingBtn = (Button) view.findViewById(R.id.setting_btn);
        scanButton = (Button) view.findViewById(R.id.scanButton);
        searchBox = (EditText) view.findViewById(R.id.searchBox);
        recyclerListView =(RecyclerView) view.findViewById(R.id.productRecycleview);
        allCategory = (CardView) view.findViewById(R.id.category_cardview_id);



        saleListView = (RecyclerView) view.findViewById(R.id.sale_List);
        totalPrice = (TextView) view.findViewById(R.id.totalPrice);
        clearButton = (Button) view.findViewById(R.id.clearButton);
        endButton = (Button) view.findViewById(R.id.endButton);
        saveButtton = (Button) view.findViewById(R.id.saveButton);
        printButton = (Button) view.findViewById(R.id.printButton);
        quicklyButton = (Button) view.findViewById(R.id.btn_bil_quickly);
        table_button = (Button) view.findViewById(R.id.table_button);
        tvTable = (TextView) view.findViewById(R.id.tvTable);
        tvName = (TextView) view.findViewById(R.id.tvName);
        textNoTable = (TextView) view.findViewById(R.id.textNoTable);
        textNoTable2 = (TextView) view.findViewById(R.id.textNoTable2);
        text_invoice_ta = (TextView) view.findViewById(R.id.text_invoice_ta);



        testLoop((ArrayList<CategoryProduct>) cateProductCatolog.getAllCategoryProduct());
        initSaleLayout();
        initProductLayout();
        InitProduct();
        InitCategoryProduct();








        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case OPEN_TABLE_DIALOG:

                if (resultCode == Activity.RESULT_OK) {
                    // here the part where I get my selected date from the saved variable in the intent and the displaying it.
                    Bundle bundle = data.getExtras();
                    String tableNo = bundle.getString("tableNo", "error");
                    String status_service = bundle.getString("status_service", "error");
                    textNoTable.setText(tableNo);
                    textNoTable2.setText(status_service);
                    searchBox.setText("");
                    numberTable = Integer.parseInt(tableNo);

                        if (status_service.equals("busy")){

                            orderNew();

                        }else {

                            testeiei();

                        }

//
//                    saleId = Integer.parseInt(resultData);
//
//                    sale = saleLedger.getByTableId(saleId);
//
//                    showListTemp(sale.getAllLineItem());



//



                }
                break;
        }

//        IntentResult scanningResult = IntentIntegrator.parseActivityResult(
//                requestCode, resultCode, intent);
//
//        if (scanningResult != null) {
//            String scanContent = scanningResult.getContents();
//            searchBox.setText(scanContent);
//
//        } else {

//        }
    }

    private void initSaleLayout() {


//        saleListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                showEditPopup(arg1,arg2);
//            }
//        });

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopup(v);
//                String textNo = textNoTable.getText().toString();
//
//                if(register.hasSale()){
//
//                    if (textNo.equals("")){
//
//                        Toast.makeText(getActivity().getBaseContext() , "TableNumber is empty", Toast.LENGTH_SHORT).show();
//
//                    }else {
//
//                        showPopup(v);
//
//                    }
//
//                } else {
//
//                    Toast.makeText(getActivity().getBaseContext() , res.getString(R.string.hint_empty_sale), Toast.LENGTH_SHORT).show();
//                }
            }
        });


//        saveButtton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String textNo = textNoTable.getText().toString();
//
//                if(register.hasSale()){
//
//                    if (textNo.equals("")){
//
//                        Toast.makeText(getActivity().getBaseContext() , "TableNumber is empty", Toast.LENGTH_SHORT).show();
//
//                    }else {
//
//
//                        int tableNumber = Integer.parseInt(textNo);
////                        register.endSale(DateTimeStrategy.getCurrentTime(),tableNumber);
//                        textNoTable.setText("");
//                        update();
//
//
//
//                    }
//
//                } else {
//
//                    Toast.makeText(getActivity().getBaseContext() , res.getString(R.string.hint_empty_sale), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });




//        clearButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!register.hasSale() || register.getCurrentSale().getAllLineItem().isEmpty()) {
//                    Toast.makeText(getActivity().getBaseContext() , res.getString(R.string.hint_empty_sale), Toast.LENGTH_SHORT).show();
//                } else {
//
//                    showConfirmClearDialog();
//                }
//            }
//        });

        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reLoadFragment();

                String textNo = textNoTable.getText().toString();

                int tableNumber = Integer.parseInt(textNo);

                register.holdOrderSale(DateTimeStrategy.getCurrentTime(),tableNumber,"busy");
                textNoTable.setText("");

                orderNew();

            }
        });


        quicklyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



//                reLoadFragment();


//				saveButtton.setVisibility(View.GONE);
//				endButton.setVisibility(View.VISIBLE);
                tvTable.setBackgroundColor(Color.RED);
                tvTable.setText(R.string.table_takeaway);
                tvName.setBackgroundColor(Color.RED);

                textNoTable.setText("0");
                textNoTable.setVisibility(View.GONE);
                text_invoice_ta.setText("Ta8888");
                text_invoice_ta.setVisibility(View.VISIBLE);
                textNoTable2.setText("takeAway");

                orderNew();


            }
        });

        table_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textNoTable.setText("");
                textNoTable.setVisibility(View.VISIBLE);
                text_invoice_ta.setText("Ta8888");
                text_invoice_ta.setVisibility(View.GONE);

                tvTable.setBackgroundColor(Color.parseColor("#33B5E5"));
                tvTable.setText(R.string.table_number);
                tvName.setBackgroundColor(Color.parseColor("#33B5E5"));




                String textNo = textNoTable.getText().toString();

                if (textNo.equals("")){

                    openTablePopup(v);


                }else {

                    int tableNumber = Integer.parseInt(textNo);


                    openTablePopup(v);

                }





            }
        });
    }

    private void showList(List<LineItem> list) {

        saleList = new ArrayList<Map<String, String>>();
        for(LineItem line : list) {
            saleList.add(line.toMap());
        }
//        SimpleAdapter sAdap;
//        sAdap = new SimpleAdapter(getActivity().getBaseContext(), saleList,
//                R.layout.listview_lineitem, new String[]{"name","quantity","price"}, new int[] {R.id.name,R.id.quantity,R.id.price});
//        saleListView.setAdapter(sAdap);
//        sAdap.notifyDataSetChanged();

        RecyclerViewAdapterSale saleAdapter = new RecyclerViewAdapterSale(getContext(), saleList,this,saleInterface);
        saleListView.setHasFixedSize(true);
        saleListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        saleListView.setAdapter(saleAdapter);

//        Toast.makeText(getActivity().getBaseContext() , saleList.toString(), Toast.LENGTH_SHORT).show();

    }

    private void showListTemp (List<LineItem> list){
        saleListTemp = new ArrayList<Map<String, String>>();
        for(LineItem line : list) {
            saleListTemp.add(line.toMap());
        }


        RecyclerViewAdapterSale saleAdapter = new RecyclerViewAdapterSale(getContext(), saleListTemp,this,saleInterface);
        saleListView.setHasFixedSize(true);
        saleListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        saleListView.setAdapter(saleAdapter);


//        Toast.makeText(getActivity().getBaseContext() , saleListTemp.toString(), Toast.LENGTH_SHORT).show();





    }


    public boolean tryParseDouble(String value)
    {
        try  {
            Double.parseDouble(value);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }



    public void showPopup(View anchorView) {



        Bundle bundle = new Bundle();
        bundle.putString("edttext", totalPrice.getText().toString());
        bundle.putString("table_id",textNoTable.getText().toString());
        bundle.putString("sale_id",register.getCurrentSale().getId()+"");
        PaymentFragmentDialog newFragment = new PaymentFragmentDialog(MainFragment.this, this);
        newFragment.setArguments(bundle);
        newFragment.show(getFragmentManager(), "");

    }


    public void openTablePopup(View anchorView) {


        OpenTableFragmentDialog newFragment = new OpenTableFragmentDialog(MainFragment.this, mainFragment);
        newFragment.setTargetFragment(this, OPEN_TABLE_DIALOG);
        newFragment.show(getFragmentManager(), "OpenTable");


    }








    private void initProductLayout() {

        searchBox.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                if (s.length() >= SEARCH_LIMIT) {
                    search();
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });


//        settingBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                Intent intent = new Intent(getActivity(), SettingActivity.class);
//                startActivity(intent);
//
//            }
//        });


        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegratorSupportV4 scanIntegrator = new IntentIntegratorSupportV4(MainFragment.this);
                scanIntegrator.initiateScan();
            }
        });

        allCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBox.setText("");
            }
        });

    }


    private void testLoop(ArrayList<CategoryProduct> list) {

        categoryListFromSqlite = new ArrayList<Map<String, String>>();
        for (CategoryProduct categoryProduct : list) {
            categoryListFromSqlite.add(categoryProduct.toMap());

        }

    }

    private void showCategoryList(ArrayList<CategoryProduct> list) {

        categoryList = new ArrayList<Map<String, String>>();
        for(CategoryProduct categoryProduct : list) {
            categoryList.add(categoryProduct.toMap());
        }


        RecyclerViewAdapterCate testAdapter = new RecyclerViewAdapterCate(getContext(), categoryList,MainFragment.this,adapterInterface);
        categoryListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        categoryListView.setAdapter(testAdapter);
//			testAdapter.notifyDataSetChanged();

    }


    private void showRecycleList(ArrayList<Product> list) {

        productRecycleView = new ArrayList<Map<String, String>>();
        for(Product product : list) {
            productRecycleView.add(product.toMap());


        }





        String numberTable = textNoTable.getText().toString();
        String tableStatus = textNoTable2.getText().toString();
//        String numberTable = "10";


        RecyclerViewAdapter testAdapter = new RecyclerViewAdapter(getContext(), productRecycleView,MainFragment.this,this,numberTable,tableStatus,productInterface);
        recyclerListView.setHasFixedSize(true);
        recyclerListView.setLayoutManager(new GridLayoutManager(getActivity(),6));
        recyclerListView.setAdapter(testAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(testAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerListView);



    }


    RecyclerViewAdapter.ProductInterface productInterface = new RecyclerViewAdapter.ProductInterface() {
        @Override
        public void OnItemClicked(String idProduct, int position, String name, String topping_group) {

            String test = productRecycleView.get(position).get("id");
            String numberTable = textNoTable.getText().toString();
            String tableStatus = textNoTable2.getText().toString();
            int productID = Integer.parseInt(idProduct);



            if (numberTable.equals("")){


                Toast.makeText(getActivity().getBaseContext(), "table is empty ",
                        Toast.LENGTH_SHORT).show();

            }else {


                if (tableStatus.equals("busy")){

                    Toast.makeText(getActivity().getBaseContext(), "topping_group =  " +topping_group ,
                            Toast.LENGTH_SHORT).show();
                            Bundle bundle = new Bundle();
                            bundle.putString("idProduct",idProduct);
                            bundle.putString("name",name);
                            bundle.putString("position",position+"");
                            bundle.putString("topping_group",topping_group);
                            bundle.putString("tableNo",numberTable +"");



                            AddToppingProduct newFragment = new AddToppingProduct(MainFragment.this);
                            newFragment.setArguments(bundle);
                            newFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {

                                    orderNew();

                                }
                            });

                            newFragment.show(getFragmentManager(), "AddToppingProduct");


                }else{

                    Toast.makeText(getActivity().getBaseContext(), "topping_group =  " +topping_group ,
                            Toast.LENGTH_SHORT).show();

                            Bundle bundle = new Bundle();
                            bundle.putString("idProduct",idProduct);
                            bundle.putString("name",name);
                            bundle.putString("position",position+"");
                            bundle.putString("topping_group",topping_group);
                            bundle.putString("tableNo",numberTable +"");



                            AddToppingProduct newFragment = new AddToppingProduct(MainFragment.this);
                            newFragment.setArguments(bundle);
                            newFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {

                                    orderNew();

                                }
                            });

                            newFragment.show(getFragmentManager(), "AddToppingProduct");



                }

            }

        }
    };

    RecyclerViewAdapterCate.FragmentCommunication adapterInterface = new RecyclerViewAdapterCate.FragmentCommunication() {
        @Override
        public void OnItemClicked(String item_id) {

            searchBox.setText(item_id);
        }
    };


    RecyclerViewAdapterSale.SaleInterface saleInterface = new RecyclerViewAdapterSale.SaleInterface() {
        @Override
        public void OnItemClicked(String id,String name, String qty, String price, int position, String topping_name,String topping_group) {

            String tableStatus = textNoTable2.getText().toString();


            if (tableStatus.equals("busy")){
                Bundle bundle = new Bundle();
                bundle.putString("id",id);
                bundle.putString("position",position +"");
                bundle.putString("status",tableStatus);
                bundle.putString("group_topping_id",topping_group);
                bundle.putString("name",name);
                bundle.putString("qty",qty +"");
                bundle.putString("price",price +"");
//            bundle.putString("sale_id",register.getCurrentSale().getId()+"");

                bundle.putString("product_id",sale.getLineItemAt(position).getProduct().getId()+"");
                EditFragmentDialog newFragment = new EditFragmentDialog(MainFragment.this, mainFragment);
                newFragment.setArguments(bundle);
                newFragment.show(getFragmentManager(), "");

                String product_id = sale.getLineItemAt(position).getProduct().getId()+"";

                Toast.makeText(getActivity().getBaseContext(), "  group_topping_id = " + topping_group,
                        Toast.LENGTH_SHORT).show();



            }else{


                Bundle bundle = new Bundle();
                bundle.putString("id",id);
                bundle.putString("position",position +"");
                bundle.putString("status",tableStatus);
                bundle.putString("group_topping_id",topping_group);
                bundle.putString("name",name);
                bundle.putString("qty",qty +"");
                bundle.putString("price",price +"");
//            bundle.putString("sale_id",register.getCurrentSale().getId()+"");
                bundle.putString("product_id",register.getCurrentSale().getLineItemAt(position).getProduct().getId()+"");
                EditFragmentDialog newFragment = new EditFragmentDialog(MainFragment.this, mainFragment);
                newFragment.setArguments(bundle);
                newFragment.show(getFragmentManager(), "");


                Toast.makeText(getActivity().getBaseContext(), "  group_topping_id = " + topping_group,
                        Toast.LENGTH_SHORT).show();
            }

        }

    };





    private void InitProduct(){

        String token = "9bdtrkt9w7apuatehl6o";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, InventoryDaoAndroid.SERVER_URL+token, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String Response = response.getString("message");


                            if (Response.equals("success")){

                                ArrayList DataFromSqlite = new ArrayList();
                                for (int ii = 0; ii < productRecycleView.size(); ii++) {

                                    DataFromSqlite.add(productRecycleView.get(ii).get("name"));

                                }

//								Log.d("categoryListFromSqlite", "categoryListFromSqlite " + myArrlis + " Successfully_InventoryFragment.");

                                JSONArray jsonArray = response.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject product = jsonArray.getJSONObject(i);

                                    if (DataFromSqlite.contains(product.getString("name"))){

                                        Log.d("Result", "ข้อมูลซ้ำใน Sqlite" );


                                    }else {

                                        String code = product.getString("code");
                                        String name = product.getString("name");
                                        int	category = product.getInt("cat_id");
                                        String	topping_group = product.getString("topping");
                                        double price = product.getDouble("price");;
                                        double cost = product.getDouble("cost");
                                        String image = product.getString("image");
                                        String gpk  = product.getString("gpk");
                                        String status = "use";
//										String cat_name = product.getString("cat_name");
                                        int	sync = 1;

                                        productCatalog.addProduct(category,topping_group,name,image,cost,price,status,code,gpk,sync);

                                        update();

                                    }


//									fragment.update();

//										Toast.makeText(getActivity().getBaseContext(), name,
//												Toast.LENGTH_SHORT).show();




                                }




                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        MySingleton.getInstance(getContext()).addtoRequestQue(request);


    }


    private void InitCategoryProduct() {

        String token = "9bdtrkt9w7apuatehl6o";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, CateProductDaoAndroid.SERVER_URL+token, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String Response = response.getString("message");

                            if (Response.equals("success")) {

                                ArrayList myArrlis = new ArrayList();
                                for (int ii = 0; ii < categoryListFromSqlite.size(); ii++) {

                                    myArrlis.add(categoryListFromSqlite.get(ii).get("gpk"));

                                }

                                Log.d("categoryListFromSqlite", "categoryListFromSqlite " + myArrlis + " SuccessfullyC.");


                                JSONArray jsonArray = response.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject product = jsonArray.getJSONObject(i);

                                    if (myArrlis.contains(product.getString("gpk"))) {

                                        Log.d("Result", "Duplicate data " + myArrlis );

                                    }else {

                                        String gpk = product.getString("gpk");
                                        int parent_id = product.getInt("parent_id");
                                        int sequence = product.getInt("sequence");
                                        String name = product.getString("name");
                                        int sync = product.getInt("sync");
                                        String image = product.getString("image");
                                        String type = product.getString("type");
                                        String status = product.getString("status");


                                        cateProductCatolog.addCateProduct(gpk,sequence,parent_id,name,sync,image,status,type);

                                        Log.d("Result", "Saving "+ name + " .......");


                                    }

//                                    showCategoryList((ArrayList<CategoryProduct>) cateProductCatolog.getAllCategoryProduct());


                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        MySingleton.getInstance(getContext()).addtoRequestQue(request);


    }


    private void search() {

        String search = searchBox.getText().toString();
//		String testsearch = searchBox.getText().toString();

        if (search.equals("/demo")) {
            testAddProduct();
            searchBox.setText("");
        } else if (search.equals("/clear")) {
            DatabaseExecutor.getInstance().dropAllData();
            searchBox.setText("");
        }
        else if (search.equals("")) {
            showRecycleList((ArrayList<Product>) productCatalog.getAllProduct());
            showCategoryList((ArrayList<CategoryProduct>) cateProductCatolog.getAllCategoryProduct());
        } else {
            List<Product> result = productCatalog.searchProduct(search);
//			List<CategoryProduct> result2 = cateProductCatolog.searchCateProduct(search);
            showRecycleList((ArrayList<Product>) result);
            if (result.isEmpty()) {

            }
        }
    }








    protected void testAddProduct() {
        Demo.testProduct(getActivity());
        Toast.makeText(getActivity().getBaseContext(), res.getString(R.string.success),
                Toast.LENGTH_SHORT).show();
    }


    public  void Endprocess(){

        textNoTable.setText("");
        textNoTable2.setText("empty");
        showList(new ArrayList<LineItem>());
        totalPrice.setText("0.00");

    }


//    public void editOrder() {
//        statusTable = "busy";
//        String textNo = textNoTable.getText().toString();
////        String tableStatus = statusTable;
////
//        saleId = Integer.parseInt(textNo);
//        sale = saleLedger.getByTableId(saleId);
//        showListTemp(sale.getAllLineItem());
//        totalPrice.setText(sale.getTotal() + "");
//
//
//    }

    public void testeiei(){

        if(register.hasSale()){

            sale = saleLedger.getByTableId(numberTable);
            showListTemp(sale.getAllLineItem());
            totalPrice.setText(sale.getTotal() + "");

        }
        else{
            showList(new ArrayList<LineItem>());
            totalPrice.setText("0.00");
        }
    }

    public void orderNew() {

        String tableStatus = textNoTable2.getText().toString();

            if (tableStatus.equals("empty")){

                showList(new ArrayList<LineItem>());
                totalPrice.setText("0.00");


            }else if (tableStatus.equals("busy")){

//                String textNo = textNoTable.getText().toString();
////                saleId = Integer.parseInt(textNo);
                sale = saleLedger.getByTableId(numberTable);
                showListTemp(sale.getAllLineItem());
                totalPrice.setText(sale.getTotal() + "");

            }else{

                if(register.hasSale()){

                    showList(register.getCurrentSale().getAllLineItem());
                    totalPrice.setText(register.getTotal() + "");
                }
                else{
                    showList(new ArrayList<LineItem>());
                    totalPrice.setText("0.00");
                }
            }



    }

    public void test(){

       textNoTable2.setText("busy");

    }

    public void  refresh(){

        orderNew();

    }


//    private void showConfirmClearDialog() {
//
//        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
//        dialog.setTitle(res.getString(R.string.dialog_clear_sale));
//        dialog.setPositiveButton(res.getString(R.string.no), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//
//        dialog.setNegativeButton(res.getString(R.string.yes), new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                String textNo = textNoTable.getText().toString();
//                int tableNumber = Integer.parseInt(textNo);
//
//
//                register.cancleSale();
//                textNoTable.setText("");
//
//                update();
//            }
//        });
//
//        dialog.show();
//    }



    @Override
    public void update() {

        orderNew();
        search();






    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }



    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    private void reLoadFragment(){

        ((MainActivity)getActivity()).refresh();

    }


}
