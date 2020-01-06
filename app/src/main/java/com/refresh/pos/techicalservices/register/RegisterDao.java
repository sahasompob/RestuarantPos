package com.refresh.pos.techicalservices.register;

import com.refresh.pos.domain.inventory.Product;
import com.refresh.pos.domain.inventory.ProductLot;
import com.refresh.pos.domain.register.UserDetail;

import java.util.List;

/**
 * DAO for Inventory.
 * 
 * @author Refresh Team
 *
 */
public interface RegisterDao {

	/**
	 * Adds product to inventory.

	 * @return id of this product that assigned from database.
	 */
	int addUser(UserDetail userDetail);
	

	boolean editUSer(UserDetail userDetail);

	/**
	 * Returns product from inventory finds by id.
	 * @param id id of product.
	 * @return product
	 */
	UserDetail getUserDetailById(int id);
	


	List<UserDetail> getAllUserDetail();
	
	/**
	 * Returns list of product in inventory finds by name.
	 * @param name name of product.
	 * @return list of product in inventory finds by name.
	 */


}
