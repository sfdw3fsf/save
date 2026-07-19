package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Service

public class ThongTinCongThietBiService {
    private final DbContext dbContext;

    public ThongTinCongThietBiService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getAll() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("cur_returnds", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_COMMON.SP_LAY_DANHSACH_THONGTIN_DAUNOI_THIETBI", params);
    }
}
