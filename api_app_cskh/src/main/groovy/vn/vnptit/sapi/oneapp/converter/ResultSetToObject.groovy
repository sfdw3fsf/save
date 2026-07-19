package vn.vnptit.sapi.oneapp.converter;

//import net.logstash.logback.encoder.org.apache.commons.lang3.ObjectUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


import java.lang.reflect.Field
import java.math.BigDecimal
import java.sql.Clob
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Types
import java.text.SimpleDateFormat

@Service
class ResultSetToObjectv {

    static final String NULL = "NULL"

    public <T> List<T> getDanhMuc(ResultSet rs, Class<T> type) {
        List<T> ret = []
        if (rs != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            int columnCnt = rs.getMetaData().getColumnCount()
            List<String> objectFieldMatch = []
            for (int i = 1; i <= columnCnt; ++i) {
                Field f = type.declaredFields.find { it.name.equalsIgnoreCase(rs.getMetaData().getColumnName(i)) }
                /*for (Field fi : type.getDeclaredFields()) {
                if (fi.name.equalsIgnoreCase(rs.getMetaData().getColumnName(i))) {
                    f = fi
                }
            }*/
                if (f) objectFieldMatch.add(f.name)
                else objectFieldMatch.add(NULL)
            }
            while (rs.next()) {
                T obj = type.getConstructor().newInstance()

                for (int i = 1; i <= columnCnt; ++i) {
                    String fName = objectFieldMatch[i - 1]
                    if (fName != NULL) {
                        try {
                            if (rs.getMetaData().getColumnType(i) == Types.DATE) {
                                obj[fName] = rs.getDate(i) ? sdf.format(new Date(rs.getDate(i).getTime())) : null
                            } else if (rs.getMetaData().getColumnType(i) == Types.TIMESTAMP) {
                                obj[fName] = rs.getTimestamp(i) ? sdf.format(new Date(rs.getTimestamp(i).getTime())) : null
                            } else if (rs.getMetaData().getColumnType(i) == Types.CLOB) {
                                java.sql.Clob clob = rs.getClob(i)
                                obj[fName] = clob.getSubString(1, (int) clob.length())
                            } else {
                                obj[fName] = rs.getObject(i)
                            }
                        } catch (e) {
                            //loggingManaged.error(e.message + fName, e)
                        }
                    }
                }
                ret.add(obj)
            }
        }
        ret
    }

    List<Map<String, Object>> getDynamicData(ResultSet rs) {
        List<Map<String, Object>> listData = new ArrayList<>()
        if (rs != null) {
            int columnCnt = rs.getMetaData().getColumnCount()
            while (rs.next()) {
                Map<String, Object> data = new HashMap<>()
                for (int i = 1; i <= columnCnt; ++i) {
                    String fName = rs.getMetaData().getColumnName(i)
                    try {
                        Object fValue
                        if (rs.getMetaData().getColumnType(i) == Types.DATE) {
                            fValue = rs.getDate(fName) ? new Date(rs.getDate(fName).getTime()) : null
                        } else if (rs.getMetaData().getColumnType(i) == Types.CLOB) {
                            java.sql.Clob clob = rs.getClob(i)
                            fValue = clob.getSubString(1, (int) clob.length())
                        } else if (rs.getMetaData().getColumnType(i) == Types.NCLOB) {
                            java.sql.NClob clob = rs.getNClob(i)
                            fValue = clob.getSubString(1, (int) clob.length())
                        } else if (rs.getMetaData().getColumnType(i) == Types.BLOB) {
                            java.sql.Blob clob = rs.getBlob(i)
                            fValue = Base64.getEncoder().encodeToString(clob.getBytes(1, (int) clob.length()))
                        } else if (rs.getMetaData().getColumnType(i) == Types.ROWID) {
                            java.sql.RowId rowId = rs.getRowId(i)
                            fValue = rowId.toString()
                        } else {
                            fValue = rs.getObject(fName)
                        }
                        data.put(fName.toLowerCase(), fValue)
                    } catch (Exception e) {
                        // loggingManaged.error(e.message)
                    }
                }
                listData.add(data)
            }
        }
        return listData
    }

    List<Map<String, Object>> getDynamicData1(ResultSet rs) {
        List<Map<String, Object>> listData = new ArrayList<>()
        if (rs != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            int columnCnt = rs.getMetaData().getColumnCount()
            while (rs.next()) {
                Map<String, Object> data = new HashMap<>()
                for (int i = 1; i <= columnCnt; ++i) {
                    String fName = rs.getMetaData().getColumnName(i)
                    try {
                        Object fValue
                        if (rs.getMetaData().getColumnType(i) == Types.DATE) {
                            fValue = rs.getDate(fName) ? sdf.format(new Date(rs.getDate(fName).getTime())) : null
                        } else if (rs.getMetaData().getColumnType(i) == Types.TIMESTAMP) {
                            fValue = rs.getDate(fName) ? sdf.format(new Date(rs.getTimestamp(fName).getTime())) : null
                        } else if (rs.getMetaData().getColumnType(i) == Types.CLOB) {
                            java.sql.Clob clob = rs.getClob(i)
                            fValue = clob.getSubString(1, (int) clob.length())
                        } else if (rs.getMetaData().getColumnType(i) == Types.NCLOB) {
                            java.sql.NClob clob = rs.getNClob(i)
                            fValue = clob.getSubString(1, (int) clob.length())
                        } else if (rs.getMetaData().getColumnType(i) == Types.BLOB) {
                            java.sql.Blob clob = rs.getBlob(i)
                            fValue = Base64.getEncoder().encodeToString(clob.getBytes(1, (int) clob.length()))
                        } else {
                            fValue = rs.getObject(fName)
                        }
                        data.put(fName.toLowerCase(), fValue)
                    } catch (Exception e) {

                    }
                }
                listData.add(data)
            }
        }
        return listData
    }

    Map<String, Object> getObjectDynamicData(ResultSet rs) {
        Map<String, Object> data = new HashMap<>()
        if (rs != null) {
            int columnCnt = rs.getMetaData().getColumnCount()
            for (int i = 1; i <= columnCnt; ++i) {
                String fName = rs.getMetaData().getColumnName(i)
                try {
                    Object fValue
                    if (rs.getMetaData().getColumnType(i) == Types.DATE) {
                        fValue = rs.getDate(fName) ? new Date(rs.getDate(fName).getTime()) : null
                    } else if (rs.getMetaData().getColumnType(i) == Types.CLOB) {
                        java.sql.Clob clob = rs.getClob(i)
                        fValue = clob.getSubString(1, (int) clob.length())
                    } else if (rs.getMetaData().getColumnType(i) == Types.BLOB) {
                        java.sql.Blob clob = rs.getBlob(i)
                        fValue = Base64.getEncoder().encodeToString(clob.getBytes(1, (int) clob.length()))
                    } else {
                        fValue = rs.getObject(fName)
                    }
                    data.put(fName.toLowerCase(), fValue)
                } catch (Exception e) {

                }
            }
        }
        return data
    }


    public List<Map<String, Object>> resultAsListMap(ResultSet rs) {

        int columns = rs.getMetaData().getColumnCount()
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>()
        // https://stackoverflow.com/questions/18383448/is-there-a-way-to-intelligently-set-fetch-size-on-jdbctemplate
        while (rs.next()) {
            HashMap<String, Object> row = new HashMap<String, Object>()
            HashMap<String, Object> rowdetail = new HashMap<String, Object>()
            for (int i = 1; i <= columns; ++i) {
                //Rowdetail.put(rs.getMetaData().getcolumType(i), rs.getObject(i))
                //row.put (rs.getMetaData().getColumnName(i), rowdetail)
                row.put(rs.getMetaData().getColumnType(i), rs.getObject(i))
            }

            list.add(row)
        }
        return list
    }

    public List<Map<String, Object>> resultAsListMapNew(ResultSet rs) {

        int columns = rs.getMetaData().getColumnCount()
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>()
        // https://stackoverflow.com/questions/18383448/is-there-a-way-to-intelligently-set-fetch-size-on-jdbctemplate
        while (rs.next()) {
            HashMap<String, Object> row = new HashMap<String, Object>()
            HashMap<String, Object> rowdetail = new HashMap<String, Object>()
            for (int i = 1; i <= columns; ++i) {
                //Rowdetail.put(rs.getMetaData().getcolumType(i), rs.getObject(i))
                row.put(rs.getMetaData().getColumnName(i), rs.getObject(i))
                //row.put(rs.getMetaData().getColumnType(i), rs.getObject(i))
            }

            list.add(row)
        }
        return list
    }

    List<Map<String, Object>> getDynamicDataNew(List<Map<String, Object>> buffer, Map<String, Integer> objectFieldMatch) {
        List<Map<String, Object>> listData = new ArrayList<>()
        if (buffer != null) {
            int columnCnt = buffer.size()
            for (int j = 0; j < columnCnt; j++) {
                Map<String, Object> recordrow = buffer.get(j)
                Map<String, Object> data = new HashMap<>()
                for (Map.Entry<String, Object> m : recordrow.entrySet()) {
                    if (objectFieldMatch.containsKey(m.getKey().toLowerCase())) {
                        Object fValue
                        try {
                            switch (objectFieldMatch.get(m.getKey().toLowerCase())) {
                                case Types.DATE:
                                    java.sql.Timestamp sqlDate = (java.sql.Timestamp) m.getValue()
                                    fValue = sqlDate ? new Date(sqlDate.getTime()) : null
                                    break
                                case Types.CLOB:
                                    java.sql.Clob clob = (java.sql.Clob) m.getValue()
                                    fValue = clob.getSubString(1, (int) clob.length())
                                    break
                                case Types.NCLOB:
                                    java.sql.NClob clob = (java.sql.NClob) m.getValue()
                                    fValue = clob.getSubString(1, (int) clob.length())
                                    break
                                case Types.BLOB:
                                    java.sql.Blob clob = (java.sql.Blob) m.getValue();
                                    fValue = Base64.getEncoder().encodeToString(clob.getBytes(1, (int) clob.length()))
                                    break
                                default:
                                    fValue = m.getValue()
                            }
                        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
                        | SecurityException e) {
                            e.printStackTrace()
                        }
                        data.put(m.getKey().toLowerCase(), fValue)
                    } //endif

                }
                listData.add(data)
            }
            return listData
        }
    }

    public static <T> Map<String, Integer> GetObjectBothFieldTypeMatch(ResultSet rs, Class<T> type) {

        int columnCnt = rs.getMetaData().getColumnCount()
        Map<String, Integer> objectFieldMatch = new HashMap<String, Integer>()
        for (int i = 1; i <= columnCnt; ++i) {
            Field f = type.declaredFields.find { it.name.equalsIgnoreCase(rs.getMetaData().getColumnName(i)) }
            if (f) {
                if (f.type == Date.class)
                    objectFieldMatch.put(f.name, 1000) // Khi output cua Class kieu Date thi se du lieu la null, fai update ve kieu String
                else if (f.type == int.class || f.type == long.class)
                    objectFieldMatch.put(f.name, 1001) // Khi output cua Class kieu int va long thi se du lieu la null, fai update ve kieu String
                else
                    objectFieldMatch.put(f.name, rs.getMetaData().getColumnType(i))
            }

        }
        return objectFieldMatch
    }

    public static <T> Map<String, Integer> GetobjectBothFieldTypeMatchDanhmuc(ResultSet rs, Class<T> type) {
        int columnCnt = 0
        Map<String, Integer> objectFieldMatch = new HashMap<String, Integer>()

        try {
            columnCnt = rs.getMetaData().getColumnCount()
            for (int i = 1; i <= columnCnt; ++i) {

                objectFieldMatch.put(rs.getMetaData().getColumnName(i), rs.getMetaData().getColumnType(i));
            }

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace()
        }

        /*
         * for (Field fi : type.getDeclaredFields()) { if
         * (fi.name.equalsIgnoreCase(rs.getMetaData().getColumnName(i))) { f = fi } }
         */

        return objectFieldMatch
    }

    public Map<String, Integer> GetobjectBothFieldTypeMatchDynamic(ResultSet rs) {
        int columnCnt = 0
        Map<String, Integer> objectFieldMatch = new HashMap<String, Integer>()

        try {
            columnCnt = rs.getMetaData().getColumnCount()
            for (int i = 1; i <= columnCnt; ++i) {

                objectFieldMatch.put(rs.getMetaData().getColumnName(i), rs.getMetaData().getColumnType(i));
            }

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace()
        }

        /*
         * for (Field fi : type.getDeclaredFields()) { if
         * (fi.name.equalsIgnoreCase(rs.getMetaData().getColumnName(i))) { f = fi } }
         */

        return objectFieldMatch
    }

    public static <T> List<T> getDanhMuc_NewSimpleloop(List<Map<String, Object>> buffer, Map<String, Integer> objectFieldMatch, Class<T> type) {
        List<T> ret = []
        if (buffer != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            int columnCnt = buffer.size()
            for (int j = 0; j < columnCnt; j++) {
                Map<String, Object> recordrow = buffer.get(j)
                T obj = type.getConstructor().newInstance()
                for (Map.Entry<String, Object> m : recordrow) {
                    for( Map.Entry<String,Integer> x:objectFieldMatch) {
                        if (x.getKey().equalsIgnoreCase(m.getKey())) {
                            if (m.getValue() != null) {
                                try {
                                    switch (x.value) {
                                        case Types.DATE:
                                            java.sql.Timestamp sqlDate = (java.sql.Timestamp) m.getValue()
                                            obj[x.getKey()] = sdf.format(new java.util.Date(sqlDate.getTime()))
                                            break
                                        case Types.TIMESTAMP:
                                            if (m.getValue() instanceof oracle.sql.TIMESTAMP) {
                                                oracle.sql.TIMESTAMP ts1 = (oracle.sql.TIMESTAMP) m.getValue()
                                                java.sql.Timestamp ts2 = ts1.timestampValue()
                                                obj[x.getKey()] = sdf.format(new java.util.Date(ts2.getTime()))
                                            } else {
                                                java.sql.Timestamp sqlDate = (java.sql.Timestamp) m.getValue()
                                                obj[x.getKey()] = sdf.format(new java.util.Date(sqlDate.getTime()))
                                            }
                                            break
                                        case Types.CLOB:
                                            java.sql.Clob clob = (Clob) m.getValue()
                                            obj[x.getKey()] = clob.getSubString(1, (int) clob.length())
                                            break
                                        case 1000: // Khi output cua Class kieu Date thi se du lieu la null, fai update ve kieu String
                                            obj[x.getKey()] = null
                                            break
                                        default:
//                                            println("m.getValue(): " + m.getValue())
//                                            println("m.getKey: " + x.getKey())
//                                            println("x.value: " + x.value)
                                            try{
                                                obj[x.getKey()] =  m.getValue()
                                            }
                                            catch (Exception ex)
                                            {
                                                if (obj.getClass().getDeclaredField(x.getKey()).getType() == Long.class){
                                                    obj[x.getKey()] = Long.parseLong( m.getValue())
                                                }
                                                else if (obj.getClass().getDeclaredField(x.getKey()).getType() == Integer.class){
                                                    obj[x.getKey()] = Integer.parseInt( m.getValue())
                                                }
                                            }
                                    }
                                } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
                                | SecurityException | SQLException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace()
                                }
                            } else {
                                if(x.value == 1001)
                                    obj[x.getKey()] = 0
                                else
                                    obj[x.getKey()] = m.getValue()
                            }
                         }
                    }
                }
                ret.add(obj)
            }

            ret
        }
    }

    List<Map<String, Object>> getDynamicDataNewfix(List<Map<String, Object>> buffer, Map<String, Integer> objectFieldMatch) {
        List<Map<String, Object>> listData = new ArrayList<>()
        if (buffer != null) {
            int columnCnt = buffer.size()
            for (int j = 0; j < columnCnt; j++) {
                Map<String, Object> recordrow = buffer.get(j)
                Map<String, Object> data = new HashMap<>()
                for (Map.Entry<String, Object> m : recordrow.entrySet()) {
                    if (objectFieldMatch.containsKey(m.getKey())) {
                        if(m.getKey() != null) {
                            Object fValue
                            try {
                                switch (objectFieldMatch.get(m.getKey())) {
                                    case Types.DATE:
                                        java.sql.Timestamp sqlDate = (java.sql.Timestamp) m.getValue()
                                        fValue = sqlDate ? new Date(sqlDate.getTime()) : null
                                        break
                                    case Types.CLOB:
                                        //java.sql.Clob clob = (java.sql.Clob) m.getValue()
                                        Clob clob = (Clob) m.getValue()
                                        fValue = clob ? clob.getSubString(1, (int) clob.length()) : null
                                        //fValue = clob.getSubString(1, (int) clob.length())
                                        break
                                    case Types.NCLOB:
                                        java.sql.NClob clob = (java.sql.NClob) m.getValue()
                                        fValue = clob ? clob.getSubString(1, (int) clob.length()) : null
                                        //fValue = clob.getSubString(1, (int) clob.length())
                                        break
                                    case Types.BLOB:
                                        java.sql.Blob clob = (java.sql.Blob) m.getValue()
                                        fValue = clob ? Base64.getEncoder().encodeToString(clob.getBytes(1, (int) clob.length())) : null

                                        //fValue = Base64.getEncoder().encodeToString(clob.getBytes(1, (int) clob.length()))
                                        break
                                    case Types.ROWID:
                                        java.sql.RowId rowId = (java.sql.RowId) m.getValue()
                                        fValue = rowId.toString()
                                        break
                                    default:
                                        fValue = m.getValue()
                                }
                            } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
                            | SecurityException e) {
                                e.printStackTrace()
                            }
                            data.put(m.getKey().toLowerCase(), fValue)
                        } else {
                            data.put(m.getKey().toLowerCase(), m.getValue())
                        }
                    } //endif

                }
                listData.add(data)
            }
            return listData
        }
    }

}
