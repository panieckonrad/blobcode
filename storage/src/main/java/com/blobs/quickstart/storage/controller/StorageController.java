package com.blobs.quickstart.storage.controller;

import com.blobs.quickstart.storage.model.SuccessfulOperation;
import com.blobs.quickstart.storage.service.BlobStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private BlobStorageService blobStorageService;

    @GetMapping(value = "/files", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getListOfFiles() {
        return blobStorageService.listOfFiles();
    }

    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object handleFileUpload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        blobStorageService.upload(multipartFile.getResource());
        return new ResponseEntity(new SuccessfulOperation(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{fileName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object handleFileUpload(@PathVariable String fileName) throws IOException {
        blobStorageService.delete(fileName);
        return new ResponseEntity(new SuccessfulOperation(), HttpStatus.OK);
    }

    @GetMapping(value = "/download/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    byte[] handleFileDownload(HttpServletResponse response, @PathVariable String fileName) throws IOException {
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        return blobStorageService.download(fileName);
    }
}
