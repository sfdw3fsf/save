<template src="./index.template.html"></template>
<script>
import IpUtils from '../IpUtils.js';

export default {
  name: 'ModalChiTietDaiIP',
  props: {
    modalID: null,
  },
  mounted: async function () {
  },
  validations: {},
  watch: {},
  computed: {},
  data: function () {
    return {
      target: '.main-wrapper',
      animationSettings: {effect: 'Zoom'},
      // currentMode: 'VIEW', // VIEW, ADD, EDIT
      isViewDaiIP: false,
      isIPDauRead: false,
      isIPCuoiRead: false,
      isDaiIPChaRead: false,
      showIPv4: true,
      showPhamVi: true,
      isDisableBtnGhiLai: false,
      dsTrangthai: [
        {TT_ID: 1, TT_TEN: 'Chưa sử dụng'},
        {TT_ID: 2, TT_TEN: 'Đang sử dụng'},
        {TT_ID: 3, TT_TEN: 'Bị tạm ngưng sử dụng'},
        {TT_ID: 4, TT_TEN: 'Bị khóa tạm thời'},
      ],
      form: {
        pId: 0,
        pLoaiDaiIPId: 0,
        pLoaiDaiIP: null,
        pPhanLoaiId: null,
        pPhanLoai: null,
        pDiaChiDaiIPChaId: null,
        pDiaChiDaiIPChaSubnetMask: null,
        pDiaChiDaiIPCha: "",
        pDiaChiDaiIP: "",
        pDonviThuchienId: 0,
        pDonviThuchienTen: "",
        pNguoiThuchienTen: "",
        pNhanvienId: 0,
        pNhanvienTen: "",
        pNguoiCapnhat: "",
        pKyHieu: "",
        pIPDau: "",
        pIPCuoi: "",
        pIDCId: 0,
        pIDCTen: "",
        pTrangthaiId: 0,
        pTrangthaiTen: "",
        pSubnetId: 0,
        pSubnetMaskId: 0,
        pSubnetMask: "",
        pGateway: "",
        pCanCuKhaiBao: "",
        pGhichu: "",
        dtpThoidiemKhaibao: new Date(),
        pGhiChu: "",
        tongSoIpDung: 0,
        tongSoIpChua: 0,
        pHieuLuc: 1,
        dsDaiIP: [],
        dsDaiIPCha: [],
        dsLoaiIP: [],
        dsIdc: [],
        dsSubnetMark: [],
      },
    }
  },
  methods: {
    closeModal: function () {
      this.$emit("closeAndRefresh");
      this.$refs.ModalChiTietDaiIP.hide(this.modalID)
    },
    async showModal(item, dsData, showMode) {
      // console.log(2222222)
      console.log(item)
      this.isDaiIPChaRead = true;
      if(showMode == "view") {
        this.isViewDaiIP = true;
      } else {
        this.isViewDaiIP = false;
      }
      if(showMode == "add") {
        this.isDaiIPChaRead = false;
      }
      // console.log(this.isDaiIPChaRead);

      this.isIPDauRead = true;
      this.isIPCuoiRead = true;
      if(showMode == "edit" && item.IPSTORE_CHA_ID > 0) {
        this.isIPDauRead = false;
        this.isIPCuoiRead = false;
      }

      if (item.pId == 0) {
        // console.log(item)
        // this.resetCurrentItem();
        this.resetForm();
      } else
        this.form = this.mapPayloadToForm(item);

      this.form.pDonviThuchienId = dsData.pDonviThuchienId;
      this.form.pDonviThuchienTen = dsData.pDonviThuchienTen;
      this.form.pNguoiThuchienTen = dsData.pNguoiThuchienTen;
      this.form.pNhanvienId = dsData.pNhanvienId;
      this.form.pNhanvienTen = dsData.pNhanvienTen;
      this.form.pNguoiCapnhat = dsData.pNguoiCapnhat;
      this.form.dsDaiIP = dsData.dsDaiIP;
      this.form.dsDaiIPCha = dsData.dsDaiIPCha;
      this.form.dsSubnetMark = dsData.dsSubnetMark;
      this.form.dsIdc = dsData.dsIdc;
      this.form.dsLoaiIP = dsData.dsLoaiIP;
      this.$refs.ModalChiTietDaiIP.show()

    },
    onChangeSubnetMask(value) {
      this.form.pSubnetMaskId = value
      const item = this.form.dsSubnetMark.find(x => x.ID == value)
      this.form.pSubnetMask = item.TEN
    },
    onChangeDaiIPCha(value) {
      if (value == 0) {
        this.form.pDiaChiDaiIPChaId = null;
        this.isIPDauRead = true;
        this.isIPCuoiRead = true;
      } else {
        this.form.pDiaChiDaiIPChaId = value;
        const item = this.form.dsDaiIPCha.find(x => x.ID == value)
        this.form.pDiaChiDaiIPCha = item.TEN;
        this.form.pDiaChiDaiIP = item.TEN;
        this.form.pDiaChiDaiIPChaSubnetMask = item.SUBNETMASK;
        this.isIPDauRead = false;
        this.isIPCuoiRead = false;
      }
    },
    onIDCChange(value) {
      this.form.pIDCId = value
    },
    onLoaiIPChange(value) {
      this.form.pLoaiDaiIPId = value
      const item = this.form.dsLoaiIP.find(x => x.ID == value)
      // this.pDaiIdSelect = selected;
      if (item) {
        const stFirst = item.TEN.indexOf("(");
        const stEnd = item.TEN.indexOf(")");

        const value1 = item.TEN.substring(0, stFirst).trim();
        const value2 = item.TEN.substring(stFirst + 1, stEnd);

        if (value1.toUpperCase() == "IP V4") {
          this.form.pLoaiDaiIP = 1;
          this.showIPv4 = true;
        } else {
          this.form.pLoaiDaiIP = 0;
          this.showIPv4 = false;
        }
        if (value2.toUpperCase() == "INTERNET") {
          this.form.pPhanLoai = 1;
          this.showPhamVi = true;
        } else {
          this.form.pPhanLoai = 0;
          this.showPhamVi = false;
        }
        this.form.pLoaiDaiIPId = item.ID;
        this.form.pPhanLoaiId = item.PHANLOAI_ID;
      }
    },
    onTrangThaiChange(value) {
      this.form.pTrangthaiId = value
    },
    resetForm() {
      this.form = {
        pId: 0,
        pLoaiDaiIPId: 0,
        pLoaiDaiIP: null,
        pPhanLoaiId: null,
        pPhanLoai: null,
        pDiaChiDaiIP: '',
        pDiaChiDaiIPChaId: null,
        pDiaChiDaiIPCha: '',
        pKyHieu: '',
        pSubnetMaskId: 187,         // default subnet mask
        pSubnetId: 0,
        pGateway: '',
        pIDCId: 0,
        pCanCuKhaiBao: '',
        pGhichu: '',
        dtpThoidiemKhaibao: new Date(), // hôm nay
        subnet: '',
        pIPDau: '',
        pIPCuoi: '',
        pTrangthaiId: 1,            // Chưa sử dụng
        pDonviThuchienId: 0,
        pDonviThuchienTen: "",
        pNguoiThucHien: '',
        pSubnetMask: '255.255.255.0',
        tongSoIpDung: 0
      }
    },

    // Simple action handlers - just console log
    onSave: async function () {
      var isValid = this.checkForm();
      if (!isValid)
          //     this.$toast.success("OK")
          // else
        return;
      // console.log(this.form)

      let rs = await this.updateItem(this.form);
    },
    onEdit: function () {
      this.isViewDaiIP = !this.isViewDaiIP;
      this.isDaiIPChaRead = this.isViewDaiIP;
      if (this.form.pDiaChiDaiIPChaId != null && this.form.pDiaChiDaiIPChaId > 0) {
        this.isDaiIPChaRead = true;
        this.isIPDauRead = false;
        this.isIPCuoiRead = false;
      } else {
        this.isIPDauRead = true;
        this.isIPCuoiRead = true;
      }
    },
    checkForm: function () {
      if (this.form.pDiaChiDaiIPChaId != null && this.form.pDiaChiDaiIPChaId > 0) {
        const item = this.form.dsDaiIPCha.find(x => x.ID == this.form.pDiaChiDaiIPChaId)
        this.form.pDiaChiDaiIPCha = item.TEN;
        this.form.pDiaChiDaiIPChaSubnetMask = item.SUBNETMASK;
      }
      if (this.form.pLoaiDaiIPId == 0) {
        this.$root.toastError('Vui lòng chọn Loại dải IP.');
        return false;
      }
      if (this.form.pLoaiDaiIP == 0) {
        this.$root.toastError('Chưa hỗ trợ IPv6.');
        return false;
      }
      if (
          this.form.pDiaChiDaiIPChaId === null ||
          this.form.pDiaChiDaiIPChaId === 0 ||
          this.form.pDiaChiDaiIPChaId === undefined
      ) {
        if (!IpUtils.isValidNetworkAddress(this.form.pDiaChiDaiIP)) {
          this.$root.toastError('IP/Dải IP không hợp lệ.');
          return false;
        }
      }
      if (!this.form.pKyHieu || this.form.pKyHieu.trim() === "") {
        this.$root.toastError('Vui lòng nhập Ký hiệu.');
        return false;
      }
      if (this.form.pIDCId == 0) {
        this.$root.toastError('Vui lòng chọn IDC.');
        return false;
      }
      if (this.form.pSubnetMaskId == 0) {
        this.$root.toastError('Vui lòng chọn SubnetMask.');
        return false;
      }
      if (
          this.form.pDiaChiDaiIPChaId === null ||
          this.form.pDiaChiDaiIPChaId === 0 ||
          this.form.pDiaChiDaiIPChaId === undefined
      ) {
        if (!IpUtils.isValidNetworkAddressWithMask(this.form.pDiaChiDaiIP, this.form.pSubnetMask)) {
          this.$root.toastError('IP/Dải IP không hợp lệ với subnet mask.');
          return false;
        }
      }

      if (this.form.pDiaChiDaiIPChaId > 0) {
        if (!this.form.pIPDau || this.form.pIPDau.trim() === "") {
          this.$root.toastError('Vui lòng nhập IP đầu.');
          return false;
        }
        if (!this.form.pIPCuoi || this.form.pIPCuoi.trim() === "") {
          this.$root.toastError('Vui lòng nhập IP cuối.');
          return false;
        }
        if (!IpUtils.isIPv4(this.form.pIPDau, this.form.pSubnetMask)) {
          this.$root.toastError('IP đầu không hợp lệ.');
          return false;
        }
        // if(this.form.pLoaiDaiIP == 1){
        if (!IpUtils.isIPv4(this.form.pIPCuoi, this.form.pSubnetMask)) {
          this.$root.toastError('IP cuối không hợp lệ.');
          return false;
        }
        if (!IpUtils.isSubnetOfUseMask(this.form.pDiaChiDaiIP, this.form.pSubnetMask, this.form.pDiaChiDaiIPCha, this.form.pDiaChiDaiIPChaSubnetMask)) {
          this.$root.toastError('IP/Dải IP không thuộc Dải IP cha.');
          return false;
        }
        var isValid = IpUtils.isIpRangeValid(this.form.pIPDau, this.form.pIPCuoi, this.form.pSubnetMask);
        if (!isValid) {
          this.$root.toastError("IP đầu và IP cuối không hợp lệ.");
          return false;
        }
        isValid = IpUtils.isIpInRange(this.form.pIPDau, this.form.pDiaChiDaiIP, this.form.pSubnetMask);
        if (!isValid) {
          this.$root.toastError("Địa chỉ IP đầu không thuộc dải IP.");
          return false;
        }
      }
      // if(this.form.pLoaiDaiIP == 1){

      // }else{
      //     if(!this.isIPv6(this.form.pIPCuoi)){
      //         this.$root.toastError('IP không hợp lệ.');
      //         return false;
      //     }
      // }
      if (!this.form.pGateway || this.form.pGateway.trim() === "") {
        this.$root.toastError('Vui lòng nhập Gateway.');
        return false;
      } else {
        if (!IpUtils.checkGatewayValid(this.form.pGateway.trim(), this.form.pDiaChiDaiIP.trim(), this.form.pSubnetMask)) {
          this.$root.toastError('Gateway không hợp lệ.');
          return false;
        }
      }
      if (!this.form.pTrangthaiId || this.form.pTrangthaiId == 0) {
        this.$root.toastError('Vui lòng chọn Trạng thái sử dụng.');
        return false;
      }
      if (!this.form.dtpThoidiemKhaibao) {
        this.$root.toastError('Vui lòng chọn Thời điểm khai báo.');
        return false;
      }

      // if (this.form.pId == 0) {
      //   if (this.isExist(this.form.pDiaChiDaiIP, this.form.pGateway.trim())) {
      //     this.$root.toastError('IP/Dải IP đã tồn tại.');
      //     return false;
      //   }
      // }

      // var isValid = IpUtils.sameSubnet(this.form.pIPDau, this.form.pIPCuoi, this.form.pSubnetMask);
      // if (!isValid) {
      //   this.$root.toastError("IP đầu và IP cuối không hợp lệ.");
      //   return false;
      // }

      return true;
    },
    isExist(ten, gateWay) {
      const exists = this.form.dsDaiIP.some(
          item =>
              item.TEN?.trim().toUpperCase() === ten?.trim().toUpperCase() &&
              item.GATEWAY?.trim().toUpperCase() === gateWay?.trim().toUpperCase()
      );
      return exists;
    },
    mapPayloadToForm(payload) {
      // console.log("payload")
      // console.log(payload)
      function trimUpper(ten) {
        if (!ten) return 0;
        return ten.trim().toUpperCase();
      }

      return {
        pId: payload.ID || 0,
        pCanCuKhaiBao: payload.CANCU_TH || "",
        pGhichu: payload.GHI_CHU || "",
        tongSoIpDung: payload.DA_SU_DUNG || "",
        pGateway: payload.GATEWAY || "",
        pIDCId: payload.IDC_ID || 0,
        pLoaiDaiIPId: payload.IPL_ID || 0,
        pPhanLoaiId: payload.IPPL_ID || 0,
        pLoaiDaiIP: trimUpper(payload.TENLOAIIP) == "IP V4" ? 1 : 0,
        pPhanLoai: trimUpper(payload.TENPHANLOAIIP) == "INTERNET" ? 1 : 0,
        pIPCuoi: payload.IP_CUOI || "",
        pIPDau: payload.IP_DAU || "",
        pKyHieu: payload.KYHIEU || "",
        pSubnetMask: payload.SUBNETMARK || "",
        pDiaChiDaiIP: payload.TEN || "",
        pDiaChiDaiIPChaId: payload.IPSTORE_CHA_ID || null,
        // pDiaChiDaiIPCha: payload.DIACHIDAIIPCHA || "",
        pTrangthaiId: payload.TRANGTHAI_ID || 0,
        pSubnetMaskId: payload.SUBNETMARK_ID || 0,
        dtpThoidiemKhaibao: payload.NGAY_TH ? new Date(payload.NGAY_TH) : null,
        pDonviThuchienId: payload.DONVI_ID || 0,   // nếu có trường đơn vị quản lý
        pDonviThuchienTen: payload.DONVI_TEN || "",   // nếu có trường đơn vị quản lý
        pNguoiThuchienTen: payload.NHANVIEN_TEN || "",
      };
    },
    toNumberOrNull(val) {
      if (val === undefined || val === null || val === "" || isNaN(val)) {
        return null;
      }
      return Number(val);
    },
    formatDateISO(date) {
      if (!date) return null;
      const d = new Date(date);
      const year = d.getFullYear();
      const month = String(d.getMonth() + 1).padStart(2, "0");
      const day = String(d.getDate()).padStart(2, "0");
      return `${year}-${month}-${day}`;  // ISO yyyy-MM-dd
    },
    updateItem: async function (args) {
      let result = true;
      // console.log(args)
      try {
        this.loading(true);
        // xử lý id: 0 => null
        const payload = {
          id: (args.pId === 0 ? null : this.toNumberOrNull(args.pId)),
          idCha: (args.pDiaChiDaiIPChaId === 0 ? null : this.toNumberOrNull(args.pDiaChiDaiIPChaId)),
          ip_loai_id: this.toNumberOrNull(args.pLoaiDaiIPId),
          ip_phanloai_id: this.toNumberOrNull(args.pPhanLoaiId),
          subnet_mask_id: this.toNumberOrNull(args.pSubnetMaskId),
          idc_id: this.toNumberOrNull(args.pIDCId),
          trangthai_id: this.toNumberOrNull(args.pTrangthaiId),
          ten: args.pDiaChiDaiIP,
          kyhieu: args.pKyHieu,
          ipDau: args.pIPDau,
          ipCuoi: args.pIPCuoi,
          gateway: args.pGateway,
          cancuTH: args.pCanCuKhaiBao ? args.pCanCuKhaiBao : null,
          ghichu: args.pGhichu ? args.pGhichu : null,
          // subnet_id: this.toNumberOrNull(args.pSubnetId),
          thoidiem_Khaibao: this.formatDateISO(args.dtpThoidiemKhaibao),
          donvi_th_id: args.pDonviThuchienId,
          nguoiCapnhat: args.pNguoiCapnhat,
        };
        // console.log(payload)

        let rs = await this.$root.context.post("/web-ecms/idc/daiIP/update-daiIP", payload);

        if (rs) {
          var pId = rs.data.ID;
          if (this.form.pId == 0) {
            this.$root.toastSuccess("Thêm IP/Dải IP thành công");
            if (
                this.form.pDiaChiDaiIPChaId === null ||
                this.form.pDiaChiDaiIPChaId === 0 ||
                this.form.pDiaChiDaiIPChaId === undefined
            ) {
              let msg = `Bạn có muốn thêm IP/Dải IP con không?`
              let flag = await this.$confirm(msg, "Xác nhận",
                  {
                    confirmButtonText: "Đồng ý",
                    cancelButtonText: "Không đồng ý",
                    type: "info",
                    dangerouslyUseHTMLString: true,
                  }
              )
                  .then((s) => true)
                  .catch((e) => {
                    return false;
                  });
              if (flag) {
                // rs = await this.$root.context.post('/web-ecms/idc/daiIP/lay-ds-daiIP-cha', {});
                if (this.form.pDonviThuchienId == 891543) {
                  rs = await this.$root.context.post('/web-ecms/idc/daiIP/lay-ds-daiIP-DonviId', 0);
                }
                else {
                  rs = await this.$root.context.post('/web-ecms/idc/daiIP/lay-ds-daiIP-DonviId', this.form.pDonviThuchienId);
                }
                // this.dsData.dsDaiIPCha = rs.data;
                // this.dsDaiIPCha = rs.data;
                this.form.dsDaiIPCha = [{ID: 0, TEN: 'Bỏ chọn'}, ...rs.data];
                this.$nextTick(() => {
                  this.form.pDiaChiDaiIPChaId = pId;
                  // this.form.pId = pId;
                });
                this.isIPDauRead =false;
                this.isIPCuoiRead = false;
                this.isDaiIPChaRead = true;

              } else {
                this.closeModal();
              }
            } else {
              this.closeModal();
            }
          } else {
            this.closeModal();
            this.$root.toastSuccess("Cập nhật IP/Dải IP thành công");
          }
        } else {
          if (this.form.pId == 0) {
            this.closeModal();
            this.$root.toastError("Thêm IP/Dải IP thất bại");
          }
        }
      } catch
          (e) {
        this.handleApiError(e);
        console.error("API error:", e);
        result = false;
      } finally {
        // var rs1 = await this.$root.context.post('/web-ecms/idc/daiIP/lay-ds-daiIP', {});
        // this.dsDaiIP = rs1.data;
        this.loading(false);
      }
      return result;
      // this.enableControls(true);
    }
    ,
    handleApiError: function (e) {
      if (e?.response?.data?.message_detail) {
        this.$root.toastError(e.response.data.message_detail);
      }
    }
    ,
  },
  // mounted: function () { }
}
</script>
<style>
.popup-box {
  max-height: 90vh !important;
  overflow-y: auto;
}

.info-row .value .d-flex input:first-child {
  margin-right: 10px;
}

.text-muted {
  font-size: 0.875em;
  color: #6c757d !important;
  margin-top: 2px;
}
</style>
