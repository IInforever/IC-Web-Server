/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.icb.controller;

import com.i1nfo.icb.model.Paste;
import com.i1nfo.icb.service.PasteService;
import com.i1nfo.icb.validate.AnonymousPaste;
import com.i1nfo.icb.validate.PasteValidate;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/paste")
public class PasteController {

    private final PasteService pasteService;

    @Autowired
    public PasteController(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    @PostMapping("/anonymous")
    public ResponseEntity<Object> createAnonymousPaste(@RequestBody @Validated(AnonymousPaste.class) Paste paste) {
        return getCreateResponse(paste);
    }

    @PostMapping
    public ResponseEntity<Object> createPaste(
            @RequestBody @Validated(PasteValidate.class) @NotNull Paste paste,
            @RequestAttribute @NotNull Long userID) {
        paste.setUid(userID);
        return getCreateResponse(paste);
    }

    @NotNull
    protected ResponseEntity<Object> getCreateResponse(@Validated(PasteValidate.class) @RequestBody Paste paste) {
        Long id = pasteService.create(paste);
        if (id != null) {
            Map<String, Long> response = new HashMap<>();
            response.put("id", id);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.internalServerError().build();
    }

}
