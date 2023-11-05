package com.sipvs.zadanie1.xml;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Timestamp {

    private static final String TS_QUERY_MIME_TYPE = "application/timestamp-query";
    private static final String TS_REPLY_MIME_TYPE = "application/timestamp-reply";

    private String errorMessage = "OK";

    public Timestamp() {
    }

    /**
     * Get Timestamp
     *
     * @param tsRequest The timestamp request as a byte array.
     * @param tsUrl The URL to send the timestamp request to.
     * @return The timestamp reply as a byte array.
     */
    public byte[] getTimestamp(byte[] tsRequest, String tsUrl) {
        try {
            URL url = new URL(tsUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", TS_QUERY_MIME_TYPE);
            conn.setDoOutput(true);

            try (OutputStream requestStream = conn.getOutputStream()) {
                requestStream.write(tsRequest, 0, tsRequest.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String contentType = conn.getContentType();
                System.out.println("Content-Type: " + contentType);
                if (!TS_REPLY_MIME_TYPE.equalsIgnoreCase(contentType)) {
                    throw new Exception("Incorrect response MIME type: " + contentType);
                }

                try (InputStream inStream = new BufferedInputStream(conn.getInputStream());
                     ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inStream.read(buffer)) != -1) {
                        baos.write(buffer, 0, bytesRead);
                    }
                    return baos.toByteArray();
                }
            } else {
                throw new Exception("Non-OK response received: " + responseCode);
            }
        } catch (Exception e) {
            this.errorMessage = e.toString();
            return null;
        }
    }

    /**
     * Get the error message.
     *
     * @return The error message.
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
