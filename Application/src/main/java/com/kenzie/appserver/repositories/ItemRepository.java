package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.ItemRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface ItemRepository extends CrudRepository<ItemRecord, String> {
    List<ItemRecord> findByLocation(String location);
    List<ItemRecord> findAll();
}
