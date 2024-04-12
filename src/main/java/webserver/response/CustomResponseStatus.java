package webserver.response;

public enum CustomResponseStatus {
    OK(200, "OK"),
    REDIRECT(302, "REDIRECT");

    private final int statusCode;
    private final String statusMessage;

    CustomResponseStatus(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
