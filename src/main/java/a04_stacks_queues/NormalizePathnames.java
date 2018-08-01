package a04_stacks_queues;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A file or directory can be specified via a string called the pathname. This string may specify an
 * absolute path or relative path to the current working directory.
 * 
 * Write a program which takes a pathname, and returns the shortest equivalent pathname.
 * 
 * @author lchen
 *
 */
public class NormalizePathnames {

	public static String normalizePathname(String path) {
		if (path == null || path.trim().equals(""))
			throw new IllegalArgumentException("Empty string is not a legal path.");

		Deque<String> pathNames = new LinkedList<>();
		if (path.startsWith("/")) // special case: starts with "/"
			pathNames.push("/");

		for (String token : path.split("/")) {
			if (token.equals("..")) {
				if (pathNames.isEmpty() || pathNames.peek().equals(".."))
					pathNames.push(token);
				else {
					if (pathNames.peek().equals("/"))
						throw new IllegalArgumentException("Path error, trying to go up root " + path);
					pathNames.pop();
				}
			} else if (!token.equals(".") && !token.isEmpty()) // must be a name
				pathNames.push(token);
		}

		StringBuilder result = new StringBuilder();
		if (!pathNames.isEmpty()) {
			Iterator<String> it = pathNames.descendingIterator();
			String prev = it.next();
			result.append(prev);
			while (it.hasNext()) {
				if (!prev.equals("/")) {
					result.append("/");
				}
				prev = it.next();
				result.append(prev);
			}
		}
		return result.toString();
	}

	public static void main(String[] args) {
		assert (normalizePathname("123/456").equals("123/456"));
		assert (normalizePathname("/123/456").equals("/123/456"));
		assert (normalizePathname("usr/lib/../bin/gcc").equals("usr/bin/gcc"));
		assert (normalizePathname("./../").equals(".."));
		assert (normalizePathname("../../local").equals("../../local"));
		assert (normalizePathname("./.././../local").equals("../../local"));
		assert (normalizePathname("/foo/../foo/./../").equals("/"));
		assert (normalizePathname("scripts//./../scripts/awkscripts/././").equals("scripts/awkscripts"));
	}
}
