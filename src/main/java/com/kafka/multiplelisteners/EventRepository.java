package com.kafka.multiplelisteners;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<EventConsumer, Long> {
}
