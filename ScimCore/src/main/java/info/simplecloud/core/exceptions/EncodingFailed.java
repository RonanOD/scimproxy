package info.simplecloud.core.exceptions;

@SuppressWarnings("serial")
public class EncodingFailed extends RuntimeException {

    public EncodingFailed(String msg, Throwable cause) {
        super(msg, cause);
    }

}
