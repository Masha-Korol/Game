package com.netcracker.game.services.service;

import com.netcracker.game.services.dto.TiledMapDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ITiledMapService {

    void addTiledMap(String tiledmapName, Integer tileSetId, MultipartFile jsonFile) throws IOException;
    String getDataById(Integer tiledMapId);
    TiledMapDto getById(Integer tiledMapId);
}
