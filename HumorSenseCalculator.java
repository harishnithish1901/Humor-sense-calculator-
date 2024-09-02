import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List; // Import the desired List class

public class HumorSenseCalculator extends JFrame {
    private JTextArea questionArea, responseArea;
    private JButton submitButton, calculateButton;
    private List<String> questions;
    private Map<String, Integer> keywords;
    private List<String> userResponses;

    public HumorSenseCalculator() {
        // Initialize questions, keywords, and userResponses
        questions = new ArrayList<>();
        keywords = new HashMap<>();
        keywords.put("serial bulb", 3);
        keywords.put("joke", 2);
        keywords.put("animal", 1);
        userResponses = new ArrayList<>();
   
        try (BufferedReader reader = new BufferedReader(new FileReader("jokes.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                questions.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error reading questions file!");
        }
        // Create UI components
        questionArea = new JTextArea(questions.get(0));
        responseArea = new JTextArea();
        submitButton = new JButton("Submit");
        calculateButton = new JButton("Calculate Score");

        // Set up layout and event handlers
        setLayout(new BorderLayout());
        add(questionArea, BorderLayout.NORTH);
        add(responseArea, BorderLayout.CENTER);
        add(submitButton, BorderLayout.WEST);
        add(calculateButton, BorderLayout.EAST);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userResponses.add(responseArea.getText());
                responseArea.setText("");
        
                // Track current question index
                int currentQuestionIndex = 1; // Start from the second question
        
                if (currentQuestionIndex < questions.size()) {
                    questionArea.setText(questions.get(currentQuestionIndex));
                    currentQuestionIndex++; // Increment index for the next question
                } else {
                    // Handle end of questions (e.g., show a message)
                }
            }
        });
        

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int score = 0;
                for (String response : userResponses) {
                    String[] words = response.split("\\s+");
                    for (String word : words) {
                        String lowercaseWord = word.toLowerCase();
                        if (keywords.containsKey(lowercaseWord)) {
                            score += keywords.get(lowercaseWord);
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, "Your humor score is: " + score);
            }
        });

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new HumorSenseCalculator();
    }
}