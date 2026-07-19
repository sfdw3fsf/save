<template>
   <b-modal
    id="capNhatThaoTac"
    title="Cập nhật thao tác"
    modal-class="cap-nhat-thao-tac"
    header-class="popup-header"
    content-class="cap-nhat-thao-tac-content"
    body-class="cap-nhat-thao-tac-body"
    size="xl"
    hide-footer
  >
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="javascript:void(0)" @click.prevent="tsbtnNhapMoi_Click" :class="{disabled: !tsbtnNhapMoi}">
                    <span class="icon one-file-plus"></span> Nhập mới
                </a>
            </li>
            <li>
                <a href="javascript:void(0)" @click.prevent="tsbtnGhiLai_Click" :class="{disabled: !tsbtnGhiLai}">
                    <span class="icon one-save"></span> Ghi lại
                </a>
            </li>
            <li>
                <a href="javascript:void(0)" @click.prevent="tsbtnHuyBo_Click" :class="{disabled: !tsbtnHuyBo}">
                    <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Hủy
                </a>
            </li>
            <li>
                <a href="javascript:void(0)" @click.prevent="tsbtnXoa_Click" :class="{disabled: !tsbtnXoa}">
                    <span class="icon one-trash"></span> Xóa
                </a>
            </li>
        </ul>
    
    </div>
    <div class="popup-body">
        <div class="grid-stack-box">
            <div class="box-col  box-form" id="boxLeft">
                <div class="legend-title">Chi tiết thao tác</div>
                <div class="info-row">
                    <div class="key w90">Tên thao tác</div>
                    <div class="value">
                        <input type="text" class="form-control highlight" v-model="tenThaoTac">
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w90">Code</div>
                    <div class="value">
                        <input type="text" class="form-control highlight" v-model="codeThaoTac">
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w90">Tên Control</div>
                    <div class="value">
                        <input type="text" class="form-control highlight" v-model="tenControl">
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w90">Ghi chú</div>
                    <div class="value">
                        <input type="text" class="form-control highlight" v-model="ghiChu">
                    </div>
                </div>
                <div class="legend-title mart10">
                    Danh sách thao tác
                </div>
                <div class="table-content" style="height: 300px;">
                    <DataGrid
                            v-bind:columns="danhSachThaoTac.header"                    
                            v-bind:dataSource="danhSachThaoTac.list"
                            :enable-paging-server="false"                                        
                            :allowPaging="true"
                            :showFilter="true"
                            :showColumnCheckbox="false"
                            :enabledSelectFirstRow="false"
                            @rowClicked="grvDanhSach_FocusedRowChanged"
                            ref="danh_sach_thao_tac"
                            >
                        </DataGrid>
                </div>
            </div>
            <div class="box-col box-form" id="boxRight">
                <div class="legend-title">Danh sách Control</div>
                <div class="box-move-select-table">
                    <div class="box-col">
                
                        <div class="table-content" style="height: 500px;">
                            <DataGrid
                                v-bind:columns="danhSachControlDaGan.header"                    
                                v-bind:dataSource="danhSachControlDaGan.list"
                                :enable-paging-server="false"                                        
                                :allowPaging="true"
                                :showFilter="true"
                                :showColumnCheckbox="true"
                                :enabledSelectFirstRow="false"
                                ref="danh_sach_control_da_gan"
                                >
                            </DataGrid>
                        </div>
                        
                    </div>
                    <div class="actions">
                        <button class="btn" @click="btnHDRight">
                            <span class="fa fa-angle-right"></span>
                        </button>
                        <button class="btn" @click="btnHDRightAll">
                            <span class="fa fa-angle-double-right"></span>
                        </button>
                        <button class="btn" @click="btnHDLeft">
                            <span class="fa fa-angle-left"></span>
                        </button>
                        <button class="btn" @click="btnHDLeftAll">
                            <span class="fa fa-angle-double-left"></span>
                        </button>
                    </div>
                    <div class="box-col">
                
                        <div class="table-content" style="height: 500px;">
                            <DataGrid
                                v-bind:columns="danhSachControlChuaGan.header"                    
                                v-bind:dataSource="danhSachControlChuaGan.list"
                                :enable-paging-server="false"                                        
                                :allowPaging="true"
                                :showFilter="true"
                                :showColumnCheckbox="true"
                                :enabledSelectFirstRow="false"
                                ref="danh_sach_control_chua_gan"
                                >
                            </DataGrid>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
  </b-modal>
</template>
<script>
import moment from "moment";
import Vue from 'vue'
import API from '../API'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
import gridView from '@/components/Shared/gridview.vue'
export default {
  props: ['luongId'],
  components: {
    API,
    gridView
  },
  name: 'capNhatThaoTac',
  mounted() {
    this.frmThaoTac_Load()
  },
  data() {
    return {
      dsTHAOTAC : [],
      dsTHAOTACCONTROL : [],
      objThaoTac :{},
      thaotac_id : 0,
      danhSachThaoTac : {
        list: [],
        header: [
            {fieldName: 'stt', headerText: 'STT', allowFiltering: true, width: 'auto'},
            {fieldName: 'thaotac', headerText: 'Thao tác đã gán', allowFiltering: true, width: 'auto'},
            {fieldName: 'code', headerText: 'Code', allowFiltering: true, width: 'auto'},
        ],
        value: {},
        isEnabled: true,
      },
      tsbtnNhapMoi : false,
      tsbtnGhiLai : false,
      tsbtnXoa : false,
      tsbtnHuyBo : false,
      tenThaoTac : '',
      codeThaoTac : '',
      tenControl : '',
      ghiChu : '',
      danhSachControlDaGan : {
        list: [],
        header: [
          {fieldName: 'control_name', headerText: 'Control đã gán', allowFiltering: true, width: 'auto'},
        ],
        value: {},
        isEnabled: true,
      },
      danhSachControlChuaGan : {
        list: [],
        header: [
          {fieldName: 'control_name', headerText: 'Control chưa gán', allowFiltering: true, width: 'auto'},
        ],
        value: {},
        isEnabled: true,
      }
    }
  },
  watch: {
  },
  methods: {
    show() {
      this.$bvModal.show("capNhatThaoTac");
      this.frmThaoTac_Load()
    },
    ShowAlert(mesage)
    {
      this.$toast.error(mesage);
    },
    showSuccess(mesage) {
      this.$toast.success(mesage);
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    frmThaoTac_Load() {
        this.HienThiDanhSach()
    },

    async HienThiDanhSach() {
        let input = {
            'thaotacId' : this.thaotac_id
        }

        let data = this.GetData(await API.lay_ds_thaotac(this.axios, input))
        if (data && data.length > 0) {
            this.danhSachThaoTac.list = data
            this.grvDanhSach_FocusedRowChanged(0)
        }
    },
    async capNhat() {
        if (!this.KiemTra_DuLieu()) return;
        if (this.tsbtnNhapMoi == false)
        {
            let input = this.TaoDuLieu(1);
            let data =  API.capnhat_thaotac(this.axios, input)
            if (data && data.data) {
                if (data.data.error == 200) {
                    this.showSuccess('Thêm mới thao tác thành công !");')
                }
            }     
        }
        else
        {
            let input = this.TaoDuLieu(2);
            let data = await API.capnhat_thaotac(this.axios, input)
            if (data && data.data) {
                if (data.data.error == 200) {
                    this.showSuccess('Cập nhật thao tác thành công !");')
                }
            } 
        }
        this.HienThiDanhSach();
        this.setButton(3);
    },
    TaoDuLieu(themmoi) {
        let jsonThaoTac = {
            'THAOTAC' : this.tenThaoTac,
            'CODE' : this.codeThaoTac,
            'GHICHU' : this.ghiChu
        }
        let thaoTacCt = []

        if (this.danhSachControlDaGan.list.length > 0) {
            this.danhSachControlDaGan.list.map(item => {
                thaoTacCt.push ({
                    'PHANVUNG_ID' : item.phanvung_id,
                    'THAOTAC_ID' : item.thaotac_id,
                    'CONTROL_NAME' : item.control_name
                })
            })
        }

        let obj = {
            'thaotac_id' : this.thaotac_id,
            'kieu' : themmoi,
            'json_thaotac' : JSON.stringify(jsonThaoTac),
            'json_thaotac_ct' : JSON.stringify(thaoTacCt)
        }
        return obj
    },
    tsbtnNhapMoi_Click() {
        this.setButton(1)
    },
    tsbtnHuyBo_Click() {
        this.setButton(2)
    },
    tsbtnGhiLai_Click() {
        this.capNhat()
    },
    async tsbtnXoa_Click() {
        let input = {
            'thaotac_id' : this.thaotac_id
        }

        let data = await API.xoa_thaotac(this.axios, input)
        console.log(data)

        if (data && data.data) {
            if (data.data.error == 200) {
                this.showSuccess('Xóa thành công !')
                this.HienThiDanhSach()
            }
        }
    },
    clear()
    {
        this.ghiChu= "";
        this.tenThaoTac = "";
        this.codeThaoTac = "";
        this.thaotac_id = 0;
    },

    KiemTra_DuLieu() {
        if (this.tenThaoTac == "")
        {
            this.ShowAlert('Tên thao tác không được để trống !')
            return false;
        }
        if (this.codeThaoTac == "")
        {
            this.ShowAlert('Mã thao tác không được để trống !')
            return false;
        }
        if (this.tenControl != "")
        {
            if (this.danhSachControlDaGan.list.length > 0) {
                this.danhSachControlDaGan.list.map(item => {
                    if (item.control_name.toUpperCase() == this.tenControl.toUpperCase()) {
                        this.ShowAlert('Control này đã có trong dữ liệu !')
                        return false
                    }
                })
            }
            if (this.danhSachControlChuaGan.list.length > 0) {
                this.danhSachControlChuaGan.list.map(item => {
                    if (item.control_name.toUpperCase() == this.tenControl.toUpperCase()) {
                        this.ShowAlert('Control này đã có trong dữ liệu !')
                        return false
                    }
                })
            }
                          
        }
        return true;
    },

    grvDanhSach_FocusedRowChanged(index) {
        if (index < 0)
        {
            this.setButton(1);
            return;
        }
        if(this.danhSachThaoTac.list.length > 0) {
            let obj = this.danhSachThaoTac.list[index] ? this.danhSachThaoTac.list[index] : {}
            console.log('obj is ', obj)
            if (obj)
            {
                this.thaotac_id = obj.thaotac_id ? obj.thaotac_id : 0
                this.tenThaoTac = obj.thaotac ? obj.thaotac : ''
                this.codeThaoTac = obj.code ? obj.code : ''
                this.ghiChu = obj.ghichu ? obj.ghichu : ''

                this.HienThi_Control(this.thaotac_id);
                this.setButton(3);
            }
        }
    },
    setButton(kieu) {
        this.tsbtnNhapMoi = false;
        this.tsbtnGhiLai = false;
        this.tsbtnXoa = false;
        this.tsbtnHuyBo = false;
        this.tenControl = "";
        if (kieu == -1)//Bat dau
        {
            this.tsbtnGhiLai = true;
            this.tsbtnHuyBo = true;
        }
        if (kieu == 0)//Bat dau
        {
            this.tsbtnNhapMoi = true;
            this.clear();
        }
        if (kieu == 1)//Them moi
        {
            this.tsbtnGhiLai = true;
            this.tsbtnHuyBo = true;
            this.clear();
        }
        if (kieu == 2)//Huy
        {
            this.tsbtnNhapMoi = true;
            this.tsbtnXoa = true;
            this.clear();
        }
        if (kieu == 3)//Edit
        {
            this.tsbtnNhapMoi = true;
            this.tsbtnXoa = true;
            this.tsbtnGhiLai = true;
            this.tsbtnHuyBo = true;
        }
    },
    async HienThi_Control(id) {
        console.log('id is ', id)
        let input = {
            "p_thaotac_id": id,
            "p_kieu": 1
        }
        let input1 = {
            "p_thaotac_id": id,
            "p_kieu": 0
        }

        let data = this.GetData(await API.sp_lay_ds_thaotac_control(this.axios, input))
        let data1 = this.GetData(await API.sp_lay_ds_thaotac_control(this.axios, input1))

        if (data) {
            this.danhSachControlDaGan.list = data
        }

        if (data1) {
            this.danhSachControlChuaGan.list = data1
        }

    },
    btnHDRight()
    {
      // var items = this.listTable.hop_dong_thu_tien.list.filter(a => a.ischecked);
      let items = this.$refs.danh_sach_control_da_gan.getSelectedRecords();
      if (items.length > 0) {
        for (var item of items) {
          this.danhSachControlChuaGan.list.push(
            {
              'phanvung_id': item.phanvung_id, 
              'thaotac_id': item.thaotac_id,
              'control_name' : item.control_name
            }
          );
        }
      }
      var temps = this.danhSachControlDaGan.list.filter(a => !items.includes(a));
      this.danhSachControlDaGan.list = [];
      this.danhSachControlDaGan.list = temps;
    },

    btnHDRightAll()
    {
      if (this.danhSachControlDaGan.list.length > 0) {
        for (var item of this.danhSachControlDaGan.list) {
          this.danhSachControlChuaGan.list.push(
            {
              'phanvung_id': item.phanvung_id, 
              'thaotac_id': item.thaotac_id,
              'control_name' : item.control_name
            }
          );
        }
        this.danhSachControlDaGan.list = [];
      }
    },

    btnHDLeft()
    {
      // var items = this.listTable.hop_dong_chua_tra_tien.list.filter(a => a.ischecked);
      let items = this.$refs.danh_sach_control_chua_gan.getSelectedRecords();
      if (items.length > 0) {
        for (var item of items) {
          this.danhSachControlDaGan.list.push(
            {
              'phanvung_id': item.phanvung_id, 
              'thaotac_id': item.thaotac_id,
              'control_name' : item.control_name
            }
          );
        }
      }
      var temps = this.danhSachControlChuaGan.list.filter(a => !items.includes(a));
      this.danhSachControlChuaGan.list = [];
      this.danhSachControlChuaGan.list = temps;
    },

    btnHDLeftAll()
    {
      if (this.danhSachControlChuaGan.list.length > 0) {
        for (var item of this.danhSachControlChuaGan.list) {
          this.danhSachControlDaGan.list.push(
            {
              'phanvung_id': item.phanvung_id, 
              'thaotac_id': item.thaotac_id,
              'control_name' : item.control_name
            }
          );
        }
        this.danhSachControlChuaGan.list = [];
      }
    },

  }
}
</script>

<style scoped>
    a.disabled { color:lightgray !important; }
    a.disabled:hover { color:lightgray !important; }
    .disabled { pointer-events: none; }
</style>
