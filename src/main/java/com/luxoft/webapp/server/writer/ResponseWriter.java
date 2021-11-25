package com.luxoft.webapp.server.writer;

import com.luxoft.webapp.server.exception.ErrorType;
import com.luxoft.webapp.server.exception.ServerException;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ResponseWriter {
    public String writeSuccessResponse(InputStream inputStream, BufferedWriter writer) throws IOException {
        try {
            writer.write("HTTP/1.1 200 OK");
            writer.newLine();
            writer.newLine();

            byte[] buf = new byte[8192];
            int length;
            while ((length = inputStream.read(buf)) > 0) {
                String s = new String(buf, StandardCharsets.UTF_8);
                writer.write(s);
            }
        }catch (IOException ex){
            writeErrorResponse(writer, new ServerException(ErrorType.INTERNAL_SERVER_ERROR));
            return "500";
        }
        return "200";
    }

    public void writeErrorResponse(BufferedWriter writer, ServerException exception) throws IOException {
       new ErrorWriter(exception).errorWriter(writer);
    }

    private static class ErrorWriter {
        ServerException exception;
        private static final String ERROR_MATRIX = "src/main/resources/webapp/matrixErr.html";

        public ErrorWriter(ServerException exception) {
            this.exception = exception;
        }

        public void errorWriter(BufferedWriter writer) throws IOException {
            writer.write("HTTP/1.1 " + exception.getType().getCode());

            writer.newLine();
            writer.newLine();

            writer.write(errorReader());
        }

        private String errorReader() throws IOException {
            String matrixPage = "";

            File file = new File(ERROR_MATRIX);
            FileInputStream fileInputStream = new FileInputStream(file);

            byte[] buf = new byte[8192];

            int length;
            while ((length = fileInputStream.read(buf)) > 0) {
                matrixPage = new String(buf, StandardCharsets.UTF_8);
            }

            return matrixPage.replaceAll("&&&", exception.getType().getCode() + " " + exception.getType().name().replaceAll("_", " "));
        }

    }
}
