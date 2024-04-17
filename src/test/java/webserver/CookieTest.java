package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CookieTest {

    @Test
    void 쿠키_값을_파싱해서_map으로_저장한다() {
        // given
        final String cookie = "JSESSIONID=656cef62-e3c4-40bc-a8df-94732920ed46; Path=/";
        final Cookie expected = new Cookie();
        expected.put("JSESSIONID", "656cef62-e3c4-40bc-a8df-94732920ed46");
        expected.put("Path", "/");

        // when
        final Cookie actual = Cookie.from(cookie);

        // then
        assertThat(actual.get("JSESSIONID")).isEqualTo(expected.get("JSESSIONID"));
        assertThat(actual.get("Path")).isEqualTo(expected.get("Path"));
        assertThat(actual).isEqualTo(expected);
    }
}
