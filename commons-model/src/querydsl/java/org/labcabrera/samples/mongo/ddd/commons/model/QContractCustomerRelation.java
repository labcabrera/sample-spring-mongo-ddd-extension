package org.labcabrera.samples.mongo.ddd.commons.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContractCustomerRelation is a Querydsl query type for ContractCustomerRelation
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QContractCustomerRelation extends EntityPathBase<ContractCustomerRelation<?, ?>> {

    private static final long serialVersionUID = -1446518773L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContractCustomerRelation contractCustomerRelation = new QContractCustomerRelation("contractCustomerRelation");

    public final ListPath<String, StringPath> authorization = this.<String, StringPath>createList("authorization", String.class, StringPath.class, PathInits.DIRECT2);

    public final QContract contract;

    public final QCustomer customer;

    public final StringPath id = createString("id");

    public final NumberPath<java.math.BigDecimal> percent = createNumber("percent", java.math.BigDecimal.class);

    public final EnumPath<RelationType> type = createEnum("type", RelationType.class);

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QContractCustomerRelation(String variable) {
        this((Class) ContractCustomerRelation.class, forVariable(variable), INITS);
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QContractCustomerRelation(Path<? extends ContractCustomerRelation> path) {
        this((Class) path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContractCustomerRelation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QContractCustomerRelation(PathMetadata metadata, PathInits inits) {
        this((Class) ContractCustomerRelation.class, metadata, inits);
    }

    public QContractCustomerRelation(Class<? extends ContractCustomerRelation<?, ?>> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.contract = inits.isInitialized("contract") ? new QContract(forProperty("contract")) : null;
        this.customer = inits.isInitialized("customer") ? new QCustomer(forProperty("customer"), inits.get("customer")) : null;
    }

}

