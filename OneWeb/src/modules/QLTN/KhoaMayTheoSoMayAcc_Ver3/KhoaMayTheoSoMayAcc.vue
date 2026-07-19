<template src='./KhoaMayTheoSoMayAcc.html'></template>


<script>

  import { mapActions, mapGetters } from 'vuex'
  import Vue from 'vue'
  import moment from 'moment'
  import breadcrumb from '@/components/breadcrumb'
  import CommonsAPI from '../api/Commons.js'
  import KhoaMayAcc from '../api/KhoaMayTheoSoMayAcc.js'
  import { Query } from '@syncfusion/ej2-data'
  import { DialogUtility } from '@syncfusion/ej2-popups'

  Vue.use(DialogUtility)
  let DialogObj = undefined

  import {
    TrangThaiTB,
    DichVuVienThong,
    LoaiHinhTB
  } from "@/const/enums"

  export default Vue.extend({
    components : { breadcrumb},
    async created () {
      if (this.danhSachDichVuVienThongGetter === undefined
        || this.danhSachDichVuVienThongGetter.length === 0) {
            await this.getDanhSachDichVuVienThong();
        }
        this.params.p_dichvu_options = this.danhSachDichVuVienThongGetter
        this.params.p_dichvu = DichVuVienThong.CO_DINH
        // await this.getLoaiHinhTB(this.params.p_dichvu)
        await this.getDSLyDoKhoa()
        //await this.getDiemTinNhiem()
        await this.getKyCuocHienHanh()

        this.params.p_chieukhoa_options = [{ id: "1", text : "Khóa 1 chiều do nợ cước"},{ id: "2", text : "Khóa 2 chiều do nợ cước"}]
        this.params.p_chieukhoa = "1"

        this.params.p_kycuoc = moment(new Date()).subtract(1,'months').endOf('month').format('YYYYMM') + '01'
    },
    computed : {
      ...mapGetters('qltnCommon', ['danhSachDichVuVienThongGetter']),
    },
    data () {
      return {
        fieldsDichVu: {text: 'TEN_DVVT', value: 'DICHVUVT_ID' },
        fieldsLoaiHinh: {text: 'TEN_LOAIHINH', value: 'LOAITB_ID' },
        fieldsLyDoKhoa: {text: 'NOIDUNG', value: 'LYDOTRA_ID' },
        params: {
          p_kycuoc: null,
          p_khachhang_id: null,
          p_thanhtoan_id: null,
          p_thuebao_id: null,
          p_dichvu: null,
          p_dichvu_options : [],
          p_somay_acc : null,
          p_ten_tb: null,
          p_loaihinh_tb_options: [],
          p_loaihinh_tb: null,
          p_loaihinh: null,
          p_diem_tinnhiem: null,
          p_ma_tt: null,
          p_ten_tt: null,
          p_chieukhoa_options: [],
          p_chieukhoa: null,
          p_lydokhoa_options: [],
          p_lydokhoa: null,
          p_ma_PLKH: null,
          p_sdt_nhacno: null,
          p_ghichu: null,
          p_trangthai_tb: null,
          p_trangthai_tb_id: null,
          p_chk_thuhoi : '0',
          p_tongno: 0,
          KHOAMAY_LOAINV_DIEMTN : -1,
          KHOAMAY_TB_THEO_TUYENTHU : -1,
          KHOAMAY_KIEMTRA_KHIEUNAI: -1,
          HNI_GIAOPHIEU_GDV: -1,
          LAY_GHICHU_NHAC_LAM_LYDOKHOA: -1,
          KHONG_KHOAMAY_MOTCHIEU: -1,
          p_trangthai : 0 //0: Trạng thái có thể cho khóa thuê bao, 1: Thuê bao thuộc khách hàng đặc biệt tháng, 2: Thuê bao thuộc danh sách không cho phép khóa
        }
      }
    },
    methods: {
        ...mapActions('qltnCommon', ['getDanhSachDichVuVienThong']),
      async getLoaiHinhTB(id, selected = null) {
        let v_selected = null
        if(selected)
          v_selected = selected
        // else if(this.params.p_loaihinh !== null)
        //   v_selected = this.params.p_loaihinh
        this.loading(true)
        try {
          await CommonsAPI.getDanhSachLoaiHinhThueBao(this.axios, id).then(
            (res) => {
              if (res.data.error_code === 'BSS-00000000'
                && res.data.data !== undefined
                && res.data.data.length > 0) {
                this.params.p_loaihinh_tb_options = res.data.data
                if(v_selected)
                  this.params.p_loaihinh_tb = v_selected.toString()
                else
                  this.params.p_loaihinh_tb = this.params.p_loaihinh_tb_options[0].LOAITB_ID
              } else {
                this.params.p_loaihinh_tb_options = []
                this.params.p_loaihinh_tb = null
              }
            }
          )
        }
        catch (error) {
          console.log(error);
        }finally{
          this.loading(false)
        }
      },
      async getDSLyDoKhoa() {
        try {
            let response = await CommonsAPI.getDanhSachLyDoKhoa(this.axios);
            if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
              this.params.p_lydokhoa_options = response.data.data
              this.params.p_lydokhoa = response.data.data[0].LYDOTRA_ID
            }
        }
        catch (error) {
            console.log(error);
        }
        finally {
        }
      },
      async getDiemTinNhiemKhachHang() {
        try {
          let postData = {
            pKyCuoc: this.params.p_kycuoc,
            pKhachHangId: this.params.p_khachhang_id
          }
          let response = await KhoaMayAcc.getDiemTinNhiemKhachHang(this.axios, postData);
          if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
            this.params.p_diem_tinnhiem = response.data.data[0].DIEMTINNHIEM
            this.params.p_ma_PLKH = response.data.data[0].MA_PLKH
          } else
          {
            this.params.p_diem_tinnhiem = "0"
            this.params.p_ma_PLKH = ""
          }
        }
        catch (error) {
          console.log(error);
          this.params.p_diem_tinnhiem = "0"
          this.params.p_ma_PLKH = ""
        }
        finally {
        }
      },
      async getLyDoKhoa() {
        try {
          let postData = {
            pThanhToanId: this.params.p_thanhtoan_id
          }
          let response = await KhoaMayAcc.getLyDoKhoa(this.axios, postData);
          if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
            let ret = this.params.p_lydokhoa_options.filter( (x => x.NOIDUNG ===  response.data.data))
            if(ret !== null & ret.length > 0)
            {
              this.params.p_lydokhoa = ret.LYDOTRA_ID
            } else
              this.params.p_lydokhoa = null
          } else
          {

          }
        }
        catch (error) {
          console.log(error);
          this.params.p_diem_tinnhiem = "0"
          this.params.p_ma_PLKH = ""
        }
        finally {
        }
      },

      async getISDNTheoThueBao(thuebao_id) {
        try {
          let response = await KhoaMayAcc.getDSISDNTheoThueBao(this.axios, { pThueBaoId: thuebao_id});
          if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
            console.log(response.data.data)
          }
        }
        catch (error) {
          console.log(error);
        }
        finally {
        }
      },
      onFilteringDichVu(e){
        var query = new Query();
        query = (e.text !== '') ? query.where('TEN_DVVT', 'contains', e.text, true) : query;
        e.updateData(this.params.p_dichvu_options, query);
      },
      onFilteringLoaiHinh(e){
        var query = new Query();
        query = (e.text !== '') ? query.where('TEN_LOAIHINH', 'contains', e.text, true) : query;
        e.updateData(this.params.p_loaihinh_tb_options, query);
      },
      onFilteringLyDoKhoa(e){
        var query = new Query();
        query = (e.text !== '') ? query.where('NOIDUNG', 'contains', e.text, true) : query;
        e.updateData(this.params.p_lydokhoa_options, query);
      },
      onDichVuChange: function(args){
        this.getLoaiHinhTB(args.value)
      },
      onSearch: function() {

        if(this.params.p_somay_acc === null
          || this.params.p_somay_acc.trim() === ''){
            this.$toast.error('Trường Số máy/ account bắt buộc nhập.')
            this.$refs.p_somay_acc.focus()
            return false
          }
        this.params.p_somay_acc = this.params.p_somay_acc.trim()

        let postData = {
          maTB: this.params.p_somay_acc,
          donViDLId: 0
        }

        this.loading(true)
        CommonsAPI.getDanhBaTheoMaThueBao(this.axios, {maTB : this.params.p_somay_acc})
        //KhoaMayAcc.getDSTheoMaTB(this.axios, postData)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000'
                && response.data.data !== undefined
                && response.data.data.length > 0)
            {
              // console.log(response.data.data)
              if (response.data.data.length == 1) {
                this.setThongTinTB(response.data.data[0])

              } else {
                this.$toast.error('Chức năng frmTraCuu_ThueBao đang được phát triển')
              }
              // this.params.p_nhanvienthu_options = response.data.data
              // this.params.p_nhanvienthu = this.params.p_nhanvienthu_options[0]
            } else
            {
              this.$toast.error('Không có dữ liệu, vui lòng thử lại.')
              this.clearForm()
            }
          }
        )
        .catch(error => {
          console.log(error)
        })
        .finally(() => {
          this.loading(false)
        })
      },
      clearForm()
      {
        this.params.p_somay_acc = ''
        this.params.p_diem_tinnhiem = 0
        this.params.p_khachhang_id = null
        this.params.p_thanhtoan_id = null
        this.params.p_thuebao_id = null
        //this.params.p_loaihinh_tb = null
        this.params.p_loaihinh = null
        this.params.p_dichvu = DichVuVienThong.CO_DINH
        this.params.p_ten_tb = null
        this.params.p_ma_tt = null
        this.params.p_ten_tt = null
        this.params.p_trangthai_tb = null
        this.params.p_trangthai_tb_id = null
        this.params.p_tongno = 0
        this.params.p_ma_PLKH = ""
      },
      async setThongTinTB(data) // tìm thấy 1 TB
      {
        try{
        //   this.params.p_chieukhoa_options = []
          this.params.p_khachhang_id = data.KHACHHANG_ID
          this.params.p_thanhtoan_id = data.THANHTOAN_ID
          this.params.p_thuebao_id = data.THUEBAO_ID
          this.params.p_loaihinh_tb = data.LOAITB_ID
          this.params.p_loaihinh = data.LOAITB_ID
          this.params.p_dichvu = parseInt(data.DICHVUVT_ID)
          this.params.p_ten_tb = data.TEN_TB
          this.params.p_ma_tt = data.MA_TT
          this.params.p_ten_tt = data.TEN_TT
          this.params.p_trangthai_tb = data.TRANGTHAI_TB

          //this.params.p_loaihinh_tb = parseInt(response.data.data[0].LOAITB_ID)

          //this.getLoaiHinhTB(this.params.p_dichvu, parseInt(response.data.data[0].LOAITB_ID))
          await this.getLoaiHinhTB(this.params.p_dichvu, data.LOAITB_ID)
          this.params.p_loaihinh_tb = this.params.p_loaihinh

          this.getDiemTinNhiemKhachHang()

          if(this.params.LAY_GHICHU_NHAC_LAM_LYDOKHOA === 1)
            this.getLyDoKhoa()
          this.kiemtraTBTrongDSDacBiet()
            .then(
              (res) => {
                if(res.data.error_code === 'BSS-00000000'
                  && res.data.data !== undefined
                  && res.data.data !== null)
                  {
                    this.params.p_trangthai = 1
                  }
                else {
                  this.params.p_trangthai = 0
                }
              }
            )

          this.params.p_trangthai_tb_id = parseInt(data.TRANGTHAITB_ID)
          this.params.p_khachhang_id = parseInt(data.KHACHHANG_ID)
          if (this.params.p_loaihinh_tb === LoaiHinhTB.ISDN2B_CD || this.params.p_loaihinh_tb === LoaiHinhTB.ISDN2B_CQ
          || this.params.p_loaihinh_tb === LoaiHinhTB.ISDN30B_CD || this.params.p_loaihinh_tb === LoaiHinhTB.ISDN30B_CQ)
          {
              this.getISDNTheoThueBao(this.params.p_thuebao_id)
          }
          if (this.params.p_dichvu === DichVuVienThong.CO_DINH
            || this.params.p_dichvu === DichVuVienThong.DI_DONG
            || this.params.p_dichvu === DichVuVienThong.GPHONE
            || this.params.p_dichvu === DichVuVienThong.IMS)
          {
            if (this.params.KHONG_KHOAMAY_MOTCHIEU === -1)
            this.params.p_chieukhoa_options = [{ id: "1", text : "Khóa 1 chiều do nợ cước"},{ id: "2", text : "Khóa 2 chiều do nợ cước"}]
          }
          else
          {
             this.params.p_chieukhoa_options = [{ id: "2", text : "Khóa 2 chiều do nợ cước"}]
          }
          this.params.p_chieukhoa = this.params.p_chieukhoa_options[0].id
        } catch( err )
        {

        }

      },
      redirectDongBo: function() {
        this.$router.push({
                name: 'TiepNhanThongTinKhoaMoNoCuoc',
                params: {
                    tag: '1+7+1;4;8;9;10;11;12',
                    dichvuID: this.params.dichVu_Selected,
                    kieuYCID: 0,
                    tthdID: 1
                }
            });
      },
      validateData: function(){
        let error = []
        if(this.params.p_chieukhoa === null)
          error.push('Trường chiều khóa bắt buộc nhập.')
        if(this.params.p_lydokhoa === null)
          error.push('Trường lý do khóa bắt buộc nhập.')
        if(this.params.p_sdt_nhacno === null)
          error.push('Trường số điện thoại nhắc nợ bắt buộc nhập.')

        if(error.length > 0){
          this.$toast.error(error.join('\r\n'))
          return false
        }

        error = []

        let txtChieuKhoa = this.params.p_chieukhoa_options.filter(e => '' + e.id === '' + this.params.p_chieukhoa
        )

        if(this.params.p_chieukhoa === 1) // khóa 1 chiều
        {
          if(this.params.p_trangthai_tb_id !== TrangThaiTB.THUONG)
          {
            error.push("Bạn đang chọn chiều khóa: " + txtChieuKhoa[0].text + ", chỉ được chuyển hợp đồng đối với những thuê bao có trạng thái hoạt động bình thường! Bạn hãy chọn thuê bao khác!")
          }
        } else {
          if (this.params.p_trangthai_tb_id !== TrangThaiTB.THUONG
              && this.params.p_trangthai_tb_id !== TrangThaiTB.KHOA_MOTCHIEU_YEUCAU
              && this.params.p_trangthai_tb_id != TrangThaiTB.KHOA_MOTCHIEU_CUONGBUC)
          {
            error.push("Bạn đang chọn chiều khóa: " + txtChieuKhoa[0].text + ", chỉ được chuyển hợp đồng đối với những thuê bao có trạng thái hoạt động bình thường!\nBạn hãy chọn thuê bao khác!");
          }else if (this.params.p_chk_thuhoi !== '1')
          {
            //error.push("Bạn chắc chắn không thu hồi thiết bị của khách hàng?", "Thông báo", MessageBoxButtons.YesNo, MessageBoxIcon.Information) != DialogResult.Yes)
            //      return;
          }
        }

        if(error.length > 0){
          this.$toast.error(error.join('<BR>'))
          return false
        }

        return true
      },
      async getTSMacDinh(thamso) {
        try {
          let response = await KhoaMayAcc.getThamSoMacDinh(this.axios, { pMaThamSo: thamso});
          return response
        }
        catch (error) {
          this.$toast.error(error.data.message_detail)
        }
        finally {
        }
      },
      async kiemtraTBTruocKhiCapNhat() {
        let data  = {
          pThueBaoId : this.params.p_thuebao_id,
          pMaTB : this.params.p_somay_acc,
          pMaTT : this.params.p_ma_tt,
          pNguoiDungId : this.$auth.getNguoiDungID(),
          pKieu : 1,
          pThamSoMD : 'KHOAMAY_TB_KHONG_XULY',
          pTrangThai : this.params.p_trangthai_tb_id,
          pKyCuoc: this.params.p_kycuoc
        }

        try{
          let response = await KhoaMayAcc.checkThueBaoTruocKhiCapNhat(this.axios, data);
          if(response.data.error_code === 'BSS-00000000' && response.data.data !== undefined)
            return response.data.data
          else
            return null
        }
        catch(error)
        {
          this.$toast.error(error.data.message_detail)
          this.loading(false)
          return null
        }
/*
        try {


          if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {

            let res = await this.kiemtraTBTrongDSDacBiet()
            if (res.data.error_code === 'BSS-00000000' && res.data.data !== undefined) {
              this.params.p_trangthai = 1 // TB Đặc biệt

              let str = "Thuê bao " + this.params.p_somay_acc + " đang thuộc thuê bao đặc biệt tháng!" +
                                (doituong != "" ? " Đối tượng: " + doituong : "")
                                + " Bạn có muốn tiếp tục?

              DialogObj = DialogUtility.confirm({
              title: 'Thông báo',
              content: "<div style='padding:10px'>Bạn có thật sự muốn xóa " + selected.length + " thuê bao đang được chọn không ?</div>",
              okButton: {  text: 'Đồng ý', click: this.confirmDelete },
              cancelButton: {  text: 'Hủy'},
              showCloseIcon: true,
              closeOnEscape: true,
              animationSettings: { effect: 'Zoom' }
          });
            }
          }
        }
        catch (error) {
          this.$toast.error(error.data.message_detail)
        }
        finally {
          this.loading(false)
        }
*/
      },
      async kiemtraTBTrongDSDacBiet() {
        let data  = {
          pThueBaoId : this.params.p_thuebao_id,
          pKyCuoc: this.params.p_kycuoc
        }

        try {
          let response = await KhoaMayAcc.checkThueBaoTrongDSDacBiet(this.axios, data);
          return response
        }
        catch (error) {
          this.$toast.error(error.data.message_detail)
          this.loading(false)
        }
        finally {
        }
      },
      async kiemtraMyTVChinhPhu() {
        let data  = {
          pThueBaoId : this.params.p_thuebao_id,
          pMaTB : this.params.p_somay_acc,
          pKieu : 1
        }

        try {
          let response = await KhoaMayAcc.checkMyTVChinhPhu(this.axios, data);
          if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
            return response.data.data
          } else
            return true
        }
        catch (error) {
          this.$toast.error(error.data.message_detail)
          this.loading(false)
          return null
        }
        finally {
        }
      },
      async giaophieuKhoaMay() {
        let data  = {
          pQuyTrinhId : 1,
          pDSThaoTac : 'STATUS_TONGDAI_CD,GIAOVIEC_NVQL_CAP,GIAOPHIEU_GIAMDOC_PBHKV'
        }

        try {
          let response = await KhoaMayAcc.giaophieuKhoaMay(this.axios, data);
          if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
            //console.log(response.data.data)
            return response.data.data
          } else
            return null
        }
        catch (error) {
          this.$toast.error(error.data.message_detail)
          this.loading(false)
          return null
        }
        finally {
        }
      },
      async kiemtraTongNoTB() {
        let data  = {
          pMaTB : this.params.p_somay_acc,
          pMaTT : this.params.p_ma_tt,
          pKieu : 1,
          pKyHoaDon: this.params.p_kycuoc
        }

        try {
          let response = await KhoaMayAcc.checkTongNoTB(this.axios, data);
          if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
            //return response.data.data
            this.params.p_tongno = response.data.data[0].TONGNO
            return true
          } else
            return false
        }
        catch (error) {
          this.$toast.error(error.data.message_detail)
          this.loading(false)
          return null
        }
        finally {
        }
      },
      async kiemtraKhieuNaiTB() {
        let data  = {
          pThueBaoId : this.params.p_thuebao_id
        }

        try {
          let response = await KhoaMayAcc.checkKhieuNaiTB(this.axios, data);
          if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
            //console.log(response.data.data)
            return response.data.data
          } else
            return null
        }
        catch (error) {
          this.$toast.error(error.data.message_detail)
          this.loading(false)
          return null
        }
        finally {
        }
      },
      async chuyenTBSangHDKhoaMo() {
        let lydo = this.params.p_lydokhoa_options.filter( x => x.LYDOTRA_ID === this.params.p_lydokhoa)
        let data = {
          "pDSTB" : [
            {
              "pCachKhoa": 0,
              "pDichVuVTId": this.params.p_dichvu,
              "pDonViId": this.params.p_d,
              "pKieuKhoa": this.params.p_chieukhoa,
              "pKieuYC": 1, // khóa máy
              "pLoaiTBId": this.params.p_loaihinh_tb,
              "pLyDoKhoa": lydo[0].NOIDUNG,
              "pMaTB": this.params.p_somay_acc,
              "pMaTT": this.params.p_ma_tt,
              "pMayCN": "-",
              "pNgayCN": moment().format('DD/MM/YYYY'),
              "pNgayKhoa": moment().format('DD/MM/YYYY'),
              "pNguoiCN": this.$auth.getUserName(),
              "pNhanVienId": this.$auth.getNhanVienID(),
              "pThuHoi": this.params.p_chk_thuhoi,
              "pThueBaoId": this.params.p_thuebao_id,
              "pTienNo": this.params.p_tongno
            }
          ]
        }

        try {
          let response = await KhoaMayAcc.chuyenTBsangHDKhoaMo(this.axios, data);
          if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
            //console.log(response.data.data)
            return response.data.data
          } else
            return null
        }
        catch (error) {
          this.$toast.error(error.data.message_detail)
          this.loading(false)
          return null
        }
        finally {
        }
      },
      async xacnhanKhoaMay()
      {
        if(!this.validateData())
          return false

        this.loading(true)

        let ret = await this.kiemtraTBTruocKhiCapNhat()

        if(ret !== true)
        {
          this.$toast.error(ret)
          return
        }

        ret = await this.kiemtraMyTVChinhPhu()

        if(ret === null)
        {
          //this.$toast.error(ret)
          return
        }


        ret = await this.kiemtraTongNoTB()
        console.log(ret)
        if(ret === null || ret !== true)
        {
          //this.$toast.error(ret)
          return
        } else
        {
          if(this.params.p_tongno <= 0)
          {
            DialogObj = DialogUtility.confirm({
              title: 'Thông báo',
              content: "<div style='padding:10px'>Thuê bao " + this.params.p_somay_acc + " đã thanh toán hết nợ. Bạn có đồng ý tiếp tục?</div>",
              okButton: {  text: 'Đồng ý', click: this.confirmTongNo },
              cancelButton: {  text: 'Hủy'},
              showCloseIcon: true,
              closeOnEscape: true,
              animationSettings: { effect: 'Zoom' }
            });
          } else
          {
            this.confirmTongNo()
          }
        }
        this.loading(false)

      },
      async confirmTongNo()
      {
        if(DialogObj !== undefined)
          DialogObj.hide()
        this.loading(true)
        //let ret = await this.giaophieuKhoaMay()
        let ret = await this.chuyenTBSangHDKhoaMo()

        if(ret !== null)
        {
          this.$toast.success("Chuyển thành công thuê bao có mã: " + this.params.p_somay_acc + " sang hợp đồng thay đổi dịch vụ !")
          this.clearForm()
        }
        this.loading(false)
      },
      isNumber: function(evt) {
        evt = (evt) ? evt : window.event;
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if ((charCode > 31 && (charCode < 48 || charCode > 57)) /*&& charCode !== 46*/) {
          evt.preventDefault();;
        } else {
          return true;
        }
      },
      getKyCuocHienHanh: function(){
        CommonsAPI.getKyCuocHienHanh(this.axios)
        .then(
          (res) => {
            if(res.data.error_code === 'BSS-00000000'
              && res.data.data !== undefined
              && res.data.data.length > 0
            )
            {
              this.params.p_kycuoc = res.data.data[0].KYCUOC
            }
          }
        )
      }
    }
  })
</script>
