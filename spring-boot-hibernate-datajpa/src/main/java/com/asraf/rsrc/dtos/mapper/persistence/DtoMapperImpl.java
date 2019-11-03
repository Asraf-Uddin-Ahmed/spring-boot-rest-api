package com.asraf.rsrc.dtos.mapper.persistence;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import com.asraf.rsrc.dtos.mapper.DtoMapper;

public abstract class DtoMapperImpl implements DtoMapper {

	protected final ModelMapper modelMapper;

	protected DtoMapperImpl(final ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
		this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

}
