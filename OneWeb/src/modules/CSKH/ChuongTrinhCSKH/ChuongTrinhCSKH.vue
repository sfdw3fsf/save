<template src="./ChuongTrinhCSKH.html"></template>
<style src="./ChuongTrinhCSKH.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import CustomDataGrid from "../utils/CustomDataGrid.vue";
import { mapActions, mapState } from "vuex";
import downloadexcel from "vue-json-excel";
import ChuongTrinhCSKH from "../api/ChuongTrinhCSKH";
import moment from 'moment'
import { required, maxLength } from 'vuelidate/lib/validators'
import BssRequiredMarker from '@/components/BssRequiredMarker';
import BssErrorMarker from '@/components/BssErrorMarker'
import ModalXuatDuLieu from '../../ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu'

export default {
  components: {
    breadcrumb,
    downloadexcel,
    BssRequiredMarker,
    BssErrorMarker,
    datagrid: CustomDataGrid,
    ModalXuatDuLieu,
  },
  computed: {
    ...mapState("chuongtrinhCSKH", ["dsChuongTrinhCSKH"]),
  },
  async mounted() {
    await this.fetchData(0, 1000);
    this.dataSelect.Ma_CT = this.dsChuongTrinhCSKH[0].MA_CT;
    this.dataSelect.Ten_CT = this.dsChuongTrinhCSKH[0].TEN_CT;
    this.dataSelect.Ngay_BD = this.dsChuongTrinhCSKH[0].NGAY_BD;
    this.dataSelect.Ngay_KT = this.dsChuongTrinhCSKH[0].NGAY_KT;
    this.dataSelect.Trang_Thai = this.dsChuongTrinhCSKH[0].TRANG_THAI;
    this.dataSelect.id = this.dsChuongTrinhCSKH[0].ID;
    console.log("Danh sách: ", this.dsChuongTrinhCSKH);
  },
  validations: {
    dataSelect: {
      Ma_CT: {
        required,
        maxLength: maxLength(100),
      },
      Ten_CT: {
        required,
        maxLength: maxLength(300)
      },
      Ngay_BD: {
        required,
        khongLonHonNgayKetThuc: (ngay, vm) => {
          if (vm.Ngay_KT) return ngay <= vm.Ngay_KT
          else return true
        },
        khongNhoHonNgayHienTai: (ngay, vm) => {
          return ngay >= new Date().setHours(0, 0, 0, 0)
        },
      },
    }
  },
  data() {
    return {
      header: {
        title: "CHƯƠNG TRÌNH CHĂM SÓC KHÁCH HÀNG",
        list: [
          { name: "Chăm sóc khách hàng", link: { name: "Ui.cards" } },
          {
            name: "Quản lý chính sách CSKH",
            link: { name: "Ui.buttons" }
          },
          {
            name: "Tạo chương trình CSKH",
            link: { name: "Ui.buttons" }
          }
        ]
      },
      data: {
        pageNo: 1,
        pageSize: 10
      },
      dataSelect: {
        id: "",
        Ma_CT: "",
        Ten_CT: "",
        Ngay_BD: new Date(),
        Ngay_KT: new Date(),
        Trang_Thai: null
      },
      buttonState: {
        isDisablebtnNhapMoi: false,
        isDisablebtnCapNhap: false,
        isDisablebtnHuy: false,
        isDisablebtnXoa: false
      },
      fields: [
        {
          fieldName: "INDEX",
          headerText: "STT",
          allowFiltering: true,
          cellCssClass: 'text-right'
        },
        {
          fieldName: "MA_CT",
          headerText: "Mã CT",
          allowFiltering: true
        },
        {
          fieldName: "TEN_CT",
          headerText: "Tên CT",
          allowFiltering: true
        },
        {
          fieldName: "NgayBD",
          headerText: "Ngày BĐ",
          allowFiltering: true,
          cellCssClass: 'text-right'
        },
        {
          fieldName: "NgayKT",
          headerText: "Ngày KT",
          allowFiltering: true,
          cellCssClass: 'text-right'
        }
      ],
      isShowModal: false,
      dataExcel: [],
      fieldExcel: {
        "STT": "INDEX",
        "Mã CT": "MA_CT",
        "Tên CT": "TEN_CT",
        "Ngày BĐ": "NGAY_BD",
        "Ngày KT": "NGAY_KT"
      },
      dsThuocTinh: [],
      today: new Date(),
      style: "",
    };
  },
  methods: {
    ...mapActions("chuongtrinhCSKH", [
        "getDSChuongTrinhCSKH",
        "deleteChuongTrinhCSKH"
    ]),
    async fetchData (page, size) {
        let data = {
            pageNo: page,
            pageSize: size
        }
        await this.getDSChuongTrinhCSKH(data)
        this.dsChuongTrinhCSKH.forEach(element => {
          this.dataExcel.push({
            STT: element.INDEX,
            Ma_CT: element.MA_CT,
            Ten_CT: element.TEN_CT,
            Ngay_BD: element.NgayBD,
            Ngay_KT: element.NgayKT
          })
        })
        // console.log("excel: ", this.dataExcel)
    },
    setButtonState (nhapMoi, capNhap, huy, xoa) {
      this.buttonState.isDisablebtnNhapMoi = nhapMoi
      this.buttonState.isDisablebtnCapNhap = capNhap
      this.buttonState.isDisablebtnHuy = huy
      this.buttonState.isDisablebtnXoa = xoa
    },
    clean () {
      this.dataSelect.id = "";
      this.dataSelect.Ma_CT = "";
      this.dataSelect.Ten_CT = "";
      this.dataSelect.Ngay_BD = new Date();
      this.dataSelect.Ngay_KT = new Date();
      this.dataSelect.Trang_Thai = false;
    },
    cleanForm() {
      this.setButtonState(true, false, false, true)
      this.$refs.Ma_CT.focus()
      this.clean()
    },
    cancelForm() {
      this.setButtonState(false, true, true, true)
      this.clean()
    },
    rowClick(rowIndex) {
      this.setButtonState(false, false, false, false)
      let currentData = this.$refs.gridChuongTrinhCSKH.dataItems[rowIndex]
      this.dataSelect.Ma_CT = currentData.MA_CT
      this.dataSelect.Ten_CT = currentData.TEN_CT
      this.dataSelect.Ngay_BD = currentData.NGAY_BD
      this.dataSelect.Ngay_KT = currentData.NGAY_KT
      this.dataSelect.Trang_Thai = currentData.TRANG_THAI
      this.dataSelect.id = currentData.ID
    },
    showModal() {
      this.isShowModal = !this.isShowModal
    },
    async save() {
      this.$root.loading(true);
      let data = {
        ctcskhId: this.dataSelect.id ? this.dataSelect.id : 0,
        maCt: this.dataSelect.Ma_CT,
        tenCt: this.dataSelect.Ten_CT,
        ngayBd: moment(this.dataSelect.Ngay_BD).format("DD/MM/YYYY"),
        ngayKt: this.dataSelect.Ngay_KT ? moment(this.dataSelect.Ngay_KT).format("DD/MM/YYYY") : "",
        trangThai: this.dataSelect.Trang_Thai ? 1 : 0,
        mayCn: "test_PC",
	      nguoiCn: "test_ng_cn" //this.$auth.getUserName()
      }
      console.log(data)
      if (this.validateData(data)) {
        this.$root.loading(false);
        return
      }
      await ChuongTrinhCSKH.capNhapChuongTrinhCSKH(this.axios, data)
        .then(async (response) => {
          console.log(response)
          if (response.data.error_code === "BSS-00000000") {
            await this.fetchData(0, 1000)
            if (data.ctcskhId === 0) {
              this.$toast.success("Thêm mới thành công !")
              this.$refs.gridChuongTrinhCSKH.setCurrentSelectedRow(0)
              this.dataSelect.Ma_CT = this.dsChuongTrinhCSKH[0].MA_CT;
              this.dataSelect.Ten_CT = this.dsChuongTrinhCSKH[0].TEN_CT;
              this.dataSelect.Ngay_BD = this.dsChuongTrinhCSKH[0].NGAY_BD;
              this.dataSelect.Ngay_KT = this.dsChuongTrinhCSKH[0].NGAY_KT;
              this.dataSelect.Trang_Thai = this.dsChuongTrinhCSKH[0].TRANG_THAI;
              this.dataSelect.id = this.dsChuongTrinhCSKH[0].ID;
            } else {
              this.$toast.success("Cập nhập thành công !")
              // this.$refs.gridChuongTrinhCSKH.setCurrentSelectedRow(0)
            }

            this.setButtonState(false, false, false, false)
            // this.clean()
          } else {
            this.$toast.error(response.data.message)
          }
        })
        .catch(reject => {
          console.log(reject)
          if (reject.response && reject.response.data && reject.response.data.message)
            // console.log(reject.response.data)
            this.$toast.error(reject.response.data.message)
          else this.$toast.error("Có lỗi xảy ra!")
        })
        .finally(final => {
          this.$root.loading(false)
        });
      this.$root.loading(false)
    },
    async deleteCT () {
        this.$root.loading(true)
        if (!this.dataSelect.id) {
          this.$toast.error("Chọn bản ghi cần xoá!")
          this.$root.loading(false)
          return
        }
        await this.deleteChuongTrinhCSKH(this.dataSelect.id)
        this.fetchData(0,1000)
        this.clean()
        this.$root.loading(false)
    },
    xuatFile() {
      if (this.dataExcel.length === 0){
        this.$root.toastError("Không có dữ liệu để xuất")
        return
      }
      this.dsThuocTinh = []
      for (let key in this.dataExcel[0]){
        this.dsThuocTinh.push(key)
      }
      this.$bvModal.show('modal-xuat-dulieu')
    },
    validateData(data) {
      this.$v.$touch();
      let errorMessage = []
      let allow = true

      if (this.$v.dataSelect.Ma_CT.$error) {
        this.$refs.Ma_CT.focus()
        allow = false

        if (!this.$v.dataSelect.Ma_CT.required) {
          errorMessage.push("Trường mã chương trình bắt buộc nhập.")
        } else if (!this.$v.dataSelect.Ma_CT.maxLength) {
          errorMessage.push("Trường mã chương trình dữ liệu vượt quá độ dài cho phép.")
        }
      } else if (data.ctcskhId === 0) {
        let arr = this.dsChuongTrinhCSKH.filter(val => val.MA_CT && val.MA_CT.toUpperCase() == data.maCt.toUpperCase() && val.ID != data.ctcskhId)
        console.log(arr)
        if (arr.length > 0) {
          errorMessage.push('Mã chương trình đã tồn tại.');
          this.$refs.Ma_CT.focus()
          allow = false
          this.style = "border-color: #dc3545;"
        }
      }

      if (this.$v.dataSelect.Ten_CT.$error) {
        if (allow) {
          this.$refs.Ten_CT.focus()
          allow = false
        }

        if (!data.tenCt.required) {
          errorMessage.push("Trường tên chương trình bắt buộc nhập.")
        } else if (!data.tenCt.maxLength) {
          errorMessage.push("Trường tên chương trình dữ liệu vượt quá độ dài cho phép.")
        }
      }

      if (this.$v.dataSelect.Ngay_BD.$error) {
        if (allow) {
          this.$refs.Ngay_BD.focusIn()
          allow = false
        }

        if (!this.$v.dataSelect.Ngay_BD.required) {
          errorMessage.push("Trường ngày bắt đầu bắt buộc chọn.")
        } else if (!this.$v.dataSelect.Ngay_BD.khongLonHonNgayKetThuc) {
          errorMessage.push("Ngày bắt đầu không được lớn hơn ngày kết thúc!")
        } else if (!this.$v.dataSelect.Ngay_BD.khongNhoHonNgayHienTai) {
          errorMessage.push("Ngày bắt đầu không được nhỏ hơn ngày hiện tại!")
        }
      }

      // if (!this.$v.dataSelect.Ngay_KT.khongNhoHonNgayBatDau) {
      //   if (allow) {
      //     this.$refs.Ngay_KT.focusIn()
      //     allow = false
      //   }
      //   errorMessage.push("Ngày bắt đầu không được lớn hơn ngày kết thúc!")
      // }

      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join('\n'))
        return true
      } else {
        return false
      }
    }
  }
};
</script>
