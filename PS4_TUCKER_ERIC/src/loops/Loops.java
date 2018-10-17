package loops;

import java.util.Scanner;

/**
 * this class contains a collection of static methods practicing searching loops
 * 
 * @author eric tucker
 */
public class Loops
{

    public static void main (String[] args)
    {
    }

    /**
     * returns true if String token 't' is contained within String 's' returns false if otherwise
     */
    public static boolean containsToken (String s, String t)
    {
        try (Scanner scn = new Scanner(s))
        {
            while (scn.hasNext())
            {
                if (scn.next().equals(t))
                {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * returns the line (a string) from Scanner scn that contains String token 't' return empty string if token t does
     * not exist in Scanner scn OR if scanner is empty
     */
    public static String findLineWithToken (Scanner scn, String t)
    {
        while (scn.hasNextLine())
        {
            String line = scn.nextLine();
            if (containsToken(line, t))
            {
                return line;
            }
        }
        return "";
    }

    /**
     * returns true if String 's' is a a palindrome returns false if otherwise Palindrome = a word that reads the same
     * forwards as backwards
     */
    public static boolean isPalindrome (String s)
    {
        for (int i = 0; i < s.length(); i++)
        {
            if (s.charAt(i) != s.charAt(s.length() - i - 1))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * returns the longest string token from Scanner scn that is a palindrome if no palindromes exist in Scanner scn,
     * returns empty string
     */
    public static String findLongestPalindrome (Scanner scn)
    {
        int longest = 0;
        String longestPalindrome = "";
        while (scn.hasNext())
        {
            String token = scn.next();
            if (isPalindrome(token) && token.length() > longest)
            {
                longestPalindrome = token;
                longest = token.length();
            }
        }
        return longestPalindrome;
    }

    /**
     * returns the number of white space characters of the line in Scanner scn with the most white space characters if
     * Scanner scn contains no lines, then returns -1
     */
    public static int findMostWhitespace (Scanner scn)
    {
        int mostWhiteSpace = -1;

        while (scn.hasNextLine())
        {
            String line = scn.nextLine();
            mostWhiteSpace = Math.max(countWhiteSpace(line), mostWhiteSpace);
        }
        return mostWhiteSpace;
    }

    /**
     * returns the count of the number of white space characters in String 'line' this method supports
     * findMostWhiteSpace
     */
    public static int countWhiteSpace (String line)
    {
        int whiteSpace = 0;

        for (int i = 0; i < line.length(); i++)
        {
            if (Character.isWhitespace(line.charAt(i)))
            {
                whiteSpace++;
            }
        }
        return whiteSpace;
    }

}
