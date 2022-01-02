package a05_graphs_trees_heaps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordLadderII {
  public List<List<String>> findLadders1(String beginWord, String endWord, List<String> wordList) {
    List<List<String>> ladders = new ArrayList<>();
    Set<String> wordSet = new HashSet<>(wordList);
    // confirm if word list must contain end word!
    if (!wordSet.contains(endWord))
      return ladders;

    Map<String, List<String>> beginMap = new HashMap<>();
    Map<String, List<String>> endMap = new HashMap<>();
    beginMap.put(beginWord, new LinkedList<>(Arrays.asList(beginWord)));
    endMap.put(endWord, new LinkedList<>(Arrays.asList(endWord)));
    wordSet.remove(beginWord);
    wordSet.remove(endWord);

    boolean found = false;
    boolean isForward = true;
    while (!beginMap.isEmpty() && !endMap.isEmpty()) {
      // always choose the smaller end
      if (beginMap.size() > endMap.size()) {
        Map<String, List<String>> temp = beginMap;
        beginMap = endMap;
        endMap = temp;
        isForward = !isForward;
      }

      Map<String, List<String>> newMap = new HashMap<>();
      for (Map.Entry<String, List<String>> entry : beginMap.entrySet()) {
        char[] chrs = entry.getKey().toCharArray();
        for (int i = 0; i < chrs.length; i++) {
          char temp = chrs[i];
          for (char c = 'a'; c <= 'z'; c++) {
            if (temp == c)
              continue;
            chrs[i] = c;
            String target = String.valueOf(chrs);
            if (wordSet.contains(target)) {
              List<String> list = new LinkedList<>(entry.getValue());
              list.add(isForward ? list.size() : 0, target);
              newMap.put(target, list);
              wordSet.remove(target);
            }
            if (endMap.containsKey(target)) {
              List<String> list = new LinkedList<>();
              list.addAll(isForward ? entry.getValue() : endMap.get(target));
              list.addAll(isForward ? endMap.get(target) : entry.getValue());
              ladders.add(list);
              found = true;
            }
          }
          chrs[i] = temp;
        }
      }

      beginMap = newMap;

      if (found)
        break;
    }

    return ladders;
  }

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
    boolean found = false;
    wordSet.removeAll(beginSet);
    Set<String> newSet = new HashSet<>();
    for (String word : beginSet) {
      char[] chrs = word.toCharArray();
      for (int i = 0; i < chrs.length; i++) {
        char temp = chrs[i];
        for (char c = 'a'; c <= 'z'; c++) {
          if (temp == c)
            continue;
          chrs[i] = c;
          String target = new String(chrs);
          if (!wordSet.contains(target))
            continue;
          newSet.add(target);
          String key = isForward ? word : target;
          String value = isForward ? target : word;
          if (!graph.containsKey(key))
            graph.put(key, new ArrayList<>());
          graph.get(key).add(value);
          if (endSet.contains(target))
            found = true;
        }
        chrs[i] = temp;
      }
    }
    if (found)
      return true;
    if (newSet.size() > endSet.size())
      return buildDAGbyDFS(wordSet, endSet, newSet, graph, !isForward);
    return buildDAGbyDFS(wordSet, newSet, endSet, graph, isForward);
  }

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

    List<String> list = new ArrayList<>();
    list.add(beginWord);
    generateLadders(beginWord, endWord, graph, list, ladders);

    return ladders;
  }

  private boolean buildDAGbyBFS(String beginWord, String endWord, Set<String> wordSet, Map<String, List<String>> graph) {
    if (wordSet.contains(beginWord)) {
      wordSet.remove(beginWord);
    }

    Set<String> forwardQueue = new HashSet<String>();
    Set<String> backwardQueue = new HashSet<String>();

    forwardQueue.add(beginWord);
    backwardQueue.add(endWord);

    boolean found = false;
    boolean isForward = true;
    while (forwardQueue.isEmpty() != true) {
      // visited will store the words of current layer
      Set<String> visited = new HashSet<String>();

      // swap the queues because we are always extending the forwardQueue
      if (forwardQueue.size() > backwardQueue.size()) {
        Set<String> temp = forwardQueue;
        forwardQueue = backwardQueue;
        backwardQueue = temp;
        isForward = !isForward;
      }

      for (String currWord : forwardQueue) {
        char charList[] = currWord.toCharArray();

        for (int i = 0; i < currWord.length(); i++) {
          char oldChar = charList[i];

          // replace the i-th character with all letters from a to z except the original character
          for (char c = 'a'; c <= 'z'; c++) {
            charList[i] = c;

            String word = String.valueOf(charList);
            // skip if the character is same as original or if the word is not present in the wordList
            if (c == oldChar || !wordSet.contains(word)) {
              continue;
            }

            // if the backwardQueue already contains it we can stop after completing this level
            if (backwardQueue.contains(word)) {
              found = true;
              addEdge(currWord, word, isForward, graph);
            }

            /* the word shouldn't be presnt in forwardQueue because if it is then the edge will
            be between two words at the same level which we don't want */
            else if (!found && wordSet.contains(word) == true && forwardQueue.contains(word) == false) {
              visited.add(word);
              addEdge(currWord, word, isForward, graph);
            }

          }
          charList[i] = oldChar;
        }

      }

      // removing the words of the previous layer
      for (String currWord : forwardQueue) {
        if (wordSet.contains(currWord)) {
          wordSet.remove(currWord);
        }
      }

      if (found) {
        break;
      }

      forwardQueue = visited;
    }
    return found;
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

  private void addEdge(String word1, String word2, boolean isForward, Map<String, List<String>> graph) {
    if (isForward) {
      if (!graph.containsKey(word1)) {
        graph.put(word1, new ArrayList<String>());
      }
      graph.get(word1).add(word2);
    } else {
      if (!graph.containsKey(word2)) {
        graph.put(word2, new ArrayList<String>());
      }
      graph.get(word2).add(word1);
    }
  }

  public static void main(String[] args) {
    WordLadderII solution = new WordLadderII();
    List<String> words = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");
    List<List<String>> ladders = solution.findLadders2("hit", "cog", words);
    System.out.println(ladders);
    System.out.println(solution.findLadders("hit", "cog", words));
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
    List<List<String>> ladders1 = solution.findLadders1("magic", "pearl", words);
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
