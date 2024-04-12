package webserver;

import controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.dispatcher.RequestDispatcher;
import webserver.request.CustomRequest;
import webserver.response.CustomResponse;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
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

            Controller controller = RequestDispatcher.dispatch(customRequest);
            controller.service(customRequest, customResponse);
        } catch (final Exception e) {
            logger.error(e.getMessage());
        }
    }

}