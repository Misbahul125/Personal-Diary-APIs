package com.collegegroup.personaldiary.services.implementations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.collegegroup.personaldiary.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImageFile(String path, MultipartFile multipartFile) throws IOException {

		// get file name
		String fileName = multipartFile.getOriginalFilename();

		// create full path
		String randomUID = UUID.randomUUID().toString();
		String updatedFileName = randomUID.concat(fileName.substring(fileName.lastIndexOf('.')));

		String filePath = path + File.separator + updatedFileName;

		// create folder images if not
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}

		// copy file
		Files.copy(multipartFile.getInputStream(), Paths.get(filePath));

		return updatedFileName;

	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {

		String fullPath = path + File.separator + fileName;
		InputStream inputStream = new FileInputStream(fullPath);

		return inputStream;

	}

	@Override
	public boolean deleteImage(String path, String imageName) {

		try {
			String imagePath = path + File.separator + imageName;

			File file = new File(imagePath);

			//System.out.println(imagePath);

			file.delete();
			//System.out.println("deleted");
			return true;
		} catch (Exception e) {

			e.printStackTrace();
			return false;

		}

	}

}
