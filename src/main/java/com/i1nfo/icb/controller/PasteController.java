/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.icb.controller;

import com.i1nfo.icb.model.Paste;
import com.i1nfo.icb.service.PasteService;
import com.i1nfo.icb.utils.SecurityUtils;
import com.i1nfo.icb.validate.AnonymousPasteCreateValidate;
import com.i1nfo.icb.validate.PasteCreateValidate;
import com.i1nfo.icb.validate.PasteUpdateValidate;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Object> createAnonymousPaste(@RequestBody @Validated(AnonymousPasteCreateValidate.class) Paste paste) {
        return getCreateResponse(paste);
    }

    @PostMapping
    public ResponseEntity<Object> createPaste(@RequestBody @Validated(PasteCreateValidate.class) @NotNull Paste paste,
                                              @RequestAttribute @NotNull Long userID) {
        paste.setUid(userID);
        return getCreateResponse(paste);
    }

    @NotNull
    protected ResponseEntity<Object> getCreateResponse(@Validated(PasteCreateValidate.class) @RequestBody Paste paste) {
        Long id = pasteService.create(paste);
        if (id != null) {
            Map<String, Long> response = new HashMap<>();
            response.put("id", id);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.internalServerError().build();
    }

    /**
     * Get current user's all pastes
     */
    @GetMapping
    public ResponseEntity<Object> getPaste(@RequestAttribute Long userID) {
        List<Paste> pastes = pasteService.getByUid(userID);
        if (pastes == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(pasteService.getByUid(userID));
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
            return ResponseEntity.notFound().build();
        // check authority
        if (paste.getUid() != null && paste.getUid().equals(userID)) {
            if (paste.hasPasswd())
                paste.setPasswd(null);
            return ResponseEntity.ok(paste);
        }

        if (paste.getIsPrivate())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // check password
        if (paste.hasPasswd() && !paste.getPasswd().equals(SecurityUtils.calcMD5(passwd)))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        paste.setPasswd(null);
        return ResponseEntity.ok(paste);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> patchPaste(@PathVariable Long id,
                                             @RequestAttribute Long userID,
                                             @RequestBody @Validated(PasteUpdateValidate.class) @NotNull Paste paste) {
        if (paste.isEmpty())
            return ResponseEntity.badRequest().build();
        if (pasteService.updateById(id, paste, userID) != 0)
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Object> updatePaste(@PathVariable Long id,
//                                              @RequestAttribute Long userID,
//                                              @RequestBody @Validated(PasteUpdateAllValidate.class) Paste paste) {
//        return ResponseEntity.ok().build();
//    }

}
