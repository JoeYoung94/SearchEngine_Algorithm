package webEngine;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class PageRanking {
	private final static CreateWordDic wordDic = CreateWordDic.getInstatnce();
	private final static Map<String, List<PageInfo>> wordDictionary = wordDic.getWordDictionary();
	private final static String[] dataArray = { "1", "11" };

	public static void main(String[] args) throws IOException {

		List<PageInfo> list = joinList(dataArray);
		for (int i = 0; i < list.size(); i++) {
			// System.out.println(list.get(i).getPageName() + "--" +
			// list.get(i).getFrequency());
		}
		Map<String, Integer> map = convertListToTable(list);
		List<EachPage> pageList = listTop10Pages(map);
		printTop10Pages(pageList);
	}

	/**
	 * Add the list of searching each string in word dictionary.
	 * 
	 * @param inputData
	 * @return Give a list that contains all the
	 */
	private static List<PageInfo> joinList(String[] inputData) {
		List<PageInfo> newList = new ArrayList<PageInfo>();
		for (String str : inputData) {
			if (wordDictionary.containsKey(str)) {
				newList.addAll(wordDictionary.get(str));
			}
		}
		return newList;
	}

	/**
	 * Print top 10 pages.
	 * 
	 * @param pageList
	 */
	private static void printTop10Pages(List<EachPage> pageList) {
		for (int i = 0; i < pageList.size(); i++) {
			System.out.println(pageList.get(i).getPageName() + "--" + pageList.get(i).getPageScore());
		}
	}

	/**
	 * Print pages with page name and page score.
	 * 
	 * @param map
	 */
	private static void printPageMap(Map<String, Integer> map) {
		System.out.println("The elements in hash map");
		Set<String> keys = map.keySet();
		for (String key : keys) {
			System.out.println(key + "---" + map.get(key));
		}
	}

	/**
	 * Print the 10 pages which have the top highest score in descending order.
	 * 
	 * @param map
	 * @return Give a list of pages in descending order according to page score.
	 */
	private static List<EachPage> listTop10Pages(Map<String, Integer> map) {
		int counter = 0;
		PriorityQueue<EachPage> pq = pageSorting(map);
		List<EachPage> list = new ArrayList<EachPage>();
		while (!pq.isEmpty() && counter <= 10) {
			counter += 1;
			list.add(pq.poll());
		}
		return list;
	}

	public static List<EachPage> listTop10Pages(String[] args) {

		List<PageInfo> pagelist = joinList(args);
		Map<String, Integer> map = convertListToTable(pagelist);

		int counter = 0;
		PriorityQueue<EachPage> pq = pageSorting(map);
		List<EachPage> list = new ArrayList<EachPage>();
		while (!pq.isEmpty() && counter <= 10) {
			counter += 1;
			list.add(pq.poll());
		}
		return list;
	}

	/**
	 * Count score of each page
	 * 
	 * @param list
	 * @return Give hash map which key is page name and value is page score
	 */
	private static Map<String, Integer> convertListToTable(List<PageInfo> list) {

		Map<String, Integer> table = new HashMap<String, Integer>();
		for (int i = 0; i < list.size(); i++) {

			if (!table.containsKey(list.get(i).getPageName())) {
				table.put(list.get(i).getPageName(), list.get(i).getFrequency());
			} else {
				Integer score = table.get(list.get(i).getPageName()) + list.get(i).getFrequency();
				table.put(list.get(i).getPageName(), score);
			}
		}

		return table;
	}

	/**
	 * Sorting page according to priority queue
	 * 
	 * @param table
	 * @return Give a queue with descending order according to page score
	 */
	private static PriorityQueue<EachPage> pageSorting(Map<String, Integer> table) {
		PriorityQueue<EachPage> pq = new PriorityQueue<EachPage>();
		Set<String> keys = table.keySet();
		for (String key : keys) {
			EachPage eachPage = new EachPage(key, table.get(key));
			pq.add(eachPage);
		}
		return pq;
	}

}
