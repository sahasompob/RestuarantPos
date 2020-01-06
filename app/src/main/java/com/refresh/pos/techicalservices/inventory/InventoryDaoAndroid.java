package com.refresh.pos.techicalservices.inventory;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.provider.ContactsContract;
import android.util.Log;

import com.refresh.pos.domain.inventory.GroupToppingProduct;
import com.refresh.pos.domain.inventory.Product;
import com.refresh.pos.domain.inventory.ProductLot;
import com.refresh.pos.domain.inventory.ToppingProduct;
import com.refresh.pos.techicalservices.Database;
import com.refresh.pos.techicalservices.DatabaseContents;

/**
 * DAO used by android for Inventory.
 * 
 * @author Refresh Team
 *
 */
public class InventoryDaoAndroid implements InventoryDao {

	public static final int  SYNC_STATUS_OK = 1;
	public static final int  SYNC_STATUS_FAILED = 0;
	public static final String SERVER_URL = "http://139.180.142.52:3000/api/products?token=";
	public static final String SERVER_URL_POST = "http://139.180.142.52:3000/api/product";
	public static final String SERVER_URL_PUT = "http://139.180.142.52:3000/api/product";
	public static final String SERVER_URL_TOPPING_POST = "http://139.180.142.52:3000/api/topping";
	public static final String SERVER_URL_TOPPING_PUT = "http://139.180.142.52:3000/api/topping";
	public static final String SERVER_URL_TOPPING_GROUP_POST = "http://139.180.142.52:3000/api/toppinggroup";
	public static final String SERVER_URL_TOPPING_GROUP_PUT = "http://139.180.142.52:3000/api/toppinggroup";




	private Database database;
	
	/**
	 * Constructs InventoryDaoAndroid.
	 * @param database database for use in InventoryDaoAndroid.
	 */
	public InventoryDaoAndroid(Database database) {
		this.database = database;
	}

	@Override
	public int addProduct(Product product) {

		ContentValues content = new ContentValues();
		content.put("cate_id", product.getCate_id());
        content.put("topping_group", product.getTopping_group());
        content.put("name", product.getName());
        content.put("image", product.getImage());
		content.put("cost", product.getCost());
        content.put("unit_price", product.getUnitPrice());
        content.put("status", "ACTIVE");
		content.put("code", product.getCode());
		content.put("gpk", product.getGpk());
		content.put("sync", product.getSyncProduct());


        
        int id = database.insert(DatabaseContents.TABLE_PRODUCT_CATALOG.toString(), content);
        
        
    	ContentValues content2 = new ContentValues();
        content2.put("_id", id);
        content2.put("quantity", 0);
        database.insert(DatabaseContents.TABLE_STOCK_SUM.toString(), content2);
        
        return id;
	}

		public boolean updateProductSync(Product product) {
			ContentValues content = new ContentValues();
			content.put("_id", product.getId());
			content.put("name", product.getName());
			content.put("code", product.getCode());
			content.put("status", "ACTIVE");
			content.put("unit_price", product.getUnitPrice());
			content.put("sync", product.getSyncProduct());
			return database.update(DatabaseContents.TABLE_PRODUCT_CATALOG.toString(), content);
	}


	/**
	 * Converts list of object to list of product.
	 * @param objectList list of object.
	 * @return list of product.
	 */
	private List<Product> toProductList(List<Object> objectList) {
		List<Product> list = new ArrayList<Product>();
        for (Object object: objectList) {
        	ContentValues content = (ContentValues) object;
                list.add(new Product(
                		content.getAsInteger("_id"),
						content.getAsInteger("cate_id"),
						content.getAsString("topping_group"),
                        content.getAsString("name"),
                        content.getAsString("image"),
						content.getAsDouble("cost"),
                        content.getAsDouble("unit_price"),
						content.getAsString("status"),
						content.getAsString("code"),
						content.getAsString("gpk"),
						content.getAsInteger("sync"))
                );
        }
        return list;
	}

	@Override
	public List<Product> getAllProduct() {
        return getAllProduct(" WHERE status = 'ACTIVE'");
	}
	
	/**
	 * Returns list of all products in inventory.
	 * @param condition specific condition for getAllProduct.
	 * @return list of all products in inventory.
	 */
	private List<Product> getAllProduct(String condition) {
		String queryString = "SELECT * FROM " + DatabaseContents.TABLE_PRODUCT_CATALOG.toString() + condition + " ORDER BY name";
        List<Product> list = toProductList(database.select(queryString));
        return list;
	}
	
	/**
	 * Returns product from inventory finds by specific reference. 
	 * @param reference reference value.
	 * @param value value for search.
	 * @return list of product.
	 */
	private List<Product> getProductBy(String reference, String value) {
        String condition = " WHERE " + reference + " = " + value + " ;";
        return getAllProduct(condition);
	}
	
	/**
	 * Returns product from inventory finds by similar name.
	 * @param reference reference value.
	 * @param value value for search.
	 * @return list of product.
	 */
	private List<Product> getSimilarProductBy(String reference, String value) {
        String condition = " WHERE " + reference + " LIKE '%" + value + "%' ;";
        return getAllProduct(condition);
	}

	@Override
	public Product getProductByBarcode(String barcode) {
		List<Product> list = getProductBy("barcode", barcode);
        if (list.isEmpty()) return null;
        return list.get(0);
	}

	@Override
	public Product getProductById(int id) {
		return getProductBy("_id", id+"").get(0);
	}



	@Override
	public void editProduct(int id, int cate_id,int topping_group, String name, String image , double cost,  double salePrice,String status ,String code ,String gpk,int sync) {
		ContentValues content = new ContentValues();
		content.put("_id", id);
		content.put("name", name);
		content.put("code", code);
		content.put("topping_group", topping_group);
		content.put("status", "ACTIVE");
		content.put("unit_price",salePrice);

		database.update(DatabaseContents.TABLE_PRODUCT_CATALOG.toString(), content);
	}





	@Override
	public int addProductLot(ProductLot productLot) {
		 ContentValues content = new ContentValues();
         content.put("date_added", productLot.getDateAdded());
         content.put("quantity",  productLot.getQuantity());
         content.put("product_id",  productLot.getProduct().getId());
         content.put("cost",  productLot.unitCost());
         int id = database.insert(DatabaseContents.TABLE_STOCK.toString(), content);
         
         int productId = productLot.getProduct().getId();
         ContentValues content2 = new ContentValues();
         content2.put("_id", productId);
         content2.put("quantity", getStockSumById(productId) + productLot.getQuantity());
         Log.d("inventory dao android","" + getStockSumById(productId) + " " + productId + " " +productLot.getQuantity() );
         database.update(DatabaseContents.TABLE_STOCK_SUM.toString(), content2);   
         
         return id;
	}


	@Override
	public List<Product> getProductByName(String name) {
		return getSimilarProductBy("name", name);
	}

	@Override
	public List<Product> searchProduct(String search) {
		String condition = " WHERE name LIKE '%" + search + "%' OR cate_id LIKE '%" + search + "%' ;";
        return getAllProduct(condition);
	}
	
	/**
	 * Returns list of all ProductLot in inventory.
	 * @param condition specific condition for get ProductLot.
	 * @return list of all ProductLot in inventory.
	 */
	private List<ProductLot> getAllProductLot(String condition) {
		String queryString = "SELECT * FROM " + DatabaseContents.TABLE_STOCK.toString() + condition;
        List<ProductLot> list = toProductLotList(database.select(queryString));
        return list;
	}

	/**
	 * Converts list of object to list of ProductLot.
	 * @param objectList list of object.
	 * @return list of ProductLot.
	 */
	private List<ProductLot> toProductLotList(List<Object> objectList) {
		List<ProductLot> list = new ArrayList<ProductLot>();
		for (Object object: objectList) {
			ContentValues content = (ContentValues) object;
			int productId = content.getAsInteger("product_id");
			Product product = getProductById(productId);
					list.add( 
					new ProductLot(content.getAsInteger("_id"),
							content.getAsString("date_added"),
							content.getAsInteger("quantity"),
							product,
							content.getAsDouble("cost"))
					);
		}
		return list;
	}

	@Override
	public List<ProductLot> getProductLotByProductId(int id) {
		return getAllProductLot(" WHERE product_id = " + id);
	}
	
	@Override
	public List<ProductLot> getProductLotById(int id) {
		return getAllProductLot(" WHERE _id = " + id);
	}

	@Override
	public List<ProductLot> getAllProductLot() {
		return getAllProductLot("");
	}

	@Override
	public int getStockSumById(int id) {
		String queryString = "SELECT * FROM " + DatabaseContents.TABLE_STOCK_SUM + " WHERE _id = " + id;
		List<Object> objectList = (database.select(queryString));
		ContentValues content = (ContentValues) objectList.get(0);
		int quantity = content.getAsInteger("quantity");
		Log.d("inventoryDaoAndroid", "stock sum of "+ id + " is " + quantity);
		return quantity;
	}

	@Override
	public void updateStockSum(int productId, double quantity) {
		 ContentValues content = new ContentValues();
         content.put("_id", productId);
         content.put("quantity", getStockSumById(productId) - quantity);
         database.update(DatabaseContents.TABLE_STOCK_SUM.toString(), content);   
	}

	@Override
	public void clearProductCatalog() {		
		database.execute("DELETE FROM " + DatabaseContents.TABLE_PRODUCT_CATALOG);
	}

	@Override
	public void clearStock() {
		database.execute("DELETE FROM " + DatabaseContents.TABLE_STOCK);
		database.execute("DELETE FROM " + DatabaseContents.TABLE_STOCK_SUM);
	}

	@Override
	public void suspendProduct(Product product) {
		ContentValues content = new ContentValues();
		content.put("_id", product.getId());
		content.put("name", product.getName());
		content.put("code", product.getCode());
		content.put("status", "INACTIVE");
		content.put("unit_price", product.getUnitPrice());
		database.update(DatabaseContents.TABLE_PRODUCT_CATALOG.toString(), content);
	}

	@Override
	public int addTopping(ToppingProduct toppingProduct) {
		ContentValues content = new ContentValues();
		content.put("gpk", toppingProduct.getGpk());
		content.put("group_id", toppingProduct.getGroupId());
		content.put("name", toppingProduct.getName());
		content.put("price", toppingProduct.getPrice());
		content.put("image", toppingProduct.getImage());
		content.put("status", "use");


		int id = database.insert(DatabaseContents.TABLE_TOPPING.toString(), content);
		return id;
	}

	@Override
	public void editTopping(int id,int groupId, String name,double price) {
		ContentValues content = new ContentValues();
		content.put("_id", id);
		content.put("group_id", groupId);
		content.put("name", name);
		content.put("price", price);
		database.update(DatabaseContents.TABLE_TOPPING.toString(), content);
	}

	private List<ToppingProduct> toToppingList(List<Object> objectList) {
		List<ToppingProduct> list = new ArrayList<ToppingProduct>();
		for (Object object: objectList) {
			ContentValues content = (ContentValues) object;
			list.add(new ToppingProduct(
					content.getAsInteger("_id"),
					content.getAsString("gpk"),
					content.getAsInteger("group_id"),
					content.getAsString("name"),
					content.getAsDouble("price"),
					content.getAsString("image"),
					content.getAsString("status")
			));
		}
		return list;
	}

	@Override
	public List<ToppingProduct> getAllTopping() {return getAllTopping(" WHERE status = 'use'");
	}
	@Override
	public List<ToppingProduct> getToppingByGroupId(String id) {

		List<ToppingProduct> list = getAllTopping(" WHERE group_id in "+"("+ id +")");

		return list;
	}

	private List<ToppingProduct> getAllTopping(String condition) {
		String queryString = "SELECT * FROM " + DatabaseContents.TABLE_TOPPING.toString() + condition + " ORDER BY _id";
		List<ToppingProduct> list = toToppingList(database.select(queryString));
		return list;
	}


	@Override
	public int addGroupTopping(GroupToppingProduct groupToppingProduct) {
		ContentValues content = new ContentValues();
		content.put("gpk", groupToppingProduct.getGpk());
		content.put("name", groupToppingProduct.getName());
		content.put("image", groupToppingProduct.getImage());
		content.put("status", "use");


		int id = database.insert(DatabaseContents.TABLE_TOPPING_GROUP.toString(), content);
		return id;
	}

	@Override
	public void editGroupTopping(int id, String name, String image) {
		ContentValues content = new ContentValues();
		content.put("_id", id);
		content.put("name", name);
		content.put("image", image);
		database.update(DatabaseContents.TABLE_TOPPING_GROUP.toString(), content);
	}



	private List<GroupToppingProduct> toGroupToppingList(List<Object> objectList) {
		List<GroupToppingProduct> list = new ArrayList<GroupToppingProduct>();
		for (Object object: objectList) {
			ContentValues content = (ContentValues) object;
			list.add(new GroupToppingProduct(
					content.getAsInteger("_id"),
					content.getAsString("gpk"),
					content.getAsString("name"),
					content.getAsString("image"),
					content.getAsString("status")
			));
		}
		return list;
	}

	@Override
	public List<GroupToppingProduct> getAllGroupTopping() {return getAllGroupTopping(" WHERE status = 'use'");
	}

	private List<GroupToppingProduct> getAllGroupTopping(String condition) {
		String queryString = "SELECT * FROM " + DatabaseContents.TABLE_TOPPING_GROUP.toString() + condition + " ORDER BY _id";
		List<GroupToppingProduct> list = toGroupToppingList(database.select(queryString));
		return list;
	}







}
