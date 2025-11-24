package com.diana.pure.model;

import java.time.LocalDate;

public record Meeting(
        LocalDate date,
        String place,
        String description
) {
}