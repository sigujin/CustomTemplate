package com.vito.base.jsonbean;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pc on 2016/7/21.
 */
public class VitoListJsonTempBean<T> implements Serializable {
    @JsonProperty("total")
    private int total;

    @JsonProperty("rows")
    private ArrayList<T> rows;

    public int getTotal() {
        return total;
    }

    public ArrayList<T> getRows() {
        return rows;
    }
}
