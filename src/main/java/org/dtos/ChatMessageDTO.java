package org.dtos;

import java.time.LocalDateTime;

public class ChatMessageDTO {
    private String role;//user or assistant
    private String message;// Conteudo da mensagem
    private String context; //Contexo opcional , nutrition, recipe, plan
    private LocalDateTime timestamp;

    public ChatMessageDTO(){}
    public ChatMessageDTO(String role, String message, String context, LocalDateTime timestamp) {
        this.role = role;
        this.message = message;
        this.context = context;
        this.timestamp = timestamp;
    }

    // Construtor de conveniência — mensagem do utilizador sem timestamp
    public ChatMessageDTO(String role, String message, String context) {
        this(role, message, context, LocalDateTime.now());
    }

    // Construtor mínimo — só a mensagem
    public ChatMessageDTO(String message) {
        this("user", message, null, LocalDateTime.now());
    }

    //  Getters

    public String getRole() { return role; }
    public String getMessage() { return message; }
    public String getContext() { return context; }
    public LocalDateTime getTimestamp() { return timestamp; }

    //  Setters

    public void setRole(String role) { this.role = role; }
    public void setMessage(String message) { this.message = message; }
    public void setContext(String context) { this.context = context; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    // overrides

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ChatMessageDTO other)) return false;
        if (message == null) return false;
        return message.equals(other.message) &&
                role.equals(other.role) &&
                timestamp.equals(other.timestamp);
    }

    @Override
    public int hashCode() {
        return message != null ? message.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ChatMessageDTO{" +
                "role='" + role + '\'' +
                ", message='" + message + '\'' +
                ", context='" + context + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

}
