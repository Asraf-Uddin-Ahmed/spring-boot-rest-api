package com.asraf.controllers;

import java.io.IOException;
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
import com.asraf.dtos.request.filestorage.AclRequestDto;
import com.asraf.dtos.request.filestorage.FileObjectRequestDto;
import com.asraf.dtos.request.filestorage.FilesObjectRequestDto;
import com.asraf.dtos.request.filestorage.FolderObjectRequestDto;
import com.asraf.dtos.request.filestorage.PresignedUrlRequestDto;
import com.asraf.dtos.response.PresignedUrlResponseDto;
import com.asraf.dtos.response.UploadFileResponseDto;
import com.asraf.dtos.response.requestdto.RequestBodyResponseDto;
import com.asraf.exceptions.StoragePathNotFoundException;
import com.asraf.services.FileStorageService;
import com.asraf.services.aws.s3.S3ObjectService;

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

	@GetMapping("/presigned-url/request")
	public RequestBodyResponseDto<PresignedUrlRequestDto> getPresignedUrlRequest() {
		RequestBodyResponseDto<PresignedUrlRequestDto> response = new RequestBodyResponseDto<PresignedUrlRequestDto>(
				PresignedUrlRequestDto.class).withSuperClass(1);
		return response;
	}

	@PutMapping("/acl")
	public void setAcl(@Valid @RequestBody AclRequestDto requestDto) {
		s3ObjectService.setAcl(requestDto.getFilePath(),
				CannedAccessControlList.valueOf(requestDto.getCannedAccessControlList()));
	}

	@GetMapping("/acl/request")
	public RequestBodyResponseDto<AclRequestDto> getAclRequest() {
		RequestBodyResponseDto<AclRequestDto> response = new RequestBodyResponseDto<AclRequestDto>(AclRequestDto.class)
				.withSuperClass(1);
		return response;
	}

	@PutMapping("/make-public")
	public void makePublic(@Valid @RequestBody FileObjectRequestDto requestDto) {
		s3ObjectService.makePublic(requestDto.getFilePath());
	}

	@GetMapping("/make-public/request")
	public RequestBodyResponseDto<FileObjectRequestDto> getMakePublicRequest() {
		RequestBodyResponseDto<FileObjectRequestDto> response = new RequestBodyResponseDto<FileObjectRequestDto>(
				FileObjectRequestDto.class);
		return response;
	}

	@DeleteMapping("/file")
	public void deleteFile(@Valid @RequestBody FileObjectRequestDto requestDto) {
		if (!s3ObjectService.isExists(requestDto.getFilePath())) {
			throw new StoragePathNotFoundException("filePath", requestDto.getFilePath());
		}
		s3ObjectService.delete(requestDto.getFilePath());
	}

	@GetMapping("/file/request")
	public RequestBodyResponseDto<FileObjectRequestDto> getDeleteFileRequest() {
		RequestBodyResponseDto<FileObjectRequestDto> response = new RequestBodyResponseDto<FileObjectRequestDto>(
				FileObjectRequestDto.class);
		return response;
	}

	@DeleteMapping("/files")
	public DeleteObjectsResult deleteFiles(@Valid @RequestBody FilesObjectRequestDto requestDto) {
		return s3ObjectService.delete(requestDto.getFilePaths());
	}

	@GetMapping("/files/request")
	public RequestBodyResponseDto<FilesObjectRequestDto> getDeleteFilesRequest() {
		RequestBodyResponseDto<FilesObjectRequestDto> response = new RequestBodyResponseDto<FilesObjectRequestDto>(
				FilesObjectRequestDto.class);
		return response;
	}

	@DeleteMapping("/folder")
	public DeleteObjectsResult deleteFolder(@Valid @RequestBody FolderObjectRequestDto requestDto) {
		try {
			return s3ObjectService.deleteAllObjects(requestDto.getFolderPath());
		} catch (AmazonS3Exception e) {
			throw new StoragePathNotFoundException("folderPath", requestDto.getFolderPath());
		}
	}

	@GetMapping("/folder/request")
	public RequestBodyResponseDto<FolderObjectRequestDto> getDeleteFolderRequest() {
		RequestBodyResponseDto<FolderObjectRequestDto> response = new RequestBodyResponseDto<FolderObjectRequestDto>(
				FolderObjectRequestDto.class);
		return response;
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
}
