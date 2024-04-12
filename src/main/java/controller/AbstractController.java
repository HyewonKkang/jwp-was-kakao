package controller;

import webserver.request.CustomMethod;
import webserver.request.CustomRequest;
import webserver.response.CustomResponse;

abstract class AbstractController implements Controller {
    @Override
    public void service(CustomRequest request, CustomResponse response) {
        if (isPost(request)) {
            doPost(request, response);
            return;
        }
        doGet(request, response);
    }

    private boolean isPost(CustomRequest request) {
        return request.isMethodEqual(CustomMethod.POST);
    }

    protected abstract void doGet(CustomRequest request, CustomResponse response);

    protected abstract void doPost(CustomRequest request, CustomResponse response);
}
