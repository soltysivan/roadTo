package org.euro.dao.entity;

import javax.persistence.*;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @ManyToOne
    private User author;
    @ManyToOne
    private Dialog dialogMes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Dialog getDialogMes() {
        return dialogMes;
    }

    public void setDialogMes(Dialog dialogMes) {
        this.dialogMes = dialogMes;
    }
}
