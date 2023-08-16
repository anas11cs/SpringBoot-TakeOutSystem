package com.tkxel.takeoutsystem.utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Log4j2
public class TakeOutSystemUtils {
    public Boolean isNotEmptyOrNullOptional(Optional value){
        try {
            if (value.isPresent() && value.get() != null) {
                return true;
            }
        }
        catch (Exception exception){
            log.error(exception);
        }
        return false;
    }
}
