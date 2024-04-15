package webserver;

public class Cookie {
    private final String name;
    private final String value;
    private final String path = "/";

    public Cookie(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + "=" + value + ";" + " Path=" + path + ";";
    }
}
