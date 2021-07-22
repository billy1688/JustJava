package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    final int MAX = 100;
    final int MIN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String subject = "Order of " + getName();
        String message = createOrderSummary();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public String createOrderSummary() {
        String message = getString(R.string.name) + getName();
        message += "\nAdd whipped cream? " + addWhippedCream();
        message += "\nAdd coffee? " + addCoffice();
        message += "\nQuantity: " + quantity;
        message += "\nTotal: $" + calculatePrice();
        message += "\nThank you!";
        return message;
    }

    private String getName() {
        EditText editText = (EditText) findViewById(R.id.edit_text);
        return editText.getText().toString();
    }

    private Boolean addWhippedCream() {
        CheckBox checkbox = (CheckBox) findViewById(R.id.check_box);
        return checkbox.isChecked();
    }

    private Boolean addCoffice() {
        CheckBox checkbox = (CheckBox) findViewById(R.id.check_box_2);
        return checkbox.isChecked();
    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice() {
        int basePrice = 5;
        if (addWhippedCream()) {
            basePrice += 1;
        }
        if (addCoffice()) {
            basePrice += 2;
        }
        return quantity * basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    public void increment(View view) {
        if (quantity == MAX) {
            Toast.makeText(this, "Maximum of cups", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity += 1;
        display(quantity);
    }

    public void decrement(View view) {
        if (quantity == MIN) {
            Toast.makeText(this, "Manimum of cups", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity -= 1;
        display(quantity);
    }
}