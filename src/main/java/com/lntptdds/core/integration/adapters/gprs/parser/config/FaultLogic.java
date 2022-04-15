package com.lntptdds.core.integration.adapters.gprs.parser.config;

import java.util.ArrayList;

public class FaultLogic {


    public static ArrayList<String> parseFaultVariable3(int faultValue) {
//        System.out.println(faultVariable);
        ArrayList<String> returnFaultsList = new ArrayList<String>();
//        long faultValue = Double.doubleToLongBits(faultVariable);
        if ((faultValue & 4) == 4) {
            returnFaultsList.add("VA: Voltage Absent");
        }
        if ((faultValue & 16) == 16) {
            returnFaultsList.add("VOLTAGE UNBAL: Voltage unbalance");
        }
        if ((faultValue & 32) == 32) {
            returnFaultsList.add("CAP CURT. UNBAL: Capacitor Current unbalance");
        }
        if ((faultValue & 64) == 64) {
            returnFaultsList.add("CONTROL FAULT: Control Voltage fault");
        }
        if ((faultValue & 256) == 256) {
            returnFaultsList.add("VOLTAGE THD: Voltage total harmonic distortion");
        }
        if ((faultValue & 512) == 512) {
            returnFaultsList.add("CURRENT THD: Current total harmonic distortion");
        }
        if ((faultValue & 32768) == 32768) {
            returnFaultsList.add("CBF: Capacitor bank faulty");
        }
        return returnFaultsList;
    }

    public static ArrayList<String> parseFaultVariable2(int faultValue) {
//        System.out.println(faultVariable);
        ArrayList<String> returnFaultsList = new ArrayList<String>();
//        long faultValue = Double.doubleToLongBits(faultVariable);
        if ((faultValue & 1) == 1) {
            returnFaultsList.add("OCC B: B phase over capacitor current");
        }
        if ((faultValue & 2) == 2) {
            returnFaultsList.add("UCC B: B phase under capacitor current");
        }
        if ((faultValue & 4) == 4) {
            returnFaultsList.add("OF: Over Frequency");
        }
        if ((faultValue & 8) == 8) {
            returnFaultsList.add("UF: Under Frequency");
        }
        if ((faultValue & 16) == 16) {
            returnFaultsList.add("OVER TEMP INT.: Internal Over Temperature");
        }
        if ((faultValue & 32) == 32) {
            returnFaultsList.add("CC THD R: R phase capacitor current total harmonic distortion");
        }
        if ((faultValue & 64) == 64) {
            returnFaultsList.add("CC THD Y: Y phase capacitor current total harmonic distortion");
        }
        if ((faultValue & 128) == 128) {
            returnFaultsList.add("CC THD B: B phase capacitor current total harmonic distortion");
        }
        if ((faultValue & 256) == 256) {
            returnFaultsList.add("OB: Out of Bank");
        }
        if ((faultValue & 512) == 512) {
            returnFaultsList.add("LU: Load unbalance");
        }
        if ((faultValue & 2048) == 2048) {
            returnFaultsList.add("OVER TEMP EXT.: External Over Temperature");
        }
        if ((faultValue & 8192) == 8192) {
            returnFaultsList.add("PU: Power UP");
        }
        if ((faultValue & 16384) == 16384) {
            returnFaultsList.add("RTC BAT.: FAIL RTC Battery Fail");
        }
        if ((faultValue & 32768) == 32768) {
            returnFaultsList.add("NVRAM FAULT: NVRAM fault");
        }
        return returnFaultsList;
    }

    public static ArrayList<String> parseFaultVariable1(int faultValue) {
//        System.out.println(faultVariable);
        ArrayList<String> returnFaultsList = new ArrayList<String>();
//        long faultValue = Double.doubleToLongBits(faultVariable);
        if ((faultValue & 1) == 1) {
            returnFaultsList.add("R OV: R phase Over Voltage");
        }
        if ((faultValue & 2) == 2) {
            returnFaultsList.add("R UV: R phase Under Voltage");
        }
        if ((faultValue & 4) == 4) {
            returnFaultsList.add("Y OV: Y phase Over Voltage");
        }
        if ((faultValue & 8) == 8) {
            returnFaultsList.add("Y UV: Y phase Under Voltage");
        }
        if ((faultValue & 16) == 16) {
            returnFaultsList.add("B OV: B phase Over Voltage");
        }
        if ((faultValue & 32) == 32) {
            returnFaultsList.add("B UV: B phase Under Voltage");
        }
        if ((faultValue & 64) == 64) {
            returnFaultsList.add("R OL: R phase over load current");
        }
        if ((faultValue & 128) == 128) {
            returnFaultsList.add("R UL: R phase under load current");
        }
        if ((faultValue & 256) == 256) {
            returnFaultsList.add("Y OL: Y phase over load current");
        }
        if ((faultValue & 512) == 512) {
            returnFaultsList.add("Y UL: Y phase under load current");
        }
        if ((faultValue & 1024) == 1024) {
            returnFaultsList.add("B OL: B phase over load current");
        }
        if ((faultValue & 2048) == 2048) {
            returnFaultsList.add("B UL: B phase under load current");
        }
        if ((faultValue & 4096) == 4096) {
            returnFaultsList.add("OCC R: R phase over capacitor current");
        }
        if ((faultValue & 8192) == 8192) {
            returnFaultsList.add("UCC R: R phase under capacitor current");
        }
        if ((faultValue & 16384) == 16384) {
            returnFaultsList.add("OCC Y: Y phase over capacitor current");
        }
        if ((faultValue & 32768) == 32768) {
            returnFaultsList.add("UCC Y: Y phase under capacitor current");
        }
        return returnFaultsList;

    }


}
