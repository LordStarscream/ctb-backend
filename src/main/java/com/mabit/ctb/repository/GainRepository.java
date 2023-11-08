package com.mabit.ctb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mabit.ctb.entity.report.Gain;


@Repository
public interface GainRepository extends CrudRepository<Gain, Long>
{
}