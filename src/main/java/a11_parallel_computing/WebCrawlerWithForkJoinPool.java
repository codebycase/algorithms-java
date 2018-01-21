package a11_parallel_computing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WebCrawlerWithForkJoinPool {
	interface LinkHandler {
		/**
		 * Returns the number of visited links
		 */
		int size();

		/**
		 * Checks if the link was already visited
		 */
		boolean visited(String link);

		/**
		 * Marks this link as visited
		 */
		void addVisited(String link);
	}

	public static class LinkFinderTask extends RecursiveTask<Integer> {
		private static final long serialVersionUID = 7745219556084337754L;

		private String url;
		private LinkHandler handler;
		private final long t0 = System.nanoTime();

		public LinkFinderTask(String url, LinkHandler handler) {
			this.url = url;
			this.handler = handler;
		}

		@Override
		protected Integer compute() {
			if (!handler.visited(url)) {
				try {
					List<RecursiveTask<Integer>> tasks = new ArrayList<>();
					Document doc = Jsoup.connect(url).get();
					for (Element link : doc.select("a")) {
						String href = link.attr("abs:href");
						if (href != null && !href.isEmpty()) {
							tasks.add(new LinkFinderTask(href, handler));
						}
					}
					handler.addVisited(url);
					if (handler.size() == 1000) {
						System.out.println("Time for visit 1000 distinct links = " + (System.nanoTime() - t0));
						return handler.size();
					}
					// invoke recursively
					invokeAll(tasks);
				} catch (Exception e) {
					// ignore
				}
			}
			return null;
		}
	}

	public static class WebCrawler implements LinkHandler {
		private final Collection<String> visitedLinks = Collections.synchronizedSet(new HashSet<>());
		private String url;
		private ForkJoinPool mainPool;

		public WebCrawler(String startingUrl, int maxThreads) {
			this.url = startingUrl;
			mainPool = new ForkJoinPool(maxThreads);
		}

		public void startCrawling() {
			mainPool.invoke(new LinkFinderTask(this.url, this));
		}

		@Override
		public int size() {
			return visitedLinks.size();
		}

		@Override
		public boolean visited(String link) {
			return visitedLinks.contains(link);
		}

		@Override
		public void addVisited(String link) {
			visitedLinks.add(link);
		}

	}

	public static void main(String[] args) {
		String startingUrl = "http://www.javaworld.com";
		WebCrawler crawler = new WebCrawler(startingUrl, 64);
		crawler.startCrawling();
	}
}
