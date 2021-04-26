package edu.nuist.qdb.entity.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FragmentReps extends JpaRepository<Fragment, Long>{
    public Fragment findById(long id);
    public Fragment save(Fragment f);

    @Modifying
	@Query(value = "delete from fragment where id=:fid ",nativeQuery = true)
	public void deleteByFragmentId(@Param("fid")long fid);

    @Query(value = "select * from fragment where cid=:cid ",nativeQuery = true)
    public List<Fragment> findByCid(@Param("cid")long cid);
}
