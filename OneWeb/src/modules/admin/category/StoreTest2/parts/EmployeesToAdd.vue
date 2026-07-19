<template>
    <div class="box-form">
        <div class="legend-title">Danh sách đơn vị</div>
        <div class="box-move-select-table">
            <div class="actions w40 padl0">
                <button class="btn">
                    <span class="-ap icon-chevron-thin-right"></span>
                </button>
                <button class="btn">
                    <span class="-ap icon-chevron-thin-left"></span>
                </button>
            </div>
            <div class="box-col" style="width:calc(100% - 40px);">
                <div class="table-content" style="height: 248px;">
                    <table class="table-result table-gachle tree-grid-2">
                        <thead class="center">
                            <tr>
                                <th class="w20"></th>
                                <th class="w20"></th>
                                <th class="w20">
                                    <div class="check-action">
                                        <input type="checkbox" class="check">
                                        <span class="name"></span>
                                    </div>
                                </th>
                                <th>Mã NV</th>
                                <th>Tên n...</th>
                            </tr>
                            <tr>
                                <th class="w20"></th>
                                <th class="w20"></th>
                                <th class="w20">
                                    <div class="check-action">
                                        <input type="checkbox" disabled="" class="check">
                                        <span class="name"></span>
                                    </div>
                                </th>
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
                                <th class="w20 center selected1">
                                    <span class="fa fa-play text-main"></span>
                                </th>
                                <td colspan="4" class="fw6 tree-open highlight1">
                                    <span class="tree-arrow"></span> Đơn vị : Ban công nghệ
                                </td>
                            </tr>
                            <tr>
                                <th class="w20 center">

                                </th>
                                <td class="tree-first w20">
                                    <div class="tree-line"></div>
                                    <div class="tree-indent"></div>
                                </td>
                                <td class="w20 center">
                                    <div class="check-action">
                                        <input type="checkbox" class="check">
                                        <span class="name"></span>
                                    </div>
                                </td>
                                <td class=""></td>
                                <td></td>
                            </tr>
                            <tr>
                                <th class="w20 center">
                                </th>
                                <td class="tree-first w20">
                                    <div class="tree-line"></div>
                                    <div class="tree-indent"></div>
                                </td>
                                <td class="w20 center">
                                    <div class="check-action">
                                        <input type="checkbox" class="check">
                                        <span class="name"></span>
                                    </div>
                                </td>
                                <td class=""></td>
                                <td></td>
                            </tr>
                            <tr>
                                <th class="w20 center">

                                </th>
                                <td class="tree-first w20">
                                    <div class="tree-line"></div>
                                    <div class="tree-indent"></div>
                                </td>
                                <td class="w20 center">
                                    <div class="check-action">
                                        <input type="checkbox" class="check">
                                        <span class="name"></span>
                                    </div>
                                </td>
                                <td class=""></td>
                                <td></td>
                            </tr>
                            <tr>
                                <th class="w20 center">

                                </th>
                                <td class="tree-first w20">
                                    <div class="tree-line"></div>
                                    <div class="tree-indent"></div>
                                </td>
                                <td class="w20 center">
                                    <div class="check-action">
                                        <input type="checkbox" class="check">
                                        <span class="name"></span>
                                    </div>
                                </td>
                                <td class=""></td>
                                <td></td>
                            </tr>
                            <tr>
                                <th class="w20 center">

                                </th>
                                <td class="tree-first w20">
                                    <div class="tree-line"></div>
                                    <div class="tree-indent"></div>
                                </td>
                                <td class="w20 center">
                                    <div class="check-action">
                                        <input type="checkbox" class="check">
                                        <span class="name"></span>
                                    </div>
                                </td>
                                <td class=""></td>
                                <td></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="tc-pagination  ovauto nowrap h34">
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
