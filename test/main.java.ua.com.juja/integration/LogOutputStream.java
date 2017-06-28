package integration;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Alexandero on 14.06.2017.
 */
public class LogOutputStream extends OutputStream {
    private String log;

    @Override
    public void write(int b) throws IOException {
        byte[] bytes = new byte[]{(byte) (b & 0x00FF >> 8), (byte) (b & 0xFF00)};
        log += new String(bytes, "UTF-8");
    }

    public String getData() {
        return log;
    }
}
