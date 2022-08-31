package com.example.springboot;

import java.util.ArrayList;
import java.util.HashMap;

public class Topics {
    private final HashMap<String, ArrayList<String>> topics = new HashMap<>();

    public void add_topic(String topic_name, String url){
        if (!topics.containsKey(topic_name)){
            ArrayList<String> url_list = new ArrayList<>();
            url_list.add(url);
            topics.put(topic_name, url_list);
        }else{
            ArrayList<String> url_list = topics.get(topic_name);
            url_list.add(url);
        }
    }

    public ArrayList<String> get_subscribers(String topic_name){
        return topics.get(topic_name);
    }

    public void print_topics(){
        System.out.println("Debugging list of subscriber: ");
        for (HashMap.Entry<String, ArrayList<String>> entry : topics.entrySet()) {
            String topic_name = entry.getKey();
            ArrayList<String> url_list = entry.getValue();
            System.out.println("Topic name:" + topic_name);
            for (String url: url_list){
                System.out.println("     " + url);
            }
        }
    }
}
