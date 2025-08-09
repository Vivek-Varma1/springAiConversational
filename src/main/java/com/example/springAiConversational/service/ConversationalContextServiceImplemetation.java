package com.example.springAiConversational.service;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.springAiConversational.constants.*;
import org.springframework.stereotype.Service;

@Service
public class ConversationalContextServiceImplemetation implements ConversationalContextService {


    Map <String,List<String>> contextStore =new HashMap<>();

    @Override
    public List<String> fetchAllContextIds() {
        return  new ArrayList<>(contextStore.keySet());
    }


    @Override
    public String preparePromptWithContextHistory(String contextId, String message) {
        List<String> history = contextStore.get(contextId);
        if (history == null) {
            history = new ArrayList<>();
            contextStore.put(contextId, history);
        }
        // Add current message to history
        history.add(message);

        StringBuilder prompt = new StringBuilder();
        prompt.append(PromptConstants.PROMPT_WHAT_WERE_WE_TALKING_ABOUT);

        for (int i = 0; i < history.size() - 1; i++) {
            prompt.append(padStringWithDelimeter(history.get(i)));
        }

        prompt.append(PromptConstants.PROMPT_DELIMITER_FOR_HISTORICAL_CONTEXT);
        prompt.append(PromptConstants.PROMPT_USE_CONTEXT_IF_NEEDED);
        prompt.append(PromptConstants.PROMPT_THE_CURRENT_QUESTION);
        prompt.append(history.get(history.size() - 1));

        return prompt.toString();  // return the constructed prompt string
    }


    private String padStringWithDelimeter(String s) {
        return PromptConstants.PROMPT_DELIMITER+s+PromptConstants.PROMPT_DELIMITER;
    }
}
