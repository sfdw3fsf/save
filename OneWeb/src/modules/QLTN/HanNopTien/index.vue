<template src="./index.html"></template>
<style src="./style.scss" scoped>
</style>
<script>
import moment from "moment";
import $ from "jquery";
import {DataManager, ODataAdaptor, Predicate, Query} from '@syncfusion/ej2-data';

const hasChild = (key, array) => {
  let rst = false;
  array.forEach((item) => {
    if (item.DONVI_CHA_ID == key) {
      rst = true;
    }
  });
  return rst;
};

const hasParrent = (pid, array) => {
  let rst = false;
  array.forEach((item) => {
    if (item.DONVI_ID == pid) {
      rst = true;
    }
  });
  return rst;
};

export default {
  components: { },
  data: function () {
    return {
      header: {
        title: "HẠN NỘP TIỀN",
        list: [
          { name: "Quản lý đại lý", link: { name: "Ui.cards" } },
          {
            name: "Hạn nộp tiền",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      isShowSearch: false,
      fields: "",
      dataTree: [],
      dsDoiTuong_tree:[],
      search_dt:"",
      dt_checked:[],
      dt_selected:[],
      dsDonViID:null,
      ckbChonNhanh: false,
      ngayHan: null,
      dsHanNop: [],
      lsHanNop: [],
      selectedId: [],
      choseId: "",
      isAddNew: false,
      selectedRow: null,
      dateDelete: "",
      txtMaQuanLy:"",
      txtTenQuanLy:"",
       fields: {
        dataSource: null,
        id: "DONVI_ID",
        parentID: "DONVI_CHA_ID",
        text: "TEN_HT",
        hasChildren: "hasChild",
      },

    };
  },
  validations: {},
  watch: {
    ckbChonNhanh(){
      if(this.ckbChonNhanh == true)
      {
        this.$refs.treeObjGan.checkAll();
      }
      else
      {
        this.$refs.treeObjGan.uncheckAll();
      }

    },
    txtMaQuanLy(newdata) {
      this.search_dt = newdata;
    },
    txtTenQuanLy(newdata) {
      this.search_dt = newdata;
    },

  },
  created: async function () {
    this.init();
  },
  async mounted() {
    $(document).on("click", ".btn-del", (evt) => {
      // console.log(evt.currentTarget)
      const data = evt.currentTarget.getAttribute("data-id");
      this.onclickDelete(data.split(" ")[0].split("-").reverse().join("/"));
    });
  },
  methods: {
    init: async function () {
      this.dataTree = [];
      this.dsHanNop = [];
      this.selectedId = [];
      this.choseId = "";
      this.displayTreeView();
      // await this.getTreeData();
      // $(".tree-plus a").click(function (e) {
      //   if (e.target !== this) return;
      //   $(this).parent().toggleClass("open");
      // });
    },
    checkAllNode() {
      if (!this.ckbChonNhanh) {
        this.dataTree.forEach((item) => {
          this.selectedId.push(item.DONVI_ID);
        });
      } else {
        this.selectedId = [];
      }
    },
    expandAll() {
      $(".haschild").addClass("open");
    },
    collapseAll() {
      $(".haschild").removeClass("open");
    },
    onChangeTreeCb(ev, value) {
      const isChecked = ev.target.checked;
      if (isChecked && !this.selectedId.includes(value)) {
        this.selectedId.push(value);
        if (this.ckbChonNhanh) {
          const childId = this.dataTree.filter(
            (item) => item.DONVI_CHA_ID == value
          );
          childId.forEach((item) => {
            if (!this.selectedId.includes(item.DONVI_ID)) {
              this.selectedId.push(item.DONVI_ID);
            }
          });
        }
      } else {
        const index = this.selectedId.indexOf(value);
        if (index > -1) {
          this.selectedId.splice(index, 1);
        }
        if (this.ckbChonNhanh) {
          const childId = this.dataTree.filter(
            (item) => item.DONVI_CHA_ID == value
          );
          childId.forEach((item) => {
            const index = this.selectedId.indexOf(item.DONVI_ID);
            if (index > -1) {
              this.selectedId.splice(index, 1);
            }
          });
        }
      }

      if (this.selectedId.length == 0) {
        this.isAddNew = false;
      }
      this.getDsHanNop();
    },
    onclickDelete(item) {
      this.dateDelete = item;
      this.$bvModal.show("notice-exist");
    },
    closeNoticeExist(value) {
      this.$bvModal.hide("notice-exist");
      value && this.delete(this.dateDelete);
    },
    onClickAddNew() {
      if (this.dt_selected.length == 0) {
        this.$toast.error(
          "Hãy chọn ít nhất 1 đối tượng trong danh sách để thực hiện nhập mới ngày hạn"
        );
        return false;
      }
      this.ngayHan = null;
      $(".mt-3px").focus();
      this.isAddNew = true;
    },
    onClickSave() {
      if (!this.ngayHan) {
        this.$toast.error("Hãy chọn Ngày hạn trước khi ghi lại");
        return false;
      }
      this.addNew();
    },

    getTreeData: async function () {
      const body = {
        ds: "",
        kieu: 0,
        donViId: this.$auth.getDonViID(),
        nhanVienId: this.$auth.getNhanVienID(),
        length: 0,
      };
      try {
        let ketqua=[];
        var rs = await this.$root.context.get(
          "/web-thuno/api/thu-no/quan-ly-dai-ly/don-vi-ban-hang/nhan-vien",
          body
        );
        ketqua=rs.data;
        return ketqua;

      } catch (error) {}
    },
    getDsHanNop: async function () {
      const body = {
        ds: this.dt_checked.length > 0 ? this.dt_checked.join() : "",
        kieu: 2,
        donViId: this.$auth.getDonViID(),
        nhanVienId: this.$auth.getNhanVienID(),
        length:1,// this.dt_checked.length,
      };
      console.log(body);
      try {
        var rs = await this.$root.context.get(
          "/web-thuno/api/thu-no/quan-ly-dai-ly/don-vi-ban-hang/nhan-vien",
          body
        );
        // console.log(rs);
        this.dsHanNop = rs.data.map((item) => ({
          ...item,
          NGAY_HAN:item.NGAY_HAN,//moment(item.NGAY_HAN).format('DD/MM/YYYY'),
          XOA: `<button class="btn btn-main pad4 lh14 btn-del" data-id="${item.NGAY_HAN}">
                    <span class="-ap icon-close"></span>
                  </button>`,
        }));
      } catch (error) {}
    },
    getLSHanNop: async function () {
      const body = {
        ds: "",
        kieu: 4,
        donViId: this.$auth.getDonViID(),
        nhanVienId: this.$auth.getNhanVienID(),
        length:this.dt_selected,
      };
      console.log(body);
      try {
        var rs = await this.$root.context.get(
          "/web-thuno/api/thu-no/quan-ly-dai-ly/don-vi-ban-hang/nhan-vien",
          body
        );
        this.lsHanNop = rs.data.map((item) => ({
          ...item,
          NGAY_HAN:item.NGAY_HAN
        }));
      } catch (error) {}
    },
    addNew: async function () {
      let ngayChotCu = moment(this.ngayHan).format("DD/MM/YYYY");
      if (this.selectedRow) {
        ngayChotCu = this.selectedRow.NGAY_HAN;
      }
    //   let tmp_ds=[];
    //   tmp_ds.push(this.dt_selected);
      const body = {
        ds: this.dt_checked,
        kieu: 2,
        ngayChot: moment(this.ngayHan).format("DD/MM/YYYY"),
        mayCN: "abc",
        ipCN: "11",
        nguoiCN: this.$auth.getUserName(),
        ngayChotCu: ngayChotCu,
      };
      console.log(body);
      try {
        var rs = await this.$root.context.post(
          "/web-thuno/api/thu-no/quan-ly-dai-ly/han-nop-tien",
          body
        );
        // console.log(rs);
        if (rs.error == "200") {
          this.$toast.success("Cập nhật thành công");
        }
        this.getDsHanNop();
        this.getLSHanNop();
        this.isAddNew=false;
      } catch (error) {
        this.$toast.error("Cập nhật thất bại");
      }
    },
    delete: async function (ngay) {
      // console.log(ngay);
      const body = {
        ds: this.dt_checked,
        kieu: 1,
        ngayChot: ngay,
        mayCN: "abc",
        ipCN: "11",
        nguoiCN: this.$auth.getUserName(),
        ngayChotCu: ngay,
      };

      try {
        const { data } = await this.axios.delete(
          "/web-thuno/api/thu-no/quan-ly-dai-ly/han-nop-tien",
          { data: body }
        );
        if (data && data.error == "200") {
          this.$toast.success("Xóa thông tin thành công!");
          this.getDsHanNop();
        } else {
          this.$toast.error("Xóa thông tin thất bại!");
        }
      } catch (error) {
        this.$toast.error("Xóa thông tin thất bại!");
      }
    },
    createTree (ds) {
      let tree = []
      let newDs = []
      var matinh = this.$root.token.getMaTinh();
      var parent='DONVI_CHA_ID';
      var child='DONVI_ID';
      // if(matinh != 'HNI')
      // {
      //   parent='kho_cha_id';
      //   child='kho_id';
      // }
      ds.forEach(item => {
        if(item.DONVI_CHA_ID === null) {
          item.DONVI_CHA_ID = 0
        }
        newDs.push(item)
      })
      let khoChaArray = newDs.map((item) => {
        return item[parent]
      })
      let khoArray = newDs.map(item => item[child])
      // check item la parent node
      newDs.forEach(item => {
        if (khoChaArray.includes(item[child])) {
          // check kho_cha_id khong co trong ds kho_id thi xoa kho_cha_id
          if (!khoArray.includes(item[parent])) {
            delete item.DONVI_CHA_ID
          }

          tree.push({...item, hasChild: true, expanded: false})
        } else {
          tree.push(item)
        }
      }

      )

      return tree
    },
 changeDataSource (data) {
      var matinh = this.$root.token.getMaTinh();
      var parent='DONVI_CHA_ID';
      var child='DONVI_ID';
      this.fields = {
        dataSource: data, id: child, text: 'TEN_HT', parentID: parent, hasChildren: 'hasChild'
      }

    },

     async displayTreeView () {
      try {
        this.loading(true)
        let dsKho = await this.getTreeData();//await this.getDanhSachKho()

        if(dsKho.length > 0) {
          this.dsDoiTuong_tree = this.createTree(dsKho);
          this.changeDataSource(this.dsDoiTuong_tree);
        }
      } catch(error){
        this.$toast.error('Không load được danh sách kho/đơn vị!!!')
      } finally {
        this.loading(false)
      }

    },

    nodeChecked(args) {
           var treeObj  = document.getElementById('treeviewGan').ej2_instances[0];
           var dodai = treeObj.checkedNodes.length;

           this.dt_checked =  treeObj.checkedNodes;
           let items = [];
              this.dt_checked.forEach(function (item) {
                items.push(item);

              });

           this.dt_checked = items;
           console.log(this.dt_checked.join());
           this.dt_selected = treeObj.checkedNodes[dodai-1];
           //console.log(this.dt_checked)
           this.getDsHanNop();
           this.getLSHanNop();
          //  if(this.tab_selected==1)
          //  {
          //    this.getDsHanNop();
          //  }
          //  else
          //  {
          //    this.getLichSu_HanChan();
          //  }
        },
        collapseAll()
            {
              this.$refs.treeObjGan.collapseAll()
            },
        expandAll()
            {
              this.$refs.treeObjGan.expandAll();
            },

 clickButton(key) {
      if (key == "lamtuoi") {
        this.displayTreeView();
      }

      if (key == "xemct") {
        this.expandAll();
      }

      if (key == "thulai") {
          this.collapseAll();
      }

      if (key == "nhapmoi") {
         this.txtNgayHan="";
      }
      if (key == "ghilai") {
        this.Them();
      }
      if (key == "huongdan") {

      }
      if(key=="timkiem")
      {
        if(document.getElementById('tab_timkiem').style.display=="block")
        {
          document.getElementById('tab_timkiem').style.display="none";
        }
        else
        {
          document.getElementById('tab_timkiem').style.display="block";
        }
      }
    },
searchDonVi () {
      let predicate
      let predicats = []
      let _array = []
      let _filter = []
      var matinh = this.$root.token.getMaTinh();
      var child='DONVI_ID';
      this.loading(true);
      if (this.txtMaQuanLy.trim() !== '' || this.txtTenQuanLy.trim() !== '') {
        predicate = new Predicate('TEN_HT', 'contains', this.search_dt.trim(), true)
        let filteredList = new DataManager(this.dsDoiTuong_tree).executeLocal(new Query().where(predicate))
        for (let j = 0; j < filteredList.length; j++) {
          _filter.push(filteredList[j][child])
          let filters = this.getFilterItems(filteredList[j], this.dsDoiTuong_tree)
          for (let i = 0; i < filters.length; i++) {
            if (_array.indexOf(filters[i]) === -1 && filters[i] != null) {
              _array.push(filters[i])
              predicats.push(new Predicate(child, 'equal', filters[i], false))
            }
          }
        }
        if (predicats.length === 0) {
          this.changeDataSource([])
          this.loading(false);
        } else {
          let query = new Query().where(Predicate.or(predicats))
          let newList = new DataManager(this.dsDoiTuong_tree).executeLocal(query)
          this.changeDataSource(newList)
          setTimeout(() => {
            this.$refs.treeObjGan.expandAll()
          }, 100)
          this.loading(false);
        }
      } else {
        this.changeDataSource(this.dsDoiTuong_tree)
        this.loading(false);
      }
    },
getFilterItems (fList, list) {
      let nodes = []
      var matinh = this.$root.token.getMaTinh();
      var parent='DONVI_CHA_ID';
      var child='DONVI_ID';
      nodes.push(fList[child])
      let query2 = new Query().where(child, 'equal', fList[parent], false)
      let fList1 = new DataManager(list).executeLocal(query2)
      if (fList1.length !== 0) {
        let pNode = this.getFilterItems(fList1[0], list)
        for (let i = 0; i < pNode.length; i++) {
          if (nodes.indexOf(pNode[i]) === -1 && pNode[i] != null) {
            nodes.push(pNode[i])
          }
        }
        return nodes
      }
      return nodes
    },

  },
};
</script>
<style>
li.non-active a {
  color: #d3d3d3 !important;
}

@import "https://cdn.syncfusion.com/ej2/material.css";
</style>
