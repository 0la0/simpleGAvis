package javafxDriver;

import gaViz.main.*;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.SceneAntialiasing;


public class Basic3dDriver {

	private Group root = new Group();
	private Group boundryGroup = new Group();
	private Xform world = new Xform();
	private PerspectiveCamera camera = new PerspectiveCamera(true);
	private Xform cameraXform = new Xform();
	private Xform cameraXform2 = new Xform();
	private Xform cameraXform3 = new Xform();
	private double cameraDistance = 2500;

	private Xform particleGroup = new Xform();
	private ArrayList<Cube> particles = new ArrayList<Cube>();
	private int size = 500;
	private int halfSize = size;

	private SubScene scene;
	private Cube goalCube;
	private int particleSize = 20;
	
	private double mousePosX;
	private double mousePosY;
	private double mouseOldX;
	private double mouseOldY;
	private double mouseDeltaX;
	private double mouseDeltaY;
	
	private Options options;
	private int goal;
	private Population parent; 
	private Population child;
	private long lastTime;
	private boolean isFrameSkip = false;
	private int sizeMult = 4;
	
	
	public Basic3dDriver (Options options) {
		this.options = options;
		this.goal = (int) Math.pow(2, this.options.geneLength);
		BinaryStringHelper.setStringLength(this.options.geneLength);
		
		this.buildGoalCube();
		this.initPopulation();
		this.buildParticles();
		this.generate(0);
		
		
		//---UI SETUP---//
		root.getChildren().add(world);
		this.buildCamera();
		this.buildBoundries();
		
		this.scene = new SubScene(root, 900, 675, true, SceneAntialiasing.BALANCED);
		this.scene.setFill(Color.color(0.85, 0.85, 1.0));
		this.scene.setCamera(camera);
		this.handleMouse();
			
		//---SET UP AND START TIMER---//
		this.lastTime = System.nanoTime();
		AnimationTimer timer = new AnimationTimer() {
			public void handle(long now) {
				float elapsedTime = (float) ((now - lastTime) / 1000000.0);
				lastTime = now;
				generate(elapsedTime);
				
			}
		};
		timer.start();
	}
	
	public void initPopulation () {
		parent = new Population(this.options.populationSize, this.options.numGenes, this.goal);
		this.options.fitnessObj.calcFitness(parent);
		this.options.probabilityObj.calc(parent);
	}
	
	private void generate (float elapsedTime) {
		//long startTime = System.nanoTime();
		this.isFrameSkip = !this.isFrameSkip;
		if (this.isFrameSkip) return;
		
		
		//---------BREED NEW GENERATION-------------//
		child = this.options.breederObj.breed(parent);
		this.options.crossoverObj.crossover(child);
		this.options.mutateObj.mutate(child);
		this.options.fitnessObj.calcFitness(child);
		this.options.probabilityObj.calc(child);
		parent = child;	
		//------------------------------------------//
		
		
		for (int i = 0; i < this.child.size; i++) {
			Cube cube = particles.get(i);
			
			double normalX = this.child.individuals[i].genome[0] / (this.goal * 1.0);
			double normalY = this.child.individuals[i].genome[1] / (this.goal * 1.0);
			double normalZ = this.child.individuals[i].genome[2] / (this.goal * 1.0);
			double normalR = this.child.individuals[i].genome[3] / (this.goal * 1.0);
			double normalG = this.child.individuals[i].genome[4] / (this.goal * 1.0);
			double normalB = this.child.individuals[i].genome[5] / (this.goal * 1.0);
			double normalScaleX = this.child.individuals[i].genome[6] / (this.goal * 1.0);
			double normalScaleY = this.child.individuals[i].genome[7] / (this.goal * 1.0);
			double normalScaleZ = this.child.individuals[i].genome[8] / (this.goal * 1.0);
			
			int x = (int) Math.floor(500 * normalX);
			int y = (int) Math.floor(500 * normalY);
			int z = (int) Math.floor(500 * normalZ);
			
			cube.translate(x, y, z);
			cube.setColor(normalR, normalG, normalB);
			cube.setScale(normalScaleX * this.sizeMult, normalScaleY * this.sizeMult, normalScaleZ * this.sizeMult);
		}
		
		//---------------RESET GOAL-----------------//
		if (Math.random() < 0.005) {
			float x = (float) (Math.random());
			float y = (float) (Math.random());
			float z = (float) (Math.random());
			float r = (float) Math.random();
			float g = (float) Math.random();
			float b = (float) Math.random();
			float scaleX = (float) (Math.random());
			float scaleY = (float) (Math.random());
			float scaleZ = (float) (Math.random());
			this.options.fitnessObj.setGoal(new float[]{x, y, z, r, g, b, scaleX, scaleY, scaleZ});
			//this.options.fitnessObj.setGoal(new float[]{x, y, z, r, g, b});
			//System.out.println("Goal state set to: " + x + ", " + y + ", " + z);
		
			this.goalCube.translate(x * 500, y * 500, z * 500);
			this.goalCube.setColor(r, g, b);
			this.goalCube.setScale(scaleX * this.sizeMult, scaleY * this.sizeMult, scaleZ * this.sizeMult);
		}
		
		//---SCATTER: RANDOM RESTART---//
		//if (Math.random() < 0.005) this.initPopulation();
		
	}

	private void buildCamera() {
		root.getChildren().add(cameraXform);
		cameraXform.getChildren().add(cameraXform2);
		cameraXform2.getChildren().add(cameraXform3);
		cameraXform3.getChildren().add(camera);
		cameraXform3.setRotateZ(180.0);

		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		camera.setTranslateZ(-cameraDistance);
		cameraXform.ry.setAngle(320.0);
	}
	
	private void buildGoalCube () {
		float x = (float) (Math.random());
		float y = (float) (Math.random());
		float z = (float) (Math.random());
		float r = (float) Math.random();
		float g = (float) Math.random();
		float b = (float) Math.random();
		float scaleX = (float) (Math.random());
		float scaleY = (float) (Math.random());
		float scaleZ = (float) (Math.random());
		this.options.fitnessObj.setGoal(new float[]{x, y, z, r, g, b, scaleX, scaleY, scaleZ});
		
		this.goalCube = new Cube(60, 60, 60);
		this.goalCube.translate(x * 500, y * 500, z * 500);
		this.goalCube.setColor(r, g, b);
		
		//this.boundryGroup.getChildren().add(this.goalCube.getBox());
	}

	//---BUILDS CUBE SKELETON FOR VISUAL SPATIAL REFERENCE---//
	private void buildBoundries() {
		PhongMaterial blackMaterial = new PhongMaterial();
		blackMaterial.setDiffuseColor(Color.BLACK);
		blackMaterial.setSpecularColor(Color.BLACK);

		double lineWidth = 1.0;
		ArrayList<Cube> bounds = new ArrayList<Cube>();

		for (int i = 0; i < 4; i++)
			bounds.add(new Cube(size * 2, lineWidth, lineWidth, blackMaterial));
		for (int i = 0; i < 4; i++)
			bounds.add(new Cube(lineWidth, size * 2, lineWidth, blackMaterial));
		for (int i = 0; i < 4; i++)
			bounds.add(new Cube(lineWidth, lineWidth, size * 2, blackMaterial));

		bounds.get(0).translate(0, halfSize, halfSize);
		bounds.get(1).translate(0, halfSize, -halfSize);
		bounds.get(2).translate(0, -halfSize, halfSize);
		bounds.get(3).translate(0, -halfSize, -halfSize);

		bounds.get(4).translate(halfSize, 0, halfSize);
		bounds.get(5).translate(halfSize, 0, -halfSize);
		bounds.get(6).translate(-halfSize, 0, halfSize);
		bounds.get(7).translate(-halfSize, 0, -halfSize);

		bounds.get(8).translate(halfSize, halfSize, 0);
		bounds.get(9).translate(halfSize, -halfSize, 0);
		bounds.get(10).translate(-halfSize, halfSize, 0);
		bounds.get(11).translate(-halfSize, -halfSize, 0);

		for (Cube bound : bounds) {
			boundryGroup.getChildren().add(bound.getBox());
		}
		//boundryGroup.getChildren().add(this.goalCube.getBox());
		world.getChildren().addAll(boundryGroup);
	}

	private void buildParticles () {
		for (Individual individual : parent.individuals) {
			Color color = new Color(0.6, 0.2, 0.1, 1);
			Cube box = new Cube(this.particleSize, this.particleSize, this.particleSize, color, color);
			
			double normalX = individual.genome[0] / (this.goal * 1.0);
			double normalY = individual.genome[1] / (this.goal * 1.0);
			double normalZ = individual.genome[2] / (this.goal * 1.0);
			int x = (int) Math.floor(500 * normalX);
			int y = (int) Math.floor(500 * normalY);
			int z = (int) Math.floor(500 * normalZ);
			
			box.translate(x, y, z);
			particles.add(box);
		}
		
		for (Cube particle : particles) {
			this.particleGroup.getChildren().addAll(particle.getBox());
		}
		this.world.getChildren().addAll(this.particleGroup);
	}
	
	private int getPosNeg () {
		return (Math.random() < 0.5) ? 1 : -1;
	}
	
	public Node getUiNode () {
		return this.scene;
	}
	
	public String toString () {
		return "basic3D";
	}
	
	/*
	 * 3D camera rotate on mouse drag
	 * from http://docs.oracle.com/javafx/8/3d_graphics/sampleapp.htm
	 */
	public void handleMouse() {
		
		scene.setOnMousePressed((MouseEvent e) -> {
			mousePosX = e.getSceneX();
			mousePosY = e.getSceneY();
			mouseOldX = e.getSceneX();
			mouseOldY = e.getSceneY();
		});
		
		scene.setOnMouseDragged((MouseEvent e) -> {
			mouseOldX = mousePosX;
			mouseOldY = mousePosY;
			mousePosX = e.getSceneX();
			mousePosY = e.getSceneY();
			mouseDeltaX = (mousePosX - mouseOldX);
			mouseDeltaY = (mousePosY - mouseOldY);

			double modifier = 1.0;
			double modifierFactor = 0.1;

			if (e.isControlDown()) {
				modifier = 0.1;
			}
			if (e.isShiftDown()) {
				modifier = 10.0;
			}
			if (e.isPrimaryButtonDown()) {
				cameraXform.ry.setAngle(cameraXform.ry.getAngle() - mouseDeltaX * modifierFactor * modifier * 2.0);  // +
				cameraXform.rx.setAngle(cameraXform.rx.getAngle() + mouseDeltaY * modifierFactor * modifier * 2.0);  // -
			} else if (e.isSecondaryButtonDown()) {
				double z = camera.getTranslateZ();
				double newZ = z + mouseDeltaX * modifierFactor * modifier;
				camera.setTranslateZ(newZ);
			} else if (e.isMiddleButtonDown()) {
				cameraXform2.t.setX(cameraXform2.t.getX() + mouseDeltaX * modifierFactor * modifier * 0.3);  // -
				cameraXform2.t.setY(cameraXform2.t.getY() + mouseDeltaY * modifierFactor * modifier * 0.3);  // -
			}
		});
		
	}

}