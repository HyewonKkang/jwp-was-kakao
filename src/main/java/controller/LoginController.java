package controller;

import db.DataBase;
import model.User;
import webserver.request.CustomRequest;
import webserver.response.CustomResponse;

import java.util.Map;

public class LoginController extends AbstractController {

    @Override
    protected void doGet(CustomRequest request, CustomResponse response) throws Exception {
    }

    @Override
    protected void doPost(CustomRequest request, CustomResponse response) throws Exception {
        boolean loginSuccess = loginSuccess(request);
        if (loginSuccess) {
            response.sendRedirect("/index.html");
            return;
        }
        response.sendRedirect("/user/login_failed.html");
    }

    private boolean loginSuccess(final CustomRequest customRequest) { // TODO: 서비스로?
        final Map<String, String> body = customRequest.getBody();
        final String userId = body.get("userId");
        final String password = body.get("password");

        final User user = DataBase.findUserById(userId).orElse(null);

        if (user == null) {
            return false;
        }
        return user.getPassword().equals(password);
    }
}
