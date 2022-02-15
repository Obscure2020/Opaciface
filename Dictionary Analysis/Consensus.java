import java.util.*;
import java.util.Map.*;
import java.io.*;

class Consensus {

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
        //Setup
        Scanner scan = new Scanner(new File("words_alpha.txt"));
        char first = ' ';
        int wordCount = 0;
        Map<String, Integer> totalMap = new HashMap<>();
        Map<String, Integer> frontMap = new HashMap<>();
        Map<String, Integer> backMap = new HashMap<>();

        //Scan
        System.err.print("Progress: ");
        while(scan.hasNext()){
            String original = scan.nextLine().toLowerCase();
            if(original.charAt(0) != first){
                first = original.charAt(0);
                System.err.print(first);
            }
            wordCount++;

            String proc1 = consonate(original);
            if(proc1.length() < 3) continue;

            if(proc1.length() > 3){
                String proc2 = front(proc1);
                String proc3 = back(proc1);
                //Add proc2 to full map
                Integer n = totalMap.get(proc2);
                n = (n == null) ? 1 : n+1;
                totalMap.put(proc2, n);
                //Add proc3 to full map
                n = totalMap.get(proc3);
                n = (n == null) ? 1 : n+1;
                totalMap.put(proc3, n);
                //Add proc2 to front map
                n = frontMap.get(proc2);
                n = (n == null) ? 1 : n+1;
                frontMap.put(proc2, n);
                //Add proc3 to back map
                n = backMap.get(proc3);
                n = (n == null) ? 1 : n+1;
                backMap.put(proc3, n);
            } else { // This segment will run if proc1.length() == 3 exactly.
                //Add proc1 to full map
                Integer n = totalMap.get(proc1);
                n = (n == null) ? 1 : n+1;
                totalMap.put(proc1, n);
                //Add proc1 to front map
                n = frontMap.get(proc1);
                n = (n == null) ? 1 : n+1;
                frontMap.put(proc1, n);
                //Add proc1 to back map
                n = backMap.get(proc1);
                n = (n == null) ? 1 : n+1;
                backMap.put(proc1, n);
            }
        }
        System.err.println();
        scan.close();

        //Results
        System.out.println(wordCount + " words, condensed down to " + totalMap.size() + " consonant stems.");

        //Man, I wish sorting a HashMap by Value was easier. Props to "KK JavaTutorials" on YouTube, whose code I copied.
        Set<Entry<String, Integer>> entrySet = totalMap.entrySet();
        ArrayList<Entry<String, Integer>> sortList = new ArrayList<>(entrySet);
        Collections.sort(sortList, new Comparator<Entry<String, Integer>>() {
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2){
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        for(Entry<String, Integer> k : sortList){
            String s = k.getKey();
            int val = k.getValue();
            Integer fCount = frontMap.get(s);
            int frontCount = (fCount == null) ? 0 : fCount;
            Integer bCount = backMap.get(s);
            int backCount = (bCount == null) ? 0 : bCount;
            System.out.println(s + " - " + val + " - " + frontCount + " front, " + backCount + " back.");
        }
    }

}