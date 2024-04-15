package webserver.response;

import java.util.HashMap;
import java.util.Map;

public class CustomResponseHeader {

    private static final String CONTENT_TYPE_KEY = "Content-Type";
    private static final String CONTENT_LENGTH_KEY = "Content-Length";
    private static final String LOCATION_KEY = "Location";

    private final Map<String, String> headers = new HashMap<>();


    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setContentType(final String contentType) {
        addHeader(CONTENT_TYPE_KEY, contentType);
    }

    public void setContentLength(final int contentLength) {
        addHeader(CONTENT_LENGTH_KEY, String.valueOf(contentLength));
    }

    public void setLocation(final String location) {
        addHeader(LOCATION_KEY, location);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
