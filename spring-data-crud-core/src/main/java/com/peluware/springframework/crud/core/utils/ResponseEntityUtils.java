package com.peluware.springframework.crud.core.utils;

import lombok.experimental.UtilityClass;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@UtilityClass
public final class ResponseEntityUtils {

    public static ResponseEntity<ByteArrayResource> resource(byte[] content, String filename, MediaType mediaType, boolean inline) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(mediaType)
                .headers(getHeadersForFile(filename, inline))
                .body(new ByteArrayResource(content));
    }

    public static ResponseEntity<ByteArrayResource> resource(byte[] content, String filename, MediaType mediaType) {
        return resource(content, filename, mediaType, false);
    }

    private static HttpHeaders getHeadersForFile(String filename, boolean inline) {
        var safeFilename = StringUtils.toASCII(filename);
        var encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8).replace("+", "%20");

        var headerValue = (inline ? "inline" : "attachment") + "; filename=\"" + safeFilename + "\"; filename*=UTF-8''" + encodedFilename;

        var headers = new HttpHeaders();
        headers.set(CONTENT_DISPOSITION, headerValue);
        headers.setAccessControlExposeHeaders(List.of(CONTENT_DISPOSITION));

        return headers;
    }

}
