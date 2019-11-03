package com.asraf.rsrc.dtos.mapper;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

import com.asraf.rsrc.dtos.response.entities.BaseEntityResponseDto;
import com.asraf.rsrc.entities.BaseEntity;

public interface ResponseDtoMapper<TEntity extends BaseEntity, TResponseDto extends BaseEntityResponseDto> extends DtoMapper {

	TResponseDto getResponseDto(TEntity entity);

	List<TResponseDto> getResponseDtos(Iterable<TEntity> entities);

	List<TResponseDto> getResponseDtos(Collection<TEntity> entities);

	Page<TResponseDto> getResponseDtos(Page<TEntity> pageEntity);

}
