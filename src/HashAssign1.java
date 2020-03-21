import java.util.*;
import java.io.*;
public class HashAssign1 {
    private static void permutations(String letters, String soFar, HashSet<String> ret) {
        //System.out.println(letters);
        if(letters.length() == 0){
            //System.out.println("here: " + soFar);
            ret.add(soFar);
            return;
        }
        for(int i=0;i<letters.length();i++){
            permutations(letters.substring(0, i) + letters.substring(i+1), soFar + letters.charAt(i), ret);
        }
    }
    public static void main(String[] args){
        Scanner f;
        try{
            f = new Scanner(new BufferedReader(new FileReader("dictionary.txt")));
        }
        catch(IOException e){
            System.out.println("Error reading file");
            return;
        }

        HashTable<String> words = new HashTable<>();

        while(f.hasNext()){
            words.add(f.next());
        }

        //System.out.println(words.toArray());
        //System.out.println(words.getLoad());

        Scanner in = new Scanner(System.in);
        while(true){
            System.out.println("Please enter the letters (space-separated, ^ to exit): ");
            String letters = in.nextLine().replaceAll("\\s+","");

            if(letters.equals("^")){
                break;
            }

            HashSet<String> permutations = new HashSet<>();

            permutations(letters, "", permutations);

            for(String p : permutations){
                //System.out.println(p);
                if(words.contains(p)){
                    System.out.println(p);
                }
            }
        }

    }
}
