# Tóm tắt task: Sync user CSHT từ ONEBSS

## Yêu cầu gốc
Lead giao: tích hợp API đồng bộ dữ liệu CSHT theo mô hình mới, xây job sync số liệu từ ONEBSS về DB.
- Bảng `user_qlcsht` trên schema `qlsc`
- Hàm sync API → bảng (code ở **api_tichhop**)
- Cron job 21h hàng ngày (code ở **api_app_common**)
- Chạy từng tỉnh, hardcode 4 tỉnh: **VTU, HCM, HNI, BDG**

## RIMS API
- URL: `http://rims.vnpt.vn:8080/application/g/onebss_usrtinhtp_chst_v1`
- Method: POST
- Auth: Bearer token (token đã set sẵn trong application.yaml)
- Body: `{ "ma_tinh_tp": "HNI", "System_code": "", "page_index": 1, "page_size": 5000 }`
- Response: `{ success, data: [...], totalRecord, totalPage, errorMsg }`

## DB Schema (đã tạo)
```sql
CREATE TABLE qlsc.user_qlcsht (
    id                    NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ma_tinh_tp            VARCHAR2(20),
    ten_tinh_tp           VARCHAR2(200),
    building_code         VARCHAR2(100),
    building_name         VARCHAR2(500),
    address               VARCHAR2(1000),
    system_code           VARCHAR2(100),
    system_name           VARCHAR2(200),
    usage_purpose         VARCHAR2(200),
    scope_type            VARCHAR2(50),
    management_block_code VARCHAR2(50),
    role_code             VARCHAR2(50),
    user_donvi_id         VARCHAR2(50),
    user_donvi_path       VARCHAR2(500),
    unit_name             VARCHAR2(200),
    ten_don_vi            VARCHAR2(200),
    phuongxa_id           VARCHAR2(50),
    ten_phuong_xa         VARCHAR2(200),
    full_name             VARCHAR2(200),
    email                 VARCHAR2(200),
    phone                 VARCHAR2(50),
    note_text             CLOB,
    src_created_at        VARCHAR2(50),
    src_updated_at        VARCHAR2(50),
    src_created_by        VARCHAR2(100),
    src_updated_by        VARCHAR2(100),
    sync_at               TIMESTAMP DEFAULT SYSTIMESTAMP
);
CREATE INDEX qlsc.idx_user_qlcsht_tinh ON qlsc.user_qlcsht(ma_tinh_tp);
```

## Files đã tạo / sửa

### api_tichhop (d:/WorkSpacee/api_tichhop)
1. **Tạo mới**: `src/main/groovy/vn/vnptit/sapi/oneapp/resources/OneBssCshtService.groovy`
   - Endpoint: `POST /onebss-csht/sync`
   - Body: `{ "ma_tinh_tp": "...", "system_code": "" }`
   - Logic: paginate RIMS → batch insert qlsc.user_qlcsht → soft-swap DELETE cũ ở cuối
   - Constants: `PAGE_SIZE = 5000`, `BATCH_SIZE = 2000`
   - Datasource: `css` (qua `oracleManaged.getDataSource("css")`)

2. **Sửa**: `src/main/resources/application.yaml`
   - Thêm trong `spring.httpservices.rims:`:
     ```yaml
     onebss_csht_url: ${rims_onebss_csht_url:http://rims.vnpt.vn:8080/application/g/onebss_usrtinhtp_chst_v1}
     onebss_csht_token: ${rims_onebss_csht_token:eyJ0eXAiOi... (token đầy đủ trong file)}
     ```
   - Sửa eureka client thành env-driven: `enabled: ${EUREKA_ENABLED:false}` (và 3 dòng tương tự)
   - **⚠️ ĐÃ SỬA tạm credentials css để test local — cần REVERT trước khi commit**:
     - Hiện tại: `url: jdbc:oracle:thin:@10.159.28.219:1521/PDB1`, `username: CSS`, `password: ERPBss#2022`
     - Default cũ: `url: jdbc:oracle:thin:@10.159.28.215:1521/PDB_BSSDEV`, `username: css`, `password: Bss#2021`

3. **Sửa**: `src/main/groovy/vn/vnptit/sapi/oneapp/util/OnOffAdvice.groovy`
   - Thêm `"/onebss-csht/"` vào `lstPassServices` (whitelist không kiểm token vì cron-to-service)

4. **Sửa**: `.idea/workspace.xml` (chỉ local)
   - Thêm VM_PARAMETERS cho Run config: `-Doracle.jdbc.fanEnabled=false -Dspring.devtools.restart.enabled=false`

### api_app_common (d:/WorkSpacee/api_app_common)
1. **Tạo mới**: `src/main/java/com/vn/vnptit/Oneapp_Com/service/CshtSyncService.java`
   - Method `syncAllProvinces()`: dùng `Arrays.stream(MA_TINH_LIST).parallel().forEach(...)` chạy 4 tỉnh đồng thời
   - URL gọi: `http://tichhop/onebss-csht/sync` (qua Eureka discovery)
   - Hardcode `MA_TINH_LIST = {"VTU","HCM","HNI","BDG"}`

2. **Sửa**: `src/main/java/com/vn/vnptit/Oneapp_Com/managed/ScheduledTasks.java`
   - Thêm method `syncCshtRunAt9PM()` với `@Scheduled(cron = "0 0 21 * * ?")`

## Logic sync (snapshot swap)
```
1. syncStartedAt = NOW
2. Loop pages: INSERT mọi record với sync_at = syncStartedAt
3. Cuối cùng: DELETE WHERE ma_tinh_tp=? AND sync_at < syncStartedAt
```
→ Idempotent, tự handle delete record source. Soft-swap: dữ liệu cũ chỉ bị xoá khi sync thành công 100%.

## Đã decline (không làm)
- **MERGE/UPSERT logic** — phức tạp, không cần thiết với usecase chỉ tra cứu
- **Dedup statement** — verified không có duplicate thật, không cần
- **Staging table + atomic swap** — overkill cho size <10M
- **Cột `is_current` flag** — không cần thiết

## Issue đã thảo luận, accept không xử lý
- **Cửa sổ duplicate trong lúc sync** (data cũ + mới đồng thời ~5-10 phút). Giải pháp đề xuất nếu cần: tạo VIEW `qlsc.v_user_qlcsht` lọc theo MAX(sync_at) per tỉnh.
- **Diff 13 rows** (DB 570,839 vs RIMS totalRecord 570,826 cho HNI). Verified:
  - 0 duplicate khi GROUP BY all fields → mọi row unique
  - Tất cả từ 1 sync_at → không phải leftover
  - Kết luận: RIMS pagination quirk, 0.002%, accept
- **ID growing** mỗi đêm — Oracle NUMBER chứa được, không tràn, không có FK trỏ vào → kệ

## Testing
- ✅ Test local thành công với HCM (278k), HNI (570k), HCM data integrity verified
- ❌ Chưa test cron job thực tế ở DEV
- DB local đang dùng: `10.159.28.219:1521/PDB1`, user `CSS`/`ERPBss#2022`

## Pending trước khi commit/merge
1. **Revert credentials trong `application.yaml`** (xem section Files đã sửa #2)
2. **Quyết định Eureka default** — prod cần `EUREKA_ENABLED=true`
3. **Deploy DEV và để cron 21h chạy 1 đêm**, sáng verify
4. **Test cron trigger thủ công** (option): có thể thêm 1 endpoint trong api_app_common để gọi `cshtSyncService.syncAllProvinces()` thủ công cho test, không cần đợi 21h

## Performance
| Tỉnh | Records | Pages (5000) | Time est. |
|------|---------|-------------|-----------|
| HNI  | 570k    | 114         | ~7-10 phút |
| HCM  | 278k    | 56          | ~4-5 phút |
| BDG  | 278k    | 56          | ~4-5 phút |
| VTU  | ~?      | ~?          | vài phút |

Cron parallel → tổng = max ≈ 7-10 phút mỗi đêm.

## Branch
`feat/dongbo-user`
