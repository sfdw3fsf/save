<template src="./index.template.html"></template>

<script>
export default {
  props: {
    modalItemID: 0
  },
  data: function () {
    return {
      loaiIPList: [],
      phanLoaiIPList: [],
      IDCList: [],
      VRFList: [],
      ZoneList: [],
      VlanList: [],
      SubnetList: [],
      QuyHoachSubnetList: [],
      dataItems: [],
      lichSuCapPhatList: [],
      currentItem: {
        id: 0,
        ip: "",
        loaiIP: 0,
        phanLoaiIP: 0,
        idc: 0,
        vrf: 0,
        zone: 0,
        ghiChu: "",
        hieuLuc: 1,
        vlan: 0,
        subnet: 0,
        subnetQuyHoach: 0,
        haTangMang: '',
        hatangmang_id: 0,
        thietBi: '',
        thietBiMang: 0,
      },
      controls: {
        txtIP: {
          enabled: false,
          invalid: false,
        },
        checBoxHieuLuc: {
          enabled: false,
        },
        selectMucDichSuDung: {
          enabled: false,
          invalid: false,
        },
        txtGhiChu: {
          enabled: false,
        },
        selectVRF: {
          enabled: false,
          invalid: false,
        },
        selectHaTangMang: {
          enabled: false,
          invalid: false,
        },
        selectZone: {
          enabled: false,
          invalid: false,
        },
        inputThietBi: {
          enabled: false,
          invalid: false,
        },
        selectLoaiIP: {
          enabled: false,
          invalid: false,
        },
        selectSubnet: {
          enabled: false,
          invalid: false,
        },
        selectVLAN: {
          enabled: false,
          invalid: false,
        },
        selectPhanLoaiIP: {
          enabled: false,
          invalid: false,
        },
        selectIDC: {
          enabled: false,
          invalid: false,
        },
      },
      buttons: {
        btnNhapMoi: {
          enabled: true,
        },
        btnGhiLai: {
          enabled: false,
        },
        btnHuyBo: {
          enabled: false,
        },
        btnSua: {
          enabled: false,
        },
        btnXoa: {
          enabled: false,
        },
      },
    };
  },
  computed: {
    gridItems: function () {
      return this.$refs.gridItems;
    },
  },
  watch: {
    "currentItem.id": function (value) {
      this.buttons.btnSua.enabled = value > 0;
      this.buttons.btnXoa.enabled = value > 0;
    }, "currentItem.ip": function (value) {
      this.controls.txtIP.invalid = !this.validateIPFormat(value);
    },
    "currentItem.loaiIP": function (value) {
      this.controls.selectLoaiIP.invalid = value == 0;
    },
    "currentItem.idc": function (value) {
      this.onChangeIDC(value);
    },
    "currentItem.vrf": function (value) {
      this.onChangeVRF(value);
    },
    "currentItem.zone": function (value) {
      this.onChangeZone(value);
    },
    "currentItem.vlan": function (value) {
      this.onChangeVlan(value);
    },
    "currentItem.subnet": async function (value) {
      if (this.SubnetList.length > 0) {
        this.changeSubnet(value);
      } else {
        await this.getSubnetList(this.currentItem.vlan);
        if (this.SubnetList.length > 0) {
          this.changeSubnet(value);
        }
      }
    }
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      this.dataItems = await this.getItems();
      await this.getLoaiIPList();
      await this.getPhanLoaiIPList();
      await this.getIDCList();
      await this.getQuyHoachSubnet();
      if (this.modalItemID > 0) {
        this.dataItems = this.dataItems.filter(x => x.ID == this.modalItemID);
        if (this.dataItems.length > 0) {
          this.setCurrentItem(this.dataItems[0]);
          this.enableControls(false);
        }
        this.gridItems.selectRows([this.modalItemID]);
      }
    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {
    getHieuLucText: function (value) {
      return value == 1 ? "Hiệu lực" : "Không hiệu lực";
    },
    enableControls: function (args) {
      this.controls.txtIP.enabled = args;
      this.controls.checBoxHieuLuc.enabled = args;
      this.controls.txtGhiChu.enabled = args;
      this.controls.selectMucDichSuDung.enabled = args;
      this.controls.selectVRF.enabled = args;
      this.controls.selectZone.enabled = args;
      this.controls.selectLoaiIP.enabled = args;
      this.controls.selectSubnet.enabled = args;
      this.controls.selectVLAN.enabled = args;
      this.controls.selectPhanLoaiIP.enabled = args;
      this.controls.selectIDC.enabled = args;
      this.buttons.btnGhiLai.enabled = args;
      this.buttons.btnNhapMoi.enabled = !args;
      this.buttons.btnHuyBo.enabled = this.buttons.btnGhiLai.enabled;
      if (this.currentItem.id > 0)
        this.buttons.btnSua.enabled = !this.buttons.btnGhiLai.enabled;
    },
    getLichSuCapPhatIPStore(id) {
      return this.$root.context.post("/web-ecms/danhmuc-phanloai-hatang/lay-lich-su-cap-phat-theo-ipstore", {
        id: id,
      }).then(rs => {
        if (rs != null) {
          return rs.data;
        }
        return [];
      }).catch(e => {
        console.error('Lỗi khi lấy lịch sử cấp phát:', e);
        this.$root.toastError('Lỗi khi lấy lịch sử cấp phát IP Store');
        return [];
      });
    },
    setCurrentItem: async function (item) {
      this.currentItem.id = item.id ?? item.ID;
      this.currentItem.ip = item.ip ?? item.IP;
      this.currentItem.loaiIP = item.loaiIP ?? item.IP_LOAI_ID;
      this.currentItem.phanLoaiIP = item.phanLoaiIP ?? item.IP_PHANLOAI_ID;
      this.currentItem.idc = item.idc ?? item.IDC_ID;
      this.currentItem.vrf = item.vrf ?? item.VRF_ID;
      this.currentItem.zone = item.zone ?? item.ZONE_ID;
      this.currentItem.ghiChu = item.ghiChu ?? item.GHI_CHU;
      this.currentItem.hieuLuc = item.hieuLuc ?? item.HIEU_LUC;
      this.currentItem.vlan = item.vlan ?? item.VLAN_ID;
      this.currentItem.subnet = item.subnet ?? item.SUBNET_ID;
      this.currentItem.mucDichSuDung = item.subnetQuyHoach ?? item.SUBNET_QUYHOACH_ID;
      this.currentItem.haTangMang = item.haTangMang ?? item.HATANGMANG;
      this.currentItem.hatangmang_id = item.hatangmang_id ?? item.HATANGMANG_ID;
      this.currentItem.thietBi = item.thietBi ?? item.THIETBI;
      this.lichSuCapPhatList = await this.getLichSuCapPhatIPStore(item.ID);
    },
    setCurrentItemDefault: function () {
      this.setCurrentItem({
        id: 0,
        ip: "",
        loaiIP: 0,
        phanLoaiIP: 0,
        idc: 0,
        vrf: 0,
        zone: 0,
        ghiChu: "",
        hieuLuc: 1,
        vlan: 0,
        subnet: 0,
        subnetQuyHoach: 0,
        haTangMang: '',
        hatangmang_id: 0,
        thietBi: '',
      });
    }, validateControls: function () {
      let msg = "";
      if (this.controls.txtIP.invalid) {
        msg = "Địa chỉ IP không đúng định dạng IPv4 hoặc IPv6.";
      }

      // Validate các trường bắt buộc
      if (this.currentItem.loaiIP == 0 || this.currentItem.loaiIP == null) {
        if (msg != "") msg += "\n\r";
        msg += "Loại IP không được để trống.";
      }
      if (this.currentItem.phanLoaiIP == 0 || this.currentItem.phanLoaiIP == null) {
        if (msg != "") msg += "\n\r";
        msg += "Phân loại IP không được để trống.";
      }
      if (this.currentItem.idc == 0 || this.currentItem.idc == null) {
        if (msg != "") msg += "\n\r";
        msg += "IDC không được để trống.";
      }
      if (this.currentItem.vrf == 0 || this.currentItem.vrf == null) {
        if (msg != "") msg += "\n\r";
        msg += "VRF không được để trống.";
      }
      if (this.currentItem.zone == 0 || this.currentItem.zone == null) {
        if (msg != "") msg += "\n\r";
        msg += "Zone không được để trống.";
      }
      if (this.currentItem.vlan == 0 || this.currentItem.vlan == null) {
        if (msg != "") msg += "\n\r";
        msg += "VLAN không được để trống.";
      }
      if (this.currentItem.subnet == 0 || this.currentItem.subnet == null) {
        if (msg != "") msg += "\n\r";
        msg += "Subnet không được để trống.";
      }

      if (msg != "") this.$root.toastError(msg);
      return msg == "";
    },
    gridItems_RowSelected: async function (args) {
      this.setCurrentItem(args.data);
    },
    gridItems_RowDeselected: async function (args) {
      let items = this.gridItems.getSelectedRecords();
      if (!(items == null || items.length == 0)) {
        this.setCurrentItem(items[0]);
      } else {
        this.setCurrentItemDefault();
      }
    },
    btnGhiLai_Click: async function (args) {
      if (this.validateControls()) {
        let flag = await this.$confirm(
          "Có chắc bạn muốn lưu lại IP Store đang chọn?",
          "Xác nhận thực hiện",
          {
            confirmButtonText: "Có",
            cancelButtonText: "Không",
            type: "info",
            dangerouslyUseHTMLString: true,
          }
        )
          .then((s) => true)
          .catch((e) => {
            return false;
          });
        if (flag) {
          try {
            this.$root.showLoading(true);
            if (this.currentItem.id == 0) {
              let rs = await this.addItem(this.currentItem);
              if (rs) {
                this.dataItems = await this.getItems();
                this.$root.toastSuccess("Thêm IP Store thành công");
              }
              this.enableControls(!rs);
            } else {
              let rs = await this.updateItem(this.currentItem);
              if (rs) {
                this.dataItems = await this.getItems();
                this.$root.toastSuccess("Cập nhật IP Store thành công");
              }
              this.enableControls(!rs);
            }
          } finally {
            this.$root.showLoading(false);
          }
        }
      }
    },
    btnNhapMoi_Click: async function (args) {
      this.enableControls(true);
      this.setCurrentItemDefault();
    },
    btnHuyBo_Click: async function (args) {
      let items = this.gridItems.getSelectedRecords();
      let item = null;
      if (!(items == null || items.length == 0)) {
        if (this.currentItem.id == 0) {
          item = items[0];
        }
        else {
          item = items.find(x => x.ID == this.currentItem.id);
        }
      }
      if (item != null) {
        this.setCurrentItem(item);
      } else {
        this.setCurrentItemDefault();
      }
      this.enableControls(false);
    },
    btnSua_Click: async function (args) {
      this.enableControls(true);
    },
    btnXoa_Click: async function (args) {
      let flag = await this.$confirm(
        "Có chắc bạn muốn xóa IP Store đang chọn?",
        "Xác nhận thực hiện",
        {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
          type: "warning",
          dangerouslyUseHTMLString: true,
        }
      )
        .then((s) => true)
        .catch((e) => {
          return false;
        });
      if (flag) {
        try {
          this.$root.showLoading(true);
          let errorMessages = '';
          let items = this.gridItems.getSelectedRecords();

          if (items?.length) {
            const results = await Promise.all(items.map(async item => {
              try {
                const rs = await this.$root.context.post("/web-ecms/danhmuc-phanloai-hatang/xoa-ipstore", {
                  id: item.ID,
                });

                console.log(rs, '123');
                if (rs.error != 200 || rs.error_code != 'BSS-00000000') {
                  return `${item.IP}: ${rs.message_detail || rs.message || 'Lỗi không xác định'}\n`;
                }
                return '';
              } catch (error) {
                console.error('Lỗi khi xóa IP Store:', error);
                if (error.response?.data) {
                  return `${item.IP}: ${error.response.data.message_detail || error.response.data.message || 'Lỗi không xác định'}\n`;
                }
                return `${item.IP}: Lỗi kết nối server\n`;
              }
            }));
            errorMessages = results.join('');
          }

          if (errorMessages === '') {
            this.setCurrentItemDefault();
            this.dataItems = await this.getItems();
            this.enableControls(false);
            this.$root.toastSuccess("Xóa IP Store thành công");
          } else {
            this.$root.toastError("Xóa IP Store thất bại\n" + errorMessages);
          }
        } catch (error) {
          console.error('Lỗi tổng thể khi xóa IP Store:', error);
          if (error.response?.data) {
            this.$root.toastError(error.response.data.message_detail || error.response.data.message || "Có lỗi xảy ra khi xóa IP Store. Vui lòng thử lại sau.");
          } else {
            this.$root.toastError("Có lỗi xảy ra khi xóa IP Store. Vui lòng thử lại sau.");
          }
        } finally {
          this.$root.showLoading(false);
        }
      }
    },

    getItems: async function () {
      let rs = await this.$root.context.post("/web-ecms/danhmuc-phanloai-hatang/lay-ds-ipstore");
      if (rs != null) {
        const data = rs.data;
        data.forEach(item => {
          item.HIEU_LUC_TEXT = this.getHieuLucText(item.HIEU_LUC);
        });
        return data;
      }
      return [];
    },

    getLoaiIPList: async function () {
      let rs = await this.$root.context.post("/web-ecms/loai-ip/lay-danhsach");
      if (rs != null) {
        this.loaiIPList = rs.data;
      }
    },

    getPhanLoaiIPList: async function () {
      let rs = await this.$root.context.post("/web-ecms/phanloai-ip/lay-danhsach");
      if (rs != null) {
        this.phanLoaiIPList = rs.data;
      }
    },

    getIDCList: async function () {
      let rs = await this.$root.context.get("/web-ecms/danhmuc/ha-tang-idc");
      if (rs != null) {
        this.IDCList = rs.data;
      }
    },

    onChangeIDC: async function (args) {
      let idIDC = args;
      this.getVFRListByIDC(idIDC);
    },

    getVFRListByIDC: async function (idIDC) {
      let rs = await this.$root.context.get("/web-ecms/danhmuc/vrf-by-idc?idcId=" + idIDC);
      if (rs != null) {
        this.VRFList = rs.data;
      }
    },

    onChangeVRF: async function (args) {
      let idVRF = args;
      this.getZoneList(idVRF);
    },

    getZoneList: async function (idVRF) {
      const params = {
        id: idVRF,
      }
      let rs = await this.$root.context.post("/web-ecms/danhmuc/quan-ly-zone/get-zone-by-vrf", params);
      if (rs != null) {
        this.ZoneList = rs.data;
      }
    },

    onChangeZone: async function (args) {
      let idZone = args;
      this.getVlanList(idZone)
    },

    getVlanList: async function (idZone) {
      const params = {
        id: idZone,
      }
      let rs = await this.$root.context.post("/web-ecms/quanly-vlan/get-vlan-by-zone", params);
      if (rs != null) {
        this.VlanList = rs.data;
      }
    },

    onChangeVlan: async function (args) {
      let idVlan = args;
      this.getSubnetList(args);
    },

    getSubnetList: async function (idVlan) {
      const params = {
        vlan_id: idVlan,
        ip_loai_id: this.currentItem.loaiIP
      }
      let rs = await this.$root.context.post("/web-ecms/quanly-subnet/get-subnet-by-vlan-loai-ip", params);
      if (rs != null) {
        this.SubnetList = rs.data;
      }
    },

    changeSubnet: async function (args) {
      const params = {
        id: this.SubnetList.find(x => x.ID == args)?.HATANGMANG_ID
      }
      let rs = await this.$root.context.post("/web-ecms/danhmuc/ha-tang-mang/get-hatangmang", params);
      this.currentItem.hatangmang_id = this.SubnetList.find(x => x.ID == args)?.HATANGMANG_ID;
    },

    getQuyHoachSubnet: async function () {
      let rs = await this.$root.context.get("/web-ecms/danhmuc/quy-hoach-subnet/danh-sach");
      if (rs != null) {
        this.QuyHoachSubnetList = rs.data;
      }
    },

    addItem: async function (args) {
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc-phanloai-hatang/them-moi-ipstore", {
          ip: args.ip.trim(),
          ip_loai_id: args.loaiIP,
          ip_phanloai_id: args.phanLoaiIP,
          idc_id: args.idc,
          vrf_id: args.vrf,
          zone_id: args.zone,
          vlan_id: args.vlan,
          subnet_id: args.subnet,
          subnet_quyhoach_id: args.mucDichSuDung,
          hatangmang_id: args.hatangmang_id,
          thietbimang_id: args.thietbimang_id,
          ghi_chu: args.ghiChu,
          hieu_luc: args.hieuLuc,
        });
        return rs.error == 200 && rs.error_code == 'BSS-00000000';
      } catch (e) {
        console.error('Lỗi khi thêm IP Store:', e);
        if (e.response?.data) {
          this.$root.toastError(e.response.data.message_detail || e.response.data.message || 'Lỗi không xác định');
        } else {
          this.$root.toastError('Lỗi kết nối server');
        }
        return false;
      }
    },

    updateItem: async function (args) {
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc-phanloai-hatang/cap-nhat-ipstore", {
          id: args.id,
          ip: args.ip.trim(),
          ip_loai_id: args.loaiIP,
          ip_phanloai_id: args.phanLoaiIP,
          idc_id: args.idc,
          vrf_id: args.vrf,
          zone_id: args.zone,
          vlan_id: args.vlan,
          subnet_id: args.subnet,
          subnet_quyhoach_id: args.mucDichSuDung,
          hatangmang_id: args.hatangmang_id,
          thietbimang_id: args.thietbimang_id,
          ghi_chu: args.ghiChu,
          hieu_luc: args.hieuLuc,
        });
        return rs.error == 200 && rs.error_code == 'BSS-00000000';
      } catch (e) {
        console.error('Lỗi khi cập nhật IP Store:', e);
        if (e.response?.data) {
          this.$root.toastError(e.response.data.message_detail || e.response.data.message || 'Lỗi không xác định');
        } else {
          this.$root.toastError('Lỗi kết nối server');
        } return false;
      }
    },

    validateIPFormat: function (ip) {
      if (!ip || ip.trim() === "") return false;

      const trimmedIp = ip.trim();

      // Check if it contains CIDR notation (/)
      if (trimmedIp.includes('/')) {
        const parts = trimmedIp.split('/');
        if (parts.length !== 2) return false;

        const ipAddress = parts[0];
        const cidr = parts[1];

        // Validate CIDR prefix length
        const cidrNum = parseInt(cidr, 10);
        if (isNaN(cidrNum)) return false;

        // IPv4 CIDR validation
        const ipv4Regex = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;

        if (ipv4Regex.test(ipAddress)) {
          // Check for leading zeros in IPv4
          const ipParts = ipAddress.split('.');
          for (let part of ipParts) {
            if (part.length > 1 && part[0] === '0') {
              return false;
            }
          }
          // IPv4 CIDR must be between 0 and 32
          return cidrNum >= 0 && cidrNum <= 32;
        }

        // IPv6 CIDR validation
        const ipv6Patterns = [
          /^([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$/,
          /^::$/,
          /^::1$/,
          /^::([0-9a-fA-F]{1,4}:){0,6}[0-9a-fA-F]{1,4}$/,
          /^([0-9a-fA-F]{1,4}:){1,7}:$/,
          /^([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}$/,
          /^([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}$/,
          /^([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}$/,
          /^([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}$/,
          /^([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}$/,
          /^[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})$/,
          /^([0-9a-fA-F]{1,4}:){6}((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/,
          /^::([0-9a-fA-F]{1,4}:){0,5}((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/,
          /^fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}$/,
          /^::ffff:((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/
        ];

        for (let pattern of ipv6Patterns) {
          if (pattern.test(ipAddress)) {
            // Additional validation for IPv6 compression
            if (ipAddress.includes('::')) {
              const doubleColonCount = (ipAddress.match(/::/g) || []).length;
              if (doubleColonCount > 1) {
                return false;
              }
            }
            // IPv6 CIDR must be between 0 and 128
            return cidrNum >= 0 && cidrNum <= 128;
          }
        }

        return false;
      }

      // If no CIDR notation, validate as regular IP (backward compatibility)
      // IPv4 validation with strict format
      const ipv4Regex = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;

      // IPv6 validation (comprehensive patterns)
      const ipv6Patterns = [
        /^([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$/,
        /^::$/,
        /^::1$/,
        /^::([0-9a-fA-F]{1,4}:){0,6}[0-9a-fA-F]{1,4}$/,
        /^([0-9a-fA-F]{1,4}:){1,7}:$/,
        /^([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}$/,
        /^([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}$/,
        /^([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}$/,
        /^([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}$/,
        /^([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}$/,
        /^[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})$/,
        /^([0-9a-fA-F]{1,4}:){6}((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/,
        /^::([0-9a-fA-F]{1,4}:){0,5}((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/,
        /^fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}$/,
        /^::ffff:((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/
      ];

      // Check IPv4 format
      if (ipv4Regex.test(trimmedIp)) {
        const parts = trimmedIp.split('.');
        for (let part of parts) {
          if (part.length > 1 && part[0] === '0') {
            return false;
          }
        }
        return true;
      }

      // Check IPv6 format
      for (let pattern of ipv6Patterns) {
        if (pattern.test(trimmedIp)) {
          if (trimmedIp.includes('::')) {
            const doubleColonCount = (trimmedIp.match(/::/g) || []).length;
            if (doubleColonCount > 1) {
              return false;
            }
          }
          return true;
        }
      }

      return false;
    },
  },
};
</script>
<style>
.check-action {
  top: 3px;
}

.e-rowcell .check-action input[type="checkbox"]:checked:disabled+.name:before {
  background: #fff;
  border-color: #0176ff;
  color: #0176ff;
}

.e-rowcell .check-action input[type="checkbox"]:disabled+.name:before {
  background: #fff;
  border-color: #0176ff;
}
</style>
