package org.controllers.rest;

import org.dtos.ChatMessageDTO;
import org.services.AIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final AIService aiService;

    public ChatController(AIService aiService) {
        this.aiService = aiService;
    }

    //POST /api/chat
    //Body: {"message": "..."
    @PostMapping
    public ResponseEntity<ChatMessageDTO> chat(@RequestBody ChatMessageDTO message) throws Exception{
        ChatMessageDTO response = aiService.chat(message);//Service
        return ResponseEntity.ok(response);
    }

    //POST /api/chat/suggest
    //sugestao de receitas com base nos ingredientes que temos
    @PostMapping("/suggest")
    public ResponseEntity<ChatMessageDTO> suggest(@RequestBody ChatMessageDTO message) throws Exception{
        ChatMessageDTO response = aiService.suggestRecipes(message);//service
        return ResponseEntity.ok(response);
    }
}
