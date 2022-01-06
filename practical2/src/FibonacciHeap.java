


/**
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap
{
	HeapNode first=null;
	HeapNode min=null;
	
	HeapNode last=null;
	
	int nodeCount=0;
	
	int markedNodes = 0; 
	
	int treesNum=0;
	
	static int totalLinks=0;
	static int totalCuts=0;
	
	//**********************************************************************
	//Helpful functions
	
	/**
	    * public void consolidating()
	    *
	    * At the end of the process,
	    *  we obtain a non-lazy binomial heap containing at most log (n+1) trees,
	    *  at most one of each rank
	    *   
	    */
	public void consolidating() {
		if(isEmpty()) return;
		HeapNode [] arr=new HeapNode[(int)(Math.log(nodeCount)/Math.log(2))+2];
		
		HeapNode pre=first;
		HeapNode pos=pre.getNext();
		
		
		while(pre.getNext()!=null) {
			consolidating(arr,pre);
			pre=pos;
			pos=pre.getNext();
			
			
		}

		consolidating(arr,pre);



		
		first=null;
		min=null;
		last=null;
		treesNum=0;
		for(int i=arr.length-1;i>=0;i--) {
			
			if(arr[i]!=null) {
				
				
				arr[i].setNext(first);
				arr[i].setParent(null);
				first=arr[i];
				treesNum++;
				
				if(last==null) last=arr[i];
			}
		}
		
		this.min=findMin();
		this.first.setPrev(null);
		
	}
	
	/**
	    * public void consolidating(int [] arr,HeapNode temp)
	    *
	    * Adds a specific HeapNode into the array of consolidating
	    *   
	    */
	public void consolidating(HeapNode [] arr,HeapNode node) {
		int ord;
		for(ord=node.getOrder();arr[ord]!=null;ord++) { //as long as there already a tree in the current spot we will link them
			
			HeapNode other=arr[ord];
			
			if(other.getKey()<node.getKey()) {
				HeapNode temp=node;
				node=other;
				other=temp;
			}
			
			node.addChild(other);
			
			arr[ord]=null;
			
			totalLinks++;
			
			
		}
		
		arr[ord] = node;
		
	}
	
	
	public void cut(HeapNode x) { //cutting all the needed pointers from the current node
		totalCuts++;
		treesNum++;
		
		HeapNode parent=x.getParent(); //parent!=null
		x.setParent(null);
		
		if(x.getMarked()) this.markedNodes--;
		x.setMarked(false);
		
		
		parent.setOrder(parent.getOrder()-1);
		if(parent.getChild()==x) {
			parent.setChild(x.getNext());
			
		}
		 if(x.getPrev()!=null) {
			x.getPrev().setNext(x.getNext());
			
		}
		 if(x.getNext()!=null){
			x.getNext().setPrev(x.getPrev());
		}
		
		if(x!=first) {
			x.setPrev(null);
			x.setNext(null);
			x.setNext(first);
			first=x;
		}
		
		
		
		
		
	}
	
	
	public void cascading_cut(HeapNode x) {
		HeapNode parent=x.getParent();
		cut(x);
		if(parent.getParent()!=null) { //if parent is not a root
			if(!parent.getMarked()) {
				parent.setMarked(true);
				this.markedNodes++;
			}
			else {
				cascading_cut(parent);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	//**********************************************************************
   /**
    * public boolean isEmpty()
    *
    * Returns true if and only if the heap is empty.
    *   
    */
    public boolean isEmpty()
    {
    	return nodeCount==0;
    }
		
   /**
    * public HeapNode insert(int key)
    *
    * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
    * The added key is assumed not to already belong to the heap.  
    * 
    * Returns the newly created node.
    */
    public HeapNode insert(int key)
    {    
    	treesNum++;
    	HeapNode newNode = new HeapNode(key);
    	if(isEmpty()||key<min.getKey()) {
    		min=newNode;
    		last=newNode;
    	}
    	
    	nodeCount++;
    	
    	newNode.setNext(first);
    	first=newNode;
    	
    	
    	return newNode;
    }

   /**
    * public void deleteMin()
    *
    * Deletes the node containing the minimum key.
    *
    */
    public void deleteMin()
    {
    	if(this.isEmpty()) return;
    	if(this.min.getChild()==null) {
    		if(this.min.prev!=null)
    			this.min.prev.setNext(this.min.getNext());
    		else if(this.min==this.first) {
    			this.first=this.min.getNext();
    		}
    	}
    	else {
    		HeapNode child = this.min.getChild();
    		
    		if(this.min.prev!=null) {
    			this.min.prev.setNext(child);
    		}
    		else if(this.min==this.first) {
    			this.first=child;
    		}
    		
    		HeapNode temp=child;
    		while(temp.getNext()!=null) {
    			temp.setParent(null);
    			temp=temp.getNext();
    		}
    		temp.setNext(min.getNext());
    		
    		
    	}
    	this.min=null;
    	
    	this.nodeCount--;
    	consolidating();
    	
     	return;
     	
    }

   /**
    * public HeapNode findMin()
    *
    * Returns the node of the heap whose key is minimal, or null if the heap is empty.
    *
    */
    public HeapNode findMin()
    {
    	if(isEmpty()) return null;
    	
    	
    	if(this.min==null) { //happens only when updating min
    		this.min=this.first;
    		HeapNode temp=first;
    		while(temp!=null) {
    			if(temp.getKey()<min.getKey()) {
    				this.min=temp;
    			}
    		
    		temp=temp.getNext();
    		}
    	}
    	
    	return this.min;
    } 
    
   /**
    * public void meld (FibonacciHeap heap2)
    *
    * Melds heap2 with the current heap.
    *
    */
    public void meld (FibonacciHeap heap2)
    {
    	if(heap2.isEmpty()) return;
    	
    	if(isEmpty()) {
    		this.first=heap2.first;
    		this.min=heap2.min;
    		this.nodeCount=heap2.nodeCount;
    		return;
    	}
    	
    	this.nodeCount+=heap2.nodeCount;
    	this.treesNum+=heap2.treesNum;
    	
    	if(heap2.min.key<this.min.key) this.min=heap2.min;
    	
    	
    	last.setNext(heap2.first);
    	
    	this.last=heap2.last;
    }

   /**
    * public int size()
    *
    * Returns the number of elements in the heap.
    *   
    */
    public int size()
    {
    	return this.nodeCount;
    }
    	
    /**
    * public int[] countersRep()
    *
    * Return an array of counters. The i-th entry contains the number of trees of order i in the heap.
    * Note: The size of of the array depends on the maximum order of a tree, and an empty heap returns an empty array.
    * 
    */
    public int[] countersRep()
    {
    	if(isEmpty()) return new int[0];
    	int maxOrder = 0;
    	HeapNode temp=first;
    	
    	while(temp!=null) {
    		
    		if(maxOrder<temp.getOrder()) {
    			maxOrder=temp.getOrder();
    		}
    		
    		temp=temp.next;
    	}
    	
    	int[] arr = new int[maxOrder+1]; //counter array, all zeroes!!
    	
    	temp=first;
    	
    	while(temp!=null) {
    		arr[temp.getOrder()] ++;
    		temp=temp.getNext();
    	}
    	
        return arr; //	 to be replaced by student code
    }
	
   /**
    * public void delete(HeapNode x)
    *
    * Deletes the node x from the heap.
	* It is assumed that x indeed belongs to the heap.
    *
    */
    public void delete(HeapNode x) 
    {    
    	decreaseKey(x,x.getKey()-min.getKey()+1); //make x min
    	deleteMin(); //delete it
    	
    }

   /**
    * public void decreaseKey(HeapNode x, int delta)
    *
    * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated
    * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
    */
    public void decreaseKey(HeapNode x, int delta)
    {    
    	x.setKey(x.getKey()-delta);
    	
    	if(x.getParent()!=null) {
    		if(x.getKey()<x.getParent().getKey()) {
    			cascading_cut(x);
	    	
    		}
    		
    	}
    	
    	if(x.getKey()<this.min.getKey()) {
    		this.min=x;
    	}
    		
    }

   /**
    * public int potential() 
    *
    * This function returns the current potential of the heap, which is:
    * Potential = #trees + 2*#marked
    * 
    * In words: The potential equals to the number of trees in the heap
    * plus twice the number of marked nodes in the heap. 
    */
    public int potential() 
    {    
    	return treesNum+2*markedNodes;
    }

   /**
    * public static int totalLinks() 
    *
    * This static function returns the total number of link operations made during the
    * run-time of the program. A link operation is the operation which gets as input two
    * trees of the same rank, and generates a tree of rank bigger by one, by hanging the
    * tree which has larger value in its root under the other tree.
    */
    public static int totalLinks()
    {    
    	return totalLinks;
    }

   /**
    * public static int totalCuts() 
    *
    * This static function returns the total number of cut operations made during the
    * run-time of the program. A cut operation is the operation which disconnects a subtree
    * from its parent (during decreaseKey/delete methods). 
    */
    public static int totalCuts()
    {    
    	return totalCuts;
    }

     /**
    * public static int[] kMin(FibonacciHeap H, int k) 
    *
    * This static function returns the k smallest elements in a Fibonacci heap that contains a single tree.
    * The function should run in O(k*deg(H)). (deg(H) is the degree of the only tree in H.)
    *  
    * ###CRITICAL### : you are NOT allowed to change H. 
    */
    public static int[] kMin(FibonacciHeap H, int k)
    {    
    	if(k==0) return new int[0];
    	
    	FibonacciHeap temp=new FibonacciHeap();
    	
    	HeapNode[] arr = new HeapNode[2*H.first.getOrder()*k];
    	int indx = 0;
    	
    	temp.insert(H.findMin().getKey()).info=indx;
    	arr[indx++]=H.findMin();
    	
    	int [] ans = new int[k];
    	
    	for(int i=0;i<Math.min(k, H.nodeCount);i++) {
    		
    		int min_indx = 0;
    		
    		min_indx = temp.findMin().info;

    		
    		temp.deleteMin();
    		for(HeapNode child=arr[min_indx].getChild();child!=null;child=child.getNext()) {
    			temp.insert(child.getKey()).info=indx;
        		arr[indx++] = child;
        	}
    		
    		ans[i] = arr[min_indx].getKey();
    		
    	}
    	
    	
    	
    	return ans;
    }
    
   /**
    * public class HeapNode
    * 
    * If you wish to implement classes other than FibonacciHeap
    * (for example HeapNode), do it in this file, not in another file. 
    *  
    */
    public static class HeapNode{

    	public int key;
    	private HeapNode child;
    	private HeapNode parent;
    	private HeapNode next;
    	private HeapNode prev;
    	private boolean marked=false;
    	private int order;
    	private int info;

    	public HeapNode(int key) {
    		this.key = key;
    	}

    	public int getKey() {
    		return this.key;
    	}
    	
    	public void setKey(int k) {
    		this.key=k;
    	}
    	
    	public int getOrder() {
    		return this.order;
    	}
    	
    	public void setOrder(int ord) {
    		this.order=ord;
    	}
    	
    	public HeapNode getChild() {
    		return this.child;
    	}
    	
    	public void setChild(HeapNode c) {
    		this.child=c;
    		if(c!=null) {
    			c.parent=this;
    			c.setPrev(null);
    			//this.order=Math.max(order, c.getOrder()+1);
    		}
    	}
    	
    	public void addChild(HeapNode c) {
    		HeapNode current = this.child;
    		this.child=c;
    		if(c!=null) {
    			c.parent=this;
    			c.setPrev(null);
    			c.setNext(current);
    			this.order++;
    		}
    	}
    	
    	public HeapNode getParent() {
    		return this.parent;
    	}
    	
    	public void setParent(HeapNode p) {
    		this.parent=p;
    	}
    	
    	public HeapNode getNext() {
    		return this.next;
    	}
    	
    	public void setNext(HeapNode n) {
    		this.next=n;
    		if(n!=null) n.prev=this;
    	}
    	
    	public HeapNode getPrev() {
    		return this.prev;
    	}
    	
    	public void setPrev(HeapNode pre) {
    		this.prev=pre;
    	}
    	
    	public boolean getMarked() {
    		return this.marked;
    	}
    	
    	public void setMarked(boolean mark) {
    		this.marked=mark;
    	}
    }
}
