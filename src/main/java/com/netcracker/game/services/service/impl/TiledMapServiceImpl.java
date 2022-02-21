package com.netcracker.game.services.service.impl;

import com.netcracker.game.data.model.TileSet;
import com.netcracker.game.data.model.TiledMap;
import com.netcracker.game.data.repository.TileSetRepository;
import com.netcracker.game.data.repository.TiledMapRepository;
import com.netcracker.game.services.dto.TiledMapDto;
import com.netcracker.game.services.mapstruct.TiledMapMapper;
import com.netcracker.game.services.service.ITiledMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
public class TiledMapServiceImpl implements ITiledMapService {

    @Autowired
    TiledMapRepository tiledMapRepository;

    @Autowired
    TileSetRepository tileSetRepository;

    @Autowired
    TiledMapMapper tiledMapMapper;

    @Override
    public void addTiledMap(String tiledmapName, Integer tileSetId, MultipartFile jsonFile) throws IOException {
        String content = new String(jsonFile.getBytes());
        Optional<TileSet> tileSet = tileSetRepository.findById(tileSetId);

        if (tileSet.isPresent()) {
            TiledMap tiledMap = new TiledMap();
            tiledMap.setTileSet(tileSet.get());
            tiledMap.setName(tiledmapName);
            tiledMap.setData(content);
            tiledMapRepository.save(tiledMap);
        }
    }

    @Override
    public String getDataById(Integer tiledMapId) {
        return Objects.requireNonNull(tiledMapRepository.findById(tiledMapId).orElse(null)).getData();
    }

    @Override
    public TiledMapDto getById(Integer tiledMapId) {
        TiledMap tiledMap = tiledMapRepository.findById(tiledMapId).orElse(null);
        if (Objects.nonNull(tiledMap)) {
            return tiledMapMapper.toDto(tiledMap);
        } else {
            return new TiledMapDto();
        }
    }


}
