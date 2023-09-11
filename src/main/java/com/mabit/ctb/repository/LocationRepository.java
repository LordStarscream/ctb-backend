package com.mabit.ctb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mabit.ctb.entity.Location;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long>{
    /**
     *
     * @param name
     * @return
     */
    Location findByName(String name);

}
