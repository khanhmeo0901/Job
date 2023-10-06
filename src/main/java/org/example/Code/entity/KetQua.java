package org.example.Code.entity;

import java.util.List;
import java.util.Map;

public class KetQua {
    private Map<String, List<String>> data;

    public KetQua() {
    }

    public KetQua(Map<String, List<String>> data) {
        this.data = data;
    }

    public Map<String, List<String>> getData() {
        return data;
    }

    public void setData(Map<String, List<String>> data) {
        this.data = data;
    }
}
