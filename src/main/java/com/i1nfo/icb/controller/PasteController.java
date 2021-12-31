/*
 * Copyright (c) IInfo 2021.
 */

package com.i1nfo.icb.controller;

import com.i1nfo.icb.model.Paste;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/paste")
public class PasteController {

    @PostMapping("/anonymous")
    public ResponseEntity<Object> createAnonymousPaste(@RequestBody Paste paste) {
        return ResponseEntity.ok().build();
    }

}
