<template src="./ProcessOverdueNumber.html"></template>
<style src="./ProcessOverdueNumber.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component';
import ProcessOverdueNumberAPI from './ProcessOverdueNumberAPI'
import gridview from '@/components/Shared/gridview'
import XLSX from 'xlsx'

//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//
export default {
  components: {breadcrumb,VueFlatPickr,gridview,XLSX },
  name: 'ProcessOverdueNumber',
  mounted () {
    this.listbox.ds_thuebao.list = [];
    
    //this.listbox.ds_thuebao.header = [{name:'maTb',text:'Số máy'},{name:'tenTb',text:'Tên thuê bao'},{name:'ngaySd',text:'Ngày sử dụng'},{name:'tgThueSo',text:'Thời gian thuê (tháng)'},{name:'thoiHan',text:'Thời hạn (ngày)'},{name:'tenNv',text:'Nhân viên'},{name:'tenDv',text:'Đơn vị'}]
  },
  data () {
    return {
      dateconfig: {
        altFormat: 'd/m/Y',
        altInput: true,
        dateFormat: 'd/m/Y',
        allowInput:true,
      },
      ngay_ht: new Date(),
      header: {
        title: 'XỬ LÝ THUÊ BAO THUÊ SỐ',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      isNhoHon:false,
      isLonHon:false,
      listbox: {
        ds_dovi: {
          list: [],
          value: { dichvuvt_id:"", ten_dvvt:"" },
          isEnabled: false,
        },
        ds_thuebao: {
          list: [],
          header: [ {fieldName: 'maTb', headerText: 'Số máy', allowFiltering: true}, 
                      {fieldName: 'tenTb', headerText: 'Tên thuê bao', allowFiltering: true},
                      {fieldName: 'ngaySd', headerText: 'Ngày sử dụng', allowFiltering: true , allowHtml : true},
                      {fieldName: 'tgThueSo', headerText: 'Thời gian thuê (tháng)', allowFiltering: true , allowHtml : true},                                
                      {fieldName: 'thoiHan', headerText: 'Thời hạn (ngày)', allowFiltering: true , allowHtml : true},  
                      {fieldName: 'tenNv', headerText: 'Nhân viên', allowFiltering: true , allowHtml : true},                                     
                      {fieldName: 'tenDv', headerText: 'Đơn vị', allowFiltering: true , allowHtml : true},                                     
                      ],
          value: {},
          isEnabled: true,
        },
      },
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      from_date: new Date(),
      to_date: new Date(),
      success_code: "BSS-00000000",
      thoihancon_lonhon: 0,
      thoihancon_nhohon: 0,
      somay: '',
    }
  },
  computed: {
    nhohon: {
      get() { return this.isNhoHon; },
      set(value) { this.isNhoHon=value; 
      this.thoihancon_nhohon="0";
      }
    },
    lonhon: {
      get() { return this.isLonHon; },
      set(value1) { this.isLonHon=value1; 
      this.thoihancon_lonhon="0";
      }
      
    }
  },
  methods: {
    ShowAlert(mesage,val)
    {
      if (val==0)
      {
        this.$toast.success(mesage);
      }
      else if (val==1)
      {
        this.$toast.warning(mesage);
      }
      else if (val==2)
      {
        this.$toast.error(mesage);
      }
    },
     validateNumber: event => {
      //  const reg = /^-?[0-9]\d?$/;
      //  const str = event.target.value
      //   if(!reg.test(str)){
      //     event.preventDefault()
      //   }
      // let keyCode = event.keyCode;
      // if (keyCode < 48 || keyCode > 57) {
      //   event.preventDefault();
      // }
    },
  

    
    btnGetSubClicked() {
      // const reg = /^-?[0-9]\d?$/;

      // if(this.thoihancon_lonhon ){
      //   if(!reg.test(this.thoihancon_lonhon)){
      //     return this.$toast.warning('Dữ liệu nhập không đúng định dạng!')
      //   }
      // }

      // if(this.thoihancon_nhohon ){
      //   if(!reg.test(this.thoihancon_nhohon)){
      //     return this.$toast.warning('Dữ liệu nhập không đúng định dạng!')
      //   }
      // }

      if(this.isLonHon && !Number(this.thoihancon_lonhon)  ){
        //show alert
        return this.$toast.warning('Bạn chọn thời hạn còn thuê số >= nhưng bạn chưa nhập số liệu')
         
      }

      if(this.isNhoHon && !Number(this.thoihancon_nhohon)){
        //show alert
        return this.$toast.warning('Bạn chọn thời hạn còn thuê số <= nhưng bạn chưa nhập số liệu')
         
      }

    

     

      var pdata = {};
      if (this.somay.trim() !="")
      {
        pdata.maTb = this.somay.trim();
      }
      if (this.isNhoHon)
      {
         pdata.denNgay = this.thoihancon_nhohon;
      }
      if (this.isLonHon)
      {
        pdata.tuNgay =this.thoihancon_lonhon;
      }
      this.$root.loading(true)
      // this.loading(true);
      ProcessOverdueNumberAPI.thueso_theohan(this.axios, pdata).then((response) => {
        this.$root.loading(false)
        if(response.data.error_code && response.data.error_code === this.success_code) {
          this.listbox.ds_thuebao.list = response.data.data;
          console.log(this.listbox.ds_thuebao.list);
          //this.listbox.ds_thuebao.header = [{fieldName:'maTb',text:'Số máy'},{fieldName:'tenTb',text:'Tên thuê bao'},{fieldName:'ngaySd',text:'Ngày sử dụng'},{fieldName:'tgThueSo',text:'Thời gian thuê (tháng)'},{fieldName:'thoiHan',text:'Thời hạn (ngày)'},{fieldName:'tenNv',text:'Nhân viên'},{fieldName:'tenDv',text:'Đơn vị'}]
        }
        else {
          this.listbox.ds_thuebao.list = [];
          
        }
       
      }).catch(err=>{
        console.log(err)
        this.$root.loading(false)
      })
    },  
    onSoMayEnter()
    {
      this.btnGetSubClicked();
    },

    btnExportFileClicked: function () {
      //console.log(this.listbox.ds_phieuyeucau.list);
      if (this.listbox.ds_thuebao.list != null && this.listbox.ds_thuebao.list.length > 0) {
        let data = XLSX.utils.json_to_sheet(this.listbox.ds_thuebao.list)
        let wb = XLSX.utils.book_new() // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data, 'DsPorts') // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, 'ds_thuebao.xlsx')
      } else this.ShowAlert('Không có dữ liệu!',1)
    },
    
  },
};

</script>
