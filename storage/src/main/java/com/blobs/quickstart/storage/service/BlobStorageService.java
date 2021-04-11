package com.blobs.quickstart.storage.service;

import com.azure.storage.blob.BlobContainerAsyncClient;
import com.blobs.quickstart.storage.model.FileItem;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

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
        // TODO
    }

    public void delete(String fileName) {
        // TODO
    }

    public byte[] download(String fileName) {
        // TODO
        return new byte[0];
    }
}
