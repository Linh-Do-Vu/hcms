package com.example.ezhcm.service.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IFilesStorageService {
    public void init();

    public String save(MultipartFile file,Long idAttachment);

    public Resource load(String filename);

    public void deleteAll();

    public Stream<Path> loadAll();
}
