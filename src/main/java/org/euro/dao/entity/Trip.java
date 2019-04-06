package org.euro.dao.entity;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Entity
public class Trip implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long Id;
    @NotBlank(message = "Поле не може бути пустим")
    private String beginning;
    @NotBlank(message = "Поле не може бути пустим")
    private String finish;
    @NotBlank(message = "Поле не може бути пустим")
    private String price;
    @NotBlank(message = "Поле не може бути пустим")
    private String date;

    @ManyToMany(mappedBy = "trips",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<User> users;

    public Trip() {
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getBeginning() {
        return beginning;
    }

    public void setBeginning(String beginning) {
        this.beginning = beginning;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
