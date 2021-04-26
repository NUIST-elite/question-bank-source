package edu.nuist.qdb.entity.component;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComponentReps extends JpaRepository<Component, Long>{
    public Component save(Component c);
    
    public Component findById( long id );

    List<Component> findByRelId(long relId);

    @Modifying
	@Query(value = "delete from component where id=:id ",nativeQuery = true)
	public void deleteById(@Param("id")long id);

}
