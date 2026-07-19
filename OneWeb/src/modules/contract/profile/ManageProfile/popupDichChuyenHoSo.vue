<template>
  <b-modal
    ref="popupDichChuyenHS"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Di chuyển hồ sơ
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="hideModal"
        ></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a @click="tsbtnGhiLai_Click">
              <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Xác
              nhận
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form padt8 padb8">
          <div class="row">
            <div class="col-sm-7 col-12">
              <span class="title fw6 upper mart7 block"
                >DI CHUYỂN ĐỐI TƯỢNG HỒ SƠ ĐIỆN TỬ</span
              >
            </div>
            <div class="col-sm-5 col-12">
              <div class="info-row marb0">
                <div class="key w30 nowrap">Lý do</div>
                <div class="value">
                  <div class="select-custom">
                    <SelectExt
                      v-model="cboLyDoDiChuyen.value"
                      :options="cboLyDoDiChuyen.data"
                      placeholder="Vui lòng chọn lý do"
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="box-form">
              <div class="legend-title">Đối tượng chuyển đi</div>
              <div class="tree-plus" style="height: 300px; overflow: auto">
                <ejs-treeview
                  id="treeviewDi"
                  :fields="fieldsDi"
                  :showCheckBox="false"
                  @nodeSelected="nodeSelectedDi"
                  :expandAll="true"
                  ref="treeviewDi"
                ></ejs-treeview>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="box-form">
              <div class="legend-title">Đối tượng chuyển đến</div>
              <div class="tree-plus" style="height: 300px; overflow: auto">
                <ejs-treeview
                  id="treeviewDen"
                  :fields="fieldsDen"
                  :showCheckBox="false"
                  @nodeSelected="nodeSelectedDen"
                  :expandAll="true"
                  ref="treeviewDen"
                ></ejs-treeview>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import ActionTop from "@/components/ActionTop.vue";
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import API from "./API";
export default {
  name: "popupDichChuyenHS",
  components: { breadcrumb, ActionTop },
  data() {
    return {
      fieldsDi: {
        dataSource: null,
        id: "id",
        parentID: "parentID",
        text: "text",
        hasChildren: "hasChild",
        imageUrl: 'icon'
      },
      fieldsDen: {
        dataSource: null,
        id: "id",
        parentID: "parentID",
        text: "text",
        hasChildren: "hasChild",
        imageUrl: 'icon'
      },
      cboLyDoDiChuyen: {
        value: 0,
        data: [],
      },
      tvHoSo_selectedNode: [],
      tvHoSoNhan_selectedNode: [],
       dsIcon: [],
    };
  },
  mounted() {
    this.displayTreview();
    this.loadLyDo();
  },
  methods: {
    showModal() {
      this.$refs["popupDichChuyenHS"].show();
      this.displayTreview();
    },
    hideModal() {
      this.$refs["popupDichChuyenHS"].hide();
    },
    loadLyDo() {
      this.loading(true);
      API.sp_getlydohoso(this.axios)
        .then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            this.cboLyDoDiChuyen.data = response.data.data.map((x) => ({
              id: x.lydohs_id,
              text: x.lydo_hs,
            }));
            this.cboLyDoDiChuyen.value = response.data.data[0].lydohs_id;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    async displayTreview() {
      self=this;
      this.loading(true);
      await this.loadIcon();
       this.tvHoSo_selectedNode = [];
       this.tvHoSoNhan_selectedNode = [];
      this.fieldsDi = {
        dataSource: null,
        id: "id",
        parentID: "parentID",
        text: "text",
        hasChildren: "hasChild",
        imageUrl: 'icon'
      };
      this.fieldsDen = {
        dataSource: null,
        id: "id",
        parentID: "parentID",
        text: "text",
        hasChildren: "hasChild",
        imageUrl: 'icon'
      };
      let items = [];
      await API.sp_gettreehoso(this.axios, {})
        .then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            this.treeHoSo = response.data.data;
            response.data.data.forEach(function (item) {
              items.push({
                id: item.hoso_id,
                text: item.ma_hs_doituong,
                parentID: item.hoso_cha_id == null ? 0 : item.hoso_cha_id,
                 icon: "data:image/jpeg;base64,"+self.dsIcon[item.loaihs_id -1]
              });
            });
            let hsgoc = [
              {
                id: 0,
                parentID: null,
                text: "Root",
                hasChild: true,
                icon: "data:image/jpeg;base64,"+self.dsIcon[0]
              },
            ];
            hsgoc.push(...items);
            let hsTree = this.createTree(hsgoc);
            this.fieldsDi = {
              dataSource: hsTree,
              id: "id",
              parentID: "parentID",
              text: "text",
              hasChildren: "hasChild",
              imageUrl: 'icon'
            };
            this.fieldsDen = {
              dataSource: hsTree,
              id: "id",
              parentID: "parentID",
              text: "text",
              hasChildren: "hasChild",
              imageUrl: 'icon'
            };
            //console.log("tree: "+JSON.stringify(hsgoc))
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    createTree(dsHS) {
      let tree = [];
      let hsChaArray = dsHS.map((item) => item["parentID"]);
      let hsArray = dsHS.map((item) => item["id"]);
      dsHS.forEach((item) => {
        if (hsChaArray.includes(item["id"])) {
          if (!hsArray.includes(item["parentID"])) {
            delete item.parentID;
          }
          tree.push({ ...item, hasChild: true, expanded: true });
        } else {
          tree.push(item);
        }
      });
      return tree;
    },
    nodeSelectedDi() {
      let treeObj = document.getElementById("treeviewDi").ej2_instances[0];
      this.tvHoSo_selectedNode = this.treeHoSo.filter(
        (x) => x.hoso_id == treeObj.selectedNodes[0]
      );
    },
    nodeSelectedDen() {
      let treeObj = document.getElementById("treeviewDen").ej2_instances[0];
      this.tvHoSoNhan_selectedNode = this.treeHoSo.filter(
        (x) => x.hoso_id == treeObj.selectedNodes[0]
      );
      
    },
    tsbtnGhiLai_Click() {
      let vhoso_id,
        vhoso_cha_id = -1,
        vhoso_cha_id_cu = -1;
      let vmuc_id_goc = -1,
        vmuc_id_moi = -1;
      if (this.tvHoSo_selectedNode.length == 0) {
        this.$toast.error("Bạn không thể di chuyển đối tượng này!");
        return false;
      }
      if (this.tvHoSoNhan_selectedNode.length == 0) {
        this.$toast.error("Bạn phải chọn đối tượng hồ sơ chuyển đến!");
        return false;
      }
      vhoso_id = this.tvHoSo_selectedNode[0].hoso_id;
      vhoso_cha_id = this.tvHoSoNhan_selectedNode[0].hoso_id;
      vhoso_cha_id_cu = this.tvHoSo_selectedNode[0].hoso_cha_id;
      if (vhoso_cha_id_cu == vhoso_cha_id) {
        this.$toast.error("Không có sự thay đổi!");
        return false;
      }
      vmuc_id_goc = this.tvHoSo_selectedNode[0].muc_id;
      vmuc_id_moi = this.tvHoSoNhan_selectedNode[0].muc_id;
      if (vmuc_id_goc <= vmuc_id_moi) {
        this.$toast.error(
          "Bạn không thể di chuyển đối tượng đến mức bằng hoặc thấp hơn!"
        );
        return false;
      }
      // hs.DICHUYEN_HOSO(vhoso_id, vhoso_cha_id,Convert.ToInt32(cboLyDoDiChuyen.SelectedValue),tt_nd.may_cn,tt_nd.ma_nd);
      this.loading(true);
      API.dichuyen_hoso(this.axios, {
        vhoso_id: vhoso_id,
        vhoso_cha_id: vhoso_cha_id,
        vlydohs_id: this.cboLyDoDiChuyen.value,
      })
        .then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            this.$toast.success("Di chuyển hồ sơ thành công!");
             this.$emit("acceptChange");
            this.hideModal();
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    async loadIcon() {
      for(let i =1; i<5;i++){
        await API.sp_lay_data_icon_theo_loaihs(this.axios,{"p_loaihs_id": i}
        ).then((response) => { 
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
           this.dsIcon.push(response.data.data[0].icon_img)
          }
        })
      }
    },
  },
};
</script>
