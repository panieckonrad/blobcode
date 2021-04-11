package com.blobs.quickstart;

/*
 * export AZURE_STORAGE_CONNECTION_STRING="<yourconnectionstring>"
 */
import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import org.apache.log4j.PropertyConfigurator;
import java.io.*;

public class App
{
    public static void main( String[] args ) throws IOException
    {
        String log4jConfPath = "log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);

        System.out.println("Hello Azure Storage!");
        String connectStr = System.getenv("AZURE_STORAGE_CONNECTION_STRING");
        System.out.println(connectStr);


        // Create a BlobServiceClient object which will be used to create a container client
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectStr).buildClient();

        //Create a unique name for the container
        String containerName = "blobcontainer"; // + java.util.UUID.randomUUID();

        // Create the container and return a container client object
        BlobContainerClient containerClient = blobServiceClient.createBlobContainer(containerName);

        // Create a local file in the ./data/ directory for uploading and downloading
        String localPath = "./data/";
        String fileName = "quickstart" + java.util.UUID.randomUUID() + ".txt";
        File localFile = new File(localPath + fileName);

        // Write text to the file
        FileWriter writer = new FileWriter(localPath + fileName, true);
        writer.write("Hello, World!");
        writer.close();

        // Get a reference to a blob
        BlobClient blobClient = containerClient.getBlobClient(fileName);

        System.out.println("\nUploading to Blob storage as blob:\n\t" + blobClient.getBlobUrl());

        // Upload the blob
        blobClient.uploadFromFile(localPath + fileName);

        System.out.println("\nListing blobs...");

        // List the blob(s) in the container.
        for (BlobItem blobItem : containerClient.listBlobs()) {
            System.out.println("\t" + blobItem.getName());
        }

        // Download the blob to a local file
        // Append the string "DOWNLOAD" before the .txt extension so that you can see both files.
        String downloadFileName = fileName.replace(".txt", "DOWNLOAD.txt");
        File downloadedFile = new File(localPath + downloadFileName);

        System.out.println("\nDownloading blob to\n\t " + localPath + downloadFileName);

        blobClient.downloadToFile(localPath + downloadFileName);

        /*
        // Clean up
        System.out.println("Deleting blob container...");
        containerClient.delete();

        System.out.println("Deleting the local source and downloaded files...");

        boolean isLocalDeleted = localFile.delete();
        boolean isDownloadedDeleted = downloadedFile.delete();

        if (isLocalDeleted && isDownloadedDeleted) {
            System.out.println("Deleted both files");
        } else {
            System.out.println("Couldn't delete the files!");
        }
        */
        System.out.println("Done");
    }
}