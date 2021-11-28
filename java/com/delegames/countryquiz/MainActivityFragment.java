package com.delegames.countryquiz;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.preference.PreferenceManager;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String TAG = "NigerianQuiz Activity";
    //outlines retrieved from: https://commons.wikimedia.org/w/index.php?search=zamfara+state+map&title=Special:MediaSearch&go=Go&type=image
    //may require crediting the author and other license requirements before usage on PlayStore
    String countries[] = {"AFGHANISTAN", "ALBANIA", "ALGERIA", "AMERICAN SAMOA", "ANDORRA", "ANGOLA", "ANGUILLA","ANTIGUA AND BARBUDA", "ARGENTINA", "ARMENIA", "ARUBA", "ASHMORE AND CARTIER ISLANDS", "AUSTRALIA", "AUSTRIA", "AZERBAIJAN", "BAHRAIN", "BANGLADESH", "BARBADOS", "BELGIUM", "BENIN", "BERMUDA", "BHUTAN", "BOLIVIA", "BOSNIA AND HERZEGOVINA", "BOTSWANA", "BRAZIL", "BRITISH VIRGIN ISLANDS", "BRUNEI", "BULGARIA", "BURKINA FASO", "BURMA", "BURUNDI", "CAMBODIA", "CAMEROON", "CANADA", "CAPE VERDE", "CAYMAN ISLANDS", "CENTRAL AFRICAN REPUBLIC", "CHAD", "CHILE", "CHINA", "CHRISTMAS ISLAND", "CLIPPERTON ISLAND", "COCOS(KEELING) ISLANDS", "COLOMBIA", "COMOROS", "COOK ISLANDS", "COSTA RICA", "COTED'IVOIRE", "CROATIA", "CUBA", "CZECH REPUBLIC", "DEMOCRATIC REPUBLIC OF THE CONGO", "DENMARK", "DJIBOUTI", "DOMINICA", "DOMINICAN REPUBLIC", "EAST TIMOR", "ECUADOR", "EGYPT", "EL SALVADOR", "EQUATORIAL GUINEA", "ERITREA", "ESTONIA", "ETHIOPIA", "FALKAND ISLANDS", "FAROE ISLANDS", "FIJI", "FINLAND", "FRANCE", "FRENCH GUIANA", "FRENCH POLYNESIA", "GABON", "GAMBIA", "GAZA STRIP", "GEORGIA", "GERMANY", "GHANA", "GIBRALTAR", "GREECE", "GREENLAND", "GRENADA", "GUADELOUPE", "GUAM", "GUATEMALA", "GUERNSEY", "GUINEA-BISSAU", "GUINEA", "GUYANA", "HAITI", "HONDURAS", "HONG KONG", "HUNGARY", "ICELAND", "INDIA", "INDONESIA", "IRAN", "IRAQ", "IRELAND", "ISRAEL", "ITALY", "JAMAICA", "JAPAN","JERSEY", "JORDAN", "KAZAKHSTAN", "KENYA", "KIRIBATI", "LAOS", "LATVIA", "LEBANON", "LESOTHO", "LIBERIA", "LIBYA", "LIECHTENSTEIN", "LITHUANIA", "LUXEMBOURG", "MACEDONIA", "MADAGASCAR", "MALAWI", "MALAYSIA", "MALDIVES", "MALI", "MALTA", "MARSHALL ISLANDS", "MAURITANIA", "MAURITIUS ISLAND", "MEXICO", "MICRONESIA", "MOLDOVA", "MONACO", "MONGOLIA", "MONTENEGRO", "MOROCCO", "MOZAMBIQUE", "MYANMAR", "NAMIBIA", "NAURU", "NEPAL", "NETHERLANDS", "NEW ZEALAND", "NICARAGUA", "NIGER", "NIGERIA", "NORWAY", "OMAN", "PAKISTAN", "PALAU", "PANAMA", "PAPUA NEW GUINEA", "PARAGUAY", "PERU", "PHILIPPINES", "POLAND", "PORTUGAL", "QATAR", "REPUBLIC OF THE CONGO", "SIERRA LEONE", "SINGAPORE", "SLOVAKIA", "SLOVENIA", "SOLOMON ISLANDS", "SOMALIA", "SOUTH AFRICA", "SOUTH GEORGIA AND SOUTH SANDWICH ISLANDS", "SOUTH SUDAN", "SPAIN", "SRI LANKA", "SUDAN", "SURINAME", "SVALBARD", "SWAZILAND", "SWEDEN", "SWITZERLAND", "SYRIA", "TAIWAN", "TAJIKISTAN", "TANZANIA", "THAILAND", "TOGO", "TOKELAU", "TONGA", "TRINIDAD AND TOBAGO", "TUNISIA", "TURKEY", "TURKMENISTAN", "TURKS AND CAICOS ISLANDS", "TUVALU", "UGANDA", "UKRAINE", "UNITED ARAB EMIRATES", "UNITED KINGDOM", "UNITED STATES", "URUGUAY", "UZBEKISTAN", "VANUATU", "VENEZUELA", "VIETNAM", "VIRGIN ISLANDS", "WALLIS AND FUTUNA ISLANDS", "WEST BANK", "WESTERN SAHARA", "YEMEN", "ZAMBIA", "ZIMBABWE"};
    String capitals[] = {"KABUL","TIRANA","ALGIERS","PAGO PAGO","ANDORRA LA VELLA","LUANDA","THE VALLEY","SAINT JOHN'S","BUENOS AIRES","YEREVAN","ORANJESTAD","CANBERRA","CANBERRA","VIENNA","BAKU","MANAMA","DHAKA","BRIDGETOWN","BRUSSELS","PORTO-NOVO","HAMILTON","THIMPHU","LA PAZ","SARAJEVO","GABORONE","BRASILIA","ROAD TOWN","BANDAR SERI BEGAWAN","SOFIA","OUAGADOUGOU","NAYPYITAW","GITEGA","PHNOM PENH","YAOUNDE","OTTAWA","PRAIA","GEORGE TOWN","BANGUI","N'DJAMENA","SANTIAGO","BEIJING","FLYING FISH COVE","PORT JAOUEN","WEST ISLAND","BOGOTA","MORONI","AVARUA DISTRICT","SAN JOSE","YAMOUSSOUKRO","ZAGREB","HAVANA","PRAGUE","KINSHASA","COPENHAGEN","DJIBOUTI CITY","ROSEAU","SANTO DOMINGO","DILI","QUITO","CAIRO","SAN SALVADOR","MALABO","ASMARA","TALLINN","ADDIS ABABA","STANLEY","TORSHAVN","SUVA","HELSINKI","PARIS","CAYENNE","PAPE'ETE","LIBREVILLE","BANJUL","GAZA CITY","TBILISI","BERLIN","ACCRA","WESTSIDE","ATHENS","NUUK","SAINT GEORGE'S","BASSE-TERRE","HAGATNA","GUATEMALA CITY","SAINT PETER PORT","BISSAU","CONAKRY","GEORGETOWN","PORT-AU-PRINCE","TEGUCIGALPA","HONG KONG","BUDAPEST","REEYKJAVIK","NEW DELHI","JAKARTA","TEHRAN","BAGHDAD","DUBLIN","JERUSALEM","ROME","KINGSTON","TOKYO","SAINT HELIER","AMMAN","NUR-SULTAN","NAIROBI","TARAWA","VIENTIANE","RIGA","BEIRUT","MASERU","MONROVIA","TRIPOLI","VADUZ","VILNIUS","LUXEMBOURG","SKOPJE","ANTANANARIVO","LILONGWE","KUALA LUMPUR","MALE","BAMAKO","VALLETTA","MAJURO","NOUAKCHOTT","PORT LOUIS","MEXICO CITY","PALIKIR","CHISINAU","MONTE-CARLO","ULAANBAATAR","PODGORICA","RABAT","MAPUTO","NAYPYITAW","WINDHOEK","YAREN","KATHMANDU","AMSTERDAM","WELLINGTON","MANAGUA","NIAMEY","ABUJA","OSLO","MUSCAT","ISLAMABAD","NGERULMUD","PANAMA CITY","PORT MORESBY","ASUNCION","LIMA","MANILA","WARSAW","LISBON","DOHA","BRAZZAVILLE","FREETOWN","SINGAPORE","BRATISLAVA","LJUBLJANA","HONIARA","MOGADISHU","CAPE TOWN, PRETORIA, AND BLOEMFONTEIN","KING EDWARD POINT","JUBA","MADRID","SRI JAYAWARDENEPURA KOTTE","KHARTOUM","PARAMARIBO","LONGYEARBYEN","MBABANE AND LOBAMBA","STOCKHOLM","BERN","DAMASCUS","TAIPEI CITY","DUSHANBE","DODOMA","BANGKOK","LOME","NUKUNONU","NUKU'ALOFA","PORT OF SPAIN","TUNIS","ANKARA","ASHGABAT","COCKBURN TOWN","FUNAFUTI","KAMPALA","KYIV","ABU DHABI","LONDON","WASHINGTON, D.C.","MONTEVIDEO","TASHKENT","PORT VILA","CARACAS","HANOI","CHARLOTTE AMALIE","MATA'UTU","EAST JERUSALEM","LAAYOUNE","SANA'A","LUSAKA","HARARE"};
    private HashMap<String,String> countryMap;
    private ArrayList<String> options;
    private static final int COUNTRIES_IN_QUIZ = 5;
    private static List<String> fileNameList;
    private List<String> quizCountriesList;
    private String correctAnswer;
    public static int totalGuesses;
    private static int correctAnswers;
    private int guessRows;
    private SecureRandom random;
    private Handler handler;
    private Animation shakeAnimation;
    private LinearLayout quizLinearLayout;
    private TextView questionNumberTextView;
    private TextView guessCountryTextView;
    private ImageView outlineImageView;
    private LinearLayout[] guessLinearLayouts;
    private TextView answerTextView;
    private static boolean onFirstTry; //bool for determining if user gets the right answer on first try
    private static boolean isBonusQuestion; //bool used to know if the current question being displayed is a bonus question
    public static int userScore;
    public static SharedPreferences sp;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private boolean firstRun = true;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        fileNameList = new ArrayList<>();
        quizCountriesList = new ArrayList<>();
        random = new SecureRandom();
        handler = new Handler();
        guessRows = 1;
        countryMap = new HashMap<String,String>();
        for(int i = 0; i < countries.length; ++i)
            countryMap.put(countries[i], capitals[i]);
        isBonusQuestion = false;
        options = new ArrayList<>();
        onFirstTry = true;
        isBonusQuestion = false;
        userScore = 0;
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),R.anim.incorrect_shake);
        shakeAnimation.setRepeatCount(3);

        quizLinearLayout = (LinearLayout)view.findViewById(R.id.quizLinearLayout);
        questionNumberTextView = (TextView)view.findViewById(R.id.questionNumberTextView);
        outlineImageView = (ImageView)view.findViewById(R.id.outlineImageView);
        guessLinearLayouts = new LinearLayout[1];
        guessLinearLayouts[0] = (LinearLayout)view.findViewById(R.id.row1LinearLayout);
        answerTextView = (TextView)view.findViewById(R.id.answerTextView);
        guessCountryTextView = (TextView)view.findViewById(R.id.guessCountryTextView);
        //mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        //mAdView.loadAd(adRequest);

        InterstitialAd.load(getContext(),"YOUR_AD_UNIT_ID",adRequest,
                new InterstitialAdLoadCallback(){
            @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd)
            {
                mInterstitialAd = interstitialAd;
                //ad loaded
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback()
                {
                    @Override
                    public void onAdDismissedFullScreenContent()
                    {
                        ;//ad was dismissed
                    }
                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError)
                    {
                        ;//full screen content failed to show
                    }
                    @Override
                    public void onAdShowedFullScreenContent()
                    {
                        mInterstitialAd = null; //reference set to null so ad is not shown a second time
                    }
                });
            }
            @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError)
            {
                Log.i("Country Quiz","LOAD FAILED!");
                mInterstitialAd = null;
            }
                });



        for(LinearLayout row : guessLinearLayouts)
        {
            for(int column = 0; column < row.getChildCount(); ++column)
            {
                Button button = (Button)row.getChildAt(column);
                button.setOnClickListener(guessButtonListener);
            }
        }
        questionNumberTextView.setText(getString(R.string.question,1,COUNTRIES_IN_QUIZ));
        return view;
    }

    public void resetQuiz()
    {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(getContext(),"YOUR_AD_UNIT_ID",adRequest,
                new InterstitialAdLoadCallback(){
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd)
                    {
                        mInterstitialAd = interstitialAd;
                        //ad loaded
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback()
                        {
                            @Override
                            public void onAdDismissedFullScreenContent()
                            {
                                ;//ad was dismissed
                            }
                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError)
                            {
                                ;//full screen content failed to show
                            }
                            @Override
                            public void onAdShowedFullScreenContent()
                            {
                                mInterstitialAd = null; //reference set to null so ad is not shown a second time
                            }
                        });
                    }
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError)
                    {
                        Log.i("Country Quiz","LOAD FAILED!");
                        mInterstitialAd = null;
                    }
                });

        if (mInterstitialAd != null)
            mInterstitialAd.show(getActivity());
        onFirstTry = true;
        isBonusQuestion = false;
        userScore = 0;
        AssetManager assets = getActivity().getAssets();
        fileNameList.clear();
        try
        {
            String []paths = assets.list("CountryOutlineImages");
            for(String path : paths)
                fileNameList.add(path.replace(".jpg",""));
        }
        catch(IOException exception)
        {
            Log.e(TAG,"Error loading image file names",exception);
        }
        correctAnswers = 0;
        totalGuesses = 0;
        quizCountriesList.clear();
        int countryCounter = 1;
        int numberOfCountries = fileNameList.size();
        while(countryCounter <= COUNTRIES_IN_QUIZ)
        {
            int randomIndex = random.nextInt(numberOfCountries);
            String filename = fileNameList.get(randomIndex);
            if (!quizCountriesList.contains(filename))
            {
                quizCountriesList.add(filename);
                ++countryCounter;
            }
        }
        loadNextCountry();
    }

    private void loadNextCountry()
    {
        onFirstTry = true; // on each new question, this bool is initialized to true and is only set to false when the user first guesses an incorrect answer
        //if (quizSignsList.size() == 0)
        //    return;
        String nextImage = quizCountriesList.remove(0);
        correctAnswer = nextImage;
        answerTextView.setText("");
        questionNumberTextView.setText(getString(R.string.question,(correctAnswers+1),COUNTRIES_IN_QUIZ));
        AssetManager assets = getActivity().getAssets();
        try(InputStream stream = assets.open("CountryOutlineImages/"+nextImage+".jpg"))
        {
            Drawable sign = Drawable.createFromStream(stream,nextImage);
            outlineImageView.setImageDrawable(sign);
            animate(false);
        }
        catch(IOException exception)
        {
            Log.e(TAG,"Error loading "+nextImage,exception);
        }
        Collections.shuffle(fileNameList);
        int correct = fileNameList.indexOf(correctAnswer);
        fileNameList.add(fileNameList.remove(correct));
        for(int row = 0; row < guessRows; ++row)
        {
            for(int column = 0; column < guessLinearLayouts[row].getChildCount(); column++)
            {
                Button newGuessButton = (Button)guessLinearLayouts[row].getChildAt(column);
                newGuessButton.setEnabled(true);
                String filename = fileNameList.get((row * 2)+column);
                newGuessButton.setText(getCountryName(filename));
            }
        }
        int row = random.nextInt(guessRows);
        int column = random.nextInt(2);
        LinearLayout randomRow = guessLinearLayouts[row];
        String countryName = getCountryName(correctAnswer);
        ((Button)randomRow.getChildAt(column)).setText(countryName);

        guessCountryTextView.setText("Guess the Country");
    }

    private String getCountryName(String name)
    {
        return name;
    }

    private void animate(boolean animateOut)
    {
        if (correctAnswers == 0)
            return;
        int centerX = (quizLinearLayout.getLeft() + quizLinearLayout.getRight())/2;
        int centerY = (quizLinearLayout.getTop() + quizLinearLayout.getBottom())/2;
        int radius = Math.max(quizLinearLayout.getWidth(),quizLinearLayout.getHeight());
        Animator animator;
        if (animateOut)
        {
            animator = ViewAnimationUtils.createCircularReveal(quizLinearLayout,centerX,centerY,radius,0);
            animator.addListener(new AnimatorListenerAdapter()
                                 {
                                     @Override
                                     public void onAnimationEnd(Animator animation)
                                     {
                                         loadNextCountry();
                                     }
                                 }
            );
        }
        else
        {
            animator = ViewAnimationUtils.createCircularReveal(quizLinearLayout,centerX,centerY,0,radius);
        }
        animator.setDuration(500);
        animator.start();
    }

    private void loadBonusQuestion()
    {
        for(int row = 0; row < guessRows; ++row) {
            for (int column = 0; column < guessLinearLayouts[row].getChildCount(); column++) {
                Button newGuessButton = (Button) guessLinearLayouts[row].getChildAt(column);
                newGuessButton.setEnabled(true);
            }
        }
        questionNumberTextView.setText("Bonus Question");
        while(options.size() < 2)
        {
            String capital = capitals[random.nextInt(capitals.length)];
            if (options.contains(capital) || (capital.equals(countryMap.get(correctAnswer.toUpperCase()))))
                continue;
            else
                options.add(capital);
        }
        options.remove(1); //remove last index's element
        options.add(countryMap.get(correctAnswer.toUpperCase()));
        Collections.shuffle(options);

        int count = 0;
        for(int row = 0; row < guessRows; ++row)
        {
            LinearLayout guessRow = guessLinearLayouts[row];
            for(int i = 0; i < guessRow.getChildCount(); i++)
            {
                ((Button)guessRow.getChildAt(i)).setText(options.get(count));
                count += 1;
            }
        }
        guessCountryTextView.setText(String.format("What is the Capital of %s",correctAnswer.toUpperCase()));
        answerTextView.setText("");
        options.clear();
    }
    private OnClickListener guessButtonListener = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Button guessButton = ((Button)v);
            String guess = guessButton.getText().toString();
            String answer = getCountryName(correctAnswer);
            ++totalGuesses;
            if (isBonusQuestion) //this will be the 2nd pass assuming the player got the previous question correct on the 1st try
            {
                for(int row = 0; row < guessRows; ++row) { //disable all other buttons on user input so user does not quickly click another button in the time postDelayed is taking to post the next animation
                    for (int column = 0; column < guessLinearLayouts[row].getChildCount(); column++) {
                        Button newGuessButton = (Button) guessLinearLayouts[row].getChildAt(column);
                        newGuessButton.setEnabled(false);
                    }
                }
                isBonusQuestion = false;
                if (guess.equals(countryMap.get(correctAnswer.toUpperCase()))) //if correct
                {
                    answerTextView.setText("CORRECT");
                    userScore += 10;
                    answerTextView.setTextColor(getResources().getColor(R.color.correct_answer, getContext().getTheme()));
                }
                else
                {
                    answerTextView.setText("WRONG. The answer is "+countryMap.get(correctAnswer.toUpperCase()));
                    answerTextView.setTextColor(getResources().getColor(R.color.incorrect_answer, getContext().getTheme()));
                }
                if (correctAnswers != COUNTRIES_IN_QUIZ) { //if this is the last question in the quiz, don't load a new one
                    handler.postDelayed(
                            new Runnable() {
                                @Override
                                public void run() {
                                    animate(true);
                                }
                            }, 2000);
                }
            }
            else if (isBonusQuestion == false) { //this is the 1st pass of a particular question
                if (guess.equalsIgnoreCase(answer)) {
                    for(int row = 0; row < guessRows; ++row) {
                        for (int column = 0; column < guessLinearLayouts[row].getChildCount(); column++) {
                            Button newGuessButton = (Button) guessLinearLayouts[row].getChildAt(column);
                            newGuessButton.setEnabled(false);
                        }
                    }
                    userScore += 50;
                    answerTextView.setTextColor(getResources().getColor(R.color.correct_answer, getContext().getTheme()));
                    ++correctAnswers;
                    answerTextView.setText("CORRECT");
                    if (onFirstTry) {
                        answerTextView.setText(answerTextView.getText() + " ON FIRST TRY");
                        //answerTextView.setText(correctAnswer.toUpperCase()+" : "+countryMap.get(correctAnswer.toUpperCase()));
                        isBonusQuestion = true;
                        handler.postDelayed(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        loadBonusQuestion();
                                    }
                                }, 2000);

                    }
                    else
                    {
                        if (correctAnswers != COUNTRIES_IN_QUIZ) {  //if this is the last question in the quiz, don't load a new one
                            handler.postDelayed(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            animate(true);
                                        }
                                    }, 2000);
                        }
                    }
                } else {
                    onFirstTry = false;
                    userScore -= 10;
                    outlineImageView.startAnimation(shakeAnimation);
                    answerTextView.setText(R.string.incorrect_answer);
                    answerTextView.setTextColor(getResources().getColor(R.color.incorrect_answer, getContext().getTheme()));
                    guessButton.setEnabled(false);
                }
            }
            //BUG(FIXED):last question does not get bonus because its not post delayed
            if (isBonusQuestion && onFirstTry) //prevent the AlertDialog below from displaying until the bonusQuestion is attended to, assuming user got question onFirstTry
                return;
            if (correctAnswers == COUNTRIES_IN_QUIZ) {
                ResultsDialogFragment  quizResults = new ResultsDialogFragment();
                quizResults.setCancelable(false);
                quizResults.show(getFragmentManager(), "quiz results");
            }
        }
    };


    private void disableButtons()
    {
        for(int row = 0; row < guessRows; ++row)
        {
            LinearLayout guessRow = guessLinearLayouts[row];
            for(int i = 0; i < guessRow.getChildCount(); i++)
                guessRow.getChildAt(i).setEnabled(false);
        }
    }
}

