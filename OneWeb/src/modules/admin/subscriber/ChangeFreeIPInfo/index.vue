<template>
    <div>
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a href="#">
                        <span class="icon design_bullet-list-67 nc-icon-glyph"></span> Danh sách
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp nhận
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon one-excel"></span> Xuất Excel
                    </a>
                </li>
            </ul>
            <div class="more">
                <a href="#">
                    <span class="icon -ap  icon-dots-three-vertical"></span>
                </a>
            </div>
        </div>
        <div class="page-content">     
            <button   class="btn btn-action" id="button-id"  v-b-modal.popupModalChangeFreeIPInfo>Show popup</button>
        </div>
        <ModalChangeFreeIPInfo/>
    </div>
</template>
<script>

    import ModalChangeFreeIPInfo from './parts'
    export default {
        components: { ModalChangeFreeIPInfo },
        name: 'ChangeFreeIPInfo',
        computed: {
        },
        destroyed () {
        },
        methods: {  
          },
        created () {
        },
        data () {
            return {
             
            }
        }
    }
</script>