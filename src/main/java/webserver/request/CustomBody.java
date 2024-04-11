package webserver.request;

import utils.RequestBodyParser;

import java.util.Map;

public class CustomBody {

    private final Map<String, String> elements;

    public CustomBody(final String body) {
        this.elements = RequestBodyParser.parse(body);
    }

    public Map<String, String> getBody() {
        return elements;
    }
}
