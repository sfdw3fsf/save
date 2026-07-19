<template src="./index.template.html"></template>
<script>
import Vue from "vue";
import { mapGetters } from "vuex";
import store from "../../store/index";
import { GridPlugin } from "@syncfusion/ej2-vue-grids";
import DataGridCustome from "../DataGrid/index.vue";

Vue.use(GridPlugin);
Vue.prototype.$eventHub = new Vue();
export default {
  components: {
    DataGridCustome
  },

  created() {
    //
    this.$eventHub.$on("detail", (event) => {
      this.isDel_event = true;
      this.$confirm('Bạn có muốn xóa thông tin quyền gạch nợ này không?', 'Thông báo', {
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Không đồng ý'
      }).then(async () => {
          // alert("passed value:" + event);
              console.log(event);
              this.isDel_event = true;
              let res = await this.axios.post('/web-hopdong/ganquyen-gachno/sp_delete_nguoidung_gn', {
                nhom_id: event.nhom_id,
              });
              if(res.data.error_code == "BSS-00000000"){
                // this.$toast.success(res.data.message_detail)
                this.$alert('Xóa thông tin quyền gạch nợ thành công', 'Thông báo')
                this.NAP_DS_NGUOIDUNG_DG(this.ds_nhomnguoi.quyen_gn);
                console.log(res, this.ds_nhomnguoi.quyen_gn);
              }else{
                // this.$toast.warning(res.data.message_detail)
                this.$alert('Xóa thông tin quyền gạch nợ không thành công', 'Thông báo') 
              }
              setTimeout(() => {
                  this.isDel_event = false;
                }, 1000);
        }
      ).catch(() => {
        this.isDel_event = false;
      })
    });

    // 
    // this.$eventHub.$on("sel", (event) => {
    //         // alert("passed value:" + event);
    //         console.log(event);
    //     });
    this.initData();
  },
  data: function () {
    return {
      // template for
      // getColumnTemplateChonSuCoCpn(parent) {
      //   return function () {
      //     return {
      //       template: {
      //         template: `
      //           <div class="check-action">
      //           <input
      //             type="checkbox"
      //             class="check"
      //             v-model="parent.dsIdChon"
      //             :value="data.baohong_id"/>
      //           <span class="name"></span>
      //           </div>
      //         `,
      //         data() {
      //           return {
      //             parent: parent,
      //             data: {}
      //           }
      //         }
      //       }
      //     }
      //   }
      // },
      // common
      dsIdChon: [],
      ma_nd: null,
      don_vi_id: 0,
      vnguoidung_id_selected: -1,
      vnhom_id_nd_luoi: 0,
      vnhom_id: 0,
      isDel_event: false,

      // DANH SÁCH NHÓM NGƯỜI
      ds_nhomnguoi: {
        ma_nguoi_dung: null,
        enabledMaND: true,
        quyen_gn: null,
        hieu_luc_tu: "",
        den_ngay: "",
        chk_KTH: false,
      },
      cbQuyen_GN: [],
      cbNhom_ND: [],
      options: {
        nguoi_dung: [],
        nguoi_dung_temp: [],
        thoi_gian_thiet_lap: [
          // { 
          //   thoigian: '1', 
          //   nhom_id: 1
          // },
          // { 
          //   thoigian: '2',
          //   nhom_id: 88989
          // },
        ],
        ngay_DG: [],
        ngay_DG_temp: [],
        ngay_CG: [],
        ngay_CG_temp: [],
      },
      config: {
        nguoi_dung: [
          {
            headerText: "Mã người dùng",
            allowSorting: true,
            fieldName: "ma_nd",
            width: "120",
          },
          {
            headerText: "Mã nhân viên",
            allowSorting: true,
            fieldName: "ma_nv",
            width: "120",
            // freeze: 'Left'
          },
          {
            headerText: "Mã đơn vị",
            allowSorting: true,
            fieldName: "ma_dv",
            width: "120",
            // freeze: 'Left'
          },
          {
            headerText: "Tên người dùng",
            allowSorting: true,
            fieldName: "ten_nd",
            width: "120",
            // freeze: 'Left'
          },
          {
            headerText: "Nhóm người dùng",
            allowSorting: true,
            fieldName: "ten_nhom",
            width: "120",
            // freeze: 'Left'
          },
          
          
        ],
        thoi_gian_thiet_lap: [
          {
            fieldName: "",
            headerText: "",
            visible: true,
            headerAlign: "Center",
            textAlign: "Center",
            allowFiltering: false,
            width: "50",
            template: this.getColumnTemplateChon(this),
          },
          {
            headerText: "TG thiết lập",
            allowSorting: true,
            fieldName: "thoigian",
            width: "400",
          },
          {
            textAlign: 'center',
            headerText: '',
            allowSorting: true,
            fieldName: '',
            template: function () {
                return {
                    template: Vue.component('columnTemplate', {
                        template: `
                        <row><a class="btn btn-huylydo lh14" @click.prevent="del(event)"
                          style="background-color: #e03636 !important; padding: 5px 15px; color: white">
                          <span class="fa fa-close"></span></a>
                        </row>             
                        `, 
                        data () {
                        return {
                            data: {},
                            
                        }
                        },
                        computed: {
                            parent () {
                                return this.$parent.$parent.$parent
                            }
                        },
                        methods: {
                            // onDownLoad()
                            // {
                            //     this.$parent.$parent.$parent.url= this.data.url;
                            //     console.log('xxx', this, this.$parent.$parent.$parent.url);
                            //     this.$parent.$parent.$parent.$refs.saveFileModal.showModal()
                            // },
                            async del(e){
                                console.log(this.$parent.$parent.$parent.vnhom_id);
                                this.$eventHub.$emit("detail", this.data);
                                // let res = await this.axios.post('/web-hopdong/ganquyen-gachno/sp_delete_nguoidung_gn', {
                                //   nhom_id: this.vnhom_id_nd_luoi,
                                // })
                                // console.log(res);
                            }
                        }
                    })
                }
            }
          },
        ],
        ngay_DG: [
          {
            headerText: "Ngày trong tháng",
            allowSorting: true,
            fieldName: "ngay",
            width: "400",
          },
        ],
        ngay_CG: [
          {
            headerText: "Ngày trong tháng",
            allowSorting: true,
            fieldName: "ngay",
            width: "400",
          },
        ],
      },
      model: {
        nguoi_dung: {

        },
        thoi_gian_thiet_lap: {

        },
        ngay_DG:{

        },
        ngay_CG: {

        }
      }
    };
  },
  computed: {
    getgrcNguoiDungs(){
      // console.log('abc');
      return store.getters.getgrcNguoiDung
    },
    getNgayDG_TAB_QUYEN_NDs: function(){
      let $self = this;
      $self.options.ngay_DG = store.getters.getNgayDG_TAB_QUYEN_ND;
      return store.getters.getNgayDG_TAB_QUYEN_ND;
    },
    getNgayCG_TAB_QUYEN_NDs: function(){
      this.options.ngay_CG = store.getters.getNgayCG_TAB_QUYEN_ND;
      return store.getters.getNgayCG_TAB_QUYEN_ND;
    },
    getgrcThoiGian_NDs: function(){
      this.dsIdChon = [...store.getters.getThoiGian_ND];
      this.options.thoi_gian_thiet_lap = store.getters.getThoiGian_ND;
      return store.getters.getThoiGian_ND;
    }

    //
    // dataCols() {
    //   this.config.thoi_gian_thiet_lap.unshift({
    //     fieldName: '',
    //     headerText: '',
    //     visible: true,
    //     headerAlign: 'Center',
    //     textAlign: 'Center',
    //     allowFiltering: false,
    //     width: '50',
    //     template: this.getColumnTemplateChonSuCoCpn(this)
    //   })
    // },
    
  },
  methods: {
    // async selectingEventTGTL(e){
    //   console.log(e)
    //   if(e.length && !this.isDel_event){
    //     console.log('selectingEventTGTL');
    //     let selected_row = e[e.length-1];
        
    //     let vnhom_id = selected_row['nhom_id']
    //     console.log('vnhom_id: ', vnhom_id)
    //     await store.commit('SET_NHOM_ID_TAB_QUYEN_ND', vnhom_id);
    //     this.vnhom_id_nd_luoi = store.state.vnd_id;
    //     console.log('this.vnhom_id_nd_luoi: ', this.vnhom_id_nd_luoi);

    //     this.model.thoi_gian_thiet_lap = selected_row;
    //     this.LOAD_DS_DG(vnhom_id, 2);
    //     this.NAP_DS_NHOMND_CG(vnhom_id);

    //     let tgtt_isupdated = e.map(x => {return {...x, isupdate: '1'}});
    //     store.commit('SET_DATASOURCE_TGTT_TEMP_TAB_QUYEN_ND', tgtt_isupdated);
    //   }
    // },
    selectedRowChanged(e){
      try {
        console.log('selectedRowChanged', e);
        if (e) {
          // console
          // let row = e[e.length - 1];
          this.model.thoi_gian_thiet_lap = e;
          let vnhom_id = e["nhom_id"];
          store.commit('SET_NHOM_ID_TAB_QUYEN_ND', vnhom_id);
          this.vnhom_id_nd_luoi = store.state.vnd_id;
          console.log('this.vnhom_id_nd_luoi: ', this.vnhom_id_nd_luoi);
          this.LOAD_DS_DG(vnhom_id, 2);
          this.NAP_DS_NHOMND_CG(vnhom_id);
        }
      } catch (error) {
        console.log(error);
      }
      
    },
    getColumnTemplateChon (parent) {
      return function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input 
                                    type="checkbox" 
                                    class="check"
                                    v-model="checkedState"
                                    :value="data"
                                    @change="check($event)"
                                    />
                                <span class="name"></span>
                            </div>
                        `,
            data () {
              return {
                parent: parent,
                data: { }
              }
            },
            computed: {
              checkedState: function () {
                // console.log('xx', parent.dsIdChon);
                return this.data.trangthai == "1" ? true : false; // define the checked state of the template checkbox
              },
            }, methods: {
              check(e){
                // console.log(e);
                let index = e.target._value.index;
                let isChecked = e.target.checked;
                parent.dsIdChon[index]['trangthai'] = isChecked ? "1" : "0";
                parent.dsIdChon[index]['isupdate'] = "1"
                // console.log('eventxxx ', parent.dsIdChon);
                let tgtt_isupdated = parent.dsIdChon.filter(x => x.isupdate == 1);
                // console.log(tgtt_isupdated);
                if(tgtt_isupdated.length){
                  store.commit('SET_DATASOURCE_TGTT_TEMP_TAB_QUYEN_ND', tgtt_isupdated);
                }
              }
            }
          }
        }
      }
    },
    // rowClicked(e){
    //   try {
    //     if(e && e.target){
    //       console.log('rowClickedxxxx: ', e);
    //       let index = e.rowIndex;
    //       let tem = [...this.options.thoi_gian_thiet_lap];
    //       if(e.name == "rowSelected"){
    //         console.log('rowSelected: ')
    //         tem[index]['trangthai'] = "1";
    //         tem[index]['isupdate'] = "1";
    //       }
    //       if(e.name == "rowDeselected"){
    //         console.log('rowDeselected: ')
    //         tem[index]['trangthai'] = "0";
    //         tem[index]['isupdate'] = "1";
    //       }
    //       store.commit('SET_DATASOURCE_TGTT_TEMP_TAB_QUYEN_ND', tem);
    //       console.log(tem);
    //     }

    //   } catch (error) {
    //     console.log(error);
    //   }
    // },
    btnLamMoiND_Click()
    {
        // grvThoiGian_ND.FocusedRowHandle = GridControl.AutoFilterRowHandle;
        this.ds_nhomnguoi.hieu_luc_tu = ''; // dtpTuNgay_ND.EditValue = null;
        this.ds_nhomnguoi.den_ngay = ''; // dtpDenNgay_ND.EditValue = null;
        this.ds_nhomnguoi.chk_KTH = false; // ckbKhongGH_ND.Checked = false;
        // dtpTuNgay_ND.Properties.ReadOnly = false;
        // dtpDenNgay_ND.Properties.ReadOnly = false;
        // btnTaoMoiND.Enabled = true;
        // btnTaoMoiND.Text = "Tạo mới";
        store.commit('SET_NHOM_ID_TAB_QUYEN_ND', 0); // vnd_id = 0;
    },
    async btnGo_Click()
    {
        try
        {
            let ds_dagan = store.getters.getNgayDG_TAB_QUYEN_ND; //grcNgayDaGan.DataSource as DataTable;
            if (ds_dagan && ds_dagan.length)
            {
                if (this.vnhom_id_nd_luoi <= 0)
                {
                    // Message_Box.ShowWarning("Chưa chọn có nhóm");
                    this.$toast.warning("Chưa chọn có nhóm");
                    return;
                }
                if(!this.ds_nhomnguoi.quyen_gn)
                {
                  this.$toast.warning("Chưa chọn quyền gạch nợ")
                    // Message_Box.ShowWarning("Chưa chọn quyền gạch nợ");
                    return;
                }
                if (this.options.ngay_DG_temp.length == 0)
                {
                    Message_Box.ShowWarning("Bạn chưa chọn phiếu để thực hiện");
                    return;
                }
                let ds = [];
                for(let i = 0; i < this.options.ngay_DG_temp.length; i++){
                    // let t = this.options.ngay_DG_temp[i]["chon"];
                    ds.push({
                      ID: this.vnhom_id_nd_luoi,
                      NGAY: this.options.ngay_DG_temp[i]['ngay']
                    })
                }
                // BSS-66793_028
                let result = await this.axios.post('/web-hopdong/ganquyen-gachno/sp_capnhat_nguoidung_gn', {
                  vkieu: 2, //--1: gan, 2: huy gan, 3: xoa ( ở đây truyền 1)
                  vds: JSON.stringify(ds),
                  vnguoidung_id: this.vnguoidung_id_selected,
                  vquyen_gn: this.ds_nhomnguoi.quyen_gn,
                  vnhom_id_nd_luoi: this.vnhom_id_nd_luoi
                });
                if(result.data.error_code == 'BSS-00000000') {
                  // Message_Box.ShowTB("Đã gán quyền gạch nợ thành công");
                  this.$toast.success("Đã gỡ quyền gạch nợ thành công");
                  // BSS-66793_009
                  this.LOAD_DS_DG(store.state.vnd_id, 2);
                  // BSS-66793_020
                  this.NAP_DS_NGUOIDUNG_CG(this.ds_nhomnguoi.quyen_gn);
                }else{
                  this.$toast.warning("Đã gán quyền gạch nợ thất bại !!");
                }
                // Message_Box.ShowTB("Đã gỡ quyền gạch nợ thành công");
                // LOAD_DS_DG(2);
                // NAP_DS_NGUOIDUNG_CG();
            }
            else
            {
              this.$toast.warning("Không có danh sách không thể gán");
                // Message_Box.ShowWarning("Không có danh sách không thể gán");
            }

        }
        catch (ex)
        {
          this.$toast.warning("Gán danh sách có lỗi "+ex)// Message_Box.ShowWarning("Gán danh sách có lỗi " + ex);
          console.log(ex);
        }

    },
    async btnGan_Click()
    {
        try
        {
            if (this.vnguoidung_id_selected == -1)
            {
                // Message_Box.ShowWarning("Chưa chọn người dùng");
                this.$toast.warning("Chưa chọn người dùng");
                return;
            }
            if (this.vnhom_id_nd_luoi <= 0)
            {
                // Message_Box.ShowWarning("Chưa chọn có nhóm");
                this.$toast.warning("Chưa chọn có nhóm");
                return;
            }
            let ds_cgan = store.getters.getNgayCG_TAB_QUYEN_ND; // grcNgayChuaGan.DataSource as DataTable;
            if(ds_cgan && ds_cgan.length){
              if(!this.ds_nhomnguoi.quyen_gn)
              {
                this.$toast.warning("Chưa chọn quyền gạch nợ")
                  // Message_Box.ShowWarning("Chưa chọn quyền gạch nợ");
                  return;
              }
              // ds_cgan.AcceptChanges();
              if (this.options.ngay_CG_temp.length == 0)
              {
                  this.$toast.warning("Bạn chưa chọn phiếu để thực hiện");
                  return;
              }
              // if (this.options.ngay_CG_temp.filter(e => e['chon'] == 's1').length == 0)
              // {
              //     this.$toast.warning("Bạn chưa chọn phiếu để thực hiện");
              //     return;
              // }
              // let xdoc = {};
              // console.log('ngay_CG_temp: ', this.options.ngay_CG_temp );
              // debugger
              let ds = [];
              for(let i = 0; i < this.options.ngay_CG_temp.length; i++){
                // let t = ds_cgan[i]["chon"];
                // if(t != 's1') {
                //   continue;
                // }
                ds.push({
                  ID: this.vnhom_id_nd_luoi,
                  NGAY: this.options.ngay_CG_temp[i]['ngay']
                })
              }
              console.log('ds: ', ds);

              // BSS-66793_028
              let result = await this.axios.post('/web-hopdong/ganquyen-gachno/sp_capnhat_nguoidung_gn', {
                vkieu: 1, //--1: gan, 2: huy gan, 3: xoa ( ở đây truyền 1)
                vds: JSON.stringify(ds),
                vnguoidung_id: this.vnguoidung_id_selected,
                vquyen_gn: this.ds_nhomnguoi.quyen_gn,
                vnhom_id_nd_luoi: this.vnhom_id_nd_luoi
              });
              if(result.data.error_code == 'BSS-00000000') {
                // Message_Box.ShowTB("Đã gán quyền gạch nợ thành công");
                this.$toast.success("Đã gán quyền gạch nợ thành công");
                // BSS-66793_009
                this.LOAD_DS_DG(store.state.vnd_id, 2);
                // BSS-66793_020
                this.NAP_DS_NGUOIDUNG_CG(this.ds_nhomnguoi.quyen_gn);
              }else{
                this.$toast.warning("Đã gán quyền gạch nợ thất bại");
              }
            }
            else
            {
              this.$toast.warning("Không có danh sách không thể gán");
                // Message_Box.ShowWarning("Không có danh sách không thể gán");
            }
          
        }
        catch(ex)
        {
          this.$toast.warning("Gán danh sách có lỗi "+ex)
          console.log(ex);
            // Message_Box.ShowWarning("Gán danh sách có lỗi "+ex);
        }
    },
    formatDate(d){
      try {
        // moment().formatDate
        let daF = d.split('/').reverse().join('-');
        return daF;
      } catch (error) {
        console.log(error);
      }
    },
    async btnTaoMoiND_Click()
    {
        try
        {
          console.log('vnd_id: ', store.state);
          let vnd_id = store.state.vnd_id;
          let nhanvien_id = await this.checkMa_NguoiDung();
          if(this.ds_nhomnguoi.ma_nguoi_dung){
            if(nhanvien_id != '-1'){
              let donvi_id = await this.checkDonVi(nhanvien_id);
              if(donvi_id != '-1'){
                // if (vnd_id == 0){
                    if (this.vnguoidung_id_selected == -1)
                    {
                      this.$toast.warning('Bạn chưa chọn người dùng');
                        // Message_Box.ShowWarning("Bạn chưa chọn người dùng");
                        return;
      
                    }
                    let dt_all = [];// new DataTable();
                    if (!dt_all || !dt_all.length)
                    {
                        // var ds_out =
                        //     grcThoiGian_ND.DataSource as DataTable;
                        let ds_out = store.state.grcThoiGian_ND; // store.getters.getgrcNguoiDung;
                        if (ds_out) dt_all = [...ds_out];
                    }
                    if (dt_all)
                    {
                        if (!this.ds_nhomnguoi.chk_KTH)
                        {
                            if (!this.ds_nhomnguoi.hieu_luc_tu || !this.ds_nhomnguoi.den_ngay)
                            {
                              this.$toast.warning("Bạn phải nhập từ ngày và đến ngày");
                                // Message_Box.ShowWarning("Bạn phải nhập từ ngày và đến ngày");
                              return;
                            }
                        }
                        dt_all = []; // dt_all.Clear();
                        // dr = dt_all.NewRow();
                        let dr = {};
                        dr["NGUOIDUNG_ID"] = this.vnguoidung_id_selected; // vnguoidung_id;
                        dr["KIEU"] = this.ds_nhomnguoi.quyen_gn; // cboQuyenGN.EditValue;
                        this.ds_nhomnguoi.hieu_luc_tu ? dr["TUNGAY"] = this.formatDate(this.ds_nhomnguoi.hieu_luc_tu) : '';                  
                        this.ds_nhomnguoi.den_ngay ? dr["DENNGAY"] = this.formatDate(this.ds_nhomnguoi.den_ngay) : ''; 
                        dr["TRANGTHAI"] = 1;
                        dt_all.push(dr);
                        // BSS-66793_033
                        // let json_list = Newtonsoft.Json.JsonConvert.SerializeObject(dt_all).Replace("T00:00:00", "");
                        // var kq = qldm.GET_VALUE_FUNC("{?DB2}.capnhat_quyen_nd_gn",
                        // "vkieu", 1,
                        // "vds", json_list,
                        // "vthoigian", ckbThoiGian.Checked ? 1 : 0);
                        let res = await this.axios.post('/web-hopdong/ganquyen-gachno/capnhat_quyen_nd_gn', {
                          kieu: 1,
                          ds: JSON.stringify(dt_all),
                          thoigian: this.ds_nhomnguoi.chk_KTH ? 1 : 0
                        });
                        let kq = res.data.data;
                        if (!kq)
                        {
                          this.$toast.warning('Có lỗi khi thêm quyền');
                            // Message_Box.ShowWarning("Có lỗi khi thêm quyền");
                        }
                        else
                        {
                            if (kq.toString().toLocaleLowerCase().startsWith('ok'))
                            {
                              this.$toast.success("Thêm quyền gạch nợ thành công");
                                // Message_Box.ShowTB("Thêm quyền gạch nợ thành công");
                                let vkieu = this.ds_nhomnguoi.quyen_gn;
                                this.NAP_DS_NGUOIDUNG_DG(vkieu);
                                let vct_temp_id = kq.toString().split('-')[1] ? Number(kq.toString().split('-')[1]) : 0;
      
                                // đoạn này qua form chức năng chưa hiểu để làm gì
                                // grvThoiGian_ND.FocusedRowHandle = GridControl.AutoFilterRowHandle;
                                // ChucNang.FocusGridViewRow(grvThoiGian_ND, "NHOM_ID", vct_temp_id);
      
                                // dtpTuNgay_ND.EditValue = null;
                                // dtpDenNgay_ND.EditValue = null;
                                this.ds_nhomnguoi.hieu_luc_tu = '';
                                this.ds_nhomnguoi.den_ngay = '';
                                await this.handleDSNguoiDung(+donvi_id);
                            }
                            else
                            {
                              // Message_Box.ShowWarning("Thông báo: " + kq);
                              this.$toast.warning("Thông báo: " + kq);
                            }
                        }
                    }
                // }
              }else{
                this.$toast.warning("Không tìm thấy đơn vị của người dùng");
              }
            }else{
              this.$toast.warning("Mã người dùng không tồn tại");
            }
          }else{
            this.$toast.warning("Nhập mã người dùng");
          }
        }
        catch (ex)
        {
          this.$toast.warning("Có lỗi khi thêm quyền " + ex);
            // Message_Box.ShowWarning("Có lỗi khi thêm quyền " + ex);
        }
    },
    cboQuyenGN_EditValueChanged: function (value) { 
      try {
        store.commit('SET_QUYENGN_TAB_QUYEN_ND', value)
        if (store.state.vnguoidung_id != -1)
        {
            this.NAP_DS_NGUOIDUNG_CG(value);
            this.NAP_DS_NGUOIDUNG_DG(value);
            this.NAP_DS_KHO_DG();
        }
        // alert(value);
      } catch (e) {
        console.log(e);
      }
    },
    async NAP_DS_KHO_DG(){
      try {
        await store.dispatch('NAP_DS_NGAY_DG_TAB_QUYEN_ND')
      } catch (error) {
        console.log(error);
      }
    },
    async NAP_DS_NGUOIDUNG_CG(vkieu){
      let vnhom_id = this.model.thoi_gian_thiet_lap.nhom_id ? this.model.thoi_gian_thiet_lap.nhom_id : 0;
      // let vkieu = this.ds_nhomnguoi.quyen_gn;
      await store.dispatch('NAP_DS_NGUOIDUNG_CG_TAB_QUYEN_ND', {vnhom_id, vkieu});
      this.options.ngay_CG = [...store.state.grcNgayChuaGan];
    },
    async NAP_DS_NGUOIDUNG_DG(vkieu){
      // let vkieu = this.ds_nhomnguoi.quyen_gn;
      await store.dispatch('NAP_DS_NGUOIDUNG_DG', {vkieu});
      // this.options.thoi_gian_thiet_lap = [...store.state.grcThoiGian_ND];
      // set lại isDel_event
    },
    async checkMa_NguoiDung(){
      try{
        let res_nhanvien_id = await this.axios.post('/web-quantri/huonggiao/lay_nhanvien_id_theo_mand',{
            "ma_nd": this.ds_nhomnguoi.ma_nguoi_dung
          });
          if(res_nhanvien_id.data.data == "-1"){
            this.$toast.warning("Mã người dùng không tồn tại!!");
          }
          return +res_nhanvien_id.data.data
      }catch(error){

      }
    },
    async checkDonVi(nhanvien_id){
      try{
        let res_ma_donvi = await this.axios.post('/web-quantri/huonggiao/lay_donvi_id_theo_nhanvien_id',{
          "nhanvien_id": nhanvien_id
        });
        return res_ma_donvi.data.data;
      }catch(error){
        console.log(error)
      }
    },
    async txtLocMaND_KeyUp() {
      try {
        if(this.ds_nhomnguoi.ma_nguoi_dung){
          let nhanvien_id = await this.checkMa_NguoiDung();
          if(nhanvien_id == -1){
            return;
          }else{
            const donvi_id = await this.checkDonVi(nhanvien_id);
            let isExist = store.state.dsDonVi.find(e => e.id == donvi_id)            
            if(!isExist){
              this.$toast.warning("Không tìm thấy đơn vị của người dùng");
              return;
            }
            else{
              await this.handleDSNguoiDung(+donvi_id);
            }
          }
        }else{
          this.$toast.warning("Nhập mã người dùng");
        }
      } catch (error) {
        console.log(error);
      }
    },
    async handleDSNguoiDung(donvi_id){
      try {
        this.don_vi_id = donvi_id;
        await this.NAP_DS_NGUOIDUNG();
        let dsTemp = store.getters.getgrcNguoiDung;
        // console.log('ds: ', dsTemp);
        const index = dsTemp.findIndex(e => e.ma_nd == this.ds_nhomnguoi.ma_nguoi_dung);
        console.log('index: ', index);
        const item = {...dsTemp[index]};
        // console.log('item: ', item);
        dsTemp.splice(index, 1);
        dsTemp.unshift(item);
        // console.log(dsTemp);
        await store.commit('SET_DATASOURCE_NGUOI_DUNG', dsTemp)
      } catch (error) {
        console.log(error);
      }
    },
    searchNodes(id, dsDonVi) {
        // console.log(args);
        let _text = id;// document.getElementById('txtFilter').value // mask.element.value;
        console.log(_text);
        let predicats = [], _array = [], _filter = [];
        if (_text == "") {
            // alert()
            this.changeDataSource([...dsDonVi]);
        }
        else {
            // let root = dsDonVi[0];
            let dsTem = [...dsDonVi];
            let predicate = new Predicate('donvi_id', 'contains', _text, true);
            console.log(predicate);
            let filteredList = new DataManager(dsTem).executeLocal(new Query().where(predicate));
            // this.dsDVTem = [root,...filteredList];
            for (let j = 0; j < filteredList.length; j++) {
                _filter.push(filteredList[j]["donvi_id"]);
                let filters = this.getFilterItems(filteredList[j], dsDonVi);
                for (let i = 0; i < filters.length; i++) {
                    if (_array.indexOf(filters[i]) == -1 && filters[i] != null) {
                        _array.push(filters[i]);
                        predicats.push(new Predicate('donvi_id', 'equal', filters[i], false));
                    }
                }
            }
            if (predicats.length == 0) {
                this.changeDataSource([]);
            } else {
                let query = new Query().where(new Predicate.or(predicats));
                let newList = new DataManager(dsDonVi).executeLocal(query);
                this.changeDataSource(newList);
                // setTimeout(function () {
                //     listTreeObj.expandAll();
                // }, 400);
            }
            // console.log(filteredList);
            // this.changeDataSource(filteredList);
        }
    },
    async changeDataSource(data) {
        // console.log(data);
        this.createTreeDonVi(data);
        // console.log(tem);
        await store.commit("SET_DONVI_UPDATE", {
          dataSource: tem,
          id: "donvi_id",
          text: "ten_dv",
        });
    },
    createTreeDonVi(dsDonVi) {
        if(dsDonVi){
            let tree = []
            let donviChaArray = dsDonVi.map(item => (item['donvi_cha_id']))
            let donviArray = dsDonVi.map(item => (item['donvi_id']))
            dsDonVi.forEach(item => {
                //check item la parent node
                if (donviChaArray.includes(item['donvi_id'])) {
                    //check donvi_cha_id khong co trong ds donvi_id thi xoa donvi_cha_id
                    if (!donviArray.includes(item['donvi_cha_id'])) {
                        delete item.hoso_cha_id;
                    }
                    tree.push({ ...item, hasChild: true, expanded: item.muc_id < 2 })
                } else {
                    tree.push(item)
                }
            });
            store.commit('SET_DONVI_UPDATE', {
                dataSource: tree, id: 'donvi_id', text: 'ten_dv',
                parentID: 'donvi_cha_id', hasChildren: 'hasChild'
            });
            // this.tt_doituong_hs.fieldsDonVi = store.state.fieldsDonVi;
            // return tree
        }
    },
    //Find the Parent Nodes for corresponding childs
    getFilterItems(fList, list) {
        let nodes = [];
        nodes.push(fList["id"]);
        let query2 = new Query().where('id', 'equal', fList["donvi_cha_id"], false);
        let fList1 = new DataManager(list).executeLocal(query2);
        if (fList1.length != 0) {
            let pNode = this.getFilterItems(fList1[0], list);
            for (let i = 0; i < pNode.length; i++) {
                if (nodes.indexOf(pNode[i]) == -1 && pNode[i] != null)
                    nodes.push(pNode[i]);
            }
            return nodes;
        }
        return nodes;
    },
    async selectedRowTGTL(row) {
      try {
        console.log(row);
        if (row) {
          // this.vnhom_id = row['nhom_id'];
          // let vnhom_id = row['nhom_id']
          // await store.commit('SET_NHOM_ID_TAB_QUYEN_ND', vnhom_id);
          // this.model.thoi_gian_thiet_lap = row;
          // this.LOAD_DS_DG(vnhom_id, 2);
          // this.NAP_DS_NHOMND_CG(vnhom_id);

        }
      } catch (error) {
        console.log(error);
      }
    },
    
    selectingEventCG(e){
      console.log(e)
      this.options.ngay_CG.forEach(item => {
        if(e.map(i => {return i.ngay}).includes(item.ngay)){
          item['chon'] = 's1';
        }
      })
      this.options.ngay_CG_temp = e.map(x => {return {...x, chon: 's1'}});
      console.log(this.options.ngay_CG);
    },
    selectingEventDG(e){
      console.log(e)
      this.options.ngay_DG_temp = e.map(x => {return {...x, chon: 's1'}});
    },
    async selectedRow(row) {
      try {
        // console.log(row, this.$refs.danh_sach_nguoi_dung.grid_RowSelected);
        if (row) {
          this.model.nguoi_dung = row;
          // store.commit('SET_NHOM_ID_TAB_QUYEN_ND', row['nhom_id']);
          await store.commit('SET_NGUOIDUNG_TAB_QUYEN_ND', row['nguoidung_id']);
          this.vnguoidung_id_selected = row['nguoidung_id']
          // if (e.FocusedRowHandle < 0)
          //   {
          //       grcNgayDaGan.DataSource = null;
          //       grcNgayChuaGan.DataSource = null;
          //   }
          //   else
          //   {
            let vkieu = this.ds_nhomnguoi.quyen_gn;
            this.NAP_DS_NGUOIDUNG_DG(vkieu); // thoi gian thiet lap
            this.NAP_DS_NGUOIDUNG_CG(vkieu); // ngay chua gan
               // NAP_DS_KHO_CG();
            this.NAP_DS_KHO_DG();
            // this.LOAD_DS_DG()
            // }
        }else{
          this.options.ngay_DG = [];
          this.options.ngay_CG = [];
        }
      } catch (error) {
        console.log(error);
      }
    },

    
    async LOAD_DS_DG(vnhom_id, kieu_giaodien){
      try {
        let vkieu = this.ds_nhomnguoi.quyen_gn, vkieu_giaodien = kieu_giaodien, vnhom_nd_id = this.model.nguoi_dung['nhom_nd_id'];
        await store.dispatch('LOAD_DS_DG',{
          vnhom_id, vkieu, vkieu_giaodien, vnhom_nd_id
        });
        // store.getters.getgrcNgay_ND_DG
      } catch (error) {
        console.log(error);
      }
    },
    async NAP_DS_NHOMND_CG(vnhom_id){
      try {
        let vkieu = this.ds_nhomnguoi.quyen_gn;
        await store.dispatch('NAP_DS_NGUOIDUNG_CG_TAB_QUYEN_ND', {
          vkieu, vnhom_id
        });
      } catch (error) {
        
      }
    },
    selectedRowNgay_DG(row) {
      try {
        if (row) {
        }
      } catch (error) {
        console.log(error);
      }
    },
    selectedRowNgay_CG(row) {
      try {
        if (row) {
        }
      } catch (error) {
        console.log(error);
      }
    },
    //Hiển thị danh sách lưới người dùng, tab danh sách quyền người dùng BSS-66793_012->BSS-66793_013
    async NAP_DS_NGUOIDUNG() {
      let don_vi_id = this.don_vi_id;
      await store.dispatch('NAP_DS_NGUOIDUNG', {don_vi_id});
    },
    async SP_DS_QUYEN_GN() {
      await store.dispatch("SP_DS_QUYEN_GN");
      // this.cbQuyen_GN = await store.state.cbQuyenGN;
      // console.log("template 1 ", store.state.cbQuyenGN);
      this.cbQuyen_GN = await store.state.cbQuyenGN;
    },
    async initData() {
      this.SP_DS_QUYEN_GN();
      // thong tin nguoi dung
      this.ma_nd = await this.$root.token.getDonViID();
      console.log(this.ma_nd);
      // Thong tin don vi
      this.don_vi_id = await this.$root.token.getDonViID();
      console.log('abc: ', this.don_vi_id);
      await this.NAP_DS_NGUOIDUNG();

      // set value cbx
      this.ds_nhomnguoi.quyen_gn = this.cbQuyen_GN[0]['kieu_id'];
      store.commit('SET_QUYENGN_TAB_QUYEN_ND', this.ds_nhomnguoi.quyen_gn);// set cbQuyen_GN
    },
    refresh() {},
    newItem() {},
  },
  watch: {
    "ds_nhomnguoi.quyen_gn": function (value) {
      let $self = this;
      store.commit('SET_QUYENGN_TAB_QUYEN_ND', value);// set cbQuyen_GN
      $self.cboQuyenGN_EditValueChanged(value);
      // alert(value);
    },
  },
};
</script>

<style>
</style>