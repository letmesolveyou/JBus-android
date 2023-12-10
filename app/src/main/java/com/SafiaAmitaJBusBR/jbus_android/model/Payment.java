package com.SafiaAmitaJBusBR.jbus_android.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Payment extends Invoice {
    private int busId;
    public Timestamp departureDate;
    public List<String> busSeat;

    public int getBusId(){
        return this.busId;
    }

    public static List<Payment> samplePaymentList(int size) {
        List<Payment> paymentList = new ArrayList<>();
        List<String> s = new ArrayList<>();
        s.add("BR01");

        for (int i = 1; i <= size; i++) {
            Payment p = new Payment();
            p.buyerId = 2;
            p.renterId = 2;
            p.busId = 0;
            p.busSeat = s;
            p.departureDate = Timestamp.valueOf("2023-5-21 9:30:00");
        }

        return paymentList;
    }

}
