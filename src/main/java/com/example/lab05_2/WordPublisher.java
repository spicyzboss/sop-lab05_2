package com.example.lab05_2;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class WordPublisher {
    protected Word words;
    @Autowired
    private RabbitTemplate rabbit;

    public WordPublisher() {
        this.words = new Word();
        this.words.goodWords.add("happy");
        this.words.goodWords.add("enjoy");
        this.words.goodWords.add("like");
        this.words.badWords.add("fuck");
        this.words.badWords.add("olo");
    }

    @PostMapping("/addBad")
    public ArrayList<String> addBadWord(@RequestBody String s) {
        this.words.badWords.add(s);
        return this.words.badWords;
    }

    @GetMapping("/delBad/{word}")
    public ArrayList<String> deleteBadWord(@PathVariable("word") String s) {
        this.words.badWords.remove(s);
        return this.words.badWords;
    }

    @PostMapping("/addGood")
    public ArrayList<String> addGoodWord(@RequestBody String s) {
        this.words.goodWords.add(s);
        return this.words.goodWords;
    }

    @GetMapping("/delGood/{word}")
    public ArrayList<String> deleteGoodWord(@PathVariable("word") String s) {
        this.words.goodWords.remove(s);
        return this.words.goodWords;
    }

    @PostMapping("/proof")
    public String proofSentence(@RequestBody String s) {
        boolean b = false;
        boolean g = false;

        for (String v : s.split(" ")) {
            b = this.words.badWords.contains(v) || b;
            g = this.words.goodWords.contains(v) || g;
        }

        if (b && g) {
            rabbit.convertAndSend("Fanout", "", s);
            return "good & bad";
        } else if (b) {
            rabbit.convertAndSend("Direct", "bad", s);
            return "bad";
        } else if (g) {
            rabbit.convertAndSend("Direct", "good", s);
            return "good";
        }
        return "";
    }

    @GetMapping("/getSentence")
    public Sentence getSentence() {
        return (Sentence) (rabbit.convertSendAndReceive("Direct", "get", ""));
    }
}
