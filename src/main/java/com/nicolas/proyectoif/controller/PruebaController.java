package com.nicolas.proyectoif.controller;

import com.nicolas.proyectoif.entity.PruebaTest;
import com.nicolas.proyectoif.repository.PruebaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prueba")
public class PruebaController {

    @Autowired
    private PruebaRepository pruebaRepository;

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewUser (@RequestParam Integer id) {
        PruebaTest pruebaTest = new PruebaTest();
        pruebaTest.setNum(id);
        pruebaRepository.save(pruebaTest);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<PruebaTest> getAllUsers() {
        // This returns a JSON or XML with the users
        return pruebaRepository.findAll();
    }
}
