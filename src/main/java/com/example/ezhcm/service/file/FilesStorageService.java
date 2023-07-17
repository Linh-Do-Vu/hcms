package com.example.ezhcm.service.file;

import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.model.Log;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
@Service
public class FilesStorageService implements IFilesStorageService {
//    private final Path root = Paths.get("upload");

    private final Path root = Paths.get("/opt/ezhospital/upload");

    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }


    @Override
    public String save(MultipartFile file,Long idAttachment) {
        try {
            String originalFilename = file.getOriginalFilename();
            String newFilename = idAttachment + "-" + originalFilename; // Thay đổi tên file

            Path destination = this.root.resolve(newFilename);

            Files.copy(file.getInputStream(), destination);
            Log.info("FilesStorageService save" +newFilename);
            return newFilename ;
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                Log.error("A file of that name already exists");
                throw new RuntimeException(e) ;
            }

            Log.error(e.getMessage());
            throw new RuntimeException(e.getMessage()) ;

        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                Log.error("Could not read the file!");
                throw new CustomException(ErrorCode.NOT_FOUND,"Không thể đọc file !");
            }
        } catch (MalformedURLException e) {
            Log.error(e.getMessage());
            throw  new CustomException(ErrorCode.NOT_FOUND,"Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.NOT_FOUND,"Không thể đọc file !");
        }
    }


}

