<template  src="./main.html">

</template>

<script>
import BssRequiredMarker from '@/components/BssRequiredMarker'
import ModalChonDiaChi from '../../BanDoTuyenCot/ChonDiaChi/Modal'
import SearchAccount from "@/modules/search/subscriber/SearchAccount";
//import SearchAccount  from "../thumuctest/SearchAccount/SearchAccount";
import ThemMoiPhongTro from "../ThemMoiPhongTro/main"
import moment from 'moment'
import popupChonThueBao from "../chon-thuebao/index"
import ThemExcel from "../ThemExcel/main"
import XLSX from "xlsx";
var itemThaoTac= function () {
  return {
    template: {
      template: `
        <div class="table-result ghi-chu" style="background: transparent;">
          <div style="display: inline-flex;align-items: center;justify-content: space-around;" class="mb-1 ghi-chu-item">
            <div style="margin-left: 3px" class="pull-right" @click="onClickXoa(this.data)">
              <button class="btn btn-second lh12 pad2">
                <span class="-ap icon-close"  style="color:red"></span>
              </button>
            </div>
          </div>
        </div>`,
      data() {
        return {
          data: { },
          textChange: ''
        }
      },
      computed: {
        parent() {
          return this.$parent.$parent.$parent.$parent.$parent.$parent.$parent
        }
      },methods:{
        onClickXoa() {
          this.parent.xoaCaNhanThueTro(this.data)
        },
      },mounted() {
      }
    }
  }
}
export default {
  name: '', provide: {
  },
  components :{BssRequiredMarker,ModalChonDiaChi,SearchAccount,moment,ThemMoiPhongTro,popupChonThueBao,ThemExcel},
  computed: {
  },
  data () {
    return {
      itemThaoTac: itemThaoTac,
      fileExcel:null,
      dataAll:null,
      cboDichVu:[],
      titleEmpty:"Nhập vào số máy/Account ở phía trên hoặc chọn thuê bao từ danh bạ để gán vào các phòng trọ.",
      titleStatus:"Chưa nhập thuê bao",
      dsloaithuebao:[],
       category : {
        dichvu: 0,
        thongtinchuho: 0,
        thongtinnhatro: 0,
        capnhatdautien: 0,
        capnhatgannhat: 0,
      },popupComponent: SearchAccount,
      datathuebao :{
        loaithuebaoId :"",
        somay:""
      },
      type:0,
      thuebaoDaChon: null,
      dataSelected :{
         soMay :"",
          dichVu:"",
          loaiHinh:"",
          tocDo:"",
          id:"",
          tenChuHo:"",
          diachi:"",
          soCMND:"",
          ngaycap:"",
          sodt:"",
          email:"",
          ngaysinh:"",
          ghichu:"",
          soluongphong:"",
          nhanvienthuthap:"",
          ngaythuthap:"",
          nguoiCn:"",
        mayCn:"",
        thuebaoId:"",
      },      dataTheoPhong :{
        },
      maTb:"",
      thuebaoId:"",
      doituongId:"",
      waterMark: '',
      dateVal: new Date(),
      dateFormat: 'dd/MM/yyyy',
      modelChiTietThueBao:{},
      modelSearchThueBao:{},
      resolvePromise: null,
      rejectPromise: null,
      key:1,
      dsNguoiThue:[],
      dsThueBao :[]
    }
  },
  methods :{
    changeLoaiThueBao: function(data,item){

    },
    xacnhanthuebao:function (){
    }
  ,async xoaCaNhanThueTro(data) {
    this.$bvModal.msgBoxConfirm("Xác nhận loại thuê bao khỏi danh sách?",
      {
        title: "",
        centered: true,
        size: "sm",
        okTitle: 'Đồng ý',
        cancelTitle: 'Hủy',
      }).then(async (value) => {
      if (value) { await this.$root.context.post(
        `/web-cabman/quanly-nhatro/DELETE_CANHAN_THUEPHONG_TRO`,
        {
          rowId : data.ID,
          nhanvienid :  this.$root.token.getNhanVienID()
        }
      ).then(res =>{
        if(res.data.result == true){
          this.$root.toastSuccess("Xóa nhà trọ thành công.")
          this.loadThongTinPhongTro()
        }
      }).catch(err=>{
        if(err.response.data.message_detail){
          this.$root.toastError(err.response.data.message_detail)
        }else {
          this.$root.toastError(err.response.data.message)
        }
      }).finally(()=>{
        this.loading(false)
      }) }
    })
  },
    DiaChiXacNhan(data){
      this.dataSelected.diachi = data.diachimoi
    },  popupClosed: async function (data) {
      // if(data.ma_tb) {
      //   this.dataSelected.soMay = data.ma_tb ;
      // }
      this.dataSelected.soMay = data.ma_tb ? data.ma_tb : "";
      this.maTb = data.ma_tb ? data.ma_tb : "";
      this.thuebaoId = data.thuebao.thuebao_id ? data.thuebao.thuebao_id : "";
      this.doituongId = data.thuebao.doituong_id ? data.thuebao.doituong_id : "";
      await this.layChiTietThueBao(this.thuebaoId)
      this.$refs.popupSearchAccount.hide();

      this.modelSearchThueBao.MA_TB = this.maTb
      this.modelSearchThueBao.THUEBAO_ID = this.thuebaoId
      this.modelSearchThueBao.NHOMTHUE_ID = this.dataAll.NHOMTHUE_ID
      this.$refs.ThemMoiPhongTro.show(this.modelSearchThueBao).then(result => {
        if(result.ok ==true && result.data ==true ){
          this.loadThongTinPhongTro()
        }
      })
    }, linkTaiFileMau_Click: function () {
      window.location.href = "/static/files/MauFile_Import_PhongTro.xlsx";
    },loadThongTinPhongTro: async function(){
      this.loading(true)
      await this.$root.context.get(
        "/web-cabman/quanly-nhatro/NAP_DS_CANHAN_THUE_PHONGTRO?nhatro_id=" + this.dataAll.ID
      ).then(res =>{
        if(res.data.length >0){
          this.dsNguoiThue = res.data
          this.dsNguoiThue.forEach(item => {
            if(item.NGAYHETHANTRO!=null) {
              item.NGAYHETHANTRO = moment(item.NGAYHETHANTRO).format('DD/MM/YYYY')
            }
          })
        }else {
          this.dsNguoiThue = []
        }
      }).catch(e=> {throw e}).finally(()=> this.loading(false))
    },async xacnhanChonThueBao() {
      this.loading(true)
      await this.getDsThueBao()
    },showSearchAccount() {
      this.$refs.popupSearchAccount.show();
    },donShowSearchAccount(){
      this.$root.toastError("Không được thay đổi thông tin thuê bao sử dụng");
    },
    onSelectedRowChanged(data){

    },
    clearSoMay(){
      this.dataSelected.soMay = ''
    },
    show (isEdit,data) {
      return new Promise((resolve, reject) => {
        this.$bvModal.show("suanhatro")
        console.log("sửa nahf trọ ",data)
        this.dataAll = data
        this.dsNguoiThue =[]
        this.showThongTinNhaTroEdit(data.NHOMTHUE_ID)
        this.loadThongTinPhongTro()
        this.getCboDichVu()
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },getDsThueBao: async function () {
      this.loading(true)
      await this.$root.context.post(
        '/web-cabman/danhmuc/layDsThueBaoTheoMaTB',
        {
          maThueBao: this.datathuebao.somay,
          kieu: 0
        }
      ).then(data =>{
          if(data.data.length>0){
            this.dsThueBao = data.data
            this.$bvModal.show('modal-ChonThueBao')
          }else{
            this.$root.toastError("Không tìm thấy thuê bao cần tìm.");
        }
      }).catch(() =>  this.$root.toastError("Không tìm thấy thuê bao cần tìm.")).finally(()=>{
        this.loading(false)
      })
    },getCboDichVu: async function () {
      await this.$root.context.get(
        '/web-cabman/quanly-nhatro/NAP_CBO_DICHVU',
      ).then(data =>{
        this.cboDichVu = data.data
        if(data.data.length >0){
          this.datathuebao.loaithuebaoId = -1
        }
      })

    },closeModal(){
      ++this.key
      this.resolvePromise({ok: false, data: null})
      this.$bvModal.hide("suanhatro")
    }, ghiLai: async function () {
      if (!this.dataSelected.tenChuHo) {
        this.$root.toastError("Tên chủ hộ không được để trống!")
        return
      }
      if (!this.dataSelected.diachi) {
        this.$root.toastError("Địa chỉ không được để trống!")
        return
      }
      if (!this.dataSelected.soluongphong) {
        this.$root.toastError("Số lượng không được để trống!")
        return
      }
      await this.edit_phongtro()
    }, popupChonThueBao_ok: async function () {
      this.$root.showLoading(true)
      try {
        console.log("chọn xong rồi làm gì đó đi " , this.thuebaoDaChon)
        await this.layChiTietThueBao(this.thuebaoDaChon.THUEBAO_ID)
        this.modelSearchThueBao.MA_TB = this.thuebaoDaChon.MA_TB
        this.modelSearchThueBao.THUEBAO_ID =  this.thuebaoDaChon.THUEBAO_ID
        this.modelSearchThueBao.NHOMTHUE_ID = this.dataAll.NHOMTHUE_ID
        this.$refs.ThemMoiPhongTro.show(this.modelSearchThueBao).then(result => {
          if(result.ok ==true && result.data ==true ){
            this.loadThongTinPhongTro()
          }
        })
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    }, popupChonThueBao_selectedRowChanged: function (dataItem) {
      console.log(dataItem)
      this.thuebaoDaChon = dataItem
    },
    edit_phongtro:async function(){
      this.loading(true)
      let ngay_cap ;
      let ngay_sn;
      if(this.dataSelected.ngaycap){
        ngay_cap = moment( this.dataSelected.ngaycap).format('DD/MM/YYYY')
      }
      if(this.dataSelected.ngaycap){
        ngay_sn = moment( this.dataSelected.ngaysinh).format('DD/MM/YYYY')
      }
      if(!this.dataSelected.nhomthueId){
        return
      }
      await this.$root.context.post(
        `/web-cabman/quanly-nhatro/sua-nhatro`,{
          row_id: this.dataSelected.id,
          thuebao_id: this.dataSelected.thuebaoId,
          ten_chuho: this.dataSelected.tenChuHo,
          diachi: this.dataSelected.diachi,
          so_dt: this.dataSelected.sodt,
          so_gt: this.dataSelected.soCMND,
          email: this.dataSelected.email,
          ngay_cap: ngay_cap ,
          ngay_sn: ngay_sn ,
          nguoi_cn: this.$root.token.getUserName(),
          ghi_chu: this.dataSelected.ghichu,
          quy_mo: this.dataSelected.soluongphong
        }
      ).then(res =>{
        if(res.data.length>=0){
          this.$toast.success('Sửa nhà trọ thành công.')
          this.resolvePromise({ok: true, data: null})
        //  this.$bvModal.hide("suanhatro")
        }
      }).catch(e=> {    this.$toast.error('Sửa nhà trọ thất bại.')
      }).finally(()=> this.loading(false))
    },
    layChiTietThueBao: async function (thuebao_id){
      this.loading(true)
      await this.$root.context.get(
        "/web-cabman/quanly-nhatro/SP_GET_CHITIET_THUEBAO?thuebao_id=" + thuebao_id
      ).then(res =>{
        if(res.data.length>0){
            //Danh sách thuê bao theo phòng
            this.modelSearchThueBao = res.data[0]
        }
      }).catch(e=> {throw e}).finally(()=> this.loading(false))
    },
    async showThongTinNhaTroEdit(nhaTroId){
      this.loading(true)
      await this.$root.context.get(
        "/web-cabman/quanly-nhatro/NAP_DS_SUA_PHONGTRO?nhatro_id=" + nhaTroId
      ).then(res => {
        if (res.data.length > 0) {
          //Form chính
          this.modelChiTietThueBao = res.data[0]
          this.dataSelected.dichVu = res.data[0].TEN_DVVT
          this.dataSelected.loaiHinh = res.data[0].LOAIHINH_TB
          this.dataSelected.tocDo = res.data[0].TOCDO
          this.dataSelected.tenChuHo = res.data[0].TENCHUHO
          this.dataSelected.diachi = res.data[0].DIACHI
          this.dataSelected.sodt = res.data[0].SO_DT
          this.dataSelected.email = res.data[0].EMAIL
          this.dataSelected.soCMND = res.data[0].SO_GT
          this.dataSelected.thuebaoId = res.data[0].THUEBAO_ID
          this.dataSelected.soMay = res.data[0].MA_TB
          if(res.data[0].NGAYCAP){
            this.dataSelected.ngaycap = moment(res.data[0].NGAYCAP).format('DD/MM/YYYY')
          }
          this.dataSelected.ghichu = res.data[0].GHICHU
          this.dataSelected.mayCn = res.data[0].NGAY_CN
          this.dataSelected.nguoiCn = res.data[0].NGUOI_CN
          this.dataSelected.nhomthueId = res.data[0].NHOMTHUE_ID
          if(res.data[0].NGAY_SN){
            this.dataSelected.ngaysinh = moment(res.data[0].NGAY_SN).format('DD/MM/YYYY')
          }
          this.dataSelected.nhanvienthuthap = res.data[0].TEN_NV
          if(res.data[0].NGAY_INS){
            this.dataSelected.ngaythuthap = moment(res.data[0].NGAY_INS).format('DD/MM/YYYY')
          }
          this.dataSelected.soluongphong = res.data[0].QUIMO
          this.dataSelected.id = res.data[0].ID
          this.doituongId = res.data[0].DOITUONG_ID
          this.maTb = res.data[0].MA_TB
        }
      }).catch(e=> {throw e}).finally(()=> this.loading(false))
    },
    async showModal() {
    },checkfile: function () {
    if (this.fileExcel == null) {
      this.$root.toastError("Chưa chọn file excel")
      return false
    }
    if (
      this.fileExcel.type !== "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" && this.fileExcel.type !== "application/vnd.ms-excel"
    ) {
      this.$toast.error("File không đúng định dạng cho phép (xls, xlsx) hoặc không đúng biểu mẫu vui lòng thử lại")
      return false
    }
    return true
  }, kiemTraDuLieu(tencot) {
    if (!(tencot.includes("Số máy/Account VNPT") && tencot.includes("Mã phòng") && tencot.includes("Ngày hết hạn thuê(dd/MM/yyyy)")&& tencot.includes("Ghi chú") )) {
      this.$root.toastError("File không đúng cấu trúc, vui lòng tải file mẫu để đúng định dạng.")
      return false
    }
    return true
  }, async txtDuongDan_ButtonClick() {
    let tencot
    try {
      this.loading(true)
      if (this.checkfile()) {
        let s = this.fileExcel.name.toLowerCase();
        if (s.endsWith(".xlsx") || s.endsWith(".xls")) {
          let reader = new FileReader();
          reader.onload = async (e) => {
            /* Parse data */
            const data = e.target.result;
            const workBook = XLSX.read(data, {type: "binary"});
            if (workBook != null) {
              for (let i = 0; i < workBook.SheetNames.length; i++) {
                const sheetName = workBook.SheetNames[i];
                this.vds_upload = XLSX.utils.sheet_to_json(
                  workBook.Sheets[sheetName]
                );
                if(this.vds_upload.length<= 0){
                  this.$root.toastError("Dữ liệu file excel không có giá trị.")
                  return
                }

                if(Object.keys(this.vds_upload[0]).length !==4){
                  this.$root.toastError("Dữ liệu file excel chưa đúng số lượng cột, vui lòng tải file mẫu.")
                  return
                }
              }
              tencot = Object.keys(this.vds_upload[0])

              if (this.kiemTraDuLieu(tencot)) {
                console.log(" danh sách excel nè", this.vds_upload)
                this.$refs.ThemExcel.show(this.fileExcel,this.vds_upload,this.dataSelected.tenChuHo,this.dataAll.NHOMTHUE_ID).then(result => {
                  if(result.data ==true && result.ok ==true){
                    this.loadThongTinPhongTro()
                  }
                })
              }
            }
          }
          reader.readAsBinaryString(this.fileExcel)
        }
      }
    } catch (e) {
      console.log(e)
    } finally {
      this.loading(false)
    }
  }}
  ,watch :{
    dsNguoiThue (val){
      console.log(val.length)
      this.titleStatus ="Chưa nhập thuê bao"
      if(val.length == 0){
        this.titleStatus ="Chưa nhập thuê bao"
      }else {
        this.titleStatus = val.length + " thuê bao"
      }
    },fileExcel(val){
      this.txtDuongDan_ButtonClick()
    }

  }
}
</script>

<style scope>
left-line {
  border-left: double;
  padding-left: 10px;
}
.modal-xxl{
  max-width: 80% !important;
}
.sua-nha-tro .gutter {
  background-color: transparent;
}
.bss-modal-tracuu-danhba {
}
.bss-modal-tracuu-danhba .breadcrumb-top {
  display: none;
}
.bss-modal-tracuu-danhba.bss-modal .modal-body .list-actions-top {
  position: inherit;
}
.vl {
  border-left: 6px solid #0176FF;
  height: 34px;
  position: absolute;
  /* left: 50%; */
  margin-left: -24px;
  top: 0;
}
.column {
  float: left;
  width: 16.6%;
}
.row:after {
  content: "";
  display: table;
  clear: both;
}
</style>
