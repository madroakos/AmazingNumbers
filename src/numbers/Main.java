package numbers;

import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static NumProcessing num = new NumProcessing();
    public static void main(String[] args) {
        String input;
        System.out.println("Welcome to Amazing Numbers!\n");
        greeting();
        do {
            input = inputRequest();
            if (input.split(" ")[0].equals("0")) {
                System.out.println("Goodbye!");
                break;
            }else if (!input.equals("")) {
                switch (input.split(" ").length) {
                    case 1 -> num.numeral = Long.parseLong(input);
                    case 2 -> {
                        num.numeral = Long.parseLong(input.split(" ")[0]);
                        num.forHowLong = Long.parseLong(input.split(" ")[1]);
                    }
                    default -> {
                        num.numeral = Long.parseLong(input.split(" ")[0]);
                        num.forHowLong = Long.parseLong(input.split(" ")[1]);
                        for (int i = 2; i < input.split(" ").length; i++) {
                            num.propertySearch.add(input.split(" ")[i].toLowerCase());
                        }
                    }
                }
                num.details();
                num.numeral = 0;
                num.forHowLong = 0;
                num.propertySearch.clear();
            }
        } while (!input.split(" ")[0].equals("0"));

    }

    public static void greeting() {
        System.out.println("""
                Supported requests:
                - enter a natural number to know its properties;
                - enter two natural numbers to obtain the properties of the list:
                \t* the first parameter represents a starting number;
                \t* the second parameter shows how many consecutive numbers are to be processed;
                - two natural numbers and properties to search for;
                - a property preceded by minus must not be present in numbers;
                - separate the parameters with one space;
                - enter 0 to exit.
                """);
    }

    public static String inputRequest() {
        String availableProperties = "EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, JUMPING, SQUARE, SUNNY, HAPPY, SAD";
        StringBuilder errorPropMSG = new StringBuilder();
        StringBuilder wrongWords = new StringBuilder();
        int helpError = 0;
        String input, numeral, howLong;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a request: ");
        input = scanner.nextLine();
        System.out.println();
        if (input.equals("")) {
            return "";
        } else {
            if (input.split(" ").length == 1) {
                if (isLong(input)) {
                    return input;
                } else {
                    System.out.println("The first parameter should be a natural number or zero.\n");
                }
            } else {
                numeral = input.split(" ")[0];
                howLong = input.split(" ")[1];
                if (!isLong(numeral)) {
                    System.out.println("The first parameter should be a natural number or zero.\n");
                }
                if (!isLong(howLong)) {
                    System.out.println("The second parameter should be a natural number.");
                }

                //Check if property word is valid
                for (int i = 2; i < input.split(" ").length; i++) {
                    if (input.split(" ")[i].substring(0, 1).contains("-")) {
                        if (!availableProperties.contains(input.split(" ")[i].substring(1).toUpperCase())) {
                            helpError++;
                            wrongWords.append(input.split(" ")[i].toUpperCase()).append(" ");
                        }
                    } else {
                        if (!availableProperties.contains(input.split(" ")[i].toUpperCase())) {
                            helpError++;
                            wrongWords.append(input.split(" ")[i].toUpperCase()).append(" ");
                        }
                    }
                }
                if (helpError == 1) {
                    errorPropMSG = new StringBuilder(wrongWords.toString().split(" ")[0]);
                    System.out.println("The property [" + errorPropMSG + "] is wrong.");
                    System.out.printf("Available properties: [" + availableProperties + "]\n");
                    return "";
                } else if (helpError > 1) {
                    for (int i = 0; i < helpError - 1; i++) {
                        errorPropMSG.append(wrongWords.toString().split(" ")[i]).append(", ");
                    }
                    errorPropMSG.append(wrongWords.toString().split(" ")[helpError - 1]);
                }
                if (!errorPropMSG.toString().equals("")) {
                    System.out.println("The properties [" + errorPropMSG + "] are wrong.");
                    System.out.printf("Available properties: [" + availableProperties + "]\n");
                    return "";
                }

                //Check for mutually exclusive properties
                String[] properties = new String[input.split(" ").length - 2];
                for (int i = 0; i < input.split(" ").length - 2; i++) {
                    properties[i] = input.split(" ")[i + 2];
                }

                String[][] EXCLUSIVE = new String[][]{
                        {"even", "odd"}, {"spy", "duck"}, {"sunny", "square"}, {"happy", "sad"},
                        {"-even", "-odd"}, {"-happy", "-sad"}
                };

                if (properties.length != 1) {
                    for (int i = 0; i < properties.length - 1; i++) {
                        for (int j = i + 1; j < properties.length; j++) {
                            if ((properties[j].contains(properties[i])) || (properties[i].contains(properties[j]))) {
                                if (properties[i].length() != properties[j].length()) {
                                    System.out.println("The request contains mutually exclusive properties: [" + properties[i].toUpperCase() + ", " + properties[j].toUpperCase() + "]\nThere are no numbers with these properties.");
                                    return "";
                                }
                            }
                            for (String[] strings : EXCLUSIVE) {
                                if (strings[0].contains(properties[i]) || (strings[0].contains(properties[j]))) {
                                    if (strings[1].contains(properties[i]) || (strings[1].contains(properties[j]))) {
                                        System.out.println("The request contains mutually exclusive properties: [" + properties[i].toUpperCase() + ", " + properties[j].toUpperCase() + "]\nThere are no numbers with these properties.");
                                        return "";
                                    }
                                }
                            }
                        }
                    }
                }


                //Can be converted
                if (isLong(numeral) && isLong(howLong)) {
                    return input;
                } else {
                    return "";
                }
            }
        }
        return "";
    }

    public static boolean isLong (String input) {
        long number;
        try {
            number = Long.parseLong(input);
            return number >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

class NumProcessing {
    public long numeral;
    public long forHowLong;
    ArrayList<String> propertySearch = new ArrayList<>();

    NumProcessing() {
        forHowLong = 0;
    }

    public boolean parity() {
        return (this.numeral % 2 == 0);
    }

    public boolean divSeven() {
        return (this.numeral % 7 == 0);
    }

    public boolean end() {
        return (String.valueOf(numeral).endsWith("7"));
    }

    public boolean buzz() {
        return divSeven() || end();
    }

    public boolean gapnum() {
        String num = String.valueOf(this.numeral);
        if (num.length() < 3) {
            return false;
        } else {
            long helper = Long.parseLong(num.charAt(0) + num.substring(num.length() - 1));
            return Long.parseLong(num) % helper == 0;
        }
    }

    public boolean duck() {
        return String.valueOf(this.numeral).indexOf('0') != -1;
    }

    public boolean pali() {
        String helper = String.valueOf(this.numeral);
        char a, b;
        for (int i = 0; i < helper.length(); i++) {
            a = helper.charAt(i);
            b = helper.charAt(helper.length() - 1 - i);
            if (!(a == b)) {
                return false;
            }
        }
        return true;
    }

    public boolean spy() {
        String helper = String.valueOf(this.numeral);
        long add = 0, multi = 1;
        for (int i = 0; i < helper.length(); i++) {
            int hulp = Integer.parseInt(helper.substring(i, i + 1));
            add += hulp;
            multi *= hulp;
        }
        return add == multi;
    }

    public boolean isSquare(Long num) {
        return num % Math.sqrt(num) == 0;
    }

    public boolean isJumping(Long num) {
        String helper = Long.toString(num);
        int help, n = 1;
        if (helper.length() == 1) {
            return true;
        } else if (helper.length() > 1){
            for (int i = 1; i < helper.length(); i++) {
                help = Integer.parseInt(String.valueOf(helper.charAt(i) - helper.charAt(i-1)));
                if (help == 1 || help == -1) {
                    n++;
                } else {
                    break;
                }
            }
        }
        return n == helper.length();
    }

    public boolean isHappy(Long num) {
        long helper;
        long tempNum = num;

        do {
            helper = 0;
            String helperString = String.valueOf(tempNum);
            if (helperString.length() == 1) {
                helper += Math.pow(tempNum, 2);
            } else {
                for (int i = 1; i < helperString.length()+1; i++ ) {
                    helper += Math.pow(Long.parseLong(helperString.substring(i-1,i)),2);
                }
                if (helper == num || helper == 145) {
                    return false;
                } else if (helper == 1) {
                    return true;
                }
            }
            tempNum = helper;
        } while (helper != 1);

        return true;
    }

    public void details() {
        DecimalFormat formatter = new DecimalFormat("#,###");
        if (forHowLong == 0) {
            System.out.printf("Properties of %s\n", formatter.format(numeral));
            System.out.printf("""
                    \t\tbuzz: %s
                    \t\tduck: %s
                    palindromic: %s
                    \t  gapful: %s
                    \t\t spy: %s
                    \t  square: %s
                    \t   sunny: %s
                    \t jumping: %s
                    \t\teven: %s
                    \t\t odd: %s
                    \t   happy: %s
                    \t\t sad: %s

                    """, buzz(), duck(), pali(), gapnum(), spy(), isSquare(numeral), isSquare(numeral+1), isJumping(numeral), parity(), !parity(), isHappy(numeral), !isHappy(numeral));


        } else {
            String msg;
            int helper = 0;
            for (int i = 0; i < forHowLong; ) {
                msg = formatter.format(numeral) + " is ";
                msg += buzz() ? "buzz " : "";
                msg += duck() ? "duck " : "";
                msg += pali() ? "palindromic " : "";
                msg += gapnum() ? "gapful " : "";
                msg += spy() ? "spy " : "";
                msg += isSquare(numeral) ? "square " : "";
                msg += isSquare(numeral+1) ? "sunny " : "";
                msg += isJumping(numeral) ? "jumping " : "";
                msg += parity() ? "even " : "";
                msg += !parity() ? "odd " : "";
                msg += isHappy(numeral) ? "happy " : "";
                msg += !isHappy(numeral) ? "sad " : "";
                if (propertySearch.size() == 0) {
                    System.out.println(msg);
                    i++;
                    numeral++;
                } else {
                    for (int j = 0; j < propertySearch.size(); j++) {
                        if (propertySearch.get(j).contains("-")) {
                            if (!msg.contains(propertySearch.get(j).substring(1))) {
                                helper++;
                            } else {
                                break;
                            }
                        } else {
                            if (msg.contains(propertySearch.get(j))) {
                                helper++;
                            } else {
                                break;
                            }
                        }
                        if (helper == propertySearch.size()) {
                            System.out.println(msg);
                            i++;
                        }
                    }
                    numeral++;
                    helper = 0;
                }
            }
            System.out.println();
        }
    }
}