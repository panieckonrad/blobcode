package com.blobs.quickstart.storage.service;

import com.azure.storage.blob.BlobContainerAsyncClient;
import com.blobs.quickstart.storage.model.FileItem;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlobStorageService {

    private final BlobContainerAsyncClient blobContainerAsyncClient;

    public BlobStorageService(BlobContainerAsyncClient blobContainerAsyncClient) {
        this.blobContainerAsyncClient = blobContainerAsyncClient;
    }

    public List<FileItem> listOfFiles() {
        return blobContainerAsyncClient.listBlobs().toStream()
                .map(blobItem -> {
                    return new FileItem(blobItem.getName());
                })
                .collect(Collectors.toList());
    }

    public void upload(Resource resource) throws IOException {

        String fileName = resource.getFilename();
        //String fileAbsolutePath = resource.getFile().getPath();
        String fileAbsolutePath ="/Users/konrad.paniec/Desktop/pictures/konrado.txt";
        var blob =  blobContainerAsyncClient.getBlobAsyncClient(fileName);
        blob.uploadFromFile(fileAbsolutePath);

    }

    public void delete(String fileName) {
        var blob = this.blobContainerAsyncClient.getBlobAsyncClient(fileName);
        blob.delete();
    }

    public byte[] download(String fileName) {
        String localPath = "/Users/konrad.paniec/Desktop/pictures/";
        File downloadedFile = new File(localPath + fileName);

        System.out.println("\nDownloading blob to\n\t " + localPath + fileName);

        blobContainerAsyncClient.getBlobAsyncClient(fileName).downloadToFile(localPath + fileName);

        return new byte[0];
    }
}
