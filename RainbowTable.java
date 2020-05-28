/**
 *  This class provides functionality to build rainbow tables (with a different reduction function per round) for 8 character long strings, which
 consist of the symbols "a .. z", "A .. Z", "0 .. 9", "!" and "#" (64 symbols in total).
 Properly used, it creates the following value pairs (start value - end value) after 10,000 iterations of hashFunction() and reductionFunction():
 start value  -  end value
 Kermit12        lsXcRAuN
 Modulus!        L2rEsY8h
 Pigtail1        R0NoLf0w
 GalwayNo        9PZjwF5c
 Trumpets        !oeHRZpK
 HelloPat        dkMPG7!U
 pinky##!        eDx58HRq
 01!19!56        vJ90ePjV
 aaaaaaaa        rLtVvpQS
 036abgH#        klQ6IeQJ


 *
 * @author Michael Schukat
 * @version 1.0
 */
public class RainbowTable
{
    /**
     * Constructor, not needed for this assignment
     */
    public RainbowTable() {

    }

    public static void main(String[] args) {
        long res = 0;
        int i;
        String start;
        String end;

        if (args != null && args.length > 0) { // Check for <input> value
            start = args[0];

            if (start.length() != 8) {
                System.out.println("Input " + start + " must be 8 characters long - Exit");
            }
            else {

                int j = 0;

                end = start;

                // Change the Problem Switch Here

                int problem = 2;

                switch (problem) {

                    // Problem 1

                    case 1:

                        System.out.println("\nPROBLEM 1:\n\nGenerated Chain:\n\n" + end);

                        // Generate and Print Chain

                        for (j = 0; j < 10000; j++) {

                            end = reductionFunction(hashFunction(end), j);

                            System.out.println(end);

                        }

                        // Print Start and End Values of Chain

                        System.out.println("\nStart Value: " + start);
                        System.out.println("End Value: " + end);

                        break;

                    // Problem 2

                    case 2:

                        // Initialize Variables

                        long testValue = 0;

                        boolean match = false;

                        int l = 0;

                        String[] matchArr = new String[5];

                        long[] matchHashArr = new long[5];

                        System.out.println("\nPROBLEM 2:\n\nGenerated Chain of Hash Values:\n");

                        // Generate and Print Chain of Hash Values

                        for (j = 0; j < 10000; j++) {

                            testValue = hashFunction(end);

                            System.out.println(testValue);

                            // If a Hash Value in the Chain Matches one of our 4 Hash Values, Store the Password String and Hash Value in Corresponding Arrays

                            if (testValue == 895210601874431214L || testValue == 750105908431234638L || testValue == 111111111115664932L || testValue == 977984261343652499L) {

                                match = true;

                                matchArr[l] = end;
                                matchHashArr[l] = testValue;

                                l++;

                            }

                            // Cycle to the Next Password Value

                            end = reductionFunction(hashFunction(end), j);

                        }

                        // If there was a Match

                        if (match == true){

                            System.out.println("\nMatches Found:\n");

                            int a = 0;

                            // Loop through Matches Found

                            for (a = 0; a<l; a++){

                                // Print Start Value, Matched Password and Matched Hash Value

                                System.out.println("Start Value: "+start+" Matched Value: "+matchArr[a]+"  Hash Value Matched: "+matchHashArr[a]);

                            }

                        }

                        // If there wasn't a Match

                        else {

                            System.out.println("\nNo Matches Found!\n");

                        }

                        break;

                }



            }
        }
        else { // No <input>
            System.out.println("Use: RainbowTable <Input>");
        }
    }

    private static long hashFunction(String s){
        long ret = 0;
        int i;
        long[] hashA = new long[]{1, 1, 1, 1};

        String filler, sIn;

        int DIV = 65536;

        filler = new String("ABCDEFGHABCDEFGHABCDEFGHABCDEFGHABCDEFGHABCDEFGHABCDEFGHABCDEFGH");

        sIn = s + filler; // Add characters, now have "<input>HABCDEF..."
        sIn = sIn.substring(0, 64); // // Limit string to first 64 characters

        for (i = 0; i < sIn.length(); i++) {
            char byPos = sIn.charAt(i); // get i'th character
            hashA[0] += (byPos * 17111); // Note: A += B means A = A + B
            hashA[1] += (hashA[0] + byPos * 31349);
            hashA[2] += (hashA[1] - byPos * 101302);
            hashA[3] += (byPos * 79001);
        }

        ret = (hashA[0] + hashA[2]) + (hashA[1] * hashA[3]);
        if (ret < 0) ret *= -1;
        return ret;
    }

    private static String reductionFunction(long val, int round) {  // Note that for the first function call "round" has to be 0, 
        String car, out;                                            // and has to be incremented by one with every subsequent call. 
        int i;                                                      // I.e. "round" created variations of the reduction function.
        char dat;

        car = new String("0123456789ABCDEFGHIJKLMNOPQRSTUNVXYZabcdefghijklmnopqrstuvwxyz!#");
        out = new String("");

        for (i = 0; i < 8; i++) {
            val -= round;
            dat = (char) (val % 63);
            val = val / 83;
            out = out + car.charAt(dat);
        }

        return out;
    }
}