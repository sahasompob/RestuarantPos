package com.refresh.pos.techicalservices;

/**
 * Enum for name of tables in database.
 * 
 * @author Refresh Team
 *
 */
public enum DatabaseContents {
	
	DATABASE("com.refresh.db1"),
	TABLE_PRODUCT_CATALOG("product_catalog"),
	TABLE_ORG("org"),
	TABLE_STOCK("stock"),
	TABLE_SALE("sale"),
	TABLE_SALE_MOCK("sale_mock"),
	TABLE_SALE_LINEITEM("sale_lineitem"),
	TABLE_SALE_LINEITEM_TEMP("sale_lineitem_temp"),
	TABLE_STOCK_SUM("stock_sum"),
	LANGUAGE("language"),
	TABLE_Category_Product("category_product"),
	TABLE_TABLE_DETAIL("table_detail"),
	TABLE_TOPPING("topping"),
	TABLE_TOPPING_GROUP("topping_group"),
	TABLE_BILL_RUNNING("bill_running"),
	TABLE_Users("user");


	private String name;
	
	/**
	 * Constructs DatabaseContents.
	 * @param name name of this content for using in database.
	 */
	private DatabaseContents(String name) {
		this.name = name;
	}
	
	@Override 
	public String toString() {
		return name;
	}
}
