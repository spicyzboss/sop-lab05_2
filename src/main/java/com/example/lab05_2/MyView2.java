package com.example.lab05_2;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Arrays;

@Route("index2")
public class MyView2 extends HorizontalLayout {
    private TextField addWord, addSentence, goodSentence, badSentence;
    private Button btnGood, btnBad, btnSentence, showSentence;
    private ComboBox<String> goodSelect, badSelect;

    public MyView2() {
        addWord = new TextField("Add Word");
        btnGood = new Button("Add Good Word");
        btnBad = new Button("Add Bad Word");

        goodSelect = new ComboBox();
        goodSelect.setLabel("Good Words");
        goodSelect.setItems(new ArrayList<String>(Arrays.asList("Boom", "Boss", "Poom")));
        goodSelect.setAllowCustomValue(true);
        badSelect = new ComboBox();
        badSelect.setLabel("Bad Words");
        addSentence = new TextField("Add Sentence");
        btnSentence = new Button("Add Sentence");
        goodSentence = new TextField("Good Sentences");
        badSentence = new TextField("Bad Sentences");
        showSentence = new Button("Show Sentence");

        VerticalLayout v1 = new VerticalLayout();
        VerticalLayout v2 = new VerticalLayout();

        addWord.setWidth("100%");
        btnGood.setWidth("100%");
        btnBad.setWidth("100%");
        goodSelect.setWidth("100%");
        badSelect.setWidth("100%");
        addSentence.setWidth("100%");
        btnSentence.setWidth("100%");
        goodSentence.setWidth("100%");
        badSentence.setWidth("100%");
        showSentence.setWidth("100%");

        v1.add(addWord, btnGood, btnBad, goodSelect, badSelect);
        v2.add(addSentence, btnSentence, goodSentence, badSentence, showSentence);

        v1.setWidth("100%");
        v2.setSizeFull();
        this.add(v1, v2);
        this.setSizeFull();
    }
}
