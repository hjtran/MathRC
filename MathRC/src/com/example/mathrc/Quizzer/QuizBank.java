package com.example.mathrc.Quizzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import com.example.mathrc.Quizzer.exceptions.*;

public class QuizBank {
	
	private ArrayList<QuizQuestion> bank;
	private int bankSize;
    private static Random rand;
    private ArrayList<QuizQuestion> usedQuestionBank;
    
	public QuizBank(InputStream bankTextFile) throws IOException, QuizQuestionException{
		
		// Initialize variables
		bank = new ArrayList<QuizQuestion>();
		bankSize = 0;
		rand = new Random();
		usedQuestionBank = new ArrayList<QuizQuestion>();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(bankTextFile));
		String line = null;
		
		// Read file for all questions until it reads "endbank"
		if(in.ready()) line = in.readLine();
		while(!line.equals("endbank")){
			if(line.substring(0,2).equals("Q:")){
				bankSize++;
				bank.add(new QuizQuestion(line));
				line = in.readLine();
				bank.get(bankSize-1).addCorrectAnswer(line);
				do{
					line = in.readLine();
					if(line.substring(0,2).equals("Q:") || line.equals("endbank"))
						break;
					bank.get(bankSize-1).addIncorrectAnswer(line);
				}while(true);
			}
		}
		
		in.close();
	}
	
	public int getBankSize(){
		return bankSize;
	}
	
	public QuizQuestion getRandQuizQuestion() throws QuizBankEmptyException{
		if(bankSize == 0) throw new QuizBankEmptyException();
		int randQuestionNumber = randInt(0,bankSize-1);
		QuizQuestion outputQuizQuestion = bank.get(randQuestionNumber);
		bank.remove(randQuestionNumber);
		usedQuestionBank.add(outputQuizQuestion);
		bankSize--;
		return outputQuizQuestion;
		
	}
	
	public void restoreBank(){
		for(int i = 0; i < usedQuestionBank.size(); i++){
			bankSize++;
			bank.add(usedQuestionBank.get(i));
		}
		usedQuestionBank.clear();
	}
	
	
	
	
	/**
	 * Returns a pseudo-random number between min and max, inclusive.
	 * The difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * @param min Minimum value
	 * @param max Maximum value.  Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public static int randInt(int min, int max) {

	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
}
