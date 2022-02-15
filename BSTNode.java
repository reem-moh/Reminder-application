public class BSTNode<K extends Comparable<K>, T>{
	T data;
	BSTNode<K,T> left;
	BSTNode<K,T> right;
	K key;
	
	BSTNode(T d,K k){
		data=d;
		key=k;
	}
	
	BSTNode(T d,K k, BSTNode<K,T> l,BSTNode<K,T> r){
		data=d;
		key=k;
		left=l;
		right=r;
	}
}
