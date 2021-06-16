package edu.bostonu.aws.copylogstos3.repositories;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class S3Repository {
	
	private AmazonS3 s3;
	
	public S3Repository(String profile, String region) {
		
        AWSCredentials credentials = null;
        try {
            credentials = new ProfileCredentialsProvider("radiant_user").getCredentials();
        } catch (Exception e) {
            System.out.println("Cannot load the credentials from the credential profiles file. ");
            e.printStackTrace();
        }
        
        s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
	}
	
	public List<String> getBucketFiles(String bucketName, String prefix) {
		
		List<String> bucketContents = new ArrayList<>();
		
        ObjectListing objectListing = s3.listObjects(new ListObjectsRequest()
                .withBucketName(bucketName)
                .withPrefix(prefix));
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            System.out.println(" - " + objectSummary.getKey());
            bucketContents.add(objectSummary.getKey().substring(objectSummary.getKey().lastIndexOf("/")+1));
        }
        
        return bucketContents;
		
	}
	
	public void copyFileToS3(String bucketName, String prefix, File file) {
		
		s3.putObject(new PutObjectRequest(bucketName + "/" + prefix, file.getName(), file));
		
	}
	
	

}
