<template src="./index.html"></template>
<style scoped></style>
<script>
import moment from "moment";
import {previewPrint} from "../../../utils/util";

export default {
  components: {},
  filters: {},
  data: function () {
    return {
      hoaDon:[],
      ngayRaHd: new Date(),
      thangNam: new Date(),
      tongTien: [
        {
          TT: "NO_TONG>=0",
          TEN: "Tiền >= 0",
        },
        {
          TT: "NO_TONG<0",
          TEN: "Tiền < 0",
        },
      ],
      kieu: "NO_TONG>=0",
      maKH:""
    };
  },
  validations: {},
  watch: {},
  created: async function () {},
  async mounted() {},
  methods: {
    async onClickViewResult() {
      const body = {
        ds: {
          1: moment(this.thangNam).format("MM/YYYY"),
          3: moment(this.ngayRaHd).format("DD/MM/YYYY"),
          17: this.maKH,
          19: this.kieu
        }
        // ds: {
        //   1: "03/2021",
        //   3: "09/12/2021",
        //   17: "011637984,IS01151021",
        //   19: "NO_TONG>=0",
        // },
      };
      await this.inHD(body);
      // await this.getDSHoaDonByMaTT();
      // await this.taiFile();
    },

     async getDSHoaDonByMaTT() {
            try {
                this.loading(true);
                this.hoaDon = [];
                let apiBody = {
                    cusCode: this.maKH,
                    fromDate: moment(this.ngayRaHd).format("DD/MM/YYYY"),
                    toDate: moment(this.ngayRaHd).format("DD/MM/YYYY"),
                    phanvung_id: this.$auth.getPhanVungID()
                };
                let response = await API.getDanhSachHoaDon(this.axios, apiBody);
                console.log(this.hoaDon);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                    let count = 1;
                    this.hoaDon = response.data.data.map((item) => ({
                        ...item,
                        fkey:item.fkey
                    }));
                }
                this.hoaDon = [...this.hoaDon];
                console.log(this.hoaDon);
            }
            catch (error) {
                console.log(error)
                this.$toast.error(error.data.message);
            }
            finally {
                this.loading(false);
            }
        },
        async taiFile() {
            if (this.hoaDon.length === 0) {
                this.$root.toastError("Không tìm thấy thông tin thỏa điều kiện !");
                return;
            }
            const listFkey = this.listHoaDon;
            var _this = this;
            listFkey.forEach(item => {
                this.axios({
                    url:
                        "https://api-onebss-dev.vnptit3.vn/app-com/hoadon_dientu/downloadInvPDFFkeyNoPay",
                    method: "POST",
                    data: {
                        // phanvung_id: 26,
                        // fkey: "200001GTKT0001AA21E1$1"
                        phanvung_id: parseInt(this.$auth.getPhanVungID()),
                        fkey: item.fkey
                    }
                })
                    .then(response => {
                        const linkSource = `data:application/pdf;base64,${response.data.data.response}`;
                        const downloadLink = document.createElement("a");
                        const fileName = item.fkey + ".pdf";
                        downloadLink.href = linkSource;
                        downloadLink.download = fileName;
                        downloadLink.click();
                    })
                    .catch(function(error) {
                        console.log(error);
                        _this.$root.toastError(error.data.message);
                    });
            });
        },
    inHD: async function (body) {
      // try {
      //   const rs = await this.axios.post(
      //     `/web-thuno/api/thu-no/in-hoa-don/hoa-don-chuyen-doi`,
      //     body,
      //     {
      //       headers : {responseType: "blob"}
      //     }
      //   );
      //   console.log(rs.data);
      //   if(rs.data) {
      //     previewPrint(rs.data);
      //   }
      // } catch (error) {}
      this.$root.showLoading(true);
      try {
        const baseUrl = process.env.API;
        await this.axios({
          url:
            `${baseUrl}/web-thuno/api/thu-no/in-hoa-don/hoa-don-chuyen-doi`,
          data : body,
          method: "POST",
          responseType: "blob",
        }).then((response) => {
          previewPrint(response.data);
        });
      } catch (error) {
        this.$toast.error("Không có dữ liệu");
      } finally {
        this.$root.showLoading(false);
      }
    },
  },
};
</script>
