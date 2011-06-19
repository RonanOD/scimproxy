package info.simplecloud.core.execeptions;

@SuppressWarnings("serial")
public class FailedToSetValue extends Exception {

    public FailedToSetValue(String msg, Throwable e) {
        super(msg, e);
    }

}
