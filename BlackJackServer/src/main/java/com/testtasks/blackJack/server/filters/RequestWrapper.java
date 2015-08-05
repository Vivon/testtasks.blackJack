package com.testtasks.blackJack.server.filters;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class RequestWrapper extends HttpServletRequestWrapper {

    private final byte[] requestBytes;

    public RequestWrapper(HttpServletRequest request, byte[] requestBytes) {
        super(request);
        this.requestBytes = requestBytes;
    }

    public byte[] getRequestBytes() {
        return requestBytes;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ProxyInputStream(requestBytes);
    }

    private static class ProxyInputStream extends ServletInputStream {

        private ByteArrayInputStream inputStream;

        private ProxyInputStream(byte[] content) throws IOException {
            this.inputStream = new ByteArrayInputStream(content);
        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }
    }
}
