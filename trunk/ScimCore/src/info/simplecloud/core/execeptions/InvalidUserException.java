package info.simplecloud.core.execeptions;


@SuppressWarnings("serial")
public class InvalidUserException extends Exception {

    public InvalidUserException(String msg) {
        super(msg);
    }

    public InvalidUserException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
