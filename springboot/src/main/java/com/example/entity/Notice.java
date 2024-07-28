package com.example.entity;

/**
 * Notice
 */
public class Notice {
    /** ID */
    private Integer id;
    /** title */
    private String title;
    /** content */
    private String content;
    /** time of announcement */
    private String time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
