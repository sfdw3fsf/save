package vn.vnpt.vnptit.ecms.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class HopDongKhoaMoDTO implements Serializable {

    @NotNull
    private Long thueBaoId;

    private String lyDoKhoaMo;

    public Long getThueBaoId() {
        return thueBaoId;
    }

    public void setThueBaoId(Long thueBaoId) {
        this.thueBaoId = thueBaoId;
    }

    public String getLyDoKhoaMo() {
        return lyDoKhoaMo;
    }

    public void setLyDoKhoaMo(String lyDoKhoaMo) {
        this.lyDoKhoaMo = lyDoKhoaMo;
    }
    
}
