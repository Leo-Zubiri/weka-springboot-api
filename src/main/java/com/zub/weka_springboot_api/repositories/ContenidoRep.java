package com.zub.weka_springboot_api.repositories;

import com.zub.weka_springboot_api.models.RepContenido;
import com.zub.weka_springboot_api.models.Contenido;
import org.springframework.data.repository.NoRepositoryBean;

import org.springframework.data.domain.Pageable;
import java.util.List;

@NoRepositoryBean
public interface ContenidoRep {
    public List<Contenido> findAll(Pageable pageable);
    public List<RepContenido> findDescribe(Pageable pageable);
}
