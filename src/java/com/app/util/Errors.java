package com.app.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public final class Errors {
    
    private StringBuffer errors;
    private List messages;
    
    public Errors() {
        this.messages = new ArrayList();
        this.errors = new StringBuffer();
    }
    
    public void error(String message) {
        if(message != null && !message.equals("")) {
            if(errors.length() != 0) {
                this.errors.append("\\n");
            }
            this.errors.append(message);
        }
    }
    
    public final void  message(String message) {
        if(message != null && !message.equals("")) {
            this.messages.add(message);
        }
    }
    
    public final StringBuffer getErrors() {
        return errors;
    }
    
    public final List getMessages() {
        return messages;
    }
    
    public final void save(HttpServletRequest request) {
        if (this.errors.length() != 0) {
            request.setAttribute("errors", getErrors());
        }
        if (!this.messages.isEmpty()) {
            request.setAttribute("messages", getMessages());
        }
    }
}
