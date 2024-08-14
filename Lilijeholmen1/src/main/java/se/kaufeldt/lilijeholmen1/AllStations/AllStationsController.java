package se.kaufeldt.lilijeholmen1.AllStations;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.kaufeldt.lilijeholmen1.Service.TempAndHumService;

@Controller
public class AllStationsController {
    private final TempAndHumService tempAndHumService;

    public AllStationsController(TempAndHumService tempAndHumService) {
        this.tempAndHumService = tempAndHumService;
    }

    @GetMapping("/all")
    public String getAllStationsData(Model model) {
        model.addAttribute("stationsData", tempAndHumService.getAllStationsData());
        return "all"; // Returns the name of the HTML template (without the extension)
    }
}
