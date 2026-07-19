<template>
  <div class="modal-content popup-box">
    <div class="popup-body">
      <div class="box-form">
        <div class="legend-title">Thông tin</div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w90">Mã khách hàng</div>
              <div class="value">
                <input type="text" v-model="txtMaKH" readonly class="form-control " />
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">Số máy/Acc</div>
              <div class="value">
                <input type="text" v-model="txtMaTB" readonly class="form-control " />
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">Tên thuê bao</div>
              <div class="value">
                <input type="text" v-model="txtTenTB" readonly class="form-control " />
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w90">Trang bị</div>
              <div class="value">
                <div class="select-custom">
                  <!-- <Select2 ref="trangbiId" :disabled='false' v-model="listbox.list_quan.value"
                            :options="listbox.list_quan.list" :settings="settingSelect2"/> -->
                  <select2 ref="trangbiId" :options="listbox.list_quan.list" v-model="listbox.list_quan.value" :disabled="true">
                    <option value="0">Select one</option>
                  </select2>
                </div>
              </div>
            </div>

            <div class="info-row">
              <div class="key w90">Phường/Xã</div>
              <div class="value">
                <select2 disabled :options="listbox.list_phuong.list" v-model="listbox.list_phuong.value">
                  <option value="0">Select one</option>
                </select2>
              </div>
            </div>

            <div class="info-row">
              <div class="key w90">Tòa nhà</div>
              <div class="value">
                <select2 disabled :options="listbox.list_toanha.list" v-model="listbox.list_toanha.value">
                  <option value="0">Select one</option>
                </select2>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="box-form">
        <div class="legend-title">Danh sách Thuê bao</div>
        <DataGrid v-bind:columns="grvheaders" v-bind:dataSource="grvdatalist" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :enabled-select-first-row="true"></DataGrid>
      </div>
    </div>
  </div>
</template>
<script>
import API from '../API'
export default {
  name: 'Thuebao_ToaNha',
  props: ['ma_gd', 'hdtb_id'],
  components: {
    API
  },
  data() {
    return {
      settingSelect2: 'vi',
      txtMaKH: '',
      txtMaTB: '',
      txtTenTB: '',
      grvdatalist: [],
      grvheaders: [
        { fieldName: 'ma_kh', headerText: 'Mã khách hàng' },
        { fieldName: 'ma_tb', headerText: 'Số máy/Acc' },
        { fieldName: 'ten_tb', headerText: 'Tên thuê bao' },
        { fieldName: 'ten_dvvt', headerText: 'Dịch vụ VT' },
        { fieldName: 'loaihinh_tb', headerText: 'Loại TB' },
        { fieldName: 'ma_toanha', headerText: 'Mã tòa nhà' },
        { fieldName: 'ten_toanha', headerText: 'Tên tòa nhà' }
      ],

      listbox: {
        list_quan: {
          list: [],
          value: ''
        },
        list_phuong: {
          list: [],
          value: ''
        },
        list_toanha: {
          list: [],
          value: ''
        }
      }
    }
  },
  methods: {
    async loadData() {
      if (this.ma_gd) {
        var data = {
          p_ma_gd: this.ma_gd,
          p_ma_kh: null,
          p_ma_tb: null,
          p_tag: 'tracuu_hopdong'
        }
        var toanha_id = ''
        await API.sp_lay_ds_thuebao_toanha_by_tag(this.axios, data).then((response) => {
          if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.grvdatalist = response.data.data
            this.txtMaKH = response.data.data[0].ma_kh
            this.txtMaTB = response.data.data[0].ma_tb
            this.txtTenTB = response.data.data[0].ten_tb
            toanha_id = response.data.data[0].toanha_id
          } else {
            this.grvdatalist = []
            this.txtMaKH = ''
            this.txtMaTB = ''
            this.txtTenTB = ''
          }
        })
        console.log(`hdtb_id ${this.hdtb_id}`)
        data = {
          hdtb_id: this.hdtb_id,
          toanha_id: null,
          tag: 'capnhat_hopdong'
        }
        var out_phuong = ''
        //load quan
        await API.sp_lay_tt_quan_phuong_by_id(this.axios, data).then((response) => {
          if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.listbox.list_quan.value = response.data.data.out_quan_id
            out_phuong = response.data.data.out_phuong_id
          } else {
            this.listbox.list_quan.value = ''
          }
        })

        //load phuong
        var data_phuong = {
          ID: this.listbox.list_quan.value
        }
        await API.css_phuong(this.axios, data_phuong).then((response) => {
          if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.listbox.list_phuong.list = response.data.data.map((item) => {
              return {
                id: item.PHUONG_ID,
                text: item.TEN_PHUONG
              }
            })
            this.listbox.list_phuong.value = out_phuong
          } else {
            this.listbox.list_phuong.list = []
            this.listbox.list_phuong.value = ''
          }
        })

        //load ds toa nha
        var data_toanha = {
          quan_id: this.listbox.list_quan.value,
          phuong_id: this.listbox.list_phuong.value
        }

        API.sp_lay_ds_toanha_by_quanphuong(this.axios, data_toanha).then((response) => {
          if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.listbox.list_toanha.list = response.data.data.map((item) => {
              return {
                id: item.toanha_id,
                text: item.ten_toanha
              }
            })
            this.listbox.list_toanha.value = toanha_id
          } else this.listbox.list_toanha.list = []
        })
      }
    }
  },
  created() {
    console.log('ThueBao created')
  },
  mounted() {
    console.log('ThueBao mounted')
    API.css_quan(this.axios, null).then((response) => {
      console.log(' ThueBao response-120')
      console.log(response)
      if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
        console.log('Co du lieu - 120')
        this.listbox.list_quan.list = response.data.data.map((item) => {
          return {
            id: item.QUAN_ID,
            text: item.TEN_QUAN
          }
        })
        console.log(this.listbox.list_quan.list)
      } else this.listbox.list_quan.list = []
    })
    this.loadData()
  }
  // watch: {
  //   ma_gd: async function() {
  //     console.log('watch magd watch magd watch magd watch magd')

  //   }
  // }
}
</script>