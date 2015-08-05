package com.testtasks.blackJack.server.filters;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ResponseWrapper extends HttpServletResponseWrapper {

    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private ServletOutputStream servletOutputStream;

    public ResponseWrapper(HttpServletResponse response, ServletOutputStream servletOutputStream) {
        super(response);
        this.servletOutputStream = servletOutputStream;
    }

    public byte[] getResponseBytes() {
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new ProxyOutputStream(byteArrayOutputStream, servletOutputStream);
    }

    private static class ProxyOutputStream extends ServletOutputStream {

        private ByteArrayOutputStream byteArrayOutputStream;
        private ServletOutputStream servletOutputStream;

        public ProxyOutputStream(ByteArrayOutputStream byteArrayOutputStream, ServletOutputStream servletOutputStream) {
            this.byteArrayOutputStream = byteArrayOutputStream;
            this.servletOutputStream = servletOutputStream;
        }

        public ByteArrayOutputStream getByteArrayOutputStream() {
            return byteArrayOutputStream;
        }

        @Override
        public void write(int b) throws IOException {
            this.byteArrayOutputStream.write(b);
            this.servletOutputStream.write(b);
        }

        @Override
        public void flush() throws IOException {
            this.servletOutputStream.flush();
        }

        @Override
        public void close() throws IOException {
            this.servletOutputStream.close();
        }
    }
}
