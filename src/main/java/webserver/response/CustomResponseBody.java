package webserver.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;

public class CustomResponseBody {
    private static final int OFFSET_ZERO = 0;
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final DataOutputStream dos;

    public CustomResponseBody(DataOutputStream dos) {
        this.dos = dos;
    }

    public void writeBody(byte[] body) {
        try {
            dos.write(body, OFFSET_ZERO, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
