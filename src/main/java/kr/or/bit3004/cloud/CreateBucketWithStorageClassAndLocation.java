package kr.or.bit3004.cloud;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageClass;
import com.google.cloud.storage.StorageOptions;

public class CreateBucketWithStorageClassAndLocation {
	public static void createBucketWithStorageClassAndLocation(String projectId, String bucketName) {
		Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
		System.out.println("이거당2/2");
		StorageClass storageClass = StorageClass.STANDARD;
		String location = "asia";
		System.out.println("이거당2/4");
		Bucket bucket =
				storage.create(
					BucketInfo.newBuilder(bucketName)
					.setStorageClass(storageClass)
					.setLocation(location)
					.build());					
		System.out.println(
			"Created bucket "+bucket.getName()+" in "+bucket.getLocation()+" with storage class "+bucket.getStorageClass());
		
	}
}
