package vn.vnpt.vnptit.ecms.dto.idc;

import com.fasterxml.jackson.databind.ObjectMapper;
import vn.vnpt.common.exception.BadRequestExceptionMessage;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class BaseMap {
    private final Map<String, Object> map;
    private final List<String> errors = new ArrayList<>();

    public BaseMap(Map<String, Object> map) {
        if (map == null) throw new NullPointerException("Contructor initial is null!");
        this.map = map;
    }
    // validation
    public BaseMap validEmpty(String key, String... msg) {
        if (isKeyEmpty(key)) {
            if (msg == null) {
                this.errors.add("invalid key: " + key);
            } else {
                this.errors.addAll(Arrays.asList(msg));
            }
        }
        return this;
    }
    public BaseMap validNumber(String key, String... msg) {
        if (isKeyEmpty(key)) {
            if (msg == null) {
                this.errors.add("invalid key: " + key);
            } else {
                this.errors.addAll(Arrays.asList(msg));
            }
        } else {
            try {
                Long.parseLong(map.get(key).toString());
            } catch (Exception e) {
                this.errors.add("ExceptionInfo: " + e.getMessage());
            }
        }
        return this;
    }
    public BaseMap validNumberIfNotEmpty(String key, String... msg) {
        if (isKeyEmpty(key)) return this;
        try {
            Long.parseLong(map.get(key).toString());
        } catch (Exception e) {
            this.errors.add("ExceptionInfo: " + e.getMessage());
        }
        return this;
    }
    @SuppressWarnings(value = "unchecked")
    public BaseMap validListNumId(String key, String... msg) {
        if (isKeyEmpty(key)) {
            if (msg == null) {
                this.errors.add("invalid key: " + key);
            } else {
                this.errors.addAll(Arrays.asList(msg));
            }
        }
        List<Object> idList = new ArrayList<>();
        try {
            idList = this.get(key, List.class);
            if (idList == null || idList.isEmpty()
                    || idList.stream().anyMatch(id -> Objects.isNull(id))) {
                this.errors.add("invalid key: " + key);
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.errors.add(e.getMessage());
        }

        return this;
    }
    @SuppressWarnings(value = "unchecked")
    public BaseMap validList(String key, List<String> keyReqList, String... msg) {
        if (isKeyEmpty(key)) {
            if (msg == null) {
                this.errors.add("invalid key: " + key);
            } else {
                this.errors.addAll(Arrays.asList(msg));
            }
        }
        List<Map<String, Object>> objList = new ArrayList<>();
        try {
            objList = this.get(key, List.class);
            if (objList == null || objList.isEmpty()) {
                this.errors.add("invalid key: " + key);
                return this;
            }
            objList.forEach(map -> {
                for (String keyReq : keyReqList) {
                    if (!map.containsKey(keyReq) || isEmpty(map.get(keyReq))) {
                        this.errors.add("invalid key required: " + keyReq);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            this.errors.add(e.getMessage());
        }
        return this;
    }
    public BaseMap validListIfNotEmpty(String key, List<String> keyReqList, String... msg) {
        if (isKeyEmpty(key)) return this;
        List<Map<String, Object>> objList = new ArrayList<>();
        try {
            objList = this.get(key, List.class);
            if (objList == null || objList.isEmpty()) {
                this.errors.add("invalid key: " + key);
                return this;
            }
            objList.forEach(map -> {
                for (String keyReq : keyReqList) {
                    if (!map.containsKey(keyReq) || isEmpty(map.get(keyReq))) {
                        this.errors.add("invalid key required: " + keyReq);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            this.errors.add(e.getMessage());
        }
        return this;
    }
    public BaseMap validDate(String key, String ft, String... msg) {
        if (isKeyEmpty(key)) {
            if (msg == null) {
                this.errors.add("invalid key: " + key);
            } else {
                this.errors.addAll(Arrays.asList(msg));
            }
        } else {
            try {
                DateFormat dateFt = new SimpleDateFormat(ft);
                dateFt.parse(this.map.get(key).toString());
            } catch (Exception e) {
                this.errors.add(e.getMessage());
            }
        }
        return this;
    }
    public BaseMap validDateIfNotEmpty(String key, String ft, String... msg) {
        if (this.isKeyEmpty(key)) return this;
        try {
            DateFormat dateFt = new SimpleDateFormat(ft);
            dateFt.parse(this.map.get(key).toString());
        } catch (Exception e) {
            this.errors.add(e.getMessage());
            this.errors.addAll(Arrays.asList(msg));
        }
        return this;
    }
    public void validComplate() {
        if (!this.errors.isEmpty()) throw new BadRequestExceptionMessage(String.join(", ", this.errors));
    }


    public <T> T get(String key, Class<T> type) {
        if (isKeyEmpty(key)) return null;
        return this.castValue(this.map.get(key),  type);
    }

    public <T> T get(String key, Class<T> type, T df) {
        if (isKeyEmpty(key)) return df;
        return this.castValue(this.map.get(key),  type);
    }

    public boolean isKeyEmpty(String key) {
        return !this.map.containsKey(key) || isEmpty(this.map.get(key));
    }

    private boolean isEmpty(Object val) {
        return val == null || "null".equals(val) || val.toString().trim().isEmpty();
    }

    @SuppressWarnings(value = "unchecked")
    public String getListIdToString(String key) {
        String result;
        try {
            List<Object> data = this.get(key, List.class);
            result = data.stream().map(Object::toString).collect(Collectors.joining(","));
        } catch (Exception ex) {
            throw new ValidationException(String.join(", ", ex.getMessage()));
        }
        return result;
    }

    @SuppressWarnings(value = "unchecked")
    public String getListObjWithJson(String key) {
        String result;
        try {
            List<Map<String, Object>> data = this.get(key, List.class);
            if (data == null) return null;
            result = new ObjectMapper().writeValueAsString(data);
        } catch (Exception ex) {
            throw new BadRequestExceptionMessage(String.join(", ", ex.getMessage()));
        }
        return result;
    }

    private <T> T castValue(Object value, Class<T> type) {
        try {
            if (type == String.class) {
                return type.cast(value);

            } else if (type == (Number.class)) {
                return type.cast(value);

            } else if (type == (Long.class)) {
                Long v = Long.parseLong(value.toString());
                return type.cast(v);

            } else if (type == (short.class) || type == (Short.class)) {
                Short v = Short.parseShort(value.toString());
                return type.cast(v);

            } else if (type == (Integer.class)) {
                Integer v = Integer.parseInt(value.toString());
                return type.cast(v);

            }  else if (type == (UUID.class)) {
                UUID v = UUID.fromString(value.toString());
                return type.cast(v);

            }  else if (type == (BigDecimal.class)) {
                BigDecimal v = new BigDecimal(value.toString());
                return type.cast(v);
            }
            return type.cast(value);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ValidationException(String.join(", ", ex.getMessage()));
        }
    }
}
