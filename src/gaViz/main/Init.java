package gaViz.main;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import gaViz.crossover.*;
import gaViz.fitness.*;
import gaViz.print.*;
import gaViz.probability.ProbabilityStandard;
import gaViz.breed.BreedStandard;
import gaViz.mutate.MutateStandard;

public class Init {
	
	private JPanel mainPanel = new JPanel();
	private JPanel mainRadioPanel;
	private JPanel printRadioPanel;
	private JPanel fitnessPanel;
	
	public Init () { 
		int w = 300;
		int h = 400;
		this.buildUserInterface();
		
		JFrame imgFrame = new JFrame();
		imgFrame.add(this.mainPanel);
		imgFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		imgFrame.setSize(w, h);
		imgFrame.pack();
		imgFrame.setLocation(10, 10);
		imgFrame.setVisible(true);
	}
	
	private void buildUserInterface () {
		
		//==========DRIVER RADIO BUTTONS============//
		final MainButtonListener buttonListener = new MainButtonListener();
		buttonListener.setSelected("print");
		
		JRadioButton printButton = new JRadioButton("print");
        printButton.setActionCommand("print");
        printButton.setSelected(true);
        printButton.addActionListener(buttonListener);
        
        JRadioButton imageButton = new JRadioButton("image");
        imageButton.setActionCommand("image");
        imageButton.setSelected(true);
        imageButton.addActionListener(buttonListener);
        
        JRadioButton animationButton = new JRadioButton("animation");
        animationButton.setActionCommand("animation");
        animationButton.setSelected(true);
        animationButton.addActionListener(buttonListener);
 
        ButtonGroup mainButtonGroup = new ButtonGroup();
        mainButtonGroup.add(printButton);
        mainButtonGroup.add(imageButton);
        mainButtonGroup.add(animationButton);
        
        mainRadioPanel = new JPanel(new GridLayout(0, 1));
        mainRadioPanel.add(printButton);
        mainRadioPanel.add(imageButton);
        mainRadioPanel.add(animationButton);
		TitledBorder mainTitle = BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(Color.black),
			"Representation"
		);
		mainTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
		mainRadioPanel.setBorder(mainTitle);
		this.mainPanel.add(mainRadioPanel);
		
		
		//==========PRINT OPTIONS===========//
		final MainButtonListener printButtonListener = new MainButtonListener();
		printButtonListener.setSelected("numeric");
		
		JRadioButton numericButton = new JRadioButton("numeric");
		numericButton.setActionCommand("numeric");
		numericButton.setSelected(true);
		numericButton.addActionListener(printButtonListener);
        
        JRadioButton binaryButton = new JRadioButton("binary");
        binaryButton.setActionCommand("binary");
        binaryButton.setSelected(true);
        binaryButton.addActionListener(printButtonListener);
        
        ButtonGroup printButtonGroup = new ButtonGroup();
        printButtonGroup.add(numericButton);
        printButtonGroup.add(binaryButton);
        
        printRadioPanel = new JPanel(new GridLayout(0, 1));
        printRadioPanel.add(numericButton);
        printRadioPanel.add(binaryButton);
		TitledBorder printTitle = BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(Color.black),
			"Print Options"
		);
		printTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
		printRadioPanel.setBorder(printTitle);
		this.mainPanel.add(printRadioPanel);
		
		
		
		//==========BASIC OPTIONS============//
		final JFormattedTextField numGenes = new JFormattedTextField(
        	NumberFormat.getNumberInstance()
        );
        numGenes.setValue(2);
        JLabel _numGenes = new JLabel("Number of Genes: ");
        
        final JFormattedTextField geneLength = new JFormattedTextField(
        	NumberFormat.getNumberInstance()
        );
        geneLength.setValue(8);
        JLabel _geneLength = new JLabel("Binary Gene Length: ");
		
        final JFormattedTextField populationSize = new JFormattedTextField(
        	NumberFormat.getNumberInstance()
    	);
    	populationSize.setValue(100);
    	JLabel _populationSize = new JLabel("Population Size: ");
    	
    	final JFormattedTextField numGenerations = new JFormattedTextField(
            NumberFormat.getNumberInstance()
        );
        numGenerations.setValue(10);
        JLabel _numGenerations = new JLabel("Num Generations: ");
        
        final JTextField mutateThresh = new JTextField();
       // mutateThresh.setName("0.0005");
        mutateThresh.setText("0.0005");
        JLabel _mutateThresh = new JLabel("Mutate Thresh: ");
        
        final JComboBox crossover = new JComboBox(new String[]{
        	"per gene", "across genes"
        });
        JLabel _crossover = new JLabel("crossover: ");
        
    	
    	JPanel optionsPanel = new JPanel(new GridLayout(6, 2));
        
        optionsPanel.add(_numGenes);
        optionsPanel.add(numGenes);
        optionsPanel.add(_geneLength);
        optionsPanel.add(geneLength);
        optionsPanel.add(_populationSize);
        optionsPanel.add(populationSize);
        optionsPanel.add(_numGenerations);
        optionsPanel.add(numGenerations);
        optionsPanel.add(_mutateThresh);
        optionsPanel.add(mutateThresh);
        optionsPanel.add(_crossover);
        optionsPanel.add(crossover);
        
        TitledBorder optionsBorder = BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(Color.black),
			"Basic Options"
		);
		optionsBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
		optionsPanel.setBorder(optionsBorder);
		this.mainPanel.add(optionsPanel);
		
		
		//==========FITNESS FUNCTION============//
		final MainButtonListener fitnessButtonListener = new MainButtonListener();
		fitnessButtonListener.setSelected("ones");
		
		JRadioButton numOnes = new JRadioButton("num ones");
		numOnes.setActionCommand("ones");
		numOnes.setSelected(true);
		numOnes.addActionListener(fitnessButtonListener);
		
		JRadioButton maxSquared = new JRadioButton("max squared");
		maxSquared.setActionCommand("squared");
		maxSquared.setSelected(true);
		maxSquared.addActionListener(fitnessButtonListener);
		        
		JRadioButton customSquared = new JRadioButton("custom squared");
		customSquared.setActionCommand("custom");
		customSquared.setSelected(true);
		customSquared.addActionListener(fitnessButtonListener);
		
		final JFormattedTextField geneFields[] = new JFormattedTextField[5];
		final JLabel[] geneLabels = new JLabel[5];
		
		for (int i = 0; i < geneFields.length; i++) {
			geneFields[i] = new JFormattedTextField(NumberFormat.getNumberInstance());
			geneFields[i].setValue(i * (1 / (geneFields.length * 1.0) ));
			geneLabels[i] = new JLabel("gene " + i + ": ");
		}
		
		ButtonGroup fitnessButtonGroup = new ButtonGroup();
		fitnessButtonGroup.add(numOnes);
		fitnessButtonGroup.add(maxSquared);
		fitnessButtonGroup.add(customSquared);
		 
		fitnessPanel = new JPanel(new GridLayout(9, 2));
		fitnessPanel.add(numOnes);
		fitnessPanel.add(maxSquared);
		fitnessPanel.add(customSquared);
		fitnessPanel.add(new JLabel());
		for (int i = 0; i < geneLabels.length; i++) {
			fitnessPanel.add(geneLabels[i]);
			fitnessPanel.add(geneFields[i]);
		}
		TitledBorder fitnessTitle = BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(Color.black),
			"Fitness Function"
		);
		fitnessTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
		fitnessPanel.setBorder(fitnessTitle);
		this.mainPanel.add(fitnessPanel);	
		
			
		//==========MAIN BUTTON============//	
		JButton goButton = new JButton("--go--");
		goButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				double[] geneGoals = new double[Integer.parseInt(numGenes.getText())];
				for (int i = 0; i < geneGoals.length; i++) {
					geneGoals[i] = Float.parseFloat(geneFields[i].getText());
				}
				try {
					buildOptions(
							buttonListener.getSelected(), 
							Integer.parseInt(numGenes.getText()), 
							Integer.parseInt(geneLength.getText()),
							NumberFormat.getNumberInstance(java.util.Locale.US)
								.parse(populationSize.getText()).intValue(),
							Integer.parseInt(numGenerations.getText()),
							Float.parseFloat(mutateThresh.getText()),
							crossover.getSelectedItem().toString(),
							fitnessButtonListener.getSelected(),
							geneGoals,
							printButtonListener.getSelected()
					);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		this.mainPanel.add(goButton);
		
		//-----numGenes listener----//
		numGenes.addPropertyChangeListener(new PropertyChangeListener(){
			int oldValue = 2;
			public void propertyChange(PropertyChangeEvent arg0) {
				try {
					int newValue = Integer.parseInt(numGenes.getText());
					if (newValue != oldValue) {
						oldValue = newValue;
					}
					if (newValue < 0) {
						return;
					}
					for (int i = 4; i >= 0; i--) {
						if (i > newValue - 1) {
							geneLabels[i].setEnabled(false);
							geneFields[i].setEnabled(false);
						} else {
							geneLabels[i].setEnabled(true);
							geneFields[i].setEnabled(true);
						}
					}
				}
				catch (NumberFormatException e) {}
			}
		});
	}
	
	private void buildOptions (String mainType, int numGenes, int geneLength, int populationSize, int numGenerations, float mutateThresh, String crossoverType, String fitnessType, double[] geneGoals, String printOption) {
		BinaryStringHelper.setStringLength(geneLength);
		
		Options options = new Options();
		options.numGenes = numGenes;
		if (populationSize % 2 != 0) populationSize++;
		options.populationSize = populationSize;
		options.numGenerations = numGenerations;
		options.geneLength = geneLength;
		options.mutateObj = new MutateStandard(mutateThresh);
		options.breederObj = new BreedStandard();
		options.probabilityObj = new ProbabilityStandard();
		
		if (crossoverType.equals("per gene")) {
			options.crossoverObj = new CrossoverGenenome();
		}
		else if (crossoverType.equals("across genes")) {
			options.crossoverObj = new CrossoverOverall();
		}
		
		if (fitnessType.equals("ones")) {
			options.fitnessObj = new FitnessNumOnes();
		}
		else if (fitnessType.equals("squared")) {
			options.fitnessObj = new FitnessSquared();
		}
		else if (fitnessType.equals("custom")) {
			options.fitnessObj = new FitnessCustomGoal(geneGoals);
		}
		
		// ---INIT REPRESENTATION---//
		if (mainType.equals("print")) {
			IPrint printObj;
			if (printOption.equals("binary")) {
				printObj = new PrintBinaryStrings();
			}
			else {
				printObj = new PrintNumericRepresentation();
			}
			new StdOutDriver(options, printObj);
		}
		else if (mainType.equals("image")) {
			new StaticImageDriver(options);
		}
		else if (mainType.equals("animation")) {
			new AnimationDriver(options);
		}
	}
	
	private class MainButtonListener implements ActionListener{
		
		private String selected = "print";
	    
		public void actionPerformed(ActionEvent arg0) {
			this.selected = arg0.getActionCommand();
	    }
		
		public String getSelected () {
			return this.selected;
		}
		
		public void setSelected (String selected) {
			this.selected = selected;
		}

	}
	
	public static void main (String[] args) {
		new Init();
	}
	
}


