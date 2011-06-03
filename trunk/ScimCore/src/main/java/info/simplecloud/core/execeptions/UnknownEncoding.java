package info.simplecloud.core.execeptions;

@SuppressWarnings("serial")
public class UnknownEncoding extends Exception {

    public UnknownEncoding(String encoding) {
        super("The encoding '" + encoding + "' is unknown.");
    }

}
