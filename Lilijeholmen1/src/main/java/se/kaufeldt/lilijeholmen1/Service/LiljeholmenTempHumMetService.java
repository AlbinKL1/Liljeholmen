package se.kaufeldt.lilijeholmen1.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import se.kaufeldt.lilijeholmen1.Met.Met;
import se.kaufeldt.lilijeholmen1.Met.Timeseries;


@Service
public class LiljeholmenTempHumMetService {
    private final WebClient webClient;

    public LiljeholmenTempHumMetService(WebClient webClient) {
        this.webClient = webClient;
    }

    public double getMetTemp() {
        Met metData = fetchData();
        return extractTemperature(metData);
    }

    public double getMetHum() {
        Met metData = fetchData();
        return extractHumidity(metData);
    }

    private Met fetchData() {
        Mono<Met> m = webClient
                .get()
                .uri("https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=59.3110&lon=18.0300")
                .retrieve()
                .bodyToMono(Met.class);
        return m.block();
    }

    private double extractTemperature(Met metData) {
        if (metData != null && metData.getProperties() != null && metData.getProperties().getTimeseries() != null
                && !metData.getProperties().getTimeseries().isEmpty()) {
            Timeseries timeseries = metData.getProperties().getTimeseries().get(0);
            if (timeseries != null && timeseries.getData() != null && timeseries.getData().getInstant() != null
                    && timeseries.getData().getInstant().getDetails() != null) {
                return timeseries.getData().getInstant().getDetails().getAirTemperature();
            }
        }
        return Double.NaN;
    }

    private double extractHumidity(Met metData) {
        if (metData != null && metData.getProperties() != null && metData.getProperties().getTimeseries() != null
                && !metData.getProperties().getTimeseries().isEmpty()) {
            Timeseries timeseries = metData.getProperties().getTimeseries().get(0);
            if (timeseries != null && timeseries.getData() != null && timeseries.getData().getInstant() != null
                    && timeseries.getData().getInstant().getDetails() != null) {
                return timeseries.getData().getInstant().getDetails().getRelativeHumidity();
            }
        }
        return Double.NaN;
    }
}
