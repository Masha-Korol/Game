package com.netcracker.game.services.mapstruct;

import com.netcracker.game.data.model.File;
import com.netcracker.game.services.dto.FileDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class FileMapper extends com.netcracker.game.services.mapstruct.Mapper<File, FileDto> {

//    @Mappings({
//            @Mapping(target = "type", source = "contentType"),
//            @Mapping(target = "name", source = "originalFilename"),
//            @Mapping(target = "data", source = "bytes")
//    })
//    public abstract File toEntity(MultipartFile file) throws IOException;
}
