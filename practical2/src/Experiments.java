

public class Experiments {
	public static void main(String [] args) {
		/*
		for(int i=4;i<15;i+=2) {
			FibonacciHeap.totalLinks=0;
			FibonacciHeap.totalCuts=0;
			int m=(int) Math.round(Math.pow(3, i))-1;
			System.out.println("m= 3^"+i+"-1 = "+m);
			
			
			FibonacciHeap fibonacciHeap=new FibonacciHeap();
			
			
			long startTime = System.nanoTime();
			
			
			for(int k=0;k<=m;k++) {
				fibonacciHeap.insert(k);
			}
			
			for(int j=0;j<3*m/4;j++) {
				fibonacciHeap.deleteMin();
			}
			
			long stopTime = System.nanoTime();
			
			System.out.println("micro (1/1000 mili) seconds: "+(stopTime - startTime)/1000);
			System.out.println("total links: "+FibonacciHeap.totalLinks);
			System.out.println("total cuts: "+FibonacciHeap.totalCuts);
			System.out.println("potential: "+fibonacciHeap.potential());
			
			
		}*/
		
		for(int logm=10;logm<26;logm+=5) {
			FibonacciHeap.totalLinks=0;
			FibonacciHeap.totalCuts=0;
			System.out.println("m= 2^"+logm);
			int m=(int) Math.round(Math.pow(2, logm));
			
			FibonacciHeap fibonacciHeap=new FibonacciHeap();
			FibonacciHeap.HeapNode [] arr=new FibonacciHeap.HeapNode[m+1];
			
			
			long startTime = System.nanoTime();
			
			
			for(int k=m-1;k>=-1;k--) {
				arr[k+1]=fibonacciHeap.insert(k);
			}
			fibonacciHeap.deleteMin();
			
			for(int i=logm;i>0;i--) {
				fibonacciHeap.decreaseKey(arr[m-(int)Math.pow(2, i)+1  + 1], i+1);
			}
			
			long stopTime = System.nanoTime();
			
			int current_cuts = FibonacciHeap.totalCuts;
			System.out.println("micro (1/1000 mili) seconds: "+(stopTime - startTime)/1000);
			System.out.println("total links: "+FibonacciHeap.totalLinks);
			System.out.println("total cuts: "+FibonacciHeap.totalCuts);
			System.out.println("potential: "+fibonacciHeap.potential());
			
			fibonacciHeap.decreaseKey(arr[m-2  + 1], m+1);
			System.out.println("number of cuts because line decreaseKey(m-2,m+1) "+(FibonacciHeap.totalCuts-current_cuts));
			System.out.println("afer line decreaseKey(m-2,m+1)");
			System.out.println("total links: "+FibonacciHeap.totalLinks);
			System.out.println("total cuts: "+FibonacciHeap.totalCuts);
			System.out.println("potential: "+fibonacciHeap.potential());
			
		}
		
	}
	

}
