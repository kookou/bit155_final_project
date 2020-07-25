package kr.or.bit3004.cloud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class ListObject {
	public static void listObjects(String projectId, String bucketName, ArrayList<CloudUpload> list) {
		
		Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
		Bucket bucket = storage.get(bucketName);
		Page<Blob> blobs = bucket.list();
		
		for(Blob blob : blobs.iterateAll()) {
			CloudUpload file = new CloudUpload();
			file.setFileName(blob.getName());
			file.setFileSize(blob.getSize());
			file.setFileLink(blob.getMediaLink());
			//list.add(blob.getName());
			//list.add(String.valueOf(blob.getSize()));
			list.add(file);
		}
		for(CloudUpload i : list) {
		    System.out.println(i.toString());
		}

	}
}
