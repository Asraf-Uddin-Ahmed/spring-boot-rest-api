package com.asraf.rsrc.resources.presignedurls;

import java.util.Date;

import com.asraf.rsrc.dtos.response.PresignedUrlResponseDto;
import com.asraf.rsrc.resources.BaseResource;

import lombok.Getter;

@Getter
public abstract class PresignedUrlResource extends BaseResource {

	private final PresignedUrlResponseDto response;

	public PresignedUrlResource(final String presignedUrl, final String publicUrl, final String filePath,
			final Date expirationTime) {

		this.response = PresignedUrlResponseDto.builder().presignedUrl(presignedUrl).publicUrl(publicUrl)
				.filePath(filePath).expirationTime(expirationTime).build();

	}

}