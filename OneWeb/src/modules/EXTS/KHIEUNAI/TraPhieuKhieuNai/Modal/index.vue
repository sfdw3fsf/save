<template src="./index.html"></template>
<script>
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required} from 'vuelidate/lib/validators'

export default {
  components: { BssErrorMarker, BssRequiredMarker},
  validations: {
    modelInput: {
      txtNDTra: {
        required
      }
    },      
  },
  name: 'Modal',
  props: {
    modalShow: Boolean,
    doiTuong: Object,
    id: String
  },
  created: function () {

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
        nhanvien_id: -1
    }
  },
  methods: {
     onHiddenModal() {
      this.isShown = false
      this.key++
    },
    async onShownModal() {
      this.isShown = true
      try{
        this.loading(true)
        if(this.doiTuong.ma_tb != null){
          let ma_tb = this.doiTuong.ma_tb;
          let ngaygiao = this.doiTuong.ngaygiao;
          await this.HienThiDanhSach(ma_tb, ngaygiao);
        }     
        await this.fillDataCbo()
        //await this.SendSMS()  
      }catch(error){

      }finally{
          this.loading(false)
      }
    },
    onEnterAcc: async function() {
      try{
        this.loading(true)
        if(this.modelInput.ma_tb != null || this.modelInput.ma_tb != ''){
          let ngaygiao = this.modelDateTime.dtpNgayGiao;
          let ma_tb = this.modelInput.ma_tb;
          await this.HienThiDanhSach(ma_tb, ngaygiao);
          
        }
        else {
          this.$root.toastError("Chưa nhập thông tin Acc")
        }         
      }catch(error){

      }finally{
          this.loading(false)
      }
    },
    // SendSMS: async function(){
    //   let data = {
    //     content: "tu choi khieu nai",
    //     msisdn: "84915568584",
    //     mt_type: "reply"
    //   }
    //   console.log('data dauvao',data) 
    //   var rs = await this.$root.context.post("/web-cabman/traphieukhieunai/off-send-sms",data);
    //   console.log('data output',rs.data)
    // },
    HienThiDanhSach: async function(ma_tb, ngaygiao){
      let kycuoc = await this.$root.context.get('/web-thuno/api/thu-no/common/ky-cuoc-hien-hanh')
      let data = {
          P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
          P_MA_TB: ma_tb,
          P_NGAYGIAO: ngaygiao,
          P_DONVI_NHAN_ID: null,//this.$root.token.getDonViID(),
          P_KYHOADON: null,//kycuoc.data[0].KYCUOC,
          P_NHANVIEN_ID: null,//this.$root.token.getNhanVienID(),
          PROCEDURE_NAME: 'CABMAN.TRA_PHIEU_KHIEUNAI.LAY_DS_PHIEU_YC_KN'
        }
        console.log('data', data)
        let res = await this.$root.context.post('/web-cabman/ban-do-mang-cap/execute1', data)
        console.log('rs', res)
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
            //this.dtpNgayTra.Value = tt_nd.ngay_cn;
            this.modelCbo.mcboTT = 2;
            //this.dtpNgayGiao.Value = tt_nd.ngay_cn;
            this.modelInput.txtSoQD = "";
            this.modelInput.txtDonViGiao = "";
        }
    },
    selectedItemsDsHoSo: async function(data){
       if (data != null || data != ''){
          console.log('data ', data)
          console.log('this.dtList', this.dtList)
          let selected = this.dtList.filter(obj=>obj.PHIEUKN_ID === data[0]);
          this.selectedRow = selected;
          console.log('selected ', selected)
          this.phieukn_id = selected[0].PHIEUKN_ID
          this.modelInput.txtMaTB = selected[0].MA_TB;
          this.modelDateTime.dtpNgayTN = selected[0].NGAY_TN;//Convert.ToDateTime(dtList.Rows[nIndex]["ngay_tn"].ToString());
          this.modelInput.txtNguoiTN = selected[0].NGUOI_TN;
          this.modelInput.txtNguoiLH = selected[0].NGUOI_KN;
          if (selected[0].HENGQ_TU != '' || selected[0].HENGQ_TU != null)
          {
            this.modelDateTime.dtpTuNgay = selected[0].HENGQ_TU//Convert.ToDateTime(dtList.Rows[nIndex]["hengq_tu"].ToString());
            this.modelCkeckBox.chkHenTL = true;
            this.modelDateTime.enableDate = true;
          }
          else
          {
            this.modelCkeckBox.chkHenTL = false;
            this.modelDateTime.enableDate = false;
          }
          if (selected[0].HENGQ_DEN != '' || selected[0].HENGQ_DEN != null)
          {
            this.modelDateTime.dtpDenNgay = selected[0].HENGQ_DEN;//Convert.ToDateTime(dtList.Rows[nIndex]["hengq_den"].ToString());
            this.modelDateTime.enableDate = true;
          }
          else {
            this.modelDateTime.enableDate = true;
          }
              
          this.modelInput.txtGhiChu = selected[0].GHICHU_KN;
          this.modelInput.txtTenTB = selected[0].TEN_TB;
          this.modelInput.txtDiaChiTB = selected[0].DIACHI_TB;
          this.modelInput.txtNoiDungKN = selected[0].ND_KHIEUNAI;

          //Thong tin phieu giao
          this.modelDateTime.dtpNgayGiao = selected[0].NGAYGIAO;//Convert.ToDateTime(dtList.Rows[nIndex]["ngaygiao"].ToString());
          this.modelInput.txtDonViGiao = selected[0].DONVI_GIAO;
          this.modelInput.txtNhanVienGiao = selected[0].NHANVIEN_GIAO;
       }
    },
    btnLayTTMoi_Click: async function() {
      try
      {
        this.loading(true)
        this.HienThiDanhSach(this.modelInput.txtMaTB, this.modelInput.dtpNgayGiao);
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
              cancelTitle: 'Hủy',
            }
          )
          .then((value) => {
            if (value) {
              this.CapNhat();
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
        await this.TaoDuLieu();
        console.log(' this.dsTraPhieu ', this.dsTraPhieu)
        console.log('this.ttph_rm_kdv ',this.ttph_rm_kdv)
        if (this.ttph_rm_kdv == 0){
          await this.TraPhieu_KN_V2(this.dsTraPhieu)
        }
        else
        {
          let data = {
            phieukn_id: this.phieukn_id,
            noidung: this.modelInput.txtNDTra,
            nhanvien_tra_id: this.nhanvien_tra_id,
            khieunai_id: this.khieunai_id
          }
          try {
            await this.$root.context.post("/web-cabman/traphieukhieunai/traphieu-roimang-kdv", data);  
          } catch (error) {
            this.$root.toastError("Có lỗi trong quá trình trả phiếu !")
          }
        }

        this.$root.toastSuccess('Đã trả phiếu thành công!')
        this.modelInput.txtNDTra = "";
        this.modelCbo.mcboTT = 0;
        this.modelInput.txtMaTB = "";
        this.modelInput.txtTenTB = "";
        this.modelInput.txtDiaChiTB = "";
        //let rs1 = await this.$root.context.get("/web-cabman/traphieukhieunai/lay-ds-phieutra", this.phieukn_id);  
        //if (rs1.data.length > 0)
        // DataTable dt = bangts.GetDataSql(string.Format(sql, DatabaseConstants.DB3, phieukn_id));
        // if(dt.Rows.Count > 0)
        // XyLyPhieu_SuKien.ThongBao_XuLyPhieuXong(this, new Common.Model.XuLyPhieuEventArgs(
        //         Convert.ToInt64(dt.Rows[0]["thuebao_id"])
        //         , Convert.ToInt64(dt.Rows[0]["khieunai_id"])
        //         , Common.Model.KieuXuLy.TraPhieu_GQKN));
        //await this.HienThiDanhSach(ma_tb, ngaygiao);
        let ma_tb = this.doiTuong.ma_tb;
        let ngaygiao = this.doiTuong.ngaygiao;
        await this.HienThiDanhSach(ma_tb, ngaygiao);
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
        this.modelCbo.mcboTT = rs.data[0]
      }
    },
    TraPhieu_KN_V2: async function(ds)
    {
        try
        {
                //Update tthd = thanh toán của hopdong_tb
                let data_update = {
                  phieukn_id: this.phieukn_id,
                  khieunai_id: ds[0].KHIEUNAI_ID
                }
                let data = await this.$root.context.post("/web-cabman/traphieukhieunai/capnhat-tthd", data_update);
                console.log('data capnhat',data)
                // Hủy giao phiếu hiện tại
                let data_huy = {
                  phieukn_id: this.phieukn_id,
                  nhanvien_tra_id: ds[0].NHANVIEN_TRA_ID,
                  noidung: ds[0].ND_TRA
                }
                let huy = await this.$root.context.post("/web-cabman/traphieukhieunai/huy-giaophieu", data_huy);
                console.log('data huy',huy)
                //#region "hàm nhắn tin trả phiếu"
                
                let data_send = {
                  phieukn_id: this.phieukn_id,
                  nhanvien_tra_id: ds[0].NHANVIEN_TRA_ID,
                  noidung: ds[0].ND_TRA
                }
                console.log('data_send', data_send)
                let rs = await this.$root.context.post("/web-cabman/traphieukhieunai/xuly-thongtin-sms", data_send);
                console.log('noidungguitinnhan', rs)
                //#endregion
        }
        catch (ex)
        {
          this.$root.toastError("Có lỗi trong quá trình trả phiếu !")
        }
        finally
        {
        }
    },
    TaoDuLieu: async function(){
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
