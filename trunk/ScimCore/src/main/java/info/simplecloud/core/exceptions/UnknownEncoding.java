package info.simplecloud.core.exceptions;

@SuppressWarnings("serial")
public class UnknownEncoding extends Exception {

    public UnknownEncoding(String encoding) {
        super("The encoding '" + encoding + "' is unknown.");
    }

}
