
package com.totallygroup.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class DayforceSelection {

    private String loadedRegionTitle;

    @Override
    public String toString() {
        return "DayforceSelection{" +
                "loadedRegionTitle='" + loadedRegionTitle + '\'' +
                '}';
    }
}
