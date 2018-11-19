package wkda.services.chatbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wkda.services.chatbot.dto.ConversationDTO;
import wkda.services.chatbot.dto.ResponseDTO;
import wkda.services.chatbot.service.ChatBotService;

import javax.validation.Valid;

@RestController
public class MessageController {

    @Autowired
    private ChatBotService chatBotService;

    @CrossOrigin(origins = "*")
    @PostMapping(value = "api/chatbot/message", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO message(@Valid @RequestBody ConversationDTO conversationDTO) {
       return chatBotService.sendMessage(conversationDTO);
    }


}
