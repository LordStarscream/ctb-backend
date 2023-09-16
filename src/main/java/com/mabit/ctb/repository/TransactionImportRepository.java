/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mabit.ctb.repository;

import com.mabit.ctb.entity.TransactionImport;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Mario Bittner <MarioBittner@gmx.de>
 */
public interface TransactionImportRepository extends CrudRepository<TransactionImport, Long> {
}
