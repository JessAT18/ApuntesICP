package com.postman.rest.demopostman;

public class MensajeDeErroresRest {
    private String msg;
    private String causa;

    public MensajeDeErroresRest(String msg, String causa) {
        this.msg = msg;
        this.causa = causa;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }
}
