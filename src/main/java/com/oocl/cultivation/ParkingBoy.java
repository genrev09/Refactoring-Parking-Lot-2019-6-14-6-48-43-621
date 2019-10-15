package com.oocl.cultivation;

import java.util.ArrayList;
import java.util.List;

public class ParkingBoy {

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
                setLastErrorMessage("Not enough position.");
                return null;
            } else {
                return availableParkingLot.addCar(car);
            }
        }
    }

    public Car fetch(ParkingTicket ticket) {
        Car car = parkingLot.getCar(ticket);
        if (ticket == null)
            setLastErrorMessage("Please provide your parking ticket.");
        else if (car == null)
            setLastErrorMessage("Unrecognized parking ticket.");
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
