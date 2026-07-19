<template src="./RegiSMSBrandname.html">
</template>
<script>
  import Vue from "vue";
  import { MultiSelectPlugin, CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns"
  import moment from "moment"
  import frmBRNGuiMail from "@/modules/contract/complete_profile/CompleteProfileITVAS/popups/frmBRNGuiMail/frmBRNGuiMail.vue"
  
  export default {
    name: "RegiSMSBrandname",
    components: {  frmBRNGuiMail },
    //props: {
    //  id: String,
    //},
    provide: {
      multiselect: [CheckBoxSelection]
    },
    data: function () {
      return {
        options: {
          cboLoaiVina: [],
          cboLoaiMobi: [],
          cboLoaiVtl: [],
          cboLoaiVnm: [],
          cboHoSo: [],
          cboLoaiGtel: [],
          cboLoaiItel: [],
          cboLoaiMobicast: []
        },

        model: {
          cboLoaiVina: [],
          cboLoaiMobi: [],
          cboLoaiVtl: [],
          cboLoaiVnm: [],
          cboHoSo: [],
          cboLoaiGtel: [],
          cboLoaiItel: [],
          cboLoaiMobicast: []
        },

        txtHopDong: "",
        txtThueBaoAo: "",
        nmGiaVina: "",
        txtTenKhachHang: "",
        nmGiaMobi: "",
        txtTenNhan: "",
        nmGiaVtl: "",
        txtMaSoThue: "",
        dtpNgayHetHan: "",
        ckbNgayHetHan: false,

        searchDate: {
          isEnabled: false,
          fromDate: new Date(),
          toDate: new Date(),
          dateconfig: {
            altFormat: 'd/m/Y',
            altInput: true,
            dateFormat: 'd/m/Y',
            allowInput: true,
          },
        },

        nmGiaVnm: "",
        nmGiaGtel: "",
        txtGhiChu: "",
        nmGiaItel: "",
        nmGiaMobicast: "",

        colsgrcDanhSach: [
          {
            fieldName: "label_id",
            headerText: "Nhãn ID",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "label",
            headerText: "Thương hiệu",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "expired_date",
            headerText: "Ngày hết hạn",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "status_name",
            headerText: "Trạng thái Vinaphone",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "status_vms_name",
            headerText: "Trạng thái Mobifone",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "status_vtl_name",
            headerText: "Trạng thái Viettel",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "status_gtel_name",
            headerText: "Trạng thái Gtel",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "status_vnm_name",
            headerText: "Trạng thái Vietnamobile",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "status_itel_name",
            headerText: "Trạng thái Itel",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "status_mobicast_name",
            headerText: "Trạng thái Mobicast",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "reason_reject",
            headerText: "Lý do từ chối",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          }
        ],
        grcDanhSach: [],
        hdtb_id: 0,
        success_code: "BSS-00000000",

        thuebao_id: 0,
        agent_id: 0,
        adser_id: 0,
        contract_id: 0,
        agent_type_id: 0,
        contract_type_id: 0,
        label_id: 0,
        status: 0,

        dic: {
          "-2": "Đã xóa",
          "-1": "Đã khóa",
          "0": "Chờ duyệt",
          "1": "Đã duyệt",
          "2": "Từ chối",
          "3": "Đang cập nhật",
          "4": ""
        },

        dt: [],


        popupComponent: null,
        popupComponentData: null,
        popupComponentAttr: {},
        popupComponentEvts: {
          'form-close': this.popupClosed,
          'acceptChangeCTV': this.popupClosed,
          'acceptChangeNGT': this.popupClosed,
          'PopUpCongTacVien_Thoat': this.popupClosed,
        },
        popupComponentName: '',
        resource: ''
      }
    },

    methods: {
      openDialog(data) {
        if (!data) {
          return
        }
        this.hdtb_id = data.hdtb_id
        console.log(this.hdtb_id)
        this.$refs.dialog_RegiSMSBrandname.show()
      },

      async frm_BRN_TDTT_Brandname_Load() {
        try {
          await this.loading(true)
          debugger
          this.init_timer()
          //this.hdtb_id = 9110701

          this.resource = (await this.axios.post('/web-hopdong/quanly_khdn_mnp/sp_lay_ds_dl_nghiepvu', {
              P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
              p_nghiepvu: 'KIEMTRA_WHITELIST_BRANDNAME',
              p_ds_para: JSON.stringify({ vhdtb_id: this.hdtb_id })
            })).data.data[0].kq == 'ok' ? 'web-thicong' : 'tichhop';
          await this.LoadDanhMuc()
          await this.AnTrangThaiLinhVucDaDangKy(1)
          await this.AnTrangThaiLinhVucDaDangKy(2)
          await this.AnTrangThaiLinhVucDaDangKy(3)
          await this.AnTrangThaiLinhVucDaDangKy(4)
          await this.AnTrangThaiLinhVucDaDangKy(5)
          await this.AnTrangThaiLinhVucDaDangKy(7)
          await this.AnTrangThaiLinhVucDaDangKy(8)
          await this.LoadThongTinBrandname()
          await this.LoadThongtinTrangthaiBRN()
        }
        catch (err) {
          this.$toast.error(err.message);
        } finally {
          await this.loading(false)
        }
      },

      grcDanhSach_rowSelected(args) {

      },

      getDate(strDate, format) {
        if (format) {
          return moment(strDate, format).toDate()
        } else {
          return moment(strDate, 'DD/MM/YYYY HH:mm:ss').toDate()
        }
      },

      formatDate(date, format) {
        if (format) {
          return moment(date).format(format)
        } else {
          return moment(date).format('DD/MM/YYYY HH:mm:ss')
        }
      },


      async LoadDanhMuc() {
        try {

          var json_data_sp_load_dulieu_form_tao_brn = JSON.stringify({
            "vhdtb_id": this.hdtb_id
          });
          var link_sp_load_dulieu_form_tao_brn = "/web-quantri/sms-brandname/sp_load_dulieu_form_tao_brn"
          const files_sp_load_dulieu_form_tao_brn = await this.axios.post(link_sp_load_dulieu_form_tao_brn, json_data_sp_load_dulieu_form_tao_brn, {
            headers: {
              'Content-Type': 'application/json'
            }
          });
          if (files_sp_load_dulieu_form_tao_brn.data.data.ds_hoso && files_sp_load_dulieu_form_tao_brn.data.error_code == this.success_code) {
            var ds = files_sp_load_dulieu_form_tao_brn.data.data.data
            this.options.cboHoSo = files_sp_load_dulieu_form_tao_brn.data.data.ds_hoso

            this.thuebao_id = ds[0].thuebao_id
            this.agent_id = ds[0].agent_id
            this.adser_id = ds[0].adser_id
            this.contract_id = ds[0].contract_id
            this.agent_type_id = ds[0].agent_type_id
            this.contract_type_id = ds[0].contract_type_id
            this.label_id = ds[0].label_id
          }

          var json_fn_get_status_hdtb = JSON.stringify({
            "vhdtb_id": this.hdtb_id
          });
          var link_fn_get_status_hdtb = "/web-thicong/hoinghi_truyenhinh/fn_get_status_hdtb"
          const files_fn_get_status_hdtb = await this.axios.post(link_fn_get_status_hdtb, json_fn_get_status_hdtb, {
            headers: {
              'Content-Type': 'application/json'
            }
          });

          if (files_fn_get_status_hdtb.data.data && files_fn_get_status_hdtb.data.error_code == this.success_code) {
            status = files_fn_get_status_hdtb.data.data
            if (status == 1)
              this.SetButton(2)
            else
              this.SetButton(1)
          }

          await this.HT_DS_LINHVUC_NHAMANG(1)
          await this.HT_DS_LINHVUC_NHAMANG(2)
          await this.HT_DS_LINHVUC_NHAMANG(3)
          await this.HT_DS_LINHVUC_NHAMANG(4)
          await this.HT_DS_LINHVUC_NHAMANG(5)
          await this.HT_DS_LINHVUC_NHAMANG(7)
          await this.HT_DS_LINHVUC_NHAMANG(8)


        }
        catch (err) {
          this.$toast.error(err.message)
        }
      },

      async LoadThongTinBrandname() {
        try {
          //debugger
          var json_data_sp_lay_thongtin_dangky_brandname = JSON.stringify({
            "vhdtb_id": this.hdtb_id
          });
          var link_sp_lay_thongtin_dangky_brandname = "/web-quantri/sms-brandname/sp_lay_thongtin_dangky_brandname"
          const files_sp_lay_thongtin_dangky_brandname = await this.axios.post(link_sp_lay_thongtin_dangky_brandname, json_data_sp_lay_thongtin_dangky_brandname, {
            headers: {
              'Content-Type': 'application/json'
            }
          });
          if (files_sp_lay_thongtin_dangky_brandname.data.data && files_sp_lay_thongtin_dangky_brandname.data.error_code == this.success_code) {
            var ds = files_sp_lay_thongtin_dangky_brandname.data.data
            this.txtHopDong = ds[0].ten_pl
            this.txtTenNhan = ds[0].label
            this.txtTenKhachHang = ds[0].customer
            this.txtMaSoThue = ds[0].taxcode
            this.dtpNgayHetHan = ds[0].expired_date
            this.txtThueBaoAo = ds[0].mobile_alias
            this.txtGhiChu = ds[0].note
            this.username = ds[0].username

            this.model.cboLoaiVina = Number(ds[0].label_type_vnp) != 0 ? Number(ds[0].label_type_vnp) : -1
            this.model.cboLoaiMobi = Number(ds[0].label_type_vms) != 0 ? Number(ds[0].label_type_vms) : -1
            this.model.cboLoaiVtl = Number(ds[0].label_type_vtl) != 0 ? Number(ds[0].label_type_vtl) : -1
            this.model.cboLoaiGtel = Number(ds[0].label_type_gtel) != 0 ? Number(ds[0].label_type_gtel) : -1
            this.model.cboLoaiVnm = Number(ds[0].label_type_vnm) != 0 ? Number(ds[0].label_type_vnm) : -1
            this.model.cboLoaiItel = Number(ds[0].label_type_itel) != 0 ? Number(ds[0].label_type_itel) : -1
            this.model.cboLoaiMobicast = Number(ds[0].label_type_mobicast) != 0 ? Number(ds[0].label_type_mobicast) : -1

            this.nmGiaVina = Number(ds[0].cuoc_sms_vnp)
            this.nmGiaMobi = Number(ds[0].cuoc_sms_vms)
            this.nmGiaVtl = Number(ds[0].cuoc_sms_vtl)
            this.nmGiaGtel = Number(ds[0].cuoc_sms_gtel)
            this.nmGiaVnm = Number(ds[0].cuoc_sms_vnm)
            this.nmGiaItel = Number(ds[0].cuoc_sms_itel)
            this.nmGiaMobicast = Number(ds[0].cuoc_sms_mobicast)
          }
        }
        catch (err) {
          this.$toast.error(err.message)
        }
      },

      async LoadThongtinTrangthaiBRN() {
        try {
          //debugger
          var json_fn_get_status_hdtb = JSON.stringify({
            "vhdtb_id": this.hdtb_id
          });
          var link_fn_get_status_hdtb = "/web-thicong/hoinghi_truyenhinh/fn_get_status_hdtb"
          const files_fn_get_status_hdtb = await this.axios.post(link_fn_get_status_hdtb, json_fn_get_status_hdtb, {
            headers: {
              'Content-Type': 'application/json'
            }
          });

          if (files_fn_get_status_hdtb.data.data && files_fn_get_status_hdtb.data.error_code == this.success_code) {
            status = files_fn_get_status_hdtb.data.data
            //debugger
            if (status == 1) {
              var json_LAY_DS_NHAN = JSON.stringify({
                "agent_id": this.agent_id,
                "adser_id": this.adser_id,
                "contract_id": this.contract_id,
                "agent_type": Number(this.agent_type_id)
              });
              var link_LAY_DS_NHAN = `/${this.resource}/brandname/LAY_DS_NHAN`
              const files_LAY_DS_NHAN = await this.axios.post(link_LAY_DS_NHAN, json_LAY_DS_NHAN, {
                headers: {
                  'Content-Type': 'application/json'
                }
              });
              debugger
              if (files_LAY_DS_NHAN.data.data && files_LAY_DS_NHAN.data.error_code == this.success_code) {
                this.dt = files_LAY_DS_NHAN.data.data.data.items.filter(item => (item.label_id === this.label_id))
                for (let i = 0; i < this.dt.length; i++) {
                  let temp = this.dt[i].label_type.toString()
                  let temp2 = this.dt[i].status.toString()
                  this.dt[i].status_name = this.dic[(temp == "0" || temp == "" || temp == "-1") ? "4" : (temp2 == "2" ? "0" : this.dt[i].status.toString())]
                  temp = this.dt[i].label_type_vms.toString()
                  temp2 = this.dt[i].status_vms.toString()
                  this.dt[i].status_vms_name = this.dic[(temp == "0" || temp == "" || temp == "-1") ? "4" : (temp2 == "2" ? "0" : this.dt[i].status_vms.toString())]
                  temp = this.dt[i].label_type_vtl.toString()
                  temp2 = this.dt[i].status_vtl.toString()
                  this.dt[i].status_vtl_name = this.dic[(temp == "0" || temp == "" || temp == "-1") ? "4" : (temp2 == "2" ? "0" : this.dt[i].status_vtl.toString())]
                  temp = this.dt[i].label_type_gtel.toString()
                  temp2 = this.dt[i].status_gtel.toString()
                  this.dt[i].status_gtel_name = this.dic[(temp == "0" || temp == "" || temp == "-1") ? "4" : (temp2 == "2" ? "0" : this.dt[i].status_gtel.toString())]
                  temp = this.dt[i].label_type_vnm.toString()
                  temp2 = this.dt[i].status_vnm.toString()
                  this.dt[i].status_vnm_name = this.dic[(temp == "0" || temp == "" || temp == "-1") ? "4" : (temp2 == "2" ? "0" : this.dt[i].status_vnm.toString())]
                  temp = this.dt[i].label_type_itel.toString()
                  temp2 = this.dt[i].status_itel.toString()
                  this.dt[i].status_itel_name = this.dic[(temp == "0" || temp == "" || temp == "-1" || this.model.cboLoaiItel.toString() == "-1") ? "4" : (temp2 == "2" ? "0" : this.dt[i].status_itel.toString())]
                  temp = this.dt[i].label_type_mobicast.toString()
                  temp2 = this.dt[i].status_mobicast.toString()
                  this.dt[i].status_mobicast_name = this.dic[(temp == "0" || temp == "" || temp == "-1" || this.model.cboLoaiMobicast.toString() == "-1") ? "4" : (temp2 == "2" ? "0" : this.dt[i].status_mobicast.toString())]
                }
                this.grcDanhSach = this.dt
                //for (let i = 0; i < dt.length; i++) {
                //  let temp = dt[i].label_type.toString()
                //  let temp2 = dt[i].status.toString()
                //  dt[i].status_name = this.dic[(temp == "0" || temp == "" || temp == "-1") ? "4" : (temp2 == "2" ? "0" : dt[i].status.toString())]
                //  temp = dt[i].label_type_vms.toString()
                //  temp2 = dt[i].status_vms.toString()
                //  dt[i].status_vms_name = this.dic[(temp == "0" || temp == "" || temp == "-1") ? "4" : (temp2 == "2" ? "0" : dt[i].status_vms.toString())]
                //  temp = dt[i].label_type_vtl.toString()
                //  temp2 = dt[i].status_vtl.toString()
                //  dt[i].status_vtl_name = this.dic[(temp == "0" || temp == "" || temp == "-1") ? "4" : (temp2 == "2" ? "0" : dt[i].status_vtl.toString())]
                //  temp = dt[i].label_type_gtel.toString()
                //  temp2 = dt[i].status_gtel.toString()
                //  dt[i].status_gtel_name = this.dic[(temp == "0" || temp == "" || temp == "-1") ? "4" : (temp2 == "2" ? "0" : dt[i].status_gtel.toString())]
                //  temp = dt[i].label_type_vnm.toString()
                //  temp2 = dt[i].status_vnm.toString()
                //  dt[i].status_vnm_name = this.dic[(temp == "0" || temp == "" || temp == "-1") ? "4" : (temp2 == "2" ? "0" : dt[i].status_vnm.toString())]
                //  temp = dt[i].label_type_itel.toString()
                //  temp2 = dt[i].status_itel.toString()
                //  dt[i].status_itel_name = this.dic[(temp == "0" || temp == "" || temp == "-1" || this.model.cboLoaiItel.toString() == "-1") ? "4" : (temp2 == "2" ? "0" : dt[i].status_itel.toString())]
                //  temp = dt[i].label_type_mobicast.toString()
                //  temp2 = dt[i].status_mobicast.toString()
                //  dt[i].status_mobicast_name = this.dic[(temp == "0" || temp == "" || temp == "-1" || this.model.cboLoaiMobicast.toString() == "-1") ? "4" : (temp2 == "2" ? "0" : dt[i].status_mobicast.toString())]
                //}

              }
            }
          }
        }
        catch (err) {
          this.$toast.error(err.message)
        }
      },

      async HT_DS_LINHVUC_NHAMANG(telco_id) {
        try {
          var json_LAY_DS_LINHVUC = JSON.stringify({
            "telco_id": telco_id,
            "contract_type_id": Number(this.contract_type_id),
            "agent_type": Number(this.agent_type_id)
          });
          var link_LAY_DS_LINHVUC = `/${this.resource}/brandname/LAY_DS_LINHVUC`
          const files_LAY_DS_LINHVUC = await this.axios.post(link_LAY_DS_LINHVUC, json_LAY_DS_LINHVUC, {
            headers: {
              'Content-Type': 'application/json'
            }
          });
          if (files_LAY_DS_LINHVUC.data.data && files_LAY_DS_LINHVUC.data.error_code == this.success_code) {
            var ds = files_LAY_DS_LINHVUC.data.data.data.items
            switch (telco_id) {
              case 1:
                this.options.cboLoaiVina = ds
                break;
              case 2:
                this.options.cboLoaiMobi = ds
                break;
              case 3:
                this.options.cboLoaiVtl = ds
                break;
              case 4:
                this.options.cboLoaiGtel = ds
                break;
              case 5:
                this.options.cboLoaiVnm = ds
                break;
              case 7:
                this.options.cboLoaiItel = ds
                break;
              case 8:
                this.options.cboLoaiMobicast = ds
                break;
            }
          }
        }
        catch (err) {
          this.$toast.error(err.message)
        }
      },

      async AnTrangThaiLinhVucDaDangKy(telco_id) {
        try {
          //debugger
          var json_fn_kiemtra_dangky_nhamang_brn = JSON.stringify({
            "vthuebao_id": this.thuebao_id,
            "vtelco_id": telco_id
          });
          var link_fn_kiemtra_dangky_nhamang_brn = "/web-quantri/sms-brandname/fn_kiemtra_dangky_nhamang_brn"
          const files_fn_kiemtra_dangky_nhamang_brn = await this.axios.post(link_fn_kiemtra_dangky_nhamang_brn, json_fn_kiemtra_dangky_nhamang_brn, {
            headers: {
              'Content-Type': 'application/json'
            }
          });
          //debugger
          if (files_fn_kiemtra_dangky_nhamang_brn.data.data && files_fn_kiemtra_dangky_nhamang_brn.data.error_code == this.success_code) {
            var khoa_mo = files_fn_kiemtra_dangky_nhamang_brn.data.data
            if (khoa_mo == "OK") {
              switch (telco_id) {
                case 1:
                  this.disableButton("cboLoaiVina")
                  break;
                case 2:
                  this.disableButton("cboLoaiMobi")
                  break;
                case 3:
                  this.disableButton("cboLoaiVtl")
                  break;
                case 4:
                  this.disableButton("cboLoaiGtel")
                  break;
                case 5:
                  this.disableButton("cboLoaiVnm")
                  break;
                case 7:
                  this.disableButton("cboLoaiItel")
                  break;
                case 8:
                  this.disableButton("cboLoaiMobicast")
                  break;
              }
            }
            else {
              switch (telco_id) {
                case 1:
                  this.enableButton("cboLoaiVina")
                  break;
                case 2:
                  this.enableButton("cboLoaiMobi")
                  break;
                case 3:
                  this.enableButton("cboLoaiVtl")
                  break;
                case 4:
                  this.enableButton("cboLoaiGtel")
                  break;
                case 5:
                  this.enableButton("cboLoaiVnm")
                  break;
                case 7:
                  this.enableButton("cboLoaiItel")
                  break;
                case 8:
                  this.enableButton("cboLoaiMobicast")
                  break;
              }
            }
          }
        }
        catch (err) {
          this.$toast.error(err.message)
        }
      },

      async btnNhapMoi_Click() {
        if (this.model.cboHoSo == "") {
          this.$toast.warning("Chọn file hồ sơ")
          return
        }
             for (var i = 0; i < this.model.cboHoSo.length; i++) {
        if (this.model.cboHoSo[i].file_path.indexOf('https://storage-onebss.vnpt.vn/onebss-production/') == -1) {
          var url = (await this.GetFileURL(this.model.cboHoSo[i].file_path)).data.data[0].file_url;
          this.model.cboHoSo[i].file_path = url;
        }
      }
      console.log("file_hs", this.model.cboHoSo);
        try {
          await this.loading(true)
          var json_UPDATEBRANDNAME = JSON.stringify({
            "label_id": this.label_id,
            "label": this.txtTenNhan,
            "label_type_vnp": Number(this.model.cboLoaiVina),
            "label_type_vms": Number(this.model.cboLoaiMobi),
            "label_type_vtl": Number(this.model.cboLoaiVtl),
            "label_type_gtel": Number(this.model.cboLoaiGtel),
            "label_type_vnm": Number(this.model.cboLoaiVnm),
            "label_type_itel": Number(this.model.cboLoaiItel),
            "label_type_mobicast": Number(this.model.cboLoaiMobicast),
            "customer": this.txtTenKhachHang,
            "taxCode": this.txtMaSoThue,
            "username": this.username,
            "expired_date": this.formatDate(this.getDate(this.dtpNgayHetHan), 'DD/MM/YYYY'),
            "note": this.txtGhiChu,
            "mobile_alias": this.txtThueBaoAo,
            "ds_file": JSON.parse(JSON.stringify(this.model.cboHoSo)),

            "gia_vnp": Number(this.nmGiaVina),
            "gia_vms": Number(this.nmGiaMobi),
            "gia_vtl": Number(this.nmGiaVtl),
            "gia_gtel": Number(this.nmGiaGtel),
            "gia_vnm": Number(this.nmGiaVnm),
            "gia_itel": Number(this.nmGiaItel),
            "gia_mbicast": Number(this.nmGiaMobicast)
          });
          var link_UPDATEBRANDNAME = `/${this.resource}/brandname/UPDATEBRANDNAME`
          const files_UPDATEBRANDNAME = await this.axios.post(link_UPDATEBRANDNAME, json_UPDATEBRANDNAME, {
            headers: {
              'Content-Type': 'application/json'
            }
          });
          debugger

          if (files_UPDATEBRANDNAME.data.data && files_UPDATEBRANDNAME.data.error_code == this.success_code) {
            var ds = files_UPDATEBRANDNAME.data.data
            var result = ds
            if (result.message!="Success") {
              this.$toast.error(ds.message + ". Liên hệ admin để được hỗ trợ")
              this.SetButton(1)
              return
            }
            else {
              this.$toast.success(ds.message);
              await this.axios.post('/web-ccdv/tram-thicong-dv-cntt/cap_nhat_status_hdtb', { vhdtb_id: this.hdtb_id, vstatus: 1 });
              this.LoadThongtinTrangthaiBRN()
              this.SetButton(2);

            }
          }
        }
        catch (err) {
          this.$toast.error(err.message);
        } finally {
          await this.loading(false)
        }
      },

      async btnCapNhat_Click() {
         try {
          // await this.loading(true)
          // debugger
          var link_fn_co_kiemtra_trangthai_brn = "/web-quantri/sms-brandname/fn_co_kiemtra_trangthai_brn"
          const files_fn_co_kiemtra_trangthai_brn = await this.axios.post(link_fn_co_kiemtra_trangthai_brn)
          if (files_fn_co_kiemtra_trangthai_brn.data.data && files_fn_co_kiemtra_trangthai_brn.data.error_code == this.success_code) {
            var result = files_fn_co_kiemtra_trangthai_brn.data.data
            if (result == "OK") {
              for (let i = 0; i < this.dt.length; i++) {
                var temp = this.dt[i].label_type.toString()
                var temp2 = this.dt[i].status.toString()
                if (temp != "0" && temp != "" && temp != "-1") {
                  if (temp2 == "0" || temp2 == "3") {
                    this.$toast.warning('Trạng thái mạng Vinaphone đang duyệt, không thể hoàn thành')
                    return
                  }
                }

                temp = this.dt[i].label_type_vms.toString()
                temp2 = this.dt[i].status_vms.toString()
                if (temp != "0" && temp != "" && temp != "-1") {
                  if (temp2 == "0" || temp2 == "3") {
                    this.$toast.warning('Trạng thái mạng Mobifone đang duyệt, không thể hoàn thành')
                    return
                  }
                }

                temp = this.dt[i].label_type_vtl.toString()
                temp2 = this.dt[i].status_vtl.toString()
                if (temp != "0" && temp != "" && temp != "-1") {
                  if (temp2 == "0" || temp2 == "3") {
                    this.$toast.warning('Trạng thái mạng Viettel đang duyệt, không thể hoàn thành')
                    return
                  }
                }

                temp = this.dt[i].label_type_gtel.toString()
                temp2 = this.dt[i].status_gtel.toString()
                if (temp != "0" && temp != "" && temp != "-1") {
                  if (temp2 == "0" || temp2 == "3") {
                    this.$toast.warning('Trạng thái mạng Gtel đang duyệt, không thể hoàn thành')
                    return
                  }
                }

                temp = this.dt[i].label_type_vnm.toString()
                temp2 = this.dt[i].status_vnm.toString()
                if (temp != "0" && temp != "" && temp != "-1") {
                  if (temp2 == "0" || temp2 == "3") {
                    this.$toast.warning('Trạng thái mạng Vietnamobile đang duyệt, không thể hoàn thành')
                    return
                  }
                }
                try {
                  if(this.dt[i].label_type_itel != null && this.dt[i].status_itel != null) {
                    temp = this.dt[i].label_type_itel.toString()
                    temp2 = this.dt[i].status_itel.toString()
                    if (temp != "0" && temp != "" && temp != "-1" && cboLoaiItel.EditValue.toString() != "-1") {
                      if (temp2 == "0" || temp2 == "3") {
                        this.$toast.warning('Trạng thái mạng Itel đang duyệt, không thể hoàn thành')
                        return
                      }
                    }
                  }

                  if(this.dt[i].label_type_mobicast != null && this.dt[i].status_mobicast  != null) {
                    temp = this.dt[i].label_type_mobicast.toString()
                    temp2 = this.dt[i].status_mobicast.toString()
                    if (temp != "0" && temp != "" && temp != "-1" && cboLoaiMobicast.EditValue.toString() != "-1") {
                      if (temp2 == "0" || temp2 == "3") {
                        this.$toast.warning('Trạng thái mạng Mobicast đang duyệt, không thể hoàn thành')
                        return
                      }
                    }
                  }
                } catch(ex){
                  console.error(ex);
                }
              }
            }
            var trangthai = JSON.stringify(this.dt[0])

            var json_fn_update_trangthai_hoancong_brn = JSON.stringify({
              "vhdtb_id": this.hdtb_id,
              "vjson_trangthai": trangthai
            });
            var link_fn_update_trangthai_hoancong_brn = "/web-quantri/sms-brandname/fn_update_trangthai_hoancong_brn"
            const files_fn_update_trangthai_hoancong_brn = await this.axios.post(link_fn_update_trangthai_hoancong_brn, json_fn_update_trangthai_hoancong_brn, {
              headers: {
                'Content-Type': 'application/json'
              }
            });
            if (files_fn_update_trangthai_hoancong_brn.data.data && files_fn_update_trangthai_hoancong_brn.data.error_code == this.success_code) {
              result = files_fn_update_trangthai_hoancong_brn.data.data
              if (result == "OK") {
                this.$toast.success("Ghi nhận thành công")
              }
            }
          }
        }
        catch (err) {
          this.$toast.error(err.message);
        } finally {
          // await this.loading(false)
        }
      },

      async tsbtnGuiMailKH_Click() {
        let data = {
          hdtb_id: this.hdtb_id
        }
        document.getElementById("RegiSMSBrandname").parentElement.style.display = "none"
        this.$refs.frmBRNGuiMail.openDialog(data)
      },
    async GetFileURL(url) {
      return await this.axios.post(`/web-quantri/upload/link_view_file_v2`, { list_file: url });
    },
      async tsbtnUpdate_Click() {
        if (this.model.cboHoSo == "") {
          this.$toast.warning("Chọn file hồ sơ")
          return
        }
        for (var i = 0; i < this.model.cboHoSo.length; i++) {
          if (this.model.cboHoSo[i].file_path.indexOf('https://storage-onebss.vnpt.vn/onebss-production/') == -1) {
            var url = (await this.GetFileURL(this.model.cboHoSo[i].file_path)).data.data[0].file_url;
            this.model.cboHoSo[i].file_path = url;
          }
        }
          console.log("file_hs", this.model.cboHoSo);
        try {
          await this.loading(true)
          var json_UPDATEBRANDNAME = JSON.stringify({
            "label_id": this.label_id,
            "label": this.txtTenNhan,
            "label_type_vnp": Number(this.model.cboLoaiVina),
            "label_type_vms": Number(this.model.cboLoaiMobi),
            "label_type_vtl": Number(this.model.cboLoaiVtl),
            "label_type_gtel": Number(this.model.cboLoaiGtel),
            "label_type_vnm": Number(this.model.cboLoaiVnm),
            "label_type_itel": Number(this.model.cboLoaiItel),
            "label_type_mobicast": Number(this.model.cboLoaiMobicast),
            "customer": this.txtTenKhachHang,
            "taxCode": this.txtMaSoThue,
            "username": this.username,
            "expired_date": this.formatDate(this.getDate(this.dtpNgayHetHan), 'DD/MM/YYYY'),
            "note": this.txtGhiChu,
            "mobile_alias": this.txtThueBaoAo,
            "ds_file": JSON.parse(JSON.stringify(this.model.cboHoSo)),

            "gia_vnp": Number(this.nmGiaVina),
            "gia_vms": Number(this.nmGiaMobi),
            "gia_vtl": Number(this.nmGiaVtl),
            "gia_gtel": Number(this.nmGiaGtel),
            "gia_vnm": Number(this.nmGiaVnm),
            "gia_itel": Number(this.nmGiaItel),
            "gia_mbicast": Number(this.nmGiaMobicast)
          });
          var link_UPDATEBRANDNAME = `/${this.resource}/brandname/UPDATEBRANDNAME`
          const files_UPDATEBRANDNAME = await this.axios.post(link_UPDATEBRANDNAME, json_UPDATEBRANDNAME, {
            headers: {
              'Content-Type': 'application/json'
            }
          });
          debugger

          if (files_UPDATEBRANDNAME.data.data && files_UPDATEBRANDNAME.data.error_code == this.success_code) {
            var ds = files_UPDATEBRANDNAME.data.data
            var result = ds
         
          if (result.message != "Success") {
            this.$toast.error(ds.message + ". Liên hệ admin để được hỗ trợ")
            // this.SetButton(1)
            return
          }
          else {
            this.$toast.success(ds.message);
            // await this.axios.post('/web-ccdv/tram-thicong-dv-cntt/cap_nhat_status_hdtb', { vhdtb_id: this.hdtb_id, vstatus: 1 });
            this.LoadThongtinTrangthaiBRN()
            // this.SetButton(2);

          }
            // if (result != 200) {
            //   this.$toast.error(ds.message + ". Liên hệ admin để được hỗ trợ")
            //   this.SetButton(1)
            //   return
            // }
            // else {
            //   this.$toast.success(ds.message)
            //   this.LoadThongtinTrangthaiBRN()
            //   SetButton(2)
            // }
          }
        }
        catch (err) {
          this.$toast.error(err.message);
        } finally {
          await this.loading(false)
        }
      },

      SetButton(kieu) {
        if (kieu == 1) {
          this.enableButton("btnNhapMoi")
          this.disableButton("btnCapNhat")
          this.disableButton("tsbtnGuiMailKH")
          this.disableButton("tsbtnUpdate")
        }
        else {
          this.disableButton("btnNhapMoi")
          this.enableButton("btnCapNhat")
          this.enableButton("tsbtnGuiMailKH")
          this.enableButton("tsbtnUpdate")
        }
      },

      enableButton(ele) {
        let control_name = document.getElementById(ele)
        if (control_name != null) {
          if (control_name.classList.contains('non-active')) {
            control_name.classList.remove("non-active");
            control_name.classList.add("active");
          }
          document.getElementById(ele).style.pointerEvents = "auto"
        }
      },

      disableButton(ele) {
        let control_name = document.getElementById(ele)
        if (control_name != null) {
          if (control_name.classList.contains('active')) {
            control_name.classList.remove("active");
            control_name.classList.add("non-active");
          }
          document.getElementById(ele).style.pointerEvents = "none"
        }
      },

      async cboLoaiVina_EditValueChanged() {
        //debugger
        let value = Number(this.model.cboLoaiVina)
        var json_fn_lay_thongtin_gia_brn = JSON.stringify({
          "vagent_type_id": this.agent_type_id,
          "vcontract_type_id": this.contract_type_id,
          "vtelco_id": 1,
          "vlabel_type_id": value
        });
        var link_fn_lay_thongtin_gia_brn = "/web-quantri/sms-brandname/fn_lay_thongtin_gia_brn"
        const files_fn_lay_thongtin_gia_brn = await this.axios.post(link_fn_lay_thongtin_gia_brn, json_fn_lay_thongtin_gia_brn, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        if (files_fn_lay_thongtin_gia_brn.data.data && files_fn_lay_thongtin_gia_brn.data.error_code == this.success_code) {
          this.nmGiaVina = files_fn_lay_thongtin_gia_brn.data.data
        }
      },

      async cboLoaiMobi_EditValueChanged() {
        let value = Number(this.model.cboLoaiMobi)
        var json_fn_lay_thongtin_gia_brn = JSON.stringify({
          "vagent_type_id": this.agent_type_id,
          "vcontract_type_id": this.contract_type_id,
          "vtelco_id": 2,
          "vlabel_type_id": value
        });
        var link_fn_lay_thongtin_gia_brn = "/web-quantri/sms-brandname/fn_lay_thongtin_gia_brn"
        const files_fn_lay_thongtin_gia_brn = await this.axios.post(link_fn_lay_thongtin_gia_brn, json_fn_lay_thongtin_gia_brn, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        if (files_fn_lay_thongtin_gia_brn.data.data && files_fn_lay_thongtin_gia_brn.data.error_code == this.success_code) {
          this.nmGiaMobi = files_fn_lay_thongtin_gia_brn.data.data
        }
      },

      async cboLoaiVtl_EditValueChanged() {
        let value = Number(this.model.cboLoaiVtl)
        var json_fn_lay_thongtin_gia_brn = JSON.stringify({
          "vagent_type_id": this.agent_type_id,
          "vcontract_type_id": this.contract_type_id,
          "vtelco_id": 3,
          "vlabel_type_id": value
        });
        var link_fn_lay_thongtin_gia_brn = "/web-quantri/sms-brandname/fn_lay_thongtin_gia_brn"
        const files_fn_lay_thongtin_gia_brn = await this.axios.post(link_fn_lay_thongtin_gia_brn, json_fn_lay_thongtin_gia_brn, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        if (files_fn_lay_thongtin_gia_brn.data.data && files_fn_lay_thongtin_gia_brn.data.error_code == this.success_code) {
          this.nmGiaVtl = files_fn_lay_thongtin_gia_brn.data.data
        }
      },

      async cboLoaiVnm_EditValueChanged() {
        let value = Number(this.model.cboLoaiVnm)
        var json_fn_lay_thongtin_gia_brn = JSON.stringify({
          "vagent_type_id": this.agent_type_id,
          "vcontract_type_id": this.contract_type_id,
          "vtelco_id": 5,
          "vlabel_type_id": value
        });
        var link_fn_lay_thongtin_gia_brn = "/web-quantri/sms-brandname/fn_lay_thongtin_gia_brn"
        const files_fn_lay_thongtin_gia_brn = await this.axios.post(link_fn_lay_thongtin_gia_brn, json_fn_lay_thongtin_gia_brn, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        if (files_fn_lay_thongtin_gia_brn.data.data && files_fn_lay_thongtin_gia_brn.data.error_code == this.success_code) {
          this.nmGiaVnm = files_fn_lay_thongtin_gia_brn.data.data
        }
      },

      async cboLoaiGtel_EditValueChanged() {
        let value = Number(this.model.cboLoaiGtel)
        var json_fn_lay_thongtin_gia_brn = JSON.stringify({
          "vagent_type_id": this.agent_type_id,
          "vcontract_type_id": this.contract_type_id,
          "vtelco_id": 4,
          "vlabel_type_id": value
        });
        var link_fn_lay_thongtin_gia_brn = "/web-quantri/sms-brandname/fn_lay_thongtin_gia_brn"
        const files_fn_lay_thongtin_gia_brn = await this.axios.post(link_fn_lay_thongtin_gia_brn, json_fn_lay_thongtin_gia_brn, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        if (files_fn_lay_thongtin_gia_brn.data.data && files_fn_lay_thongtin_gia_brn.data.error_code == this.success_code) {
          this.nmGiaGtel = files_fn_lay_thongtin_gia_brn.data.data
        }
      },

      async cboLoaiItel_EditValueChanged() {
        let value = Number(this.model.cboLoaiItel)
        var json_fn_lay_thongtin_gia_brn = JSON.stringify({
          "vagent_type_id": this.agent_type_id,
          "vcontract_type_id": this.contract_type_id,
          "vtelco_id": 7,
          "vlabel_type_id": value
        });
        var link_fn_lay_thongtin_gia_brn = "/web-quantri/sms-brandname/fn_lay_thongtin_gia_brn"
        const files_fn_lay_thongtin_gia_brn = await this.axios.post(link_fn_lay_thongtin_gia_brn, json_fn_lay_thongtin_gia_brn, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        if (files_fn_lay_thongtin_gia_brn.data.data && files_fn_lay_thongtin_gia_brn.data.error_code == this.success_code) {
          this.nmGiaItel = files_fn_lay_thongtin_gia_brn.data.data
        }
      },

      async cboLoaiMobicast_EditValueChanged() {
        //debugger
        let value = Number(this.model.cboLoaiMobicast)
        var json_fn_lay_thongtin_gia_brn = JSON.stringify({
          "vagent_type_id": this.agent_type_id,
          "vcontract_type_id": this.contract_type_id,
          "vtelco_id": 8,
          "vlabel_type_id": value
        });
        var link_fn_lay_thongtin_gia_brn = "/web-quantri/sms-brandname/fn_lay_thongtin_gia_brn"
        const files_fn_lay_thongtin_gia_brn = await this.axios.post(link_fn_lay_thongtin_gia_brn, json_fn_lay_thongtin_gia_brn, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        if (files_fn_lay_thongtin_gia_brn.data.data && files_fn_lay_thongtin_gia_brn.data.error_code == this.success_code) {
          this.nmGiaMobicast = files_fn_lay_thongtin_gia_brn.data.data
        }
      },

      init_timer() {
        const timer = setInterval(() => {
          this.LoadThongtinTrangthaiBRN()
        }, 30000);

        this.$once("hook:beforeDestroy", () => {
          clearInterval(timer);
        });
      }
    },

    

    created() {
      //const timer = setInterval(() => {
      //  this.LoadThongtinTrangthaiBRN()
      //}, 100000);

      //this.$once("hook:beforeDestroy", () => {
      //  clearInterval(timer);
      //});
    },

    watch: {
      "model.cboLoaiVina": {
        handler(val) {
          this.cboLoaiVina_EditValueChanged()
        },
        deep: true
      },
      "model.cboLoaiMobi": {
        handler(val) {
          this.cboLoaiMobi_EditValueChanged()
        },
        deep: true
      },
      "model.cboLoaiVtl": {
        handler(val) {
          this.cboLoaiVtl_EditValueChanged()
        },
        deep: true
      },
      "model.cboLoaiVnm": {
        handler(val) {
          this.cboLoaiVnm_EditValueChanged()
        },
        deep: true
      },
      "model.cboLoaiGtel": {
        handler(val) {
          this.cboLoaiGtel_EditValueChanged()
        },
        deep: true
      },
      "model.cboLoaiItel": {
        handler(val) {
          this.cboLoaiItel_EditValueChanged()
        },
        deep: true
      },
      "model.cboLoaiMobicast": {
        handler(val) {
          this.cboLoaiMobicast_EditValueChanged()
        },
        deep: true
      }
    },

    mounted() {

    },
  }
</script>

<style>
  .text-rose {
    color: #FF007F !important;
  }

  /*.e-grid .e-rowcell {
    overflow: inherit !important;
  }*/

  .grid-thamso .e-checkbox-wrapper .e-stop::before,
  .grid-thamso .e-css.e-checkbox-wrapper .e-stop::before {
    content: '\e7a5' !important;
    background: #fff;
    color: #0176FF;
    border-color: #0176FF;
    font-size: 14px;
  }

  li.non-active a {
    color: #d3d3d3 !important;
  }

  div.non-active div {
    color: #d3d3d3 !important;
  }

  .multiselect, .multiselect__input, .multiselect__single {
    font-size: 1rem !important;
  }

  .multiselect__tags {
    min-height: 32px !important;
    border: 1px solid #ced4da !important;
    padding: 6px 32px 0 6px;
    border-radius: 4px;
  }

  .multiselect__placeholder {
    margin-bottom: 5px !important;
    padding-top: 0px !important;
  }

  .multiselect__select {
    top: 0px !important;
    padding: 10px 9px !important;
  }

  .multiselect--disabled {
    background: none !important;
  }

    .multiselect--disabled .multiselect__current, .multiselect--disabled .multiselect__select {
      background: none !important;
    }

  a.disabled {
    color: lightgray !important;
  }

    a.disabled:hover {
      color: lightgray !important;
    }

  .disabled {
    pointer-events: none;
  }

  a {
    cursor: pointer;
  }
</style>
