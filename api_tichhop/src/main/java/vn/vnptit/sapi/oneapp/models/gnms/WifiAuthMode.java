package vn.vnptit.sapi.oneapp.models.gnms;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Gets or Sets WifiAuthMode
 */
public enum WifiAuthMode {
  
  OPEN("open"),
  
  SHARED("shared"),
  
  RADIUS("radius"),
  
  WPA2("wpa2"),
  
  PSK2("psk2"),
  
  WPA_WPA2("wpa wpa2"),
  
  PSK_PSK2("psk psk2");

  private String value;

  WifiAuthMode(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static WifiAuthMode fromValue(String value) {
    for (WifiAuthMode b : WifiAuthMode.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

