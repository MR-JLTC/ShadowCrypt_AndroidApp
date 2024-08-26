package com.shadowcrypt.Views;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.shadowcrypt.R;
import com.google.android.material.textfield.TextInputEditText;
import com.shadowcrypt.ViewModel.ActionViewModel;
import com.shadowcrypt.ViewModel.CipherViewModel;

import java.util.Objects;

public class ActionActivity extends AppCompatActivity {
    private TextInputEditText keyInputET;
    private TextInputEditText msgInputET;
    private TextInputEditText outputRsltET;
    private CipherViewModel cipherViewModel;
    private ActionViewModel actionViewModel;
    private String ctype, ptype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_action);

        //Views Configs
        keyInputET = findViewById(R.id.keyET);
        msgInputET = findViewById(R.id.MsgTextET);
        outputRsltET = findViewById(R.id.OUtputRsltET);

        Button processBtn = findViewById(R.id.actionBtn);
        processBtn.setTextColor(getResources().getColor(R.color.white));
        processBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
        processBtn.setOnClickListener(v -> onBtnClickExec());

        //Getting the arguments value from the Intent
        ctype = Objects.requireNonNull(getIntent().getStringExtra("cipherTypeSltd"));
        ptype = Objects.requireNonNull(getIntent().getStringExtra("processTypeSltd"));

        //ViewModels Configs
        cipherViewModel = new ViewModelProvider(this).get(CipherViewModel.class);
        actionViewModel = new ViewModelProvider(this).get(ActionViewModel.class);
        actionViewModel.setValuesBasedOnSelections(ctype, ptype);

        String text = Objects.requireNonNull(msgInputET.getText()).toString();
        String key = Objects.requireNonNull(keyInputET.getText()).toString();

        //ViewModel LiveData trigger
        actionViewModel.fieldStateAnalyzer(key, text);
        actionViewModel.inputConstraintAnalyzer(ctype);

        //ViewModels LiveData, Observation for changes
        cipherViewModel.outputText().observe(this, outputRsltET::setText);
        actionViewModel.isThereABlankField().observe(this, isThereABlankField -> {
            Runnable exec_state = isThereABlankField ?
                    ActionActivity.this::initDefOutputResultETState :
                    ActionActivity.this::initOutputResultETState;
            exec_state.run();
        });
        actionViewModel.isKeyInputBlank().observe(this, isBlank -> {
            if(isBlank) keyInputET.setError("missing key value");
            else keyInputET.setError(null);
        });
        actionViewModel.isTextInputBlank().observe(this, isBlank -> {
            if(isBlank) msgInputET.setError("missing text value");
            else msgInputET.setError(null);
        });
        actionViewModel.isNumericsOnly().observe(this, isNumerics ->{
            if(isNumerics) keyInputET.setInputType(InputType.TYPE_CLASS_NUMBER);
        });
        actionViewModel.isLettersOnly().observe(this, isLetterOnly ->{
            if(isLetterOnly) {
                InputFilter lettersOnlyFilter = (source, start, end, dest, dstart, dend) -> {
                    for (int i = start; i < end; i++) {
                        if (!Character.isLetter(source.charAt(i)) && !Character.isSpaceChar(source.charAt(i))) {
                            return "";
                        }
                    }
                    return null;
                };
                keyInputET.setFilters(new InputFilter[]{lettersOnlyFilter});
            }
        });
        actionViewModel.isNoInputConstraint().observe(this, isNoConstraint -> {
            if(isNoConstraint) keyInputET.setInputType(InputType.TYPE_CLASS_TEXT);
        });
        actionViewModel.keyInputLabelHint().observe(this, keyInputET::setHint);
        actionViewModel.actBtnHint().observe(this, processBtn::setText);
    }

    private void onBtnClickExec(){
        try {
            String text = Objects.requireNonNull(msgInputET.getText()).toString();
            String key = Objects.requireNonNull(keyInputET.getText()).toString();
            actionViewModel.fieldStateAnalyzer(key, text);
            cipherViewModel.setInputText(text);
            cipherViewModel.setKey(key);
            cipherViewModel.processResult(ctype, ptype);
        }catch(Exception exp){
            Toast.makeText(this, exp.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initOutputResultETState(){
        outputRsltET.setFocusableInTouchMode(true);
        outputRsltET.setLongClickable(true);
        outputRsltET.setFocusable(true);
        outputRsltET.setTextIsSelectable(true);
        outputRsltET.setEnabled(true);
    }

    private void initDefOutputResultETState(){
        outputRsltET.setFocusableInTouchMode(false);
        outputRsltET.setLongClickable(false);
        outputRsltET.setFocusable(false);
        outputRsltET.setTextIsSelectable(false);
        outputRsltET.setEnabled(false);
    }
}