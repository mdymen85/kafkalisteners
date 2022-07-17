package com.kafka.multiplelisteners;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<EventConsumer, Long> {

    @Modifying
    @Query(value = "insert into camel_table1(uuid, number) values(:uuid, :number)", nativeQuery = true)
    void insert1(@Param("uuid") String uuid, @Param("number") String number);

    @Modifying
    @Query(value = "insert into camel_table2(uuid, number) values(:uuid, :number)", nativeQuery = true)
    void insert2(@Param("uuid") String uuid, @Param("number") String number);

    @Modifying
    @Query(value = "insert into camel_table3(uuid, number) values(:uuid, :number)", nativeQuery = true)
    void insert3(@Param("uuid") String uuid, @Param("number") String number);

    @Modifying
    @Query(value = "insert into camel_table4(uuid, number) values(:uuid, :number)", nativeQuery = true)
    void insert4(@Param("uuid") String uuid, @Param("number") String number);

    @Modifying
    @Query(value = "insert into outbox_camel_table1(uuid, number) values(:uuid, :number)", nativeQuery = true)
    void insert_outbox1(@Param("uuid") String uuid, @Param("number") String number);

    @Modifying
    @Query(value = "insert into outbox_camel_table2(uuid, number) values(:uuid, :number)", nativeQuery = true)
    void insert_outbox2(@Param("uuid") String uuid, @Param("number") String number);

    @Modifying
    @Query(value = "insert into outbox_camel_table3(uuid, number) values(:uuid, :number)", nativeQuery = true)
    void insert_outbox3(@Param("uuid") String uuid, @Param("number") String number);

    @Modifying
    @Query(value = "insert into outbox_camel_table4(uuid, number) values(:uuid, :number)", nativeQuery = true)
    void insert_outbox4(@Param("uuid") String uuid, @Param("number") String number);

}
