package org.example.Code.service;

import org.example.Code.entity.KetQua;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ELKService {
    //    public KetQua getDataFromELk(String keyword, int from, int size, List<String> listOption);
    public KetQua getDataFromELk(String keyword, int from, int size, boolean and, boolean not, boolean or);

    public void downloadFileFromELK(String fileName);

    public void testELK(String folder);
}
