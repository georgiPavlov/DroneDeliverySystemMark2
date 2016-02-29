package DronePakage;

/**
 * Created by georgipavlov on 28.02.16.
 */
public class DroneDeliveryWindows {
    private String startTime;
    private String endTime;
    private int endBattery;

    public DroneDeliveryWindows(String startTime, String endTime, int endBattery) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.endBattery = endBattery;
    }

    public String getStartTime() {

        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getEndBattery() {
        return endBattery;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setEndBattery(int endBattery) {
        this.endBattery = endBattery;
    }
}
