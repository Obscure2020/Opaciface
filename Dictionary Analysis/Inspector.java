import java.util.*;
import java.io.*;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

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

    private static double frequency(String input) throws Exception{
        String httpsURL = "https://api.datamuse.com/words?sp=" + input + "&md=f&max=1";
        URL myUrl = new URL(httpsURL);
        HttpsURLConnection conn = (HttpsURLConnection)myUrl.openConnection();
        InputStream is = conn.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String webLine = br.readLine();
        br.close();

        int fLoc = webLine.lastIndexOf(":");
        if(fLoc < 0) return 0.0;
        int matchCheck = webLine.indexOf(input);
        if(matchCheck < 0) return 0.0;
        fLoc++;
        int eLoc = webLine.lastIndexOf("\"");
        String slice = webLine.substring(fLoc, eLoc);
        return Double.valueOf(slice).doubleValue();
    }

    public static void main(String[] args) throws Exception{
        Scanner scan = new Scanner(System.in);
        Scanner dictionary = new Scanner(new File("words_alpha.txt"));
        ArrayList<ScoredWord> frontMatches = new ArrayList<>();
        ArrayList<ScoredWord> backMatches = new ArrayList<>();

        System.out.print("Investigative target: ");
        String search = scan.nextLine().toLowerCase();
        scan.close();
        System.out.println();

        System.out.print("Searching...");
        int count = 0;
        int prevCount = 0;
        while(dictionary.hasNext()){
            String original = dictionary.nextLine().toLowerCase();
            String proc1 = consonate(original);
            if(proc1.length() < 3) continue;

            String proc2 = front(proc1);
            if(proc2.equals(search)){
                double score = frequency(original);
                frontMatches.add(new ScoredWord(original, score));
                count++;
            }

            String proc3 = back(proc1);
            if(proc3.equals(search)){
                double score = frequency(original);
                backMatches.add(new ScoredWord(original, score));
                count++;
            }

            if(count > prevCount && count % 100 == 0){
                System.out.print('.');
                prevCount = count;
            }
        }
        dictionary.close();

        System.out.print(" Sorting...");
        Collections.sort(frontMatches);
        Collections.sort(backMatches);
        System.out.println(" Done.");
        System.out.println();

        System.out.println("Front Matches: ");
        for(ScoredWord k : frontMatches){
            System.out.print(k.getWord() + ' ');
        }
        System.out.println();
        System.out.println();

        System.out.println("Back Matches: ");
        for(ScoredWord k : backMatches){
            System.out.print(k.getWord() + ' ');
        }
        System.out.println();
    }

}