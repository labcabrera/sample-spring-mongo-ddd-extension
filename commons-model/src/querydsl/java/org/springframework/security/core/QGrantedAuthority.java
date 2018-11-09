package org.springframework.security.core;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QGrantedAuthority is a Querydsl query type for GrantedAuthority
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QGrantedAuthority extends BeanPath<GrantedAuthority> {

    private static final long serialVersionUID = 1264551024L;

    public static final QGrantedAuthority grantedAuthority = new QGrantedAuthority("grantedAuthority");

    public final StringPath authority = createString("authority");

    public QGrantedAuthority(String variable) {
        super(GrantedAuthority.class, forVariable(variable));
    }

    public QGrantedAuthority(Path<? extends GrantedAuthority> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGrantedAuthority(PathMetadata metadata) {
        super(GrantedAuthority.class, metadata);
    }

}

