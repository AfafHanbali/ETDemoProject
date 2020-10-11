package com.example.ettdemoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

//TODO : any activity i create must always has suffix '$Activity'.
public class UserInformation extends AppCompatActivity {

    //TODO : how could later tell the difference between 'id' as a view and 'id' as an object property ?!
    private TextView id;
    private TextView name;
    private TextView street;
    private TextView suite;
    private TextView city;
    private TextView zipCode;
    private TextView lat;
    private TextView lng;
    private TextView phone;
    private TextView website;
    private TextView companyName;
    private TextView catchPhrase;
    private TextView bs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        id = findViewById(R.id.tv_id);
        name = findViewById(R.id.tv_name);
        street = findViewById(R.id.tv_street);
        suite = findViewById(R.id.tv_suite);
        city = findViewById(R.id.tv_city);
        zipCode = findViewById(R.id.tv_zipcode);
        lat = findViewById(R.id.tv_lat);
        lng = findViewById(R.id.tv_lng);
        phone = findViewById(R.id.tv_phone);
        website = findViewById(R.id.tv_website);
        companyName = findViewById(R.id.tv_companyName);
        catchPhrase = findViewById(R.id.tv_catchPhrase);
        bs = findViewById(R.id.tv_bs);

        setFieldsText();

    }

    public void setFieldsText() {
        Intent intent = getIntent();
        //TODO : if one of the keys is missing , it will return null and that might write null on the screen or cause runTimeException !
        id.setText(intent.getStringExtra("id"));
        name.setText(intent.getStringExtra("name"));
        street.setText(intent.getStringExtra("street"));
        suite.setText(intent.getStringExtra("suite"));
        city.setText(intent.getStringExtra("city"));
        zipCode.setText(intent.getStringExtra("zipCode"));
        lat.setText(intent.getStringExtra("lat"));
        lng.setText(intent.getStringExtra("lng"));
        phone.setText(intent.getStringExtra("phone"));
        website.setText(intent.getStringExtra("website"));
        companyName.setText(intent.getStringExtra("companyName"));
        catchPhrase.setText(intent.getStringExtra("catchPhrase"));
        bs.setText(intent.getStringExtra("bs"));
    }

}
