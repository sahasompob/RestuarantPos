package com.refresh.pos.domain.register;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TOUCH on 1/24/2019.
 */

public class UserDetail {
    private int cate_product_id;
    private String pk;
    private int sequence;
    private int parent_id;
    private String name;
    private int sync;
    private String image;
    private String status;
    private String type;


    public UserDetail(int cate_product_id, String pk, int sequence, int parent_id, String name, int syn, String image, String status, String type) {
        this.cate_product_id = cate_product_id;
        this.pk = pk;
        this.sequence = sequence;
        this.parent_id = parent_id;
        this.name = name;
        this.sync = syn;
        this.image = image;
        this.status = status;
        this.type = type;
    }

    /**
     * Static value for UNDEFINED ID.
     */
    public static final int UNDEFINED_ID = -1;

    public UserDetail(String pk, int sequence, int parent_id, String name, int syn, String image, String status, String type) {
        this(UNDEFINED_ID, pk, sequence, parent_id, name, syn, image, status, type);
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
        map.put("cate_product_id", cate_product_id + "");
        map.put("pk", name);
        map.put("sequence", sequence + "");
        map.put("paren_id", parent_id+"");
        map.put("name", name);
        map.put("sync", sync+"");
        map.put("image", image);
        map.put("status", status);
        map.put("type", type);
        return map;

    }
}
