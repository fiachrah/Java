import java.util.*;

public class AlphabetSpeedTest {

    // Alphabet array is initialized in forwards direction
    private static char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    // Scanner object needed for any user input required by multiple methods
    private static Scanner scanner = new Scanner(System.in);

    // Time values initialized here so they can be called by multiple methods
    private static long start;
    private static long end;

    public static void main(String[] args) {

        // Game is immediately started when program is run
        startGame();

    }

    public static void startGame() {

        // Instructions for User
        System.out.println("Type the alphabet in order (hit enter between letters)");
        System.out.println("Forwards or Backwards: ");

        // While loop is created here to keep asking the forwards of backwards question if user inputs anything other than f or b
        Boolean fOrBLoop = true;

        while (fOrBLoop) {

            String fOrB = scanner.nextLine();

            if (fOrB.equals("f")) {

                // If f is entered, the while loop is immediately ended and the playGame method is called with the forwards parameter
                fOrBLoop = false;
                playGame(1);

            }
            else if (fOrB.equals("b")) {

                // If b is entered, the while loop is immediately ended and the playGame method is called with the backwards parameter
                fOrBLoop = false;
                playGame(0);

            } else {

                // User is reminded what to enter before the loop is restarted
                System.out.println("Invalid! You must enter f or b to start!");

            }

        }

    }

    public static void playGame(int fOrB) {

        // Timer is started as game is now in motion
        start = System.currentTimeMillis();

        // Alphabet array is reversed if necessary
        if (fOrB == 0) {

            alphabet = reverseArray(alphabet);

        }

        // While loop is created here to keep asking the first letter if user inputs anything other than the correct first letter
        Boolean firstLetInc = true;

        while (firstLetInc) {

            // For loop is created here to loop through the alphabet array
            for (int i = 0; i < 26; i++) {

                Character letter = scanner.next().charAt(0);

                // If letter correct and not final letter
                if (letter.equals(alphabet[i]) && i != 25) {

                    // While loop is stopped and statement is printed
                    firstLetInc = false;
                    System.out.println("[" + alphabet[i] + ": Correct! Now type " + alphabet[i + 1] + "]");

                }
                // If letter correct and is final letter
                else if (letter.equals(alphabet[i]) && i == 25) {

                    // Statement is printed and endGame method is called
                    System.out.println("Correct! Well done!");
                    endGame();


                }
                // If letter is incorrect
                else {

                    // i is de-incremented to stay on the same letter for next loop
                    i--;
                }

            }

        }

    }


    public static void endGame() {

        // Timer is ended as game has completed
        end = System.currentTimeMillis();

        // Time Taken is calculated and converted to seconds
        float timeInSecs = (end - start) / 1000;

        // Time is displayed to User and program ends
        System.out.println("\nTime taken: " + timeInSecs + " seconds");

    }

    public static char[] reverseArray(char[] array) {

        // Array is looped through and each value swapped until midpoint is reached
        for (int i = 0; i < array.length / 2; i++) {

            // Current Value is saved temporarily and array position swapped, current value slotted into new space in array
            char currentVal = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = currentVal;

        }

        // Reversed array is returned
        return array;
        }
}
