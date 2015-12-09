package rest;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
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

    private AmazonS3 s3Client;
    private String bucketName;
    private String keyName;

    public S3Service(){
        this.s3Client = new AmazonS3Client(new ProfileCredentialsProvider("email"));
    }

    public boolean putToS3(File file){
        boolean isUploadSuccess=false;

        try {
            this.s3Client.putObject(new PutObjectRequest(
                    bucketName, keyName, file));
            isUploadSuccess=true;
        } catch (AmazonClientException e) {
            logger.error(e.getMessage());
        }
        return isUploadSuccess;
    }

    public File getFromS3(String fileName){
        S3Object object;
        File file = new File(fileName);
        try {
            object = this.s3Client.getObject(new GetObjectRequest(this.bucketName, this.keyName));
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
    public boolean deleteFromS3(File file){
        boolean isDeleteSuccess= false;
        try {
            this.s3Client.deleteObject(new DeleteObjectRequest(this.bucketName, this.keyName));
            isDeleteSuccess=true;
        } catch (AmazonClientException e) {
            logger.error(e.getMessage());
        }
        return isDeleteSuccess;
    }


}
