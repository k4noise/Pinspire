package com.k4noise.pinspire.common.metrics.counter;

import com.k4noise.pinspire.adapter.actuator.CounterActuator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CounterAspect {
   CounterActuator counterActuator;

    @After("@annotation(CounterMetric) && within(com.k4noise.pinspire.service.PinService)")
    public void incrementPinCount() {
       counterActuator.incrementPinCount();
    }

    @After("@annotation(CounterMetric) && within(com.k4noise.pinspire.service.BoardService)")
    public void incrementBoardCount() {
        counterActuator.incrementBoardCount();
    }
}