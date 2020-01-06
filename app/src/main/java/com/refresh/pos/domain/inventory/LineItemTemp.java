package com.refresh.pos.domain.inventory;

import java.util.HashMap;
import java.util.Map;

/**
 * LineItem of Sale.
 *
 * @author Refresh Team
 *
 */
public class LineItemTemp {

	private int id;
	private String gpk;
	private int member_id;
	private int table_id;
	private int user_id;
	private Product product;
	private int quantity;
	private double unitPriceAtSale;
	private String topping;
	private String created;


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
//	public LineItemTemp(int id,String gpk, int member_id,int table_id, int user_id, Product product,int quantity, String topping, String created) {
//////
//////		this(UNDEFINED, gpk, member_id, table_id, user_id, product, quantity,product.getUnitPrice(), topping, created);
//////
//////	}

	/**
	 * Constructs a new LineItem.
	 * @param id ID of this LineItem, This value should be assigned from database.
	 * @param product product of this LineItem.
	 * @param quantity product quantity of this LineItem.
	 * @param unitPriceAtSale unit price at sale time. default is price from ProductCatalog.
	 */
	public LineItemTemp(int id,String gpk, int member_id,int table_id, int user_id, Product product,int quantity,double unitPriceAtSale, String topping, String created) {
		this.id = id;
		this.product = product;
		this.quantity = quantity;
		this.unitPriceAtSale = unitPriceAtSale;
		this.gpk = gpk;
		this.member_id = member_id;
		this.table_id = table_id;
		this.user_id = user_id;
		this.topping = topping;
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
		return unitPriceAtSale * quantity;
	}

	/**
	 * Returns the description of this LineItem in Map format.
	 * @return the description of this LineItem in Map format.
	 */
	public Map<String, String> toMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", product.getName());
		map.put("quantity", quantity + "");
		map.put("price", getTotalPriceAtSale() + "");
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
