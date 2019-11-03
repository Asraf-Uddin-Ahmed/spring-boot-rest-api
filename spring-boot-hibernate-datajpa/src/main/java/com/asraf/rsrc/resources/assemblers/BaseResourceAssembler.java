package com.asraf.rsrc.resources.assemblers;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.asraf.rsrc.controllers.BaseController;
import com.asraf.rsrc.entities.BaseEntity;
import com.asraf.rsrc.resources.BaseResource;

public abstract class BaseResourceAssembler<TEntity extends BaseEntity, TResource extends BaseResource>
		extends ResourceAssemblerSupport<TEntity, TResource> {

	public BaseResourceAssembler(Class<? extends BaseController> controllerClass, Class<TResource> resourceClass) {
		super(controllerClass, resourceClass);
	}

}
