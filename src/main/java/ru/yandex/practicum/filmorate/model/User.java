package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class User {

    private long id;
    @Email
    private String email;
    @NotEmpty
    private String login;
    private String name;
    @Past
    private LocalDate birthday;

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", id);
        values.put("email", email);
        values.put("login", login);
        values.put("name", name);
        values.put("birthday", birthday);
        return values;
    }

}
