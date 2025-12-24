package com.example.tamagotchi.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tamagotchi.model.Pet;

public class SaveManager {

    private static final String SAVE_NAME = "SAVE";

    public static void save(Context context, Pet pet) {
        SharedPreferences sp = context.getSharedPreferences(SAVE_NAME, Context.MODE_PRIVATE);
        sp.edit()
                .putString("name", pet.getName())
                .putInt("hunger", pet.getHunger())
                .putInt("care", pet.getCare())
                .putInt("mood", pet.getMood())
                .putInt("day", pet.getDay())
                .apply();
    }

    public static Pet load(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SAVE_NAME, Context.MODE_PRIVATE);

        String name = sp.getString("name", "Pet");
        Pet pet = new Pet(name);

        setPrivate(pet,
                sp.getInt("hunger", 100),
                sp.getInt("care", 100),
                sp.getInt("mood", 100),
                sp.getInt("day", 1)  // <-- закрываем скобку здесь
        );

        return pet;
    }

    public static void clear(Context context) {
        context.getSharedPreferences(SAVE_NAME, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .apply();
    }

    private static void setPrivate(Pet pet, int hunger, int care, int mood, int day) {
        try {
            java.lang.reflect.Field f1 = Pet.class.getDeclaredField("hunger");
            java.lang.reflect.Field f2 = Pet.class.getDeclaredField("care");
            java.lang.reflect.Field f3 = Pet.class.getDeclaredField("mood");
            java.lang.reflect.Field f4 = Pet.class.getDeclaredField("day");

            f1.setAccessible(true);
            f2.setAccessible(true);
            f3.setAccessible(true);
            f4.setAccessible(true);

            f1.setInt(pet, hunger);
            f2.setInt(pet, care);
            f3.setInt(pet, mood);
            f4.setInt(pet, day);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

