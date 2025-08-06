package com.peluware.springframework.crud.core.web.controllers;


import cz.jirutka.rsql.parser.ast.Node;
import com.peluware.springframework.crud.core.web.export.Exporter;
import com.peluware.springframework.crud.core.ReadService;
import com.peluware.springframework.crud.core.utils.ResponseEntityUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Controller for exporting data in a specific format (e.g., CSV, Excel).
 * <p>
 * This controller allows exporting entity data, either as a whole page or a single entity,
 * based on the provided search parameters and pagination.
 * </p>
 *
 * @param <ID> Type of the entity's identifier (e.g., {@link Long}, {@link String})
 * @param <O>  Type of the options or configuration used for the export
 */
public interface ExportController<ID, O> {

    ReadService<?, ID> getService();

    /**
     * Retrieves the exporter used for exporting data.
     *
     * @return the {@link Exporter} that handles the export process
     */
    Exporter<O> getExporter();

    /**
     * Retrieves the export options based on the provided parameters.
     *
     * @param params the parameters that may include fields, titles, and other options for the export
     * @return the export options configured for the exporter
     */
    O getExportOptions(MultiValueMap<String, String> params);

    /**
     * Endpoint to export a page of data
     * <p>
     * This method allows exporting a page of entities based on the provided search string and pagination parameters.
     * The export format is determined by the exporter.
     * </p>
     *
     * @param search   an optional search string to filter the results
     * @param query    rest parameters of the request
     * @param pageable the pagination information
     * @return a {@link ResponseEntity} containing the export file as a {@link ByteArrayResource}
     */
    @GetMapping("/export")
    default ResponseEntity<ByteArrayResource> exportPage(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Node query,
            @RequestParam(required = false) MultiValueMap<String, String> params,
            Pageable pageable
    ) {

        var options = getExportOptions(params);
        var entities = getService().page(search, pageable, query);
        var exported = getExporter().export(entities, options);

        return ResponseEntityUtils.resource(
                exported.getByteArray(),
                exported.getFilename(),
                exported.getMediaType()
        );
    }

    /**
     * Endpoint to export a single entity's
     * <p>
     * This method allows exporting a specific entity based on its ID
     * </p>
     *
     * @param params rest parameters of the request
     * @param id     the ID of the entity to export
     * @return a {@link ResponseEntity} containing the export file as a {@link ByteArrayResource}
     */
    @GetMapping("/export/{id}")
    default ResponseEntity<ByteArrayResource> exportFind(
            @PathVariable ID id,
            @RequestParam(required = false) MultiValueMap<String, String> params
    ) {
        var options = getExportOptions(params);
        var entity = getService().find(id);
        var exported = getExporter().export(List.of(entity), options);
        return ResponseEntityUtils.resource(
                exported.getByteArray(),
                exported.getFilename(),
                exported.getMediaType()
        );

    }
}
