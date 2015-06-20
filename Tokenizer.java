import java.io.*;
import java.util.*;

/**
 * Created by Henry on 2015/6/17.
 */
public class Tokenizer {
    private String passage;
    private String punctuation;
    private List stopwords = new ArrayList<String>();
    public HashMap<String, HashMap<Integer, Indexer>> tokenMap = new HashMap<>();

    Tokenizer(String stopwordsPath){
        try {
            passage = "";
            punctuation = " \t\n\r\f,.:;?![]0123456789()+-*/<>\"@#$%^&~\\|";
            BufferedReader bfr1 = new BufferedReader(new FileReader(stopwordsPath));
            String line;
            while((line = bfr1.readLine()) != null){
                stopwords.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tokenize(Integer docID, String text){
        StringTokenizer st = new StringTokenizer(text, punctuation);
        String tempWord;
        Integer pos = 0;

        while(st.hasMoreTokens()){
            pos += 1;
            if(!isStopword(tempWord = st.nextToken())) {
                tempWord = stemTerm(tempWord);
                if(!tokenMap.containsKey(tempWord)){
                    HashMap<Integer, Indexer> docIDMap = new HashMap<>();
                    Indexer indexer = new Indexer(docID, pos);
                    docIDMap.put(docID, indexer);
                    tokenMap.put(tempWord, docIDMap);
                }else{
                    if(!tokenMap.get(tempWord).containsKey(docID)){
                        Indexer indexer = new Indexer(docID, pos);
                        tokenMap.get(tempWord).put(docID, indexer);
                    }else{
                        tokenMap.get(tempWord).get(docID).updateTf();
                        tokenMap.get(tempWord).get(docID).updatePosList(pos);
                    }
                }
            }
        }
    }

    public Boolean isStopword(String word){
        if(stopwords.contains(word)){
            return true;
        }else{
            return false;
        }
    }

    public String stemTerm(String term){
        char[] w = new char[501];
        Stemmer s = new Stemmer();
        StringBuffer sb = new StringBuffer();

        int i = 0;
        while(i < term.length()){
            int ch = 0;
            ch = term.charAt(i++);
            if (Character.isLetter((char) ch))
            {
                int j = 0;
                while(true)
                {  ch = Character.toLowerCase((char) ch);
                    w[j] = (char) ch;
                    if (j < 500) j++;
                    if(i < term.length()) {
                        ch = term.charAt(i++);
                    }else {
                        ch = -1;
                    }
                    if (!Character.isLetter((char) ch))
                    {
                       /* to test add(char ch) */
                        for (int c = 0; c < j; c++) s.add(w[c]);

                       /* or, to test add(char[] w, int j) */
                       /* s.add(w, j); */

                        s.stem();
                        {  String u;

                          /* and now, to test toString() : */
                            u = s.toString();

                          /* to test getResultBuffer(), getResultLength() : */
                          /* u = new String(s.getResultBuffer(), 0, s.getResultLength()); */

                            sb.append(u);
                        }
                        break;
                    }
                }
            }
            if (ch < 0) break;
            sb.append((char)ch);

        }
        return sb.toString();
    }

//    public static void main(String[] args) {
//        Tokenizer tknz = new Tokenizer("resource/stopwords.txt");
//        tknz.passage = "March/April-June/July,";
//
//        tknz.tokenize(1, tknz.passage);
//        tknz.tokenize(2, tknz.passage);
//        tknz.tokenize(3, tknz.passage);
//    }
}
