package ExceptionsPakage;

/**
 * Created by georgipavlov on 26.02.16.
 */
public class InputExeption extends Exception {
    public InputExeption(String message) {
        super("input is not correct " + message);
    }
}
