package se.kaufeldt.lilijeholmen1.AllStations;

public class StationData {
    private String stationName;
    private double temperature;
    private double humidity;

    public StationData(String stationName, double temperature, double humidity) {
        this.stationName = stationName;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public String getStationName() {
        return stationName;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }
}
