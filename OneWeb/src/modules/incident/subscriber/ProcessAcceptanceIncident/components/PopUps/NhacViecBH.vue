<template>
  <ejs-dialog
    :enableResize="true"
    :allowDragging="true"
    :visible="false"
    ref="dialogNhacViecBH"
    :position="{ X: 'center', Y: 'top' }"
    :close="closeDialog"
    :header="'Nhắn tin SMS'"
    showCloseIcon="true"
    width="80%"
    target="#app .main-wrapper"
  >
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a @click.prevent="tsbtnSMS_Click">
            <span class="icon fa fa-comments"></span> Gửi SMS
          </a>
        </li>
        <li>
          <a @click="closeDialog">
            <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát
          </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="legend-title">{{ labelFunctionTitle }}</div>
        <div class="grid-stack-box">
          <div class="box-col box-form" id="boxLeft1">
            <section class="box-head">
              <div class="legend-title">
                <div class="pull-left">
                  <span class="icon fa fa-angle-up"></span>Thông tin xử lý
                </div>
                <div class="clearfix"></div>
              </div>
              <div class="row">
                <span>{{ strLoadDB }}</span>
                <ejs-treeview
                  id="treeview"
                  ref="tree"
                  cssClass="depts-tree-view"
                  :fields="fields"
                  @nodeSelected="selectDonViFocusedNodeChange"
                  :allowMultiSelection="true"
                  :selectedNodes="selectedNodes"
                  :expandedNodes="expandedNodes"
                />
              </div>
            </section>
            <DataGrid
              v-bind:columns="columns_NV"
              v-bind:dataSource="ds_nhanvien"
              :showFilter="true"
              :allowResizing="false"
              :change="ds_nhanvien"
              @selectedRowChanged="selectedRowNhanVien"
              @rowDoubleClicked="rowNhanVienDoubleClicked"
              panelDataHeight="200"
              style="margin-top: 22px"
            >
            </DataGrid>
          </div>
          <div class="box-col box-form" id="boxRight1">
            <div class="legend-title">Danh sách nhân viên</div>
            <div class="info-row">
              <div class="key w100">Số ĐT</div>
              <div class="value">
                <input
                  type="text"
                  class="form-control highlight"
                  v-model="txtSoDT"
                />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">ND nhắc mẫu</div>
              <div class="value">
                <ejs-dropdownlist
                  :allowFiltering="true"
                  :dataSource="ds_sms_temp"
                  :fields="{ text: 'sms', value: 'id' }"
                  v-model="smsTempId"
                  :change="smsTemp_Changed"
                  :filtering="onFilteringSmsTemp"
                ></ejs-dropdownlist>
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">{{ txtNoiDung.length }}/500</div>
              <div class="value">
                <textarea
                  cols="4"
                  rows="4"
                  class="form-control"
                  maxlength="500"
                  v-model="txtNoiDung"
                >
                </textarea>
              </div>
            </div>
            <div class="article">
              <span class="icon text-main f20 -ap icon-info" /><span
                class="fw6 red font-italic"
                >Lưu ý</span
              >
              <div class="text-desc">
                <i class="red"
                  >- Có thể gửi tin nhắn tới nhiều số cùng lúc, để gửi tin nhập
                  các số điện thoại phân cách nhau bới dấu chấm phẩy ";"</i
                >
                <p class="red font-italic">
                  - Nháy đúp vào một dòng trên danh bạ để thêm số điện thoại vào
                  dánh sách cần gửi
                </p>
              </div>
            </div>
            <div class="mt-2">
              <div class="legend-title">Danh sách tin nhắn đã gửi</div>
              <DataGrid
                v-bind:columns="columns_tinNhan"
                v-bind:dataSource="dgvTienTrinhSMS"
                panelDataHeight="200"
                :allowResizing="false"
              >
              </DataGrid>
            </div>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>
<script>
import API from "../../API";
import { Query, Predicate } from "@syncfusion/ej2-data";
import TreeView from "@/components/Shared/TreeView/TreeView.vue";
export default {
  components: { TreeView },
  props: [
    "baohong_id",
    "kieugoi_id",
    "phieu_id",
    "thuebao_id",
    "ma_tb",
    "kieu_nhac",
  ],
  data() {
    return {
      labelFunctionTitle: "NHẮN TIN SMS",
      ds_nhanvien: [],
      txtNoiDung: "",
      txtSoDT: "",
      columns_NV: [
        {
          fieldName: "ma_nv",
          headerText: "Mã NV",
        },
        {
          fieldName: "ten_nv",
          headerText: "Tên NV",
        },
        {
          fieldName: "so_dt",
          headerText: "SĐT",
        },
        {
          fieldName: "chucdanh",
          headerText: "Chức danh",
        },
        {
          fieldName: "vitri",
          headerText: "Vị trí",
        },
        {
          fieldName: "diachi_nv",
          headerText: "Địa chỉ",
        },
      ],
      columns_tinNhan: [
        {
          fieldName: "nguoi_cn",
          headerText: "Người Gửi",
        },
        {
          fieldName: "ngay_cn",
          headerText: "Ngày",
        },
        {
          fieldName: "so_dt",
          headerText: "Số điện thoại",
        },
        {
          fieldName: "nv_nhan",
          headerText: "Người Nhận",
        },
        {
          fieldName: "noidung",
          headerText: "Nội dung",
        },
      ],
      ds_sms_temp: [],
      smsTempId: -1,
      vnoidung: "",
      dgvTienTrinhSMS: [],
      THOIHAN_NHACVIEC_TTKD: -1,
      tvDonVi: [],
      isLoadDB: false,
      strLoadDB: "",
      fields: {},
      selectedNodes: [0, 1],
      expandedNodes: [0],
      donviIDCHA: 0,
      donviId: 0,
      dicNV: [],
    };
  },
  methods: {
    onFilteringSmsTemp() {
      var query = new Query();
      query =
        e.text !== "" ? query.where("sms", "contains", e.text, true) : query;
      e.updateData(this.ds_sms_temp, query);
    },
    refresh() {
      this.strLoadDB = "";
      this.txtNoiDung = "";
      this.vnoidung = "";
      this.txtSoDT = "";
      this.tvDonVi = [];
      this.ds_nhanvien = [];
      this.ds_sms_temp = [];
      this.dgvTienTrinhSMS = [];
      this.dicNV = [];
    },
    smsTemp_Changed() {
      let smsTemp = this.ds_sms_temp.find((x) => x.id == this.smsTempId);
      this.txtNoiDung = (smsTemp ? smsTemp.sms : "") + this.vnoidung;
    },
    async showDialog() {
      await this.refresh();
      this.loadTreeView();
      this.init();
      this.$refs.dialogNhacViecBH.show();
    },
    closeDialog() {
      this.$refs.dialogNhacViecBH.hide();
    },
    async selectDonViFocusedNodeChange(node) {
      try {
        this.donviId = Number(node.nodeData.id);
        await this.loadListView(this.donviId);
      } catch (e) {
        this.$toast.console.error("Có lỗi xảy ra: " + e);
      }
    },
    async loadListView(p_donvicha_id) {
      this.loading(true);
      try {
        this.ds_nhanvien = [];
        let params = {
          vdonvi_id: p_donvicha_id,
          vloaidv_nhan_id: 0,
          vhuonggiao_id: 0,
        };
        let response = await API.LAY_NV_GIAO_PHKH_V2(params);
        if (
          response &&
          response.data &&
          response.data.error_code === "BSS-00000000" &&
          response.data.data
        ) {
          this.ds_nhanvien = response.data.data;
        }
      } catch (e) {
        console.log(e);
      }
      this.$nextTick(() => {
        document.getElementById("treeview_active").classList.add("e-active");
      });
      this.loading(false);
    },
    loadTreeView() {
      this.RefreshTreeview();
    },
    async RefreshTreeview() {
      this.tvDonVi = [];
      this.isLoadDB = true;
      this.strLoadDB = "Đang tải dữ liệu ... 0 %";
      await this.MainReLoadDatabase();
      this.handleTreeData();
    },
    async handleTreeData() {
      await this.LoadTreeview(this.donviIDCHA);
      let donViTree = await this.createTreeDonVi(this.tvDonVi);
      this.fields = {
        dataSource: donViTree,
        id: "donvi_id",
        text: "ten_dv",
        parentID: "donvi_cha_id",
        hasChildren: "hasChild",
        expanded: "expanded",
        selected: "isSelected",
      };
      this.loadListView(0);
      this.strLoadDB = "";
    },
    createTreeDonVi(dsDonVi) {
      let tree = [];
      let donviChaArray = dsDonVi.map((item) => {
        return item["donvi_cha_id"];
      });
      let donviArray = dsDonVi.map((item) => item["donvi_id"]);
      dsDonVi.forEach((item) => {
        if (donviChaArray.includes(item["donvi_id"])) {
          if (!donviArray.includes(item["donvi_cha_id"])) {
            delete item.donvi_cha_id;
          }
          tree.push({ ...item, hasChild: true, expanded: false });
        } else {
          tree.push(item);
        }
      });
      let departmentUserId = Number.parseInt(this.$root.token.getDonViID());
      const usrDonViIdx = tree.findIndex(
        (item) => item.donvi_id === departmentUserId
      );
      if (usrDonViIdx !== -1) {
        tree[usrDonViIdx].isSelected = true;
        tree[usrDonViIdx].expanded = true;
      }
      return tree;
    },
    async LoadTreeview(p_donvicha_id) {
      try {
        let params = {
          p_donvicha_id,
        };
        let response = await API.LoadTreeview(params);
        if (
          response &&
          response.data &&
          response.data.error_code === "BSS-00000000" &&
          response.data.data
        ) {
          this.tvDonVi = response.data.data;
        }
      } catch (e) {
        console.log(e);
      }
    },
    async MainReLoadDatabase() {
      try {
        this.strLoadDB = "Loading ... dữ liệu đơn vị";
        await this.displayTreview();
        this.strLoadDB = "Loading ... loại đơn vị";
        this.isLoadDB = false;
      } catch (error) {
        this.$toast.error("Lỗi khi lấy dữ liệu đơn vị !");
      }
    },
    async displayTreview() {
      try {
        let user_map = await this.getUserMap();
        if (user_map == "-1") user_map = "";
        if (user_map) {
          await this.getDvCha(user_map);
        } else {
          this.donviIDCHA = Number.parseInt(this.$root.token.getDonViID());
        }
      } catch (error) {}
    },
    async getDvCha(v_ma_nd) {
      try {
        let params = {
          v_ma_nd,
        };
        let response = await API.SP_LAY_DONVI_ID_THEO_MA_ND(params);
        if (
          response &&
          response.data &&
          response.data.error_code === "BSS-00000000" &&
          response.data.data
        ) {
          this.donviIDCHA = response.data.data;
        }
      } catch (e) {
        console.log(e);
      }
    },
    async getUserMap() {
      let kq = null;
      try {
        // let params = {
        //   id_neo: "user_map",
        //   table: "admin.nguoidung",
        //   dk: `where nguoidung_id = ${this.$auth.getNguoiDungID()}`,
        // };
        // let response = await API.getUserMap(params);
        // let data = this.GetData(await frmApi.fn_tt_nguoidung(this.axios, input))
        let nguoidungid = this.$root.token.getNguoiDungID()
        let input = { type: 3, param: nguoidungid}
        let response = await API.fn_tt_nguoidung(this.axios, input);
        if (
          response &&
          response.data &&
          response.data.error_code === "BSS-00000000" &&
          response.data.data
        ) {
          kq = response.data.data;
        }
      } catch (e) {
        console.log(e);
      }
      return kq;
    },
    async init() {
      let dt = await this.lay_ds_nv();
      this.ds_sms_temp = await this.Lay_tinnhan_mau_nhacviec();
      await this.LoadTienTrinhSMS();
      await this.LAY_NOIDUNG_SMS_GIAOVIEC_V2(
        2,
        0,
        this.thuebao_id,
        +this.ma_tb,
        "",
        "",
        this.phieu_id
      );
      let ds_kt1 = await API.LAY_DS_THAMSO_MD();
      if (ds_kt1.length > 0) {
        let str_vtemp = "THOIHAN_NHACVIEC_TTKD";
        const det = ds_kt1.filter((x) => x["ma_ts"].toString() == str_vtemp);
        if (det.length > 0) {
          let row = det[0];
          if (row["ten_ts"].toString().trim() != "0")
            this.THOIHAN_NHACVIEC_TTKD = row["ten_ts"];
        }
      }
    },
    async LAY_NOIDUNG_SMS_GIAOVIEC_V2(
      vkieu,
      vhdtb_id,
      vthuebao_id,
      vma_tb,
      vnhiemvu,
      vghichu,
      vphieu_id
    ) {
      try {
        let params = {
          vkieu,
          vhdtb_id,
          vthuebao_id,
          vma_tb,
          vnhiemvu,
          vghichu,
          vphieu_id,
        };
        let response = await API.fn_LAY_NOIDUNG_SMS_GIAOVIEC_V2(
          JSON.stringify(params)
        );
        if (
          response &&
          response.data &&
          response.data.error_code === "BSS-00000000" &&
          response.data.data
        ) {
          this.vnoidung = response.data.data;
        }
      } catch (e) {
        console.log(e);
      }
    },
    async LoadTienTrinhSMS() {
      try {
        let params = {
          baohong_id: +this.baohong_id,
        };
        let response = await API.LoadTienTrinhSMS(params);
        if (
          response &&
          response.data &&
          response.data.error_code === "BSS-00000000" &&
          response.data.data
        ) {
          this.dgvTienTrinhSMS = response.data.data;
        }
      } catch (e) {
        console.log(e);
      }
    },
    async Lay_tinnhan_mau_nhacviec() {
      let kq = 0;
      try {
        let params = {
          kieugoi_id: this.kieugoi_id,
        };
        let response = await API.Lay_tinnhan_mau_nhacviec(params);
        if (
          response &&
          response.data &&
          response.data.error_code === "BSS-00000000" &&
          response.data.data
        ) {
          kq = response.data.data;
        }
      } catch (e) {
        console.log(e);
      }
      return kq;
    },
    async lay_ds_nv() {
      let kqnt = null;
      try {
        let params = {
          baohong_id: this.baohong_id,
        };
        let response = await API.sp_lay_nvtn_theo_baohong_id(params);
        if (
          response &&
          response.data &&
          response.data.error_code === "BSS-00000000" &&
          response.data.data
        ) {
          kqnt = response.data.data;
        }
      } catch (e) {
        console.log(e);
      }
      return kqnt;
    },
    selectedRowNhanVien(i, item) {},
    rowNhanVienDoubleClicked(i, item) {
      const sdt = item.so_dt;
      if (sdt) {
        if (this.txtSoDT.length != 0) {
          if (!this.txtSoDT.split(";").includes(sdt)) {
            this.txtSoDT += ";" + sdt;
            this.dicNV.push({
              nhanvien_id: item.nhanvien_id,
              sdt,
            });
          }
        } else {
          this.txtSoDT = sdt;
        }
      } else {
        this.$toast.warning(
          "Số điện thoại trống không thể thêm vào danh sách gửi đi"
        );
      }
    },
    async tsbtnSMS_Click() {
      if (this.txtSoDT != "") {
        if (
          this.txtSoDT
            .split(";")
            .join("")
            .split("")
            .some((c) => !Number.isInteger(+c))
        ) {
          this.$toast.warning("Số điện thoại nhập vào không đúng");
          return;
        }
      }
      if (this.txtNoiDung.length == 0) {
        this.$toast.warning("Bạn chưa nhập nội dung tin nhắn");
        return;
      }
      if (this.THOIHAN_NHACVIEC_TTKD > 0 && this.kieu_nhac != 0) {
        let kt;
        try {
          let params = {
            baohong_id: this.baohong_id,
            THOIHAN_NHACVIEC_TTKD: this.THOIHAN_NHACVIEC_TTKD,
            kieu_nhac: this.kieu_nhac,
          };
          let response = await API.sp_lay_nvtn_theo_baohong_id(params);
          if (
            response &&
            response.data &&
            response.data.error_code === "BSS-00000000" &&
            response.data.data
          ) {
            kt = response.data.data;
          }
        } catch (e) {
          console.log(e);
        }
        if (kt.toLowerCase() != "ok") {
          this.$toast.warning(kt);
          return;
        }
      }
      try {
        for (const sdt of this.txtSoDT.split(";")) {
          if (sdt) {
            await this.sendSMS(sdt, this.txtNoiDung);
            let nhanvien_nhan_id = -1;
            if (this.dicNV.length) {
              const nv = this.dicNV.find((x) => x.sdt == sdt);
              if (nv) {
                nhanvien_nhan_id = nv.nhanvien_id;
              }
            }
            try {
              await this.sp_capnhat_tientrinh_sms(sdt, nhanvien_nhan_id);
            } catch (error) {
              return;
            }
          }
        }
        await this.LoadTienTrinhSMS();
        this.$root.$toast.success("Gửi tin nhắn thành công");
      } catch (error) {
        this.$root.$toast.error("Gửi tin nhắn không thành công");
      }
    },
    async sp_capnhat_tientrinh_sms(so_dt, nhanvien_nhan_id) {
      this.loading(true);
      try {
        let params = {
          phanvung_id: this.$root.token.getPhanVungID(),
          baohong_id: this.baohong_id,
          phieu_id: this.phieu_id,
          donvi_id: this.$auth.getDonViID(),
          nhanvien_id: this.$auth.getNhanVienID(),
          nguoidung_id: this.$auth.getNguoiDungID(),
          noidung: "'" + this.txtNoiDung.replace("'", "''") + "'",
          so_dt,
          nguoi_cn: this.$root.token.getUserName(),
          may_cn: this.$root.token.getNguoiDungID(),
          ip_cn: "1",
          nhanvien_nhan_id,
          kieu_nhac: this.kieu_nhac,
        };
        const response = await API.sp_capnhat_tientrinh_sms(params);
      } catch (e) {
        this.$root.$toast.error("Không thể gửi tin nhắn");
      } finally {
        this.loading(false);
      }
    },
    async sendSMS(so_dt, noidung) {
      this.loading(true);
      try {
        const response = await API.SendSMS_NhacViec({
          msisdn: so_dt,
          content: noidung
        });
      } catch (e) {
        this.$root.$toast.error("Không thể gửi tin nhắn");
      } finally {
        this.loading(false);
      }
    },
  },
};
</script>
<style>
.box-head {
  height: 343px;
  overflow-y: auto;
}
</style>