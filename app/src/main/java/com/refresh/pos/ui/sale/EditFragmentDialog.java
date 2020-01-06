package com.refresh.pos.ui.sale;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.refresh.pos.R;
import com.refresh.pos.domain.inventory.Inventory;
import com.refresh.pos.domain.inventory.LineItem;
import com.refresh.pos.domain.inventory.ProductCatalog;
import com.refresh.pos.domain.inventory.ToppingProduct;
import com.refresh.pos.domain.sale.Register;
import com.refresh.pos.domain.sale.Sale;
import com.refresh.pos.domain.sale.SaleLedger;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.ui.component.UpdatableFragment;
import com.refresh.pos.ui.main.MainFragment;
import com.refresh.pos.ui.main.ProductToppingAdapter;
import com.refresh.pos.ui.main.SaleInterface;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A dialog for edit a LineItem of sale,
 * overriding price or set the quantity.
 * @author Refresh Team
 *
 */
@SuppressLint("ValidFragment")
public class EditFragmentDialog extends DialogFragment {
	private Register register;
	private  MainFragment mainFragment;
	private  UpdatableFragment reportFragment;
	private EditText quantityBox;
	private EditText priceBox;
	private Button comfirmButton;
	private String id;
	private String position;
	private String group_topping_id;
	private String name;
	private String qty;
	private String price;
	private String status;
	private LineItem lineItem;
	private Button removeButton;
	private TextView nameProduct;
	private Sale sale;
	private SaleLedger saleLedger;
    private List<ToppingProduct> mModelList;
    private RecyclerView toppingRecycleview;
    private ProductCatalog productCatalog;




    private SaleInterface saleInterface;



	/**
	 * Construct a new  EditFragmentDialog.
//	 * @param saleFragment
	 * @param reportFragment
	 */
	public EditFragmentDialog(MainFragment mainFragment, UpdatableFragment reportFragment) {
		super();
		this.mainFragment = mainFragment;
		this.reportFragment = reportFragment;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dialog_saleedit, container, false);
		try {
			register = Register.getInstance();
			saleLedger = SaleLedger.getInstance();
            productCatalog = Inventory.getInstance().getProductCatalog();

        } catch (NoDaoSetException e) {
			e.printStackTrace();
		}
		
		quantityBox = (EditText) v.findViewById(R.id.quantityBox);
		priceBox = (EditText) v.findViewById(R.id.priceBox);
		comfirmButton = (Button) v.findViewById(R.id.confirmButton);
		removeButton = (Button) v.findViewById(R.id.removeButton);
		nameProduct = (TextView) v.findViewById(R.id.name_product);
        toppingRecycleview = (RecyclerView) v.findViewById(R.id.topping_list);




//		saleId = getArguments().getString("sale_id");
		id = getArguments().getString("id");
		position = getArguments().getString("position");
		group_topping_id = getArguments().getString("group_topping_id");
		name = getArguments().getString("name");
		qty = getArguments().getString("qty");
		price = getArguments().getString("price");
		status = getArguments().getString("status");

		String checkStatus = status;
		String aa = qty;
		String bb = price;

		if (checkStatus.equals("busy")){


			int idGroupTopping= Integer.parseInt(group_topping_id);
			String testid ="2,3";
//            showToppingList(productCatalog.getAllTopping());
			showToppingList(productCatalog.getToppingByGroupId(testid));
//			lineItem = register.getCurrentSale().getLineItemAt(Integer.parseInt(position));
//			lineItem = sale.getLineItemAt(Integer.parseInt(position));
//
//			int a = Integer.parseInt(aa);
//			double b = Double.parseDouble(bb);
////
//

			nameProduct.setText(name);
			quantityBox.setText(qty);
			priceBox.setText(price);
//			quantityBox.setText(lineItem.getQuantity()+"");
//			priceBox.setText(lineItem.getProduct().getUnitPrice()+"");


			Toast.makeText(getActivity().getBaseContext(), id + "  ID  in else statement",
					Toast.LENGTH_SHORT).show();

			removeButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {

//					Log.d("remove", "id=" + lineItem.getId());
					register.removeItem(Integer.parseInt(id));

					end();
				}
			});

			comfirmButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View view) {

					String topping = "";
					String topping_name = "";
					String topping_name2 = "";
					String strSeparator = ",";
					int totalToppingPrice = 0;


					for (ToppingProduct model : mModelList) {
						if (model.isSelected()) {
							topping += model.getId()+":"+ model.getName()+":"+model.getPrice()+ strSeparator;
							topping_name += model.getName()+ strSeparator;
							totalToppingPrice+= model.getPrice();

							topping_name2 = topping_name.substring(0,topping_name.length()-1);


						}
					}

					register.updateItemTemp(
							Integer.parseInt(id),
							Integer.parseInt(quantityBox.getText().toString()),
							Double.parseDouble(priceBox.getText().toString()),
							topping_name2,
							topping,
							totalToppingPrice
					);

					Toast.makeText(getActivity().getBaseContext(), topping + " /" + topping_name2 + " /" + totalToppingPrice+"",
							Toast.LENGTH_SHORT).show();
				end();
				}

			});



		}else {

			showToppingList(productCatalog.getAllTopping());
//			lineItem = register.getCurrentSale().getLineItemAt(Integer.parseInt(position));
//			lineItem = sale.getLineItemAt(Integer.parseInt(position));
//
//			int a = Integer.parseInt(aa);
//			double b = Double.parseDouble(bb);
////
//

			nameProduct.setText(name);
			quantityBox.setText(qty);
			priceBox.setText(price);
//			quantityBox.setText(lineItem.getQuantity()+"");
//			priceBox.setText(lineItem.getProduct().getUnitPrice()+"");


			Toast.makeText(getActivity().getBaseContext(), id + "  ID  in else statement",
					Toast.LENGTH_SHORT).show();

			removeButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {

//					Log.d("remove", "id=" + lineItem.getId());
					register.removeItem(Integer.parseInt(id));

					end();
				}
			});

			comfirmButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View view) {

					String topping = "";
					String topping_name = "";
					String topping_name2 = "";
					String strSeparator = ",";
					int totalToppingPrice = 0;


					for (ToppingProduct model : mModelList) {

						if (model.isSelected()) {
							topping += model.getId()+":"+ model.getName()+":"+model.getPrice()+ strSeparator;
							topping_name += model.getName()+ strSeparator;
							totalToppingPrice+= model.getPrice();

							topping_name2 = topping_name.substring(0,topping_name.length()-1);


						}
					}

					register.updateItemTemp(
							Integer.parseInt(id),
							Integer.parseInt(quantityBox.getText().toString()),
							Double.parseDouble(priceBox.getText().toString()),
							topping_name2,
							topping,
							totalToppingPrice
					);

					Toast.makeText(getActivity().getBaseContext(), topping + " /" + topping_name2 + " /" + totalToppingPrice+"",
							Toast.LENGTH_SHORT).show();
					end();
				}

			});


//			lineItem = register.getCurrentSale().getLineItemAt(Integer.parseInt(position));
////		lineItem = sale.getLineItemAt(Integer.parseInt(position));
//			nameProduct.setText(lineItem.getProduct().getName()+"");
//			quantityBox.setText(lineItem.getQuantity()+"");
//			priceBox.setText(lineItem.getProduct().getUnitPrice()+"");
//
//
//			Toast.makeText(getActivity().getBaseContext(), status+ "  in else statement",
//					Toast.LENGTH_SHORT).show();
//
//			removeButton.setOnClickListener(new OnClickListener(){
//				@Override
//				public void onClick(View arg0) {
//
////					Log.d("remove", "id=" + lineItem.getId());
////					register.removeItem(lineItem);
////
////					end();
//				}
//			});
//
//			comfirmButton.setOnClickListener(new OnClickListener(){
//				@Override
//				public void onClick(View view) {
//
//
////				register.updateItemTemp(
////						Integer.parseInt(id),
////						Integer.parseInt(quantityBox.getText().toString()),
////						Double.parseDouble(priceBox.getText().toString()),
////						""
////				);
//
////				end();
//				}
//
//			});

		}



		return v;
	}

    private void showToppingList(List<ToppingProduct> list) {

        mModelList = new ArrayList<>();


        for(ToppingProduct toppingProduct : list) {

            mModelList.add(new ToppingProduct(toppingProduct.getId(),toppingProduct.getGpk(),
                    toppingProduct.getGroupId(),toppingProduct.getName(),
                    toppingProduct.getPrice(),toppingProduct.getImage(),toppingProduct.getStatus()));

        }

        ProductToppingAdapter testAdapter = new ProductToppingAdapter(getContext(), mModelList);
        toppingRecycleview.setAdapter(testAdapter);
        toppingRecycleview.setLayoutManager(new GridLayoutManager(getActivity(),2));

    }
	/**
	 * End.
	 */
	private void end(){

		mainFragment.orderNew();
//		reportFragment.update();
		this.dismiss();
	}

	
}
