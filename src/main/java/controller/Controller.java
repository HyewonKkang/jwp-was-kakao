package controller;

import webserver.request.CustomRequest;
import webserver.response.CustomResponse;

public interface Controller {
    void service(CustomRequest request, CustomResponse response) throws Exception;
}
