<template  src="./main.html">

</template>

<script>

import moment from 'moment'
var itemTrangThai= function () {
  return {
    template: {
      template: `
        <div class="table-result ghi-chu" style="background: transparent;">
        <div style="display: inline-flex;align-items: center;justify-content: space-around;color: red" class="mb-1 ghi-chu-item"  v-show="!readyIcon">
          <div style="margin-left: 3px;margin-right: 5px" class="pull-left">
            <span class="icon one-file-check2"></span>
          </div>
          <div class="pull-right">{{ data.TRANGTHAI }}</div>
        </div>

        <div style="display: inline-flex;align-items: center;justify-content: space-around;" class="mb-1 ghi-chu-item"
             v-show="readyIcon">
          <div style="margin-left: 3px;margin-right: 5px" class="pull-left">
            <span class=" -ap icon-close"></span>
          </div>
          <div class="pull-right">{{ data.TRANGTHAI }}</div>
        </div>
        </div>`,
      data() {
        return {
          data: { },
          textChange: '',
          ready : false
        }
      },
      computed: {
        parent() {
          return this.$parent.$parent.$parent.$parent.$parent.$parent.$parent
        },
        readyIcon(){
          console.log(this.data.TRANGTHAI)
          return this.data.TRANGTHAI.includes("Sẵn sàng")
        }
      },methods:{

      },mounted() {
      }
    }
  }
}
export default {
  name: '', provide: {
  },
  components :{moment},
  computed: {
  },
  data () {
    return {
      listData : [],
      TenChuHo:"",
      fileExcel: null,
      resolvePromise: null,
      rejectPromise: null,
      itemTrangThai:itemTrangThai,
      nhomthueId: null
    }
  },
  methods :{
    async naplai() {
      await this.loadThongTinTBInit()
    },
    async thucthi(){
      this.loading(true)
      // let ngayHetHanThue ;
      // if(this.dataSelected.ngayHetHanThue){
      //   ngayHetHanThue = moment( this.dataSelected.ngayHetHanThue).format('DD/MM/YYYY')
      // }
      // console.log(ngayHetHanThue)
      for (const item of this.listData) {
        this.loading(true)
        if(item.TRANGTHAI_ID == 1){
          await this.$root.context.post(
            `/web-cabman/quanly-nhatro/insert_phongtro`,{
              nhomthue_id: this.nhomthueId,
              thuebao_id: item.THUEBAO_ID,
              quy_mo: item.MA_PHONG,
              // ngay_hethan_tro: ngayHetHanThue,
              nguoi_cn: this.$root.token.getUserName(),
              nhanvien_id: this.$root.token.getNhanVienID(),
              ghi_chu: item.GHICHU
            }
          ).then(res =>{

          }).catch(e=> {
          }).finally(()=> this.loading(false))
        }
        }
      this.$toast.success( "Tiến trình cập nhật hoàn thành")
      this.resolvePromise({ok: true, data: true})
      this.$bvModal.hide("themexcel")
    },
    showModal(){

    },closeModal(){
      this.$bvModal.hide("themexcel")
    },encryptList(){
      this.listData.forEach(item=>{
        item.GHICHU = item['Ghi chú'];
        delete item['Ghi chú'];
        item.MA_PHONG = item['Mã phòng'];
        delete item['Mã phòng'];
        item.NGAY_HETHAN = item['Ngày hết hạn thuê(dd/MM/yyyy)'];
        delete item['Ngày hết hạn thuê(dd/MM/yyyy)'];
        item.MA_TB = item['Số máy/Account VNPT'];
        delete item['Số máy/Account VNPT'];
      })
    },grid_selectedRowChanged(data){

    }, layChiTietThueBao: async function (matb){
      this.loading(true)
      await this.$root.context.get(
        "/web-cabman/quanly-nhatro/SP_GET_CHITIET_THUEBAO_BY_MA_TB?ma_tb=" + matb
      ).then(res =>{
        if(res.data.length>0){
          //Danh sách thuê bao theo phòng
          console.log(res.data)
        }
      }).catch(e=> {throw e}).finally(()=> this.loading(false))
    },
  show (fileExcel,data,tenchuho,nhomthue_id) {
    return new Promise(async (resolve, reject) => {
    this.fileExcel =[...fileExcel]
      console.log(this.fileExcel)
      this.TenChuHo =tenchuho
      this.listData = [...data]
      this.nhomthueId = nhomthue_id
      if (data.length > 0) {
        this.encryptList()
      }
      await this.loadThongTinTBInit()
      console.log(this.fileExcel)
      this.$bvModal.show("themexcel")
      this.resolvePromise = resolve
      this.rejectPromise = reject
    })
  }, linkTaiFileMau_Click: function () {
      window.location.href = "/static/files/MauFile_Import_PhongTro.xlsx";
    }, grid_dataGridCellCreated(e) {
      console.log(e)
      e.cellAllowHtml = true;
      if(!e.cellDataItem.THUEBAO_ID){
        $(e.cellElement).empty();
        $(e.cellElement).append(
          '<span style="color: red;stroke: red;stroke-width: 5px"><i class=" -ap icon-close"></i></span>'
        );
      }
    },async loadThongTinTBInit() {
      if (this.listData.length < 1) {
        return
      } else {
        for (const item of this.listData) {
          this.loading(true)
          await this.$root.context.get(
            "/web-cabman/quanly-nhatro/SP_GET_CHITIET_THUEBAO_BY_MA_TB?ma_tb=" + item.MA_TB
          ).then(res =>{
            if(res.data.length>0){
              //Chỉnh sửa danh sách thuê bao sau đó
              console.log(res.data)

                item.TRANGTHAI_ID = 1
                item.TRANGTHAI = "Sẵn sàng"
                item.THUEBAO_ID = res.data[0].THUEBAO_ID
                item.THUONGHIEU = res.data[0].THUONGHIEU
                item.TEN_TB = res.data[0].TEN_TB
                item.TEN_DVVT = res.data[0].TEN_DVVT
                item.LOAIHINH_TB = res.data[0].LOAIHINH_TB
                item.DIACHI_LD = res.data[0].DIACHI_LD
            }  else {
              item.TRANGTHAI = "Không tồn tại thuê bao có mã " + item.MA_TB
              item.TRANGTHAI_ID = 0
              item.MA_TB = ""
            }
          }).catch(e=> {throw e}).finally(()=> this.loading(false))
        }
      }
    }
  },watch :{

  }
}
</script>

<style scope>

</style>
