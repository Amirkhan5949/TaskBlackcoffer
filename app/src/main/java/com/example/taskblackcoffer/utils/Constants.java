package com.example.taskblackcoffer.utils;

public class Constants {
    public static class User{
        public static String key = "Users";
        public static String email = "email" ;
        public static String number = "number" ;
        public static String password = "password" ;
        public static String login_with = "login_with" ;
        public static String fb_id = "fb_id" ;


    }

    public enum AuthType{
        GOOGLE,FACEBOOK,EMAIL
    }

    public enum Auth{
        REGISTRATION,LOGIN
    }

}
