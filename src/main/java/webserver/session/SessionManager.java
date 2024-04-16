package webserver.session;

import webserver.Cookie;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "JSESSIONID";
    private static final Map<String, Session> SESSIONS = new HashMap<>();

    public static Session createSession() {
        final String sessionId = UUID.randomUUID().toString();
        final Session session = new Session(sessionId);
        SESSIONS.put(sessionId, session);
        return session;
    }

    public static Session findSession(final String id) {
        return Optional.ofNullable(SESSIONS.get(id))
                .orElseThrow(() -> new IllegalArgumentException("세션을 찾을 수 없습니다."));
    }

    public void remove(final String id) {
        SESSIONS.remove(id);
    }

    public static Session getSession(Cookie cookie) {
        try {
            String sessionId = cookie.get(SESSION_COOKIE_NAME);
            return findSession(sessionId);
        } catch (Exception e) {
            Session session = createSession();
            cookie.put(SESSION_COOKIE_NAME, session.getId());
            return session;
        }
    }
}
