package com.asraf.rsql.handler;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.asraf.rsql.RsqlSearchOperation;

public class EqualOperationHandler<T> extends OperationHandler<T> {

	public EqualOperationHandler(String property, List<String> arguments, Root<T> root, CriteriaBuilder builder) {
		super(property, arguments, root, builder);
	}

	@Override
	public Predicate handleOperation(RsqlSearchOperation operation) {
		if (operation != RsqlSearchOperation.EQUAL) {
			return super.successor == null ? null : super.successor.handleOperation(operation);
		}

		if (argument instanceof String) {
			String arugmentStr = argument.toString();
			if (super.getCriteriaPath(root).getJavaType().equals(boolean.class)
					|| super.getCriteriaPath(root).getJavaType().equals(Boolean.class)) {
				return this.getEqualPredicateForBooleanArgument(root, builder, arugmentStr);
			} else if (super.getCriteriaPath(root).getJavaType().equals(String.class)) {
				return builder.like(super.getCriteriaPath(root), arugmentStr.replace('*', '%'));
			}
			return builder.equal(super.getCriteriaPath(root), argument);
		} else if (argument == null) {
			return builder.isNull(super.getCriteriaPath(root));
		} else if (argument instanceof Date) {
			return builder.equal(super.getCriteriaPath(root), (Date) argument);
		} else {
			return builder.equal(super.getCriteriaPath(root), argument);
		}
	}

	private Predicate getEqualPredicateForBooleanArgument(final Root<T> root, final CriteriaBuilder builder,
			String argument) {
		String argumentLowerCase = argument.toLowerCase();
		if (argumentLowerCase.equals("true")) {
			return builder.isTrue(this.getCriteriaPath(root));
		}
		if (argumentLowerCase.equals("false")) {
			return builder.isFalse(this.getCriteriaPath(root));
		}
		throw new IllegalArgumentException("Parameter value [" + argument
				+ "] did not match expected type [java.lang.Boolean (n/a)] for " + this.property + " field");
	}

}
