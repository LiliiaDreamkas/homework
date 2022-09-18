package lesson5.model;

import lombok.Data;

@Data
public class ErrorBody {
    private int status;
    private String message;
    private String timestamp;
}
