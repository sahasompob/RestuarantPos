package com.refresh.pos.domain.sale;

import com.refresh.pos.domain.DateTimeStrategy;
import com.refresh.pos.domain.inventory.Inventory;
import com.refresh.pos.domain.inventory.LineItem;
import com.refresh.pos.domain.inventory.Product;
import com.refresh.pos.domain.inventory.Stock;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.techicalservices.sale.SaleDao;

/**
 * Handles all Sale processes.
 * 
 * @author Refresh Team
 *
 */
public class Register {
	private static Register instance = null;
	private static SaleDao saleDao = null;
	private static Stock stock = null;

	private Sale currentSale;

	private Register() throws NoDaoSetException {
		if (!isDaoSet()) {
			throw new NoDaoSetException();
		}
		stock = Inventory.getInstance().getStock();

	}


	/**
	 * Determines whether the DAO already set or not.
	 * @return true if the DAO already set; otherwise false.
	 */
	public static boolean isDaoSet() {
		return saleDao != null;
	}

	public static Register getInstance() throws NoDaoSetException {
		if (instance == null) instance = new Register();
		return instance;
	}

	/**
	 * Injects its sale DAO
	 * @param dao DAO of sale
	 */
	public static void setSaleDao(SaleDao dao) {
		saleDao = dao;
	}

	/**
	 * Initiates a new Sale.
	 * @param startTime time that sale created.
	 * @return Sale that created.
	 */
	public Sale initiateSale(String startTime,int tableId) {
		if (currentSale != null) {
			return currentSale;
		}
		currentSale = saleDao.initiateSale(startTime,tableId);
		return currentSale;
	}

	/**
	 * Add Product to Sale.
	 * @param product product to be added.
	 * @param quantity quantity of product that added.
	 * @return LineItem of Sale that just added.
	 */
//	public LineItem addItem(Product product, int quantity) {
////		if (currentSale == null)
////
////			initiateSale(DateTimeStrategy.getCurrentTime());
////
////		LineItem lineItem = currentSale.addLineItem(product, quantity);
////
////		if (lineItem.getId() == LineItem.UNDEFINED) {
////			int lineId = saleDao.addLineItem(currentSale.getId(), lineItem);
////			lineItem.setId(lineId);
////
////		} else {
////
////			saleDao.updateLineItem(currentSale.getId(), lineItem);
////		}
////
////		return lineItem;
//	}


	public LineItem addItem_temp(Product product, int quantity,String endTime, int tableNo, String status_service,
								 String topping_group, String topping ,String topping_name,double toppingPrice) {

		if (currentSale == null)

        initiateSale(DateTimeStrategy.getCurrentTime(),tableNo);

		LineItem lineItem = currentSale.addLineItem_Temp(product, quantity,topping_group,topping,topping_name,toppingPrice);

		if (lineItem.getId() == LineItem.UNDEFINED) {

			int lineId = saleDao.addLineItem_Temp(tableNo, lineItem, endTime,topping_group,topping,topping_name,toppingPrice);
			lineItem.setId(lineId);

		} else {

			saleDao.updateLineItem_Sale(lineItem);
//			saleDao.updateTableService(tableNo,status_service);


		}

		return lineItem;
	}

	/**
	 * Returns total price of Sale.
	 * @return total price of Sale.
	 */
	public double getTotal() {
		if (currentSale == null) return 0;
		return currentSale.getTotal();
	}

	/**
	 * End the Sale.
//	 * @param endTime time that Sale ended.
	 */
	public void endSale(String gpk,String code,String dated,int tableNo ,int user_id, int member_id, int payment_id ,String vat_type,
						int vat_percent, double totalPrice, double discount,double money_input,double money_change,
						String created,int sync ,String status) {

		if (currentSale != null) {

			saleDao.endSale(currentSale,gpk,code,dated,tableNo,user_id,member_id,payment_id,vat_type,vat_percent,totalPrice,discount,money_input,money_change,created,sync,status);
			saleDao.updateTableService(tableNo,"empty");

			for(LineItem line : currentSale.getAllLineItem()){
				stock.updateStockSum(line.getProduct().getId(), line.getQuantity());
			}
			currentSale = null;
		}




	}

	public void saveSaleDetail(int sale_id, String gpk , int product_id, int qty, double price, double cost, double total, String topping,String topping_name,double toppingPrice, int sync, String status) {

		saleDao.saveSaleDetail(sale_id, gpk, product_id, qty, price,cost,total,topping,topping_name,toppingPrice,sync,status);


	}

	public void clearTemp(int tableNo) {

		saleDao.clearTemp(tableNo);


	}


	public void holdOrderSale(String endTime,int tableNo,String status_service) {
		if (currentSale != null) {

			saleDao.holdOrderSale(currentSale, endTime,tableNo);

			saleDao.updateTableService(tableNo,status_service);

			for(LineItem line : currentSale.getAllLineItem()){
				stock.updateStockSum(line.getProduct().getId(), line.getQuantity());
			}
			currentSale = null;
		}
	}

	/**
	 * Returns the current Sale of this Register.
	 * @return the current Sale of this Register.
	 */
	public Sale getCurrentSale() {
		if (currentSale == null)

			initiateSale(DateTimeStrategy.getCurrentTime(),0);
		return currentSale;
	}

	/**
	 * Sets the current Sale of this Register.
	 * @param id of Sale to retrieve.
	 * @return true if success to load Sale from ID; otherwise false.
	 */
	public boolean setCurrentSale(int id) {
		currentSale = saleDao.getSaleById(id);
		return false;
	}

	/**
	 * Determines that if there is a Sale handling or not.
	 * @return true if there is a current Sale; otherwise false.
	 */
	public boolean hasSale(){
		if(currentSale == null)return false;
		return true;
	}

	/**
	 * Cancels the current Sale.
	 */
	public void cancleSale() {
		if (currentSale != null){
			saleDao.cancelSale(currentSale,DateTimeStrategy.getCurrentTime());
			currentSale = null;
		}
	}



	/**
	 * Edit the specific LineItem
	 * @param saleId ID of LineItem to be edited.
	 * @param lineItem LineItem to be edited.
	 * @param quantity a new quantity to set.
	 * @param priceAtSale a new priceAtSale to set.
	 */
	public void updateItem(int saleId, LineItem lineItem, int quantity, double priceAtSale) {

		lineItem.setUnitPriceAtSale(priceAtSale);
		lineItem.setQuantity(quantity);
		saleDao.updateLineItem(saleId, lineItem);
	}

	public void updateItemTemp(int id, int quantity, double priceAtSale,String topping_name,String topping, int toppingPrice) {

		saleDao.updateLineItem_Temp(id, quantity,priceAtSale,topping_name,topping,toppingPrice);
	}

	/**
	 * Removes LineItem from the current Sale.
//	 * @param lineItem lineItem to be removed.
	 */
	public void removeItem(int id) {
		saleDao.removeLineItem(id);
	}
	
}