package org.labcabrera.samples.mongo.ddd.commons.data;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface ApiRepository<E> extends PagingAndSortingRepository<E, String>, QuerydslPredicateExecutor<E> {

}
