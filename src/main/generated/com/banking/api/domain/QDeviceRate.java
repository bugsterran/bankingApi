package com.banking.api.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeviceRate is a Querydsl query type for DeviceRate
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDeviceRate extends EntityPathBase<DeviceRate> {

    private static final long serialVersionUID = 887058839L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeviceRate deviceRate = new QDeviceRate("deviceRate");

    public final QDevice device;

    public final NumberPath<Long> deviceId = createNumber("deviceId", Long.class);

    public final NumberPath<java.math.BigDecimal> rate = createNumber("rate", java.math.BigDecimal.class);

    public final NumberPath<Long> seqNo = createNumber("seqNo", Long.class);

    public final StringPath year = createString("year");

    public QDeviceRate(String variable) {
        this(DeviceRate.class, forVariable(variable), INITS);
    }

    public QDeviceRate(Path<? extends DeviceRate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeviceRate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeviceRate(PathMetadata metadata, PathInits inits) {
        this(DeviceRate.class, metadata, inits);
    }

    public QDeviceRate(Class<? extends DeviceRate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.device = inits.isInitialized("device") ? new QDevice(forProperty("device")) : null;
    }

}

