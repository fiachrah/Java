public class Palindromes {

    // Initialize Method Operation Counters

    static int met1Op = 0;
    static int met2Op = 0;
    static int met3Op = 0;
    static int met4Op = 0;

    public static void main (String[] args)
    {

        // Run All 3 Tests 20 times increasing the range by 50000 each time to record data for graphs

        for(int i = 50000; i<= 1000000; i=(i+50000)){

            System.out.println("\n----------------------------------NEW TEST-------------------------------------");

            runTests(0, i);

        }


    }

    public static void runTests(int minRange, int maxRange) {

        // Set Counters Back to 0

        met1Op = 0;
        met2Op = 0;
        met3Op = 0;
        met4Op = 0;

        // Test the speed of each Method [Test 1]

        System.out.println("\nCompare the speed of each Method [Test 1] [Range: "+minRange+" - "+maxRange+"]:");

        testMethodSpeed(1,minRange,maxRange);
        testMethodSpeed(2,minRange,maxRange);
        testMethodSpeed(3,minRange,maxRange);
        testMethodSpeed(4,minRange,maxRange);

        // Assigning values from counters to local variables as global variable for 2nd Method will be changed during the 2nd Test

        int met1OpLoc = met1Op;
        int met2OpLoc = met2Op;
        int met3OpLoc = met3Op;
        int met4OpLoc = met4Op;


        // Count the number of matching Decimal and Binary Palindromes [Test 2]

        System.out.println("\nCount the number of matching Decimal and Binary Palindromes [Test 2] [Range: "+minRange+" - "+maxRange+"]:");

        countMatchingDecBinPal(minRange, maxRange);

        // Count the number of primitive operations for each Method [Test 3]

        System.out.println("\nCount the number of Primitive Operations for each Method [Test 3] [Range: "+minRange+" - "+maxRange+"]:");
        System.out.println("[Method 1] Primitive Operations: "+met1OpLoc);
        System.out.println("[Method 2] Primitive Operations: "+met2OpLoc);
        System.out.println("[Method 3] Primitive Operations: "+met3OpLoc);
        System.out.println("[Method 4] Primitive Operations: "+met4OpLoc);


    }


    public static void testMethodSpeed(int method, int min, int max){

        // Set Start Time

        float start = System.nanoTime();

        // Initialize Counters

        int binPal = 0;
        int decPal = 0;

        // Switch to Chosen Method

        switch(method) {
            case 1:

                // Loop through Range

                for(int i = min; i <= max; i++){

                    // If Dec Num is Palindrome

                    if(loopToReverseTest(Integer.toString(i))){

                        decPal++;

                    }

                    // If Bin Num is Palindrome

                    if(loopToReverseTest(convertToBin(Integer.toString(i)))){

                        binPal++;

                    }

                }

                break;
            case 2:

                // Loop through Range

                for(int i = min; i <= max; i++){

                    // If Dec Num is Palindrome

                    if(compareCharactersLoopTest(Integer.toString(i))){

                        decPal++;

                    }

                    // If Bin Num is Palindrome

                    if(compareCharactersLoopTest(convertToBin(Integer.toString(i)))){

                        binPal++;

                    }

                }

                break;
            case 3:

                // Loop through Range

                for(int i = min; i <= max; i++){

                    // If Dec Num is Palindrome

                    if(arrayVsQueueTest(Integer.toString(i))){

                        decPal++;

                    }

                    // If Bin Num is Palindrome

                    if(arrayVsQueueTest(convertToBin(Integer.toString(i)))){

                        binPal++;

                    }


                }

                break;
            case 4:

                // Loop through range

                for(int i = min; i <= max; i++){

                    // If Dec Num is Palindrome

                    if(recursiveReverseTest((Integer.toString(i)))){

                        decPal++;

                    }

                    // If Bin Num is Palindrome

                    if(recursiveReverseTest(convertToBin(Integer.toString(i)))){

                        binPal++;

                    }

                }

                break;

        }

        // Set End Time and Calculate Time Lapsed

        float end = System.nanoTime();

        float time = end - start;

        // Print Results of Test

        System.out.println("[Method "+method+"] Num of Palindromes found: "+(decPal+binPal)+" ("+decPal+" decimal & "+binPal+" binary), Time taken: "+time+" nanoseconds");


    }

    public static void countMatchingDecBinPal(int min, int max){

        int matches = 0;

        // Loop Through Range

        for(int i = min; i <= max; i++){

            // Check for Palindrome in both Dec and Bin Num (Used 2nd Method as Test 1 indicated it was the most efficient)

            if(compareCharactersLoopTest(Integer.toString(i)) && compareCharactersLoopTest(convertToBin(Integer.toString(i)))){

                // Increment Counter

                matches++;

            }

        }

        // Print Results

        System.out.println("Instances in which both the Decimal number and its Binary equivalent are recorded as Palindromes: "+matches);

    }

    // Method 1

    public static boolean loopToReverseTest(String testInput){

        String reverseString = "";

        met1Op++; // Ops Count

        // Loop through input backwards

        for(int i = (testInput.length() - 1); i>=0; i--){

            // Add Characters to reverse string

            reverseString += testInput.charAt(i);

            met1Op++; // Ops Count
        }

        // Return true if reverse string and input match

        if(reverseString.equals(testInput)){

            met1Op++; // Ops Count

            return true;
        }

        met1Op++; // Ops Count

        return false;

    }

    // Method 2

    public static boolean compareCharactersLoopTest(String testInput){

        int j = 0;

        met2Op++; // Ops Count

        // Loop through input backwards

        for(int i = (testInput.length() - 1); i >= 0; i--){

            // If the character does not match the character in the same position in original input, return false

            if(testInput.charAt(j) != testInput.charAt(i)){

                met2Op++; // Ops Count

                return false;
            }

            // Increment character counter on forwards string

            j++;

            met2Op++; // Ops Count
        }

        // Return true if these tests are passed

        met2Op++; // Ops Count

        return true;

    }

    // Method 3

    public static boolean arrayVsQueueTest(String testInput){

        // Create a new queue and stack

        Queue q = new ArrayQueue();
        Stack s = new ArrayStack(testInput.length()+1);

        met3Op = met3Op + 2; // Ops Count

        // Loop through input

        for(int i = 0; i<testInput.length(); i++){

            // Enqueue and Push the current character to queue and stack

            q.enqueue(testInput.charAt(i));
            s.push(testInput.charAt(i));

            met3Op = met3Op + 2; // Ops Count

        }

        // Loop through queue and stack

        for(int i = 0; i<testInput.length(); i++){

            met3Op++; // Ops Count

            // If the de-queued object and popped object are not the same, return false

            if(!q.dequeue().equals(s.pop())){

                met3Op++; // Ops Count

                return false;

            }

        }

        // Return true if these tests are passed

        met3Op++; // Ops Count

        return true;

    }

    // Method 4

    public static boolean recursiveReverseTest(String testInput){

        // Test input against reverse of input, return true if they equal

        if(testInput.equals(reverse(testInput))){

            met4Op++; // Ops Count

            return true;

        }

        met4Op++; // Ops Count

        return false;

    }

    // Recursive Reverse Method

    public static String reverse(String input){

        // Return input if Length is 0

        if (input.length() == 0){

            met4Op++; // Ops Count

            return input;

        }

        // Recursive Reverse Method Call

        met4Op++; // Ops Count

        return reverse(input.substring(1)) + input.charAt(0);

    }

    // Converting Decimal String to Binary String Method

    public static String convertToBin(String decNum){

        // Convert Dec to Bin and return String

        return Integer.toBinaryString((Integer.parseInt(decNum)));

    }

}
