package a13_design_scalability;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

interface HashFunction {
	public int hash(Object key);
}

interface Server {
	public String getName();

	public void setName(String name);
}

/**
 * A solution is to create a number of virtually replicated nodes at different positions on the hash ring for each server. For example, for server A with ip address IPA, we can map it to M positions
 * on the hash ring using Hash(IPA + "0"), Hash(IPA +"1"), … Hash(IPA +"M - 1").
 * 
 * @author lchen
 *
 * @param <T>
 */
public class ConsistentHash<T extends Server> {

	private final HashFunction hashFunction;
	private final int numberOfReplications;
	private final SortedMap<Integer, T> hashRing = new TreeMap<Integer, T>();

	public ConsistentHash(HashFunction hashFunc, int numberOfReplications, Collection<T> servers) {

		this.hashFunction = hashFunc;
		this.numberOfReplications = numberOfReplications;

		for (T server : servers) {
			add(server);
		}
	}

	public void add(T serverNode) {
		for (int i = 0; i < numberOfReplications; i++) {
			hashRing.put(hashFunction.hash(serverNode.getName() + i), serverNode);
		}
	}

	public void remove(T serverNode) {
		for (int i = 0; i < numberOfReplications; i++) {
			hashRing.remove(hashFunction.hash(serverNode.getName() + i));
		}
	}

	public T get(Object key) {
		if (hashRing.isEmpty()) {
			return null;
		}
		int hash = hashFunction.hash(key);
		if (!hashRing.containsKey(hash)) {
			SortedMap<Integer, T> tailMap = hashRing.tailMap(hash);
			hash = tailMap.isEmpty() ? hashRing.firstKey() : tailMap.firstKey();
		}
		return hashRing.get(hash);
	}

}
