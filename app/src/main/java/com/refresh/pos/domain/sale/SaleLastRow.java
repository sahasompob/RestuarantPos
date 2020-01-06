package com.refresh.pos.domain.sale;

import com.refresh.pos.domain.inventory.LineItem;
import com.refresh.pos.domain.inventory.LineItemSale;

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
public class SaleLastRow {

	private int id;
	private String gpk;
	private String code;
	private String dated;
	private int table_id;
	private int user_id;
	private int member_id;
	private int payment_id;
	private String vat_type;
	private int vat_percent;
	private int vat_value;
	private double total;
	private double discount;
	private double money_input;
	private double money_change;
	private String created;
	private int sync;
	private String status;

	public SaleLastRow(int id, String gpk, String code, String dated, int table_id, int user_id,
					   int member_id, int payment_id, String vat_type, int vat_percent,
					   int vat_value, double total, double discount, double money_input,
					   double money_change, String created, int sync, String status) {
		this.id = id;
		this.gpk = gpk;
		this.code = code;
		this.dated = dated;
		this.table_id = table_id;
		this.user_id = user_id;
		this.member_id = member_id;
		this.payment_id = payment_id;
		this.vat_type = vat_type;
		this.vat_percent = vat_percent;
		this.vat_value = vat_value;
		this.total = total;
		this.discount = discount;
		this.money_input = money_input;
		this.money_change = money_change;
		this.created = created;
		this.sync = sync;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGpk() {
		return gpk;
	}

	public void setGpk(String gpk) {
		this.gpk = gpk;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDated() {
		return dated;
	}

	public void setDated(String dated) {
		this.dated = dated;
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

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public int getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(int payment_id) {
		this.payment_id = payment_id;
	}

	public String getVat_type() {
		return vat_type;
	}

	public void setVat_type(String vat_type) {
		this.vat_type = vat_type;
	}

	public int getVat_percent() {
		return vat_percent;
	}

	public void setVat_percent(int vat_percent) {
		this.vat_percent = vat_percent;
	}

	public int getVat_value() {
		return vat_value;
	}

	public void setVat_value(int vat_value) {
		this.vat_value = vat_value;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getMoney_input() {
		return money_input;
	}

	public void setMoney_input(double money_input) {
		this.money_input = money_input;
	}

	public double getMoney_change() {
		return money_change;
	}

	public void setMoney_change(double money_change) {
		this.money_change = money_change;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public int getSync() {
		return sync;
	}

	public void setSync(int sync) {
		this.sync = sync;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}




	public Map<String, String> toMap() {	
		Map<String, String> map = new HashMap<String, String>();
		map.put("id",id +"");
		map.put("gpk", gpk);
		map.put("code", code);
		map.put("dated", dated);
		map.put("table_id",table_id + "");
		map.put("user_id", user_id + "");
		map.put("member_id", member_id + "");
		map.put("payment_id", payment_id + "");
		map.put("vat_type", vat_type);
		map.put("vat_percent", vat_percent + "");
		map.put("vat_value", vat_value + "");
		map.put("total", total + "");
		map.put("discount", discount + "");
		map.put("money_input", money_input + "");
		map.put("money_change", money_change + "");
		map.put("created", created);
		map.put("sync", sync +"");
		map.put("status", status);

		return map;

	}

	/**
	 * Removes LineItem from Sale.
	 * @param lineItem lineItem to be removed.
	 */


}