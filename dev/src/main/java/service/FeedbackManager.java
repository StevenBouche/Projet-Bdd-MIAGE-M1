package service;

import dal.MarkLogicUtility;
import models.Feedback;
import models.Person;
import models.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FeedbackManager extends MarkLogicManager<Feedback,String> {

    public FeedbackManager(MarkLogicUtility utility) {
        super(utility, Feedback.class, String.class);
    }

    public Map<String, List<Feedback>> getFeedbackPersonJoinProduct(List<Person> p2, Product p) throws InterruptedException {

        List<Feedback> feedbacks = this.readAll();

        Map<String, List<Feedback>> map = new HashMap<>();

        p2.forEach(person -> {

            List<Feedback> fb = feedbacks.stream().filter(feedback ->
                    feedback.getAsinProduct().equals(p.getAsin()) &&
                            feedback.getIdPerson().equals(person.getId()))
                    .collect(Collectors.toList());

            map.put(person.getId(),fb);

        });

        return map;

    }

}
