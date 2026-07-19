package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.stereotype.Service;
import vn.vnpt.database.DbContext;
import vn.vnpt.vnptit.ecms.dto.idc.ThiCongThietBiDto;

@Service
public class ThiCongThietBiService {
    private final DbContext dbContext;

    public ThiCongThietBiService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getThongTinPhieu(ThiCongThietBiDto dto) {
        return 1;
    }

    public Object luuPhieu(ThiCongThietBiDto dto) {
        return 1;
    }
}
