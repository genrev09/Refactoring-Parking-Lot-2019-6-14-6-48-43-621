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
                getPositionRate(parkingLot1) >= getPositionRate(parkingLot2);

        ParkingLot parkingLotWithHighPositionRate = getParkingLotList().stream()
                .reduce((parking1, parking2)
                -> getHigherPositionRate(hasHighPositionRate, parking1, parking2)).orElse(null);

        if (parkingLotWithHighPositionRate == null)
            return null;
        return parkingLotWithHighPositionRate.addCar(car);
    }

    private int getPositionRate(ParkingLot parkingLot) {
        return parkingLot.getAvailableParkingPosition() / parkingLot.getCapacity();
    }

    private ParkingLot getHigherPositionRate(BiPredicate<ParkingLot, ParkingLot> hasHighPositionRate, ParkingLot parking1, ParkingLot parking2) {
        return hasHighPositionRate.test(parking1,parking2) ? parking1 : parking2;
    }
}
