package com.example.naveed.ocf.Utility;

import android.widget.EditText;

public class ValidationUtility {

    public static boolean EditTextValidator(EditText... mEditTexts)
    {
        for (EditText mEditText:mEditTexts) {
            if (mEditText.getText().toString().isEmpty()) {
                mEditText.setError("Required");
                return false;
            }
        }
        return true;
    }
}