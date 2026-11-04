package pe.edu.lab16.eventos;
import jakarta.persistence.*;import java.time.LocalDateTime;
@Entity @Table(name="eventos") public class Evento{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=120) private String nombre;
 @Column(nullable=false,length=180) private String lugar;
 @Column(nullable=false) private LocalDateTime fecha;
 @Column(nullable=false) private Integer capacidad;
 @Column(nullable=false,length=20) private String estado;
 public Long getId(){return id;}public void setId(Long v){id=v;}public String getNombre(){return nombre;}public void setNombre(String v){nombre=v;}public String getLugar(){return lugar;}public void setLugar(String v){lugar=v;}public LocalDateTime getFecha(){return fecha;}public void setFecha(LocalDateTime v){fecha=v;}public Integer getCapacidad(){return capacidad;}public void setCapacidad(Integer v){capacidad=v;}public String getEstado(){return estado;}public void setEstado(String v){estado=v;}}
