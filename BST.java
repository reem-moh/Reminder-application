public class BST <K extends Comparable<K>, T> implements Map<K,T>{
	
	BSTNode<K,T> root;
	BSTNode<K,T> current;
	
	BST(){
		current=root=null;
	}
	
	
	//true;
	public boolean empty() {
		return root==null;
	}
	
	//true
	public boolean full() {
		return false;
	}
	
	//true
	public void clear() {
		root=null;
	}
	
	
	//true
	public T retrieve() {
		return current.data;
	}
	
	//true
	public void update(T e) {
		current.data=e;
		
	}
	
	
	//true
	public boolean find(K key) {
		BSTNode<K,T> p = root,q = current;
		
		if(empty())
			return false;
		
		while(p != null) {
			if(p.key.equals(key)) {
				current = p;
				return true;
			}
			else if(key.compareTo(p.key) <0)
				p = p.left;
			else
				p = p.right;
		}
		
		current = q;
		return false;

		
	}
	
	//true 
	public int nbKeyComp(K key) {
		BSTNode<K,T> p = root;
		int count=0;
		if(empty())
			return 0;
		
		while(p != null) {
			if(p.key.equals(key)) {
				return ++count;
			}
			
			if(key.compareTo(p.key) <0)
				p = p.left;
			else
				p = p.right;
			count++;
		}
		return count;
	}
	
	
	//true
	public boolean insert(K key, T data) {
		BSTNode<K,T> p=root, q = root;
		
		if(find(key)) {
			return false; // key already in the BST
		}
		
		
		if (empty()) {
			root = current = new BSTNode<K,T>(data, key);
			return true;
		}
		else {
			BSTNode<K, T> newn = new BSTNode<K,T>(data, key);

			while(p != null) {
				q=p;
			   if(key.compareTo(p.key) <0)
					p = p.left;
				else
					p = p.right;
			}
			current=q;
			
			if (key.compareTo(current.key)<0)
				current.left = newn;
			else
				current.right = newn;
			current = newn;
			return true;
		}

	}
	
	
	public List<Pair<K, T>> getAll(){
		List<Pair<K, T>> list=new LinkedList<Pair<K, T>>();
		
		BSTNode<K,T> p=root;
		
		rec_inc(list,p);
		
		return list;
	}
	
	private void rec_inc(List<Pair<K, T>> list, BSTNode<K, T> p) {
		if(p==null)
			return;
		
	    rec_inc(list,p.left);
		list.insert(new Pair<K,T>(p.key,p.data));
		rec_inc(list,p.right);
		
		
	}

	public List<Pair<K, T>> getRange(K k1, K k2){
		List<Pair<K, T>> list=new LinkedList<Pair<K, T>>();
		
		BSTNode<K,T> p=root;
		
		rec_inc(list,p,k1,k2);
		
		
		return list;
	}
	
	private void rec_inc(List<Pair<K, T>> list, BSTNode<K, T> p, K k1, K k2) {
		if(p==null)
			return;
		if(p.key.compareTo(k1)>=0 && p.key.compareTo(k2)<=0) {
			rec_inc(list,p.left,k1,k2);
			list.insert(new Pair<K,T>(p.key,p.data));
			rec_inc(list,p.right,k1,k2);	
		}else {
		rec_inc(list,p.left,k1,k2);
		rec_inc(list,p.right,k1,k2);
		}
		
	}

	//false
	public int nbKeyComp(K k1, K k2){
		BSTNode<K,T> p = root;
		if(empty())
			return 0;
		if(k1.compareTo(k2)>0)
			return 1;
		
		return rec10(k1,k2,p);
	}
	
	private int rec10(K k1,K k2,BSTNode<K,T> p) {
		if(p==null)
			return 0;
		
		if(p.key.compareTo(k2)==0)
			return 1+rec10(k1,k2,p.left);
		if(p.key.compareTo(k1)==0)
			return 1+rec10(k1,k2,p.right);
		if(p.key.compareTo(k2)>0)
			return rec10(k1,k2,p.left)+1;
		if(p.key.compareTo(k1)<0)
			return rec10(k1,k2,p.right)+1;
		
		return rec10(k1,k2,p.right)+rec10(k1,k2,p.left)+1;
		
	}
	
	/*
	private int rec1(K k1,K k2,BSTNode<K,T> p) {
		if(p==null)
			return 0;
		if(p.key.compareTo(k2)==0)
			return 1;
		if(k1.compareTo(p.key)==0)
			return 1;
		
		if(k1.compareTo(p.key)>0)
			return 1+rec1(k1,k2,p.right);
		if(k1.compareTo(p.key)<0)
			return 1+rec1(k1,k2,p.left);
		return 0;	
	}
	
	private int rec2(K k1,K k2,BSTNode<K,T> p) {
		int count=0;
		if(p==null)
			return 0;
		if(k2.compareTo(p.key) >=0 && k1.compareTo(p.key) <=0)
				count++;
		
		return rec2(k1,k2,p.left)+rec2(k1,k2,p.right)+count;
		
	}
	
	private int rec(K k1,K k2,BSTNode<K,T> p) {
		
		if(p==null)
			return 0;
		int x=rec(k1,k2,p.left);
		int y=rec(k1,k2,p.right);
		if(p.key.compareTo(k2)==0)
			return 1+x;
		if(k1.compareTo(p.key)==0)
			return 1+y;
		//if(k2.compareTo(p.key) >=0 && k1.compareTo(p.key) <=0)
		//	count++;
		if(x+y==0)
			return 0;
		return x+y+1;
	}
	*/
	//true
	public boolean remove(K key){
		K k1=key;
		BSTNode<K,T> p=root;
		BSTNode<K,T> q=null;
		while(p!=null) {
			if(k1.compareTo(p.key)<0) {
				q=p;
				p=p.left;
			}else if(k1.compareTo(p.key)>0) {
				q=p;
				p=p.right;
			}else {
				if(p.left!=null && p.right!=null) {
					BSTNode<K,T> min=p.right;
					q=p;
					while(min.left!=null) {
						q=min;
						min=min.left;
					}
					p.key=min.key;
					p.data=min.data;
					k1=min.key;
					p=min;
				}
				if(p.left!=null) {
					p=p.left;
				}else {
					p=p.right;
				}
				
				if(q==null) {
					root=p;
				}else {
					if(k1.compareTo(q.key)<0) {
						q.left=p;
					}else
						q.right=p;
				}
				current=root;
				return true;
				
			}
				
		}
		return false;
	}
	
}