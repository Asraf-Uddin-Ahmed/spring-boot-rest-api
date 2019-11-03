package com.asraf.rsrc.dtos.request;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ListOfRequestDto<TReqestDto extends BaseRequestDto> extends BaseRequestDto {
	@NotEmpty
	private List<@NotNull TReqestDto> requestList;
}
