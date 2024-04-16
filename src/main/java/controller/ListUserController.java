package controller;

import db.DataBase;
import model.User;
import webserver.request.CustomRequest;
import webserver.response.CustomResponse;
import webserver.session.Session;
import webserver.template.HandlebarsTemplateLoader;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class ListUserController extends AbstractController {

    @Override
    protected void doGet(CustomRequest request, CustomResponse response) throws Exception {
        final Session session = request.getSession();
        final boolean logined = (boolean) Optional.ofNullable(session.getAttribute("logined")).orElse(false);

        if (!logined) {
            response.sendRedirect("/user/login.html");
            return;
        }

        response.responseOK(renderUserListTemplate().getBytes(), request);
    }

    @Override
    protected void doPost(CustomRequest request, CustomResponse response) throws Exception {
    }

    private String renderUserListTemplate() throws IOException {
        final Collection<User> users = DataBase.findAll();
        final Map<String, Object> params = Map.of("users", users);

        final HandlebarsTemplateLoader handlebarsTemplateLoader = new HandlebarsTemplateLoader();
        return handlebarsTemplateLoader.render("user/list", params);
    }
}
