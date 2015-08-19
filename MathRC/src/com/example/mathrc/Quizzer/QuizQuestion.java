/******************************
 * Class to hold a quiz question and answers
 * 
 * Mutator Methods:
 * 		addQuestion(String)
 * 		addCorrectAnswer(String)
 * 		addIncorrectAnswer(String)
 * Utility Methods:
 * 		qToString()
 ******************************/

package com.example.mathrc.Quizzer;

import com.example.mathrc.Quizzer.exceptions.*;

public class QuizQuestion {
	private String question;
	private String correctAnswer;
	private String incorrectAnswer1;
	private String incorrectAnswer2;
	private String incorrectAnswer3;
	private int numOfAnswers;
	
	
	//  Instantiate variables with construction
	public QuizQuestion(){
		numOfAnswers = 1;
	}
	
	public QuizQuestion(String inputQuestion){
		numOfAnswers = 1;
		question = inputQuestion;
	}
	
	// Mutator methods to add question and answers to question
	public void addQuestion(String inputQuestion){
		question = inputQuestion;
	}
	
	public void addCorrectAnswer(String inputAnswer){
		correctAnswer = inputAnswer;
	}
	
	public void addIncorrectAnswer(String inputAnswer) throws TooManyAnswersException{
		if(incorrectAnswer1 == null) {incorrectAnswer1 = inputAnswer; numOfAnswers = 2;}
		else if(incorrectAnswer2 == null) {incorrectAnswer2 = inputAnswer; numOfAnswers = 3;}
		else if(incorrectAnswer3 == null) {incorrectAnswer3 = inputAnswer; numOfAnswers = 4;}
		else throw new TooManyAnswersException();
	}
	
	// Accessor methods to access number of answers
	public int numberOfAnswers(){
		return numOfAnswers;
	}
	
	public boolean hasQuestion(){
		if(question == null) return true;
		else return false;
	}
	
	// Return question in a string array. {question, correct answer, incorrectanswers...}
	public String[] qToString() throws NoQuestionException, NoCorrectAnswerException, TooFewAnswersException{
		if(question == null) throw new NoQuestionException();
		if(correctAnswer == null) throw new NoCorrectAnswerException();
		String[] outputString = null;
		switch(numOfAnswers){
		case(4): 
			outputString = new String[]{question, correctAnswer, incorrectAnswer1, incorrectAnswer2, incorrectAnswer3};
			break;
		case(3):
			outputString = new String[]{question, correctAnswer, incorrectAnswer1, incorrectAnswer2};
			break;
		case(2):
			outputString = new String[]{question, correctAnswer, incorrectAnswer1};
			break;
		case(1):
			throw new TooFewAnswersException();
		}
		return outputString;
		
	}
	
}
