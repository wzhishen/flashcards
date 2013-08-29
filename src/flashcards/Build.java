package flashcards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


/**
 * Build.java
 * -------------------------------------------------------------
 * Authors: Zhishen Wen and Eric Nida
 * GUI for BuildModel.  Allows users to view, edit, add and save 
 * components of flashcards.
 */

public class Build extends JFrame {

    /**
     * Launches the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Build frame = new Build();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            }
        });
    }
      
    /**
     * Creates the application.
     */
    private BuildModel buildModel  = new BuildModel();
    
    public Build() {
        createGui();
    }
    
    
    // Create listener classes.
     
    /**
     * Listener class for ReadIn JButton.  Allows user to select file to read
     * in from open file dialog and all cards contained in the file.
     * @exception ArrayIndexOutofBoundsException if file format is not valid.
     * @exception NumberFormatException if file format is not valid.
     * @exception IndexOutOfBoundsException if file format 
     * is not valid (blank).
     */
    class ReadInButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            try {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Select File to Read in:");
                int result = chooser.showOpenDialog(getContentPane());
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    buildModel.createCardDeck(file);
                    displayTextFieldContent();
                    setEditComponentsEnabled(true);
                    readInButton.setEnabled(true);
                    saveAsMenuItem.setEnabled(true);
                    writeOutButton.setEnabled(true);
                    saveAsDefaultMenuItem.setEnabled(true);
                    setPrevNextButtonVisibility(0, buildModel.getCardDeckSize() - 1);
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
            catch (IndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(
                        null, 
                        "Invalid data file format! Please retry.", 
                        "ERROR", 
                        JOptionPane.ERROR_MESSAGE);
            }  
        }
    }
    
    /**
     * Listener class for write out JButton that allows user to select
     * file save location of edited or new flashcard file.
     */
    class WriteOutButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Save File to Another Location:");
            int result = chooser.showSaveDialog(getContentPane());
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                buildModel.save(file);
            }
        }
    }
    
    /**
     * Listener class for Exit JButton to end program.
     */
    class ExitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            System.exit(0);
        }
    }
    
    /**
     * Listener class JButton that allows user to edit the
     * question of the current flashcard.
     * @exception BlankCardException if user tries to save blank question.
     * @exception DuplicateQuestionException if user tries to save question
     * that already exists in deck.
     * @exception NullPointerException if user cancels the JOptionPane.
     */
    class EditQuestionButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            try {
                String newQuestion = JOptionPane.showInputDialog(getContentPane(), 
                        "Enter new question:");
                if (newQuestion.trim().length() == 0) throw new BlankCardException();
                if (buildModel.hasSameQuestion(newQuestion)) throw new DuplicateQuestionException();
                buildModel.getCurrentCard().setQuestion(newQuestion);
                questionTextField.setText(newQuestion);
                writeOutButton.setEnabled(true);
            }
            catch (BlankCardException e) {
                JOptionPane.showMessageDialog(null, 
                                              "Blank question is NOT allowed! Please retry.", 
                                              "Invalid Input", 
                                              JOptionPane.ERROR_MESSAGE);   
            }
            catch (DuplicateQuestionException e) {
                JOptionPane.showMessageDialog(null, 
                                              "Duplicate question found! Please revise.", 
                                              "Invalid Input", 
                                              JOptionPane.ERROR_MESSAGE);
            }
            catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, 
                                              "You may edit the question next time.", 
                                              "Information", 
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    /**
     * Listener class for JButton that allows user to edit the answer of 
     * the current flashcard.
     * @exception BlankCardException if user tries to save blank answer.
     * @exception NullPointerException if user cancels the JOptionPane.
     */
    class EditAnswerButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            try {
                String newAnswer = JOptionPane.showInputDialog(getContentPane(),
                        "Enter new answer:");
                if (newAnswer.trim().length() == 0) throw new BlankCardException();
                buildModel.getCurrentCard().setAnswer(newAnswer);
                answerTextField.setText(newAnswer);
                writeOutButton.setEnabled(true);
            }
            catch (BlankCardException e) {
                JOptionPane.showMessageDialog(null, 
                                              "Blank answer is NOT allowed! Please retry.", 
                                              "Invalid Input", 
                                              JOptionPane.ERROR_MESSAGE);   
            }
            catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, 
                                              "You may edit the answer next time.", 
                                              "Information", 
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    /**
     * Listener class for JButton that allows user to select either (constant)
     * easy interval, moderate interval, or difficult interval for current
     * card.
     */
    class EditIntervalButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            Object[] options = new String[] {"Easy: " + BuildModel.getEasyInterval(), 
                                             "Moderate: " + BuildModel.getModerateInterval(), 
                                             "Difficult: " + BuildModel.getDifficultInterval()};
            int option = JOptionPane.showOptionDialog(null, 
                                                      "Set this" +
                                                      " card as:\n(the " +
                                                      "number indicates" +
                                                      " actual interval)", 
                                                      "Set interval", 
                                                      JOptionPane.YES_NO_CANCEL_OPTION, 
                                                      JOptionPane.QUESTION_MESSAGE, 
                                                      null, 
                                                      options, 
                                                      options[0]);
            if (option == JOptionPane.YES_OPTION) {
                buildModel.getCurrentCard().setInterval(BuildModel.getEasyInterval());
                intervalTextField.setText(Integer.toString(BuildModel.getEasyInterval()));
            }
            if (option == JOptionPane.NO_OPTION) {
                buildModel.getCurrentCard().setInterval(BuildModel.getModerateInterval());
                intervalTextField.setText(Integer.toString(BuildModel.getModerateInterval()));
            }
            if (option == JOptionPane.CANCEL_OPTION) {
                buildModel.getCurrentCard().setInterval(BuildModel.getDifficultInterval());
                intervalTextField.setText(Integer.toString(BuildModel.getDifficultInterval()));
            }
            writeOutButton.setEnabled(true);
        }
    }
    
    /**
     * Listener class for JButton that allows user to enter the time that
     * current flashcard will next appear.
     * @exception NumberFormatException if user enters non-integer characters.
     * @exception IndexOutOfBoundException if user enters integer that exceeds
     * size of card deck.
     */
    class EditPresentationButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            try {
                int newPresentation = Integer.parseInt(
                        JOptionPane.showInputDialog(
                                getContentPane(), 
                                "Enter new presentation time: (any positive" +
                                " integer)\nEnter 0 to put the card at the" +
                                " end of the card deck."));
                if (newPresentation < 0 || newPresentation > buildModel.getCardDeckSize())
                    throw new IndexOutOfBoundsException();
                Card newCard = buildModel.getCurrentCard();
                buildModel.removeCard(newCard);
                if (newPresentation == 0) 
                    buildModel.addCard(newCard);
                else 
                    buildModel.addCard(newCard, newPresentation);
                presentationTextField.setText(Integer.toString(buildModel.getScheduledTime(newCard)));
                writeOutButton.setEnabled(true);
            }
            catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(null, 
                                              "Illegal characters found! Please retry.", 
                                              "Invalid Input", 
                                              JOptionPane.ERROR_MESSAGE);
            }
            catch (IndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(null, 
                                              "Invalid number! Please retry.", 
                                              "Invalid Input", 
                                              JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Listener class for JButton that allows user to select card status as
     * either "virgin" or "not virgin".
     */
    class EditStatusButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            Object[] options = new String[] {"Virgin", "Not virgin"};
            int option = JOptionPane.showOptionDialog(null, 
                                                      "Set this card as:", 
                                                      "Set status", 
                                                      JOptionPane.YES_NO_OPTION, 
                                                      JOptionPane.QUESTION_MESSAGE, 
                                                      null, 
                                                      options, 
                                                      options[0]);
            if (option == JOptionPane.YES_OPTION) {
                buildModel.getCurrentCard().setStatus(true);
                statusTextField.setText("Yes");
            }
            if (option == JOptionPane.NO_OPTION) {
                buildModel.getCurrentCard().setStatus(false);
                statusTextField.setText("No");
            }
            writeOutButton.setEnabled(true);
        }
    }
    
    /**
     * Listener class for JButton that allows user to add a newly created
     * card to the deck of flashcards.  Uses addNewCard() method for user
     * to enter new card information.
     */
    class AddCardButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            int yesNo = JOptionPane.showConfirmDialog(
                    null, 
                    "Would you like to create a new card?", 
                    "Confirm", 
                    JOptionPane.YES_NO_OPTION);
            if (yesNo == JOptionPane.YES_OPTION) addNewCard();
        }
    }
    
    /**
     * Listener class for JButton that allows user to remove currently
     * selected card.  Permanently deletes card information from associated
     * file after prompting user that action cannot be reversed.
     */
    class RemoveCardButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            int yesNo = JOptionPane.showConfirmDialog(
                    null, 
                    "This action cannot be reversed. \nDo you really " +
                    "want to remove this card?", 
                    "Warning", 
                    JOptionPane.YES_NO_OPTION);
            if (yesNo == JOptionPane.YES_OPTION) {
                buildModel.removeCard(buildModel.getCurrentCard());
                buildModel.save();
                buildModel.decrementCounter();
                displayBlankTextFieldContent();
                writeOutButton.setEnabled(true);
                removeCardButton.setEnabled(false);
                setPrevNextButtonVisibility(0, buildModel.getCardDeckSize() - 1);
           }
        }
    }
    
    /**
     * Listener class for JButton that allows user to step through card deck
     * to next card.
     * @exception IndexOutOfBoundsException catches any instances where current
     * card is last card in deck (and next card does not exist).
     */
    class NextCardButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            try {
                prevCardButton.setEnabled(true);
                buildModel.save();
                buildModel.setNextCard();
                displayTextFieldContent();
                removeCardButton.setEnabled(true);
                setPrevNextButtonVisibility(0, buildModel.getCardDeckSize() - 1);
            }
            catch (IndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(null, 
                        "No Card to Select.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                nextCardButton.setEnabled(false);
                prevCardButton.setEnabled(false);
            }
        }
    }
    
    /**
     * Listener class for JButton that allows user to step through card deck
     * to previous card.
     * @exception IndexOutOfBoundsException catches any instances where current
     * card is first card in deck (and previous card does not exist).
     */
    class PrevCardButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            try {
                nextCardButton.setEnabled(true);
                buildModel.save();
                buildModel.setPreviousCard();
                displayTextFieldContent();
                removeCardButton.setEnabled(true);
                setPrevNextButtonVisibility(0, buildModel.getCardDeckSize() - 1);
            }
            catch (IndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(null, 
                        "No Card to Select.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                prevCardButton.setEnabled(false);
                nextCardButton.setEnabled(false);
            }
        }
    }
    
    /**
     * Listener for JButton that resets status of all cards in deck to
     * virgin.
     */
    class ResetButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            int yesNo = JOptionPane.showConfirmDialog(
                    null, 
                    "Reset all cards to virgin status.\nThis action cannot" +
                    " be reversed.\nDo you really want to reset these cards?", 
                    "Warning", 
                    JOptionPane.YES_NO_OPTION);
            if (yesNo == JOptionPane.YES_OPTION) {
                buildModel.setAllCardsVirgin();
                buildModel.sortCards();
                if (buildModel.save()) {
                    JOptionPane.showMessageDialog(
                            null, 
                            "Reset all cards successfully! The records have been updated.",
                            "Successful Output", 
                            JOptionPane.INFORMATION_MESSAGE);
                    setAllComponentsEnabled(false);
                    readInButton.setEnabled(true);
                    readInMenuItem.setEnabled(true);
                }
                else
                    JOptionPane.showMessageDialog(
                            null, 
                            "Failed to reset these cards! Please retry.", 
                            "ERROR", 
                            JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Listener class for JButton that allows user to find a card based on 
     * whether entered text matches either the question or answer.
     * @exception NoSuchElementException if no card question or answer matches
     * user-entered keyword.
     */
    class FindCardButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            String keyWord = JOptionPane.showInputDialog("Enter any key words:");
            try {
                Card targetCard = buildModel.findCard(keyWord);
                if (targetCard == null) throw new java.util.NoSuchElementException();
                int index = buildModel.getCardDeck().indexOf(targetCard);
                buildModel.setCurrentCard(targetCard);
                buildModel.setCounter(index);
                displayTextFieldContent();
                setPrevNextButtonVisibility(0, buildModel.getCardDeckSize() - 1);
            }
            catch(java.util.NoSuchElementException e) {
                JOptionPane.showMessageDialog(
                        null, 
                        "No card containing keyword \'" + keyWord + "\' found!", 
                        "No Result", 
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    
    /**
     * Listener class for JBUtton that creates new blank data file in the
     * default location (in the Flashcard project directory).
     */
    class CreateFileButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            int yesNo = JOptionPane.showConfirmDialog(
                    null, 
                    "Creating a new blank data file will overwrite the" +
                    " default data file.\nThis action cannot be reversed.\n" +
                    "Do you really want to create a blank data file?", 
                    "Warning", 
                    JOptionPane.YES_NO_OPTION);
            if (yesNo == JOptionPane.YES_OPTION) {
                buildModel.removeAllCards();
                buildModel.save();
                setEditComponentsEnabled(true);
                readInButton.setEnabled(true);
                saveAsMenuItem.setEnabled(true);
                saveAsDefaultMenuItem.setEnabled(true);
                setPrevNextButtonVisibility(0, buildModel.getCardDeckSize() - 1);
                displayBlankTextFieldContent();
                setEditButtonsEnabled(false);
            }
        }
    }
    
    /**
     * Listener class for JMenuItem that quits the build program.
     */
    class QuitMenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            System.exit(0);
        }
    }
    
    /**
     * Listener class for "Read File" JMenuItem that allows user to select
     * file to read in from open file dialog and all cards contained in the
     * file.
     * @exception ArrayIndexOutofBoundsException if file format is not valid.
     * @exception NumberFormatException if file format is not valid.
     * @exception IndexOutOfBoundsException if file format 
     * is not valid (blank).
     */
    class ReadInMenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            try {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Select File to Read in:");
                int result = chooser.showOpenDialog(getContentPane());
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    buildModel.createCardDeck(file);
                    displayTextFieldContent();
                    setEditComponentsEnabled(true);
                    readInButton.setEnabled(true);
                    saveAsMenuItem.setEnabled(true);
                    saveAsDefaultMenuItem.setEnabled(true);
                    writeOutButton.setEnabled(true);
                    saveAsDefaultMenuItem.setEnabled(true);
                    setPrevNextButtonVisibility(0, buildModel.getCardDeckSize() - 1);
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
            catch (IndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(
                        null, 
                        "Invalid data file format! Please retry.", 
                        "ERROR", 
                        JOptionPane.ERROR_MESSAGE);
            }            
        }
    }
    
    /**
     * Listener class for "save as" JButton that allows user to select
     * file save location of edited or new flashcard file.
     */
    class SaveAsMenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Save File to Another Location:");
            int result = chooser.showSaveDialog(getContentPane());
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                buildModel.save(file);
            }
        }
    }
    
    /**
     * Listener class for "Read from Default File" JMenuItem that, when
     * selected, opens the card deck contained in the default data file
     * for editing.  If no data file exists in the default location (in
     * flashcard project directory), blank file created.  Error message
     * to ensure default file is in correct format.
     */
    class ReadInDefaultMenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            if (buildModel.read() == 1) {
                JOptionPane.showMessageDialog(
                        null, 
                        "Read data file successfully! You can now edit" +
                        " the file.", 
                        "Successful Read-in", 
                        JOptionPane.INFORMATION_MESSAGE);
                setEditComponentsEnabled(true);
                displayTextFieldContent();
                readInButton.setEnabled(true);
                saveAsMenuItem.setEnabled(true);
                saveAsDefaultMenuItem.setEnabled(true);
                writeOutButton.setEnabled(true);
                setPrevNextButtonVisibility(0, buildModel.getCardDeckSize() - 1);
            }
            else if (buildModel.read() == 0) {
                JOptionPane.showMessageDialog(
                        null, 
                        "No Data file found! New data file will be" +
                        " created automatically.", 
                        "Initialization Information", 
                        JOptionPane.INFORMATION_MESSAGE);
                buildModel.save();
                setEditComponentsEnabled(true);
                displayTextFieldContent();
                readInButton.setEnabled(false);
                saveAsMenuItem.setEnabled(true);
                saveAsDefaultMenuItem.setEnabled(true);
                writeOutButton.setEnabled(true);
                setPrevNextButtonVisibility(0, buildModel.getCardDeckSize() - 1);
                setEditButtonsEnabled(false);
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
     * Listener class for "Save to Default File" JMenuItem that, when
     * selected, saves the new or edited card deck to default data file.
     * If no data file exists in the default location.
     */
    class SaveAsDefaultMenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            if (buildModel.save())
                JOptionPane.showMessageDialog(
                        null, 
                        "Save data file successfully! The records have been updated.",
                        "Successful Output", 
                        JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(
                        null, 
                        "Failed to write to file! Please retry.", 
                        "ERROR", 
                        JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Initialize contents of this frame.
     */
    void createGui() {
  
        setBounds(100, 100, 550, 550);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Build Flashcards");
        getContentPane().setLayout(new GridLayout(5, 1));

        // panel 1
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        JPanel panel1Inner = new JPanel();
        panel1Inner.setLayout(new GridLayout(2, 3, 2, 7));
        panel1Inner.add(readInButton);
        panel1Inner.add(createFileButton);
        panel1Inner.add(writeOutButton);
        panel1Inner.add(addCardButton);
        panel1Inner.add(removeCardButton);
        panel1Inner.add(resetButton);
        panel1.add(Box.createHorizontalStrut(30), BorderLayout.WEST);
        panel1.add(Box.createHorizontalStrut(30), BorderLayout.EAST);
        panel1.add(Box.createVerticalStrut(30), BorderLayout.SOUTH);
        panel1.add(panel1Inner, BorderLayout.CENTER);
        
        // panel 2
        JPanel panel2 = new JPanel();
        JPanel labelPanel = new JPanel();
        JPanel questionPanel = new JPanel();
        panel2.setLayout(new BorderLayout());
        labelPanel.setLayout(new GridLayout(5, 1)); 
        questionPanel.setLayout(new GridLayout(5, 1, 0, 2));
        questionTextField.setBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
        answerTextField.setBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
        intervalTextField.setBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
        presentationTextField.setBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
        statusTextField.setBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
        questionTextField.setEditable(false);
        answerTextField.setEditable(false);
        intervalTextField.setEditable(false);
        presentationTextField.setEditable(false);
        statusTextField.setEditable(false);
        labelPanel.add(questionLabel);
        questionPanel.add(questionTextField);
        labelPanel.add(answerLabel);
        questionPanel.add(answerTextField);
        labelPanel.add(intervalLabel);
        questionPanel.add(intervalTextField);
        labelPanel.add(presentationLabel);
        questionPanel.add(presentationTextField);
        labelPanel.add(statusLabel);
        questionPanel.add(statusTextField);
        panel2.add(labelPanel, BorderLayout.WEST);
        panel2.add(questionPanel, BorderLayout.CENTER);
        panel2.add(Box.createHorizontalStrut(30), BorderLayout.EAST);
        
        
        // panel 3
        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout());
        panel3.add(prevCardButton);
        panel3.add(findCardButton);
        panel3.add(nextCardButton);
        
        //panel 4
        JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout());
        panel4.add(editLabel);
        panel4.add(editQuestionButton);
        panel4.add(editAnswerButton);
        panel4.add(editIntervalButton);
        panel4.add(editTimeButton);    
        panel4.add(editStatusButton);
        
        // panel 5
        JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout());
        JPanel panel5Inner = new JPanel();
        panel5Inner.setLayout(new GridLayout(1, 1));
        panel5Inner.add(exitButton);
        panel5.add(panel5Inner, BorderLayout.CENTER);
        panel5.add(Box.createHorizontalStrut(120), BorderLayout.WEST);
        panel5.add(Box.createHorizontalStrut(120), BorderLayout.EAST);
        panel5.add(Box.createVerticalStrut(30), BorderLayout.NORTH);
        panel5.add(Box.createVerticalStrut(30), BorderLayout.SOUTH);
        
        // Add all panels to frame.
        getContentPane().add(panel1);
        getContentPane().add(panel2);
        getContentPane().add(panel3);
        getContentPane().add(panel4);
        getContentPane().add(panel5);

        // Menu Bar
        myJMenuBar.add(myJMenu);
        myJMenu.add(readInMenuItem);
        myJMenu.add(readInDefaultMenuItem);
        myJMenu.add(saveAsMenuItem);
        myJMenu.add(saveAsDefaultMenuItem);
        myJMenu.add(createFileMenuItem);
        myJMenu.add(quitMenuItem);
        this.setJMenuBar(myJMenuBar);
        
        // Attach listeners
        quitMenuItem.addActionListener(new QuitMenuItemListener());
        readInMenuItem.addActionListener(new ReadInMenuItemListener());
        readInDefaultMenuItem.addActionListener(
                new ReadInDefaultMenuItemListener());
        saveAsMenuItem.addActionListener(new SaveAsMenuItemListener());
        saveAsDefaultMenuItem.addActionListener(
                new SaveAsDefaultMenuItemListener());
        createFileMenuItem.addActionListener(new CreateFileButtonListener());
        readInButton.addActionListener(new ReadInButtonListener());
        writeOutButton.addActionListener(new WriteOutButtonListener());
        addCardButton.addActionListener(new AddCardButtonListener());
        removeCardButton.addActionListener(new RemoveCardButtonListener());
        exitButton.addActionListener(new ExitButtonListener());
        editQuestionButton.addActionListener(new EditQuestionButtonListener());
        editAnswerButton.addActionListener(new EditAnswerButtonListener());
        editIntervalButton.addActionListener(new EditIntervalButtonListener());
        editTimeButton.addActionListener(new EditPresentationButtonListener());     
        editStatusButton.addActionListener(new EditStatusButtonListener());
        nextCardButton.addActionListener(new NextCardButtonListener());
        prevCardButton.addActionListener(new PrevCardButtonListener());
        resetButton.addActionListener(new ResetButtonListener());
        findCardButton.addActionListener(new FindCardButtonListener());      
        createFileButton.addActionListener(new CreateFileButtonListener());
        
        // set keyboard equivalents for each button
        readInButton.setMnemonic('i');
        writeOutButton.setMnemonic('w');
        addCardButton.setMnemonic('a');
        removeCardButton.setMnemonic('d');
        exitButton.setMnemonic('e');
        editQuestionButton.setMnemonic('q');
        editAnswerButton.setMnemonic('w');
        editIntervalButton.setMnemonic('i');
        editTimeButton.setMnemonic('t');
        editStatusButton.setMnemonic('s');
        nextCardButton.setMnemonic('n');
        prevCardButton.setMnemonic('v');
        resetButton.setMnemonic('r');
        findCardButton.setMnemonic('f');
        createFileButton.setMnemonic('c');
        
        // set initial component visibility
        setAllComponentsEnabled(false);
        saveAsMenuItem.setEnabled(false);
        saveAsDefaultMenuItem.setEnabled(false);
    }
    
    /**
     * Populates the text fields in the Build GUI to display the current
     * card's: question, answer, interval, presentation time, and status.
     */
    private void displayTextFieldContent() {
        questionTextField.setText(buildModel.getCurrentCard().getQuestion());
        answerTextField.setText(buildModel.getCurrentCard().getAnswer());
        intervalTextField.setText(Integer.toString(
                buildModel.getCurrentCard().getInterval()));
        presentationTextField.setText(Integer.toString(
                buildModel.getScheduledTime()));
        statusTextField.setText(
                buildModel.getCurrentCard().getStatus() ? "Yes" : "No");
    }
    
    /**
     * Clears all GUI text fields.
     */
    private void displayBlankTextFieldContent() {
        questionTextField.setText("");
        answerTextField.setText("");
        intervalTextField.setText("");
        presentationTextField.setText("");
        statusTextField.setText("");
    }
    
    /**
     * Set all buttons and texts to enabled.
     * @param b boolean value indicating whether or not all components
     * should be enabled.
     */
    private void setAllComponentsEnabled(boolean b) {
        writeOutButton.setEnabled(b);
        prevCardButton.setEnabled(b);
        nextCardButton.setEnabled(b);
        setEditComponentsEnabled(b);
    }
    
    /**
     * Enable or disable components and text fields sans the previous card,
     * next card and writeOutButton.
     * @param b boolean value indicating whether or not components
     * should be enabled.
     */
    private void setEditComponentsEnabled(boolean b) {
        addCardButton.setEnabled(b);
        removeCardButton.setEnabled(b);
        findCardButton.setEnabled(b);
        resetButton.setEnabled(b);
        questionTextField.setEnabled(b);
        answerTextField.setEnabled(b);
        intervalTextField.setEnabled(b);
        presentationTextField.setEnabled(b);
        statusTextField.setEnabled(b);
        setEditButtonsEnabled(b);
    }
    
    /**
     * Enable or disable all edit JButtons.
     * @param b boolean value indicating whether or not all edit buttons
     * should be enabled.
     */
    private void setEditButtonsEnabled(boolean b) {
        editQuestionButton.setEnabled(b);
        editAnswerButton.setEnabled(b);
        editIntervalButton.setEnabled(b);
        editTimeButton.setEnabled(b);
        editStatusButton.setEnabled(b);
    }
    
    /**
     * Enable or disable next card JButton.  If current card is the
     * last card in the deck, next card button will not be enabled.
     * @param upper integer value not to be exceeded by card counter.
     */
    private void setNextButtonVisibility(int upper) {
        if (BuildModel.getCurrentCounter() >= upper)
            nextCardButton.setEnabled(false);
        else
            nextCardButton.setEnabled(true);
    }
    
    /**
     * Enable or disable previous card JButton.  If current card is that
     * first card in the deck, previous card button will not be enabled.
     * @param lower integer value that card counter should equal at its
     * minimum.
     */
    private void setPrevButtonVisibility(int lower) {
        if (BuildModel.getCurrentCounter() <= lower)
            prevCardButton.setEnabled(false);
        else
            prevCardButton.setEnabled(true);
    }
    
    /**
     * Enable or disable both previous and next card JButtons depending
     * on whether current card counter is equal to the lower and upper
     * limits, respectively.
     * @param lower integer value that card counter should equal at its
     * minimum.
     * @param upper integer value not to be exceeded by card counter.
     */
    private void setPrevNextButtonVisibility(int lower, int upper) {
        setNextButtonVisibility(upper);
        setPrevButtonVisibility(lower);
    }
    
    /**
     * Adds new flashcard to the card deck.  Provides user with the option
     * to enter a new question, answer and interval for card and adds the
     * card to the deck.
     * @exception BlankCardException if user tries to save blank input.
     * @exception DuplicateQuestionException if user tries to save duplicate
     * question.
     * @exception NullPointerException if user cancels the JOptionPane.
     */
    private void addNewCard() {
        String newQuestion = "", newAnswer = "";
        int interval = BuildModel.getModerateInterval();
        
        try {
            newQuestion = JOptionPane.showInputDialog(getContentPane(),
                    "Enter new question:");
            if (newQuestion.trim().length() == 0) throw new BlankCardException();
            if (buildModel.hasSameQuestion(newQuestion)) throw new DuplicateQuestionException();
            newAnswer = JOptionPane.showInputDialog(getContentPane(),
                    "Enter new answer:");
            if (newAnswer.trim().length() == 0) throw new BlankCardException();
            Object[] options = new String[] {"Default (Moderate)",
                    "Easy: " + BuildModel.getEasyInterval(), 
                    "Moderate: " + BuildModel.getModerateInterval(), 
                    "Difficult: " + BuildModel.getDifficultInterval()};
            int option = JOptionPane.showOptionDialog(null, 
                                         "Set this card as:" +
                                         "\n(the number indicates actual interval)", 
                                         "Set interval", 
                                         JOptionPane.YES_NO_CANCEL_OPTION, 
                                         JOptionPane.QUESTION_MESSAGE, 
                                         null, 
                                         options, 
                                         options[0]);
            if (option == 0)
                interval = BuildModel.getEasyInterval();
            if (option == 1)
                interval = BuildModel.getModerateInterval();
            if (option == 2)
                interval = BuildModel.getDifficultInterval();
            
            buildModel.addCard(new Card(newQuestion, newAnswer, interval, true));
            if (buildModel.save()) {
                JOptionPane.showMessageDialog(
                        null, 
                        "Add card successfully! The records have been updated.",
                        "Successful Output", 
                        JOptionPane.INFORMATION_MESSAGE);
                writeOutButton.setEnabled(true);
                setPrevNextButtonVisibility(0, buildModel.getCardDeckSize() - 1);
                buildModel.updateCurrentCard();
                displayTextFieldContent();
                setEditButtonsEnabled(true);
            }
            else
                JOptionPane.showMessageDialog(
                        null, 
                        "Failed to add this card! Please retry.", 
                        "ERROR", 
                        JOptionPane.ERROR_MESSAGE);
        }
        catch (BlankCardException e) {
            JOptionPane.showMessageDialog(null, 
                                          "Blank input is NOT allowed! Please retry.", 
                                          "Invalid Input", 
                                          JOptionPane.ERROR_MESSAGE);   
        }
        catch (DuplicateQuestionException e) {
            JOptionPane.showMessageDialog(null, 
                                          "Duplicate question found! Please revise.", 
                                          "Invalid Input", 
                                          JOptionPane.ERROR_MESSAGE);
        }
        catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, 
                                          "You may add this card next time.", 
                                          "Information", 
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, 
                                          "Please select your card.", 
                                          "Information", 
                                          JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Instance variables
    private JButton readInButton = new JButton("Read In From File...");
    private JButton createFileButton = new JButton("Create Blank File");
    private JButton addCardButton = new JButton("Add New Card");
    private JButton editQuestionButton = new JButton("Question");
    private JButton editAnswerButton = new JButton("Answer");
    private JButton editIntervalButton = new JButton("Interval");
    private JButton editTimeButton = new JButton("Scheduled Time");
    private JButton editStatusButton = new JButton("Status");
    private JButton prevCardButton = new JButton("<=  Previous Card");
    private JButton nextCardButton = new JButton("Next Card  =>");
    private JButton findCardButton = new JButton("Find Card");
    private JButton removeCardButton = new JButton("Delete Card");
    private JButton writeOutButton = new JButton("Write Out To File...");
    private JButton exitButton = new JButton("Exit");
    private JButton resetButton = new JButton("Reset");
    private JLabel editLabel = new JLabel("Edit: ");
    private JLabel questionLabel = new JLabel("    Question: ");
    private JLabel answerLabel = new JLabel("    Answer: ");
    private JLabel intervalLabel = new JLabel("    Interval: ");
    private JLabel presentationLabel = new JLabel("    Scheduled Time: ");
    private JLabel statusLabel = new JLabel("    Is Virgin? ");
    private JMenuBar myJMenuBar = new JMenuBar();
    private JMenu myJMenu = new JMenu("File");
    private JMenuItem readInMenuItem = new JMenuItem("Read File...");
    private JMenuItem readInDefaultMenuItem = new JMenuItem("Read From Default File");
    private JMenuItem saveAsMenuItem = new JMenuItem("Save as...");
    private JMenuItem saveAsDefaultMenuItem = new JMenuItem("Save To Default File");
    private JMenuItem createFileMenuItem = new JMenuItem("Create Blank File");
    private JMenuItem quitMenuItem = new JMenuItem("Quit");
    private JTextField questionTextField = new JTextField("Please press 'Read In From File' button.");
    private JTextField answerTextField = new JTextField();
    private JTextField intervalTextField = new JTextField();
    private JTextField presentationTextField = new JTextField(); 
    private JTextField statusTextField = new JTextField();
}

/**
 * Define custom exception for use when new or edited card question matches
 * one already in existence.
 */
class DuplicateQuestionException extends Exception {  
    public DuplicateQuestionException() {}  
    public DuplicateQuestionException(String msg) {super(msg);}
}

/**
 * Define custom exception for use when user tries to save a blank field
 * as flashcard information.
 */
class BlankCardException extends Exception {  
    public BlankCardException() {}  
    public BlankCardException(String msg) {super(msg);}
}
