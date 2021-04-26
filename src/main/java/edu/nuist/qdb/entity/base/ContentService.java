package edu.nuist.qdb.entity.base;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentService {
    @Autowired
    private FragmentReps fReps;

    @Autowired
    private ContentReps cReps;

    @Transactional
    public void deleteById(long id){
        Content c = findById(id);
        for(Fragment f: c.getFragments()){
            fReps.deleteById(f.getId());
        }
        cReps.delete(c);
    }

    public void save(Content c){
        Content t = cReps.save(c);
        for(Fragment f: c.getFragments()){
            f.setCid(t.getId());
            fReps.save(f);
        }
    }

    public Content findById(long id){
        Content c = cReps.findById(id);
        c.setFragments(fReps.findByCid(c.getId()));
        return c;
    }

    public Content findByRelationId(long id){
        Content c = cReps.findByRelationId(id);
        c.setFragments(fReps.findByCid(c.getId()));
        return c;
    }
}
