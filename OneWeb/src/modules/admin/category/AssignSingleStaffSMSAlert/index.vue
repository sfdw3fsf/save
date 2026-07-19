<template src="./template.html"></template>
<script>
import Select2Ext from "@/modules/contract/setup/DeclareLandline/components/Select2Ext";
import DataGridCustom from "@/modules/contract/setup/DeclareLandline/components/DataGridCustom";
import ComboboxGridCustom from "@/modules/contract/setup/DeclareLandline/components/ComboboxGridCustom";
import VueDateExt from "@/modules/contract/setup/DeclareLandline/components/VueDateExt";
import TreeView from "@/modules/contract/setup/DeclareLandline/components/TreeView";
import $ from "jquery";
import moment from "moment";
var rs;
export default {
  components: {
    Select2Ext,
    DataGridCustom,
    ComboboxGridCustom,
    VueDateExt,
    TreeView,
  },
  computed: {
    cboKieuCB_dataSource_remake() {
      return this.cboKieuCB_dataSource.map((item) => ({
        label: item.kieu_cb,
        id: item.kieucb_id,
      }));
    },
  },
  data() {
    return {
      //region Khai báo
      cboKieuCB_dataSource: [],
      cboKieuCB: [],
      donvi_dataSource: [],
      selectedNodes: [0],
      expandedNodes_temp: [],
      expandedNodes: null,
      fields: "",
      postData: [],
      filterDonvi: [],
      txtLocDonVi: "",
      collums_lt: [
        {
          fieldName: "cap_tn",
          headerText: "Cấp tin nhắn",
          allowFiltering: true,
          allowSorting: false,
          width: "auto",
          textAlign: "left",
        },
        {
          fieldName: "loai_tn",
          headerText: "Loại tin nhắn",
          allowFiltering: true,
          allowSorting: false,
          width: "auto",
          textAlign: "left",
          isGroupedColumn: true,
        },
      ],
      collums_dv: [
        {
          fieldName: "ten_dv",
          headerText: "Đơn vị",
          allowFiltering: true,
          allowSorting: false,
          width: "auto",
          textAlign: "left",
        },
      ],
      collums_nvdg: [
        {
          fieldName: "stt",
          headerText: "STT",
          allowFiltering: true,
          allowSorting: false,
          width: "auto",
          textAlign: "left",
        },
        {
          fieldName: "ma_nv",
          headerText: "Mã nhân viên",
          allowFiltering: true,
          allowSorting: false,
          width: "auto",
          textAlign: "left",
        },
        {
          fieldName: "ten_nv",
          headerText: "Tên nhân viên",
          allowFiltering: true,
          allowSorting: false,
          width: "auto",
          textAlign: "left",
        },
      ],
      collums_nv: [
        {
          fieldName: "stt",
          headerText: "STT",
          allowFiltering: true,
          allowSorting: false,
          width: "auto",
          textAlign: "left",
        },
        {
          fieldName: "ma_nv",
          headerText: "Mã nhân viên",
          allowFiltering: true,
          allowSorting: false,
          width: "auto",
          textAlign: "left",
        },
        {
          fieldName: "ten_nv",
          headerText: "Tên nhân viên",
          allowFiltering: true,
          allowSorting: false,
          width: "auto",
          textAlign: "left",
        },
        {
          fieldName: "ma_dv",
          headerText: "Mã đơn vị",
          allowFiltering: true,
          allowSorting: false,
          width: "auto",
          textAlign: "left",
        },
        {
          fieldName: "chucdanh",
          headerText: "Chức danh",
          allowFiltering: true,
          allowSorting: false,
          width: "auto",
          textAlign: "left",
        },
        {
          fieldName: "vitri",
          headerText: "Vị trí",
          allowFiltering: true,
          allowSorting: false,
          width: "auto",
          textAlign: "left",
        },
      ],
      KhoCgCache: [],
      DviCache: [],
      KhoCgIndex: 0,
      DviIndex: 0,
      KhoCgWord: "",
      DviWord: "",
      vdonvi_id: "-1",
      dsKieuCB: [],
      loaitn_id: 0,
      currentFindIndex: 0,
      vnhanvien_dagan_id: "-1",
      vnhanvien_id: "-1",
      vcaptn_id: "-1",
      vtinnhan_id: "-1",
      vma_nv_dagan: "-1",
    };
    //endregion

    //region Khai báo thuộc tính
  },
  async mounted() {
    this.$refs.dtpNhanTin_Tu.disabled = true;
    this.$refs.dtpNhanTin_Den.disabled = true;
    this.loading(true)
    await this.frmQLTinNhanV2_FormLoad();
    this.loading(false)
  },
  methods: {
    //region Hàm
    async NAP_DS_DONVI() {
      return new Promise(async (resolve, reject) => {
        try {
          // Xóa cache
          this.DviIndex = 0;
          this.DviWord = null;
          this.DviCache = null;

          rs = await this.axios.post(
            "web-quantri/quanlytinnhanv2/sp_ql_tinnhan_dsdonvi"
          );
          this.donvi_dataSource = rs.data.data.map((item) => ({
            ...item,
            donvi_cha_id:
              rs.data.data.filter((x) => x.id == item.donvi_cha_id).length > 0
                ? item.donvi_cha_id
                : null,
            hasChildren:
              rs.data.data.filter((x) => x.donvi_cha_id == item.id).length > 0
                ? true
                : false,
          }));

          this.selectedNodes = [0];

          // Chọn VNPT tỉnh
          // var node = tvDonVi.FindNodeByFieldValue("DONVI_ID", 0);
          // if (node != null)
          // {
          //     tvDonVi.MakeNodeVisible(node);
          //     node.Expanded = true;

          //     tvDonVi.FocusedNode = node;
          // }
        } catch (err) {
          this.$toast.error("Lỗi khi nạp danh sách đơn vị\r\n" + err.message);
        }
        resolve(true)
      })
    },

    async NAP_DS_NHANVIEN() {
      return new Promise( async (resolve, reject) => {
        try {
          this.$refs.grdNhanVien.dataSource = [];

          rs = await this.axios.post(
            "web-quantri/quanlytinnhanv2/sp_get_nhanvien_donvi",
            {
              donvi_id: this.vdonvi_id,
              kieu: 1,
            }
          );
          this.$refs.grdNhanVien.dataSource = rs.data.data;
        } catch (err) {
          this.$toast.error("Lỗi khi nạp danh sách nhân viên\r\n" + err.message);
        }
        resolve(true)
      })
    },

    async NAP_DS_NHANVIEN_DAGAN() {
      try {
        rs = await this.axios.post(
          "web-quantri/quanlytinnhanv2/sp_getnhanvien_donvi_dagan_tn",
          {
            tinnhan_id: this.vtinnhan_id,
            donvi_id: this.vdonvi_id,
            kieu: 1,
          }
        );
        this.$refs.grdNhanVienDaGan.dataSource = rs.data.data.map(
          (item, i) => ({
            ...item,
            stt: i + 1,
          })
        );

        if (this.$refs.grdNhanVienDaGan.dataSource.length <= 0) {
          this.$refs.chkTuNgay.checked = false;
          this.$refs.dtpNhanTin_Tu.value = "";

          this.$refs.chkDenNgay.checked = false;
          this.$refs.dtpNhanTin_Den.value = "";

          rs = await this.axios.post(
            "web-quantri/quanlytinnhanv2/sp_ql_tinnhan_kieucb"
          );

          this.dsKieuCB = rs.data.data;
          this.cboKieuCB_dataSource = this.dsKieuCB;
          //this.HienThi_CheckBox_Combobox(this.$refs.cboKieuCB, this.dsKieuCB, "kieucb_id", "kieu_cb", "chon", "1");
        }
      } catch (err) {
        console.log(err);
        this.$toast.error("Lỗi khi nạp danh sách nhân viên\r\n" + err.message);
      }
    },

    async NAP_DS_LOAI_TIN() {
      try {
        rs = await this.axios.post(
          "/web-quantri/quanlytinnhanv2/sp_ql_tinnhan_loaitin",
          {
            loaitn: this.loaitn_id,
          }
        );

        this.$refs.grvLoaiTin.dataSource = [
          ...new Map(
            rs.data.data.map((item) => [item["tinnhan_id"], item])
          ).values(),
        ];
      } catch (err) {
        this.$toast.error(err.message);
        console.log(err);
      }
      //(grcLoaiTin.MainView as DevExpress.XtraGrid.Views.Grid.GridView).BestFitColumns();
    },

    async NAP_DONVI_DULIEU() {
      try {
        return new Promise(async (resolve, reject) => {
          rs = await this.axios.post(
            "web-quantri/quanlytinnhanv2/sp_ql_tinnhan_donvi",
            {
              tinnhan_id: this.vtinnhan_id,
              captn_id: this.vcaptn_id,
              kieu: 0,
              nhanvien_id: this.vnhanvien_id,
            }
          );
          this.$refs.gridHienTai.dataSource = rs.data.data;

          rs = await this.axios.post(
            "web-quantri/quanlytinnhanv2/sp_ql_tinnhan_donvi",
            {
              tinnhan_id: this.vtinnhan_id,
              captn_id: this.vcaptn_id,
              kieu: 1,
              nhanvien_id: this.vnhanvien_id,
            }
          );
          this.$refs.gridKeTiep.dataSource = rs.data.data;
          resolve(true);
        });
      } catch (err) {
        console.log(err);
      }
    },

    //endregion

    //region Events

    async frmQLTinNhanV2_FormLoad() {
      return new Promise(async (resolve, reject) => {
        let Tag = this.$route.query.tag;
        if (Tag && Tag != "") {
          let words = Tag;
          if (words.length >= 1) this.loaitn_id = Number(words);
        }

        await this.NAP_DS_DONVI();
        await this.NAP_DS_LOAI_TIN();

        rs = await this.axios.post(
          "web-quantri/quanlytinnhanv2/sp_ql_tinnhan_kieucb"
        );
        this.dsKieuCB = rs.data.data;
        this.cboKieuCB_dataSource = this.dsKieuCB;
        resolve(true)
      })
      //this.HienThi_CheckBox_Combobox(this.$refs.cboKieuCB, this.dsKieuCB, "kieucb_id", "kieu_cb", "chon", "1");
    },

    tvDonVi_FocusedNodeChanged(data) {
      this.vdonvi_id = data.nodeData.id;
      this.NAP_DS_NHANVIEN();
    },
    changeDataSource(data) {
      this.fields = {
        dataSource: data,
        id: "donvi_id",
        text: "ten_dv",
        parentID: "donvi_cha_id",
        hasChildren: "hasChildren",
      };
    },

    async grdNhanVien_FocusedRowChanged(data) {
      this.vnhanvien_id = data.data.nhanvien_id;
      await this.NAP_DONVI_DULIEU();
    },
    txtLocDonVi_EditValueChanged() {
      this.currentFindIndex = 0;
    },

    txtLocDonVi_KeyUp() {
      try {
        if (!this.$refs.txtLocDonVi.value) return;
        if (this.$refs.txtLocDonVi.value == this.txtLocDonVi) {
          if (this.filterDonvi.length != 0) {
            this.expandedNodes_temp = [];
            this.pushDonviCha(this.filterDonvi[0]);
            this.expandedNodes = this.expandedNodes_temp;
            this.selectedNodes = [this.filterDonvi[0]["donvi_id"]];
            // $('#treeDonvi').animate({
            //      scrollTop: $(".e-active").offset().top
            // }, 900);
            $("#treeDonvi").animate(
              {
                scrollTop:
                  $("#treeDonvi").scrollTop() -
                  $("#treeDonvi").offset().top +
                  $(".e-active").offset().top,
              },
              500
            );
            this.filterDonvi.splice(0, 1);
          } else {
            this.$toast.error(
              "Đã duyệt hết " + this.filterDonvi.length + " đơn vị phù hợp!"
            );
          }
        } else {
          this.txtLocDonVi = this.$refs.txtLocDonVi.value;
          var term = this.$refs.txtLocDonVi.value;
          var search = new RegExp(term, "i");
          this.filterDonvi = this.donvi_dataSource.filter((item) =>
            search.test(item.ten_dv)
          );
          this.txtLocDonVi_KeyUp();
        }
      } catch (err) {
        console.log(err);
        this.$toast.error("Không tìm thấy đơn vị phù hợp");
      }
    },

    async txtLocMaND_KeyUp() {
      try {
        var ma_nd = this.$refs.txtLocMaND.value;

        rs = await this.axios.post(
          "web-quantri/gankhonv/sp_grv_nhanvien_mand",
          {
            p_pagesize: 100,
            p_ma_nd: ma_nd,
            p_pagenumber: 1,
          }
        );
        var ds = rs.data.data;
        if (ds.length != 0) {
          var ma_nv = ds[0]["ma_nv"];
          var donvi_id = ds[0]["donvi_id"];
          let node = this.donvi_dataSource.filter(
            (x) => x["donvi_id"] == donvi_id
          );

          if (node.length != 0) {
            this.expandedNodes_temp = [];
            this.pushDonviCha(node[0]);
            this.expandedNodes = this.expandedNodes_temp;
            this.selectedNodes = [node[0]["donvi_id"]];
            let tree = document.getElementById("treeDonvi");
            tree.getElementsByClassName("e-node-focus")[0].scrollIntoView();
          } else {
            this.$toast.error(
              "Không tìm thấy đơn vị của người dùng, hãy nhấn nút Làm mới và thử lại"
            );
          }
        } else {
          this.$toast.error(
            "Người dùng với mã " +
              this.$refs.txtLocMaND.value +
              " không tồn tại"
          );
        }
      } catch (err) {
        this.$toast.error(
          "Lỗi khi tìm kiếm nhân viên theo mã người dùng\r\n" + err.message
        );
      }
    },

    txtLocMaNV_EditValueChanged() {},

    async txtLocMaNV_KeyUp() {
      try {
        var ma_nv = this.$refs.txtLocMaNV.value;
        rs = await this.axios.post("/web-quantri/lay-dulieu/sp_tt_nhanvien",{ param: ma_nv.replaceAll("'", "''"), type: 1 });
        var ds = rs.data.data;

        if (ds.length != 0) {
          var donvi_id = ds[0]["donvi_id"];
          let node = this.donvi_dataSource.filter(
            (x) => x["donvi_id"] == donvi_id
          );

          if (node.length != 0) {
            this.expandedNodes_temp = [];
            this.pushDonviCha(node[0]);
            this.expandedNodes = this.expandedNodes_temp;
            this.selectedNodes = [node[0]["donvi_id"]];
            let tree = document.getElementById("treeDonvi");
            tree.getElementsByClassName("e-node-focus")[0].scrollIntoView();
          } else {
            this.$toast.error(
              "Không tìm thấy đơn vị của nhân viên, hãy nhấn nút Làm mới và thử lại"
            );
          }
        } else {
          this.$toast.error(
            "Nhân viên với mã " + this.$refs.txtLocMaNV.value + " không tồn tại"
          );
        }
      } catch (err) {
        console.log(err);
        this.$toast.error(
          "Lỗi khi tìm kiếm nhân viên theo mã\r\n" + err.message
        );
      }
    },

    HienThi_CheckBox_Combobox(
      cbo,
      ds,
      valuefield,
      viewfield,
      checkedfield,
      checkedvalue
    ) {
      cbo.dataSource = ds;
    },

    async grvLoaiTin_FocusedRowChanged(data) {
      this.vcaptn_id = data.data.captn_id;
      this.vtinnhan_id = data.data.tinnhan_id;
      await this.NAP_DONVI_DULIEU();

      this.NAP_DS_NHANVIEN_DAGAN(this.vtinnhan_id);
    },

    async TaoDuLieuPost() {
      return new Promise((resolve, reject) => {
        this.$refs.gridKeTiep.dataSource.forEach(async (item) => {
          let rs = await this.axios.post(
            "web-hopdong/khoiphucthanhly/get_keys",
            {
              keyname: "NHANVIENTN_ID",
            }
          );
          this.postData.push({
            "DONVI_ID": item.donvi_id,
            "IP_CN": "1.1.1.1",
            "KIEUCB_ID": this.cboKieuCB.join(","),
            "MAY_CN": this.$root.token.getMaNhanVien(),
            "NGAY_CN": moment().format("DD/MM/YYYY HH:mm:ss"),
            "NGUOI_CN": this.$root.token.getNhanVienID(),
            "NHANTIN_DEN": this.$refs.dtpNhanTin_Tu.value
              ? this.$refs.dtpNhanTin_Tu.value
              : moment().format("DD/MM/YYYY HH:mm:ss"),
            "NHANTIN_TU": this.$refs.dtpNhanTin_Den.value
              ? this.$refs.dtpNhanTin_Den.value
              : moment().format("DD/MM/YYYY HH:mm:ss"),
            "NHANVIENTN_ID": Number(rs.data.data),
            "NHANVIEN_ID": this.vnhanvien_id,
            "TINNHAN_ID": this.vtinnhan_id
          });
        });

        resolve(true);
      });
    },
    async tsbtnGhiLai_Click() {
      try {
        let kt = false;

        // for (let i = 0; i < this.$refs.cboKieuCB.dataSource.length; i++)
        // {
        //     if (this.$refs.cboKieuCB.dataSource[i].CheckState == this.CheckState.checked)
        //     {
        //         kt = true;
        //         break;
        //     }
        // }
        this.postData = [];
        if (this.cboKieuCB && this.cboKieuCB.length == 0) {
          this.$toast.error("Hãy chọn Kiểu cảnh báo");
          return;
        }

        if (this.$refs.gridKeTiep.dataSource.length <= 0) {
          this.$toast.error("Hãy chọn đơn vị cần cảnh báo");
          return;
        }

        await this.axios.post(
          "web-quantri/quanlytinnhanv2/fn_nhanvien_tn_delete",
          {
            nhanvien_id: this.vnhanvien_id,
            tinnhan_id: this.vtinnhan_id,
          }
        );
        await this.TaoDuLieuPost();
        setTimeout(async () => {
          await this.axios.post(
            "web-quantri/quanlytinnhanv2/sp_nhanvien_tn_update",
            {
              data: this.postData,
            }
          );

          await this.axios.post(
            "web-quantri/quanlytinnhanv2/delete_dhsx_nhanvientn_kcb",
            {
              nhanvien_id: this.vnhanvien_id,
              tinnhan_id: this.vtinnhan_id,
            }
          );

          // qldv.exeSQL(string.Format(@"delete {0}.nhanvientn_kcb where nhanvien_id={1} and tinnhan_id={2}",
          //             DatabaseConstants.DB10, vnhanvien_id, vtinnhan_id));

          for (let i = 0; i < this.cboKieuCB.length; i++) {
            await this.axios.post(
              "web-quantri/quanlytinnhanv2/insert_dhsx_nhanvientn_kcb",
              {
                kieucb_id: this.cboKieuCB[i],
                nhanvien_id: this.vnhanvien_id,
                tinnhan_id: this.vtinnhan_id,
              }
            );
          }

          this.NAP_DONVI_DULIEU();

          this.NAP_DS_NHANVIEN_DAGAN(this.vtinnhan_id);

          this.loading(false);
          this.$toast.success("Cập nhật thành công!");
        }, 100);

        //qldv.exeSQL("delete " + DatabaseConstants.DB10 + @".NHANVIEN_TN where nhanvien_id = " + vnhanvien_id + " and tinnhan_id=" + vtinnhan_id);

        // qlnv.Xoa_dl_nhanvien_tn(vnhanvien_id, vtinnhan_id);

        // this.StartWait();
        // DataTable dtTemp = gridKeTiep.dataSource as DataTable;
        // if (!dtTemp.Columns.Contains("NHANVIENTN_ID_MOI"))
        //     dtTemp.Columns.Add("NHANVIENTN_ID_MOI", typeof(long));
        // long gp_id = new TruyenDanFacade().GET_KEYS_JUMP("NHANVIENTN_ID", dtTemp.length);

        // for (int i = 0; i < dtTemp.length; i++)
        // {
        //     dtTemp[i]["NHANVIENTN_ID_MOI"] = gp_id;
        //     gp_id++;
        // }

        // qlnv.Capnhat_dl_nhanvien_tn(dtTemp, vnhanvien_id, vtinnhan_id,
        //         chkTuNgay.checked ? dtpNhanTin_Tu.DateTime : "",
        //         chkDenNgay.checked ? dtpNhanTin_Den.DateTime : "",
        //         tt_nd.may_cn, tt_nd.ma_nd, tt_nd.ip);
      } catch (err) {
        this.loading(false);
        console.log(err);
        this.$toast.error("Có lỗi xảy ra");
      }
    },

    btnGo_Click() {
      try {
        // gridviewHienTai.CloseEditor();
        // var dtDonViChuaGan = gridHienTai.dataSource as DataTable;
        // var dtDonViDaGan = gridKeTiep.dataSource as DataTable;
        // dtDonViChuaGan.AcceptChanges();
        // var tmp = dtDonViChuaGan.Select("CHON='s1'");
        // foreach (DataRow item in tmp)
        // {
        //     DataRow dt = dtDonViDaGan.NewRow();
        //     dt["DONVI_ID"] = item["DONVI_ID"];
        //     dt["TEN_DV"] = item["TEN_DV"];
        //     dt["CHON"] = "s0";
        //     dtDonViDaGan.Add(dt);
        //     dtDonViChuaGan.Remove(item);
        // }
        // gridHienTai.dataSource = dtDonViChuaGan;
        // gridKeTiep.dataSource = dtDonViDaGan;
        let selected = this.$refs.gridHienTai.getSelectedRecords();
        this.$refs.gridKeTiep.dataSource = selected.concat(
          this.$refs.gridKeTiep.dataSource
        );
        selected.forEach((item) => {
          const index = this.$refs.gridHienTai.dataSource.indexOf(item);
          this.$refs.gridHienTai.dataSource.splice(index, 1);
        });
      } catch (err) {}
    },

    btnGan_Click() {
      try {
        let selected = this.$refs.gridKeTiep.getSelectedRecords();
        this.$refs.gridHienTai.dataSource = selected.concat(
          this.$refs.gridHienTai.dataSource
        );
        selected.forEach((item) => {
          const index = this.$refs.gridKeTiep.dataSource.indexOf(item);
          this.$refs.gridKeTiep.dataSource.splice(index, 1);
        });
      } catch (err) {}
    },

    tsbtnThoat_Click() {
      this.Close();
    },
    //endregion

    btnTroGiup_Click() {
      this.HuongDanSD_Show("Hưỡng dẫn sử dụng", Size.Empty);
    },

    chkTuNgay_CheckedChanged() {
      this.$refs.dtpNhanTin_Tu.disabled = !this.$refs.chkTuNgay.checked;
    },

    chkDenNgay_CheckedChanged() {
      this.$refs.dtpNhanTin_Den.disabled = !this.$refs.chkDenNgay.checked;
    },

    pushDonviCha(node) {
      if (node.donvi_cha_id != null) {
        let data = this.donvi_dataSource.filter(
          (x) => x.donvi_id == node.donvi_cha_id
        );
        if (data.length > 0) {
          this.expandedNodes_temp.push(data[0].donvi_id);
          this.pushDonviCha(data[0]);
        }
      }
    },

    async grdNhanVienDaGan_FocusedRowChanged(data) {
      this.vnhanvien_dagan_id = data.data.nhanvien_id;
      this.vma_nv_dagan = data.data.ma_nv;
      if (this.$refs.grdNhanVienDaGan.length <= 0) return;

      this.$refs.txtLocMaNV.value = this.vma_nv_dagan;
      rs = await this.axios.post("/web-quantri/lay-dulieu/sp_tt_nhanvien",{ param: this.vnhanvien_dagan_id, type: number });
      let ds = rs.data.data;

      if (ds.length != 0) {
        let donvi_id = ds[0]["donvi_id"];
        let node = this.donvi_dataSource.filter(
          (x) => x["donvi_id"] == donvi_id
        );

        if (node.length != 0) {
          //tvDonVi.FocusedNode = node[0];
          this.expandedNodes_temp = [];
          this.pushDonviCha(node[0]);
          this.expandedNodes = this.expandedNodes_temp;
          this.selectedNodes = [node[0]["donvi_id"]];
          let tree = document.getElementById("treeDonvi");
          tree.getElementsByClassName("e-node-focus")[0].scrollIntoView();

          this.changeDataSource(this.donvi_dataSource);

          // this.expandedNodes = [0],
          // console.log(node)
          // //this.selectedNodes  = [Number(node[0]['donvi_id'])]

          //this.$refs.tvDonVi.expandedNodes = [node[0]['donvi_id']]

          for (let i = 0; i < this.$refs.grdNhanVien.dataSource.length; i++) {
            if (
              this.$refs.grdNhanVien.dataSource[i].nhanvien_id ==
              this.vnhanvien_dagan_id
            ) {
              this.$refs.grdNhanVien.$refs.DataGridCustom.setCurrentSelectedRow(
                i
              );
              break;
            }
          }
        }
      }

      rs = await this.axios.post(
        "/web-quantri/lay-dulieu/sp_tt_nhanvien_tn",
        { 
          "param": this.vnhanvien_dagan_id,
          "param1": this.vtinnhan_id,
          "type": 1
        });

      let ds2 = rs.data.data;
      if (ds2.length != 0) {
        if (ds2[0]["nhantin_tu"]) {
          this.$refs.chkTuNgay.checked = true;
          this.$refs.dtpNhanTin_Tu.value = ds2[0]["nhantin_tu"];
        } else {
          this.$refs.chkTuNgay.checked = false;
          this.$refs.dtpNhanTin_Tu.value = "";
        }

        if (ds2[0]["nhantin_den"]) {
          this.$refs.chkDenNgay.checked = true;
          this.$refs.dtpNhanTin_Den.value = ds2[0]["nhantin_den"];
        } else {
          this.$refs.chkDenNgay.checked = false;
          this.$refs.dtpNhanTin_Den.value = "";
        }
      }

      rs = await this.axios.post(
        "web-quantri/quanlytinnhanv2/sp_ql_tinnhan_kieucb"
      );
      this.dsKieuCB = rs.data.data;
      this.cboKieuCB_dataSource = this.dsKieuCB;
      //this.HienThi_CheckBox_Combobox(this.$refs.cboKieuCB, this.dsKieuCB, "kieucb_id", "kieu_cb", "chon", "1");
    },

    xoaNhanVien_Click() {
      try {
        //     if (MessageBox.Show("Bạn thật sự muốn xóa nhân viên này khỏi danh sách cảnh báo không?", "Thông báo",
        //                 MessageBoxButtons.YesNo, MessageBoxIcon.Information) != DialogResult.Yes)
        //         return;
        //     qlnv.Xoa_dl_nhanvien_tn(vnhanvien_dagan_id, vtinnhan_id);
        //     qldv.exeSQL(string.Format(@"delete {0}.nhanvientn_kcb where nhanvien_id={1} and tinnhan_id={2}",
        //                                 DatabaseConstants.DB10, vnhanvien_dagan_id, vtinnhan_id));
        //     NAP_DONVI_DULIEU();
        //     NAP_DS_NHANVIEN_DAGAN(vtinnhan_id);
        //     Message_Box.ShowTB("Xóa dữ liệu thành công!");
      } catch (err) {
        this.$toast.error(err.message);
        console.log(err);
      }
    },

    frmQLTinNhanV2_Load() {},
  },
};
</script>
<style>
.tree-plus ul li.e-level-1:before {
  border-top: 0px !important;
}
</style>