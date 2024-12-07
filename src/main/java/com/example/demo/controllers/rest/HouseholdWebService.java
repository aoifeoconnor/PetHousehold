package com.example.demo.controllers.rest;

import com.example.demo.dtos.NewHousehold;
import com.example.demo.entities.Household;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.services.HouseholdService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;


@RestController
@AllArgsConstructor
@RequestMapping("/myapi/households")

public class HouseholdWebService {
    private final HouseholdService householdService;

    @GetMapping({"", "/"})
    public List<Household> getAllHouseholds() {
        return householdService.getAllHouseholds();
    }

    @GetMapping("/no-pets")
    public List<Household> getHouseholdsWithNoPets() {
        return householdService.getAllHouseholdsNoPets();
    }

    @GetMapping("/{eircode}")
    public Household getHouseholdByEircode(@PathVariable String eircode) throws NotFoundException {
        return householdService.getHouseholdByEircode(eircode);
    }

    @DeleteMapping("/{eircode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHouseholdByEircode(@PathVariable String eircode) {
        householdService.deleteHouseholdByEircode(eircode);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Household addHousehold(@RequestBody @Valid NewHousehold newHousehold) {
        Household household = new Household(
                newHousehold.eircode(),
                newHousehold.numberOfOccupants(),
                newHousehold.maxNumberOfOccupants(),
                newHousehold.ownerOccupied()
        );
        return householdService.createHousehold(household);
    }
}
