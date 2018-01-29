package com.cs442.amalviy1.freefood_at_iit.Pojo;

import java.util.ArrayList;

/**
 * Created by Ankit on 11/13/16.
 */
public class Token_Pojo {

   public static ArrayList<String> tokenList= null;

    public Token_Pojo(ArrayList<String> tokenList) {
        this.tokenList = tokenList;
    }
    public Token_Pojo()
    {

    }

    public ArrayList<String> getTokenList() {
        return tokenList;
    }

    public void setTokenList(ArrayList<String> tokenList) {
        this.tokenList = tokenList;
    }
}
