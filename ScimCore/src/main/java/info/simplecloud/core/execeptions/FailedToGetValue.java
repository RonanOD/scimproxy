package info.simplecloud.core.execeptions;

@SuppressWarnings("serial")
public class FailedToGetValue extends Exception {

    public FailedToGetValue(String msg, Throwable e) {
        super(msg, e);
    }

}
