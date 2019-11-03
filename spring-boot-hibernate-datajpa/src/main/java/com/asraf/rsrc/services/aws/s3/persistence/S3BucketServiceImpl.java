package com.asraf.rsrc.services.aws.s3.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.asraf.rsrc.services.aws.s3.S3BucketService;

@Service
public class S3BucketServiceImpl implements S3BucketService {

	protected AmazonS3 s3Client;

	@Autowired
	public S3BucketServiceImpl(AmazonS3 s3Client) {
		this.s3Client = s3Client;
	}

	public boolean isBucketExists(String bucketName) {
		return s3Client.doesBucketExistV2(bucketName);
	}

	public void createBucket(String bucketName) throws Exception {
		if (this.isBucketExists(bucketName)) {
			throw new Exception(bucketName + " - bucket already exists!");
		}
		CreateBucketRequest request = new CreateBucketRequest(bucketName, s3Client.getRegionName());
		s3Client.createBucket(request);
	}

	public void deleteBucket(String bucketName) throws Exception {
		if (this.isBucketExists(bucketName)) {
			throw new Exception(bucketName + " - bucket already exists!");
		}
		s3Client.deleteBucket(bucketName);
	}

	public List<Bucket> getAllS3Buckets() {
		return s3Client.listBuckets();
	}

	public ObjectListing showBucketSummary(String bucketName) {
		ObjectListing objectListing = s3Client.listObjects(new ListObjectsRequest().withBucketName(bucketName));
		return objectListing;
	}

}
