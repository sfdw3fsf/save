package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * DanhMuc
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class PageRequest {
    @JsonProperty("pageNum")
    private Integer pageNum = null;
    @JsonProperty("pageRec")
    private Integer pageRec = null;
    @JsonProperty("totalRow")
    private Long totalRow = null;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageRec() {
        return pageRec;
    }

    public void setPageRec(Integer pageRec) {
        this.pageRec = pageRec;
    }

    public Long getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Long totalRow) {
        this.totalRow = totalRow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PageRequest danhMuc = (PageRequest) o;
        return Objects.equals(this.getPageNum(), danhMuc.getPageNum()) &&
                Objects.equals(this.getPageRec(), danhMuc.getPageRec()) &&
                Objects.equals(this.getTotalRow(), danhMuc.getTotalRow());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPageNum(), getPageRec(), getTotalRow());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Class clazz = this.getClass();
        sb.append(clazz.getSimpleName()).append(" {\n");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String propName = field.getName();
            String methodName = "get" + propName.substring(0, 1).toUpperCase() + propName.substring(1, propName.length());
            try {
                Method method = clazz.getMethod(methodName, null);
                sb.append("    ");
                sb.append(propName);
                sb.append(": ");
                sb.append(toIndentedString(method.invoke(this, null)));
                sb.append("\n");
            } catch (Exception e) {

            }
        }
        //sb.append("    id: ").append(toIndentedString(getID())).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        } else {
            return o.toString();
        }
    }
}
