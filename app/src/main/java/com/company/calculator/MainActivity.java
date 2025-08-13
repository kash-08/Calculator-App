package com.company.calculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.company.calculator.databinding.ActivityMainBinding;
import com.google.android.material.appbar.MaterialToolbar;

import net.objecthunter.exp4j.ExpressionBuilder;

import org.mariuszgromada.math.mxparser.Expression;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;
    String number=null;
    int countOpenPar=0;
    int countClosePar=0;
    boolean operator =false;
    boolean dotControl=false;
    boolean isDegree=true;
    String result="";
    boolean ButtonEqualsControl=false;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        mainBinding.textViewResult.setText("0");
        sharedPreferences=this.getSharedPreferences("com.company.calculator", Context.MODE_PRIVATE);

        mainBinding.btn0.setOnClickListener(v -> {
            OnNumberClicked("0");
        });
        mainBinding.btn1.setOnClickListener(v -> {
            OnNumberClicked("1");
        });
        mainBinding.btn2.setOnClickListener(v -> {
            OnNumberClicked("2");
        });
        mainBinding.btn3.setOnClickListener(v -> {
            OnNumberClicked("3");
        });
        mainBinding.btn4.setOnClickListener(v -> {
            OnNumberClicked("4");
        });
        mainBinding.btn5.setOnClickListener(v -> {
            OnNumberClicked("5");
        });
        mainBinding.btn6.setOnClickListener(v -> {
            OnNumberClicked("6");
        });
        mainBinding.btn7.setOnClickListener(v -> {
            OnNumberClicked("7");
        });
        mainBinding.btn8.setOnClickListener(v -> {
            OnNumberClicked("8");
        });
        mainBinding.btn9.setOnClickListener(v -> {
            OnNumberClicked("9");
        });
        mainBinding.btnbrac1.setOnClickListener(v -> {
            OnParClicked("(");
            countOpenPar++;
        });
        mainBinding.btnbrac2.setOnClickListener(v -> {
            if(countOpenPar>countClosePar){
                OnParClicked(")");
                countClosePar++;
            }
        });
        mainBinding.btnop1.setOnClickListener(v -> {
            if(!operator && !dotControl){
                if(number==null){
                    number="0+";
                }else if(ButtonEqualsControl){
                    number = result + "+";

                }
                else{
                    number+="+";
                }
                mainBinding.textViewResult.setText(number);
                operator=true;
                dotControl=true;
                ButtonEqualsControl=false;
            }
        });
        mainBinding.btnop2.setOnClickListener(v -> {
            if(!operator && !dotControl){
                if(number==null){
                    number="0-";
                }else if(ButtonEqualsControl) {
                    number = result + "-";
                }
                else{
                    number+="-";
                }
                mainBinding.textViewResult.setText(number);
                operator=true;
                dotControl=true;
                ButtonEqualsControl=false;
            }
        });
        mainBinding.btnop3.setOnClickListener(v -> {
            if(!operator && !dotControl){
                if(number==null){
                    number="0*";
                }else if(ButtonEqualsControl) {
                    number = result + "*";
                }
                else{
                    number+="*";
                }
                mainBinding.textViewResult.setText(number);
                operator=true;
                dotControl=true;
                ButtonEqualsControl=false;
            }
        });
        mainBinding.btnop4.setOnClickListener(v -> {
            if(!operator&&!dotControl){
                if(number==null){
                    number="0/";
                }else if(ButtonEqualsControl) {
                    number = result + "/";
                }
                else{
                    number+="/";
                }
                mainBinding.textViewResult.setText(number);
                operator=true;
                dotControl=true;
                ButtonEqualsControl=false;
            }
        });
        mainBinding.btnDot.setOnClickListener(v -> {
            if(!dotControl && !operator){
                if (ButtonEqualsControl){
                    if(!result.contains(".")){
                        number=result+".";
                        mainBinding.textViewResult.setText(number);
                        dotControl=true;
                        ButtonEqualsControl=false;
                    }

                }else{
                    if(number==null){
                        number="0.";
                        mainBinding.textViewResult.setText(number);
                        dotControl=true;
                        operator=true;

                    }else{
                        String expressionAfterLastOperator="";
                        String lastCharacter;
                        dootLoop:for(int i=number.length()-1;i>=0;i--){
                            lastCharacter=String.valueOf(number.charAt(i));
                            switch(lastCharacter){
                                case "+": case "-": case "*": case "/":
                                    break dootLoop;

                                default:
                                    expressionAfterLastOperator=lastCharacter.concat(expressionAfterLastOperator);
                                    break;
                            }
                        }
                        if(!expressionAfterLastOperator.contains(".")){
                            number+=".";
                            mainBinding.textViewResult.setText(number);
                            dotControl=true;
                            operator=true;

                        }
                    }

                }

            }
        });
        mainBinding.btne.setOnClickListener(v -> {
            if (number == null) number = "";
            number += Math.E; // Inserts the value of e
            mainBinding.textViewResult.setText(number);
        });

        mainBinding.btnsin.setOnClickListener(v -> {
            if (number == null) number = "";
            number += "sin(";
            mainBinding.textViewResult.setText(number);
            countOpenPar++;
        });

        mainBinding.btncos.setOnClickListener(v -> {
            if (number == null) number = "";
            number += "cos(";
            mainBinding.textViewResult.setText(number);
            countOpenPar++;
        });

        mainBinding.btndeg.setOnClickListener(v -> {
            isDegree = !isDegree;
            mainBinding.btndeg.setText(isDegree ? "deg" : "rad");
        });



        mainBinding.btnC.setOnClickListener(v -> {
            OnButtonACClicked();

        });
        mainBinding.btndel.setOnClickListener(v -> {
            if(number==null || number.length()==1){
                OnButtonACClicked();
            }else{
                String lastChar;
                lastChar=String.valueOf(number.charAt(number.length()-1));
                switch(lastChar){
                    case "+": case "-": case "*": case "/" : case ".":
                        operator=false;
                        dotControl=false;
                        break;
                    case "(":
                        countOpenPar--;
                        break;
                    case ")":
                        countClosePar--;
                        break;
                }
                number=number.substring(0,number.length()-1);
                mainBinding.textViewResult.setText(number);
                lastChar=String.valueOf(number.charAt(number.length()-1));
                switch(lastChar){
                    case "+": case "-": case "*": case "/" : case ".":
                        operator=true;
                        dotControl=true;
                        break;
                }
            }

        });
        mainBinding.btnop5.setOnClickListener(v ->  {
            String expressionForCalculate=mainBinding.textViewResult.getText().toString();

            int difference=countOpenPar-countClosePar;
            if (difference >0){
                for(int i=0;i<difference;i++){
                    expressionForCalculate=expressionForCalculate.concat(")");
                }
            }

            Expression expression=new Expression(expressionForCalculate);
            result=String.valueOf(expression.calculate());

            if(result.equals("NaN")){
                checkDivisior(expressionForCalculate);

            }else{
                int indexOfDot=result.indexOf(".");
                String expressionAfterDot=result.substring(indexOfDot+1);
                if(expressionAfterDot.equals("0")){
                    result=result.substring(0,indexOfDot);
                }

                mainBinding.textViewResult.setText(result);
                mainBinding.textViewHistory.setText(expressionForCalculate.concat("=").concat(result));

                ButtonEqualsControl=true;
                operator=false;
                dotControl=false;
                countOpenPar=0;
                countClosePar=0;
                CalculateResult();

            }

        });

        mainBinding.toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.settingsitem) {
                Intent intent = new Intent(MainActivity.this, ChangeThemeActivity.class);
                startActivity(intent);
                return true;
            }else{
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isDarkMode=sharedPreferences.getBoolean("switch", false);
        if (isDarkMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor=sharedPreferences.edit();


        editor.putString("resultText", mainBinding.textViewResult.getText().toString());
        editor.putString("historyText", mainBinding.textViewHistory.getText().toString());
        editor.putString("result", result);
        editor.putString("number", number);
        editor.putBoolean("operator", operator);
        editor.putBoolean("dotControl", dotControl);
        editor.putBoolean("ButtonEqualsControl", ButtonEqualsControl);
        editor.putInt("countOpenPar", countOpenPar);
        editor.putInt("countClosePar", countClosePar);
        editor.putBoolean("isDegree", isDegree);

        editor.apply();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mainBinding.textViewResult.setText(sharedPreferences.getString("resultText", "0"));
        mainBinding.textViewHistory.setText(sharedPreferences.getString("historyText", ""));
        result=sharedPreferences.getString("result", "");
        number=sharedPreferences.getString("number", null);
        operator=sharedPreferences.getBoolean("operator", false);
        dotControl=sharedPreferences.getBoolean("dotControl", false);
        ButtonEqualsControl=sharedPreferences.getBoolean("ButtonEqualsControl", false);
        countOpenPar=sharedPreferences.getInt("countOpenPar", 0);
        countClosePar=sharedPreferences.getInt("countClosePar", 0);
        isDegree=sharedPreferences.getBoolean("isDegree", true);
    }

    public void OnNumberClicked(String clickedNumber){
        if(number==null || ButtonEqualsControl){
            number=clickedNumber;
        }else{
            number=number+clickedNumber;
        }
        mainBinding.textViewResult.setText(number);
        operator=false;
        dotControl=false;
        ButtonEqualsControl=false;
    }
    public void OnParClicked(String par){
        if(number==null || ButtonEqualsControl){
            number=par;
        }else{
            number=number+par;
        }
        mainBinding.textViewResult.setText(number);
        ButtonEqualsControl=false;
    }
    private void CalculateResult() {
        try {
            String expressionForCalculate = number;
            if (isDegree) {
                expressionForCalculate = expressionForCalculate.replaceAll("sin\\(", "sin([deg] ");
                expressionForCalculate = expressionForCalculate.replaceAll("cos\\(", "cos([deg] ");
            }

            Expression expression = new Expression(expressionForCalculate);
            double calcResult = expression.calculate();

            if (Math.floor(calcResult) == calcResult) {
                result = String.valueOf((long) calcResult);
            } else {
                result = String.valueOf(calcResult);
            }

            mainBinding.textViewResult.setText(result);
            mainBinding.textViewHistory.setText(number + "=" + result);

            number = result;
        } catch (Exception e) {
            mainBinding.textViewResult.setText("Error");
        }
    }

    public void OnButtonACClicked(){
        number=null;
        mainBinding.textViewResult.setText("0");
        mainBinding.textViewHistory.setText("");
        operator=false;
        dotControl=false;
        countOpenPar=0;
        countClosePar=0;
        isDegree=true;
        ButtonEqualsControl=false;
        result="";

    }
    public void checkDivisior(String expressionForCalculate){
        if(expressionForCalculate.contains("/")){
            int indexOfSlash=expressionForCalculate.indexOf("/");
            String expressionAfterSlash=expressionForCalculate.substring(indexOfSlash+1);

            if(expressionAfterSlash.equals(")")){
                int closingPar=0, openingPar=0;
                for(int i=0;i<expressionForCalculate.length();i++){
                    String isPar=String.valueOf(expressionForCalculate.charAt(i));
                    if(isPar.equals("(")){
                        openingPar++;
                    }else if(isPar.equals(")")){
                        closingPar++;
                    }
                }
                int diff=openingPar-closingPar;
                if (diff >0){
                    for(int i=0;i<diff;i++){
                        expressionAfterSlash="(".concat(expressionAfterSlash);
                    }
                }
            }
            Expression expression=new Expression(expressionAfterSlash);
            String newResult=String.valueOf(expression.calculate());
            if(newResult.equals("0.0")){
                mainBinding.textViewHistory.setText("Divisor can't be zero");
            }else{
                checkDivisior(expressionAfterSlash);
            }

        }else{
            mainBinding.textViewHistory.setText("Syntax Error");
        }
    }

}