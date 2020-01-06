package com.refresh.pos.techicalservices.register;

import android.content.ContentValues;
import android.util.Log;

import com.refresh.pos.domain.inventory.Product;
import com.refresh.pos.domain.inventory.ProductLot;
import com.refresh.pos.domain.register.UserDetail;
import com.refresh.pos.techicalservices.Database;
import com.refresh.pos.techicalservices.DatabaseContents;
import com.refresh.pos.techicalservices.inventory.InventoryDao;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO used by android for Inventory.
 * 
 * @author Refresh Team
 *
 */
public class RegisterDaoAndroid implements RegisterDao {

	public static final int  SYNC_STATUS_OK = 1;
	public static final int  SYNC_STATUS_FAILED = 0;
	public static final String SERVER_URL_Register = "http://139.180.142.52:3000/api/register";


	private Database database;

	/**
	 * Constructs InventoryDaoAndroid.
	 * @param database database for use in InventoryDaoAndroid.
	 */
	public RegisterDaoAndroid(Database database) {
		this.database = database;
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
						content.getAsString("name"),
						content.getAsString("name"),
                        content.getAsString("image"),
						content.getAsDouble("cost"),
                        content.getAsDouble("unit_price"),
						content.getAsString("image"),
						content.getAsString("gpk"),
						content.getAsString("status"),
						content.getAsInteger("sync"))
                );
        }
        return list;
	}


	@Override
	public int addUser(UserDetail userDetail) {
		ContentValues content = new ContentValues();

//		content.put("cate_id", userDetail.g());
//		content.put("name", userDetail.getName());
//		content.put("image", userDetail.getImage());
//		content.put("cost", userDetail.getCost());
//		content.put("unit_price", userDetail.getUnitPrice());
//		content.put("status", "ACTIVE");
//		content.put("code", userDetail.getCode());
//		content.put("gpk", userDetail.getGpk());
//		content.put("sync", userDetail.getSyncProduct());


		int id = database.insert(DatabaseContents.TABLE_PRODUCT_CATALOG.toString(), content);

		return id;
	}

	@Override
	public boolean editUSer(UserDetail userDetail) {
		return false;
	}

	@Override
	public UserDetail getUserDetailById(int id) {
		return null;
	}

	@Override
	public List<UserDetail> getAllUserDetail() {
		return null;
	}

}
