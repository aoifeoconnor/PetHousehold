package com.example.demo.services;

import com.example.demo.entities.Household;

import java.util.List;

public interface HouseholdService {
    Household getHouseholdByEircode(String eircode);
    Household getHouseholdWithPetsByEircode(String eircode);
    List<Household> getAllHouseholdsNoPets();
    List<Household> getAllHouseholds();
    void deleteHouseholdByEircode(String eircode);
    Household createHousehold(Household household);
}
