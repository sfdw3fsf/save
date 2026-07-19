<template src="./index.popup.html"></template>
<script>
import TraCuuDoTruyenDanTheoThietBi from '@/modules/CABMAN/TraCuuDoTruyenDanTheoThietBi';
export default {
    components: {
        TraCuuDoTruyenDanTheoThietBi
    },
    name: 'ModalDDCTruyenDan',
    props: ["modalId", "data"],
    data: function () {
        return {
            panzoom: '',
            gianDoLanDau: true,
            pageSettings: { pageSizes: true, pageCount: 4, pageSize: 10 },
            nodesGianDo1: [],
            connectorsGianDo1: [],
            danDo: {
                viTri: null,
                thietBiId: null,
                loaiTbiId: null,
                thietBiGocId: null,
                loaiTbiGocId: null,
                donViId: null,
                toVtId: null,
                ttVtId: null
            },
            dataSelected: {
                ttvt: 0,
                toKT: 0,
                tramTB: 0,
                cong: 0,
                loaiThietBiCha: 0,
                thietBiCha: 0,
                thietBiCon: '',
            },
            dsTTVT: [],
            dsToKT: [],
            dsTramTB: [],
            dsCong: [],
            dsLoaiThietBi: [],
            dsThietBiCha: [],
            dsThietBiCon: [],
            danDoList: [],
            searchText: null,
            gianDoRefresh: false,
            dsThietBiSearch: [],
            showDataSearch: false,
            dataFake: {
                ttvt: 6,
                toKT: 179,
                tramTB: 418,
                loaiThietBiCha: 18,
                thietBi: 690,
                cong: 1
            }
        }
    },
    methods: {
        modalShow: async function () {
            await this.$refs.controlTraCuuDoTruyenDanTheoThietBi.bindData(this.data);
        },
    },
};
</script>
<style src="./index.scss"></style>
