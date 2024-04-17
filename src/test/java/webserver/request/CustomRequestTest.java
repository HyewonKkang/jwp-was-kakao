package webserver.request;

import org.junit.jupiter.api.Test;
import webserver.Cookie;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomRequestTest {

    private final String testDir = "./src/test/resources/";

    @Test
    void InputStream을_통해_CustomRequest를_생성할_수_있다() throws IOException {
        // given
        InputStream in = new FileInputStream(new File(testDir + "Http_POST.txt"));

        // when
        final CustomRequest request = CustomRequest.makeRequest(in);

        // then
        assertThat(request).isNotNull();
    }

    @Test
    void CustomRequest에서_필요한_값을_얻을_수_있다() throws IOException {
        // given
        InputStream in = new FileInputStream(new File(testDir + "Http_POST.txt"));

        // when
        final CustomRequest request = CustomRequest.makeRequest(in);

        // then
        assertThat(request.findPath()).isEqualTo("/user/create");
        assertThat(request.findMethod().toString()).isEqualTo("POST");
        assertThat(request.findContentType()).isEqualTo("text/html;charset=utf-8");
        assertThat(request.getBody()).containsEntry("userId", "javajigi")
                .containsEntry("password", "password")
                .containsEntry("name", "JaeSung");
    }
}
