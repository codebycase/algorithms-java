package a01_fundamentals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;

/**
 * Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. An island is
 * surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You
 * may assume all four edges of the grid are all surrounded by water.
 * 
 * Example 1:
 * 
 * <pre>
11110
11010
11000
00000
Answer: 1
 * </pre>
 * 
 * Example 2:
 * 
 * <pre>
11000
11000
00100
00011
Answer: 3
 * </pre>
 * 
 * @author lchen
 *
 */
public class NumberOfIslands {
    int[][] dirs = { { 0, 1 }, { 1, 0 }, { -1, 0 }, { 0, -1 } };

    /* solution1: clear joined lands */
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0)
            return 0;
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    clearJoinedLands(grid, i, j);
                }
            }
        }
        return count;
    }

    private void clearJoinedLands(char[][] grid, int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[i].length || grid[i][j] == '0')
            return;
        grid[i][j] = '0';
        for (int[] dir : dirs) {
            clearJoinedLands(grid, i + dir[0], j + dir[1]);
        }
        return;
    }

    /* solution2: union search */
    public int numIslands2(char[][] grid) {
        if (grid == null || grid.length == 0)
            return 0;
        int count = 0;

        int m = grid.length;
        int n = grid[0].length;
        int[] roots = new int[m * n];
        Arrays.fill(roots, -1);

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '1') {
                    int root = n * i + j;
                    roots[root] = root;
                    count++;

                    for (int[] dir : dirs) {
                        int x = i + dir[0];
                        int y = j + dir[1];
                        int nb = n * x + y;
                        if (x < 0 || x >= m || y < 0 || y >= n || roots[nb] == -1)
                            continue;
                        int rootNb = findIsland(roots, nb);
                        if (root != rootNb) {
                            roots[root] = rootNb; // union two islands
                            root = rootNb;
                            count--;
                        }
                    }
                }
            }
        }

        return count;
    }

    private int findIsland(int[] roots, int id) {
        while (id != roots[id]) {
            roots[id] = roots[roots[id]]; // compress path
            id = roots[id];
        }
        return id;
    }

    public int numDistinctIslands(int[][] grid) {
        Set<String> islands = new HashSet<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != 0) {
                    StringBuilder sb = new StringBuilder();
                    dfs(grid, i, j, sb, "o"); // origin
                    // grid[i][j] = 0;
                    islands.add(sb.toString());

                }
            }
        }
        return islands.size();
    }

    private void dfs(int[][] grid, int i, int j, StringBuilder sb, String dir) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[i].length || grid[i][j] == 0)
            return;
        sb.append(dir);
        grid[i][j] = 0;
        dfs(grid, i - 1, j, sb, "u");
        dfs(grid, i, j + 1, sb, "r");
        dfs(grid, i + 1, j, sb, "d");
        dfs(grid, i, j - 1, sb, "l");
        sb.append("b"); // bound
    }

    public int maxAreaOfIsland(int[][] grid) {
        boolean[][] seen = new boolean[grid.length][grid[0].length];
        int ans = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                ans = Math.max(ans, extendArea(r, c, grid, seen));
            }
        }
        return ans;
    }

    public int extendArea(int r, int c, int[][] grid, boolean[][] seen) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || seen[r][c] || grid[r][c] == 0)
            return 0;
        seen[r][c] = true;
        return (1 + extendArea(r + 1, c, grid, seen) + extendArea(r - 1, c, grid, seen)
                + extendArea(r, c - 1, grid, seen) + extendArea(r, c + 1, grid, seen));
    }

    public static void maxAvgDiffGroups(int[] nums) {
        // Arrays.sort(nums);
        if (nums == null || nums.length == 0)
            return;
        List<Integer> minGroup = new ArrayList<>();
        List<Integer> maxGroup = new ArrayList<>();
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            min = Math.min(min, nums[i]);
            max = Math.max(max, nums[i]);
        }
        int minCount = 0;
        int maxCount = 0;
        for (int i = 0; i < nums.length; i++) {
            int minDiff = nums[i] - min;
            int maxDiff = max - nums[i];
            if (minDiff + minCount >= maxDiff + maxCount) {
                maxGroup.add(nums[i]);
                maxCount += maxDiff;
            } else {
                minGroup.add(nums[i]);
                minCount += minDiff;
            }
        }
        System.out.println(minGroup + ":" + maxGroup);
    }

    public static List<List<Integer>> maxAvgDiffGroups2(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> groups = new ArrayList<>();
        groups.add(new ArrayList<>()); // min group
        groups.add(new ArrayList<>()); // max group
        if (nums == null || nums.length == 0)
            return groups;
        groups.get(0).add(nums[0]);
        if (nums.length == 1)
            return groups;
        groups.get(1).add(nums[nums.length - 1]);
        int i = 1, j = nums.length - 2;
        while (i <= j) {
            int iDiff = nums[i] - nums[i - 1];
            int jDiff = nums[j + 1] - nums[j];
            if (iDiff == jDiff) {
                groups.get(0).add(nums[i++]);
                groups.get(1).add(nums[j--]);
            } else if (iDiff < jDiff) {
                groups.get(0).add(nums[i++]);
            } else {
                groups.get(1).add(nums[j--]);
            }
        }
        System.out.println(nums[i]);
        System.out.println(average(groups.get(0)) + ":" + average(groups.get(1)));
        return groups;
    }

    public static List<List<Integer>> maxAvgDiffGroups3(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> groups = new ArrayList<>();
        groups.add(new ArrayList<>()); // min group
        groups.add(new ArrayList<>()); // max group
        if (nums == null || nums.length == 0)
            return groups;
        groups.get(0).add(nums[0]);
        if (nums.length == 1)
            return groups;
        groups.get(1).add(nums[nums.length - 1]);
        int i = 1, j = nums.length - 2;
        while (i <= j) {
            int iDiff = nums[i] - nums[i - 1];
            int jDiff = nums[j + 1] - nums[j];
            if (iDiff == jDiff) {
                groups.get(0).add(nums[i++]);
                groups.get(1).add(nums[j--]);
            } else if (iDiff < jDiff) {
                groups.get(0).add(nums[i++]);
            } else {
                groups.get(1).add(nums[j--]);
            }
        }
        System.out.println(nums[i]);
        System.out.println(average(groups.get(0)) + ":" + average(groups.get(1)));
        return groups;
    }

    private static double average(List<Integer> list) {
        double total = 0;
        for (int l : list) {
            total += l;
        }
        return total / list.size();
    }

    public static void main(String[] args) {
        NumberOfIslands solution = new NumberOfIslands();
        Assert.assertEquals(solution.numIslands(new char[][] { { '1', '1', '1', '1', '0' }, { '1', '1', '0', '1', '0' },
                { '1', '1', '0', '0', '0' }, { '0', '0', '0', '0', '0' } }), 1);
        Assert.assertEquals(solution.numIslands(new char[][] { { '1', '1', '0', '0', '0' }, { '1', '1', '0', '0', '0' },
                { '0', '0', '1', '0', '0' }, { '0', '0', '0', '1', '1' } }), 3);
        Assert.assertEquals(solution.numIslands2(new char[][] { { '1', '1', '1', '1', '0' },
                { '1', '1', '0', '1', '0' }, { '1', '1', '0', '0', '0' }, { '0', '0', '0', '0', '0' } }), 1);
        Assert.assertEquals(solution.numIslands2(new char[][] { { '1', '1', '0', '0', '0' },
                { '1', '1', '0', '0', '0' }, { '0', '0', '1', '0', '0' }, { '0', '0', '0', '1', '1' } }), 3);

        maxAvgDiffGroups(new int[] { 1, 5, 3, 4, 2, 7 });
        System.out.println(maxAvgDiffGroups2(new int[] { 1, 5, 3, 4, 4, 3, 4, 4, 4, 4, 4, 4, 2, 7 }));
    }

}
