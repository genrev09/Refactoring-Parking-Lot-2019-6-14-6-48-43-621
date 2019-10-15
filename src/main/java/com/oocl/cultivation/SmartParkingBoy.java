package com.oocl.cultivation;

public class SmartParkingBoy extends ParkingBoy {

    public SmartParkingBoy(ParkingLot parkingLot) {
        super(parkingLot);
    }

    @Override
    public ParkingTicket park(Car car) {
        ParkingLot parkingLotWithMoreSpace = getParkingLotList().stream()
                .reduce((this::getGreaterParkingLotCapacity))
                .orElse(null);

        if (parkingLotWithMoreSpace == null){
            setLastErrorMessage("Not enough position.");
            return null;
        }
        return parkingLotWithMoreSpace.addCar(car);
    }

    private ParkingLot getGreaterParkingLotCapacity(ParkingLot parkingLot, ParkingLot parkingLot2) {
        return parkingLot.countCars() <= parkingLot2.countCars() ? parkingLot : parkingLot2;
    }
}
