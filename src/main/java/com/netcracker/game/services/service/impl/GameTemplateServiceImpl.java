package com.netcracker.game.services.service.impl;

import com.netcracker.game.data.model.GameTemplate;
import com.netcracker.game.data.model.GameUser;
import com.netcracker.game.data.repository.GameTemplateRepository;
import com.netcracker.game.data.repository.GameUserRepository;
import com.netcracker.game.exception.BadInputDataException;
import com.netcracker.game.services.dto.gametemplate.GameTemplateCreationDto;
import com.netcracker.game.services.dto.gametemplate.GameTemplateDto;
import com.netcracker.game.services.dto.pagination.PageDto;
import com.netcracker.game.services.dto.pagination.PageRequestDto;
import com.netcracker.game.services.mapstruct.GameTemplateCreationMapper;
import com.netcracker.game.services.mapstruct.GameTemplateMapper;
import com.netcracker.game.services.service.IGameTemplateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GameTemplateServiceImpl implements IGameTemplateService {

    private final GameTemplateRepository gameTemplateRepository;
    private final GameUserRepository gameUserRepository;
    private final GameTemplateMapper gameTemplateMapper;
    private final GameTemplateCreationMapper gameTemplateCreationMapper;

    public GameTemplateServiceImpl(GameTemplateRepository gameTemplateRepository,
                                   GameUserRepository gameUserRepository, GameTemplateMapper gameTemplateMapper,
                                   GameTemplateCreationMapper gameTemplateCreationMapper) {
        this.gameTemplateRepository = gameTemplateRepository;
        this.gameUserRepository = gameUserRepository;
        this.gameTemplateMapper = gameTemplateMapper;
        this.gameTemplateCreationMapper = gameTemplateCreationMapper;
    }

    @Override
    public void addGameTemplate(GameTemplateCreationDto dto) throws BadInputDataException {
        if (gameTemplateRepository.existsByName(dto.getName())){
            throw new BadInputDataException("Template name '" + dto.getName() + "' has already been taken");
        }

        GameTemplate gameTemplate = gameTemplateCreationMapper.toEntity(dto);
        gameTemplateRepository.save(gameTemplate);
    }

    @Override
    public PageDto<GameTemplateDto> getAll(String searchText, PageRequestDto pageRequest) {
        Pageable pageable = PageRequest.of(pageRequest.getPage() - 1, pageRequest.getItemsPerPage(), Sort.by("name"));
        Page<GameTemplate> page = gameTemplateRepository.findAllByNameContainsIgnoreCase(searchText, pageable);
        Page<GameTemplateDto> pageDto = page.map(gameTemplateMapper::toDto);
        return new PageDto<GameTemplateDto>(pageDto);
    }

    @Override
    public List<GameTemplateDto> getAllByGameUser(Integer gameUserId) {
        GameUser gameUser = gameUserRepository.findById(gameUserId).orElseThrow();
        return gameTemplateMapper.toDto(gameTemplateRepository.findAllByOwner(gameUser));
    }

    @Override
    @Transactional
    public void editGameTemplate(GameTemplateDto gameTemplateDto) {
        GameTemplate gameTemplate = gameTemplateRepository.findById(gameTemplateDto.getTemplateId()).orElseThrow();
        gameTemplate.setNumberOfPlayers(gameTemplateDto.getNumberOfPlayers());
        gameTemplate.setName(gameTemplateDto.getName());
        gameTemplateRepository.save(gameTemplate);
    }

    @Override
    public void deleteGameTemplate(Integer gameTemplateId) {
        gameTemplateRepository.deleteById(gameTemplateId);
    }

    @Override
    public GameTemplateDto getByName(String gameTemplateName) {
        return gameTemplateMapper.toDto(gameTemplateRepository.findByName(gameTemplateName));
    }
}
