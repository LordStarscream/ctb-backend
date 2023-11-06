package com.mabit.ctb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mabit.ctb.entity.report.Donation;


@Repository
public interface DonationRepository extends CrudRepository<Donation, Long>
{
}