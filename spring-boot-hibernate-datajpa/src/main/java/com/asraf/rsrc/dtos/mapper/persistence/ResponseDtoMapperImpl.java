package com.asraf.rsrc.dtos.mapper.persistence;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.asraf.rsrc.dtos.mapper.ResponseDtoMapper;
import com.asraf.rsrc.dtos.response.entities.BaseEntityResponseDto;
import com.asraf.rsrc.entities.BaseEntity;

public abstract class ResponseDtoMapperImpl<TEntity extends BaseEntity, TResponseDto extends BaseEntityResponseDto>
		extends DtoMapperImpl implements ResponseDtoMapper<TEntity, TResponseDto> {

	private final Class<TResponseDto> tResponseDtoType;

	protected ResponseDtoMapperImpl(final ModelMapper modelMapper, final Class<TResponseDto> responseDtoType) {
		super(modelMapper);
		this.tResponseDtoType = responseDtoType;
	}

	public final TResponseDto getResponseDto(final TEntity entity) {
		return super.modelMapper.map(entity, tResponseDtoType);
	}

	public final List<TResponseDto> getResponseDtos(final Iterable<TEntity> entities) {
		return StreamSupport.stream(entities.spliterator(), false).map(entity -> getResponseDto(entity))
				.collect(Collectors.toList());
	}

	public final List<TResponseDto> getResponseDtos(final Collection<TEntity> entities) {
		return entities.stream().map(entity -> getResponseDto(entity)).collect(Collectors.toList());
	}

	public final Page<TResponseDto> getResponseDtos(final Page<TEntity> pageEntity) {
		Page<TResponseDto> pagedResponseDto = new PageImpl<TResponseDto>(this.getResponseDtos(pageEntity.getContent()),
				pageEntity.getPageable(), pageEntity.getTotalElements());
		return pagedResponseDto;
	}

	protected final ResponseDtoMapperImpl<TEntity, TResponseDto> setEntityToResponsePropertyMap(
			final PropertyMap<TEntity, TResponseDto> entityToResponsePropertyMap) {
		if (entityToResponsePropertyMap != null) {
			super.modelMapper.addMappings(entityToResponsePropertyMap);
		}
		return this;
	}

	protected final <TSource, TDestination> ResponseDtoMapperImpl<TEntity, TResponseDto> setNestedObjectPropertyMap(
			final PropertyMap<TSource, TDestination> nestedObjectPropertyMap) {
		if (nestedObjectPropertyMap != null) {
			super.modelMapper.addMappings(nestedObjectPropertyMap);
		}
		return this;
	}
}
