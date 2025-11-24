package com.diana.pure.logic;

import com.diana.pure.model.Parser;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;

import java.util.List;

import static com.diana.pure.model.MessageTemplate.formate;
import static java.util.Objects.isNull;

public class InlineProcessor {
    private static final String DEFAULT_TITLE_MESSAGE_CONTENT = """
            Напиши дату, место, описание:
            """;
    private static final String DEFAULT_INPUT_MESSAGE_CONTENT = """
            23.08, cafe, знакомство
            """;
    private static final String TITLE_MESSAGE_CONTENT = """
            ваша встреча:
            """;

    public static AnswerInlineQuery onUpdateReceived(Update update) {
        String text = "unsupported action";
        if (isNull(update.getInlineQuery())) {
            System.out.println("Unsupported action received: " + text);
            return new AnswerInlineQuery();
        }
        var inlineQuery = update.getInlineQuery();
        var user = inlineQuery.getFrom();
        text = inlineQuery.getQuery();
        System.out.println(user.getFirstName() + " inline " + text);
        return answerInlineQuery(inlineQuery.getId(), text);
    }

    private static AnswerInlineQuery answerInlineQuery(String inlineQueryId, String queryText) {
        var titleText = (queryText == null || queryText.isBlank())
                ? DEFAULT_TITLE_MESSAGE_CONTENT
                : TITLE_MESSAGE_CONTENT;
        var descriptionText = (queryText == null || queryText.isBlank())
                ? DEFAULT_INPUT_MESSAGE_CONTENT
                : queryText;

        String text;
        try {
            var meeting = Parser.parse(descriptionText);
            text = formate(meeting);
        } catch (Exception e) {
            text = descriptionText;
        }

        InputTextMessageContent inputMessageContent = new InputTextMessageContent();
        inputMessageContent.setMessageText(text);

        InlineQueryResultArticle inlineQueryResult = new InlineQueryResultArticle();
        inlineQueryResult.setId(inlineQueryId);
        inlineQueryResult.setTitle(titleText);
        inlineQueryResult.setDescription(descriptionText);
        inlineQueryResult.setInputMessageContent(inputMessageContent);

        AnswerInlineQuery answerInlineQuery = new AnswerInlineQuery();
        answerInlineQuery.setInlineQueryId(inlineQueryId);
        answerInlineQuery.setIsPersonal(true);
        answerInlineQuery.setCacheTime(0);
        answerInlineQuery.setResults(List.of(inlineQueryResult));

        return answerInlineQuery;
    }
}
