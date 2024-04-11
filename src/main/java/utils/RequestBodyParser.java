package utils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestBodyParser {

    private RequestBodyParser() {
    }

    private static final String PARAMETER_SEPARATOR = "&";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final String NAME_VALUE_SEPARATOR = "=";

    public static Map<String, String> parse(final String query) {
        if (query == null || query.isEmpty()) {
            return Map.of();
        }
        final String[] params = query.split(PARAMETER_SEPARATOR);
        return Arrays.stream(params)
                .map(param -> param.split(NAME_VALUE_SEPARATOR))
                .collect(Collectors.toMap(pair -> pair[KEY_INDEX], pair -> pair[VALUE_INDEX], (a, b) -> b));
    }
}
