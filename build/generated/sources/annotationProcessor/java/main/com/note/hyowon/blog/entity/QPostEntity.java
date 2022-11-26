package com.note.hyowon.blog.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPostEntity is a Querydsl query type for PostEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostEntity extends EntityPathBase<PostEntity> {

    private static final long serialVersionUID = -306376887L;

    public static final QPostEntity postEntity = new QPostEntity("postEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath contentAsHtml = createString("contentAsHtml");

    public final StringPath contentAsMarkdown = createString("contentAsMarkdown");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastChgDateTime = _super.lastChgDateTime;

    public final NumberPath<Long> postNo = createNumber("postNo", Long.class);

    public final DatePath<java.time.LocalDate> regDate = createDate("regDate", java.time.LocalDate.class);

    public final StringPath title = createString("title");

    public final DatePath<java.time.LocalDate> updDate = createDate("updDate", java.time.LocalDate.class);

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

    public final StringPath visibleYn = createString("visibleYn");

    public final StringPath writer = createString("writer");

    public QPostEntity(String variable) {
        super(PostEntity.class, forVariable(variable));
    }

    public QPostEntity(Path<? extends PostEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPostEntity(PathMetadata metadata) {
        super(PostEntity.class, metadata);
    }

}

