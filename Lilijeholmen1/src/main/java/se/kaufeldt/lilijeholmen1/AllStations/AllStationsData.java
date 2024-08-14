package se.kaufeldt.lilijeholmen1.AllStations;

import se.kaufeldt.lilijeholmen1.Service.TempAndHumService;
import se.kaufeldt.lilijeholmen1.AllStations.StationData;

import java.util.List;

public class AllStationsData {

    private List<StationData> stationsData;
    public AllStationsData(List<StationData> stationsData) {
        this.stationsData = stationsData;
    }
    public List<StationData> getStationsData() {
        return stationsData;
    }
}
