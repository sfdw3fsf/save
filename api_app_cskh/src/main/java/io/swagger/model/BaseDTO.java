package io.swagger.model;
import java.lang.reflect.*;

public class BaseDTO {
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

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        } else {
            return o.toString();
        }
    }
}
