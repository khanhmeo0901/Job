package org.example.Code.entity;

import java.util.List;
import java.util.Map;

public class ListObjectKeyWord {
    private String id;
    private List<ObjectKeyWord> list;

    public ListObjectKeyWord() {
    }

    public ListObjectKeyWord(String id, List<ObjectKeyWord> list) {
        this.id = id;
        this.list = list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ObjectKeyWord> getList() {
        return list;
    }

    public void setList(List<ObjectKeyWord> list) {
        this.list = list;
    }
}
