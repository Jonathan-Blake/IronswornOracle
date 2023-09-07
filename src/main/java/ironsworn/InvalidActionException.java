package ironsworn;

public class InvalidActionException extends RuntimeException {
    public InvalidActionException(String keyword) {
        super("Could not find action for keyword: "+keyword);
    }
}
