<template src="./Menu.html"></template>
<style scoped src="./Menu.scss"></style>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import breadcrumb from "@/components/breadcrumb";
import treemenu from "./custom-treemenu";
import API from "./API";
import Vue from "vue";
export default {
  components: { breadcrumb, treemenu },
  data: function () {
    return {
      header: {
        title: "",
      },
      list: [
        { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
        {
          name: "Lắp đặt mới",
          link: { name: "Ui.buttons" },
        },
      ],
      params: {
        loaict_options: [],
        loaict: null,
        nhommenu_options: [],
        nhommenu: null,
        nhomchucnang_options: [],
        nhomchucnang: null,
        chucnang_options: [],
        chucnang: null,
        menucha_options: [],
        manucha: 0,
        tenmenu: "",
        vitri: "",
        txtghichu: null,
        txtURL: "",
        tenhienthi: "",
        thongtinmenu: [],
        thongtinchucnang: [],
        menu_id: null,
        timkiemmenu: null,
        max_menu_id: 0,
      },
      treedata: [],
      selectedNodes: [],
      MenuWord: "",
      MenuIndex: 0,
      MenuCache: [],
      menuTree: [],
      MenuChaCache: [],
      expandedNodes: [-1],
      collapseAll: [-1],
      button: {
        nhapmoi: true,
        ghilai: true,
        xoa: true,
        huybo: true,
      },
      fields: {
        dataSource: null,
        id: "id",
        parentID: "parentID",
        text: "text",
        hasChildren: "hasChild",
      },
    };
  },
  computed: {},
  created() {
    this.getMenu();
    this.loadLoaiCT();
  },
  methods: {
    async loadLoaiCT() {
      this.params.loaict_options = [];
      this.params.loaict = null;
      await API.getADMIN_LOAI_CT(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            response.data.data.forEach(function (item) {
              items.push({ id: item.LOAI_CT_ID, text: item.TENLOAI_CT });
            });
            this.params.loaict_options = items;
            this.params.loaict = items[0].id;
            this.loadNhomMenu(items[0].id);
            this.loadNhomCN(items[0].id);
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    loadNhomMenu(loaictid) {
      this.params.nhommenu_options = [];
      this.params.nhommenu = null;
      API.getNhom_Menu_LCT(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            response.data.data.forEach(function (item) {
              items.push({
                id: item.NHOM_MENU_ID,
                text: item.NHOM_MENU,
                loaictid: item.LOAI_CT_ID,
              });
            });
            let nhommenu = items
              .filter((x) => x.loaictid == loaictid)
              .sort((a, b) => a.id - b.id);
            // console.log(JSON.stringify(items))
            // console.log(JSON.stringify(loaictid))
            this.params.nhommenu_options = nhommenu;
            this.params.nhommenu = nhommenu[0].id;
            this.loadMenuCha(nhommenu[0].id, 0);
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    loadNhomCN(loaictid) {
      this.params.nhomchucnang_options = [];
      this.params.nhomchucnang = null;
      API.getNhom_CN_LCT(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            response.data.data.forEach(function (item) {
              items.push({
                id: item.NHOM_CN_ID,
                text: item.TEN_NHOM,
                loaictid: item.LOAI_CT_ID,
              });
            });
            let nhomcn = items
              .filter((x) => x.loaictid == loaictid)
              .sort((a, b) => a.id - b.id);
            this.params.nhomchucnang_options = nhomcn;
            this.params.nhomchucnang = nhomcn[0].id;
            this.loadChucNang(nhomcn[0].id);
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    loadChucNang(nhomcn) {
      this.params.chucnang_options = [];
      this.params.chucnang = null;
      API.getChucNang_NCN(this.axios, { vkieu: 1, vnhom_cn_id: nhomcn }) //objChucNang.getChucNang_NCN(0,nhom_cn_id)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            response.data.data.forEach(function (item) {
              items.push({
                id: item.chucnang_id,
                text: item.ten_cn,
              });
            });
            this.params.chucnang_options = items;
            this.params.chucnang = items[0].id;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    getChucNang(chucnangid) {
      this.loading(true);
      API.getChucNang(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            response.data.data.forEach(function (item) {
              items.push({
                id: item.CHUCNANG_ID,
                text: item.TEN_CN,
                NHOM_CN_ID: item.NHOM_CN_ID,
              });
            });
            this.params.chucnang_options = items;
            this.params.chucnang = chucnangid;
            if (chucnangid != null) {
              this.params.nhomchucnang = items.filter(
                (x) => x.id == chucnangid
              )[0].NHOM_CN_ID;
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    async loadMenuCha(nhommenuid, themmoi) {
      this.loading(true);
      this.params.menucha_options = [];
      this.params.menucha = null;
      this.fields = {
        dataSource: null,
        id: "id",
        parentID: "parentID",
        text: "text",
        hasChildren: "hasChild",
      };
      await API.getMenu(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.params.thongtinmenu = response.data.data;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
      await API.getMenuCha_NMN(this.axios, { nhom_menu_id: nhommenuid })
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            response.data.data.forEach(function (item) {
              items.push({
                id: item.menu_id,
                text: item.ten_hienthi,
                parentID: item.menu_cha_id == null ? 0 : item.menu_cha_id,
              });
            });
            this.params.menucha_options = items;
            this.params.menucha = items[0].id;
            let menugoc = [
              {
                id: 0,
                parentID: null,
                text: "Chương trình chính",
                hasChild: true,
              },
            ];
            menugoc.push(...items);
            this.menuTree = this.createTree(menugoc);
            this.fields = {
              dataSource: this.menuTree,
              id: "id",
              parentID: "parentID",
              text: "text",
              hasChildren: "hasChild",
            };
            let menu = this.params.thongtinmenu.filter(
              (x) => x.menu_id == this.menuTree[1].id
            );
            if (menu[0] == undefined) return false;
            this.params.menu_id = this.menuTree[1].id;
            this.getChucNang(menu[0].chucnang_id);
            this.params.tenmenu = menu[0].ten_menu;
            this.params.vitri = menu[0].vitri;
            this.params.tenhienthi = menu[0].ten_hienthi;
            this.params.menucha = menu[0].menu_cha_id;
            this.params.txtghichu = menu[0].ghichu;
            this.params.txtURL = menu[0].url;
            this.selectedNodes = [];
            console.log("items: " + JSON.stringify(items));
            if (themmoi > 0) {
              this.selectedNodes.push(themmoi);
            } else {
              this.selectedNodes.push(this.menuTree[1].id.toString());
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    createTree(dsMenu) {
      let tree = [];
      let menuChaArray = dsMenu.map((item) => item["parentID"]);
      let menuArray = dsMenu.map((item) => item["id"]);
      dsMenu.forEach((item) => {
        if (menuChaArray.includes(item["id"])) {
          if (!menuArray.includes(item["parentID"])) {
            delete item.parentID;
          }
          tree.push({ ...item, hasChild: true, expanded: true });
        } else {
          tree.push(item);
        }
      });
      return tree;
    },
    SetButton(kieu) {
      this.button.nhapmoi = false;
      this.button.xoa = false;
      this.button.ghilai = false;
      this.button.huybo = false;
      switch (kieu) {
        case -1: //batdau
          this.button.ghilai = true;
          this.button.huybo = true;
          this.$refs.focustenmenu.focus();
          break;
        case 0: //Bat dau
          this.button.nhapmoi = true;
          this.$refs.focustenmenu.focus();
          this.clear();
          break;
        case 1: //Them moi
          this.button.ghilai = true;
          this.button.huybo = true;
          this.$refs.focustenmenu.focus();
          //this.loadLoaiCT();
          //this.getMenu();
          this.clear();
          break;
        case 2: //huy
          this.button.nhapmoi = true;
          this.button.xoa = true;
          this.$refs.focustenmenu.focus();
          this.clear();
          break;
        case 3: //edit
          this.button.nhapmoi = true;
          this.button.xoa = true;
          this.button.ghilai = true;
          this.button.huybo = true;
          this.$refs.focustenmenu.focus();
          break;
      }
    },
    async getMenu() {
      await API.getMenu(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.params.thongtinmenu = response.data.data;
            var max_menu_id = this.getMax(response.data.data, "menu_id");
            this.params.max_menu_id = max_menu_id.menu_id;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    getMax(arr, prop) {
      var max;
      for (var i = 0; i < arr.length; i++) {
        if (max == null || parseInt(arr[i][prop]) > parseInt(max[prop]))
          max = arr[i];
      }
      return max;
    },
    async CapNhat(kieu, data) {
      let apiParams = {
        kieu: kieu, // 1 - them moi, 2 - update
        json_menu: data,
      };
      await API.CapNhat(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined
          ) {
            this.clear();
            this.getMenu();
            if (kieu == 1) {
              setTimeout(
                function () {
                  this.loadMenuCha(
                    this.params.nhommenu,
                    this.params.max_menu_id
                  );
                  this.$toast.success("Thêm mới bản ghi thành công");
                }.bind(this),
                500
              );
            } else {
              this.loadMenuCha(this.params.nhommenu, this.params.menu_id);
              this.$toast.success("Cập nhật bản ghi thành công");
            }
          } else {
            if (kieu == 1) {
              this.$toast.error("Thêm mới bản ghi thất bại");
            } else {
              this.$toast.error("Cập nhật bản ghi thất bại");
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
      this.SetButton(3);
    },
    onChangeLoaiCT(agrs) {
      this.loadNhomMenu(agrs.id);
      this.loadNhomCN(agrs.id);
    },
    onChangeNhomCN(agrs) {
      this.loadChucNang(agrs.id);
    },
    onChangeNhomMenu(agrs) {
      console.log("nhomMN: "+agrs.id)
      this.loadMenuCha(agrs.id, 0);
    },
    clear() {
      this.$refs.focustenmenu.focus();
      this.params.tenmenu = "";
      this.params.vitri = "";
      this.params.tenhienthi = "";
      this.params.txtghichu = "";
      this.params.txtURL = "";
    },
    clickNhapMoi() {
      this.SetButton(1);
    },
    clickGhiLai() {
      if (this.params.tenmenu == "") {
        this.$toast.error("Vui lòng nhập tên menu");
        this.$refs.focustenmenu.focus();
        return false;
      }
      if (this.params.tenhienthi == "") {
        this.$toast.error("Vui lòng nhập tên hiển thị");
        this.$refs.inputTenHienThi.focus();
        return false;
      }
      if (this.params.vitri == "") {
        this.$toast.error("Vui lòng nhập vị trí");
        this.$refs.inputViTri.focus();
        return false;
      }
      if (this.params.nhommenu == null) {
        this.$toast.error("Vui lòng chọn nhóm menu");
        return false;
      }
      let data =
        '[{"GHICHU": "' +
        this.params.txtghichu +
        '","NHOM_MENU_ID":  "' +
        this.params.nhommenu +
        '","MENU_ID":  "' +
        this.params.menu_id +
        '","TEN_MENU": "' +
        this.params.tenmenu +
        '","TEN_HIENTHI": "' +
        this.params.tenhienthi +
        '","VITRI": "' +
        this.params.vitri +
        '","MENU_CHA_ID": "' +
        this.params.menucha +
        '","CHUCNANG_ID": "' +
        this.params.chucnang +
        '","URL": "' +
        this.params.txtURL +
        '"}]';
      this.$alert("Bạn thật sự muốn cập nhật dữ liệu không ?", "Thông báo", {
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
        if (this.button.nhapmoi == false) {
          //this.$toast.error("thêm mới")
          this.CapNhat(1, data);
        } else {
          // this.$toast.error("cập nhật")
          this.CapNhat(2, data);
        }
      });
    },
    clickHuy() {
      this.SetButton(0);
    },
    clickXoa() {
      this.$alert("Bạn thật sự muốn xóa dữ liệu không ?", "Thông báo", {
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
        API.XoaMenu(this.axios, { menu_id: this.params.menu_id })
          .then((response) => {
            if (response.data.error_code === "BSS-00000000") {
              this.$toast.success("Xóa thành công");
              this.loadMenuCha(this.params.nhommenu, 0);
              this.getMenu();
            } else {
              this.$toast.error("Đã xảy ra lỗi");
            }
          })
          .catch((error) => {
            this.$toast.error("Lỗi: " + JSON.stringify(error));
            console.log(error);
          })
          .finally(() => {});
      });
    },
    nodeSelected() {
      let treeObj = document.getElementById("treeview").ej2_instances[0];
      //treeObj.checkedNodes.toString();
      //treeObj.selectedNodes[0]
      let nhomcnCache = this.params.chucnang_options;
      let menu = this.params.thongtinmenu.filter(
        (x) => x.menu_id == treeObj.selectedNodes[0]
      );
      if (menu[0] == undefined) return false;
      this.params.menu_id = treeObj.selectedNodes[0];
      //this.getChucNang(menu[0].chucnang_id);
      this.params.chucnang = menu[0].chucnang_id;
      this.params.tenmenu = menu[0].ten_menu;
      this.params.vitri = menu[0].vitri;
      this.params.tenhienthi = menu[0].ten_hienthi;
      this.params.menucha = menu[0].menu_cha_id;
      this.params.txtghichu = menu[0].ghichu;
      this.params.txtURL = menu[0].url;
      //console.log("chucnangid: "+JSON.stringify( menu[0]))
      if (menu[0].chucnang_id != null) {
        this.params.nhomchucnang = nhomcnCache.filter(
          (x) => x.id == menu[0].chucnang_id
        )[0].NHOM_CN_ID;
      }
    },
    TimKiemDSMenu() {
      this.collapseAll = [-1];
      if (this.MenuWord == this.params.timkiemmenu) {
        this.MenuIndex++;
        if (this.MenuIndex >= this.MenuCache.length) {
          this.MenuIndex = 0;
        }
        this.selectedNodes = [this.MenuCache[this.MenuIndex]];
        // console.log("this.selectedNodes: "+this.selectedNodes)
        this.expandedNodes = [-1];
        let ps = this.getParents(this.MenuCache[this.MenuIndex]);
        if (!(ps == null || ps.length == 0)) {
          for (let i = 0; i < ps.length; i++) {
            this.expandedNodes.push(ps[i].id);
          }
        }
      } else {
        this.MenuIndex = 0;
        this.MenuWord = this.params.timkiemmenu;
        this.MenuCache = this.menuTree
          .filter((a) =>
            a.text
              .toString()
              .toLowerCase()
              .includes(this.params.timkiemmenu.toLowerCase())
          )
          .map((b) => b.id);
        this.MenuChaCache = this.menuTree
          .filter((a) =>
            a.text
              .toString()
              .toLowerCase()
              .includes(this.params.timkiemmenu.toLowerCase())
          )
          .map((b) => b.parentID);
        if (this.MenuCache.length) {
          this.selectedNodes = [this.MenuCache[0]];
          let ps = this.getParents(this.MenuCache[0]);
          if (!(ps == null || ps.length == 0)) {
            for (let i = 0; i < ps.length; i++) {
              this.expandedNodes.push(ps[i].id);
            }
          }
        } else {
          this.selectedNodes = ["0"];
          this.expandedNodes = [-1];
        }
      }
    },
    getParents: function (nodeParentID) {
      let rs = [];
      let parent = this.menuTree.find((x) => x.id == nodeParentID);
      if (parent != null) {
        rs.push(parent);
        if (parent.parentID != null) {
          let ps = this.getParents(parent.parentID);
          if (!(ps == null || ps.length == 0)) {
            for (let i = 0; i < ps.length; i++) {
              rs.push(ps[i]);
            }
          }
        }
      }
      return rs;
    },
  },
};
</script>
