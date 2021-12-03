/**
 *
 * AVLTree
 *
 * An implementation of an AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTree {
	
	private int nodeCount = 0;
	private int rotationCount=0;
	AVLNode root;
	
	

  /**
   * public boolean empty()
   *
   * Returns true if and only if the tree is empty.
   *
   */
  public boolean empty() {
    return (this.nodeCount == 0); // to be replaced by student code
  }
  
  //################################################################################################## custom functions
  /**
   * public String SearchNode(int k)
   *
   * Returns the last real node in the search for the node with key k.
   * Returns null if the tree is empty
   */
  public IAVLNode SearchNode(int k) {
	  if(empty()) return null;
	  
	  IAVLNode x=root;

		  while(x.isRealNode()) {
			  if(x.getKey()==k)
				  return x;
			  else if(k<x.getKey())
				  x=x.getLeft();
			  else
				  x=x.getRight();
		  }
		  return x.getParent();
  }
  
  /**
   * public IAVLNode Successor(IAVLNode x)
   *
   * Returns the Successor of x
   * Returns null if x is empty or x is max
   */
  public IAVLNode Successor(IAVLNode x) {
	  if(x.isRealNode()==false) return null;
	  
	  IAVLNode temp;
	  if(x.getRight().isRealNode()) {
		  temp=x.getRight();
		  while(temp.getLeft().isRealNode())
			  temp=temp.getLeft();
		  return temp;
	  }
	  else {
		  temp=x.getParent();
		  while(temp!=null&&x==temp.getRight()) {
			  x=temp;
			  temp=x.getParent();
		  }
		  return temp;
	  }
		  
  }
  
  public void updateHeightToRoot(IAVLNode x) {
	  IAVLNode z;
	  do {
		  
		  int bf=getBf(x);
		  //System.out.print("bf = "+bf);
		  if(bf==1||bf==-1) AVLNode.updateHeight(x);
		  else {
			  
			  if(bf<=-2) {
				  if(getBf(x.getLeft())==-1) {
					  rotate(x.getLeft());
				  }
				  else if(getBf(x.getLeft())==1) {
					  z=x.getLeft().getRight();
					  rotate(z);rotate(z);
				  }
				  else if(x.getLeft().isRealNode()==false) {
					  rotate(x.getRight());
					  AVLNode.updateHeight(x);
					  x=x.getParent();
					  //System.out.print("!1"+x.getValue()+" "+nodeCount);
				  }
				  
				  else if(getBf(x.getRight())==1) {
					  rotate(x.getRight());
				  }
				  else if(getBf(x.getRight())==-1) {
					  z=x.getRight().getLeft();
					  rotate(z);rotate(z);
				  }
				  else if(x.getRight().isRealNode()==false) {
					  rotate(x.getLeft());
					  AVLNode.updateHeight(x);
					  x=x.getParent();
					 // System.out.print("!2"+x.getValue()+" "+nodeCount);
				  }
				  
			  }
			  
			  if(bf>=2) {
				  if(getBf(x.getRight())==1) {
					  rotate(x.getRight());
				  }
				  else if(getBf(x.getRight())==-1) {
					  z=x.getRight().getLeft();
					  rotate(z);rotate(z);
				  }
				  else if(x.getRight().isRealNode()==false) {
					  rotate(x.getLeft());
					  AVLNode.updateHeight(x);
					  x=x.getParent();
					 // System.out.print("!2"+x.getValue()+" "+nodeCount);
				  }
				  
				  else if(getBf(x.getLeft())==-1) {
					  rotate(x.getLeft());
				  }
				  else if(getBf(x.getLeft())==1) {
					  z=x.getLeft().getRight();
					  rotate(z);rotate(z);
				  }
				  else if(x.getLeft().isRealNode()==false) {
					  rotate(x.getRight());
					  AVLNode.updateHeight(x);
					  x=x.getParent();
					  //System.out.print("!1"+x.getValue()+" "+nodeCount);
				  }
				  
			  }
		  }
		  AVLNode.updateHeight(x);
		  //int bf = AVLNode.updateHeight(x);
		  x=x.getParent();
	  }while(x!=null);
	  if(x==root) AVLNode.updateHeight(x); 
	  
  }
  
  /*public void updateHeightToRootDelete(IAVLNode replaceBy) {
	  IAVLNode x=replaceBy;
	  //AVLNode.updateHeight(x);
	  IAVLNode z;
	  do {
		  
		  int bf=getBf(x);
		  //System.out.print("bf = "+bf);
		  if(bf==1||bf==-1) AVLNode.updateHeight(x);
		  else {
			  
			  if(bf<=-2) {
				  if(getBf(x.getLeft())==-1) {
					  rotate(x.getLeft());
				  }
				  else if(getBf(x.getLeft())==1) {
					  z=x.getLeft().getRight();
					  rotate(z);rotate(z);
				  }
				  else if(x.getLeft().isRealNode()==false) {
					  rotate(x.getRight());
					  AVLNode.updateHeight(x);
					  x=x.getParent();
					  //System.out.print("!1"+x.getValue()+" "+nodeCount);
				  }
				  
				  else if(getBf(x.getRight())==1) {
					  rotate(x.getRight());
				  }
				  else if(getBf(x.getRight())==-1) {
					  z=x.getRight().getLeft();
					  rotate(z);rotate(z);
				  }
				  else if(x.getRight().isRealNode()==false) {
					  rotate(x.getLeft());
					  AVLNode.updateHeight(x);
					  x=x.getParent();
					 // System.out.print("!2"+x.getValue()+" "+nodeCount);
				  }
				  
			  }
			  
			  if(bf>=2) {
				  if(getBf(x.getRight())==1) {
					  rotate(x.getRight());
				  }
				  else if(getBf(x.getRight())==-1) {
					  z=x.getRight().getLeft();
					  rotate(z);rotate(z);
				  }
				  else if(x.getRight().isRealNode()==false) {
					  rotate(x.getLeft());
					  AVLNode.updateHeight(x);
					  x=x.getParent();
					 // System.out.print("!2"+x.getValue()+" "+nodeCount);
				  }
				  
				  else if(getBf(x.getLeft())==-1) {
					  rotate(x.getLeft());
				  }
				  else if(getBf(x.getLeft())==1) {
					  z=x.getLeft().getRight();
					  rotate(z);rotate(z);
				  }
				  else if(x.getLeft().isRealNode()==false) {
					  rotate(x.getRight());
					  AVLNode.updateHeight(x);
					  x=x.getParent();
					  //System.out.print("!1"+x.getValue()+" "+nodeCount);
				  }
				  
			  }
		  }
		  AVLNode.updateHeight(x);
		  //int bf = AVLNode.updateHeight(x);
		  x=x.getParent();
	  }while(x!=null);
	  if(x==root) AVLNode.updateHeight(x); 
	  
  }*/
  
  public void rotate(IAVLNode x) {
	  if(x==root) return;
	  rotationCount++;
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
	  x.setParent(grandFather);
	  if(grandFather!=null) {
		  if(grandFather.getKey()>x.getKey()) grandFather.setLeft(x);
		  else grandFather.setRight(x);
	  }
	  
	  if(p==root) root=(AVLTree.AVLNode) x;
  }
  public static int getBf(IAVLNode x) {
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
	  
	  
	 IAVLNode x=SearchNode(k);
	 if(x.getKey()==k) return x.getValue();
	 return null;
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
	  if(empty()) {
		  this.root=new AVLNode(k,i);
		  this.nodeCount++;
		  return 0;
	  }
	  IAVLNode x=SearchNode(k);
	  if(x.getKey()==k) return -1;
	  
	  nodeCount++;
	  
	  AVLNode NodeToAdd=new AVLNode(k,i,x);
	  if(x.getKey()>k)
		  x.setLeft(NodeToAdd);
	  
	  if(x.getKey()<k)
		  x.setRight(NodeToAdd);
	  
	  updateHeightToRoot(NodeToAdd);
	  return rotationCount;	// to be replaced by student code
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
	   if(empty()) {
			  return -1;
		  }
		  IAVLNode x=SearchNode(k);
		  if(x.getKey()==k) {
			  nodeCount--;
			  
			  IAVLNode p=x.getParent();
			  IAVLNode replaceBy=new AVLNode();
			  IAVLNode UpdateH;
			  if(!x.getLeft().isRealNode()&&!x.getRight().isRealNode())
				  replaceBy=new AVLNode();
			  if(x.getLeft().isRealNode()&&x.getRight().isRealNode()) {
				  replaceBy=Successor(x);
				  UpdateH=replaceBy;
				  if(replaceBy==null) {
					  replaceBy=new AVLNode();
					  UpdateH=replaceBy;
				  }
				  else {
					  
					  
					  
					  if(replaceBy.getParent().getLeft()==replaceBy) {
						  replaceBy.getParent().setLeft(replaceBy.getRight());
						  //replaceBy.getRight().setParent(replaceBy.getParent());
						  UpdateH = replaceBy.getRight();
						  
						  if(x==root) {
							  replaceBy.setParent(null);
							  root=(AVLTree.AVLNode) replaceBy;
						  }
						  else {
							  if(p.getLeft()==x) {
								  p.setLeft(replaceBy);
								  //replaceBy.setParent(p);
							  }
							  if(p.getRight()==x) {
								  p.setRight(replaceBy);
								  //replaceBy.setParent(p);
							  }
						  }
						  
						  
						  replaceBy.setLeft(x.getLeft());
						  //replaceBy.getLeft().setParent(replaceBy);
						  
						  replaceBy.setRight(x.getRight());
						  //replaceBy.getRight().setParent(replaceBy);
						  
						  
						  updateHeightToRoot(UpdateH);
						  return 421;
					  }
					  if(replaceBy.getParent().getRight()==replaceBy) {		
						  
						  if(x==root) {
							  replaceBy.setParent(null);
							  root=(AVLTree.AVLNode) replaceBy;
						  }
						  else {
							  if(p.getLeft()==x) {
								  p.setLeft(replaceBy);
								  //replaceBy.setParent(p);
							  }
							  if(p.getRight()==x) {
								  p.setRight(replaceBy);
								  //replaceBy.setParent(p);
							  }
						  }
						  replaceBy.setLeft(x.getLeft());
						  //replaceBy.getLeft().setParent(replaceBy);
						  
						  updateHeightToRoot(replaceBy);
						  return rotationCount;
						  
					  }
					  
				  }
			  }
			  if(x.getLeft().isRealNode() && !x.getRight().isRealNode())
				  replaceBy=x.getLeft();

			  if(!x.getLeft().isRealNode() && x.getRight().isRealNode())
				  replaceBy=x.getRight();
			  
			  if(x==root) {
				  replaceBy.setParent(null);
				  root=(AVLTree.AVLNode) replaceBy;
			  }
			  else {
				  if(p.getLeft()==x) {
					  p.setLeft(replaceBy);
					  //replaceBy.setParent(p);
				  }
				  if(p.getRight()==x) {
					  p.setRight(replaceBy);
					  //replaceBy.setParent(p);
				  }
			  }
			  
			  updateHeightToRoot(replaceBy);
			  return rotationCount;
		  }
		  
		  return -1;
		  
		  
		 // return 421;	// to be replaced by student code
   }

   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty.
    */
   public String min() {
	   if(empty()) return null;
	   
	   IAVLNode temp=root;
	   while(temp.getLeft().isRealNode())
			  temp=temp.getLeft();
	   return temp.getValue();
	   //return "minDefaultString"; // to be replaced by student code
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty.
    */
   public String max() {
	   if(empty()) return null;
	   
	   IAVLNode temp=root;
	   while(temp.getRight().isRealNode())
			  temp=temp.getRight();
	   return temp.getValue();
	   //return "maxDefaultString"; // to be replaced by student code
   }

  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
  public int[] keysToArray() {
	  	int [] arr = new int[nodeCount];
	  	int arr_indx=0;
	  	IAVLNode [] stack = new IAVLNode[root.getHeight()+1];//later size=  root.getHeight()
	  	int stackI = 0;
	  	
	  	if(empty())
	  		return arr;
	  	
	  	IAVLNode temp=root;
	  	do {
	  		
	  		while(temp.isRealNode()) {
	  			stack[stackI++]=temp;
	  			temp=temp.getLeft();
	  		}
	  		stackI--;
	  		temp=stack[stackI];
	  		arr[arr_indx++]=temp.getKey();
	  		temp=temp.getRight();
	  		
	  	}while(stackI>0||temp.isRealNode());
	  	
	  	
	  	
	  	return arr;
	  	
        //return new int[33]; // to be replaced by student code
  }

  /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
  public String[] infoToArray() {
	  String [] arr = new String[nodeCount];
	  	int arr_indx=0;
	  	IAVLNode [] stack = new IAVLNode[root.getHeight()+1];//later size=  root.getHeight()
	  	int stackI = 0;
	  	
	  	if(empty())
	  		return arr;
	  	
	  	IAVLNode temp=root;
	  	do {
	  		
	  		while(temp.isRealNode()) {
	  			stack[stackI++]=temp;
	  			temp=temp.getLeft();
	  		}
	  		stackI--;
	  		temp=stack[stackI];
	  		arr[arr_indx++]=temp.getValue();
	  		temp=temp.getRight();
	  		
	  	}while(stackI>0||temp.isRealNode());
	  	
	  	
	  	
	  	return arr;
  }

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    */
   public int size() {
	   return nodeCount;
	   //return 422; // to be replaced by student code
   }
   
   /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    */
   public IAVLNode getRoot() {
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
   public AVLTree[] split(int x) {
	   return null; 
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
   public int join(IAVLNode x, AVLTree t) {
	   return -1;
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
			return this.key; // to be replaced by student code
		}
		public String getValue() {
			return this.info; // to be replaced by student code
		}
		public void setLeft(IAVLNode node) {
			this.left = node;
			updateHeight(this);
			node.setParent(this);
			return; // to be replaced by student code
		}
		public IAVLNode getLeft() {
			return this.left; // to be replaced by student code
		}
		public void setRight(IAVLNode node) {
			this.right = node;
			updateHeight(this);
			node.setParent(this);
			return; // to be replaced by student code
		}
		public IAVLNode getRight() {
			return this.right; // to be replaced by student code
		}
		public void setParent(IAVLNode node) {
			this.parent = node;
			updateHeight(this);
			return; // to be replaced by student code
		}
		public IAVLNode getParent() {
			return this.parent; // to be replaced by student code
		}
		public boolean isRealNode() {
			return (this.key == -1) ? false : true; // to be replaced by student code
		}
	    public void setHeight(int height) {
	    	this.height = height;
	      return; // to be replaced by student code
	    }
	    public int getHeight() {
	      return this.height; // to be replaced by student code
	    }
	    
	    public static int updateHeight(IAVLNode x) {
	    	int lh,rh;
	    	
	    	if(x.getLeft()==null) lh=-10;
	    	else lh=x.getLeft().getHeight();
	    	
	    	if(x.getRight()==null) rh=-10;
	    	else rh=x.getRight().getHeight();
	    	
	    	if(lh>rh)
	    		x.setHeight(1+lh);
	    	else
	    		x.setHeight(1+rh);
	    	if(x.getHeight()<0&&x.getKey()>0) x.setHeight(0);
	    	if(x.getHeight()<0&&x.getKey()<0) x.setHeight(-1);
	    	return getBf(x);
	    }
  }

}
  
