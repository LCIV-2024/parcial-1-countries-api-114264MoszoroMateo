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
import org.springframework.test.util.ReflectionTestUtils;
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
        Map<String,String> lenguaje = Map.of("RUS","Russian");


        countries = new ArrayList<>();
        Country country1 = new Country();
        country1.setName("Rusia");
        country1.setCode("RUS");
        country1.setRegion("Europa");
        country1.setLanguages(lenguaje);
        Country country2 = new Country();
        country2.setName("Argentina");
        country2.setCode("ARG");
        country2.setRegion("Americas");
        Country country3 = new Country();
        country3.setName("Brazil");
        country3.setCode("BRA");
        country3.setRegion("Americas");

        countries.add(country1);
        countries.add(country2);
        countries.add(country3);
    }

    @DisplayName("Traer countries formateadas a dto")
    @Test
    void getAllCountries() {
        List<CountryDTO> respuesta = new ArrayList<>();
        String url = "https://restcountries.com/v3.1/all";
        List<Map<String, Object>> response = new ArrayList<>();

        when(restTemplate.getForObject(url, List.class)).thenReturn(response);
        when(countryService.getAllCountries()).thenReturn(countries);
        List<Country> paises = countryService.getAllCountries();
        for (Country c : paises){
            respuesta.add(ReflectionTestUtils.invokeMethod(countryService,"mapToDTO",c));
        }
        assertEquals(respuesta.size(),countries.size());
        assertEquals(respuesta.get(0).getCode(),countries.get(0).getCode());
        assertEquals(respuesta.get(0).getName(),countries.get(0).getName());
        assertEquals(respuesta.get(1).getCode(),countries.get(1).getCode());
        assertEquals(respuesta.get(1).getName(),countries.get(1).getName());
        assertEquals(respuesta.get(2).getCode(),countries.get(2).getCode());
        assertEquals(respuesta.get(2).getName(),countries.get(2).getName());

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
        //given
        String continent = "Americas";
        String url = "https://restcountries.com/v3.1/all";
        List<Map<String, Object>> response = new ArrayList<>();
        when(restTemplate.getForObject(url, List.class)).thenReturn(response);
        when(countryService.getAllCountries()).thenReturn(countries);

        //when
        List<CountryDTO> respuesta = countryService.getCountriesByContinent(continent);

        //then
        assertEquals(respuesta.size(),2);
        assertEquals(respuesta.get(0).getName(),countries.get(1).getName());
        assertEquals(respuesta.get(1).getName(),countries.get(2).getName());
    }

    @Test
    void getCountryByLanguage() {
        //given
        String languaje = "Russian";
        String url = "https://restcountries.com/v3.1/all";
        List<Map<String, Object>> response = new ArrayList<>();
        when(restTemplate.getForObject(url, List.class)).thenReturn(response);
        when(countryService.getAllCountries()).thenReturn(countries);

        //when
        List<CountryDTO> respuesta = countryService.getCountryByLanguage(languaje);

        //then
        assertEquals(respuesta.size(),1);
        assertEquals(respuesta.get(0).getName(),countries.get(0).getName());
        assertEquals(respuesta.get(0).getCode(),countries.get(0).getCode());
    }

    @Test
    void getCountryMostBorder() {

    }

    @Test
    void amountOfCountrySave() {
    }
}