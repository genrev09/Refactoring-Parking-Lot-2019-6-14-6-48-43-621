package com.oocl.cultivation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ParkingBoy {

    public static final String FULL_PARKING_ERRORMESSAGE = "Not enough position.";
    public static final String NO_TICKET_ERRORMESSAGE = "Please provide your parking ticket.";
    public static final String INVALID_TICKET_ERRORMESSAGE = "Unrecognized parking ticket.";
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
                    .filter(getParkingLotWithSpace())
                    .findFirst().orElse(null);
            if (availableParkingLot == null){
                setLastErrorMessage(FULL_PARKING_ERRORMESSAGE);
                return null;
            } else {
                return availableParkingLot.addCar(car);
            }
        }
    }

    private Predicate<ParkingLot> getParkingLotWithSpace() {
        return parkingLot -> parkingLot.countCars() != parkingLot.getCapacity();
    }

    public Car fetch(ParkingTicket ticket) {
        Car car = parkingLot.getCar(ticket);
        if (ticket == null)
            setLastErrorMessage(NO_TICKET_ERRORMESSAGE);
        else if (car == null)
            setLastErrorMessage(INVALID_TICKET_ERRORMESSAGE);
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
