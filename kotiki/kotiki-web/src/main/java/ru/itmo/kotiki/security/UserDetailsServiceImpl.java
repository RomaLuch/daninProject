package ru.itmo.kotiki.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.itmo.kotiki.cpntroller.dto.OperationType;
import ru.itmo.kotiki.cpntroller.dto.RabbitOwnerMessage;
import ru.itmo.kotiki.cpntroller.dto.UserResponseMessage;


public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RabbitOwnerMessage message = new RabbitOwnerMessage(OperationType.GET_USER_BY_NAME, null, username, null);
        String rabbitResponse = (String) rabbitTemplate.convertSendAndReceive("ownerQueue", objectMapper.writeValueAsString(message));
        UserResponseMessage userResponseMessage = objectMapper.readValue(rabbitResponse, UserResponseMessage.class);
        if (userResponseMessage == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new MyUserDetails(userResponseMessage);
    }

}
