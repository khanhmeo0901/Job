package org.example.Code.entity;

import java.util.List;
import java.util.Map;

public class KetQua {
    private Map<String, List<ListObjectKeyWord>> listData;

    private String total;
    public KetQua() {
    }

    public KetQua( Map<String, List<ListObjectKeyWord>> data, String total) {
        this.listData = data;
        this.total = total;
    }

    public Map<String, List<ListObjectKeyWord>> getListData() {
        return listData;
    }

    public void setListData(Map<String, List<ListObjectKeyWord>> listData) {
        this.listData = listData;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}
