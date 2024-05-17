package ssu.ru.stocks.models;

public enum Status {

    STARTER {
        @Override
        public String toString() {
            return "Стартовый";
        }
    },

    PRO {
        @Override
        public String toString() {
            return "Профи";
        }
    },

    PREMIUM {
        @Override
        public String toString() {
            return "Премиум";
        }
    }
}
