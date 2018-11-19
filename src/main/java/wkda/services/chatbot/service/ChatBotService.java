package wkda.services.chatbot.service;

import com.amazonaws.services.lexruntime.AmazonLexRuntime;
import com.amazonaws.services.lexruntime.model.PostTextRequest;
import com.amazonaws.services.lexruntime.model.PostTextResult;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wkda.services.chatbot.dto.ConversationDTO;
import wkda.services.chatbot.dto.MessageWrapper;
import wkda.services.chatbot.dto.ResponseDTO;
import wkda.services.chatbot.utils.JsonUtils;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class ChatBotService {

    @Autowired
    private AmazonLexRuntime lexClient;

    @Autowired
    private AmazonTranslate translate;

    @Value("${wkda.chatbot.aws.lex.botName}")
    private String botName;

    @Value("${wkda.chatbot.aws.lex.botAlias}")
    private String botAlias;

    @Value("${wkda.chatbot.aws.lex.botUser}")
    private String botUser;

    public ResponseDTO sendMessage(ConversationDTO conversationDTO){
        PostTextRequest textRequest = new PostTextRequest();
        textRequest.setBotName(botName);
        textRequest.setBotAlias(botAlias);
        textRequest.setUserId(botUser);
        textRequest.setInputText(translate(conversationDTO.getText(), "en", "en"));
        return responseDTO(lexClient.postText(textRequest));
    }

    private ResponseDTO responseDTO(PostTextResult result){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSlots(result.getSlots());
        String [] messages = null;
        try {
            MessageWrapper wrapper = JsonUtils.fromJson(result.getMessage(), MessageWrapper.class);
            if (Objects.nonNull(wrapper)) {
                messages = new String[wrapper.getMessages().size()];
                for (int i = 0; i < wrapper.getMessages().size(); i++) {
                    messages[i] = translate(wrapper.getMessages().get(i).getValue(), "en", "en");
                }
            }
        } catch (IOException e) {
            messages = new String[1];
            messages[0] = translate(result.getMessage(), "en", "en");
        }
        responseDTO.setMessages(messages);
        return responseDTO;
    }

    private String translate(String text, String sourceLang, String targetLang){
        TranslateTextRequest request = new TranslateTextRequest()
                .withText(text)
                .withSourceLanguageCode(sourceLang)
                .withTargetLanguageCode(targetLang);
        return translate.translateText(request).getTranslatedText();
    }
}
