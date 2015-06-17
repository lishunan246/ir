import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by Henry on 2015/6/17.
 */
public class Tokenizer {
    String passage;
    HashMap<String, Integer> token_tf;
    String punctuation = " \t\n\r\f,.:;?![]'";

    public HashMap<String, Integer> tokenize(String text){
        StringTokenizer st = new StringTokenizer(text, punctuation);
        while(st.hasMoreTokens()){
            System.out.println(st.nextToken());
        }
        return token_tf;
    }

//    public Boolean stopword(String word){
//        return true;
//    }
//
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

    public static void main(String[] args) {
        Tokenizer tknz = new Tokenizer();
        tknz.passage = "This is a tests. can you figures, the ! signed ? out of   the words";
//        tknz.tokenize(tknz.passage);

//        FileInputStream in = null;
//        try {
//            in = new FileInputStream(FilePath);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        System.out.println(tknz.stemTerm("This is a tests. can you figures, the ! signed ? out of   the words"));
    }
}
