package webserver.dispatcher;

import controller.*;
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
        map.put(new RequestBranch("/user/login", CustomMethod.GET)
                , new LoginController());
        map.put(new RequestBranch("/user/list", CustomMethod.GET)
                , new ListUserController());
        requestMap = new ControllerMap(map);
    }

    public static Controller dispatch(final CustomRequest request) {
        Controller controller = requestMap.getController(request);
        if (controller != null) {
            return controller;
        }
        return new DefaultController();
    }
}
