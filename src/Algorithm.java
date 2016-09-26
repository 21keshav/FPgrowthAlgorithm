import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;

public class Algorithm  {

	public static void main(String[] args) {
		Algorithm al = new Algorithm();
		FPgrowth fp = new FPgrowth();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Treshold values:");
		fp.Thresholdnew = sc.nextInt();
		try {
			fp.readFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    	fp.updateTableOnTreshold(fp.Thresholdnew);
	    	fp.sortElements(fp.Thresholdnew);
	    	fp.root = fp.createFptree(fp.root);
		List<FPobject> templist = new ArrayList<>();
		al.FPgrowthAlgo(fp,templist,fp.Thresholdnew);   
	}

	/**
	 * Algorithm Implementation
	 */
	void FPgrowthAlgo(FPgrowth T,List<FPobject> suffix,int Threshold) {
		
		if(T.root.children.size() <= 0 ) {
			return;	
		}else {
			List<FPobject> Val = suffix;
			for (int i = T.order.size()-1; i >= 0 ; i--) {
				FPobject tempobj = T.order.get(i);
				Val.add(tempobj);
				System.out.println();
				for(int k = 0;k < Val.size(); k++) {
					System.out.print(" " +Val.get(k).attribute);
				}
				
				System.out.print(":"+ tempobj.count);
				FPgrowth Tnew = new FPgrowth();
				List<FPTree> list2 = T.pointersMap.get(tempobj);
				
				for(int k = 0;k<list2.size();k++) {
					FPTree vnode = list2.get(k); 
					List<String> prefix = T.extractPrefixElements(vnode, T.root);
					int count = vnode.count;
					(Tnew).addTrasactionsTree(prefix, count);
				}
				
				Tnew.updateTableOnTreshold(T.Thresholdnew);
				Tnew.sortElements(T.Thresholdnew);
				Tnew.root = Tnew.createFptree(Tnew.root);
				FPgrowthAlgo( Tnew, Val, T.Thresholdnew);
				Val.remove(tempobj); 
			}
				
			return;	 	
				 
		}
	}
		 
}
			
			

	
	
	
