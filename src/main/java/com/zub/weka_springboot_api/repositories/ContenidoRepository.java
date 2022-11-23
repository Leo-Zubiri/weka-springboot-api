package com.zub.weka_springboot_api.repositories;

import com.zub.weka_springboot_api.models.RepContenido;
import com.zub.weka_springboot_api.models.Contenido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationExpression;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public class ContenidoRepository implements ContenidoRep {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Contenido> findAll(Pageable pageable) {
        return mongoTemplate.find(new Query().with(pageable), Contenido.class);
    }

    @Override
    public Contenido findByID(String id){
        Contenido contenido = mongoTemplate.findOne(new Query(Criteria.where("_id").is(id)), Contenido.class);
        //System.out.println(contenido);
        return contenido;
    }

    @Override
    public List<RepContenido> findDescribe(Pageable pageable) {

        /*
        AggregationExpression mapRating = ConditionalOperators.switchCases(
                ConditionalOperators.Switch.CaseOperator.when(ComparisonOperators.valueOf("rating").equalToValue("TV-Y")).then(1),
                ConditionalOperators.Switch.CaseOperator.when(ComparisonOperators.valueOf("rating").equalToValue("TV-Y7")).then(2),
                ConditionalOperators.Switch.CaseOperator.when(ComparisonOperators.valueOf("rating").equalToValue("TV-Y7FV")).then(3),
                ConditionalOperators.Switch.CaseOperator.when(ComparisonOperators.valueOf("rating").equalToValue("TV-G")).then(4),
                ConditionalOperators.Switch.CaseOperator.when(ComparisonOperators.valueOf("rating").equalToValue("G")).then(4),
                ConditionalOperators.Switch.CaseOperator.when(ComparisonOperators.valueOf("rating").equalToValue("TV-PG")).then(5),
                ConditionalOperators.Switch.CaseOperator.when(ComparisonOperators.valueOf("rating").equalToValue("PG")).then(5),
                ConditionalOperators.Switch.CaseOperator.when(ComparisonOperators.valueOf("rating").equalToValue("TV-14")).then(6),
                ConditionalOperators.Switch.CaseOperator.when(ComparisonOperators.valueOf("rating").equalToValue("PG-13")).then(6),
                ConditionalOperators.Switch.CaseOperator.when(ComparisonOperators.valueOf("rating").equalToValue("NC-17")).then(7),
                ConditionalOperators.Switch.CaseOperator.when(ComparisonOperators.valueOf("rating").equalToValue("TV-MA")).then(7),
                ConditionalOperators.Switch.CaseOperator.when(ComparisonOperators.valueOf("rating").equalToValue("R")).then(8)
                //ConditionalOperators.Switch.CaseOperator.when(ComparisonOperators.valueOf("qty").greaterThanEqualToValue(100)).then("WARNING"),
                //ConditionalOperators.Switch.CaseOperator.when(ComparisonOperators.valueOf("qty").equalToValue(0)).then("EMPTY")
        ).defaultTo(-1);
        */
        return mongoTemplate.aggregate(
                Aggregation.newAggregation(
                        Aggregation.project("_id","popular_rank","certificate","startYear","endYear","episodes",
                                "runtime","type","orign_country","language","rating","numVotes","genres")

                                /*
                                .and(ConditionalOperators.Cond.when(
                                        ComparisonOperators.Eq.valueOf("type").equalTo("Movie")
                                ).then(1).otherwise(2)
                                ).as("type")

                                .and(
                                        mapRating
                                ).as("rating")*/
                )
                ,"contenido",RepContenido.class).getMappedResults();


        //return mongoTemplate.aggregate(, "contenido", RepContenido.class).getMappedResults();

    }
}