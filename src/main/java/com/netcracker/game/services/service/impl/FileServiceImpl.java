package com.netcracker.game.services.service.impl;

import com.netcracker.game.data.model.File;
import com.netcracker.game.data.repository.FileRepository;
import com.netcracker.game.services.dto.FileDto;
import com.netcracker.game.services.mapstruct.FileMapper;
import com.netcracker.game.services.service.IFileService;
import liquibase.pro.packaged.F;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileServiceImpl implements IFileService {

    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    public FileServiceImpl(FileRepository fileRepository,
                           FileMapper fileMapper) {
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
    }

    @Override
    public FileDto uploadFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File newFile = new File(fileName, file.getContentType(), file.getBytes());

        return fileMapper.toDto(fileRepository.save(newFile));
    }

    @Override
    public FileDto getFile(Integer fileId) {
        return fileMapper.toDto(fileRepository.findById(fileId).orElse(null));
    }

    @Override
    public List<FileDto> getAllFiles() {
        return fileMapper.toDto(fileRepository.findAll());
    }
}
