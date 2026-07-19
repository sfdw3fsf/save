<template src="./SearchManual.html"></template>
<style src="./SearchManual.scss"></style>
<script>
import XLSX from 'xlsx'
import moment from 'moment'
import breadcrumb from '@/components/breadcrumb'
import api from './api.js'
import DatePicker from 'vue2-datepicker'
import { ListView } from '@syncfusion/ej2-lists'
export default {
  components: {
    XLSX,
    moment,
    breadcrumb,
    ListView,
    DatePicker
  },
  name: 'SearchManual',
  data() {
    return {
      loading: false,
      cbloaiCamNang:{
        checked: 0,
        list:[]
      },
      cbloaiLinhVuc:{
        checkbox: true,
        checked: 0,
        list:[]
      },
      isanable: true,
      inptimKiem:"",
      header: {
        title: 'Tìm kiếm cẩm nang',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } }
        ]
      },
      headerGridbox: [
        { fieldName: 'acc', headerText: 'Tên cẩm nang', allowFiltering: true, allowSorting: true },
        { fieldName: 'dv', headerText: 'Nội dung', allowFiltering: true, allowSorting: true },
        { fieldName: 'loai_hinh', headerText: 'Đầu mối', allowFiltering: true, allowSorting: true },
        { fieldName: 'so_ao', headerText: 'Thời gian áp dụng', allowFiltering: true, allowSorting: true },
        { fieldName: 'trangtahi', headerText: 'Mức ưu tiên', allowFiltering: true, allowSorting: true },
        { fieldName: 'ngay_cn', headerText: 'Loại cẩm nangh', allowFiltering: true, allowSorting: true }, // KO BT
        { fieldName: 'nguoi_cn', headerText: 'Loại lĩnh vực', allowFiltering: true, allowSorting: true },
        { fieldName: 'nguoi_cn', headerText: 'Tải file', allowFiltering: true, allowSorting: true }
      ],
      gridBox: {
        data: [],
        selected: null
      },
      template : '<div class="item">'
          +'<div class="head">Cẩm nang thứ ${stt} <a role="button" data-toggle="collapse" data-target="#item${stt}" aria-expanded="false" aria-controls="item${stt}"><span class="down"></span></a></div>'
          +'<div id="item${stt}" class="content collapse show">'
              +'<table class="table-info-row">'
                  +'<tr>'
                      +'<td class="key">Tên cẩm nang:</td>'
                      +'<td class="value">${chude}</td>'
                  +'</tr>'
                  +'<tr>'
                      +'<td class="key">Nội dung:</td>'
                      +'<td class="value">${noidung}</td>'
                  +'</tr>'
                  +'<tr>'
                      +'<td class="key">Đầu mối:</td>'
                      +'<td class="value">${ten_nv}</td>'
                  +'</tr>'
                  +'<tr>'
                    +'<td class="key">Thời gian áp dụng</td>'
                    +'<td class="value">${thoigian}</td>'
                  +'</tr>'
                  +'<tr>'
                      +'<td class="key">Mức ưu tiên</td>'
                      +'<td class="value">${thutu}</td>'
                  +'</tr>'
                  +'<tr>'
                      +'<td class="key">Loại cẩm nang: </td>'
                      +'<td class="value">${loai_cn}</td>'
                  +'</tr>'
                  +'<tr>'
                      +'<td class="key">Loại lĩnh vực</td>'
                      +'<td class="value">${nhom_dichvu}</td>'
                  +'</tr>'
                  +'<tr>'
                    +'<td class="key">Tải file: </td>'
                    +'<td class="value">${download}</td>'
                  +'</tr>'
              + '</table>'
          +'</div>'
      +'</div>'
    }
  },
  mounted() {
     this.form_load()
  },
  created() {},
  watch: {
    loading: function (val, oldval) {
      this.$root.showLoading(val)
    }
  },
  methods: {
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    form_load: async function () {
      this.loading = true
      let data = this.GetData(await api.form_load_loaiCamNang(this.axios))
      if(data && data.length> 0){
         this. cbloaiCamNang.list = data.map((x) => ({
            id: x.loaicn_id,
            text: x.loai_cn
          }))
          this.cbloaiCamNang.checked = data[0].loaicn_id
          this.getloai_lv(data[0].loaicn_id)
      }
      this.loading = false
    },
    onLoaiCamNangChange(item){
        this.getloai_lv(item.id)
    },
    getloai_lv: async function (loaicn) {
      let data = this.GetData(await api.get_linh_vuc(this.axios, { loaicn_id: loaicn }))
      if(data && data.length> 0){
         this.cbloaiLinhVuc.list = data.map((x) => ({
            id: x.nhomdv_id,
            text: x.nhom_dichvu
          }))
          this.cbloaiLinhVuc.checked = data[0].nhomdv_id
      }else{
         this.cbloaiLinhVuc.list= []
         this.cbloaiLinhVuc.checked = 0
      }
    },
    checkboxChang(e){
        this.isanable = e.target.checked
    },
    async btnSearch_Click(){
      $("#element").empty()
      this.loading = true
      let inpstring = ""
      let loailv = 0
      let find = 1
      if(this.inptimKiem){
            inpstring = this.inptimKiem
      }
      if(this.cbloaiLinhVuc.checkbox){
         find = 0
         loailv = this.cbloaiLinhVuc.checked
      }
      let params ={
        "p_find": find,
        "p_timkiem": inpstring,
        "p_loailv_tk": loailv
        }
        try {
          let data = this.GetData(await api.search_camNang(this.axios, params))
          if(data && data.length > 0){
             this.show_ds(data)
          }
        
        } catch (error) {
            this.$toast.error('Lỗi: ' + error.data.message_detail )
        }
        this.loading = false
    },
    show_ds(dataSource){
        let listObj = new ListView({
            dataSource: dataSource,
            fields: { text: 'chude'},
            template: this.template,
        });
        listObj.appendTo('#element');
    },
    selectedItemsChangedGridbox(){},
  }
}
</script>
