package info.simplecloud.core.execeptions;

@SuppressWarnings("serial")
public class DecodeFailed extends RuntimeException {

    public DecodeFailed(String msg, Throwable e) {
        super(msg, e);
    }

}
