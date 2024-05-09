/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tools;

//import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Hello
 */
public class ImageUploader {
    
    public static void saveImage(){
        
    }
    public static void sendImage(File imageFile,int id,String endPoint) {
       try {
        // Create a URL object for the Symfony server's /api/uploadImage route
        URL url = new URL("http://localhost:8000/" + endPoint + "/" + id);

        // Create an HTTPURLConnection object for the URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // Create a ByteArrayOutputStream to hold the image data
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Read the image data from the image file into the ByteArrayOutputStream
        FileInputStream fileInputStream = new FileInputStream(imageFile);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        // Get the byte array representation of the image data
        byte[] imageData = outputStream.toByteArray();

        // Set the Content-Type header to indicate that the request body contains image data
        String fileName = imageFile.getName();
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        String contentType;
        switch (fileType) {
            case "jpg":
            case "jpeg":
                contentType = "image/jpeg";
                break;
            case "png":
                contentType = "image/png";
                break;
            case "gif":
                contentType = "image/gif";
                break;
            default:
                contentType = "application/octet-stream";
                break;
        }

        // Write the image data to the request body
        String boundary = "===" + System.currentTimeMillis() + "===";
        DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
        dataOutputStream.writeBytes("--" + boundary + "\r\n");
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"image\"; filename=\"" + "file" + "\"\r\n");
        dataOutputStream.writeBytes("Content-Type: " + contentType + "\r\n");
        dataOutputStream.writeBytes("\r\n");
        dataOutputStream.write(imageData);
        dataOutputStream.writeBytes("\r\n--" + boundary + "--\r\n");
        dataOutputStream.flush();
        dataOutputStream.close();

        // Check the response code to see if the upload was successful
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Image upload successful");
            System.out.println(connection.getResponseCode());
        } else {
            System.out.println("Image upload failed with response code " + responseCode);
            System.out.println(connection.getResponseCode());
        }

        // Disconnect the connection
        connection.disconnect();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    
    
}
