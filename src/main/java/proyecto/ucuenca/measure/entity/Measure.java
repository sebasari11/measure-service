package proyecto.ucuenca.measure.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "measure")
@JsonPropertyOrder({"create", "id"})
public class Measure {
    @Id
    @NotNull
    private Long id;

    @NotNull
    @Field("user_id")
    private Long userId;

    @NotNull
    @Field("systolic_pressure")
    private Double systolicPressure;

    @NotNull
    @Field("diastolic_pressure")
    private Double diastolicPressure;

    @NotNull
    private Double steps;

    @NotNull
    private Double pulse;

    private Date create;
}
