package rest;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Notebook on 09.12.2015.
 */
public class S3Service {

    final static Logger logger = Logger.getLogger(S3Service.class);

    private static AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider("s3"));
    private static String bucketName = "flightradaremail";

    /**
     * Legt eine Datei auf das S3 Bucket.
     * @param file Datei
     * @return True wenn geglückt
     */
    protected static boolean putToS3(File file){
        boolean isUploadSuccess=false;

        try {
            s3Client.putObject(new PutObjectRequest(
                    bucketName, file.getName(), file));
            isUploadSuccess=true;
        } catch (AmazonClientException e) {
            logger.error(e.getMessage());
        }
        return isUploadSuccess;
    }

    /**
     * Holt Datei vom S3 Bucket.
     * @param keyName Dateiname im Bucket
     * @return Datei
     */
    protected static File getFromS3(String keyName){
        S3Object object;
        File file = new File(keyName);
        try {
            object = s3Client.getObject(new GetObjectRequest(bucketName, keyName));
            IOUtils.copy(object.getObjectContent(), new FileOutputStream(file));
                    } catch (AmazonClientException e) {
           logger.error(e.getMessage());
        } catch (FileNotFoundException e) {
           logger.error(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * Löscht Datei aus dem Bucket.
     * @param keyName Dateiname im Bucket
     * @return True wenn erfolgreich
     */
    protected static boolean deleteFromS3(String keyName){
        boolean isDeleteSuccess= false;
        try {
            s3Client.deleteObject(new DeleteObjectRequest(bucketName, keyName));
            isDeleteSuccess=true;
        } catch (AmazonClientException e) {
            logger.error(e.getMessage());

        }
        return isDeleteSuccess;
    }


}
