package wkda.services.chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {

    private Map<String, String> slots;

    private String [] messages;

}
