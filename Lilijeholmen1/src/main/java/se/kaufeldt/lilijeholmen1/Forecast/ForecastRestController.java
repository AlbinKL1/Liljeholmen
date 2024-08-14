package se.kaufeldt.lilijeholmen1.Forecast;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.kaufeldt.lilijeholmen1.Service.TempAndHumService;

@RestController
public class ForecastRestController {

    @Autowired
    TempAndHumService tempAndHumService;

    @GetMapping("/optimal-forecast")
    public ForecastData getOptimalForecast() {
        return tempAndHumService.getClosestToOptimal();
    }
}
