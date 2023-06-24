package com.example.jaz_s24542_nbp.Controller;

import com.example.jaz_s24542_nbp.Model.CurrencyData;
import com.example.jaz_s24542_nbp.Model.Nbpresponse;
import com.example.jaz_s24542_nbp.Repository.CurrencyDataRepository;
import com.example.jaz_s24542_nbp.Service.NbpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/Nbp")
public class NbpController {
    private final NbpService nbpService;
    public NbpController(NbpService nbpService) {this.nbpService = nbpService;}
    @Tag(name = "Kontroler", description = "kontroler NbpService")
    @Operation(summary = "Funkcja pobiera średni kurs podanej waluty dla danego zakresu dat", description = "Podajemy datę początkową i końcową oraz skrót waluty")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Zapytanie poprawnie", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CurrencyData.class)) }),
            @ApiResponse(responseCode = "400", description = "Niepoprawny kod waluty lub niepoprwany zakres dat", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nie znaleziono danych dla tego zapytania", content = @Content),
            @ApiResponse(responseCode = "500", description = "Błąd serwera NBP", content = @Content)
    })
    @GetMapping("/rate/{currency}/{startDate}/{endDate}")
    public ResponseEntity<CurrencyData> getCurrencyRate(
            @PathVariable("currency") String currency,
            @PathVariable("startDate")LocalDate startDate,
            @PathVariable("endDate") LocalDate endDate) {
        Nbpresponse nbpResponse = nbpService.getCurrencyRates("A", currency, startDate, endDate);
        double averageRate = nbpService.calculateAverageRate(nbpResponse.getRates());
        CurrencyData item = nbpService.saveCurrencyData(currency, startDate, endDate, averageRate);

        return ResponseEntity.ok(item);
    }
}
