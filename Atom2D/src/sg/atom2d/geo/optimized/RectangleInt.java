package sg.atom2d.geo.optimized;
/**
 * A integer based rectangle. The strange name is to avoid name clashes with
 * java.awt.Rectangle. <p> Rect and Box represents the same concept, but their
 * different definition makes them suitable for use in different situations.
 *
 * @author <a href="mailto:atomixnmc@gmail">Micheal Nguyen</a>
 */
public class RectangleInt {
    
    public int x;
    public int y;
    public int height;
    public int width;

    /**
     * Create a rectangle.
     *
     * @param x X coordinate of upper left corner.
     * @param y Y coordinate of upper left corner.
     * @param width Width of rectangle.
     * @param height Height of rectangle.
     */
    public RectangleInt(int x, int y, int width, int height) {
        set(x, y, width, height);
    }

    /**
     * Create a default rectangle.
     */
    public RectangleInt() {
        this(0, 0, 0, 0);
    }

    /**
     * Create a rectangle as a copy of the specified rectangle.
     *
     * @param rectangle
     */
    public RectangleInt(RectangleInt rectangle) {
        this(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    /**
     * Create a rectnagle based on specified box.
     *
     * @param box Box to create rectangle from.
     */
    public RectangleInt(BoxInt box) {
        this(box.x1, box.y1, box.x2 - box.x1, box.y2 - box.y1);
    }

    /**
     * Copy the specified rectangle.
     *
     * @param rectangle Rectangle to copy.
     */
    public void copy(RectangleInt rectangle) {
        this.x = rectangle.x;
        this.y = rectangle.y;
        this.width = rectangle.width;
        this.height = rectangle.height;
    }

    /**
     * Clone this rectangle
     *
     * @return Clone of this rectangle.
     */
    public Object clone() {
        return new RectangleInt(x, y, width, height);
    }

    /**
     * Check if this rectangle equals the specified object.
     *
     * @param object Object to chack.
     * @return True if the two equals, false otherwise.
     */
    public boolean equals(Object object) {
        RectangleInt rectangle = (RectangleInt) object;
        
        return this.x == rectangle.x
                && this.y == rectangle.y
                && this.width == rectangle.width
                && this.height == rectangle.height;
    }

    /**
     * Return true if this rectangle is empty.
     *
     * @return True if this rectangle is empty, false otherwise.
     */
    public boolean isEmpty() {
        return width <= 0 || height <= 0;
    }

    /**
     * Expand this rectangle the specified amount in each direction.
     *
     * @param dx Amount to expand to left and right.
     * @param dy Amount to expand on top and botton.
     */
    public void expand(int dx, int dy) {
        x -= dx;
        y -= dy;
        width += dx + dx;
        height += dy + dy;
    }

    /**
     * Set the parameters for this rectangle.
     *
     * @param x X coordinate of upper left corner.
     * @param y Y coordinate of upper left corner.
     * @param width Width of rectangle.
     * @param height Height of rectangle.
     */
    public void set(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Set this rectangle as extent of specified polyline.
     *
     * @param xArray X coordinates of polyline.
     * @param yArray Y coordinates of polyline.
     */
    public void set(int xArray[], int yArray[]) {
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        
        for (int i = 0; i < xArray.length; i++) {
            if (xArray[i] < minX) {
                minX = xArray[i];
            }
            if (xArray[i] > maxX) {
                maxX = xArray[i];
            }
            
            if (yArray[i] < minY) {
                minY = yArray[i];
            }
            if (yArray[i] > maxY) {
                maxY = yArray[i];
            }
        }
        
        x = minX;
        y = minY;
        
        width = maxX - minX + 1;
        height = maxY - minY + 1;
    }

    /**
     * Return X coordinate of center of this rectangle.
     *
     * @return X coordinate of center of this rectangle.
     */
    public int getCenterX() {
        return x + (int) Math.floor(width / 2.0);
    }

    /**
     * Return Y coordinate of center of this rectangle.
     *
     * @return Y coordinate of center of this rectangle.
     */
    public int getCenterY() {
        return y + (int) Math.floor(height / 2.0);
    }

    /**
     * Return a string representation of this rectangle.
     *
     * @return String representation of this rectangle.
     */
    public String toString() {
        return new String("Rectangle: x= " + x + " y=" + y
                + " width=" + width + " height=" + height);
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
/*    
    public void setLocation(Point point) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public void setLocation(int bulletsX, int bulletsY) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public void setSize(int bulletsWidth, int bulletsHeight) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public void setRect(Rectangle original) {
        this.x = original.x;
    }

    public Rectangle createIntersection(Rectangle backgroundBounds) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    */ 
}
