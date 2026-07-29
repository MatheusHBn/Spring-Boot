package Matheuszin_springboot.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus()
public class CustomNotFoundException extends RuntimeException{
    public CustomNotFoundException(String message) {
        super(message);
    }
}
