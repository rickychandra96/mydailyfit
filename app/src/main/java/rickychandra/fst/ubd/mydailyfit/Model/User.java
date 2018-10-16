package rickychandra.fst.ubd.mydailyfit.Model;

/**
 * Created by Ricky on 1/5/2018.
 */

public class User {
    protected double weight;
    protected double height;
    protected int age;
    protected char gender;
    protected String activ;

    public User(double weight, double height, int age, char gender, String activ) {
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.gender = gender;
        this.activ = activ;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public int getAge() {
        return age;
    }

    public char getGender() {
        return gender;
    }

    public String getActiv() {
        return activ;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setActiv(String activ) {
        this.activ = activ;
    }
}
