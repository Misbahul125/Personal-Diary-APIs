package com.collegegroup.personaldiary.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface S3FileService {

	public List<String> saveFile(List<MultipartFile> multipartFiles);
	
	public byte[] downloadFile(String fileName);
	
	public List<byte[]> downloadFiles(List<String> fileNames);
	
	public List<String> getAllFiles();
	
	public String updateFile(String existingFileName, MultipartFile multipartFile);
	
	public boolean deleteFiles(List<String> fileNames);
	
}
