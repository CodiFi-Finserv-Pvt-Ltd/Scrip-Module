
public class test {
public static merge(int arr[],int l,int mid,int r) {
	int n1=mid-l+1;
	int n2=r-mid;
	int[] L=new int[n1];
	int[] R=new int[n2];
	for(int i=0;i<l.length;i++) {
		L[i]=arr[l+i];
	}
	
	for(int j=0;j<r.length;j++) {
		R[j]=arr[mid+1+j];
	}
}
}
