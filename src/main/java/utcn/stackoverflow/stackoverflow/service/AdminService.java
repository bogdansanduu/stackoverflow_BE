package utcn.stackoverflow.stackoverflow.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import utcn.stackoverflow.stackoverflow.dto.UserDTO;
import utcn.stackoverflow.stackoverflow.entity.User;
import utcn.stackoverflow.stackoverflow.repository.UserRepository;

@Service
public class AdminService {

    @Value("${TWILIO_ACCOUNT_SID}")
    String ACCOUNT_SID;
    @Value("${TWILIO_AUTH_TOKEN}")
    String AUTH_TOKEN;
    @Value("${TWILIO_OUTGOING_SMS_NUMBER}")
    String OUTGOING_SMS_NUMBER;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;

    public User saveUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        return userRepository.save(user);
    }

    @PostConstruct
    private void setup() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public UserDTO banUser(Long userId) {
        User user = userRepository.findByUserId(userId);

        if (user == null) {
            return null;
        }

        if (!user.isBanned()) {
            sendEmail(user.getEmail(), "Banned", "Banned");

            Message message = Message.creator(
                    new PhoneNumber(user.getPhoneNumber()),
                    new PhoneNumber(OUTGOING_SMS_NUMBER),
                    "YOU ARE BANNED"
            ).create();

            System.out.println(message.getStatus().toString());
        }

        user.setBanned(!user.isBanned());

        User userSaved = userRepository.save(user);
        return new UserDTO(userSaved.getUserId(), userSaved.getFirstName(), user.getLastName(), user.getScore(), user.getRole(), user.isBanned());
    }

    private void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sandubogdan2001@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);

        System.out.println("Mail Sent successfully!");
    }

    private void sendSMS(String smsNumber, String smsMessage) {
    }

}
