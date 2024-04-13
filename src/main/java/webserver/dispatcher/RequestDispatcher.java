package webserver.dispatcher;

import controller.Controller;
import controller.DefaultController;
import controller.LoginController;
import controller.SignUpController;
import webserver.request.CustomMethod;
import webserver.request.CustomRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestDispatcher {
    private static final ControllerMap requestMap;

    static {
        final Map<RequestBranch, Controller> map = new HashMap<>();
        map.put(new RequestBranch("/user/create", CustomMethod.POST)
                , new SignUpController());
        map.put(new RequestBranch("/user/login", CustomMethod.POST)
                , new LoginController());
        requestMap = new ControllerMap(map);
    }

    public static Controller dispatch(CustomRequest request) {
        Controller controller = requestMap.getController(request);
        if (controller != null) {
            return controller;
        }
        return new DefaultController();
    }
}
