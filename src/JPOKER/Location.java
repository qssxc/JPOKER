package JPOKER;

import javax.swing.JComponent;

public class Location {
	JComponent jcomponent;
	float width;
	float height;
	float x;
	float y;
	public Location(JComponent jcomponent, float width, float height, float x, float y) {
        this.jcomponent = jcomponent;        
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }
	public JComponent getJcomponent() {
		return jcomponent;
	}
	public float getWidth() {
		return width;
	}
	public float getHeight() {
		return height;
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	
}
