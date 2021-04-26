package edu.nuist.qdb.entity.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentReps  extends JpaRepository<Content, Long>{
    public Content findById( long id );
    public Content findByRelationId( long id );
    public Content save(Content f);

    @Modifying
	@Query(value = "delete from content where id=:id ",nativeQuery = true)
	public void deleteById(@Param("id")long id);

    @Query(value = "select * from content where id=:id ",nativeQuery = true)
    public List<Content> findByid(@Param("id")long id);
}
