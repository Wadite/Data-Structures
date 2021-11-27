
public class Tester {

	public static void main(String [] args) {
		AVLTree tree=new AVLTree();
		System.out.println(tree.empty());
		tree.insert(4,"hi");
		System.out.println(tree.empty());
		tree.insert(2,"bye");
		tree.insert(5,"hello");
		System.out.println(tree.size());
		printArr(tree.keysToArray());
		System.out.println(tree.getRoot().getRight().getKey());
		System.out.println(tree.Successor(tree.getRoot()).getKey());
		tree.delete(8);
		printArr(tree.keysToArray());
		tree.delete(4);
		printArr(tree.keysToArray());
		tree.delete(2);
		System.out.println(tree.getRoot().getKey());
		System.out.println(tree.getRoot().getLeft().getKey());
		System.out.println(tree.getRoot().getLeft().getParent().getKey());
		printArr(tree.keysToArray());
		tree.delete(5);
		System.out.println(tree.empty());
		
		
		
		
		tree.insert(4,"s4");tree.insert(2,"s2");tree.insert(1,"s1");tree.insert(3,"s3");tree.insert(5,"s5");
		tree.insert(6,"s6");tree.insert(8,"s8");tree.insert(7,"s7");
		printArr(tree.keysToArray());
		tree.delete(2);
		printArr(tree.keysToArray());
		printArr(tree.infoToArray());
		
		System.out.println(tree.min());
		System.out.println(tree.max());
	}
	public static void printArr(int [] arr) {
		for(int i=0;i<arr.length;i++)
			System.out.print(arr[i]+", ");
		System.out.println();
	}
	public static void printArr(AVLTree.IAVLNode[] arr) {
		for(int i=0;i<arr.length;i++)
			if(arr[i]!=null) System.out.print(arr[i].getKey()+", ");
		System.out.println();
		
	}
	public static void printArr(String [] arr) {
		for(int i=0;i<arr.length;i++)
			if(arr[i]!=null) System.out.print(arr[i]+", ");
		System.out.println();
		
	}
}
