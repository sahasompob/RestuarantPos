package com.refresh.pos.techicalservices.sale;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import android.content.ContentValues;

import com.refresh.pos.domain.DateTimeStrategy;
import com.refresh.pos.domain.inventory.LineItem;
import com.refresh.pos.domain.inventory.LineItemSale;
import com.refresh.pos.domain.inventory.LineItemTemp;
import com.refresh.pos.domain.inventory.Product;
import com.refresh.pos.domain.sale.QuickLoadSale;
import com.refresh.pos.domain.sale.Sale;
import com.refresh.pos.domain.sale.SaleLastRow;
import com.refresh.pos.domain.sale.SaleReport;
import com.refresh.pos.techicalservices.Database;
import com.refresh.pos.techicalservices.DatabaseContents;


/**
 * DAO used by android for Sale process.
 * 
 * @author Refresh Team
 *
 */
public class SaleDaoAndroid implements SaleDao {

	public static final int  SYNC_STATUS_OK = 1;
	public static final int  SYNC_STATUS_FAILED = 0;
	public static final String SERVER_URL_GET= "http://139.180.142.52:3000/api/sales?token=";
	public static final String SERVER_URL_POST = "http://139.180.142.52:3000/api/sales";


	Database database;
	public SaleDaoAndroid(Database database) {
		this.database = database;
	}




	@Override
	public Sale initiateSale(String startTime,int tableId) {
		ContentValues content = new ContentValues();
        content.put("start_time", startTime.toString());
        content.put("status", "ON PROCESS");
        content.put("payment", "n/a");
		content.put("table_id",tableId);
        content.put("total", "0.0");
        content.put("orders", "0");
        content.put("end_time", startTime.toString());

        int id = database.insert(DatabaseContents.TABLE_SALE_MOCK.toString(), content);
		return new Sale(id,startTime);
	}

	@Override
	public void endSaleMock(Sale sale, String endTime,int tableNo , double totalPrice) {
		ContentValues content = new ContentValues();
		content.put("_id", sale.getId());
        content.put("status", "ENDED");
        content.put("payment", "n/a");
		content.put("table_id", tableNo);
        content.put("total", totalPrice);
        content.put("orders", sale.getOrders());
        content.put("start_time", sale.getStartTime());
        content.put("end_time", endTime);
		database.update(DatabaseContents.TABLE_SALE_MOCK.toString(), content);
	}

	@Override
	public void endSale(Sale sale,String gpk,String code,String dated,int tableNo ,int user_id, int member_id, int payment_id ,String vat_type,
						int vat_percent, double totalPrice, double discount,double money_input,double money_change,
						String created,int sync ,String status) {


		ContentValues content = new ContentValues();
		content.put("gpk", gpk);
		content.put("code",code);
		content.put("dated", dated);
		content.put("table_id", tableNo);
		content.put("user_id", user_id);
		content.put("member_id", member_id);
		content.put("payment_id", payment_id);
		content.put("vat_type", vat_type);
		content.put("vat_percent", vat_percent);
		content.put("vat_value", 0);
		content.put("total",totalPrice);
		content.put("discount",discount);
		content.put("money_input",money_input);
		content.put("money_change",money_change);
		content.put("created", created);
		content.put("sync",sync);
		content.put("status", status);
		database.insert(DatabaseContents.TABLE_SALE.toString(), content);
	}

	@Override
	public void holdOrderSale(Sale sale, String endTime,int tableNo) {
		ContentValues content = new ContentValues();
		content.put("_id", sale.getId());
		content.put("status", "ENDED");
		content.put("payment", "n/a");
		content.put("table_id", tableNo);
		content.put("total", sale.getTotal());
		content.put("orders", sale.getOrders());
		content.put("start_time", sale.getStartTime());
		content.put("end_time", endTime);
		database.update(DatabaseContents.TABLE_SALE_MOCK.toString(), content);
	}

	@Override
	public void saveSaleDetail(int sale_id, String gpk , int product_id, int qty, double price, double cost,
							   double total, String topping,String topping_name,double toppingPrice, int sync, String status) {


		String SALTCHARS = "abcdefghijklmnopqrstuvwxyz1234567890";
		StringBuilder salt = new StringBuilder();

		Random rnd = new Random();
		while (salt.length() < 3) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}

		String saltStr = salt.toString();

		ContentValues content = new ContentValues();
		content.put("gpk", saltStr);
		content.put("sale_id", sale_id);
		content.put("product_id", product_id);
		content.put("quantity", qty);
		content.put("unit_price", price);
		content.put("unit_cost", cost);
		content.put("total", total);
		content.put("topping", topping);
		content.put("topping_name", topping_name);
		content.put("topping_price", toppingPrice);
		content.put("sync", sync);
		content.put("status", status);

		database.insert(DatabaseContents.TABLE_SALE_LINEITEM.toString(), content);

	}
	
	@Override
	public int addLineItem(int saleId, LineItem lineItem) {
		ContentValues content = new ContentValues();
        content.put("sale_id", saleId);
        content.put("product_id", lineItem.getProduct().getId());
        content.put("quantity", lineItem.getQuantity());
        content.put("unit_price", lineItem.getPriceAtSale());
        int id = database.insert(DatabaseContents.TABLE_SALE_LINEITEM.toString(), content);
        return id;
	}

	@Override
	public int addLineItem_Temp(int tableNo, LineItem lineItem,String endTime,String topping_group,String topping,String topping_name,double toppingPrice) {
		ContentValues content = new ContentValues();
		content.put("pk", "eeo8");
		content.put("member_id", "1");
		content.put("table_id", tableNo);
		content.put("user_id", lineItem.getProduct().getId());
		content.put("product_id", lineItem.getProduct().getId());
		content.put("qty", lineItem.getQuantity());
		content.put("unit_price", lineItem.getPriceAtSale());
		content.put("topping_group", topping_group);
		content.put("topping", topping);
		content.put("topping_name", topping_name);
		content.put("topping_price", toppingPrice);
		content.put("created", endTime);
		int id = database.insert(DatabaseContents.TABLE_SALE_LINEITEM_TEMP.toString(), content);
		return id;
	}

	@Override
	public void updateLineItem(int saleId, LineItem lineItem) {
		ContentValues content = new ContentValues();		
		content.put("_id", lineItem.getId());
		content.put("sale_id", saleId);
		content.put("product_id", lineItem.getProduct().getId());
		content.put("quantity", lineItem.getQuantity());
		content.put("unit_price", lineItem.getPriceAtSale());
		database.update(DatabaseContents.TABLE_SALE_LINEITEM.toString(), content);
	}

	@Override
	public void updateLineItem_Temp(int id, int quantity, double priceAtSale,String topping_name,String topping, int toppingPrice) {
		ContentValues content = new ContentValues();
		content.put("_id", id);
		content.put("qty", quantity);
//		content.put("unit_price", priceAtSale);
		content.put("topping", topping);
		content.put("topping_name", topping_name);
		content.put("topping_price", toppingPrice);
		content.put("created", DateTimeStrategy.getCurrentTime());

		database.update(DatabaseContents.TABLE_SALE_LINEITEM_TEMP.toString(), content);
	}

	@Override
	public void updateLineItem_Sale(LineItem lineItem) {
		ContentValues content = new ContentValues();
		content.put("_id", lineItem.getId());
		content.put("product_id", lineItem.getProduct().getId());
		content.put("quantity", lineItem.getQuantity());
		content.put("unit_price", lineItem.getPriceAtSale());
		database.update(DatabaseContents.TABLE_SALE_LINEITEM_TEMP.toString(), content);
	}

	@Override
	public void updateTableService(int tableNo, String status_service) {
		ContentValues content = new ContentValues();
		content.put("table_id", tableNo);
		content.put("status_service", status_service);

		database.updateTable(DatabaseContents.TABLE_TABLE_DETAIL.toString(), content);
	}


	@Override
	public List<SaleLastRow> getLastRow() {
		return getLastRow(" WHERE status = 'ENDED'");
	}


	/**
	 * This method get all Sale *BUT* no LineItem will be loaded.
	 * @param condition
	 * @return
	 */
	public List<SaleLastRow> getLastRow(String condition) {
		String queryString = "SELECT * FROM " + DatabaseContents.TABLE_SALE + " ORDER BY _id DESC LIMIT 1 ";
		List<Object> objectList = database.select(queryString);

		List<SaleLastRow> list = new ArrayList<SaleLastRow>();
		for (Object object: objectList) {
			ContentValues content = (ContentValues) object;
			list.add(new SaleLastRow(
							content.getAsInteger("_id"),
							content.getAsString("gpk"),
							content.getAsString("code"),
							content.getAsString("dated"),
							content.getAsInteger("table_id"),
							content.getAsInteger("user_id"),
							content.getAsInteger("member_id"),
							content.getAsInteger("payment_id"),
							content.getAsString("vat_type"),
							content.getAsInteger("vat_percent"),
							content.getAsInteger("vat_value"),
							content.getAsDouble("total"),
							content.getAsDouble("discount"),
							content.getAsDouble("money_input"),
							content.getAsDouble("money_change"),
							content.getAsString("created"),
							content.getAsInteger("sync"),
							content.getAsString("status")

					)
			);
		}
		return list;

	}

	@Override
	public List<SaleLastRow> getAllSale() {
		return getAllSale(" WHERE status = 'ENDED'");
	}

	@Override
	public List<SaleLastRow> getAllSaleDuring(Calendar start, Calendar end) {
		String startBound = DateTimeStrategy.getSQLDateFormat(start);
		String endBound = DateTimeStrategy.getSQLDateFormat(end);
		List<SaleLastRow> list = getAllSale(" WHERE dated BETWEEN '" + startBound + " 00:00:00' AND '" + endBound + " 23:59:59' AND status = 'ENDED'");
		return list;
	}

	/**
	 * This method get all Sale *BUT* no LineItem will be loaded.
	 * @param condition
	 * @return
	 */
	public List<SaleLastRow> getAllSale(String condition) {
		String queryString = "SELECT * FROM " + DatabaseContents.TABLE_SALE + condition;
		List<Object> objectList = database.select(queryString);
		List<SaleLastRow> list = new ArrayList<SaleLastRow>();
		for (Object object: objectList) {
			ContentValues content = (ContentValues) object;
			list.add(new SaleLastRow(
					content.getAsInteger("_id"),
					content.getAsString("gpk"),
					content.getAsString("code"),
					content.getAsString("dated"),
					content.getAsInteger("table_id"),
					content.getAsInteger("user_id"),
					content.getAsInteger("member_id"),
					content.getAsInteger("payment_id"),
					content.getAsString("vat_type"),
					content.getAsInteger("vat_percent"),
					content.getAsInteger("vat_value"),
					content.getAsDouble("total"),
					content.getAsDouble("discount"),
					content.getAsDouble("money_input"),
					content.getAsDouble("money_change"),
					content.getAsString("created"),
					content.getAsInteger("sync"),
					content.getAsString("status")
					)
			);
		}
		return list;
	}
	/**
	 * This load complete data of Sale.
	 * @param id Sale ID.
	 * @return Sale of specific ID.
	 */
	@Override
	public Sale getSaleById(int id) {
		String queryString = "SELECT * FROM " + DatabaseContents.TABLE_SALE + " WHERE _id = " + id;
        List<Object> objectList = database.select(queryString);
        List<Sale> list = new ArrayList<Sale>();
        for (Object object: objectList) {
        	ContentValues content = (ContentValues) object;
        	list.add(new Sale(
        			content.getAsInteger("_id"),
        			content.getAsString("start_time"),
        			content.getAsString("end_time"),
        			content.getAsString("status"),
							getLineItem(content.getAsInteger("_id")))
        			);
        }
        return list.get(0);
	}



    @Override
    public SaleReport getSaleReportById(int id) {
        String queryString = "SELECT * FROM " + DatabaseContents.TABLE_SALE + " WHERE _id = " + id;
        List<Object> objectList = database.select(queryString);
        List<SaleReport> list = new ArrayList<SaleReport>();
        for (Object object: objectList) {
            ContentValues content = (ContentValues) object;
            list.add(new SaleReport(
                    content.getAsInteger("_id"),
					content.getAsString("status"),
					content.getAsString("payment"),
					content.getAsInteger("table_id"),
					content.getAsDouble("total"),
					content.getAsString("start_time"),
                    content.getAsString("end_time"),
                    content.getAsString("order"),
                    getLineItemProduct(content.getAsInteger("_id")))

            );
        }
        return list.get(0);
    }

	@Override
	public List<LineItemSale> getLineItemProduct(int saleId) {
		String queryString = "SELECT * FROM " + DatabaseContents.TABLE_SALE_LINEITEM + " WHERE sale_id = " + saleId;
		List<Object> objectList = database.select(queryString);
		List<LineItemSale> list = new ArrayList<LineItemSale>();
		for (Object object: objectList) {
			ContentValues content = (ContentValues) object;
			int productId = content.getAsInteger("product_id");

			String queryString2 = "SELECT * FROM " + DatabaseContents.TABLE_PRODUCT_CATALOG + " WHERE _id = " + productId;
			List<Object> objectList2 = database.select(queryString2);

			List<Product> productList = new ArrayList<Product>();
			for (Object object2: objectList2) {
				ContentValues content2 = (ContentValues) object2;
				productList.add(new Product(productId,content2.getAsInteger("cate_id"),content2.getAsString("topping_group"),content2.getAsString("name"),content2.getAsString("code"),
						content2.getAsDouble("cost"),content2.getAsDouble("unit_price"),content2.getAsString("image"),content2.getAsString("gpk"),
						content2.getAsString("status"),content2.getAsInteger("sync")));
			}

			list.add(new LineItemSale(
					content.getAsInteger("_id"),
					content.getAsString("gpk"),
					content.getAsInteger("sale_id"),
					productList.get(0),
					content.getAsInteger("quantity"),
					content.getAsDouble("unit_price"),
					content.getAsDouble("unit_cost"),
                    content.getAsDouble("total"),
                    content.getAsString("topping"),
                    content.getAsString("topping_name"),
					content.getAsDouble("topping_price"),
                    content.getAsString("sync"),
                    content.getAsString("status")));
		}

		return list;
	}




	@Override
	public Sale getByTableId(int id) {
		String queryString = "SELECT * FROM " + DatabaseContents.TABLE_SALE_MOCK + " WHERE table_id = " + id;
		List<Object> objectList = database.select(queryString);
		List<Sale> list = new ArrayList<Sale>();
		for (Object object: objectList) {
			ContentValues content = (ContentValues) object;
			list.add(new Sale(
					content.getAsInteger("_id"),
					content.getAsString("start_time"),
					content.getAsString("end_time"),
					content.getAsString("status"),
					getLineItem(content.getAsInteger("table_id")))
			);
		}
		return list.get(0);
	}

	@Override
	public LineItemTemp getByTableIdTest(int id) {
		String queryString = "SELECT * FROM " + DatabaseContents.TABLE_SALE_LINEITEM_TEMP + " WHERE table_id = " + id;
		List<Object> objectList = database.select(queryString);
		List<LineItemTemp> list = new ArrayList<LineItemTemp>();

		for (Object object: objectList) {
			ContentValues content = (ContentValues) object;
			int productId = content.getAsInteger("product_id");

			String queryString2 = "SELECT * FROM " + DatabaseContents.TABLE_PRODUCT_CATALOG + " WHERE _id = " + productId;
			List<Object> objectList2 = database.select(queryString2);

			List<Product> productList = new ArrayList<Product>();
			for (Object object2: objectList2) {
				ContentValues content2 = (ContentValues) object2;
				productList.add(new Product(productId,content2.getAsInteger("cate_id"),content2.getAsString("topping_group"),content2.getAsString("name"),content2.getAsString("code"),
						content2.getAsDouble("cost"),content2.getAsDouble("unit_price"),content2.getAsString("image"),content2.getAsString("gpk"),
						content2.getAsString("status"),content2.getAsInteger("sync")));
			}
			list.add(new LineItemTemp(
					content.getAsInteger("_id"),
					content.getAsString("pk"),
					content.getAsInteger("member_id"),
					content.getAsInteger("table_id"),
					content.getAsInteger("user_id"),
					productList.get(0),
					content.getAsInteger("qty"),
					content.getAsDouble("unit_price"),
					content.getAsString("topping"),
					content.getAsString("created")));
		}
		return list.get(0);
	}


	@Override
	public List<LineItem> getLineItem(int tableId) {
		String queryString = "SELECT * FROM " + DatabaseContents.TABLE_SALE_LINEITEM_TEMP + " WHERE table_id = " + tableId;
		List<Object> objectList = database.select(queryString);
		List<LineItem> list = new ArrayList<LineItem>();
		for (Object object: objectList) {
			ContentValues content = (ContentValues) object;
			 int productId = content.getAsInteger("product_id");

			String queryString2 = "SELECT * FROM " + DatabaseContents.TABLE_PRODUCT_CATALOG + " WHERE _id = " + productId;
			List<Object> objectList2 = database.select(queryString2);

			List<Product> productList = new ArrayList<Product>();
			for (Object object2: objectList2) {
				ContentValues content2 = (ContentValues) object2;
				productList.add(new Product(productId,content2.getAsInteger("cate_id"),content2.getAsString("topping_group"),content2.getAsString("name"),content2.getAsString("code"),
						content2.getAsDouble("cost"),content2.getAsDouble("unit_price"),content2.getAsString("image"),content2.getAsString("gpk"),
						content2.getAsString("status"),content2.getAsInteger("sync")));
			}

			list.add(new LineItem(
					content.getAsInteger("_id"),
					content.getAsString("pk"),
					content.getAsInteger("member_id"),
					content.getAsInteger("table_id"),
					content.getAsInteger("user_id"),
					productList.get(0),
					content.getAsInteger("qty"),
					content.getAsDouble("unit_price"),
					content.getAsString("topping_group"),
					content.getAsString("topping"),
					content.getAsString("topping_name"),
					content.getAsDouble("topping_price"),
					content.getAsString("created")));
		}

		return list;
	}

	@Override
	public List<LineItem> getAllLinebyTable(int tableNo) {
		String queryString = "SELECT * FROM " + DatabaseContents.TABLE_SALE_LINEITEM_TEMP + " WHERE table_id = " + tableNo;
		List<Object> objectList = database.select(queryString);
		List<LineItem> list = new ArrayList<LineItem>();
		for (Object object: objectList) {
			ContentValues content = (ContentValues) object;
			int productId = content.getAsInteger("product_id");

			String queryString2 = "SELECT * FROM " + DatabaseContents.TABLE_PRODUCT_CATALOG + " WHERE _id = " + productId;
			List<Object> objectList2 = database.select(queryString2);

			List<Product> productList = new ArrayList<Product>();
			for (Object object2: objectList2) {
				ContentValues content2 = (ContentValues) object2;
				productList.add(new Product(productId,content2.getAsInteger("cate_id"),content2.getAsString("topping_group"),content2.getAsString("name"),content2.getAsString("code"),
						content2.getAsDouble("cost"),content2.getAsDouble("unit_price"),content2.getAsString("image"),content2.getAsString("gpk"),
						content2.getAsString("status"),content2.getAsInteger("sync")));
			}

			list.add(new LineItem(content.getAsInteger("_id") ,
					content.getAsString("pk"),
					content.getAsInteger("member_id"),
					content.getAsInteger("table_id"),
					content.getAsInteger("user_id"),
					productList.get(0),
					content.getAsInteger("qty"),
					content.getAsDouble("unit_price"),
					content.getAsString("topping_group"),
					content.getAsString("topping"),
					content.getAsString("topping_name"),
					content.getAsDouble("topping_price"),
					content.getAsString("created")));
		}
		return list;
	}

	@Override
	public List<LineItemTemp> getLineItemTemp(int tableNo) {
		String queryString = "SELECT * FROM " + DatabaseContents.TABLE_SALE_LINEITEM_TEMP + " WHERE table_id = " + tableNo;
		List<Object> objectList = database.select(queryString);
		List<LineItemTemp> list = new ArrayList<LineItemTemp>();
		for (Object object: objectList) {
			ContentValues content = (ContentValues) object;
			int productId = content.getAsInteger("product_id");

			String queryString2 = "SELECT * FROM " + DatabaseContents.TABLE_PRODUCT_CATALOG + " WHERE _id = " + productId;
			List<Object> objectList2 = database.select(queryString2);

			List<Product> productList = new ArrayList<Product>();
			for (Object object2: objectList2) {
				ContentValues content2 = (ContentValues) object2;
				productList.add(new Product(productId,content2.getAsInteger("cate_id"),content2.getAsString("topping_group"),content2.getAsString("name"),content2.getAsString("code"),
						content2.getAsDouble("cost"),content2.getAsDouble("unit_price"),content2.getAsString("image"),content2.getAsString("gpk"),
						content2.getAsString("status"),content2.getAsInteger("sync")));
			}

			list.add(new LineItemTemp(
					content.getAsInteger("_id"),
					content.getAsString("pk"),
					content.getAsInteger("member_id"),
					content.getAsInteger("table_id"),
					content.getAsInteger("user_id"),
					productList.get(0),
					content.getAsInteger("qty"),
					content.getAsDouble("unit_price"),
					content.getAsString("topping"),
					content.getAsString("created")));
		}
		return list;
	}

	@Override
	public void clearSaleLedger() {
		database.execute("DELETE FROM " + DatabaseContents.TABLE_SALE);
		database.execute("DELETE FROM " + DatabaseContents.TABLE_SALE_LINEITEM);
	}

	@Override
	public void cancelSale(Sale sale,String endTime) {

//		database.delete(DatabaseContents.TABLE_SALE_MOCK.toString(), sale.getId());


    }

	@Override
	public void removeLineItem(int id) {
		database.delete(DatabaseContents.TABLE_SALE_LINEITEM_TEMP.toString(), id);
	}

	@Override
	public void clearTemp(int tableId) {
		database.deleteTableId(DatabaseContents.TABLE_SALE_LINEITEM_TEMP.toString(), tableId);
		database.deleteTableId(DatabaseContents.TABLE_SALE_MOCK.toString(), tableId);
		database.deleteTableId(DatabaseContents.TABLE_SALE_MOCK.toString(), 0);
	}





}
