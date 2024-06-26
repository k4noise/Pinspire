package com.k4noise.pinspire.adapter.actuator;

import com.k4noise.pinspire.adapter.event.UserCreateEvent;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Map;

@Component
@WebEndpoint(id="entities-counter")
@Endpoint(id="entities-counter")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CounterActuator {
    MeterRegistry meterRegistry;

    @PostMapping("/increment-user-count")
    @EventListener
    public void handleUserCreatedEvent(UserCreateEvent event) {
        meterRegistry.counter("app.users.count").increment();
    }

    @PostMapping("/increment-pin-count")
    public void incrementPinCount() {
        meterRegistry.counter("app.pins.count").increment();
    }

    @PostMapping("/increment-board-count")
    public void incrementBoardCount() {
        meterRegistry.counter("app.boards.count").increment();
    }

    @ReadOperation
    public Map<String, Object> getCounts() {
        return Map.of("userCount", (long) meterRegistry.counter("app.users.count").count(),
                "pinCount", (long) meterRegistry.counter("app.pins.count").count(),
                "boardCount", (long) meterRegistry.counter("app.boards.count").count());
    }
}