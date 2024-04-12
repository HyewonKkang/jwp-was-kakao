package webserver.response;

import webserver.request.CustomRequest;

import java.io.DataOutputStream;
import java.io.OutputStream;

public class CustomResponse {

    private final DataOutputStream dos;
    private final CustomResponseHeader responseHeader;
    private final CustomResponseBody responseBody;


    public CustomResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
        this.responseHeader = new CustomResponseHeader(dos);
        this.responseBody = new CustomResponseBody(dos);
    }

    public void sendRedirect(String path) {
        responseHeader.redirectHeader(path);
    }

    public void responseOK(final byte[] body, final CustomRequest customRequest) {
        responseHeader.response200Header(body.length, customRequest);
        responseBody.writeBody(body);
    }
}
