package org.labcabrera.samples.mongo.ddd.commons.model.security;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QApiUser is a Querydsl query type for ApiUser
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QApiUser extends EntityPathBase<ApiUser> {

    private static final long serialVersionUID = -1122830858L;

    public static final QApiUser apiUser = new QApiUser("apiUser");

    public final BooleanPath accountNonExpired = createBoolean("accountNonExpired");

    public final BooleanPath accountNonLocked = createBoolean("accountNonLocked");

    public final DateTimePath<java.time.LocalDateTime> activation = createDateTime("activation", java.time.LocalDateTime.class);

    public final CollectionPath<org.springframework.security.core.GrantedAuthority, org.springframework.security.core.QGrantedAuthority> authorities = this.<org.springframework.security.core.GrantedAuthority, org.springframework.security.core.QGrantedAuthority>createCollection("authorities", org.springframework.security.core.GrantedAuthority.class, org.springframework.security.core.QGrantedAuthority.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> credentialsExpiration = createDateTime("credentialsExpiration", java.time.LocalDateTime.class);

    public final BooleanPath credentialsNonExpired = createBoolean("credentialsNonExpired");

    public final StringPath email = createString("email");

    public final BooleanPath enabled = createBoolean("enabled");

    public final DateTimePath<java.time.LocalDateTime> expiration = createDateTime("expiration", java.time.LocalDateTime.class);

    public final StringPath id = createString("id");

    public final DateTimePath<java.time.LocalDateTime> locked = createDateTime("locked", java.time.LocalDateTime.class);

    public final StringPath password = createString("password");

    public final ListPath<String, StringPath> roles = this.<String, StringPath>createList("roles", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath username = createString("username");

    public QApiUser(String variable) {
        super(ApiUser.class, forVariable(variable));
    }

    public QApiUser(Path<? extends ApiUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QApiUser(PathMetadata metadata) {
        super(ApiUser.class, metadata);
    }

}

