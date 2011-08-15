package info.simplecloud.core.exceptions;

@SuppressWarnings("serial")
public class DecodingFailed extends RuntimeException {

    public DecodingFailed(String msg, Throwable cause) {
        super(msg, cause);
    }

}
