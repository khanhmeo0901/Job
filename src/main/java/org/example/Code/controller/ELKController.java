package org.example.Code.controller;

import org.example.Code.entity.KetQua;
import org.example.Code.service.ELKService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ELKController {
    private ELKService elkService;
    public ELKController(ELKService elkService) {
        this.elkService = elkService;
    }

    @GetMapping("/getData")
    public ResponseEntity<?> getDataFromELK(@RequestParam String keyword) {
//        elkService.getDataFromELk(keyword);

         return new ResponseEntity<>(elkService.getDataFromELk(keyword),HttpStatus.OK);
    }
}
