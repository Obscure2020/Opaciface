import java.util.*;
import java.io.*;

class Inspector {

    private static String consonate(String input){
        StringBuilder output = new StringBuilder();
        String consonants = "bcdfghjklmnpqrstvwxz";
        for(int i=0; i<input.length(); i++){
            char c = input.charAt(i);
            if(consonants.indexOf(c) >= 0) output.append(c);
        }
        return output.toString();
    }

    private static String front(String input){
        return input.substring(0,3);
    }

    private static String back(String input){
        return input.substring(input.length()-3);
    }

    public static void main(String[] args) throws Exception{
        Scanner scan = new Scanner(System.in);
        Scanner dictionary = new Scanner(new File("words_alpha.txt"));
        ArrayList<String> backMatches = new ArrayList<>();

        System.out.print("Investigative target: ");
        String search = scan.nextLine().toLowerCase();
        scan.close();
        System.out.println();

        System.out.println("Front Matches: ");
        while(dictionary.hasNext()){
            String original = dictionary.nextLine().toLowerCase();
            String proc1 = consonate(original);
            if(proc1.length() < 3) continue;
            String proc2 = front(proc1);
            if(proc2.equals(search)) System.out.print(original + ' ');
            String proc3 = back(proc1);
            if(proc3.equals(search)) backMatches.add(original);
        }
        dictionary.close();
        System.out.println();
        System.out.println();
        System.out.println("Back Matches: ");
        for(String k : backMatches){
            System.out.print(k + ' ');
        }
    }

}