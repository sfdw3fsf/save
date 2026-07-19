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
  <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :isModal="true"
              :animationSettings="animationSettings" ref="dialogCreateIssue" :position="position"
              :header="'Kích hoạt toàn trình khóa máy nợ cước DV CNTT/ATBMTT'" showCloseIcon="true" width="75%"
              :beforeClose="validation"
              @close="dialogClose"
              target="#app .main-wrapper">
    <div class="dialogCreateIssue">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript:;" v-on:click="cmdKichHoat_DynamicMultipleApis">
              <span class="icon one-save"></span> Kích hoạt hàng loạt
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <!--                  <div class="legend-title">Thông tin chung</div>-->

          <!--                  <div class="row">-->
          <!--                    <div class="col-sm-4 col-12">-->
          <!--                      <div class="info-row">-->
          <!--                        <div class="key w110">Dự án <span class="red">(*)</span></div>-->
          <!--                        <div class="value">-->
          <!--                          <div class="select-custom">-->
          <!--                            <select2 :options="controls.cboProject.list" v-model="controls.cboProject.value" required title="Bạn chưa chọn dự án" :disabled="true">-->
          <!--                            </select2>-->
          <!--                          </div>-->
          <!--                        </div>-->
          <!--                      </div>-->
          <!--                    </div>-->
          <!--                    <div class="col-sm-4 col-12">-->
          <!--                      <div class="info-row">-->
          <!--                        <div class="key w110">Kiểu yêu cầu <span class="red">(*)</span></div>-->
          <!--                        <div class="value">-->
          <!--                          <div class="select-custom">-->
          <!--                            <select2 :options="controls.cboIssueType.list" v-model="controls.cboIssueType.value" @change="cboIssueType_EditValueChanged">-->
          <!--                            </select2>-->
          <!--                          </div>-->
          <!--                        </div>-->
          <!--                      </div>-->
          <!--                    </div>-->
          <!--                    <div class="col-sm-4 col-12">-->
          <!--                      <div class="info-row">-->
          <!--                        <div class="key w110">Hạn xử lý <span class="red">(*)</span></div>-->
          <!--                        <div class="value">-->
          <!--                          <div class="input-icon-right">-->
          <!--                            <date-picker input-class="form-control" :format="dateFormat" :value-type="'format'" v-model="controls.dtpDuedate.value"></date-picker>-->
          <!--                          </div>-->
          <!--                        </div>-->
          <!--                      </div>-->
          <!--                    </div>-->
          <!--                  </div>-->
          <!--              <div class="row">-->
          <!--                  <div class="col-sm-4 col-12">-->
          <!--                      <div class="info-row">-->
          <!--                          <div class="key w110">Nhóm dịch vụ  <span class="red">(*)</span></div>-->
          <!--                          <div class="value">-->
          <!--                              <div class="select-custom">-->
          <!--                                  <select2 :options="controls.cboDonViJira.list" v-model="controls.cboDonViJira.value" :disabled="true">-->
          <!--                                  </select2>-->
          <!--                              </div>-->
          <!--                          </div>-->
          <!--                      </div>-->
          <!--                  </div>-->
          <!--                  <div class="col-sm-4 col-12">-->
          <!--                      <div class="info-row">-->
          <!--                          <div class="key w110">Sản phẩm <span class="red">(*)</span></div>-->
          <!--                          <div class="value">-->
          <!--                              <div class="select-custom">-->
          <!--                                  <select2 :options="controls.cboSanPhamJira.list" v-model="controls.cboSanPhamJira.value" :disabled="true">-->
          <!--                                  </select2>-->
          <!--                              </div>-->
          <!--                          </div>-->
          <!--                      </div>-->
          <!--                  </div>-->
          <!--                  <div class="col-sm-4 col-12" v-if="controls.cboMucDoUT.visible">-->
          <!--                      <div class="info-row">-->
          <!--                          <div class="key w110">Mức độ ưu tiên</div>-->
          <!--                          <div class="value">-->
          <!--                              <div class="select-custom">-->
          <!--                                  <select2 :options="controls.cboMucDoUT.list" v-model="controls.cboMucDoUT.value">-->
          <!--                                  </select2>-->
          <!--                              </div>-->
          <!--                          </div>-->
          <!--                      </div>-->
          <!--                  </div>-->
          <!--              </div>-->
          <!--                  <div class="row">-->
          <!--                    <div class="col-sm-4 col-12">-->
          <!--                      <div class="info-row">-->
          <!--                        <div class="key w110">Người tạo   <span class="red">(*)</span></div>-->
          <!--                        <div class="value">-->
          <!--                          <input type="text" class="form-control" v-model="state.txtNguoiTao" readonly="true">-->
          <!--                        </div>-->
          <!--                      </div>-->
          <!--                    </div>-->
          <!--                    <div class="col-sm-4 col-12">-->
          <!--                      <div class="info-row">-->
          <!--                        <div class="key w110">Đơn vị YC  <span class="red">(*)</span></div>-->
          <!--                        <div class="value">-->
          <!--                          <div class="select-custom">-->
          <!--                            <select2 :options="controls.cboKhachHangJira.list" v-model="controls.cboKhachHangJira.value" @change="cboDonViJira_SelectedIndexChanged">-->
          <!--                            </select2>-->
          <!--                          </div>-->
          <!--                        </div>-->
          <!--                      </div>-->
          <!--                    </div>-->
          <!--                  </div>-->
          <!--              <div class="info-row">-->
          <!--                  <div class="key w110">Tiêu đề  <span class="red">(*)</span></div>-->
          <!--                  <div class="value">-->
          <!--                      <input ref="txtTieude" type="text" class="form-control" v-model="controls.txtTieude" required>-->
          <!--                  </div>-->
          <!--              </div>-->
          <!--              <div class="info-row">-->
          <!--                  <div class="key w110">Mô tả  <span class="red">(*)</span></div>-->
          <!--                  <div class="value">-->
          <!--                      <textarea ref="txtMota" id="txtMota" cols="30" rows="40" class="form-control" style="height: 300px;resize: vertical;" :Value="controls.txtMota" @input="textareaInput" required title="Bạn chưa nhập mô tả" :disabled="disableMoTa"></textarea>-->
          <!--                  </div>-->
          <!--              </div>-->
          <div class="legend-title mart20">Danh sách kích hoạt toàn trình chưa thành công</div>
          <div class="info-row">
            <!--            <DataGrid-->
            <!--              class="table-result table-gachle"-->
            <!--              v-bind:columns="headers"-->
            <!--              v-bind:dataSource="list_hdtb"-->
            <!--              :enable-paging-server="false"-->
            <!--              :allowPaging="true"-->
            <!--              :commands="[-->
            <!--                      { name: 'GUI_YC', cssClass: 'btn btn-primary one-upload', title: 'Gửi YC' }-->
            <!--                    ]"-->
            <!--              :commandColumn="{ headerText: 'Thao tác', width: 200, firstColumn: false, show: true }"-->
            <!--              :showFilter="true"-->
            <!--              @commandClicked="xuly_yc"-->
            <!--            >-->
            <!--            </DataGrid>-->

            <DataGrid v-bind:columns="thueBao_grid.commonFields"
                      v-bind:dataSource="thueBao_grid.items"
                      dataKeyField="MA_TB"
                      :showFilter="true"
                      :allowPaging="true"
                      :enablePagingServer="false"
                      :showColumnCheckbox="true"
                      :enabledSelectFirstRow="false"
                      :panelDataHeight='"400"'
                      ref="DSThuebaoGrid"
                      @selectedRowChanged="onDSThuebaoGrid_RowSelected"
                      @commandClick="commandClick"
            >
            </DataGrid>
          </div>

        </div>
        <div class="box-form">
          <div class="legend-title mart20">Danh sách kích hoạt toàn trình đã thành công</div>
          <div class="info-row">

            <DataGrid v-bind:columns="thueBao_grid_done.commonFields"
                      v-bind:dataSource="thueBao_grid_done.items"
                      dataKeyField="MA_TB"
                      :showFilter="true"
                      :allowPaging="true"
                      :enablePagingServer="false"
                      :enabledSelectFirstRow="false"
                      :panelDataHeight='"400"'
                      ref="DSThuebaoGrid_Done"
            >
            </DataGrid>
          </div>

        </div>
      </div>
      <QL_BieuMau ref="popupQL_BieuMau" />
      <ejs-dialog :enableResize='true' :isModal="true" :allowDragging="true" :visible="false" ref="dlgErrorDialog"
                  :header="'Thông tin lỗi'"
                  showCloseIcon=true :closeOnEscape="true" width="30%" height="50%">
        <div>
          <div class="popup-body">
            <div id="object-view"></div>
          </div>
        </div>
      </ejs-dialog>
    </div>

  </ejs-dialog>
</template>
<script>
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment'
import api from './Api.js'
import QL_BieuMau from '../../popupCreateIssue_v2/QL_BieuMau.vue'
import commonFn from '@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions'
import fileDownload from 'js-file-download'
import axios from 'axios'

export default {
  components: {
    moment,
    DatePicker,
    api,
    QL_BieuMau
  },
  name: 'ppWorkflowActivation',
  data() {
    return {
      list_hdtb: [],
      headers: [
        { fieldName: 'nhom_dv', headerText: 'Nhóm dịch vụ', allowFiltering: true },
        { fieldName: 'ten_sanpham', headerText: 'Sản phẩm', allowFiltering: true },
        { fieldName: 'tieu_de', headerText: 'Tiêu đề', allowFiltering: true },
        { fieldName: 'mo_ta', headerText: 'Mô tả', allowFiltering: false },
        { fieldName: 'trangthai', headerText: 'Trạng thái', allowFiltering: false }
      ],
      disableMoTa: false,
      Loading: false,
      dateFormat: 'DD/MM/YYYY',
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
      tt_nd: {
        nhanvien_id: this.$root.token.getNhanVienID() * 1,
        nguoidung_id: this.$root.token.getNguoiDungID() * 1,
        phanvung_id: this.$root.token.getPhanVungID() * 1,
        donvi_id: this.$root.token.getDonViID() * 1,
        may_cn: '',
        ip_cn: this.$root.token.getIP(),
        ngay_cn: moment(new Date()).format('DD/MM/YYYY hh:mm A'),
        ngay_bg: moment(new Date().setSeconds(new Date().getSeconds() + 30)).format(
          'DD/MM/YYYY hh:mm A'
        ),
        ten_dv: '',
        nguoi_cn: this.$root.token.getUserName()
      },
      JiraService: {
        DEFAULT_PROJECT_ID: 10101,
        CUSTOM_FIELD_DANHMUC_DV_ID: 12600,
        CUSTOM_FIELD_DANHMUC_KH_ID: 10329,
        SERVICE_USER: 'dhsxkd_api',
        SERVICE_PASS: '*********'
      },
      controls: {
        cboProject: {
          list: [],
          value: 0
        },
        cboIssueType: {
          list: [],
          value: 0
        },
        cboSanPhamJira: {
          list: [],
          value: 0
        },
        cboDonViJira: {
          list: [],
          value: 0
        },
        cboKhachHangJira: {
          list: [],
          value: 0
        },
        cboMucDoUT: {
          list: [],
          value: 0,
          visible: true
        },
        dtpDuedate: {
          enabled: true,
          value: moment(new Date()).format('DD/MM/YYYY')
        },
        txtFileDinhKem: '',
        txtTieude: '',
        txtMota: '',
        txtMotaTemp: '',
        prevent_Exit: false
      },

      thueBao_grid: {
        items: [],
        checked: [],
        selected: {},
        fields: [],
        commonFields: [
          // { fieldName: 'ISCHECK', headerText: '', allowSorting: false, allowFiltering: false, textAlign: 'Center', width: 40, headerTemplate: () => { return this.columnsCheckboxAll() }, template: () => { return this.columnsCheckbox() }},
          {
            fieldName: 'MA_TB',
            headerText: 'Số máy/Acc',
            allowFiltering: true,
            allowSorting: false,
            width: 120
          },
          {
            fieldName: 'TEN_TB',
            headerText: 'Tên thuê bao',
            allowFiltering: true,
            allowSorting: false,
            width: 150
          },
          {
            fieldName: 'DIACHI_LD',
            headerText: 'Địa chỉ lắp đặt',
            allowFiltering: true,
            allowSorting: false,
            width: 150
          },
          {
            fieldName: 'CHIEU_KM',
            headerText: 'Chiều khóa/mở',
            allowFiltering: true,
            allowSorting: false,
            width: 150
          },
          {
            fieldName: 'STATUS',
            headerText: 'Trạng thái kích hoạt',
            allowFiltering: true,
            allowSorting: false,
            width: 150
          },
          {
            headerText: 'Thao tác',
            allowFiltering: true,
            allowSorting: false,
            width: 70,
            commands: [{ buttonOption: { content: 'Kích hoạt', cssClass: 'e-flat', name: 'active' } }],
            textAlign: 'center'
          },
          {
            headerText: 'Lỗi',
            allowFiltering: true,
            allowSorting: false,
            width: 70,
            commands: [{ buttonOption: { content: 'Chi tiết lỗi', cssClass: 'e-flat', name: 'errorDetails' } }],
            textAlign: 'center'
          }
        ]
      },
      thueBao_grid_done: {
        items: [],
        checked: [],
        selected: {},
        fields: [],
        commonFields: [
          // { fieldName: 'ISCHECK', headerText: '', allowSorting: false, allowFiltering: false, textAlign: 'Center', width: 40, headerTemplate: () => { return this.columnsCheckboxAll() }, template: () => { return this.columnsCheckbox() }},
          {
            fieldName: 'MA_TB',
            headerText: 'Số máy/Acc',
            allowFiltering: true,
            allowSorting: false,
            width: 120
          },
          {
            fieldName: 'TEN_TB',
            headerText: 'Tên thuê bao',
            allowFiltering: true,
            allowSorting: false,
            width: 150
          },
          {
            fieldName: 'DIACHI_LD',
            headerText: 'Địa chỉ lắp đặt',
            allowFiltering: true,
            allowSorting: false,
            width: 150
          },
          {
            fieldName: 'CHIEU_KM',
            headerText: 'Chiều khóa/mở',
            allowFiltering: true,
            allowSorting: false,
            width: 150
          },
          {
            fieldName: 'STATUS',
            headerText: 'Trạng thái kích hoạt',
            allowFiltering: true,
            allowSorting: false,
            width: 150
          },
        ]
      }
    }
  },
  created: async function() {

  },
  mounted: function() {

  },
  watch: {},
  methods: {
    async xuly_yc(name, dataItem) {
      console.log('thaotacfile')
      console.log('name')
      if (name === 'GUI_YC') {
        console.log(dataItem)
      }
    },
    dialogClose() {
      this.$emit("closeFormKichhoatToantrinh");
      console.log('before close')
    },
    openDialog(data) {
      try {
        if (!data) {
          return
        }
        this.controls.txtTieude = ''
        this.controls.txtMota = ''
        this.controls.txtMotaTemp = ''
        this.controls.txtFileDinhKem = ''
        // this.$refs.file1.value= '';

        // this.state =  {
        //   baohong_id: data.baohong_id ? data.baohong_id : -1,
        //   phieu_baohong_id: data.phieu_baohong_id ? data.phieu_baohong_id : -1,
        //   hdtb_id: data.hdtb_id ? data.hdtb_id : -1,
        //   phieu_id: data.phieu_id ? data.phieu_id : -1,
        //   loai: data.loai ? data.loai : 1,
        //   kieu: data.kieu ?  data.kieu : 1,
        //   vip_giamsat: data.vip_giamsat ? data.vip_giamsat : false,
        // };
        // console.log(this.state);
        console.log(data)
        this.$refs.dialogCreateIssue.show()
        this.load_Data(data)
        // this.pageLoad(data);
      } catch (ex) {
        commonFn.showException(this, ex)
      }
    },
    hideDialog() {
      this.$refs.dialogCreateIssue.hide()
    },
    async textareaInput(e) {
      this.controls.txtMota = e.target.value
    },
    async pageLoad(dulieu) {
      try {
        this.loading(true)
        this.list_hdtb = []
        this.tt_nd.may_cn = await this.$root.token.getMachine()
        this.tt_nd.ip_cn = await this.$root.token.getIP()

        //this.$refs.txtMota.value = "";

        let ds_dv = []
        let sp_id = 0
        let dv_id = 0
        let loaitb_id = 0
        let hdtb_id = this.state.hdtb_id
        let tieude = ''
        let mota = ''
        let phieu_id = ''

        let req = {
          'kieu': 1,
          'hdtb_id': this.state.hdtb_id,
          'baohong_id': -1,
          'tinh_id': this.tt_nd.phanvung_id
        }
        await this.NAP_PROJECT_MACDINH()
        // await this.NAP_DS_DONVI_SANPHAM_JIRA(dv_id);
        await this.NAP_DS_KHACHHANG_JIRA()
        let data_nhomdv = this.GetData(await api.jira_get_customfield_options(this.axios, { 'customFieldId': this.JiraService.CUSTOM_FIELD_DANHMUC_DV_ID }))
        this.state.danhmuc_dv = data_nhomdv
        for (const x of dulieu) {
          let req = {
            'kieu': 1,
            'hdtb_id': x.hdtb_id,
            'baohong_id': -1,
            'tinh_id': this.tt_nd.phanvung_id
          }
          ds_dv = this.GetData(await api.lay_thongtin_jira(this.axios, req))
          sp_id = Number(ds_dv[0]['sanpham_id'])
          dv_id = Number(ds_dv[0]['jr_danhmuc_id'])
          let ten_dm = ''
          loaitb_id = Number(ds_dv[0]['loaitb_id'])

          if (data_nhomdv && data_nhomdv.length > 0) {
            ten_dm = data_nhomdv.find(x => x.JR_DANHMUC_ID == dv_id)
          }

          let donviObj = this.state.danhmuc_dv.filter(x => x.JR_DANHMUC_ID == dv_id)
          if (donviObj && donviObj.length > 0) {
            this.controls.cboSanPhamJira.list = donviObj.map((x) => {
              return { id: x.SANPHAM_ID, text: x.LOAIHINH_TB }
            })
          }
          console.log(this.controls.cboSanPhamJira.list)
          let ten_sanpham = this.controls.cboSanPhamJira.list.find(x => x.id == sp_id)

          /*  let data_yc = this.GetData(await api.sp_vip_nap_thongtin_mota_tinh_frm_create_issue(this.axios, { "vhdtb_id": x.hdtb_id, "vnhanvien_id": this.tt_nd.nhanvien_id }));
            console.log(data_yc);

            if(data_yc && data_yc.length > 0){
              tieude = data_yc[0].TIEUDE;
              mota = data_yc[0].MOTA;
            }
            console.log(tieude);*/

          tieude = this.GetData(await api.fn_lay_thongtin_tieude_jira(this.axios, { 'hdtb_id': x.hdtb_id }))

          mota = this.GetData(await api.lay_thongtin_kh_it_360(this.axios, {
            'vhdtb_id': x.hdtb_id,
            'vloaitb_id': loaitb_id,
            'vnhanvien_id': this.tt_nd.nhanvien_id
          }))

          this.list_hdtb.push({
            sanpham_id: sp_id,
            nhomdv_id: dv_id,
            nhom_dv: ten_dm.JR_TEN_DANHMUC,
            ten_sanpham: ten_sanpham.text,
            tieu_de: tieude,
            mo_ta: mota,
            hdtb_id: x.hdtb_id,
            loaitb_id: loaitb_id,
            phieu_id: x.phieu_id,
            trangthai: 'Cho xu ly...'
          })
        }

        /*ds_dv = this.GetData(await api.lay_thongtin_jira(this.axios, req));

        if(ds_dv && ds_dv.length > 0){
            sp_id = Number(ds_dv[0]["sanpham_id"]);
            dv_id = Number(ds_dv[0]["jr_danhmuc_id"]);
            loaitb_id = Number(ds_dv[0]["loaitb_id"]);
            //region cuongpq kiểm tra ẩn mô tả với loại hình dịch vụ CNTT
            let svalue = this.GetData(await this.axios.get(`/web-hopdong/vbn/fn_kiemtra_chophep_sua_mota_it360?vloaitb_id=${loaitb_id}`));
            if (svalue != "OK")
            {
              this.disableMoTa = true;
            }
            else {
              this.disableMoTa = false;
            }
            //end region
        }
        else{
          this.$toast.warning("Dịch vụ này không tích hợp lên IT360 !");
          this.loading(false);
          this.hideDialog();
          return;
        }*/

        this.state.txtNguoiTao = this.JiraService.SERVICE_USER

        // for (var it of this.controls.cboDonViJira.list)
        // {
        //     if (it.id == dv_id)
        //     {
        //         this.controls.cboDonViJira.value = it.id;
        //         break;
        //     }
        // }

        // for (var it of this.controls.cboSanPhamJira.list)
        // {
        //     if (it.id == sp_id)
        //     {
        //         this.controls.cboSanPhamJira.value = it.id;
        //         break;
        //     }
        // }

        // await this.NAP_THONGTIN_MOTA(hdtb_id, loaitb_id);
        await this.NAP_DS_ISSUETYPE()

        this.loading(false)
      } catch (ex) {
        commonFn.showException(this, ex)
      }
      this.loading(false)
    },
    async cboIssueType_EditValueChanged(e) {

      try {
        let data = this.GetData(await api.sp_lay_mucdo_uutien_frmcreateissue(this.axios, { vissue_type_id: e }))
        if (data && data.length > 0) {
          this.controls.cboMucDoUT.visible = true
          this.controls.cboMucDoUT.list = data.map((x) => {
            return { id: x.mdut_id, text: x.mucdo_ut }
          })
          this.controls.cboMucDoUT.value = this.controls.cboMucDoUT.list[0].id
        } else {
          this.controls.cboMucDoUT.visible = false
          this.controls.cboMucDoUT.list = []
        }
      } catch (ex) {
        commonFn.showException(this, ex)
      }
    },
    async NAP_PROJECT_MACDINH() {
      try {
        let data = this.GetData(await api.Get_Project(this.axios, { projectidorkey: this.JiraService.DEFAULT_PROJECT_ID }))

        if (data) {
          this.controls.cboProject.list = [{ id: data.id * 1, text: data.name }]
          this.controls.cboProject.value = this.controls.cboProject.list[0].id
        }
      } catch (ex) {
        commonFn.showException(this, ex)
      }
    },
    async NAP_DS_ISSUETYPE() {
      try {
        let data = this.GetData(await api.Get_IssueTypes_V2(this.axios, { 'vloaihd_id': this.state.loaihd_id }))
        if (data && data.length > 0) {
          this.controls.cboIssueType.list = data.map((x) => {
            return { id: x.id * 1, text: x.name }
          })
          this.controls.cboIssueType.value = this.controls.cboIssueType.list[0].id
          if (this.state.kieu == 5) {
            this.controls.cboIssueType.value = 10207
          }
          if (this.state.kieu == 6) {
            this.controls.cboIssueType.value = 10211
          }
          await this.cboIssueType_EditValueChanged(this.controls.cboIssueType.value)
        }
      } catch (ex) {
        commonFn.showException(this, ex)
      }
    },
    async NAP_DS_DONVI_SANPHAM_JIRA(dv_id) {
      let data = this.GetData(await api.jira_get_customfield_options(this.axios, { 'customFieldId': this.JiraService.CUSTOM_FIELD_DANHMUC_DV_ID }))

      if (data && data.length > 0) {
        this.state.danhmuc_dv = data
        this.controls.cboDonViJira.list = data.map((x) => {
          return { id: x.JR_DANHMUC_ID, text: x.JR_TEN_DANHMUC }
        })
        this.controls.cboDonViJira.value = dv_id
        await this.NAP_DS_SANPHAM_THEO_DONVI(dv_id)
      }

    },
    async NAP_DS_SANPHAM_THEO_DONVI(danhmuc_id) {
      console.log('danhmuc_id', danhmuc_id)
      if (!danhmuc_id) return
      if (!this.state.danhmuc_dv) return
      let donviObj = this.state.danhmuc_dv.filter(x => x.JR_DANHMUC_ID == danhmuc_id)
      if (donviObj && donviObj.length > 0) {
        this.controls.cboSanPhamJira.list = donviObj.map((x) => {
          return { id: x.SANPHAM_ID, text: x.LOAIHINH_TB }
        })
        this.controls.cboSanPhamJira.value = this.controls.cboSanPhamJira.list[0].id
      }
    },
    async NAP_DS_KHACHHANG_JIRA() {
      let data = this.GetData(await api.jira_get_customfield_options(this.axios, { 'customFieldId': this.JiraService.CUSTOM_FIELD_DANHMUC_KH_ID }))
      if (data && data.length > 0) {
        this.controls.cboKhachHangJira.list = data.map((x) => {
          return { id: x.JR_KHACHHANG_ID, text: x.JR_TEN_KH }
        })
        let kh = data.find(x => x.JR_TINH_ID == this.tt_nd.phanvung_id)
        if (kh) {
          this.controls.cboKhachHangJira.value = kh.JR_KHACHHANG_ID
        }
      }
    },
    async VIP_NAP_THONGTIN_MOTA_TINH(hdtb_id, loaitb_id) {
      let data = this.GetData(await api.sp_vip_nap_thongtin_mota_tinh_frm_create_issue(this.axios, {
        'hdtb_id': this.state.hdtb_id,
        'vnhanvien_id': this.tt_nd.nhanvien_id
      }))

      if (data && data.length > 0) {
        this.controls.txtTieude = data[0].tieude
        this.controls.txtMota = data[0].mota
      }
    },
    async NAP_THONGTIN_MOTA(hdtb_id, loaitb_id) {
      try {
        let data = this.GetData(await api.fn_lay_thongtin_tieude_jira(this.axios, { 'hdtb_id': hdtb_id }))
        this.controls.txtTieude = data
        let data1 = this.GetData(await api.lay_thongtin_kh_it_360(this.axios, {
          'vhdtb_id': hdtb_id,
          'vloaitb_id': loaitb_id,
          'vnhanvien_id': this.tt_nd.nhanvien_id
        }))
        this.controls.txtMota = data1
        this.$refs.txtMota.value = data1
      } catch (ex) {
        console.error(ex)
      }
    },
    async Load_Thongtin_baohong(vbaohong_id, vphieu_id) {
      this.controls.txtTieude = ''
      this.controls.txtMota = ''
      this.controls.txtFileDinhKem = ''

      let data = this.GetData(await api.sp_lay_thongtin_tieudo_mota_baohong_frm_create_issue(this.axios, {
        'vphieu_id': vphieu_id,
        'vkieu': this.state.kieu
      }))
      if (data && data.length > 0) {
        data = commonFn.LowerCasePropertyList(data)
        this.controls.txtTieude = data[0].tieude
        this.controls.txtMota = data[0].mota
        this.$refs.txtMota.value = data[0].mota
      }
    },
    async cboDonViJira_SelectedIndexChanged(e) {
      await this.NAP_DS_SANPHAM_THEO_DONVI(e)
    },
    async btnBieuMau_Click() {
      this.$refs.popupQL_BieuMau.openDialog()
    },
    async btnGhiLai_Click() {
      //await this.KIEMTRA_VA_TAOISSUE();
      try {
        this.loading(true)
        await this.TAO_ISSUE_THEO_DS(this.list_hdtb)
      } catch (e) {
        console.log(e)
        this.$toast.error('Lỗi!')
      } finally {
        this.loading(false)
      }
    },

    async TAO_ISSUE_THEO_DS(ds_hdtb) {
      let i = 0
      for (const x of ds_hdtb) {
        let svalue = this.GetData(await api.kiemtra_gui_yc_it360(this.axios, { 'vhdtb_id': x.hdtb_id, 'vkieu': 1 }))
        if (svalue != 'OK') {
          x.trangthai = svalue
          // this.list_hdtb = [...this.list_hdtb]
          continue
        } else {
          x.trangthai = svalue
          //lấy thông tin lables
          let labels = this.GetData(await api.sp_lay_thongtin_label_jira(this.axios, {
            'v_hdtb_id': x.hdtb_id,
            'v_baohong_id': -1
          }))
          console.log('labels', labels)
          let mucdo_ut = '-1'
          if (labels && labels.length > 0) {
            labels = [labels[0].MA_KH, labels[0].MA_TB, labels[0].LOAIHINH]
          } else {
            labels = []
          }
          console.log(labels)

          let customfield_14300_id = x.nhomdv_id
          let customfield_14300_child_id = x.sanpham_id
          let customfield_10329_id = this.controls.cboKhachHangJira.value
          let issuetype = this.controls.cboIssueType.value

          let req = {
            'description': x.mo_ta,
            'duedate': moment(this.controls.dtpDuedate.value, 'DD/MM/YYYY').format('YYYY-MM-DD'),
            'issuetype': issuetype.toString(),
            'labels': labels,
            'project': this.controls.cboProject.value.toString(),
            'reporter': this.state.txtNguoiTao,
            'summary': x.tieu_de
          }

          req.customfield_10328_id = customfield_14300_id.toString()
          req.customfield_10328_child_id = customfield_14300_child_id.toString()
          req.customfield_10329_id = customfield_10329_id.toString()

          let data_create_issue = this.GetData(await api.Creat_Issue(this.axios, req))

          console.log('Creat_Issue', data_create_issue)

          if (data_create_issue && data_create_issue.id && Number(data_create_issue.id) > 0) {

            req = {
              'issueKeyOrID': data_create_issue.key,
              'login': {
                'password': '',
                'username': ''
              },
              'participant': this.tt_nd.nguoi_cn
            }
            let add_Issue = this.GetData(await api.add_Issue_Participant(this.axios, req))  // Chuyen vi tri theo yeu cau cua DuyBN
            req = {
              'issueKeyOrID': data_create_issue.key
            }
            let issueInfo = this.GetData(await api.Get_Issue_Info(this.axios, req))
            console.log('issueInfo', issueInfo)
            if (!issueInfo) {
              issueInfo = data_create_issue
            }

            if (x.hdtb_id > 0) {
              req = {
                'vloai': 1,
                'vhdtb_id': x.hdtb_id,
                'vbaohong_id': 0,
                'vphieu_id': x.phieu_id,
                'vvip_xl_tinh': '0',
                'vtinh_id': this.tt_nd.phanvung_id,
                'vissue_id': data_create_issue.id,
                'vkey': data_create_issue.key,
                'vuser_tao': this.JiraService.SERVICE_USER,
                'vtieude': x.tieu_de,
                'vnoidung': x.mo_ta,
                'vnguoi_cn': this.tt_nd.nguoi_cn
              }

              req.vvip_xl_tinh = 0
              req.vtinh_id = -1
              let rsMap = this.GetData(await api.fn_insert_jira_giaophieu_issue(this.axios, req))
              if (rsMap != 'OK') {
                x.trangthai = 'Có lỗi khi cập nhật yêu cầu vào hợp đồng'
              }
            }

            this.$emit('onSuccess', issueInfo)
            this.$toast.success('Tạo yêu cầu thành công')
            //   this.hideDialog();
          } else {
            if (data_create_issue.errorMessages && data_create_issue.errorMessages.length > 0) {
              // this.$toast.error("Có lỗi khi tạo yêu cầu lên hệ thống IT360. " + JSON.stringify(data_create_issue.errors[0]));
              x.trangthai = 'Có lỗi khi tạo yêu cầu lên hệ thống IT360. ' + JSON.stringify(data_create_issue.errors[0])
              return
            } else if (data_create_issue.errors && data_create_issue.errors.length > 0) {
              //this.$toast.error("Có lỗi khi tạo yêu cầu lên hệ thống IT360. " + JSON.stringify(data_create_issue.errors[0]));
              x.trangthai = 'Có lỗi khi tạo yêu cầu lên hệ thống IT360. ' + JSON.stringify(data_create_issue.errors[0])
              return
            } else
              //  this.$toast.error("Có lỗi khi tạo yêu cầu lên hệ thống IT360. " + JSON.stringify(data_create_issue));
            {
              x.trangthai = 'Có lỗi khi tạo yêu cầu lên hệ thống IT360. ' + JSON.stringify(data_create_issue.errors[0])
            }
          }
        }
        i++
      }
      this.list_hdtb = [...this.list_hdtb]

    },

    async KIEMTRA_VA_TAOISSUE() {
      console.log('KIEMTRA_VA_TAOISSUE')
      this.loading(true)
      try {
        if (this.state.hdtb_id && this.state.hdtb_id > 0) {
          let svalue = this.GetData(await api.kiemtra_gui_yc_it360(this.axios, {
            'vhdtb_id': this.state.hdtb_id,
            'vkieu': 1
          }))
          if (svalue != 'OK') {
            this.$toast.warning(svalue)
            this.loading(false)
            return
          }
        }

        //lấy thông tin lables
        let labels = this.GetData(await api.sp_lay_thongtin_label_jira(this.axios, {
          'v_hdtb_id': this.state.hdtb_id,
          'v_baohong_id': this.state.baohong_id
        }))
        console.log('labels', labels)
        let mucdo_ut = (this.controls.cboMucDoUT.list.length > 0 ? this.controls.cboMucDoUT.value : '-1')
        if (labels && labels.length > 0) {
          labels = [labels[0].MA_KH, labels[0].MA_TB, labels[0].LOAIHINH]
        } else {
          labels = []
        }
        let customfield_14300_id = this.controls.cboDonViJira.value
        let customfield_14300_child_id = this.controls.cboSanPhamJira.value
        let customfield_10329_id = this.controls.cboKhachHangJira.value
        let issuetype = this.controls.cboIssueType.value
        let req = {
          'description': this.controls.txtMota,
          'duedate': moment(this.controls.dtpDuedate.value, 'DD/MM/YYYY').format('YYYY-MM-DD'),
          'issuetype': issuetype.toString(),
          'labels': labels,
          'project': this.controls.cboProject.value.toString(),
          'reporter': this.state.txtNguoiTao,
          'summary': this.controls.txtTieude
        }

        //CODE sua theo moi api chua dap ung se bo sung sau
        if (mucdo_ut != '-1' && issuetype == '10207') {
          req.customfield_10328_id = customfield_14300_id.toString()
          req.customfield_10328_child_id = customfield_14300_child_id.toString()

          req.customfield_10329_id = customfield_10329_id.toString()
          req.customfield_12601_id = mucdo_ut.toString()
        } else if (mucdo_ut != '-1' && issuetype == '10211') {
          req.customfield_10328_id = customfield_14300_id.toString()
          req.customfield_10328_child_id = customfield_14300_child_id.toString()
          req.customfield_10329_id = customfield_10329_id.toString()
          req.customfield_10335_id = mucdo_ut.toString()
        } else {
          req.customfield_10328_id = customfield_14300_id.toString()
          req.customfield_10328_child_id = customfield_14300_child_id.toString()
          req.customfield_10329_id = customfield_10329_id.toString()
        }

        let data = this.GetData(await api.Creat_Issue(this.axios, req))
        // if(data && data == "ok"){
        //     //test
        //     data = {"id":"1366844","key":"BSS-47312","self":"https://cntt.vnpt.vn/rest/api/2/issue/1366844"};
        // }
        console.log('Creat_Issue', data)
        if (data && data.id && Number(data.id) > 0) {
          console.log('this.state.files', this.state.files)
          if (this.state.files && this.state.files.length > 0) {
            try {
              let formData = new FormData()
              for (var file of this.state.files) {
                formData.append('files', file)
              }
              formData.append('issueKeyOrID', data.key)
              formData.append('username', '')
              formData.append('password', '')
              let attRes = this.GetData(await api.add_Issue_Attachments(this.axios, formData, { headers: { 'Content-Type': 'multipart/form-data' } }))
              if (!attRes || (attRes.length && attRes.length == 0) || (attRes.length > 0 && (!attRes[0].id || attRes[0].id <= 0))) {
                this.$toast.success('Lỗi upload file đính kèm')
              }
            } catch (ex) {
              commonFn.showException(this, ex)
            }

          }
          req = {
            'issueKeyOrID': data.key,
            'login': {
              'password': '',
              'username': ''
            },
            'participant': this.tt_nd.nguoi_cn
          }
          let add_Issue = this.GetData(await api.add_Issue_Participant(this.axios, req))  // Chuyen vi tri theo yeu cau cua DuyBN
          req = {
            'issueKeyOrID': data.key
          }
          let issueInfo = this.GetData(await api.Get_Issue_Info(this.axios, req))
          console.log('issueInfo', issueInfo)
          if (!issueInfo) {
            issueInfo = data
          }

          if (this.state.hdtb_id > 0) {
            req = {
              'vloai': this.state.loai,
              'vhdtb_id': this.state.hdtb_id,
              'vbaohong_id': 0,
              'vphieu_id': this.state.phieu_id,
              'vvip_xl_tinh': '1',
              'vtinh_id': this.tt_nd.phanvung_id,
              'vissue_id': data.id,
              'vkey': data.key,
              'vuser_tao': this.JiraService.SERVICE_USER,
              'vtieude': this.controls.txtTieude,
              'vnoidung': this.controls.txtMota,
              'vnguoi_cn': this.tt_nd.nguoi_cn
            }
            if (this.state.kieu == 3) {
              req.vvip_xl_tinh = 1
              let rsMap = this.GetData(await api.fn_insert_jira_giaophieu_issue(this.axios, req))
              if (rsMap != 'OK') {
                this.$toast.error('Có lỗi khi cập nhật yêu cầu vào hợp đồng')
              }
            } else {
              req.vvip_xl_tinh = 0
              req.vtinh_id = -1
              let rsMap = this.GetData(await api.fn_insert_jira_giaophieu_issue(this.axios, req))
              if (rsMap != 'OK') {
                this.$toast.error('Có lỗi khi cập nhật yêu cầu vào hợp đồng')
              }
            }
          }

          this.$emit('onSuccess', issueInfo)
          this.$toast.success('Tạo yêu cầu thành công')
          this.hideDialog()
        } else {
          if (data.errorMessages && data.errorMessages.length > 0) {
            this.$toast.error('Có lỗi khi tạo yêu cầu lên hệ thống IT360. ' + JSON.stringify(data.errors[0]))
            return
          } else if (data.errors && data.errors.length > 0) {
            this.$toast.error('Có lỗi khi tạo yêu cầu lên hệ thống IT360. ' + JSON.stringify(data.errors[0]))
            return
          } else {
            this.$toast.error('Có lỗi khi tạo yêu cầu lên hệ thống IT360. ' + JSON.stringify(data))
          }
        }
      } catch (ex) {
        commonFn.showException(this, ex)
      }
      this.loading(false)
    },
    async changeFileUpload(e) {
      console.log('changeFileUpload', e)
      let files = this.$refs.file1.files
      this.controls.txtFileDinhKem = ''
      if (files && files.length > 0) {
        this.state.files = files
        this.controls.txtFileDinhKem = Array.from(files).map((x) => {
          return x.name
        }).join()
      }
      console.log(this.controls.txtFileDinhKem)
    },
    GetData: function(response) {
      if (response.data.error === 200 || response.data.error === '200' || response.data.faultCode == '') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
        if (response.data && response.data.data) {
          return response.data.data
        }
      }
      return null
    },

    ShowAlert: function(msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function(msg) {
      this.$toast.success(msg)
    },
    ShowError: function(msg) {
      this.$toast.error(msg)
    },
    load_Data: async function(data) {
      this.tt_nd.may_cn = await this.$root.token.getMachine()
      this.thueBao_grid.items = data
    },
    async commandClick(args) {
      console.log(args)
      if (args.commandColumn.buttonOption.name == 'active') {
        let confirm = await this.$bvModal
          .msgBoxConfirm(
            'Bạn có chắc muốn khóa số máy này không?',
            {
              title: 'Xác nhận hành động',
              centered: true,
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy'
            }
          )
        this.controls.prevent_Exit = true
        if (confirm) {
          //Nếu đã kích hoạt thành công thì chỉ cần hoàn thiện
          // await this.cmdKichHoat_Dynamic(args.rowData.hdtb_id)
          if(args.rowData.STATUSID != 2) { await this.cmdKichHoat_Dynamic(args.rowData.hdtb_id) }
          else  await this.cmdHTHS_Tudong(args.rowData.hdtb_id)
        }
        this.controls.prevent_Exit = false
      } else if (args.commandColumn.buttonOption.name == 'errorDetails') {
        if (args.rowData.STATUSID == 0) {
          return this.$toast.warning('Thuê bao chưa kích hoạt!')
        } else {
          //Show errors
          this.displayObject(args.rowData.data)
        }
      }
    },
    onDSThuebaoGrid_RowSelected(arg) {
      console.log(arg.rowData)
    },
    async cmdKichHoat_DynamicMultipleApis() {
      let listSelected = this.$refs.DSThuebaoGrid.getSelectedRecords()
      console.log(listSelected)
      if (listSelected.length == 0) {
        this.$toast.warning('Vui lòng chọn số máy để khóa!')
        return
      }

      let confirm = await this.$bvModal
        .msgBoxConfirm(
          'Bạn có chắc muốn khóa hàng loạt số máy đã chọn không?',
          {
            title: 'Xác nhận hành động',
            centered: true,
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          }
        )
      if (!confirm) return

      console.log(listSelected)
      let url = 'web-thicong/cnttdynamic/api'
      // let bodies = listSelected.map(e => {
      //   return ''
      //   let obj = {
      //     hdtb_id: e.hdtb_id,
      //   }
      //   return obj
      // })
      let bodies = []
      listSelected.forEach(e => {
        if (e.STATUSID != 2) {
          let obj = {
                 hdtb_id: e.hdtb_id,
               }
          bodies.push(obj)
        }
      })
      // let bodies = [
      //   {
      //     hdtb_id: 100001
      //   },
      //   {
      //     hdtb_id: 100002
      //   },
      //   {
      //     hdtb_id: 100003
      //   }
      // ]

      // Gọi các l��i gọi API và xử lý kết quả sau khi tất cả hoàn thành
      // await this.callMultipleApis(url, bodies)

      // Thêm logic đóng dialog khi hoàn thành xử lý
      this.controls.prevent_Exit = true
      await this.callMultipleApis(url, bodies)
      this.controls.prevent_Exit = false
    },

    async callMultipleApis(url, bodies) {
      // Tạo mảng các promise cho các lời gọi API
      const promises = bodies.map(body =>

        axios.post(url, body, {
          headers: {
            'Content-Type': 'application/json'
          }
        }).then(response => ({
          data: response.data.data,
          hdtb_id: body.hdtb_id
        }))
      )

      try {
        // Chờ tất cả các lời gọi API hoàn thành
        const results = await Promise.all(promises)

        // Xử lý kết quả sau khi tất cả các lời gọi API hoàn thành
        console.log('callMultipleApis')
        // console.log(results.map(result => result.data.data))

        results.forEach(result => {
          console.log(result)
          // Tìm phần tử tương ứng với hdtb_id trong thueBao_grid.items
          let item = this.thueBao_grid.items.find(item => item.hdtb_id == result.hdtb_id)

          // Nếu không tìm thấy, tạo mới một đối tượng
          if (!item) {
            // item = { hdtb_id: result.hdtb_id };
            // this.thueBao_grid.items.push(item);
            item.data = null
            return
          }

          // Gán dữ liệu từ kết quả API vào đối tượng
          item.data = result.data
          item.STATUS = result.data.status == '01' ? 'Kích hoạt Lỗi' : 'Kích hoạt thành công'
          item.STATUSID = 1 //0 chua kich hoat, 1 da kich hoat, 2 da hoan thien
        })
        this.thueBao_grid.items = [...this.thueBao_grid.items]
        await this.hoanThienHoSo_Multiple()

      } catch (error) {
        // Xử lý lỗi nếu có
        console.error('Error calling APIs:', error)
      }
    },

    async hoanThienHoSo_Multiple() {
      let url = 'web-thicong/hoinghi_truyenhinh/tudong_hths_khoamay_toantrinh_dvcntt'

      // let bodies = listSelected.map(e => {
      //   return ''
      //   let obj = {
      //     hdtb_id: e.hdtb_id,
      //   }
      //   return obj
      // })
      let bodies = []
      this.thueBao_grid.items.forEach(e => {
        if (e.STATUSID != 2) {
          let obj = {
            hdtb_id: e.hdtb_id,
            nguoi_cn: this.tt_nd.nguoi_cn,
            may_cn: this.tt_nd.may_cn,
          }
          bodies.push(obj)
        }
      })
      console.log('hoanThienHoSo_Multiple')
      console.log(bodies)
      const promises = bodies.map(body =>

        axios.post(url, body, {
          headers: {
            'Content-Type': 'application/json'
          }
        }).then(response => ({
          data: response.data.data,
          hdtb_id: body.hdtb_id
        }))
      )

      try {
        // Chờ tất cả các lời gọi API hoàn thành
        const results = await Promise.all(promises)

        // Xử lý kết quả sau khi tất cả các lời gọi API hoàn thành
        console.log('hoanThienHoSo_Multiple')
        console.log(results)
        // console.log(results.map(result => result.data.data))

        results.forEach(result => {
          console.log('1')
          console.log(result)
          // Tìm phần tử tương ứng với hdtb_id trong thueBao_grid.items
          if(result.data == 'ok') {
            let item = this.updateThuebaoGridData('Hoàn thiện thành công', 'Hoàn thiện thành công', 2, result.hdtb_id)
            const index = this.thueBao_grid.items.findIndex(item => item.hdtb_id == result.hdtb_id);

            // Nếu tìm thấy phần tử, xóa nó khỏi mảng
            if (index !== -1) {
              this.thueBao_grid.items.splice(index, 1);
            }
            this.thueBao_grid_done.items = [...this.thueBao_grid_done.items, item]
            this.$toast.success('Hoàn thiện hồ sơ thành công thuê bao: ' + item.MA_TB);
          }
          else {
            let item = this.updateThuebaoGridData(result.data, 'Hoàn thiện không thành công', 1, result.hdtb_id)
            this.$toast.error('Hoàn thiện hồ sơ không thành công thuê bao: ' + item.MA_TB);
          }
        })
        this.thueBao_grid.items = [...this.thueBao_grid.items]

      } catch (error) {
        // Xử lý lỗi nếu có
        console.error('Error calling APIs:', error)
      }
    },

    async cmdKichHoat_Dynamic(hdtb_id) {
      try {
        this.loading(true)
        this.controls.prevent_Exit = true
        const get_ketqua_dynamic = await this.ConvertResApiHBDT(
          api.cntt_dynamic_api(this.axios, { hdtb_id: hdtb_id, vdem: 0, vtype: 'tsbtnDongBoCMS_Click' }))
        const ketqua_status = get_ketqua_dynamic.status
        // if (ketqua_status == '00') {
        //   this.$toast.success(get_ketqua_dynamic.message)
        // } else {
        //   this.$toast.error(get_ketqua_dynamic.message)
        // }
        let item = this.thueBao_grid.items.find(item => item.hdtb_id == hdtb_id)
        console.log(item)
        if (!item) {
          // item = { hdtb_id: result.hdtb_id };
          // this.thueBao_grid.items.push(item);
          item.data = null
          return
        }        // Gán dữ liệu từ kết quả API vào đối tượng
        item.data = get_ketqua_dynamic
        item.STATUS = get_ketqua_dynamic.status == '01' ? 'Kích hoạt Lỗi' : 'Kích hoạt thành công'
        item.STATUSID = 1 //0 chua kich hoat, 1 da kich hoat, 2 da hoan thien

        console.log(get_ketqua_dynamic)
        //reload grid
        this.thueBao_grid.items = [...this.thueBao_grid.items]
        if(get_ketqua_dynamic.status == '00') {
        // if(get_ketqua_dynamic != '00') {//test
          this.$toast.success('Kích hoạt thành công')
          await this.cmdHTHS_Tudong(hdtb_id)
        }

      } catch (e) {
        console.error('Error:', e)
      } finally {
        this.loading(false)
        this.controls.prevent_Exit = false
      }
    },
    async ConvertResApiHBDT(response_input) {
      let response = await response_input
      if (response.data.errorCode && response.data.errorCode != '' && response.data.errorCode != '0' && response.data.faultString && response.data.faultString != '') {
        throw response.data.faultString
      }
      if (response.data.error === 200 || response.data.error === '200' || response.data.errorCode === 0) {
        if (response.data.error_code === 'BSS-00000000') {
          return response.data.data
        }
      } else {
        if (response.data.error === '204' || response.data.error === 204) {
          return []
        } else {
          return response.data.message
        }
      }
    },
    validation(args) {
      if (this.controls.prevent_Exit == true) {
        this.$toast.error('Đang thực thi, không thể thực hiện thao tác này')
        args.cancel = true
      } else {
        args.cancel = false
      }
    },

    displayObject(obj) {
      const container = document.getElementById('object-view');
      container.innerHTML = ''; // Xóa nội dung trước đó

      // Kiểm tra nếu obj không phải là object
      if (typeof obj !== 'object' || obj === null) {
        const div = document.createElement('div');
        div.textContent = obj; // Đặt nội dung div là obj
        container.appendChild(div);
      } else {
        // Nếu obj là một object, lặp qua các key và hiển thị chúng
        for (const key in obj) {
          if (obj.hasOwnProperty(key)) {
            const div = document.createElement('div');
            div.textContent = `${key}: ${obj[key]}`;
            container.appendChild(div);
          }
        }
      }
      this.$refs.dlgErrorDialog.show();
    },


    async cmdHTHS_Tudong(hdtb_id) {
      try {
        let body = {
          hdtb_id: hdtb_id,
          nguoi_cn: this.tt_nd.nguoi_cn,
          may_cn: this.tt_nd.may_cn
        }
        let rs = await api.tudong_hths_khoamay_toantrinh_dvcntt(this.axios, body)
        if(rs.data.data == 'ok') {
          let item = this.updateThuebaoGridData('Hoàn thiện thành công', 'Hoàn thiện thành công', 2, hdtb_id)
          // Tìm chỉ số của phần tử trong mảng
          const index = this.thueBao_grid.items.findIndex(item => item.hdtb_id == hdtb_id);

          // Nếu tìm thấy phần tử, xóa nó khỏi mảng
          if (index !== -1) {
            this.thueBao_grid.items.splice(index, 1);
          }

          this.thueBao_grid_done.items = [...this.thueBao_grid_done.items, item]
          this.$toast.success('Hoàn thiện hồ sơ thành công thuê bao: ' + item.MA_TB);
        }
        else {
          let item = this.updateThuebaoGridData(rs.data.data, 'Hoàn thiện không thành công', 1, hdtb_id)
          this.$toast.error('Hoàn thiện hồ sơ không thành công thuê bao: ' + item.MA_TB) ;
        }
      }
      catch (e) {
        console.log(e)
      }
    },
    updateThuebaoGridData(data, status, status_id, hdtb_id) {
      console.log('updateThuebaoGridData');
      console.log(data);

      // Tìm chỉ số của item trong mảng thueBao_grid.items
      let itemIndex = this.thueBao_grid.items.findIndex(item => item.hdtb_id == hdtb_id);

      // Nếu không tìm thấy item, trả về null
      if (itemIndex === -1) {
        let newItem = { hdtb_id: hdtb_id, data: null };
        this.thueBao_grid.items.push(newItem);
        return newItem;
      }

      // Gán dữ liệu từ kết quả API vào đối tượng mới
      let updatedItem = {
        ...this.thueBao_grid.items[itemIndex], // Giữ lại các thuộc tính cũ
        data: data,
        STATUS: status,
        STATUSID: status_id // 0 chua kich hoat, 1 da kich hoat, 2 da hoan thien
      };


      // Thay thế item cũ bằng item mới trong mảng
      this.thueBao_grid.items[itemIndex] = updatedItem;

      console.log(this.thueBao_grid.items[itemIndex])
      // Reset grid
      this.thueBao_grid.items = [...this.thueBao_grid.items];

      return updatedItem;
    }
  }
}
</script>

<style>
.chuyenTo .page-content {
  top: 130px !important;
}
</style>
