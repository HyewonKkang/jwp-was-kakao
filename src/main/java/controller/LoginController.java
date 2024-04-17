package controller;

import db.DataBase;
import model.User;
import utils.FileIoUtils;
import webserver.Cookie;
import webserver.request.CustomRequest;
import webserver.response.CustomResponse;
import webserver.session.SessionManager;

import java.util.Map;

public class LoginController extends AbstractController {

    @Override
    protected void doGet(CustomRequest request, CustomResponse response) throws Exception {
        final Cookie cookie = request.getCookie();
        final Object logined = SessionManager.getSession(cookie).getAttribute("logined");
        if (logined != null && (boolean) logined){
            response.sendRedirect("/index.html");
            return;
        }
        byte[] body = FileIoUtils.loadFileFromClasspath(request.findFilePath());
        response.responseOK(body, request);
    }

    @Override
    protected void doPost(final CustomRequest request, final CustomResponse response) throws Exception {
        final User loginedUser = checkLogin(request);
        if (loginedUser != null) {
            Cookie cookie = request.getCookie();
            SessionManager.getSession(cookie)
                    .setAttribute("logined", true);
            SessionManager.getSession(cookie)
                    .setAttribute("user", loginedUser);

            response.setCookie(cookie);
            response.sendRedirect("/index.html");
            return;
        }
        response.sendRedirect("/user/login_failed.html");
    }

    private User checkLogin(final CustomRequest customRequest) {
        final Map<String, String> body = customRequest.getBody();
        final String userId = body.get("userId");
        final String password = body.get("password");
        final User user = DataBase.findUserById(userId).orElse(null);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
