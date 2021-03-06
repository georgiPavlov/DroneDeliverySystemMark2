package WarehousePakage;

/**
 * Created by georgipavlov on 29.02.16.
 */
public class Distance {
    private String endDate;
    private String startDate;
    private long[] totalDistance;
    private int droneIndex;

    public Distance(String endDate, String startDate, long[] totalDistance,int droneIndex) {
        this.endDate = endDate;
        this.startDate = startDate;
        this.totalDistance = totalDistance;
        this.droneIndex = droneIndex;
    }

    public int getDroneIndex() {
        return droneIndex;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public long[] getTotalDistance() {
        return totalDistance;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setTotalDistance(long[] totalDistance) {
        this.totalDistance = totalDistance;
    }

    public void setDroneIndex(int droneIndex) {
        this.droneIndex = droneIndex;
    }
}
