import java.util.*;
import java.util.Map.Entry;
import java.*;
import java.io.File;
import java.io.FileNotFoundException;
/**
 *   FP  Tree object 
 */
class FPTree {
	protected String attribute = "";
	protected int count = 0;
	protected List<FPTree> children = new ArrayList<>();
	protected FPTree parent;
	protected FPTree sibling;
		
    	FPTree() {
    		
    	}
		
	FPTree(int c) {
		this.count = c;
	}
		
	FPTree(String attr, int c) {
		this.attribute = attr;
		this.count = c;
	}
}

/**
 *   Sort Elements Attribute elements   
 */
class compareElements  implements Comparator<FPobject> {
	  @Override
	  public int compare(FPobject e1, FPobject e2) {
		  if(e1.count > e2.count){
			  return -1;
		  } else {
			  return 1;
		  }
	  }
}

/**
 *   Sort Elements Attribute elements based on Prority
 */
class comparePrority  implements Comparator<FPobject> {
	 
	    @Override
	    public int compare(FPobject e1, FPobject e2) {
	        if(e1.prority < e2.prority) {
	            return -1;
	        } else {
	            return 1;
	        }
	    }
}


/**
 *   Algorithm  Methods
 */

public class FPgrowth  {
	
	protected String troot ="root";
	protected FPTree root= new FPTree(troot,0);
	protected HashMap<FPobject,List<FPTree>> pointersMap = new HashMap<>();
	protected int Thresholdnew =0;
	protected List<String[]> dataset = new ArrayList<>();
	protected List<List<FPobject>> datasetNew = new ArrayList<>();
	protected HashSet<FPobject> fpObjectsSet = new HashSet<>();
	protected HashSet<FPobject> fpObjectsSetNew = new HashSet<>();
	protected List<FPobject> order = new ArrayList<>();
	
	/**
	 *   Get FP TreeNode Node
	 */
	public FPTree getnode(String element,FPTree treeelement) {
		  List<FPTree> templist = treeelement.children;
		  FPTree temp = null;
		  if(!templist.isEmpty()){
			 for(int i=0;i<templist.size();i++) {
				  temp = templist.get(i);
				 if(temp.attribute.equals(element)) {
					 return temp;
				 }
				 
			 } 
		  }
		  temp = null;
		  return temp;
	  } 
	
	/**
	 *   Create Fp Tree
	 */
	public FPTree createFptree(FPTree root) {
	
		for(int i=0;i<datasetNew.size();i++) {
			List<FPobject> tempList = datasetNew.get(i);
			if(tempList.isEmpty()) {
			  continue;
			} else {
			  root = fPTreeCreation(tempList, root); 
		    }
		}
		return root;
		
    }
	
	/**
	 *    Fp Tree creation Method (Support Method)
	 */
	public FPTree fPTreeCreation(List<FPobject> list, FPTree root) {
		FPTree tnode = root;
		int count = 0;
		for(int i = 0;i < list.size(); i++) {
			  FPobject fpElement = list.get(i); 
			  FPTree tElement = getnode(fpElement.attribute, tnode);
			  if(tElement == null){
				 FPTree newnode = new FPTree(fpElement.attribute, 1);
				 FPTree tempparent = tnode;
				 tnode.children.add(newnode);
				 tnode = newnode;
				 tnode.parent = tempparent;
				 if(pointersMap.containsKey(fpElement)) {
					   pointersMap.get(fpElement).add(tnode);
					   List<FPTree> tempfpTree = pointersMap.get(fpElement);
					   if(!tempfpTree.isEmpty()) {
					        FPTree previous = tempfpTree.get(tempfpTree.size()-1);
					        previous.sibling = tnode;
					   }
				 } else {
					 List<FPTree> temp = new ArrayList();
					 temp.add(tnode);
					 pointersMap.put(fpElement, temp); 
				 }	  
			  } else {
				  tnode = tElement;
				  tnode.count = tnode.count+ 1; 
			  }	  
		    
      		} 
	  	return root; 
	} 
	
	/**
	 *    Printing Pointer Table.
	 */
	public void printPoniterTable(FPTree root) {
		System.out.println("pointer map");
		for(Entry<FPobject, List<FPTree>> entry:pointersMap.entrySet()) {
			FPobject temp =  (FPobject) entry.getKey();
			System.out.print(temp.attribute + " ");
			List<FPTree> list = (ArrayList) entry.getValue();
			 for(int i = 0; i < list.size(); i++) {
				 System.out.print(" "+list.get(i).attribute);
				 List<String> prefix =	extractPrefixElements(list.get(i), root);
				 for(int j = 0; j < prefix.size(); j++) {
					 System.out.print(prefix.get(j));
				 }
				 System.out.print(" ");
			 }
			 System.out.println();
		}
	}
	
	/**
	 *    Printing FP Tree.
	 */
	public void printfpTree(FPTree root) {
		dfs(root);
		
	}
	
	public void dfs(FPTree root) {
		
		if(root.children.size() <= 0){
			return;
		}
		for(int i=0;i < root.children.size();i++) {
			System.out.println(" name :" + root.children.get(i).attribute+ "count:"+root.children.get(i).count+
					"  parent is:  "+ root.children.get(i).parent.attribute); 
			dfs(root.children.get(i));
			System.out.println();
		}
		return;
	}
	
	/**
	 *    Extract Prefix elements
	 */
	public List<String> extractPrefixElements(FPTree node, FPTree root) {
		FPTree temp = node;
		node = node.parent;
		List<String> result = new ArrayList<>();
		if(node != null) {
			while(node != root) {
				if(node != null) {
					if(node != root)	{
						result.add(node.attribute);
						node = node.parent;	
					}
				}
			}
		
		}
		return result;
	}  
	
	/**
	 *    Extract Prefix elements
	 */
	public void readFile()throws FileNotFoundException  {
		Scanner Infile = new Scanner(new File("C:\\Datasets\\Fpgrowthexample.txt "));
		while(Infile.hasNextLine()) {
			String temp = Infile.nextLine();
			String[] col = temp.split(","); 
			addTrasactions(col);
		
		}
		System.out.println("Dataread sucessfully");	
	}
	
	public void addTrasactions(String[] col) {
		dataset.add(col);	
		createCountTable(col);
	}
	
	/**
	 *    Search FP object
	 */
	public FPobject searchFPobject(String val) {
		 Iterator iterator = fpObjectsSet.iterator();
		 if(!fpObjectsSet.isEmpty()){
			while(iterator.hasNext()){
				 FPobject temp =(FPobject)iterator.next();
				 if(temp!=null) {
					 if(temp.attribute.equals(val)) {
						 return temp;
				 	}
				}
			 
			}
		 }
	   return null;
	}
	
	/**
	 *    Search FP object
	 */
	public void createCountTable(String[] col)	{
		int colsize = col.length;
		List<FPobject> temp = new ArrayList<>();
		for(int i=0;i<colsize;i++) {
			String key = col[i];
			FPobject current = searchFPobject(key);
			if(current == null) {
				 current = new FPobject(key,1);
				 fpObjectsSet.add(current);
				 temp.add(current);
			} else {
			  	current.count = current.count+1;
			  	temp.add(current);
			}
		}	
	
		datasetNew.add(temp);
	}
	
	/**
	 *    Update FP object Set depending upon Threshold
	 */
	public void updateTableOnTreshold(int Threshold) {
		Iterator iterator = fpObjectsSet.iterator();
		while(iterator.hasNext()) {
			FPobject temp = (FPobject)iterator.next();
			if(temp.count < Threshold) {
				continue; 
			 } else {
				 fpObjectsSetNew.add(temp); 
				 order.add(temp);
			 }
				
		}
		Iterator iterator1 = fpObjectsSetNew.iterator();
		while(iterator1.hasNext()) {
			FPobject temp1 = (FPobject)iterator1.next();
		 }
	} 
	
	 
	/**
	 *    Update FP object Set depending upon Threshold
	 */ 
	public void sortElements(int Threshold) {
		if(!order.isEmpty()) {
			Collections.sort(order,new compareElements());
			FPobject tempFpobj = null;
			for (int i = 0; i < order.size(); i++) {
				tempFpobj = order.get(i);
				tempFpobj.prority = i;
			
			}
		}
		if(!datasetNew.isEmpty()) {
			for (int i = 0; i < datasetNew.size(); i++) {
				Collections.sort(datasetNew.get(i),new comparePrority());
			}	 
			for (int i = 0; i < datasetNew.size(); i++) {
				List<FPobject> templist =  datasetNew.get(i);
				for(int j=0;j<templist.size();j++) {
					FPobject fpelement = templist.get(j);
					if(fpelement.count< Threshold) {
						datasetNew.get(i).remove(fpelement); 
					} 
				}
			}
		 }
	}
	
	 
	/**
	 *    Add transactions to a Tree Threshold
	 */ 
	public void addTrasactionsTree(List<String> prefix,int count) {
		List<FPobject> temp = new ArrayList<>();
		for(int i=0;i<prefix.size();i++){
			 String key = prefix.get(i);
			 FPobject current = search (key);
			 if(current == null) {
				  current = new FPobject(key,count);
				  fpObjectsSet.add(current);
				  temp.add(current);
			 } else {
				  current.count = current.count + count;
				  temp.add(current);
			 }
		}
		datasetNew.add(temp);      
	} 
	
}
