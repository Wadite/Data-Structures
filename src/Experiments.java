
public class Experiments {
	public static void main(String [] args) {
		//Exp 1
		/*
		for(int i=1;i<6;i++) {
			
		int n=1000*(int) Math.pow(2, i);
		System.out.println("i: "+i+", n: "+n);
		int [] reverseOrder=new int [n];
		int [] randomOrder = new int [n];
		for(int j=0;j<n;j++) {
			reverseOrder[j]=n-j;
			randomOrder[j]=n-j;
		}
		randomOrder(randomOrder);
		
		System.out.println("countSwitches reverse order: "+countSwitches(reverseOrder));
		AVLTreeForExp tree=new AVLTreeForExp();
		for(int a:reverseOrder)
			tree.insert(a, "s"+a);
		System.out.println("AVL cost reverse order "+tree.searchCost);
		
		System.out.println("countSwitches random order: "+countSwitches(randomOrder));
		AVLTreeForExp tree2=new AVLTreeForExp();
		for(int a:randomOrder)
			tree2.insert(a, "s"+a);
		System.out.println("AVL cost random order "+tree2.searchCost);
		
		}
		*/
		
		//Exp 2
		
		for(int i=1;i<11;i++) {
			
			int n=1000*(int) Math.pow(2, i);
			System.out.println("i: "+i+", n: "+n);
			int [] randomOrder = new int [n];
			for(int j=0;j<n;j++) {
				randomOrder[j]=n-j;
			}
			randomOrder(randomOrder);
			
			AVLTreeForExp tree=new AVLTreeForExp();
			AVLTreeForExp.joinsCostInsplits=0;AVLTreeForExp.maxJoinCost=0;AVLTreeForExp.joinsCnt=0;
			for(int a:randomOrder)
				tree.insert(a, "s"+a);
			
			int randomKey = randomOrder[(int)(Math.random()*n)];
			tree.split(randomKey);
			
			
			System.out.println("average joins cost in random: "+tree.joinsCostInsplits/tree.joinsCnt);
			System.out.println("max join cost in random: "+tree.maxJoinCost);
			
			AVLTreeForExp tree2=new AVLTreeForExp();
			AVLTreeForExp.joinsCostInsplits=0;AVLTreeForExp.maxJoinCost=0;AVLTreeForExp.joinsCnt=0;
			for(int a:randomOrder)
				tree2.insert(a, "s"+a);
			tree2.split(tree2.maxLeftSideTree());
			
			System.out.println("average joins cost in left max: "+tree2.joinsCostInsplits/tree2.joinsCnt);
			System.out.println("max join cost in left max: "+tree2.maxJoinCost);
			
			
			}
		
	}
	
	public static int countSwitches(int [] arr) {
		int cnt=0;
		for(int j=0;j<arr.length;j++)
			for(int i=j+1;i<arr.length;i++)
				if(arr[i]<arr[j])
					cnt++;
		return cnt;
	}
	public static void randomOrder(int [] arr) {
		for(int j=0;j<arr.length;j++) {
			int r=j+(int)(Math.random()*(arr.length-j));
			int temp=arr[j];
			arr[j]=arr[r];
			arr[r]=temp;
		}
	}
	public static void printArr(int [] arr) {
		for(int j=0;j<arr.length;j++) {
			System.out.print(arr[j]+" ");
		}
		System.out.println();
	}
	
}
