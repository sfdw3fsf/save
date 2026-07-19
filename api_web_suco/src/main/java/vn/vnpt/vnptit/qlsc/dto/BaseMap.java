package vn.vnpt.vnptit.qlsc.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaseMap<K, V> extends HashMap<K, V> {

    public String getString(Object key) {
        return get(key) == null ? null : String.valueOf(get(key));
    }

    public Integer getInt(Object key) {
        String value = this.getString(key);
        if (value == null)
            return null;
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid integer value for key: " + key);
        }
    }

    public Long getLong(Object key) {
        String value = this.getString(key);
        if (value == null)
            return null;
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid long value for key: " + key);
        }
    }

    public Double getDouble(Object key) {
        String value = this.getString(key);
        if (value == null)
            return null;
        try {
            return Double.valueOf(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid double value for key: " + key);
        }
    }

    public Boolean getBoolean(Object key) {
        String value = this.getString(key);
        if (value == null)
            return null;
        try {
            return Boolean.parseBoolean(value);
        } catch (Exception e) {
            throw new RuntimeException("Invalid boolean value for key: " + key);
        }
    }

    public LocalDateTime getDate(Object key, String ft) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ft);
        String value = this.getString(key);
        if (value == null)
            return null;
        try {
            return LocalDateTime.parse(value, formatter);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Invalid date format for key: " + key);
        }
    }

    public class Valid {

        private final List<String> errMsgList = new ArrayList<>();

        /**
         * Validates that the value for the given key is a valid number
         *
         * @param key The key to validate
         * @param msg Optional error message to throw if validation fails
         * @return this Valid instance for method chaining
         */
        public Valid isNumber(Object key, String... msg) {
            String value = BaseMap.this.getString(key);
            if (value != null) {
                try {
                    Double.parseDouble(value);
                } catch (NumberFormatException e) {
                    String errorMsg = (msg != null && msg.length > 0)
                            ? msg[0]
                            : "Invalid number value for key: " + key;
                    errMsgList.add(errorMsg);
                }
            }
            return this;
        }

        /**
         * Validates that the value for the given key is not empty
         *
         * @param key The key to validate
         * @param msg Optional error message to throw if validation fails
         * @return this Valid instance for method chaining
         */
        public Valid isEmpty(Object key, String... msg) {
            String value = BaseMap.this.getString(key);
            if (value == null || value.trim().isEmpty()) {
                String errorMsg = (msg != null && msg.length > 0)
                        ? msg[0]
                        : "Value is required for key: " + key;
                errMsgList.add(errorMsg);
            }
            return this;
        }

        /**
         * Validates that the value for the given key matches the specified date format
         *
         * @param key    The key to validate
         * @param format The date format pattern (e.g., "yyyy-MM-dd")
         * @param msg    Optional error message to throw if validation fails
         * @return this Valid instance for method chaining
         */
        public Valid isDateWithFormat(Object key, String format, String... msg) {
            String value = BaseMap.this.getString(key);
            if (value != null) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                    LocalDateTime.parse(value, formatter);
                } catch (DateTimeParseException e) {
                    String errorMsg = (msg != null && msg.length > 0)
                            ? msg[0]
                            : "Invalid date format for key: " + key + ", expected format: " + format;
                    errMsgList.add(errorMsg);
                }
            }
            return this;
        }

        /**
         * Validates that the value for the given key is not null
         *
         * @param key The key to validate
         * @param msg Optional error message to throw if validation fails
         * @return this Valid instance for method chaining
         */
        public Valid isNotNull(Object key, String... msg) {
            if (get(key) == null) {
                String errorMsg = (msg != null && msg.length > 0)
                        ? msg[0]
                        : "Value cannot be null for key: " + key;
                errMsgList.add(errorMsg);
            }
            return this;
        }

        /**
         * Executes all collected validations
         *
         * @throws RuntimeException if any validation fails, with all error messages
         *                              numbered
         */
        public void complete() {
            if (!errMsgList.isEmpty()) {
                String[] formattedErrors = new String[errMsgList.size()];
                for (int i = 0; i < errMsgList.size(); i++) {
                    formattedErrors[i] = (i + 1) + ". " + errMsgList.get(i);
                }
                throw new RuntimeException(String.join("\n", formattedErrors));
            }
        }
    }

    /**
     * Creates a new Valid instance for validation chaining
     *
     * @return a new Valid instance
     */
    public Valid valid() {
        return new Valid();
    }

}

