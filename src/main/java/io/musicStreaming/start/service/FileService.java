package io.musicStreaming.start.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {

    //Takes file and save it in given directory path
    public void uploadFileOfKind(MultipartFile file, String name) {
        try {
            Path path = Paths.get(pathOf(name) + "/" + file.getOriginalFilename());
            Path path2 = Paths.get(servedPathOf(name) + "/" + file.getOriginalFilename());

            if (!Files.exists(path.getParent()))
                Files.createDirectories(path.getParent());

            if (!Files.exists(path2.getParent()))
                Files.createDirectories(path2.getParent());

            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(file.getInputStream(), path2, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {/*TODO:LOG IT*/
            e.printStackTrace();
        }
    }

    public String pathOf(String name) {
        String mediaPath = "src/main/resources/static";
        return name.equals("image") ? (mediaPath + "/images") : (mediaPath + "/songs");
    }

    public String servedPathOf(String name) {
        String servedPath = "target/classes/static";
        return name.equals("image") ? (servedPath + "/images") : (servedPath + "/songs");
    }
}
