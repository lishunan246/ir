import java.io.*;
import java.util.HashMap;

/**
 * Created by Henry on 2015/6/19.
 */
public class IR {
    public void readFile(Integer from, Integer to, Tokenizer tknz){
        String filename;
        BufferedReader bfr;
        String line;
        String passage = "";
        for(Integer i=from; i<=to; i++){
            filename = "resource/Reuters/" + i.toString() + ".html";
            try {
                bfr = new BufferedReader(new FileReader(new File(filename)));
                while((line = bfr.readLine()) != null){
                    passage += line;
                }
                tknz.tokenize(i, passage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                continue;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Tokenizer tknz = new Tokenizer("resource/stopwords.txt");
        IR ir = new IR();
        ir.readFile(1, 1, tknz);

        try {
            FileOutputStream fos = new FileOutputStream("resource/tokenMap.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(tknz.tokenMap);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Tokenizer tknz2 = new Tokenizer("resource/stopwords.txt");
        try {
            FileInputStream fis = new FileInputStream("resource/tokenMap.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            tknz2.tokenMap = (HashMap<String, HashMap<Integer, Indexer>>) ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
