package com.enjoyapp.carhelper.Utils;

import java.util.regex.Pattern;

public class EmailAndPasswordValidation {

    public EmailAndPasswordValidation() {
    }

    public boolean isValidEmail(String mEamil) {
        return EMAIL_ADDRESS_PATTERN.matcher(mEamil).matches();
    }

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
}
