package com.dama.backend.dama.auth;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @Valid  

    @Size(max=255)
    @NotNull (message="il nome è obbligatorio")
    @NotBlank (message="il nome non può essere vuoto")
    private String name;

    @Size(max=255)
    @NotNull (message="il cognome è obbligatorio")
    @NotBlank (message="il cognome non può essere vuoto")
    private String surname;
    
    @Size(min = 8,max=255)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).*$", message = "La password deve contenere almeno un numero, una lettera minuscola, una lettera maiuscola e un carattere speciale.")
    @NotNull (message="la password è obbligatoria")
    @NotBlank (message="la password non può essere vuota")
    private String password;
    
    @Size(max=255)
    @NotNull (message="l'username è obbligatorio")
    @NotBlank (message="l'username non può essere vuoto")
    private String username;
    
    @Size(max=255)
    @NotNull (message="l'email è obbligatoria")
    @NotBlank (message="l'email non può essere vuota")
    @Email
    private String email;
}
