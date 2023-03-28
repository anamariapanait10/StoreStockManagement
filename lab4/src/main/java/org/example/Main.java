package org.example;

import org.example.application.enums.RoomConnectionType;
import org.example.application.enums.RoomNumber;
import org.example.model.Accomodation;
import org.example.model.Appartment;
import org.example.model.Duplex;
import org.example.model.OpenSpace;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Accomodation> accomodations = new ArrayList<>();
        Random r = new Random();

        for(int i = 0; i < 10; i++) {
            HashMap<String, String> uti = new HashMap<>();
            uti.put("Balcony", "Yes");
            uti.put("Bathroom number", "2");

            Appartment app = Appartment.builder()
                    .rentOrPurchaseDate(LocalDate.now())
                    .roomNumber(RoomNumber.THREE_ROOMS)
                    .price((r.nextInt(10000) + 1) * 100)
                    .surface((r.nextInt(20) + 1) * 10)
                    .number((r.nextInt(20) + 1) * 10)
                    .utilities(uti)
                    .sold(r.nextBoolean())
                    .build();
            accomodations.add(app);
        }

        accomodations = accomodations.stream().sorted((o1, o2) -> {
            if (o1.getRoomNumber() != o2.getRoomNumber()) {
                if (o1.getRoomNumber() == RoomNumber.FOUR_PLUS_ROOMS) {
                    return 1;
                }
                if (o1.getRoomNumber() == RoomNumber.FOUR_ROOMS) {
                    if (o2.getRoomNumber() == RoomNumber.FOUR_PLUS_ROOMS) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
                if (o1.getRoomNumber() == RoomNumber.THREE_ROOMS) {
                    if (o2.getRoomNumber() == RoomNumber.FOUR_ROOMS || o2.getRoomNumber() == RoomNumber.FOUR_PLUS_ROOMS) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
                if (o1.getRoomNumber() == RoomNumber.TWO_ROOMS) {
                    if (o2.getRoomNumber() == RoomNumber.ONE_ROOM) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
            return 0;
        }).collect(Collectors.toList());

        System.out.println("--------------------");

        for (Accomodation a : accomodations) {
            System.out.println("Accomodation number: " + a.getNumber());
            System.out.println();

            for(String k : a.getUtilities().keySet()) {
                System.out.println(k + " : " + a.getUtilities().get(k));
            }
            System.out.println("--------------------");
        }

        int nrApartaments = (int) accomodations.stream().filter(a -> a.getClass() == Appartment.class && a.isSold()).count();
        int nrDuplexes = (int) accomodations.stream().filter(a -> a.getClass() == Duplex.class && a.isSold()).count();
        int nrOpenSpaces = (int) accomodations.stream().filter(a -> a.getClass() == OpenSpace.class && a.isSold()).count();

        System.out.println("-------------- Statistics (sold accomodations type) -------------");

        System.out.println("Number of sold apartments: " + nrApartaments);
        System.out.println("Number of sold duplexes: " + nrDuplexes);
        System.out.println("Number of sold open spaces: " + nrOpenSpaces);

        System.out.println("-----------------------------------------------------------------");

        int nrOneRoom = (int) accomodations.stream().filter(a -> a.getRoomNumber() == RoomNumber.ONE_ROOM).count();
        int nrTwoRoom = (int) accomodations.stream().filter(a -> a.getRoomNumber() == RoomNumber.TWO_ROOMS).count();
        int nrThreeRoom = (int) accomodations.stream().filter(a -> a.getRoomNumber() == RoomNumber.THREE_ROOMS).count();
        int nrFourRoom = (int) accomodations.stream().filter(a -> a.getRoomNumber() == RoomNumber.FOUR_ROOMS).count();
        int nrFourPlusRoom = (int) accomodations.stream().filter(a -> a.getRoomNumber() == RoomNumber.FOUR_PLUS_ROOMS).count();

        System.out.println("-------------- Statistics (room number) -------------");

        System.out.println("Number of sold one room accomodations: " + nrOneRoom);
        System.out.println("Number of sold two room accomodations: " + nrTwoRoom);
        System.out.println("Number of sold three room accomodations: " + nrThreeRoom);
        System.out.println("Number of sold four room accomodations: " + nrFourRoom);
        System.out.println("Number of sold four plus room accomodations: " + nrFourPlusRoom);

        System.out.println("-----------------------------------------------------");
    }
}
/*
2 camere, 3 camere, duplex, open space.

Ap: nr apartamentului, pretul, dimensiunea, data cumpararii/inchirierii.

Utilitati: 1 baie/doua, cate dormitoare, balcon/nu,
in functie de camera: canapea, masa, aragaz , etc.

1) Scris cod
2) Sortati dupa numarul de camere toate apartamentele.
3) Afisati toate utilitatile fiecarui apartament.
4) Clasament nr vanzari
5) Afisati cate apartamente sunt in functie de nr de camere.
 */