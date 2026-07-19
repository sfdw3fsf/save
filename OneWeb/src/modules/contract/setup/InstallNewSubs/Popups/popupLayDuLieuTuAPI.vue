<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
      <div class="title"><span class="icon one-notepad"></span>{{ Text }}</div>
      <div class="close -ap icon-close hidden" data-dismiss="modal"></div>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="closeForm"> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận (F5) </a>
        </li>
        <li>
          <a href="#" @click.prevent="tsbtnTimKiem_Click">
            Tìm kiếm
          </a>
        </li>
        <li :class="{ hidden: !btnThoat }">
          <a href="#" @click.prevent="terminateForm"> <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Huỷ bỏ </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="row">
        <div class="col-12">
          <div class="box-form">
            <div class="legend-title mart20">
              <table class="table-result table-gachle">
                <tbody>
                  <tr v-for="(hdx, idx) in dynamicLayout" :key="idx">
                    <td class="w20 center"></td>
                    <td class="fw6">{{ hdx.Text }}</td>
                    <td>
                      <template v-if="hdx.Type == 'ButtonEdit'">
                        <div class="input-more-button -right">
                          <button class="btn h-auto pad4 lh14 w24" @click.prevent="hdx.ButtonClick(hdx)">
                            <span class="-ap icon-more_horiz"></span>
                          </button>
                          <input type="text" :class="{ disabled: hdx.ReadOnly ? true : false }" class="form-control padr30  h24 bor0 nocorner bg-none" :value="hdx.Value" @change="(v) => (hdx.Value = v.target.value)" />
                        </div>
                      </template>
                      <template v-if="hdx.Type == 'TextEdit'">
                        <input
                          type="text"
                          class="form-control padr30  h24 bor0 nocorner bg-none"
                          :value="hdx.Value"
                          @change="
                            (v) => {
                              hdx.Value = v.target.value
                              hdx.EditValueChanged(hdx)
                            }
                          "
                        />
                      </template>
                      <template v-if="hdx.Type == 'CalcEdit'">
                        <input type="number" class="form-control padr30  h24 bor0 nocorner bg-none" :value="hdx.Value" @change="(v) => (hdx.Value = v.target.value)" />
                      </template>
                      <template v-if="hdx.Type == 'DateEdit'">
                        <div class="input-icon-right">
                          <date-picker input-class="form-control h24 bor0 nocorner bg-none" :format="hdx.EditMask" :value-type="'format'" v-model="hdx.Value" @change="hdx.EditValueChanged(hdx)"></date-picker>
                          <span class="icon one-calendar"></span>
                        </div>
                      </template>
                      <template v-if="hdx.Type == 'GridLookUpEdit'">
                        <!-- LookUpEdit {{hdx.DataSource}} {{hdx.DisplayMember}} {{hdx.ValueMember}} {{hdx.Value}} -->
                        <SelectExt class="bor0 nocorner bg-none" v-model="hdx.Value" :options="hdx.DataSource" dataTextField="text" dataValueField="id" @change="hdx.EditValueChanged(hdx)"></SelectExt>
                      </template>
                      <template v-if="hdx.Type == 'LookUpEdit'">
                        <!-- LookUpEdit {{hdx.DataSource}} {{hdx.DisplayMember}} {{hdx.ValueMember}} {{hdx.Value}} -->
                        <SelectExt class="bor0 nocorner bg-none" v-model="hdx.Value" :options="hdx.DataSource" dataTextField="text" dataValueField="id" @change="hdx.EditValueChanged(hdx)"></SelectExt>
                      </template>
                      <template v-if="hdx.Type == 'CheckedComboBoxEdit1'">
                        <!-- Tree {{hdx.DataSource}} {{hdx.DisplayMember}} {{hdx.ValueMember}} -->
                        <treeselect :multiple="true" :options="hdx.DataSource.map((x) => ({ label: x[hdx.DisplayMember], id: x[hdx.ValueMember] }))" placeholder="..." v-model="hdx.Value" />
                      </template>
                      <template v-if="hdx.Type == 'CheckEdit'">
                        <input type="checkbox" class="check" :value="hdx.ValueChecked" v-model="hdx.Value" />
                      </template>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-12">
          <div class="box-form">
            <div class="legend-title mart20">Kết quả tìm kiếm</div>
            <DataGrid
              v-bind:columns="grcDuLieu.cols"
              v-bind:dataSource="grcDuLieu.list"
              :showFilter="true"
              :showColumnCheckbox="false"
              :selectionSettings="{ type: 'Single' }"
              :dataKeyField="grcDuLieu.keycol"
              :defaultColumnCheckboxChecked="false"
              panelDataHeight="200"
              @selectedItemsChanged="selectedItemsChanged"
            >
            </DataGrid>
          </div>
        </div>
      </div>
    </div>
    <!-- <popupPreviewHTML :dulieu="prevhtml" ref="popupPreviewHTML"></popupPreviewHTML> -->
  </div>
</template>
<style scoped></style>
<script>
import { LoaiHinhTB } from '../Enums'
import popupPreviewHTML from './popupPreviewHTML'
export default {
  name: 'popupLayDuLieuTuAPI',
  props: ['dulieu'],
  components: { popupPreviewHTML },
  watch: {},
  data() {
    return {
      grcDuLieu: {
        list: [],
        cols: [], // truong hop form nay kha nang cols se phai tu tao dong
        keycol: '',
        value: {},
        values: []
      },
      btnThoat: false,
      loaihinh_tb: 0,
      field_name: '',
      api_url: '',
      method: '',
      hienthi_bang: '',
      giatri_trave: '',
      ItemList: [],
      Text: 'Lấy dữ liệu từ API',
      DicInputControl: [],
      dynamicLayout: [],
      thamsocontrol: []
    }
  },
  mounted() {
    //console.log(this.dulieu)
    if (this.dulieu.loaihinh_tb) this.loaihinh_tb = this.dulieu.loaihinh_tb
    if (this.dulieu.field_name) this.field_name = this.dulieu.field_name
    if (this.dulieu.api_url) this.api_url = this.dulieu.api_url
    if (this.dulieu.method) this.method = this.dulieu.method
    if (this.dulieu.hienthi_bang) this.hienthi_bang = this.dulieu.hienthi_bang
    if (this.dulieu.giatri_trave) this.giatri_trave = this.dulieu.giatri_trave
    if (this.dulieu.ItemList) this.ItemList = this.dulieu.ItemList
    if (this.dulieu.Text) this.Text = this.dulieu.Text
    if (this.dulieu.thamsocontrol) this.thamsocontrol = this.dulieu.thamsocontrol
    this.frmLayDuLieuTuAPI_Load()
  },
  computed: {},
  methods: {
    closeForm() {
      this.$emit('form-close', { vitri: this.dulieu.idx ? this.dulieu.idx : 0, bangtrave: this.grcDuLieu.values, giatrichon: this.grcDuLieu.value[this.giatri_trave] ? this.grcDuLieu.value[this.giatri_trave] : '404' })
    },
    terminateForm() {
      this.$emit('form-close', null)
    },
    rowChanged(idx, item) {
      this.grcDuLieu.value = item
    },
    selectedItemsChanged(items) {
      this.grcDuLieu.values = items
      this.grcDuLieu.value = items.length > 0 ? items[0] : {}
    },
    async frmLayDuLieuTuAPI_Load() {
      var rs = await this.$root.context.post('/web-hopdong/lapdatmoi/lay_ds_thuoctinh_api', {
        vloaitb_id: this.loaihinh_tb,
        vfield_name: this.field_name
      })
      this.dsControl = this.UpperCasePropertyList(rs.data)
      // dsControl = hdtbServices.LayDSControl_API(_loaihinh_tb, _field_name);
      await this.TaoForm()
      await this.GanMacDinh()
      await this.GetDataFromAPI()
    },
    UpperCasePropertyList(obj) {
      return obj.map(function(item) {
        for (var key in item) {
          var upper = key.toUpperCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    async TaoForm() {
      // var lstLayoutControlItem = [];
      for (var item of this.dsControl) {
        console.log(item)
        var item1 = {}
        var edit = { Value: 0 }
        switch (item.ATT) {
          case 0:
            edit.Type = 'TextEdit' //new TextEdit();
            edit.Value = item.GIATRI
            // item1.Control = edit;
            break
          case 1:
            edit.Type = 'CalcEdit' //new CalcEdit();
            break
          case 2:
            edit.Type = 'DateEdit' //new DateEdit();
            edit.DisplayFormat.FormatString = 'dd/MM/yyyy'
            edit.EditFormat.FormatString = 'dd/MM/yyyy'
            edit.EditMask = 'dd/MM/yyyy'
            edit.Mask.UseMaskAsDisplayFormat = true
            edit.Mask.MaskType = DevExpress.XtraEditors.Mask.MaskType.DateTimeAdvancingCaret
            break
          case 4:
            edit.Type = 'GridLookUpEdit' //new GridLookUpEdit();
            edit.ValueMember = item.VALUE_MEMBER
            edit.DisplayMember = item.DISPLAY_MEMBER
            edit.View.OptionsView.ShowAutoFilterRow = true
            var api = item.LOOKUP_API
            var sql = item.LOOKUP_SQL
            if (api && api.trim() != '') {
              var dt_api = await this.GetDataFromAPI_Control(api)
              edit.DataSource = dt_api
            }
            if (sql && sql.trim() != '') {
              // var rs = await this.$root.context.post("/web-danhba/huonggiao/sp_huonggiao_runsql",{
              //     "command":sql
              // })
              // var dt_sql = tdan.GET_DATA_SQL(sql);
              edit.DataSource = item.LOOKUP_SQL_RESPONSE //rs.data;
            }
            break
          case 5:
            edit.Type = 'ButtonEdit' //new ButtonEdit();
            break
          case 7:
            edit.Type = 'CheckEdit' //new CheckEdit();
            edit.ValueChecked = 1
            edit.ValueUnchecked = 0
            edit.Text = ''
            break
          case 9:
            edit.Type = 'DateEdit' //new DateEdit();
            edit.DisplayFormat.FormatString = 'dd/MM/yyyy HH:mm:ss'
            edit.EditFormat.FormatString = 'dd/MM/yyyy HH:mm:ss'
            edit.EditMask = 'dd/MM/yyyy HH:mm:ss'
            edit.Mask.UseMaskAsDisplayFormat = true
            // edit.Mask.MaskType = DevExpress.XtraEditors.Mask.MaskType.DateTimeAdvancingCaret;
            break
          case 10:
            edit.Type = 'CheckedComboBoxEdit' //new CheckedComboBoxEdit();
            edit.ValueMember = item.VALUE_MEMBER
            edit.DisplayMember = item.DISPLAY_MEMBER
            var sql2 = item.LOOKUP_SQL
            if (sql2 && sql.trim() != '') {
              // DataTable dt_sql2 = tdan.GET_DATA_SQL(sql2);
              // var rs = await this.$root.context.post("/web-danhba/huonggiao/sp_huonggiao_runsql",{
              //     "command":sql2
              // })
              edit.DataSource = item.LOOKUP_SQL_RESPONSE //rs.data;//dt_sql2;
            }
            break
          default:
            edit.Type = 'TextEdit' //new TextEdit();
            break
        }
        item1 = edit
        edit.Name = 'txt' + item.TEN_THAMSO
        // DicInputControl.Add(edit.Name, edit);
        this.DicInputControl.push(edit)

        // item1.Location = new Point(0, 0); // khong dung
        item1.Text = item.TEN_HIENTHI_TS
        this.dynamicLayout.push(item1) // cho nay can convert tu dynamicLayout nay ra thanh cols cua grc
      }
    },
    GanMacDinh() {
      if (this.loaihinh_tb == LoaiHinhTB.VNPT_SLL) {
        if (this.field_name == 'SOLUONG') {
          for (var item of this.DicInputControl)
            if (item.Name == 'txtidtruong')
              try {
                item.Value = this.ItemList.filter((x) => x.sFieldName == 'MA_PHU')[0].sValue
                item.ReadOnly = true
              } catch (ex) {
              } finally {
                break
              }
          // DicInputControl["txtidtruong"].EditValue = _ItemList.AsEnumerable().Where(x => x.sFieldName == "MA_PHU").FirstOrDefault().sValue;
          // DicInputControl["txtidtruong"].Properties.ReadOnly = true;
        }
      }
    },
    async GetDataFromAPI_Control(url_control) {
      this.loading(true)
      // DevExpress.Utils.WaitDialogForm f = new DevExpress.Utils.WaitDialogForm("Đang lấy dữ liệu từ API");
      try {
        var link = this.MakeGetLinkControl(this.thamsocontrol, url_control)
        // RestClient rc = new RestClient();
        // rc.EndPoint = link;

        // // f.Text = "Bạn vui lòng chờ...";
        // // f.Show();
        // string ketqua = rc.MakeRequest();
        // API_RESULT_DYNAMIC dlkq = JsonConvert.DeserializeObject<API_RESULT_DYNAMIC>(ketqua);
        var rs = await this.$root.context.post('/general/callapi', {
          apipath: link,
          apimethod: 'GET',
          apibody: ''
        })
        return rs && rs.data && rs.data.Data ? rs.data.Data : [] //TODO can test lai cho nay vi data tra ra o field data co the can lay vao sau hon
        // var dt = dlkq.Data;
        // // f.Close();
        // return dt;
      } catch (ex) {
        this.$toast.error('' + ex)
        return null
      } finally {
        this.loading(false)
      }
    },
    MakeGetLinkControl(thamso, url_control) {
      var link = url_control + '?'
      var query = ''

      for (var i = 0; i < thamso.length; i++) {
        query = query + thamso[i].Name + '=' + thamso[i].Value
        if (i < thamso.length - 1) {
          query = query + '&'
        }
      }
      return link + query
    },
    async tsbtnTimKiem_Click() {
      this.GetDataFromAPI()
    },
    async GetDataFromAPI() {
      // DevExpress.Utils.WaitDialogForm f = new DevExpress.Utils.WaitDialogForm("Đang lấy dữ liệu từ API");
      this.loading(true)
      try {
        var link = this.MakeGetLink()
        // RestClient rc = new RestClient();

        // rc.EndPoint = link;

        // f.Text = "Bạn vui lòng chờ...";
        // f.Show();
        // string ketqua = rc.MakeRequestEdu("");
        //MakeRequestEdu nhieu hon MakeRequest o header -> request.Headers.Add("Authorization", "bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MTU1ODcwNjUsImlzcyI6ImR1b25ncCIsImF1ZCI6ImR1b25ncCJ9.dU3b3O-VWxIFv05lDs34Zi5AGUAFdx_n2oV_MX-DpHw");

        var rs = await this.$root.context.post('/tichhop/general/callapi', {
          apipath: link,
          apimethod: 'GET',
          apibody: ''
        })
        // API_RESULT_DYNAMIC dlkq = JsonConvert.DeserializeObject<API_RESULT_DYNAMIC>(ketqua);
        // dt = dlkq.Data.Copy();
        // console.log(rs.data,rs.data.data);
        if (rs.data && rs.data.data) {
          rs = JSON.parse(rs.data.data)
        } else {
          var err = ''
          if (rs.faultString) err = rs.faultString
          else if (rs.data.faultString) err = rs.data.faultString
          else if (rs.data.data.faultString) err = rs.data.data.faultString
          else err = 'Lỗi lấy dữ liệu từ API liên kết!'
          this.$toast.error(err)
          return
        }
        if (!rs.success) {
          this.$toast.error(rs.msg)
          return
        }
        var dt = rs && rs.data && rs.data.aRoot ? rs.data.aRoot : []
        if (!dt || dt.length <= 0) this.$toast.error('Không có dữ liệu từ hệ thống API liên kết!')
        for (var i = 0; i < dt.length; i++) {
          dt[i]['chon'] = false
        }
        //BindingSource bs = new BindingSource();
        //bs.DataSource = dt;
        console.log(dt)
        this.grcDuLieu.list = dt // Can cho phep edit tren tat ca cac cot tru cot "chon"
        var cols = []
        for (var item of Object.keys(dt[0]))
          if (item == 'chon') continue
          else cols.push({ fieldName: item, headerText: item, allowFiltering: true, allowSorting: true })
        this.grcDuLieu.cols = cols
        // for (int i = 0; i < gdvDuLieu.Columns.Count; i++)
        // {
        //     if (gdvDuLieu.Columns[i].FieldName.ToString() != "chon")
        //     {
        //         gdvDuLieu.Columns[i].OptionsColumn.AllowEdit = false;
        //     }
        //     else {
        //         gdvDuLieu.Columns[i].OptionsColumn.AllowEdit = true;
        //         gdvDuLieu.Columns[i].FieldName = "chon";
        //     }
        // }

        // gdvDuLieu.OptionsView.ColumnAutoWidth = false;
        // gdvDuLieu.BestFitColumns();
        // f.Close();
      } catch (ex) {
        this.$toast.error('' + ex)
      } finally {
        this.loading(false)
      }
    },
    MakeGetLink() {
      var link = this.api_url + '?'
      var query = ''

      for (var i = 0; i < this.DicInputControl.length; i++) {
        query = query + this.DicInputControl[i].Name.replace('txt', '') + '=' + this.DicInputControl[i].Value
        if (i < this.DicInputControl.length - 1) {
          query = query + '&'
        }
      }
      return link + query
    }
  }
}
</script>
