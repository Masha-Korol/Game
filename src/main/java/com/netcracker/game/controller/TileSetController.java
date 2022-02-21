package com.netcracker.game.controller;

import com.netcracker.game.services.dto.FileDto;
import com.netcracker.game.services.dto.TileSetDto;
import com.netcracker.game.services.service.IFileService;
import com.netcracker.game.services.service.ITileSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(
        path = "/tilesets"
)
public class TileSetController {

    @Autowired
    ITileSetService tileSetService;

    @Autowired
    IFileService fileService;

    // too risky to remain this functionality open for end user
    // cuz tilesets moderation can be a headache

//    @PostMapping("/{tilesetName}")
//    public TileSetDto createTileSet(@PathVariable("tilesetName") String tilesetName,
//                                    @RequestParam("file") MultipartFile file) throws IOException {
//        TileSetDto tileSetDto = new TileSetDto();
//        String content = new String(file.getBytes());
//        FileDto fileDto = fileService.getFile(1);
//
//        tileSetDto.setFile(fileDto);
//        tileSetDto.setName(tilesetName);
//        tileSetDto.setData(content);
//
//        return tileSetService.create(tileSetDto);
//    }
//
    @GetMapping
    public List<TileSetDto> getAllTileSet() {
        return tileSetService.getAllTileSet();
    }
//
    @GetMapping("/{id}")
    public String get(@PathVariable Integer id) {
        return tileSetService.get(id);
    }
//
//    @GetMapping("/getfull")
//    public TileSetDto getFull() {
//        return tileSetService.getFull(1);
//    }
}
