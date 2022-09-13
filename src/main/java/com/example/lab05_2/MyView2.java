package com.example.lab05_2;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Route("index2")
public class MyView2 extends HorizontalLayout {
    private TextField addWordField, addSentenceField;
    private TextArea goodSentenceArea, badSentenceArea;
    private Button addGoodWordBtn, addBadWordBtn, addSentenceBtn, showSentenceBtn;
    private ComboBox<String> goodWordBox, badWordBox;

    public MyView2() {
        VerticalLayout leftLayout = new VerticalLayout();
        addWordField = new TextField("Add Word");
        addGoodWordBtn = new Button("Add Good Word");
        addGoodWordBtn.addClickListener(e -> {
            List<String> v = WebClient
                    .create()
                    .post()
                    .uri("http://localhost:8080/addGood")
                    .body(Mono.just(addWordField.getValue()), String.class)
                    .retrieve()
                    .bodyToMono(List.class)
                    .block();

            this.goodWordBox.setItems(v);
            new Notification("Insert " + this.addWordField.getValue() + " to Good Word Lists Complete.", 3000).open();
            this.addWordField.setValue("");
        });
        addBadWordBtn = new Button("Add Bad Word");
        addBadWordBtn.addClickListener(e -> {
            List<String> v = WebClient
                    .create()
                    .post()
                    .uri("http://localhost:8080/addBad")
                    .body(Mono.just(addWordField.getValue()), String.class)
                    .retrieve()
                    .bodyToMono(List.class)
                    .block();

            this.badWordBox.setItems(v);
            new Notification("Insert " + this.addWordField.getValue() + " to Bad Word Lists Complete.", 3000).open();
            this.addWordField.setValue("");
        });
        goodWordBox = new ComboBox("Good Words");
        badWordBox = new ComboBox("Bad Words");

        leftLayout.add(addWordField, addGoodWordBtn, addBadWordBtn, goodWordBox, badWordBox);

        VerticalLayout rightLayout = new VerticalLayout();
        addSentenceField = new TextField("Add Sentence");
        addSentenceBtn = new Button("Add Sentence");
        addSentenceBtn.addClickListener(e -> {
            String o = WebClient
                .create()
                .post()
                .uri("http://localhost:8080/proof")
                .body(Mono.just(addSentenceField.getValue()), String.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();

            this.addSentenceField.setValue("");
            if (o.length() > 0) {
                new Notification("Found " + o + " word.", 3000).open();
            } else {
                new Notification("Not Found in any Word Lists.", 3000).open();
            }
        });
        goodSentenceArea = new TextArea("Good Sentences");
        badSentenceArea = new TextArea("Bad Sentences");
        showSentenceBtn = new Button("Show Sentence");
        showSentenceBtn.addClickListener(e -> {
            Sentence o = WebClient
                    .create()
                    .get()
                    .uri("http://localhost:8080/getSentence")
                    .retrieve()
                    .bodyToMono(Sentence.class)
                    .block();

            this.badSentenceArea.setValue(o.badSentences.toString());
            this.goodSentenceArea.setValue(o.goodSentences.toString());
        });

        rightLayout.add(addSentenceField, addSentenceBtn, goodSentenceArea, badSentenceArea, showSentenceBtn);

        leftLayout.setSizeFull();
        addWordField.setWidth("100%");
        addGoodWordBtn.setWidth("100%");
        addBadWordBtn.setWidth("100%");
        goodWordBox.setWidth("100%");
        badWordBox.setWidth("100%");

        rightLayout.setSizeFull();
        addSentenceField.setWidth("100%");
        addSentenceBtn.setWidth("100%");
        goodSentenceArea.setWidth("100%");
        badSentenceArea.setWidth("100%");
        showSentenceBtn.setWidth("100%");

        this.add(leftLayout, rightLayout);
        this.setSizeFull();
    }
}
