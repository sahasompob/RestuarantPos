package com.refresh.pos.techicalservices.sale;

import java.util.Calendar;
import java.util.List;

import com.refresh.pos.domain.inventory.LineItem;
import com.refresh.pos.domain.inventory.LineItemSale;
import com.refresh.pos.domain.inventory.LineItemTemp;
import com.refresh.pos.domain.sale.Sale;
import com.refresh.pos.domain.sale.SaleLastRow;
import com.refresh.pos.domain.sale.SaleReport;

/**
 * DAO for Sale process.
 * 
 * @author Refresh Team
 *
 */
public interface SaleDao {

	/**
	 * Initiates a new Sale.
	 * @param startTime time that Sale initiated.
	 * @return Sale that initiated
	 */
	Sale initiateSale(String startTime,int tableNo);

	/**
	 * End Sale
	 * @param sale Sale to be ended.
	 * @param endTime time that Sale ended.
	 */
	void endSaleMock(Sale sale, String endTime,int tableNo,double totalPrice);
//	void endSale(Sale sale, String endTime,int tableNo,double totalPrice);
	void endSale(Sale sale,String gpk,String code,String dated,int tableNo ,int user_id, int member_id, int payment_id ,String vat_type,
				 int vat_percent, double totalPrice, double discount,double money_input,double money_change,
				 String created,int sync ,String status);



	void saveSaleDetail(int sale_id, String gpk , int product_id, int qty, double price, double cost, double total, String topping,String topping_name,double toppingPrice, int sync, String status);


	void holdOrderSale(Sale sale, String endTime, int tableNo);

	/**
	 * Add LineItem to Sale.
	 * @param saleId ID of the Sale to add LineItem.
	 * @param lineItem LineItem to be added.
	 * @return ID of LineItem that just added.
	 */
	int addLineItem(int saleId, LineItem lineItem);

	int addLineItem_Temp(int tableNo, LineItem lineItem,String endTime,String topping_group, String topping,String topping_name,double toppingPrice);

    void updateTableService(int tableNo, String status_service);


	List<SaleLastRow> getLastRow();

	/**
	 * Returns all sale in the records.
	 * @return all sale in the records.
	 */
	List<SaleLastRow> getAllSale();

	/**
	 * Returns the Sale with specific ID.
	 * @param id ID of specific Sale.
	 * @return the Sale with specific ID.
	 */
	Sale getSaleById(int id);
	SaleReport getSaleReportById(int id);
	Sale getByTableId(int id);

	List<LineItemTemp> getLineItemTemp(int tableNo);

	/**
	 * Clear all records in SaleLedger.	
	 */
	void clearSaleLedger();

	LineItemTemp getByTableIdTest(int id);

	/**
	 * Returns list of LineItem that belong to Sale with specific Sale ID.
//	 * @param saleId ID of sale.
	 * @return list of LineItem that belong to Sale with specific Sale ID.
	 */
	List<LineItem> getLineItem(int tableNo);
    List<LineItemSale> getLineItemProduct(int saleId);
	List<LineItem> getAllLinebyTable(int tableNo);

	/**
	 * Updates the data of specific LineItem.
	 * @param saleId ID of Sale that this LineItem belong to.
	 * @param lineItem to be updated.
	 */
	void updateLineItem(int saleId, LineItem lineItem);
	void updateLineItem_Sale(LineItem lineItem);

	void updateLineItem_Temp(int id, int quantity, double priceAtSale,String topping_name,String topping, int toppingPrice);


	/**
	 * Returns list of Sale with scope of time. 
	 * @param start start bound of scope.
	 * @param end end bound of scope.
	 * @return list of Sale with scope of time.
	 */
	List<SaleLastRow> getAllSaleDuring(Calendar start, Calendar end);
	
	/**
	 * Cancel the Sale.
	 * @param sale Sale to be cancel.
	 * @param endTime time that cancelled.
	 */
	void cancelSale(Sale sale,String endTime);

	void clearTemp(int tableId);

	/**
	 * Removes LineItem.
	 * @param id of LineItem to be removed.
	 */
	void removeLineItem(int id);


}
