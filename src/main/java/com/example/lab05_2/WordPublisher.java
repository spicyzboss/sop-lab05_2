package com.example.lab05_2;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/addBad/{word}")
    public ArrayList<String> addBadWord(@PathVariable("word") String s) {
        this.words.badWords.add(s);
        return this.words.badWords;
    }

    @GetMapping("/delBad/{word}")
    public ArrayList<String> deleteBadWord(@PathVariable("word") String s) {
        this.words.badWords.remove(s);
        return this.words.badWords;
    }

    @GetMapping("/addGood/{word}")
    public ArrayList<String> addGoodWord(@PathVariable("word") String s) {
        this.words.goodWords.add(s);
        return this.words.goodWords;
    }

    @GetMapping("/delGood/{word}")
    public ArrayList<String> deleteGoodWord(@PathVariable("word") String s) {
        this.words.goodWords.remove(s);
        return this.words.goodWords;
    }

    @GetMapping("/proof/{sentence}")
    public void proofSentence(@PathVariable("sentence") String s) {
        boolean b = false;
        boolean g = false;

        for (String v : s.split(" ")) {
            b = this.words.badWords.contains(v) || b;
            g = this.words.goodWords.contains(v) || g;
        }

        if (b && g) {
            rabbit.convertAndSend("Fanout", "", s);
        } else if (b) {
            rabbit.convertAndSend("Direct", "bad", s);
        } else if (g) {
            rabbit.convertAndSend("Direct", "good", s);
        }
    }

//    @GetMapping("/getSentence")
//    public Sentence getSentence() {
//        return ;
//    }
}
