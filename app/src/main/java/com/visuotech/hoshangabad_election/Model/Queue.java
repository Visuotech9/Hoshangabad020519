package com.visuotech.hoshangabad_election.Model;

public class Queue {
    String queue_count,polling_id,creation_date,male_count,female_count;

    public String getMale_count() {
        return male_count;
    }

    public void setMale_count(String male_count) {
        this.male_count = male_count;
    }

    public String getFemale_count() {
        return female_count;
    }

    public void setFemale_count(String female_count) {
        this.female_count = female_count;
    }

    public Queue() {
    }

    public String getQueue_count() {
        return queue_count;
    }

    public void setQueue_count(String queue_count) {
        this.queue_count = queue_count;
    }

    public String getPolling_id() {
        return polling_id;
    }

    public void setPolling_id(String polling_id) {
        this.polling_id = polling_id;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }
}
