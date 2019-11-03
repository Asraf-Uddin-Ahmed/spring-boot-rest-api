package com.asraf.rsrc;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.cfg.reveng.DelegatingReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.TableIdentifier;
import org.hibernate.mapping.MetaAttribute;

public class EntityGenerationStrategy extends DelegatingReverseEngineeringStrategy {

	public EntityGenerationStrategy(ReverseEngineeringStrategy delegate) {
		super(delegate);
	}

	@Override
	public Map<String, MetaAttribute> tableToMetaAttributes(TableIdentifier tableIdentifier) {

		Map<String, MetaAttribute> metaAttributes = super.tableToMetaAttributes(tableIdentifier);
		if (metaAttributes == null) {
			metaAttributes = new HashMap<String, MetaAttribute>();
		}

		// String className = super.tableToClassName(tableIdentifier);
		// int dotIndex = className.lastIndexOf('.');
		// String abstractClassName = className.substring(dotIndex + 1) + "Base";
		String abstractClassName = "BaseEntity";

		// Update extends modifier
		if (!metaAttributes.containsKey("scope-class")) {
			MetaAttribute metaAttribute = new MetaAttribute("extends");
			metaAttribute.addValue(abstractClassName);
			metaAttributes.put(metaAttribute.getName(), metaAttribute);
		}

		return metaAttributes;
	}

}