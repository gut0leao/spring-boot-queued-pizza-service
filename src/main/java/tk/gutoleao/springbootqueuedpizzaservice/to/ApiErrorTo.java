package tk.gutoleao.springbootqueuedpizzaservice.to;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ApiErrorTo {

    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiErrorTo(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}