package a01_fundamentals;

/**
 * This problem is concerned with rectangles whose sides are parallel to the X-axis and Y axis.
 * Write a program which tests if two rectangles have a non empty intersection, return the rectangle
 * formed by their intersection.
 * 
 * @author lchen
 *
 */
public class RectangleIntersection {
	static class Rectangle {
		int x, y, width, height;

		public Rectangle(int x, int y, int width, int height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
	}

	public static Rectangle intersectRectangle(Rectangle R1, Rectangle R2) {
		if (!isIntersect(R1, R2))
			return new Rectangle(0, 0, -1, -1); // no intersection

		int x = Math.max(R1.x, R2.x);
		int y = Math.max(R1.y, R2.y);
		int width = Math.min(R1.x + R1.width, R2.x + R2.width) - x;
		int height = Math.min(R1.y + R1.height, R2.y + R2.height) - y;
		return new Rectangle(x, y, width, height);
	}

	private static boolean isIntersect(Rectangle R1, Rectangle R2) {
		return R1.x <= R2.x + R2.width && R1.x + R1.width >= R2.x 
			&& R1.y <= R2.y + R2.height && R1.y + R1.height >= R2.y;
	}

	public static void main(String[] args) {
		Rectangle R1, R2;
		R1 = new Rectangle(0, 0, 2, 2);
		R2 = new Rectangle(1, 1, 3, 3);
		Rectangle result = intersectRectangle(R1, R2);
		assert (result.x == 1 && result.y == 1 && result.width == 1 && result.height == 1);
		R1 = new Rectangle(0, 0, 1, 1);
		R2 = new Rectangle(1, 1, 3, 3);
		result = intersectRectangle(R1, R2);
		assert (result.x == 1 && result.y == 1 && result.width == 0 && result.height == 0);
		R1 = new Rectangle(0, 0, 1, 1);
		R2 = new Rectangle(2, 2, 3, 3);
		result = intersectRectangle(R1, R2);
		assert (result.x == 0 && result.y == 0 && result.width == -1 && result.height == -1);
	}
}
