package com.example.service;

import com.example.Entity.AttachEntity;
import com.example.dto.AttachDTO;
import com.example.exp.AppBadRequestException;
import com.example.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class AttachService {

    /*Attach
    1. Upload  (ANY)
    2. Open (by id)
    3. Open general (by id)
    4. Download (by id  with origin name)
    5. Pagination (ADMIN)
    6. Delete by id (delete from system and table) (ADMIN)*/
    @Autowired
    private AttachRepository attachRepository;


    @Value("${attach.folder.name}")
    private String folderName;

    // 1.Upload
    public AttachDTO save(MultipartFile file) {
        String pathFolder = getYmDString();
        File folder = new File(folderName + "/" + pathFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String key = UUID.randomUUID().toString();
        String extension = getExtension(file.getOriginalFilename());
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(folderName + "/" + pathFolder + "/" + key + "." + extension);

            Files.write(path, bytes);

            AttachEntity entity = new AttachEntity();
            entity.setId(key);
            entity.setPath(pathFolder);
            entity.setSize(file.getSize());
            entity.setOriginalName(file.getOriginalFilename());
            entity.setExtension(extension);
            attachRepository.save(entity);

            AttachDTO attachDTO = new AttachDTO();
            attachDTO.setId(key);
            attachDTO.setOriginalName(entity.getOriginalName());
            attachDTO.setUrl("");

            return attachDTO;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);
        return year + "/" + month + "/" + day; // 2024/09/01
    }

    public String getExtension(String fileName) { // mp3/jpg/npg/mp4.....
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    public byte[] loadImage(String fileName) {
        try {
            BufferedImage originalImage = ImageIO.read(new File("attaches/" + fileName));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "png", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (Exception e) {
            return new byte[0];
        }

    }

    public byte[] loadImageById(String id) {
        AttachEntity entity = get(id);
        try {
            String url = folderName+"/"+entity.getPath()+"/"+id+"."+entity.getExtension();
            BufferedImage originalImage = ImageIO.read(new File(url));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, entity.getExtension(), baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (Exception e) {
            return new byte[0];
        }
    }

    public byte[] openGeneral(String id) {
        byte[] data;
        try {
            AttachEntity entity = get(id);
            String path = entity.getPath() + "/" + id + "." + entity.getExtension();
            Path file = Paths.get("attaches/" + path);
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    //4. Download (by id  with origin name)
    public String saveToSystem(MultipartFile file) {
        try {
            File folder = new File("attaches");
            if (!folder.exists()) {
                folder.mkdir();
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get("attaches/" + file.getOriginalFilename());
            Files.write(path, bytes);
            return file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public byte[] loadByIdGeneral(String id) {
        AttachEntity entity = get(id);
        try {
            String url = folderName+"/"+entity.getPath()+"/"+id+"."+entity.getExtension();
            File files = new File(url);
            byte[] bytes = new byte[(int) files.length()];
            FileInputStream fileInputStream = new FileInputStream(files);
            fileInputStream.read(bytes);
            fileInputStream.close();
            return bytes;
        } catch (Exception e) {
            return new byte[0];
        }
    }

    public AttachEntity get(String id){
        return attachRepository.findById(id).orElseThrow(()->{
            throw new AppBadRequestException("File not found");
        });
    }

    //5. Pagination (ADMIN)
    public PageImpl<AttachEntity> getByPagination(int page,int size){
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"createdDate"));
        Page<AttachEntity> entities = attachRepository.findAll(pageable);
        List<AttachEntity> list = entities.stream().toList();
        return new PageImpl<>(list,pageable,entities.getTotalElements());

    }
    //6.Delete
    public boolean delete(String originalFilename) {
        AttachEntity entity = get(originalFilename);
            // Construct the directory path using the current date
            String directoryPath = "attaches/" + entity.getPath();
            Path filePath = Paths.get(directoryPath, originalFilename);

            // Check if the file exists
            File file = filePath.toFile();
            if (!file.exists()) {
                throw new RuntimeException("File not found: " + originalFilename);
            }

            // Attempt to delete the file
            try {
                Files.delete(filePath);
                System.out.println("File deleted successfully: " + originalFilename);
                if (attachRepository.existsById(entity.getId())) {
                    attachRepository.deleteById(entity.getId());
                    return true;
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete file: " + originalFilename, e);
            }

            return false;

    }





}
