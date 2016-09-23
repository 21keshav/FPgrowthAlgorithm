FP growth Explanation:
FP growth is fast algorithm to compute frequent patterns. One of the main task of FP growth algorithm is to create the (Frequent pattern)
FP growth Tree from the Transactional database. This tree is a compressed version of the database. Initially the frequency of each item 
is computed from the database. If the frequency of item in database is less than a Minimum support count. Then that item set is not 
considered to mine frequent item sets.Then Transactional database is sorted in descending order of its frequency . Using the sorted
Transactional database the FP Growth tree is created. A general set of conditional patterns is created for each frequent item using 
the FP growth tree . A conditional FP Tree is created from the conditional patterns and tree is mined recursively. 
The Frequent patterns are mined at each step as the tree is mined recursively. FP growth algorithm is one of the fastest algorithm to 
mine frequent items from the Dataset. This algorithm overcomes the problems of Aprori based algorithms


Important Classes and Function Explanation.
Java Projects Classes and Functions:- 
1.  Algorithm.java : 
    Main() : Function calls the functions for initial implementation of the algorithm.  
    AlgorithmFPgrowth(): The implementation of Algorithm is done in this function.  
2.  FPGrowth.java:  
    filereadfunction(): Read the File i.e. Dataset. 
    addTrasactions():Adds all the transactions from the dataset. 
    SortElements(): Sorts the dataset depending upon the minimum threshold count and removes the elements. 
    createFpTree(): Creation of FP Tree,Conditional FP tree. 
    extractPrefixElements(): Extraction of conditional base Patterns.  
3. FPTree.java:   
   stores FP_tree object with class FP tree.  
4. FPobject.java:   
   stores an object of each item in the dataset with class FPObject.   