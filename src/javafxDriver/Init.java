package javafxDriver;

import gaViz.breed.BreedStandard;
import gaViz.crossover.CrossoverGenenome;
import gaViz.crossover.CrossoverOverall;
import gaViz.fitness.FitnessCustomGoal;
import gaViz.fitness.FitnessNumOnes;
import gaViz.fitness.FitnessSquared;
import gaViz.main.*;
import gaViz.mutate.MutateStandard;
import gaViz.probability.ProbabilityStandard;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Init extends Application {
	
	private VBox mainGraphicsPane = new VBox();
	private Basic3dDriver driverThree = null;
	private boolean isFullscreen = false;
			
	@Override
	public void start (Stage stage) {
		Group root = new Group();
		Scene scene = new Scene(root, 904, 680, Color.WHITE);
		stage.setScene(scene);
		stage.setTitle("Genetic Algorithm 3D Visualization");
		
		double[] initialGoalState = new double[]{0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f};
		
		Options options = new Options();
		options.numGenes = 9;
		options.populationSize = 1000; //---MUST BE EVEN
		options.geneLength = 8;
		options.mutateObj = new MutateStandard(0.0005f);
		options.breederObj = new BreedStandard();
		options.probabilityObj = new ProbabilityStandard();
		options.crossoverObj = new CrossoverGenenome();
		options.fitnessObj = new FitnessCustomGoal(initialGoalState);
		
		driverThree = new Basic3dDriver(options);
		mainGraphicsPane.getChildren().add(driverThree.getUiNode());
		//mainGraphicsPane.add(driverThree.getUiNode());
		
		scene.setRoot(this.mainGraphicsPane);
		
		stage.show();
		
		scene.setOnKeyPressed((KeyEvent e) -> {
			if (e.getCode() == KeyCode.S)
				driverThree.initPopulation();
			else if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.ESCAPE) {
				isFullscreen = !isFullscreen;
				stage.setFullScreen(isFullscreen);
				
				driverThree.setFullscreen(isFullscreen, stage.getWidth(), stage.getHeight());
			}	
		});
	}

	public static void main (String[] args) {
		launch(args);
	}

}