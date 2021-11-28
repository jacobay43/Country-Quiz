package com.delegames.countryquiz;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.res.Configuration;
import android.content.pm.ActivityInfo;

import androidx.fragment.app.DialogFragment;

public class ResultsDialogFragment extends DialogFragment
{

    public ResultsDialogFragment() {
    }
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //High scores
        //Reference the current instance of quizFragment so you can access its public variables current values
        MainActivityFragment quizFragment = (MainActivityFragment) getFragmentManager().findFragmentById(R.id.quizFragment);

        //ensure you are using a copy of the Set returned from getString and a copy of the Set put in putString, do not alter the reference directly to avoid uncertain results
        quizFragment.sp = PreferenceManager.getDefaultSharedPreferences(getActivity()); //get reference to the context
        Set<String> highScores = new HashSet<String>(quizFragment.sp.getStringSet("pref_highScores",new HashSet<String>())); //check for Set of high scores or initialize to an empty Set
        //if (highScores == null) //if null is returned .i.e this is the 1st time running the app
        //    highScores = new java.util.HashSet<String>(); //create a brand new set
        SharedPreferences.Editor editor = quizFragment.sp.edit();
        highScores.add(String.format("%d", quizFragment.userScore)); //add the current) score to the Set
        editor.putStringSet("pref_highScores", new HashSet<String>(highScores)); //persist it using SharedPreferences
        editor.apply();
        editor.commit();
        highScores = quizFragment.sp.getStringSet("pref_highScores",null); //retrieve all high score
        List<Integer> scoreList = new ArrayList<>();
        for(String s : highScores)
            scoreList.add(Integer.parseInt(s));
        Collections.sort(scoreList,Collections.reverseOrder()); //sort scores from highest to lowest

        if(scoreList.size() > 5)
            scoreList = scoreList.subList(0, 5); //attempt to trim to top 5 scores

        builder.setMessage(getString(R.string.results, quizFragment.totalGuesses, (1000 / (double) quizFragment.totalGuesses),quizFragment.userScore,scoreList.toString()));
        builder.setPositiveButton(R.string.reset_quiz, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        quizFragment.resetQuiz();
                    }
                }
        );
        return builder.create();
    }
}
