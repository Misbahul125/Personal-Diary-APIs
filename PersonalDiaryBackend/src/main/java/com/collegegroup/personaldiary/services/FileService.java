package com.collegegroup.personaldiary.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	
	public String uploadImageFile(String path, MultipartFile multipartFile) throws IOException;
	public InputStream getResource(String path, String fileName) throws FileNotFoundException;
	public boolean deleteImage(String path, String imageName) throws Exception;

}
