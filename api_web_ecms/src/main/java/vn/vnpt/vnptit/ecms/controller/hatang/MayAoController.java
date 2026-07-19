package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.AddDiskRequestDTO;
import vn.vnpt.vnptit.ecms.dto.hatang.MayAoDto;
import vn.vnpt.vnptit.ecms.service.hatang.MayAoService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/virtual-machine")
public class MayAoController {
    private final MayAoService service;

    public MayAoController(MayAoService service) {
        this.service = service;
    }

    @Operation(summary = "Get list virtual machine")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getListVirtualMachine() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getListVirtualMachine());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get detail by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getDetailsById(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDetailVirtualMachineById(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Create a new virtual machine")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> createVirtualMachine(@Valid @RequestBody MayAoDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.createVirtualMachine(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Update virtual machine")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> updateVirtualMachineById(@PathVariable(value = "id") int id, @Valid @RequestBody MayAoDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.updateVirtualMachineById(id, dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Delete virtual machine")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> deleteVirtualMachineById(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.deleteVirtualMachineById(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get list allocation history virtual machine")
    @RequestMapping(value = "/allocation-history/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getListAllocationHistoryVirtualMachine(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getListAllocationHistoryVirtualMachine(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get list disk VM virtual machine")
    @RequestMapping(value = "/disk-vm/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getListDiskVmVirtualMachine(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getListDiskVmVirtualMachine(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get list backup virtual machine")
    @RequestMapping(value = "/backup/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getListBackupVirtualMachine(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getListBackupVirtualMachine(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get list nfs virtual machine")
    @RequestMapping(value = "/nfs-smb/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getListNFSVirtualMachine(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getListNFSVirtualMachine(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get list iops")
    @RequestMapping(value = "/iops", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getListIOPS() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getListIOPS());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get list throughtput")
    @RequestMapping(value = "/throughtput", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getListThroughtput() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getListThroughtput());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get list customers virtual machine")
    @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getListKHVirtualMachine(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getListNFSVirtualMachine(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get list network virtual machine")
    @RequestMapping(value = "/network/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getListNetworkVirtualMachine(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getListNFSVirtualMachine(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get list history virtual machine")
    @RequestMapping(value = "/history/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getListHistoryVirtualMachine(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getListHistoryVirtualMachine(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get detail by id")
    @RequestMapping(value = "/ht-ao-hoa/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getDetailHTAoHoa(@PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDetailHTAoHoa(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get list of Vms with IP")
    @GetMapping(path = "/get-vms-with-ip")
    public ResponseEntity<ApiResult> getListOfVmsWithIP() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getListOfVmsWithIP());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/{id}/nfsAdd")
    public ApiResult addNfsToVirtualMachine(
            @PathVariable("id") int vmId,
            @RequestParam("nfsId") int nfsId
    ) {
        ApiResult rs = new ApiResult();

        try {
            Object result = this.service.addNfs(vmId, nfsId);
            rs.setErrorCode("BSS-00000000");
            rs.setMessage("Thêm NFS thành công");
        } catch (Exception e) {
            rs.setErrorCode("BSS-99999999");
            rs.setMessage("Lỗi: " + e.getMessage());
        }

        return rs;
    }

    @PostMapping("/{id}/backupAdd")
    public ApiResult addBackupToVirtualMachine(
            @PathVariable("id") int vmId,
            @RequestParam("backupId") int backupId
    ) {
        ApiResult rs = new ApiResult();

        try {
            Object result = this.service.addBackup(vmId, backupId);
            rs.setErrorCode("BSS-00000000");
            rs.setMessage("Thêm NFS thành công");
        } catch (Exception e) {
            rs.setErrorCode("BSS-99999999");
            rs.setMessage("Lỗi: " + e.getMessage());
        }

        return rs;
    }

    @PostMapping("/{id}/diskAdd")
    public ApiResult addDiskToVirtualMachine(
            @PathVariable("id") int vmId,
            @RequestBody AddDiskRequestDTO request
    ) {
        ApiResult rs = new ApiResult();

        try {
            Object result = this.service.addDisk(vmId, request);
            rs.setErrorCode("BSS-00000000");
            rs.setMessage("Thêm disk thành công");
            rs.setData(result);
        } catch (Exception e) {
            rs.setErrorCode("BSS-99999999");
            rs.setMessage("Lỗi: " + e.getMessage());
        }

        return rs;
    }

    @PostMapping("/{id}/networkIp")
    public ResponseEntity<ApiResult> getNetworkIp(@PathVariable("id") int vmId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getNetworkIP(vmId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}
