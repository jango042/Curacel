package com.curacel.vehiclelisting.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponses {
    public static int CODE_OK = 0;
    public static int CODE_ERROR = -1;
    public static String MESSAGE_UPDATE = "Successfully updated";
    public static String MESSAGE_CREATE = "Successfully created";
    public static String MESSAGE_EMPTY = "List is empty";
    public static String MESSAGE_WRONG_ID = "Id provided is wrong";
    public static String MESSAGE_ERROR = "Error occurred, please contact admin";
    private int code;
    private String message;

    public MessageResponses(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static MessageResponses response(int code, String message) {
        return new MessageResponses(code, message);
    }

    public static MessageResponses isCreating(int code){
        MessageResponses mr;

        if(code==0){
            mr = MessageResponses.response(MessageResponses.CODE_OK, MessageResponses.MESSAGE_CREATE);
        }else{
            mr = MessageResponses.response(MessageResponses.CODE_OK, MessageResponses.MESSAGE_UPDATE);
        }
        return mr;
    }
}
