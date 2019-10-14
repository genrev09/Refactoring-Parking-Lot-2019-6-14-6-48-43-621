package com.oocl.cultivation.test;

import com.oocl.cultivation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ParkingBoyFacts {
    private static final String UNRECOGNIZED_PARKING_TICKET = "Unrecognized parking ticket.";
    private static final String NO_TICKET_MESSAGE = "Please provide your parking ticket.";
    private static final String FULL_PARKING_LOT_MESSAGE = "Not enough position.";
    private static final int ONE = 1;
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void should_park_car_into_the_parking_lot_by_parking_boy() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        Car car = new Car();

        ParkingTicket parkingTicket = parkingBoy.park(car);

        assertNotNull(parkingTicket);
    }

    @Test
    void should_fetch_the_car_from_parking_lot_by_parking_boy() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        Car parkedCar = new Car();
        
        ParkingTicket parkingTicket = parkingBoy.park(parkedCar);

        Car car = parkingBoy.fetch(parkingTicket);
        assertNotNull(car);
    }

    @Test
    void should_Park_multiple_cars_into_parking_lot_and_get_right_car_using_parking_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();

        ParkingTicket parkingTicket1 = parkingBoy.park(car1);
        parkingBoy.park(car2);
        parkingBoy.park(car3);

        assertEquals(parkingLot.countCars(),3);
        assertEquals(car1,parkingLot.getCar(parkingTicket1));
    }

    @Test
    void should_not_fetch_car_if_has_invalid_or_no_ticket() {
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);

        parkingBoy.park(car);
        ParkingTicket wrongTicket = new ParkingTicket();

        assertNull(parkingLot.getCar(wrongTicket));
        assertNull(parkingLot.getCar(null));
    }

    @Test
    void should_not_fetch_car_using_used_ticket() {

        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        Car car = new Car();

        ParkingTicket parkingTicket = parkingBoy.park(car);
        parkingBoy.fetch(parkingTicket);
        Car car2 = parkingBoy.fetch(parkingTicket);

        assertNull(car2);
    }

    @Test
    void should_not_park_when_parking_lot_is_full() {
        Car ExceedingCar = new Car();
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        for (int park_times = 0; park_times < parkingLot.getCapacity(); park_times++){
            parkingBoy.park(new Car());
        }

        ParkingTicket ticket = parkingBoy.park(ExceedingCar);
        assertNull(ticket);
    }

    @Test
    void should_message_unrecognized_parking_ticket_when_ticket_is_used() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        Car originalCar = new Car();
        ParkingTicket ticket = parkingBoy.park(originalCar);

        parkingBoy.fetch(ticket);
        parkingBoy.fetch(ticket);

        assertEquals(UNRECOGNIZED_PARKING_TICKET,parkingBoy.getLastErrorMessage());
    }

    @Test
    void should_message_please_provide_your_parking_ticket_when_ticket_is_null() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);

        parkingBoy.fetch(null);

        assertEquals(NO_TICKET_MESSAGE,parkingBoy.getLastErrorMessage());
    }

    @Test
    void should_message_not_enough_position_when_parking_lot_is_full() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        Car car = new Car();

        for (int park_times = 0; park_times < parkingLot.getCapacity(); park_times++){
            parkingBoy.park(new Car());
        }

        parkingBoy.park(car);

        assertEquals(FULL_PARKING_LOT_MESSAGE, parkingBoy.getLastErrorMessage());
    }

    @Test
    void should_park_car_to_another_parking_lot_when_parking_lot_1_is_full() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        parkingBoy.addParkingLot(new ParkingLot());
        Car car = new Car();

        for (int count = 0; count < parkingLot.getCapacity(); count ++){
            parkingBoy.park(new Car());
        }

        parkingBoy.park(car);

        assertNull(parkingBoy.getLastErrorMessage());
    }

    @Test
    void should_park_car_to_parking_lot_with_more_space_with_smart_parking_boy() {
        ParkingLot parkingLot1 = new ParkingLot();
        ParkingLot parkingLot2 = new ParkingLot();
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLot1);
        smartParkingBoy.addParkingLot(parkingLot2);

        smartParkingBoy.park(new Car());
        smartParkingBoy.park(new Car());

        assertEquals(ONE,parkingLot1.countCars());
        assertEquals(ONE,parkingLot2.countCars());
    }

    @Test
    void should_park_car_to_parking_lot_with_larger_available_position_rate__using_super_smart_parking_boy() {
        ParkingLot parkingLot1 = new ParkingLot();
        ParkingLot parkingLot2 = new ParkingLot();
        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLot1);
        superSmartParkingBoy.addParkingLot(parkingLot2);

        superSmartParkingBoy.park(new Car());
        superSmartParkingBoy.park(new Car());

        assertEquals(ONE,parkingLot1.countCars());
        assertEquals(ONE,parkingLot2.countCars());
    }
}
