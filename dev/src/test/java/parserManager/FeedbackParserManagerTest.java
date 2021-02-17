package parserManager;

import models.Feedback;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackParserManagerTest {

    @BeforeAll
    static void init() {

    }

    @Test
    void FeedbackManager() throws IOException, URISyntaxException {
        Feedback();
    }

    public void Feedback() throws IOException, URISyntaxException {
        FeedbackParserManager f = new FeedbackParserManager();
        List<Feedback> Feedback = f.getFeedback();
        System.out.println("TEST");

    }
}