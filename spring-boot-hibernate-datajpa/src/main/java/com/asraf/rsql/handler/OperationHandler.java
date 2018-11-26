package com.asraf.rsql.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.asraf.rsql.RsqlSearchOperation;
import com.asraf.utils.DateUtils;

public abstract class OperationHandler<T> {

	protected OperationHandler<T> successor;
	protected String property;
	protected List<Object> arguments;
	protected Root<T> root;
	protected CriteriaBuilder builder;
	protected Object argument;

	public abstract Predicate handleOperation(RsqlSearchOperation operation);

	public OperationHandler(String property, List<String> strArguments, Root<T> root, CriteriaBuilder builder) {
		this.property = property;
		this.root = root;
		this.builder = builder;

		this.arguments = castArguments(root, strArguments);
		this.argument = arguments.get(0);
	}

	public void setSuccessor(OperationHandler<T> successor) {
		this.successor = successor;
	}

	protected <RET> Path<RET> getCriteriaPath(final Root<T> root) {
		String[] pathObjects = property.split("[.]");
		Path<RET> criteriaPath = pathObjects.length == 1 ? root.get(pathObjects[0])
				: root.get(pathObjects[0]).<RET>get(pathObjects[1]);
		return criteriaPath;
	}

	private List<Object> castArguments(final Root<T> root, final List<String> arguments) {
		final List<Object> objArguments = new ArrayList<Object>();
		final Class<? extends Object> type = this.getCriteriaPath(root).getJavaType();

		for (final String argument : arguments) {
			if (argument.equalsIgnoreCase("_null_")) {
				objArguments.add(null);
			} else if (type.equals(Integer.class)) {
				objArguments.add(Integer.parseInt(argument));
			} else if (type.equals(Long.class)) {
				objArguments.add(Long.parseLong(argument));
			} else if (type.equals(Date.class)) {
				objArguments.add(DateUtils.parseGmtDateOrTime(argument));
			} else {
				objArguments.add(argument);
			}
		}

		return objArguments;
	}

}
