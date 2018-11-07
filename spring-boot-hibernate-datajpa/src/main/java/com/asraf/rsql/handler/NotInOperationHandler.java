package com.asraf.rsql.handler;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.asraf.rsql.RsqlSearchOperation;

public class NotInOperationHandler<T> extends OperationHandler<T> {

	public NotInOperationHandler(String property, List<String> arguments, Root<T> root, CriteriaBuilder builder) {
		super(property, arguments, root, builder);
	}

	@Override
	public Predicate handleOperation(RsqlSearchOperation operation) {
		if (operation != RsqlSearchOperation.NOT_IN) {
			return super.successor == null ? null : super.successor.handleOperation(operation);
		}
		return builder.not(this.getCriteriaPath(root).in(super.arguments));
	}

}
