/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mabit.ctb.entity.report;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author mario
 */
public class ReportContainer {
    private List<Gain> gains;
    private List<Income> incomes;
    private List<Donation> donations;
    private HashMap<String, List<Hold>> hold;

    public ReportContainer(List<Gain> gains, List<Income> incomes, List<Donation> donations, HashMap<String, List<Hold>> hold) {
        this.gains = gains;
        this.incomes = incomes;
        this.donations = donations;
        this.hold = hold;
    }

    public List<Gain> getGains() {
        return gains;
    }

    public List<Income> getIncomes() {
        return incomes;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public HashMap<String, List<Hold>> getHold() {
        return hold;
    }

}
