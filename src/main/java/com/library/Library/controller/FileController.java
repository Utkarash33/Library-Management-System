package com.library.Library.controller;

import com.library.Library.service.SftpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/files")
public class FileController {


    @Autowired
    private SftpService sftpService;

    @PostMapping("")
    public ResponseEntity<String> uploadFileToSftp(@RequestParam("file") MultipartFile file) {
        try {
            sftpService.uploadFileToSftp(file);
            return ResponseEntity.ok("File uploaded to SFTP server successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file to SFTP server: " + e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<String> downloadFileFromSftp(@RequestParam("remoteFilePath") String remoteFilePath, @RequestParam("localFilePath") String localFilePath) {
        try {
            sftpService.downloadFileFromSftp(remoteFilePath, localFilePath);
            return ResponseEntity.ok("File downloaded from SFTP server successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to download file from SFTP server: " + e.getMessage());
        }
    }
}
