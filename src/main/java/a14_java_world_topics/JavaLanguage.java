package a14_java_world_topics;

public class JavaLanguage {
	// This swap it not working as the references are passed by value!
	public static void swap(Integer i, Integer j) {
		Integer temp = i;
		i = j;
		j = temp;
	}

	public static void main(String[] args) {
		Integer i = 10;
		Integer j = 20;
		swap(i, j);
		System.out.println("i = " + i + ", j = " + j); // Answer: i = 10, j = 20
		
	       int x = -1; // 11111111111111111111111111111110
	       System.out.println(x>>>29);  
	       System.out.println(x>>>30);  
	       System.out.println(x>>>31); 
	       
	}
}
