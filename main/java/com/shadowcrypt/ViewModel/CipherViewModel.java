package com.shadowcrypt.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.shadowcrypt.EnumUtility.CIPHER_TYPE;
import com.shadowcrypt.Model.CipherModel;

import java.util.Objects;

public class CipherViewModel extends ViewModel {
    /* Eager Instantiations */
    private final MutableLiveData<CipherModel> selectedCipher = new MutableLiveData<>();
    private final MutableLiveData<String> inputText = new MutableLiveData<>();
    private final MutableLiveData<String> key = new MutableLiveData<>();
    private final MutableLiveData<String> outputText = new MutableLiveData<>();

    public MutableLiveData<String> outputText() {return outputText;}

    public void setInputText(String inputText) {this.inputText.setValue(inputText);}
    public void setKey(String key) {this.key.setValue(key);}
    private void setSelectedCipher(CipherModel cipher) {selectedCipher.setValue(cipher);}

    private CIPHER_TYPE typeAnalyzer(String cType){
        return switch(cType){
            case "Shift Cipher" -> CIPHER_TYPE.SHIFT_CIPHER;
            case "Shift Cipher ASCII" -> CIPHER_TYPE.SHIFT_CIPHER_ASCII;
            case "Vigenere Cipher" -> CIPHER_TYPE.VIGENERE_CIPHER;
            case "Vigenere Cipher ASCII" -> CIPHER_TYPE.VIGENERE_CIPHER_ASCII;
            case "Vernam Cipher ASCII" -> CIPHER_TYPE.VERNAM_CIPHER;
            default -> throw new IllegalStateException("Unexpected value: " + cType);
        };
    }

    private void cipherModelInstantiation(String cipherType){
        CipherModel buildModel = new CipherModel.Builder()
                .setCipherType(typeAnalyzer(cipherType))
                .build();
        setSelectedCipher(buildModel);
    }

    public void processResult(String cipherType, String processType){
        cipherModelInstantiation(cipherType);
        String input = inputText.getValue();
        String keyString = key.getValue();
        if (input == null || input.isBlank() || keyString == null || keyString.isBlank()) {
            outputText.setValue("Missing required Value/s");
            return;
        }
        Runnable action = processType.equalsIgnoreCase("Encryption") ?
                this::encrypt : this::decrypt;
        action.run();
    }

    private void encrypt(){
        CipherModel cipher = selectedCipher.getValue();
        String input = inputText.getValue();
        String p_key = key.getValue();
        String output = Objects.requireNonNull(cipher).encrypt(input, p_key);
        outputText.setValue(output);
    }

    private void decrypt(){
        CipherModel cipher = selectedCipher.getValue();
        String input = inputText.getValue();
        String p_key = key.getValue();
        String output = Objects.requireNonNull(cipher).decrypt(input, p_key);
        outputText.setValue(output);
    }
}
