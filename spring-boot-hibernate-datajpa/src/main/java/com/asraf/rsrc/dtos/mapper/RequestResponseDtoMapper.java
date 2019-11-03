package com.asraf.rsrc.dtos.mapper;

import com.asraf.rsrc.dtos.request.BaseRequestDto;
import com.asraf.rsrc.dtos.response.entities.BaseEntityResponseDto;
import com.asraf.rsrc.entities.BaseEntity;

public interface RequestResponseDtoMapper<TEntity extends BaseEntity, TResponseDto extends BaseEntityResponseDto, TRequestDto extends BaseRequestDto>
		extends ResponseDtoMapper<TEntity, TResponseDto> {

	TEntity getEntity(TRequestDto requestDto);

	void loadEntity(TRequestDto requestDto, TEntity entity);

}
