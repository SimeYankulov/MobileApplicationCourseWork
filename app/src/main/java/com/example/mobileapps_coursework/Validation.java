package com.example.mobileapps_coursework;

import android.service.autofill.FieldClassification;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Validation {
    public static interface MatchSuccess {
        void MatchSuccess();
    }

    public static interface MatchFail {
        void MatchFail();
    }

    static boolean MatchString(String regex1, String text){
        final String regex = regex1;
        final String string = text;

        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(string);

        while (matcher.find()) {
            System.out.println("Full match: " + matcher.group(0));
            return true;

        }
        return false;
    }
    static void Validate(EditText editUsername,
                         EditText editPassword,
                         EditText editConfirmPassword,
                         EditText editPhone,
                         EditText editEmail,
                         MatchSuccess success,
                         MatchFail fail
    ){
        if(
                MatchString(
                        "^((\\((\\+|00)?\\d+\\))|(\\+|00))? ?\\d+([\\/ -]?(\\(\\d+\\)|\\d+))+(\\:\\d+)?$",
                        editPhone.getText().toString()
                )
                        &&
                        MatchString("^[\\w\\.]+@(\\w+\\.)+[a-zA-Z]{2,4}$",
                                editEmail.getText().toString()
                        )
                        &&
                        MatchString("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
                                editPassword.getText().toString()
                        )
                        && editPassword.getText().toString().equals(editConfirmPassword.getText().toString())
        ){
            success.MatchSuccess();
        }else{
            fail.MatchFail();
        }

    }

}
