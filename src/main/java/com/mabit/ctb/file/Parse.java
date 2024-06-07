/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mabit.ctb.file;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author mario
 */
@Slf4j
public final class Parse {

    private Parse(){
        throw new IllegalStateException("it is a final class");
    }

    /*
      Parse a Textfield containing  double Value to a nullable Double
      Throws an Exception if no Double value is in the field
     */

    public static Double formatStringToDouble(String string) {
        Double res = null;
        try {
            res = (string != null && !string.isEmpty()) ? Representation.priceNumberFormat().parse(string).doubleValue() : null;
        } catch (ParseException e) {
            log.error("Error Parsing <"+string+"> into Double",e);
        }
        return res;
    }

    public static String priceDoubleToString(Double number) {
        return Representation.getPriceFormat(number);
    }

    public static Double stringToDouble(String string) {
        return  (string != null && !string.isEmpty()) ? Double.parseDouble(string) : null;
    }

    public static Double stringToDoublePositive(String string) {
        var result = stringToDouble(string);
        if (result == null)
            return null;
        else{
            if (result < 0)
                return -result;
        }
        return  result;
    }

    public static String doubleToString(Double value) {
        return (value != null) ? value.toString() : null;
    }

    public static LocalDateTime dateTimePickerToLocalDateTime(LocalDate date, LocalTime time) {
        if (date != null && time != null) {
            return date.atTime(time);
        } else {
            return null;
        }
    }
}
