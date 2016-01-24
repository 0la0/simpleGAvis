package javafxDriver;

import gaViz.main.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.animation.AnimationTimer;
import javafx.scene.AmbientLight;
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
	//private Group boundryGroup = new Group();
	private Xform world = new Xform();
	private PerspectiveCamera camera = new PerspectiveCamera(true);
	private Xform cameraXform = new Xform();
	private Xform cameraXform2 = new Xform();
	private Xform cameraXform3 = new Xform();
	private double cameraDistance = 1500; //2500

	private Xform particleGroup = new Xform();
	private ArrayList<Cube> particles = new ArrayList<Cube>();
	
	private SubScene scene;
	private int particleSize = 20;
	
	private double mousePosX;
	private double mousePosY;
	private double mouseOldX;
	private double mouseOldY;
	private double mouseDeltaX;
	private double mouseDeltaY;
	
	private GaConfigOptions options;
	private GaGenerator gaGenerator;
	private int goal;
	private long lastTime;
	private int sizeMult = 4;
	private int width = 900;
	private int height = 675;
	
	public Basic3dDriver (GaConfigOptions options, GaGenerator gaGenerator) {
		this.options = options;
		this.gaGenerator = gaGenerator;
		this.goal = gaGenerator.getGoalSize();
		this.resetGoal();
		this.buildParticles();
		
		//---UI SETUP---//
		root.getChildren().add(world);
		this.buildCamera();
		this.scene = new SubScene(root, width, height, true, SceneAntialiasing.BALANCED);
		this.scene.setFill(Color.color(0, 0, 0));
		this.scene.setCamera(camera);
		this.handleMouse();
		
		//---ADD LIGHTING---//
//		AmbientLight light = new AmbientLight();
//	    light.setColor(Color.WHITE);
//	    Group lightGroup = new Group();
//	    lightGroup.getChildren().add(light);
//	    root.getChildren().add(lightGroup);

		//---SET UP AND START TIMER---//
		this.lastTime = System.nanoTime();
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				float elapsedTime = (float) ((now - lastTime) / 1000000.0);
				lastTime = now;
				//TODO: figure out javaFx timing!
				//if (cnt++ % 2 == 0) {
					gaGenerator.createNewPopulation();
					generate(elapsedTime);
					animateCamera(elapsedTime);
				//}
			}
		};
		timer.start();
	}
	
	private void generate (float elapsedTime) {
		
		AtomicInteger indexCnt = new AtomicInteger(0);
		gaGenerator.getGenerations().getPopulations().forEach(population -> {
			
			population.getIndividuals().forEach(individual -> {
				Cube cube = particles.get(indexCnt.getAndIncrement());
				renderIndividual(individual, cube);
			});
			
		});
		
		if (Math.random() < 0.005) resetGoal();
	}
	
	private void renderIndividual (Individual individual, Cube cube) {
		double[] normalValues = individual.getGenome().stream()
				.mapToDouble(gene ->  gene / (goal * 1.0))
				.toArray();

		int x = (int) Math.floor(1000 * (normalValues[0] - 0.5));
		int y = (int) Math.floor(1000 * (normalValues[1] - 0.5));
		int z = (int) Math.floor(1000 * (normalValues[2] - 0.5));

		cube.translate(x, y, z);
		// opacity based on age, the dying are dissipating
		//double opacity = ((double) generationsCount - genIndex) / generationsCount;
		cube.setColor(normalValues[3], normalValues[4], normalValues[5]);
		//cube.setColor(normalValues[3], normalValues[4], normalValues[5], opacity);
		cube.setScale(normalValues[6] * this.sizeMult, normalValues[7] * this.sizeMult, normalValues[8] * this.sizeMult);
		//cube.setRotate(normalValues[9] * 360, normalValues[10] * 360, normalValues[11] * 360);
	}

	private void resetGoal () {
		double[] newGoal = new Random().doubles(this.options.numGenes).toArray();
		this.options.fitnessObj.setGoal(newGoal);
	}
	
	private void animateCamera (float elapsedTime) {
		//this.totTime += elapsedTime * 0.0001;
		//this.cameraDistance = 1500 + (2000 * Math.sin(this.totTime));
		//this.camera.setTranslateZ(-this.cameraDistance);
		this.cameraXform.ry.setAngle(cameraXform.ry.getAngle() + elapsedTime * 0.01);
		this.cameraXform.rx.setAngle(cameraXform.rx.getAngle() + elapsedTime * 0.01);  
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

	private void buildParticles () {
		int numParticles = options.populationSize * options.numGenerations;
		for (int i = 0; i < numParticles; i++) {
			Color color = new Color(0.6, 0.2, 0.1, 1);
			Cube box = new Cube(this.particleSize, this.particleSize, this.particleSize, color, color);
			int x = (int) Math.floor(500 * Math.random());
			int y = (int) Math.floor(500 * Math.random());
			int z = (int) Math.floor(500 * Math.random());
			box.translate(x, y, z);
			particles.add(box);
		}
		particles.forEach(particle -> {
			particleGroup.getChildren().add(particle.getBox());
		});
		this.world.getChildren().addAll(this.particleGroup);
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