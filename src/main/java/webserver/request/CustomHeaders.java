package webserver.request;

import webserver.Cookie;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomHeaders {

    private static final String SPLIT_REGEX = ": ";
    private static final int VALUE_INDEX = 1;
    private static final int KEY_INDEX = 0;

    private final Map<String, String> elements;
    private final List<Cookie> cookies;

    public CustomHeaders(final List<String> headersInput) {
        this.elements = parseHeaders(headersInput);
        this.cookies = getCookies();
    }

    private Map<String, String> parseHeaders(final List<String> headersInput) {
        return headersInput.stream()
                .map(input -> input.split(SPLIT_REGEX))
                .collect(Collectors.toMap(split -> split[KEY_INDEX], split -> split[VALUE_INDEX]));
    }

    private List<Cookie> getCookies() {
        return elements.entrySet().stream()
                .filter(entry -> entry.getKey().equals("Cookie"))
                .map(entry -> entry.getValue().split("="))
                .map(it -> new Cookie(it[0], it[1]))
                .collect(Collectors.toList());
    }

    public Cookie getCookie(final String key) {
        return cookies.stream()
                .filter(cookie -> cookie.getName().equals(key))
                .findFirst()
                .orElse(null);
    }

    public Map<String, String> getElements() {
        return elements;
    }
}
