package info.simplecloud.core.execeptions;


@SuppressWarnings("serial")
public class InvalidUser extends Exception {

    public InvalidUser(String msg) {
        super(msg);
    }

    public InvalidUser(String msg, Throwable cause) {
        super(msg, cause);
    }

}
