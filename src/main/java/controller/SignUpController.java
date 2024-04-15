package controller;

import db.DataBase;
import model.User;
import webserver.request.CustomRequest;
import webserver.response.CustomResponse;

public class SignUpController extends AbstractController {
    @Override
    protected void doGet(CustomRequest request, CustomResponse response) {
    }

    @Override
    protected void doPost(CustomRequest request, CustomResponse response) throws Exception {
        createUser(request);
        response.sendRedirect("/index.html");
    }


    private void createUser(final CustomRequest customRequest) {
        final User user = User.of(customRequest.getBody());
        if (DataBase.findUserById(user.getUserId()).isEmpty()) {
            DataBase.addUser(user);
        }
    }
}
