package com.banking.api.repository;

import com.banking.api.domain.QDeviceRate;
import com.banking.api.domain.dto.DeviceRateDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CustomDeviceRateRepositoryImpl implements CustomDeviceRateRepository {

    @PersistenceContext
    EntityManager em;

    private final JPAQueryFactory jpaQueryFactory;

    public CustomDeviceRateRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;

    }

    @Override
    public List<DeviceRateDto> findTopRateDeviceEachYear() {

        String sql = "SELECT B.DEVICE_ID AS DEVICE_ID, " +
                            "C.DEVICE_NAME AS DEVICE_NAME, " +
                            "B.YEAR AS YEAR, " +
                            "B.RATE AS RATE " +
                     "FROM ( SELECT YEAR AS YEAR, MAX(RATE) AS RATE " +
                            "FROM DEVICE_RATE_TB " +
                            "GROUP BY YEAR ) A, " +
                            "DEVICE_RATE_TB B, DEVICE_TB C " +
                     "WHERE A.RATE = B.RATE AND A.YEAR = B.YEAR " +
                            "AND B.DEVICE_ID = C.DEVICE_ID " +
                     "GROUP BY B.YEAR, B.RATE ";

        List<Object[]> list= em.createNativeQuery(sql).getResultList();
        return list.parallelStream().map(objects -> {
            return new DeviceRateDto.Builder()
                    .setDeviceId(Long.valueOf(objects[0].toString()))
                    .setDeviceName((String)objects[1])
                    .setYear((String)objects[2])
                    .setRate((BigDecimal)objects[3])
                    .build();
        }).collect(Collectors.toList());

        /* T_T 인라인뷰 미지원 NATIVE SQL 로 급 선회..
        QDeviceRate deviceRate = QDeviceRate.deviceRate;
        return jpaQueryFactory.select(Projections.bean(DeviceRateDto.class
                    ,deviceRate.deviceId.as("deviceId")
                    ,deviceRate.device.deviceName.as("deviceName")
                    ,deviceRate.year.as("year")
                    ,deviceRate.rate.as("rate")
                ))
                .from(deviceRate)
                .where(deviceRate.rate.in(
                        JPAExpressions.select(deviceRate.rate.max())
                                .from(deviceRate)
                                .groupBy(deviceRate.year)
                ))
                .fetch();*/
    }

    @Override
    public List<DeviceRateDto> findTopRateDeviceByYear(String year) {
        QDeviceRate deviceRate = QDeviceRate.deviceRate;
        return jpaQueryFactory.select(Projections.bean(DeviceRateDto.class
                    ,deviceRate.device.deviceName.as("deviceName")
                    ,deviceRate.year.as("year")
                    ,deviceRate.rate.as("rate")))
                .from(deviceRate)
                .where(deviceRate.year.eq(year)
                        ,deviceRate.rate.eq(JPAExpressions.select(deviceRate.rate.max())
                                .from(deviceRate)
                                .where(deviceRate.year.eq(year))))
                .orderBy(deviceRate.year.asc())
                .fetch();
    }

    @Override
    public List<DeviceRateDto> findTopRateYearByDeviceId(Long deviceId) {
        QDeviceRate deviceRate = QDeviceRate.deviceRate;
        return jpaQueryFactory.select(Projections.bean(DeviceRateDto.class
                    ,deviceRate.device.deviceName.as("deviceName")
                    ,deviceRate.year.as("year")
                    ,deviceRate.rate.as("rate")))
                .from(deviceRate)
                .where(deviceRate.deviceId.eq(deviceId)
                        ,deviceRate.rate.eq(JPAExpressions.select(deviceRate.rate.max())
                                .from(deviceRate)
                                .where(deviceRate.deviceId.eq(deviceId))))
                .orderBy(deviceRate.year.asc())
                .fetch();
    }

    @Override
    public List<DeviceRateDto> findRateByDeviceIdPredictNextYearRate(Long deviceId) {
        QDeviceRate deviceRate = QDeviceRate.deviceRate;
        return jpaQueryFactory.select(Projections.bean(DeviceRateDto.class
                ,deviceRate.device.deviceName.as("deviceName")
                ,deviceRate.year.as("year")
                ,deviceRate.rate.as("rate")))
                .from(deviceRate)
                .where(deviceRate.deviceId.eq(deviceId))
                .orderBy(deviceRate.year.asc())
                .fetch();
    }
}
