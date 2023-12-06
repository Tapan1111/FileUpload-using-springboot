package com.tapan.LearnFileUpload.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tapan.LearnFileUpload.Repository.StorageRepository;
import com.tapan.LearnFileUpload.entity.ImageData;
import com.tapan.LearnFileUpload.utils.ImageUtils;

@Service
public class StorageService {

	@Autowired
	private StorageRepository storageRepository;

	public String uploadFile(MultipartFile file) throws IOException {

		ImageData imageData = storageRepository.save(ImageData.builder().name(file.getOriginalFilename())
				.type(file.getContentType()).imagedata(ImageUtils.compressImage(file.getBytes())).build());
		if (imageData != null) {
			return "file uploaded successfully : " + file.getOriginalFilename();
		}
		return null;
	}

	public byte[] downloadImage(String fileName) {
		Optional<ImageData> dbImageData = storageRepository.findByName(fileName);
		byte[] images = ImageUtils.decompressImage(dbImageData.get().getImagedata());
		return images;

	}

}
