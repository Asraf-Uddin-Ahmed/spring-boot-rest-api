package com.asraf.dtos.response;

import java.util.List;

import com.asraf.constants.UserRoleResponse;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@JsonView(UserRoleResponse.Anonymous.class)
public class ListOfResponseDto<TResponseDto extends BaseResponseDto> extends BaseResponseDto {
	private List<TResponseDto> responseList;
}
