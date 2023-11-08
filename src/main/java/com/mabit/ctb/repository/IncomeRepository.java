package com.mabit.ctb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mabit.ctb.entity.report.Income;


@Repository
public interface IncomeRepository extends CrudRepository<Income, Long>
{
}