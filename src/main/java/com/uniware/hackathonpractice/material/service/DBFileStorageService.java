package com.uniware.hackathonpractice.material.service;

import com.uniware.hackathonpractice.material.exception.FileStorageException;
import com.uniware.hackathonpractice.material.exception.MyFileNotFoundException;
import com.uniware.hackathonpractice.material.exception.WrongExtensionException;
import com.uniware.hackathonpractice.material.persistence.model.DBFile;
import com.uniware.hackathonpractice.material.persistence.repository.DBFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DBFileStorageService {

    private DBFileRepository DBFileRepository;

    @Autowired
    public DBFileStorageService(DBFileRepository DBFileRepository) {
        this.DBFileRepository = DBFileRepository;
    }

    public DBFile storeFile(MultipartFile file) throws FileStorageException, WrongExtensionException {

        String fileExtentions = ".jpg,.pdf";
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        int lastIndex = fileName.lastIndexOf('.');
        String substring = fileName.substring(lastIndex);

        if(fileExtentions.contains(substring)) {
            try {
                if (fileName.contains("..")) {
                    throw new FileStorageException("Filename contains invalid path sequence " + fileName);
                }

                DBFile DBFile = new DBFile(fileName, file.getContentType(), file.getBytes());

                return DBFileRepository.save(DBFile);

            } catch (Exception ex) {

                throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
            }

        } else {
            throw new WrongExtensionException("File is not supported for upload!");
        }
    }

    public DBFile getFile(String fileId) throws MyFileNotFoundException {
        return DBFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }



}
