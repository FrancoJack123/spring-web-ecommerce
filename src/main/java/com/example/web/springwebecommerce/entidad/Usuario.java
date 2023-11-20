package com.example.web.springwebecommerce.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioId;

    private String nombreUsuario;
    private String apellidoUsuario;
    private String dniUsuario;
    private String telefonoUsuario;

    @Column(unique = true)
    private String correoUsuario;

    private String passwordUsuario;
    private Boolean estadoUsuario;

    @Column(nullable = true)
    private Boolean restablecer;

    @Column(nullable = true)
    private Boolean confirmar;

    @Column(nullable = true)
    private String token;


    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    @Column(name = "fecha_ingreso_usuario", updatable = false)
    private Date fechaIngresoUsuario;

    @ManyToOne
    @JoinColumn(name = "cargoId")
    private Rol cargoId;

}
