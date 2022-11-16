package com.zub.weka_springboot_api.controllers;

import com.zub.weka_springboot_api.models.Contenido;
import com.zub.weka_springboot_api.repositories.ContenidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/contenido")
public class ContenidoController {
    @Autowired
    private ContenidoRepository contenidoRepository;

    @GetMapping("/findAll")
    public List<Contenido> findAll(Pageable pageable){
        return contenidoRepository.findAll(pageable);
    }

    @GetMapping("/findDescribe")
    public List<Contenido> findDescribe(Pageable pageable){
        return contenidoRepository.findAll(pageable);
    }
}
