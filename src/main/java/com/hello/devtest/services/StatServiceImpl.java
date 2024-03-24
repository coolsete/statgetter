package com.hello.devtest.services;

import com.hello.devtest.exceprtions.CustomException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class StatServiceImpl implements StatService{

    private static final int STAT_INTERVAL = 60;
    private static final MathContext DEFAULT_ROUND = new MathContext(10);
    private final AtomicReference<Triple[]> stat = new AtomicReference<>(new Triple[STAT_INTERVAL]);
    @Override
    public void loadStats(String data) {
        var triple = dataParsing(data);
        Triple[] localStat = new Triple[STAT_INTERVAL];
        long current = System.currentTimeMillis();
        for (Triple curr :stat.get()) {
            if(curr != null && current - curr.timestamp > 0 && current - curr.timestamp < STAT_INTERVAL) {
                localStat[(int)(current - curr.timestamp)] = curr;
            }
        }
        if(current - triple.timestamp > 0 && current - triple.timestamp < STAT_INTERVAL) {
            localStat[(int)(current - triple.timestamp)] = triple;
        }
        stat.set(localStat);
    }
    private Triple dataParsing(String data) {
        var list = Arrays.asList(data.split(","));
        try {
            long timestamp = Long.parseLong(list.get(0));
            var realNumber = new BigDecimal(list.get(1));
            int number = Integer.parseInt(list.get(2));
            return new Triple(timestamp, realNumber, number);
        } catch (NumberFormatException ex) {
            log.error(ex.getLocalizedMessage());
            throw new CustomException("Cannot parse input data.", ex.getCause());
        }
    }

    @Override
    public String getStats() {
        Triple[] localStat = stat.get();
        var list = Arrays.stream(localStat).filter(Objects::nonNull).toList();
        int total = list.size();
        if(total == 0) {
            return "0,0,0,0,0";
        }
        BigDecimal sumX = list.stream().map(Triple::getX).reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(DEFAULT_ROUND.getPrecision(), RoundingMode.UP);
        BigDecimal avgX = sumX.divide(new BigDecimal(total), DEFAULT_ROUND);
        int sumY = list.stream().mapToInt(Triple::getY).sum();
        BigDecimal avgY = new BigDecimal(sumY).divide(new BigDecimal(total), DEFAULT_ROUND);
        return String.format("%d,%s,%s,%d,%s", list.size(), sumX.toPlainString(), avgX.toPlainString(), sumY, avgY.toPlainString());
    }

    @Getter
    @Setter
    private class Triple {
        private long timestamp;
        private BigDecimal x;
        private int y;

        public Triple(long timestamp, BigDecimal x, int y) {
            this.timestamp = timestamp;
            this.x = x;
            this.y = y;
        }
    }
}
