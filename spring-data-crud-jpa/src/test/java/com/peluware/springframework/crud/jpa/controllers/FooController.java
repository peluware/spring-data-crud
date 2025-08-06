package com.peluware.springframework.crud.jpa.controllers;

import com.peluware.springframework.crud.core.web.controllers.ExportController;
import com.peluware.springframework.crud.core.web.export.Exporter;
import com.peluware.springframework.crud.jpa.models.Foo;
import com.peluware.springframework.crud.jpa.dto.FooDto;
import com.peluware.springframework.crud.core.web.controllers.CrudController;
import com.peluware.springframework.crud.jpa.services.FooService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@RequiredArgsConstructor
@RestController
@RequestMapping("/foos")
public class FooController implements CrudController<Foo, FooDto, Long>, ExportController<Long, Object> {

    private final FooService service;

    @Override
    public Exporter<Object> getExporter() {
        return null;
    }

    @Override
    public Object getExportOptions(MultiValueMap<String, String> params) {
        return null;
    }
}