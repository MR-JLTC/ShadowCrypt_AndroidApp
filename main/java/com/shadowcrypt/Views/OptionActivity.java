package com.shadowcrypt.Views;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.shadowcrypt.R;
import com.google.android.material.textfield.TextInputLayout;
import com.shadowcrypt.Views.Navigation.IntentBuilder;

public class OptionActivity extends AppCompatActivity {
    private TextInputLayout cipherTypeIL, processTypeIL;
    private static String cipherTypeSelected, processTypeSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_option);

        //TextInputLayout Config
        cipherTypeIL = findViewById(R.id.cipherTypeinputLayout);
        processTypeIL = findViewById(R.id.processTypeInputLayout);

        //CipherType...
        AutoCompleteTextView cipherType_autocompleteTV = findViewById(R.id.cipherType_Auto_complete_text);
        ArrayAdapter<String> cipherTypesAdapterItem = new ArrayAdapter<>(this, R.layout.item_list, getResources().getStringArray(R.array.cipherTypesItem));
        cipherType_autocompleteTV.setAdapter(cipherTypesAdapterItem);
        cipherType_autocompleteTV.setOnItemClickListener((parent, view, position, id) -> {
            cipherTypeSelected = parent.getItemAtPosition(position).toString();
            Toast.makeText(OptionActivity.this, "Selected: " + cipherTypeSelected, Toast.LENGTH_SHORT).show();
            cipherTypeIL.setBoxStrokeErrorColor(null);
            //To remove the error notif once got an option selected
            cipherTypeIL.setError("");
            cipherTypeIL.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
        });
        //ProcessType...
        AutoCompleteTextView process_autoCompleteTV = findViewById(R.id.processType_Auto_complete_text);
        ArrayAdapter<String> processTypeAdapterItem = new ArrayAdapter<>(this, R.layout.item_list, getResources().getStringArray(R.array.processTypesItem));
        process_autoCompleteTV.setAdapter(processTypeAdapterItem);
        process_autoCompleteTV.setOnItemClickListener((parent, view, position, id) -> {
            processTypeSelected = parent.getItemAtPosition(position).toString();
            Toast.makeText(OptionActivity.this, "Selected: "+processTypeSelected, Toast.LENGTH_SHORT).show();
            //To remove the error notif once got an option selected
            processTypeIL.setError("");
            processTypeIL.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
        });

        //Button Config
        Button prdBtn = findViewById(R.id.prdbtn);
        prdBtn.setTextColor(getResources().getColor(R.color.white));
        prdBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
        prdBtn.setOnClickListener(v -> onBtnClickExec());
    }

    private void onBtnClickExec(){
        boolean isCipherTypeNull = (cipherTypeSelected==null);
        boolean isProcessTypeNull = (processTypeSelected==null);
        if(isCipherTypeNull || isProcessTypeNull) {
            cipherTypeIL.setError((cipherTypeSelected==null) ? "Missing type" : null);
            processTypeIL.setError((processTypeSelected==null) ? "Missing type" : null);
            String msg = (isCipherTypeNull && isProcessTypeNull) ? "Missing values" :
                         (isCipherTypeNull) ? "No CipherType Selected" : "No ProcessType Selected";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }else{
            Intent actnScrn = new IntentBuilder(OptionActivity.this, ActionActivity.class)
                    .putExtras("cipherTypeSltd", cipherTypeSelected)
                    .putExtras("processTypeSltd", processTypeSelected)
                    .build();
            startActivity(actnScrn);
            Toast.makeText(this, "This works fine", Toast.LENGTH_SHORT).show();
        }
    }
}