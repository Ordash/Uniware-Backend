package com.uniware.hackathonpractice.material.web;

import com.uniware.hackathonpractice.material.exception.FileStorageException;
import com.uniware.hackathonpractice.material.exception.WrongExtensionException;
import com.uniware.hackathonpractice.material.persistence.model.DBFile;
import com.uniware.hackathonpractice.material.service.DBFileStorageService;
import com.uniware.hackathonpractice.material.utility.ErrorResponse;
import com.uniware.hackathonpractice.material.utility.UploadFileResponse;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private DBFileStorageService dbFileStorageService;

    @Autowired
    public FileController(DBFileStorageService dbFileStorageService) {
        this.dbFileStorageService = dbFileStorageService;
    }

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) throws FileStorageException, WrongExtensionException, IOException, FileUploadBase.FileSizeLimitExceededException {


        DBFile dbFile = dbFileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        List<UploadFileResponse> list = new ArrayList<>();
        for (MultipartFile file : files) {
            UploadFileResponse uploadFileResponse = null;
            try {
                uploadFileResponse = uploadFile(file);
            } catch (FileStorageException | WrongExtensionException | IOException | FileUploadBase.FileSizeLimitExceededException e) {
                e.printStackTrace();
            }
            list.add(uploadFileResponse);
        }
        return list;
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        DBFile dbFile = dbFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

    @ResponseBody
    @ExceptionHandler(FileStorageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse fileStorageHadler(FileStorageException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(WrongExtensionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse wrongExtensionHandler(WrongExtensionException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(FileUploadBase.FileSizeLimitExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse fileSizeTooLargeHandler(FileUploadBase.FileSizeLimitExceededException ex) {
        return new ErrorResponse(ex.getMessage());
    }
}
