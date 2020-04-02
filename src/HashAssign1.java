/*
 * File: HashAssign1.java
 * Author: David Hui
 * Description: Allows a user to generate valid Scrabble words given some letters.
 */
import java.util.*;
import java.io.*;
public class HashAssign1 {
    private static void permutations(String letters, String soFar, HashSet<String> ret) {
        if(letters.length() == 0){ // no more letters to use, add to the HashSet
            ret.add(soFar);
            return;
        }

        // add a letter from the ones that are left
        for(int i=0;i<letters.length();i++){
            permutations(letters.substring(0, i) + letters.substring(i+1), soFar + letters.charAt(i), ret);
        }
    }
    public static void main(String[] args){
        Scanner f;
        // read the file
        try{
            f = new Scanner(new BufferedReader(new FileReader("dictionary.txt")));
        }
        catch(IOException e){
            System.err.println("Error reading file");
            System.exit(1);
            return; // make intellij happy
        }

        // create hashtable to store the words
        HashTable<String> words = new HashTable<>();

        // add all the words
        while(f.hasNext()){
            words.add(f.next().toLowerCase());
        }

        f.close();

        // setup user input
        Scanner in = new Scanner(System.in);
        while(true){
            System.out.println("Please enter the letters (up to eight, ^ to exit): ");
            String letters = in.nextLine().replaceAll("\\s+","").toLowerCase(); //allows spaces or not

            // enforce max character limit
            if(letters.length() > 8){
                System.out.println("You can only enter up to eight letters!");
                continue;
            }

            if(letters.equals("^")){ // stop when the escape character is typed
                break;
            }

            HashSet<String> permutations = new HashSet<>(); // store permutations here

            permutations(letters, "", permutations); // generate the permutations

            for(String p : permutations){ // go through all the permutations and check if they are a word
                if(words.contains(p)){
                    System.out.println(p);
                }
            }
        }

    }
}
