package ssu.ru.stocks.models;

import lombok.Getter;

import static ssu.ru.stocks.util.Constants.*;

@Getter
public enum Status {

    STARTER(STARTER_LB, STARTER_UB) {
        @Override
        public String toString() {
            return "Стартовый";
        }
    },

    PRO(PRO_LB, PRO_UB) {
        @Override
        public String toString() {
            return "Профи";
        }
    },

    PREMIUM(PREMIUM_LB, PREMIUM_UB) {
        @Override
        public String toString() {
            return "Премиум";
        }
    };

    private final double lowerBound;
    private final double upperBound;

    Status(double lowerBound, double upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }
}
