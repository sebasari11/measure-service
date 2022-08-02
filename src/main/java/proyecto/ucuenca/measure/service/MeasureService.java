package proyecto.ucuenca.measure.service;

import proyecto.ucuenca.measure.entity.Measure;

import java.util.List;

public interface MeasureService {
    public List<Measure> findAll();
    public Measure createMeasure(Measure measure);
    public Measure updateMeasure(Measure measure);
    public Measure deleteMeasure(Measure measure);
    public Measure getMeasure(Long id);
    public List<Measure> findLastMeasures();
    public Measure findLastMeasure();
}
