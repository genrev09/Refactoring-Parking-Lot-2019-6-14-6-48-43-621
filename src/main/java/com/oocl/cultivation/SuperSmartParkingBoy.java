package com.oocl.cultivation;

import java.util.function.BiPredicate;
import java.util.function.IntPredicate;

public class SuperSmartParkingBoy extends ParkingBoy {

    public static final String FULL_PARKING_ERRORMESSAGE = "Not enough position.";

    public SuperSmartParkingBoy(ParkingLot parkingLot) {
        super(parkingLot);
    }

    @Override
    public ParkingTicket park(Car car) {
        BiPredicate<ParkingLot,ParkingLot> hasHighPositionRate = (parkingLot1, parkingLot2) ->
                getParkingLotPositionRate(parkingLot1)
                >= getParkingLotPositionRate(parkingLot2);

        ParkingLot parkingLotWithHighPositionRate = getParkingLotList().stream()
                .reduce((parking1, parking2)
                -> getParkingLotWithHighPositionRate(hasHighPositionRate, parking1, parking2)).orElse(null);

        if (parkingLotWithHighPositionRate == null){
            setLastErrorMessage(FULL_PARKING_ERRORMESSAGE);
            return null;
        }
        return parkingLotWithHighPositionRate.addCar(car);
    }

    private ParkingLot getParkingLotWithHighPositionRate(BiPredicate<ParkingLot, ParkingLot> hasHighPositionRate, ParkingLot parking1, ParkingLot parking2) {
        return hasHighPositionRate.test(parking1,parking2) ? parking1 : parking2;
    }

    private int getParkingLotPositionRate(ParkingLot parkingLot) {
        return parkingLot.getAvailableParkingPosition() / parkingLot.getCapacity();
    }
}
