package com.example.naveed.ocf.Helper;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtility {

    public final static String UK_POSTCODE_REJEX = "(GIR 0AA)|((([A-Z-[QVX]][0-9][0-9]?)|(([A-Z-[QVX]][A-Z-[IJZ]][0-9][0-9]?)|(([A-Z-[QVX]][0-9][A-HJKPSTUW])|([A-Z-[QVX]][A-Z-[IJZ]][0-9][ABEHMNPRVWXY]))))\\s?[0-9][A-Z-[CIKMOV]]{2})";
    private final static String LICENCE_REJEX = "^(?:[0-9]{16})$";
    private final static String LICENCE_REJEX_THRESHOLD = "^(?:[0-9]{10,16})";
    private static final String TAG = "ValidationUtils";

    public static boolean isValidEmail(EditText mEditText) {
        String text = mEditText.getText().toString();
        if (text.isEmpty()) {
            return false;
        } else
        {
            boolean correct = android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches();
            if(!correct) {
                return false;
            }
            else
            {
                if(text.contains("example"))
                {
                    return false;
                }
            }


        }
        return true;
    }

    public static boolean edittextValidator(EditText... mEditTexts)
    {
        for (EditText mEditText:mEditTexts) {
            if (mEditText.getText().toString().isEmpty()) {

                mEditText.setError("Required");
                return false;
            }
        }
        return true;
    }

    public static boolean isemptyedittextValidator(EditText... mEditTexts)
    {
        for (EditText mEditText:mEditTexts) {
            if (mEditText.getText().toString().isEmpty()) {

                // mEditText.setError("Required");
                mEditText.setFocusable(true);
                return false;
            }
        }
        return true;
    }



    public  boolean educationLevel(int level, Context mContext)
    {
        if (level==-1) {

            Toast.makeText(mContext, "Required",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean ukPostCodeValidator(EditText postCode)
    {

        final Pattern pattern = Pattern.compile(UK_POSTCODE_REJEX);
        if (!pattern.matcher(postCode.getText().toString().toUpperCase().trim()).matches()) {
            return false;
        }
        return true;

    }

    public static boolean licenceNumberValidation(String licenceNumber, boolean threshold)
    {

        try {

            licenceNumber = licenceNumber.replaceAll(" ","");
            licenceNumber = licenceNumber.replaceAll("[^\\d.]", "");
            final Pattern pattern = Pattern.compile(threshold?LICENCE_REJEX_THRESHOLD:LICENCE_REJEX);
            if (!pattern.matcher(licenceNumber.toUpperCase().trim()).matches()) {
                return false;
            }
        }catch (Exception e)
        {
            Log.e(TAG, "licenceNumberValidation:" + e.getMessage());
            return false;
        }

        return true;
    }

    public static boolean isThisDateValid(String dateToValidate){

        if(dateToValidate == null){
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        sdf.setLenient(false);

        try {

            //if not valid, it will throw ParseException
            Date date = sdf.parse(dateToValidate);
            System.out.println(date);

        } catch (ParseException e) {

            e.printStackTrace();
            return false;
        }

        return true;
    }


    public static boolean isValidPhone(String phone) {

        // String regex = "^(?:(?:\\+|00)(\\d{1,3})[\\s-]?)?(\\d{11})$";
        String pak_regix="^(92)\\d{3}-{0,1}\\d{7}$|^\\d{11}$|^\\d{4}-\\d{7}$";
        Pattern p = Pattern.compile(pak_regix);
        Matcher m = p.matcher(phone);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkExpiry(String s) {

        try {
            if(new SimpleDateFormat("dd MMM yyyy").parse(s).before(new Date()))
                return false;
        } catch (ParseException e) {
            Log.e(TAG, "checkExpiry:" + e.getMessage());
            return false;
        }

        return true;
    }

    public static boolean isValidPassword(EditText edPassword) {
        return (edPassword.getText().toString().length()>=6);
    }
}
