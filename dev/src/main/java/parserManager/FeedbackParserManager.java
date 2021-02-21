package parserManager;

import dal.DataAccess;
import models.Feedback;
import models.LinkPerson;
import parser.FeedbackParser;
import parser.LinkPersonParser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FeedbackParserManager {

    DataAccess access = new DataAccess();
    List<String[]> feedbackStr;
    List<Feedback> feedback = new ArrayList<>();

    public FeedbackParserManager() throws IOException, URISyntaxException {
        this.feedbackStr = access.getDataCSV("Feedback.csv", '|', true);
        this.feedbackStr.remove(0);
    }

    public List<Feedback> getFeedback(){

        if(!feedback.isEmpty()||feedbackStr.isEmpty()) return feedback;

        FeedbackParser parser = new FeedbackParser(this.feedbackStr);
        this.feedback = parser.getDataParse();

        return this.feedback;
    }

}