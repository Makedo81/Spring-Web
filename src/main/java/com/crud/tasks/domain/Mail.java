package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Mail {

    String mailTo;
    String subject;
    String message;
   // String toCc;
}
