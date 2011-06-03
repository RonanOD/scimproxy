package info.simplecloud.core.execeptions;

@SuppressWarnings("serial")
public class EncodingFailed extends Exception {

    public EncodingFailed(String msg, Exception e) {
        super(msg, e);
    }

}
