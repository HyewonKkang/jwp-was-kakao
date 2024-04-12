package webserver;

import controller.SignUpController;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.request.CustomMethod;
import webserver.request.CustomRequest;
import webserver.response.CustomResponse;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String DEFAULT_FILE_NAME = "templates/index.html";
    private static final String CREATE_USER_PATH = "/user/create";
    private static final String DEFALUT_PAGE_PATH = "/";
    private static final String DEFAULT_PAGE_MESSAGE = "Hello, World!";
    private static final String NEW_CLIENT_CONNECT_MESSAGE = "New Client Connect! Connected IP : {}, Port : {}";

    private final Socket connection;

    public RequestHandler(final Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logConnectionDetails();
        try {
            processConnection();
        } catch (final Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void logConnectionDetails() {
        logger.debug(NEW_CLIENT_CONNECT_MESSAGE, connection.getInetAddress(), connection.getPort());
    }

    private void processConnection() {
        try (
                final InputStream in = connection.getInputStream();
                final OutputStream out = connection.getOutputStream();
        ) {
            final CustomRequest customRequest = CustomRequest.makeRequest(in);
            final CustomResponse customResponse = new CustomResponse(out);

            byte[] body = DEFAULT_PAGE_MESSAGE.getBytes();

            if (customRequest.isMethodEqual(CustomMethod.GET)) {
                body = loadResponseBody(customRequest);
            }
            if (customRequest.isMethodEqual(CustomMethod.POST) && isCreateUserRequest(customRequest)) {
                SignUpController signUpController = new SignUpController();
                signUpController.service(customRequest, customResponse);
                return;
            }
            customResponse.standardResponse(body, customRequest);
        } catch (final Exception e) {
            logger.error(e.getMessage());
        }
    }

    private boolean isCreateUserRequest(final CustomRequest customRequest) {
        return customRequest.isPathStartingWith(CREATE_USER_PATH);
    }

    private byte[] loadResponseBody(final CustomRequest customRequest) throws Exception {
        if (customRequest.isPathEqual(DEFALUT_PAGE_PATH)) {
            return FileIoUtils.loadFileFromClasspath(DEFAULT_FILE_NAME);
        }
        return FileIoUtils.loadFileFromClasspath(customRequest.findFilePath());
    }

}