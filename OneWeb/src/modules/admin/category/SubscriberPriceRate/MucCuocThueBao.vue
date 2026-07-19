<template >
    <div class="main-wrapper">
        <div class="breadcrumb-top">
            <div class="main-title">Mức cước thuê bao</div>
            <ul class="breadcrumb">
                <li class="breadcrumb-item"><a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ
                    </a></li>
                <li class="breadcrumb-item"><a href="#">Lập hợp đồng</a></li>
                <li class="breadcrumb-item active">Lắp đặt mới</li>
            </ul>
        </div>
        <div class="list-actions-top">
            <ul class="list">

                <li>
                    <a href="#">
                        <span class="icon one-file-plus"></span>Nhập mới
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon one-save"></span> Ghi lại
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Hủy
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon one-trash"></span> Xóa
                    </a>
                </li>
            </ul>
        </div>
        <div class="page-content">
            <div class="grid-stack-box">
                <div class="box-col box-form marb0" id="boxLeft">
                    <ThongTinMucCuoc/>
                    <DanhSachMucCuoc/>   
                 <!--   <div class="tc-pagination">
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
                    </div> -->
                </div>
                <div class="box-col" id="boxRight" style="height: 800px;">

                    <div class="box-move-select-table box-form marb0" id="boxTop">
                        <div class="box-col">
                            <div class="legend-title">Đối tượng đã gán</div>

                            <div class="table-content" style="height: calc(100% - 80px);">
                                <table class="table-result table-gachle tree-grid-2">
                                    <thead class="center">
                                        <tr>
                                            <th class="w20"></th>
                                            <th class="w20">
                                                <div class="check-action">
                                                    <input type="checkbox" class="check">
                                                    <span class="name"></span>
                                                </div>
                                            </th>
                                            <th>Đối tượng</th>
                                        </tr>
                                        <tr>
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

                                        </tr>
                                    </thead>
                                    <tbody>

                                        <tr class="highlight">

                                            <td class="w20 center selected1">
                                                <span class="fa fa-play text-main"></span>
                                            </td>
                                            <td class="w20 center">
                                                <div class="check-action">
                                                    <input type="checkbox" class="check">
                                                    <span class="name"></span>
                                                </div>
                                            </td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td class="w20 center"></td>
                                            <td class="w20 center">
                                                <div class="check-action">
                                                    <input type="checkbox" class="check">
                                                    <span class="name"></span>
                                                </div>
                                            </td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td class="w20 center"></td>
                                            <td class="w20 center">
                                                <div class="check-action">
                                                    <input type="checkbox" class="check">
                                                    <span class="name"></span>
                                                </div>
                                            </td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td class="w20 center"></td>
                                            <td class="w20 center">
                                                <div class="check-action">
                                                    <input type="checkbox" class="check">
                                                    <span class="name"></span>
                                                </div>
                                            </td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td class="w20 center"></td>
                                            <td class="w20 center">
                                                <div class="check-action">
                                                    <input type="checkbox" class="check">
                                                    <span class="name"></span>
                                                </div>
                                            </td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td class="w20 center"></td>
                                            <td class="w20 center">
                                                <div class="check-action">
                                                    <input type="checkbox" class="check">
                                                    <span class="name"></span>
                                                </div>
                                            </td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td class="w20 center"></td>
                                            <td class="w20 center">
                                                <div class="check-action">
                                                    <input type="checkbox" class="check">
                                                    <span class="name"></span>
                                                </div>
                                            </td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td class="w20 center"></td>
                                            <td class="w20 center">
                                                <div class="check-action">
                                                    <input type="checkbox" class="check">
                                                    <span class="name"></span>
                                                </div>
                                            </td>
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
                        <div class="actions">
                            <button class="btn">
                                <span class="-ap icon-chevron-thin-right"></span>
                            </button>
                            <button class="btn">
                                <span class="-ap icon-chevron-thin-left"></span>
                            </button>
                        </div>
                        <div class="box-col">
                            <div class="legend-title">Đối tượng chưa gán</div>

                            <div class="table-content" style="height: calc(100% - 80px);">
                                <table class="table-result table-gachle tree-grid-2">
                                    <thead class="center">
                                        <tr>
                                            <th class="w20"></th>
                                            <th class="w20">
                                                <div class="check-action">
                                                    <input type="checkbox" class="check">
                                                    <span class="name"></span>
                                                </div>
                                            </th>
                                            <th>Đối tượng</th>
                                        </tr>
                                        <tr>
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

                                        </tr>
                                    </thead>
                                    <tbody>

                                        <tr class="highlight">

                                            <td class="w20 center selected1">
                                                <span class="fa fa-play text-main"></span>
                                            </td>
                                            <td class="w20 center">
                                                <div class="check-action">
                                                    <input type="checkbox" class="check">
                                                    <span class="name"></span>
                                                </div>
                                            </td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td class="w20 center"></td>
                                            <td class="w20 center">
                                                <div class="check-action">
                                                    <input type="checkbox" class="check">
                                                    <span class="name"></span>
                                                </div>
                                            </td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td class="w20 center"></td>
                                            <td class="w20 center">
                                                <div class="check-action">
                                                    <input type="checkbox" class="check">
                                                    <span class="name"></span>
                                                </div>
                                            </td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td class="w20 center"></td>
                                            <td class="w20 center">
                                                <div class="check-action">
                                                    <input type="checkbox" class="check">
                                                    <span class="name"></span>
                                                </div>
                                            </td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td class="w20 center"></td>
                                            <td class="w20 center">
                                                <div class="check-action">
                                                    <input type="checkbox" class="check">
                                                    <span class="name"></span>
                                                </div>
                                            </td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td class="w20 center"></td>
                                            <td class="w20 center">
                                                <div class="check-action">
                                                    <input type="checkbox" class="check">
                                                    <span class="name"></span>
                                                </div>
                                            </td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td class="w20 center"></td>
                                            <td class="w20 center">
                                                <div class="check-action">
                                                    <input type="checkbox" class="check">
                                                    <span class="name"></span>
                                                </div>
                                            </td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td class="w20 center"></td>
                                            <td class="w20 center">
                                                <div class="check-action">
                                                    <input type="checkbox" class="check">
                                                    <span class="name"></span>
                                                </div>
                                            </td>
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
                    <div class="box-form marb0" id="boxBottom">
                        <div class="box-move-select-table">
                            <div class="box-col">
                                <div class="legend-title">Tốc độ đã gán</div>
                            </div>
                            <div class="actions">
                            </div>
                            <div class="box-col">
                                <div class="legend-title">Tốc độ chưa gán</div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w60">Loại hình</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select name="" id="" class="form-control">
                                        <option value=""></option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="box-move-select-table" style="height: calc(100% - 70px);">
                            <div class="box-col">

                                <div class="table-content" style="height: calc(100% - 50px);">
                                    <table class="table-result table-gachle tree-grid-2">
                                        <thead class="center">
                                            <tr>
                                                <th class="w20"></th>
                                                <th class="w20">
                                                    <div class="check-action">
                                                        <input type="checkbox" class="check">
                                                        <span class="name"></span>
                                                    </div>
                                                </th>
                                                <th>Tốc độ</th>
                                                <th>Mức cước</th>
                                            </tr>
                                            <tr>
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
                                                <td class="w20 center selected1">
                                                    <span class="fa fa-play text-main"></span>
                                                </td>
                                                <td class="w20 center">
                                                    <div class="check-action">
                                                        <input type="checkbox" class="check">
                                                        <span class="name"></span>
                                                    </div>
                                                </td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="w20 center">

                                                </td>
                                                <td class="w20 center">
                                                    <div class="check-action">
                                                        <input type="checkbox" class="check">
                                                        <span class="name"></span>
                                                    </div>
                                                </td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="w20 center">

                                                </td>
                                                <td class="w20 center">
                                                    <div class="check-action">
                                                        <input type="checkbox" class="check">
                                                        <span class="name"></span>
                                                    </div>
                                                </td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="w20 center">

                                                </td>
                                                <td class="w20 center">
                                                    <div class="check-action">
                                                        <input type="checkbox" class="check">
                                                        <span class="name"></span>
                                                    </div>
                                                </td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="w20 center">

                                                </td>
                                                <td class="w20 center">
                                                    <div class="check-action">
                                                        <input type="checkbox" class="check">
                                                        <span class="name"></span>
                                                    </div>
                                                </td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="w20 center">

                                                </td>
                                                <td class="w20 center">
                                                    <div class="check-action">
                                                        <input type="checkbox" class="check">
                                                        <span class="name"></span>
                                                    </div>
                                                </td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="w20 center">

                                                </td>
                                                <td class="w20 center">
                                                    <div class="check-action">
                                                        <input type="checkbox" class="check">
                                                        <span class="name"></span>
                                                    </div>
                                                </td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="w20 center">

                                                </td>
                                                <td class="w20 center">
                                                    <div class="check-action">
                                                        <input type="checkbox" class="check">
                                                        <span class="name"></span>
                                                    </div>
                                                </td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="w20 center">

                                                </td>
                                                <td class="w20 center">
                                                    <div class="check-action">
                                                        <input type="checkbox" class="check">
                                                        <span class="name"></span>
                                                    </div>
                                                </td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="w20 center">

                                                </td>
                                                <td class="w20 center">
                                                    <div class="check-action">
                                                        <input type="checkbox" class="check">
                                                        <span class="name"></span>
                                                    </div>
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

                            <div class="actions">
                                <button class="btn">
                                    <span class="-ap icon-chevron-thin-right"></span>
                                </button>
                                <button class="btn">
                                    <span class="-ap icon-chevron-thin-left"></span>
                                </button>
                            </div>
                            <div class="box-col">

                                <div class="table-content" style="height: calc(100% - 50px);">
                                    <table class="table-result table-gachle tree-grid-2">
                                        <thead class="center">
                                            <tr>
                                                <th class="w20"></th>
                                                <th class="w20">
                                                    <div class="check-action">
                                                        <input type="checkbox" class="check">
                                                        <span class="name"></span>
                                                    </div>
                                                </th>
                                                <th>Tốc độ</th>
                                                <th>Mức cước</th>
                                            </tr>
                                            <tr>
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
                                                <td class="w20 center selected1">
                                                    <span class="fa fa-play text-main"></span>
                                                </td>
                                                <td class="w20 center">
                                                    <div class="check-action">
                                                        <input type="checkbox" class="check">
                                                        <span class="name"></span>
                                                    </div>
                                                </td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="w20 center">

                                                </td>
                                                <td class="w20 center">
                                                    <div class="check-action">
                                                        <input type="checkbox" class="check">
                                                        <span class="name"></span>
                                                    </div>
                                                </td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="w20 center">

                                                </td>
                                                <td class="w20 center">
                                                    <div class="check-action">
                                                        <input type="checkbox" class="check">
                                                        <span class="name"></span>
                                                    </div>
                                                </td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="w20 center">

                                                </td>
                                                <td class="w20 center">
                                                    <div class="check-action">
                                                        <input type="checkbox" class="check">
                                                        <span class="name"></span>
                                                    </div>
                                                </td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="w20 center">

                                                </td>
                                                <td class="w20 center">
                                                    <div class="check-action">
                                                        <input type="checkbox" class="check">
                                                        <span class="name"></span>
                                                    </div>
                                                </td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="w20 center">

                                                </td>
                                                <td class="w20 center">
                                                    <div class="check-action">
                                                        <input type="checkbox" class="check">
                                                        <span class="name"></span>
                                                    </div>
                                                </td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="w20 center">

                                                </td>
                                                <td class="w20 center">
                                                    <div class="check-action">
                                                        <input type="checkbox" class="check">
                                                        <span class="name"></span>
                                                    </div>
                                                </td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="w20 center">

                                                </td>
                                                <td class="w20 center">
                                                    <div class="check-action">
                                                        <input type="checkbox" class="check">
                                                        <span class="name"></span>
                                                    </div>
                                                </td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="w20 center">

                                                </td>
                                                <td class="w20 center">
                                                    <div class="check-action">
                                                        <input type="checkbox" class="check">
                                                        <span class="name"></span>
                                                    </div>
                                                </td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="w20 center">

                                                </td>
                                                <td class="w20 center">
                                                    <div class="check-action">
                                                        <input type="checkbox" class="check">
                                                        <span class="name"></span>
                                                    </div>
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
                </div>
            </div>
        </div>
        <!-- <footer class="footer">
            VNPT-IT &copy; 2021
        </footer>
        <script src="../vendor/jquery/split.js"></script>
        <script type="text/javascript">
            Split(['#boxLeft', '#boxRight'], {
                sizes: [50, 50],
                gutterSize: 16,
                onDragEnd: function (sizes) {
                },
            });

        </script>
        <script type="text/javascript">
            Split(['#boxTop', '#boxBottom'], {
                sizes: [50, 50],
                direction: 'vertical',
                minSize:[300,300],

            })
        </script> -->
    </div>
</template>
<script>
import ThongTinMucCuoc from './part/ThongTinMucCuoc'
import DanhSachMucCuoc from './part/DanhSachMucCuoc'
export default {
  components: {
    ThongTinMucCuoc,
    DanhSachMucCuoc

  },
  data () {
    return {

    }
  },
  mounted () {
    Split(['#boxLeft', '#boxRight'], {
      sizes: [50, 50],
      gutterSize: 16,
      direction: 'vertical-rl',
      //   minSize: [300, 300],
      onDragEnd: function (sizes) {
      }
    })
    Split(['#boxTop', '#boxBottom'], {
      sizes: [50, 50],
      direction: 'vertical',
      minSize: [300, 300]

    })
  }
}
</script>
