package com.asraf.rsql.handler;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.asraf.rsql.RsqlSearchOperation;

public class InOperationHandler<T> extends OperationHandler<T> {

	public InOperationHandler(String property, List<String> arguments, Root<T> root, CriteriaBuilder builder) {
		super(property, arguments, root, builder);
	}

	@Override
	public Predicate handleOperation(RsqlSearchOperation operation) {
		if (operation != RsqlSearchOperation.IN) {
			return super.successor == null ? null : super.successor.handleOperation(operation);
		}
		return super.getCriteriaPath(root).in(super.arguments);
	}

}
