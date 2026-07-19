package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import groovy.sql.OutParameter
import groovy.sql.Sql
import io.swagger.model.response.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import oracle.jdbc.OracleTypes
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.tika.Tika
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import vn.vnptit.sapi.oneapp.convert.ResultSetToObject
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.ObjectStoragesManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.model.request.*
import vn.vnptit.sapi.oneapp.service.VisaService
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine
import vn.vnptit.sapi.oneapp.util.MessageUtils

import javax.servlet.http.HttpServletRequest
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import java.sql.ResultSet
import java.text.SimpleDateFormat

@RestController
@RequestMapping("/data-upload")
class DataUploadController {
    private final HttpServletRequest request
    private final ObjectMapper objectMapper

    @Autowired
    LoggingManaged loggingManaged
    @Autowired
    GenericApi genericApi
    @Autowired
    OracleManaged oracleManaged

    @Autowired
    ObjectStoragesManaged objectStoragesManaged

    @Autowired
    DataUtils dataUtils

    @Autowired
    ResultSetToObject resultSetToObject

    @Autowired
    VisaService visaService

    String bucketName = "onebss-develop"

    @Autowired
    DataUploadController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper
        this.request = request
    }

    // 1 upload multiple files
    @PostMapping("/**")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    ResponseEntity<BaseResponse<List<String>>> upload(@RequestParam("files") MultipartFile[] files) {
        try {
            if (files != null && files.size() > 0) {
                for (MultipartFile file : files) {
                    String filename = file.getOriginalFilename()
                    if (filename == null || filename.trim().length() == 0 ||
                            (!filename.trim().endsWith(".xls") && !filename.trim().endsWith(".xlsx") && !filename.trim().endsWith(".jpg")
                                    && !filename.trim().endsWith(".jpeg") && !filename.trim().endsWith(".png") && !filename.trim().endsWith(".txt"))) {
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, "", "Tham số không hợp lệ", ErrorCodeDefine.INVALID_PARAM));
                    }
                }
            }

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maTinh = session_.get(GenericApi.MA_TINH) ?: "VNP"
            String maNV = session_.get(GenericApi.MA_NV) ?: "UNKNOWN"

            Map<String, String> tags = [:]
            request.getParameterNames().each {
                tags.put(it, request.getParameter(it))
            }
            def urlList = []
            String folder = "DATA_UPLOAD/$maTinh/$maNV/${new SimpleDateFormat('yyyyMMdd_hhmmss').format(new Date())}/".toString()

            files.each { MultipartFile it ->
                String filename = it.originalFilename
                //index_ = index_ + 1;
                boolean uploadS3Ok = objectStoragesManaged.uploadObject(bucketName, folder + filename, it.bytes, tags)
                if (uploadS3Ok) {
                    String uploadId = UUID.randomUUID().toString()

                    Tika tika = new Tika()
                    String type = tika.detect(new ByteArrayInputStream(it.bytes))

                    Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
                        if (type == 'text/plain') {
                            int rowCount = 0

                            sql.withBatch(50, "insert into common.data_upload(id, data) values (?,?)") { stmt ->
                                iteratorText(it.bytes) { JSONObject joRow ->
                                    stmt.addBatch(uploadId, joRow.toString())
                                    rowCount++
                                }
                            }

                            if (rowCount) {
                                sql.execute("""insert into common.data_upload_info(CREATED_AT, ID,
AGENT,
USERNAME,
TOKEN_DATA,
OBJSTG_FILENAME) values(sysdate, ?,?,?,?,?)""", [uploadId, maTinh, maNV, tags.toString(), folder + filename])

                                urlList.add(uploadId)
                            }

                        } else if (type.contains("ooxml") || type.contains("msoffice")) {
                            //application/x-tika-ooxml
                            int rowCount = 0

                            sql.withBatch(50, "insert into common.data_upload(id, data) values (?,?)") { stmt ->
                                interatorExcel(it.bytes, type.contains("ooxml") ? "2007" : "2003") { JSONObject joRow ->
                                    stmt.addBatch(uploadId, joRow.toString())
                                    rowCount++
                                }
                            }

                            if (rowCount) {
                                sql.execute("""insert into common.data_upload_info(CREATED_AT, ID,
AGENT,
USERNAME,
TOKEN_DATA,
OBJSTG_FILENAME) values(sysdate, ?,?,?,?,?)""", [uploadId, maTinh, maNV, tags.toString(), folder + filename])

                                urlList.add(uploadId)
                            }
                        }
                    }
                }
            }

            return ResponseEntity.ok().body(BaseResponseBuilder.of(urlList, null, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))

        } catch (Exception ex) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, ex.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    static void iteratorText(byte[] fileByte, Closure clz) {
        InputStreamReader ins = new InputStreamReader(new ByteArrayInputStream(fileByte))
        CSVParser records = CSVFormat.DEFAULT.builder()
                .setIgnoreSurroundingSpaces(true)
                .build()
                .parse(ins)
        int rowIdx = 0
        List<String> colNames = []
        for (CSVRecord record : records) {
            if (rowIdx == 0)
                for (int col = 0; col < record.size(); col++) {
                    colNames.add(record.get(col))
                }
            else {
                JSONObject jo = new JSONObject()
                for (int col = 0; col < record.size(); col++) {
                    jo.put(colNames[col], record.get(col))
                }
                clz(jo)
            }
            rowIdx++
        }
        ins.close()
    }

    static void interatorExcel(byte[] fileByte, String version, Closure clz) {
        InputStream ins = new ByteArrayInputStream(fileByte)
        Workbook workbook = version == "2007" ? new XSSFWorkbook(ins) : new HSSFWorkbook(ins)
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();

        int rowIdx = 0
        List<String> colNames = []
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            if (rowIdx == 0) {
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    colNames.add(cell.getStringCellValue())
                }
            } else {
                JSONObject jo = new JSONObject()
                int col = 0
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    def ct = cell.getCellType()

                    if (ct == CellType.STRING) {
                        jo.put(colNames[col], cell.getStringCellValue())
                    } else if (ct == CellType.NUMERIC) {
                        jo.put(colNames[col], cell.getNumericCellValue())
                    } else if (ct == CellType.BOOLEAN) {
                        jo.put(colNames[col], cell.getBooleanCellValue())
                    }

                    col++
                }
                clz(jo)
            }
            rowIdx++
        }

        workbook.close();
        ins.close();
    }

}
