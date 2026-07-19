<template>
  <div>
      <div class="popup-body">
        <vue-card>
            <DataGrid panelDataHeight="240" :columns="config" :dataSource="options"
            ></DataGrid>
        </vue-card>
      </div>
  </div>
</template>
<script>
import api from '../api/index.js'
export default {
    props: {
        vphieukn_id: [String, Number],
        vnhanvien_id: [String, Number],
        htkn_id: [String, Number],
    },
    data() {
        return {
            config: [
                {
                    headerText: "Ngày giao",
                    filterAble: true,
                    width: '100',
                    fieldName: 'ngaygiao'
                },
                {
                    headerText: "Người giao",
                    filterAble: true,
                    width: '100',
                    fieldName: 'nv_giao'
                },
                {
                    headerText: "Mã NV TH",
                    filterAble: true,
                    width: '100',
                    fieldName: 'ma_nv'
                },
                {
                    headerText: "Người thực hiện",
                    filterAble: true,
                    width: '100',
                    fieldName: 'ten_nv'
                },
                {
                    headerText: "User người TH",
                    filterAble: true,
                    width: '100',
                    fieldName: 'ten_nv'
                },
                {
                    headerText: "Điện thoại",
                    filterAble: true,
                    width: '100',
                    fieldName: 'so_dt'
                },
                {
                    headerText: "Nhiệm vụ",
                    filterAble: true,
                    width: '100',
                    fieldName: 'str_nhiemvu'
                },
                {
                    headerText: "Kết quả XL",
                    filterAble: true,
                    width: '100',
                    fieldName: 'kqxl'
                },
                {
                    headerText: "Trạng thái",
                    filterAble: true,
                    width: '100',
                    fieldName: 'trangthai'
                },
                {
                    headerText: "Ghi chú",
                    filterAble: true,
                    width: '100',
                }
            ],
            options: []
        }
    },
    methods: {
        init() {
            this.getNVTheoPhieuID()
        },
        getNVTheoPhieuID() {
            api.getNVTheoPhieuID(this.axios, {
                "vkieu_id": 2,
                "vphieukn_id": this.vphieukn_id,
                "vnhanvien_id": this.vnhanvien_id
            }).then(res => {
                this.options = res.data.data ? res.data.data : []
            })
        }
    }
};
</script>