package proyecto.ucuenca.measure.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import proyecto.ucuenca.measure.entity.Measure;
import proyecto.ucuenca.measure.service.MeasureService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/measures")
public class MeasureRest {
    @Autowired
    MeasureService measureService;

    // -------------------Retrieve All Measures--------------------------------------------
    @GetMapping
    public ResponseEntity<List<Measure>> listAllMeasures(){
        List<Measure> measures= new ArrayList<>();
        measures = measureService.findAll();
        if (measures.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(measures);
    }

    // -------------------Retrieve Single Measure------------------------------------------

    @GetMapping(value = "/{id}")
    public ResponseEntity<Measure> getMeasure(@PathVariable("id") long id) {
        log.info("Fetching Measure with id {}", id);
        Measure measure = measureService.getMeasure(id);
        if (  null == measure) {
            log.error("Measure with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(measure);
    }

    // -------------------Retrieve Last 10 Measures--------------------------------------------

    @GetMapping (value = "/lastMeasures")
    public  ResponseEntity<List<Measure>> listLastMeasures(){
        List<Measure> measures = new ArrayList<>();
        measures = measureService.findLastMeasures();
        if(measures.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(measures);
    }

    // -------------------Retrieve Last Measure--------------------------------------------

    @GetMapping (value = "/lastMeasure")
    public  ResponseEntity<Measure> getLastMeasure(){
        Measure measure = new Measure();
        measure = measureService.findLastMeasure();
        if(measure == null ){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(measure);
    }

    // -------------------Create a Measures--------------------------------------------

    @PostMapping
    public ResponseEntity<Measure> createMeasure(@Valid @RequestBody Measure measure, BindingResult result){
        log.info("Creating Measure: {}", measure);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));

        }
        Measure measureBD = measureService.createMeasure(measure);
        return ResponseEntity.status(HttpStatus.CREATED).body(measureBD);
    }

    // ------------------- Update a Measure ------------------------------------------------

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateMeasure(@PathVariable("id") long id, @RequestBody Measure measure) {
        log.info("Updating Measure with id {}", id);

        Measure currentMeasure = measureService.getMeasure(id);

        if ( null == currentMeasure ) {
            log.error("Unable to update. Measure with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        measure.setId(id);
        currentMeasure = measureService.updateMeasure(measure);
        return  ResponseEntity.ok(currentMeasure);
    }

    // ------------------- Delete a Measure-----------------------------------------
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Measure> deleteMeasure(@PathVariable("id") long id) {
        log.info("Fetching & Deleting Measure with id {}", id);

        Measure measure = measureService.getMeasure(id);
        if ( null == measure ) {
            log.error("Unable to delete. Measure with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        measure = measureService.deleteMeasure(measure);
        return  ResponseEntity.ok(measure);
    }

    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String> error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

}
