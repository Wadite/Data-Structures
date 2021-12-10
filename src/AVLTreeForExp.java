/**
 *
 * AVLTree
 *
 * An implementation of an AVL Tree with
 * distinct integer keys and info.
 *
 * Student 1: Elior Segal, 326128006, eliorsegal
 * Student 2: Roee Kishon, 205836810, roeekishon
 *
 */

public class AVLTreeForExp extends AVLTree{
	public int searchCost=0;
	public int joinsCostInsplits=0;
	public int maxJoinCost=0;
	public int splitsCnt=0;
	private IAVLNode maxNode;
	
  //################################################################################################## custom functions
  /**
   * public String SearchNode(int k)
   *
   * Returns the last real node in the search for the node with key k.
   * Returns null if the tree is empty
   */
	@Override
  public IAVLNode SearchNode(int k) { //(O(log(n))
	  return fingerSearchNode(k);
	  /*
	  if(empty()) return null;  //empty then surely no node has key k
	  
	  IAVLNode x=root;

		  while(x.isRealNode()) { //while we can keep searching
			  if(x.getKey()==k) // found
				  return x;
			  else if(k<x.getKey()) //if exist then on the left side
				  x=x.getLeft();
			  else					//if exist then on the right side
				  x=x.getRight();
		  }
		  return x.getParent();		//the last real node
		  */
  }
  /**
   * public String SearchNode(int k)
   *
   * Returns the last real node in the search for the node with key k.
   * Returns null if the tree is empty
   */
  public IAVLNode fingerSearchNode(int k) { //(O(log(n))
	  if(empty()) return null;  //empty then surely no node has key k
	  
	  IAVLNode x=maxNode;
	  if(x.getKey()==k) // found
		  return x;
	  
		  while(x!=root) { //while we can keep searching
			  IAVLNode p=x.getParent();
			  searchCost++;
			 
			  if(p.getKey()==k)
				  return p;
			  else if(k<p.getKey()) //if exist then on the left side
				  x=p;
			  else					//if exist then on the right side
				  break;
		  }
		  
		  while(x.isRealNode()) { //while we can keep searching
			  searchCost++;
			  if(x.getKey()==k) // found
				  return x;
			  else if(k<x.getKey()) //if exist then on the left side
				  x=x.getLeft();
			  else					//if exist then on the right side
				  x=x.getRight();
		  }
		  searchCost++;
		  return x.getParent();		//the last real node
  }
  

//##################################################################################################
  /**
   * public int insert(int k, String i)
   *
   * Inserts an item with key k and info i to the AVL tree.
   * The tree must remain valid, i.e. keep its invariants.
   * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
   * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
   * Returns -1 if an item with key k already exists in the tree.
   */
  @Override
   public int insert(int k, String i) {//O(log(n))
	   rotationCount=0;
	   
	  if(empty()) {	//just update the root
		  this.root=new AVLNode(k,i);
		  if(this.maxNode==null||k>this.maxNode.getKey()) maxNode=root;
		  return 0;
	  }
	  IAVLNode x=SearchNode(k);	//find node closets to the place of k
	  if(x.getKey()==k) return -1;	//if k already in the tree
	  
	  
	  AVLNode NodeToAdd=new AVLNode(k,i,x);
	  if(this.maxNode==null||k>this.maxNode.getKey()) maxNode=NodeToAdd;
	  
	  if(x.getKey()>k)	//then add in the left
		  x.setLeft(NodeToAdd);
	  
	  if(x.getKey()<k)	//then add in the right
		  x.setRight(NodeToAdd);
	  
	  updateHeightToRoot(NodeToAdd);	//rebalance if needed
	  return rotationCount;
   }
  @Override
  public AVLTree[] split(int x) {
	  splitsCnt++;
	  return super.split(x);
  }
  @Override
  public int join(IAVLNode x, AVLTree t) {
	  int c=super.join(x, t);
	  joinsCostInsplits+=c;
	  if(c>maxJoinCost)
		  maxJoinCost=c;
	  return c;
	  
  }
  
  public int maxLeftSideTree() {
	  if(empty()) return -1; //no max exists
	   
	   IAVLNode temp=root.getLeft();
	   while(temp.getRight().isRealNode())	//keep going right until we reach the max
			  temp=temp.getRight();
	   return temp.getKey();
  }


}

