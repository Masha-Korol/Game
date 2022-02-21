package com.netcracker.game.controller;

import com.netcracker.game.services.dto.TiledMapDto;
import com.netcracker.game.services.service.ITiledMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/tiledmaps")
@CrossOrigin
public class TiledMapController {

    @Autowired
    ITiledMapService tiledMapService;

    @PostMapping("/add/{tiledmapName}")
    public ResponseEntity<?> addTiledMap(@PathVariable("tiledmapName") String tiledMapName,
                                         @RequestParam("tileset_id") Integer tileSetId,
                                         @RequestParam("file") MultipartFile jsonFile) {
        try {
            tiledMapService.addTiledMap(tiledMapName, tileSetId, jsonFile);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public TiledMapDto getTiledMapById(@PathVariable Integer id) {
        return tiledMapService.getById(id);
    }

    @GetMapping("/data/{id}")
    public String getTiledMapJsonDataById(@PathVariable Integer id) {
        return tiledMapService.getDataById(id);
    }
}
