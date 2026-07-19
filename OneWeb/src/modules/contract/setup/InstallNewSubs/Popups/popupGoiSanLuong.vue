<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
      <div class="title"><span class="icon one-notepad"></span>Gói sản lượng báo hiệu quốc tế</div>
      <div class="close -ap icon-close hidden" data-dismiss="modal" @click.prevent="closeForm"></div>
    </div>

    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="btnGhiLai_Click"> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Ghi lại </a>
        </li>

        <li>
          <a href="#" @click.prevent="btnXoa_Click"> <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Xoá </a>
        </li>
      </ul>
    </div>

    <div class="popup-body">
      <div class="box-form">
          <div class="legend-title mart20">Gói sản lượng</div>
          <div class="row">
            <div class="col-sm-6 col-12">
                <div class="info-row">
                    <div class="key">Sản lượng</div>
                </div>
            </div>
            <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key">Giá cước</div>
                </div>
            </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
              <div class="info-row">
                  <div class="value">
                      <div class="row">
                          <div class="col-12">
                              <input type="text" placeholder="Nhập giá trị gói" title="Nhập giá trị gói" class="form-control" v-model="grcGoiSanLuong.list[0].sanluong" :blur="CheckSanLuong">
                          </div>
                      </div>
                  </div>
              </div>
          </div>
          <div class="col-sm-6 col-12">
              <div class="info-row">
                  <div class="value">
                      <div class="row">
                          <div class="col-12">
                              <input type="text" placeholder="Nhập số tiền" title="Nhập số tiền" class="form-control" v-model="grcGoiSanLuong.list[0].giacuoc">
                          </div>
                      </div>
                  </div>
              </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
              <div class="info-row">
                  <div class="value">
                      <div class="row">
                          <div class="col-12">
                              <input type="text" placeholder="Vượt gói" title="Vượt gói" class="form-control" v-model="grcGoiSanLuong.list[1].sanluong" :blur="CheckSanLuong">
                          </div>
                      </div>
                  </div>
              </div>
          </div>
          <div class="col-sm-6 col-12">
              <div class="info-row">
                  <div class="value">
                      <div class="row">
                          <div class="col-12">
                              <input type="text" placeholder="Đơn giá/bản tin" title="Đơn giá/bản tin" class="form-control" v-model="grcGoiSanLuong.list[1].giacuoc">
                          </div>
                      </div>
                  </div>
              </div>
          </div>
        </div>
          <!-- <ejs-grid
            ref="grid"
            :load="load"
            :dataSource="ds_thietbimua.list"
            locale="vi-VN"
            :allowResizing="true"
            :allowSorting="true"
            :allowFiltering="true"
            :allowPaging="false"
            :pageSettings="{ pageSize: 10, pageSizes: [10, 50, 100, 200] }"
            :gridLines="1"
            :rowSelected="rowselect"
            :rowDeselected="rowdeselect"
            height="400"
            :editSettings="{ allowEditing: true, mode: 'Normal', allowAdding: true, allowDeleting: true }"
            :actionComplete="actionComplete"
            :toolbar="['Add', 'Edit', 'Delete', 'Update', 'Cancel']"
          >
            <e-columns>
              <e-column field="sanluong" headerText="Sản lượng" headerTextAlign="center" textAlign="Left" width="150" minWidth="100" maxWidth="500" isPrimaryKey="true"></e-column>
              <e-column field="giacuoc" :customAttributes="{ class: 'tien' }" format="N" headerText="Giá cước" headerTextAlign="center" textAlign="Right" width="150" :allowEditing="tiennotreadonly"></e-column>
            </e-columns>
          </ejs-grid> -->
      </div>
    </div>
  </div>
</template>
<style>
input.disabled,
select.disabled,
.disabled input,
.disabled select,
.disabled /deep/ .select2-selection {
  background-color: #eee;
  color: lightgray;
}
/* 
.e-grid .e-gridheader .e-sortfilter .e-headercelldiv,
.e-grid .e-gridheader .e-stackedheadercelldiv,
.e-grid .e-gridheader .e-headercell .e-headercelldiv.e-headerchkcelldiv {
  padding: 0 5px 0 5px !important;
}

.e-grid .e-rowcell.vat,
.e-grid .e-summarycell.vat,
.vat {
  font-weight: bold;
  color: darkred;
}

.vat .e-headercelldiv {
  margin-top: 10px;
}

.e-grid .e-rowcell.tien,
.e-grid .e-summarycell.tien,
.tien {
  font-weight: bold;
  color: red;
}

.e-input-group-icon {
  display: none !important;
} */
</style>
<script>
import Vue from 'vue'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import { L10n, setCulture } from '@syncfusion/ej2-base'
import { GridPlugin, Page, Filter, GridComponent, Sort, Grid, Aggregate, Edit, Resize, Group, CommandColumn, ExcelExport, Freeze, Toolbar } from '@syncfusion/ej2-vue-grids'
import { required, minLength, integer, maxValue } from 'vuelidate/lib/validators'

L10n.load({
  'vi-VN': {
    grid: {
      EmptyRecord: 'Không có bản ghi nào để hiển thị ',
      Item: 'Bản ghi',
      Items: 'Bản ghi',
      FilterbarTitle: ''
    }
  }
})

export default {
  name: 'popupGoiSanLuong',
  provide: {
    grid: [Sort, Filter, Resize, Group, Freeze, CommandColumn, ExcelExport, Edit, Aggregate, Toolbar]
  },
  components: { required, minLength, integer, maxValue, DatePicker, L10n, setCulture, GridPlugin, Page, Sort, Filter, GridComponent, Grid, Aggregate, Edit },
  props: ['dulieu'],
  // watch: {
  //     'ds_thietbimua.list': {
  //         handler: function() { console.log(this.$refs.grid.ej2Instances); },
  //         deep:true,
  //     },
  // },
  data() {
    return {
      grcGoiSanLuong: {
        list:  [{giacuoc:null, sanluong:null},{giacuoc:null, sanluong:null}],
        header: [],
        value: {},
        isEnabled: true,
        checked: []
      },
      is_ghilai: false,
    }
  },
  mounted() {
    this.LoadDSGoiSanLuong();
  },
  computed: {
    // is_nv_vnpti() {
    //     return this.$root.token.getPhanVungID()==98?1:0
    // },
  },
  methods: {
    async LoadDSGoiSanLuong()
    {
      var rs = await this.$root.context.post('/web-hopdong/lapdatmoi/sp_ds_hdtb_cntt_sub_baohieuquocte', {
          hdtb_id: this.dulieu.hdtb_id, loaicuoc: this.dulieu.loaicuoc
      });
      if(rs && rs.data && rs.data.length>0) {
          this.grcGoiSanLuong.list = rs.data;
          if(!this.grcGoiSanLuong.list || this.grcGoiSanLuong.list.length==0) {
            this.grcGoiSanLuong.list = [{giacuoc:null, sanluong:null},{giacuoc:null, sanluong:null}]
          }
          else if(this.grcGoiSanLuong.list.length==1) {
            this.grcGoiSanLuong.list.push({giacuoc:null, sanluong:null});
          }
      }
      else
      {
        // NOTE: cho nay khong can khoi tao, form nay se co nut add giong lap moi
        this.grcGoiSanLuong.list = [{giacuoc:null, sanluong:null},{giacuoc:null, sanluong:null}];//[];//[{giacuoc:null, sanluong:null},{giacuoc:null, sanluong:null}];
      }
    },
    async btnGhiLai_Click() {
      try
      {
        var rs = await this.$root.context.post('/web-hopdong/lapdatmoi/quyen_xoa_loaicuoc_baohieuquocte',{ hdtb_id: this.dulieu.hdtb_id, luong_id:this.dulieu.luong_id });// tdan.GET_VALUE_FUNC("{?DB2}.QUYEN_XOA_LOAICUOC_BAOHIEUQUOCTE", "vhdtb_id", hdtb_id, "vluong_id", luong_id, "vuser", tt_nd.ma_nd).ToString();
        if (rs && rs.data == "OK")
        {
              for (var row of this.grcGoiSanLuong.list)
              {
                  if (!row['sanluong'] || !row['giacuoc'])
                  {
                      this.$toast.error("Hãy nhập đủ thông tin giá cước và sản lượng");
                      return;
                  }
              }

              var sanluong = this.grcGoiSanLuong.list[0]["sanluong"];
              var sanluong_vuot = this.grcGoiSanLuong.list[1]["sanluong"];
              if (sanluong_vuot <= sanluong || sanluong_vuot != sanluong*1 + 1)
              {
                  this.$toast.error("Sản lương vượt gói phải lơn hơn sản lượng và sản lượng vượt gói phải bằng sản lượng + 1");
                  return;
              }

              var kq = await this.$root.context.post('/web-hopdong/lapdatmoi/fn_capnhat_htdb_cntt_sub_baohieuquocte', { hdtb_id: this.dulieu.hdtb_id, jsondata: JSON.stringify(this.grcGoiSanLuong.list), ma_tb: this.dulieu.ma_tb, luong_id: this.dulieu.luong_id, loaicuoc: this.dulieu.loaicuoc })//tdan.GET_VALUE_FUNC("{?DB2}.FN_CAPNHAT_HTDB_CNTT_SUB_BAOHIEUQUOCTE", "vhdtb_id", hdtb_id, "vjson_data", '[' + txttrongoi.EditValue.ToString() + ']',"vma_tb", ma_tb, "vluong_id", luong_id, "vma_nd", tt_nd.ma_nd, "vloaicuoc", loaicuoc);
              if (kq && kq.data != "OK")
              {
                  this.$toast.error("Lỗi ghi lại "+kq.data);
                  return;
              }
              else
              {
                  this.$toast.success("Cập nhật thành công");
                  this.is_ghilai = true;
                  this.closeForm();
              }
          }
          else
          {
              this.$toast.error(rs.data);
              return;
          }
      }
      catch (ex)
      {
        console.error(ex);
          this.$toast.error("Lỗi ghi lại "+ex);
          return;
      }
    },
    async btnXoa_Click() {
      try
      {
          
          var rs = await this.$root.context.post('/web-hopdong/lapdatmoi/quyen_xoa_loaicuoc_baohieuquocte',{ hdtb_id: this.dulieu.hdtb_id, luong_id:this.dulieu.luong_id });// tdan.GET_VALUE_FUNC("{?DB2}.QUYEN_XOA_LOAICUOC_BAOHIEUQUOCTE", "vhdtb_id", hdtb_id, "vluong_id", luong_id, "vuser", tt_nd.ma_nd).ToString();
          if (rs && rs.data == "OK")
          {
              // int kq = tdan.EXECUTE_SQL("delete from " + DatabaseConstants.DB2 + ".hdtb_cntt_sub where hdtb_id = " + hdtb_id + " and loaihinh_tb = " + 2114.ToString() + " and pin = 'TRONGOI'");
              var kq = await this.$root.context.get('/web-hopdong/lapdatmoi/sp_del_hdtb_cntt_sub_by_pin', {params:{ hdtb_id: this.dulieu.hdtb_id, pin: 'GOISANLUONG' }});
              if (kq && kq.data && kq.data > 0)
              {
                  this.$toast.success("Xóa thành công");
                  this.LoadDSGoiSanLuong();
                  this.is_ghilai = false;
              }
              else
              {
                  this.$toast.error("Xóa không thành công");
              }
          }
          else
          {
              this.$toast.error(rs.data);
              return;
          }
      }
      catch (ex)
      {
          this.$toast.error(ex);
      }
    },
    CheckSanLuong(e) {
      if(!e.target.value || e.target.value<0) {
        this.$toast.error("Chưa nhập giá trị sản lượng. Hãy nhập giá trị sản lượng.");
        e.target.focus();
      }
    },
    closeForm() {
      this.$emit('form-close', this.is_ghilai)
    },
    // actionComplete(args) {
    //   if (args.requestType === 'save') {
    //     for (var item of this.ds_thietbimua.list)
    //       if (item.loaitbi_id == args.data.loaitbi_id) {
    //         item.dangky = args.data.dangky ? 1 : 0
    //         break
    //       }
    //     var val = args.data
    //     var oldval = args.rowData
    //     if (val.tien_thue != oldval.tien_thue) {
    //       val.tien = this.round(val.tien_thue / (100 + val.tyle_vat) * 100)
    //       val.vat = val.tien_thue-val.tien //this.round(val.tien_thue / (100 + val.tyle_vat) * val.tyle_vat)
    //     }
    //     else if (val.tien != oldval.tien) val.vat = val.tien ? this.round(val.tien * val.tyle_vat / 100) : 0
    //     if (val.tien_tratruoc != oldval.tien_tratruoc) val.vat_tratruoc = val.tien_tratruoc ? this.round(val.tien_tratruoc * val.tyle_vat / 100) : 0
        
    //     if (val.soluong == 1) val.serialreadonly = false
    //     else val.serialreadonly = true
    //     var item = this.ds_thietbimua.list.find((x) => x.loaitbi_id == val.loaitbi_id)
    //     if (item) {
    //       item.tien = val.tien
    //       item.vat = val.vat
    //       item.tien_tratruoc = val.tien_tratruoc
    //       item.vat_tratruoc = val.vat_tratruoc
    //       item.serialreadonly = val.serialreadonly
    //     }
    //     this.$forceUpdate()
    //   }
    //   if (args.requestType === 'refresh') {
    //   }
    //   if (args.requestType === 'beginEdit') {
    //     if (args.rowData['serialreadonly']) args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'serial'].readOnly = true
    //     else args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'serial'].readOnly = false
    //     if (args.rowData['tratruocreadonly']) {
    //       args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'tien_tratruoc'].readOnly = true
    //       args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'vat_tratruoc'].readOnly = true
    //     } else {
    //       args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'tien_tratruoc'].readOnly = false
    //       args.form.elements[this.$refs.grid.ej2Instances.element.getAttribute('id') + 'vat_tratruoc'].readOnly = false
    //     }
    //   }
    // },
    // load: function() {
    //   this.$refs.grid.ej2Instances.element.addEventListener('mouseup', function(e) {
    //     var instance = this.ej2_instances[0]
    //     if (e.target.classList.contains('e-rowcell')) {
    //       if (instance.isEdit) instance.endEdit()
    //       let index = parseInt(e.target.getAttribute('Index'))
    //       instance.selectRow(index)
    //       instance.startEdit()
    //     }
    //   })
    //   this.$refs.grid.ej2Instances.element.addEventListener('change', function(e) {
    //     if (e.target.classList.contains('e-checkbox')) {
    //       var instance = this.ej2_instances[0]
    //       if (instance.isEdit) {
    //         instance.editModule.batchSave()
    //         instance.endEdit()
    //       }
    //     }
    //   })
    // },
    // tongtienAggregateFn: function(d) {
    //   return 'Tổng: ' + (this.TinhTongAggr(d, 'tien') * 1 + this.TinhTongAggr(d, 'vat') * 1).toLocaleString('en-US')
    // },
    // tienAggregateFn: function(d) {
    //   return this.TinhTongAggr(d, 'tien')
    // },
    // vatAggregateFn: function(d) {
    //   return this.TinhTongAggr(d, 'vat')
    // },
    // giablkAggregateFn: function(d) {
    //   return this.TinhTongAggr(d, 'gia_block_tiep')
    // },
    // vatblkAggregateFn: function(d) {
    //   return this.TinhTongAggr(d, 'vat_block_tiep')
    // },
    // TinhTongAggr: function(d, e) {
    //   var fdata = d.result.filter((item) => item.dangky == 1)
    //   return fdata && fdata.length > 0 ? fdata.map((x) => x[e] * x['soluong']).reduce((a, b) => a + b) : 0
    // },
    // btnCanBangThue_Click() {
    //   this.$refs.grid.ej2Instances.endEdit()
    //   if (this.formversion == 2) this.CanBangThue()
    // },
    // terminateForm() {
    //   this.$emit('form-close', null)
    // },
    // formatPrice(value) {
    //   let val = (value / 1).toFixed(0).replace('.', ',')
    //   return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.')
    // },
    // ShowAlert(mesage, val) {
    //   if (val == 0) {
    //     this.$toast.success(mesage)
    //   } else if (val == 1) {
    //     this.$toast.warning(mesage)
    //   } else if (val == 2) {
    //     this.$toast.error(mesage)
    //   }
    // },
    // rowselect() {
    //   // var grid= this.$refs.grid;
    //   // this.rowchon0 = this.rowchon;
    //   // this.rowchon = grid.getSelectedRecords();
    //   // for (var item of this.rowchon)
    //   // {
    //   //     this.ds_thietbimua.list.filter(x=>x.loaitbi_id == item.loaitbi_id)[0].dangky=1;
    //   // }
    // },

    // rowdeselect() {
    //   // var grid= this.$refs.grid;
    //   // this.rowchon0 = this.rowchon;
    //   // this.rowchon = grid.getSelectedRecords();
    //   // for (var item of this.rowchon0)
    //   // {
    //   //     if (this.rowchon.filter(x => x.loaitbi_id == item.loaitbi_id).length<=0)
    //   //     {
    //   //         this.ds_thietbimua.list.filter(x=>x.loaitbi_id == item.loaitbi_id)[0].dangky=0;
    //   //     }
    //   // }
    // },
    // CanBangThue() {
    //   //HT_TongTien();
    //   var lst_thietbi = []
    //   var tong_tien_tb = 0
    //   var tong_vat = 0
    //   var tong_chua_thue = 0
    //   var tong_tien_tb_khongvat = 0
    //   for (var i = 0; i < this.ds_thietbimua.list.length; i++) {
    //     var item = this.ds_thietbimua.list[i]
    //     if (item.dangky == 1) {
    //       var dTongtien = item.tong_tien ? item.tong_tien : 0
    //       var dTongVat = item.tong_thue ? item.tong_thue : 0
    //       var dTien_thue = item.tien_thue ? item.tien_thue : 0
    //       var dsoluong = item.soluong ? item.soluong : 0

    //       tong_tien_tb += dTien_thue * dsoluong
    //       tong_vat += dTongVat
    //       if (dTongVat == 0) {
    //         tong_tien_tb_khongvat += dTien_thue * dsoluong
    //       }

    //       if (dTongVat == 0) continue

    //       var a = {}
    //       a.index = i

    //       a.tong_tien = dTongtien
    //       a.tong_vat = dTongVat
    //       a.tien_thue = dTien_thue
    //       lst_thietbi.push(a)
    //     }
    //   }
    //   tong_chua_thue = this.round((tong_tien_tb - tong_tien_tb_khongvat) / 1.1, 0)
    //   tong_vat = tong_tien_tb - tong_tien_tb_khongvat - tong_chua_thue

    //   var delta = 0
    //   var delta_vat = 0

    //   delta_vat = tong_vat - tong_vat //vat_tb;
    //   delta = tong_chua_thue + tong_tien_tb_khongvat - tong_tien_tb //tien_tb;

    //   //      Message_Box.Show(delta.ToString() + " - " + delta_vat.ToString());
    //   lst_thietbi.sort(function(a, b) {
    //     return Math.abs(b.tong_vat / b.tong_tien - 0.1) - Math.abs(a.tong_vat / a.tong_tien - 0.1)
    //   })
    //   //lst_thietbi = lst_thietbi.OrderByDescending(x => x.SapXep()).ToList();
    //   for (var i = 0; i < lst_thietbi.length; i++) {
    //     if (delta <= 0) break
    //     for (var item of lst_thietbi.filter((x) => x.tong_vat / x.tong_tien - 0.1 >= 0)) {
    //       // if (!item.VatNhoHon10()) // dieu kien nay filter luon o vong for roi!
    //       {
    //         var dTienVat = this.ds_thietbimua.list[item.index].tong_tien ? this.ds_thietbimua.list[item.index].tong_tien : 0
    //         if (dTienVat == 0) continue
    //         var dTien = this.ds_thietbimua.list[item.index].tong_tien ? this.ds_thietbimua.list[item.index].tong_tien : 0
    //         dTien = dTien + 1
    //         this.ds_thietbimua.list[item.index].tong_tien = dTien
    //         delta = delta - 1
    //         if (delta <= 0) break
    //       }
    //     }
    //     if (delta <= 0) break
    //   }

    //   for (var i = 0; i < lst_thietbi.length; i++) {
    //     if (delta >= 0) break
    //     for (var item of lst_thietbi.filter((x) => x.tong_vat / x.tong_tien - 0.1 < 0)) {
    //       // if (item.VatNhoHon10())
    //       {
    //         var dTienVat = this.ds_thietbimua.list[item.index].tong_tien ? this.ds_thietbimua.list[item.index].tong_tien : 0
    //         if (dTienVat == 0) continue
    //         var dTien = this.ds_thietbimua.list[item.index].tong_tien ? this.ds_thietbimua.list[item.index].tong_tien : 0
    //         dTien = dTien - 1
    //         this.ds_thietbimua.list[item.index].tong_tien = dTien
    //         delta = delta + 1
    //         if (delta >= 0) break
    //       }
    //     }
    //     if (delta >= 0) break
    //   }
    //   lst_thietbi.sort(function(a, b) {
    //     return Math.abs(b.tong_vat / b.tong_tien - 0.1) - Math.abs(a.tong_vat / a.tong_tien - 0.1)
    //   })
    //   // lst_thietbi = lst_thietbi.OrderByDescending(x => x.SapXepVAT()).ToList();
    //   for (var i = 0; i < lst_thietbi.length; i++) {
    //     if (delta_vat <= 0) break
    //     for (var item of lst_thietbi.filter((x) => x.tong_vat / x.tong_tien - 0.1 < 0)) {
    //       // if (item.VatNhoHon10())
    //       {
    //         var dTienVat = this.ds_thietbimua.list[item.index].tong_thue ? this.ds_thietbimua.list[item.index].tong_thue : 0
    //         if (dTienVat == 0) continue
    //         dTien_vat = dTien_vat + 1
    //         this.ds_thietbimua.list[item.index].tong_thue = dTien_vat
    //         // dgrDS.Rows[item.index].Cells["TONG_THUE"].Value = dTien_vat;
    //         delta_vat = delta_vat - 1
    //         if (delta_vat <= 0) break
    //       }
    //     }
    //     if (delta_vat <= 0) break
    //   }

    //   for (var i = 0; i < lst_thietbi.length; i++) {
    //     if (delta_vat >= 0) break
    //     for (var item of lst_thietbi.filter((x) => x.tong_vat / x.tong_tien - 0.1 >= 0)) {
    //       // if (!item.VatNhoHon10())
    //       {
    //         var dTienVat = this.ds_thietbimua.list[item.index].tong_thue ? this.ds_thietbimua.list[item.index].tong_thue : 0
    //         if (dTienVat == 0) continue
    //         dTien_vat = dTien_vat - 1
    //         this.ds_thietbimua.list[item.index].tong_thue = dTien_vat
    //         delta_vat = delta_vat + 1
    //         if (delta_vat >= 0) break
    //       }
    //     }
    //     if (delta_vat >= 0) break
    //   }
    //   this.$toast.success('Đã cân bằng thuế thành công')
    // },
    // GetDsTbiMua: async function() {
    //   this.loading(true)
    //   var kq_lay_lhtb = await this.$root.context.post('/web-hopdong/dichvu-cntt-gtgt/fn_kiemtra_hienthi_thietbi_theo_loaitb', {
    //     loaitb_id: this.loaitb_id
    //   })
    //   var input = {}
    //   input.vloaitb_id = this.loaitb_id
    //   if (kq_lay_lhtb && kq_lay_lhtb.data == 'OK') {
    //     input.vtocdo_id = this.tocdo_id
    //   }
    //   //Lấy danh sách thiết bị
    //   await API.lay_ds_thietbi(this.axios, input)
    //     .then((response) => {
    //       this.loading(false)
    //       if (response.data.error_code && response.data.error_code === this.success_code) {
            
    //         if (this.tocdo_id && kq_lay_lhtb && kq_lay_lhtb.data == 'OK') {
              
    //           this.ds_thietbimua.list = response.data.data
    //               .filter(x => x.ma_tbi !== null)
    //               .filter(x => x.ma_tbi.includes(this.tocdo_id));
    //         }
    //         else {
    //           this.ds_thietbimua.list = response.data.data
    //         }
    //         for (var item of this.ds_thietbimua.list) {
    //           item.tratruocreadonly = false
    //           item.serialreadonly = false
    //           item.ma_tbi = item.loaitbi_id + '-' + item.ma_tbi
    //         }
    //         if (this.dulieu.ds && this.dulieu.ds.length > 0) {
    //           var ds = this.dulieu.ds
    //           for (var item of this.ds_thietbimua.list) {
    //             var dstb = ds.filter((x) => x.loaitbi_id === item.loaitbi_id)
    //             if (item.block_tiep && item.block_tiep != 0) item.sl_cha = this._sl_cha
    //             if (dstb.length > 0) {
    //               item.dangky = 1
    //               item.soluong = dstb[0].soluong
    //               item.tien_thue = dstb[0].tien_thue
    //               item.tien = dstb[0].tien
    //               item.vat = dstb[0].vat
    //               item.tien_tratruoc = dstb[0].tien_tratruoc ? dstb[0].tien_tratruoc : 0
    //               item.vat_tratruoc = dstb[0].vat_tratruoc ? dstb[0].vat_tratruoc : 0
    //               // item.tien_khuyenmai = dstb[0].tien_khuyenmai;
    //               // item.vat_khuyenmai = dstb[0].vat_khuyenmai;
    //               //THANGDM1 2022-12-19 sua lai theo du lieu moi
    //               item.tien_km = dstb[0].tien_km
    //               item.vat_km = dstb[0].vat_km
    //               item.serial = dstb[0].serial
    //               item.block_dau = dstb[0].block_dau ? dstb[0].block_dau : 0
    //               item.block_tiep = dstb[0].block_tiep ? dstb[0].block_tiep : 0

    //               if (!item.block_tiep || item.block_tiep == 0 || this._sl_cha == 0) item.sl_cha = dstb[0].sl_cha ? dstb[0].sl_cha : 0
    //               else item.sl_cha = this._sl_cha
    //               item.gia_block_tiep = dstb[0].gia_block_tiep ? dstb[0].gia_block_tiep : 0
    //               item.vat_block_tiep = dstb[0].vat_block_tiep ? dstb[0].vat_block_tiep : 0
    //             } else {
    //               item.dangky = 0
    //             }
    //           }
    //         }
    //         if (this.formversion == 2)
    //           for (var item of this.ds_thietbimua.list) {
    //             var dVat = item.vat ? item.vat : 0
    //             var dTien = item.tien ? item.tien : 0
    //             if (dVat && dVat != 0 && item.dangky != 1) {
    //               item.tien_thue = this.round(dTien*1 + dVat*1);//this.round(dTien * 1.1 * 10) / 10
    //             } else {
    //               if (item.dangky != 1) {
    //                 item.tien_thue = this.round(dTien * 1 * 10) / 10
    //               }
    //             }
    //           }

    //         //ChangeCell_ReadOnly
    //         if (this.ds_thietbimua.list && this.ds_thietbimua.list.length > 0) {
    //           var input1 = {}
    //           input1.lstChiTietKMID = this.sDSChiTietKM_ID
    //           input1.lstLoaiTbiID = this.ds_thietbimua.list.map((x) => x.loaitbi_id)
    //           API.layds_tragop_thietbi(this.axios, input1).then((response) => {
    //             this.loading(false)
    //             if (response.data.error_code && response.data.error_code === this.success_code) {
    //               var dtTmp = response.data.data
    //               for (var item of this.ds_thietbimua.list) {
    //                 if (dtTmp.some((x) => x.loaitbi_id == item.loaitbi_id)) item.tratruocreadonly = true
    //                 else item.tratruocreadonly = false
    //                 if (item.soluong == 1) item.serialreadonly = false
    //                 else item.serialreadonly = true
    //               }
    //             }
    //           })
    //         }
    //       } else if (response.data.error == 204) {
    //         this.ShowAlert('Không có thiết bị cho loại thuê bao này!', 2)
    //       } else {
    //         this.ShowAlert('Lỗi lấy danh sách thiết bị!', 2)
    //       }
    //     })
    //     .finally(() => {
    //       this.loading(false)
    //     })
    // },
    // round(num) {
    //     if(this.is_nv_vnpti==1)
    //         return +(Math.round(num + "e+2")  + "e-2");
    //     else return Math.round(num);
    // },
  }
}
</script>
