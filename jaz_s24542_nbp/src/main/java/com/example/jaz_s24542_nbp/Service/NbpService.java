package com.example.jaz_s24542_nbp.Service;

import com.example.jaz_s24542_nbp.Model.CurrencyData;
import com.example.jaz_s24542_nbp.Model.Nbpresponse;
import com.example.jaz_s24542_nbp.Repository.CurrencyDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NbpService {

    private static final String NBP_API_URL = "http://api.nbp.pl/api/exchangerates/rates";

    private final RestTemplate restTemplate;
    private final CurrencyDataRepository currencyDataRepository;

    public NbpService(RestTemplate restTemplate, CurrencyDataRepository currencyDataRepository) {
        this.restTemplate = restTemplate;
        this.currencyDataRepository = currencyDataRepository;
    }
    public Nbpresponse getCurrencyRates(String table, String code, LocalDate startDate, LocalDate endDate) {
        String url = String.format("%s/%s/%s/%s/%s", NBP_API_URL, table, code, startDate, endDate);
        return restTemplate.getForObject(url, Nbpresponse.class);
    }
    public double calculateAverageRate(List<Nbpresponse.Rate> rates) {
        return rates.stream().mapToDouble(Nbpresponse.Rate::getMid).average()
                .orElseThrow(() -> new RuntimeException("Nie można obliczyć średniego kursu"));
    }
    public CurrencyData saveCurrencyData(String currency, LocalDate startDate, LocalDate endDate, double averageRate) {

        CurrencyData history = new CurrencyData();
        history.setCurrency(currency);
        history.setStartDate(startDate);
        history.setEndDate(endDate);
        history.setAverageRate(averageRate);
        history.setQueryDateTime(LocalDateTime.now());

        currencyDataRepository.save(history);

        return history;
    }
}
