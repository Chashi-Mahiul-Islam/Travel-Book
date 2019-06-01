package com.example.user.last_try;

/**
 * Created by User on 1/19/2018.
 */

public class ke_koydin {
    public String ke;
    public int koydin;
    public String journey_type;
    public int votes;
    public String kon_desh;
    public String database_path_for_dual;
    public String journey_title;
    public ke_koydin() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ke_koydin(String ke, int koydin,String journey_type,int votes,String kon_desh,String database_path_for_dual,String journey_title) {
        this.ke = ke;
        this.koydin = koydin;
        this.journey_type=journey_type;
        this.votes=votes;
        this.kon_desh=kon_desh;
        this.database_path_for_dual=database_path_for_dual;
        this.journey_title=journey_title;
    }
}
