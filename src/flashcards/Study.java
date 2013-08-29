package flashcards;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 * @author Zhishen (Jason) Wen and Eric Nida
 * @version Nov 29, 2012
 * MCIT, Penn CIS
 */
/****************************************************
 * The Study class. GUI for the StudyModel class.
 ****************************************************
 */
public class Study extends JFrame{
    /**
     ************** Launches this application. **************
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Study frame = new Study();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            }
        });
    }

    /**
     ************** Creates this application. **************
     */
    private StudyModel studyModel  = new StudyModel();
    
    public Study() {
        initializeFrame();
    }
    
    /**
     *************** Creates listener classes. ***************
     */
    /**
     * Inner class that handles events for showAnswerButton.
     */
    class ShowAnswerButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            Card currentCard = studyModel.getNextCard();
            answerTextArea.setText(currentCard.getAnswer());
            setDifficultyLevelButtonEnabled(true);
            showAnswerButton.setEnabled(false);
        }
    }
    
    /**
     * Inner class that handles events for saveButton.
     */
    class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            if (studyModel.save())
                JOptionPane.showMessageDialog(
                        null, 
                        "Save data file successfully! You progress is now saved.", 
                        "Output Successful", 
                        JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(
                        null, 
                        "Failed to write to data file! Please retry.", 
                        "Output Failed", 
                        JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Inner class that handles events for exitButton.
     */
    class ExitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            if (studyModel.getGoal() >= studyModel.getTime()) {
                int yesNo = JOptionPane.showConfirmDialog(
                        null, 
                        "You have not reached your goal, quit anyway?\n(your" +
                        " progress will be saved automatically)", 
                        "Exit", 
                        JOptionPane.OK_CANCEL_OPTION);
                if (yesNo == JOptionPane.YES_OPTION) {
                    studyModel.save();
                    System.exit(0);
                }
            }
            else System.exit(0);
        }
    }
    
    /**
     * Inner class that handles events for easyButton.
     */
    class EasyButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            try {
                studyModel.setEasy();
                setDifficultyLevelButtonEnabled(false);
                saveButton.setEnabled(true);
                nextCardButton.setEnabled(true);
                showAnswerButton.setEnabled(false);
            }
            catch (IndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(
                        null, 
                        "Interval exceeds card deck size.", 
                        "Error", 
                        JOptionPane.INFORMATION_MESSAGE);
                nextCardButton.setEnabled(true);
            }
        }
    }
    
    /**
     * Inner class that handles events for moderateButton.
     */
    class ModerateButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            try {
                studyModel.setModerate();
                setDifficultyLevelButtonEnabled(false);
                saveButton.setEnabled(true);
                nextCardButton.setEnabled(true);
                showAnswerButton.setEnabled(false);
            }
            catch (IndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(
                        null, 
                        "Interval exceeds card deck size.", 
                        "Error", 
                        JOptionPane.INFORMATION_MESSAGE);
                nextCardButton.setEnabled(true);
            }
        }
    }
    
    /**
     * Inner class that handles events for difficultButton.
     */
    class DifficultButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            try {
                studyModel.setDifficult();            
                setDifficultyLevelButtonEnabled(false);
                saveButton.setEnabled(true);
                nextCardButton.setEnabled(true);
                showAnswerButton.setEnabled(false);
            }
            catch (IndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(
                        null, 
                        "Interval exceeds card deck size.", 
                        "Error", 
                        JOptionPane.INFORMATION_MESSAGE);
                nextCardButton.setEnabled(true);
            }
        }
    }
    
    /**
     * Inner class that handles events for nextCardButton.
     */
    class NextCardButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            studyModel.advance();
            nextCardButton.setEnabled(false);
            showAnswerButton.setEnabled(true);
            cardsStudiedTextField.setText(Integer.toString(studyModel.getTime()));
            cardsRemainedTextField.setText(
                    Integer.toString(studyModel.getGoal() - studyModel.getTime()));
            if (studyModel.getGoal() <= studyModel.getTime()) {
                int yesNo = JOptionPane.showConfirmDialog(
                        null, 
                        "Your goal has been reached. Continue?\nClick" +
                        " 'Yes' to continue, 'No' to quit.", 
                        "Congratulations!", 
                        JOptionPane.YES_NO_OPTION);
                if (yesNo == JOptionPane.YES_OPTION) {
                    studyModel  = new StudyModel();
                    setGoalButton.setEnabled(true);
                    setTextFieldEnabled(false);
                    setButtonEnabled(false);
                }
                if (yesNo == JOptionPane.NO_OPTION) System.exit(0);
            }
            Card currentCard = studyModel.getNextCard();
            questionTextArea.setText(currentCard.getQuestion());
            answerTextArea.setText("Click 'Show Answer' to see.");
            studyModel.save();
        }
    }
    
    /**
     * Inner class that handles events for setGoalButton.
     */
    class SetGoalButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            try {
                int goal = Integer.parseInt(
                        JOptionPane.showInputDialog("How many cards " +
                        		"would you like to study?").trim());
                if (goal <= 0 || goal > studyModel.getCardDeckSize())
                    throw new IndexOutOfBoundsException();
                studyModel.setGoal(goal);
                cardsStudiedTextField.setText(Integer.toString(
                        studyModel.getTime()));
                cardsRemainedTextField.setText(Integer.toString(
                        studyModel.getGoal() - studyModel.getTime()));
                setTextFieldEnabled(true);
                questionTextArea.setText(studyModel.getNextCard().getQuestion());
                answerTextArea.setText("Click 'Show Answer' button to see.");
                showAnswerButton.setEnabled(true);
                setGoalButton.setEnabled(false);
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        null, 
                        "Illegal characters found! Please retry.", 
                        "Invalid Input", 
                        JOptionPane.ERROR_MESSAGE);
            }
            catch (NullPointerException e) {
                JOptionPane.showMessageDialog(
                        null, 
                        "Please enter your goal in order to begin!", 
                        "Warning", 
                        JOptionPane.INFORMATION_MESSAGE);
            }
            catch (IndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(
                        null, 
                        "Invalid number! Please retry.", 
                        "Invalid Input", 
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Inner class that handles events for readInButton.
     */
    class ReadInButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            if (studyModel.read() == 1) {
                JOptionPane.showMessageDialog(
                        null, 
                        "Read data file successfully! You can now edit the file.", 
                        "Successful Read-in", 
                        JOptionPane.INFORMATION_MESSAGE);
                readInButton.setEnabled(false);
                readOtherButton.setEnabled(false);
                setGoalButton.setEnabled(true);
                questionTextArea.setText("Please click 'Set Your Goal' button.");
            }
            else if (studyModel.read() == 0) {
                JOptionPane.showMessageDialog(
                        null, 
                        "Data file is NOT found or is BLANK! Please check" +
                        " file location.", 
                        "Initialization Error", 
                        JOptionPane.ERROR_MESSAGE);
            }
            else 
                JOptionPane.showMessageDialog(
                        null, 
                        "Unable to read in the data file! Please retry.", 
                        "ERROR", 
                        JOptionPane.ERROR_MESSAGE);
            }
    }
    
    /**
     * Inner class that handles events for readOtherButton.
     */
    class ReadOtherButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            try {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Select File to Read in:");
                int result = chooser.showOpenDialog(getContentPane());
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    studyModel.createCardDeck(file);
                    readInButton.setEnabled(false);
                    readOtherButton.setEnabled(false);
                    setGoalButton.setEnabled(true);
                    questionTextArea.setText("Please click 'Set Your Goal' button.");
                }            
            }
            catch (ArrayIndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(
                        null, 
                        "Invalid data file format! Please retry.", 
                        "ERROR", 
                        JOptionPane.ERROR_MESSAGE);
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        null, 
                        "Invalid data file format! Please retry.", 
                        "ERROR", 
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     ************** Initializes the contents of this frame. *************
     */
    private void initializeFrame() {
        // configure this frame
        setBounds(100, 100, 500, 500);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Study Flashcards");
        getContentPane().setLayout(new GridLayout(6, 1));
        
        // set panel properties
        // panel 1
        JPanel p1 = new JPanel();
        JPanel p1Inner = new JPanel();
        JPanel p1Inner2 = new JPanel();
        JPanel p1Inner3 = new JPanel();
        p1.setLayout(new GridLayout(1, 3));
        p1Inner.setLayout(new FlowLayout());
        p1Inner2.setLayout(new BorderLayout());
        p1Inner3.setLayout(new BorderLayout());
        p1.add(p1Inner2);
        p1.add(p1Inner3);
        p1.add(p1Inner);
        p1Inner.add(cardsStudiedLabel);
        p1Inner.add(cardsStudiedTextField);
        p1Inner.add(cardsRemainedLabel);
        p1Inner.add(cardsRemainedTextField);
        p1Inner2.add(readInButton, BorderLayout.CENTER);
        p1Inner3.add(readOtherButton, BorderLayout.CENTER);
        p1Inner2.add(Box.createHorizontalStrut(5), BorderLayout.EAST);
        p1Inner2.add(Box.createHorizontalStrut(10), BorderLayout.WEST);
        p1Inner2.add(Box.createVerticalStrut(20), BorderLayout.NORTH);
        p1Inner2.add(Box.createVerticalStrut(20), BorderLayout.SOUTH);
        p1Inner3.add(Box.createHorizontalStrut(10), BorderLayout.EAST);
        p1Inner3.add(Box.createHorizontalStrut(5), BorderLayout.WEST);
        p1Inner3.add(Box.createVerticalStrut(20), BorderLayout.NORTH);
        p1Inner3.add(Box.createVerticalStrut(20), BorderLayout.SOUTH);
        
        // panel 2
        JPanel p2 = new JPanel();
        p2.setLayout(new BorderLayout());
        p2.add(questionLabel, BorderLayout.NORTH);
        p2.add(questionTextArea, BorderLayout.CENTER);
        p2.add(Box.createHorizontalStrut(30), BorderLayout.WEST);
        p2.add(Box.createHorizontalStrut(30), BorderLayout.EAST);
        
        // panel 3
        JPanel p3 = new JPanel();
        p3.setLayout(new BorderLayout());
        p3.add(answerLabel, BorderLayout.NORTH);
        p3.add(answerTextArea, BorderLayout.CENTER);
        p3.add(Box.createHorizontalStrut(30), BorderLayout.WEST);
        p3.add(Box.createHorizontalStrut(30), BorderLayout.EAST);
        
        // panel 4
        JPanel p4 = new JPanel();
        JPanel p4Inner = new JPanel();
        p4.setLayout(new BorderLayout());
        p4Inner.setLayout(new FlowLayout());
        p4.add(p4Inner, BorderLayout.CENTER);
        p4Inner.add(setGoalButton);
        p4Inner.add(showAnswerButton);
        p4Inner.add(nextCardButton);
        p4.add(Box.createVerticalStrut(15), BorderLayout.NORTH);
        
        // panel 5
        JPanel p5 = new JPanel();
        JPanel p5Inner = new JPanel();
        p5.setLayout(new BorderLayout());
        p5Inner.setLayout(new FlowLayout());
        p5.add(p5Inner, BorderLayout.CENTER);
        p5Inner.add(easyButton);
        p5Inner.add(moderateButton);
        p5Inner.add(difficultButton);
        p5Inner.setBorder(new TitledBorder(
                UIManager.getBorder("TitledBorder.border"), 
                "Level of difficulty", 
                TitledBorder.CENTER, 
                TitledBorder.TOP)); // set panel title
        p5.add(Box.createHorizontalStrut(20), BorderLayout.WEST);
        p5.add(Box.createHorizontalStrut(20), BorderLayout.EAST);
        
        // panel 6
        JPanel p6 = new JPanel();
        JPanel p6Inner = new JPanel();
        p6.setLayout(new BorderLayout());
        p6Inner.setLayout(new FlowLayout());
        p6.add(p6Inner, BorderLayout.EAST);
        p6Inner.add(saveButton);
        p6Inner.add(exitButton);
        p6.add(Box.createVerticalStrut(40), BorderLayout.NORTH);
        
        // add these six panels to this frame
        getContentPane().add(p1);
        getContentPane().add(p2);
        getContentPane().add(p3);
        getContentPane().add(p4);
        getContentPane().add(p5);
        getContentPane().add(p6);

        // set label properties
        answerLabel.setLabelFor(answerTextArea);
        questionLabel.setLabelFor(questionTextArea);
        cardsRemainedLabel.setLabelFor(cardsRemainedTextField);
        cardsStudiedLabel.setLabelFor(cardsStudiedTextField);
        cardsRemainedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cardsStudiedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // attach listeners to each component
        showAnswerButton.addActionListener(new ShowAnswerButtonListener());
        saveButton.addActionListener(new SaveButtonListener());
        exitButton.addActionListener(new ExitButtonListener());
        easyButton.addActionListener(new EasyButtonListener());
        moderateButton.addActionListener(new ModerateButtonListener());
        difficultButton.addActionListener(new DifficultButtonListener());
        nextCardButton.addActionListener(new NextCardButtonListener());
        setGoalButton.addActionListener(new SetGoalButtonListener());
        readInButton.addActionListener(new ReadInButtonListener());
        readOtherButton.addActionListener(new ReadOtherButtonListener());
        
        // set keyboard equivalents for each button
        setGoalButton.setMnemonic('g');
        showAnswerButton.setMnemonic('a');
        nextCardButton.setMnemonic('n');
        easyButton.setMnemonic('e');
        moderateButton.setMnemonic('m');
        difficultButton.setMnemonic('d');
        saveButton.setMnemonic('s');
        exitButton.setMnemonic('e');
        readInButton.setMnemonic('l');
        readOtherButton.setMnemonic('f');
        
        // set color and font themes
        // panel 1
        p1.setBackground(new Color(230, 230, 250));
        p1Inner.setBackground(new Color(230, 230, 250));
        p1Inner2.setBackground(new Color(230, 230, 250));
        p1Inner3.setBackground(new Color(230, 230, 250));
        readInButton.setBackground(new Color(152, 251, 152));
        readOtherButton.setBackground(new Color(152, 251, 152));
        cardsStudiedTextField.setBackground(new Color(255, 240, 245));
        cardsStudiedTextField.setForeground(new Color(0, 0, 205));
        cardsRemainedTextField.setBackground(new Color(255, 240, 245));
        cardsRemainedTextField.setForeground(new Color(255, 0, 0));
        // panel 2
        p2.setBackground(new Color(210, 255, 215));
        questionTextArea.setForeground(new Color(0, 0, 205));
        questionTextArea.setBackground(new Color(255, 255, 204));
        questionTextArea.setFont(new Font("Consolas", Font.PLAIN, 17));
        // panel 3
        p3.setBackground(new Color(210, 255, 215));
        answerTextArea.setBackground(new Color(255, 255, 204));
        answerTextArea.setForeground(new Color(255, 69, 0));
        answerTextArea.setFont(new Font("Consolas", Font.PLAIN, 17));
        // panel 4
        p4.setBackground(new Color(210, 255, 215));
        p4Inner.setBackground(new Color(210, 255, 215));
        showAnswerButton.setBackground(new Color(255, 222, 173));
        setGoalButton.setBackground(new Color(240, 230, 140));
        nextCardButton.setBackground(new Color(255, 160, 122));
        // panel 5
        p5.setBackground(new Color(210, 255, 215));
        p5Inner.setBackground(new Color(210, 255, 215));
        easyButton.setBackground(new Color(222, 184, 135));
        moderateButton.setBackground(new Color(233, 150, 122));
        difficultButton.setBackground(new Color(205, 92, 92));
        // panel 6
        p6.setBackground(new Color(210, 255, 215));
        p6Inner.setBackground(new Color(255, 182, 193));
        saveButton.setForeground(new Color(255, 255, 0));
        saveButton.setBackground(new Color(138, 43, 226));
        exitButton.setForeground(new Color(255, 255, 0));
        exitButton.setBackground(new Color(218, 112, 214));
        
        // set initial component visibility
        setTextFieldEditable(false);
        setTextFieldEnabled(false);
        setButtonEnabled(false);
        setGoalButton.setEnabled(false);
    }
    
    /** 
     ******************* Helpers *******************
     */
    /**
     * Controls the editability of text field components
     * @param b Boolean that is passed to make components
     * editable/uneditable.
     */
    private void setTextFieldEditable(boolean b) {
        cardsStudiedTextField.setEditable(b);
        cardsRemainedTextField.setEditable(b);
        questionTextArea.setEditable(b);
        answerTextArea.setEditable(b);
    }
    
    /**
     * Controls the visibility of text field components
     * @param b Boolean that is passed to enable/disable
     * components
     */
    private void setTextFieldEnabled(boolean b) {
        cardsStudiedTextField.setEnabled(b);
        cardsRemainedTextField.setEnabled(b);
        questionTextArea.setEnabled(b);
        answerTextArea.setEnabled(b);
    }
    
    /**
     * Controls the visibility of button components
     * @param b Boolean that is passed to enable/disable
     * components
     */
    private void setButtonEnabled(boolean b) {
        showAnswerButton.setEnabled(false);
        nextCardButton.setEnabled(false);
        saveButton.setEnabled(false);
        setDifficultyLevelButtonEnabled(false);
    }
    
    /**
     * Controls the visibility of difficulty level buttons
     * @param b Boolean that is passed to enable/disable
     * components
     */
    private void setDifficultyLevelButtonEnabled(boolean b) {
        easyButton.setEnabled(b);
        moderateButton.setEnabled(b);
        difficultButton.setEnabled(b);
    }
    
    /**
     ***************** Declare and initialize components. *****************
     */
    private JLabel cardsStudiedLabel = new JLabel("Cards studied: ");
    private JLabel cardsRemainedLabel = new JLabel("Cards remaining: ");
    private JLabel questionLabel = new JLabel("     Question: ");
    private JLabel answerLabel = new JLabel("     Answer: ");
    private JTextField cardsStudiedTextField = new JTextField("##");
    private JTextField cardsRemainedTextField = new JTextField("##");
    private JTextArea questionTextArea = new JTextArea("Please click 'Read Data File' button to begin.");
    private JTextArea answerTextArea = new JTextArea("");
    private JButton setGoalButton = new JButton("Set Your Goal!");
    private JButton nextCardButton = new JButton("Next Card");
    private JButton showAnswerButton = new JButton("Show Answer");
    private JButton easyButton = new JButton("Easy");
    private JButton moderateButton = new JButton("Moderate");
    private JButton difficultButton = new JButton("Difficult");
    private JButton saveButton = new JButton("Save");
    private JButton exitButton = new JButton("Exit");
    private JButton readInButton = new JButton("Load Last Progress");
    private JButton readOtherButton = new JButton("Load From...");
}
