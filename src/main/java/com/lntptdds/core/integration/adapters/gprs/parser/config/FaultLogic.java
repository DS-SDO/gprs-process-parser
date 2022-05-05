package com.lntptdds.core.integration.adapters.gprs.parser.config;

import java.util.ArrayList;

public class FaultLogic {


    public static ArrayList<String> parseFaultVariable3(int faultValue) {
//        System.out.println(faultVariable);
        ArrayList<String> returnFaultsList = new ArrayList<>();
//        long faultValue = Double.doubleToLongBits(faultVariable);
        if ((faultValue & 4) == 4) {
            returnFaultsList.add("VA");
        }
        if ((faultValue & 16) == 16) {
            returnFaultsList.add("VOLTAGE-UNBAL");
        }
        if ((faultValue & 32) == 32) {
            returnFaultsList.add("CAP-CURT-UNBAL");
        }
        if ((faultValue & 64) == 64) {
            returnFaultsList.add("CONTROL-FAULT");
        }
        if ((faultValue & 256) == 256) {
            returnFaultsList.add("VOLTAGE-THD");
        }
        if ((faultValue & 512) == 512) {
            returnFaultsList.add("CURRENT-THD");
        }
        if ((faultValue & 32768) == 32768) {
            returnFaultsList.add("CBF");
        }
        return returnFaultsList;
    }

    public static ArrayList<String> parseFaultVariable2(int faultValue) {
//        System.out.println(faultVariable);
        ArrayList<String> returnFaultsList = new ArrayList<>();
//        long faultValue = Double.doubleToLongBits(faultVariable);
        if ((faultValue & 1) == 1) {
            returnFaultsList.add("OCC-B");
        }
        if ((faultValue & 2) == 2) {
            returnFaultsList.add("UCC-B");
        }
        if ((faultValue & 4) == 4) {
            returnFaultsList.add("OF");
        }
        if ((faultValue & 8) == 8) {
            returnFaultsList.add("UF");
        }
        if ((faultValue & 16) == 16) {
            returnFaultsList.add("OVER-TEMP-INT");
        }
        if ((faultValue & 32) == 32) {
            returnFaultsList.add("CC-THD-R");
        }
        if ((faultValue & 64) == 64) {
            returnFaultsList.add("CC-THD-Y");
        }
        if ((faultValue & 128) == 128) {
            returnFaultsList.add("CC-THD-B");
        }
        if ((faultValue & 256) == 256) {
            returnFaultsList.add("OB");
        }
        if ((faultValue & 512) == 512) {
            returnFaultsList.add("LU");
        }
        if ((faultValue & 2048) == 2048) {
            returnFaultsList.add("OVER-TEMP-EXT");
        }
        if ((faultValue & 8192) == 8192) {
            returnFaultsList.add("PU");
        }
        if ((faultValue & 16384) == 16384) {
            returnFaultsList.add("RTC-BAT");
        }
        if ((faultValue & 32768) == 32768) {
            returnFaultsList.add("NVRAM-FAULT");
        }
        return returnFaultsList;
    }

    public static ArrayList<String> parseFaultVariable1(int faultValue) {
//        System.out.println(faultVariable);
        ArrayList<String> returnFaultsList = new ArrayList<>();

//        long faultValue = Double.doubleToLongBits(faultVariable);
        if ((faultValue & 1) == 1) {
//            returnFaultsList.add("R OV: R phase Over Voltage");
            returnFaultsList.add("R-OV");
        }
        if ((faultValue & 2) == 2) {
            returnFaultsList.add("R-UV");
        }
        if ((faultValue & 4) == 4) {
            returnFaultsList.add("Y-OV");
        }
        if ((faultValue & 8) == 8) {
            returnFaultsList.add("Y-UV");
        }
        if ((faultValue & 16) == 16) {
            returnFaultsList.add("B-OV");
        }
        if ((faultValue & 32) == 32) {
            returnFaultsList.add("B-UV");
        }
        if ((faultValue & 64) == 64) {
            returnFaultsList.add("R-OL");
        }
        if ((faultValue & 128) == 128) {
            returnFaultsList.add("R-UL");
        }
        if ((faultValue & 256) == 256) {
            returnFaultsList.add("Y-OL");
        }
        if ((faultValue & 512) == 512) {
            returnFaultsList.add("Y-UL");
        }
        if ((faultValue & 1024) == 1024) {
            returnFaultsList.add("B-OL");
        }
        if ((faultValue & 2048) == 2048) {
            returnFaultsList.add("B-UL");
        }
        if ((faultValue & 4096) == 4096) {
            returnFaultsList.add("OCC");
        }
        if ((faultValue & 8192) == 8192) {
            returnFaultsList.add("UCC-R");
        }
        if ((faultValue & 16384) == 16384) {
            returnFaultsList.add("OCC-Y");
        }
        if ((faultValue & 32768) == 32768) {
            returnFaultsList.add("UCC-Y");
        }
        return returnFaultsList;

    }


}
