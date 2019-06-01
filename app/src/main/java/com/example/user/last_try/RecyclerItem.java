package com.example.user.last_try;

/**
 * Created by User on 10/31/2017.
 */

public class RecyclerItem implements Comparable{

    private String title;
    private String description;
    private int contributors;
private String cover_image_path;
private int votes;
    private String database_path_for_vote;
    private String kon_desh;
    private String journey_title;
    public RecyclerItem(String title, String description, String cover_image_path, int contributors, int votes, String database_path_for_vote, String kon_desh, String journey_title) {
        this.title = title;//name
        this.description = description;//koydin gesi
        this.cover_image_path=cover_image_path;
        this.contributors=contributors;//oyta contributor
        this.votes=votes;//koyta vote
        this.database_path_for_vote=database_path_for_vote;//
        this.kon_desh=kon_desh;
        this.journey_title=journey_title;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getCover_image_path() {
        return cover_image_path;
    }

    public void setCover_image_path(String cover_image_path) {
        this.cover_image_path =cover_image_path;
    }

    public int getContributors() {
        return contributors;
    }

    public void setContributors(int contributors) {
        this.contributors = contributors;
    }
    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
    public String getDatabase_path_for_vote() {
        return database_path_for_vote;
    }

    public void setDatabase_path_for_vote(String database_path_for_vote) {
        this.database_path_for_vote = database_path_for_vote;
    }
    public String getKon_desh() {
        return kon_desh;
    }

    public void setKon_desh(String kon_desh) {
        this.kon_desh = kon_desh;
    }
    public String getJourney_title() {
        return journey_title;
    }

    public void setJourney_title(String journey_title) {
        this.journey_title = journey_title;
    }


   @Override
    public int compareTo(Object another) {//search page e vote wise sort korbe

        if(((RecyclerItem)another).votes > votes){
            return 1;
        }else if(((RecyclerItem)another).votes==votes){
            return 0;
        }else{
            return -1;
        }


    }
}
