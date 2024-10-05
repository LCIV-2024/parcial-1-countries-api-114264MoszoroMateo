package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.CountryToSave;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/countries")
public class CountryController {

    private final CountryService countryService;

    @GetMapping()
    public ResponseEntity<List<CountryDTO>> getCountry(@RequestParam(required = false) String code,@RequestParam(required = false) String name ){
        List<CountryDTO> response = countryService.getCountry(code,name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{continent}/continent")
    public ResponseEntity<List<CountryDTO>> getCountryByContinent(@PathVariable String continent){
        List<CountryDTO> response = countryService.getCountriesByContinent(continent);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{language}/language")
    public ResponseEntity<List<CountryDTO>> getCountriesByLanguage(@PathVariable String language){
        List<CountryDTO> response = countryService.getCountryByLanguage(language);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/most-borders")
    public ResponseEntity<CountryDTO> getCountryMostBorder(){
        CountryDTO response = countryService.getCountryMostBorder();
        return ResponseEntity.ok(response);
    }
    @PostMapping()
    public ResponseEntity<List<CountryDTO>> postCountries(@RequestBody CountryToSave amountOfCountryToSave){
        List<CountryDTO> response = countryService.amountOfCountrySave(amountOfCountryToSave.getAmountOfCountryToSave());
        return ResponseEntity.ok(response);
    }

}