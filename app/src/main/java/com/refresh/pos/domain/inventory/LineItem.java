package com.refresh.pos.domain.inventory;

import java.util.HashMap;
import java.util.Map;

/**
 * LineItem of Sale.
 * 
 * @author Refresh Team
 * 
 */
public class LineItem {
	private int id;
	private String gpk;
	private int member_id;
	private int table_id;
	private int user_id;
	private Product product;
	private int quantity;
	private double unitPriceAtSale;
	private String topping_group;
	private String topping;
	private String topping_name;
    private double topping_price;
    private String created;

	public String getTopping_group() {
		return topping_group;
	}

	public void setTopping_group(String topping_group) {
		this.topping_group = topping_group;
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

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public int getTable_id() {
		return table_id;
	}

	public void setTable_id(int table_id) {
		this.table_id = table_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getTopping() {
		return topping;
	}

	public void setTopping(String topping) {
		this.topping = topping;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
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
	public LineItem(int id,String gpk, int member_id,int table_id, int user_id, Product product,int quantity,String topping_group, String topping,String topping_name,double topping_price, String created) {

		this(UNDEFINED, gpk, member_id, table_id, user_id, product, quantity,product.getUnitPrice(),topping_group,topping,topping_name,topping_price, created);

	}

	/**
	 * Constructs a new LineItem.
	 * @param id ID of this LineItem, This value should be assigned from database.
	 * @param product product of this LineItem.
	 * @param quantity product quantity of this LineItem.
	 * @param unitPriceAtSale unit price at sale time. default is price from ProductCatalog.
	 */
	public LineItem(int id,String gpk, int member_id,int table_id, int user_id, Product product,int quantity,double unitPriceAtSale,String topping_group, String topping,String topping_name,double topping_price, String created) {
		this.id = id;
		this.product = product;
		this.quantity = quantity;
		this.unitPriceAtSale = unitPriceAtSale;
		this.gpk = gpk;
		this.member_id = member_id;
		this.table_id = table_id;
		this.user_id = user_id;
		this.topping_group = topping_group;
		this.topping = topping;
		this.topping_name = topping_name;
		this.topping_price = topping_price;
		this.created = created;
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
		map.put("name", product.getName());
		map.put("quantity", quantity + "");
		map.put("price", getTotalPriceAtSale() +"");
		map.put("topping_group",topping_group+"");
        map.put("topping",topping);
		map.put("topping_name",topping_name);
        map.put("topping_price", topping_price + "");
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
