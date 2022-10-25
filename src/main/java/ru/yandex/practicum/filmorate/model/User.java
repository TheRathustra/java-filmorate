package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class User {

    long id;
    @Email
    String email;
    @NotEmpty
    String login;
    String name;
    @Past
    LocalDate birthday;
}
