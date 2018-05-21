
import java.util.*;

public class HandScan {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int[] intHand = new int[5];
        String[] signs = new String[5];
        System.out.println("");
        System.out.println("[Welcome to HandScan]");
        System.out.println("Please input your cards in the following format:\n[value(1-10 & j / q / k) color(r / b) sign(s / h / c / d)] (ex: 4 r d)");

        for (int i = 0; i < 5; i++) {
            System.out.print("Input Card: ");
            String in = input.nextLine();
            StringTokenizer st = new StringTokenizer(in);
            String card = st.nextToken();
            if (Character.isDigit(card.charAt(0))) {
                intHand[i] = Integer.parseInt(card);
            } else {
                intHand[i] = faceToInt(card.charAt(0));
            }
            String color = st.nextToken();
            String sign = st.nextToken();
            signs[i] = color + sign;
        }
        System.out.println("--------------------");
        System.out.println("Received Hand:");
        for (int card = 0; card < intHand.length; card++) {
            String value = "" + intHand[card];
            if (intHand[card] > 10) {
                value = valueWord(intToFace(intHand[card]));
            }
            System.out.println(colorWord(signs[card].charAt(0)) + " " + value + " of " + signWord(signs[card].charAt(1)));
        }
        System.out.println("--------------------");

        Arrays.sort(intHand);
        System.out.println("");

        // check
        if (royalFlush(intHand, signs)) {
            System.out.println("Royal Flush");
        } else if (straightFlush(intHand, signs)) {
            System.out.println("Straight Flush");
        } else if (fourOfAKind(intHand)) {
            System.out.println("Four Of A Kind");
        } else if (fullHouse(intHand)) {
            System.out.println("Full House");
        } else if (flush(signs)) {
            System.out.println("Flush");
        } else if (straight(intHand)) {
            System.out.println("Straight");
        } else if (threeOfAKind(intHand)) {
            System.out.println("Three Of A Kind");
        } else if (twoPair(intHand)) {
            System.out.println("Two Pair");
        } else if (onePair(intHand)) {
            System.out.println("One Pair");
        } else {
            System.out.println("High Card");
        }

    }

    private static boolean royalFlush(int[] hand, String[] signs) {
        if (sameSign(signs)) {
            if (hand[hand.length - 1] != 14) {
                return false;
            } else {
                return straightFlush(hand, signs);
            }
        } else {
            return false;
        }
    }

    private static boolean straightFlush(int[] hand, String[] signs) {
        if (sameSign(signs)) {
            return straight(hand);
        } else {
            return false;
        }
    }

    private static boolean fourOfAKind(int[] hand) {
        return getMaxRepetition(hand) >= 4;
    }

    private static boolean threeOfAKind(int[] hand) {
        return getMaxRepetition(hand) >= 3;
    }

    private static boolean fullHouse(int[] hand) {
        if (hand[0] == hand[1]) {
            if (!(hand[1] == hand[2])) {
                if (hand[2] == hand[3] && hand[3] == hand[4]) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (hand[3] == hand[4]) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    private static boolean flush(String[] signs) {
        return sameSign(signs);
    }

    private static boolean straight(int[] hand) {
        int lastCard = hand[0];
        boolean straight = true;
        for (int card = 1; card < hand.length; card++) {
            if (!(hand[card] == lastCard + 1)) {
                straight = false;
                break;
            } else {
                lastCard = hand[card];
            }
        }
        return straight;
    }

    private static boolean twoPair(int[] hand) {
        if (onePair(hand)) {
            if (hand[0] == hand[1]) {
                return hand[2] == hand[3] || hand[3] == hand[4];
            } else {
               return hand[1] == hand[2] && hand[3] == hand[4];
            }
        } else {
            return false;
        }
    }

    private static boolean onePair(int[] hand) {
        return getMaxRepetition(hand) == 2;
    }

    private static boolean sameSign(String[] signs) {
        String lastSign = signs[0];
        boolean sameSigns = true;
        for (int card = 1; card < signs.length; card++) {
            if (!lastSign.equals(signs[card])) {
                sameSigns = false;
            }
        }
        return sameSigns;
    }

    private static int getMaxRepetition(int[] hand) {
        int currMax = 0;
        int max = 0;
        int lastCard = hand[0];
        for (int card = 1; card < hand.length; card++) {
            if (lastCard == hand[card]) {
                currMax++;
            } else {
                max = Math.max(currMax, max);
            }
        }
        return max;
    }

    private static int[] handToInt(char[] hand) {
        int[] newHand = new int[hand.length];
        for (int card = 0; card < hand.length; card++) {
            if (!Character.isDigit(hand[card])) {
                newHand[card] = faceToInt(hand[card]);
            } else {
                newHand[card] = Character.getNumericValue(hand[card]);
            }
        }
        return newHand;
    }

    private static int faceToInt(char face) {
        switch(face) {
            case 'x': return 10;
            case 'j': return 11;
            case 'q': return 12;
            case 'k': return 13;
            case 'a': return 14;
            default: return -1;
        }
    }

    private static char intToFace(int cardInt) {
        switch(cardInt) {
            case 10: return 'x';
            case 11: return 'j';
            case 12: return 'q';
            case 13: return 'k';
            case 14: return 'a';
            default: return 'n';
        }
    }

    private static String colorWord(char color) {
        switch(color) {
            case 'r': return "Red";
            case 'b': return "Black";
            default: return "NULL";
        }
    }

    private static String signWord(char sign) {
        switch(sign) {
            case 'd': return "Diamonds";
            case 'c': return "Clovers";
            case 's': return "Spades";
            case 'h': return "Hearts";
            default: return "NULL";
        }
    }

    private static String valueWord(char value) {
        switch(value) {
            case 'a': return "Ace";
            case 'j': return "Jack";
            case 'q': return "Queen";
            case 'k': return "King";
            default: return "NULL";
        }
    }
}
