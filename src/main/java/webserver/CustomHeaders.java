package webserver;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomHeaders {

    private static final String SPLIT_REGEX = ": ";
    private static final int VALUE_INDEX = 1;
    private static final int KEY_INDEX = 0;

    private final Map<String, String> values;

    public CustomHeaders(final List<String> headersInput) {
        this.values = parseHeaders(headersInput);
    }

    private Map<String, String> parseHeaders(final List<String> headersInput) {
        return headersInput.stream()
                .map(input -> input.split(SPLIT_REGEX))
                .collect(Collectors.toMap(split -> split[KEY_INDEX], split -> split[VALUE_INDEX]));
    }

    public Map<String, String> getValues() {
        return values;
    }
}
