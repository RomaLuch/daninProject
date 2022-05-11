package ru.itmo.kotiki;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {})
class ProgramTest {

    @Test
    public void contextCheck() {
        assertTrue(true);
    }


}