package com.example.reminder;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IsciLab {
    private static IsciLab sIsciLab;

    private static List<Isci> mIsciler;

    public static IsciLab get(Context context) { 
        if (sIsciLab == null) {
            sIsciLab = new IsciLab(context);
        }

        return sIsciLab;
    }

    private IsciLab(Context context) {
        mIsciler = new ArrayList<>();
    }

    public void addIsci(Isci c) {
        mIsciler.add(c);
    }

    public List<Isci> getIsciler() {
        return mIsciler;
    }

    public Isci getIsci(UUID id) {
        for (Isci isci : mIsciler) {
            if (isci.getId().equals(id) ) {
                return isci;
            }
        }

        return null;
    }
    public static boolean deleteIsci(UUID isciId){
        for(Isci isci:mIsciler){
            if(isci.getId().equals(isciId)){
                mIsciler.remove(isci);
                return true;
            }
        }
        return false;
    }
}
