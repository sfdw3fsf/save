package vn.vnpt.vnptit.ecms.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.springframework.stereotype.Component;

@Component
public class CssBrasMapper implements RowMapper<CssBrasModel> {

	@Override
	public CssBrasModel map(ResultSet rs, StatementContext ctx) throws SQLException {
		CssBrasModel item = new CssBrasModel();
		item.setPhanvung_id(rs.getInt("PHANVUNG_ID"));
		item.setBras_id(rs.getInt("BRAS_ID"));
		item.setTenbras(rs.getString("TENBRAS"));
		item.setCaidat_md(rs.getInt("CAIDAT_MD"));
		item.setIp(rs.getString("IP"));
		item.setBras_vdc_id(rs.getInt("BRAS_VDC_ID"));
		item.setSo_slot(rs.getString("SO_SLOT"));
		item.setSo_subslot(rs.getString("SO_SUBSLOT"));
		item.setUsed(rs.getInt("USED"));
		item.setSlot_bd(rs.getInt("SLOT_BD"));
		item.setSubslot_bd(rs.getInt("SUBSLOT_BD"));
		item.setTurack_id(rs.getInt("TURACK_ID"));
		item.setSlot_visa(rs.getInt("SLOT_VISA"));
		item.setPort_visa(rs.getInt("PORT_VISA"));
		item.setSlotbd(rs.getInt("SLOTBD"));
		item.setSo_port(rs.getInt("SO_PORT"));
		item.setPortbd(rs.getInt("PORTBD"));
		item.setDonvi_id(rs.getInt("DONVI_ID"));
		return item;
	}

}
