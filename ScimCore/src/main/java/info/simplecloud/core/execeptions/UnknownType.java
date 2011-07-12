package info.simplecloud.core.execeptions;

@SuppressWarnings("serial")
public class UnknownType extends RuntimeException {

    public UnknownType(String msg) {
        super(msg);
    }

}
