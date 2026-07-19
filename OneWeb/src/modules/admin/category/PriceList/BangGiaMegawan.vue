<template>
  <b-modal ref="modelbanggiamegawan" size="xl" hide-header hide-header-close hide-footer body-class="p-0" no-close-on-backdrop @show="handleShowModal">
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span> Cập nhật chi tiết giá cho thuê bao Megawan, TSL</div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#" @click.prevent="tsbtnNhapMoi_Click" :class="{ disabled: !tsbtnNhapMoi.Enabled }"> <span class="icon one-file-plus"></span>Nhập mới </a>
          </li>
          <li>
            <a href="#" @click.prevent="tsbtnGhiLai_Click" :class="{ disabled: !tsbtnGhiLai.Enabled }"> <span class="icon one-save"></span>Ghi lại </a>
          </li>
          <li>
            <a href="#" @click.prevent="tsbtnHuyBo_Click" :class="{ disabled: !tsbtnHuyBo.Enabled }"> <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Huỷ </a>
          </li>

          <li>
            <a href="#" @click.prevent="tsbtnXoa_Click" :class="{ disabled: !tsbtnXoa.Enabled }"> <span class="icon one-trash"></span>Xoá </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin tốc độ, giá cước đấu nối, cước thuê kênh</div>
          <div class="row">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w90">Tốc độ</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="tocdo_megawan_info.TOCDO" />
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Đấu nối MgWan</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="tocdo_megawan_info.CDN_MGWAN" />
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Đấu nối TSL</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="tocdo_megawan_info.CDN_TSL" />
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Dịch chuyển TSL</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="tocdo_megawan_info.CDC_TSL" />
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w90">Đơn vị</div>
                <div class="value">
                  <div class="select-custom">
                    <select2 :options="listbox.ds_dv_tinh.list" v-model="listbox.ds_dv_tinh.value">
                      <option disabled value="0">Select one</option>
                    </select2>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Thuê kênh MgWan</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="tocdo_megawan_info.CTK_MGWAN" />
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Thuê kênh TSL</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="tocdo_megawan_info.CTK_TSL" />
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Đổi tốc độ TSL</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="tocdo_megawan_info.DTD_TSL" />
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w90">Tốc độ quy đổi</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="tocdo_megawan_info.CL_TOCDO_QD" />
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Thuê kênh Metronet</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="tocdo_megawan_info.CTK_METRO" />
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Ghi chú</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="tocdo_megawan_info.GHICHU" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Bảng giá cước lắp đặt mới megawan</div>
          <DataGrid v-bind:columns="listbox.ds_tocdo_megawan.headers" v-bind:dataSource="listbox.ds_tocdo_megawan.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" @selectedItemsChanged="dgvDsChange"> </DataGrid>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import API from './API'
import DatabaseConstants from '@/const/enums/DBConstants'
export default {
  components: { DatabaseConstants },
  name: 'BangGiaMegawan',

  data() {
    return {
      tsbtnNhapMoi: { Enabled: true },
      tsbtnGhiLai: { Enabled: true },
      tsbtnXoa: { Enabled: true },
      tsbtnHuyBo: { Enabled: true },

      tocdo_megawan_info: {},
      dgrList: {
        cols: [
          { fieldName: 'toc_do', headerText: 'Tốc độ', allowfiltering: true },
          { fieldName: 'cdn_mgwan', headerText: 'Đấu nối Mgwan', allowfiltering: true },
          { fieldName: 'ctk_mgwan', headerText: 'Thuê kênh Mgwan', allowfiltering: true },
          { fieldName: 'ctk_metro', headerText: 'Thuê kênh Metronet', allowfiltering: true },
          { fieldName: 'cdn_tsl', headerText: 'Đấu nối TSL', allowfiltering: true },
          { fieldName: 'ctk_tsl', headerText: 'Thuê kênh TSL', allowfiltering: true },
          { fieldName: 'cdc_tsl', headerText: 'Dịch chuyển TSL', allowfiltering: true },
          { fieldName: 'dtd_tsl', headerText: 'Đổi tốc độ TSL', allowfiltering: true },
          { fieldName: 'cl_tocdo_qd', headerText: 'Tốc độ QD', allowfiltering: true }
        ],
        list: []
      },
      listbox: {
        ds_dv_tinh: {
          list: [],
          value: ''
        },
        ds_tocdo_megawan: {
          list: [],
          headers: [
            { fieldName: 'toc_do', headerText: 'Tốc độ', allowfiltering: true },
            { fieldName: 'cdn_mgwan', headerText: 'Đấu nối Mgwan', allowfiltering: true },
            { fieldName: 'ctk_mgwan', headerText: 'Thuê kênh Mgwan', allowfiltering: true },
            { fieldName: 'ctk_metro', headerText: 'Thuê kênh Metronet', allowfiltering: true },
            { fieldName: 'cdn_tsl', headerText: 'Đấu nối TSL', allowfiltering: true },
            { fieldName: 'ctk_tsl', headerText: 'Thuê kênh TSL', allowfiltering: true },
            { fieldName: 'cdc_tsl', headerText: 'Dịch chuyển TSL', allowfiltering: true },
            { fieldName: 'dtd_tsl', headerText: 'Đổi tốc độ TSL', allowfiltering: true },
            { fieldName: 'cl_tocdo_qd', headerText: 'Tốc độ QD', allowfiltering: true }
          ]
        }
      }
    }
  },
  methods: {
    handleShowModal: async function () {
      this.loading(true)

      // var data={
      //         "command":"select '1' DV_TINH_ID, 'Kb' DV_TINH from dual union select '2' DV_TINH_ID, 'Mb' DV_TINH from dual union select '3' DV_TINH_ID, 'Gb' DV_TINH from dual union select '5' DV_TINH_ID, 'STM1' DV_TINH from dual"
      //     };

      await API.sp_lay_dv_tinh(this.axios, null).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000')
          this.listbox.ds_dv_tinh.list = response.data.data.map((x) => {
            return { id: x.dv_tinh_id, text: x.dv_tinh }
          })
        else this.listbox.ds_dv_tinh.list = []
      })

      this.SelectList()
      this.SetButton(0)

      this.loading(false)
    },
    hideModal() {
      this.$refs.modelbanggiamegawan.hide()
    },

    showModal() {
      this.$refs.modelbanggiamegawan.show()
    },

    SelectList: async function () {
      this.loading(true)
      //  var data={
      //       "command":"SELECT tocdo_id,tocdo,donvi,cdn_mgwan,ctk_mgwan,cdn_tsl,ctk_tsl,ghichu,tocdo||' '||donvi toc_do ,cdc_tsl,dtd_tsl,ctk_metro, tocdo_qd FROM " + DatabaseConstants.DB2 + ".tocdo_kenh order by donvi,tocdo asc"
      //   };

      await API.sp_lay_tocdo_v2(this.axios, null).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') this.listbox.ds_tocdo_megawan.list = response.data.data
        else this.listbox.ds_tocdo_megawan.list = []
      })
      this.loading(false)
    },

    SetButton(kieu) {
      this.tsbtnNhapMoi.Enabled = false
      this.tsbtnGhiLai.Enabled = false
      this.tsbtnXoa.Enabled = false
      this.tsbtnHuyBo.Enabled = false

      if (kieu == -1) {
        //Bat dau
        this.tsbtnGhiLai.Enabled = true
        this.tsbtnHuyBo.Enabled = true
      }

      if (kieu == 0) {
        //Bat dau
        this.tsbtnNhapMoi.Enabled = true
        this.tocdo_megawan_info = {}
      }

      if (kieu == 1) {
        //Them moi
        this.tsbtnGhiLai.Enabled = true
        this.tsbtnHuyBo.Enabled = true
        this.tocdo_megawan_info = {}
      }

      if (kieu == 2) {
        //Huy
        this.tsbtnNhapMoi.Enabled = true
        this.tsbtnXoa.Enabled = true
        this.tocdo_megawan_info = {}
      }

      if (kieu == 3) {
        //Edit
        this.tsbtnNhapMoi.Enabled = true
        this.tsbtnGhiLai.Enabled = true
        this.tsbtnHuyBo.Enabled = true
        this.tsbtnXoa.Enabled = true
      }
      if (kieu == 4) {
        //Edit
        this.tsbtnNhapMoi.Enabled = false
        this.tsbtnGhiLai.Enabled = false
        this.tsbtnHuyBo.Enabled = false
      }
    },

    dgvDsChange: function (obj) {
      this.tocdo_megawan_info = Object.assign({}, obj[0])
      if (obj[0].donvi.toUpperCase() == 'KB') this.listbox.ds_dv_tinh.value = 1
      if (obj[0].donvi.toUpperCase() == 'MB') this.listbox.ds_dv_tinh.value = 2
      if (obj[0].donvi.toUpperCase() == 'GB') this.listbox.ds_dv_tinh.value = 3
      if (obj[0].donvi.toUpperCase() == 'STM1') this.listbox.ds_dv_tinh.value = 5
      let row = obj[0]
      let itocdo_id = row.tocdo_id
      this.tocdo_megawan_info.tocdo = row.toc_do
      if (row.ctk_mgwan != '') this.tocdo_megawan_info.CTK_MGWAN = row.ctk_mgwan //.ToString(Format.FM_TIEN_VND)
      else this.tocdo_megawan_info.CTK_MGWAN = '0'
      if (row.cdn_mgwan != '') this.tocdo_megawan_info.CDN_MGWAN = row.cdn_mgwan //.ToString(Format.FM_TIEN_VND)
      else this.tocdo_megawan_info.CDN_MGWAN = '0'
      //TuấnNA thêm code hiển thị cước thuê kênh và đấu nối TSL --- Ngày 07/11/2010
      if (row.ctk_tsl != '') this.tocdo_megawan_info.CTK_TSL = row.ctk_tsl //.ToString(Format.FM_TIEN_VND)
      else this.tocdo_megawan_info.CTK_TSL= '0'
      if (row.cdn_tsl != '') this.tocdo_megawan_info.CDN_TSL = row.cdn_tsl //.ToString(Format.FM_TIEN_VND)
      else this.tocdo_megawan_info.CDN_TSL = '0'
      if (row.cdc_tsl != '') this.tocdo_megawan_info.CDC_TSL = row.cdc_tsl //.ToString(Format.FM_TIEN_VND)
      else this.tocdo_megawan_info.CDC_TSL = '0'
      if (row.dtd_tsl != '') this.tocdo_megawan_info.DTD_TSL = row.dtd_tsl //.ToString(Format.FM_TIEN_VND)
      else this.tocdo_megawan_info.DTD_TSL = '0'
      if (row.ctk_metro != '') this.tocdo_megawan_info.CTK_METRO = row.ctk_metro //.ToString(Format.FM_TIEN_VND)
      else this.txtThueKenhMetro.value = '0'
      if (row.cl_tocdo_qd != '') this.listbox.ds_dv_tinh.value = row.cl_tocdo_qd //.ToString(Format.FM_TIEN_VND)
      else this.listbox.ds_dv_tinh.value = '0'
      this.tocdo_megawan_info.GHICHU = row.ghichu
      this.listbox.ds_dv_tinh.value = row.donvi
      this.SetButton(3)
    },

    tsbtnNhapMoi_Click: function () {
      this.SetButton(1)
    },

    tsbtnHuyBo_Click: function () {
      this.SetButton(0)
    },

    tsbtnGhiLai_Click: async function () {
      if (this.tocdo_megawan_info.TOCDO == null || this.tocdo_megawan_info.TOCDO == undefined) {
        this.$toast.error('Bạn chưa nhập tôc độ!')
        return
      }

      if (this.tocdo_megawan_info.CDN_MGWAN == null || this.tocdo_megawan_info.CDN_MGWAN == undefined) {
        this.$toast.error('Bạn chưa nhập cước đấu nối Megawan!')
        return
      }

      if (this.tocdo_megawan_info.CTK_MGWAN == null || this.tocdo_megawan_info.CTK_MGWAN == undefined) {
        this.$toast.error('Bạn chưa nhập cước thuê kênh Megawan!')
        return
      }

      if (this.tocdo_megawan_info.CTK_METRO == null || this.tocdo_megawan_info.CTK_METRO == undefined) {
        this.$toast.error('Bạn chưa nhập cước thuê kênh Metronet!')
        return
      }

      if (this.tocdo_megawan_info.CDN_TSL == null || this.tocdo_megawan_info.CDN_TSL == undefined) {
        this.$toast.error('Bạn chưa nhập cước đấu nối truyền số liệu!')
        return
      }

      if (this.tocdo_megawan_info.CTK_TSL == null || this.tocdo_megawan_info.CTK_TSL == undefined) {
        this.$toast.error('Bạn chưa nhập cước thuê kênh truyền số liệu!')
        return
      }
      var ds_tocdokenh = []
      var toddockenh = {}
      toddockenh.tocdo_id = this.tsbtnNhapMoi.Enabled ? this.tocdo_megawan_info.TOCDO_ID : 0
      //toddockenh.donvi = this.listbox.ds_dv_tinh.value;
      if (this.listbox.ds_dv_tinh.value == 1) toddockenh.donvi = 'Kb'
      else if (this.listbox.ds_dv_tinh.value == 2) toddockenh.donvi = 'Mb'
      else if (this.listbox.ds_dv_tinh.value == 3) toddockenh.donvi = 'Gb'
      else if (this.listbox.ds_dv_tinh.value == 5) toddockenh.donvi = 'STM1'
      else toddockenh.donvi = ''

      toddockenh.tocdo = this.tocdo_megawan_info.TOCDO
      toddockenh.ctk_mgwan = this.tocdo_megawan_info.CTK_MGWAN
      toddockenh.cdn_mgwan = this.tocdo_megawan_info.CDN_MGWAN
      toddockenh.ghichu = this.tocdo_megawan_info.GHICHU
      toddockenh.ctk_tsl = this.tocdo_megawan_info.CTK_TSL
      toddockenh.cdn_tsl = this.tocdo_megawan_info.CDN_TSL
      toddockenh.cdc_tsl = this.tocdo_megawan_info.CDC_TSL
      toddockenh.dtd_tsl = this.tocdo_megawan_info.DTD_TSL
      toddockenh.ctk_metro = this.tocdo_megawan_info.CTK_METRO
      toddockenh.tocdo_qd = this.tocdo_megawan_info.TOCDO_QD
      ds_tocdokenh.push(toddockenh)

      var data = {
        action: this.tsbtnNhapMoi.Enabled ? 2 : 1,
        ds_tocdokenh: JSON.stringify(ds_tocdokenh)
      }

      this.loading(true)
      await API.sp_tocdokenh_capnhat(this.axios, data).then((response) => {
        if (response.data.error_code === 'BSS-00000000') {
          this.SelectList()
          this.SetButton(0)
          this.$toast.success(response.data.message)
        } else this.$toast.error(response.data.message)
      })
    },

    tsbtnXoa_Click: async function () {
      this.$confirm('Bạn thật sự muốn xóa dữ liệu không ?', 'Thông báo', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không'
      }).then(async () => {
        var data = {
          tocdokenh_id: this.tocdo_megawan_info.TOCDO_ID
        }
        await API.sp_tocdokenh_xoa(this.axios, data).then((response) => {
          if (response.data.error_code === 'BSS-00000000') this.$toast.success(response.data.message)
          else this.$toast.error(response.data.message)
        })
        this.SelectList()
        this.SetButton(0)
      })
    }
  }
}
</script>
