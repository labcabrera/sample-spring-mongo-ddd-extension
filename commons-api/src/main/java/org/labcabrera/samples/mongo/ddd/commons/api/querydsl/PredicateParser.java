package org.labcabrera.samples.mongo.ddd.commons.api.querydsl;

import java.util.Map;
import java.util.Optional;

import org.labcabrera.samples.mongo.ddd.commons.api.errors.PredicateParseException;
import org.springframework.util.StringUtils;

import com.github.vineey.rql.filter.FilterContext;
import com.github.vineey.rql.filter.parser.DefaultFilterParser;
import com.github.vineey.rql.querydsl.filter.QuerydslFilterBuilder;
import com.github.vineey.rql.querydsl.filter.QuerydslFilterParam;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;

public class PredicateParser {

	@SuppressWarnings("rawtypes")
	public Optional<Predicate> buildPredicate(String searchExpression, Map<String, Path> pathMapping) {
		if (StringUtils.isEmpty(searchExpression)) {
			return Optional.empty();
		}
		try {
			DefaultFilterParser parser = new DefaultFilterParser();
			Predicate predicate = parser.parse(searchExpression, FilterContext
				.withBuilderAndParam(new QuerydslFilterBuilder(), new QuerydslFilterParam().setMapping(pathMapping)));
			return Optional.of(predicate);
		}
		catch (Exception ex) {
			throw new PredicateParseException(searchExpression, ex);
		}
	}

}
