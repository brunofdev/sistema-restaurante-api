package com.restaurante01.api_restaurante.log;

import com.restaurante01.api_restaurante.log.repository.LogRepository;
import org.apache.juli.logging.Log;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    private final LogRepository logRepository;

    public LogService(LogRepository logRepository){
        this.logRepository = logRepository;
    }



}
