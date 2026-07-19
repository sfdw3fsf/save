<template src="./ManageProfile.html"></template>
<style scoped src="./ManageProfile.scss"></style>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import breadcrumb from "@/components/breadcrumb";
import API from "./API";
import popupDichChuyenHS from "./popupDichChuyenHoSo.vue";
import popupUpdateIcon from "./popupUpdateIcon.vue";
import Vue from "vue";
import { TreeViewPlugin } from "@syncfusion/ej2-vue-navigations";
Vue.use(TreeViewPlugin);
export default {
  components: { breadcrumb, popupDichChuyenHS,popupUpdateIcon },
  data: function () {
    return {
      header: {
        title: "Quản lý đối tượng hồ sơ điện tử",
      },
      dtHoSo: [],
      dsHoSo: [],
      sDuongDan: "",
      ilDoiTuongThaoTac: 1,
      note_text: "",
      txtDoiTuong: "",
      txtMaHS: "",
      txtGhiChu: "",
      treeHoSo: [],
      selectedNodes: [],
      ndwTuSo: 0,
      ndwDenSo: 0,
      tag:0,
      fields: {
        dataSource: null,
        id: "id",
        parentID: "parentID",
        text: "text",
        hasChildren: "hasChild",
        imageUrl: 'icon'
      },
      cboLoaiDT: {
        value: 0,
        data: [],
      },
      tsbtnGhiLai: {
        enabled: false,
      },
      tsbtnXoa: {
        enabled: false,
      },
      tsbtnNhapMoi: {
        enabled: true,
      },
      tvHoSo_selectedNode: [],
      dt_hsTree: [],
      dsIcon: [],
    };
  },
  created() {  
    this.displayTreview();
    this.loadCboLoaiHS();
    this.tsbtnGhiLai.enabled = false;
    this.tsbtnXoa.enabled = false; 
  },
  methods: {
    async displayTreview() {
      self=this;
      await this.getIcon();
      this.fields = {
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
                icon: "data:image/jpeg;base64,"+self.dsIcon[item.loaihs_id -1],
                mahs: item.ma_hs
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
            this.dt_hsTree = hsTree;
            this.fields = {
              dataSource: hsTree,
              id: "id",
              parentID: "parentID",
              text: "text",
              hasChildren: "hasChild",
              imageUrl: 'icon'
            };
            this.selectedNodes = [];
            this.selectedNodes.push(this.tvHoSo_selectedNode[0].hoso_id);
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
    nodeSelected(data) {
      let treeObj = document.getElementById("treeview").ej2_instances[0];
      this.tvHoSo_selectedNode = this.treeHoSo.filter(
        (x) => x.hoso_id == treeObj.selectedNodes[0]
      );
      if (treeObj.selectedNodes[0] != 0) {
        if(this.tvHoSo_selectedNode[0].hoso_cha_id ==null){
          this.sDuongDan = "C:\\HOSO";
        }else{
          this.sDuongDan = this.tvHoSo_selectedNode[0].hoso_cha_id + "\\" +  this.tvHoSo_selectedNode[0].hoso_id
        }
        this.tsbtnGhiLai.enabled = true;
        this.tsbtnXoa.enabled = true;
        this.txtDoiTuong = this.tvHoSo_selectedNode[0].doituong;
        this.txtMaHS = this.tvHoSo_selectedNode[0].ma_hs;
        this.txtGhiChu = this.tvHoSo_selectedNode[0].ghichu;
        this.cboLoaiDT.value = this.tvHoSo_selectedNode[0].loaihs_id;
        let sTuSo = 0;
        let sDenSo = 0;
        sTuSo = this.tvHoSo_selectedNode[0].tuso;
        sDenSo = this.tvHoSo_selectedNode[0].denso;
        if (sTuSo == "") sTuSo = 0;
        if (sDenSo == "") sDenSo = 0;
        this.ndwTuSo = sTuSo;
        this.ndwDenSo = sDenSo;
        this.note_text = this.tvHoSo_selectedNode[0].ma_hs;
        this.ilDoiTuongThaoTac = 1;
        this.tsbtnGhiLai.enabled = true;
      } else {
        this.tsbtnGhiLai.enabled = false;
      }
      this.tsbtnNhapMoi.enabled = true;
    },
    loadCboLoaiHS() {
      this.loading(true);
      API.sp_getloaihoso(this.axios, {})
        .then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            this.cboLoaiDT.data = response.data.data.map((x) => ({
              id: x.loaihs_id,
              text: x.loai_hs,
              muc_id: x.muc_id,
            }));
            this.cboLoaiDT.value = response.data.data[0].loaihs_id;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    tsbtnNhapMoi_Click() {
      let sLoaiHS_ID = "";
      let iLoaiHS_ID = 0;
      this.tsbtnNhapMoi.enabled = false;
      this.tsbtnGhiLai.enabled = true;
      this.tsbtnXoa.enabled = false;
      this.txtDoiTuong = "";
      this.txtGhiChu = "";
      this.txtMaHS = "";
      if (this.tvHoSo_selectedNode.length > 0) {
        sLoaiHS_ID = this.tvHoSo_selectedNode[0].loaihs_id;
      } else {
        sLoaiHS_ID = "1";
      }
    },
    tsbtnGhiLai_Click() {
      var vhoso_id = null;
      var vhoso_cha_id = null;
      var vmuc_cha_id = -2;
      var vma_hs = this.txtMaHS;
      var vloaihs_id = this.cboLoaiDT.value;
      var vdoituong = this.txtDoiTuong;
      var vghichu = this.txtGhiChu;
      var vtuso = this.ndwTuSo;
      var vdenso = this.ndwDenSo;
      var vmuc_id = -1;
      if (!this.tsbtnNhapMoi.enabled) 
      {
        if (this.tvHoSo_selectedNode.length == 0 || this.tvHoSo_selectedNode[0].hoso_id == null) {
          vhoso_cha_id = null //-1;
          vmuc_cha_id = -1;
        } else {
          vhoso_cha_id = this.tvHoSo_selectedNode[0].hoso_id;
          vmuc_cha_id = this.tvHoSo_selectedNode[0].muc_id;
        }
        let drMuc = this.cboLoaiDT.data.filter(
          (x) => x.id == this.cboLoaiDT.value
        );
        if (drMuc.length > 0) {
          vmuc_id = drMuc[0]["muc_id"];
        }
        //console.log("muc " + vmuc_id + " ch " + vmuc_cha_id);
        if (vmuc_id <= vmuc_cha_id) {
          this.$toast.error(
            "Bạn không được gán đối tượng vào đối tượng cùng mức hoặc mức thấp hơn!"
          );
          return false;
        }
        if(this.txtMaHS.trim()==""){
          this.$toast.error("Vui lòng nhập mã đối tượng");
          this.$refs.inputMaHS.focus();
        }
         if(this.txtDoiTuong.trim()==""){
          this.$toast.error("Vui lòng nhập tên đối tượng");
          this.$refs.inputTenDT.focus();
        }
        /* hs.INSERT_HOSO(vma_hs,vdoituong,vloaihs_id,vhoso_cha_id,vtuso,vdenso,vghichu,tt_nd.may_cn,tt_nd.ma_nd); */
        this.loading(true);
        API.themmoi_hoso(this.axios, {
          vma_hs: vma_hs,
          vdoituong:vdoituong,
          vloaihs_id: vloaihs_id,
          vhoso_cha_id: vhoso_cha_id,
          vtuso: vtuso,
          vdenso: vdenso,
          vghichu: vghichu
        })
          .then((response) => {
            if (
              response.data.data &&
              response.data.error_code &&
              response.data.error_code === "BSS-00000000" &&
              response.data.data === "1"
            ) {
              this.$toast.success("Thêm mới đối tượng thành công");
              this.displayTreview();
              this.tsbtnGhiLai.enabled = false;
              this.tsbtnXoa.enabled = false;
              this.tsbtnNhapMoi.enabled = true;
            }
          })
          .catch((error) => {
            console.log(error);
          })
          .finally(() => {
            this.loading(false);
          });
      } else {
        console.log("cap nhat");
        if (this.tvHoSo_selectedNode.length == 0 || this.tvHoSo_selectedNode[0].hoso_cha_id == null) {
          vhoso_cha_id = null, //-1;
          vmuc_cha_id = -1;
          vhoso_id = this.tvHoSo_selectedNode[0].hoso_id;
          console.log("vhoso_id: "+this.tvHoSo_selectedNode[0].hoso_id);
        } else {
          vhoso_cha_id = this.tvHoSo_selectedNode[0].hoso_cha_id;
          vmuc_cha_id = this.tvHoSo_selectedNode[0].muc_id -1;
          console.log("mcha "+vmuc_cha_id+" muc "+this.tvHoSo_selectedNode[0].muc_id)
          vhoso_id = this.tvHoSo_selectedNode[0].hoso_id;
        }
        let drMuc = this.cboLoaiDT.data.filter(
          (x) => x.id == this.cboLoaiDT.value
        );
        if (drMuc.length > 0) {
          vmuc_id = drMuc[0].muc_id;
        }
        if (vmuc_id <= vmuc_cha_id) {
          this.$toast.error(
            "Bạn không được gán đối tượng vào đối tượng cùng mức hoặc mức thấp hơn!"
          );
          return false;
        }
        //hs.CAPNHAT_HOSO(vhoso_id, vma_hs, vdoituong, vloaihs_id, vhoso_cha_id, vtuso, vdenso, vghichu, tt_nd.may_cn, tt_nd.ma_nd);
        this.loading(true);
        API.capnhat_hoso(this.axios, {
          vhoso_id: vhoso_id,
          vma_hs:vma_hs,
          vdoituong: vdoituong,
          vloaihs_id:vloaihs_id,
          vhoso_cha_id: vhoso_cha_id,
          vtuso: vtuso,
          vdenso: vdenso,
          vghichu: vghichu
        })
          .then((response) => {
            if (
              response.data.data &&
              response.data.error_code &&
              response.data.error_code === "BSS-00000000" &&
              response.data.data === "1"
            ) {
              this.$toast.success("Cập nhật đối tượng thành công");
              this.displayTreview();
            }
          })
          .catch((error) => {
            console.log(error);
          })
          .finally(() => {
            this.loading(false);
          });
      }
      
    },
    async tsbtnXoa_Click() {
      let _count = 0;
      let hasChild = this.dt_hsTree.filter(
        (x) => x.id == this.tvHoSo_selectedNode[0].hoso_id
      )[0].hasChild;
      await API.fn_tt_file_hs(this.axios, { param: this.tvHoSo_selectedNode[0].hoso_id, type: 1 })
        .then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            _count = response.data.data;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
      if (hasChild != undefined || _count >0) {
        this.$toast.error("Bạn không thể xóa đối tượng vì có đối tượng con!");
        return false;
      }
      this.$alert("Bạn có chắc chắn xóa file hồ sơ này?", "Thông báo", {
        dangerouslyUseHTMLString: true,
        showCancelButton: true,
        confirmButtonText: "Đồng ý",
        cancelButtonText: "Hủy bỏ",
        showCloseButton: true,
        type: "warning",
      }).then(async (v) => {
        if (!v) {
          return;
        }
        //hs.THUHOI_HOSO(Convert.ToInt32(tvHoSo.SelectedNode.Tag.ToString()), "", tt_nd.may_cn, tt_nd.ma_nd);
        API.thuhoi_hoso(this.axios, {
          vhoso_id:  this.tvHoSo_selectedNode[0].hoso_id,
          vghichu: this.txtGhiChu,
        })
          .then((response) => {
            if (
              response.data.data &&
              response.data.error_code &&
              response.data.error_code === "BSS-00000000" &&
              response.data.data === "1"
            ) {
              this.$toast.success("Thu hồi hồ sơ thành công");
              this.displayTreview();
            }
          })
          .catch((error) => {
            console.log(error);
          })
          .finally(() => {
            this.loading(false);
          });
      });
    },
    txtMaHS_KeyPress(){
      this.selectedNodes = [];
      this.selectedNodes.push(this.dt_hsTree.filter((x) => x.mahs == this.txtMaHS)[0].id.toString());
    },
    tsbtnFileHS_Click() {
      this.$router.push({ name: "UploadEProfile"});
    },
    tsbtnDiChuyenHS_Click() {
       this.$refs.popupDichChuyenHS.showModal();
    },
    tsbtnAddIcon_Click() {
      this.$refs.popupUpdateIcon.showModal();
    },
    async getIcon(){
      for(let i =1; i<5;i++){
        await API.sp_lay_data_icon_theo_loaihs(this.axios,{"p_loaihs_id": i}
        ).then((response) => { 
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
           this.dsIcon.push(response.data.data[0].icon_img)
          }
        })
      }
    },
    acceptDiChuyenHS(){
      this.displayTreview();
    }
  },
};
</script>