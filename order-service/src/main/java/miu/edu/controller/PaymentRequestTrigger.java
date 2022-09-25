package miu.edu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import miu.edu.dto.PlaceOrderDTO;
import miu.edu.model.Order;
import miu.edu.service.RestService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class PaymentRequestTrigger {

    @Autowired
    private RestService rest;

    @Autowired
    private ObjectMapper mapper;

    @Pointcut("@annotation(PaymentRequest)")
    public void trigger() {}

    @AfterReturning(value = "trigger()", returning ="order")
    public void sendRequest(JoinPoint joinPoint, Order order) {
        Object requestBody = joinPoint.getArgs()[0];
        log.info("Try sending request to payment");
        try {
            PlaceOrderDTO placeOrder = mapper.convertValue(requestBody, PlaceOrderDTO.class);
            log.info(placeOrder.toString());
            log.info("Sending request to payment");
            rest.paymentInitialize(placeOrder.getPaymentInfo(), order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
