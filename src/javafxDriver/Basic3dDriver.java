package javafxDriver;

import gaViz.main.*;

import java.util.ArrayDeque;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.SceneAntialiasing;

//"Chromosome" comes from the Greek words khroma meaning "color" and soma meaning "body"!
public class Basic3dDriver {

	private Group root = new Group();
	private Group boundryGroup = new Group();
	private Xform world = new Xform();
	private PerspectiveCamera camera = new PerspectiveCamera(true);
	private Xform cameraXform = new Xform();
	private Xform cameraXform2 = new Xform();
	private Xform cameraXform3 = new Xform();
	private double cameraDistance = 1500; //2500

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
	
	private GaConfigOptions options;
	private int goal;
	private final int generationsCount = 3;
	private ArrayDeque<Population> generations = new ArrayDeque<Population>();
	private long lastTime;
	private boolean isFrameSkip = false;
	private int sizeMult = 4;
	private int width = 900;
	private int height = 675;
	private double totTime = 0;
	private int cnt = 0;
	
	public Basic3dDriver (GaConfigOptions options) {
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
		//this.buildBoundries();
		
		this.scene = new SubScene(root, width, height, true, SceneAntialiasing.BALANCED);
		//this.scene.setFill(Color.color(0.85, 0.85, 1.0));
		this.scene.setFill(Color.color(0, 0, 0));
		this.scene.setCamera(camera);
		this.handleMouse();

		//---SET UP AND START TIMER---//
		this.lastTime = System.nanoTime();
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				float elapsedTime = (float) ((now - lastTime) / 1000000.0);
				lastTime = now;
				//if (cnt++ % 2 == 0)
					generate(elapsedTime);
				//animateCamera(elapsedTime);
				/*
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				*/
			}
		};
		timer.start();
	}
	
	public void initPopulation () {
		for (int g = 0; g < generationsCount; g++)
			generations.add(new Population(this.options.populationSize, this.options.numGenes, this.goal));
		this.options.fitnessObj.calcFitness(generations.peekLast());
		this.options.probabilityObj.calc(generations.peekLast());
	}
	
	private void animateCamera (float elapsedTime) {
		//this.totTime += elapsedTime * 0.0001;
		//this.cameraDistance = 1500 + (2000 * Math.sin(this.totTime));
		//this.camera.setTranslateZ(-this.cameraDistance);
		this.cameraXform.ry.setAngle(cameraXform.ry.getAngle() + elapsedTime * 0.01);
		this.cameraXform.rx.setAngle(cameraXform.rx.getAngle() + elapsedTime * 0.01);  
	}
	
	private void generate (float elapsedTime) {
		//long startTime = System.nanoTime();
		this.isFrameSkip = !this.isFrameSkip;
		if (this.isFrameSkip) return;
		
		
		//---------BREED NEW GENERATION-------------//
		Population child = this.options.breederObj.breed(generations.peekLast());
		this.options.crossoverObj.crossover(child);
		this.options.mutateObj.mutate(child);
		this.options.fitnessObj.calcFitness(child);
		this.options.probabilityObj.calc(child);
		
		generations.pop(); // "dying" generation
		generations.add(child); // new gemeration
		//------------------------------------------//
		
		// for each generation of particles
		int size = child.getSize();
		int genIndex = 0;
		for (Population gen : generations) {
			for (int i = 0; i < size; i++) {
				Cube cube = particles.get(i + genIndex * size);

				double normalX = gen.getIndividual(i).getGene(0) / (this.goal * 1.0);
				double normalY = gen.getIndividual(i).getGene(1) / (this.goal * 1.0);
				double normalZ = gen.getIndividual(i).getGene(2) / (this.goal * 1.0);
				double normalR = gen.getIndividual(i).getGene(3) / (this.goal * 1.0);
				double normalG = gen.getIndividual(i).getGene(4) / (this.goal * 1.0);
				double normalB = gen.getIndividual(i).getGene(5) / (this.goal * 1.0);
				double normalScaleX = gen.getIndividual(i).getGene(6) / (this.goal * 1.0);
				double normalScaleY = gen.getIndividual(i).getGene(7) / (this.goal * 1.0);
				double normalScaleZ = gen.getIndividual(i).getGene(8) / (this.goal * 1.0);
				//double rx = gen.getIndividual(i).getGene(9) / (this.goal * 1.0);
				//double ry = gen.getIndividual(i).getGene(9) / (this.goal * 1.0);
				//double rz = gen.getIndividual(i).getGene(9) / (this.goal * 1.0);

				int x = (int) Math.floor(1000 * (normalX - 0.5));
				int y = (int) Math.floor(1000 * (normalY - 0.5));
				int z = (int) Math.floor(1000 * (normalZ - 0.5));

				cube.translate(x, y, z);
				// opacity based on age, the dying are dissipating
				double opacity = ((double) generationsCount - genIndex) / generationsCount;
				cube.setColor(normalR, normalG, normalB, opacity);
				cube.setScale(normalScaleX * this.sizeMult, normalScaleY * this.sizeMult, normalScaleZ * this.sizeMult);
				//cube.setRotate(rx * 360, ry * 360, rz * 360);
			}
			genIndex++;
		}
		
		//---------------RESET GOAL-----------------//
		if (Math.random() < 0.005) resetGoalCube();
		
		//---SCATTER: RANDOM RESTART---//
		//if (Math.random() < 0.005) this.initPopulation();
		
	}

	private void resetGoalCube() {
		double x = Math.random();
		double y = Math.random();
		double z = Math.random();
		double r = Math.random();
		double g = Math.random();
		double b = Math.random();
		double scaleX = Math.random();
		double scaleY = Math.random();
		double scaleZ = Math.random();
		//double rx = Math.random();
		//double ry = Math.random();
		//double rz = Math.random();
		//this.options.fitnessObj.setGoal(new double[]{x, y, z, r, g, b, scaleX, scaleY, scaleZ, rx, ry, rz});
		this.options.fitnessObj.setGoal(new double[]{x, y, z, r, g, b, scaleX, scaleY, scaleZ});
		//this.options.fitnessObj.setGoal(new double[]{x, y, z, r, g, b});
		//System.out.println("Goal state set to: " + x + ", " + y + ", " + z);
	
		this.goalCube.translate((x - 0.5) * 1000, (y - 0.5) * 1000, (z - 0.5) * 1000);
		this.goalCube.setColor(r, g, b);
		this.goalCube.setScale(scaleX * this.sizeMult, scaleY * this.sizeMult, scaleZ * this.sizeMult);
		//this.goalCube.setRotate(rx * 360, ry * 360, rz * 360);
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
		double x = Math.random();
		double y = Math.random();
		double z = Math.random();
		double r = Math.random();
		double g = Math.random();
		double b = Math.random();
		double scaleX = Math.random();
		double scaleY = Math.random();
		double scaleZ = Math.random();
		double rx = Math.random();
		double ry = Math.random();
		double rz = Math.random();
		//this.options.fitnessObj.setGoal(new double[]{x, y, z, r, g, b, scaleX, scaleY, scaleZ, rx, ry, rz});
		this.options.fitnessObj.setGoal(new double[]{x, y, z, r, g, b, scaleX, scaleY, scaleZ});
		
		this.goalCube = new Cube(60, 60, 60);
		this.goalCube.translate(x * 500, y * 500, z * 500);
		this.goalCube.setColor(r, g, b);
		this.goalCube.setRotate(rx, ry, rz);
		
		//this.boundryGroup.getChildren().add(this.goalCube.getBox());
	}

	//---BUILDS CUBE SKELETON FOR VISUAL SPATIAL REFERENCE---//
	private void buildBoundries() {
		double lineWidth = 1.0;
		ArrayList<Cube> bounds = new ArrayList<Cube>();

		for (int i = 0; i < 4; i++)
			bounds.add(new Cube(size * 2, lineWidth, lineWidth, Color.BLACK, Color.BLACK));
		for (int i = 0; i < 4; i++)
			bounds.add(new Cube(lineWidth, size * 2, lineWidth, Color.BLACK, Color.BLACK));
		for (int i = 0; i < 4; i++)
			bounds.add(new Cube(lineWidth, lineWidth, size * 2, Color.BLACK, Color.BLACK));

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
		for(Population gen : generations) {
			for (Individual individual : gen.getIndividuals()) {
				Color color = new Color(0.6, 0.2, 0.1, 1);
				Cube box = new Cube(this.particleSize, this.particleSize, this.particleSize, color, color);
				
				double normalX = individual.getGene(0) / (this.goal * 1.0);
				double normalY = individual.getGene(1) / (this.goal * 1.0);
				double normalZ = individual.getGene(2) / (this.goal * 1.0);
				int x = (int) Math.floor(500 * normalX);
				int y = (int) Math.floor(500 * normalY);
				int z = (int) Math.floor(500 * normalZ);
				
				box.translate(x, y, z);
				particles.add(box);
			}
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
	
	@Override
	public String toString () {
		return "basic3D";
	}
	
	public void setFullscreen (boolean isFullscreen, double w, double h) {
		if (isFullscreen) {
			//this.scene.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
			//this.scene.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
			this.scene.setWidth(w);
			this.scene.setHeight(h);
		}
		else {
			this.scene.setWidth(width);
			this.scene.setHeight(height);
		}
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
			double modifierFactor = 0.2;

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