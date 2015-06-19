import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Henry on 2015/6/16.
 */
public class Indexer {
    private Integer docID;
    private Integer tf;
    private List posList;

    Indexer(Integer d, Integer pos){
        setDocID(d);
        tf = 0;
        posList = new ArrayList<Integer>();
        updateTf();
        updatePosList(pos);
    }


    public Integer getTf() {
        return tf;
    }

    public void updateTf() {
        this.tf += 1;
    }

    public List getPosList() {
        return posList;
    }

    public void updatePosList(Integer pos) {
        this.posList.add(pos);
    }

    public Integer getDocID() {
        return docID;
    }

    public void setDocID(Integer docID) {
        this.docID = docID;
    }

}
