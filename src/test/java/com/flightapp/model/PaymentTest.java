package com.flightapp.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class PaymentTest {

    @Test
    void payment_builder_shouldSetFields() {
        Payment p = Payment.builder()
                .id("PAY123")
                .amount(3500)
                .method("CARD")
                .status("SUCCESS")
                .build();

        assertThat(p.getId()).isEqualTo("PAY123");
        assertThat(p.getStatus()).isEqualTo("SUCCESS");
    }

    @Test
    void payment_setters_shouldModifyFields() {
        Payment p = new Payment();
        p.setAmount(5000);

        assertThat(p.getAmount()).isEqualTo(5000);
    }
}
