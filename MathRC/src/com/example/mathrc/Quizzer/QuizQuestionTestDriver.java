/*********************************
 * Test Driver to test QuizQuestion functionality
 * 
 *********************************/

package com.example.mathrc.Quizzer;

import com.example.mathrc.Quizzer.exceptions.*;



public class QuizQuestionTestDriver {

	
	public static void main(String[] args) throws QuizQuestionException{
		QuizQuestion testQ = new QuizQuestion();
		//testQ.qToString(); // Uncomment to test NoQuestionException
		testQ.addQuestion("What is your question?");
		//testQ.qToString(); // Uncomment to test NoCorrectAnswerException
		testQ.addCorrectAnswer("This is the correct answer.");
		testQ.addIncorrectAnswer("This is the first wrong answer.");
		testQ.addIncorrectAnswer("This is the second wrong answer.");
		testQ.addIncorrectAnswer("This is the third wrong answer.");
		//testQ.addIncorrectAnswer("This answer shouldn't successfully add."); // Uncomment to test TooManyAnswersException
		String[] output = testQ.qToString();
		for(int i = 0; i < output.length ; i++) System.out.println(output[i]);
		
		
	}
}
