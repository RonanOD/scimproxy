package info.simplecloud.core.execeptions;

@SuppressWarnings("serial")
public class EncodingFailed extends RuntimeException {

    public EncodingFailed(String msg, Exception e) {
        super(msg, e);
    }

}
