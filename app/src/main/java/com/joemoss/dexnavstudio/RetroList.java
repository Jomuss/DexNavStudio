package com.joemoss.dexnavstudio;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class RetroList {

    @SerializedName("results")
    private List<Map<String, String>> results;

    @SerializedName("count")
    private int count;

    @SerializedName("next")
    private String next;

    @SerializedName("previous")
    private String previous;




    RetroList(List<Map<String, String>> results, int count, String next, String previous){
        this.results = results;
        this.previous = previous;
        this.next = next;
        this.count = count;
    }


    List<Map<String, String>> getResults() {
        return results;
    }

    public void setResults(List<Map<String, String>> results) {
        this.results = results;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }
}
