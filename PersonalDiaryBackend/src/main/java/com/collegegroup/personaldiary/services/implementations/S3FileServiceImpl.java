package com.collegegroup.personaldiary.services.implementations;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.collegegroup.personaldiary.services.S3FileService;

@Service
public class S3FileServiceImpl implements S3FileService {

	@Value("${bucketName}")
	private String bucketName;

	private final AmazonS3 amazonS3;

	public S3FileServiceImpl(AmazonS3 amazonS3) {
		this.amazonS3 = amazonS3;
	}

	@Override
	public List<String> saveFile(List<MultipartFile> multipartFiles) {
		
		List<String> uploadedFileNames = new ArrayList<>();
		
		try {
			
			List<String> fileNames = getCustomizedFileNames(multipartFiles);
			List<File> files = convertMultipartToFile(multipartFiles);
			
			int i=0;
			
			for(File file : files) {
				PutObjectResult putObject = amazonS3
						.putObject(new PutObjectRequest(bucketName, fileNames.get(i), file)
								.withCannedAcl(CannedAccessControlList.PublicReadWrite));
				
				uploadedFileNames.add(amazonS3.getUrl(bucketName, fileNames.get(i++)).toString());
			}
			
			return uploadedFileNames;
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public byte[] downloadFile(String fileName) {
		
		S3Object s3Object = amazonS3.getObject(bucketName, fileName);
		S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
		try {
			return IOUtils.toByteArray(s3ObjectInputStream);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public List<byte[]> downloadFiles(List<String> fileNames) {
		
		List<byte[]> files = new ArrayList<>();
		
		for(String fileName : fileNames) {
			S3Object s3Object = amazonS3.getObject(bucketName, fileName);
			S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
			try {
				files.add(IOUtils.toByteArray(s3ObjectInputStream));
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		return files;
		
	}

	@Override
	public List<String> getAllFiles() {
		
		ListObjectsV2Result listObjectsV2Result = amazonS3.listObjectsV2(bucketName);
		
		return listObjectsV2Result
				.getObjectSummaries()
				.stream()
				.map(s3Object -> s3Object.getKey())
				.collect(Collectors.toList());
		
	}

	@Override
	public String updateFile(String fileURLToBeDeleted, MultipartFile multipartFile) {
		
		List<String> fileURLsToBeDeleted = new ArrayList<>();
		fileURLsToBeDeleted.add(fileURLToBeDeleted);
		
		List<MultipartFile> multipartFiles = new ArrayList<>();
		multipartFiles.add(multipartFile);
		
		List<String> savedFileURLs = saveFile(multipartFiles);
		
//		List<String> fileNames = getCustomizedFileNames(multipartFiles);
//
//		try {
//			List<File> files = convertMultipartToFile(multipartFiles);
//			
//			PutObjectResult putObject = amazonS3.putObject(bucketName, fileNames.get(0), files.get(0));
//		} catch (IOException e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
		
		boolean isDeleted = deleteFiles(fileURLsToBeDeleted);
		
		return savedFileURLs.get(0);
		
	}

	@Override
	public boolean deleteFiles(List<String> fileURLs) {
		
		for(String fileURL : fileURLs) {
			
			String[] urlParts = fileURL.split("/");
	        //String bucketName = urlParts[3];
	        String objectKey = String.join("/", Arrays.copyOfRange(urlParts, 4, urlParts.length));
	        
	        // Create a DeleteObjectRequest with the bucket name and object key
	        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, objectKey);
	        
	        // Delete the object
	        amazonS3.deleteObject(deleteObjectRequest);

		}
		
		return true;
		
	}

	private List<String> getCustomizedFileNames(List<MultipartFile> multipartFiles) {

		List<String> fileNames = new ArrayList<>();

		for (MultipartFile multipartFile : multipartFiles) {
			String randomUID = UUID.randomUUID().toString();
			String updatedFileName = randomUID.concat(multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf('.')));
			//String fileName = System.currentTimeMillis() + "_"+ (multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf('.')));

			fileNames.add(updatedFileName);
		}

		return fileNames;

	}

	private List<File> convertMultipartToFile(List<MultipartFile> multipartFiles) throws IOException {

		List<File> files = new ArrayList<>();

		for (MultipartFile multipartFile : multipartFiles) {
			File convertedFile = new File(multipartFile.getOriginalFilename());
			FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
			fileOutputStream.write(multipartFile.getBytes());
			fileOutputStream.close();

			files.add(convertedFile);
		}

		return files;

	}
	
}
