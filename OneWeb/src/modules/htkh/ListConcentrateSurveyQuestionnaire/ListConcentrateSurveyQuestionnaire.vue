<template src="./ListConcentrateSurveyQuestionnaire.html"></template>
<style src="./ListConcentrateSurveyQuestionnaire.scss"></style>

<script>
import breadcrumb from '@/components/breadcrumb'
import { DataManager, Predicate, Query } from '@syncfusion/ej2-data'
//import DepartmentAPI from './API.js'
//import API from '../../admin/category/AssignStaffLocation/API'
import API from '../../htkh/ListConcentrateSurveyQuestionnaire/API'
import ModalXuatDuLieu from "./popup/ModalXuatDuLieu";
import DateTimeLib from '../../../utils/DateTimeLib'
import ModalDSBD from './popup/DanhSachBienDong'

export default {
  name: 'Department',
  components: {
    breadcrumb,ModalXuatDuLieu,ModalDSBD
  },
  data () {
    return {
      header: {
        title: 'Danh sách phiếu khảo sát tập trung',
        list: [
          {
            name: 'Chăm sóc khách hàng',
            link: {name: 'Ui.cards'},
            active: false
          },
          {
            name: 'Danh sách phiếu khảo sát tập trung',
            link: {name: 'Ui.buttons'},
            active: true
          }
        ]
      },
      txtTimkiem: 'Tìm kiếm',
      cboTrangThaiPhieu: '',
      DSTrangthai: [
        {ID: 1, TRANGTHAI: 'Chưa giao'},
        {ID: 2, TRANGTHAI: 'Đang xử lý'},
        {ID: 3, TRANGTHAI: 'Hệ thống Bỏ qua'},
        {ID: 6, TRANGTHAI: 'Đã khảo sát'},
        {ID: 7, TRANGTHAI: 'Tồn'},
        {ID: 8, TRANGTHAI: 'KSV Bỏ qua'},
        {ID: 0, TRANGTHAI: 'Toàn bộ'},
      ],
      // Nhân viên
      gridColumnsNVGiaoViec: [
        {
          fieldName: "ma_nd",
          headerText: "Mã ND",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "ten_nv",
          headerText: "Tên nhân viên",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "ten_dv",
          headerText: "Đơn vị",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "thanhcong",
          headerText: "Thành công",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "thatbai",
          headerText: "Thất bại",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "tong",
          headerText: "Tổng",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "tyle",
          headerText: "Tỉ lệ",
          allowFiltering: true,
          allowSorting: true,
        },
      ],
      gridNVGiaoViec: [],
      rowNVGiaoViecSelected: {},

      // Danh sách phiếu
      gridColumnsDanhSach: [
        {
          fieldName: "ma_tinh",headerText: "Tỉnh",allowFiltering: true,allowSorting: true,freeze: "left"
        },
        {
          fieldName: "ma_tb",headerText: "Số máy/Acc",allowFiltering: true,allowSorting: true,freeze: "left"
        },
        {
          fieldName: "trangthai_xuly",headerText: "Trạng thái",allowFiltering: true,allowSorting: true,
        },
        {
          fieldName: "ngay_ht",headerText: "Ngày hoàn thành",allowFiltering: true,allowSorting: true,
        },
        {
          fieldName: "loaihinh_tb",headerText: "Loại hình",allowFiltering: true,allowSorting: true,
        },
        {
          fieldName: "henks_tu",headerText: "Giờ hẹn đầu",allowFiltering: true,allowSorting: true,
        },
        {
          fieldName: "henks_den",headerText: "Giờ hẹn cuối",allowFiltering: true,allowSorting: true,
        },
        {
          fieldName: "ma_nd",headerText: "Nhân viên thực hiện",allowFiltering: true,allowSorting: true,
        },
        {
          fieldName: "ngay_cn",headerText: "Ngày CN",allowFiltering: true,allowSorting: true,
        },
        {
          fieldName: "loaiphieu",headerText: "Nguồn",allowFiltering: true,allowSorting: true,
        },
        {
          fieldName: "ngay_yc",headerText: "Ngày YC",allowFiltering: true,allowSorting: true,
        },
        {
          fieldName: "ghichu",headerText: "Ghi chú",allowFiltering: true,allowSorting: true,
        },
        {
          fieldName: "donvi_tn",headerText: "Đơn vị tiếp nhận",allowFiltering: true,allowSorting: true,
        },
        {
          fieldName: 'thuebao_id', headerText: '', visible: false
        },
		    {
          fieldName: 'phieu_id', headerText: '', visible: false
        },
        {
          fieldName: 'loai_id', headerText: '', visible: false
        },
        {
          fieldName: 'key', headerText: '', visible: false, isPrimaryKey: true
        },
        {
          fieldName: 'hdkh_id', headerText: '', visible: false
        },
      ],
      gridDanhSach: [],
      rowSelected: {},
      arrRowSelected: [],

      // Đơn Vị
      donviVaule: [],
      listItemDonVi: [],
      listItemDonViGoc: [],
      //

      lichSuKSUrl: '#',

      today: new Date(),
      dtpStartDate: new Date(),
      dtpEndDateSearch: new Date(),

      ds_bd:{
        list: [],
        header:  [
          {fieldName:'ma_gd',headerText:'Mã GD',allowFiltering: true},
          {fieldName:'ngay_yc',headerText:'Ngày yêu cầu',allowFiltering: true, type: "Date", format: "dd/MM/yyyy hh:mm a"},
          {fieldName:'trangthai_hd',headerText:'Trạng thái',allowFiltering: true},
          {fieldName:'ten_loaihd',headerText:'Loại hợp đồng',allowFiltering: true},
          {fieldName:'ten_kieuld',headerText:'Kiếu lắp đặt',allowFiltering: true},
          {fieldName:'ten_dv',headerText:'Đơn vị lập HĐ/PL',allowFiltering: true}
        ],
      },
    }
  },
  computed: {
    dsThuocTinh() {
      return this.gridColumnsDanhSach.map(item=>item.fieldName)
    }
  },
  async mounted() {
    this.loading(true);
    this.cboTrangThaiPhieu = 1; // set default trạng thái

    let nhanvien_id = this.$root.token.getNhanVienID();
    await Promise.all([
      this.Lay_Ds_NguoiDung_Tinh(nhanvien_id, 2),
      this.getDsNhanVien(),
      //this.getDsLoaiHinhTB()
    ])
    .then(() => {
      this.loading(false);
    })
    .catch(() => {
      this.loading(false);
    });
  },
  methods: {
    getDsLoaiHinhTB: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB`
        );

        console.log(rs);

      } catch (error) {}
    },
    async Lay_Ds_NguoiDung_Tinh(nhanVienID, quyen = 2) {
      try {
        let list_tinh = await API.sp_lay_ds_nguoidung_tinh(this.axios,nhanVienID, quyen);
        console.log(list_tinh);

        if (list_tinh.data.error_code === "BSS-00000000" && list_tinh.data.data !== undefined && list_tinh.data.data.length > 0) {
          let arr_tinh = list_tinh.data.data.map(item => item.tinh_id);
          let list_donvi = await API.lay_ds_donvi(this.axios);

          if (list_donvi.data.error_code === "BSS-00000000" && list_donvi.data.data !== undefined && list_donvi.data.data.length > 0) {
            let arr_donvi = list_donvi.data.data;
            arr_donvi = arr_donvi.filter((x) => arr_tinh.indexOf(x.id) != -1);

            let items = [];
            let itemMienBac = [];
            let itemMienTrung = [];
            let itemMienNam = [];

            arr_donvi.forEach(function (item) {
              if (item.vung == 1) { // Miền Bắc
                itemMienBac.push({
                  id: item.ten,
                  label: item.ten + ' - ' + item.ma
                });
              }

              if (item.vung == 2) { // Miền Trung
                itemMienTrung.push({
                  id: item.ten,
                  label: item.ten + ' - ' + item.ma
                });
              }

              if (item.vung == 3) { // Miền Nam
                itemMienNam.push({
                  id: item.ten,
                  label: item.ten + ' - ' + item.ma
                });
              }
            });

            if (itemMienBac.length > 0) {
              items.push({
                id: 'mienbac', label: 'Vùng : Miền Bắc', children : itemMienBac
              })
            }

            if (itemMienTrung.length > 0) {
              items.push({
                id: 'mientrung', label: 'Vùng : Miền Trung', children : itemMienTrung
              })
            }

            if (itemMienNam.length > 0) {
              items.push({
                id: 'miennam', label: 'Vùng : Miền Nam', children : itemMienNam
              })
            }

            this.listItemDonViGoc = items;
          }
        }
      } catch (e) {
        console.log(e);
      }
    },
    async getDsNhanVien() {
      this.loading(true);
      API.lay_ds_nd_dv_con(this.axios, {
        vdonvi_id_cha: this.$root.token.getDonViID()
      })
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            //
            let dtNhanVienGV = response.data.data;
            this.gridNVGiaoViec = dtNhanVienGV;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    onChangeTrangThai: function(arr) {
      console.log(arr);
    },
    onChangeTreeSelect: function (dataitem) {
      console.log('on change');
    },
    removeNoChildren (node) {
      return {
        children: node.children && node.children.length ? node.children : 0
      }
    } ,
    async btnTimKiem_Click() {
      await this.tim_kiem();
    },
    async tsbtnBoQua_Click() {
      try {
        if (this.isEmptyObj(this.rowSelected)) {
          this.$root.toastError("Bạn hãy chọn phiếu trên lưới.")
          return;
        }

        if (this.rowSelected.ma_nd == '') {
          this.$root.toastError("Phiếu cần được giao đi.")
          return;
        }

        await this.$bvModal.msgBoxConfirm("Bạn chắc chắn muốn bỏ qua phiếu cần xử lý không ?", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          "size": "sm"
        })
        .then(async(value) => {
          if (value) {
            let vid = 0;
            // gọi API cập nhật bỏ qua phiếu
            if (this.rowSelected.loai_id == 1) {
               vid = this.rowSelected.phieu_id;
            } else if (this.rowSelected.loai_id == 2) {
               vid = this.rowSelected.hdkh_id;
            }

            await API.sp_boqua_chiaphieu(this.axios, {
              vloai_id: this.rowSelected.loai_id,
              vid: vid
            })
              .then(async (response) => {
                console.log("sp_boqua_chiaphieu",response);
                if (response.data.error_code === "BSS-00000000" ) {
                  this.$root.toastSuccess("Cập nhập thành công!")
                  this.btnTimKiem_Click();
                }
              })
              .catch((error) => {
                this.$root.toastError("Có lỗi xảy ra :" + error)
              })
              .finally(() => {

              });
          }
        })
        .catch(err => {
          // An error occurred
        });

      } catch (e) {
        console.log(e)
        this.$toast.error('Không load được danh sách đơn vị cha')
      } finally {
        this.loading(false)
      }
    },
    getContextMenuItemsSuCoTmp() {
      return [
        {text: 'Giao phiếu', target: '.e-content', id: 'ctx-menu-giao-phieu'},
        {text: 'Đánh dấu bỏ qua', target: '.e-content', id: 'btnIgnore_Click'},
        {text: 'Thuê bao không khảo sát', target: '.e-content', id: 'btnBlackList_Click'},
      ]
    },
    async onContextMenuClick(args) {
      if(args.item.id === 'ctx-menu-giao-phieu') {
        await this.giao_viec();
      } else if (args.item.id === 'btnIgnore_Click') {
        await this.danh_dau_bo_qua()
      } else if (args.item.id === 'btnBlackList_Click') {
        await this.thue_bao_khong_khao_sat()
      }
    },
    // đánh dấu bỏ qua
    async danh_dau_bo_qua() {
      console.log(this.rowSelected);

      if (this.isEmptyObj(this.rowSelected)) {
        this.$root.toastError("Bạn hãy chọn phiếu trên lưới.")
        return;
      }

      this.loading(true);

      API.sp_boqua_giaophieu_cskh(this.axios, {
        vloai_id: this.rowSelected.loai_id,
        v_nguoi_cn: this.$root.token.getUserName(),
        vid: this.rowSelected.loai_id
      })
      .then((response) => {
        if (response.data.error_code === "BSS-00000000") // && response.data.data !== undefined && response.data.data.length > 0)
        {
          this.$root.toastSuccess("Cập nhập thành công!")
          this.btnTimKiem_Click();
        }
      })
      .catch((error) => {
        this.$root.toastSuccess(error)
        console.log(error);
      })
      .finally(() => {
        this.loading(false);
      });
    },
    // thuê bao không khảo sát
    async thue_bao_khong_khao_sat() {
      console.log(this.rowSelected);

      if (this.isEmptyObj(this.rowSelected)) {
        this.$root.toastError("Bạn hãy chọn phiếu trên lưới.")
        return;
      }

      this.loading(true);

      API.sp_them_tb_khong_ks(this.axios, {
        vthuebao_id: this.rowSelected.loai_id,
        vkieu: this.$root.token.getUserName(),
        vnhanvien_id: this.rowSelected.loai_id
      })
        .then((response) => {
          if (response.data.error_code === "BSS-00000000") // && response.data.data !== undefined && response.data.data.length > 0)
          {
            this.$root.toastSuccess("Cập nhập thành công!")
            this.btnTimKiem_Click();
          }
        })
        .catch((error) => {
          this.$root.toastSuccess(error)
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    isEmptyObj (Obj) {
      var isEmptyObj = !Object.keys(Obj).length
      return isEmptyObj
    },
    async giao_viec() {
      /**
       * Hiện tại đang chỉ xử lý cho chọn từng phiếu trên giao diện cũ
       */

      if (this.arrRowSelected.length == 0) {
        this.$root.toastError("Bạn hãy chọn phiếu trên lưới.")
        return;
      }

      if (this.isEmptyObj(this.rowNVGiaoViecSelected)) {
        this.$root.toastError("Bạn hãy chọn nhân viên cần giao.")
        return;
      }

      /*if (this.isEmptyObj(this.rowNVGiaoViecSelected) && this.rowSelected.ma_nd) {
        this.$root.toastError("Phiếu hiện tại đang được xử lý.")
        return;
      }*/

      //this.loading(true);

      let dsHDTB = [];
      let dsBaohong_ID = [];

      this.arrRowSelected.forEach((element, index) => {
        if (element.ma_nd !== '') {
          if (element.loai_id == "1") {
            if (element.hdkh_id > 0) {
              dsHDTB.push(element.phieu_id)
            }
          } else {
            dsBaohong_ID.push(element.phieu_id)
          }
        }
      });

      let flag = true;

      console.log(dsHDTB.toString())
      console.log(dsBaohong_ID.toString())

      if (dsHDTB.length > 0) {
        API.giuphieu_ob_lm_tt_cskh(this.axios, {
          vdshdtb_id: dsHDTB.toString(),
          ma_nd: this.rowNVGiaoViecSelected.ma_nd
        })
          .then((response) => {
            if (response.data.error_code === "BSS-00000000") // && response.data.data !== undefined && response.data.data.length > 0)
            {
              flag = true;
            }
          })
          .catch((error) => {
            flag = false;
            console.log(error);
          })
          .finally(() => {
            this.loading(false);
          });
      }

      if (dsBaohong_ID.length > 0) {
        API.giuphieu_ob_bh_tt_cskh(this.axios, {
          vdsbaohong_id: dsBaohong_ID.toString(),
          ma_nd: this.rowNVGiaoViecSelected.ma_nd
        })
          .then((response) => {
            if (response.data.error_code === "BSS-00000000") // && response.data.data !== undefined && response.data.data.length > 0)
            {
              flag = true;
            }
          })
          .catch((error) => {
            flag = false;
            console.log(error);
          })
          .finally(() => {
            this.loading(false);
          });
      }

      if (flag == true) {
        await this.tim_kiem();
        this.$root.toastSuccess("Cập nhập thành công!")
      } else {
        this.$root.toastError("Đã xảy ra lỗi. Vui lòng thử lại.")
      }
    },
    gridDSDuLieuSelected: async function (args) {
      var data = JSON.parse(JSON.stringify(args.data));
      if (args) {
        this.rowSelected = data;
        this.arrRowSelected = [];
        this.arrRowSelected.push(data);

        console.log(this.arrRowSelected)

        if( typeof this.rowSelected.ma_tb !== 'undefined' && this.rowSelected.ma_tb != null ){
          this.lichSuKSUrl = `/#/cskh/TraCuuThueBaoKhaoSat?maTB=${this.rowSelected.ma_tb}`
        }
      }
    },
    gridNVGiaoViecSelected: async function (args) {
      var data = JSON.parse(JSON.stringify(args.data));
      if (args) {
        this.rowNVGiaoViecSelected = data;
      }

      console.log(data);
      console.log(typeof data);
    },
    // sự kiện này sẽ được kích hoạt sau bất kỳ hành động nào của Grid như chỉnh sửa, phân trang , v.v. chỉnh sửa hộp thoại và “batchsave” để chỉnh sửa hàng loạt.
    gridDSDuLieuComplete() {
      console.log('Complete action');
    },
    // sự kiện change page
    gridDSDuLieuPageChanged(e) {
      console.log('change page');
      /* if (e.pageIndex != this.lastPage) {
        this.lastPage = e.pageIndex;
        this.pageChange = true;
      } else {
        this.pageChange = false;
      } */
    },
    onXuatFileClicked() {
      if (this.gridDanhSach.length <= 0) {
        this.$root.toastError("Không có dữ liệu để xuất!")
        return
      }
      this.gridDanhSach.forEach(e => e.LOAI_HD = e.LOAI_YC)
      this.$bvModal.show('modal-xuat-dulieu')
    },
    tsbtnXuatExcel_Click() {
      if (this.gridDanhSach.length <= 0)
        this.$toast.error("Chưa có dữ liệu để xuất excel!");
      else {
        let excelExportProperties = {
          fileName: "danh-sach-phieu-can-xu-ly.xlsx",
          dataSource: this.gridDanhSach,
        };

        this.$refs.gridDanhSach.excelExport(excelExportProperties);
      }
    },
    async tsbtnDieuLai_Click() {

      if (this.isEmptyObj(this.rowSelected) || this.rowSelected.phieu_id) {
        this.$root.toastError("Hãy chọn một thuê bao đã được khảo sát.")
        return;
      }

      this.loading(true);

      API.sp_dieulai_thuebao_ks(this.axios, {
        vphieu_id: this.rowSelected.phieu_id,
        vloai_id: this.rowSelected.loai_id
      })
      .then((response) => {
        if (response.data.error_code === "BSS-00000000") // && response.data.data !== undefined && response.data.data.length > 0)
        {
          this.$root.toastSuccess("Cập nhập thành công!")
        }
      })
      .catch((error) => {
        this.$root.toastSuccess(error)
        console.log(error);
      })
      .finally(() => {
        this.loading(false);
      });
    },
    async tsBtn_GiaoViecNV_Click() {
      await this.giao_viec();
    },
    async tsbtnChiTiet_Click() {
      try {
        if (this.isEmptyObj(this.rowSelected)) {
          this.$root.toastError("Bạn hãy chọn phiếu trên lưới.")
          return;
        }

        if( typeof this.rowSelected.ma_tb !== 'undefined' && (this.rowSelected.ma_tb == null || this.rowSelected.ma_tb === '')) {
          this.$root.toastError("Không tồn tại mã thuê bao")
          return;
        }

        this.loading(true);

        let params = {
          "ma_tb": this.rowSelected.ma_tb
        };

        let ds =  await API.lay_ds_hd_theo_ma_tb(this.axios, params);
        console.log(ds)
        if (ds.data.error_code === "BSS-00000000" && ds.data.data !== undefined && ds.data.data.length > 0) {
          this.ds_bd.list = ds.data.data;
          console.log(this.ds_bd.list)
          this.$bvModal.show('pop_dsbd');
          this.loading(false);
        } else {
          //await this.Hienthi_DS_HOPDONG();
        }
      } catch (e) {
        console.log(e)
      }
    },
    async search_get_dsbd(obj) {
      console.log(obj)
    },
    async tim_kiem() {
      try {
        console.log(this.donviVaule)
        this.loading(true)
        let startDate = null;
        let endDate = null;

        let trangthai = this.cboTrangThaiPhieu;
        let arrDonvi = this.donviVaule.join();

        if (arrDonvi.length <= 0) {
          this.$root.toastError("Bạn chưa chọn đơn vị.")
          return false;
        }

        if (trangthai == null) {
          this.$root.toastError("Bạn chưa chọn trạng thái.")
          return false;
        }

        if (this.dtpStartDate == null) {
          //this.$root.toastError("Bạn chưa chọn thời gian Từ ngày.")
          //return false;
        } else {
          startDate = DateTimeLib.toDateDisplay(this.dtpStartDate) + ' 00:00:00';
        }

        if (this.dtpEndDateSearch == null) {
          //this.$root.toastError("Bạn chưa chọn thời gian Đến ngày.")
          //return false;
        } else {
          endDate = DateTimeLib.toDateDisplay(this.dtpEndDateSearch) + ' 23:59:59'
        }


        const params = {
          "vtrangthai": parseInt(trangthai),
          "vstart_date": startDate,
          "vend_date": endDate
        };

        this.txtTimkiem = 'Lấy dữ liệu';

        let response = await API.sp_lay_ds_phieu_ks_theodk(this.axios, params)
        console.log(response)
        if (response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {

          let ds = []

          response.data.data.forEach((element, index) => {
            if (this.donviVaule.includes(element.ma_tinh)) {
              // trạng thái xử lý
              let trangthai = '';
              if (element.tt_xuly == 1) trangthai = 'Chưa giao'
              if (element.tt_xuly == 2) trangthai = 'Đang xử lý'
              if (element.tt_xuly == 3) trangthai = 'Đã bị tỉnh xử lý'
              if (element.tt_xuly == 4) trangthai = 'Hẹn khách hàng'
              if (element.tt_xuly == 6) trangthai = 'Đã xong'
              if (element.tt_xuly == 7) trangthai = 'Phiếu tồn'
              if (element.tt_xuly == 8) trangthai = 'Bỏ qua'

              // Loại phiếu
              let loaiphieu = '';
              if (element.loai_id == 1) loaiphieu = 'Chưa giao'
              if (element.loai_id == 2) loaiphieu = 'Đang xử lý'

              ds.push({
                ma_tinh: element.ma_tinh,
                ma_tb: element.ma_tb,
                trangthai_xuly: trangthai,
                ngay_ht: element.ngay_ht,
                loaihinh_tb: element.loaihinh_tb,
                henks_tu: element.henks_tu,
                henks_den: element.henks_den,
                ma_nd: element.ma_nd,
                ngay_cn: element.ngay_cn,
                loaiphieu: loaiphieu,
                ngay_yc: element.ngay_yc,
                ghichu: element.ghichu,
                donvi_tn: element.donvi_tn,
                thuebao_id: element.thuebao_id,
                phieu_id: element.phieu_id,
                loai_id: element.loai_id,
                hdkh_id: element.hdkh_id,
                key: index+1
              })
            }
          });

          this.gridDanhSach = ds;

        } else {
          this.$toast.error('Không có dữ liệu')
        }

      } catch (e) {
        console.log(e)
        this.$toast.error('Không load được danh sách đơn vị cha')
      } finally {
        this.loading(false)
      }
    },
  },
}
</script>
