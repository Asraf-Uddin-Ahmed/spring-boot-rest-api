package com.asraf.rsql.handler;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.asraf.rsql.RsqlSearchOperation;

public class GreaterThanOrEqualOperationHandler<T> extends OperationHandler<T> {

	public GreaterThanOrEqualOperationHandler(String property, List<String> arguments, Root<T> root,
			CriteriaBuilder builder) {
		super(property, arguments, root, builder);
	}

	@Override
	public Predicate handleOperation(RsqlSearchOperation operation) {
		if (operation != RsqlSearchOperation.GREATER_THAN_OR_EQUAL) {
			return super.successor == null ? null : super.successor.handleOperation(operation);
		}

		if (argument instanceof Date) {
			return builder.greaterThanOrEqualTo(super.getCriteriaPath(root), (Date) argument);
		}
		return builder.greaterThanOrEqualTo(super.getCriteriaPath(root), argument.toString());
	}

}
