package org.labcabrera.samples.mongo.ddd.app.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAppCustomer is a Querydsl query type for AppCustomer
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAppCustomer extends EntityPathBase<AppCustomer> {

    private static final long serialVersionUID = -1236271143L;

    public static final QAppCustomer appCustomer = new QAppCustomer("appCustomer");

    public final org.labcabrera.samples.mongo.ddd.commons.model.QCustomer _super = new org.labcabrera.samples.mongo.ddd.commons.model.QCustomer(this);

    //inherited
    public final ListPath<String, StringPath> authorization = _super.authorization;

    //inherited
    public final DatePath<java.time.LocalDate> birthDate = _super.birthDate;

    //inherited
    public final StringPath id = _super.id;

    //inherited
    public final SimplePath<org.labcabrera.samples.mongo.ddd.commons.model.IdCard> idCard = null; //_super.idCard;

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final StringPath surname = _super.surname;

    public QAppCustomer(String variable) {
        super(AppCustomer.class, forVariable(variable));
    }

    public QAppCustomer(Path<? extends AppCustomer> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAppCustomer(PathMetadata metadata) {
        super(AppCustomer.class, metadata);
    }

}

