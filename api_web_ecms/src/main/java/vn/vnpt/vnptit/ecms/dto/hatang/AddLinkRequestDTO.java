package vn.vnpt.vnptit.ecms.dto.hatang;

import java.io.Serializable;
import java.util.List;

public class AddLinkRequestDTO implements Serializable {
    private Long id;
    private List<Long> linkIds;

    public AddLinkRequestDTO(Long id, List<Long> linkIds) {
        this.id = id;
        this.linkIds = linkIds;
    }

    public AddLinkRequestDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getLinkIds() {
        return linkIds;
    }

    public void setLinkIds(List<Long> linkIds) {
        this.linkIds = linkIds;
    }
}
