package com.asraf.rsrc.dtos.response.errors;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CsvValidationErrorResponseDto extends ApiSubErrorResponseDto {

	private int row;
	private List<ApiValidationErrorResponseDto> errors;

	public CsvValidationErrorResponseDto(int row) {
		this.row = row;
		this.errors = new ArrayList<>();
	}

	public void addError(ApiValidationErrorResponseDto apiValidationErrorResponseDto) {
		this.errors.add(apiValidationErrorResponseDto);
	}

	public boolean hasErrors() {
		return !this.errors.isEmpty();
	}

}
