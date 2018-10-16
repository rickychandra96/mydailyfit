package rickychandra.fst.ubd.mydailyfit;

/**
 * Created by Ricky on 1/5/2018.
 */

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import rickychandra.fst.ubd.mydailyfit.Model.User;

/**
 * RUMUS-RUMUS
 Rumus untuk mengetahui BMR laki-laki = 66 + (13,7 x BB kg) + (5 x TB cm) – (6,8 x usia)
 Rumus untuk mengetahui BMR perempuan = 655 + (9,6 x BB kg) + (1,8 x TB cm) – (4,7 x usia)
 ringan = 1.3
 sedang = 1.5
 berat = 1.7
 kalori = BMR*aktivitas
 */
public class CalorieNeeds extends User{
    public CalorieNeeds(double weight, double height, int age, char gender, String activ) {
        super(weight, height, age, gender, activ);
    }

    private double getBmr(){
        //menghitung basal metabolic rate
        if (gender=='L'){
            return 66 + (13.7 * weight) + (5 * height) - (6.8 * age);
        }else{
            return 655 + (9.6 * weight) + (1.8 * height) - (4.7 * age);
        }
    }

    public String getCalorieNeed(){
        double bmr = getBmr();
        double calorieNeed=0;
        String result ="0";

        if (gender=='L' && activ.equals("sangat ringan")){
            calorieNeed = 1.3 * bmr;
        }else if (gender=='L' && activ.equals("ringan")) {
            calorieNeed = 1.65 * bmr;
        }else if (gender=='L' && activ.equals("sedang")) {
            calorieNeed = 1.76 * bmr;
        }else if (gender=='L' && activ.equals("berat")) {
            calorieNeed = 2.1 * bmr;
        }else if (gender=='P' && activ.equals("sangat ringan")) {
            calorieNeed = 1.3 * bmr;
        }else if (gender=='P' && activ.equals("ringan")) {
            calorieNeed = 1.55 * bmr;
        }else if (gender=='P' && activ.equals("sedang")) {
            calorieNeed = 1.7 * bmr;
        }else if (gender=='P' && activ.equals("berat")) {
            calorieNeed = 2 * bmr;
        }
        result = String.format("%.0f",calorieNeed);
        return result;
    }

    public String getBodyCategory(){
        double imt = getIMT();
        if (imt>25){
            return "Gemuk";
        }else if (imt>18.5 && imt<26){
            return "Normal";
        }else{
            return "Kurus";
        }
    }

    private double getIMT(){
        //untuk mengetahui kategori tubuh
        double height2 = (height/100 * height/100);
        return (weight/height2);
    }
}
