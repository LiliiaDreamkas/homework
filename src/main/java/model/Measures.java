package model;

import lombok.Data;

@Data
public class Measures {
    private Measure original;
    private Measure metric;
    private Measure us;

    @Data
    public class Measure {
        private float amount;
        private String unit;
    }
}
