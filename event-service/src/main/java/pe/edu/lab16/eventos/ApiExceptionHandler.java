package pe.edu.lab16.eventos;
import org.springframework.http.*;import org.springframework.web.bind.MethodArgumentNotValidException;import org.springframework.web.bind.annotation.*;import org.springframework.dao.DataIntegrityViolationException;import java.time.Instant;import java.util.*;
@RestControllerAdvice public class ApiExceptionHandler{
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<Map<String,Object>> validar(MethodArgumentNotValidException e){List<String> errores=e.getBindingResult().getFieldErrors().stream().map(x->x.getField()+": "+x.getDefaultMessage()).toList();return ResponseEntity.badRequest().body(Map.of("timestamp",Instant.now().toString(),"status",400,"error","Validación fallida","detalles",errores));}
 @ExceptionHandler(DataIntegrityViolationException.class) ResponseEntity<Map<String,Object>> integridad(DataIntegrityViolationException e){return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("timestamp",Instant.now().toString(),"status",409,"error","Conflicto de integridad","detalles","Verifique correo o inscripción duplicados"));}
}
