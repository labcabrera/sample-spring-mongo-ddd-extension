package org.labcabrera.samples.mongo.ddd.commons.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContract is a Querydsl query type for Contract
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QContract extends EntityPathBase<Contract<?>> {

    private static final long serialVersionUID = 307920369L;

    public static final QContract contract = new QContract("contract");

    public final SimplePath<Object> additionalData = createSimple("additionalData", Object.class);

    public final ListPath<String, StringPath> authorization = this.<String, StringPath>createList("authorization", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath contractNumber = createString("contractNumber");

    public final DatePath<java.time.LocalDate> effective = createDate("effective", java.time.LocalDate.class);

    public final StringPath id = createString("id");

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QContract(String variable) {
        super((Class) Contract.class, forVariable(variable));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QContract(Path<? extends Contract> path) {
        super((Class) path.getType(), path.getMetadata());
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QContract(PathMetadata metadata) {
        super((Class) Contract.class, metadata);
    }

}

