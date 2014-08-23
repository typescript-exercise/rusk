package test.matcher;

public class NotMatchException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public NotMatchException(String name, Object expectedValue) {
        super(name + " = " + expectedValue);
    }
}
