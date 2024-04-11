package webserver.response;

import webserver.RequestHandler;
import webserver.request.CustomRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CustomResponse {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String CRLF = "\r\n";
    private static final String HTTP_1_1_200_OK = "HTTP/1.1 200 OK " + CRLF;
    private static final String HTTP_1_1_302_REDIRECT = "HTTP/1.1 302 REDIRECT " + CRLF;
    private static final String CONTENT_TYPE_KEY = "Content-Type: ";
    private static final String CONTENT_LENGTH_KEY = "Content-Length: ";
    private static final String LOCATION_INDEX_HTML = "Location: /index.html " + CRLF;
    private static final int OFFSET_ZERO = 0;

    private final DataOutputStream dos;


    public CustomResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
    }

    public void sendRedirect(final byte[] body) {
        redirectHeader();
        responseBody(body);
    }

    public void standardResponse(final byte[] body, final CustomRequest customRequest) {
        response200Header(body.length, customRequest);
        responseBody(body);
    }

    private void response200Header(int lengthOfBodyContent, CustomRequest customRequest) {
        try {
            dos.writeBytes(HTTP_1_1_200_OK);
            dos.writeBytes(CONTENT_TYPE_KEY + customRequest.findContentType() + CRLF);
            dos.writeBytes(CONTENT_LENGTH_KEY + lengthOfBodyContent + CRLF);
            dos.writeBytes(CRLF);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void redirectHeader() {
        try {
            dos.writeBytes(HTTP_1_1_302_REDIRECT);
            dos.writeBytes(LOCATION_INDEX_HTML);
            dos.writeBytes(CRLF);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(byte[] body) {
        try {
            dos.write(body, OFFSET_ZERO, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
