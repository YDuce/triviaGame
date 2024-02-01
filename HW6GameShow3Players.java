package code;

import java.util.Scanner;

public class HW6GameShow3Players {

    // Constants for difficulty levels and points
    private static final int EASY = 0;
    private static final int MEDIUM = 3;
    private static final int HARD = 6;
    private static final int POINTS_EASY = 3;
    private static final int POINTS_MEDIUM = 7;
    private static final int POINTS_HARD = 12;

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);

        // Variables to track player's progress
        int questionIndex;
        int points;
        int questionCounter;
        int playerTotal;
        int playerGotRight;

        // Variables to store multiple player scores
        int numberOfPlayers = 3;
        int[][] playerScores = new int[numberOfPlayers][3];
        String userResponse;

        System.out.println("Welcome to TriviaGame! Answer three questions from easy, medium, or hard categories.\nTo change difficulty enter h/home  To quit enter q/quit");
        System.out.println("\nPress ENTER to begin.");
        userResponse = kb.nextLine();

        for (int currentPlayer = 0; currentPlayer < numberOfPlayers && !returnUserQuit(userResponse); currentPlayer++) {
            System.out.println("\nPlayer: " + (currentPlayer + 1));
            questionCounter = 0;
            playerTotal = 0;
            playerGotRight = 0;
            do {
                System.out.println("\nChoose difficulty: Easy, Medium, or Hard. Enter 'q' to quit.");
                userResponse = returnUserInput(kb);

                int[] index = returnSetting(userResponse);
                questionIndex = index[0];
                points = index[1];

                for (int questionNumber = (questionIndex + questionCounter); questionCounter < 3 && (!returnUserHome(userResponse)) && (!returnUserQuit(userResponse)); questionNumber++) {
                    System.out.println("Question #" + (questionCounter + 1) + "\n" + returnTriviaQuestion(questionNumber));
                    userResponse = kb.nextLine();

                    if (returnUserHome(userResponse) || returnUserQuit(userResponse)) {
                        break;
                    } else if (returnSurveySays(userResponse, returnTriviaAnswer(questionNumber))) {
                        System.out.println("Correct," + " the answer was " + userResponse + " that adds " + points + " points to your score.\n");
                        playerTotal += points;
                        playerGotRight++;
                    } else {
                        System.out.println("Incorrect, the penalty will be minus " + points + " points. \nThe correct answer is " + returnTriviaAnswer(questionNumber) + "\n");
                        playerTotal -= points;
                    }
                    questionCounter++;
                }
                playerScores[currentPlayer] = returnPlayerStats(playerTotal, playerGotRight, questionCounter);

            } while (questionCounter < 3 && (!returnUserQuit(userResponse)));

            if (!returnUserQuit(userResponse)) {

                if (playerGotRight == 3) {
                    System.out.println("\nYou answered all questions correctly, well done.");
                }
                System.out.println("\nResults: Correct answers - " + playerGotRight + ", Incorrect answers - " + (questionCounter - playerGotRight) + ", Total Score: " + playerTotal);
            }
        }
        

        // Variables to track the highest score and the player with the highest score
        int highScore = 0;
        int highestScorer = -1;

        // Iterate through player scores
        for (int displayPlayer = 0; displayPlayer < playerScores.length && playerScores[displayPlayer][2] > 0; displayPlayer++) {
            // Print individual player scores
            System.out.println("\nFinal Scores\n\nPlayer " + (displayPlayer + 1) + ": " + playerScores[displayPlayer][0]);

            // Update highest score and scorer if needed
            if (playerScores[displayPlayer][0] > highScore) {
                highScore = playerScores[displayPlayer][0];
                highestScorer = displayPlayer;
            }
        }

        // Announce the winner if the game was not quit
        if (!returnUserQuit(userResponse)) {
            System.out.println("\nPlayer: " + (highestScorer + 1) + " wins with " + highScore + " points");
        }

        //Farewell message
        System.out.println("\nThanks for playing! Goodbye.");

    }

    /**
     * Returns an array containing the player's total score, the number of questions
     * answered correctly, and the total number of questions attempted.
     *
     * @param playerTotal      The total score of the player.
     * @param playerGotRight   The number of questions answered correctly by the player.
     * @param questionCounter  The total number of questions attempted by the player.
     * @return An array containing the player's total score, correct answers, and
     *         total questions attempted.
     */
    private static int[] returnPlayerStats(int playerTotal, int playerGotRight, int questionCounter) {
        int[] playerStats = { playerTotal, playerGotRight, questionCounter };
        return playerStats;
    }

    /**
     * Returns an array containing the index where questions for the given difficulty
     * start and the corresponding points for correct answers.
     *
     * @param userResponse The user's chosen difficulty level.
     * @return An array containing the index and points for the specified difficulty.
     */
    private static int[] returnSetting(String userResponse) {
        int[] settingsIndexed = new int[2];

        switch (userResponse) {
            case "easy":
                settingsIndexed[0] = EASY;
                settingsIndexed[1] = POINTS_EASY;
                break;
            case "medium":
                settingsIndexed[0] = MEDIUM;
                settingsIndexed[1] = POINTS_MEDIUM;
                break;
            case "hard":
                settingsIndexed[0] = HARD;
                settingsIndexed[1] = POINTS_HARD;
                break;
            default:
        }
        return settingsIndexed;
    }

    /**
     * Gets the user's input for the chosen difficulty level.
     *
     * @param kb The Scanner object to read user input.
     * @return The user's chosen difficulty level.
     */
    private static String returnUserInput(Scanner kb) {
        String input;
        do {
            System.out.print("Enter choice: ");
            input = kb.nextLine().toLowerCase();
            if (!(input.equals("easy") || input.equals("medium") || input.equals("hard") || returnUserQuit(input))) {
                System.out.println("**Invalid input. Enter easy, medium, or hard.**");
            }
        } while (!(input.equals("easy") || input.equals("medium") || input.equals("hard") || returnUserQuit(input)));

        return input;
    }

    /**
     * Checks if the user wants to quit the game.
     *
     * @param userInput The user's input.
     * @return True if the user wants to quit, false otherwise.
     */
    private static boolean returnUserQuit(String userInput) {
        return userInput.equalsIgnoreCase("q") || userInput.equalsIgnoreCase("quit");
    }

    /**
     * Checks if the user wants to return to the home screen.
     *
     * @param userInput The user's input.
     * @return True if the user wants to return home, false otherwise.
     */
    private static boolean returnUserHome(String userInput) {
        return userInput.equalsIgnoreCase("h") || userInput.equalsIgnoreCase("home");
    }

    /**
     * Checks if the user's answer matches the correct answer.
     *
     * @param usersAnswer   The user's answer.
     * @param correctAnswer The correct answer.
     * @return True if the answers match, false otherwise.
     */
    private static boolean returnSurveySays(String usersAnswer, String correctAnswer) {
        return usersAnswer.equalsIgnoreCase(correctAnswer);
    }

    /**
     * Retrieves the trivia question based on the provided index.
     *
     * @param questionRequest The index of the trivia question.
     * @return The trivia question.
     */
    private static String returnTriviaQuestion(int questionRequest) {
        String[] triviaQuestions = {
                "Name the largest ocean on Earth.",            // Easy question #1
                "In what year did the Titanic sink?",          // Easy question #2
                "Which planet is known as the Red Planet?",    // Easy question #3

                "Who wrote the play \"Romeo and Juliet\"?",    // Medium question #1
                "In which year did World War II end?",         // Medium question #2
                "What is the capital of Brazil?",              // Medium question #3

                "What is the largest mammal in the world?",    // Hard question #1
                "In which year did the Chernobyl disaster occur?",  // Hard question #2
                "Who developed the theory of relativity?"      // Hard question #3
        };

        return triviaQuestions[questionRequest];
    }

    /**
     * Retrieves the correct answer for the trivia question based on the provided index.
     *
     * @param answerRequest The index of the trivia question.
     * @return The correct answer to the trivia question.
     */
    private static String returnTriviaAnswer(int answerRequest) {
        String[] triviaAnswers = {
                "Pacific",             // Easy question #1 answer
                "1912",                // Easy question #2 answer
                "Mars",                // Easy question #3 answer

                "William Shakespeare", // Medium question #1 answer
                "1945",                // Medium question #2 answer
                "Brasilia",            // Medium question #3 answer

                "Blue Whale",          // Hard question #1 answer
                "1986",                // Hard question #2 answer
                "Albert Einstein"      // Hard question #3 answer
        };

        return triviaAnswers[answerRequest];
    }

}
