/*
 * Copyright (c) IInfo 2022.
 */

package com.i1nfo.icb.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.i1nfo.icb.model.Paste;
import com.i1nfo.icb.service.PasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/pastes")
public class PasteAdminController {

    private final PasteService pasteService;

    @Autowired
    public PasteAdminController(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    @GetMapping
    public ResponseEntity<Object> getPastes(@RequestParam(defaultValue = "1") long page,
                                            @RequestParam(defaultValue = "20") long size) {
        IPage<Paste> pastes = pasteService.getALl(new Page<>(page, size));
        if (pastes == null || pastes.getTotal() == 0)
            return ResponseEntity.notFound().build();
        Map<String, Object> response = new HashMap<>();
        response.put("pages", pastes.getPages());
        response.put("total", pastes.getTotal());
        List<Paste> entities = pastes.getRecords();
        if (!entities.isEmpty())
            response.put("data", entities);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePaste(@PathVariable Long id) {
        if (pasteService.removeById(id))
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }
}
