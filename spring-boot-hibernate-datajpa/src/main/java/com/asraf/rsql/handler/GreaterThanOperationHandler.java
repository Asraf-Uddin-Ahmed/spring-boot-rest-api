package com.asraf.rsql.handler;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.asraf.rsql.RsqlSearchOperation;

public class GreaterThanOperationHandler<T> extends OperationHandler<T> {

	public GreaterThanOperationHandler(String property, List<String> arguments, Root<T> root, CriteriaBuilder builder) {
		super(property, arguments, root, builder);
	}

	@Override
	public Predicate handleOperation(RsqlSearchOperation operation) {
		if (operation != RsqlSearchOperation.GREATER_THAN) {
			return super.successor == null ? null : super.successor.handleOperation(operation);
		}

		if (argument instanceof Date) {
			return builder.greaterThan(super.getCriteriaPath(root), (Date) argument);
		}
		return builder.greaterThan(super.getCriteriaPath(root), argument.toString());
	}

}
