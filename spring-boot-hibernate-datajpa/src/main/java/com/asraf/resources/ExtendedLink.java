package com.asraf.resources;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;

import com.asraf.utils.ReflectionUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.Getter;

@JsonInclude(Include.NON_EMPTY)
public class ExtendedLink extends Link {

	private static final long serialVersionUID = 1L;

	@Getter
	private HttpMethod method;

	@Getter
	private String format;

	@Getter
	private String fields;

	@Getter
	private Search search;

	public ExtendedLink(Link link) {
		super(link.getHref(), link.getRel());
		this.initFormat();
		this.initFields();
	}

	public ExtendedLink withMethod(HttpMethod method) {
		this.method = method;
		return this;
	}

	public ExtendedLink withSearchableData() {
		this.initFieldsForSearch();
		this.search = this.search == null ? new Search() : this.search;
		this.search = this.search.initParam().initSort().initPagination().initOperators().initFullExample();
		return this;
	}

	public ExtendedLink withPageableData() {
		this.initFieldsForSearch();
		this.search = this.search == null ? new Search() : this.search;
		this.search = this.search.initSort().initPagination().initPageableExample();
		return this;
	}

	public ExtendedLink withProxyProperty(String responseName, String searchName) {
		this.search = this.search == null ? new Search() : this.search;
		this.search.addProxyProperty(responseName, searchName);
		return this;
	}

	public ExtendedLink withSearchableData(String searchParam) {
		this.initFields();
		this.search = this.search == null ? new Search() : this.search;
		this.search = this.search.setParam(searchParam);
		return this;
	}

	public ExtendedLink withSearchableData(Class<?> searchClass) {
		String searchParam = "";
		for (Map.Entry<String, String> entry : ReflectionUtils.getFieldNameWithTypes(searchClass).entrySet()) {
			searchParam += ("&" + entry.getKey() + "={" + entry.getValue() + "}");
		}
		searchParam = searchParam.substring(1);
		this.withSearchableData(searchParam);
		return this;
	}

	public ExtendedLink withNullFormatAndFields() {
		this.format = null;
		this.fields = null;
		return this;
	}

	private void initFormat() {
		this.format = "format={hal_json | json | xml}&fields={fields}";
	}

	private void initFields() {
		this.fields = "fields={-}{* | ** | property | property.* | property.childProperty | property.child* | property[childProperty1,childProperty2] | (property1,property2)[childProperty]}{,}";
	}

	private void initFieldsForSearch() {
		this.fields = "fields={-}{* | ** | content | content.* | content.property | content.prop* | content.property.childProperty | content.property[childProperty1,childProperty2] | (content.property1,content.property2)[childProperty]}{,}";
	}

	@Data
	@JsonInclude(Include.NON_EMPTY)
	private class Search {
		private String param;
		private String sort;
		private String pagination;
		private Map<String, String> operators;
		private Map<String, String> proxyProperties = new LinkedHashMap<>();
		private String example;

		public Search initParam() {
			this.param = "search={resourceProperty | resourceProperty.parentProperty}{operators}{value | _null_}{Logical AND | Logical OR}";
			return this;
		}

		public Search initSort() {
			this.sort = "sort={property},{asc | desc}";
			return this;
		}

		public Search initPagination() {
			this.pagination = "page={intValue}&size={intValue}";
			return this;
		}

		public Search initOperators() {
			this.operators = new LinkedHashMap<>();
			this.operators.put("Logical AND", "; | and");
			this.operators.put("Logical OR", ", | or");
			this.operators.put("Equal to", "==");
			this.operators.put("Not equal to", "!=");
			this.operators.put("Less than", "=lt= | <");
			this.operators.put("Less than or equal to", "=le= | <=");
			this.operators.put("Greater than", "=gt= | >");
			this.operators.put("Greater than or equal to", "=ge= | >=");
			this.operators.put("In", "=in=");
			this.operators.put("Not in", "=out=");
			this.operators.put("Parent access operator", ".");
			this.operators.put("Like operator", "*");
			return this;
		}

		public Search initFullExample() {
			this.example = "...endpoint?search=(prop1==val1*;prop2>val2,prop3==val3);prop4=in=(val4,val5,val6)&page=0&size=10&sort=prop5,asc&sort=prop6,desc";
			return this;
		}

		public Search initPageableExample() {
			this.example = "...endpoint?page=0&size=10&sort=prop5,asc&sort=prop6,desc";
			return this;
		}

		public Search addProxyProperty(String responseName, String searchName) {
			this.proxyProperties.put(responseName, searchName);
			return this;
		}

		public Search setParam(String param) {
			this.param = param;
			return this;
		}

	}

}