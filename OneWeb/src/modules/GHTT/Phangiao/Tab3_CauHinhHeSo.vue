<template>
    <div class="nocorner bor0 pad0 bg-none" style="box-shadow: none;">
        <div id="tab3" class="tab-pane">
            <div class="tab-content" v-if="currentTab != 'tab1' && currentTab != 'tab2'">
                <div class="row">
                    <div class="col-sm-12 col-12">
                        <div class="box-form">
                            <div class="legend-title">Cấu hình hệ số quy đổi</div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w120">Hệ số loại 1 dịch vụ</div>
                            <div class="value">
                                <input type="text" class="form-control highlight">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w120">Mô tả quy đổi</div>
                            <div class="value">
                                <input type="text" class="form-control highlight">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w120">Hệ số loại 2 dịch vụ</div>
                            <div class="value">
                                <input type="text" class="form-control highlight">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w120">Mô tả quy đổi</div>
                            <div class="value">
                                <input type="text" class="form-control highlight">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w120">Hệ số loại 3 dịch vụ</div>
                            <div class="value">
                                <input type="text" class="form-control highlight">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w120">Mô tả quy đổi</div>
                            <div class="value">
                                <input type="text" class="form-control highlight">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>


