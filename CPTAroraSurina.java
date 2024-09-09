/*
@Author: Surina Arora 
@Date: January 12th 2024 
@Description: Instead of Mario having to save Princess Peach 
from Bowser, it has become your responsibility to take on his role. 
*/

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class CPTAroraSurina {
    // Variables
    private static int userLives = 5;
    private static String userName;
    private static String[] items = {"Whistle", "Mushroom", "HammerSuit"};

    private static ArrayList<Integer> bannedRooms = new ArrayList<Integer>();
    private static int itemsCollected = 0;
    private static boolean gameOver = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Introduction
        System.out.println("Welcome to 'Save The Princess from The Beast!'");
        System.out.print("Please enter your username:");
        userName = scanner.nextLine();

        // keep playing games until user decides to stop
        while (true) {
            startGame(scanner);

            // Handle lives lost
            if (userLives <= 0) {
                System.out.println("Game over, You ran out of lives and were unable to save Princess Peach! Do you want to play again (Y/N)?");
            } else if (gameOver) {
                System.out.println("You saved Princess Peach! Mario is impressed! Do you want to play again (Y/N)?");
            }

            String playAgainChoice = scanner.nextLine().trim().toUpperCase();

            if (playAgainChoice.equals("N")) {
                break;
            }
        }

        scanner.close();
    }

    private static void startGame(Scanner scanner) {
        // restore default state
        setDefaultStartState();

        boolean shouldDisplayMenu = true;

        // Game loop
        while (userLives > 0 && itemsCollected <= 3 && !gameOver) {
            if (shouldDisplayMenu) displayMenu();
            int choice = getUserChoice(scanner, 4, "choice");

            // ban the room from being chosen again in a future round
            if (bannedRooms.contains(choice)) {
                System.out.println("You already chose room " + choice + ". Choose another room.");

                // avoid unnecessary call to display the menu on the next round
                shouldDisplayMenu = false;
                continue;
            }

            if (choice == 4) {
                if (itemsCollected == 3) {
                    playRockPaperScissors(scanner);
                } else {
                    System.out.println("You need to collect all items first!");

                    // avoid unnecessary call to display the menu on the next round
                    shouldDisplayMenu = false;
                    continue;
                }
            } else {
                playNumberGuessingGame(choice, scanner);
                bannedRooms.add(choice);
            }

            shouldDisplayMenu = true;
        }
    }

    // reset all variables for the beginning of a new game.
    private static void setDefaultStartState() {
        userLives = 5;
        bannedRooms.clear();
        itemsCollected = 0;
        gameOver = false;
    }

    private static void displayMenu() {
        System.out.println("\n" + userName + ", Before you choose a room, here is a quick rundown of what is expected.");
         System.out.println("You have been sent by Mario to bring Princess Peach safely back to Mushroom Kingdom through going on multiple adventures and eventually battling Bowser in Rock Paper Scissors."); 
         System.out.println("Mario has granted you a life count of 5 lives in order to complete this task. Let's begin by having you choose a Room."); 


        // only display non-banned rooms
        for (int i = 1; i <= 3; i++) {
            if (!bannedRooms.contains(i)) {
                System.out.println(i + ". Room " + i);
            }
        }
        System.out.println("4. Final Room - Bowser's Doom of Dungeons.");
    }

    private static int getUserChoice(Scanner scanner, int maxChoice, String displayWord) {
        int choice = 0;
        while (choice < 1 || choice > maxChoice) {
            System.out.print("Enter your " + displayWord + ": ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return choice;
    }

    private static void playNumberGuessingGame(int roomNumber, Scanner scanner) {
        int maxNumber = roomNumber + 2;
        int randomNumber = new Random().nextInt(maxNumber) + 1;
        int attempts = Math.min(userLives, 3);

        System.out.println("Welcome to Room " + roomNumber + "! Guess the number between 1 and " + maxNumber);

        while (attempts > 0) {
            int userGuess = getUserChoice(scanner, maxNumber, "guess");

            if (userGuess == randomNumber) {
                System.out.println("Congratulations! You guessed the correct number!");
                collectItem();
                return;
            } else {
                userLives--;
                attempts--;

                if (attempts > 0 && userLives > 0) {
                    System.out.println("Incorrect guess. Try again.");
                    System.out.println("Remaining lives: " + userLives);
                }
            }
        }

        System.out.println("You lost! You could not pass room " + roomNumber + "!");
        gameOver = true;
    }

    private static void collectItem() {
        System.out.println("You found a " + items[itemsCollected] + "!");
        itemsCollected++;
    }

    private static void playRockPaperScissors(Scanner scanner) {
        System.out.println("Welcome to the Final Room! It's time for a Rock-Paper-Scissors showdown!");

        int computerScore = 0;
        int userScore = 0;

        while (true) {

            // handle possible winners
            if (userScore >= 2) {
                System.out.println("Congratulations, " + userName + "! You defeated Bowser, and saved Princess Peach! Return safetly using your HammerSuit and Mushrooms along to way.");
                gameOver = true;
                return;
            } else if (computerScore >= 2) {
                System.out.println("Game over, " + userName + "! Better luck next time!");
                gameOver = true;
                return;
            }

            System.out.println("Enter your choice (Whistle/Mushroom/HammerSuit): ");
            String userChoice = scanner.nextLine().trim().toUpperCase();
            String computerChoice = items[new Random().nextInt(items.length)].toUpperCase();

            System.out.println("Computer chose: " + computerChoice);

            // Switch statement for handling choices
            switch (userChoice) {
                case "WHISTLE":
                    if (computerChoice.equals("HAMMERSUIT")) {
                        System.out.println("You win this round!");
                        userScore++;
                    } else if (computerChoice.equals("MUSHROOM")) {
                        System.out.println("You lose this round!");
                        computerScore++;
                    } else {
                        System.out.println("It's a tie! Try again.");
                    }
                    break;
                case "MUSHROOM":
                    if (computerChoice.equals("WHISTLE")) {
                        System.out.println("You win this round!");
                        userScore++;
                    } else if (computerChoice.equals("HAMMERSUIT")) {
                        System.out.println("You lose this round!");
                        computerScore++;
                    } else {
                        System.out.println("It's a tie! Try again.");
                    }
                    break;
                case "HAMMERSUIT":
                    if (computerChoice.equals("MUSHROOM")) {
                        System.out.println("You win this round!");
                        userScore++;
                    } else if (computerChoice.equals("WHISTLE")) {
                        System.out.println("You lose this round!");
                        computerScore++;
                    } else {
                        System.out.println("It's a tie! Try again.");
                    }
            }
        }
    }
}

          

