package com.asraf.rsrc.services.aws.s3;

import java.util.List;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;

public interface S3BucketService {

	boolean isBucketExists(String bucketName);

	void createBucket(String bucketName) throws Exception;

	void deleteBucket(String bucketName) throws Exception;

	List<Bucket> getAllS3Buckets();

	ObjectListing showBucketSummary(String bucketName);

}