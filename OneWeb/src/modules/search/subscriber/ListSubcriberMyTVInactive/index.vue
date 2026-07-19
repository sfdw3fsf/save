<template>
<div>
  <breadcrumb :header="header" />
  <vue-nav>
    <ul class="list">
      <li>
        <a @click="tsbtnTraCuu_Click">
          <span class="icon one-file-search"></span>Tra cứu
        </a>
      </li>
      <li>
        <a @click="tsbtnXuatExcel_Click" :class="{ disabled: isDisXuatExcel}">
          <span class="icon one-download"></span>Xuất file
        </a>
      </li>
    </ul>
  </vue-nav>
  <div class="page-content">
     <vue-card label="Thông tin tra cứu">
      <b-row>
        <b-col sm="4" cols="12">
        <div class="info-row">
          <div class="key nowrap">Số ngày không sử dụng</div>
          <div class="value">
               <div class="input-number-custom">
                    <input type="number" v-model="txtNgay" class="form-control tright">
                </div>

          </div>
        </div>
        </b-col>
        <b-col sm="8" cols="12">
         <div class="note mart7"> *Số ngày liên tiếp tính tới thời điểm hiện tại thuê bao chưa đăng nhập lại. </div>
        </b-col>        
      </b-row>
    </vue-card>
    <vue-card label="Danh sách thuê bao">
      <DataGrid
                    v-bind:dataSource="table.data"
                    v-bind:columns="table.config"
                    ref="danhsach"
                    :showFilter="true"
                    :enablePagingServer="true"               
                    :enabledSelectFirstRow="true"
                    :allowPaging="true"
                    :pageIndex="pageIndex"
                    :pageSize="pageSize"
                    :totalRecords="totalRecords"
                    @pageChanged="pageChanged"
                />
    </vue-card>
    <ExportDataModal ref="exportDataModal" :data="this.table.data"></ExportDataModal>
  </div>
</div>
</template>
<script>
import breadcrumb from '@/components/breadcrumb'
import ExportDataModal from '../../../contract/profile/ExportData/ExportDataModal.vue'
import api from './api'
export default {
  components: {
    breadcrumb,
    ExportDataModal,
  },
  watch:{
    txtNgay(){
      if(this.txtNgay == null || this.txtNgay == '' || this.txtNgay < 0){
        this.txtNgay = 0;
      }
      else{
        this.txtNgay = this.txtNgay.replace(/^0+/, ''); 
      }    
    },
    table:{
      handler:function(){
        if(this.table.data.length > 0 ){
          this.isDisXuatExcel = false;
        }
        else{
          
          this.isDisXuatExcel = true;
        }
      },
      deep:true,
    }

  },
  data () {
    return {

       header: {
        title: 'DANH SÁCH THUÊ BAO MYTV HOẠT ĐỘNG, KHÔNG SỬ DỤNG',
        list: [
          { name: 'Tra cứu', link: { name: 'Ui.cards' } },
          {
            name: 'Phát triển thuê bao',
            link: { name: 'Ui.cards' }
          },
          {
            name: 'Thống kê báo cáo visa',
            link: { name: 'Ui.cards' }
          },
          {
            name: 'Danh sách thuê bao MyTV không hoạt động',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      pageSize: 10,
      pageIndex: 0,
      totalRecords: 0,
      totalPages:0,
      txtNgay:3,
      isDisXuatExcel:true,

      table: {
        loading: false,
        data: [

        ],
        config: [
          {
            headerText: 'STT',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'stt'
          },
          {
            headerText: 'Mã tỉnh',
            allowFiltering: true,
            allowSorting: true,
            fieldName: ''
          },
          {
            headerText: 'Account MyTV',
            allowFiltering: true,
            allowSorting: true,
            isPrimaryKey: true,
            fieldName: 'Username'
          },
          {
            headerText: 'Họ và tên',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'ten_tb'
          },
          {
            headerText: 'Ngày sinh',
            allowFiltering: true,
            allowSorting: true,
            format:"dd/MM/yyyy",
            type: 'date',
            textAlign: 'right',
            fieldName: 'ngay_sn'
          },
          {
            headerText: 'Địa chỉ',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'diachi_ld'
          },
          {
            headerText: 'Điện thoại',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'sdt_lienhe'
          },
          {
            headerText: 'Gói cước',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'tocdo'
          },
          {
            headerText: 'Gói dịch vụ',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'tengoi'
          },
          {
            headerText: 'Thời gian đăng ký',
            format:"dd/MM/yyyy",
            type: 'date',
            textAlign: 'right',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'DateTime'
          },
          {
            headerText: 'Lần dùng cuối',
            format:"dd/MM/yyyy HH:mm:ss",
            allowFiltering: true,
            type: 'date',
            textAlign: 'right',
            allowSorting: true,
            fieldName: 'LastLogin'
          },
          {
            headerText: 'Chương trình xem cuối',
            allowFiltering: true,
            allowSorting: true,
            fieldName: 'ContentName'
          },
          
        ],
        
      }
    }
  },
  methods:{
    tsbtnXuatExcel_Click(){
      this.$refs.exportDataModal.showModal();
    },
   async pageChanged(args) {
      console.log(args);
      try{
              if(this.pageSize == args.pageSize){
                  this.loading(true);
                  this.pageIndex = args.pageIndex;  
                  await this.mytvUserNotLoginGetV2(); 
                  this.loading(false); 
              }
              else{
                this.loading(true);
                this.pageSize = args.pageSize;
                this.pageIndex = 0; 
                this.totalRecords = 0;
                await this.mytvUserNotLoginGetV2();
                this.loading(false); 
          }
      }
      catch(e){
        this.loading(false);
      }
     
      
      
    },
   async tsbtnTraCuu_Click(){
     this.pageIndex = 0;
     this.totalRecords = 0;
     try{
        this.loading(true);
        
        await this.mytvUserNotLoginGetV2();
        this.loading(false);
     }
     catch(e){
       console.log(e);
       this.loading(false);
     }
     
    },
    async mytvUserNotLoginGetV2(){
      try{
          
          let res = await api.mytvUserNotLoginGetV2(this.axios, '/tichhop/mytv/mytvUserNotLoginGetV2/' + this.txtNgay + '/' + this.pageSize + '/' + (this.pageIndex + 1));
          
          if(res.data.errorCode == 0 && res.data.data.Contents.Content){
            let listAccount = "";
            let result1 = res.data.data.Contents.Content;
            //this.table.data = res.data.data.Contents.Content;
            result1.forEach((obj,index)=>{
              obj.stt = this.pageSize * this.pageIndex + index + 1;
              listAccount += obj.Username + ",";
            })
            let infoUsername = await api.sp_lay_tt_tb_brcd(this.axios,{
              "ds_ma_tb":listAccount.substring(0,listAccount.length-1),
            })
            let result2 = [];
            if(infoUsername.data.error_code === "BSS-00000000" && infoUsername.data.data){
                result2 = infoUsername.data.data;
            }
            let newResult = [];
            if(result2.length > 0){
              result1.forEach((obj1)=>{
                let obj2 = result2.filter(item => item.username == obj1.Username);
                if(obj2){
                  let newObj = {
                      ...obj1,
                      ...obj2[0]
                  };
                  newResult.push(newObj)
                }
              })
              
              this.table.data = newResult;
            }
            else{
              this.table.data = result1;
            }       
            this.$set(this.table.data);
            let getLastPageRecords = await api.mytvUserNotLoginGetV2(this.axios, '/tichhop/mytv/mytvUserNotLoginGetV2/' + this.txtNgay + '/' + this.pageSize + '/' + res.data.data.TotalPage);
            let resultData = getLastPageRecords.data.data.Contents.Content;
            if(resultData && resultData.length != undefined){
              this.totalRecords = this.pageSize * (res.data.data.TotalPage - 1) + getLastPageRecords.data.data.Contents.Content.length;
            }
            else if(resultData && resultData.length == undefined){
              this.totalRecords = this.pageSize * (res.data.data.TotalPage - 1) + 1;
            }
          }
          else{
            this.table.data = [];
            this.totalRecords = 0;
            this.pageIndex = 0;
          }
          
      }
      catch(e){
        console.log(e);
        
        this.table.data = [];
        this.totalRecords = 0;
        this.pageIndex = 0;
      }
      
     
    }
  },
  mounted(){  
    this.isDisXuatExcel = true;       
  }
}
</script>