<template src="./index.html"></template>
<script>
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required} from 'vuelidate/lib/validators'
import DateTimeLib from "../../../../utils/DateTimeLib";
export default {
  components: { BssErrorMarker, BssRequiredMarker},
  validations: {
    modelInput: {
      txtNDTra: {
        required
      }
    },
  },
  props: {
      maTbProp: String
  },
  data () {
    return {
        key: 1,
        isShown: false,
        ok:true,
        dtList: [],
        selectedRow: {},
        dsTraPhieu: {},
        modelInput: {
          txtNDTra: '',
          txtMaTB: '',
          txtGhiChu: '',
          txtSoQD: '',
          txtDonViGiao: '',
          txtNguoiTN: '',
          txtNguoiLH: '',
          txtGhiChuTra: '',
          txtTenTB: '',
          txtDiaChiTB: '',
          txtNoiDungKN: '',
          txtNhanVienGiao: '',
        },
        modelCbo: {
          mcboTT: -1,
          vcboTT: []
        },
        modelDateTime: {
          dtpNgayTra: new Date(),
          dtpNgayGiao: new Date(),
          dtpNgayTN: new Date(),
          dtpTuNgay : new Date(),
          dtpDenNgay: new Date(),
          dateFormat: 'dd/MM/yyyy',
          enableDate: false
        },
        modelCkeckBox: {
          chkNgayTra: false,
          chkNgayGiao: false,
          chkHenTL: false
        },
        khieunai_id: 0,
        ttph_rm_kdv: 0,
        phieukn_id: 0,
        nhanvien_id: -1,
        dataKhieuNai: {},
        totalItems: 0,
        pageIndex: 0,
        pageSize: 10,
        maTbSearch: null
    }
  },
  async mounted() {
    await this.fillDataCbo()
    // this.modelInput.txtMaTB = this.maTbProp

    // try{
    //   this.loading(true)
    //   await this.fillDataCbo()
    //   await this.btnLayTTMoi_Click()
    // }
    // catch(error){
    //   this.$root.toastError(error)
    // }finally{
    //   this.loading(false)
    // }
  },
  methods: {
    grid_PageChanged: async function (args) {
      this.$root.showLoading(true);
      try {
        await this.HienThiDanhSach1(this.maTbSearch, '', args.pageIndex, args.pageSize);
      } finally {
        this.$root.showLoading(false);
      }
    },
    async bindData(data){

      this.dtList = [data];
      this.khieunai_id = data.KHIEUNAI_ID
      console.log('lí da ta',data)
    },
    onEnterAcc: function() {
      this.btnLayTTMoi_Click()
    },
    // SendSMS: async function(content, msisdn){
    //   let data = {
    //     content: content,
    //     msisdn: msisdn,
    //     mt_type: "reply"
    //   }
    //   await this.$root.context.post("/web-cabman/traphieukhieunai/off-send-sms",data);
    // },
    HienThiDanhSach1: async function(ma_tb, ngaygiao, pageIndex, pageSize){
       try{
        this.loading(true)
        let kycuoc = await this.$root.context.get('/web-thuno/api/thu-no/common/ky-cuoc-hien-hanh')
        let data = {
            ma_tb: ma_tb,
            ngaygiao: ngaygiao,
            donvi_nhan_id: this.$root.token.getDonViID(),
            kyhoadon: kycuoc.data[0].KYCUOC,
            nhanvien_id: this.$root.token.getNhanVienID(),
            pageIndex: pageIndex,
            pageSize: pageSize
          }

          var res = await this.$root.context.post("/web-cabman/traphieukhieunai/lay-ds-khieunai-paging",data);
          if (res.data.allItems > 0)
          {
            this.dtList = res.data.pageItems;
            this.totalItems = res.data.allItems;
            console.log('danh sach',this.dtList)
          }
          else
          {
              this.dtList = [];
              this.totalItems = 0;
              this.modelInput.txtMaTB = "";
              this.modelInput.txtNDTra = "";
              this.modelInput.txtGhiChu = "";
              this.modelInput.dtpNgayTra = DateTimeLib.toDateDisplay(new Date())//tt_nd.ngay_cn;
              this.modelCbo.mcboTT = 2;
              this.modelInput.dtpNgayGiao = DateTimeLib.toDateDisplay(new Date())//tt_nd.ngay_cn;
              this.modelInput.txtSoQD = "";
              this.modelInput.txtDonViGiao = "";
          }
        }
      catch(error){
        this.$root.toastError(error)
      }finally{
        this.loading(false)
      }
    },
    HienThiDanhSach: async function(ma_tb, ngaygiao){
       try{
        this.dtList = []
        this.loading(true)
        let kycuoc = await this.$root.context.get('/web-thuno/api/thu-no/common/ky-cuoc-hien-hanh')
        let data = {
            ma_tb: ma_tb,
            ngaygiao: ngaygiao,
            donvi_nhan_id: this.$root.token.getDonViID(),
            kyhoadon: kycuoc.data[0].KYCUOC,
            nhanvien_id: this.$root.token.getNhanVienID()
          }
          var res = await this.$root.context.post("/web-cabman/traphieukhieunai/lay-ds-phieukhieunai",data);
          if (res.data.length > 0)
          {
            this.dtList = res.data;
          }
          else
          {
              this.dtList = [];
              this.modelInput.txtMaTB = "";
              this.modelInput.txtNDTra = "";
              this.modelInput.txtGhiChu = "";
              this.modelInput.dtpNgayTra = DateTimeLib.toDateDisplay(new Date())//tt_nd.ngay_cn;
              this.modelCbo.mcboTT = 2;
              this.modelInput.dtpNgayGiao = DateTimeLib.toDateDisplay(new Date())//tt_nd.ngay_cn;
              this.modelInput.txtSoQD = "";
              this.modelInput.txtDonViGiao = "";
          }
        }
      catch(error){
        this.$root.toastError(error)
      }finally{
        this.loading(false)
      }
    },
    selectedItemsDsHoSo: async function(data){

       if (!!data){
          //let selected = this.dtList.filter(obj=>obj.phieukn_id === data);
          //console.log('selected',selected)
          //if (selected.length  > 0)
          //{
            this.selectedRow = [data];
            this.phieukn_id = data.PHIEUKN_ID
            this.modelInput.txtMaTB = data.MA_TB;
            this.modelDateTime.dtpNgayTN = data.NGAY_TN;//Convert.ToDateTime(dtList.Rows[nIndex]["ngay_tn"].ToString());
            this.modelInput.txtNguoiTN = data.NGUOI_TN;
            this.modelInput.txtNguoiLH = data.NGUOI_KN;
            if (data.HENGQ_TU != '' || data.HENGQ_TU != null)
            {
              this.modelDateTime.dtpTuNgay = data.HENGQ_TU//Convert.ToDateTime(dtList.Rows[nIndex]["hengq_tu"].ToString());
              this.modelCkeckBox.chkHenTL = true;
              this.modelDateTime.enableDate = true;
            }
            else
            {
              this.modelCkeckBox.chkHenTL = false;
              this.modelDateTime.enableDate = false;
            }
            if (data.HENGQ_DEN != '' || data.HENGQ_DEN != null)
            {
              this.modelDateTime.dtpDenNgay = data.HENGQ_DEN;//Convert.ToDateTime(dtList.Rows[nIndex]["hengq_den"].ToString());
              this.modelDateTime.enableDate = true;
            }
            else {
              this.modelDateTime.enableDate = true;
            }

            this.modelInput.txtGhiChu = data.GHICHU_KN;
            this.modelInput.txtTenTB = data.TEN_TB;
            this.modelInput.txtDiaChiTB = data.DIACHI_TB;
            this.modelInput.txtNoiDungKN = data.ND_KHIEUNAI;

            //Thong tin phieu giao
            this.modelDateTime.dtpNgayGiao = data.NGAYGIAO;//Convert.ToDateTime(dtList.Rows[nIndex]["ngaygiao"].ToString());
            this.modelInput.txtDonViGiao = data.DONVI_GIAO;
            this.modelInput.ND_giao=data.ND_GIAO;
            this.modelInput.txtNhanVienGiao = data.NHANVIEN_GIAO;
          //}

       }
    },
    btnLayTTMoi_Click: async function() {

      this.maTbSearch = this.modelInput.txtMaTB;
      let pagination = this.$refs.grid.$refs.pagination;

      if (pagination.pageIndexData != 0) {
        pagination.gotoFirstPage();
        return;
      }

      try
      {
        this.loading(true)
        await this.HienThiDanhSach1(this.maTbSearch, '', pagination.pageIndexData, pagination.pageSizeData);
      }
      catch (ex)
      {
        this.$root.toastError("Có lỗi: " +ex)
      }
      finally
      {
        this.loading(false)
      }
    },
    btnTraphieu_Click: async function() {
      try
      {
        this.loading(true)
        if (this.dtList.length <= 0) return;
        this.$v.$touch()
        if (this.$v.$invalid && !this.$v.modelInput.txtNDTra.required) {
          this.$root.$toast.error('Bạn chưa nhập nội dung trả!')
          return;
        }
        this.$bvModal
          .msgBoxConfirm(
            "Bạn thật sự muốn trả lại phiếu đã nhận không ?",
            {
              title: "Thông báo",
              centered: true,
              size: "sm",
              okTitle: 'Đồng ý',
              cancelTitle: 'Không đồng ý',
            }
          )
          .then(async (value) => {
            if (value) {
              await this.CapNhat();
            } else {
              return;
            }
          });
      }
      catch (ex)
      {
        this.$root.toastError("Có lỗi: " +ex)
      }
      finally
      {
        this.loading(false)
      }
    },
    CapNhat: async function() {
      try
      {
        this.loading(true)
          let data = {
            pKhieuNaiId: this.khieunai_id,
            pPhieuKNId: this.phieukn_id,
            pNoiDung: this.modelInput.txtNDTra,
            pLyDoTraId:this.modelCbo.mcboTT
          }
          try {
            await this.$root.context.post("/web-gqkn/khieunai/capnhat-traphieu", data);
            this.$root.toastSuccess('Đã trả phiếu thành công!')
          } catch (error) {
            this.$root.toastError("Có lỗi trong quá trình trả phiếu !")
          }

        this.$emit('success')
        this.modelInput.txtNDTra = "";
        this.modelCbo.mcboTT = 0;
        this.modelInput.txtMaTB = "";
        this.modelInput.txtTenTB = "";
        this.modelInput.txtDiaChiTB = "";
        this.maTbSearch = ""
        this.btnLayTTMoi_Click()
      }
      catch (exp)
      {
        this.$root.toastError("Có lỗi trong quá trình trả phiếu !")
        //new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, exp.Message, exp), true);
      }
      finally {
        this.loading(false)
      }
    },
    fillDataCbo: async function(){
      let rs = await this.$root.context.get("/web-cabman/traphieukhieunai/nap-combobox", {kieu: 1});
      this.modelCbo.vcboTT = rs.data
      if (rs.data.length > 0){

        this.modelCbo.mcboTT = rs.data[0].LOAIPT_ID
      }
    },
    TraPhieu_KN_V2: async function(ds)
    {
        try
        {

          //update khieu nai
          await this.$root.context.post("/web-cabman/traphieukhieunai/capnhat-khieunai", this.dataKhieuNai);
          //Update tthd = thanh toán của hopdong_tb
          let data_update = {
            phieukn_id: this.phieukn_id,
            khieunai_id: ds[0].KHIEUNAI_ID
          }
          await this.$root.context.post("/web-cabman/traphieukhieunai/capnhat-tthd", data_update);
          // Hủy giao phiếu hiện tại
          let data_huy = {
            phieukn_id: this.phieukn_id,
            nhanvien_tra_id: ds[0].NHANVIEN_TRA_ID,
            noidung: ds[0].ND_TRA
          }
          await this.$root.context.post("/web-cabman/traphieukhieunai/huy-giaophieu", data_huy);
          //#region "hàm nhắn tin trả phiếu"

          let data_send = {
            phieukn_id: this.phieukn_id,//166144,//this.phieukn_id,
            nhanvien_tra_id: ds[0].NHANVIEN_TRA_ID,
            noidung: ds[0].ND_TRA
          }
          await this.$root.context.post("/web-cabman/traphieukhieunai/xuly-thongtin-sms", data_send);
          // let rs = await this.$root.context.post("/web-cabman/traphieukhieunai/xuly-thongtin-sms", data_send);
          // if (rs.data[0].DT != '0'){
          //   await this.SendSMS(rs.data[0].ND, rs.data[0].DT)
          // }
          //#endregion
          this.$root.toastSuccess('Đã trả phiếu thành công!')
        }
        catch (ex)
        {
          this.$root.toastError(ex.response.data.message_detail)
          return;
        }
        finally
        {
        }
    },
    TaoDuLieu: async function () {
      this.dsTraPhieu = []
      let nIndex = this.selectedRow[0]
      this.phieukn_id = nIndex.PHIEUKN_ID
      let row = {}
      let rs = await this.$root.context.get("/web-cabman/traphieukhieunai/lay-phieukhieunai", {
        phieukn_id: this.phieukn_id,
      });
      row = rs.data[0]
      row.NGAYTRA = this.convertDate2String(this.modelDateTime.dtpNgayTra)
      row.ND_TRA =  this.modelInput.txtNDTra
      row.TTPH_ID = 3 //TRANGTHAI_PH.DATRALAI;
      row.NHANVIEN_TRA_ID = this.$root.token.getNhanVienID()
      row.GHICHU_TH = this.modelInput.txtGhiChuTra
      this.dataKhieuNai = {
        phieukn_id: this.phieukn_id,
        ngaytra: row.NGAYTRA,
        nd_tra: row.ND_TRA,
        ttph_id: row.TTPH_ID,
        nhanvien_tra_id: row.NHANVIEN_TRA_ID,
        ghichu_th: row.GHICHU_TH
      }
      this.dsTraPhieu.push(row)
      this.khieunai_id = row.KHIEUNAI_ID
      this.nhanvien_id = row.NHANVIEN_TRA_ID
    },
    convertDate2String(date) {
      let dd = String(date.getDate()).padStart(2, "0");
      let mm = String(date.getMonth() + 1).padStart(2, "0"); //January is 0!
      let yyyy = date.getFullYear();
      return dd + "/" + mm + "/" + yyyy;
    },
  },
};
</script>
