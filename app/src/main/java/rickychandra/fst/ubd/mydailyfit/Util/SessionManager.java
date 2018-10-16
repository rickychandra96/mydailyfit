package rickychandra.fst.ubd.mydailyfit.Util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ricky on 12/30/2017.
 */

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;// ga ush pake kalau sharedpreference langsung dibuat di activity mana aja

    private static final String PREF_NAME = "Credential";
    private static final String IS_LOGIN = "login";
    private static final String USERNAME = "username";

    public SessionManager (Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE); //inisialisasi
        editor = sharedPreferences.edit(); //inisialisasi
    }

    public void createLoginSession (String username){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(USERNAME, username);
        editor.commit();
    }

    public void createLogoutSession (){
        editor.putBoolean(IS_LOGIN, false);
        editor.remove(USERNAME);
        editor.commit();
    }

    public boolean checkLoginStatus(){
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public String getLoginUser(){
        return sharedPreferences.getString(USERNAME, "");
    }

}
