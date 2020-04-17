package com.example.controller;

import com.example.model.City;
import com.example.service.CityService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/city")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PutMapping("/add")
    public City add(@RequestBody() City city) {
        return this.cityService.save(city);
    }

    @DeleteMapping("/delete")
    public boolean delete(@RequestParam String name) {
        return this.cityService.deleteByName(name);
    }
}
