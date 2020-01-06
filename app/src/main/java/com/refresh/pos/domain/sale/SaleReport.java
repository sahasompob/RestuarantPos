package com.refresh.pos.domain.sale;

import com.refresh.pos.domain.inventory.LineItem;
import com.refresh.pos.domain.inventory.LineItemSale;
import com.refresh.pos.domain.inventory.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Sale represents sale operation.
 * 
 * @author Refresh Team
 *
 */
public class SaleReport {

	private final int id;
    private String status;
    private String payment;
    private int table_id;
    private double total;
	private String startTime;
	private String endTime;
	private String order;
	private List<LineItemSale> items;


    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public SaleReport(int id, String status,String payment,int table_id,double total, String startTime, String endTime,String order) {
		this(id, status, payment, table_id,total,startTime,endTime,order, new ArrayList<LineItemSale>());
	}

	/**
	 * Constructs a new Sale.
	 * @param id ID of this Sale.
	 * @param startTime start time of this Sale.
	 * @param endTime end time of this Sale.
	 * @param status status of this Sale.
	 * @param items list of LineItem in this Sale.
	 */
	public SaleReport(int id, String status,String payment,int table_id,double total, String startTime, String endTime,String order, List<LineItemSale> items) {
		this.id = id;
        this.status = status;
        this.payment = payment;
        this.table_id = table_id;
        this.total = total;
        this.startTime = startTime;
		this.endTime = endTime;
		this.order = order;
		this.items = items;
	}



	/**
	 * Returns list of LineItem in this Sale.
	 * @return list of LineItem in this Sale.
	 */
	public List<LineItemSale> getAllLineItem(){
		return items;
	}





	/**
	 * Add Product to Sale.
//	 * @param product product to be added.
//	 * @param quantity quantity of product that added.
	 * @return LineItem of Sale that just added.
	 */
//	public LineItemSale addLineItem(Product product, int quantity,String topping,String topping_name,double toppingPrice) {
//
//		for (LineItemSale lineItem : items) {
//			if (lineItem.getProduct().getId() == product.getId()) {
//				lineItem.addQuantity(quantity);
//				return lineItem;
//			}
//		}
//
//		LineItemSale lineItem = new LineItemSale(1,"gpk1",1,2,3,product,quantity,topping,topping_name,toppingPrice,"todaya");
//		items.add(lineItem);
//		return lineItem;
//	}

//	public LineItem addLineItem_Temp(Product product, int quantity,String topping,String topping_name,double toppingPrice) {
//
//		for (LineItemSale lineItem : items) {
//			if (lineItem.getProduct().getId() == product.getId()) {
//				lineItem.addQuantity(quantity);
//				return lineItem;
//			}
//		}
//
//		LineItemSale lineItem = new LineItem(1,"gpk1",1,2,3,product,quantity,topping,topping_name,toppingPrice,"todaya");
//		items.add(lineItem);
//		return lineItem;
//	}
	
	public int size() {
		return items.size();
	}
	
	/**
	 * Returns a LineItem with specific index.
	 * @param index of specific LineItem.
	 * @return a LineItem with specific index.
	 */
	public LineItemSale getLineItemAt(int index) {
		if (index >= 0 && index < items.size())
			return items.get(index);
		return null;
	}

	/**
	 * Returns the total price of this Sale.
	 * @return the total price of this Sale.
	 */
	public double getTotal() {
		double amount = 0;
		for(LineItemSale lineItem : items) {
			amount += lineItem.getTotalPriceAtSale();
		}
		return amount;
	}

	public int getId() {
		return id;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public String getStatus() {
		return status;
	}
	
	/**
	 * Returns the total quantity of this Sale.
	 * @return the total quantity of this Sale.
	 */
	public int getOrders() {
		int orderCount = 0;
		for (LineItemSale lineItem : items) {
			orderCount += lineItem.getQuantity();
		}
		return orderCount;
	}

	/**
	 * Returns the description of this Sale in Map format. 
	 * @return the description of this Sale in Map format.
	 */
	public Map<String, String> toMap() {	
		Map<String, String> map = new HashMap<String, String>();
		map.put("id",id + "");
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("status", getStatus());
		map.put("total", getTotal() + "");
		map.put("orders", getOrders() + "");
		
		return map;
	}

	/**
	 * Removes LineItem from Sale.
	 * @param lineItem lineItem to be removed.
	 */
	public void removeItem(LineItem lineItem) {
		items.remove(lineItem);
	}

}