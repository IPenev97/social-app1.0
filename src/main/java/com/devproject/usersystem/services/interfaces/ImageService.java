package com.devproject.usersystem.services.interfaces;

import com.devproject.usersystem.data.models.ImageModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ImageService {
    byte[] compressBytes(byte[] data);
    byte[] decompressBytes(byte[] data);
    Optional<ImageModel> getImageByName(String name);
    ImageModel saveImage(MultipartFile file) throws IOException;
    ImageModel saveImage(byte[] bytes) throws IOException;
    List<ImageModel> getAllImages();
    void delete(ImageModel image);

}
