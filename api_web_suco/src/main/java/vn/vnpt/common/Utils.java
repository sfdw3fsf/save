package vn.vnpt.common;

import com.google.common.base.CaseFormat;
import org.springframework.core.annotation.Order;
import vn.vnpt.database.SqlParameter;

import java.lang.reflect.Field;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {
	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd hh:mm a zzz");
		Date date = new Date();
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
		return sdf.format(date);
	}

	public static ArrayList<SqlParameter> process(Object obj, Integer phanVungId) {
		List<Field> fields = new ArrayList<>();
		for (Class<?> c = obj.getClass(); c != null; c = c.getSuperclass()) {
			fields.addAll(Arrays.asList(c.getDeclaredFields()));
		}
		fields.sort(new Comparator<Field>() {
			@Override
			public int compare(Field o1, Field o2) {
				Order or1 = o1.getAnnotation(Order.class);
				Order or2 = o2.getAnnotation(Order.class);
				// nulls last
				if (or1 != null && or2 != null) {
					return or1.value() - or2.value();
				} else
				if (or1 != null && or2 == null) {
					return -1;
				} else
				if (or1 == null && or2 != null) {
					return 1;
				}
				return o1.getName().compareTo(o2.getName());
			}
		});
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phan_vung_id", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		for (Field f: fields) {
			f.setAccessible(true);
			try {
				int type;
				if (Number.class.isAssignableFrom(f.getType())) type = Types.NUMERIC;
				else if (Date.class.isAssignableFrom(f.getType())) type = Types.DATE;
				else type = Types.VARCHAR;
				params.add(new SqlParameter("p_".concat(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, f.getName())),
						f.get(obj), SqlParameter.ParameterDirection.INPUT, type));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return params;
	}

	public static boolean isEmpty(Object val) {
		return val == null || "null".equals(String.valueOf(val)) || val.toString().trim().isEmpty();
	}
}
