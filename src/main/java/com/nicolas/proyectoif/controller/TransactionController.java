package com.nicolas.proyectoif.controller;

import com.nicolas.proyectoif.entity.Transaction;
import com.nicolas.proyectoif.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping(path = "/file-generation")
    public @ResponseBody
    String generateToFile(@RequestBody Map<String, Object> params) throws IOException {
        Integer ammount = (Integer) params.get("ammount");
        Boolean padded = (Boolean) params.get("padded");
        Integer month = (Integer) params.get("month");
        service.generateRandomToFile(ammount, padded, month);
        return "File generated successfully";
    }

    @PostMapping(path = "/load-files")
    public @ResponseBody
    String loadFiles() throws IOException, ParseException {
        service.loadFiles();
        return "Load file successfully";
    }


    @GetMapping(path = "/most-recent-transactions")
    public @ResponseBody
    List<Transaction> mostRecentTransactions() {
        return service.findMostRecentTransactions();
    }

    @GetMapping(path = "/year-balance")
    public @ResponseBody
    List<Object> getYearBalance() {
        return service.getYearBalance();
    }

    @GetMapping(path = "/real-balance")
    public @ResponseBody
    List<Object> getRealBalance() {
        return service.getRealBalance();
    }

    @GetMapping(path = "/month-last-tx-balance")
    public @ResponseBody
    List<Object> findLastTxMonthBalance() {
        return service.findLastTxMonthBalance();
    }

    @GetMapping(path = "/month-balance")
    public @ResponseBody
    List<Object> findMonthBalance() {
        return service.findMonthBalance();
    }

    @GetMapping(path = "/tx-year-count")
    public @ResponseBody
    List<Object> getTxYearCount() {
        return service.getTxYearCount();
    }

    @GetMapping(path = "/tx-month-count")
    public @ResponseBody
    List<Object> getTxMonthCount() {
        return service.getTxMonthCount();
    }
}
