import java.io.*;
import java.util.*;
public class LocNotManager {
	
	public static Map<Double, Map<Double, LocNot>> load(String fileName) {
		try {
		Map<Double, Map<Double, LocNot>> map1=new BST <Double, Map<Double, LocNot>>();
		Map<Double, LocNot> map2=new BST<Double, LocNot>();
		String text = null;
		double lat = 0.0;
		double lng = 0.0;
		int maxNbRepeats = 0;
		int nbRepeats = 0;
		try{
			
			Scanner input=new Scanner(new File(fileName));
	
			while(input.hasNext()) {
				String line =input.nextLine();
				String array[]=line.split("\t");
				try {
				text = array[4];
				 lat = Double.parseDouble(array[0]);
				 lng = Double.parseDouble(array[1]);
				 maxNbRepeats = Integer.parseInt(array[2]);
				 nbRepeats = Integer.parseInt(array[3]);
				}catch(ArrayIndexOutOfBoundsException e) {
				}
					catch(ClassCastException e) {
					}
				LocNot l=new LocNot(text,lat,lng,maxNbRepeats,nbRepeats);
				
				if(!map1.empty())
					if(!map1.find(lat))
						map2=new BST<Double, LocNot>();
				
				
				map2.insert(l.getLng(), l);
				
				if(!map1.insert(l.getLat(), map2)) {
					map1.find(l.getLat());
					map1.retrieve().insert(l.getLng(), l);
					map2=new BST<Double, LocNot>();
				}
					
				
			}
			input.close();
		}catch(FileNotFoundException e) {
		}
		catch(ArrayIndexOutOfBoundsException e) {
		}
		 catch(ClassCastException e) {
		 }
		 catch(Exception e) {
		 }
		
		
		return map1;
		
		}catch(Exception e) {}
		return new BST <Double, Map<Double, LocNot>>();
	}

	public static void save(String fileName, Map<Double, Map<Double, LocNot>> nots) {
		try {
			PrintWriter BF=new PrintWriter(new FileOutputStream(new File(fileName)));
			List<Pair<Double, Map<Double, LocNot>>> l=nots.getAll();
			if(!l.empty()) {
			l.findFirst();
			while(!l.last()) {
				if(l.retrieve().second==null)
					break;
				List<Pair<Double, LocNot>> l2=l.retrieve().second.getAll();
			if(!l2.empty()) {
				l2.findFirst();
			
				while(!l2.last()) {
					if(l2.retrieve().second==null)
						break;
					String s=l2.retrieve().second.toString();
					BF.write(s);
					BF.println();
					l2.findNext();
				}
			
				BF.write(l2.retrieve().second.toString());
				BF.println();
			}
				l.findNext();
			}
			List<Pair<Double, LocNot>> l2=l.retrieve().second.getAll();
			if(!l2.empty()) {
			l2.findFirst();
		
			while(!l2.last()) {
				if(l2.retrieve().second==null)
					break;
				String s=l2.retrieve().second.toString();
				BF.write(s);
				BF.println();
				l2.findNext();
			}
		
			BF.write(l2.retrieve().second.toString());
			}
			}
			BF.close();
		}catch(Exception e) {
		}
	}

	public static List<LocNot> getAllNots(Map<Double, Map<Double, LocNot>> nots) {
		try {
		if(nots==null)
			return new LinkedList<LocNot>();
		if(nots.empty())
			return new LinkedList<LocNot>();
		
		if(nots.retrieve()!=null) {
		List<Pair<Double, Map<Double, LocNot>>> l=nots.getAll();
		if(l.empty())
			return new LinkedList<LocNot>();
		if(l.retrieve()==null)
			return new LinkedList<LocNot>();
		
		if(l.retrieve().second!=null) {
		List<Pair<Double, LocNot>> l2=l.retrieve().second.getAll();
		List<LocNot> l3=new LinkedList<LocNot>();
		
		l.findFirst();
		while(!l.last()) {
			l2=l.retrieve().second.getAll();
			if(!l2.empty()) {
				l2.findFirst();
				while(!l2.last()) {
					l3.insert(l2.retrieve().second);
					l2.findNext();
				}
				l3.insert(l2.retrieve().second);
			}
			l.findNext();
		}
		l2=l.retrieve().second.getAll();
		if(!l2.empty()) {
			l2.findFirst();
			while(!l2.last()) {
				l3.insert(l2.retrieve().second);
				l2.findNext();
			}
			l3.insert(l2.retrieve().second);
		}
		return l3;
		}}
		return new LinkedList<LocNot>();
		
		}catch(Exception e) {
			
		}
		return new LinkedList<LocNot>();
	}

	public static boolean addNot(Map<Double, Map<Double, LocNot>> nots, LocNot not) {
		try {
		
		if(nots==null)
			return false;
		if(nots.full())
			return false;
		
		if(nots.insert(not.getLat(), new BST<Double, LocNot>())) {
			return nots.retrieve().insert(not.getLng(), not);}
		else
			if(nots.find(not.getLat()))
				return nots.retrieve().insert(not.getLng(), not);
		return false;
		
		}catch(Exception e) {}
		return false;
	}
    
	public static boolean delNot(Map<Double, Map<Double, LocNot>> nots, double lat, double lng) {
		try {
		
		if(nots==null)
			return false;
		if(nots.empty())
			return false;
		
		if(nots.retrieve()==null)
			return false;
		
		 
		if(nots.find(lat)) {
			if(nots.retrieve().empty())
				return false;
			if(nots.retrieve().retrieve()==null) 
				return false;
			if(nots.retrieve().remove(lng)) {
					if(nots.find(lat) && nots.retrieve().empty())
						return nots.remove(lat);
					return true;
			}
		}
		
		return false;
		
		}catch(Exception e) {}
		return false;
		
		
		
	}

	public static List<LocNot> getNotsAt(Map<Double, Map<Double, LocNot>> nots, double lat, double lng, double dst) {
		try {
		
		if(nots==null)
			return new LinkedList<LocNot>();;
		if(nots.empty())
			return new LinkedList<LocNot>();;
		
		
		LinkedList<LocNot> l3=new LinkedList<LocNot>();
		double angle=GPS.angle(dst/2);
		
		
		List<Pair<Double, Map<Double, LocNot>>> l1=nots.getRange(lat-angle,lat+angle);
		if(l1.empty())
			return new LinkedList<LocNot>();;
		if(l1.retrieve().second==null)
			return new LinkedList<LocNot>();;
		
		
		l1.findFirst();
		while(!l1.last()) {
		
		
		//هنا عشان كل خط عرض عندي اجيب النقاط المسموح فيها بخط الطول 
		List<Pair<Double, LocNot>> l2=l1.retrieve().second.getRange(lng-angle,lng+angle);
		
		
		if( !l2.empty()) {
			
		
		l2.findFirst();
		while(!l2.last()) {
			if(l2.retrieve()!=null)
				if(l2.retrieve().second!=null)
					
					l3.insert(l2.retrieve().second);
			
			l2.findNext();
		}
		if(l2.retrieve()!=null)
			if(l2.retrieve().second!=null)
				l3.insert(l2.retrieve().second);
	
		}
		 l1.findNext();
		
		}//last l1
		
		List<Pair<Double, LocNot>> l2=l1.retrieve().second.getRange(lng-angle,lng+angle);
		
		
		if( !l2.empty()) {
			
		l2.findFirst();
		while(!l2.last()) {
			if(l2.retrieve()!=null)
				if(l2.retrieve().second!=null)
					
					l3.insert(l2.retrieve().second);
			
			l2.findNext();
		}
		if(l2.retrieve()!=null)
			if(l2.retrieve().second!=null)
				l3.insert(l2.retrieve().second);
	
		}
		
		return l3;
		
		}catch(Exception e) {}
		return new LinkedList<LocNot>();
	}
     
	public static List<LocNot> getActiveNotsAt(Map<Double, Map<Double, LocNot>> nots, double lat, double lng, double dst) {
		try {
		
		if(nots==null)
			return new LinkedList<LocNot>();
		if(nots.empty())
			return new LinkedList<LocNot>();
		if(nots.retrieve()==null)
			return new LinkedList<LocNot>();
		
		
		List<LocNot> l=getNotsAt(nots,lat,lng,dst);
		if(l==null)
			return new LinkedList<LocNot>();
		if(l.empty())
			return new LinkedList<LocNot>();
		
		List<LocNot> lActive=new LinkedList<LocNot>();
		l.findFirst();
		while(!l.last()) {
			if(l.retrieve()!=null)
			if(l.retrieve().isActive())
				lActive.insert(l.retrieve());
			l.findNext();
		}
		if(l.retrieve()!=null)
		if(l.retrieve().isActive())
			lActive.insert(l.retrieve());
		
		return lActive;
		}catch(Exception e){}
		return new LinkedList<LocNot>();
	}
    
	public static void perform(Map<Double, Map<Double, LocNot>> nots, double lat, double lng, double dst) {
		try {
		if(nots==null)
			return;
		if(nots.empty())
			return ;
		
		List<LocNot> l=getActiveNotsAt(nots,lat,lng,dst);
		if(l==null)
			return;
		if(l.empty())
			return ;
		
		
		l.findFirst();
		while(!l.last()) {
			if(l.retrieve()!=null)
			l.retrieve().perform();
			l.findNext();
		}
		if(l.retrieve()!=null)
		l.retrieve().perform();
		
		}catch(Exception e) {}
		
	}

	public static Map<String, List<LocNot>> index(Map<Double, Map<Double, LocNot>> nots) {
		
		Map<String, List<LocNot>> map=new BST<String, List<LocNot>>();
		LinkedList<LocNot> list=new LinkedList<LocNot>();
		try {
		if(nots==null)
			return map;
		if(nots.empty())
			return map;
		if(nots.retrieve()==null)
			return map;
		
		List<Pair<Double, Map<Double, LocNot>>> l=nots.getAll();
		List<Pair<Double, Map<Double, LocNot>>> ll=nots.getAll();
		
		if(l==null)
			return map;
		if(l.empty())
			return map;
		
	l.findFirst();
	while(!l.last()) {
		
		
		List<Pair<Double, LocNot>> insidemap=l.retrieve().second.getAll();
		if(!insidemap.empty()) {
		  insidemap.findFirst();
		  while(!insidemap.last()) {
			String s=insidemap.retrieve().second.getText();
			String array[]=s.split(" ");
			for(int i=0;i<array.length;i++) {
				ll.findFirst();
				while(!ll.last()) {
					List<Pair<Double, LocNot>> travale=ll.retrieve().second.getAll();
					if(!travale.empty()) {
						//travale is list
						travale.findFirst();
						while(!travale.last()) {
							String stravale=travale.retrieve().second.getText();
							String arraytravale[]=stravale.split(" ");
							for(int j=0;j<arraytravale.length;j++) {
								if(arraytravale[j].equals(array[i])) {
									list.insert(travale.retrieve().second);
									break;
								}
							}
							travale.findNext();
						}
						String stravale=travale.retrieve().second.getText();
						String arraytravale[]=stravale.split(" ");
						for(int j=0;j<arraytravale.length;j++) {
							if(arraytravale[j].equals(array[i])) {
								list.insert(travale.retrieve().second);
								break;
							}
						}
					}
					ll.findNext();
				}
				List<Pair<Double, LocNot>> travale=ll.retrieve().second.getAll();
				if(!travale.empty()) {
					//travale is list
					travale.findFirst();
					while(!travale.last()) {
						String stravale=travale.retrieve().second.getText();
						String arraytravale[]=stravale.split(" ");
						for(int j=0;j<arraytravale.length;j++) {
							if(arraytravale[j].equals(array[i])) {
								list.insert(travale.retrieve().second);
								break;
							}
						}
						travale.findNext();
					}
					String stravale=travale.retrieve().second.getText();
					String arraytravale[]=stravale.split(" ");
					for(int j=0;j<arraytravale.length;j++) {
						if(arraytravale[j].equals(array[i])) {
							list.insert(travale.retrieve().second);
							break;
						}
					}
				}
				
				
				
				map.insert(array[i], list);
				list=new LinkedList<LocNot>();
			}
			insidemap.findNext();
		 }
		  String s=insidemap.retrieve().second.getText();
			String array[]=s.split(" ");
			for(int i=0;i<array.length;i++) {
				ll.findFirst();
				while(!ll.last()) {
					List<Pair<Double, LocNot>> travale=ll.retrieve().second.getAll();
					if(!travale.empty()) {
						//travale is list
						travale.findFirst();
						while(!travale.last()) {
							String stravale=travale.retrieve().second.getText();
							String arraytravale[]=stravale.split(" ");
							for(int j=0;j<arraytravale.length;j++) {
								if(arraytravale[j].equals(array[i])) {
									list.insert(travale.retrieve().second);
									break;
								}
							}
							travale.findNext();
						}
						String stravale=travale.retrieve().second.getText();
						String arraytravale[]=stravale.split(" ");
						for(int j=0;j<arraytravale.length;j++) {
							if(arraytravale[j].equals(array[i])) {
								list.insert(travale.retrieve().second);
								break;
							}
						}
					}
					ll.findNext();
				}
				List<Pair<Double, LocNot>> travale=ll.retrieve().second.getAll();
				if(!travale.empty()) {
					//travale is list
					travale.findFirst();
					while(!travale.last()) {
						String stravale=travale.retrieve().second.getText();
						String arraytravale[]=stravale.split(" ");
						for(int j=0;j<arraytravale.length;j++) {
							if(arraytravale[j].equals(array[i])) {
								list.insert(travale.retrieve().second);
								break;
							}
						}
						travale.findNext();
					}
					String stravale=travale.retrieve().second.getText();
					String arraytravale[]=stravale.split(" ");
					for(int j=0;j<arraytravale.length;j++) {
						if(arraytravale[j].equals(array[i])) {
							list.insert(travale.retrieve().second);
							break;
						}
					}
				}
				
				
				
				map.insert(array[i], list);
				list=new LinkedList<LocNot>();
			}
			
		}//end of check empty inside map
		l.findNext();
	}	
	
	//////////////////////////////////////////////////////////////////////////
	List<Pair<Double, LocNot>> insidemap=l.retrieve().second.getAll();
	if(!insidemap.empty()) {
	  insidemap.findFirst();
	  while(!insidemap.last()) {
		String s=insidemap.retrieve().second.getText();
		String array[]=s.split(" ");
		for(int i=0;i<array.length;i++) {
			ll.findFirst();
			while(!ll.last()) {
				List<Pair<Double, LocNot>> travale=ll.retrieve().second.getAll();
				if(!travale.empty()) {
					//travale is list
					travale.findFirst();
					while(!travale.last()) {
						String stravale=travale.retrieve().second.getText();
						String arraytravale[]=stravale.split(" ");
						for(int j=0;j<arraytravale.length;j++) {
							if(arraytravale[j].equals(array[i])) {
								list.insert(travale.retrieve().second);
								break;
							}
						}
						travale.findNext();
					}
					String stravale=travale.retrieve().second.getText();
					String arraytravale[]=stravale.split(" ");
					for(int j=0;j<arraytravale.length;j++) {
						if(arraytravale[j].equals(array[i])) {
							list.insert(travale.retrieve().second);
							break;
						}
					}
				}
				ll.findNext();
			}
			List<Pair<Double, LocNot>> travale=ll.retrieve().second.getAll();
			if(!travale.empty()) {
				//travale is list
				travale.findFirst();
				while(!travale.last()) {
					String stravale=travale.retrieve().second.getText();
					String arraytravale[]=stravale.split(" ");
					for(int j=0;j<arraytravale.length;j++) {
						if(arraytravale[j].equals(array[i])) {
							list.insert(travale.retrieve().second);
							break;
						}
					}
					travale.findNext();
				}
				String stravale=travale.retrieve().second.getText();
				String arraytravale[]=stravale.split(" ");
				for(int j=0;j<arraytravale.length;j++) {
					if(arraytravale[j].equals(array[i])) {
						list.insert(travale.retrieve().second);
						break;
					}
				}
			}
			
			
			
			map.insert(array[i], list);
			list=new LinkedList<LocNot>();
		}
		insidemap.findNext();
	 }
	  String s=insidemap.retrieve().second.getText();
		String array[]=s.split(" ");
		for(int i=0;i<array.length;i++) {
			ll.findFirst();
			while(!ll.last()) {
				List<Pair<Double, LocNot>> travale=ll.retrieve().second.getAll();
				if(!travale.empty()) {
					//travale is list
					travale.findFirst();
					while(!travale.last()) {
						String stravale=travale.retrieve().second.getText();
						String arraytravale[]=stravale.split(" ");
						for(int j=0;j<arraytravale.length;j++) {
							if(arraytravale[j].equals(array[i])) {
								list.insert(travale.retrieve().second);
								break;
							}
						}
						travale.findNext();
					}
					String stravale=travale.retrieve().second.getText();
					String arraytravale[]=stravale.split(" ");
					for(int j=0;j<arraytravale.length;j++) {
						if(arraytravale[j].equals(array[i])) {
							list.insert(travale.retrieve().second);
							break;
						}
					}
				}
				ll.findNext();
			}
			List<Pair<Double, LocNot>> travale=ll.retrieve().second.getAll();
			if(!travale.empty()) {
				//travale is list
				travale.findFirst();
				while(!travale.last()) {
					String stravale=travale.retrieve().second.getText();
					String arraytravale[]=stravale.split(" ");
					for(int j=0;j<arraytravale.length;j++) {
						if(arraytravale[j].equals(array[i])) {
							list.insert(travale.retrieve().second);
							break;
						}
					}
					travale.findNext();
				}
				String stravale=travale.retrieve().second.getText();
				String arraytravale[]=stravale.split(" ");
				for(int j=0;j<arraytravale.length;j++) {
					if(arraytravale[j].equals(array[i])) {
						list.insert(travale.retrieve().second);
						break;
					}
				}
			}
			
			
			
			map.insert(array[i], list);
			list=new LinkedList<LocNot>();
		}
		
	}//end of check empty inside map
		}catch(Exception e) {}
		return map;
	}

	public static void delNots(Map<Double, Map<Double, LocNot>> nots, String w) {
	 try {
		if(nots==null)
			return;	
		if(nots.empty())
			return;
	
		 
		
			List<Pair<Double, Map<Double, LocNot>>> l= nots.getAll();
			if(l==null)
				return;
			
			if(l.empty()) 
				return;
			
			l.findFirst();
		while(!l.last()) {
			List<Pair<Double, LocNot>> l2=l.retrieve().second.getAll();
			if(!l2.empty()) {
				l2.findFirst();
				while(!l2.last()) {
				String s=l2.retrieve().second.getText();
				 String array[]=s.split(" ");
				 for(int i=0;i<array.length;i++)
					 if(array[i].equals(w)) {
						 delNot(nots,l.retrieve().first,l2.retrieve().first);
						 break;
					 }
				 l2.findNext();
			   }//end while l2;
				String s=l2.retrieve().second.getText();
				 String array[]=s.split(" ");
				 for(int i=0;i<array.length;i++)
					 if(array[i].equals(w)) {
						 delNot(nots,l.retrieve().first,l2.retrieve().first);
						 break;
					 }
			}
			l.findNext(); 
		}//end of l
			List<Pair<Double, LocNot>> l2=l.retrieve().second.getAll();
			if(!l2.empty()) {
				l2.findFirst();
				while(!l2.last()) {
				String s=l2.retrieve().second.getText();
				 String array[]=s.split(" ");
				 for(int i=0;i<array.length;i++)
					 if(array[i].equals(w)) {
						 delNot(nots,l.retrieve().first,l2.retrieve().first);
						 break;
					 }
				 l2.findNext();
			   }//end while l2;
				String s=l2.retrieve().second.getText();
				 String array[]=s.split(" ");
				 for(int i=0;i<array.length;i++)
					 if(array[i].equals(w)) {
						 delNot(nots,l.retrieve().first,l2.retrieve().first);
						 break;
					 }
			}
			
			

	}catch(Exception e) {}
	}

	// Print a list of notifications in the same format used in file.
	public static void print(List<LocNot> l) {
		System.out.println("-------------------------------------------------------------------------------------");
		if (l==null)
			return;
		if (!l.empty()) {
			l.findFirst();
			while (!l.last()) {
				System.out.println(l.retrieve());
				l.findNext();
			}
			System.out.println(l.retrieve());
		} else {
			System.out.println("Empty");
		}
		System.out.println("------------------");
	}

	// Print an index.
	public static void print(Map<String, List<LocNot>> ind) {
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		List<Pair<String, List<LocNot>>> l = ind.getAll();
		if (!l.empty()) {
			l.findFirst();
			while (!l.last()) {
				System.out.println(l.retrieve().first);
				print(l.retrieve().second);
				l.findNext();
			}
			System.out.println(l.retrieve().first);
			print(l.retrieve().second);
		} else {
			System.out.println("Empty");
		}
		System.out.println("++++++++++++++++++");
	}

}