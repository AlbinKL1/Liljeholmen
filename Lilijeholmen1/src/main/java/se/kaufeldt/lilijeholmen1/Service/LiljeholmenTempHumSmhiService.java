package se.kaufeldt.lilijeholmen1.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import se.kaufeldt.lilijeholmen1.Smhi.Parameter;
import se.kaufeldt.lilijeholmen1.Smhi.Smhi;
import se.kaufeldt.lilijeholmen1.Smhi.TimeSeries;

import java.util.List;

@Service
public class LiljeholmenTempHumSmhiService {
    private final WebClient webClient;

    public LiljeholmenTempHumSmhiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public double getSmhiTemp() {
        Smhi smhiData = fetchData();
        return extractParameter(smhiData, "t");
    }

    public double getSmhiHum() {
        Smhi smhiData = fetchData();
        return extractParameter(smhiData, "r");
    }

    private Smhi fetchData() {
        Mono<Smhi> m = webClient
                .get()
                .uri("https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/18.0300/lat/59.3110/data.json")
                .retrieve()
                .bodyToMono(Smhi.class);
        return m.block();
    }

    private double extractParameter(Smhi smhiData, String paramName) {
        List<TimeSeries> timeSeriesList = smhiData.getTimeSeries();
        if (timeSeriesList != null && !((List<?>) timeSeriesList).isEmpty()) {
            for (Parameter parameter : timeSeriesList.get(0).getParameters()) {
                if (paramName.equals(parameter.getName())) {
                    return parameter.getValues().get(0);
                }
            }
        }
        throw new RuntimeException("Parameter " + paramName + " not found");
    }
}
