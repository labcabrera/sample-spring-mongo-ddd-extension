package org.labcabrera.samples.mongo.ddd.app.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAppContract is a Querydsl query type for AppContract
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAppContract extends EntityPathBase<AppContract> {

    private static final long serialVersionUID = 1885573389L;

    public static final QAppContract appContract = new QAppContract("appContract");

    public final org.labcabrera.samples.mongo.ddd.commons.model.QContract _super = new org.labcabrera.samples.mongo.ddd.commons.model.QContract(this);

    //inherited
    public final ListPath<String, StringPath> authorization = _super.authorization;

    //inherited
    public final StringPath contractNumber = _super.contractNumber;

    //inherited
    public final DatePath<java.time.LocalDate> effective = _super.effective;

    //inherited
    public final StringPath id = _super.id;

    public QAppContract(String variable) {
        super(AppContract.class, forVariable(variable));
    }

    public QAppContract(Path<? extends AppContract> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAppContract(PathMetadata metadata) {
        super(AppContract.class, metadata);
    }

}

