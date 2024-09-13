package com.ntdsoftware.homework.casillas.common.repository;

import com.ntdsoftware.homework.casillas.common.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RecordRepository extends JpaRepository<Record, Integer>, JpaSpecificationExecutor<Record> {

}
