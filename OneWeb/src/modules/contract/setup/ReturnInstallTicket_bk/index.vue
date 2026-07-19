<template>
    <div>
        <vue-bread-crumb :header="header" />
        <traphieu :ma_gd="ma_gd" :ma_tb="ma_tb" :loaihd_id="loaihd_id" :dichvuvt_id="dichvuvt_id"
        :ngaygiao="ngaygiao" :ngaytra="ngaytra" :kieu="kieu" :nhanvien_id="nhanvien_id"
        />
    </div>
</template>
<script>
import traphieu from './components/traphieu'
export default {
    data() {
        return {
            header: {
                title: "GIAO PHIÊU ĐIỀU HÀNH THI CÔNG",
                list: [
                { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
                {
                    name: "Lắp đặt mới",
                    link: { name: "Ui.buttons" },
                },
                ],
            },
        }
    },
    components: {
        traphieu
    },
    props: [
        'ma_gd',
        'ma_tb',
        'loaihd_id',
        'dichvuvt_id',
        'ngaygiao',
        'ngaytra',
        'kieu',
        'nhanvien_id'
    ]
}
</script>
