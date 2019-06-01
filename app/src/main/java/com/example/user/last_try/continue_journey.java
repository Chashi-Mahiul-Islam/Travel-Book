package com.example.user.last_try;

/**
 * Created by User on 1/19/2018.
 */

public class continue_journey {
    public int event;
    public int day;
    public String database_path;
    public boolean ager_journey_sesh_naki;
    public int koy_dine_sesh_hoyar_kotha;
    public String current_desh;
    public String title;
    public continue_journey() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public continue_journey(int event,int day,String database_path,boolean ager_journey_sesh_naki,int koy_dine_sesh_hoyar_kotha,String current_desh,String title) {
        this.event=event;
        this.day=day;
        this.database_path=database_path;
        this.ager_journey_sesh_naki=ager_journey_sesh_naki;
        this.koy_dine_sesh_hoyar_kotha=koy_dine_sesh_hoyar_kotha;
        this.current_desh=current_desh;
        this.title=title;
    }
}
