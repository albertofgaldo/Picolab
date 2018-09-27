package com.picolab.application.DTO;

public class CanvasDTO {
    private int id;
    private String url;

    public CanvasDTO(int id, String url){
        this.id=id;
        this.url=url;
    }

    public int getId(){
        return id;
    }

    public String getUrl(){
        return url;
    }
}
