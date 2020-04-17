package com.example.service;

import com.example.model.City;

public interface CityService {
    City findByName(String name);
    boolean deleteByName(String name);
    City save(City city);
}
