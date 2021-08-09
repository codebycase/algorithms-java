package a18_the_honors_question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

/**
 * Given a list of accounts where each element accounts[i] is a list of strings, where the first
 * element accounts[i][0] is a name, and the rest of the elements are emails representing emails of
 * the account.
 * 
 * Now, we would like to merge these accounts. Two accounts definitely belong to the same person if
 * there is some common email to both accounts. Note that even if two accounts have the same name,
 * they may belong to different people as people could have the same name. A person can have any
 * number of accounts initially, but all of their accounts definitely have the same name.
 * 
 * After merging the accounts, return the accounts in the following format: the first element of
 * each account is the name, and the rest of the elements are emails in sorted order. The accounts
 * themselves can be returned in any order.
 * 
 * Union find to merge accounts if contain same emails
 * 
 * Time Complexity: O(AlogA),where A=∑ai and ai is the length of accounts[i]
 */
class AccountsMerge {
  public List<List<String>> accountsMerge(List<List<String>> acts) {
    Map<String, String> owner = new HashMap<>();
    Map<String, String> parents = new HashMap<>();
    Map<String, TreeSet<String>> unions = new HashMap<>();
    for (List<String> a : acts) {
      for (int i = 1; i < a.size(); i++) {
        parents.put(a.get(i), a.get(i));
        owner.put(a.get(i), a.get(0));
      }
    }
    for (List<String> a : acts) {
      String p = find(a.get(1), parents);
      for (int i = 2; i < a.size(); i++)
        parents.put(find(a.get(i), parents), p);
    }
    for (List<String> a : acts) {
      String p = find(a.get(1), parents);
      if (!unions.containsKey(p))
        unions.put(p, new TreeSet<>());
      for (int i = 1; i < a.size(); i++)
        unions.get(p).add(a.get(i));
    }
    List<List<String>> res = new ArrayList<>();
    for (String p : unions.keySet()) {
      List<String> emails = new ArrayList<>(unions.get(p));
      emails.add(0, owner.get(p));
      res.add(emails);
    }
    return res;
  }

  private String find(String s, Map<String, String> p) {
    return p.get(s) == s ? s : find(p.get(s), p);
  }

  public List<List<String>> accountsMergeWithDSU(List<List<String>> accounts) {
    DSU dsu = new DSU();
    Map<String, String> emailToName = new HashMap<>();
    Map<String, Integer> emailToID = new HashMap<>();
    int id = 0;
    for (List<String> account : accounts) {
      String name = "";
      for (String email : account) {
        if (name == "") {
          name = email;
          continue;
        }
        emailToName.put(email, name);
        if (!emailToID.containsKey(email)) {
          emailToID.put(email, id++);
        }
        dsu.union(emailToID.get(account.get(1)), emailToID.get(email));
      }
    }

    Map<Integer, List<String>> ans = new HashMap<>();
    for (String email : emailToName.keySet()) {
      int index = dsu.find(emailToID.get(email));
      ans.computeIfAbsent(index, x -> new ArrayList<>()).add(email);
    }
    for (List<String> component : ans.values()) {
      Collections.sort(component);
      component.add(0, emailToName.get(component.get(0)));
    }
    return new ArrayList<>(ans.values());
  }

  class DSU {
    int[] parent;

    public DSU() {
      parent = new int[10001];
      for (int i = 0; i <= 10000; ++i)
        parent[i] = i;
    }

    public int find(int x) {
      if (parent[x] != x) {
        parent[x] = find(parent[x]);
      }
      return parent[x];
    }

    public void union(int x, int y) {
      parent[find(x)] = find(y);
    }
  }

  public List<List<String>> accountsMergeWithDFS(List<List<String>> accounts) {
    Map<String, String> emailToName = new HashMap<>();
    Map<String, ArrayList<String>> graph = new HashMap<>();
    for (List<String> account : accounts) {
      String name = "";
      for (String email : account) {
        if (name == "") {
          name = email;
          continue;
        }
        graph.computeIfAbsent(email, x -> new ArrayList<String>()).add(account.get(1));
        graph.computeIfAbsent(account.get(1), x -> new ArrayList<String>()).add(email);
        emailToName.put(email, name);
      }
    }

    Set<String> seen = new HashSet<>();
    List<List<String>> ans = new ArrayList<>();
    for (String email : graph.keySet()) {
      if (!seen.contains(email)) {
        seen.add(email);
        Stack<String> stack = new Stack<>();
        stack.push(email);
        List<String> component = new ArrayList<>();
        while (!stack.empty()) {
          String node = stack.pop();
          component.add(node);
          for (String nei : graph.get(node)) {
            if (!seen.contains(nei)) {
              seen.add(nei);
              stack.push(nei);
            }
          }
        }
        Collections.sort(component);
        component.add(0, emailToName.get(email));
        ans.add(component);
      }
    }
    return ans;
  }
}
