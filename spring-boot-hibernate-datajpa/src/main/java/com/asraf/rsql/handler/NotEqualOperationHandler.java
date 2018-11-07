package com.asraf.rsql.handler;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.asraf.rsql.RsqlSearchOperation;

public class NotEqualOperationHandler<T> extends OperationHandler<T> {

	public NotEqualOperationHandler(String property, List<String> arguments, Root<T> root, CriteriaBuilder builder) {
		super(property, arguments, root, builder);
	}

	@Override
	public Predicate handleOperation(RsqlSearchOperation operation) {
		if (operation != RsqlSearchOperation.NOT_EQUAL) {
			return super.successor == null ? null : super.successor.handleOperation(operation);
		}

		if (argument instanceof String) {
			String arugmentStr = argument.toString();
			if (super.getCriteriaPath(root).getJavaType().equals(boolean.class)
					|| super.getCriteriaPath(root).getJavaType().equals(Boolean.class)) {
				return this.getNotEqualPredicateForBooleanArgument(root, builder, arugmentStr);
			} else if (super.getCriteriaPath(root).getJavaType().equals(String.class)) {
				return builder.notLike(super.getCriteriaPath(root), arugmentStr.replace('*', '%'));
			}
			return builder.notEqual(super.getCriteriaPath(root), argument);
		} else if (argument == null) {
			return builder.isNotNull(super.getCriteriaPath(root));
		} else if (argument instanceof Date) {
			return builder.notEqual(super.getCriteriaPath(root), (Date) argument);
		} else {
			return builder.notEqual(super.getCriteriaPath(root), argument);
		}
	}

	private Predicate getNotEqualPredicateForBooleanArgument(final Root<T> root, final CriteriaBuilder builder,
			String argument) {
		String argumentLowerCase = argument.toLowerCase();
		if (argumentLowerCase.equals("false")) {
			return builder.isTrue(this.getCriteriaPath(root));
		}
		if (argumentLowerCase.equals("true")) {
			return builder.isFalse(this.getCriteriaPath(root));
		}
		throw new IllegalArgumentException("Parameter value [" + argument
				+ "] did not match expected type [java.lang.Boolean (n/a)] for " + this.property + " field");
	}
}
