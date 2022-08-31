package com.example.springboot;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class Controller {
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);
    private final Topics topics = new Topics();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String index(){
        return "Hello World";
    }

    @RequestMapping(value = "/subscribe/{topic}", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String subscribe(@PathVariable("topic") String topic_name, @RequestBody Map<String, String> payload){
        System.out.println("Receive topic: " + topic_name);
        System.out.println("With payload: " + payload.get("url"));

        topics.add_topic(topic_name, payload.get("url"));
        topics.print_topics();

        return payload.toString();
    }

    @RequestMapping(value = "/publish/{topic}", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public void publish(@PathVariable("topic") String topic_name, @RequestBody Map<String, String> message) {
        ArrayList<String> url_list = topics.get_subscribers(topic_name);

        for (String url: url_list){
            final WebClient webClient = WebClient.create(url);
            String result = (webClient
                    .post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromObject(message))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(REQUEST_TIMEOUT));
            System.out.println("Publish completed with " + url);
            System.out.println("     " + result);
        }
    }

    @RequestMapping(value = "/event", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String event(@RequestBody Map<String, String> msg){
        return "Event endpoint received a message: " + msg;
    }

    @RequestMapping(value = "/another", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String another(@RequestBody Map<String, String> msg){
        return "Another endpoint received a message: " + msg;
    }
}
