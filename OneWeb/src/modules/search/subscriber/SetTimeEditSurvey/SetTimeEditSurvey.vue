<style scoped src="./SetTimeEditSurvey.scss"></style>
<template>
  <b-modal
    id="popup-thietlap-tg-suaphieu-ksat"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
          <div class="title"><span class="icon one-notepad"></span>Thiết lập thời gian sửa phiếu khảo sát</div>
          <div
            class="close -ap icon-close"
            data-dismiss="modal"
            @click="$bvModal.hide('popup-thietlap-tg-suaphieu-ksat')"
          ></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click="btnNhapmoi_Click">
                    <span class="icon one-file-plus"></span> Nhập mới
                </a>
            </li>
            <li>
                <a href="#"  @click="btnGhilai_Click">
                    <span class="icon one-save"></span> Ghi lại
                </a>
            </li>            
            <li>
                <a href="#" @click="btnDelete_Click">
                    <span class="icon one-trash"></span> Xóa
                </a>
            </li>
            <li>
                <a href="#" @click="btnTimkiem_Click">
                    <span class="icon one-search"></span> Tìm kiếm
                </a>
            </li>                        
        </ul>
        <ul class="list" style="position:absolute; right:0">
            
            <li>
                <a href="#" @click="$bvModal.hide('popup-thietlap-tg-suaphieu-ksat')">
                    <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Đóng
                </a>
            </li>           
        </ul>
      </div>
      <div class="popup-body">
        <div class="row">
            <div class="popup-header col-sm-12 col-12">
                THIẾT LẬP THỜI GIAN SỬA PHIẾU KHẢO SÁT CHO ĐIỆN THOẠI VIÊN HỌC VIỆC
            </div>
        </div>
        <div class="box-form">
            <div class="legend-title">Thông tin thiết lập</div>
            <div class="info-row">
                <div class="key">Điện thoại viên</div>
                <div class="value">
                    <div class="select-custom">
                        <ComboboxGrid id="dthoaivien_id" ref="cboGridLoaiKH" v-bind:columns="[
                            {fieldName: 'ma_nv', headerText: 'Mã nhân viên', allowFiltering: true},
                            {fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowFiltering: true}                        
                        ]"
                                  v-bind:dataSource="options.cbo_dthoaivien"       
                                  v-model="dthoaivien_id"
                                  :value="dthoaivien_id"                     
                                  textField="ten_nv"
                                  valueField="nhanvien_id"                                  
                                  >
                        </ComboboxGrid>                        
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key">Số phút</div>
                <div class="value">
                    <input type="number" class="form-control" v-model="so_phut">
                </div>
            </div>
            <div class="info-row">
                <div class="key">Ngày kết thúc</div>
                <div class="value">
                    <div class="input-icon-right">
                        <flat-pickr id="ngay_kt" class="form-control" :config="dateconfig" v-model="ngay_kt"/>
                        <span class="icon one-calendar"></span>
                    </div>
                </div>
            </div>
            <div class="legend-title mart20">
                <div class="title">Danh sách điện thoại viên</div>
            </div>
            <div class="table-content">
                <DataGrid v-bind:columns="gvDSDTVien.tableCollumns" :showFilter="true" ref="gvDSDTVien"
                        :allowPaging="true" :enabledSelectFirstRow="false" :enable-paging-server="false"                        
                        v-bind:dataSource="gvDSDTVien.tableData" panelDataHeight="250"
                        @selectedRowChanged="gvDSDTVien_rowSelected"  
                        >
                </DataGrid>
            </div>
            
        </div>
    </div>
    </div>
  </b-modal>
</template>
<script>
import api from "./api/index.js";
import ComboboxGrid from "./components/ComboboxGrid";
import moment from 'moment'
export default {
  name: 'SetTimeEditSurvey',
  components: {
        ComboboxGrid,
    },
  mounted () {
    this.init()
  },
  destroyed () {},
  computed: {
    rows () {
      return this.items.length
    }
  },
  data () {
    return {
      options :{
          cbo_dthoaivien:[]
      },      
      dthoaivien_id:0,
      selected_dthoaivien_id:0,
      ngay_kt:moment(new Date()).format('DD/MM/yyyy'),
      so_phut:0,
      gvDSDTVien: {
        tableCollumns: [
            {
                fieldName: "ten_nv",
                headerText: "Điện thoại viên",
                allowFiltering: true,
                allowSorting: false,                               
            },
            {
                fieldName: "tg_sua_ks",
                headerText: "Số phút",
                allowFiltering: true,
                allowSorting: false,
                textAlign:'Right',
                format : 'N0',
                type : "Number"
            },
            {
                fieldName: "ngay_kt",
                headerText: "Ngày kết thúc",
                allowFiltering: true,
                allowSorting: false,
                format: {type: 'date', format: 'dd/MM/yyyy'},
                type: 'date',
                textAlign:'Right'
            }
        ],
        tableData: []
      },
      dateconfig: {
            altFormat: "d/m/Y",
            altInput: true,
            dateFormat: "d/m/Y",
            allowInput: true,
      },
      
    }
  },
  methods: {
    alert: function () {
      alert('Hello')
    },
    async init(){
        let data= {"vchuoi":'DIENTHOAI_VIEN'}
        try {     
            await api.lay_danhmuc_giamthinh(this.axios, data).then((response) => {        
                console.log(response)
                if (response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {  
                    this.options.cbo_dthoaivien = response.data.data.dienthoai_vien
                    console.log('lay_danhmuc_giamthinh')
                    console.log(response.data.data.dienthoai_vien)
                    this.dthoaivien_id = response.data.data.dienthoai_vien[0].nhanvien_id
                }
            });
            this.HienThi_DS_NV(false) 
        } catch (error) {
            console.log(error)
        } 
    },
    async HienThi_DS_NV(vFind){
        this.gvDSDTVien.tableData = []
        this.selected_dthoaivien_id = 0
        //if (this.dthoaivien_id==0) return//thông báo
        let data= {"vdtvien_id":this.dthoaivien_id}
        try { 
            this.gvDSDTVien.tableData = []    
            await api.ds_tgks_nhanvien(this.axios, data).then((response) => {        
                console.log(response)
                if (response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {  
                    console.log('ds_tgks_nhanvien')
                    this.gvDSDTVien.tableData = response.data.data                    
                }
                else
                {
                    if (vFind)
                        this.$toast.error("Không tìm thấy thông tin!");                        
                    this.so_phut = ''
                    this.ngay_kt=''                    
                    return;
                }

            }); 
        } catch (error) {
            console.log(error)
        } 
    },
    async btnNhapmoi_Click(){
        this.so_phut = 0
        this.ngay_kt=''  
        this.dthoaivien_id=0
    },
    async btnTimkiem_Click(){
        this.HienThi_DS_NV(true)
    },
    async btnGhilai_Click(){
        if (this.dthoaivien_id==0){
             this.$toast.error("Vui lòng chọn Điện thoại viên !");
             $('#dthoaivien_id').focus()
             return
        }
        if (this.ngay_kt == null || this.ngay_kt==''){
            this.$toast.error("Vui lòng nhập ngày kết thúc áp dụng !");
             $('#ngay_kt').focus()
             return
        }
        if (this.so_phut && this.so_phut.length>5){
            this.$toast.error("Độ dài trường số phút không được lớn hơn 5!");
             $('#so_phut').focus()
             return
        }   
                
                
        let data= {
            "nhanvien_id": this.dthoaivien_id,
            "vngay_kt": moment(this.ngay_kt,'DD/MM/yyyy').format('DD/MM/yyyy'),
            "vtg_sua_ks": this.so_phut
        }
        try {     
            await api.capnhat_tgks_nhanvien(this.axios, data).then((response) => {        
                console.log(response)
                if (response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {  
                    console.log('btnGhilai_Click')
                    //this.gvDSDTVien.tableData = response.data.data  
                    this.$toast.success("Cập nhật thời gian thành công!"); 
                    this.dthoaivien_id =0
                    this.so_phut = 0
                    this.ngay_kt = ''
                    this.HienThi_DS_NV(false);                  
                }  
                else {
                    this.$toast.error("Có lỗi: "+response.data.message);
                }              

            }); 
        } catch (error) {
            console.log(error)
        }
    },
    async btnDelete_Click(){        
        if (this.selected_dthoaivien_id==0){
             this.$toast.error("Chưa chọn dữ liệu Điện thoại viên để xóa !");             
             return
        }
        if (!window.confirm("Bạn thật sự muốn xóa dòng này?")) {
            return
        }
        let data = {
            "nhanvien_id": this.selected_dthoaivien_id,
            "phanvung_id": this.$root.token.getPhanVungID()
        }
        try {     
            await api.delete_tgks_nhanvien(this.axios, data).then((response) => {        
                if (response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {  
                    console.log('btnDelete_Click')                    
                    this.$toast.success("Xóa dữ liệu thành công!"); 
                    this.dthoaivien_id =0
                    this.selected_dthoaivien_id =0
                    this.so_phut = 0
                    this.ngay_kt = ''
                    this.HienThi_DS_NV(false);                  
                }
                
            }); 
        } catch (error) {
            console.log(error)
        }
    },
    async gvDSDTVien_rowSelected(row){
        console.log(row)
        if (!row) return
        let item = row
        this.so_phut = item.tg_sua_ks
        this.ngay_kt = moment(item.ngay_kt,'yyyy-MM-DD HH:mm:ss').format('DD/MM/yyyy')
        this.dthoaivien_id = item.nhanvien_id  
        this.selected_dthoaivien_id = item.nhanvien_id        
    }
        
  },
   watch: {        
        'dthoaivien_id': async function (newValue, oldValue) {
            //this.HienThi_DS_NV(false)
        },
    }
}
</script>
