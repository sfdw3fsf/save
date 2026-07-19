package vn.vnpt.vnptit.ecms.dto.sync.vcenter;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class VcterVMwareInfoDTO {
    private VmwareInfoValueDTO value;

    public static class VmwareInfoValueDTO {
        private Boolean instant_clone_frozen;
        private List<CdRomEntryDTO> cdroms;
        private MemoryDTO memory;
        private List<DiskEntryDTO> disks;
        private List<Object> parallel_ports;
        private List<Object> sata_adapters;
        private CpuDTO cpu;
        private List<ScsiAdapterEntryDTO> scsi_adapters;
        private String power_state;
        private List<Object> floppies;
        private IdentityDTO identity;
        private List<Object> nvme_adapters;
        private String name;
        private List<NicEntryDTO> nics;
        private BootDTO boot;
        private List<Object> serial_ports;
        private List<Object> boot_devices;
        @JsonProperty("guest_OS")
        private String guest_OS;
        private HardwareDTO hardware;

        public Boolean getInstant_clone_frozen() {
            return instant_clone_frozen;
        }

        public void setInstant_clone_frozen(Boolean instant_clone_frozen) {
            this.instant_clone_frozen = instant_clone_frozen;
        }

        public List<CdRomEntryDTO> getCdroms() {
            return cdroms;
        }

        public void setCdroms(List<CdRomEntryDTO> cdroms) {
            this.cdroms = cdroms;
        }

        public MemoryDTO getMemory() {
            return memory;
        }

        public void setMemory(MemoryDTO memory) {
            this.memory = memory;
        }

        public List<DiskEntryDTO> getDisks() {
            return disks;
        }

        public void setDisks(List<DiskEntryDTO> disks) {
            this.disks = disks;
        }

        public List<Object> getParallel_ports() {
            return parallel_ports;
        }

        public void setParallel_ports(List<Object> parallel_ports) {
            this.parallel_ports = parallel_ports;
        }

        public List<Object> getSata_adapters() {
            return sata_adapters;
        }

        public void setSata_adapters(List<Object> sata_adapters) {
            this.sata_adapters = sata_adapters;
        }

        public CpuDTO getCpu() {
            return cpu;
        }

        public void setCpu(CpuDTO cpu) {
            this.cpu = cpu;
        }

        public List<ScsiAdapterEntryDTO> getScsi_adapters() {
            return scsi_adapters;
        }

        public void setScsi_adapters(List<ScsiAdapterEntryDTO> scsi_adapters) {
            this.scsi_adapters = scsi_adapters;
        }

        public String getPower_state() {
            return power_state;
        }

        public void setPower_state(String power_state) {
            this.power_state = power_state;
        }

        public List<Object> getFloppies() {
            return floppies;
        }

        public void setFloppies(List<Object> floppies) {
            this.floppies = floppies;
        }

        public IdentityDTO getIdentity() {
            return identity;
        }

        public void setIdentity(IdentityDTO identity) {
            this.identity = identity;
        }

        public List<Object> getNvme_adapters() {
            return nvme_adapters;
        }

        public void setNvme_adapters(List<Object> nvme_adapters) {
            this.nvme_adapters = nvme_adapters;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<NicEntryDTO> getNics() {
            return nics;
        }

        public void setNics(List<NicEntryDTO> nics) {
            this.nics = nics;
        }

        public BootDTO getBoot() {
            return boot;
        }

        public void setBoot(BootDTO boot) {
            this.boot = boot;
        }

        public List<Object> getSerial_ports() {
            return serial_ports;
        }

        public void setSerial_ports(List<Object> serial_ports) {
            this.serial_ports = serial_ports;
        }

        public List<Object> getBoot_devices() {
            return boot_devices;
        }

        public void setBoot_devices(List<Object> boot_devices) {
            this.boot_devices = boot_devices;
        }

        public String getGuest_OS() {
            return guest_OS;
        }

        public void setGuest_OS(String guest_OS) {
            this.guest_OS = guest_OS;
        }

        public HardwareDTO getHardware() {
            return hardware;
        }

        public void setHardware(HardwareDTO hardware) {
            this.hardware = hardware;
        }
    }

    public static class CdRomEntryDTO {
        private String key;
        private CdRomValueDTO value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public CdRomValueDTO getValue() {
            return value;
        }

        public void setValue(CdRomValueDTO value) {
            this.value = value;
        }
    }

    public static class CdRomValueDTO {
        private Boolean start_connected;
        private CdRomBackingDTO backing;
        private Boolean allow_guest_control;
        private IdeDTO ide;
        private String label;
        private String state;
        private String type;

        public Boolean getStart_connected() {
            return start_connected;
        }

        public void setStart_connected(Boolean start_connected) {
            this.start_connected = start_connected;
        }

        public CdRomBackingDTO getBacking() {
            return backing;
        }

        public void setBacking(CdRomBackingDTO backing) {
            this.backing = backing;
        }

        public Boolean getAllow_guest_control() {
            return allow_guest_control;
        }

        public void setAllow_guest_control(Boolean allow_guest_control) {
            this.allow_guest_control = allow_guest_control;
        }

        public IdeDTO getIde() {
            return ide;
        }

        public void setIde(IdeDTO ide) {
            this.ide = ide;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class CdRomBackingDTO {
        private Boolean auto_detect;
        private String device_access_type;
        private String host_device;
        private String type;

        public Boolean getAuto_detect() {
            return auto_detect;
        }

        public void setAuto_detect(Boolean auto_detect) {
            this.auto_detect = auto_detect;
        }

        public String getDevice_access_type() {
            return device_access_type;
        }

        public void setDevice_access_type(String device_access_type) {
            this.device_access_type = device_access_type;
        }

        public String getHost_device() {
            return host_device;
        }

        public void setHost_device(String host_device) {
            this.host_device = host_device;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class IdeDTO {
        private Boolean master;
        private Boolean primary;

        public Boolean getMaster() {
            return master;
        }

        public void setMaster(Boolean master) {
            this.master = master;
        }

        public Boolean getPrimary() {
            return primary;
        }

        public void setPrimary(Boolean primary) {
            this.primary = primary;
        }
    }

    public static class MemoryDTO {
        @JsonProperty("hot_add_increment_size_MiB")
        private Long hotAddIncrementSizeMiB;

        @JsonProperty("size_MiB")
        private Long sizeMiB;

        private Boolean hot_add_enabled;

        @JsonProperty("hot_add_limit_MiB")
        private Long hotAddLimitMiB;

        public Long getHotAddIncrementSizeMiB() {
            return hotAddIncrementSizeMiB;
        }

        public void setHotAddIncrementSizeMiB(Long hotAddIncrementSizeMiB) {
            this.hotAddIncrementSizeMiB = hotAddIncrementSizeMiB;
        }

        public Long getSizeMiB() {
            return sizeMiB;
        }

        public void setSizeMiB(Long sizeMiB) {
            this.sizeMiB = sizeMiB;
        }

        public Boolean getHot_add_enabled() {
            return hot_add_enabled;
        }

        public void setHot_add_enabled(Boolean hot_add_enabled) {
            this.hot_add_enabled = hot_add_enabled;
        }

        public Long getHotAddLimitMiB() {
            return hotAddLimitMiB;
        }

        public void setHotAddLimitMiB(Long hotAddLimitMiB) {
            this.hotAddLimitMiB = hotAddLimitMiB;
        }
    }

    public static class DiskEntryDTO {
        private String key;
        private VCterDiskInfoDTO.DiskInfoValueDTO value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public VCterDiskInfoDTO.DiskInfoValueDTO getValue() {
            return value;
        }

        public void setValue(VCterDiskInfoDTO.DiskInfoValueDTO value) {
            this.value = value;
        }
    }

    public static class CpuDTO {
        private Boolean hot_remove_enabled;
        private Integer count;
        private Boolean hot_add_enabled;
        private Integer cores_per_socket;

        public Boolean getHot_remove_enabled() {
            return hot_remove_enabled;
        }

        public void setHot_remove_enabled(Boolean hot_remove_enabled) {
            this.hot_remove_enabled = hot_remove_enabled;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Boolean getHot_add_enabled() {
            return hot_add_enabled;
        }

        public void setHot_add_enabled(Boolean hot_add_enabled) {
            this.hot_add_enabled = hot_add_enabled;
        }

        public Integer getCores_per_socket() {
            return cores_per_socket;
        }

        public void setCores_per_socket(Integer cores_per_socket) {
            this.cores_per_socket = cores_per_socket;
        }
    }

    public static class ScsiAdapterEntryDTO {
        private String key;
        private ScsiAdapterValueDTO value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public ScsiAdapterValueDTO getValue() {
            return value;
        }

        public void setValue(ScsiAdapterValueDTO value) {
            this.value = value;
        }
    }

    public static class ScsiAdapterValueDTO {
        private Integer pci_slot_number;
        private VCterDiskInfoDTO.ScsiDTO scsi;
        private String label;
        private String sharing;
        private String type;

        public Integer getPci_slot_number() {
            return pci_slot_number;
        }

        public void setPci_slot_number(Integer pci_slot_number) {
            this.pci_slot_number = pci_slot_number;
        }

        public VCterDiskInfoDTO.ScsiDTO getScsi() {
            return scsi;
        }

        public void setScsi(VCterDiskInfoDTO.ScsiDTO scsi) {
            this.scsi = scsi;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getSharing() {
            return sharing;
        }

        public void setSharing(String sharing) {
            this.sharing = sharing;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class IdentityDTO {
        private String name;
        private String instance_uuid;
        private String bios_uuid;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getInstance_uuid() {
            return instance_uuid;
        }

        public void setInstance_uuid(String instance_uuid) {
            this.instance_uuid = instance_uuid;
        }

        public String getBios_uuid() {
            return bios_uuid;
        }

        public void setBios_uuid(String bios_uuid) {
            this.bios_uuid = bios_uuid;
        }
    }

    public static class NicEntryDTO {
        private String key;
        private NicValueDTO value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public NicValueDTO getValue() {
            return value;
        }

        public void setValue(NicValueDTO value) {
            this.value = value;
        }
    }

    public static class NicValueDTO {
        private Boolean start_connected;
        private NicBackingDTO backing;
        private String mac_address;
        private String mac_type;
        private Boolean allow_guest_control;
        private Boolean wake_on_lan_enabled;
        private String label;
        private String state;
        private String type;
        private Boolean upt_compatibility_enabled;

        public Boolean getStart_connected() {
            return start_connected;
        }

        public void setStart_connected(Boolean start_connected) {
            this.start_connected = start_connected;
        }

        public NicBackingDTO getBacking() {
            return backing;
        }

        public void setBacking(NicBackingDTO backing) {
            this.backing = backing;
        }

        public String getMac_address() {
            return mac_address;
        }

        public void setMac_address(String mac_address) {
            this.mac_address = mac_address;
        }

        public String getMac_type() {
            return mac_type;
        }

        public void setMac_type(String mac_type) {
            this.mac_type = mac_type;
        }

        public Boolean getAllow_guest_control() {
            return allow_guest_control;
        }

        public void setAllow_guest_control(Boolean allow_guest_control) {
            this.allow_guest_control = allow_guest_control;
        }

        public Boolean getWake_on_lan_enabled() {
            return wake_on_lan_enabled;
        }

        public void setWake_on_lan_enabled(Boolean wake_on_lan_enabled) {
            this.wake_on_lan_enabled = wake_on_lan_enabled;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Boolean getUpt_compatibility_enabled() {
            return upt_compatibility_enabled;
        }

        public void setUpt_compatibility_enabled(Boolean upt_compatibility_enabled) {
            this.upt_compatibility_enabled = upt_compatibility_enabled;
        }
    }

    public static class NicBackingDTO {
        private String network_name;
        private String type;
        private String network;

        public String getNetwork_name() {
            return network_name;
        }

        public void setNetwork_name(String network_name) {
            this.network_name = network_name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNetwork() {
            return network;
        }

        public void setNetwork(String network) {
            this.network = network;
        }
    }

    public static class BootDTO {
        private Integer delay;
        private Integer retry_delay;
        private Boolean enter_setup_mode;
        private String type;
        private Boolean retry;

        public Integer getDelay() {
            return delay;
        }

        public void setDelay(Integer delay) {
            this.delay = delay;
        }

        public Integer getRetry_delay() {
            return retry_delay;
        }

        public void setRetry_delay(Integer retry_delay) {
            this.retry_delay = retry_delay;
        }

        public Boolean getEnter_setup_mode() {
            return enter_setup_mode;
        }

        public void setEnter_setup_mode(Boolean enter_setup_mode) {
            this.enter_setup_mode = enter_setup_mode;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Boolean getRetry() {
            return retry;
        }

        public void setRetry(Boolean retry) {
            this.retry = retry;
        }
    }

    public static class HardwareDTO {
        private String upgrade_policy;
        private String upgrade_status;
        private String version;

        public String getUpgrade_policy() {
            return upgrade_policy;
        }

        public void setUpgrade_policy(String upgrade_policy) {
            this.upgrade_policy = upgrade_policy;
        }

        public String getUpgrade_status() {
            return upgrade_status;
        }

        public void setUpgrade_status(String upgrade_status) {
            this.upgrade_status = upgrade_status;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }

    public VmwareInfoValueDTO getValue() {
        return value;
    }

    public void setValue(VmwareInfoValueDTO value) {
        this.value = value;
    }
}
