package controller;

import db.DataBase;
import model.User;
import webserver.Cookie;
import webserver.request.CustomRequest;
import webserver.response.CustomResponse;
import webserver.session.SessionManager;

import java.util.Map;

public class LoginController extends AbstractController {

    @Override
    protected void doGet(CustomRequest request, CustomResponse response) throws Exception {
    }

    @Override
    protected void doPost(final CustomRequest request, final CustomResponse response) throws Exception {
        final boolean loginSuccess = checkLogin(request);
        if (loginSuccess) {
            Cookie cookie = request.getCookie();
            SessionManager.getSession(cookie)
                    .setAttribute("logined", true);
            response.setCookie(cookie);
            response.sendRedirect("/index.html");
            return;
        }
        response.sendRedirect("/user/login_failed.html");
    }

    private boolean checkLogin(final CustomRequest customRequest) { // TODO: 서비스로?
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
