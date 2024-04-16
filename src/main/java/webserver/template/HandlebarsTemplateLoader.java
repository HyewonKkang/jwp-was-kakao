package webserver.template;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;

public class HandlebarsTemplateLoader {

    private final TemplateLoader loader;
    private final Handlebars handlebars;


    public HandlebarsTemplateLoader() {
        this.loader = new ClassPathTemplateLoader();
        this.loader.setPrefix("/templates");
        this.loader.setSuffix(".html");
        this.handlebars = new Handlebars(loader);
    }

    public String render(final String path, final Object context) throws IOException {
        return this.handlebars.compile(path).apply(context);
    }
}
