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
          <div class="legend-title mart20">Luỹ tiến sản lượng</div>
          <ejs-grid
            ref="grid"
            :load="load"
            :dataSource="grcGoiSanLuong.list"
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
              <e-column field="bac" headerText="Bậc" headerTextAlign="center" textAlign="Left" width="150" minWidth="100" maxWidth="500" isPrimaryKey="true" allowEditing="false"></e-column>
              <e-column field="giatridau" :customAttributes="{ class: 'tien' }" format="N" headerText="Giá trị đầu" headerTextAlign="center" textAlign="Right" width="150" allowEditing="true"></e-column>
              <e-column field="giatricuoi" :customAttributes="{ class: 'tien' }" format="N" headerText="Giá trị cuối" headerTextAlign="center" textAlign="Right" width="150" allowEditing="true"></e-column>
              <e-column field="dongiabantin" :customAttributes="{ class: 'tien' }" format="N" headerText="Giá trị/Bản ghi" headerTextAlign="center" textAlign="Right" width="150" allowEditing="true"></e-column>
            </e-columns>
          </ejs-grid>
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
  name: 'popupLuyTienSanLuong',
  provide: {
    grid: [Sort, Filter, Resize, Group, Freeze, CommandColumn, ExcelExport, Edit, Aggregate, Toolbar]
  },
  components: { required, minLength, integer, maxValue, DatePicker, L10n, setCulture, GridPlugin, Page, Sort, Filter, GridComponent, Grid, Aggregate, Edit },
  props: ['dulieu'],
  data() {
    return {
      grcGoiSanLuong: {
        list:  [],
        header: [],
        value: {},
        isEnabled: true,
        checked: []
      },
      is_ghilai: false,
    }
  },
  mounted() {
    this.LoadDSHDTB_CNTT_SUB();
  },
  computed: {
    // is_nv_vnpti() {
    //     return this.$root.token.getPhanVungID()==98?1:0
    // },
  },
  methods: {
    async LoadDSHDTB_CNTT_SUB()
    {
      var rs = await this.$root.context.post('/web-hopdong/lapdatmoi/sp_ds_hdtb_cntt_sub_baohieuquocte', {
          hdtb_id: this.dulieu.hdtb_id, loaicuoc: this.dulieu.loaicuoc
      });
      if(rs && rs.data && rs.data.length>0) {
          this.grcGoiSanLuong.list = rs.data;
      }
      else
      {
        // NOTE: cho nay khong can khoi tao, form nay se co nut add giong lap moi
        this.grcGoiSanLuong.list = [];
        for(var i = 0; i < 10; i++) {
          this.grcGoiSanLuong.list.push({bac:i+1,giatridau:null,giatricuoi:null,dongiabantin:null});
        }
      }
      this.grcGoiSanLuong.list.sort((a,b) => a.bac*1 - b.bac*1);
    },
    async btnGhiLai_Click() {
      try
      {
        this.grcGoiSanLuong.list = this.grcGoiSanLuong.list.filter(x=>!(!x.giatridau && !x.giatricuoi && !x.dongiabantin));
        this.grcGoiSanLuong.list.sort((a,b) => a.bac*1 - b.bac*1);
        for(var i = 1; i < this.grcGoiSanLuong.list.length; i++) {
          if(this.grcGoiSanLuong.list[i].bac != this.grcGoiSanLuong.list[i-1].bac*1+1) { 
            this.$toast.error("Thứ tự bậc phải liền mạch! Bạn có thể thêm bậc mới hoặc xoá bậc trùng để đảm bảo điều kiện trên.");
            return;
          }
        }
        var rs = await this.$root.context.post('/web-hopdong/lapdatmoi/quyen_xoa_loaicuoc_baohieuquocte',{ hdtb_id: this.dulieu.hdtb_id, luong_id:this.dulieu.luong_id });// tdan.GET_VALUE_FUNC("{?DB2}.QUYEN_XOA_LOAICUOC_BAOHIEUQUOCTE", "vhdtb_id", hdtb_id, "vluong_id", luong_id, "vuser", tt_nd.ma_nd).ToString();
        if (rs && rs.data == "OK")
        {
          this.$refs.grid.ej2Instances.vueInstance.endEdit()
              for (var i=0; i<this.grcGoiSanLuong.list.length;i++) 
              {
                var row = this.grcGoiSanLuong.list[i];
                if (!row['giatridau'] || !row['giatricuoi'] || !row['dongiabantin'])
                {
                    this.$toast.error(`Hãy nhập đủ thông tin của bậc ${row['bac']}. Nếu không sử dụng, hãy xoá bậc tương ứng!`);
                    return;
                }
                if(row['giatridau'] >= row['giatricuoi']) {
                  this.$toast.error(`Giá trị cuối không được nhỏ hơn hoặc bằng giá trị đầu ở bậc ${row['bac']}!`);
                  return;
                }
                if(i<this.grcGoiSanLuong.list.length-1 && this.grcGoiSanLuong.list[i+1]['giatridau'] != this.grcGoiSanLuong.list[i]['giatricuoi']*1+1) {
                  this.$toast.error(`Giá trị đầu của bậc ${this.grcGoiSanLuong.list[i+1]['bac']} phải bằng giá trị cuối của bậc ${this.grcGoiSanLuong.list[i]['bac']} + 1!`);
                  return;
                }
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
              var kq = await this.$root.context.get('/web-hopdong/lapdatmoi/sp_del_hdtb_cntt_sub_by_pin', {params:{ hdtb_id: this.dulieu.hdtb_id, pin: 'LUYTIENSANLUONG' }});
              if (kq && kq.data && kq.data > 0)
              {
                  this.$toast.success("Xóa thành công");
                  this.LoadDSHDTB_CNTT_SUB();
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
    load: function() {
      this.$refs.grid.ej2Instances.element.addEventListener('mouseup', function(e) {
        var instance = this.ej2_instances[0]
        if (e.target.classList.contains('e-rowcell')) {
          if (instance.isEdit) instance.endEdit()
          let index = parseInt(e.target.getAttribute('Index'))
          instance.selectRow(index)
          instance.startEdit()
        }
      })
      this.$refs.grid.ej2Instances.element.addEventListener('change', function(e) {
        if (e.target.classList.contains('e-checkbox')) {
          var instance = this.ej2_instances[0]
          if (instance.isEdit) {
            instance.editModule.batchSave()
            instance.endEdit()
          }
        }
      })
    },
    rowselect() {
    },

    rowdeselect() {
    },
    actionComplete(args) {
      if (args.requestType === 'save') {
        var item_lt = this.grcGoiSanLuong.list.find(x=>x.bac==args.data.bac*1-1);
        if(item_lt && item_lt.giatricuoi && !args.data.giatridau) {
          args.data.giatridau = item_lt.giatricuoi*1 + 1;
        }
        if(item_lt && item_lt.giatricuoi && args.data.giatridau && item_lt.giatricuoi*1+1 != args.data.giatridau) { 
          this.$toast.error(`Giá trị đầu phải bằng giá trị cuối của bậc trước + 1!`);
          return
        }
        if(args.data.giatridau >= args.data.giatricuoi) { 
          this.$toast.error(`Giá trị cuối không được nhỏ hơn hoặc bằng giá trị đầu ở bậc ${args.data.bac}!`);
          return
        }
        var item = this.grcGoiSanLuong.list.find(x=>x.bac==args.data.bac);
        if(item) {
          item.giatridau = args.data.giatridau;
          item.giatricuoi = args.data.giatricuoi;
          item.dongiabantin = args.data.dongiabantin;
        }
        this.$forceUpdate()
      }
      if (args.requestType === 'refresh') {
      }
      if (args.requestType === 'beginEdit') {
        
      }
    },
  }
}
</script>
