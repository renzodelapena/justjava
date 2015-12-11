package com.example.android.renzojustjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view) {
        EditText getName = (EditText)findViewById(R.id.name_field);
        String nameValue = getName.getText().toString();
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        CheckBox sugarCheckBox = (CheckBox) findViewById(R.id.sugar_checkbox);
        boolean hasSugar = sugarCheckBox.isChecked();
        CheckBox cookieCheckBox = (CheckBox) findViewById(R.id.cookie_checkbox);
        boolean hasCookie = cookieCheckBox.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate, hasSugar, hasCookie);
        String priceMessage = createOrderSummary(nameValue, price, hasWhippedCream,hasChocolate,hasSugar, hasCookie);
        /*displayMessage(priceMessage);*/
        // Use an intent to launch an email app.
        // Send the order summary in the email body.
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + nameValue);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    private String createOrderSummary(String userName, int price, boolean addWhippedCream, boolean addChocolate, boolean addSugar, boolean addCookie){
        String priceMessage = "Name: " + userName;
        priceMessage = priceMessage + "\nAdd Whipped Cream? " + addWhippedCream;
        priceMessage = priceMessage + "\nAdd Chocolate? " + addChocolate;
        priceMessage = priceMessage + "\nAdd Sugar? " + addSugar;
        priceMessage = priceMessage + "\nAdd Cookie? " + addCookie;
        priceMessage = priceMessage + "\nQuantity: " + quantity;
        priceMessage = priceMessage + "\nTotal: $" + price;
        priceMessage = priceMessage + "\nThank you!";
        return priceMessage;
    }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate, boolean addSugar, boolean addCookie) {
        int basePrice = 5;
        if (addWhippedCream){
            basePrice = basePrice + 1;
        }
        if (addChocolate){
            basePrice = basePrice + 2;
        }
        if (addSugar){
            basePrice = basePrice + 1;
        }
        if (addCookie){
            basePrice = basePrice + 2;
        }
        return quantity * basePrice;

    }
    /**
     *
     * This method is called when the order button is clicked.
     */
    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */

    public void increment(View view) {
        if (quantity == 15) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 15 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }
    public void decrement(View view) {
        if (quantity <= 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }
}