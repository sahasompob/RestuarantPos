package com.refresh.pos.domain.Table;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TOUCH on 1/24/2019.
 */

public class Table_Detail {
    private int table_id;
    private int sequence;
    private String name;
    private String gpk;
    private String status;
    private int sync;
    private String status_service;


    public Table_Detail(int table_id,int sequence, String name,String gpk , String status, int sync, String status_service) {
        this.table_id = table_id;
        this.sequence = sequence;
        this.name = name;
        this.gpk = gpk;
        this.status = status;
        this.sync = sync;
        this.status_service = status_service;

    }


    public int getSync() {
        return sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getGpk() {
        return gpk;
    }

    public void setGpk(String gpk) {
        this.gpk = gpk;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getStatusService() {
        return status_service;
    }

    public void setStatusService(String status_service) {
        this.status_service = status_service;
    }




    /**
     * Static value for UNDEFINED ID.
     */
    public static final int UNDEFINED_ID = -1;

    public Table_Detail(int sequence,String name,String gpk, String status, int sync, String status_service) {

        this(UNDEFINED_ID,sequence, name,gpk, status,sync, status_service);
    }

    /**
     * Constructs a new Product.
     * @param id ID of the product, This value should be assigned from database.
     * @param name name of this product.
     * @param barcode barcode (any standard format) of this product.
     * @param salePrice price for using when doing sale.
     */

    /**
     * Constructs a new Product.
     * @param name name of this product.
     * @param barcode barcode (any standard format) of this product.
     * @param salePrice price for using when doing sale.
     */

    /**
     * Returns name of this product.
     * @return name of this product.
     */

    /**
     * Returns the description of this Product in Map format.
     * @return the description of this Product in Map format.
     */
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("table_id", table_id + "");
        map.put("sequence", sequence + "");
        map.put("table_name", name);
        map.put("gpk", gpk);
        map.put("image", "");
        map.put("status", status+"");
        map.put("sync", sync+"");
        map.put("status_service", status_service+"");

        return map;

    }
}
