package org.example.Code.entity;

import java.util.List;
import java.util.Map;

public class KetQua {
    private String id;
    private Map<String, List<ObjectKeyWord>> listData;

    private String total;
    public KetQua() {
    }

    public KetQua(String id, Map<String, List<ObjectKeyWord>> data, String total) {
        this.id = id;
        this.listData = data;
        this.total = total;
    }

    public Map<String, List<ObjectKeyWord>> getListData() {
        return listData;
    }

    public void setListData(Map<String, List<ObjectKeyWord>> listData) {
        this.listData = listData;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
