<style scoped>
.list-actions-top .list > li > div.button {
    display: block;
    padding: 2px 15px;
    color: #0176FF;
    border-left: 1px solid #BAE7FF;
    text-decoration: none;
    font-weight: 600;
    cursor: pointer;
}
</style>

<template>
  <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogChonMauHDDT" :position="position" :header="'Chọn mẫu hóa đơn điện tử'" showCloseIcon="true" width="80%">
    <div class="">
        <div class="list-actions-top">
          <ul class="list">
            <li>
              <a href="javascript:;" v-on:click="btnChapNhan_ItemClick">
                <span class="icon one-save"></span> Chấp nhận
              </a>
            </li>
            <li>
              <a href="javascript:;" v-on:click="btnXemMau_ItemClick">
                <span class="icon one-save"></span> Xem mẫu
              </a>
            </li>
          </ul>
        </div>
        <div class="popup-body">
            <div class="row">
              <div class="col-sm-6 col-12">
                  <div class="info-row">
                      <div class="key" style="width: 150px">
                          Số lượng hóa đơn
                      </div>
                      <div class="value">
                          <input type="number" class="form-control" v-model="state.txtSoLuongHoaDon">
                      </div>
                  </div>
              </div>
            </div>
            <div class="row">
                <div class="col-sm-4 col-12">
                  <div class="box-form">
                    <div class="legend-title">Danh mục loại hóa đơn</div>
                    <DataGrid ref="grcDanhMucLoaiHoaDon" class="table-result table-gachle"
                          v-bind:columns="[
                                      {fieldName: 'Lable', headerText: 'Tên mẫu', allowFiltering: true},
                                  ]"
                          v-bind:dataSource="grcDanhMucLoaiHoaDon.list"

                          @selectedRowChanged="gdvDanhMucLoaiHoaDon_FocusedRowChanged"
                          :enable-paging-server="false"
                          :allowPaging="true"
                          :multiple="true"
                          :showFilter="true">
                    </DataGrid>
                  </div>
                </div>
                <div class="col-sm-8 col-12">
                  <div class="box-form">
                    <div class="legend-title">Danh mục mẫu hóa đơn</div>
                    <DataGrid ref="grcMauHoaDon" class="table-result table-gachle"
                        v-bind:columns="[
                                    {fieldName: 'templatename', headerText: 'Tên mẫu', allowFiltering: true},
                                    {fieldName: 'invcatename', headerText: 'Loại mẫu', allowFiltering: true},
                                ]"
                        :showColumnCheckbox='true'
                        :enabledSelectFirstRow="false"
                        v-bind:dataSource="grcMauHoaDon.list"
                        :enable-paging-server="false"
                        :multiple="true"
                        :allowPaging="true"
                        :allowSelection='true'
                        :showFilter="true">
                    </DataGrid>
                  </div>
                </div>
            </div>
        </div>
    </div>
  </ejs-dialog>
</template>
<script>
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment'
import api from './PopUpApi'
import commonFn from "@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions";
import {
  LOAIHINH_THUEBAO,
} from "@/constants";

export default {
  components: {
    moment,
    DatePicker,
    api,
  },
  name: 'ChonMauHDDT',
  data() {
    return {
      Loading: false,
      dateFormat: 'DD/MM/YYYY HH:mm:ss',
      dateFormatMonth: 'MM/YYYY',
      typeFormat: 'datetime',
      typeFormatMonth: 'month',
      dateConfig: {
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      target: '#app', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      showTimePanel: false,
      tabIndex: 0,
      state: {},
      grcDanhMucLoaiHoaDon: {
        list: []
      },
      grcMauHoaDon: {
        list: []
      },
    }
  },
  created: async function () {

  },
  mounted: function () {
  },
  watch: {

  },
  methods: {


    dialogClose() {},
    openDialog(data) {
      if(!data){
        return;
      }
      this.state._dsHDTB_CT = data._dsHDTB_CT;
      console.log("data._dsHDTB_CT", data._dsHDTB_CT)
      this.state.soluong = data.soluong;
      this.state.isChapNhan = false;
      this.state.txtSoLuongHoaDon = this.state.soluong;
      this.$refs.dialogChonMauHDDT.show();
      this.pageLoad();
    },
    async pageLoad(){
      try{
        this.loading(true)
        let dm_lhd = this.GetData(await api.LayDanhMucLoaiHoaDon(this.axios, {}));
        if(dm_lhd && dm_lhd.length > 0){
          this.grcDanhMucLoaiHoaDon.list = dm_lhd;
        }
      }catch(ex){
        console.error(ex);
        this.$toast.error(ex);
      }
      this.loading(false)
    },
    async gdvDanhMucLoaiHoaDon_FocusedRowChanged(e){
      try{
        this.loading(true)
        let id = e.Value;
        if (this.state.dm_mhd_data)
        {
            await this.GanMauHoaDonDaDK();
        }
        this.state.dm_mhd_data = this.GetData(await api.LayDanhMucMauHoaDon(this.axios, {params: {category_id : id}}));
        if(this.state.dm_mhd_data){
          this.state.dm_mhd_data = this.LowerCasePropertyList(this.state.dm_mhd_data);
        }
        await this.LoadMauHoaDonDaDK();

      }catch(ex){
        console.error(ex);
        this.$toast.error(ex);
      }
      this.loading(false)
    },
    async GanMauHoaDonDaDK(){
      if(!this.state.dm_mhd_data) return;
      var grid = this.$refs.grcMauHoaDon;
      var seletedRows = grid.getSelectedRecords();
      console.log("seletedRows", seletedRows)
      console.log("this.state.dm_mhd_data", this.state.dm_mhd_data)
      for(const item of this.state.dm_mhd_data){
        item.chon = false
        if (seletedRows.some(x => x.identification == item.identification)){
          item.chon = true
        }
        console.log("item.chon", item.chon)
        if (item.chon == true)
        {
            let hdtbct = {};
            hdtbct.MA_CHUAN_CT = item.identification;
            hdtbct.DOMAIN_CT = item.templatename;
            hdtbct.MA_PHU_CT = item.invcateid;
            hdtbct.WEBSITE_CT = item.invcatename;
            hdtbct.IP_CT = "0";
            hdtbct.LOAITB_ID = LOAIHINH_THUEBAO.TTDL_HDDT;
            if (!this.state._dsHDTB_CT.some(x => x.MA_CHUAN_CT == hdtbct.MA_CHUAN_CT)){
                console.log("_dsHDTB_CT.push", hdtbct)
                this.state._dsHDTB_CT.push(hdtbct);
            }
        }
        else
        {
            this.state._dsHDTB_CT = this.state._dsHDTB_CT.filter(x => x.MA_CHUAN_CT != item.identification);
        }
      }
    },
    async LoadMauHoaDonDaDK(){
      if(!this.state.dm_mhd_data) return;
      if(!this.state._dsHDTB_CT) return;
      this.grcMauHoaDon.list = this.state.dm_mhd_data;
      var grid = this.$refs.grcMauHoaDon
      console.log(grid)
      var i = 0;
      for(const item of this.state.dm_mhd_data){
        if (this.state._dsHDTB_CT.some(x => x.MA_CHUAN_CT == item.identification))
        {
          grid.selectRow(i)
          item.chon = true;
        }
        i++;
      }
    },
    async  btnChapNhan_ItemClick(){
      await this.GanMauHoaDonDaDK();
      this.state.soluong =  this.state.txtSoLuongHoaDon;
      this.state.isChapNhan = true;
      if (!this.state._dsHDTB_CT || this.state._dsHDTB_CT.length ==0){
        this.$toast.error("Bạn chưa chọn mẫu hóa đơn nào!")
        return;
      }
      console.log(this.state._dsHDTB_CT)
      this.$emit("onChapNhan", {soluong: this.state.soluong, isChapNhan: this.state.isChapNhan, _dsHDTB_CT: this.state._dsHDTB_CT})
      this.$refs.dialogChonMauHDDT.hide()
    },
    async  btnXemMau_ItemClick(){
      var grid = this.$refs.grcMauHoaDon;
      var seletedRows = grid.getSelectedRecords();
      if(seletedRows && seletedRows.length <= 0){
        this.$toast.warning("Bạn chưa chọn mẫu cần xem!");
        return;
      }
      try{
        this.loading(true)
        let row = seletedRows[0];
        let xmlData = this.GetData(await api.HTMLMauHoaDon(this.axios, {params: {inv_id: row.identification}}));
        if(!xmlData){
          this.$toast.warning("Không lấy được thông tin xem mẫu hóa đơn!");
          this.loading(false)
          return;
        }
        var prettifyXml = function(sourceXml, sourcexslt)
        {
            var xmlDoc = new DOMParser().parseFromString(sourceXml, 'application/xml');
            var xsltDoc = new DOMParser().parseFromString(sourcexslt, 'application/xml');
            // console.info(xmlDoc,xsltDoc);
            var xsltProcessor = new XSLTProcessor();
            xsltProcessor.importStylesheet(xsltDoc);
            var resultDoc = xsltProcessor.transformToFragment(xmlDoc,document);
            // console.info(resultDoc);
            var resultXml = new XMLSerializer().serializeToString(resultDoc);
            // console.info(resultXml);

            return resultXml;
        };
        var html = prettifyXml(xmlData.XmlFile, xmlData.XsltFile);
        html = html.replaceAll("&lt;","<").replaceAll("&gt;",">").replaceAll("&amp;","&");
        const h = this.$createElement;
        const titleVNode = h('div', { domProps: { innerHTML: html } })
        this.$bvModal.msgBoxOk([titleVNode]);
      }catch(ex){
        console.error(ex);
        commonFn.showException(this, ex);
      }
      this.loading(false)
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200' || response.data.errorCode == 0) {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return null
    },
    LowerCasePropertyList(obj) {
      return obj.map( function( item ){
          for(var key in item){
              var upper = key.toLowerCase();
              // check if it already wasn't uppercase
              if( upper !== key ){
                  item[ upper ] = item[key];
                  delete item[key];
              }
          }
          return item;
      });
    },
    UpperCasePropertyList(obj) {
      return obj.map( function( item ){
          for(var key in item){
              var upper = key.toUpperCase();
              // check if it already wasn't uppercase
              if( upper !== key ){
                  item[ upper ] = item[key];
                  delete item[key];
              }
          }
          return item;
      });
    },
    ShowAlert: function (msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    }
  },
}
</script>

<style>
.chuyenTo .page-content {
  top: 130px !important;
}
</style>
