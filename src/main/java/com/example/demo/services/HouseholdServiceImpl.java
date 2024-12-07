package com.example.demo.services;

import com.example.demo.entities.Household;
import com.example.demo.exceptions.BadDataException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repositories.HouseholdRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HouseholdServiceImpl implements HouseholdService {

    private HouseholdRepository householdRepository;

    @Override
    public Household getHouseholdByEircode(String eircode) {
        return householdRepository.findById(eircode)
                .orElseThrow(() -> new NotFoundException("Household not found with eircode: " + eircode));
    }

    @Override
    public Household getHouseholdWithPetsByEircode(String eircode) {
        Household household = householdRepository.findPetByEircode(eircode);
        if (household == null) {
            throw new NotFoundException("Household not found with eircode: " + eircode);
        }
        return household;
    }

    @Override
    public List<Household> getAllHouseholdsNoPets() {
        return householdRepository.findHouseholdsWithNoPets();
    }

    @Override
    public List<Household> getAllHouseholds() {
        return householdRepository.findAll();
    }

    @Override
    public void deleteHouseholdByEircode(String eircode) {
        if (!householdRepository.existsById(eircode)) {
            throw new NotFoundException("Household not found with eircode: " + eircode);
        }
        householdRepository.deleteById(eircode);
    }

    @Override
    public Household createHousehold(Household household) {
        if (household.getEircode() == null || household.getEircode().isBlank()) {
            throw new BadDataException("Eircode cant be null or blank");
        }
        return householdRepository.save(household);
    }
}
