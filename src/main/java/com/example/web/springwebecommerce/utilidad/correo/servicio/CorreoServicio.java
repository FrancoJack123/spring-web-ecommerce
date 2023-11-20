package com.example.web.springwebecommerce.utilidad.correo.servicio;

import com.example.web.springwebecommerce.utilidad.correo.modelo.Correo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class CorreoServicio {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String email;

    public void enviarEmail(Correo correo){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(email);
        simpleMailMessage.setSubject(correo.getAsunto());
        simpleMailMessage.setText(correo.getMensaje());
        simpleMailMessage.setTo(correo.getPara());

        mailSender.send(simpleMailMessage);
    }
}
