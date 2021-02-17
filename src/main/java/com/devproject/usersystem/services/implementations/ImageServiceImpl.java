package com.devproject.usersystem.services.implementations;

import com.devproject.usersystem.data.models.ImageModel;
import com.devproject.usersystem.data.repositories.ImageRepository;
import com.devproject.usersystem.services.interfaces.ImageService;
import com.devproject.usersystem.services.interfaces.ProfileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;


    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }


    @Override
    public byte[] decompressBytes(byte[] data) {
            Inflater inflater = new Inflater();
            inflater.setInput(data);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
            byte[] buffer = new byte[1024];
            try {
                while (!inflater.finished()) {
                    int count = inflater.inflate(buffer);
                    outputStream.write(buffer, 0, count);
                }
                outputStream.close();
            } catch (IOException ioe) {
            } catch (DataFormatException e) {
            }
            return outputStream.toByteArray();
        }



    @Override
    public Optional<ImageModel> getImageByName(String name){
        return imageRepository.findByName(name);


    }

    @Override
    public ImageModel saveImage(MultipartFile file) throws IOException {

        ImageModel image = new ImageModel(file.getName(),file.getContentType(), file.getBytes());
        imageRepository.save(image);
        return image;
    }
    public ImageModel saveImage(byte[] bytes) throws IOException {

        ImageModel image = new ImageModel("image","png", bytes);
        imageRepository.save(image);
        return image;
    }
    public List<ImageModel> getAllImages(){

        return imageRepository.findAll();

    }

    @Override
    public void delete(ImageModel image) {
        imageRepository.delete(image);
    }




}
