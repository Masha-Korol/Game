package com.netcracker.game.services.service.impl;

import com.netcracker.game.data.model.TileSet;
import com.netcracker.game.data.repository.TileSetRepository;
import com.netcracker.game.services.dto.TileSetDto;
import com.netcracker.game.services.mapstruct.TileSetMapper;
import com.netcracker.game.services.service.ITileSetService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TileSetServiceImpl implements ITileSetService {

    private final TileSetRepository tileSetRepository;
    private final TileSetMapper tileSetMapper;

    public TileSetServiceImpl(TileSetRepository tileSetRepository,
                              TileSetMapper tileSetMapper) {
        this.tileSetRepository = tileSetRepository;
        this.tileSetMapper = tileSetMapper;
    }

    @Override
    public TileSetDto create(TileSetDto tileSetDto) {
        TileSet tileSet = tileSetMapper.toEntity(tileSetDto);
        return tileSetMapper.toDto(tileSetRepository.save(tileSet));
    }

    @Override
    public String get(Integer tileSetId) {
        return tileSetRepository.findById(tileSetId).orElse(null).getData();
    }

    @Override
    public TileSetDto getFull(Integer tileSetId) {
        return tileSetMapper.toDto(tileSetRepository.findById(tileSetId).orElse(null));
    }

    @Override
    public List<TileSetDto> getAllTileSet() {
        return tileSetMapper.toDto(tileSetRepository.findAll());
    }
}
