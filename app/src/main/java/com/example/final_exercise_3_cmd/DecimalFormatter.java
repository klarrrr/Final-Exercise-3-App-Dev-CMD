package com.example.final_exercise_3_cmd;

import java.text.DecimalFormat;

public class DecimalFormatter {
    // This public function will return a Proper and Pretty Formatted String of Numbers.
    public static String PrettyFormat(Double money){
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        return decimalFormat.format(money);
    }
}
