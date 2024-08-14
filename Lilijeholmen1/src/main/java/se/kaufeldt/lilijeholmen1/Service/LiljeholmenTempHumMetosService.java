package se.kaufeldt.lilijeholmen1.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import se.kaufeldt.lilijeholmen1.Metos.Metos;

import java.util.List;

@Service
public class LiljeholmenTempHumMetosService {
    private final WebClient webClient;

    public LiljeholmenTempHumMetosService(WebClient webClient) {
        this.webClient = webClient;
    }

    public double getMetosTemp() {
        Metos metosData = fetchData();
        return extractTemperature(metosData);
    }

    public double getMetosHum() {
        Metos metosData = fetchData();
        return extractHumidity(metosData);
    }
    private Metos fetchData() {
        Mono<Metos> m = webClient
                .get()
                .uri("https://api.open-meteo.com/v1/forecast?latitude=59.311&longitude=18.03&hourly=temperature_2m,relative_humidity_2m&timezone=auto&forecast_days=3")
                .retrieve()
                .bodyToMono(Metos.class);
        return m.block();
    }


    private double extractTemperature(Metos metosData) {
        List<Double> temperatures = metosData.getHourly().getTemperature2m();
        if (temperatures != null && !temperatures.isEmpty()) {
            return temperatures.get(0);
        }
        throw new RuntimeException("Temperature data not found");
    }

    private double extractHumidity(Metos metosData) {
        List<Integer> humidities = metosData.getHourly().getRelativeHumidity2m();
        if (humidities != null && !humidities.isEmpty()) {
            return humidities.get(0);
        }
        throw new RuntimeException("Humidity data not found");
    }
}