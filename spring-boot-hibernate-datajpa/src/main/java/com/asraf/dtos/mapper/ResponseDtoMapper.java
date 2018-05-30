package com.asraf.dtos.mapper;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

import com.asraf.dtos.response.BaseEntityResponseDto;
import com.asraf.entities.BaseEntity;

public interface ResponseDtoMapper<TEntity extends BaseEntity, TResponseDto extends BaseEntityResponseDto> extends DtoMapper {

	TResponseDto getResponseDto(TEntity entity);

	List<TResponseDto> getResponseDtos(Iterable<TEntity> entities);

	List<TResponseDto> getResponseDtos(Collection<TEntity> entities);

	Page<TResponseDto> getResponseDtos(Page<TEntity> pageEntity);

}
