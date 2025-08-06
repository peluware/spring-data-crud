package com.peluware.springframework.crud.core.web.export;

import lombok.Getter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * Interface for exporting data to a specified format (e.g., CSV, Excel, PDF).
 * <p>
 * The {@link Exporter} interface defines a method for exporting a collection of elements
 * in a specific format, such as CSV, Excel, or any other format supported by the implementation.
 * The exported data is returned as a {@link ByteArrayResource} that can be downloaded by the client.
 * </p>
 *
 * @param <O> the type of the options or configuration used for the export
 */
public interface Exporter<O> {

    /**
     * Exports a collection of elements to a specified format.
     * <p>
     * This method takes a collection of elements and converts them into the desired export format,
     * such as CSV, Excel, or another format. The export is configured based on the provided
     * </p>
     *
     * @param elements the collection of elements to export
     * @param options  the options or configuration used for the export, which may include
     * @return a {@link ResponseEntity} containing the exported data as a {@link ByteArrayResource}
     *         that can be downloaded by the client
     */
    ExportResource export(Iterable<?> elements, O options);

    @Getter
    class ExportResource {

        private final String filename;
        private final MediaType mediaType;
        private final byte[] byteArray;

        public ExportResource(byte[] byteArray, String filename, MediaType mediaType) {
            this.byteArray = byteArray;
            this.filename = filename;
            this.mediaType = mediaType;
        }

        public ExportResource(byte[] byteArray, String filename) {
            this(byteArray, filename, MediaType.APPLICATION_OCTET_STREAM);
        }
    }
}
