package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.internal.matchers.CapturesArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CountryServiceTest {
    @MockBean
    private CountryRepository countryRepository;
    @MockBean
    private RestTemplate restTemplate;
    @SpyBean
    private CountryService countryService;
    @Captor
    private ArgumentCaptor<CountryEntity> captorCountry;

    private List<Country> countries;
    @BeforeEach
    void init(){
        countries = new ArrayList<>();
        Country country1 = new Country();
        country1.setName("Rusia");
        country1.setCode("RUS");
        Country country2 = new Country();
        country2.setName("Argentina");
        country2.setCode("ARG");
        Country country3 = new Country();
        country3.setName("Brazil");
        country3.setCode("BRA");

        countries.add(country1);
        countries.add(country2);
        countries.add(country3);
    }

    @Test
    @Disabled
    void getAllCountries() {
    }

    @Test
    void getCountry() {
        //given
        String name = "Argentina";
        String code = null;
        String url = "https://restcountries.com/v3.1/all";
        List<Map<String, Object>> response = new ArrayList<>();

        when(restTemplate.getForObject(url, List.class)).thenReturn(response);
        when(countryService.getAllCountries()).thenReturn(countries);

        //when
        List<CountryDTO> respuesta = countryService.getCountry(code, name);

        //then
        assertEquals(respuesta.get(0).getCode(),countries.get(1).getCode());
        assertEquals(respuesta.get(0).getName(),countries.get(1).getName());
    }

    @Test
    void getCountriesByContinent() {
    }

    @Test
    void getCountryByLanguage() {
    }

    @Test
    void getCountryMostBorder() {
    }

    @Test
    void amountOfCountrySave() {
    }
}