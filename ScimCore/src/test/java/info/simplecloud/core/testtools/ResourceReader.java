package info.simplecloud.core.testtools;

import java.io.IOException;
import java.io.InputStream;

public class ResourceReader {
    public static String readTextFile(String fileName, Class<?> clazz) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStream is = clazz.getResourceAsStream(fileName);
        int data = 0;
        
        while ((data = is.read()) != -1) {
            sb.append((char) data);
        }

        return sb.toString();
    }
}
