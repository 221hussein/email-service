package com.hussein.userservice.utils;

public class EmailUtils {

    public static String getEmailMessage(String name,String host,String token) {
        return "Hello "+ name +"\n\nYour account has been created .Please click the link to veriy your account. \n\n"
                + getVerificationUrl(host, token)+ "\n\n The support Team";
    }

    public static String getVerificationUrl (String host, String token) {
        return host+ "/api/users?token=" + token;
    }
}
