package com.asdc.funderbackend.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/**
 * The interface Firebase file storage service.
 */
public interface FirebaseFileStorageService {
	/**
	 * Upload file string.
	 *
	 * @param file the file
	 * @return the string
	 * @throws IOException the io exception
	 */
	String uploadFile(MultipartFile file) throws IOException;
}
