
/**
 *
 * AVLTree
 *
 * An implementation of an AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTree {
	
	private int rotationCount=0; //count rotations in insertion and deletion
	IAVLNode root; //the root of the tree
	
	

  /**
   * public boolean empty()
   *
   * Returns true if and only if the tree is empty.
   *
   */
  public boolean empty() { //O(1)
    return (this.root == null|| !this.root.isRealNode()); // make sure the root is a real node
  }
  
  //################################################################################################## custom functions
  /**
   * public String SearchNode(int k)
   *
   * Returns the last real node in the search for the node with key k.
   * Returns null if the tree is empty
   */
  public IAVLNode SearchNode(int k) { //(O(log(n))
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
  }
  
  /**
   * public IAVLNode Successor(IAVLNode x)
   *
   * Returns the Successor of x
   * Returns null if x is empty or x is max
   */
  public IAVLNode Successor(IAVLNode x) { //O(log(n))
	  if(x.isRealNode()==false) return null;	//only real nodes have Successors
	  
	  IAVLNode temp;
	  if(x.getRight().isRealNode()) {	//then the successor is the most left node in the right tree
		  temp=x.getRight();
		  while(temp.getLeft().isRealNode())	//keep going left until we reach the end
			  temp=temp.getLeft();
		  return temp;
	  }
	  else {	//then the successor is the most left parent
		  temp=x.getParent();
		  while(temp!=null&&x==temp.getRight()) {	//keep going up left until we reach the end
			  x=temp;
			  temp=x.getParent();
		  }
		  return temp;
	  }
		  
  }
  
  public void updateHeightToRoot(IAVLNode x) { //O(height(root)-height(x))	//rebalance the tree form x upwards
	  IAVLNode z;
	  do {
		  
		  int bf=getBf(x);	//-balance factor
		  if(bf==1||bf==-1) AVLNode.updateHeight(x);	//just promote or demote if needed
		  else {
			  
			  if(bf<=-2) {	//we need to rotate
				  if(getBf(x.getLeft())==-1) {	//we need rotate the left child
					  rotate(x.getLeft());
				  }
				  else if(getBf(x.getLeft())==1) {	//double rotation
					  z=x.getLeft().getRight();
					  rotate(z);rotate(z);
				  }
				  else if(x.getLeft().isRealNode()==false) {	//we need rotate the right child
					  rotate(x.getRight());
					  AVLNode.updateHeight(x);	
					  x=x.getParent();	//prevent loops
				  }
				  //the same as before but symmetrically 
				  else if(getBf(x.getRight())==1) {		//we need rotate the right child
					  rotate(x.getRight());
				  }
				  else if(getBf(x.getRight())==-1) {	//double rotation
					  z=x.getRight().getLeft();
					  rotate(z);rotate(z);
				  }
				  else if(x.getRight().isRealNode()==false) {	//we need rotate the left child
					  rotate(x.getLeft());
					  AVLNode.updateHeight(x);
					  x=x.getParent();
				  }
				  else {
					  rotate(x.getLeft());
					  AVLNode.updateHeight(x);
					  x=x.getParent();
				  }
				  
			  }
			  //the same as before but symmetrically 
			  if(bf>=2) {	//we need to rotate
				  if(getBf(x.getRight())==1) {	//we need rotate the right child
					  rotate(x.getRight());
				  }
				  else if(getBf(x.getRight())==-1) {	//double rotation
					  z=x.getRight().getLeft();
					  rotate(z);rotate(z);
				  }
				  else if(x.getRight().isRealNode()==false) {	//we need rotate the left child
					  rotate(x.getLeft());
					  AVLNode.updateHeight(x);
					  x=x.getParent();
					 // System.out.print("!2"+x.getValue()+" "+nodeCount);
				  }
				  
				  else if(getBf(x.getLeft())==-1) {
					  rotate(x.getLeft());
				  }
				  else if(getBf(x.getLeft())==1) {	//double rotation
					  z=x.getLeft().getRight();
					  rotate(z);rotate(z);
				  }
				  else if(x.getLeft().isRealNode()==false) {	//we need rotate the right child
					  rotate(x.getRight());
					  AVLNode.updateHeight(x);
					  x=x.getParent();
					  //System.out.print("!1"+x.getValue()+" "+nodeCount);
				  }
				  else {
					  rotate(x.getRight());
					  AVLNode.updateHeight(x);
					  x=x.getParent();
				  }
				  
			  }
		  }
		  AVLNode.updateHeight(x);
		  x=x.getParent(); //go upwards
	  }while(x!=null); //until we got past the root
	  if(x==root) AVLNode.updateHeight(x); 
	  
  }
    
  public void rotate(IAVLNode x) {	//O(1) //rotating x
	  if(x==root) return;	//can not rotate
	  rotationCount++;	//counting
	  IAVLNode p =x.getParent();
	  IAVLNode grandFather=p.getParent();
	  if(p.getRight()==x) {//rotating left
		  p.setRight(x.getLeft());
		  x.setLeft(p); 
	  }
	  else {//rotating right
		  p.setLeft(x.getRight());
		  x.setRight(p);  
	  }
	  x.setParent(grandFather);	//the new parent is the parent of the old parent
	  if(grandFather!=null) {	//update grandFather if needed
		  if(grandFather.getKey()>x.getKey()) grandFather.setLeft(x);
		  else grandFather.setRight(x);
	  }
	  
	  if(p==root) root= x;	//update the root of the tree if needed
  }
  public static int getBf(IAVLNode x) { //O(1)	//returns -balance factor
	  if(!x.isRealNode()) return 0;
  	return -(x.getLeft().getHeight()-x.getRight().getHeight());
  }
  
//##################################################################################################

 /**
   * public String search(int k)
   *
   * Returns the info of an item with key k if it exists in the tree.
   * otherwise, returns null.
   */
  public String search(int k) { //O(log(n))
	//return "searchDefaultString";  // to be replaced by student code
	  
	  
	 IAVLNode x=SearchNode(k);	//find the node if exists
	 if(x!=null&&x.getKey()==k) return x.getValue();	//return the value if exists
	 return null;	//otherwise return null
  }

  /**
   * public int insert(int k, String i)
   *
   * Inserts an item with key k and info i to the AVL tree.
   * The tree must remain valid, i.e. keep its invariants.
   * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
   * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
   * Returns -1 if an item with key k already exists in the tree.
   */
   public int insert(int k, String i) {//O(log(n))
	   rotationCount=0;
	  if(empty()) {	//just update the root
		  this.root=new AVLNode(k,i);
		  return 0;
	  }
	  IAVLNode x=SearchNode(k);	//find node closets to the place of k
	  if(x.getKey()==k) return -1;	//if k already in the tree
	  
	  
	  AVLNode NodeToAdd=new AVLNode(k,i,x);
	  if(x.getKey()>k)	//then add in the left
		  x.setLeft(NodeToAdd);
	  
	  if(x.getKey()<k)	//then add in the right
		  x.setRight(NodeToAdd);
	  
	  updateHeightToRoot(NodeToAdd);	//rebalance if needed
	  return rotationCount;
   }

  /**
   * public int delete(int k)
   *
   * Deletes an item with key k from the binary tree, if it is there.
   * The tree must remain valid, i.e. keep its invariants.
   * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
   * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
   * Returns -1 if an item with key k was not found in the tree.
   */
   public int delete(int k) {//O(log(n))
	   rotationCount=0;
	   if(empty()) {	//node with key k doesn't exist
			  return -1;
		  }
		  IAVLNode x=SearchNode(k);		//find the node with key k
		  if(x.getKey()==k) {	//the node in the tree
			  
			  IAVLNode p=x.getParent();
			  IAVLNode replaceBy=new AVLNode();
			  IAVLNode UpdateH=replaceBy;
			  if(!x.getLeft().isRealNode()&&!x.getRight().isRealNode())	//x is a leaf
				  replaceBy=new AVLNode();
			  if(x.getLeft().isRealNode()&&x.getRight().isRealNode()) {	//x has both children
				  replaceBy=Successor(x);	//x has to be replaced by his successor
				  UpdateH=replaceBy;
				  if(replaceBy==null) {		//x has not a successor
					  replaceBy=new AVLNode();
					  UpdateH=replaceBy;
				  }
				  else {	//x has a successor
					  
					//update the nodes to their new children and parents
					  
					  if(replaceBy.getParent().getLeft()==replaceBy) {	
						  replaceBy.getParent().setLeft(replaceBy.getRight());
						  UpdateH = replaceBy.getRight();	//start rebalancing from the lower change
						  
						  if(x==root) {
							  replaceBy.setParent(null);
							  root= replaceBy;
						  }
						  else {
							  if(p.getLeft()==x) {
								  p.setLeft(replaceBy);
							  }
							  if(p.getRight()==x) {
								  p.setRight(replaceBy);
							  }
						  }
						  
						  
						  replaceBy.setLeft(x.getLeft());
						  
						  replaceBy.setRight(x.getRight());
						  
						  
						  updateHeightToRoot(UpdateH);	//rebalance if needed
						  return rotationCount;
					  }
					  if(replaceBy.getParent().getRight()==replaceBy) {		
						  
						  if(x==root) {
							  replaceBy.setParent(null);
							  root= replaceBy;
						  }
						  else {
							  if(p.getLeft()==x) {
								  p.setLeft(replaceBy);
							  }
							  if(p.getRight()==x) {
								  p.setRight(replaceBy);
							  }
						  }
						  replaceBy.setLeft(x.getLeft());
						  
						  updateHeightToRoot(replaceBy);	//rebalance if needed
						  return rotationCount;
						  
					  }
					  
				  }
			  }
			  if(x.getLeft().isRealNode() && !x.getRight().isRealNode())	//x has only left child, we will replace him with his child
				  replaceBy=x.getLeft();

			  if(!x.getLeft().isRealNode() && x.getRight().isRealNode())	//x has only left child, we will replace him with his child
				  replaceBy=x.getRight();
			  
			  //update the nodes to their new children and parents
			  
			  if(x==root) {
				  replaceBy.setParent(null);
				  root= replaceBy;
			  }
			  else {
				  if(p.getLeft()==x) {
					  p.setLeft(replaceBy);
				  }
				  if(p.getRight()==x) {
					  p.setRight(replaceBy);
				  }
			  }
			  
			  updateHeightToRoot(replaceBy);	//rebalance if needed
			  return rotationCount;
		  }
		  
		  return -1;	//the node is not in the tree
		  
		     }

   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty.
    */
   public String min() {	//O(log(n))
	   if(empty()) return null;	//no min exists
	   
	   IAVLNode temp=root;
	   while(temp.getLeft().isRealNode())	//keep going left until we reach the min
			  temp=temp.getLeft();
	   return temp.getValue();
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty.
    */
   public String max() {//O(log(n))
	   if(empty()) return null; //no max exists
	   
	   IAVLNode temp=root;
	   while(temp.getRight().isRealNode())	//keep going right until we reach the max
			  temp=temp.getRight();
	   return temp.getValue();
   }

  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
  public int[] keysToArray() {//O(n)
	  	int [] arr = new int[size()];	//to store the keys
	  	int arr_indx=0;
	  	IAVLNode [] stack = new IAVLNode[root.getHeight()+1];//store the nodes which we have not ended adding
	  	int stackI = 0;
	  	
	  	if(empty())	//no keys
	  		return arr;
	  	
	  	IAVLNode temp=root;
	  	do {	//add the left most node but keep track of the way
	  		
	  		while(temp.isRealNode()) {	//go to the left most  node (the min) in temp
	  			stack[stackI++]=temp;
	  			temp=temp.getLeft();
	  		}
	  		stackI--;
	  		temp=stack[stackI];	//start coming back up
	  		arr[arr_indx++]=temp.getKey();	//add the key to the array
	  		temp=temp.getRight();	//now add all the keys in the right sub tree
	  		
	  	}while(stackI>0||temp.isRealNode());	//until we reached the end of the right sub tree of root
	  	
	  	
	  	
	  	return arr;
	  	  }

  /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
  public String[] infoToArray() {//O(n)
	  String [] arr = new String[size()];//to store the values
	  	int arr_indx=0;
	  	IAVLNode [] stack = new IAVLNode[root.getHeight()+1];//store the nodes which we have not ended adding
	  	int stackI = 0;
	  	
	  	if(empty())//no keys
	  		return arr;
	  	
	  	IAVLNode temp=root;
	  	do {	//add the left most node but keep track of the way
	  		
	  		while(temp.isRealNode()) {	//go to the left most  node (the min) in temp
	  			stack[stackI++]=temp;
	  			temp=temp.getLeft();
	  		}
	  		stackI--;
	  		temp=stack[stackI];	//start coming back up
	  		arr[arr_indx++]=temp.getValue();	//add the value to the array
	  		temp=temp.getRight();	//now add all the values in the right sub tree
	  		
	  	}while(stackI>0||temp.isRealNode());	//until we reached the end of the right sub tree of root
	  	
	  	
	  	
	  	return arr;
  }

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    */
   public int size() {//O(n)
	   int cnt=0;//to count the nodes
	   
	   if(empty())	//no keys
	  		return 0;
	   
	  	IAVLNode [] stack = new IAVLNode[root.getHeight()+1];	//store the nodes which we have not ended counting
	  	int stackI = 0;
	  	
	  	
	  	IAVLNode temp=root;
	  	do {	//add the left most node but keep track of the way
	  		
	  		while(temp.isRealNode()) {	//go to the left most node (the min) in temp
	  			stack[stackI++]=temp;
	  			temp=temp.getLeft();
	  		}
	  		stackI--;
	  		temp=stack[stackI];	//start coming back up
	  		cnt++;	//count on the way
	  		temp=temp.getRight();	//now count the nodes in the right sub tree
	  		
	  	}while(stackI>0||temp.isRealNode());//until we reached the end of the right sub tree of root
	  	
	  	
	  	
	  	return cnt;
   }
   
   /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    */
   public IAVLNode getRoot() {//O(1)
	   return root;
   }
   
   /**
    * public AVLTree[] split(int x)
    *
    * splits the tree into 2 trees according to the key x. 
    * Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
    * 
	* precondition: search(x) != null (i.e. you can also assume that the tree is not empty)
    * postcondition: none
    */   
   public AVLTree[] split(int x) {//O(height(x))
	   IAVLNode n = SearchNode(x);
	   IAVLNode p=n.getParent();
	   
	   AVLTree t1=new AVLTree();
	   if(n.getLeft().isRealNode()) {	//init t1 to be the left sub tree of n
		   t1.root=n.getLeft();
		   t1.root.setParent(null);
	   }
	   AVLTree t2=new AVLTree();
	   if(n.getRight().isRealNode()) {	//init t2 to be the right sub tree of n
		   t2.root=n.getRight();
		   t2.root.setParent(null);
	   }
	   
	   while(n!=root) {	//until we have not reach the root
		   p=n.getParent();
		   
		   if(p.getRight()==n) {	//p<n => add p to t1 and its left sub tree
			   AVLTree temp=new AVLTree();
			   if(p.getLeft().isRealNode()) {
				   temp.root=p.getLeft();
				   temp.root.setParent(null);
			   }
			   IAVLNode p2=new AVLNode(p.getKey(),p.getValue());
			   t1.join(p2, temp);
		   }
		   else {	//p>n => add p to t2 and its right sub tree
			   AVLTree temp=new AVLTree();
			   if(p.getRight().isRealNode()) {
				   temp.root=p.getRight();
				   temp.root.setParent(null);
			   }
			   IAVLNode p2=new AVLNode(p.getKey(),p.getValue());
			   t2.join(p2, temp);
		   }
		   
		   n=n.getParent();	//go upwards
	   }
	   
	   
	   return new AVLTree[] {t1,t2};
	   
   }
   
   /**
    * public int join(IAVLNode x, AVLTree t)
    *
    * joins t and x with the tree. 	
    * Returns the complexity of the operation (|tree.rank - t.rank| + 1).
	*
	* precondition: keys(t) < x < keys() or keys(t) > x > keys(). t/tree might be empty (rank = -1).
    * postcondition: none
    */   
   public int join(IAVLNode x, AVLTree t) {//O(|tree.rank - t.rank| + 1)
	   if(t.empty()) {	//insert only x
		   int r;
		   if(this.root!=null) r=this.root.getHeight();
		   else r=-1;
		   insert(x.getKey(),x.getValue());
		   return r+1;
	   }
	   
	   if(empty()) {	//become t and insert x
		   this.root=t.root;
		   int r=this.root.getHeight();
		   insert(x.getKey(),x.getValue());
		   

		   return r+1;
	   }
	   	   
	   if(this.root.getHeight()==t.root.getHeight()) {	//the same height then make x the new root and its children this and t
		   if(x.getKey()>root.getKey()) {
			   x.setLeft(root);
			   x.setRight(t.root);
			   root=x;
			   
		   }
		   else {
			   x.setLeft(t.root);
			   x.setRight(root);
			   root=x;
		   }
		   return 1;

	   }
	   else {	//different heights
	   
		   if(this.root.getHeight()<t.root.getHeight()) {	//assume this is the higher tree otherwise replace with t
			   IAVLNode temp=this.root;
			   root=t.root;
			   t.root=temp;
		   }
		   
		   int c=this.root.getHeight()-t.root.getHeight()+1;	//complexity
		   
		   IAVLNode src=this.root;
		   
		   if(x.getKey()>root.getKey()) {
			   while(src.isRealNode()&&src.getHeight()>t.root.getHeight()) src=src.getRight();	//find the right height
			   IAVLNode p =src.getParent();
			   x.setRight(t.root);
			   x.setLeft(src);
			   p.setRight(x);
			   updateHeightToRoot(x);	//rebalance if needed
		   }
		   else {
			   while(src.isRealNode()&&src.getHeight()>t.root.getHeight()) src=src.getLeft();	//find the right height
			   IAVLNode p =src.getParent();
			   x.setLeft(t.root);
			   x.setRight(src);
			   p.setLeft(x);
			   updateHeightToRoot(x);	//rebalance if needed
		   }
		   
		   return c;
	   }
	   
	   
   }

	/** 
	 * public interface IAVLNode
	 * ! Do not delete or modify this - otherwise all tests will fail !
	 */
	public interface IAVLNode{	
		public int getKey(); // Returns node's key (for virtual node return -1).
		public String getValue(); // Returns node's value [info], for virtual node returns null.
		public void setLeft(IAVLNode node); // Sets left child.
		public IAVLNode getLeft(); // Returns left child, if there is no left child returns null.
		public void setRight(IAVLNode node); // Sets right child.
		public IAVLNode getRight(); // Returns right child, if there is no right child return null.
		public void setParent(IAVLNode node); // Sets parent.
		public IAVLNode getParent(); // Returns the parent, if there is no parent return null.
		public boolean isRealNode(); // Returns True if this is a non-virtual AVL node.
    	public void setHeight(int height); // Sets the height of the node.
    	public int getHeight(); // Returns the height of the node (-1 for virtual nodes).
	}

   /** 
    * public class AVLNode
    *
    * If you wish to implement classes other than AVLTree
    * (for example AVLNode), do it in this file, not in another file. 
    * 
    * This class can and MUST be modified (It must implement IAVLNode).
    */
  public class AVLNode implements IAVLNode{
	  
	  private int key = -1;
	  private String info = null;
	  private IAVLNode left = null;
	  private IAVLNode right = null;
	  private IAVLNode parent = null;
	  private int height = -1;
	  private int bf; 					// short for balance factor [(height of left child) - (height of right child)]
	  	
	  public AVLNode(int k,String i,IAVLNode l,IAVLNode r,IAVLNode p,int h) {
	  		this.key=k;
	  		this.info=i;
	  		this.left=l;
	  		this.right=r;
	  		this.parent=p;
	  		this.height=h;
	  		
	  		if(k!=-1) {
	  			this.left.setParent(this);
	  			this.right.setParent(this);
	  		}
	  	}
	  
	  	public AVLNode() {
	  		this(-1,null,null,null,null,-1);
	  	}
	  	public AVLNode(int k,String i) {
	  		this(k,i,new AVLNode(),new AVLNode(),null,0);
	  	}
	  	public AVLNode(int k,String i,IAVLNode l,IAVLNode r,IAVLNode p) {
	  		this(k,i,l,r,p,0);
	  	}
	  	public AVLNode(int k,String i,IAVLNode p) {
	  		this(k,i,new AVLNode(),new AVLNode(),p,0);
	  		
	  	}
	  	
	  	
		public int getKey() {
			return this.key; 
		}
		public String getValue() {
			return this.info; 
		}
		public void setLeft(IAVLNode node) {
			this.left = node;
			updateHeight(this);	//update height
			node.setParent(this);	//update child's parent
			return; 
		}
		public IAVLNode getLeft() {
			return this.left;
		}
		public void setRight(IAVLNode node) {
			this.right = node;
			updateHeight(this);	//update height
			node.setParent(this);	//update child's parent
			return;
		}
		public IAVLNode getRight() {
			return this.right; 
		}
		public void setParent(IAVLNode node) {
			this.parent = node;
			updateHeight(this);	//update height
			return; 
		}
		public IAVLNode getParent() {
			return this.parent; 
		}
		public boolean isRealNode() {
			return (this.key == -1) ? false : true; 
		}
	    public void setHeight(int height) {
	    	this.height = height;
	      return; 
	    }
	    public int getHeight() {
	      return this.height;
	    }
	    
	    public static int updateHeight(IAVLNode x) {
	    	int lh,rh;
	    	
	    	if(x.getLeft()==null) lh=-10;
	    	else lh=x.getLeft().getHeight();
	    	
	    	if(x.getRight()==null) rh=-10;
	    	else rh=x.getRight().getHeight();
	    	
	    	x.setHeight(Math.max(lh, rh)+1);
	    	
	    	if(x.getHeight()<0&&x.getKey()>0) x.setHeight(0); //x is a real leaf
	    	if(x.getHeight()<0&&x.getKey()<0) x.setHeight(-1);	//x is a virtual leaf
	    	return getBf(x);
	    }
  }

}
  
