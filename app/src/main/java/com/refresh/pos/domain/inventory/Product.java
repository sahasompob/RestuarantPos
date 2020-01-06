package com.refresh.pos.domain.inventory;

import java.util.HashMap;
import java.util.Map;

/**
 * Product or item represents the real product in store.
 * 
 * @author Refresh Team
 *
 */
public class Product {

	private int id;
	private int cate_id;
	private String topping_group;
	private String image;
	private String name;
	private double cost;
	private double unitPrice;
	private String code;
	private String gpk;
	private String status;
	private int syncProduct;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCate_id() {
		return cate_id;
	}

	public void setCate_id(int cate_id) {
		this.cate_id = cate_id;
	}

	public String getTopping_group() {
		return topping_group;
	}

	public void setTopping_group(String topping_group) {
		this.topping_group = topping_group;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getGpk() {
		return gpk;
	}

	public void setGpk(String gpk) {
		this.gpk = gpk;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getSyncProduct() {
		return syncProduct;
	}

	public void setSyncProduct(int syncProduct) {
		this.syncProduct = syncProduct;
	}

	public static final int UNDEFINED_ID = -1;


	/**
	 * Constructs a new Product.
	 * @param id ID of the product, This value should be assigned from database.
	 * @param name name of this product.
	 * @param code barcode (any standard format) of this product.
	 * @param salePrice price for using when doing sale.
	 */
	public Product(int id,int cate_id,String topping_group, String name, String image , double cost,  double salePrice,String status ,String code ,String gpk,int syncProduct) {
		this.id = id;
		this.cate_id = cate_id;
		this.topping_group = topping_group;
		this.code = code;
		this.name = name;
		this.cost = cost;
		this.unitPrice = salePrice;
		this.image = image;
		this.gpk = gpk;
		this.status = status;
		this.syncProduct = syncProduct;
	}


	/**
	 * Constructs a new Product.
	 * @param name name of this product.
	 * @param salePrice price for using when doing sale.
	 * @param syncProduct
	 */
	public Product(int cate_id,String topping_group, String name, String image , double cost, double salePrice, String status , String code , String gpk, int syncProduct) {
        this(UNDEFINED_ID, cate_id,topping_group,name, image, cost,salePrice,status,code,gpk,syncProduct);
    }


    /**
	 * Returns the description of this Product in Map format. 
	 * @return the description of this Product in Map format.
	 */
	public Map<String, String> toMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id + "");
		map.put("cate_id", cate_id +"");
		map.put("topping_group", topping_group +"");
		map.put("name", name);
		map.put("image", image);
		map.put("cost", cost + "");
		map.put("unitPrice", unitPrice + "");
		map.put("status", status);
		map.put("code", code);
		map.put("gpk", gpk);
		map.put("syncProduct", syncProduct +"");
		return map;
		
	}
	
}
