package webserver.session;

import org.junit.jupiter.api.Test;
import webserver.Cookie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionManagerTest {

    @Test
    void 세션을_생성할_수_있다() {
        // given
        Session session = SessionManager.createSession();

        // when
        String sessionId = session.getId();

        // then
        assertThat(sessionId).isNotNull();
    }

    @Test
    void 세션_아이디를_이용해_세션을_찾을_수_있다() {
        // given
        Session session = SessionManager.createSession();
        String sessionId = session.getId();

        // when
        Session foundSession = SessionManager.findSession(sessionId);

        // then
        assertThat(foundSession).isEqualTo(session);
    }

    @Test
    void 세션_아이디를_이용해_세션을_삭제할_수_있다() {
        // given
        Session session = SessionManager.createSession();
        String sessionId = session.getId();

        // when
        SessionManager.remove(sessionId);

        // then
        assertThatThrownBy(() -> SessionManager.findSession(sessionId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("세션을 찾을 수 없습니다.");
    }

    @Test
    void 쿠키_값을_이용해_세션을_찾을_수_있다() {
        // given
        Cookie cookie = new Cookie();
        Session session = SessionManager.getSession(cookie);

        // when
        Session foundSession = SessionManager.getSession(cookie);

        // then
        assertThat(foundSession).isEqualTo(session);
    }
}
