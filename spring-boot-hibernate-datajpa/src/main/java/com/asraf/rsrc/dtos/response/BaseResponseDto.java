package com.asraf.rsrc.dtos.response;

import com.asraf.rsrc.constants.UserRoleResponse;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
@JsonView(UserRoleResponse.Anonymous.class)
public abstract class BaseResponseDto {

}
