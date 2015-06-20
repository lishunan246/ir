import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;




/**
 * Created by cAOGUANGBIAO on 2015/6/20.
 */
public class VSM {
	/**
	 * member
	 */	
	
	public HashMap<String, HashMap<Integer, Indexer>> tokenMap;
	double N;
	
	/**
	 * function
	 */
	
	VSM(HashMap<String, HashMap<Integer, Indexer>>map, Integer n){
		tokenMap = map;
		N = n;
	}
	
	
	public  HashMap<Integer,Double> score(ArrayList Query){
		
		HashMap<Integer,Double>result = new HashMap(); //result
		
		HashMap<Integer, Indexer>  doclist = new HashMap();
		HashMap<String, Integer> dflist = new HashMap();
		HashMap<Integer, Indexer>  temp = new HashMap();
		HashMap<Integer, Indexer>  temp2 = new HashMap();
		
		double score;
		
		//build doclist,calculate df
        for(int i = 0;i <  Query.size(); i ++){
        	if(tokenMap.containsKey(Query.get(i))){
        		temp = tokenMap.get(Query.get(i));
        		
        		int df = temp.size();
        		dflist.put((String) Query.get(i), df);
        		
        		Iterator iter = temp.entrySet().iterator();
        		while (iter.hasNext()) {
        			Map.Entry entry = (Map.Entry) iter.next();
        			Integer key = (Integer)entry.getKey();
        			Indexer val = (Indexer)entry.getValue();
        			doclist.put(key, val);
        		}
        		
        	}
            
        }
        
        
        
        Iterator iterForCos = doclist.entrySet().iterator();
        while (iterForCos.hasNext()) {
        	double wf=0,wf_idf =0,idf=0;
        	double fenmu = 0;  //fenmu- -....
        	
        	
			Map.Entry entry = (Map.Entry) iterForCos.next();
			Integer key = (Integer)entry.getKey();
			
			for(int j = 0;j <Query.size(); j ++){
				temp2 = tokenMap.get(Query.get(j));
				if(temp2.containsKey(key)){
					double dfForCos = dflist.get(Query.get(j));
					idf = Math.log(N/dfForCos );
					float tf = temp2.get(key).getTf();
					wf = 1 + Math.log(tf);
					wf_idf = wf_idf + wf*idf;
					fenmu = fenmu + Math.pow(wf_idf,2);;
				}
			}
			
			result.put(key, wf_idf);
			
			
			
		}
        
        
        
        
        
		
		return result;
	}
}
