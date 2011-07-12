package info.simplecloud.core.execeptions;

@SuppressWarnings("serial")
public class UnhandledAttributeType extends RuntimeException {

    public UnhandledAttributeType(String msg) {
        super(msg);
    }

}
