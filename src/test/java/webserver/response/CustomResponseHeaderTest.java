package webserver.response;

import org.junit.jupiter.api.Test;
import webserver.Cookie;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomResponseHeaderTest {

    @Test
    void ResponseHeader의_Content_Type을_지정할_수_있다() {
        // given
        final CustomResponseHeader customResponseHeader = new CustomResponseHeader();

        // when
        customResponseHeader.setContentType("text/html");

        // then
        assertThat(customResponseHeader.getHeaders().get("Content-Type")).isEqualTo("text/html");
    }

    @Test
    void ResponseHeader의_Content_Length를_지정할_수_있다() {
        // given
        final CustomResponseHeader customResponseHeader = new CustomResponseHeader();

        // when
        customResponseHeader.setContentLength(10);

        // then
        assertThat(customResponseHeader.getHeaders().get("Content-Length")).isEqualTo("10");
    }

    @Test
    void ResponseHeader의_Location을_지정할_수_있다() {
        // given
        final CustomResponseHeader customResponseHeader = new CustomResponseHeader();

        // when
        customResponseHeader.setLocation("/index.html");

        // then
        assertThat(customResponseHeader.getHeaders().get("Location")).isEqualTo("/index.html");
    }

    @Test
    void ResponseHeader의_쿠키를_지정할_수_있다() {
        // given
        final CustomResponseHeader customResponseHeader = new CustomResponseHeader();
        final Cookie cookie = Cookie.from("JSESSIONID=656cef62-e3c4-40bc-a8df-94732920ed46");

        // when
        customResponseHeader.setCookie(cookie);

        // then
        assertThat(customResponseHeader.getHeaders().get("Set-Cookie")).isEqualTo("JSESSIONID=656cef62-e3c4-40bc-a8df-94732920ed46; Path=/");
    }
}
