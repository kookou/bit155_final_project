package kr.or.bit3004.cloud;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class ListBuckets {

	public static void listBuckets(String projectId) {
		projectId = "final-project-281709";
		Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
		Page<Bucket> buckets = storage.list();
		for(Bucket bucket : buckets.iterateAll()) {
			//System.out.println(bucket.getName());
		}
	}
}
