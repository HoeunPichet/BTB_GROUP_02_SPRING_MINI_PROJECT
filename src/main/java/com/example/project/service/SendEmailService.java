package com.example.project.service;

import com.example.project.exception.AppNotFoundException;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public interface SendEmailService {
    String generateOtp();

    void sendOtpEmail(String toEmail, String otp) throws IOException;

    String loadTemplate(String otp) throws IOException;
}
