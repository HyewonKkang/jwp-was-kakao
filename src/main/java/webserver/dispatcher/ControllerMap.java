package webserver.dispatcher;


import controller.Controller;
import webserver.request.CustomRequest;

import java.util.Map;

public class ControllerMap {

    private final Map<RequestBranch, Controller> controllerMap;

    public ControllerMap(Map<RequestBranch, Controller> controllerMap) {
        this.controllerMap = controllerMap;
    }

    public Controller getController(CustomRequest request) {
        final RequestBranch branch = new RequestBranch(request.findPath(), request.findMethod());
        return controllerMap.get(branch);
    }
}