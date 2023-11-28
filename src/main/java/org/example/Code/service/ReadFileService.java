package org.example.Code.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface ReadFileService {
    public  void filePath(String folderPath, String index);

    public void pushDataFromDB();
    public void test(MultipartFile file, String index);

    public void syncPushFile(String folderPath);
}
