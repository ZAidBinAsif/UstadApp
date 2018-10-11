package com.andrinotech.ustadapp.helper;


import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Toast;

import com.andrinotech.ustadapp.R;

import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;

public class Utils {

    public static int POSITION;

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }


    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static String getTwoDecimalplaces(int number) {
        NumberFormat f = new DecimalFormat("00");
        return f.format(number);
    }

    public static String getTwoDecimalplaces(double number) {
        NumberFormat f = new DecimalFormat("00.00");
        return f.format(number);
    }

    public static Boolean getTimeDifference(String name) {
        if ((name.equalsIgnoreCase("In 0 minutes")) || (name.equalsIgnoreCase("0 minutes ago"))) {
            return true;
        } else {
            return false;
        }

    }

    public static void voidShowToast(final Context context, final String msg) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static CharSequence highlight(String search, String originalText, Context context) {
        // ignore case and accents
        // the same thing should have been done for the search text
        String normalizedText = Normalizer.normalize(originalText, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
//        String searchtext = Normalizer.normalize(search, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();

        int start = normalizedText.indexOf(search.toLowerCase());
        if (start < 0) {
            // not found, nothing to to
            return originalText;
        } else {
            // highlight each appearance in the original text
            // while searching in normalized text
            Spannable highlighted = new SpannableString(originalText);
            while (start >= 0) {
                int spanStart = Math.min(start, originalText.length());
                ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary));
                int spanEnd = Math.min(start + search.length(), originalText.length());
                highlighted.setSpan(foregroundSpan, spanStart, spanEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                start = normalizedText.indexOf(search, spanEnd);
            }
            if (normalizedText.contains(highlighted)) {
                return highlighted;
            } else {
                return originalText;

            }

        }
    }


}
