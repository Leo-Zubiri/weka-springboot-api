package com.zub.weka_springboot_api.repositories;

import com.zub.weka_springboot_api.models.RepContenido;
import com.zub.weka_springboot_api.models.Contenido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public class ContenidoRepository implements ContenidoRep {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Contenido> findAll(Pageable pageable) {
        return mongoTemplate.find(new Query().with(pageable), Contenido.class);
    }

    @Override
    public List<RepContenido> findDescribe(Pageable pageable) {
        return null;
    }

}