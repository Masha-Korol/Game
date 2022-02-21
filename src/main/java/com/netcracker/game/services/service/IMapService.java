package com.netcracker.game.services.service;

import com.netcracker.game.services.dto.MapDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IMapService {

    Optional<MapDto> getMap(Integer mapId);
    Boolean addMap(String name, String description, Integer tilesetId, MultipartFile jsonFile, String previewBase64Picture) throws IOException;
    List<MapDto> getAllMaps();
}
