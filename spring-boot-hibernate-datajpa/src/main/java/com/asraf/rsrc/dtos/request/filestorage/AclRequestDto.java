package com.asraf.rsrc.dtos.request.filestorage;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.asraf.rsrc.validators.EnumValueConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AclRequestDto extends FileObjectRequestDto {
	@EnumValueConstraint(enumClass = CannedAccessControlList.class, ignoreCase = false)
	private String cannedAccessControlList;
}
