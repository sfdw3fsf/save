package vn.vnptit.sapi.oneapp.convert

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import vn.vnptit.sapi.oneapp.managed.LoggingManaged

import java.lang.reflect.Field
import java.sql.ResultSet
import java.sql.Types
import java.text.SimpleDateFormat

@Service
class ResultSetToObject {
    @Autowired
    LoggingManaged loggingManaged
    static final String NULL = "NULL"
    public <T> List<T> getDanhSach(ResultSet rs, Class<T> type) {
        List<T> ret = []
        if (rs != null) {
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
                                obj[fName] = rs.getDate(i) ? new Date(rs.getDate(i).getTime()) : null
                            } else if (rs.getMetaData().getColumnType(i) == Types.CLOB) {
                                java.sql.Clob clob = rs.getClob(i)
                                obj[fName] = clob.getSubString(1, (int) clob.length())
                            } else {
                                obj[fName] = rs.getObject(i)
                            }
                        } catch (e) {
                            loggingManaged.error(e.message + fName, e)
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
                        } else if (rs.getMetaData().getColumnType(i) == Types.BLOB) {
                            java.sql.Blob clob = rs.getBlob(i)
                            fValue = Base64.getEncoder().encodeToString(clob.getBytes(1, (int) clob.length()))
                        } else {
                            fValue = rs.getObject(fName)
                        }
                        data.put(fName.toLowerCase(), fValue)
                    } catch (Exception e) {
                        loggingManaged.error(e.message)
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
                    loggingManaged.error(e.message)
                }
            }
        }
        return data
    }

    public <T> T getObject(ResultSet rs, Class<T> type) {
        T ret = null
        int columnCnt = rs.getMetaData().getColumnCount()
        if (rs.next()) {
            ret = type.getConstructor().newInstance()
            for (int i = 1; i <= columnCnt; ++i) {
                String fName = rs.getMetaData().getColumnName(i)
                if (ret.hasProperty(fName.toLowerCase()))
                    ret[fName.toLowerCase()] = rs.getObject(fName)
            }
        }
        ret
    }

    public <T> List<T> getListObject(ResultSet rs, Class<T> type) {
        List<T> ret = []
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
                            obj[fName] = rs.getDate(i) ? new Date(rs.getDate(i).getTime()) : null
                        } else {
                            obj[fName] = rs.getObject(i)
                        }
                    } catch (e) {
                        loggingManaged.error(e.message + fName, e)
                    }
                }
            }
            ret.add(obj)
        }

        ret
    }

}
