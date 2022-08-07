package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {
    static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        var rows = scanner.nextInt();

        System.out.println("Enter the number of seats in each row:");
        var columns = scanner.nextInt();

        var cinemaRoom = new String[rows][columns];
        initCinemaRoom(cinemaRoom, rows);

        while (true) {
            var answer = printMenu();
            switch (answer) {
                case 1:
                    printRoom(cinemaRoom, rows, columns);
                    break;
                case 2:
                    sellTicket(rows, columns, cinemaRoom);
                    break;
                case 3:
                    showStatistics(cinemaRoom, rows, columns);
                    break;
                case 0:
                    return;
            }
        }
    }

    private static void showStatistics(String[][] cinemaRoom, int rows, int columns) {
        var soldTickets = getSoldTicketCount(cinemaRoom, rows, columns);
        var soldTicketPercentage = getSoldTicketPercentage(soldTickets, rows, columns);
        var currentIncome = getCurrentIncome(cinemaRoom, rows, columns);
        var totalIncome = getTotalIncome(rows, columns);

        System.out.printf("Number of purchased tickets: %d%n", soldTickets);
        System.out.printf("Percentage: %.2f%%%n", soldTicketPercentage);
        System.out.printf("Current income: $%d%n", currentIncome);
        System.out.printf("Total income: $%d%n", totalIncome);
    }

    private static int getCurrentIncome(String[][] cinemaRoom, int rows, int columns) {
        var income = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (cinemaRoom[i][j].equals("B")) {
                    income += getTicketPrice(rows, columns, i + 1);
                }
            }
        }

        return income;
    }

    private static double getSoldTicketPercentage(int soldTickets, int rows, int columns) {
        return (double) soldTickets * 100 / (rows * columns);
    }

    private static int getSoldTicketCount(String[][] cinemaRoom, int rows, int columns) {
        var soldTickets = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (cinemaRoom[i][j].equals("B")) {
                    soldTickets++;
                }
            }
        }

        return soldTickets;
    }

    private static void sellTicket(int rows, int columns, String[][] cinemaRoom) {
        int row;
        int column;
        while(true) {
            System.out.println("\nEnter a row number:");

            row = scanner.nextInt();

            System.out.println("Enter a seat number in that row:");
            column = scanner.nextInt();

            if (!isSeatValid(rows, columns, row, column)) {
                System.out.println("Wrong input!");
                continue;
            }

            if (isSeatTaken(cinemaRoom, row, column)) {
                System.out.println("That ticket has already been purchased");
                continue;
            }

            break;
        }

        var ticketPrice = getTicketPrice(rows, columns, row);
        System.out.printf("\nTicket price: $%d\n\n", ticketPrice);

        cinemaRoom[row - 1][column - 1] = "B";
    }

    private static boolean isSeatValid(int rows, int columns, int row, int column) {
        return row <= rows && column <= columns;
    }

    private static boolean isSeatTaken(String[][] cinemaRoom, int row, int column) {
        return cinemaRoom[row - 1][column - 1].equals("B");
    }

    private static int printMenu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");

        return scanner.nextInt();
    }

    private static int getTicketPrice(int rows, int columns, int row) {
        var ticketPrice = 0;
        if (rows * columns < 60) {
            ticketPrice = 10;
        } else {
            if (row <= rows / 2) {
                ticketPrice = 10;
            } else {
                ticketPrice = 8;
            }
        }

        return ticketPrice;
    }

    private static void printRoom(String[][] cinemaRoom, int rows, int columns) {
        System.out.println("Cinema:");

        // Print header
        System.out.print("  ");
        for (int i = 1; i <= columns; i++) {
            System.out.printf("%d ", i);
        }
        System.out.println();
        // Print seats
        for (int i = 0; i < rows; i++) {
            System.out.printf("%d ", i + 1);
            for (int j = 0; j < columns; j++) {
                System.out.printf("%s ", cinemaRoom[i][j]);
            }
            System.out.println();
        }
    }

    private static void initCinemaRoom(String[][] room, int rows) {
        for (int i = 0; i < rows; i++) {
            Arrays.fill(room[i], "S");
        }
    }

    private static int getTotalIncome(int rows, int columns) {
        var totalIncome = 0;

        if (rows * columns < 60) {
            totalIncome += rows * columns * 10;
        } else {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    totalIncome += i < rows / 2 ? 10 : 8;
                }
            }
        }

        return totalIncome;
    }
}