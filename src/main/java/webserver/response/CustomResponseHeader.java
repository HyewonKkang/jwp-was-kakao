package webserver.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import webserver.request.CustomRequest;

import java.io.DataOutputStream;
import java.io.IOException;

public class CustomResponseHeader {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String CRLF = "\r\n";
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final String CONTENT_TYPE_KEY = "Content-Type";
    private static final String CONTENT_LENGTH_KEY = "Content-Length";
    private static final String LOCATION_KEY = "Location";
    private static final String DEFAULT_DELIMITER = " ";
    private static final String KEY_VALUE_DELIMITER = ": ";

    private final DataOutputStream dos;

    public CustomResponseHeader(DataOutputStream dos) {
        this.dos = dos;
    }

    public void response200Header(int lengthOfBodyContent, CustomRequest customRequest) {
        try {
            dos.writeBytes(createHeaderStatus(CustomResponseStatus.OK));
            dos.writeBytes(createContext(CONTENT_TYPE_KEY, customRequest.findContentType()));
            dos.writeBytes(createContext(CONTENT_LENGTH_KEY, String.valueOf(lengthOfBodyContent)));
            dos.writeBytes(CRLF);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void redirectHeader(String path) {
        try {
            dos.writeBytes(createHeaderStatus(CustomResponseStatus.REDIRECT));
            dos.writeBytes(createContext(LOCATION_KEY, path));
            dos.writeBytes(CRLF);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String createContext(String key, String value) {
        return key + KEY_VALUE_DELIMITER + value + CRLF;
    }

    private String createHeaderStatus(CustomResponseStatus status) {
        return String.join(DEFAULT_DELIMITER, HTTP_VERSION, status.getStatusCode() + "", status.getStatusMessage(), CRLF);
    }
}
