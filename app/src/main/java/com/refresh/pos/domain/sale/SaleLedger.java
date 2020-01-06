package com.refresh.pos.domain.sale;

import java.util.Calendar;
import java.util.List;

import com.refresh.pos.domain.inventory.LineItemTemp;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.techicalservices.sale.SaleDao;

/**
 * Book that keeps sale record.
 * 
 * @author Refresh Team
 *
 */
public class SaleLedger {
	
	private static SaleLedger instance = null;
	private static SaleDao saleDao = null;
	
	private SaleLedger() throws NoDaoSetException {
		if (!isDaoSet()) {
			throw new NoDaoSetException();
		}
	}
	
	/**
	 * Determines whether the DAO already set or not.
	 * @return true if the DAO already set; otherwise false.
	 */
	public static boolean isDaoSet() {
		return saleDao != null;
	}
	
	public static SaleLedger getInstance() throws NoDaoSetException {
		if (instance == null) instance = new SaleLedger();
		return instance;
	}

	/**
	 * Sets the database connector.
	 * @param dao Data Access Object of Sale.
	 */
	public static void setSaleDao(SaleDao dao) {
		saleDao = dao;	
	}
	
	/**
	 * Returns all sale in the records.
	 * @return all sale in the records.
	 */
	public List<SaleLastRow> getAllSale() {
		return saleDao.getAllSale();
	}

	public List<SaleLastRow> getLastRow() {
		return saleDao.getLastRow();
	}

//	public List<Sale> getLastSaleId() {
//		return saleDao.getLastSaleId();
//	}
	
	/**
	 * Returns the Sale with specific ID.
	 * @param id ID of specific Sale.
	 * @return the Sale with specific ID.
	 */

    public SaleReport getSaleReportById(int id) {

        return saleDao.getSaleReportById(id);
    }

	public Sale getSaleById(int id) {

		return saleDao.getSaleById(id);
	}

	public Sale getByTableId(int id) {

		return saleDao.getByTableId(id);
	}

	public LineItemTemp getByTableIdTest(int id) {

		return saleDao.getByTableIdTest(id);
	}

	/**
	 * Clear all records in SaleLedger.	
	 */
	public void clearSaleLedger() {
		saleDao.clearSaleLedger();
	}

	/**
	 * Returns list of Sale with scope of time. 
	 * @param start start bound of scope.
	 * @param end end bound of scope.
	 * @return list of Sale with scope of time.
	 */
	public List<SaleLastRow> getAllSaleDuring(Calendar start, Calendar end) {
		return saleDao.getAllSaleDuring(start, end);
	}
}
