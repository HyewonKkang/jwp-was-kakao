package webserver.response;

import webserver.request.CustomRequest;
import webserver.Cookie;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class CustomResponse {

    private static final String CRLF = "\r\n";
    private static final String HTTP_VERSION = "HTTP/1.1";

    private static final String DEFAULT_DELIMITER = " ";
    private static final String KEY_VALUE_DELIMITER = ": ";
    private static final int OFFSET_ZERO = 0;

    private final DataOutputStream dos;
    private final CustomResponseHeader responseHeader;


    public CustomResponse(final OutputStream out) {
        this.dos = new DataOutputStream(out);
        this.responseHeader = new CustomResponseHeader();
    }

    public void sendRedirect(final String path) throws IOException {
        this.responseHeader.setLocation(path);

        this.dos.writeBytes(createHeaderStatus(CustomResponseStatus.REDIRECT));
        this.writeResponseHeaders();
        this.dos.writeBytes(CRLF);
    }

    public void responseOK(final byte[] body, final CustomRequest customRequest) throws IOException {
        this.responseHeader.setContentType(customRequest.findContentType());
        this.responseHeader.setContentLength(body.length);

        this.dos.writeBytes(createHeaderStatus(CustomResponseStatus.OK));
        this.writeResponseHeaders();
        this.dos.writeBytes(CRLF);
        this.writeBody(body);
    }

    public void writeResponseHeaders() throws IOException {
        final Map<String, String> headers = responseHeader.getHeaders();
        headers.forEach((key, value) -> {
            try {
                dos.writeBytes(createContext(key, value));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private String createContext(String key, String value) {
        return key + KEY_VALUE_DELIMITER + value + CRLF;
    }

    private String createHeaderStatus(CustomResponseStatus status) {
        return String.join(DEFAULT_DELIMITER, HTTP_VERSION, status.getStatusCode() + "", status.getStatusMessage(), CRLF);
    }

    public void writeBody(byte[] body) throws IOException {
        dos.write(body, OFFSET_ZERO, body.length);
        dos.flush();
    }

    public void setCookie(final Cookie cookie) {
        this.responseHeader.setCookie(cookie);
    }
}
