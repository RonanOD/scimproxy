package info.simplecloud.core.execeptions;

@SuppressWarnings("serial")
public class DecodingFailed extends RuntimeException {

    public DecodingFailed(String msg, Throwable cause) {
        super(msg, cause);
    }

}
