package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public abstract class ComplexUtil {
	public static <T,R> Map<T,R> map(){
		return new HashMap<T, R>();
	}

	public static <T> List<T> list() {
		return new ArrayList<T>();
	}
	
	public static <T> Set<T> set() {
		return new HashSet<T>();
	}
	
	
	public static <T> Set<T> listToSet(List<T> list){
		if(list == null)
			return null;
		Set<T> set = set();
		for(T t : list){
			set.add(t);
		}
		return set;
	}
	
	public static <T,ID> List<T>  delMultiple(List<T> list){
		GetId<T,T> getId = new GetId<T, T>() {
			public T getId(T t) {
				return t;
			}
		};
		return delMultiple(list,getId);
	}
	
	/**
	 * 鎷嗗垎list锛岃В鍐充竴涓獂ml鍙兘浼犲浐瀹氭暟鐩殑闂
	 * @param l  鍘烲ist
	 * @param split  闇�鎷嗗垎鐨勬暟鍊�
	 * @return  
	 */
	public static <T> List<List<T>> getSplitList(List<T> l,int split) 
	{
		List<List<T>> list = ComplexUtil.list();
		List<T> temp = ComplexUtil.list();
		for(int i = 0 ;i < l.size(); i++){
			if(temp.size() == split){
				list.add(temp);
				temp = ComplexUtil.list();
			}
			temp.add(l.get(i));
		}
		list.add(temp);
		
		return list;
	}
	
	/**
	 * 鍘绘帀閲嶅鐨勬暟鎹�
	 * @param list
	 * @param getId
	 * @return
	 */
	public static <T,ID> List<T>  delMultiple(List<T> list,GetId<T,ID> getId){
		List<T> result =ComplexUtil.list();
		Set<ID> set = ComplexUtil.set();
		for(int i = 0 ;i < list.size(); i++){
			T t = list.get(i);
			ID id = getId.getId(t);
			if(!set.contains(id)){
				set.add(id);
				result.add(t);
			}
		}
		return result;
	}
	
	public static <T,ID> Map<ID,T>  listToMap(List<T> list,GetId<T,ID> getId){
		Map<ID,T> result = map();
		for(int i = 0 ;i < list.size(); i++){
			T t = list.get(i);
			ID id = getId.getId(t);
			if(!result.containsKey(id)){
				result.put(id, t);
			}
		}
		return result;
	}
	
	public static <K,V> void eachMap(Map<K,V> map,Each<K,V> each){
		if(EmptyUtil.isEmptyMap(map)){
			return;
		}
		Set<Map.Entry<K,V>> entrySet = map.entrySet();
		Iterator<Map.Entry<K,V>> it = entrySet.iterator();
		while(it.hasNext()){
			Map.Entry<K,V> entry = it.next();
			each.each(entry.getKey(), entry.getValue());
		}
	}
	
	public static void main(String [] arguments){
		//haha
		/*List<String> list = new ArrayList<String>();
		
		for(int i = 0; i< 5000000; i++){
			list.add(String.valueOf(new Random().nextInt(3000)));
		}
		long l1 = System.currentTimeMillis();
		System.out.println("start..");
		
		list = delMultiple(list);
		
		long l2 = System.currentTimeMillis();
		System.out.println(list.size());
		System.out.println(l2 - l1);*/
		Map<String,Object> map = map();
		map.put("1", "a1");
		map.put("2", "b1");
		map.put("3", "c1");
		map.put("4", "d1");
		map.put("5", "e1");
		eachMap(map,new Each<String, Object>() {
			public void each(String k, Object v) {
				System.out.println(k + "=" + v);
			}
		});
	}
	
	

	
	public interface Each<K,V>{
		void each(K k, V v);
	}
	
	public interface GetId<T,ID>{
		public ID getId(T t);
	}
}
