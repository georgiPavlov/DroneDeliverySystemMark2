package TimetablePakage;

/**
 * Created by georgipavlov on 28.02.16.
 */
public class Timetable {
    private int id;
    private String date;

    public Timetable(int id, String date) {
        this.id = id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }
}
