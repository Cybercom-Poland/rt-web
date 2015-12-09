package com.cybercom.framework.vertx.web.core.server.http.request;

import java.util.Map;

public final class Request {
    private Method method;
    private String address;
    private String methodToInvoke;
    private Map<String, Object> parameters;
    private Object body;

    public Request() {
    }

    private Request(RequestBuilder requestBuilder) {
        this.method = requestBuilder.method;
        this.address = requestBuilder.address;
        this.methodToInvoke = requestBuilder.methodToInvoke;
        this.parameters = requestBuilder.parameters;
        this.body = requestBuilder.body;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMethodToInvoke() {
        return methodToInvoke;
    }

    public void setMethodToInvoke(String methodToInvoke) {
        this.methodToInvoke = methodToInvoke;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public static final class RequestBuilder {
        private Method method;
        private final String address;
        private final String methodToInvoke;
        private Map<String, Object> parameters;
        private Object body;

        public RequestBuilder(String address, String methodToInvoke) {
            this.address = address;
            this.methodToInvoke = methodToInvoke;
        }

        public RequestBuilder method(Method method) {
            this.method = method;
            return this;
        }

        public RequestBuilder parameters(Map<String, Object> parameters) {
            this.parameters = parameters;
            return this;
        }

        public RequestBuilder body(Object body) {
            this.body = body;
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }
}
