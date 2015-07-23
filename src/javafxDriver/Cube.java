package javafxDriver;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;


public class Cube {
	
	private PhongMaterial material = new PhongMaterial();
	private Box box;
	
	private Rotate rx = new Rotate(0, Rotate.X_AXIS);
	private Rotate ry = new Rotate(0, Rotate.Y_AXIS);
	private Rotate rz = new Rotate(0, Rotate.Z_AXIS);
	
	public Cube () {
		this(0, 0, 0);
	}

	public Cube (double width, double height, double depth) {
		this(width, height, depth, Color.RED, Color.RED);
	}

	public Cube (double width, double height, double depth, Color diffuse, Color specular) {
		this.box = new Box(width, height, depth);
		this.box.getTransforms().addAll(rz, ry, rx);
		this.setColor(diffuse, specular);
	}

	public void translate (double x, double y, double z) {
		this.box.setTranslateX(x);
		this.box.setTranslateY(y);
		this.box.setTranslateZ(z);
	}

	public void setColor (double r, double g, double b) {
		this.setColor(r, g, b, 1);
	}

	public void setColor (double r, double g, double b, double a) {
		if (r < 0) r = 0;
		else if (r > 1) r = 1;
		if (g < 0) g = 0;
		else if (g > 1) g = 1;
		if (b < 0) b = 0;
		else if (b > 1) b = 1;
		if (a < 0) a = 0;
		else if (a > 1) a = 1;
		
		setColor(Color.color(r, g, b, a));
	}

	private void setColor(Color color) {
		this.setColor(color, color);
	}

	private void setColor(Color diffuse, Color specular) {
		this.material.setDiffuseColor(diffuse);
		this.material.setSpecularColor(specular);
		this.box.setMaterial(material);
	}
	
	public Box getBox () {
		return this.box;
	}

	public void setRotate(double x, double y, double z) {
		this.rx.setAngle(x);
		this.ry.setAngle(y);
		this.rz.setAngle(z);
	}
	
	public void setScale(double x, double y, double z) {
		this.box.setScaleX(x);
		this.box.setScaleY(y);
		this.box.setScaleZ(z);
	}

}
