package com.banking.api.repository;

import com.banking.api.domain.DeviceRate;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRateRepository extends JpaRepository<DeviceRate, Long>, CustomDeviceRateRepository {

    /**
     * 해당 데이터를 전부조회 한다. N + 1 이슈로 EntityGraph 어노테이션 설정
     * @return
     */
    @EntityGraph("DeviceRateWithDevice")
    List<DeviceRate> findAll();

    /**
     * 기기 ID의 매년 이용율을 조회 해온다
     * @param id
     * @return
     */
    List<DeviceRate> findByDeviceId(Long id);

    /**
     * 해당 년도의 기기별 이용률을 반환한다.
     * @param year
     * @return
     */
    List<DeviceRate> findByYear(String year);

}
