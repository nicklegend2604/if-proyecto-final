package com.nicolas.proyectoif.controller;

import com.nicolas.proyectoif.entity.PruebaTest;
import com.nicolas.proyectoif.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping(path="/file-generation")
    public @ResponseBody String generateToFile (@RequestBody Map<String, Object> params) throws IOException {
        Integer ammount = (Integer) params.get("ammount");
        Boolean padded = (Boolean) params.get("padded");
        Integer month = (Integer) params.get("month");
        service.generateRandomToFile(ammount, padded, month);
        return "Saved";
    }
}
