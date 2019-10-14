package com.oocl.cultivation;

import java.util.ArrayList;
import java.util.List;

public class ParkingBoy {

    private static final String NO_TICKET = "Please provide your parking ticket.";
    private static final String INVALID_TICKET = "Unrecognized parking ticket.";
    private static final String FULL_PARKING_LOT = "Not enough position.";

    private final ParkingLot parkingLot;
    private String lastErrorMessage;
    private List<ParkingLot> parkingLotList = new ArrayList<>();

    public ParkingBoy(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
        parkingLotList.add(parkingLot);
    }

    public ParkingTicket park(Car car) {
        if (parkingLot.getAvailableParkingPosition() != 0)
            return parkingLot.addCar(car);
        else {
            ParkingLot availableParkingLot = parkingLotList.stream()
                    .filter(parkingLot -> parkingLot.countCars() != parkingLot.getCapacity())
                    .findFirst().orElse(null);
            if (availableParkingLot == null){
                setLastErrorMessage(FULL_PARKING_LOT);
                return null;
            } else {
                return availableParkingLot.addCar(car);
            }
        }
    }

    public Car fetch(ParkingTicket ticket) {
        Car car = parkingLot.getCar(ticket);
        if (ticket == null)
            setLastErrorMessage(NO_TICKET);
        else if (car == null)
            setLastErrorMessage(INVALID_TICKET);
        return car;
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }

    public List<ParkingLot> getParkingLotList() {
        return parkingLotList;
    }

    public void setLastErrorMessage(String lastErrorMessage) {
        this.lastErrorMessage = lastErrorMessage;
    }

    public void addParkingLot(ParkingLot parkingLot){
        parkingLotList.add(parkingLot);
    }
}
