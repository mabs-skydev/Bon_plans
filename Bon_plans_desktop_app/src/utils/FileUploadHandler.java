/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;

/**
 *
 * @author touir
 */
public class FileUploadHandler {
    
    private static final String url = "http://localhost/bon_plans_api/upload_file.php";
    private static final String filesDirectoryPath = "http://localhost/bon_plans_api/uploads";
    
    public static boolean uploadFile(Class<?> entityClass, int idEntity, File binaryFile) {
        try{
            String charset = "UTF-8";
            String param = "value";
            String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
            String CRLF = "\r\n"; // Line separator required by multipart/form-data.

            URLConnection connection = new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            try (
                OutputStream output = connection.getOutputStream();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
            ) {
                // Send normal param.
                writer.append("--" + boundary).append(CRLF);
                writer.append("Content-Disposition: form-data; name=\"param\"").append(CRLF);
                writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
                writer.append(CRLF).append(param).append(CRLF).flush();

                // Send binary file.
                writer.append("--" + boundary).append(CRLF);
                writer.append("Content-Disposition: form-data; name=\"fileToUpload\"; filename=\"" + binaryFile.getName() + "\"").append(CRLF);
                writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(binaryFile.getName())).append(CRLF);
                writer.append("Content-Transfer-Encoding: binary").append(CRLF);
                writer.append(CRLF).flush();
                Files.copy(binaryFile.toPath(), output);
                output.flush(); // Important before continuing with writer!
                writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.

                // End of multipart/form-data.
                writer.append("--" + boundary + "--").append(CRLF).flush();
            }

            // Request is lazily fired whenever you need to obtain information about response.
            int responseCode = ((HttpURLConnection) connection).getResponseCode();
            String responseMessage = ((HttpURLConnection) connection).getResponseMessage();
            System.out.println("Response code: "+responseCode); // Should be 200
            System.out.println("Response message: "+responseMessage);
            if(responseCode == 200){
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            LogHandler.handleException(e);
            return false;
        }
    }
    
    public static String getFileURL(String fileName, Class<?> entityClass, int idEntity){
        return filesDirectoryPath+"/"+entityClass.getSimpleName()+"_"+idEntity+"_"+fileName;
    }
}
