package controller;

import utils.FileIoUtils;
import webserver.request.CustomRequest;
import webserver.response.CustomResponse;

public class DefaultController extends AbstractController {
    private static final String DEFAULT_FILE_NAME = "templates/index.html";
    private static final String DEFAULT_PAGE_PATH = "/";

    @Override
    protected void doGet(CustomRequest request, CustomResponse response) throws Exception {
        byte[] body = loadResponseBody(request);
        response.responseOK(body, request);
    }

    private byte[] loadResponseBody(final CustomRequest customRequest) throws Exception {
        if (customRequest.isPathEqual(DEFAULT_PAGE_PATH)) {
            return FileIoUtils.loadFileFromClasspath(DEFAULT_FILE_NAME);
        }
        return FileIoUtils.loadFileFromClasspath(customRequest.findFilePath());
    }

    @Override
    protected void doPost(CustomRequest request, CustomResponse response) {

    }
}
