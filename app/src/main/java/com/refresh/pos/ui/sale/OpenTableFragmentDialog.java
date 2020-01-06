package com.refresh.pos.ui.sale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.refresh.pos.R;
import com.refresh.pos.domain.ProductCategory.CateProductCatolog;
import com.refresh.pos.domain.ProductCategory.CategoryProduct;
import com.refresh.pos.domain.ProductCategory.ProductCategory;

import com.refresh.pos.domain.Table.TableCatolog;
import com.refresh.pos.domain.Table.TableLedger;
import com.refresh.pos.domain.Table.Table_Detail;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.ui.MainActivity;
import com.refresh.pos.ui.component.UpdatableFragment;
import com.refresh.pos.ui.inventory.AddTableDialogFragment;

import com.refresh.pos.ui.inventory.RecyclerViewAdapterTable;

import java.util.ArrayList;
import java.util.Map;

/**
 * A dialog shows the total change and confirmation for Sale.
 * @author Refresh Team
 *
 */
@SuppressLint("ValidFragment")
public class OpenTableFragmentDialog extends DialogFragment  {


	private RecyclerView openTableRecycleview;
	private ArrayList<Map<String, String>> numberTable;
	private CateProductCatolog cateProductCatolog;
	private TableCatolog tableCatolog;
	private Button addTable;
    private UpdatableFragment fragment;




	/**
	 * End this UI.
	 * @param saleFragment
	 * @param reportFragment
	 */
	public OpenTableFragmentDialog(UpdatableFragment saleFragment, UpdatableFragment reportFragment) {
		super();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


		
		View v = inflater.inflate(R.layout.dialog_open_table, container,false);

		openTableRecycleview = (RecyclerView) v.findViewById(R.id.table_listview);
        addTable = (Button) v.findViewById(R.id.addTableButton);

		try {

			tableCatolog = TableLedger.getInstance().getTableCatalog();

		} catch (NoDaoSetException e) {
			e.printStackTrace();
		}


        initUI();

		return v;
	}



	private void initUI() {

		showTableList((ArrayList<Table_Detail>) tableCatolog.getAllTable());

//
//        addTable.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addTablePopup(view);
//            }
//        });


	}

//
//	private void showList(List<LineItem> list) {
//		lineitemList = new ArrayList<Map<String, String>>();
//		for(LineItem line : list) {
//			lineitemList.add(line.toMap());
//		}
////
////		SimpleAdapter sAdap = new SimpleAdapter(SaleDetailActivity.this, lineitemList,
////				R.layout.listview_lineitem, new String[]{"name","quantity","price"}, new int[] {R.id.name,R.id.quantity,R.id.price});
////		lineitemListView.setAdapter(sAdap);
//	}

	/**
	 * End
	 */
	// This is the interface declared inside your adapter.
	RecyclerViewAdapterTable.TableInterface tableInterface = new RecyclerViewAdapterTable.TableInterface() {
		@Override
		public void OnItemClicked(int position, String tableNo, String status_service) {

//			Intent i = new Intent().putExtra("data",status_service);
			Intent i = new Intent(getActivity(), MainActivity.class);
			i.putExtra("tableNo", tableNo);
			i.putExtra("status_service", status_service);
			getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
			OpenTableFragmentDialog.this.dismiss();
//
		}

	};


	private void showTableList(ArrayList<Table_Detail> list) {

		numberTable = new ArrayList<Map<String, String>>();
		for(Table_Detail table_detail : list) {
			numberTable.add(table_detail.toMap());
		}

		RecyclerViewAdapterTable testAdapter = new RecyclerViewAdapterTable(getContext(), numberTable,tableInterface);
		openTableRecycleview.setLayoutManager(new GridLayoutManager(getActivity(),5));
		openTableRecycleview.setAdapter(testAdapter);
//			testAdapter.notifyDataSetChanged();

	}






//    public void addTablePopup(View anchorView) {
//
//
//        AddTableDialogFragment newFragment = new AddTableDialogFragment(OpenTableFragmentDialog.this);
//        newFragment.show(getFragmentManager(), "");
//
//    }


}
