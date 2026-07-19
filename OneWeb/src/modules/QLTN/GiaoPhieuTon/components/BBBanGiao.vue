<template>
    <div>
        <div class="header d-flex justify-content-around">
            <div class="d-inline-flex align-items-center">
                <div class="logo">
                    <img width="60px" src="../../../../assets/img/vnpt-logo.svg">
                </div>
                <div class="text-center">                    
                    <div>Số: ......./TTKD - BB</div>
                </div>
            </div>
            <div class="text-center fw6" style="font-size: 18px;">
                <div>CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM</div>
                <div>Độc lập - Tự do - Hạnh phúc</div>
                <div>------o0o------</div>
            </div>
        </div>
        <div class="body">
            <div class="fw6 text-center mt-4" style="font-size: 18px;">
                BIÊN BẢN BÀN GIAO
            </div>
            <div class="fw6 text-center mt-4 font-italic" style="font-size: 14px;">
                Hóa đơn thu cước
            </div>
            <div class="mt-4">
                &emsp;&emsp;&emsp;&emsp;Hôm nay, ngày {{ currentDate.date }} tháng {{ currentDate.month }} năm {{ currentDate.year }} vào lúc {{ currentDate.hour }} giờ {{ currentDate.minute }} phút, tại Trung tâm Kinh Doanh 
                chúng tôi thực hiện bàn giao Hóa đơn thu cước các dịch vụ viễn thông - CNTT cho Nhân viên thu cước và CSKH:
            </div>
            <div class="mt-4">
                Bên giao: TRUNG TÂM KINH DOANH
            </div>
            <div class="mt-4">
                Ông(Bà): <span class="fw6">{{ data.NGUOIGIAO }}</span>
            </div>
            <div class="mt-4">
                Bên nhận: NHÂN VIÊN THU CƯỚC VÀ CSKH
            </div>
            <div class="mt-4">
                Ông(Bà): <span class="fw6">{{ data.NGUOINHAN }}</span> <span>Mã Đường thu: {{ data.TUYENTHU }}</span>
            </div>
            <div class="mt-4">
                Khu vực thu: <span>{{  }}</span>
            </div>
            <div class="mt-4">
                Phòng: <span>{{ data.TEN_DVQL }}</span>
            </div>
            <div class="mt-4 fw6">
                Nội dung bàn giao: Bàn giao Hóa đơn thu cước các dịch vụ viễn thông - CNTT kỳ T {{ data.KYCUOC }}
            </div>
            <div class="mt-4">
                &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Tổng số hóa đơn: <span>{{ data.TONGHD }}</span>
            </div>
            <div class="mt-4">
                &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Từ seri: <span>{{ data.SO_SERI }}</span> <span class="font-italic">(Có bản kê chi tiết kèm theo)</span>
            </div>
            <div class="mt-4">
                &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Tổng số tiền thu: <span>{{ data.TIEN_HD }}</span>
            </div>
            <div class="mt-4">
                &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Số tiền bằng chữ: <span class="font-italic">{{ data.TIENCHU }}</span>
            </div>
            <div class="mt-4">
                Biên bản được lập thành 04 bản có giá trị như nhau, mỗi bên giữ 02 bản.
            </div>
        </div>
        <!-- <div class="html2pdf__page-break"></div> -->
        <div class="p-4 mt-4">
            <div class="d-flex justify-content-around">
                <div class="text-center fw6">
                    <div>
                        BÊN GIAO
                    </div>
                    <div style="margin-top:60px">
                        {{data.NGUOIGIAO}}
                    </div>
                </div>
                <div class="text-center fw6">
                    <div class="fw6">
                        BÊN NHẬN
                    </div>
                    <div style="margin-top:60px">
                        {{data.NGUOINHAN}}
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<style scoped>
.table-1 tr td:first-child {
  padding-right: 1rem;
}

.table-2 {
  border: 1px solid #000;
}
.table-2 thead tr th {
  border: 1px solid #000;
}

.table-2 tbody tr td {
  border: 1px solid #000;
}

table, tr, td, th, tbody, thead, tfoot {
  page-break-inside: avoid !important;
}
</style>
<script>
import moment from 'moment';
export default {
  name: "BBBanGiao",
  components: {},
  props: ["data"],
  data: function () {
    return {
      nguoiGiao: this.$auth.getMaNhanVien(),
      currentDate: {
        date: moment(new Date(), 'DD/MM/YYYY').format('DD'),
        month: moment(new Date(), 'DD/MM/YYYY').format('MM'),
        year: moment(new Date(), 'DD/MM/YYYY').format('YYYY'),
        hour: moment(new Date()).format('HH'),
        minute: moment(new Date()).format('mm')
      }
    };
  },
  computed: {},
  validations: {},
  watch: {},
  created: async function () {
    console.log(this.currentDate)
  },
  async mounted() {},
  methods: {
  },
};
</script>
