package com.netcracker.game.services.service;

import com.netcracker.game.services.dto.TileSetDto;

import java.util.List;

public interface ITileSetService {

    TileSetDto create(TileSetDto tileSetDto);

    String get(Integer tileSetId);

    TileSetDto getFull(Integer tileSetId);

    List<TileSetDto> getAllTileSet();
}
