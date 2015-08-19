package com.example.mathrc;

import java.io.IOException;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.os.Build;

import com.example.mathrc.Quizzer.*;
import com.example.mathrc.Quizzer.exceptions.*;


public class ReinforcementActivity extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	private static QuizBank qBank;
	private static QuizQuestion qQuestion;
	private static Button[] answerButtons; //{correctAnswer, incorrectAnswers...}
	private static EditText question;
	private static int correctAnsNum;
	private static SeekBar progressBar;
	private static Button nextButton;
	private static Button driveButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reinforcement);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reinforcement, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}

	private void updateQuiz(){
		int numOfAns = qQuestion.numberOfAnswers();
		correctAnsNum = QuizBank.randInt(1, numOfAns);
		String[] qString = null;
		try {
			qString = qQuestion.qToString();
		} catch (QuizQuestionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		question.setText(qString[0]);
		int incAns = 2;
		for(int i = 0; i < 4; i++){
			answerButtons[i].setBackgroundResource(android.R.drawable.btn_default);
			if(i == correctAnsNum-1){
				answerButtons[i].setVisibility(View.VISIBLE);
				answerButtons[i].setText(qString[1]);
				answerButtons[i].setEnabled(true);
			}
			else if(i < numOfAns){ 
				answerButtons[i].setVisibility(View.VISIBLE);
				answerButtons[i].setText(qString[incAns]);
				answerButtons[i].setEnabled(true);
				incAns++;
			}
			else{
				answerButtons[i].setVisibility(View.INVISIBLE);
				answerButtons[i].setEnabled(false);
			}
		}
		
	} //end updateQuiz
	
// Begin Listener code
	
	public void answer1ButtonListener(View view) {
		if(correctAnsNum == 1){
			answerButtons[0].setBackgroundColor(Color.GREEN);
			answerButtons[1].setEnabled(false);
			answerButtons[2].setEnabled(false);
			answerButtons[3].setEnabled(false);
			if(progressBar.getProgress() == 10){
				qQuestion.addQuestion("Congratulations, you've answered demonstrated"
						+ "master of the subject! Press DRIVE to drive the RC Car now!");
				updateQuiz();
				for(int i=0;i<4;i++) answerButtons[i].setEnabled(false);
				driveButton.setEnabled(true);
				
			}
		}
		else{
			answerButtons[0].setBackgroundColor(Color.RED);
			answerButtons[1].setEnabled(false);
			answerButtons[2].setEnabled(false);
			answerButtons[3].setEnabled(false);
			if(progressBar.getProgress() != 0) progressBar.incrementProgressBy(-1);
		}
		nextButton.setEnabled(true);
	} // end answer1Listener
	
	public void answer2ButtonListener(View view) {
		if(correctAnsNum == 2){
			answerButtons[1].setBackgroundColor(Color.GREEN);
			answerButtons[0].setEnabled(false);
			answerButtons[2].setEnabled(false);
			answerButtons[3].setEnabled(false);
			progressBar.incrementProgressBy(1);
			if(progressBar.getProgress() == 10){
				qQuestion.addQuestion("Congratulations, you've answered demonstrated"
						+ "master of the subject! Press DRIVE to drive the RC Car now!");
				updateQuiz();
				for(int i=0;i<4;i++) answerButtons[i].setEnabled(false);
				driveButton.setEnabled(true);
				
			}
		}
		else{
			answerButtons[1].setBackgroundColor(Color.RED);
			answerButtons[0].setEnabled(false);
			answerButtons[2].setEnabled(false);
			answerButtons[3].setEnabled(false);
			if(progressBar.getProgress() != 0) progressBar.incrementProgressBy(-1);
		}
		nextButton.setEnabled(true);
	} // end answer2Listener
	
	public void answer3ButtonListener(View view) {
		if(correctAnsNum == 3){
			answerButtons[2].setBackgroundColor(Color.GREEN);
			answerButtons[1].setEnabled(false);
			answerButtons[0].setEnabled(false);
			answerButtons[3].setEnabled(false);
			progressBar.incrementProgressBy(1);
			if(progressBar.getProgress() == 10){
				qQuestion.addQuestion("Congratulations, you've answered demonstrated"
						+ "master of the subject! Press DRIVE to drive the RC Car now!");
				updateQuiz();
				for(int i=0;i<4;i++) answerButtons[i].setEnabled(false);
				driveButton.setEnabled(true);
				
			}
		}
		
		else{
			answerButtons[2].setBackgroundColor(Color.RED);
			answerButtons[1].setEnabled(false);
			answerButtons[0].setEnabled(false);
			answerButtons[3].setEnabled(false);
			if(progressBar.getProgress() != 0) progressBar.incrementProgressBy(-1);
		}
		nextButton.setEnabled(true);
	} // end answer3Listener
	
	public void answer4ButtonListener(View view) {
		if(correctAnsNum == 4){
			answerButtons[3].setBackgroundColor(Color.GREEN);
			answerButtons[1].setEnabled(false);
			answerButtons[2].setEnabled(false);
			answerButtons[0].setEnabled(false);
			progressBar.incrementProgressBy(1);
			if(progressBar.getProgress() == 10){
				qQuestion.addQuestion("Congratulations, you've answered demonstrated"
						+ "master of the subject! Press DRIVE to drive the RC Car now!");
				updateQuiz();
				for(int i=0;i<4;i++) answerButtons[i].setEnabled(false);
				driveButton.setEnabled(true);
				
			}
		}
		else{
			answerButtons[3].setBackgroundColor(Color.RED);
			answerButtons[1].setEnabled(false);
			answerButtons[2].setEnabled(false);
			answerButtons[0].setEnabled(false);
			if(progressBar.getProgress() != 0) progressBar.incrementProgressBy(-1);
		}
		nextButton.setEnabled(true);
	} // end answer4Listener
	
	public void nextButtonListener(View view) {
		try {
			qQuestion = qBank.getRandQuizQuestion();
			updateQuiz();
		} catch (QuizBankEmptyException e) {
			// TODO Auto-generated catch block
			qQuestion.addQuestion("Congratulations, you've finished all the questions on this topic! Press DRIVE"
					+ "to begin driving the car!");
			updateQuiz();
			for(int i=0;i<4;i++) answerButtons[i].setEnabled(false);
			driveButton.setEnabled(true);
		}
		nextButton.setEnabled(false);
	} //end nextButtonListener
	
	public void driveButtonListener(View view) {
		Intent intent = new Intent(this, DrivingActivity.class);
		intent.putExtra(EXTRA_MESSAGE, String.valueOf(progressBar.getProgress()));
		startActivity(intent);
		finish();
	}
	
	
// end Listener code
	
	public class PlaceholderFragment extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_reinforcement,
					container, false);
			nextButton = (Button) rootView.findViewById(R.id.nextButton);
			driveButton = (Button) rootView.findViewById(R.id.driveButton);
			nextButton.setEnabled(false);
			progressBar = (SeekBar) rootView.findViewById(R.id.progressBar);
			progressBar.setMax(R.integer.maxProgressBar);
			progressBar.setEnabled(false);
			question = (EditText) rootView.findViewById(R.id.questionEditText);
			answerButtons = new Button[]{(Button) rootView.findViewById(R.id.answer1Button), (Button) rootView.findViewById(R.id.answer2Button), 
					(Button) rootView.findViewById(R.id.answer3Button), (Button) rootView.findViewById(R.id.answer4Button), 
			};
			driveButton.setEnabled(false);
			
			
			try {
				if(TransitionActivity.quiztype.equals("Programming")) qBank = new QuizBank(getResources().openRawResource(R.raw.physicsquiz));
				else if(TransitionActivity.quiztype.equals("Physics")) qBank = new QuizBank(getResources().openRawResource(R.raw.programmingquiz));
				else qBank = new QuizBank(getResources().openRawResource(R.raw.testbank));
				qQuestion = qBank.getRandQuizQuestion();
			} catch (NotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (QuizQuestionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (QuizBankEmptyException e1) {
				e1.printStackTrace();
			}
			

			ReinforcementActivity.this.updateQuiz();

			
			
			return rootView;
		}
		
		
		
		
	} // end PlaceHolderFragment

} // end ReinforcementActivity
