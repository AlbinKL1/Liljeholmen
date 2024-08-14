package se.kaufeldt.lilijeholmen1.Forecast;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.kaufeldt.lilijeholmen1.Service.TempAndHumService;

@Controller
public class ForecastController {

    @Autowired
    private TempAndHumService tempAndHumService;

    @GetMapping("/optimal")
    public String getOptimalWeather(Model model) {
        ForecastData data = tempAndHumService.getClosestToOptimal();
        model.addAttribute("data", data);
        return "optimal";
    }
}
