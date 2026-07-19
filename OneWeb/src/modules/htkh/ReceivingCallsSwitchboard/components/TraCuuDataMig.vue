<template>
  <div class="row">
    <div class="col-sm-12 col-12">
      <div class="box-form">
        <div class="legend-title">
          <div class="pull-left">Thông tin tìm kiếm</div>
          <div class="pull-right"></div>
          <div class="clearfix"></div>
        </div>
        <div class="list-actions-top" style="position: unset !important">
          <ul class="list">
            <li>
              <a href="javascript: void(0)" @click="btnTimKiem_click">
                <span class="icon one-search"></span>Tìm kiếm
              </a>
            </li>
            <li>
              <a href="javascript: void(0)" @click="btnChapNhan_click">
                <span class="icon fa fa-check"></span>Chấp nhận
              </a>
            </li>
            <li>
              <a href="javascript: void(0)" @click="btnThemDK_click">
                <span class="-ap icon icon-plus"></span>Thêm điều kiện
              </a>
            </li>
          </ul>
        </div>
        <div class="table-content" :key="lookup_key">
          <KDataGrid
            ref="grid_LookUp"     
            v-bind:columns="grid_LookUp.config"
            v-bind:dataSource="grid_LookUp.DataSource"
            :visibleHeader="false"
            :enable-paging-server="false"
            :allowPaging="false"
            :showFilter="false"
            :showColumnSelectFlag="false"
            :showColumnCheckbox="false"
            :enabledSelectFirstRow="false"
            :selectionSettings="{ enableToggle: false }"
            panelDataHeight="230px"
            :commandColumn="{headerText: '', width: 15, cellCssClass: 'center', firstColumn: true, show: true}"
            :commands="grid_LookUp.commands"
            @commandClicked="gridCommand_LookUp"
          >
          </KDataGrid>
        </div>
      </div>
    </div>
    <div class="col-sm-12 col-12">
      <div class="box-form" :id="extend ? 'grid_TimKiemMoRong' : 'grid_TimKiemKhachHang'">
        <div class="legend-title">
          <div class="pull-left">Kết quả tìm kiếm</div>
          <div class="pull-right">{{ duration ? ('Đã tải xong (1): ' + duration + ' s') : ('Đã tải xong (0): 0') }}</div>
          <div class="clearfix"></div>
        </div>
        <div class="table-content">
          <DataGrid            
            ref="grid_DanhSach"
            v-bind:columns="grid_DanhSach.config"
            v-bind:dataSource="grid_DanhSach.DataSource"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            :showColumnCheckbox="false"
            :pageSize="20"
            @rowSelected="onSelectedRow"
            @rowDoubleClicked="onRecordDoubleClick"
          >
          </DataGrid>
        </div>
      </div>
    </div>
  </div>
</template>
<style>
.e-grid .e-movableheader {
  overflow-x: scroll !important;
}
</style>
<script>
import KDataGrid from '@/components/kylq_components/KDataGrid'
import api from "../ReceivingCallsSwitchboardApi.js"
export default {
  name: "TraCuuDataMig",
  components: { KDataGrid },
  props: ['dulieu', 'danh_muc', 'extend'],
  data: () => ({
    lookup_key: 1,
    duration: 0,
    DanhMuc: [],
    ds_dichvu: [],
    ds_loaihinh: [],
    grid_LookUp: {
      commands: [
        { name: 'delete', cssClass: 'pad3 mar5 lh14 icon -ap icon-delete btn-danger btn-plus', iconCss: 'fa fa-times', width: 100 },
      ],
      config: [
        // {
        //   fieldName: '',
        //   headerText: '',
        //   allowFiltering: true
        // },
        {
          fieldName: 'code',
          headerText: '',
          allowFiltering: false,
          template: () => {
            return {
              template: {
                template: `<div>
                    <select2 :z-index="2000" class="select2" v-model="data.code" :disabled="data.code == 'ma_tinh' ? true : false"
                      :options="data.dsThamChieu?.length > 0 ? data.dsThamChieu.map(e => ({id: e.code, text: e.name})) : []" @change="onChangeCode(data)">
                    </select2>
                  </div>`,
                data: function () {
                  return {
                    data: {},
                  };
                },
                computed: {
                  parent() {
                    return this.$parent.$parent.$parent;
                  },           
                },
                methods: {
                  onChangeCode: function (val) {                    
                    this.parent.onChangeCode(val)
                  },
                },
              },
            };
          },
        },
        {
          fieldName: 'filtered',
          headerText: '',
          allowFiltering: false,
          template: () => {
            return {
              template: {
                template: `<div>                  
                    <select2 :z-index="2000" class="select2" v-model="data.value"
                      :options="data.filtered" @change="onChange" v-if="data.att == 4">
                    </select2>
                    <input type="text" class="form-control text" :value="data.value" @change="onChangeText" v-else-if="data.att == 1" @keyup.enter="onKeyPress_Enter">
                  </div>`,
                data: function () {
                  return {
                    data: {},
                  };
                },
                computed: {
                  parent() {
                    return this.$parent.$parent.$parent;
                  },           
                },
                methods: {
                  onChange: function (val) {        
                    var index = this.parent.grid_LookUp.DataSource.findIndex(e => e.code == this.data.code)
                    this.parent.grid_LookUp.DataSource[index].value = Number(val) ? Number(val) : val                   
                  },
                  onChangeText: function (e) {
                    var index = this.parent.grid_LookUp.DataSource.findIndex(e => e.code == this.data.code)
                    this.parent.grid_LookUp.DataSource[index].filtered = e.target.value
                    this.parent.grid_LookUp.DataSource[index].value = e.target.value
                  },
                  onKeyPress_Enter: function () {
                    if (this.data.code == 'ma_tb') {
                      this.parent.btnTimKiem_click()
                    }
                  }
                },
              },
            };
          },
        }
      ],
      DataSource: []
    },
    grid_DanhSach: {
      config: [
        {
          fieldName: 'tentinh',
          headerText: 'Tỉnh',
          allowFiltering: true,
          width: 150,
          freeze:"left"
        },
        {
          fieldName: 'ma_tb',
          headerText: 'Mã thuê bao',
          allowFiltering: true,
          freeze:"left"
        },
        {
          fieldName: 'ma_kh',
          headerText: 'Mã khách hàng',
          allowFiltering: true
        },
        {
          fieldName: 'ten_tb',
          headerText: 'Tên thuê bao',
          allowFiltering: true
        },
        {
          fieldName: 'ten_dv',
          headerText: 'Dịch vụ',
          allowFiltering: true
        },
        {
          fieldName: 'ten_loaihinh',
          headerText: 'Loại hình',
          allowFiltering: true
        },
        {
          fieldName: 'diachi_ld',
          headerText: 'Địa chỉ lắp đặt',
          allowFiltering: true
        },     
        {
          fieldName: 'ma_lt',
          headerText: 'Số ảo',
          allowFiltering: true
        },   
        {
          fieldName: 'so_dt',
          headerText: 'Số điện thoại',
          allowFiltering: true
        }, 
        {
          fieldName: 'so_gt',
          headerText: 'Số giấy tờ',
          allowFiltering: true
        }, 
        {
          fieldName: 'mst',
          headerText: 'Mã số thuế',
          allowFiltering: true
        },         
        {
          fieldName: 'ma_plkh',
          headerText: 'Mã PLKH',
          allowFiltering: true
        }, 
        {
          fieldName: 'ten_trangthai',
          headerText: 'Trạng thái thuê bao',
          allowFiltering: true
        }, 
      ],
      DataSource: []
    },
    dsThamChieu: [],
    dsTimKiem: [],
    dtDanhSach: null
  }),
  mounted: async function () {
    // await this.frmTraCuuData_Mig_Load()    
  },
  watch: {
    'danh_muc'(val) {
      if (val) {
        this.DanhMuc = val
        this.frmTraCuuData_Mig_Load()
      }
    }
  },
  methods: {
    GetDataList: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return []
      } else {
        this.$root.$toast.error('Lỗi: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
        return []
      }
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return ''
      } else {
        this.$root.$toast.error('Lỗi: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
        return ''
      }
    },
    frmTraCuuData_Mig_Load: async function () {
      try {
        this.loading(true)
        if (this.danh_muc) {
          this.DanhMuc = this.danh_muc
        } else {
          this.DanhMuc = await this.LayDSThamSo()
        }
        this.ds_tinh = this.DanhMuc && this.DanhMuc['TINH'] && this.DanhMuc['TINH'].length > 0 ? this.DanhMuc['TINH'].map(e => ({...e, id: e.TENTAT, text: `[${e.TENTAT}] - ${e.TENTINH}`})) : []
        this.ds_loaihinh = this.DanhMuc && this.DanhMuc['LOAIHINH'] && this.DanhMuc['LOAIHINH'].length > 0 ? this.DanhMuc['LOAIHINH'].map(e => ({...e, id: e.LOAITB_ID, text: `[${e.TEN_DVVT}] - ${e.LOAITB_ID}.${e.LOAIHINH_TB}`})) : []
        
        var ds_dichvu = this.DanhMuc && this.DanhMuc['LOAIHINH'] && this.DanhMuc['LOAIHINH'].length > 0 ? this.DanhMuc['LOAIHINH'].map(e => ({DICHVUVT_ID: e.DICHVUVT_ID, id: e.DICHVUVT_ID, text: `[${e.DICHVUVT_ID}] - ${e.TEN_DVVT}`})) : []
        this.ds_dichvu = ds_dichvu.filter((obj, index) => {
          return index === ds_dichvu.findIndex(o => obj.DICHVUVT_ID === o.DICHVUVT_ID);
        })
        if (this.dulieu && this.dulieu.length > 0) {
          this.grid_LookUp.DataSource = this.dulieu
        } else {
          if (this.extend) {
            var ds_thamso = this.GetDataList(await api.sp_lay_ds_kieu_tk(this.axios, 8))       
            
            if (ds_thamso && ds_thamso.length > 0) {
              var keys = ['TINH']
              var dsTimKiem = this.XuLyForm_Mig(keys)
              ds_thamso = ds_thamso.map((e, index) => ({
                stt: Math.random().toString(36).slice(2, 7),
                att: 1,
                name: e.kieu_tk,
                code: e.dieukien,
                type: e.kieu_tc,
                table: e.bang_tc,
                list: null,
                filtered: null,
                value: '',
                dsThamChieu: this.LayDS()
              }))
              ds_thamso = ds_thamso.map(e => ({...e, dsThamChieu: ds_thamso}))
              dsTimKiem = dsTimKiem.concat(ds_thamso)
            
              if (dsTimKiem && dsTimKiem.length > 0) {
                this.dsThamChieu = ds_thamso
                this.grid_LookUp.DataSource = dsTimKiem
              }
            }
          } else {
            var keys = ['TINH','MA_TB','SO_DT','DIACHILD','SO_GT']
            var dsTimKiem = this.XuLyForm_Mig(keys)               
          
            if (dsTimKiem && dsTimKiem.length > 0) {
              this.grid_LookUp.DataSource = dsTimKiem
            }
          }     
        }
      } catch (e) {} finally { this.loading(false) }
    },
    LayDSThamSo: async function() {
      try {
        var input = {}
        var data = this.GetData(await api.sp_lay_ds_thamso(this.axios, input));
        var json_data = JSON.parse(data)
        if (json_data.ERROR_CODE == 1 || json_data.ERROR_CODE == '1') {
          return json_data.RESULT
        } else return {}           
      } catch (e) {
        if (e && e.data) {
          this.$toast.error('Lỗi sp_lay_ds_thamso ' + JSON.stringify(e.data.message_detail ? e.data.message_detail : e.data.message))
        } else {
          this.$toast.error('Lỗi sp_lay_ds_thamso ' + JSON.stringify(e))
        }
        return {}
      } finally {

      }
    },    
    LayDS: function () {
      // BaoHong = 1, KhieuNai = 2, DB_ThueBao = 10, DB_KhachHang = 11, DB_ThanhToan = 12, LienHe_TapTrung = 200, DB_CNTT = 201, SubData = 255,
      var ds = [{name: "Tỉnh", code: "ma_tinh", type: api.SearchType().ExactlyMatchUpperCase, att: 4, table: api.ResourceDataTable().SubData, list: this.ds_tinh, filtered: this.ds_tinh, value: ''}]
      if (this.extend) {
        ds.push({name: "Mã thanh toán", code: "ma_tt", type: api.SearchType().ExactlyMatchUpperCase, att: 1, table: api.ResourceDataTable().DB_ThanhToan, list: null, filtered: null, value: ''})
        ds.push({name: "SĐT LH thanh toán", code: "sdt", type: api.SearchType().ExactlyMatch, att: 1, table: api.ResourceDataTable().DB_ThanhToan, list: null, filtered: null, value: ''})
        ds.push({name: "Domain", code: "domain", type: api.SearchType().ExactlyMatch, att: 1, table: api.ResourceDataTable().DB_CNTT, list: null, filtered: null, value: ''})
        ds.push({name: "IP", code: "IP", type: api.SearchType().ExactlyMatch, att: 1, table: api.ResourceDataTable().DB_CNTT, list: null, filtered: null, value: ''})
      }
      else
      {
        ds.push({name: "Tên thuê bao", code: "tentb", type: api.SearchType().ContainLowerCase, att: 1, table: api.ResourceDataTable().DB_ThueBao, list: null, filtered: null, value: ''})
        ds.push({name: "Tên khách hàng", code: "tenkh", type: api.SearchType().ContainLowerCase, att: 1, table: api.ResourceDataTable().DB_KhachHang, list: null, filtered: null, value: ''})
        ds.push({name: "Liên hệ tập trung", code: "thongtin_lienhe", type: api.SearchType().ExactlyMatch, att: 1, table: api.ResourceDataTable().LienHe_TapTrung, list: null, filtered: null, value: ''})
        ds.push({name: "Mã số thuế", code: "mst", type: api.SearchType().ExactlyMatch, att: 1, table: api.ResourceDataTable().DB_KhachHang, list: null, filtered: null, value: ''})
        ds.push({name: "Số ảo", code: "ma_lt", type: api.SearchType().ExactlyMatch, att: 1, table: api.ResourceDataTable().DB_ThueBao, list: null, filtered: null, value: ''})
        ds.push({name: "Mã thuê bao", code: "ma_tb", type: api.SearchType().ExactlyMatch, att: 1, table: api.ResourceDataTable().DB_ThueBao, list: null, filtered: null, value: ''})
        ds.push({name: "Số liên hệ của KH", code: "so_dt", type: api.SearchType().ExactlyMatch, att: 1, table: api.ResourceDataTable().DB_KhachHang, list: null, filtered: null, value: ''})
        ds.push({name: "Mã khách hàng", code: "ma_kh", type: api.SearchType().ExactlyMatch, att: 1, table: api.ResourceDataTable().DB_KhachHang, list: null, filtered: null, value: ''})
        ds.push({name: "Mã thanh toán", code: "ma_tt", type: api.SearchType().ExactlyMatch, att: 1, table: api.ResourceDataTable().SubData, list: null, filtered: null, value: ''})
        ds.push({name: "Địa chỉ lắp đặt", code: "diachild", type: api.SearchType().ContainLowerCase, att: 1, table: api.ResourceDataTable().DB_ThueBao, list: null, filtered: null, value: ''})
        ds.push({name: "Số giấy tờ", code: "so_gt", type: api.SearchType().ExactlyMatch, att: 1, table: api.ResourceDataTable().DB_KhachHang, list: null, filtered: null, value: ''})
        ds.push({name: "Mã VNPT", code: "ma_vnpt", type: api.SearchType().ExactlyMatch, att: 1, table: api.ResourceDataTable().DB_ThueBao, list: null, filtered: null, value: ''})
        ds.push({name: "Địa chỉ khách hàng", code: "diachikh", type: api.SearchType().ContainLowerCase, att: 1, table: api.ResourceDataTable().DB_KhachHang, list: null, filtered: null, value: ''})
        ds.push({name: "Dịch vụ", code: "dichvuvt_id", type: api.SearchType().ExactlyMatch, att: 4, table: api.ResourceDataTable().DB_ThueBao, list: this.ds_dichvu, filtered: this.ds_dichvu, value: ''})
        ds.push({name: "Loại hình thuê bao", code: "loaitb_id", type: api.SearchType().ExactlyMatch, att: 4, table: api.ResourceDataTable().DB_ThueBao, list: this.ds_loaihinh, filtered: this.ds_loaihinh, value: ''})
      }
      return ds
    },
    XuLyForm_Mig: function (keys) {
      try {
        var ds = []
        if (keys && keys.length > 0) {
          for (let key of keys) {
            if (key == 'TINH') {
              var row = {}
              row.stt = Math.random().toString(36).slice(2, 7),
              row.att = 4
              row.name = 'Tỉnh'
              row.code = 'ma_tinh'
              row.type = api.SearchType().ExactlyMatchUpperCase
              row.table = api.ResourceDataTable().SubData
              row.list = this.ds_tinh
              row.filtered = this.ds_tinh
              row.value = ''
              row.dsThamChieu = this.LayDS()

              ds.push(row)
            } else if (key == 'MA_TB') {
              var row = {}
              row.stt = Math.random().toString(36).slice(2, 7),
              row.att = 1
              row.name = 'Mã thuê bao'
              row.code = 'ma_tb'
              row.type = api.SearchType().ExactlyMatch
              row.table = api.ResourceDataTable().DB_ThueBao
              row.list = null
              row.filtered = null
              row.value = ''
              row.dsThamChieu = this.LayDS()

              ds.push(row)
            } else if (key == 'SO_DT') {
              var row = {}
              row.stt = Math.random().toString(36).slice(2, 7),
              row.att = 1
              row.name = 'Số liên hệ của KH'
              row.code = 'so_dt'
              row.type = api.SearchType().ExactlyMatch
              row.table = api.ResourceDataTable().DB_KhachHang
              row.list = null
              row.filtered = null
              row.value = ''
              row.dsThamChieu = this.LayDS()

              ds.push(row)
            } else if (key == 'SO_GT') {
              var row = {}
              row.stt = Math.random().toString(36).slice(2, 7),
              row.att = 1
              row.name = 'Số giấy tờ'
              row.code = 'so_gt'              
              row.type = api.SearchType().ExactlyMatch
              row.table = api.ResourceDataTable().DB_KhachHang
              row.list = null
              row.filtered = null
              row.value = ''
              row.dsThamChieu = this.LayDS()

              ds.push(row)
            } else if (key == 'DIACHILD') {
              var row = {}
              row.stt = Math.random().toString(36).slice(2, 7),
              row.att = 1
              row.name = 'Địa chỉ lắp đặt'
              row.code = 'diachild'              
              row.type = api.SearchType().ContainLowerCase
              row.table = api.ResourceDataTable().DB_ThueBao
              row.list = null
              row.filtered = null
              row.value = ''
              row.dsThamChieu = this.LayDS()

              ds.push(row)
            }
          }
        }
        return ds
      } catch (e) {
        console.log(e)
      }
    },
    onChangeCode: function (val) {      
      if (val.code == 'ma_tinh') return
      var ds_thamchieu = this.extend ? this.dsThamChieu : this.LayDS()
      var index =  ds_thamchieu.findIndex(e => e.code == val.code)
      var new_row = ds_thamchieu[index]
      for (let row of this.grid_LookUp.DataSource) {
        if (row.stt == val.stt) {
          row.att = new_row['att']
          row.name = new_row['name']
          row.code = new_row['code']  
          row.type = new_row['type']
          row.table = new_row['table']
          row.list = new_row['list']
          row.filtered = new_row['filtered']
          row.value = new_row['value']
        }
      }
      this.lookup_key += 1
    },
    onSelectedRow: function (args) {
      try {
        this.loading(true)
        if (args && args.data) {
          this.dtDanhSach = args.data;
          var check_matb = this.grid_LookUp.DataSource.find(e => e.code == 'ma_tb' && e.value && e.value.length > 0)
          if (check_matb && this.grid_DanhSach.DataSource && this.grid_DanhSach.DataSource.length == 1) 
            this.$emit("xacnhan", {thongtin_TimKiem: this.grid_LookUp.DataSource, dtDanhSach: this.dtDanhSach, isConfirm: true });
        } else {
          this.dtDanhSach = {}
        }        
      } catch (e) {
        console.log(e)
      } finally { 
        this.loading(false)         
        if (this.extend) {
          document.getElementById('grid_TimKiemMoRong').scrollIntoView(true);
        } else {
          document.getElementById('grid_TimKiemKhachHang').scrollIntoView(true);
        }
      }
    },
    onRecordDoubleClick: function (rowIndex, rowData) {
      console.log('onRecordDoubleClick')
      this.$emit("xacnhan", {thongtin_TimKiem: this.grid_LookUp.DataSource, dtDanhSach: rowData, isConfirm: true});
    },
    btnTimKiem_click: async function () {
      try {
        this.loading(true)                        
        if (this.extend) {
          await this.TraCuu_Tinh();
          return
        } else {
          await this.TraCuu_TapTrung();
          return
        }          
      } catch (ex) {
        console.log(ex)
      } finally {
        this.loading(false)
      }
    },
    Tao_Ds_DieuKien_TimKiem(dsTimKiem) {
      var thongtin_TimKiem = []
      if (dsTimKiem && dsTimKiem.length > 0) {
        for (let item of dsTimKiem) {
          var row = {}
          row.name = item.name
          row.code = item.code
          row.type = item.type
          row.table = item.table
          row.value = item.value

          thongtin_TimKiem.push(row)
        }
      }
      return thongtin_TimKiem
    },
    TraCuu_Tinh: async function () {
      try {
        var thongtin_TimKiem =  this.Tao_Ds_DieuKien_TimKiem(this.grid_LookUp.DataSource)
        
        if (!thongtin_TimKiem || thongtin_TimKiem.length == 0) return;
        var check_matinh = thongtin_TimKiem.findIndex(e => e.code == "ma_tinh")
        if (check_matinh == -1 || !thongtin_TimKiem[check_matinh].value) {
          this.$toast.error("Bạn bắt buộc phải chọn tỉnh");
          return;
        }
        this.duration = 0
        let before = Date.now();
        var ds = this.GetDataList(await api.sp_CongViec_CSKH_TraCuu(this.axios, thongtin_TimKiem))
        let after = Date.now();
        this.duration = Math.round(((after-before)/1000 + Number.EPSILON) * 1000) / 1000;
        if (ds && ds.length > 0) {
          for (let item of ds) {
            item.tinh_id = this.DanhMuc['TINH'].filter(e => e.TENTAT == thongtin_TimKiem[check_matinh].value)[0]['TINH_ID']
            item.tentinh = this.DanhMuc['TINH'].filter(e => e.TENTAT == thongtin_TimKiem[check_matinh].value)[0]['TENTINH']
            if (item['dichvuvt_id']) item.ten_dv = this.DanhMuc['LOAIHINH'].filter(e => e.DICHVUVT_ID == item['dichvuvt_id'])[0]['TEN_DVVT']
            if (item['loaitb_id']) item.ten_loaihinh = this.DanhMuc['LOAIHINH'].filter(e => e.LOAITB_ID == item['loaitb_id'])[0]['LOAIHINH_TB']
            if (item['trangthaitb_id']) item.ten_trangthai = this.DanhMuc['TRANGTHAITB'].filter(e => e.TRANGTHAITB_ID == item['trangthaitb_id'])[0]['TRANGTHAI_TB']
          }
        }
        this.grid_DanhSach.DataSource = ds
      } catch (e) {
        this.$toast.error('Lỗi tìm kiếm mở rộng ' + JSON.stringify(e))
        console.log('TraCuu_Tinh', e)
      }
    },
    TraCuu_TapTrung: async function () {
      try {
        if (this.grid_LookUp.DataSource && this.grid_LookUp.DataSource.length > 0) {
          var check_value = 0
          for (let item of this.grid_LookUp.DataSource) {
            if (item.value) check_value++            
          }
          if (check_value == 0) {
            this.$toast.error('Hãy nhập thông tin tìm kiếm!')
            return
          }
        }
        var thongtin_TimKiem =  this.Tao_Ds_DieuKien_TimKiem(this.grid_LookUp.DataSource)
        if (!thongtin_TimKiem) return;
        this.duration = 0
        let before = Date.now();
        var ds = this.GetDataList(await api.sp_CSKH_18001166_TraCuu(this.axios, thongtin_TimKiem));
        let after = Date.now();
        this.duration = Math.round(((after-before)/1000 + Number.EPSILON) * 1000) / 1000;
        
        if (ds && ds.length > 0) {
          for (let item of ds) {
            if (item['tinh_id']) item.tentinh = this.DanhMuc['TINH'].filter(e => e.TINH_ID == item['tinh_id'])[0]['TENTINH']
            if (item['dichvuvt_id']) item.ten_dv = this.DanhMuc['LOAIHINH'].filter(e => e.DICHVUVT_ID == item['dichvuvt_id'])[0]['TEN_DVVT']
            if (item['loaitb_id']) item.ten_loaihinh = this.DanhMuc['LOAIHINH'].filter(e => e.LOAITB_ID == item['loaitb_id'])[0]['LOAIHINH_TB']
            if (item['trangthaitb_id']) item.ten_trangthai = this.DanhMuc['TRANGTHAITB'].filter(e => e.TRANGTHAITB_ID == item['trangthaitb_id'])[0]['TRANGTHAI_TB']
          }
        }
        this.grid_DanhSach.DataSource = ds
      } catch (e) {
        this.$toast.error('Lỗi tìm kiếm tập trung ' + JSON.stringify(e))
        console.log('TraCuu_TapTrung', e)
      }
    },
    btnChapNhan_click: function () {
      this.$emit("xacnhan", {thongtin_TimKiem: this.grid_LookUp.DataSource, dtDanhSach: this.dtDanhSach, isConfirm: true});
    },
    btnThemDK_click: function() {
      var row = {}
      row.stt = Math.random().toString(36).slice(2, 7),
      row.att = 1
      row.name = ''
      row.code = ''
      row.type = api.ResourceDataTable().ContainLowerCase
      row.table = 0
      row.list = null
      row.filtered = null
      row.value = ''
      row.dsThamChieu = this.extend ? this.dsThamChieu : this.LayDS()
      this.grid_LookUp.DataSource.push(row)
      this.lookup_key += 1
    },
    gridCommand_LookUp: function (type, dataItem) {
      if (type == 'delete') {
        if (dataItem.code == 'ma_tinh') {
          for (var item of this.grid_LookUp.DataSource) {
            if (item.stt == dataItem.stt) {
              item.value = ''
              this.lookup_key += 1
              return
            }            
          }          
        }
        this.grid_LookUp.DataSource = this.grid_LookUp.DataSource.filter(e => e.stt != dataItem.stt)
      }
    }
  }
}
</script>
