package org.euro.dao.entity;


import javax.persistence.*;
import java.util.List;

@Entity
public class Dialog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User address;
    @ManyToOne
    private User sender;
    @OneToMany(mappedBy = "dialogMes",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> dialogMessage;

    public Dialog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAddress() {
        return address;
    }

    public void setAddress(User address) {
        this.address = address;
    }

    public List<Message> getDialogMessage() {
        return dialogMessage;
    }

    public void setDialogMessage(List<Message> dialogMessage) {
        this.dialogMessage = dialogMessage;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}
