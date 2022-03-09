package com.example.querydslinflearn.support;

import com.mysema.commons.lang.Assert;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public abstract class QueryDsl4RepositorySupport {
    private final Class domainClass;
    private Querydsl querydsl;
    private EntityManager entityManager;
    private JPAQueryFactory jpaQueryFactory;

    public QueryDsl4RepositorySupport(Class<?> domainClass) {
        Assert.notNull(domainClass, "Domain class must not be null");
        this.domainClass = domainClass;
    }


}
