package com.netcracker.game.controller;

import com.netcracker.game.services.dto.FileDto;
import com.netcracker.game.services.service.IFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

@RestController
@RequestMapping(path = "/files",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@CrossOrigin
public class FileController {

    private final static Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private IFileService fileService;

    @PostMapping("/upload")
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        fileService.uploadFile(file);
    }

    @GetMapping("/{id}")
    public FileDto getFile(@PathVariable Integer id) {
        return fileService.getFile(id);
    }

    @GetMapping("/tilemap")
    List<FileDto> getAllTileMap() {
        return fileService.getAllFiles();
    }
}
