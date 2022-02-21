package com.netcracker.game.services.service;

import com.netcracker.game.services.dto.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IFileService {

    FileDto uploadFile(MultipartFile file) throws IOException;

    FileDto getFile(Integer fileId);

    List<FileDto> getAllFiles();
}
