package com.faceit.example.dto.request.postgre;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserRequest {

    @NotBlank(message = "exception.pleaseProvideAUsername")
    @Length(min = 1, max = 50, message = "exception.incorrectLengthUsername")
    private String username;
    @Length(min = 5, max = 30, message = "exception.incorrectPassword")
    private String password;
    @NotBlank(message = "exception.pleaseProvideYourFirstName")
    @Length(min = 1, max = 50, message = "exception.incorrectLengthFirstName")
    private String firstName;
    @NotBlank(message = "exception.pleaseProvideYourLastName")
    @Length(min = 1, max = 50, message = "exception.incorrectLengthLastName")
    private String lastName;
    @Email(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message = "exception.pleaseProvideAValidEmail")
    @NotBlank(message = "exception.pleaseProvideAnEmail")
    private String email;
    private int age;
}
