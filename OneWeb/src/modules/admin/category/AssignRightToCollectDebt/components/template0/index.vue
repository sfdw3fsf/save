<template src="./index.template.html"></template>
<script>
////import { Tooltip } from "@syncfusion/ej2-popups";
import store from "../../store/index";
import Vue from "vue";
import { GridPlugin } from "@syncfusion/ej2-vue-grids";
import * as moment from "moment";
import DataGridCustome from "../DataGrid/index.vue";

Vue.use(GridPlugin);
Vue.prototype.$eventHub = new Vue();
export default {
  components: {
    DataGridCustome,
  },
  watch: {
    "ds_nhomnguoi.nhom_nd": function (value) {
      // alert(e)
      store.commit("SET_NHOM_ND", value);
      var $self = this;
      $self.cboNhomND_EditValueChanged(value);
    },
    "ds_nhomnguoi.quyen_gn": (value) => {
      store.commit("SET_QUYENGN_TO_STATE", value);
      var $self = this;
    },
  },
  created() {
    this.$eventHub.$on("detail", (event) => {
      this.isDel_event = true;
      this.$confirm(
        "Bạn có muốn xóa thông tin quyền gạch nợ này không?",
        "Thông báo",
        {
          confirmButtonText: "Đồng ý",
          cancelButtonText: "Không đồng ý",
        }
      )
        .then(async () => {
          // alert("passed value:" + event);
          console.log(event);
          this.isDel_event = true;
          let res = await this.axios.post(
            "/web-hopdong/ganquyen-gachno/sp_delete_nhomnd_gn",
            {
              nhom_id: event.nhom_id,
            }
          );
          if (res.data.error_code == "BSS-00000000") {
            // this.$toast.success(res.data.message_detail)
            this.$alert("Xóa thông tin quyền gạch nợ thành công", "Thông báo");

            this.NAP_DS_NHOMND_DG(this.ds_nhomnguoi.nhom_nd);
            console.log(res, this.ds_nhomnguoi.quyen_gn);
          } else {
            this.$alert(
              "Xóa thông tin quyền gạch nợ không thành công",
              "Thông báo"
            );
            // this.$toast.warning(res.data.message_detail)
          }
          setTimeout(() => {
            this.isDel_event = false;
          }, 1000);
        })
        .catch(() => {
          this.isDel_event = false;
        });
    });

    this.initData();
  },
  data: function () {
    return {
      dsIdChon: [],
      rowIndex: -1,
      isDel_event: false,
      // DANH SÁCH NHÓM NGƯỜI
      ds_nhomnguoi: {
        quyen_gn: null,
        nhom_nd: null,
        hieu_luc_tu: "",
        den_ngay: "",
        chk_KTH: false,
      },
      cbQuyen_GN: [],
      cbNhom_ND: [],
      options: {
        thoi_gian_thiet_lap: [],
        ngay_DG: [],
        ngay_DG_temp: [],
        ngay_CG: [],
        ngay_CG_temp: [],
      },
      config: {
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
            textAlign: "center",
            headerText: "",
            allowSorting: true,
            fieldName: "",
            template: function () {
              return {
                template: Vue.component("columnTemplate", {
                  template: `
                        <row><a class="btn btn-huylydo lh14" @click="del(event)"
                          style="background-color: #e03636 !important; padding: 5px 15px; color: white">
                          <span class="fa fa-close"></span></a>
                        </row>
                        `,
                  data() {
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
                    // onDownLoad()
                    // {
                    //     this.$parent.$parent.$parent.url= this.data.url;
                    //     console.log('xxx', this, this.$parent.$parent.$parent.url);
                    //     this.$parent.$parent.$parent.$refs.saveFileModal.showModal()
                    // },
                    async del(e) {
                      console.log(this.$parent.$parent.$parent.vnhom_id);
                      this.$eventHub.$emit("detail", this.data);
                      // let res = await this.axios.post('/web-hopdong/ganquyen-gachno/sp_delete_nguoidung_gn', {
                      //   nhom_id: this.vnhom_id_nd_luoi,
                      // })
                      // console.log(res);
                    },
                  },
                }),
              };
            },
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
        nguoi_dung: {},
        thoi_gian_thiet_lap: {},
        ngay_DG: {},
        ngay_CG: {},
      },
    };
  },
  computed: {
    getgrcNgay_ND_CGs: function () {
      console.log("danh sách ngày chưa gán ", store.getters.getgrcNgay_ND_CG);
      // var $self = this;
      // $self.options.ngay_CG = [...store.getters.getgrcNgay_ND_CG]
      this.options.ngay_CG = store.getters.getgrcNgay_ND_CG;
      return store.getters.getgrcNgay_ND_CG;
    },
    getgrcNgay_ND_DGs: function () {
      this.options.ngay_DG = store.getters.getgrcNgay_ND_DG;
      return store.getters.getgrcNgay_ND_DG;
    },
    getgrcThoiGians: function () {
      this.dsIdChon = [...store.getters.getThoiGian];
      this.options.thoi_gian_thiet_lap = [...store.getters.getThoiGian];
      return store.getters.getThoiGian;
    },

  },
  methods: {
    // test(){
    //   console.log(this.dsIdChon);
    // },
    selectedRowChanged(e){
      console.log('selectedRowChanged', e);
      if (e) {
        // console
        // let row = e[e.length - 1];
        this.model.thoi_gian_thiet_lap = e;
        let vnhom_id = e["nhom_id"];
        store.commit("SET_NHOM_ID", vnhom_id);
        this.LOAD_DS_DG(vnhom_id, 1);
        this.NAP_DS_NHOMND_CG(this.ds_nhomnguoi.nhom_nd);
        // this.options.ngay_CG_temp = e.map(x => {return {...x, chon: 's1'}});
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
                let index = e.target._value.index;
                let isChecked = e.target.checked;
                parent.dsIdChon[index]['trangthai'] = isChecked ? "1" : "0";
                parent.dsIdChon[index]['isupdate'] = "1"
                console.log('eventxxx ', parent.dsIdChon);
                let tgtt_isupdated = parent.dsIdChon.filter(x => x.isupdate == 1);
                console.log('tgtt_isupdated', tgtt_isupdated);
                if(tgtt_isupdated.length){
                  store.commit('SET_DATASOURCE_TGTT_TEMP', tgtt_isupdated);
                }
              }
            }
          }
        }
      }
    },
    rowSelected(args) {
      console.log('rowSelected: ', args.rowIndex);
    },

    btnClear_Click() {
      // grvThoiGian.FocusedRowHandle = GridControl.AutoFilterRowHandle;
      this.ds_nhomnguoi.hieu_luc_tu = ""; // dtpNgayTu.EditValue = null;
      this.ds_nhomnguoi.den_ngay = ""; // dtpDenNgay.EditValue = null;
      this.ds_nhomnguoi.chk_KTH = false; // ckbThoiGian.Checked = false;
      // dtpNgayTu.Properties.ReadOnly = false;
      // dtpDenNgay.Properties.ReadOnly = false;
      // btnThemFile.Enabled = true;
      // btnThemFile.Text = "Tạo mới";
      store.commit("SET_NHOM_ID", 0); // vnhom_id = 0;
    },
    selectingEventTGTL(e) {
      console.log("selectingEventTGTL: ", e);
      if (e.length) {
        // console
        let row = e[e.length - 1];
        this.model.thoi_gian_thiet_lap = row;
        let vnhom_id = row["nhom_id"];
        store.commit("SET_NHOM_ID", vnhom_id);
        this.LOAD_DS_DG(vnhom_id, 1);
        this.NAP_DS_NHOMND_CG(this.ds_nhomnguoi.nhom_nd);

        //
        // let tgtt_isupdated = e.map(x => {return {...x, isupdate: '1'}});
        // store.commit('SET_DATASOURCE_TGTT_TEMP', tgtt_isupdated);
        // this.options.ngay_CG_temp = e.map(x => {return {...x, chon: 's1'}});
      }
    },
    selectingEventCG(e) {
      console.log(e);
      this.options.ngay_CG_temp = e.map((x) => {
        return { ...x, chon: "s1" };
      });
    },
    selectingEventDG(e) {
      console.log(e);
      this.options.ngay_DG_temp = e.map((x) => {
        return { ...x, chon: "s1" };
      });
    },
    async btnDelND_Click() {
      try {
        console.log("btnDelND_Click");
        let ds_cgan = store.getters.getgrcNgay_ND_CG; // var ds_cgan = grcNgay_ND_DG.DataSource as DataTable;store.getters.getgrcNgay_ND_CG
        console.log();
        if (ds_cgan && ds_cgan.length) {
          if (store.state.vnhom_id == 0) {
            this.$toast.warning("Không lấy được thông tin nhóm ID"); //Message_Box.ShowWarning("Không lấy được thông tin nhóm ID");
            return;
          }
          if (!this.ds_nhomnguoi.quyen_gn) {
            this.$toast.warning("Chưa chọn quyền gạch nợ");
            // Message_Box.ShowWarning("Chưa chọn quyền gạch nợ");
            return;
          }
          if (
            this.options.ngay_DG_temp.filter((e) => e["chon"] == "s1").length ==
            0
          ) {
            this.$toast.warning("Bạn chưa chọn phiếu để thực hiện");
            return;
          }
          // let xdoc = {};
          let ds = [];
          for (let i = 0; i < this.options.ngay_DG_temp.length; i++) {
            // let t = this.options.ngay_CG_temp[i]["chon"];
            ds.push({
              ID: store.state.vnhom_id,
              NGAY: this.options.ngay_DG_temp[i]["ngay"],
            });
          }

          // BSS-66793_028
          let result = await this.axios.post(
            "/web-hopdong/ganquyen-gachno/sp_capnhat_nhomnd_gn",
            {
              vkieu: 2, //--1: gan, 2: huy gan, 3: xoa ( ở đây truyền 1)
              vds: JSON.stringify(ds),
              vnhom_nd_id: this.ds_nhomnguoi.nhom_nd,
              vdsquyen_gn: this.ds_nhomnguoi.quyen_gn,
              vnhom_id_luoi: store.state.vnhom_id,
            }
          );
          if (result.data.error_code == "BSS-00000000") {
            // Message_Box.ShowTB("Đã gán quyền gạch nợ thành công");
            this.$toast.success("Đã gỡ quyền gạch nợ thành công");
            // BSS-66793_009
            this.LOAD_DS_DG(store.state.vnhom_id, 1);
            // BSS-66793_020
            this.NAP_DS_NHOMND_CG(this.ds_nhomnguoi.nhom_nd);
          } else {
            this.$toast.warning("Đã gỡ quyền gạch nợ thất bại");
          }
        } else {
          this.$toast.warning("Không có danh sách không thể gán"); //Message_Box.ShowWarning("Không có danh sách không thể gán");
        }
      } catch (ex) {
        this.$toast.warning("Gán danh sách có lỗi " + ex); //Message_Box.ShowWarning("Gán danh sách có lỗi " + ex);
        console.log(ex);
      }
    },
    async btnInsND_Click() {
      try {
        console.log("btnInsND_Click");
        if (store.state.vnhom_id == 0) {
          this.$toast.warning("Không lấy được thông tin nhóm ID"); //Message_Box.ShowWarning("Không lấy được thông tin nhóm ID");
          return;
        }
        let ds_cgan = store.getters.getgrcNgay_ND_CG; // grcNgay_ND_CG.DataSource as DataTable;
        if (ds_cgan && ds_cgan.length) {
          if (!this.ds_nhomnguoi.quyen_gn) {
            this.$toast.warning("Chưa chọn quyền gạch nợ");
            // Message_Box.ShowWarning("Chưa chọn quyền gạch nợ");
            return;
          }
          if (
            this.options.ngay_CG_temp.filter((e) => e["chon"] == "s1").length ==
            0
          ) {
            this.$toast.warning("Bạn chưa chọn phiếu để thực hiện");
            return;
          }
          // let xdoc = {};
          let ds = [];
          for (let i = 0; i < this.options.ngay_CG_temp.length; i++) {
            // let t = this.options.ngay_CG_temp[i]["chon"];
            ds.push({
              ID: store.state.vnhom_id,
              NGAY: this.options.ngay_CG_temp[i]["ngay"],
            });
          }

          // BSS-66793_031
          let result = await this.axios.post(
            "/web-hopdong/ganquyen-gachno/sp_capnhat_nhomnd_gn",
            {
              vkieu: 1, //--1: gan, 2: huy gan, 3: xoa ( ở đây truyền 1)
              vds: JSON.stringify(ds),
              vnhom_nd_id: this.ds_nhomnguoi.nhom_nd,
              vdsquyen_gn: this.ds_nhomnguoi.quyen_gn,
              vnhom_id_luoi: store.state.vnhom_id,
            }
          );
          if (result.data.error_code == "BSS-00000000") {
            // Message_Box.ShowTB("Đã gán quyền gạch nợ thành công");
            this.$toast.success("Đã gán quyền gạch nợ thành công");
            // BSS-66793_009
            this.LOAD_DS_DG(store.state.vnhom_id, 1);
            // BSS-66793_020
            this.NAP_DS_NHOMND_CG(this.ds_nhomnguoi.nhom_nd);
          } else {
            this.$toast.warning("Đã gán quyền gạch nợ thất bại");
          }
        } else {
          this.$toast.warning("Không có danh sách không thể gán");
        }
      } catch (ex) {
        this.$toast.warning("Gán danh sách có lỗi " + ex); //Message_Box.ShowWarning("Gán danh sách có lỗi " + ex);
      }
    },
    ckbThoiGian_CheckedChanged(e) {
      alert(e);
      // ds_nhomnguoi.hieu_luc_tu = '';
      // ds_nhomnguoi.den_ngay = '';
      // this.ckbThoiGian = ckbThoiGian.Checked;
    },
    grvThoiGian_FocusedRowChanged() {
      try {
        // vnhom_id = 0;
        // if(e == null)
        // {
        //   grvThoiGian.FocusedRowChanged -= grvThoiGian_FocusedRowChanged;
        //   grvThoiGian.FocusedRowHandle = 0;
        //   grvThoiGian.FocusedRowChanged += grvThoiGian_FocusedRowChanged;
        // }

        if (grvThoiGian.FocusedRowHandle >= 0) {
          // vnhom_id = Number( grvThoiGian.GetFocusedRowCellValue("NHOM_ID"));
          //   grvNgay_ND_DG.FocusedRowHandle = GridControl.AutoFilterRowHandle;
          //   grvNgay_ND_DG.ClearColumnsFilter();
          //   grcNgay_ND_DG.DataSource = qldm.GET_DATA_SQL(@"select 's0' chon,ngay
          //                 from {?DB1}.nhomnd_gn where nhom_nd_id=:vnhomnd_id  and nhom_id=:vnhom_id and ngay<>'00'
          //                 and kieu=:vkieu order by ngay asc", "vnhomnd_id", vnhom_nd_id, "vnhom_id",grvThoiGian.GetFocusedRowCellValue("NHOM_ID"),"vkieu", cboDs_quyen.EditValue);
          //   NAP_DS_NHOMND_CG();
        } else {
          // grcNgay_ND_DG.DataSource = null;
        }
        btnThemFile.Enabled = false;
      } catch (e) {
        console.log(e);
      }
    },

    async NAP_DS_NHOMND_CG() {
      try {
        let vkieu = this.ds_nhomnguoi.quyen_gn,
          vnhom_nd_id = this.ds_nhomnguoi.nhom_nd;
        await store.dispatch("NAP_DS_NHOMND_CG", {
          vnhom_nd_id,
          vnhom_id,
          vkieu,
        });
      } catch (error) {
        console.log(error);
      }
    },
    async LOAD_DS_DG(vnhom_id, kieu_giaodien) {
      try {
        let vkieu = this.ds_nhomnguoi.quyen_gn,
          vnhom_nd_id = this.ds_nhomnguoi.nhom_nd,
          vkieu_giaodien = kieu_giaodien;
        await store.dispatch("LOAD_DS_DG", {
          vnhom_nd_id,
          vnhom_id,
          vkieu,
          vkieu_giaodien,
        });
      } catch (ex) {
        this.$toast.warning("Nạp danh sách có lỗi " + ex);
      }
    },
    cboNhomND_EditValueChanged(nhom_nd) {
      // alert(nhom_nd);
      this.NAP_DS_NHOMND_DG(nhom_nd);
      this.NAP_DS_NHOMND_CG(nhom_nd);
    },
    cboQuyenGN_EditValueChanged() {
      // alert(nhom_nd);
      this.NAP_DS_NHOMND_DG(this.ds_nhomnguoi.nhom_nd);
      this.NAP_DS_NHOMND_CG(this.ds_nhomnguoi.nhom_nd);
    },
    // BSS-66793_006 -> BSS-66793_007
    async NAP_DS_NHOMND_DG(nhom_nd) {
      let vnhomnd_id = nhom_nd;
      let vkieu = this.ds_nhomnguoi.quyen_gn ? this.ds_nhomnguoi.quyen_gn : 0;
      console.log({ vnhomnd_id, vkieu });
      await store.dispatch("NAP_DS_NHOMND_DG", { vnhomnd_id, vkieu });
      this.options.thoi_gian_thiet_lap = [...store.state.grcThoiGian];
    },
    // BSS-66793_010 -> BSS-66793_011
    async NAP_DS_NHOMND_CG(nhom_nd) {
      let vnhomnd_id = nhom_nd;
      let vkieu = this.ds_nhomnguoi.quyen_gn ? this.ds_nhomnguoi.quyen_gn : 0;
      let vnhom_id = this.model.thoi_gian_thiet_lap["nhom_id"]
        ? this.model.thoi_gian_thiet_lap["nhom_id"]
        : 0;
      await store.dispatch("NAP_DS_NHOMND_CG", { vnhomnd_id, vkieu, vnhom_id });
      this.options.ngay_CG = [...store.state.grcNgay_ND_CG];
    },
    // BSS-66793_001 & BSS-66793_002
    async SP_DS_QUYEN_GN() {
      await store.dispatch("SP_DS_QUYEN_GN");
      this.cbQuyen_GN = await store.state.cbQuyenGN;
      console.log("template 0 ", this.cbQuyenGN, store.state.cbQuyenGN);
    },
    async SP_DS_NHOM_ND() {
      try {
        let res = await this.axios.get(
          "/web-hopdong/ganquyen-gachno/sp_ds_nhom_nd"
        );
        if (res.data.error_code == "BSS-00000000") {
          this.cbNhom_ND = res.data.data;
        } else {
          this.$toast.error(res.data.message_detail);
        }
      } catch (error) {
        console.log(error);
      }
    },
    async initData() {
      await this.SP_DS_QUYEN_GN();
      await this.SP_DS_NHOM_ND();

      // set value combobox
      this.ds_nhomnguoi.quyen_gn = this.cbQuyen_GN[0]["kieu_id"]; // cb quyền gạch nợ
      this.ds_nhomnguoi.nhom_nd = this.cbNhom_ND[0]["nhom_nd_id"]; // cb ds nhóm nd
    },
    refresh() {},
    formatDate(d) {
      try {
        // moment().formatDate
        let daF = d.split("/").reverse().join("-");
        return daF;
      } catch (error) {
        console.log(error);
      }
    },
    async btnThemFile_Click() {
      try {
        console.log(
          "ds_nhomnguoi.hieu_luc_tu: ",
          this.formatDate(this.ds_nhomnguoi.hieu_luc_tu),
          typeof this.ds_nhomnguoi.hieu_luc_tu
        );
        // let vnhom_id = store.state.vnhom_id;
        // if (vnhom_id == 0){
        let dt_all = []; // new DataTable();
        let ds_out = store.state.grcThoiGian;
        if (ds_out) dt_all = [...ds_out];

        if (dt_all) {
          if (!this.ds_nhomnguoi.chk_KTH) {
            if (!this.ds_nhomnguoi.hieu_luc_tu || !this.ds_nhomnguoi.den_ngay) {
              this.$toast.warning("Bạn phải nhập từ ngày và đến ngày");
              // Message_Box.ShowWarning("Bạn phải nhập từ ngày và đến ngày");
              return;
            }
          }
          dt_all = []; // dt_all.Clear();
          // dr = dt_all.NewRow();
          let dr = {};
          dr["NHOM_ND_ID"] = store.state.vnhom_nd_id;
          dr["KIEU"] = this.ds_nhomnguoi.quyen_gn.toString(); // cboQuyenGN.EditValue;
          this.ds_nhomnguoi.hieu_luc_tu
            ? (dr["TUNGAY"] = this.formatDate(this.ds_nhomnguoi.hieu_luc_tu))
            : "";
          this.ds_nhomnguoi.den_ngay
            ? (dr["DENNGAY"] = this.formatDate(this.ds_nhomnguoi.den_ngay))
            : "";
          dr["TRANGTHAI"] = 1;
          dt_all.push(dr);
          // BSS-66793_033
          // let json_list = Newtonsoft.Json.JsonConvert.SerializeObject(dt_all).Replace("T00:00:00", "");
          // var kq = qldm.GET_VALUE_FUNC("{?DB2}.capnhat_quyen_nd_gn",
          // "vkieu", 1,
          // "vds", json_list,
          // "vthoigian", ckbThoiGian.Checked ? 1 : 0);
          console.log(JSON.stringify(dt_all));
          // let tem = JSON.stringify(dt_all).replace()
          let res = await this.axios.post(
            "/web-hopdong/ganquyen-gachno/capnhat_quyen_gn",
            {
              vkieu: 1,
              vds: JSON.stringify(dt_all),
              vthoigian: this.ds_nhomnguoi.chk_KTH ? 1 : 0,
            }
          );
          let kq = res.data.data;
          if (!kq) {
            this.$toast.warning("Có lỗi khi thêm quyền");
            // Message_Box.ShowWarning("Có lỗi khi thêm quyền");
          } else {
            if (kq.toString().toLocaleLowerCase().startsWith("ok")) {
              this.$toast.success("Thêm quyền gạch nợ thành công");
              // Message_Box.ShowTB("Thêm quyền gạch nợ thành công");
              this.NAP_DS_NHOMND_DG(this.ds_nhomnguoi.nhom_nd);
              let vct_temp_id = kq.toString().split("-")[1]
                ? Number(kq.toString().split("-")[1])
                : 0;

              // đoạn này qua form chức năng chưa hiểu để làm gì
              // grvThoiGian_ND.FocusedRowHandle = GridControl.AutoFilterRowHandle;
              // ChucNang.FocusGridViewRow(grvThoiGian_ND, "NHOM_ID", vct_temp_id);

              // dtpTuNgay_ND.EditValue = null;
              // dtpDenNgay_ND.EditValue = null;
              this.ds_nhomnguoi.hieu_luc_tu = "";
              this.ds_nhomnguoi.den_ngay = "";
            } else {
              // Message_Box.ShowWarning("Thông báo: " + kq);
              this.$toast.warning("Thông báo: " + kq);
            }
          }
        }
      } catch (ex) {
        // }
        this.$toast.warning("Có lỗi khi thêm quyền " + ex);
        console.log(ex);
        // Message_Box.ShowWarning("Có lỗi khi thêm quyền " + ex);
      }
    },
  },
};
</script>
