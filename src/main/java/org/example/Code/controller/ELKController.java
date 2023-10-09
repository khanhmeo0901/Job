package org.example.Code.controller;

import org.example.Code.contanst.ApiCode;
import org.example.Code.dto.ProjectResponse;
import org.example.Code.entity.KetQua;
import org.example.Code.service.ELKService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ELKController {
    private ELKService elkService;

    public ELKController(ELKService elkService) {
        this.elkService = elkService;
    }

    @GetMapping("/getData")
    public ProjectResponse<?> getDataFromELK(@RequestParam String keyword, @RequestParam(defaultValue = "1" ) int from
            ,@RequestParam(defaultValue = "10") int size) {
        return new ProjectResponse<>(ApiCode.SUCCESS,elkService.getDataFromELk(keyword,from,size));
    }
}
