<template>
  <ejs-dialog :enableResize="true" :isModal="true" :allowDragging="true" :visible="false" :animationSettings="hieu_ung_popup" ref="frmXemTraTruoc" :position="position" :header="'Danh sách trả trước đã chọn'" showCloseIcon="true" width="80%" target="#app .main-wrapper">
    <div class="">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#" v-on:click="Taomoi_OnClick()">
                <span class="icon one-file-plus"></span>Tạo mới
            </a>
          </li>        
        </ul>
      </div>
      <div class="popup-body">
        <div class="row">
            <div class="col-sm-6 col-12">
                <div class="info-row">
                    <div class="key ">Ngày BĐĐC:</div>
                    <div class="value">
                      <vue-date v-model="ngay_bddc" :format="dateFormat" :checkbox="false"></vue-date>
                      
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key ">Ngày KTĐC:</div>
                    <div class="value">
                      <vue-date v-model="ngay_ktdc" :format="dateFormat" :checkbox="false" readonly></vue-date>
                    </div>
                </div>
            </div>
        </div>
        <div class='row'>
          <div class='col-sm-12'>
            <div class="box-form">
              <div class="legend-title">
                  Danh sách trả trước
              </div>
              <div class="row"></div>
              <DataGrid ref="gridtt"
                  class="table-result table-gachle"
                  v-bind:columns="grvTT.headers"
                  v-bind:dataSource="grvTT.list"
                  :enable-paging-server="false"
                  :allowPaging="true"
                  :commands=
                    "[
                          {name: 'GO', cssClass: 'btn btn-primary one-trash', title : 'Gỡ'}
                          ]"
                  :commandColumn="{headerText: 'Gỡ', width: 200, firstColumn: false, show: true}"
                  :showFilter="true"                        
                  @commandClicked="thaotac"
              >
              </DataGrid>
            </div>
          </div>
        </div>
        <div class='row'>
          <div class='col-sm-12'>
            <div class="box-form">
              <div class="legend-title">
                  Gói đa dịch vụ đăng ký
              </div>
              <div class="row">
                  <DataGrid ref="gv_da_dv" v-bind:columns="gv_da_dv.headers"
                      v-bind:dataSource="gv_da_dv.list" :showColumnCheckbox="false"
                      :showFilter="true" :allowPaging="true" :enablePagingServer="false"
                      :enabled-select-first-row="true">
                  </DataGrid>
              </div>
            </div>
          </div>
        </div>
        <div class='row'>
          <div class='col-sm-12'>
            <div class="box-form">
              <div class="legend-title">
                  Danh sách thuê bao đổi tốc độ
              </div>
              <div class="row">
                  <DataGrid ref="gv_tb_goi" v-bind:columns="gv_tb_doitd.headers"
                      v-bind:dataSource="gv_tb_doitd.list" :showColumnCheckbox="false"
                      :showFilter="true" :allowPaging="true" :enablePagingServer="false"
                      :enabled-select-first-row="true">
                  </DataGrid>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>

<script>
import moment from "moment";
export default {
  name: 'frmXemTraTruoc',
  data()
  {
    return {
      hieu_ung_popup: { effect: 'Zoom' },
      position: { X: 'center', Y: 'center' },
      grvTT : {
        headers: [
          {
              fieldName: "ma_tb",
              headerText: "Số máy/Account",
              allowFiltering: true,
              allowSorting: false,
              width: 150,     
              
          },
          {
              fieldName: "ten_tb",
              headerText: "Tên thuê bao",
              allowFiltering: true,
              allowSorting: false,
              width: 150,     
              
          },
          {
              fieldName: "loaihinh_tb",
              headerText: "Loại hình",
              allowFiltering: true,
              allowSorting: false,
              width: 150,     
              
          },
          {
              fieldName: "ten_km",
              headerText: "Khuyến mãi",
              allowFiltering: true,
              allowSorting: false,
              width: 150,     
              
          },
          {
              fieldName: "ten_ctkm",
              headerText: "Chi tiết km trả trước",
              allowFiltering: true,
              allowSorting: false,
              width: 150        
          },
          {
              fieldName: "sothang",
              headerText: "Số tháng",
              allowFiltering: true,
              allowSorting: false,
              width: 150,
          },
          {
              fieldName: "tien_tratruoc",
              headerText: "Tiền trả trước",
              allowFiltering: true,
              allowSorting: false,
              width: 100,
              type: "Number", 
              format: "N0"
          },
          {
              fieldName: "tien_thoai",
              headerText: "Tiền thoái",
              allowFiltering: true,
              allowSorting: false,
              width: 100,
              type: "Number", 
              format: "N0"
          },
          {
              fieldName: "tien_thuctra",
              headerText: "Thực trả",
              allowFiltering: true,
              allowSorting: false,
              width: 100,
              type: "Number", 
              format: "N0"
          },
          {
              fieldName: "ngay_bddc",
              headerText: "Ngày BĐĐC",
              allowFiltering: true,
              allowSorting: false,
              width: 100
          },
          {
              fieldName: "ngay_ktdc",
              headerText: "Ngày KTĐC",
              allowFiltering: true,
              allowSorting: false,
              width: 100              
          }
        ],
        list: []
      },
      gv_tb_doitd : {
        headers: [
          {
              fieldName: "ma_tb",
              headerText: "Số máy/Account",
              allowFiltering: true,
              allowSorting: false,
              width: 150,     
              
          },
          {
              fieldName: "ten_tb",
              headerText: "Tên thuê bao",
              allowFiltering: true,
              allowSorting: false,
              width: 150,     
              
          },
          {
              fieldName: "loaihinh_tb",
              headerText: "Loại hình",
              allowFiltering: true,
              allowSorting: false,
              width: 150,     
              
          },
          {
              fieldName: "tocdo_id_cu",
              headerText: "Tốc độ cũ",
              allowFiltering: true,
              allowSorting: false,
              width: 150,     
              
          },
          {
              fieldName: "tocdo_id_moi",
              headerText: "Tốc độ id mới",
              allowFiltering: true,
              allowSorting: false,
              width: 150,     
              
          },
          {
              fieldName: "tocdo_moi",
              headerText: "Tốc độ mới",
              allowFiltering: true,
              allowSorting: false,
              width: 150,     
              
          },
          {
              fieldName: "tien_thoai",
              headerText: "Tiền thoái",
              allowFiltering: true,
              allowSorting: false,
              width: 100,
              type: "Number", 
              format: "N0"
          }    
        ],
        list: []
      },
      gv_da_dv: {
        headers: [
          {
              fieldName: "ma_tb",
              headerText: "Mã thuê bao",
              allowFiltering: true,
              allowSorting: false,
              width: 100,                          
          },
          {
              fieldName: "ten_goi",
              headerText: "Tên gói",
              allowFiltering: true,
              allowSorting: false,
              width: 120,                    
          },
          {
              fieldName: "sl_thanhphan",
              headerText: "Số lượng thành phần gói",
              allowFiltering: true,
              allowSorting: false,
              width: 100,                          
          },
          {
              fieldName: "loaihinh_tb",
              headerText: "Loại hình",
              allowFiltering: true,
              allowSorting: false,
              width: 100,                          
          },
          {
              fieldName: "tocdo",
              headerText: "Tốc độ",
              allowFiltering: true,
              allowSorting: false,
              width: 100,                          
          },            
          {
              fieldName: "tien_goi",
              headerText: "Tiền gói",
              allowFiltering: true,
              allowSorting: false,
              width: 100,
              type: "Number", 
              format: "N0",
          },
          {
              fieldName: "sosanh_tien",
              headerText: "So sánh tiền với gói hiện tại",
              allowFiltering: true,
              allowSorting: false,
              width: 150,  
              type: "Number", 
              format: "N0",       
              cssClass: "tien-tra"     
          },  
          {
              fieldName: "ma_pa",
              headerText: "Mã PA",
              allowFiltering: true,
              allowSorting: false,
              width: 150,
          },
          {
              fieldName: "pa_tuvan",
              headerText: "Phương án",
              allowFiltering: true,
              allowSorting: false,
              width: 150,
          },
          {
              fieldName: "tien_thoai",
              headerText: "Tiền thoái (Nếu không đk KM)",
              allowFiltering: true,
              allowSorting: false,
              width: 100,
              type: "Number", 
              format: "N0"
          }                            
        ],
        list:[],
      },
      dulieu: {},
      dateFormat:'DD/MM/YYYY',
      ngay_bddc:moment(new Date()).format('DD/MM/yyyy'),
      ngay_ktdc:moment(new Date()).format('DD/MM/yyyy'),
      rkm_id:0, //TODO
    }
  },
  watch: { 
    ngay_bddc: async function(val){
      console.log(val)
      if (this.grvTT.list.length > 0){
        var so_thang = this.grvTT.list[0].sothang
        var ngay_bd = moment(val,'DD/MM/yyyy').toDate()
        this.ngay_ktdc = moment(new Date(ngay_bd.setMonth(ngay_bd.getMonth() + so_thang))).format('DD/MM/yyyy');
        this.update_ngay_dc()
        this.dulieu = await this.tinhtien_thoai_thuctra(this.dulieu)
        this.grvTT.list = this.dulieu    
      }
    },    
  },
  methods: {
    async openDialog(dulieu,arr_goi,arr_doitocdo)
    {
      console.log('arr_goi')
      console.log(arr_goi)
      this.grvTT.list = []
      
      this.dulieu = dulieu      
      if (this.dulieu.length > 0){
        var so_thang = this.dulieu[0].sothang        
        var ngay_bd = moment(this.ngay_bddc,'DD/MM/yyyy').toDate()        
        this.ngay_ktdc = moment(new Date(ngay_bd.setMonth(ngay_bd.getMonth() + so_thang))).format('DD/MM/yyyy');
        await this.update_ngay_dc()
        this.dulieu = await this.tinhtien_thoai_thuctra(dulieu)
        console.log(this.dulieu)
        this.grvTT.list = this.dulieu
      }
      if (arr_goi && arr_goi.length > 0) {
        arr_goi = await this.tinhtien_thoai_thuctra(arr_goi)
        this.gv_da_dv.list = arr_goi 
      }
      if (arr_doitocdo && arr_doitocdo.length > 0){
        arr_doitocdo = await this.tinhtien_thoai_thuctra(arr_doitocdo)
        this.gv_tb_doitd.list = arr_doitocdo
      } else {
        console.log('this.gv_tb_doitd.list')
        if (dulieu.length > 0){
          this.gv_tb_doitd.list = dulieu.filter(x => x.tocdo_id_cu != x.tocdo_id_moi)
        }
      }
      this.$refs.frmXemTraTruoc.show();
    },
    
    async thaotac(name, dataItem)
    {
      if (name === 'GO') {
        try {
          let index = this.grvTT.list.findIndex((x) => x.chitietkm_id == dataItem.chitietkm_id);
          console.log(index)
          if (index >= 0){
            this.grvTT.list.splice(index, 1)
          }
          this.$emit("go_km", this.grvTT.list);
        }
        catch (ex)
        {
          throw ex;
        }
        console.log(dataItem);
        return;
      }
    },
    async Taomoi_OnClick()
    {      
      try {
        //await this.parent.tao_hdKeyPress()
        let data_emit = {
          data_tratruoc: this.grvTT.list,
          data_goi: this.gv_da_dv.list,
          data_doitd: this.gv_tb_doitd.list,
        }
        this.$emit("tao_hd", data_emit);
      }
      catch (ex)
      {
        throw ex;
      }        
    },
    async update_ngay_dc()
    {      
      if (this.dulieu.length > 0){
        this.dulieu.forEach((item, index) => {
          item.ngay_bddc = this.ngay_bddc
          item.ngay_ktdc = this.ngay_ktdc
          item.thang_bddc = this.yyyymm(moment(this.ngay_bddc,'DD/MM/yyyy').toDate())
          item.thang_ktdc = this.yyyymm(moment(this.ngay_ktdc,'DD/MM/yyyy').toDate())
          
        })
      }  
      this.grvTT.list = this.dulieu  
    },
    async tinhtien_thoai_thuctra(dulieu) {
      console.log('tinhtien_thoai_thuctra')
      console.log(dulieu)
      try {            
        if (dulieu.length > 0){
          let ds_datcoc = await this.LAY_DS_THUEBAO_DATCOC(dulieu[0].ma_tb)
          let tmp = dulieu
          for (var i = 0; i < tmp.length; i++){
            let rkm_id = null
            let thuebao_dc_id = null
            if (ds_datcoc && ds_datcoc.length > 0) {
              var ls = ds_datcoc.filter(x => x.ma_tb == tmp[i].ma_tb)
              if (ls && ls.length > 0){
                rkm_id = ls[0].rkm_id
                thuebao_dc_id = ls[0].thuebao_dc_id
              }
            }
            var input = {
                rkm_id: rkm_id,
                thuebao_dc_id: null,
                thang_thoai: this.yyyymm(moment(this.ngay_bddc,'DD/MM/yyyy').toDate()),
                ngay_thoai: this.yyyymmdd(moment(this.ngay_bddc,'DD/MM/yyyy').toDate())
            }
            var tienthoai = await this.Lay_tienthoai_datcoc_v4(input)
            tmp[i].tien_thoai = Number(tienthoai && tienthoai > 0? tienthoai : 0 )
            if (tmp[i].tien_tratruoc) {
              tmp[i].tien_thuctra = Number(tmp[i].tien_tratruoc) - Number(tienthoai)
            }
          }
          dulieu = tmp  
          console.log('console.log(tmp)')
          console.log(tmp)
          return tmp        
        } 
      } catch (err) {
        console.log(err)
        return null
      }
    },
    async Lay_tienthoai_datcoc_v4(data) {
      try {            
          let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/lay_tienthoai_datcoc_v4`, data)

          if (response.error_code === 'BSS-00000000' && response.data != null) {                
              console.log(response.data.TIENTHOAI)
              return response.data.TIENTHOAI
          }            
          throw { response: { data: response } }
      } catch (err) {
          return null
      }
    },
    async LAY_DS_THUEBAO_DATCOC (ma_tb) {
      try {
        let postData = {
            "opt" : 1,
            "ma_tb" : ma_tb,
            "loai_hd" : 31,
            "kieuld_id": 550
          }
        let data = {
          p_data: JSON.stringify(postData)
        }
        let rps = await this.$root.context.post(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_ds_thuebao_datcoc', data
        )

        console.log(rps)

        if (rps.data && rps.data.length > 0) {
          return rps.data
        } else { 
          return []
        }
        
      } catch (error) {
        console.log(error)
        return []
      }
    },
    async dataBound() {
      console.log('databound')
      await this.tinhtien_thoai_thuctra()
    },
    getDateElements(date) {

      date = new Date(date)

      let day = date.getDate()
      day = day > 9 ? day : '0' + day

      let month = date.getMonth() + 1
      month = month > 9 ? month : '0' + month

      let hour = date.getHours()
      hour = hour > 9 ? hour : '0' + hour

      let min = date.getMinutes()
      min = min > 9 ? min : '0' + min

      let second = date.getSeconds()
      second = second > 9 ? second : '0' + second

      return {
          y: date.getFullYear(),
          M: month,
          d: day,
          h: hour,
          m: min,
          s: second
      }
    },
    yyyymm(date) {

      if (!date) {
          return ''
      }

      date = this.getDateElements(date)
      return `${date.y}${date.M}`
    },
    yyyymmdd(date) {

      if (!date) {
          return ''
      }

      date = this.getDateElements(date)
      return `${date.y}${date.M}${date.d}`
    },
    ddmmyyyyhhmiss(date) {

      if (!date) {
          return ''
      }

      date = this.getDateElements(date)
      return `${date.d}${date.M}${date.y}${date.h}${date.m}${date.s}`
    },
    ddmmyyyy(date) {

      if (!date) {
          return ''
      }

      date = this.getDateElements(date)
      return `${date.d}/${date.M}/${date.y}`
    },
    ddmmyyyyToDate(dateStr) {

      if (!dateStr) {
          return null
      }

      let arr = dateStr.trim().split('/')
      let dd = arr[0]
      let mm = arr[1]
      let yyyy = arr[2]
      return new Date(`${yyyy}-${mm}-${dd}`)
    }
  }
}
</script>

<style scoped>

</style>
