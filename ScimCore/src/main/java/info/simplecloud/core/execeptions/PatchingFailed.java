package info.simplecloud.core.execeptions;

@SuppressWarnings("serial")
public class PatchingFailed extends RuntimeException {

    public PatchingFailed(String msg, Throwable cause) {
        super(msg, cause);
    }

}
