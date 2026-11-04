package pe.edu.lab16.asistentes;import jakarta.validation.constraints.*;
public record AsistenteRequest(@NotBlank @Size(max=120) String nombres,@NotBlank @Email @Size(max=160) String correo,@Pattern(regexp="^[0-9+() -]{7,30}$") String telefono){}
