package edu.gatech.seclass.tipcalculator;

import android.content.Context;
//import android.icu.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TipCalculatorActivity extends AppCompatActivity {
    EditText checkAmountV, partySizeV;
    EditText twentyPercentTipV, twentyfivePercentTipV, fifteenPercentTipV;
    EditText twentyPercentTotalV,twentyfivePercentTotalV, fifteenPercentTotalV;
    Button btCompute, btReset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);
        checkAmountV = (EditText) findViewById(R.id.checkAmountValue);
        partySizeV = (EditText) findViewById(R.id.partySizeValue);

        fifteenPercentTipV = (EditText) findViewById(R.id.fifteenPercentTipValue);
        twentyPercentTipV = (EditText) findViewById(R.id.twentyPercentTipValue);
        twentyfivePercentTipV = (EditText) findViewById(R.id.twentyfivePercentTipValue);

        twentyPercentTotalV = (EditText) findViewById(R.id.twentyPercentTotalValue);
        twentyfivePercentTotalV = (EditText) findViewById(R.id.twentyfivePercentTotalValue);
        fifteenPercentTotalV = (EditText) findViewById(R.id.fifteenPercentTotalValue);
        btCompute = (Button) findViewById(R.id.buttonCompute);
        btReset = (Button) findViewById(R.id.buttonReset);
    }


    private static String tipValue(String ckAmountValue, String ptSizeValue, double tipRate){
        double checkAmount = Double.parseDouble(ckAmountValue);
        double partySize = Double.parseDouble(ptSizeValue);
        int result = (int)Math.round(checkAmount/partySize * tipRate);
        return String.valueOf(result);
    }

    private static String totalValue(String ckAmountValue, String ptSizeValue, double tipRate){
        double checkAmount = Double.parseDouble(ckAmountValue);
        int partySize = Integer.parseInt(ptSizeValue);
        double totalValue = checkAmount/partySize + checkAmount/partySize * tipRate;
        return String.valueOf(totalValue);
    }

    public void handleClick(View v){
        switch (v.getId()){
            case R.id.buttonCompute:
                // calculate values for '''
                String amount = checkAmountV.getText().toString();
                String partySize = partySizeV.getText().toString();
                if (partySize == null ||  amount== null || partySize == "" ||  amount== "") {
                    Context context = getApplicationContext();
                    CharSequence text = "Empty or incorrect value(s)!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context,text,duration);
                    toast.show();
                    break;
                }
                if (!amount.endsWith(".") && amount.contains(".")) {
                    String numberAfterDecimal = amount.split("\\.")[1];
                    if (numberAfterDecimal.length() > 2) {
                        Context context = getApplicationContext();
                        CharSequence text = "Empty or incorrect value(s)!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        break;
                    }
                }
                if (partySize.contains(".")  ) {
                    Context context = getApplicationContext();
                    CharSequence text = "Empty or incorrect value(s)!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context,text,duration);
                    toast.show();
                    break;
                    }


                else {
                    fifteenPercentTipV.setText(tipValue(amount,partySize,0.15));
                    twentyPercentTipV.setText(tipValue(amount,partySize,0.20));
                    twentyfivePercentTipV.setText(tipValue(amount,partySize,0.25));
                    fifteenPercentTotalV.setText(totalValue(amount,partySize,0.15));
                    twentyPercentTotalV.setText(totalValue(amount,partySize,0.20));
                    twentyfivePercentTotalV.setText(totalValue(amount,partySize,0.25));
                }

                break;

            case R.id.buttonReset:
                //set all text values back to ""
                checkAmountV.setText("");
                partySizeV.setText("");
                fifteenPercentTipV.setText("");
                twentyPercentTipV.setText("");
                twentyfivePercentTipV.setText("");
                twentyPercentTotalV.setText("");
                twentyfivePercentTotalV.setText("");
                fifteenPercentTotalV.setText("");
                break;

        }

    }
}