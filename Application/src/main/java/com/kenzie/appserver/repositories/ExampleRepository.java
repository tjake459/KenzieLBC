package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.ItemRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface ExampleRepository extends CrudRepository<ItemRecord, String> {
}
