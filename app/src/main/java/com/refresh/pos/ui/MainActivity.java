package com.refresh.pos.ui;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.refresh.pos.R;
import com.refresh.pos.domain.LanguageController;
import com.refresh.pos.domain.inventory.Inventory;
import com.refresh.pos.domain.inventory.Product;
import com.refresh.pos.domain.inventory.ProductCatalog;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.ui.component.UpdatableFragment;
import com.refresh.pos.ui.inventory.InventoryFragment;
import com.refresh.pos.ui.inventory.ProductDetailActivity;
import com.refresh.pos.ui.sale.ReportFragment;
import com.refresh.pos.ui.sale.SaleFragment;
import com.refresh.pos.ui.main.MainFragment;
import com.refresh.pos.ui.setting.SettingActivity;

/**
 * This UI loads 3 main pages (Inventory, Sale, Report)
 * Makes the UI flow by slide through pages using ViewPager.
 * 
 * @author Refresh Team
 *
 */
@SuppressLint("NewApi")
public class MainActivity extends AppCompatActivity  {

//	private ViewPager viewPager;
	private ProductCatalog productCatalog;
	private String productId;
	private Product product;
	private static boolean SDK_SUPPORTED;
	private Resources res;
    private ImageView ic_setting;
    private ImageView ic_report;
    private ImageView ic_refresh;



	@SuppressLint("NewApi")

	@Override
	protected void onCreate(Bundle savedInstanceState) {




		res = getResources();
		setContentView(R.layout.layout_main);
//		viewPager = (ViewPager) findViewById(R.id.pager);
		super.onCreate(savedInstanceState);
//		SDK_SUPPORTED = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;

        ic_setting = (ImageView) findViewById(R.id.ic_setting);
        ic_report = (ImageView) findViewById(R.id.ic_report);
		ic_refresh = (ImageView) findViewById(R.id.ic_refresh);

		Toolbar topToolBar = (Toolbar)findViewById(R.id.toolbar_top);
		setSupportActionBar(topToolBar);

		FragmentManager fragmentManager = getSupportFragmentManager();
		UpdatableFragment mainFragment = new MainFragment();
        fragmentManager.beginTransaction().replace(R.id.area_main,mainFragment,mainFragment.getTag()).commit();


        initUI();
	}


    private void initUI() {

        ic_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

				Intent intent = new Intent(MainActivity.this, SettingActivity.class);
				startActivity(intent);


            }
        });

        ic_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

				FragmentManager fragmentManager = getSupportFragmentManager();
				UpdatableFragment mainFragment = new ReportFragment();
				fragmentManager.beginTransaction().replace(R.id.area_main,mainFragment,mainFragment.getTag()).commit();


            }
        });

		ic_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                UpdatableFragment mainFragment = new MainFragment();
                fragmentManager.beginTransaction().replace(R.id.area_main,mainFragment,mainFragment.getTag()).commit();

                Toast.makeText(getApplicationContext() ,"Refresh Application", Toast.LENGTH_SHORT).show();

            }
        });


        }

	public void refresh(){

		FragmentManager fragmentManager = getSupportFragmentManager();
		UpdatableFragment mainFragment = new MainFragment();
		fragmentManager.beginTransaction().replace(R.id.area_main,mainFragment,mainFragment.getTag()).commit();

		Toast.makeText(getApplicationContext() ,"Refresh Application", Toast.LENGTH_SHORT).show();


	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			openQuitDialog();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * Open quit dialog.
	 */
	private void openQuitDialog() {
		AlertDialog.Builder quitDialog = new AlertDialog.Builder(
				MainActivity.this);
		quitDialog.setTitle(res.getString(R.string.dialog_quit));
		quitDialog.setPositiveButton(res.getString(R.string.quit), new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});

		quitDialog.setNegativeButton(res.getString(R.string.no), new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		quitDialog.show();
	}

	/**
	 * Option on-click handler.
	 * @param view
	 */
	public void optionOnClickHandler(View view) {
//		viewPager.setCurrentItem(0);
		String id = view.getTag().toString();
		productId = id;
		try {
			productCatalog = Inventory.getInstance().getProductCatalog();
		} catch (NoDaoSetException e) {
			e.printStackTrace();
		}
		product = productCatalog.getProductById(Integer.parseInt(productId));
		openDetailDialog();

	}



	/**
	 * Open detail dialog.
	 */
	private void openDetailDialog() {
		AlertDialog.Builder quitDialog = new AlertDialog.Builder(MainActivity.this);
		quitDialog.setTitle(product.getName());
		quitDialog.setPositiveButton(res.getString(R.string.remove), new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				openRemoveDialog();
			}
		});

		quitDialog.setNegativeButton(res.getString(R.string.product_detail), new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent newActivity = new Intent(MainActivity.this,
						ProductDetailActivity.class);
				newActivity.putExtra("id", productId);
				startActivity(newActivity);
			}
		});

		quitDialog.show();
	}

	/**
	 * Open remove dialog.
	 */
	private void openRemoveDialog() {
		AlertDialog.Builder quitDialog = new AlertDialog.Builder(
				MainActivity.this);
		quitDialog.setTitle(res.getString(R.string.dialog_remove_product));
		quitDialog.setPositiveButton(res.getString(R.string.no), new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		quitDialog.setNegativeButton(res.getString(R.string.remove), new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				productCatalog.suspendProduct(product);
//				pagerAdapter.update(0);
			}
		});

		quitDialog.show();
	}

	/**
	 * Get view-pager
	 * @return
	 */
//	public ViewPager getViewPager() {
//		return viewPager;
//	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu){
//
//		getMenuInflater().inflate(R.menu.menu_toolbar, menu);
//		return super.onCreateOptionsMenu(menu);
//	}
//
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//
//		switch (item.getItemId()) {
//
//			case R.id.action_settings:
//
//				Toast.makeText(MainActivity.this, "Action action_settings", Toast.LENGTH_LONG).show();
//
////				setLanguage("en");
//				return true;
//			case R.id.action_sale:
//				Toast.makeText(MainActivity.this, "Action action_sale", Toast.LENGTH_LONG).show();
//
////				setLanguage("th");
//				return true;
////            case R.id.lang_jp:
////            	setLanguage("jp");
////                return true;
//			default:
//
//				return super.onOptionsItemSelected(item);
//		}
//	}

	/**
	 * Set language
	 * @param localeString
	 */


	private void setLanguage(String localeString) {
		Locale locale = new Locale(localeString);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		LanguageController.getInstance().setLanguage(localeString);
		getBaseContext().getResources().updateConfiguration(config,
				getBaseContext().getResources().getDisplayMetrics());
		Intent intent = getIntent();
		finish();
		startActivity(intent);
	}

}

/**
 * 
 * @author Refresh team
 *
 */
//class PagerAdapter extends FragmentStatePagerAdapter {
//
//	private UpdatableFragment[] fragments;
//	private String[] fragmentNames;
//
//	/**
//	 * Construct a new PagerAdapter.
//	 * @param fragmentManager
//	 * @param res
//	 */
//	public PagerAdapter(FragmentManager fragmentManager, Resources res) {
//
//		super(fragmentManager);
//
//		UpdatableFragment reportFragment = new ReportFragment();
//		UpdatableFragment saleFragment = new SaleFragment(reportFragment);
//		UpdatableFragment inventoryFragment = new InventoryFragment(
//				saleFragment);
//
//		fragments = new UpdatableFragment[] { inventoryFragment, saleFragment,
//				reportFragment };
//		fragmentNames = new String[] { res.getString(R.string.inventory),
//				res.getString(R.string.sale),
//				res.getString(R.string.report) };
//
//	}
//
//	@Override
//	public Fragment getItem(int i) {
//		return fragments[i];
//	}
//
//	@Override
//	public int getCount() {
//		return fragments.length;
//	}
//
//	@Override
//	public CharSequence getPageTitle(int i) {
//		return fragmentNames[i];
//	}
//
//	/**
//	 * Update
//	 * @param index
//	 */
//	public void update(int index) {
//		fragments[index].update();
//	}
//
//}