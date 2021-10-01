package com.example.justjava;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

/**
 This app displays an order form to order coffee.
 */
public class MainActivity extends Activity {
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox WhippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = WhippedCreamCheckBox.isChecked();
        CheckBox Chocolate = findViewById(R.id.Chocolate);
        boolean hasChocolate = Chocolate.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        EditText Name = findViewById(R.id.name_text_field);
        String enteredName = Name.getText().toString();
        String priceMessage = createOrderSummery(price, hasWhippedCream, hasChocolate, enteredName);
//        displayMessage(priceMessage);
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"example@gmail.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, "JustJava order from "+ enteredName);
        email.putExtra(Intent.EXTRA_TEXT, priceMessage);

//need this to prompts email client only
        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }

    @NonNull
    private String createOrderSummery(int price, boolean addWhippedCream, boolean addChocolate, String nameEntered){
        String priceMessage = getString(R.string.name) + nameEntered;
        priceMessage += getString(R.string.add_whipped_cream)+ addWhippedCream;
        priceMessage += getString(R.string.add_chocolate)+ addChocolate;
        priceMessage += getString(R.string.quantity)+ quantity;
        priceMessage += getString(R.string.total)+price;
        priceMessage += getString(R.string.Thank_you);
        return priceMessage;
    }

    /**
     * Calculates the price of the order.
     * is the number of cups of coffee ordered
     */
    private int calculatePrice(boolean whipped, boolean choco) {
        int Bprice =  5;
        if(whipped){
            Bprice += 2;
        }

        if(choco){
            Bprice += 1;
        }
        return quantity * Bprice;
    }

    /**
     * This method will increament the number of coffees
     */
    public void increament(View view) {
        if(quantity==100){
            Context context = getApplicationContext();
            CharSequence text = getString(R.string.toast_greater_than_100);
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else{
            quantity += 1;
            display(quantity);
        }
    }

    /**
     * This method will decreament the number of coffees
     */
    public void decreament(View view) {
        if(quantity==1){
            Context context = getApplicationContext();
            CharSequence text = getString(R.string.toast_less_than_one);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else{
            quantity -= 1;
            display(quantity);
        }
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    @SuppressLint("SetTextI18n")
    private void display(int number) {
        TextView quantityTextView = findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /*
      This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
}