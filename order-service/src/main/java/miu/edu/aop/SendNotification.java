package miu.edu.aop;

public @interface SendNotification {
    public String type() default "message";
}
