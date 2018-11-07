package com.asraf.rsql.handler;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.asraf.rsql.RsqlSearchOperation;

public class LessThanOrEqualOperationHandler<T> extends OperationHandler<T> {

	public LessThanOrEqualOperationHandler(String property, List<String> arguments, Root<T> root,
			CriteriaBuilder builder) {
		super(property, arguments, root, builder);
	}

	@Override
	public Predicate handleOperation(RsqlSearchOperation operation) {
		if (operation != RsqlSearchOperation.LESS_THAN_OR_EQUAL) {
			return super.successor == null ? null : super.successor.handleOperation(operation);
		}

		if (argument instanceof Date) {
			return builder.lessThanOrEqualTo(super.getCriteriaPath(root), (Date) argument);
		}
		return builder.lessThanOrEqualTo(super.getCriteriaPath(root), argument.toString());
	}

}
