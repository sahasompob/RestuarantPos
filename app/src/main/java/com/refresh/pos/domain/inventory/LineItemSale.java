package com.refresh.pos.domain.inventory;

import java.util.HashMap;
import java.util.Map;

/**
 * LineItem of Sale.
 * 
 * @author Refresh Team
 * 
 */
public class LineItemSale {
	private int id;
	private String gpk;
	private int sale_id;
	private Product product;
	private Product product_id;
	private int quantity;
	private double unitPriceAtSale;
	private double unitCostAtSale;
	private double total;
	private String topping;
	private String topping_name;
	private double topping_price;
	private String sync;
    private String status;

	public Product getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Product product_id) {
		this.product_id = product_id;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getSale_id() {
		return sale_id;
	}

	public void setSale_id(int sale_id) {
		this.sale_id = sale_id;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public double getUnitPriceAtSale() {
		return unitPriceAtSale;
	}

	public double getUnitCostAtSale() {
		return unitCostAtSale;
	}

	public void setUnitCostAtSale(double unitCostAtSale) {
		this.unitCostAtSale = unitCostAtSale;
	}


	public String getSync() {
		return sync;
	}

	public void setSync(String sync) {
		this.sync = sync;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTopping_name() {
		return topping_name;
	}

	public void setTopping_name(String topping_name) {
		this.topping_name = topping_name;
	}

	public double getTopping_price() {
		return topping_price;
	}

	public void setTopping_price(double topping_price) {
		this.topping_price = topping_price;
	}



	public String getGpk() {
		return gpk;
	}

	public void setGpk(String gpk) {
		this.gpk = gpk;
	}



	public String getTopping() {
		return topping;
	}

	public void setTopping(String topping) {
		this.topping = topping;
	}


	/**
	 * Static value for UNDEFINED ID.
	 */
	public static final int UNDEFINED = -1;

	/**
	 * Constructs a new LineItem.
	 * @param product product of this LineItem.
	 * @param quantity product quantity of this LineItem.
	 */

//	public LineItemSale (int id, String gpk, int sale_id, Product product, int quantity,double total
//			,String topping, String topping_name, double topping_price, String sync, String status) {
//
//		this(UNDEFINED, gpk, sale_id, product, quantity, product.getUnitPrice(), product.getCost(),total,topping,topping_name,topping_price, sync,status);
//
//	}

	/**
	 * Constructs a new LineItem.
	 * @param id ID of this LineItem, This value should be assigned from database.
	 * @param product product of this LineItem.
	 * @param quantity product quantity of this LineItem.
	 * @param unitPriceAtSale unit price at sale time. default is price from ProductCatalog.
	 */
	public LineItemSale(int id, String gpk, int sale_id, Product product, int quantity, double unitPriceAtSale, double unitCostAtSale
			,double total, String topping, String topping_name, double topping_price, String sync, String status) {
		this.id = id;
		this.gpk = gpk;
		this.sale_id = sale_id;
		this.product = product;
		this.quantity = quantity;
		this.unitPriceAtSale = unitPriceAtSale;
		this.unitCostAtSale = unitCostAtSale;
		this.total = total;
		this.topping = topping;
		this.topping_name = topping_name;
		this.topping_price = topping_price;
		this.sync = sync;
		this.status = status;
	}

	/**
	 * Returns product in this LineItem.
	 * @return product in this LineItem.
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * Return quantity of product in this LineItem.
	 * @return quantity of product in this LineItem.
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Sets quantity of product in this LineItem.
	 * @param quantity quantity of product in this LineItem.
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Adds quantity of product in this LineItem.
	 * @param amount amount for add in quantity.
	 */
	public void addQuantity(int amount) {
		this.quantity += amount;
	}

	/**
	 * Returns total price of this LineItem.
	 * @return total price of this LineItem.
	 */
	public double getTotalPriceAtSale() {
		return unitPriceAtSale * quantity + getTotalTopping() ;
	}

	public double getTotalTopping() {
		return topping_price * quantity;
	}

	/**
	 * Returns the description of this LineItem in Map format.
	 * @return the description of this LineItem in Map format.
	 */
	public Map<String, String> toMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id+"");
		map.put("gpk", gpk);
		map.put("sale_id", sale_id+"");
		map.put("name", product.getName());
		map.put("quantity", quantity + "");
		map.put("price", getTotalPriceAtSale() +"");
		map.put("cost", unitCostAtSale+"");
		map.put("topping",topping);
		map.put("topping_name",topping_name);
        map.put("topping_price", topping_price + "");
		map.put("sync",sync);
		map.put("status",status);
		return map;

	}

	public Map<String, String> toLineMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id+"");
		map.put("gpk", gpk);
		map.put("sale_id", sale_id+"");
		map.put("product_id", product.getId()+"");
		map.put("quantity", quantity + "");
		map.put("price", getTotalPriceAtSale() +"");
		map.put("cost", unitCostAtSale+"");
		map.put("topping",topping);
		map.put("topping_name",topping_name);
		map.put("topping_price", topping_price + "");
		map.put("sync",sync);
		map.put("status",status);
		return map;

	}

	/**
	 * Returns id of this LineItem.
	 * @return id of this LineItem.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets id of this LineItem.
	 * @param id of this LineItem.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Sets price product of this LineItem.
	 * @param unitPriceAtSale price product of this LineItem.
	 */
	public void setUnitPriceAtSale(double unitPriceAtSale) {
		this.unitPriceAtSale = unitPriceAtSale;
	}

	/**
	 * Returns price product of this LineItem.
	 * @return unitPriceAtSale price product of this LineItem.
	 */
	public Double getPriceAtSale() {
		return unitPriceAtSale;
	}

	/**
	 * Determines whether two objects are equal or not.
	 * @return true if Object is a LineItem with same ID ; otherwise false.
	 */
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!(object instanceof LineItemTemp))
			return false;
		LineItemTemp lineItem = (LineItemTemp) object;
		return lineItem.getId() == this.getId();
	}
}
