package presenters;

/**
 * thrown when the MVP framework cannot display a screen because the
 * view or presenter classes related to this screen are not well formed.
 */
public class IllegalClassSetupException extends RuntimeException {
    public IllegalClassSetupException(String message) {
        super(message);
    }
}
