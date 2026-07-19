<template>
<div>
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>Chọn đơn vị quản lý</div>
        <div class="close -ap icon-close" data-dismiss="modal">
        </div>
    </div>
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận
                </a>
            </li>
        </ul>
    </div>
    <div class="popup-body">
        <div class="box-form">
            <div class="legend-title">
                <div class="pull-left">
                    Danh sách đơn vị
                </div>
                <div class="pull-right red">
                    Lựa chọn đơn vị quản lý Địa chỉ TT
                </div>
                <div class="clearfix"></div>
                
            </div>
            <div class="table-content">
                <table class="table-result table-gachle">
                    <thead class="center">
                        <tr>
                            <th class="w20"></th>
                            <th>ID</th>
                            <th>Đơn vị</th>
                        </tr>
                        <tr>
                            <th class="w20"></th>
                            <th>
                                <div class="input-icon-right">
                                    <input type="text" class="form-control">
                                    <span class="icon nc-icon-outline ui-1_zoom"></span>
                                </div>
                            </th>
                            <th>
                                <div class="input-icon-right">
                                    <input type="text" class="form-control">
                                    <span class="icon nc-icon-outline ui-1_zoom"></span>
                                </div>
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr class="highlight">
                            <td class="w20 center">
                                <span class="fa fa-play text-main"></span>
                            </td>
                            <td class="selected highlight1"></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td class="w20 center">
                            </td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td class="w20 center">
                            </td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td class="w20 center">
                            </td>
                            <td></td>
                            <td></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="tc-pagination">
                <a href="#" class="btn  one-arrow-left-forward"></a>
                <a href="#" class="btn fa fa-angle-double-left"></a>
                <a href="#" class="btn fa fa-angle-left"></a>
                <span class="text">Bản ghi</span>
                <div class="dropdown inline">
                    <div class="fw6 input-number" data-toggle="dropdown">
                        <span class="text-main">10</span>/1033
                    </div>
                    <div class="dropdown-menu">
                        <a href="#" class="dropdown-item">10</a>
                        <a href="#" class="dropdown-item">20</a>
                    </div>
                </div>
                <a href="#" class="btn fa fa-angle-right"></a>
                <a href="#" class="btn fa fa-angle-double-right"></a>
                <a href="#" class="btn one-arrow-right-forward"></a>
            </div>
        </div>
    </div>
</div>
</template>
<style scoped>

</style>
<script>
export default {
    name: 'popupDVQL',
    props: [ ],
    watch: {
    },
    data() {
        return {
            page: 0, //Page number
        }
    },
    mounted() {
    },
    computed: {
    },
    methods: {
        closeForm() {
            this.$emit('form-close',null);
        },
    }
}
</script>