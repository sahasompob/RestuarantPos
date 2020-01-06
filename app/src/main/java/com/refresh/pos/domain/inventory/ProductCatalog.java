package com.refresh.pos.domain.inventory;

import java.util.List;

import com.refresh.pos.techicalservices.inventory.InventoryDao;

/**
 * Book that keeps list of Product.
 * 
 * @author Refresh Team
 *
 */
public class ProductCatalog {

	private InventoryDao inventoryDao;


	/**
	 * Constructs Data Access Object of inventory in ProductCatalog.
	 * @param inventoryDao DAO of inventory.
	 */
	public ProductCatalog(InventoryDao inventoryDao) {
		this.inventoryDao = inventoryDao;
	}

	/**
	 * Constructs product and adds product to inventory.
	 * @param name name of product.
	 * @param salePrice price of product.
	 * @return true if product adds in inventory success ; otherwise false.
	 */
	public boolean addProduct(int cate_id,String topping_group, String name, String image , double cost,  double salePrice,String status ,String code ,String gpk,int sync) {
		Product product = new Product(cate_id ,topping_group, name, image, cost,salePrice,status,code,gpk,sync);
		int id = inventoryDao.addProduct(product);
		return id != -1;
	}

	/**
	 * Edits product.
//	 * @param product the product to be edited.
	 * @return true if product edits success ; otherwise false.
	 */
	public void editProduct(int id, int cate_id,int topping_group, String name, String image , double cost,  double salePrice,String status ,String code ,String gpk,int sync) {

		inventoryDao.editProduct(id,cate_id ,topping_group, name, image, cost,salePrice,status,code,gpk,sync);


	}

	/**
	 * Returns product from inventory finds by barcode.
	 * @param barcode barcode of product.
	 * @return product
	 */
	public Product getProductByBarcode(String barcode) {
		return inventoryDao.getProductByBarcode(barcode);
	}
	
	/**
	 * Returns product from inventory finds by id.
	 * @param id id of product.
	 * @return product
	 */
	public Product getProductById(int id) {
		return inventoryDao.getProductById(id);
	}
	
	/**
	 * Returns list of all products in inventory.
	 * @return list of all products in inventory.
	 */
	public List<Product> getAllProduct() {
		return inventoryDao.getAllProduct();
	}

	/**
	 * Returns list of product in inventory finds by name.
	 * @param name name of product.
	 * @return list of product in inventory finds by name.
	 */
	public List<Product> getProductByName(String name) {
		return inventoryDao.getProductByName(name);
	}

//	public List<Product> getPoductBySync(int name) {
//		return inventoryDao.getProductByName(name);
//	}

	/**
	 * Search product from string in inventory.
	 * @param search string for searching.
	 * @return list of product.
	 */
	public List<Product> searchProduct(String search) {
		return inventoryDao.searchProduct(search);
	}



	/**
	 * Clears ProductCatalog.
	 */
	public void clearProductCatalog() {
		inventoryDao.clearProductCatalog();
	}
	
	/**
	 * Hidden product from inventory.
	 * @param product The product to be hidden.
	 */
	public void suspendProduct(Product product) {
		inventoryDao.suspendProduct(product);
	}

	public boolean addTopping(String gpk, int groupId, String name, double price, String image, String status) {
		ToppingProduct toppingProduct = new ToppingProduct(gpk,groupId,name,price,image,status);
		int id = inventoryDao.addTopping(toppingProduct);
		return id != -1;
	}

	public void editTopping (int id,int groupId, String name,double price){

		inventoryDao.editTopping(id,groupId,name,price);
	}

	public List<ToppingProduct> getAllTopping() {
		return inventoryDao.getAllTopping();
	}

	public List<ToppingProduct> getToppingByGroupId(String id) {
		return inventoryDao.getToppingByGroupId(id);
	}


	public boolean addGroupTopping(String gpk, String name, String image, String status) {
		GroupToppingProduct groupToppingProduct = new GroupToppingProduct(gpk,name,image,status);
		int id = inventoryDao.addGroupTopping(groupToppingProduct);
		return id != -1;
	}

    public void editGroupTopping (int id, String name, String image){

        inventoryDao.editGroupTopping(id,name,image);
    }

	public List<GroupToppingProduct> getAllGroupTopping() {
		return inventoryDao.getAllGroupTopping();
	}



}
