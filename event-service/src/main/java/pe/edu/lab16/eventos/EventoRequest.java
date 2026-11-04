package pe.edu.lab16.eventos;
import jakarta.validation.constraints.*;import java.time.LocalDateTime;
public record EventoRequest(@NotBlank @Size(max=120) String nombre,@NotBlank @Size(max=180) String lugar,@NotNull @Future LocalDateTime fecha,@NotNull @Positive Integer capacidad,@NotBlank @Pattern(regexp="ACTIVO|CANCELADO|FINALIZADO") String estado){}
