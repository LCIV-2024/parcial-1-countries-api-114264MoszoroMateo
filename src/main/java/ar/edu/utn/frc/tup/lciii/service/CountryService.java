package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.exception.CountryNotFoundException;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

        private final CountryRepository countryRepository;

        private final RestTemplate restTemplate;
        private final ModelMapper modelMapper;

        public List<Country> getAllCountries() {
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                return response.stream().map(this::mapToCountry).collect(Collectors.toList());
        }

        public List<CountryDTO> getCountry(String code, String name){
                List<CountryDTO> countriesDTOS = new ArrayList<>();
                List<Country> allCountries = getAllCountries();
                if (name==null && code ==null){
                        for (Country c : allCountries){
                                CountryDTO nuevo = mapToDTO(c);
                                countriesDTOS.add(nuevo);
                        }
                }else if (name==null){
                        for (Country c : allCountries){
                                if (c.getCode().contains(code)){
                                        CountryDTO nuevo = mapToDTO(c);
                                        countriesDTOS.add(nuevo);
                                }
                        }
                } else if (code==null){
                        for (Country c : allCountries){
                                if (c.getName().contains(name)){
                                        CountryDTO nuevo = mapToDTO(c);
                                        countriesDTOS.add(nuevo);
                                }
                        }
                } else {
                        for (Country c : allCountries){

                                if (c.getName().contains(name)){
                                        CountryDTO nuevo = mapToDTO(c);
                                        countriesDTOS.add(nuevo);
                                }
                                if (c.getCode().contains(code)){
                                        CountryDTO nuevo = mapToDTO(c);
                                        countriesDTOS.add(nuevo);
                                }
                        }
                }

                return countriesDTOS;
        }

        public List<CountryDTO> getCountriesByContinent(String continent){
                List<CountryDTO> countriesDTOS = new ArrayList<>();
                List<Country> allCountries = getAllCountries();
                for (Country c : allCountries){
                        if (c.getRegion()==null){
                                break;
                        }
                        if (c.getRegion().contains(continent)){
                                CountryDTO nuevo = mapToDTO(c);
                                countriesDTOS.add(nuevo);
                        }
                }
                return countriesDTOS;
        }

        public List<CountryDTO> getCountryByLanguage(String language){
                List<CountryDTO> countriesDTOS = new ArrayList<>();
                List<Country> allCountries = getAllCountries();
                for (Country c : allCountries){
                        if (c.getLanguages()==null){
                                continue;
                        }
                        if (c.getLanguages().containsValue(     language)){
                                CountryDTO nuevo = mapToDTO(c);
                                countriesDTOS.add(nuevo);
                        }
                }
                return countriesDTOS;
        }
        public CountryDTO getCountryMostBorder(){
                CountryDTO nuevo = new CountryDTO();
                int i = 0;
                int borderSize = 0;
                List<Country> allCountries = getAllCountries();
                for (Country c : allCountries){
                        if (c.getBorders()==null){
                                continue;
                        }
                        if (c.getBorders().size()>borderSize){
                                borderSize=c.getBorders().size();
                                i= allCountries.indexOf(c);
                        }
                }
                nuevo.setName(allCountries.get(i).getName());
                nuevo.setCode(allCountries.get(i).getCode());
                return nuevo;
        }

        public List<CountryDTO> amountOfCountrySave(Integer amountOfSave){
                List<CountryDTO> countriesDTOS = new ArrayList<>();
                List<Country> allCountries = getAllCountries();
                Collections.shuffle(allCountries);
                for (int i = 0; i<amountOfSave;i++){
                        Country country = allCountries.get(i);
                        countryRepository.save(modelMapper.map(country, CountryEntity.class));
                        CountryDTO nuevo = this.mapToDTO(country);
                        countriesDTOS.add(nuevo);
                }
                return countriesDTOS;
        }


        /**
         * Agregar mapeo de campo cca3 (String)
         * Agregar mapeo campos borders ((List<String>))
         */
        private Country mapToCountry(Map<String, Object> countryData) {
                Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
                return Country.builder()
                        .name((String) nameData.get("common"))
                        .population(((Number) countryData.get("population")).longValue())
                        .area(((Number) countryData.get("area")).doubleValue())
                        .code((String) countryData.get("cca3"))
                        .region((String) countryData.get("region"))
                        .borders(((List<String>) countryData.get("borders")))
                        .languages((Map<String, String>) countryData.get("languages"))
                        .build();
        }


        private CountryDTO mapToDTO(Country country) {
                return new CountryDTO(country.getCode(), country.getName());
        }
}