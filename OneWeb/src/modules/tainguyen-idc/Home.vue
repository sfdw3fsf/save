<template>
    <div>
      <h3>Home Page</h3>
      <div class="row">
        <div class="col-lg-6">
          <div class="control-section">
            <ul>
              <li>
                <router-link active-class="" to="/contract"
                  >Quản lý hợp đồng</router-link
                >
              </li>
              <li>
                <router-link active-class="" to="/qltn"
                  >Quản lý Thu nợ</router-link
                >
              </li>
              <li>
                <router-link active-class="" to="/qlvt"
                  >Quản lý Vật tư</router-link
                >
              </li>
              <li>
                <router-link active-class="" to="/cskh"
                  >Chăm sóc khách hàng</router-link
                >
              </li>
                <li><router-link active-class="" to="/search/SearchVNEduData">Tra cứu dữ liệu VNEdu</router-link></li>
                <li><router-link active-class="" to="/contract/AssignManagement?tag=1">Gán nhân viên quản lý</router-link></li>
                <li><router-link active-class="" to="/contract/AssignManagement?tag=2">Gán nhân viên danh bạ</router-link></li>
                <li><router-link active-class="" to="/contract/UpdatePostPaid">Cập nhật thông tin trả trước</router-link></li>
                <li><router-link active-class="" to="/search/SearchContactDetails">Tra cứu Huế</router-link></li>
                <li><router-link active-class="" to="/qltn/GachNoTienMat">Gạch nợ tiền mặt</router-link></li>
                <li><router-link active-class="" to="/cskh/DanhSachPhieuKhaoSat">OB - Danh sách phiếu khảo sát</router-link></li>
                <li><router-link active-class="" to="/cskh/TraCuuLichSuPhanAnh">OB - Tra cứu lịch sử phản ánh</router-link></li>
              <li><router-link active-class="" to="/qltn/XacDinhCongNoTheoDanhSach">QLTN - Xác định công nợ theo danh sách</router-link></li>
              <li><router-link active-class="" to="/contract/RestoreCanceledPort">URD2.4.120 Khôi phục cổng đã huỷ cấu hình</router-link></li>
            </ul>
          </div>
          <button @click="logout" class="btn btn-primary">Logout</button>
        </div>
        <div class="col-lg-6"></div>
      </div>
      <div style="
          position: absolute;
          right: 0px;
          background-color: #dfff9a;
          top: 0px;
          padding: 5px 30px 20px;
          border: 1px solid #ddd;
          box-shadow: -1px 0 20px 0px #ccc;
          border-radius: 0px 0 0 20px;
        "
      >
        <h4>Modules</h4>
        <ol>
          <li>
            <router-link active-class="" to="/ecms"
              >ECMS: Quản lý tài nguyên mạng</router-link
            >
          </li>
          <li>
            <router-link active-class="" to="/cabman"
              >CABMAN: Quản lý mạng ngoại vi</router-link
            >
          </li>
          <li>
            <router-link active-class="" to="/qlsc"
              >QLSC: Quản lý sự cố</router-link
            >
          </li>
          <li>
            <router-link active-class="" to="/ext-hopdong"
              >HOPDONG: Quản lý hợp đồng</router-link
            >
          </li>
          <li>
            <router-link active-class="" to="/ext-khieunai"
              >KHIEUNAI: Quản lý khiếu nại</router-link
            >
          </li>
          <li>
            <router-link active-class="" to="/ext-quantri"
              >QUANTRI: Quản trị hệ thống</router-link
            >
          </li>
          <li>
            <router-link active-class="" to="/ext-toanha"
              >TOANHA: Quản lý tòa nhà</router-link
            >
          </li>
        </ol>
      </div>
    </div>
  </template>
  <script>
  import Vue from "vue";
  import { mapActions } from "vuex";
  
  export default Vue.extend({
    name: "Home",
    components: {},
    data() {
      return {
        header: "Sample",
      };
    },
    mounted() {
      // console.log(this.$refs.formYeuCauMoKhoa)
      // $(this.$refs.formYeuCauMoKhoa).on("hidden.bs.modal", this.$refs.formYeuCauMoKhoa.clearResult())
      let $this = this;
      this.$root.$on("bv::modal::hidden", (bvEvent, modalId) => {
        // console.log('Modal is about to be shown', bvEvent, modalId)
        //console.log(modalId)
        if (modalId === "popup-yeucaukhoamo") {
          $this.$refs.formYeuCauMoKhoa.clearForm();
          $this.$refs.formYeuCauMoKhoa.clearResult();
        } else if (modalId === "popup-khachhangtreono") {
          $this.$refs.formKhachHangTreoNo.clearForm();
          $this.$refs.formKhachHangTreoNo.clearResult();
        } else if (modalId === "popup-khachhangkhatno") {
          $this.$refs.formKhachHangKhatNo.clearForm();
          $this.$refs.formKhachHangKhatNo.clearResult();
        }
      });
    },
    methods: {
      ...mapActions("user", ["clearCurrentUser"]),
      openDialog(obj) {
        // this.$refs.dialogObj.show()
        // console.log(this.$refs[obj])
        this.$refs[obj].openDialog();
      },
      dialogOpen() {},
      dialogClose() {},
      clearData() {
        // TODO clear data
      },
      async logout() {
        await this.$store.dispatch("user/clearCurrentUser").then((response) => {
          console.log(response);
          this.$router.push("Login");
        });
      },
    },
  });
  </script>
  