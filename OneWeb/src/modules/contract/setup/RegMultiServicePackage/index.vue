<template>
    <div>
        <vue-bread-crumb :header="header" />
        <dangkygoidadichvu />
    </div>
</template>
<script>
import dangkygoidadichvu from './components/dangkygoidadichvu.vue'
export default {
    data() {
        return {
            header: {
                title: "ĐĂNG KÝ GÓI ĐA DỊCH VỤ",
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
        dangkygoidadichvu
    }
}
</script>