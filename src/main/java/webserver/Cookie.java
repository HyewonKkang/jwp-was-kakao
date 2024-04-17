package webserver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Cookie {

    private static final String MAP_DELIMITER = "=";
    private static final String COOKIE_DELIMITER = "; ";
    private static final String DEFAULT_PATH = "; Path=/";

    private final Map<String, String> cookies;

    public Cookie() {
        this.cookies = new HashMap<>();
    }

    private Cookie(final Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public static Cookie from(final String cookie) {
        if (!cookie.contains(MAP_DELIMITER)) {
            return new Cookie();
        }
        String[] cookies = cookie.split(COOKIE_DELIMITER);
        return new Cookie(parseCookie(cookies));
    }

    public String get(final String key) {
        return cookies.get(key);
    }

    public void put(final String key, final String value) {
        cookies.put(key, value);
    }

    private static Map<String, String> parseCookie(final String[] cookies) {
        return Arrays.stream(cookies)
                .map(cookie -> cookie.split(MAP_DELIMITER))
                .collect(Collectors.toMap(cookie -> cookie[0], cookie -> cookie[1]));
    }

    @Override
    public String toString() {
        return cookies.entrySet().stream()
                .map(entry -> entry.getKey() + MAP_DELIMITER + entry.getValue())
                .collect(Collectors.joining(COOKIE_DELIMITER)) + DEFAULT_PATH;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cookie cookie = (Cookie) o;
        return Objects.equals(cookies, cookie.cookies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cookies);
    }
}
