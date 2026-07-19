<template src="./index.html"></template>
<script>
import {maxLength, required, numeric} from "vuelidate/lib/validators";
import BssRequiredMarker from '@/components/BssRequiredMarker'

export default {
  components: { BssRequiredMarker },
  name: "QuyHoachVlanUpe",
  data() {
    var returnData = {
      isNhapMoi: false,
      disableVungQuyHoach: true,
      isDisableBtnNhapMoi: false,
      isDisableBtnGhiLai: false,
      isDisableBtnHuy: false,
      isDisableBtnXoa: false,
      isDisableBtnTimKiem: false,
      isSelected: -1,
      txtTimKiem: "",
      modalXoa: false,
      vlan: {
        quyHoachId: null,
        tenDslam: null,
        dslam_id: null,
        upe: [],
      },
      vlan_tu: null,
      vlan_den: null,
      oldValue: {
        txtTimKiem: null,
        vlan_tu: null,
        vlan_den: null,
        quyHoachId: null
      },
      dsVlanUpe: [],
      selectedUpe: null
    };
    return returnData;
  },
  validations: {
    vlan_tu: {
      required,
      maxlength: maxLength(20),
      numeric
    },
    vlan_den: {
      required,
      maxlength: maxLength(20),
      numeric
    }
  },
  computed: {
  },
  methods: {
    timKiemEnter:function(event) {
      if (event.key == "Enter") {
        this.timKiem()
      }
    },
    timKiem: async function () {
      var tk = this.txtTimKiem.trim();
      if (tk !== "") {
        this.loading(true);
        let rs = await this.$root.context.get(
          `/web-ecms/quyhoach-vlan-upe/lay?vlan=${this.txtTimKiem}`).catch(error => {
          this.$toast.error(error.response.data.message_detail)
          return
        })
        this.loading(false);
        if (rs) {
          this.dsVlanUpe = rs.data
          this.vlan_tu = null;
          this.vlan_den = null;
        }
      } else {
        this.loading(true);
        var rs = await this.$root.context.get(
          '/web-ecms/quyhoach-vlan-upe/lay-all'
        );
        this.loading(false);
        if (rs) {
          this.dsVlanUpe = rs.data
          this.vlan_tu = null;
          this.vlan_den = null;
        }
      }
    },
    validateData (data) {
      this.$v.$touch()
      let errorMessage = []
      if (this.$v.vlan_den.$error) {
        this.$refs.vlan_den.focus()
        if (!data.vlan_den || !data.vlan_den.toString().trim()) {
          errorMessage.unshift('Trường VLAN đến bắt buộc nhập dữ liệu.')
        } else if (isNaN(data.vlan_den)) {
          errorMessage.unshift('Trường VLAN đến phải là số!')
        } else if (data.vlan_den.toString().length > 20) {
          errorMessage.unshift('Trường VLAN đến: Dữ liệu vượt quá độ dài cho phép.')
        } else if (data.vlan_den.startsWith('-')) {
          errorMessage.unshift('Trường VLAN đến phải là số nguyên >=0.')
        } else if (data.vlan_den.toString().includes('.')) {
          errorMessage.unshift('Trường VLAN đến phải là số nguyên >=0.')
        }
      }
      if (this.$v.vlan_tu.$error) {
         this.$refs.vlan_tu.focus()
        if (!data.vlan_tu || !data.vlan_tu.toString().trim()) {
          errorMessage.unshift('Trường VLAN từ bắt buộc nhập dữ liệu.')
        } else if (isNaN(data.vlan_tu)){
          errorMessage.unshift('Trường VLAN từ phải là số!')
        } else if (data.vlan_tu.toString().length > 20) {
          errorMessage.unshift('Trường VLAN từ dữ liệu vượt quá độ dài cho phép.')
        } else if (data.vlan_tu.startsWith('-')) {
          errorMessage.unshift('Trường VLAN từ phải là số nguyên >=0.')
        } else if (data.vlan_tu.toString().includes('.')) {
          errorMessage.unshift('Trường VLAN từ phải là số nguyên >=0.')
        } else if (parseInt(data.vlan_tu) > parseInt(data.vlan_den)) {
          this.$refs.vlan_tu.focus()
          errorMessage.unshift('VLAN từ không được lớn hơn VLAN đến.')
        }
      } else if (parseInt(data.vlan_tu) > parseInt(data.vlan_den)) {
        this.$refs.vlan_tu.focus()
        errorMessage.unshift('VLAN từ không được lớn hơn VLAN đến.')
      }
      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join('\n'))
        return true
      } else {
        return false
      }
    },
    nhapMoi: async function () {
      this.$refs.dataGridVlan.setCurrentSelectedRow(-1)
      this.oldValue.txtTimKiem = this.txtTimKiem
      this.oldValue.vlan_tu = this.vlan_tu
      this.oldValue.vlan_den = this.vlan_den
      this.oldValue.quyHoachId = this.vlan.quyHoachId
      this.txtTimKiem = null;
      this.vlan_tu = null;
      this.vlan_den = null;
      this.vlan.quyHoachId = null;
      this.isNhapMoi = true
      this.setDisableBtn(false, true, false, false, true)
      this.$refs.vlan_tu.focus()
    },
    huy: async function () {
      // this.$refs.dataGridVlan.firstPage()
      if (this.isDisableBtnHuy) return
      this.$refs.dataGridVlan.setCurrentSelectedRow(0)
      try {
        this.txtTimKiem = this.oldValue.txtTimKiem
        this.vlan_tu = this.oldValue.vlan_tu
        this.vlan_den = this.oldValue.vlan_den
        this.vlan.quyHoachId = this.oldValue.quyHoachId
        this.$refs.vlan.focus()
        this.loading(true)
        var rs = await this.$root.context.get(
          '/web-ecms/quyhoach-vlan-upe/lay-all'
        )
        this.dsVlanUpe = rs.data
        this.dsVlanUpe = this.dsVlanUpe.sort(function (a, b) {
          var keyA = a.QUYHOACH_ID, keyB = b.QUYHOACH_ID;
          if (keyA < keyB) return -1;
          if (keyA > keyB) return 1;
          return 0;
        });
        rs = await this.$root.context.get(
          '/web-ecms/quyhoach-vlan-upe/lay-all-upe'
        );
        this.setDisableBtn(false, false, true, false, false)
      } finally {
        this.loading(false)
      }
    },
    xoa: function () {
      if (!this.isDisableBtnXoa) {
        this.modalXoa = !this.modalXoa;
      }
    },
    xacNhanXoa: async function () {
      try {
        // await this.xoaQuyHoachVlanUpe({
        //   quyHoachId: this.vlan.quyHoachId,
        // });
        await this.$root.context.post(
          `/web-ecms/quyhoach-vlan-upe/xoa/?quyhoachId=${this.vlan.quyHoachId}`
        );
        this.$toast.success("Xóa thành công");
        // await this.layDsQuyHoachVlanUpeAll();
        var rs = await this.$root.context.get(
          '/web-ecms/quyhoach-vlan-upe/lay-all'
        );
        this.dsVlanUpe = rs.data
        this.dsVlanUpe = this.dsVlanUpe.sort(function (a, b) {
          var keyA = a.QUYHOACH_ID, keyB = b.QUYHOACH_ID;
          if (keyA < keyB) return -1;
          if (keyA > keyB) return 1;
          return 0;
        });
      } catch (error) {
        this.$toast.error("Xóa thất bại!");
      }
      this.$refs.dataGridVlan.firstPage()
      this.$refs.dataGridVlan.setCurrentSelectedRow(0)
    },
    setDisableBtn: function (isTimKiem, isNhapmoi, isGhilai, isHuy, isXoa) {
      this.isDisableBtnTimKiem = isTimKiem;
      this.isDisableBtnNhapMoi = isNhapmoi;
      this.isDisableBtnGhiLai = isGhilai;
      this.isDisableBtnHuy = isHuy;
      this.isDisableBtnXoa = isXoa;
    },
    ghiLai: async function () {
      if (this.isDisableBtnGhiLai) return
      let data = {
        vlan_tu: this.vlan_tu,
        vlan_den: this.vlan_den
      }
      if (this.validateData(data)) {
        return
      }
      if (!this.isNhapMoi) {
        try {
          var rs = await this.$root.context.post(
            `/web-ecms/quyhoach-vlan-upe/capnhat?quyhoachId=${this.vlan.quyHoachId}`, {
              dslam_id: this.vlan.dslam_id,
              vlan_tu: this.vlan_tu,
              vlan_den: this.vlan_den,
            }).catch(error => {
            //
            this.$toast.error(error.response.data.message_detail)
          });
          if (rs) {
            if (rs.data.QUYHOACH_ID) {
              this.$toast.success("Cập nhật thành công");
              // await this.layDsQuyHoachVlanUpeAll();
              var rs = await this.$root.context.get(
                '/web-ecms/quyhoach-vlan-upe/lay-all'
              );
              this.dsVlanUpe = rs.data
              this.dsVlanUpe = this.dsVlanUpe.sort(function(a, b) {
                var keyA = a.QUYHOACH_ID, keyB = b.QUYHOACH_ID;
                if (keyA < keyB) return -1;
                if (keyA > keyB) return 1;
                return 0;
              });
            } else {
              this.$toast.error("Cập nhật thất bại!");
            }
          }
        } catch (error) {
          this.$toast.error("Cập nhật thất bại!");
          throw error;
        }
      } else {
        try {
          this.isNhapMoi = !this.isNhapMoi
          // let httpResponseData = await this.themMoiQuyHoachVlanUpe({
          //   dslam_id: this.vlan.dslam_id,
          //   vlan_tu: this.vlan_tu,
          //   vlan_den: this.vlan_den
          // })

          var rs = await this.$root.context.post(
            `/web-ecms/quyhoach-vlan-upe/them`, {
              dslam_id: this.vlan.dslam_id,
              vlan_tu: this.vlan_tu,
              vlan_den: this.vlan_den
            }).catch(error => {
              //
            this.$toast.error(error.response.data.message_detail);
          });
          if (rs) {
            this.vlan.quyHoachId = rs.data.QUYHOACH_ID;
            this.$toast.success("Thêm mới thành công");
            // await this.layDsQuyHoachVlanUpeAll();
            var rs = await this.$root.context.get(
              '/web-ecms/quyhoach-vlan-upe/lay-all'
            );
            this.dsVlanUpe = rs.data
          }
        } catch (error) {
          this.$toast.error("Thêm mới thất bại!");
        }
      }
    },
    selectVlanUpe: async function (data, index) {
      if (data) {
        this.isNhapMoi = false
        this.vlan.quyHoachId = data.QUYHOACH_ID;
        this.vlan.dslam_id = data.DSLAM_ID;
        this.vlan.tenDslam = data.TENDSLAM;
        this.vlan_tu = data.VLAN_TU;
        this.vlan_den = data.VLAN_DEN;
        this.isSelected = index;
        if (!this.$refs.selectedVlan.selectedItem) {
          this.$refs.selectedVlan.selectedItem = {
            tenDslam: this.vlan.tenDslam,
            dslamId: this.vlan.dslam_id
          }
        } else {
          this.$refs.selectedVlan.selectedItem.tenDslam = this.vlan.tenDslam
          this.$refs.selectedVlan.selectedItem.dslamId = this.vlan.dslam_id
        }
        // console.log(this.$refs.selectedVlan.selectedItem)

        // this.selectedUpe = data.DSLAM_ID + '.' + data.TENDSLAM
        this.setDisableBtn(false, false, false, false, false)
      }
      // this.setUpe([this.vlan.upe]);
    },
    selectUpe: function (data) {
      // console.log(data.dslamId)
      // if (this.selectedUpe)
      this.vlan.dslam_id = data.dslamId
    }
  },
  created() {
    // this.clearState();
    // this.createApiManager();
  },
  async mounted() {
    try {
      await this.$store.dispatch("setHeader", {
        title: "QUY HOẠCH VLAN UPE",
        list: [
          {
            name: "Lập hợp đồng",
            link: {name: "Ui.cards"},
          },
          {
            name: "Lắp đặt mới",
            link: {name: "Ui.buttons"},
          },
        ],
      });
      this.loading(true);
      var rs = await this.$root.context.get(
        '/web-ecms/quyhoach-vlan-upe/lay-all'
      );
      this.dsVlanUpe = rs.data
      this.dsVlanUpe = this.dsVlanUpe.sort(function(a, b) {
        var keyA = a.QUYHOACH_ID, keyB = b.QUYHOACH_ID;
        if (keyA < keyB) return -1;
        if (keyA > keyB) return 1;
        return 0;
      });
      this.$refs.vlan.focus()
      rs = await this.$root.context.get(
        '/web-ecms/quyhoach-vlan-upe/lay-all-upe'
      );
      this.vlan.upe = []
      rs.data.forEach(element => {
        this.vlan.upe.push({
          dslamId: element.DSLAM_ID,
          tenDslam: element.TENDSLAM
        })
      })
    } catch (err) {
    } finally {
      this.loading(false)
    }
  },
};
</script>
<style scoped src="./index.scss">
</style>
