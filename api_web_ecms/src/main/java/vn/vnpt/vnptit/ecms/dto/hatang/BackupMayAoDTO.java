package vn.vnpt.vnptit.ecms.dto.hatang;

import java.io.Serializable;

public class BackupMayAoDTO implements Serializable {
    private Number backupId;
    private String mayAoIds;

    public BackupMayAoDTO(Number backupId, String mayAoIds) {
        this.backupId = backupId;
        this.mayAoIds = mayAoIds;
    }

    public BackupMayAoDTO() {
    }

    public Number getBackupId() {
        return backupId;
    }

    public void setBackupId(Number backupId) {
        this.backupId = backupId;
    }

    public String getMayAoIds() {
        return mayAoIds;
    }

    public void setMayAoIds(String mayAoIds) {
        this.mayAoIds = mayAoIds;
    }

}
