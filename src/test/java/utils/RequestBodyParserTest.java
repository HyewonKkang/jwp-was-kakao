package utils;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
class RequestBodyParserTest {

    @Test
    void queryString_형태의_HTTP_Body를_받아_해당하는_부분을_key_value_형태의_Map으로_반환한다() {
        // given
        final String url = "userId=gooddino&password=dino11&name=dino&email=dino.shin@kakaocorp.com";

        // when
        Map<String, String> parsed = RequestBodyParser.parse(url);

        // then
        assertThat(parsed).containsEntry("userId", "gooddino")
                .containsEntry("password", "dino11")
                .containsEntry("name", "dino")
                .containsEntry("email", "dino.shin@kakaocorp.com");
    }
}
