package com.kafka.relayerconsumer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelayerInfoRepository extends CrudRepository<RelayerInfo, Long> {
}
