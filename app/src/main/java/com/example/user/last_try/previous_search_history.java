package com.example.user.last_try;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by User on 11/17/2017.
 */

public class previous_search_history implements Comparable {
    public Date date;
    public String kon_desh;
    public previous_search_history(){

    }
    public previous_search_history(Date date,String kon_desh){
        this.date=date;
        this.kon_desh=kon_desh;
    }
    @Override
    public int compareTo(@NonNull Object o) {
        if(((previous_search_history)o).date.after(date)){
            return 1;
        }else if(((previous_search_history)o).date.before(date)){
            return -1;
        }else{
            return 0;
        }
    }
}
