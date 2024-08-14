package se.kaufeldt.lilijeholmen1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kaufeldt.lilijeholmen1.AllStations.AllStationsData;
import se.kaufeldt.lilijeholmen1.AllStations.StationData;
import se.kaufeldt.lilijeholmen1.Forecast.ForecastData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TempAndHumService {
    @Autowired
    LiljeholmenTempHumMetService liljeholmenTempHumMet;
    @Autowired
    LiljeholmenTempHumMetosService liljeholmenTempHumMetos;
    @Autowired
    LiljeholmenTempHumSmhiService liljeholmenTempHumSmhi;

    private static final double OPTIMAL_TEMP = 25.0;
    private static final double OPTIMAL_HUMIDITY = 60.0;

    public double getSmhiTemp(){
        return liljeholmenTempHumSmhi.getSmhiTemp();
    }
    public double getSmhiHum(){
        return liljeholmenTempHumSmhi.getSmhiHum();
    }
    public double getMetTemp(){
        return liljeholmenTempHumMet.getMetTemp();
    }
    public double getMetHum(){
        return liljeholmenTempHumMet.getMetHum();
    }
    public double getMetosTemp(){
        return liljeholmenTempHumMetos.getMetosTemp();
    }
    public double getMetosHum(){
        return liljeholmenTempHumMetos.getMetosHum();
    }
    public ForecastData getClosestToOptimal() {
        double smhiTempDiff = Math.abs(getSmhiTemp() - OPTIMAL_TEMP);
        double smhiHumDiff = Math.abs(getSmhiHum() - OPTIMAL_HUMIDITY);
        double smhiTotalDiff = smhiTempDiff + smhiHumDiff;

        double metTempDiff = Math.abs(getMetTemp() - OPTIMAL_TEMP);
        double metHumDiff = Math.abs(getMetHum() - OPTIMAL_HUMIDITY);
        double metTotalDiff = metTempDiff + metHumDiff;

        double metosTempDiff = Math.abs(getMetosTemp() - OPTIMAL_TEMP);
        double metosHumDiff = Math.abs(getMetosHum() - OPTIMAL_HUMIDITY);
        double metosTotalDiff = metosTempDiff + metosHumDiff;

        double minDiff = Math.min(smhiTotalDiff, Math.min(metTotalDiff, metosTotalDiff));

        String closestService;
        double closestTemp;
        double closestHum;

        if (minDiff == smhiTotalDiff) {
            closestService = "SMHI";
            closestTemp = getSmhiTemp();
            closestHum = getSmhiHum();
        } else if (minDiff == metTotalDiff) {
            closestService = "MET";
            closestTemp = getMetTemp();
            closestHum = getMetHum();
        } else {
            closestService = "METOS";
            closestTemp = getMetosTemp();
            closestHum = getMetosHum();
        }

        LocalDateTime dateTime24HoursFromNow = LocalDateTime.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDateTime = dateTime24HoursFromNow.format(formatter);

        return new ForecastData(closestService, closestTemp, closestHum, formattedDateTime);
    }
    public List<StationData> getAllStationsData() {
        List<StationData> allStationsData = new ArrayList<>();

        allStationsData.add(new StationData("SMHI", liljeholmenTempHumSmhi.getSmhiTemp(), liljeholmenTempHumSmhi.getSmhiHum()));
        allStationsData.add(new StationData("MET", liljeholmenTempHumMet.getMetTemp(), liljeholmenTempHumMet.getMetHum()));
        allStationsData.add(new StationData("METOS", liljeholmenTempHumMetos.getMetosTemp(), liljeholmenTempHumMetos.getMetosHum()));

        return allStationsData;
    }
}
