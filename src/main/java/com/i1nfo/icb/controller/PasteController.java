/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.icb.controller;

import com.i1nfo.icb.model.Paste;
import com.i1nfo.icb.service.PasteService;
import com.i1nfo.icb.utils.SecurityUtils;
import com.i1nfo.icb.validate.AnonymousPasteValidate;
import com.i1nfo.icb.validate.PasteValidate;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/paste")
public class PasteController {

    private final PasteService pasteService;

    @Autowired
    public PasteController(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    @PostMapping("/anonymous")
    public ResponseEntity<Object> createAnonymousPaste(@RequestBody @Validated(AnonymousPasteValidate.class) Paste paste) {
        return getCreateResponse(paste);
    }

    @PostMapping
    public ResponseEntity<Object> createPaste(@RequestBody @Validated(PasteValidate.class) @NotNull Paste paste,
                                              @RequestAttribute @NotNull Long userID) {
        paste.setUid(userID);
        return getCreateResponse(paste);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPaste(
            @PathVariable String id,
            @RequestAttribute(required = false) Long userID,
            @RequestParam(required = false) String passwd) {
        Paste paste = pasteService.getById(id);
        if (paste == null)
            return ResponseEntity.notFound().build();
        // check expiration time
        if (new Date().after(paste.getExpireTime()))
            return ResponseEntity.status(HttpStatus.GONE).build();
        // check authority
        if (paste.getIsPrivate()) {
            if (!Objects.equals(paste.getUid(), userID))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // check password
        if (!Objects.equals(paste.getPasswd(), SecurityUtils.calcMD5(passwd)))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        // remove unnecessary data
        paste.setPasswd(null);
        paste.setIsPrivate(null);
        paste.setUid(null);

        return ResponseEntity.ok(paste);
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
