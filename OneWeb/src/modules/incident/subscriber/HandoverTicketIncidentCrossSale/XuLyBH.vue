<template src="./XuLyBH.html"></template>

<style>
.model-css {
  min-height: 700px !important;
}
</style>
 
<script>
import breadcrumb from "@/components/breadcrumb";
import DatePicker from "vue2-datepicker";
import BssErrorMarker from "@/components/BssErrorMarker";
import BssRequiredMarker from "@/components/BssRequiredMarker";
import moment from "moment";
import api from "./api";
import apiHelper from './api.helper'
import commonFn from "@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions";

import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
//CSS
import "@/assets/vendor/bootstrap-table/bootstrap-table.min.css";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css";
//JS
import "@/assets/vendor/jquery/split";
import "@/assets/vendor/bootstrap-table/bootstrap-table.min";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min";
import { required, minValue, maxValue } from "vuelidate/lib/validators";


export default {
  components: {
    breadcrumb,
    DatePicker,
    BssErrorMarker,
    BssRequiredMarker,
  },
  name: "HandoverTicketIncidentCrossSale",
  props: ["isPopup"],
  validations: {
    diachiLD: {
      DIACHI: {
        required,
      },
    },
  },
  async mounted() {

    this.tt_nd.may_cn = await this.$root.token.getMachine();
    this.tt_nd.ip_cn = await this.$root.token.getIP();
    if (!this.isPopup || this.isPopup == false)
      this.pageLoad();
  },

  data() {
    return {
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: "",
      popupComponentAttr: {},
      popupComponentEvts: {
        'form-close': this.popupClosed,
        'acceptChangeCTV': this.popupClosed,
        'acceptChangeNGT': this.popupClosed,
        'PopUpCongTacVien_Thoat': this.popupClosed,
      },
      header: {
        title: 'Giao phiếu phối hợp xử lý báo hỏng',
        list: [
          { name: 'Xử lý báo hỏng', link: { name: 'Ui.cards' } },
          {
            name: 'Giao phiếu phối hợp xử lý báo hỏng',
            link: { name: 'Ui.buttons' }
          }
        ]
      },

      selectionOptions: { type: "Multiple" },
      target: "#app",
      dateFormat: "DD/MM/YYYY",
      datetimeFormat: "DD/MM/YYYY HH:mm",
      tt_nd: {
        nhanvien_id: this.$root.token.getNhanVienID() * 1,
        nguoidung_id: this.$root.token.getNguoiDungID() * 1,
        phanvung_id: this.$root.token.getPhanVungID() * 1,
        donvi_id: this.$root.token.getDonViID() * 1,
        ngay_cn: moment(new Date()).format("DD/MM/YYYY hh:mm A"),
        ngay_bg: moment(new Date().setSeconds(new Date().getSeconds() + 30)).format(
          "DD/MM/YYYY hh:mm A"
        ),
        nguoi_cn: this.$root.token.getUserName(),
        ma_nd: this.$root.token.getUserName(),
        ma_tinh: this.$root.token.getMaTinh(),
      },
      dtpNgayBH: moment().format("DD/MM/YYYY hh:mm A"),
      txtMaTB: "",
      txtTenTB: "",
      txtDiaChiLD: "",
      txtNoiDung_Giao: "",
      txtTrangThaiTB: "",
      txtSoDT: "",
      txtGhiChuHong: "",
      state: {
        ma_lt: 0,
        loaidv_id: 0,
        baohong_id_par: 0,
        baohong_id: 0,
        phieu_id: 0,
        thuebao_id: 0,
        thuebao_id_tc: 0,
        tinh_id_tc: 0,
        quytrinh_id: 0,
        vdonvi_ql_id: 0,
        vdonvi_giao: "",
        dichvuvt_id: 0,
        loaitb_id: 0,
        ma_bh: "",
      },

      controls: {
        /* #region */
        tsbtnGiaoPhoiHop: {
          visible: true,
          enabled: true,
          type: "menu_button",
        },
        tsbtnHuyGiao: {
          visible: true,
          enabled: true,
          type: "menu_button",
        },
        tsbtnNghiemThu: {
          visible: true,
          enabled: true,
          type: "menu_button",
        },
        cboDichVuVT: {
          visible: true,
          enabled: true,
          type: "droplist",
          list: [],
          value: 0,
        },
        cboLoaiTB: {
          visible: true,
          enabled: true,
          type: "droplist",
          list: [],
          value: 0,
        },
        cboHuongGiao: {
          visible: true,
          enabled: true,
          type: "droplist",
          list: [],
          value: 0,
        },
        pceDonVi: {
          visible: true,
          enabled: true,
          type: "droplist",
          list: [],
          value: 0,
        },
        cboKQXL: {
          visible: true,
          enabled: true,
          type: "droplist",
          list: [],
          value: 0,
        },

        gridDanhSach: {
          headers: [
            {
              fieldName: 'ma_tb',
              headerText: 'Số máySố máy/Acc',
              allowFiltering: true,
              allowSorting: false,
              width: 100,
            },
            {
              fieldName: 'ma_bh',
              headerText: 'Mã báo hỏng',
              allowFiltering: true,
              allowSorting: true,
              width: 100,
            },
            {
              fieldName: 'ma_lt',
              headerText: 'Số ảo',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'loaihinh_tb',
              headerText: 'Loại hình',
              allowFiltering: true,
              allowSorting: true,
              width: 400,
            },
            {
              fieldName: 'ht_port',
              headerText: 'TT Port',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'tinhtrang',
              headerText: 'Tình trạng',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'ten_tb',
              headerText: 'Tên thuê bao',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'dienthoai_lh',
              headerText: 'Liên hệ',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'ngay_bh',
              headerText: 'Ngày BH',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'goi_kt',
              headerText: 'Gói KT"',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'chitieu_tg',
              headerText: 'Chỉ tiêu TG"',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'thoigian_ck',
              headerText: 'Thời gian CK',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'ten_ttvt',
              headerText: 'Trung tâm VT',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'tram_vt',
              headerText: 'Trạm VT',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'diachi_ld',
              headerText: 'Địa chỉ LĐ',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'ngay_sd',
              headerText: 'Ngày SD',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'ghichu_hong',
              headerText: 'Ghi chú hỏng',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'tt_ph',
              headerText: 'Trạng thái phiếu',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'ten_nv',
              headerText: 'Người nhận',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'dv_nhan',
              headerText: 'ĐV nhận',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'nd_tra',
              headerText: 'Nội dung trả',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'luot_bh',
              headerText: 'Lượt BH',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'lan_pa',
              headerText: 'KH gọi',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'ten_loaikh',
              headerText: 'Loại KH',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'ten_lkhl',
              headerText: 'KH lớn',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'cuoc_ps',
              headerText: 'Cước PS',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'ngaytra',
              headerText: 'Ngày trả',
              allowFiltering: true,
              allowSorting: true
            }
          ],
          dataSource: [],
          type: "gridview",
        },

        gridChitiet: {
          headers: [
            {
              fieldName: 'huonggiao',
              headerText: 'Huong Giao',
              allowFiltering: true,
              allowSorting: false,
              width: 100,
            },
            {
              fieldName: 'dvg',
              headerText: 'Đơn vị giao',
              allowFiltering: true,
              allowSorting: true,
              width: 100,
            },
            {
              fieldName: 'ngay_giao',
              headerText: 'Ngày giao',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'nv_giao',
              headerText: 'Người giao',
              allowFiltering: true,
              allowSorting: true,
              width: 400,
            },
            {
              fieldName: 'nd_giao',
              headerText: 'Nội dung giao',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'dvn',
              headerText: 'Đơn vị nhận',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'trangthai_ph',
              headerText: 'Trạng thái phiếu',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'ngay_tra',
              headerText: 'Ngày trả',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'nd_tra',
              headerText: 'Nội dung trả',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'lydotra',
              headerText: 'Lý do trả',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'ngay_th',
              headerText: 'Ngày CN',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'nv_th',
              headerText: 'Người thực hiện',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'so_dt',
              headerText: 'Điện thoại liên hệ',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'ghichu',
              headerText: 'Ghi chú',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'ketqua_xl',
              headerText: 'Kết quả XL',
              allowFiltering: true,
              allowSorting: true
            },
            {
              fieldName: 'chuongtrinh',
              headerText: 'Ứng dụng',
              allowFiltering: true,
              allowSorting: true
            },
          ],
          dataSource: [],
          type: "gridview",
        },

        /* #endregion */
      },
    };
  },
  computed: {

  },

  validations: {},
  methods: {
    async pageLoad() {
      try {
        this.loading(true)
        const tag = this.$route.query.Tag || this.$route.query.tag || "";
        this.state.baohong_id_par = this.$route.query.baohong_id_par || 0;

        let Tag = tag ? decodeURIComponent(tag.toString()) : "";

        if (Tag != "") {
          if (Tag.toString() != "")
            if (Tag.toString().indexOf("+") > 0) {
              let words = Tag.toString().split('+');
              if (words.Length >= 1)
                this.state.loaidv_id = Number(words[0].toString());
            }
            else
              this.state.loaidv_id = Number(Tag);
        }
        this.controls.cboDichVuVT.list = this.GetData(await api.sp_ht_dichvuvt_all(this.axios, {})).map((x) => ({ id: x.dichvuvt_id, text: x.ten_dvvt }));
        if (this.controls.cboDichVuVT.list && this.controls.cboDichVuVT.list.length > 0) this.controls.cboDichVuVT.value = this.controls.cboDichVuVT.list[0].id
        this.controls.cboKQXL.list = [{ id: 0, text: "Đã xử lý xong" }, { id: 1, text: "Không xử lý được" }, { id: 2, text: "Đang chờ xử lý" }];
        this.controls.cboKQXL.list.value = 0;
        await this.HienThi_DanhSach_BaoHong();

      } catch (ex) {
        commonFn.showException(this, ex);
      }
      this.loading(false);
    },

    async btnLayTTMoi_Click() {
      try {
        this.loading(true)
        await this.HienThi_DanhSach_BaoHong();

      } catch (ex) {
        commonFn.showException(this, ex);
      }
      this.loading(false);
    },

    async HienThi_DanhSach_BaoHong() {
      let vttph_id = 1;
      let vkyhoadon = moment().format("YYYYMM") + "01";
      let req = {
        vdonvi_id: this.tt_nd.donvi_id,
        vnhanvien_id: this.tt_nd.nhanvien_id,
        vttph_id: vttph_id,
        vkyhoadon: vkyhoadon,
        vbaohong_id: this.state.baohong_id_par
      }

      let dtBH = this.GetData(await api.lay_ds_tb_baohong_chua_nt_v3(this.axios, req));
      // console.log("dtBH", dtBH);
      if (!dtBH) return;

      this.controls.gridDanhSach.dataSource = dtBH;
    },

    async gridviewDanhSach_FocusedRowChanged(data) {

      if (!data) return;

      this.state.thuebao_id = data.thuebao_id;
      this.state.thuebao_id_tc = data.thuebao_id_thicong;
      this.state.tinh_id_tc = data.tinh_thicong;
      this.state.baohong_id = data.baohong_id;
      this.state.phieu_id = data.phieu_id;
      this.state.ma_bh = data.ma_bh;
      // this.state.trangthaitb_id = (await api.getTrangThaiThueBao(this.axios,this.state.thuebao_id))?.data.data;
      this.state.quytrinh_id = data.quytrinh_id;
      this.state.vdonvi_ql_id = data.donvi_nhan_id;
      this.state.vdonvi_giao = data.dv_giao;

      this.state.dichvuvt_id = data.dichvuvt_id;

      this.state.loaitb_id = data.loaitb_id;
      this.state.ma_lt = data.ma_lt;
      this.controls.cboDichVuVT.value = this.state.dichvuvt_id;
      this.controls.cboLoaiTB.list = this.GetData(await api.lay_ds_loaihinh_thuebao_dichvu(this.axios, { params: { dichVuVtId: this.state.dichvuvt_id } })).map((x) => ({ id: x.ID, text: x.NAME }));

      if (this.controls.cboLoaiTB.list && this.controls.cboLoaiTB.list.length > 0)
        this.controls.cboLoaiTB.value = this.controls.cboLoaiTB.list[0].id;

      this.controls.cboLoaiTB.value = this.state.loaitb_id;
      this.txtMaTB = data.ma_tb;
      this.controls.cboLoaiTB.value = data.loaitb_id;
      this.txtTenTB = data.ten_tb;
      this.txtDiaChiLD = data.diachi_ld;
      this.txtSoDT = data.dienthoai_lh;
      this.txtTrangThaiTB = data.trangthai_bh;

      this.state.ma_bh = data.ma_bh;
      this.state.dac_trung = data.dactrung;

      await this.HienThi_CT_BH();

      await this.lay_ds_huonggiao_baohong_v2({ vquytrinh_id: this.state.quytrinh_id, vloaidv_id: this.state.loaidv_id, tinhthicong_id: this.$root.token.getPhanVungID() });

      await this.Load_DV_PH(0);
    },

    async lay_ds_huonggiao_baohong_v2({ vquytrinh_id, vloaidv_id, tinhthicong_id }) {
      const response = await api.Lay_ds_huonggiao_baohong_v2(this.axios, { vquytrinh_id, vloaidv_id, tinhthicong_id })
      this.controls.cboHuongGiao.list = (this.GetData(response) || []).map((x) => ({ id: x.huonggiao_id, text: x.huonggiao, ttbh_id: x.ttbh_id }));

      if (this.controls.cboHuongGiao.list.length > 0)
        this.controls.cboHuongGiao.value = this.controls.cboHuongGiao.list[0].id;
      else
        this.controls.cboHuongGiao.value = 0;

    },

    async cboHuongGiao_SelectedValueChanged() {
      await this.Load_DV_PH(0);
    },

    async Load_DV_PH(vdonvi_cha_id) {
      if (this.state.quytrinh_id != 0) {
        const param = {
          "vquytrinh_id": this.state.quytrinh_id,
          "vhuonggiao_id": this.controls.cboHuongGiao.value,
          "vdonvi_cha_id": vdonvi_cha_id ? vdonvi_cha_id : 0,
        }
        let listDV = this.GetData(await api.lay_ds_donvi_baohong(this.axios, param)) || [];
        const huongiao_id = this.controls.cboHuongGiao.value;

        this.controls.pceDonVi.list = listDV.map((x) => ({ id: x.donvi_id, text: x.ten_dv, tinh_id: x.tinh_id }));

        if ((huongiao_id == 7779 || huongiao_id == 7785 || huongiao_id == 7791) && this.state.tinh_id_tc != 0) {

          const tinhTC = this.controls.pceDonVi.list.find(e => e.tinh_id == this.state.tinh_id_tc);
          this.controls.pceDonVi.value = tinhTC?.id;
          this.controls.pceDonVi.enabled = false;
        } else {

          this.controls.pceDonVi.value = this.controls.pceDonVi.list[0]?.id || 0
          this.controls.pceDonVi.enabled = true;
        }

      }
    },


    async HienThi_CT_BH() {
      try {
        this.loading(true);
        const req = {
          vbaohong_id: this.state.baohong_id,
          vnhanvien_id: this.tt_nd.nhanvien_id
        }
        const response =  await api.lay_danhsach_phieuth_bh_net(this.axios, req)
        this.controls.gridChitiet.dataSource = apiHelper.getDataFromResponseApiReturnArray(response)
      }catch (ex) {
       console.log(ex)
      }finally{
        this.loading(false);
      }
     
      
    },

    async tsbtnGiaoPhieu_Click() {
      try {

        let lstDaChon = this.$refs.gridDanhSach.getSelectedRecords();
        if (lstDaChon.length <= 0) {
          this.$toast.error("Bạn chưa chọn thuê bao! ");
          return false;
        }

        this.loading(true);
        let strError = "";


        for (let i = 0; i < lstDaChon.length; i++) {
          const item = lstDaChon[i];

          //pceDonVi.Visible 
          if (this.controls.cboHuongGiao.value == 7781 || this.controls.cboHuongGiao.value == 7793) {
            strError = await this.handeGiaoPhieuDonVi_PceDonViVisible(item);
          } else {
            strError = await this.handeGiaoPhieuDonVi_PceDonViV2(item);
          }
        }

        if (strError != "") {
          this.loading(false);
          this.$toast.error(strError);
          return;
        } else {
          this.$toast.success("Giao phiếu thành công.");
          await this.HienThi_DanhSach_BaoHong();
          this.loading(false);
          return;
        }

      } catch (ex) {
        this.loading(false);
        commonFn.showException(this, ex);
      }

    },

    async handeGiaoPhieuDonVi_PceDonViVisible(rowItem) {
      let strError = '';
      const { ma_bh } = item;

      if(!ma_bh.includes('KHDN')){
        strError = await this.handleGiaoPhieuBaoHong_Tinh(rowItem, strError);
        return strError;
      }

      //check giao phieu
      const checkGiaoPhieu = await this.kiemTraGiaoPhieuBH({ vbaohong_id: rowItem.baohong_id, vhuonggiao_id: this.controls.cboHuongGiao.value, vdonvi_nhan_id: this.controls.pceDonVi.value })
      if (checkGiaoPhieu) {
        strError = 'Đã giao phiếu cho đơn vị đã chọn!'
        return strError;
      }
      //thay theo comment cua a Van
      strError = await this.handleGiaoPhieu_BaoHong_V2(rowItem, strError);
      return strError

    },

    async handeGiaoPhieuDonVi_PceDonViV2(rowItem) {

      let strError = '';
      const vtinh_tc_id = rowItem.tinh_thicong;
      const vbaohong_id = rowItem.baohong_id;
      const vloaitb_id = rowItem.loaitb_id;
      const vthuebao_id_thicong = rowItem.thuebao_id_thicong;

      console.log(rowItem);
      //call check giao phieu: dtPhieuDaGiao
      const checkGiaoPhieu = await this.kiemTraGiaoPhieuBH({ vbaohong_id, vhuonggiao_id: this.controls.cboHuongGiao.value, vdonvi_nhan_id: this.controls.pceDonVi.value })
      if (checkGiaoPhieu) {
        strError = 'Đã giao phiếu cho đơn vị đã chọn!'
        return strError;
      }

      if (this.controls.cboHuongGiao.value == 7779 || this.controls.cboHuongGiao.value == 7785 || this.controls.cboHuongGiao.value == 7791) {

        const checkGiaoTinh = await this.kiemTraGiaoTinh(vbaohong_id)

        if (!checkGiaoTinh) {
          strError = 'Đã giao Tỉnh!'
          return strError
        }

        //check da chuyen tinh: call vSqlPhieuDaChuyenTinh
        const checkChuyenTinh = await this.kiemtraBaoHongBCChuyenTinh(vbaohong_id);

        if (checkChuyenTinh) {
          strError = 'Đã giao phiếu cho đơn vị đã chọn!';
          return strError
        }

        //tao du TaoDuLieu_BaoHong 
        const dataBH = await this.createBaoHongData(rowItem);
        const { dsBH_DATA, dsBH_BC_DATA } = dataBH;
        const baohong_id_tc = dsBH_DATA.BAOHONG_ID;

        if (dsBH_DATA != null) {
          //call SP_UPDATE_BAOHONG_BC_BAOHONG_ID
          const response = await api.SP_UPDATE_BAOHONG_BC_BAOHONG_ID(this.axios, { vbaohong_id_tc: baohong_id_tc, vbaohong_id_ban: vbaohong_id })

          if (response && response.data && response.data.error_code !== 'BSS-00000000') {
            return response.data.message_detail || response.data.message
          }
        }

        // new BaoHong().Nhan_hong(tinh_tc, dsBH_DATA, dsBH_BC_DATA, loaitb_id, 1, dac_trung, tt_nd.may_cn, tt_nd.ngay_cn, tt_nd.ma_nd, thuebao_id_tc, vbaohong_id, 0);
        const paramTiepNhanBH = {
          tinh_tc: vtinh_tc_id,
          vloaitb_id: vloaitb_id,
          vthutu: 1,
          vthuebao_id: vthuebao_id_thicong,
          vbaohong_id: vbaohong_id,
          dactrung: rowItem.dac_trung,
          net_giao: 0, // 0 ||1
          ds: [dsBH_DATA],
          dsBH_BC: [dsBH_BC_DATA],
          // tinhthicong_id: this.tt_nd.phanvung_id,
        }

        //Nhan_hong
        strError += await apiHelper.tiepNhanBaoHong(this.axios, paramTiepNhanBH)
        //call SP_UPDATE_BAOHONG_BC_TRANGTHAI
        strError += await apiHelper.updateTrangThaiBH_BC(this.axios, vbaohong_id);


        if (baohong_id_tc != 0)
          //call BANCHEO_SMS_BAOHONG
          await api.bancheo_sms_baohong(this.axios, { vbaohong_id: baohong_id_tc, vphanvung_id: vtinh_tc_id })

        if (vthuebao_id_thicong) {
          strError += await this.handeGiaoPhieuDonViTiepTheo({
            tinhthicong_id: vtinh_tc_id,
            vbaohong_id: baohong_id_tc,
            vloaitb_id: vloaitb_id,
            vthuebao_id: vthuebao_id_thicong,

          }, strError)
        }

      } else {
        //call GiaoPhieu_BH
        strError = await this.handleGiaoPhieu_BaoHong_V2(rowItem, strError)
      }

      return strError;
    },


    async handleGiaoPhieuBaoHong_Tinh(row, strError) {

      if (row) return;

      const param = {
        vtinh_id: this.tt_nd.phanvung_id,
        vdonvi_nhan_id: this.controls.pceDonVi.value,
        vten_dv_giao: "",
        vphieu_id: row.phieu_id,
        vbaohong_id: row.baohong_id,
        vten_nv: this.tt_nd.nhanvien_id,
        vnd_giao: this.txtNoiDung_Giao,
        vngay_cn: moment().format("DD/MM/YYYY HH:mm:ss"),
        vma_nd: this.tt_nd.ma_nd,
        vmay_cn: this.tt_nd.may_cn,
        vip_cn: this.tt_nd.ip_cn
      }

      const response = await api.giaophieu_baohong_ph_tinh(this.axios, param);
      if (response && response.data && response.data.error_code !== 'BSS-00000000') {
        strError += response.data.message_detail || response.data.message
      }

      return strError;

    },

    async handleGiaoPhieu_BaoHong(row, strError) {
      // new BaoHong().GiaoPhieu_BH();

      const param = {
        vbaohong_id: row.baohong_id,
        vdonvi_giao_id: this.tt_nd.donvi_id,
        vdonvi_nhan_id: this.controls.pceDonVi.value,
        vhuongiao_id: this.controls.cboHuongGiao.value,
        vma_nd: this.tt_nd.ma_nd,
        vnd_giao: this.txtNoiDung_Giao,
        vphieu_cha_id: row.phieu_id,
        vttbh_id: 1
      }

      const response = await api.giaophieu_bh(this.axios, param);
      if (response && response.data && response.data.error_code !== 'BSS-00000000') {
        strError += response.data.message_detail || response.data.message
      }

      return strError
    },

    async handleGiaoPhieu_BaoHong_V2(row, strError) {

      const cbHG = this.controls.cboHuongGiao.list.find(e => e.id == this.controls.cboHuongGiao.value);
      const ttbh_id = cbHG?.ttbh_id;

      const param = {
        tinhthicong_id: this.$root.token.getPhanVungID(),
        vbaohong_id: row.baohong_id,
        nhanvien_id: this.tt_nd.nhanvien_id,
        vdonvi_giao_id: this.tt_nd.donvi_id,
        vdonvi_nhan_id: this.controls.pceDonVi.value,
        vhuongiao_id: this.controls.cboHuongGiao.value,
        vma_nd: this.tt_nd.ma_nd,
        vnd_giao: this.txtNoiDung_Giao,
        vphieu_cha_id: row.phieu_id,
        vttbh_id: ttbh_id,
        vngay_cn: moment().format("DD/MM/YYYY HH:mm:ss")
      }

      const response = await api.giaoPhieuBaoHong_V2(this.axios, param);
      if (response && response.data && response.data.error_code !== 'BSS-00000000') {
        strError += response.data.message_detail || response.data.message
      }

      return strError
    },

    async handeGiaoPhieuDonViTiepTheo({ tinhthicong_id, vbaohong_id, vloaitb_id, vthuebao_id }, strError) {

      const vnhanvien_id = apiHelper.getDataFromResponseApiReturnRaise(await api.getNhanVienId(this.axios, tinhthicong_id));
      const vdonvi_id = apiHelper.getDataFromResponseApiReturnRaise(await api.getDonViId(this.axios, tinhthicong_id));

      const paramBCDVTT = {
        vbaohong_id,
        vloaitb_id,
        vthuebao_id,
        donvi_id: vdonvi_id,
        nhanvien_id: vnhanvien_id,
        tinhthicong_id
      }
      const response = await api.giaoPhieuDonViTiepTheo_BC(this.axios, paramBCDVTT);

      if (response && response.data && response.data.error_code !== 'BSS-00000000') {
        strError += response.data.message_detail || response.data.message
      }

      return strError
    },

    async tsbtnHuyGiao_Click() {
      try {

        if (this.controls.gridChitiet.dataSource.length == 0) {
          this.$toast.warning("Không có phiếu để hủy giao!");
          return;
        }

        let lstDaChon = this.$refs.gridChitiet.getSelectedRecords();

        if (lstDaChon.length <= 0) {
          this.$toast.warning("Bạn chưa chọn thuê bao! ");
          return;
        }

        let row = lstDaChon[0];
        let vphieu_id = row.phieu_id;
        let nguoi_cn = row.nguoi_cn;
        if (nguoi_cn != this.tt_nd.ma_nd) {
          this.$toast.warning("Bạn không phải người tạo phiếu! Không được hủy phiếu!");
          return;
        }


        //doing
        const response = await api.fn_bancheo_huygiao_xuly_baohong(this.axios, { vphanvung_id: this.tt_nd.phanvung_id, vphieu_id: vphieu_id, vma_bh: this.state.ma_bh });

        if (response && response.data && response.data.error_code === 'BSS-00000000') {

          this.loading(false);

          if (response.data.data != 'ok') {
            this.$toast.error(response.data.data);
            return;
          }


          this.$toast.success("Hủy giao phiếu thành công !");
          await this.HienThi_CT_BH();
        } else {
          this.loading(false);
          this.$toast.error(response.data.message_detail || response.data.message);
        }
      } catch (ex) {
        this.loading(false);
        commonFn.showException(this, ex);
      }
    },



    async tsbtnNghiemThu_Click() {
      try {
        let lstDaChon = this.$refs.gridDanhSach.getSelectedRecords();
        if (lstDaChon.length <= 0) {
          this.$toast.error("Bạn chưa chọn thuê bao! ");
          return false;
        }
        this.loading(true);

        let req = lstDaChon.map((x) => {
          return {
            vphanvung_id: this.tt_nd.phanvung_id,
            vttph_id: this.controls.pceDonVi.value,
            vttph_id: 4,
            vphieu_id: x.phieu_id,
            vct_hong: "",
            vkqxl_id: this.controls.cboKQXL.list.value,
            vghichu: this.txtGhiChuHong,
            vmay_cn: this.tt_nd.may_cn,
            vnhanvien_nt_id: this.tt_nd.nhanvien_id,
            vnguoi_cn: this.tt_nd.ma_nd,
          }
        });

        let data = this.GetData(await api.bancheo_hoanthanh_baohong_v3(this.axios, req[0]));
        this.$toast.success(data);
        this.Clear();
        await this.HienThi_DanhSach_BaoHong();

      } catch (ex) {
        commonFn.showException(this, ex);
      }
      finally {
        this.loading(false);
      }
    },

    async kiemTraGiaoTinh(vbaohong_id) {

      //call api trang thai
      const response = await api.getBHId_TC(this.axios, vbaohong_id);
      const trang_thai_bc = response?.data.data || '-1';
      if (trang_thai_bc == 3 || trang_thai_bc == 7) {
        this.$toast.error("Đã giao về TTVT");
        return false;
      }
      if (trang_thai_bc == 4) {
        this.$toast.error("Đã giao về OMC");
        return false;
      }
      if (trang_thai_bc == 1) {
        this.$toast.error("Tỉnh đã nghiệm thu");
        return false;
      }

      return true;

    },

    async kiemTraGiaoPhieuBH({ vbaohong_id, vhuonggiao_id, vdonvi_nhan_id }) {
      const response = await api.getGiaoPhieuBaoHongTheoBHid(this.axios, { vbaohong_id, vhuonggiao_id, vdonvi_nhan_id });
      if (response && response.data && response.data.error_code === 'BSS-00000000') {
        return response.data.data?.length > 0 || false;
      }
      return false;
    },

    async kiemtraBaoHongBCChuyenTinh(vbaohong_id) {
      const response = await api.kiemtraBaoHongBCChuyenTinh(this.axios, vbaohong_id);
      if (response && response.data && response.data.error_code === 'BSS-00000000') {
        return response.data.data?.length > 0 || false;
      }
      return false;
    },
    //TaoDuLieu_BaoHong
    async createBaoHongData(baoHongInfo) {
      const dsBH_DATA = await this.createDsBH_DATA(baoHongInfo)
      const dsBH_BC_DATA = await this.createDsBH_BC_DATA(baoHongInfo, dsBH_DATA.BAOHONG_ID)
      return { dsBH_DATA, dsBH_BC_DATA }
    },

    async createDsBH_DATA(baoHongRow) {
      if (!baoHongRow) return null;

      let data = {};

      const { ma_bh } = baoHongRow;

      const baohong_id_tc = await apiHelper.getKeyBAOHONG_BC(this.axios, baoHongRow.tinh_thicong);
      // const baohong_id_tc = keyBH;

      data.BAOHONG_ID_HNI = baoHongRow.baohong_id;
      data.BAOHONG_ID = baohong_id_tc;
      data.THUEBAO_ID = baoHongRow.thuebao_id_thicong;

      data.DICHVUVT_ID = this.controls.cboDichVuVT.value;
      data.MA_LT = baoHongRow.ma_lt
      data.LOAITB_ID = baoHongRow.loaitb_id;
      data.NGAY_BH = baoHongRow.ngay_bh;
      data.NGUOI_BH = baoHongRow.nguoi_bh;

      data.NGUOI_BH = baoHongRow.nguoi_bh;
      data.DIENTHOAI_LH = this.txtSoDT || "";
      data.GHICHU_HONG = this.txtGhiChuHong || "";
      // row.HT_PORT = s == "OK" ? "" : s;   //check lại 
      // row.CHITIEU_TG = Baohong.lay_chitieu_tg(thuebao_id, -1); //check lại

      const goiKtId = await apiHelper.layGoiKTId(this.axios, { vthuebao_id: baoHongRow.thuebao_id, vhdtb_id: -1 })

      data.GOIKT_ID = goiKtId;

      data.TTBH_ID = 1;

      const quytrinhId = await apiHelper.layHuongGiaoTheoThuTu(this.axios, { VPHANVUNG_ID: this.$root.token.getPhanVungID(), VLOAITB_ID: baoHongRow.loaitb_id, VTHUTU: 1 })

      data.QUYTRINH_ID = quytrinhId || 0;
      data.KENHTN_ID = 2;
      data.MA_BH = ma_bh;

      data.TEN_TB = this.txtTenTB;
      data.DIACHI_LD = this.txtDiaChiLD;

      data.MAY_CN = await this.$root.token.getMachine();
      data.NGAY_CN = moment(new Date()).format("DD/MM/YYYY");
      data.NGUOI_CN = this.$root.token.getUserName();
      // data.NHANVIEN_ID = this.$root.token.getNhanVienID();
      // data.DONVI_ID = this.$root.token.getDonViID();

      return data;
    },

    async createDsBH_BC_DATA(baoHongRow, baohong_id_tc) {
      if (!baoHongRow) return null;
      let dataBC = {};
      dataBC.LOAIHD_BANCHEO = 2;

      dataBC.THUEBAO_ID_BAN = baoHongRow.thuebao_id;
      dataBC.MA_TB = this.txtMaTB;
      dataBC.THUEBAO_ID_THICONG = baoHongRow.thuebao_id_thicong;

      dataBC.TINH_BAN = this.$root.token.getPhanVungID();

      dataBC.TINH_THICONG = baoHongRow.tinh_thicong;
      dataBC.TRANG_THAI = 0;
      dataBC.BAOHONG_ID = baoHongRow.baohong_id;

      dataBC.BAOHONG_ID_THICONG = baohong_id_tc;

      return dataBC;
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
    },
    Clear() {
      this.txtMaTB = "";
      this.txtTenTB = "";
      this.txtDiaChiLD = "";
      this.txtSoDT = "";

      this.state.vdonvi_id = "";
      this.state.vten_dv = "";

      this.state.vdonvi_ql_id = 0;
      this.state.quytrinh_id = 0;
      this.state.baohong_id = 0;
      this.state.tinh_id_tc = 0;
      this.state.thuebao_id = 0;
      this.state.thuebao_id_tc = 0;
      this.state.trangthaitb_id = 0;
    },
    async popupClosed(val) {
      console.log("popupClosed");
      if (!val) {
        this.popupRetVal = {};
        this.ClosePopup('popupComponents');
        return;
      }
      this.ClosePopup('popupComponents');
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return null
    },
  },

};
</script>
