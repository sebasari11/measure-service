package proyecto.ucuenca.measure.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import proyecto.ucuenca.measure.entity.Measure;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class MeasureImpl implements MeasureService{
    private final MongoOperations mongoOperations;

    public MeasureImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public List<Measure> findAll() {
        return mongoOperations.findAll(Measure.class);
    }

    @Override
    public Measure getMeasure(Long id) {
        Measure measure = this.mongoOperations.findOne(new Query(Criteria.where("id").is(id)), Measure.class);
        return measure;
    }

    @Override
    public Measure createMeasure(Measure measure) {
        Measure measureDB= getMeasure(measure.getId());
        if (measureDB != null) {
            return measureDB;
        }
        measure.setCreate(new Date());
        measureDB = mongoOperations.save(measure);
        return measureDB;
    }

    @Override
    public Measure updateMeasure(Measure measure) {
        Measure measureDB = getMeasure(measure.getId());
        if (measureDB == null) {
            return null;
        }
        measureDB.setUserId(measure.getUserId());
        measureDB.setSystolicPressure(measure.getSystolicPressure());
        measureDB.setDiastolicPressure(measure.getDiastolicPressure());
        measureDB.setSteps(measure.getSteps());
        measureDB.setPulse(measure.getPulse());
        measureDB.setCreate(measure.getCreate());
        return this.mongoOperations.save(measureDB);
    }

    @Override
    public Measure deleteMeasure(Measure measure) {
        Measure measureDB = getMeasure(measure.getId());
        if (measureDB == null) {
            return null;
        }
        Long id = measureDB.getId();
        return this.mongoOperations.findAndRemove(new Query(Criteria.where("Id").is(id)), Measure.class);
    }

    @Override
    public List<Measure> findLastMeasures() {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC, "id"));
        query.limit(10);
        List<Measure> measuresDB = this.mongoOperations.find(query,Measure.class);
        return measuresDB;
    }

    @Override
    public Measure findLastMeasure() {
        List<Measure> measuresDB = this.findLastMeasures();
        Measure measureDB = measuresDB.get(0);
        return measureDB;
    }
}
