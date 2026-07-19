<template src="./template.html"></template>

<script>
import breadcrumb from "@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue";
import popupForm from "./popups/popupForm.vue";
export default {
  components: {
    breadcrumb,
    popupForm,
  },
  data() {
    return {
      header: {
        title: 'Tra cứu thông tin nợ thuê bao các nhà mạng',
        list: [
          { name: 'Tra cứu', link: { name: 'Ui.cards' } },
          {
            name: 'Quản lý thu nợ',
            link: { name: 'Ui.buttons' }
          },
          {
            name: 'Tra cứu thu nợ',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      selected: {},
      dataSource_2_filter: [],
      dataSource_1: [],
      columns_grid_1: [
        {
          fieldName: "enterpriseName",
          headerText: "Nhà mạng",
        },
        {
          fieldName: "status",
          headerText: "Tình trạng nợ",
        },
      ],
      dataSource_2: [],
      columns_grid_2: [
        {
          fieldName: "tendaydu",
          headerText: "Nhà mạng",
        },
        {
          fieldName: "ngay_tt",
          headerText: "Ngày thanh toán",
          format: "dd/MM/yyyy", // format as date
          type: "date", // specify the type as date
        },
        {
          fieldName: "ngay_cn",
          headerText: "Ngày cập nhật",
          format: "dd/MM/yyyy HH:mm:ss", // format as date
          type: "date", // specify the type as date
        },
        {
          headerText: "Link file",
          textAlign: "center",
          template: function () {
            return {
              template: {
                template: `<a class="btn btn-primary" @click="downloadFile(data.url)" target="_blank">File đính kèm</a>`,
                data() {
                  return {
                    data: {},
                  };
                },
                methods: {
                  async downloadFile(f_down_url) {
                    let file = []
                    try {
                      file = JSON.parse(f_down_url)
                    }
                    catch(err) {
                      console.log(err)
                      file = []
                    }
                    if(!file || !file[0]) {
                      this.$toast.error("File không đúng định dạng")
                      return
                    }

                    let rs = await this.axios.post("/web-quantri/upload/link_view_file", {
                      list_file: file[0].link,
                    });
                    if(rs.data.data && rs.data.data[0])
                      window.open(rs.data.data[0].file_url);
                    else
                      this.$toast.error("Không tìm thấy file");
                  },
                },
              },
            };
          },
        },
      ],
    };
  },
  computed: {
    enableButton() {
      if (this.dataSource_1 && this.dataSource_1.some((x) => x.status === "DEBIT")) {
        return true;
      } else {
        return false;
      }
    },
  },
  methods: {
    rowSelected_1(info) {
      console.log(info);
      if (info.data)
        this.dataSource_2_filter = this.dataSource_2.filter(
          (x) => x.tendaydu === info.data.enterpriseName
        );
      else this.dataSource_2_filter = [];
    },
    rowSelected_2(info) {
      const { data } = info;
      this.selected = data;
    },
    xoa() {
      this.$confirm("Bạn có muốn xóa thông tin này không?", "Thông báo", {
        confirmButtonText: "Đồng ý",
        cancelButtonText: "Không đồng ý",
      }).then(async () => {
        this.axios
          .post("web-tracuu/tracuunocuoc/fn_ins_update_hs_cucvt", {
            p_ds_para: JSON.stringify({
              HS_CUCVT: [
                {
                  SO_GT: this.selected.sgt,
                  CHUQUAN_ID: this.selected.chuquan_id,
                  URL: this.selected.url,
                  NGAY_TT: this.selected.ngaytt,
                  HSCVT_ID: this.selected.hscvt_id,
                },
              ],
              KIEU_TH: 3,
            }),
            p_tt_nd: JSON.stringify({
              NGUOI_CN: this.$root.token.getUserName(),
              NGUOIDUNG_ID: this.$root.token.getNguoiDungID(),
              NHANVIEN_ID: this.$root.token.getNhanVienID(),
              MAY_CN: await this.$root.token.getMachine(),
              IP_CN: await this.$root.token.getIP(),
            }),
          })
          .then((rs) => {
            this.$toast.success("Đã xóa thành công");
            this.tracuu()
          });
      });
    },
    async tracuu() {
      this.dataSource_1 = [];
      this.dataSource_2 = [];
      this.dataSource_2_filter = [];
      this.loading(true);
      try {
        let data = await this.axios.post("web-tracuu/tracuunocuoc/sp_lay_ds_hs_cucvt", {
          p_tt_bien: JSON.stringify({
            SO_GT: this.$refs.txtSGT.value,
          }),
        });

        this.dataSource_2 = data.data.data;

        data = await this.axios.get(
          "tichhop/cucvt/searchDebitData?numberId=" + this.$refs.txtSGT.value
        );
        this.dataSource_1 = data.data.data;
      } catch (error) {
        this.$toast.error(error.message);
      }

      if (!this.dataSource_1 || this.dataSource_1.length === 0) {
        this.$toast.error("Không tìm thấy dữ liệu");
      }
      this.loading(false);
    },
    nhapmoi() {
      this.$refs.popupForm.sgt = this.$refs.txtSGT.value;
      let dataSource_1_selected = this.$refs.dataSource_1.getSelectedRecords();
      this.$refs.popupForm.enterpriseName = dataSource_1_selected[0].enterpriseName;
      this.$refs.popupForm.showDialog();
    },
  },
};
</script>
