package com.refresh.pos.ui.setting;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.refresh.pos.R;
import com.refresh.pos.ui.MainActivity;
import com.refresh.pos.ui.component.UpdatableFragment;
import com.refresh.pos.ui.sale.ReportFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    private CardView productCard;
    private CardView storeCard;
    private CardView userCard;
    private CardView settingCard;
    private CardView reportCard;
    private UpdatableFragment fragment;
    private NavigationView mNavigationView;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_report_fragment, container, false);



        mNavigationView=(NavigationView) view.findViewById(R.id.nav_view);

        initUI();
        return view;
    }


    private void initUI() {

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.nav_category:

                        UpdatableFragment fragment = new CategoryFragment();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.main_content_area,fragment,"category")
                                .commit();

                        return true;

                    case R.id.nav_food:
                        UpdatableFragment foodFragment = new ProductFragment();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.main_content_area,foodFragment,"product")
                                .commit();
                        return true;

                    case R.id.nav_group_topping:
                        UpdatableFragment groupToppingFragment = new GroupToppingFragment();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.main_content_area,groupToppingFragment,"group_topping")
                                .commit();
                        return true;

                    case R.id.nav_topping:
                        UpdatableFragment toppingFragment = new ToppingFragment();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.main_content_area,toppingFragment,"topping")
                                .commit();
                        return true;

                    case R.id.nav_table:
                        UpdatableFragment tableFragment = new TableFragment();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.main_content_area,tableFragment,"table")
                                .commit();
                        return true;
                    case R.id.nav_stock:
                        UpdatableFragment stockFragment = new StockFragment();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.main_content_area,stockFragment,"stock")
                                .commit();
                        return true;
                    case R.id.nav_report:
                        UpdatableFragment reportFragment = new ReportFragment();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.main_content_area,reportFragment,"report")
                                .commit();
                        return true;


                    case R.id.nav_user_manage:
                        UpdatableFragment userManageFragment = new UserManageFragment();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.main_content_area,userManageFragment,"userManagement")
                                .commit();
                        return true;

                    case R.id.nav_language:
                        UpdatableFragment languageFragment = new LanguageFragment();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.main_content_area,languageFragment,"language")
                                .commit();
                        return true;


                    case R.id.go_back_sale:

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        return true;

                    default:
                        return true;
                }
            }




        });

//        productCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                UpdatableFragment fragment = new ProductFragment();
//                getFragmentManager().beginTransaction()
//                        .replace(R.id.area_main,fragment,"product")
//                        .addToBackStack("product").commit();
//
//            }
//        });
//

    }



}
