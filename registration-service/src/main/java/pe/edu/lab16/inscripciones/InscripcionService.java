package pe.edu.lab16.inscripciones;
import org.springframework.stereotype.Service;import org.springframework.http.HttpStatus;import org.springframework.web.server.ResponseStatusException;import org.springframework.web.client.RestClient;import org.springframework.web.client.RestClientException;import java.util.List;
@Service public class InscripcionService{
 private final InscripcionRepository repo;private final RestClient client;
 public InscripcionService(InscripcionRepository r,RestClient.Builder b){repo=r;client=b.build();}
 public List<Inscripcion> listar(){return repo.findAll();}
 public Inscripcion obtener(Long id){return repo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Inscripción no encontrada"));}
 public Inscripcion crear(InscripcionRequest r){verificar(r);if(repo.existsByIdEventoAndIdAsistente(r.idEvento(),r.idAsistente()))throw new ResponseStatusException(HttpStatus.CONFLICT,"El asistente ya está inscrito en el evento");return repo.save(mapear(new Inscripcion(),r));}
 public Inscripcion actualizar(Long id,InscripcionRequest r){Inscripcion i=obtener(id);verificar(r);return repo.save(mapear(i,r));}
 public void eliminar(Long id){repo.delete(obtener(id));}
 private Inscripcion mapear(Inscripcion i,InscripcionRequest r){i.setIdEvento(r.idEvento());i.setIdAsistente(r.idAsistente());i.setFecha(r.fecha());i.setEstado(r.estado());return i;}
 private void verificar(InscripcionRequest r){try{EventoDto e=client.get().uri("http://event-service/eventos/{id}",r.idEvento()).retrieve().body(EventoDto.class);if(e==null)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Evento no encontrado");if(!"ACTIVO".equals(e.estado()))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El evento no está activo");long ocupados=repo.countByIdEventoAndEstado(r.idEvento(),"INSCRITA");if(ocupados>=e.capacidad())throw new ResponseStatusException(HttpStatus.CONFLICT,"El evento alcanzó su capacidad");client.get().uri("http://attendee-service/asistentes/{id}",r.idAsistente()).retrieve().toBodilessEntity();}catch(RestClientException ex){throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Evento o asistente inexistente, o servicio no disponible",ex);}}
 public record EventoDto(Long id,String nombre,String lugar,java.time.LocalDateTime fecha,Integer capacidad,String estado){}
}
