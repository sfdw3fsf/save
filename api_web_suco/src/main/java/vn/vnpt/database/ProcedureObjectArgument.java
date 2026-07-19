package vn.vnpt.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.Types;

import org.jdbi.v3.core.argument.Argument;
import org.jdbi.v3.core.statement.StatementContext;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

public class ProcedureObjectArgument implements Argument {

    private Object value;
    private String oracleTypeName;

    public ProcedureObjectArgument(Object value, String oracleTypeName) {
        this.value = value;
        this.oracleTypeName = oracleTypeName;
    }

    @Override
    public void apply(int position, PreparedStatement statement, StatementContext ctx) throws SQLException {

        if (value instanceof SQLData) {
            statement.setObject(position, value, OracleTypes.STRUCT);
            return;
        }

        Connection conn = ctx.getConnection();

        try {
            conn = conn.unwrap(OracleConnection.class);
        } catch (Exception exception) {
            //exception.printStackTrace();
        }

        ArrayDescriptor arrayDescriptor = new ArrayDescriptor(oracleTypeName, conn);
        // "value" is an instance of Object[] and each element in array is an instance of SQLData
        ARRAY array = new ARRAY(arrayDescriptor, conn, value);
        statement.setObject(position, array, Types.ARRAY);
    }
}
