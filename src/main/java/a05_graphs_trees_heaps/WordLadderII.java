package a05_graphs_trees_heaps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordLadderII {
  // BFS
  public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
    List<List<String>> ladders = new ArrayList<>();

    Set<String> wordSet = new HashSet<>(wordList);
    if (!wordSet.contains(endWord))
      return ladders;

    // build the DAG graph using bidirectional BFS
    Map<String, List<String>> graph = new HashMap<String, List<String>>();
    if (!buildDAGbyBFS(beginWord, endWord, wordSet, graph)) {
      return ladders;
    }

    // generate ladders by traversing the DAG graph
    List<String> list = new ArrayList<>();
    list.add(beginWord);
    generateLadders(beginWord, endWord, graph, list, ladders);

    return ladders;
  }

  private boolean buildDAGbyBFS(String beginWord, String endWord, Set<String> wordSet, Map<String, List<String>> graph) {
    if (wordSet.contains(beginWord)) {
      wordSet.remove(beginWord);
    }

    Set<String> beginSet = new HashSet<String>();
    Set<String> endSet = new HashSet<String>();
    beginSet.add(beginWord);
    endSet.add(endWord);

    boolean found = false;
    boolean isForward = true;
    while (!beginSet.isEmpty() && !endSet.isEmpty()) {
      Set<String> newSet = new HashSet<String>();

      // always choose the smaller set as begin set
      if (beginSet.size() > endSet.size()) {
        Set<String> temp = beginSet;
        beginSet = endSet;
        endSet = temp;
        isForward = !isForward;
      }

      // clean up previous words
      wordSet.removeAll(beginSet);

      for (String word : beginSet) {
        char[] chrs = word.toCharArray();
        for (int i = 0; i < word.length(); i++) {
          char old = chrs[i];
          for (char c = 'a'; c <= 'z'; c++) {
            chrs[i] = c;
            if (old == c) {
              continue;
            }

            String next = new String(chrs);
            if (!wordSet.contains(next))
              continue;

            newSet.add(next);
            String key = isForward ? word : next;
            String value = isForward ? next : word;
            graph.computeIfAbsent(key, k -> new ArrayList<>()).add(value);

            if (endSet.contains(next))
              found = true;
          }
          // backtrack
          chrs[i] = old;
        }
      }

      beginSet = newSet;

      if (found) {
        break;
      }
    }

    return found;
  }

  // DFS
  public List<List<String>> findLadders2(String beginWord, String endWord, List<String> wordList) {
    List<List<String>> ladders = new ArrayList<>();

    Set<String> wordSet = new HashSet<>(wordList);
    if (!wordSet.contains(endWord))
      return ladders;

    Map<String, List<String>> graph = new HashMap<>(wordList.size());

    Set<String> beginSet = new HashSet<>();
    beginSet.add(beginWord);

    Set<String> endSet = new HashSet<>();
    endSet.add(endWord);

    // build the DAG graph using bidirectional DFS
    if (!buildDAGbyDFS(wordSet, beginSet, endSet, graph, true))
      return ladders;

    // generate ladders by traversing the DAG graph
    List<String> list = new ArrayList<>();
    list.add(beginWord);
    generateLadders(beginWord, endWord, graph, list, ladders);

    return ladders;
  }

  private boolean buildDAGbyDFS(Set<String> wordSet, Set<String> beginSet, Set<String> endSet, Map<String, List<String>> graph, boolean isForward) {
    if (beginSet.isEmpty() || endSet.isEmpty())
      return false;

    wordSet.removeAll(beginSet);

    boolean found = false;
    Set<String> newSet = new HashSet<>();
    for (String word : beginSet) {
      char[] chrs = word.toCharArray();
      for (int i = 0; i < chrs.length; i++) {
        char old = chrs[i];
        for (char c = 'a'; c <= 'z'; c++) {
          if (old == c)
            continue;
          chrs[i] = c;

          String next = new String(chrs);
          if (!wordSet.contains(next))
            continue;

          newSet.add(next);
          String key = isForward ? word : next;
          String value = isForward ? next : word;
          graph.computeIfAbsent(key, k -> new ArrayList<>()).add(value);

          if (endSet.contains(next))
            found = true;
        }
        // backtrack
        chrs[i] = old;
      }
    }

    if (found)
      return true;

    if (newSet.size() > endSet.size())
      return buildDAGbyDFS(wordSet, endSet, newSet, graph, !isForward);
    return buildDAGbyDFS(wordSet, newSet, endSet, graph, isForward);
  }

  private void generateLadders(String beginWord, String endWord, Map<String, List<String>> graph, List<String> path, List<List<String>> result) {
    if (beginWord.equals(endWord)) {
      result.add(new ArrayList<>(path));
    } else if (graph.containsKey(beginWord)) {
      for (String word : graph.get(beginWord)) {
        path.add(word);
        generateLadders(word, endWord, graph, path, result);
        path.remove(path.size() - 1);
      }
    }
  }

  public static void main(String[] args) {
    WordLadderII solution = new WordLadderII();
    List<String> words = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");
    System.out.println(solution.findLadders("hit", "cog", words));
    System.out.println(solution.findLadders2("hit", "cog", words));
    words = Arrays.asList("flail", "halon", "lexus", "joint", "pears", "slabs", "lorie", "lapse", "wroth", "yalow", "swear", "cavil", "piety",
        "yogis", "dhaka", "laxer", "tatum", "provo", "truss", "tends", "deana", "dried", "hutch", "basho", "flyby", "miler", "fries", "floes",
        "lingo", "wider", "scary", "marks", "perry", "igloo", "melts", "lanny", "satan", "foamy", "perks", "denim", "plugs", "cloak", "cyril",
        "women", "issue", "rocky", "marry", "trash", "merry", "topic", "hicks", "dicky", "prado", "casio", "lapel", "diane", "serer", "paige",
        "parry", "elope", "balds", "dated", "copra", "earth", "marty", "slake", "balms", "daryl", "loves", "civet", "sweat", "daley", "touch",
        "maria", "dacca", "muggy", "chore", "felix", "ogled", "acids", "terse", "cults", "darla", "snubs", "boats", "recta", "cohan", "purse",
        "joist", "grosz", "sheri", "steam", "manic", "luisa", "gluts", "spits", "boxer", "abner", "cooke", "scowl", "kenya", "hasps", "roger",
        "edwin", "black", "terns", "folks", "demur", "dingo", "party", "brian", "numbs", "forgo", "gunny", "waled", "bucks", "titan", "ruffs",
        "pizza", "ravel", "poole", "suits", "stoic", "segre", "white", "lemur", "belts", "scums", "parks", "gusts", "ozark", "umped", "heard",
        "lorna", "emile", "orbit", "onset", "cruet", "amiss", "fumed", "gelds", "italy", "rakes", "loxed", "kilts", "mania", "tombs", "gaped",
        "merge", "molar", "smith", "tangs", "misty", "wefts", "yawns", "smile", "scuff", "width", "paris", "coded", "sodom", "shits", "benny",
        "pudgy", "mayer", "peary", "curve", "tulsa", "ramos", "thick", "dogie", "gourd", "strop", "ahmad", "clove", "tract", "calyx", "maris",
        "wants", "lipid", "pearl", "maybe", "banjo", "south", "blend", "diana", "lanai", "waged", "shari", "magic", "duchy", "decca", "wried",
        "maine", "nutty", "turns", "satyr", "holds", "finks", "twits", "peaks", "teems", "peace", "melon", "czars", "robby", "tabby", "shove",
        "minty", "marta", "dregs", "lacks", "casts", "aruba", "stall", "nurse", "jewry", "knuth");
    List<List<String>> ladders1 = solution.findLadders("magic", "pearl", words);
    List<List<String>> ladders2 = solution.findLadders2("magic", "pearl", words);
    for (List<String> ladder2 : ladders2) {
      if (ladders1.contains(ladder2)) {
        System.out.println(ladder2);
      }
      if (!ladders1.contains(ladder2)) {
        System.out.println();
        System.out.println(ladder2);
      }
    }

  }
}
