package com.mabit.ctb.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mabit.ctb.entity.Account;
import com.mabit.ctb.entity.report.Report;

@Repository
public interface ReportRepository extends CrudRepository<Report, Long>
{
    /**
     *
     * @param name
     * @return
     */
    List<Report> findByYear(Integer year);

    List<Report> findByAccount(Account account);

}