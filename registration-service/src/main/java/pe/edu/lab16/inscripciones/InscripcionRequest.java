package pe.edu.lab16.inscripciones;import jakarta.validation.constraints.*;import java.time.LocalDateTime;
public record InscripcionRequest(@NotNull @Positive Long idEvento,@NotNull @Positive Long idAsistente,@NotNull @PastOrPresent LocalDateTime fecha,@NotBlank @Pattern(regexp="INSCRITA|CANCELADA") String estado){}
