package edu.nuist.qdb.entity.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  AttributeReps extends JpaRepository<Attribute, Long>{
    public Attribute save(Attribute attribute);

    public List<Attribute> findByRelationId( long relationId );
}
