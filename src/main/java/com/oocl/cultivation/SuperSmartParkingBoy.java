package com.oocl.cultivation;

import java.util.function.BiPredicate;
import java.util.function.IntPredicate;

public class SuperSmartParkingBoy extends ParkingBoy {
    public SuperSmartParkingBoy(ParkingLot parkingLot) {
        super(parkingLot);
    }

    @Override
    public ParkingTicket park(Car car) {
        BiPredicate<ParkingLot,ParkingLot> hasHighPositionRate = (parkingLot1, parkingLot2) ->
                parkingLot1.getAvailableParkingPosition() / parkingLot1.getCapacity()
                >= parkingLot2.getAvailableParkingPosition()/parkingLot2.getCapacity();

        ParkingLot parkingLotWithHighPositionRate = getParkingLotList().stream()
                .reduce((parking1, parking2)
                -> hasHighPositionRate.test(parking1,parking2)
                ? parking1 : parking2).orElse(null);

        if (parkingLotWithHighPositionRate == null){
            setLastErrorMessage("Not enough position.");
            return null;
        }
        return parkingLotWithHighPositionRate.addCar(car);
    }
}
