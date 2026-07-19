<template src="./danhsach-port-thietbi-theo-phanloai.html">
</template>

<script>
import ChiTietCongThietBi from "./chitiet-cong-thietbi";
export default {
  props: {
    thongtinCard: {
      type: Object,
    }
  },
  data() {
    return {
      target: ".main-wrapper",
      animationSettings: { effect: "Zoom" },
      listPorts: [],
    }
  },
  components: {
    ChiTietCongThietBi
  },
  computed: {
    computedColumns() {
      return [
        { fieldName: 'SO_CARD', headerText: this.thongtinCard.title, textAlign: 'Left' },
        { fieldName: 'SOTHUTU', headerText: 'Số thứ tự', width: 100, textAlign: 'Left' },
        { fieldName: 'TEN', headerText: 'Tên cổng', textAlign: 'Left' },
        { fieldName: 'TEN_LOAICONG', headerText: 'Loại cổng', textAlign: 'Left' },
        { fieldName: 'TEN_MUCDICHSUDUNG_CONG', headerText: 'Mục đích', textAlign: 'Left' },
        { fieldName: 'TEN_BANGTHONGCONG', headerText: 'Băng thông', textAlign: 'Left' },
        { fieldName: 'TEN_HIEU_LUC', headerText: 'Hiệu lực', width: 120, textAlign: 'Left' },
        { fieldName: 'CHITIET', headerText: 'Chi tiết', template: this.getColumnTemplate(this), width: 120, textAlign: 'Left' },
      ];
    }
  },

  watch: {
  },

  created: async function () {
  },

  methods: {
    getColumnTemplate(parent) {
      return () => {
        return {
          template: {
            template: `
              <div style="display: flex; justify-content: center; align-items: center;">
                <button class="btn btn-second btn-detail" style="width: 50%;" @click="parent.handleDetailCLick(data)">Click</button>
              </div>
            `,
            data() {
              return {
                parent: parent,
                data: {}
              }
            }
          }
        }
      }
    },
    async dialogOpen() {
      try {
        this.loading(true);  // Bắt đầu trạng thái loading
        console.log('this.thongtinCard.thietbi_id', this.thongtinCard.id)
        await this.getdsPortThietBi(this.thongtinCard.id, this.thongtinCard.phanloai);

        // Kiểm tra sự tồn tại của ref và gọi phương thức show() trên dialog
        if (this.$refs.dialogDanhSachPortThietBiTheoPhanLoai) {
          this.$refs.dialogDanhSachPortThietBiTheoPhanLoai.show(); // Hiển thị dialog
        } else {
          this.$toast.error("Dialog không tồn tại hoặc không được tham chiếu đúng");
        }
      } catch (e) {
        // Hiển thị thông báo lỗi nếu có vấn đề
        console.log('loi', e);
      } finally {
        this.loading(false); // Tắt trạng thái loading
      }
    },

    // setData(data) {
    //   this.thietbi_id = data.id;
    //   this.phanloai = data.phanloai;
    //   this.title = (data.phanloai == 1) ? 'LineCard' : ((data.phanloai == 2) ? 'Module' : 'ControlCard');
    // },

    closeDialog() {
      this.$refs.dialogDanhSachPortThietBiTheoPhanLoai.hide();
    },

    handleDetailCLick(data) {
      this.$refs.dialogChiTietCongThietBi.dialogOpen(data);
      //this.$router.push(`/ha-tang/chi-tiet-cong-thiet-bi-thiet-bi/${data.ID}`);
    },

    getdsPortThietBi: async function (id, phanloai) {
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/controller-tudia/get-ds-port-thietbi-theo-phanloai", {
          id: id,
          phanloai: phanloai
        });
        this.listPorts = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
    },

    capnhatPortThietBi: async function (data) {
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/cong-thietbi/update", data);
        await this.getdsPortThietBi(this.thongtinCard.id, this.thongtinCard.phanloai);
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
    },

    chitietCongThietBiTraVe(data) {
      console.log('trave', data)
      const body = {
        "id": data.id,
        "sothutu": data.no,
        "ten": data.portName,
        "loaiCongID": data.portType,
        "mucDichSuDungCongID": data.portPurpose,
        "bangThongCongID": data.portBandWidth,
        "thongTinModuleID": data.moduleInfo,
        "ketnoi": data.connection,
        "trangthai_sd": data.usingState,
        "hieuLuc": data.validity
      }
      this.capnhatPortThietBi(body);
    }
  },
}
</script>
