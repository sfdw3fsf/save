<template>
    <b-modal
    ref="popupMucCuocTBTC"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Mức cước thuê bao
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <ActionTop :actions="actions" @onActionClick="onActionClick" />
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin mức cước thuê bao</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                  <div class="key w90">Từ ngày</div>
                  <div class="value">
                      <div class="input-icon-right">
                          <k-date-picker v-model="tu_ngay"/>
                      </div>
                  </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                  <div class="key w90">Đến ngày</div>
                  <div class="value">
                      <div class="input-icon-right">
                          <k-date-picker v-model="den_ngay"/>
                      </div>
                  </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="box-form">
              <div class="legend-title">Đối tượng</div>
              <KDataGrid
                :columns="columnsDoiTuong"
                :dataSource="dataSourcesDoiTuong"
                :showColumnCheckbox="false"
                :enable-paging-server="false"
                :allowPaging="false"
                :showFilter="true"
                ref="gridDoiTuong"
                :enabledSelectFirstRow="true"
                @onRowSelected="onSelectedRowDoiTuong"
                panelDataHeight="180px"/>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="box-form">
              <div class="legend-title">Trang bị</div>
              <KDataGrid
                :columns="columnsTrangBi"
                :dataSource="dataSourcesTrangBi"
                :showColumnCheckbox="false"
                :enable-paging-server="false"
                :allowPaging="false"
                :showFilter="true"
                ref="gridTrangBi"
                @onRowSelected="onSelectedRowTrangBi"
                :enabledSelectFirstRow="true"
                panelDataHeight="180px"/>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách mức cước thuê bao</div>
          <KDataGrid
              :columns="columns"
              :dataSource="dataSources"
              :showColumnCheckbox="false"
              :enable-paging-server="false"
              :allowPaging="true"
              :showFilter="true"
              @onRowSelected="onSelectedRow"
              ref="gridMucCuoc"
              :enabledSelectFirstRow="true"
              panelDataHeight="120px"/>
        </div>
      </div>
    </div>
    </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import moment from 'moment'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
export default {
    name:'MucCuocTBTCModal',
    components:{
      ActionTop,
      KDataGrid,
      KDatePicker
    },
    props:{
      tinh_tc:{
        type:Number,
        default:0
      },
      tocdo_tc:{
        type:Number,
        default:0
      }
    },
    data(){
      return {
        actions:[
            {
                id:'capnhat',
                name:'Cập nhật',
                active: true,
                icon_class:'one-save'
            },
        ],
        tu_ngay:moment(new Date()).format('DD/MM/YYYY'),
        den_ngay:moment(new Date()).format('DD/MM/YYYY'),
        columnsDoiTuong:[
          {
            fieldName: "ten_dt",
            headerText: "Đối tượng",
            allowFilter: true,
            allowSorting: false,
          }
        ],
        dataSourcesDoiTuong:[],
        columnsTrangBi:[
          {
            fieldName: "tentrangbi",
            headerText: "Trang bị",
            allowFilter: true,
            allowSorting: false,
          }
        ],
        dataSourcesTrangBi:[],
        columns:[
          {
            fieldName: "mucuoctb_id",
            headerText: "ID",
            allowFilter: true,
            allowSorting: false,
            width:80
          },
          {
            fieldName: "muccuoc",
            headerText: "Tên mức cước",
            allowFilter: true,
            allowSorting: false

          },
          {
            fieldName: "cuoc_tb",
            headerText: "Cước TB",
            allowFilter: true,
            allowSorting: false

          },
          {
            fieldName: "cuockhoan",
            headerText: "Cước khoán",
            allowFilter: true,
            allowSorting: false,

          },
          {
            fieldName: "cuoc_ll",
            headerText: "Cước lưu lượng",
            allowFilter: true,
            allowSorting: false

          },
          {
            fieldName: "cuoc_tg",
            headerText: "Cước trọn gói",
            allowFilter: true,
            allowSorting: false
          }
        ],
        dataSources:[],
        muccuoctb_id:0,
        doituong_id:0,
        trangbi_id:0
      }
    },
    methods:{
      onActionClick(action){
        if(action.id=='capnhat'){
            this.$emit('accept',{
              doituong_id:this.dataSourcesDoiTuong.length>0?this.doituong_id:0,
              trangbi_id:this.dataSourcesTrangBi.length>0?this.trangbi_id:0
            })
            this.hideModal()
        }
      },
      showModal() {
        this.$refs["popupMucCuocTBTC"].show()
      },
      hideModal() {
        this.$refs["popupMucCuocTBTC"].hide()
      },
      onSelectedRowDoiTuong(item){
        this.doituong_id=item.doituong_id?item.doituong_id:0
      },
      onSelectedRowTrangBi(item){
        this.trangbi_id=item.trangbi_id?item.trangbi_id:0
      },
      async onSelectedRow(item){
        this.muccuoctb_id=item.mucuoctb_id?item.mucuoctb_id:0
        this.tu_ngay=item.tungay?moment(item.tungay,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY'):''
        this.den_ngay=item.denngay?moment(item.denngay,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY'):''
        await this.HienThi_DoiTuongTB()
        await this.HienThi_TrangBi()
      },
      async HienThi_DoiTuongTB(){
        this.dataSourcesDoiTuong=await this.lay_muccuoc_tb_dt(this.tinh_tc, this.muccuoctb_id)
      },
      async HienThi_TrangBi(){
        this.dataSourcesTrangBi=await this.layds_trangbi(this.tinh_tc, this.muccuoctb_id, 1)
      },
      async HienThiDanhSach(){
        this.dataSources=await this.lay_muccuoc_tb({
          tinhthicong_id: this.tinh_tc,
          dichvuvt_id: 4,
          loaitb_id: 0,
          tocdo_id: this.tocdo_tc
        })
      },
      initDuLieu(){
        this.HienThiDanhSach()
      },
      handleShowModal(){
        this.dataSources=[]
        this.dataSourcesTrangBi=[]
        this.dataSourcesDoiTuong=[]
        this.tu_ngay=moment(new Date()).format('DD/MM/YYYY')
        this.den_ngay=moment(new Date()).format('DD/MM/YYYY')
        setTimeout(()=>{
          this.initDuLieu()
        }, 500)
      },
      async lay_muccuoc_tb(data){
          try{
            this.loading(true)
            let response = await this.axios.post('/web-bancheo/danhmuc/lay_muccuoc_tb',data)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return response.data.data
            }else{
                return []
            }
          }catch(e){
            this.loading(false)
            return []
          }
      },
      async lay_muccuoc_tb_dt(tinh_tc, muccuoc_id){
          try{
            this.loading(true)
            let response = await this.axios.post('/web-bancheo/danhmuc/lay_muccuoc_tb_dt',{
              tinhthicong_id: tinh_tc,
              muccuoc_id: muccuoc_id
            })
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return response.data.data
            }else{
                return []
            }
          }catch(e){
            this.loading(false)
            return []
          }
      },
      async layds_trangbi(tinh_tc, muccuoc_id, kieu){
          try{
            this.loading(true)
            let response = await this.axios.post('/web-bancheo/danhmuc/layds_trangbi',{
              tinhthicong_id: tinh_tc,
              muccuoc_id: muccuoc_id,
              kieu: kieu
            })
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return response.data.data
            }else{
                return []
            }
          }catch(e){
            this.loading(false)
            return []
          }
      },
    }
    
}
</script>