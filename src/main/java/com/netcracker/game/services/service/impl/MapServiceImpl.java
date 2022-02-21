package com.netcracker.game.services.service.impl;

import com.netcracker.game.data.model.File;
import com.netcracker.game.data.model.Map;
import com.netcracker.game.data.model.TileSet;
import com.netcracker.game.data.model.TiledMap;
import com.netcracker.game.data.repository.FileRepository;
import com.netcracker.game.data.repository.MapRepository;
import com.netcracker.game.data.repository.TileSetRepository;
import com.netcracker.game.data.repository.TiledMapRepository;
import com.netcracker.game.services.dto.MapDto;
import com.netcracker.game.services.mapstruct.MapMapper;
import com.netcracker.game.services.service.IMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MapServiceImpl implements IMapService {

    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private TiledMapRepository tiledMapRepository;

    @Autowired
    private TileSetRepository tileSetRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private MapMapper mapMapper;

    private final static Logger logger = LoggerFactory.getLogger(MapServiceImpl.class);

    @Override
    public Boolean addMap(String name, String description, Integer tilesetId, MultipartFile tiledmapJsonFile, String previewBase64Picture) throws IOException {
        String content = new String(tiledmapJsonFile.getBytes());
        Optional<TileSet> tileSet = tileSetRepository.findById(tilesetId);
        if (tiledMapRepository.findAll().stream().anyMatch(t -> t.getName().equals(name))) {
            return false;
        }

        if (tileSet.isPresent()) {
            TiledMap tiledMap = new TiledMap();
            tiledMap.setTileSet(tileSet.get());
            tiledMap.setName(name);
            tiledMap.setData(content);
            tiledMapRepository.saveAndFlush(tiledMap);

            File preview = new File();
            preview.setType("PNG");
            preview.setData(previewBase64Picture.getBytes());
            fileRepository.save(preview);

            Map map = new Map();
            map.setTiledMap(tiledMap);
            map.setDescription(description);
            map.setName(name);
            map.setPreview(preview);
            mapRepository.save(map);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<MapDto> getMap(Integer mapId) {
        Optional<Map> map = mapRepository.findById(mapId);
        return map.map(value -> mapMapper.toDto(value));
    }

    @Override
    public List<MapDto> getAllMaps() {
        List<MapDto> result = new ArrayList<>();
        this.mapRepository.findAll().forEach(map -> result.add(mapMapper.toDto(map)));
        return result;
    }
}
