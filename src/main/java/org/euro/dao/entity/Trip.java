package org.euro.dao.entity;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
public class Trip implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long Id;
    @NotBlank(message = "Поле не може бути пустим")
    private String beginning;
    @NotBlank(message = "Поле не може бути пустим")
    private String finish;
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<City> city;
    @NotBlank(message = "Поле не може бути пустим")
    private String price;
    @NotBlank(message = "Поле не може бути пустим")
    private String date;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(mappedBy = "trips",fetch = FetchType.EAGER)
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


    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }
}
