package com.refresh.pos.ui.setting;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.refresh.pos.R;
import com.refresh.pos.ui.MainActivity;
import com.refresh.pos.ui.component.UpdatableFragment;
import com.refresh.pos.ui.sale.ReportFragment;

public class SettingActivity extends AppCompatActivity {

    Fragment settingFragment ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting);




        initUI();


    }


    private void initUI() {

        FragmentManager fm = getSupportFragmentManager();
        if (settingFragment == null) {

            FragmentTransaction ft = fm.beginTransaction();
            settingFragment =new SettingFragment();
            ft.add(R.id.area_main,settingFragment,"main");
            ft.commit();
        }

//        reportCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                UpdatableFragment reportFragment = new ReportFragment();
//
//                FragmentManager fragmentManager = getSupportFragmentManager();
//
//                fragmentManager.beginTransaction().add(R.id.setting,reportFragment,reportFragment.getTag()).commit();
//
//            }
//        });



    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(SettingActivity.this, MainActivity.class));
        finish();

    }
}
