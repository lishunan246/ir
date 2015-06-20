import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Henry on 2015/6/19.
 */
public class IR {
    private Integer passageNum;

    IR(){
        passageNum = 0;
    }

    public void readFile(Integer from, Integer to, Tokenizer tknz){
        String filename;
        BufferedReader bfr;
        String line;
        String passage;
        for(Integer i=from; i<=to; i++){
            passage = "";
            filename = "resource/Reuters/" + i.toString() + ".html";
            try {
                setPassageNum(getPassageNum()+1);
                bfr = new BufferedReader(new FileReader(new File(filename)));
                while((line = bfr.readLine()) != null){
                    passage += line;
                }
                tknz.indexing(i, passage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                setPassageNum(getPassageNum()-1);
                continue;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Integer getPassageNum() {
        return passageNum;
    }

    public void setPassageNum(Integer passageNum) {
        this.passageNum = passageNum;
    }

    public static void main(String[] args) {
//        Tokenizer tknz = new Tokenizer("resource/stopwords.txt");
//        IR ir = new IR();
//        ir.readFile(1, 1000, tknz);
//
//        try {
//            FileOutputStream fos = new FileOutputStream("resource/tokenMap1000.ser");
//            FileOutputStream fos2 = new FileOutputStream("resource/passageNum1000.ser");
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
//            oos.writeObject(tknz.tokenMap);
//            oos2.writeObject(ir.getPassageNum());
//            oos.close();
//            oos2.close();
//            fos.close();
//            fos2.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Tokenizer tknz2 = new Tokenizer("resource/stopwords.txt");
        IR ir2 = new IR();
        try {
            FileInputStream fis = new FileInputStream("resource/tokenMap1000.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            tknz2.tokenMap = (HashMap<String, HashMap<Integer, Indexer>>) ois.readObject();
            ois.close();
            fis.close();
            FileInputStream fis2 = new FileInputStream("resource/passageNum1000.ser");
            ObjectInputStream ois2 = new ObjectInputStream(fis2);
            ir2.setPassageNum((Integer) ois2.readObject());
            ois2.close();
            fis2.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String input;

        VSM vsm = new VSM(tknz2.tokenMap, ir2.getPassageNum());
        HashMap<Integer,Double> scoreresult;

        System.out.println("input a string");
        Scanner in = new Scanner(System.in);
        input = in.nextLine();
        scoreresult = vsm.score(tknz2.tokenize(input));
        in.close();
        System.out.println(scoreresult);
    }
}
