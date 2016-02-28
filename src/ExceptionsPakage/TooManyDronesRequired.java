package ExceptionsPakage;

/**
 * Created by georgipavlov on 28.02.16.
 */
public class TooManyDronesRequired extends Exception {
    public TooManyDronesRequired() {
        super("too many drones required");
    }
}
