import java.util.ArrayList;

public class Tester {

	public static void main(String [] args) {
		AVLTree tree=new AVLTree();
		/*
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
		
		
		
		tree.insert(1,"s1");tree.insert(2,"s2");tree.insert(3,"s3");tree.insert(4,"s4");
		treePrinter(tree);
		tree.insert(5,"s5");
		treePrinter(tree);
		tree.insert(6,"s6");tree.insert(7,"s7");tree.insert(8,"s8");
		//tree.insert(4,"s4");tree.insert(2,"s2");tree.insert(1,"s1");tree.insert(3,"s3");
		
		//tree.insert(5,"s5");
		//tree.insert(6,"s6");tree.insert(8,"s8");tree.insert(7,"s7");
		treePrinter(tree);
		printArr(tree.keysToArray());
		tree.delete(2);
		printArr(tree.keysToArray());
		printArr(tree.infoToArray());
		
		System.out.println(tree.min());
		System.out.println(tree.max());
		*/
		tree.insert(13,"s13");
		tree.insert(5,"s5");
		tree.insert(2,"s2");
		tree.insert(8,"s8");
		tree.insert(12,"s12");
		tree.insert(4,"s4");
		tree.insert(3,"s3");
		tree.insert(10,"s10");
		tree.insert(1,"s1");
		tree.insert(11,"s11");
		tree.insert(15,"s15");
		tree.insert(14,"s14");
		tree.insert(7,"s7");
		tree.insert(9,"s9");
		tree.insert(6,"s6");
		tree.delete(9);
		tree.delete(3);
		tree.delete(7);
		tree.delete(14);
		tree.delete(1);
		
		treePrinter(tree);
		System.out.println("Ended");
		
		
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
	
	//prints the tree level by level until the last virtual node
		// V - virtual node
		//N - null
		public static void treePrinter(AVLTree tree){
			ArrayList<AVLTree.IAVLNode> currList = new ArrayList<>();
			currList.add(tree.getRoot());
			int level = tree.getRoot().getHeight();

			while (currList.size() > 0) {

				String space = "  ";

				for (int i = 0; i < level; i++) {
					space = space + space;
				}
				level--;
				System.out.print(space);


				ArrayList<AVLTree.IAVLNode> childrenList = new ArrayList<>();

				for (AVLTree.IAVLNode node: currList) {
					if (node != null && node.isRealNode()) {
						System.out.print(node.getValue() + space);
						childrenList.add(node.getLeft());
						childrenList.add(node.getRight());
					}
					else if (node != null) {
						System.out.print("V");
						System.out.print(space);
						childrenList.add(null);
						childrenList.add(null);

					}
					else { //node == null
						System.out.print("N");
						System.out.print(space);

						childrenList.add(null);
						childrenList.add(null);
					}

				}
				boolean onlyNull = true;

				for (int i = 0; i < childrenList.size(); i++) {
					if (childrenList.get(i) != null) {
						onlyNull = false;
						break;
					}
				}
				if (onlyNull) {
					break;
				}
				currList = childrenList;
				childrenList = new ArrayList<>();

				System.out.println();
				System.out.println();
			}
			System.out.println();
		}
}
