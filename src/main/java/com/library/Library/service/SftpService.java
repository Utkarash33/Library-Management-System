package com.library.Library.service;

import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class SftpService {


    private final  String host="172.16.1.18";


    private final int port=22;


    private final String username="walu";


    private final String password="1234";

    private static final String SFTP_DIRECTORY ="/home/utkarash33/ftp/";


    public void uploadFileToSftp(MultipartFile file) throws JSchException, SftpException, IOException {

        ChannelSftp sftpChannel = connect();
        try {
            File localFile = convertMultipartFileToFile(file);

            String remoteFilePath = SFTP_DIRECTORY+file.getOriginalFilename();
            System.out.println("remoteFilePath: "+remoteFilePath);
            sftpChannel.put(new FileInputStream(localFile), remoteFilePath);
        } finally {
            disconnect(sftpChannel);
        }
    }

    public void downloadFileFromSftp(String remoteFilePath, String localFilePath) throws JSchException, SftpException, IOException {
        ChannelSftp sftpChannel = connect();
        try {
            sftpChannel.get(remoteFilePath, localFilePath);
        } finally {
            disconnect(sftpChannel);
        }
    }

    private ChannelSftp connect() throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        Channel channel = session.openChannel("sftp");
        channel.connect();
        return (ChannelSftp) channel;
    }

    private void disconnect(ChannelSftp sftpChannel) {
        if (sftpChannel.isConnected()) {
            sftpChannel.disconnect();
        }
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        }
        return convertedFile;
    }


}

