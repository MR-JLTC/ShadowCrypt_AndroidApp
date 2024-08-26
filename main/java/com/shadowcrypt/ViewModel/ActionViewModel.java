package com.shadowcrypt.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shadowcrypt.EnumUtility.CIPHER_TYPE;

public class ActionViewModel extends ViewModel {
    /*Eager Instantiations*/
    private final MutableLiveData<String> keyInputLabel = new MutableLiveData<>();
    private final MutableLiveData<String> actBtn = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isThereABlankField = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isKeyInputBlank = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isTextInputBlank = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isNumericsOnly = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLettersOnly = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isNoInputConstraint = new MutableLiveData<>();

    public void fieldStateAnalyzer(String keyETVal, String textETVal){
        if((keyETVal==null || keyETVal.isBlank()) || (textETVal==null || textETVal.isBlank())){
            isThereABlankField.setValue(true);
            isKeyInputBlank.setValue((keyETVal== null || keyETVal.isBlank()));
            isTextInputBlank.setValue((textETVal == null || textETVal.isBlank()));
        }else isThereABlankField.setValue(false);
    }

    public void inputConstraintAnalyzer(String cipherType){
        switch (cipherType){
            case "Shift Cipher","Shift Cipher ASCII" -> isNumericsOnly.setValue(true);
            case "Vigenere Cipher" -> isLettersOnly.setValue(true);
            case "Vigenere Cipher ASCII", "Vernam Cipher ASCII" -> isNoInputConstraint.setValue(true);
            default -> {
                isNumericsOnly.setValue(false);
                isLettersOnly.setValue(false);
                isNoInputConstraint.setValue(false);
            }
        }
    }

    public MutableLiveData<Boolean> isKeyInputBlank() {return isKeyInputBlank;}
    public MutableLiveData<Boolean> isTextInputBlank() {return isTextInputBlank;}
    public MutableLiveData<Boolean> isThereABlankField() {return isThereABlankField;}
    public MutableLiveData<String> keyInputLabelHint() {
        return keyInputLabel;
    }
    public MutableLiveData<Boolean> isNumericsOnly() {return isNumericsOnly;}
    public MutableLiveData<Boolean> isLettersOnly() {return isLettersOnly;}
    public MutableLiveData<Boolean> isNoInputConstraint() {return isNoInputConstraint;}
    public MutableLiveData<String> actBtnHint() {
        return actBtn;
    }

    public void setValuesBasedOnSelections(String cipherType, String processType){
        final String BtnLabel = processType.equals("Encryption") ? "Encrypt" : "Decrypt";
        final String keyLabel = switch(cipherType){
            case "Shift Cipher","Shift Cipher ASCII" -> "Key(numerics Only)";
            case "Vigenere Cipher ASCII", "Vernam Cipher ASCII" -> "Key(unrestricted)";
            case "Vigenere Cipher" -> "Key(alphabets only)";
            default -> throw new IllegalStateException("Unexpected value: " + cipherType);
        };
        keyInputLabel.setValue(keyLabel);
        actBtn.setValue(BtnLabel);
    }
}
