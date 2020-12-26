package com.solaris_studio.cafeorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateOrderActivity extends AppCompatActivity {
    private TextView textViewHello;
    private TextView textViewAdditions;
    private CheckBox checkBoxMilk;
    private CheckBox checkBoxSugar;
    private CheckBox checkBoxLemon;
    private Spinner spinnerTea;
    private Spinner spinnerCoffee;

    private String name;
    private String password;
    private String drink;
    private StringBuilder bulderAdditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        Intent intent = getIntent();
        if (intent.hasExtra("name") && intent.hasExtra("password")) {
            name = intent.getStringExtra("name");
            password = intent.getStringExtra("password");
        }else{
            name = getString(R.string.default_name);
            password = getString(R.string.default_password);
        }
        drink = getString(R.string.tea);
        textViewHello = findViewById(R.id.textViewHello);
        String hello = String.format(getString(R.string.hello_user), name);
        textViewHello.setText(hello);
        textViewAdditions = findViewById(R.id.textViewAdditions);
        String additions = String.format(getString(R.string.text_view_add), drink);
        textViewAdditions.setText(additions);
        checkBoxMilk = findViewById(R.id.checkboxMilk);
        checkBoxSugar = findViewById(R.id.checkboxSugar);
        checkBoxLemon = findViewById(R.id.checkboxLemon);
        spinnerTea = findViewById(R.id.spinnerTea);
        spinnerCoffee = findViewById(R.id.spinnerCoffee);
        bulderAdditions = new StringBuilder();
    }

    public void onClickChangeDrink(View view) {
        RadioButton button = (RadioButton) view;
       int id = button.getId();
       if (id == R.id.radioButtonTea){
           drink = getString(R.string.tea);
           spinnerTea.setVisibility(View.VISIBLE);
           spinnerCoffee.setVisibility(View.INVISIBLE);
           checkBoxLemon.setVisibility(View.VISIBLE);
       } else if (id == R.id.radioBittonCoffee){
           drink = getString(R.string.coffee);
           spinnerTea.setVisibility(View.INVISIBLE);
           spinnerCoffee.setVisibility(View.VISIBLE);
           checkBoxLemon.setVisibility(View.INVISIBLE);
       }
       String additions = String.format(getString(R.string.text_view_add), drink);
       textViewAdditions.setText(additions);
    }

    public void onClickSendOrder(View view) {
        bulderAdditions.setLength(0);
        if (checkBoxMilk.isChecked()){
            bulderAdditions.append(getString(R.string.milk)).append(" ");
        }
        if (checkBoxSugar.isChecked()){
            bulderAdditions.append(getString(R.string.sugar)).append(" ");
        }
        if (checkBoxLemon.isChecked() && drink.equals(getString(R.string.tea))){
            bulderAdditions.append(getString(R.string.lemon)).append(" ");
        }
        String optionsOfDrink = "";
        if (drink.equals(getString(R.string.tea))){
            optionsOfDrink = spinnerTea.getSelectedItem().toString();
        } else{
            optionsOfDrink = spinnerCoffee.getSelectedItem().toString();
        }
        String order = String.format(getString(R.string.order), name, password, drink, optionsOfDrink);
        String additions;
        if(bulderAdditions.length() > 0) {
            additions = getString(R.string.need_addtions) + bulderAdditions.toString();
        } else {
            additions = "";
        }
        String fullOrder = order + additions;
        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra("order", fullOrder);
        startActivity(intent);
    }
}