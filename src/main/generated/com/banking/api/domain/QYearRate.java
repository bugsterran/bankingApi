package com.banking.api.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QYearRate is a Querydsl query type for YearRate
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QYearRate extends EntityPathBase<YearRate> {

    private static final long serialVersionUID = 1369697342L;

    public static final QYearRate yearRate = new QYearRate("yearRate");

    public final NumberPath<java.math.BigDecimal> rate = createNumber("rate", java.math.BigDecimal.class);

    public final StringPath year = createString("year");

    public QYearRate(String variable) {
        super(YearRate.class, forVariable(variable));
    }

    public QYearRate(Path<? extends YearRate> path) {
        super(path.getType(), path.getMetadata());
    }

    public QYearRate(PathMetadata metadata) {
        super(YearRate.class, metadata);
    }

}

