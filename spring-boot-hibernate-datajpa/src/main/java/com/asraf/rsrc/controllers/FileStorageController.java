package com.asraf.rsrc.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.asraf.rsrc.dtos.request.filestorage.AclRequestDto;
import com.asraf.rsrc.dtos.request.filestorage.FileObjectRequestDto;
import com.asraf.rsrc.dtos.request.filestorage.FilesObjectRequestDto;
import com.asraf.rsrc.dtos.request.filestorage.FolderObjectRequestDto;
import com.asraf.rsrc.dtos.request.filestorage.PresignedUrlRequestDto;
import com.asraf.rsrc.dtos.response.PresignedUrlResponseDto;
import com.asraf.rsrc.dtos.response.UploadFileResponseDto;
import com.asraf.rsrc.dtos.response.requestdto.RequestBodyResponseDto;
import com.asraf.rsrc.dtos.response.requestdto.RequestDataCollectionResponseDto;
import com.asraf.rsrc.exceptions.StoragePathNotFoundException;
import com.asraf.rsrc.services.FileStorageService;
import com.asraf.rsrc.services.aws.s3.S3ObjectService;

@RestController
@RequestMapping("/file-storage")
public class FileStorageController {

	private FileStorageService fileStorageService;
	private S3ObjectService s3ObjectService;

	@Autowired
	public FileStorageController(S3ObjectService s3ObjectService, FileStorageService fileStorageService) {
		this.s3ObjectService = s3ObjectService;
		this.fileStorageService = fileStorageService;
	}

	@PostMapping("/presigned-url")
	public PresignedUrlResponseDto getPresignedUrl(@Valid @RequestBody PresignedUrlRequestDto requestDto) {
		Date expirationTime = s3ObjectService.getExpirationTime(requestDto.getExpirationInMinute());
		String presignedUrl = s3ObjectService.getPreSignedUrl(requestDto.getFilePath(),
				HttpMethod.valueOf(requestDto.getHttpMethod().toUpperCase()), expirationTime).toString();
		String publicUrl = s3ObjectService.getBucketUrl(requestDto.getFilePath());
		return PresignedUrlResponseDto.builder().presignedUrl(presignedUrl).publicUrl(publicUrl)
				.filePath(requestDto.getFilePath()).expirationTime(expirationTime).build();
	}

	@PutMapping("/acl")
	public ResponseEntity<Void> setAcl(@Valid @RequestBody AclRequestDto requestDto) {
		s3ObjectService.setAcl(requestDto.getFilePath(),
				CannedAccessControlList.valueOf(requestDto.getCannedAccessControlList()));
		return null;
	}

	@PutMapping("/make-public")
	public ResponseEntity<Void> makePublic(@Valid @RequestBody FileObjectRequestDto requestDto) {
		s3ObjectService.makePublic(requestDto.getFilePath());
		return null;
	}

	@DeleteMapping("/file")
	public ResponseEntity<Void> deleteFile(@Valid @RequestBody FileObjectRequestDto requestDto) {
		if (!s3ObjectService.isExists(requestDto.getFilePath())) {
			throw new StoragePathNotFoundException("filePath", requestDto.getFilePath());
		}
		s3ObjectService.delete(requestDto.getFilePath());
		return null;
	}

	@DeleteMapping("/files")
	public DeleteObjectsResult deleteFiles(@Valid @RequestBody FilesObjectRequestDto requestDto) {
		return s3ObjectService.delete(requestDto.getFilePaths());
	}

	@DeleteMapping("/folder")
	public DeleteObjectsResult deleteFolder(@Valid @RequestBody FolderObjectRequestDto requestDto) {
		try {
			return s3ObjectService.deleteAllObjects(requestDto.getFolderPath());
		} catch (AmazonS3Exception e) {
			throw new StoragePathNotFoundException("folderPath", requestDto.getFolderPath());
		}
	}

	@PostMapping("/upload")
	public UploadFileResponseDto uploadFile(@RequestParam("file") MultipartFile file) {
		String fileName = fileStorageService.storeFile(file);
		return UploadFileResponseDto.builder().fileName(fileName).fileType(file.getContentType()).size(file.getSize())
				.build();
	}

	@PostMapping("/upload-multiple")
	public List<UploadFileResponseDto> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files).stream().map(file -> uploadFile(file)).collect(Collectors.toList());
	}

	@GetMapping("/download/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request)
			throws IOException {
		Resource resource = fileStorageService.loadFileAsResource(fileName);

		String contentType = null;
		contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@GetMapping("/requests")
	public RequestDataCollectionResponseDto getRequests() {
		RequestDataCollectionResponseDto requestDataCollection = new RequestDataCollectionResponseDto();
		this.addRequestDataOfPresignedUrl(requestDataCollection).addRequestDataOfAcl(requestDataCollection)
				.addRequestDataOfMakePublic(requestDataCollection).addRequestDataOfDeleteFile(requestDataCollection)
				.addRequestDataOfDeleteFiles(requestDataCollection).addRequestDataOfDeleteFolder(requestDataCollection);
		return requestDataCollection;
	}

	private FileStorageController addRequestDataOfPresignedUrl(RequestDataCollectionResponseDto requestDataCollection) {
		RequestBodyResponseDto<PresignedUrlRequestDto> requestBody = new RequestBodyResponseDto<PresignedUrlRequestDto>(
				PresignedUrlRequestDto.class).withSuperClass(1);
		URI uri = linkTo(methodOn(FileStorageController.class).getPresignedUrl(null)).toUri();
		requestDataCollection.addRequest(uri, org.springframework.http.HttpMethod.POST, requestBody);
		return this;
	}

	private FileStorageController addRequestDataOfAcl(RequestDataCollectionResponseDto requestDataCollection) {
		RequestBodyResponseDto<AclRequestDto> requestBody = new RequestBodyResponseDto<AclRequestDto>(
				AclRequestDto.class).withSuperClass(1);
		URI uri = linkTo(methodOn(FileStorageController.class).setAcl(null)).toUri();
		requestDataCollection.addRequest(uri, org.springframework.http.HttpMethod.PUT, requestBody);
		return this;
	}

	private FileStorageController addRequestDataOfMakePublic(RequestDataCollectionResponseDto requestDataCollection) {
		RequestBodyResponseDto<FileObjectRequestDto> requestBody = new RequestBodyResponseDto<FileObjectRequestDto>(
				FileObjectRequestDto.class);
		URI uri = linkTo(methodOn(FileStorageController.class).makePublic(null)).toUri();
		requestDataCollection.addRequest(uri, org.springframework.http.HttpMethod.PUT, requestBody);
		return this;
	}

	private FileStorageController addRequestDataOfDeleteFile(RequestDataCollectionResponseDto requestDataCollection) {
		RequestBodyResponseDto<FileObjectRequestDto> requestBody = new RequestBodyResponseDto<FileObjectRequestDto>(
				FileObjectRequestDto.class);
		URI uri = linkTo(methodOn(FileStorageController.class).deleteFile(null)).toUri();
		requestDataCollection.addRequest(uri, org.springframework.http.HttpMethod.DELETE, requestBody);
		return this;
	}

	private FileStorageController addRequestDataOfDeleteFiles(RequestDataCollectionResponseDto requestDataCollection) {
		RequestBodyResponseDto<FilesObjectRequestDto> requestBody = new RequestBodyResponseDto<FilesObjectRequestDto>(
				FilesObjectRequestDto.class);
		URI uri = linkTo(methodOn(FileStorageController.class).deleteFiles(null)).toUri();
		requestDataCollection.addRequest(uri, org.springframework.http.HttpMethod.DELETE, requestBody);
		return this;
	}

	private FileStorageController addRequestDataOfDeleteFolder(RequestDataCollectionResponseDto requestDataCollection) {
		RequestBodyResponseDto<FolderObjectRequestDto> requestBody = new RequestBodyResponseDto<FolderObjectRequestDto>(
				FolderObjectRequestDto.class);
		URI uri = linkTo(methodOn(FileStorageController.class).deleteFolder(null)).toUri();
		requestDataCollection.addRequest(uri, org.springframework.http.HttpMethod.DELETE, requestBody);
		return this;
	}

}
