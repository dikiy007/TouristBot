package com.example.service.impl;

import com.example.model.City;
import com.example.repository.CityRepository;
import com.example.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public City findByName(String name) {
        return this.cityRepository.findByName(name);
    }

    @Override
    public boolean deleteByName(String name) {
        return this.cityRepository.deleteByName(name) > 0L;
    }

    @Override
    public City save(City city) {
        return this.cityRepository.save(city);
    }
}
