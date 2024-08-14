package se.kaufeldt.lilijeholmen1.Forecast;

public class ForecastData {
    private String origin;
    private double temp;
    private double humidity;
    private String timestamp;

    public ForecastData(String origin, double temmp, double humidity, String timestamp) {
        this.origin = origin;
        this.temp = temmp;
        this.humidity = humidity;
        this.timestamp = timestamp;
    }

    public String getOrigin() {
        return origin;
    }

    public double getTemp() {
        return temp;
    }

    public double getHumidity() {
        return humidity;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
