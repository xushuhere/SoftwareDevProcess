package edu.gatech.seclass.tipcalculator;

import android.content.Context;
import android.icu.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TipCalculatorActivity extends AppCompatActivity {
    private EditText checkAmountValue;
    private EditText partySizeValue;

    private EditText fifteenPercentTipValue;
    private EditText twentyPercentTipValue;
    private EditText twentyfivePercentTipValue;

    private EditText twentyPercentTotalValue;
    private EditText twentyfivePercentTotalValue;
    private EditText fifteenPercentTotalValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);
        checkAmountValue= (EditText) findViewById(R.id.checkAmountValue);
        partySizeValue= (EditText) findViewById(R.id.partySizeValue);

        fifteenPercentTipValue= (EditText) findViewById(R.id.fifteenPercentTipValue);
        twentyPercentTipValue= (EditText) findViewById(R.id.twentyPercentTipValue);
        twentyfivePercentTipValue= (EditText) findViewById(R.id.twentyfivePercentTipValue);


        twentyPercentTotalValue= (EditText) findViewById(R.id.twentyPercentTotalValue);
        twentyfivePercentTotalValue= (EditText) findViewById(R.id.twentyfivePercentTotalValue);
        fifteenPercentTotalValue= (EditText) findViewById(R.id.fifteenPercentTotalValue);
    }




    private static String tipvalue(String checkAmountValue, String partySizeValue, double tipRate){
        double checkAmount = Double.parseDouble(checkAmountValue);
        double partySize = Double.parseDouble(partySizeValue);
        int tipValue = (int)Math.floor(checkAmount/partySize * tipRate);
        return String.valueOf(tipValue);
    }

    private static String totalValue(String checkAmountValue, String partySizeValue, double tipRate){
        double checkAmount = Double.parseDouble(checkAmountValue);
        double partySize = Double.parseDouble(partySizeValue);
        double totalValue = checkAmount/partySize + Math.round(checkAmount/partySize * tipRate);
        return String.format("%.2f", String.valueOf(totalValue));
    }

    public void handleClick(View view){
        switch (view.getId()){
            case R.id.buttonCompute:
                // calculate values for '''
                String amount = checkAmountValue.getText().toString();
                String partySize = checkAmountValue.getText().toString();
                if (android.text.TextUtils.isDigitsOnly(amount) && android.text.TextUtils.isDigitsOnly(partySize)
                        && Double.parseDouble(partySize) != 0.0 ){
                    fifteenPercentTipValue.setText(tipvalue(amount,partySize,0.15));
                    twentyPercentTipValue.setText(tipvalue(amount,partySize,0.20));
                    twentyfivePercentTipValue.setText(tipvalue(amount,partySize,0.25));
                    fifteenPercentTotalValue.setText(totalValue(amount,partySize,0.15));
                    twentyPercentTotalValue.setText(totalValue(amount,partySize,0.20));
                    twentyfivePercentTotalValue.setText(totalValue(amount,partySize,0.25));
                }
                else{
                    Context context =getApplicationContext();
                    CharSequence text = "Empty or incorrent value(s)!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context,text,duration);
                    toast.show();
                }

                checkAmountValue.setText("");
                partySizeValue.setText("");
                fifteenPercentTipValue.setText("");
                twentyPercentTipValue.setText("");
                twentyfivePercentTipValue.setText("");
                twentyPercentTotalValue.setText("");
                twentyfivePercentTotalValue.setText("");
                fifteenPercentTotalValue.setText("");
            case R.id.buttonReset:
                //set all text values back to ""
                checkAmountValue.setText("");
                partySizeValue.setText("");
                fifteenPercentTipValue.setText("");
                twentyPercentTipValue.setText("");
                twentyfivePercentTipValue.setText("");
                twentyPercentTotalValue.setText("");
                twentyfivePercentTotalValue.setText("");
                fifteenPercentTotalValue.setText("");
                break;

        }

    }



    //private float checkAmountValue(){}
    //private float checkAmountValue(){}
    //private float checkAmountValue(){}
    // private float checkAmountValue(){}
    // private float checkAmountValue(){}
    // private float checkAmountValue(){}
}
