package com.cybercom.framework.vertx.web.core.scanner.method;

public final class MethodArgument {
    private final String name;
    private final Class<?> type;
    private final boolean body;

    public MethodArgument(final String name, final Class<?> type, final boolean body) {
        this.name = name;
        this.type = type;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }

    public boolean isBody() {
        return body;
    }
}
