package com.netcracker.game.controller;

import com.netcracker.game.services.dto.MapDto;
import com.netcracker.game.services.service.IMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/maps")
@CrossOrigin
public class MapController {

    @Autowired
    IMapService mapService;

    @PostMapping("/add/{mapName}")
    public ResponseEntity<?> addMap(@PathVariable("mapName") String mapName,
                                    @RequestParam("description") String description,
                                    @RequestParam("tileset_id") String tileSetId,
                                    @RequestParam("map_file") MultipartFile tiledmapJsonFile,
                                    @RequestParam("preview") String previewBase64Picture) {
        try {
            if (mapService.addMap(mapName, description, Integer.valueOf(tileSetId), tiledmapJsonFile, previewBase64Picture)) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).build();
            } else return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{mapId}")
    public MapDto getMap(@PathVariable Integer mapId) {
        Optional<MapDto> mapDto = mapService.getMap(mapId);
        return mapDto.orElseGet(MapDto::new);
    }

    @GetMapping("/all")
    public List<MapDto> getAllMaps() {
        return mapService.getAllMaps();
    }

//    @GetMapping("/preview/{id}")
//    public String getPreview(@PathVariable("id") Integer mapId) {
//        try {
//            if (mapService.getAllMaps().stream().anyMatch(o -> o.getMapId().equals(mapId))) {
//                MapDto map = mapService.getMap(mapId);
//                return map.getPreview().getData()
//            } else return "";
//        } catch (Exception e) {
//            return "";
//        }
//    }
}
