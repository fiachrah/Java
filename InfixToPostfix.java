import javax.swing.JOptionPane;

public class InfixToPostfix {

    public static void main(String[] args) {

        // Run method with initial instructions for user
        getUserInput("Please enter an infix numerical expression between 3 and 20 characters");
    }

    public static void getUserInput(String instructions) {

        // Get input from user
        String userInput = JOptionPane.showInputDialog(instructions);

        if(userInput == null){

            System.exit(0);

        }

        // Call testInput method to check input meets requirements
        if (testInput(userInput)) {

            // If requirements are met, convert and evaluate input and display results
            printResults(userInput, convert(userInput), evaluate(convert(userInput)));
            System.exit(0);


        } else {

            // If requirements are not met, advise user of requirements and call method again with new instructions
            JOptionPane.showMessageDialog(null, "Only the following characters are valid: +,-,*,/,^,(,) and numbers 0-9");
            getUserInput("Please try again");

        }

    }

    public static Boolean testInput(String input) {

        // Test for length requirements
        if (input.length() < 3 || input.length() > 20) {

            return false;
        }

        // Test for character requirements
        char[] allowedCharacters = {'+', '-', '*', '/', '^', '(', ')', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        // Loop through input string
        for (int i = 0; i < input.length(); i++) {

            Boolean match = false;

            // Loop through allowed characters
            for (int j = 0; j < allowedCharacters.length; j++) {

                // If character is allowed, set match to true
                if (input.charAt(i) == allowedCharacters[j]){

                    match = true;

                }

            }

            // If match remains false, string failed requirement test
            if (!match){

                return false;
            }

        }

        return true;
    }

    public static String convert(String input){

        // Create stack and initialize output string and input array
        Stack s = new ArrayStack(20);

        String output = "";

        char[] inputArray = new char[input.length()];

        // Loop through input string
        for (int i = 0; i<input.length(); i++){

            // Add each string character to character array
            inputArray[i] = input.charAt(i);

            // Check if character is a number
            if(Character.isDigit(inputArray[i])){

                // Add to output string
                output = output+inputArray[i];
            }
            // Check if character is (
            else if (inputArray[i] == '('){

                // Push to stack
                s.push(inputArray[i]);
            }
            // Check if character is )
            else if (inputArray[i] == ')'){

                // Pop everything in the stack and add characters to output string until ( is reached
                while((Character) s.top() != '(') {

                    output+= s.pop();

                }
                // Check stack is not empty
                if(! s.isEmpty()){

                    // Remove the ( and discard it
                    s.pop();

                }

            }
            // Character must be an operator
            else{

                // If stack is empty, first character in stack is ( or the operator has greater precedence than the operator at the top of the stack
                if(s.isEmpty() || ((Character) s.top() == '(') || (precedence(inputArray[i]) > precedence((Character) s.top()) )){

                    // Push the operator to the stack
                    s.push(inputArray[i]);

                }
                else{

                    // Otherwise pop everything of greater precedence from the stack and add it to the output string
                    while(! s.isEmpty() && precedence(inputArray[i]) <= precedence((Character) s.top())) {

                        output+= s.pop();

                    }

                    // Then push the operator to the stack
                    s.push((inputArray[i]));

                }


            }

        }

        // Loop through everything remaining in the stack
        while(! s.isEmpty()) {

            // Pop all remaining items in the stack and add to output
            output+= s.pop();

        }

        return output;

    }

    public static Double evaluate(String expression) {

        // Create a new stack
        Stack s = new ArrayStack(20);

        char[] inputArray = new char[expression.length()];

        // Loop through expression
        for (int i = 0; i<expression.length(); i++) {

            // Add each character to character array
            inputArray[i] = expression.charAt(i);

            // Check if character is a number
            if(Character.isDigit(inputArray[i])){

                // Convert character to double and push to stack
                s.push((double)(inputArray[i]-'0'));
            }
            else{

                // Pop top 2 operands and convert back to doubles
                double op2 = (double)s.pop();
                double op1 = (double)s.pop();

                // Push the result of the calculation of both operands and the operator
                s.push(calculate(op1, op2, inputArray[i]));
            }

        }

        // Return the remaining double in the stack as this is the final result
        return (double)s.top();

    }


    public static int precedence(char operator){

        int precedence = 0;

        // Array of precedence in order of least to most
        char[] operatorPrecedence = {'-', '+', '/', '*', '^'};

        // Loop through array of precedence
        for (int i = 0; i < operatorPrecedence.length; i++){

            // If the operator matches an operator in the array
            if(operator == operatorPrecedence[i]){

                // precedence is array position + 1
                precedence = i + 1;
            }
        }

        return precedence;
    }

    public static double calculate(double op1, double op2, char operator){

        // Check operator and return result of expression using that operator
        if (operator== '^'){

            System.out.println(op1+" ^ "+op2+" = "+Math.pow(op1, op2));
            return Math.pow(op1, op2);
        }
        else if (operator== '*'){

            System.out.println(op1+" * "+op2+" = "+(op1*op2));
            return op1*op2;

        }
        else if (operator == '/'){

            System.out.println(op1+" / "+op2+" = "+(op1/op2));
            return op1/op2;

        }
        else if (operator == '+'){

            System.out.println(op1+" + "+op2+" = "+(op1+op2));
            return op1+op2;

        }
        else if (operator == '-'){

            System.out.println(op1+" - "+op2+" = "+(op1-op2));
            return op1-op2;
        }

        return 0.0;



    }


    public static void printResults(String infix, String postfix, Double result) {

        // Print results of expression
        JOptionPane.showMessageDialog(null, "The result of the expression is:\nInfix: "+infix+"\nPostfix: "+postfix+"\nResult: "+result);

    }

}


