package com.asdc.funderbackend.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.IOException;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FirebaseFileStorageServiceImplTest {

	 @Mock
	 private ResourceLoader resourceLoader;

	 @Mock
	 private ApplicationReadyEvent applicationReadyEvent;

	 @Mock
	 private Storage storage;

	 @InjectMocks
	 private FirebaseFileStorageServiceImpl firebaseFileStorageService;

	 @Test
	 void uploadFileTest() throws IOException {
	     MockMultipartFile mockFile = new MockMultipartFile(
	                "file",
	                "test.txt",
	                "text/plain",
	                "Hello, World!".getBytes()
	     );

	     when(storage.create(any(BlobInfo.class), any(byte[].class)))
	             .thenReturn(mock(Blob.class));

	     String result = firebaseFileStorageService.uploadFile(mockFile);

	     assertNotNull(result);
	    }
}
