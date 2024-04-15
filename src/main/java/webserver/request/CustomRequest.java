package webserver.request;

import utils.IOUtils;
import webserver.Cookie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomRequest {

    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String EMPTY_BODY = "";

    private final CustomRequestLine line;
    private final CustomHeaders headers;
    private final CustomBody body;

    public CustomRequest(final CustomRequestLine line, final CustomHeaders headers, final CustomBody body) {
        this.line = line;
        this.headers = headers;
        this.body = body;
    }

    public static CustomRequest makeRequest(final InputStream in) throws IOException {
        try {
            final InputStreamReader inputStreamReader = new InputStreamReader(in);
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            final String firstLine = bufferedReader.readLine();
            final CustomHeaders customHeaders = readHeaders(bufferedReader);
            final CustomBody customBody = readBodyIfPresent(bufferedReader, customHeaders);

            return new CustomRequest(
                    CustomRequestLine.from(firstLine),
                    customHeaders,
                    customBody
            );
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    private static CustomHeaders readHeaders(BufferedReader bufferedReader) {
        return new CustomHeaders(
                bufferedReader.lines()
                        .takeWhile(newLine -> newLine != null && !newLine.isEmpty())
                        .collect(Collectors.toUnmodifiableList())
        );
    }

    private static CustomBody readBodyIfPresent(BufferedReader bufferedReader, CustomHeaders customHeaders) throws IOException {
        if (customHeaders.getElements().containsKey(CONTENT_LENGTH)) {
            final int contentLength = Integer.parseInt(customHeaders.getElements().get(CONTENT_LENGTH));
            return new CustomBody(IOUtils.readData(bufferedReader, contentLength));
        }
        return new CustomBody(EMPTY_BODY);
    }

    public boolean isMethodEqual(final CustomMethod customMethod) {
        return this.line.checkMethod(customMethod);
    }

    public Map<String, String> getBody() {
        return body.getBody();
    }

    public boolean isPathEqual(final String path) {
        return line.checkPath(path);
    }

    public String findContentType() {
        return this.line.getContentType();
    }

    public String findFilePath() {
        return line.getFilePath();
    }

    public String findPath() {
        return line.getPath();
    }

    public CustomMethod findMethod() {
        return line.getMethod();
    }

    public Cookie getCookieByName(final String name) {
        return headers.getCookie(name);
    }
}
