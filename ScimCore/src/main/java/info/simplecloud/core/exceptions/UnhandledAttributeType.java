package info.simplecloud.core.exceptions;

@SuppressWarnings("serial")
public class UnhandledAttributeType extends RuntimeException {

    public UnhandledAttributeType(String msg) {
        super(msg);
    }

}
