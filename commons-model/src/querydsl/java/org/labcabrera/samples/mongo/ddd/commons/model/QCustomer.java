package org.labcabrera.samples.mongo.ddd.commons.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomer is a Querydsl query type for Customer
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCustomer extends EntityPathBase<Customer<?>> {

    private static final long serialVersionUID = 1481043133L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomer customer = new QCustomer("customer");

    public final SimplePath<Object> additionalData = createSimple("additionalData", Object.class);

    public final ListPath<String, StringPath> authorization = this.<String, StringPath>createList("authorization", String.class, StringPath.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> birthDate = createDate("birthDate", java.time.LocalDate.class);

    public final StringPath id = createString("id");

    public final QIdCard idCard;

    public final StringPath name = createString("name");

    public final StringPath surname = createString("surname");

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QCustomer(String variable) {
        this((Class) Customer.class, forVariable(variable), INITS);
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QCustomer(Path<? extends Customer> path) {
        this((Class) path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QCustomer(PathMetadata metadata, PathInits inits) {
        this((Class) Customer.class, metadata, inits);
    }

    public QCustomer(Class<? extends Customer<?>> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.idCard = inits.isInitialized("idCard") ? new QIdCard(forProperty("idCard")) : null;
    }

}

