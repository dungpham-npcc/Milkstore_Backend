package com.cookswp.milkstore.service.otp;

import com.cookswp.milkstore.pojo.entities.TemporaryUser;
import com.cookswp.milkstore.pojo.entities.User;
import com.cookswp.milkstore.repository.user.TemporaryUserRepository;
import com.cookswp.milkstore.repository.user.UserRepository;
import com.cookswp.milkstore.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
public class OtpService {
    private final TemporaryUserRepository temporaryUserRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public OtpService(UserRepository userRepository,
                      TemporaryUserRepository temporaryUserRepository,
                      EmailService emailService,
                      PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.temporaryUserRepository =temporaryUserRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }
    public String generateOtp(){
        SecureRandom random = new SecureRandom();
        return String.format("%06d", random.nextInt(1000000));
    }

    public void sendForgotPasswordOtpByEmail(String email){
        String otp = generateOtp();

        User user = userRepository.findByEmailAddress(email);
        user.setOtpCode(passwordEncoder.encode(otp));
        user.setOtpCreatedAt(LocalDateTime.now());
        user.setOtpExpiredAt(LocalDateTime.now().plusMinutes(10));
        userRepository.save(user);
        emailService.sendMessage(email,
                "Mã xác nhận để thiết lập lại mật khẩu",
                "Mã của bạn là: " + otp);
    }

    public void sendRegistrationOtpByEmail(String email) {
        if (temporaryUserRepository.findByEmailAddress(email) != null)
            temporaryUserRepository.deleteByEmailAddress(email);
        TemporaryUser newTempUser = new TemporaryUser();
        newTempUser.setEmailAddress(email);
        String otp = generateOtp();
        newTempUser.setOtpCode(passwordEncoder.encode(otp));
        newTempUser.setOtpCreatedAt(LocalDateTime.now());
        newTempUser.setOtpExpiredAt(LocalDateTime.now().plusMinutes(10));
        temporaryUserRepository.save(newTempUser);
        emailService.sendMessage(email,
                "Mã xác nhận đăng kí email mới",
                "Mã của bạn là: " + otp);
    }

    public boolean isOtpValid(String email, String inputOtpCode, boolean isRegister){
        if (isRegister) {
            TemporaryUser user = temporaryUserRepository.findByEmailAddress(email);
            return user != null &&
                    passwordEncoder.matches(inputOtpCode, user.getOtpCode()) &&
                    !LocalDateTime.now().isAfter(user.getOtpExpiredAt());
        } else {
            User user = userRepository.findByEmailAddress(email);
            return user != null &&
                    passwordEncoder.matches(inputOtpCode, user.getOtpCode()) &&
                    !LocalDateTime.now().isAfter(user.getOtpExpiredAt());
        }
    }

    public void deleteTempUser(String email){
        temporaryUserRepository.deleteByEmailAddress(email);
    }
}
