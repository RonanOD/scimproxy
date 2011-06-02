package info.simplecloud.core.execeptions;

@SuppressWarnings("serial")
public class UnknowEncodingException extends Exception {

    public UnknowEncodingException(String encoding) {
        super("The encoding '" + encoding + "' is unknown.");
    }

}
