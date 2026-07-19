<template>
    <div class="box-form">
        <div class="legend-title">Danh sách câu hỏi</div>
        <div class="box-quest marb20" v-for="(cauHoi, index) in fakeData.cauHoi" :key="index" :style="{'margin-left':cauHoi.THUTU == 0 ? '20px': '0px'}">
            <div class="title-bg-main">
                <span class="title underline">{{cauHoi.THUTU == 0 ? `Câu hỏi phụ` : `Câu hỏi ${cauHoi.THUTU}` }}</span>
            </div>
            <div class="desc marb10">
                {{ cauHoi.NOIDUNG }}
            </div>
            <div class="list-aw">
                <div class="item padt5 padb5" v-for="(cauHoiPhu, indexPhu) in fakeData.cauHoi.filter(x => x.CAUHOI_ID == cauHoi.CAUHOI_ID)" :key="cauHoiPhu.RCH_ID + cauHoiPhu.RCH_ID1" >
                    <div class="check-action">
                        <input
                            :type=" cauHoiPhu.LOAICH_ID == 1 ? 'radio' : 'checkbox' "
                            class="check"
                            :name="cauHoi.CAUHOI_ID"
                            v-model="cauHoiPhu.Checked"
                            :checked="cauHoiPhu.Checked"
                            :value="true"
                        />
                        <span class="name">{{ cauHoiPhu.NOIDUNG_TL }}</span>
                    </div>
                    <input
                        type="text"
                        class="form-control"
                        v-if="cauHoiPhu.Checked"
                        v-model="cauHoiPhu.Text"
                        @change="onNoiDungCauHoiPhuChanged( index, indexPhu )"
                    />
                </div>
            </div>
            <!-- <template v-for="(cauHoiPhu, indexPhu) in cauHoi.cauHoiPhu">
                <div
                    class="box-quest box-form marb20 marl20"
                    style="background: #fff9eb"
                    v-show=" cauHoiPhu.Checked && cauHoiPhu.dsCauHoiChau && cauHoiPhu.dsCauHoiChau.length > 0"
                    :key="cauHoiPhu.RCH_ID1"
                >
                    <div class="title-bg-main">
                        <span class="title underline">Câu hỏi phụ</span>
                    </div>
                    <div class="desc marb10">
                        <p>{{ cauHoiPhu.NOIDUNG_TL }}</p>
                    </div>
                    <div class="list-aw">
                        <div
                            class="item padt5 padb5"
                            v-for="(cauHoiChau,
                            indexChau) in cauHoiPhu.dsCauHoiChau"
                            :key="cauHoiChau.TRALOI_ID"
                        >
                            <div class="check-action">
                                <input
                                    :type=" cauHoiChau.LOAICH_ID == 1 ? 'radio' : 'checkbox' "
                                    class="check"
                                    :name="cauHoiPhu.RCH_ID1"
                                    :checked="cauHoiChau.Checked"
                                    @click=" onTraLoiCauHoiPhuChecked( index, indexPhu, indexChau ) "
                                />
                                <span class="name">{{ cauHoiChau.NOIDUNG_TL }}</span>
                            </div>
                            <input
                                type="text"
                                class="form-control" maxlength="500"
                                v-if=" cauHoiChau.Checked && cauHoiChau.LOAI == 1 "
                                v-model="cauHoiChau.Text"
                                @change="onNoiDungCauHoiChauChanged( index, indexPhu, indexChau )"
                            />
                        </div>
                    </div>
                </div>
            </template> -->
        </div>
    </div>
</template>
<script>
export default {
    props: {
        nhanVienId: { type: Number, default: 25 },
        hinhThuc: { type: Number, default: 0 },
        tbtlId: {type: Number, default: 0}
    },
    computed: {
        ketQuaKS() {
            let dtKQ = []
            this.pKhaoSat.dsCauHoiChinh.forEach((ch, indexChinh) => {
                ch.cauHoiPhu.forEach((chp, indexPhu) => {
                    if (chp.Checked) {
                        dtKQ.push({
                            CAUHOI_ID: chp.CAUHOI_ID,
                            TRALOI_ID: chp.TRALOI_ID,
                            NOIDUNG: chp.Text,
                            CAUHOI: ch.TENCAUHOI,
                            TRALOI: chp.NOIDUNG_TL,
                            DIEULAI: chp.DIEULAI,
                            RCH_ID: chp.RCH_ID1,
                            DIEULAI_XN: chp.DIEULAI_XN
                        })

                        if (chp.dsCauHoiChau && chp.dsCauHoiChau.length > 0) {
                            //có câu hỏi cháu
                            chp.dsCauHoiChau.forEach((chc, indexChau) => {
                                if (chc.Checked) {
                                    dtKQ.push({
                                        CAUHOI_ID: chc.CAUHOI_ID,
                                        TRALOI_ID: chc.TRALOI_ID,
                                        NOIDUNG: chc.Text,
                                        CAUHOI: ch.TENCAUHOI,
                                        TRALOI: chc.NOIDUNG_TL,
                                        DIEULAI: chc.DIEULAI,
                                        RCH_ID: chc.RCH_ID,
                                        DIEULAI_XN: chc.DIEULAI_XN
                                    })
                                }
                            })
                        }
                    }


                })
            })
            return dtKQ
        },
        dsCauHoiChinh() {
            return this.pKhaoSat.dsCauHoiChinh
        },
        dsCauHoiPhu() {
            return this.pKhaoSat.dsCauHoiPhu
        },
        dsCauHoiChau() {
            return this.pKhaoSat.dsCauHoiChau
        },
    },
    data() {
        return {
            API_SUCCESS: "BSS-00000000",
            pKhaoSat: {
                dsCauHoiChinh: [],
                dsCauHoiPhu: [],
                dsCauHoiChau: [],
                dsCauTraLoi: []
            },
            fakeData: {
                cauHoi: [],
                traLoi: []
            }
        };
    },
    async mounted() {

    },
    methods: {
        onChange() {
            this.fakeData.cauHoi = [...new Set(this.fakeData.cauHoi)]
        },
        async loadData() {
            this.$forceUpdate()
            this.fakeData = {
                cauHoi: [],
                traLoi: []
            }
            this.pKhaoSat = {
                dsCauHoiChinh: [],
                dsCauHoiPhu: [],
                dsCauHoiChau: [],
                dsCauTraLoi: []
            }
            await this.getDsCauHoiTheoNV();
            await this.getChiTietTraLoi();
            this.processFakeData();
        },
        processFakeData() {
            let dsCauHoiId = [...new Set(this.fakeData.cauHoi.map(item => item.CAUHOI_ID))]
            console.log("dsCauHoiId", dsCauHoiId)

            this.fakeData.cauHoi.forEach(ch => {
                let tl = this.fakeData.traLoi.filter(x => x.CAUHOI_ID == ch.CAUHOI_ID && x.TRALOI_ID == ch.TRALOI_ID)
                if (tl.length > 0)
                {
                    // console.log("tl", tl)
                    // console.log("ch", ch)
                    ch.Checked = true
                    ch.Text = tl[0].NOIDUNG
                }
                else
                {
                    ch.Checked = false
                    ch.Text = ''
                }
            });

            this.fakeData.cauHoi = [...new Set(this.fakeData.cauHoi)]
            this.fakeData.cauHoi = this.fakeData.cauHoi.filter(item => item.Checked)

            // this.fakeData.cauHoi.forEach(x => {
            //     var i = this.pKhaoSat.dsCauHoiChinh.findIndex( cauhoi => x.CAUHOI_ID == cauhoi.CAUHOI_ID );
            //     if (i <= -1 && x.KIEU != 2) {
            //         let dsCauHoiPhu = this.fakeData.cauHoi.filter( y => x.CAUHOI_ID == y.CAUHOI_ID && y.RCH_ID == 0 );

            //         dsCauHoiPhu.forEach(chp => {
            //             let dsCauHoiChau = this.fakeData.cauHoi.filter( y => y.RCH_ID == chp.RCH_ID1 );
            //             dsCauHoiChau.forEach(e => {
            //                 let dapan = this.fakeData.traLoi.filter( tl => tl.CAUHOI_ID == e.CAUHOI_ID && tl.TRALOI_ID == e.TRALOI_ID );
            //                 if (dapan.length > 0) {
            //                     e.Checked = true;
            //                     e.Text = dapan[0].NOIDUNG;
            //                 } else {
            //                     e.Checked = false;
            //                     e.Text = "";
            //                 }
            //             });
            //             chp.dsCauHoiChau = dsCauHoiChau;
            //             this.pKhaoSat.dsCauHoiChau = this.pKhaoSat.dsCauHoiChau.concat(dsCauHoiChau)

            //             let dapan = this.fakeData.traLoi.filter( tl => tl.CAUHOI_ID == chp.CAUHOI_ID && tl.TRALOI_ID == chp.TRALOI_ID );

            //             if (dapan.length > 0) {
            //                 chp.Checked = true;
            //                 chp.Text = dapan[0].NOIDUNG;
            //             } else {
            //                 chp.Checked = false;
            //                 chp.Text = "";
            //             }
            //         });

            //         dsCauHoiPhu = dsCauHoiPhu.sort((a, b) => a.THUTU_TL > b.THUTU_TL ? 1 : -1 );

            //         this.pKhaoSat.dsCauHoiChinh.push({
            //             CAUHOI_ID: x.CAUHOI_ID,
            //             TENCAUHOI: x.NOIDUNG,
            //             KIEU: x.KIEU,
            //             HINHTHUC: x.HINHTHUC,
            //             LOAICH_ID: x.LOAICH_ID,
            //             THUTU: x.THUTU,
            //             TRALOI_ID: x.TRALOI_ID,
            //             cauHoiPhu: dsCauHoiPhu
            //         });
            //         this.pKhaoSat.dsCauHoiPhu = this.pKhaoSat.dsCauHoiPhu.concat(dsCauHoiPhu)
            //     }
            // });
            // this.pKhaoSat.dsCauHoiChinh = this.pKhaoSat.dsCauHoiChinh.sort( (a, b) => (a.THUTU > b.THUTU ? 1 : -1) );
        },
        async getDsCauHoiTheoNV() {
            try {
                let rs = await this.$root.context.get( "/web-cskh/api/khao-sat-thue-bao/cau-hoi", { nhanVienId: this.nhanVienId, hinhThuc: this.hinhThuc } );

                if (rs.error_code === this.API_SUCCESS) {
                    this.fakeData.cauHoi = rs.data || [];
                    this.fakeData.cauHoi.forEach( (element, index) => (element.STT = index) );
                }
            } catch (e) {
                console.log(e);
            }

        },
        async getChiTietTraLoi() {
            try {
                if (this.tbtlId == 0) {
                    this.fakeData.traLoi = []
                    return
                }
                let rs = await this.$root.context.get( "/web-cskh/api/xac-nhan-phan-hoi-chat-luong/chi-tiet-tbtl", { tbtlId: this.tbtlId, } );

                if (rs.error_code === this.API_SUCCESS) {
                    this.fakeData.traLoi = rs.data || [];
                }
            } catch (e) {
                console.log(e);
            }
        },
        async getDsCauHoiPhu() {
            try {
                this.loading(true);
                this.dsCauHoiPhu = [];
                let response = await API.getDsCauHoiPhu(this.axios, { cauHoiId: this.pRdoCauHoiChaId });
                if (response.data.error_code === this.API_SUCCESS) {
                    this.dsCauHoiPhu = response.data.data || [];
                }
            } catch (e) {
            } finally {
                this.loading(false);
            }
        },
        onTraLoiChecked(cauHoiId, traLoiId, isRadio) {
            console.log('checked')
            //let ch = this.fakeData.cauHoi.find(x => x.CAUHOI_ID == cauHoiId && x.TRALOI_ID == traLoiId)
            this.fakeData.cauHoi.find(x => x.CAUHOI_ID == cauHoiId && x.TRALOI_ID == traLoiId).Checked = !this.fakeData.cauHoi.find(x => x.CAUHOI_ID == cauHoiId && x.TRALOI_ID == traLoiId).Checked
            if (isRadio) {
                // for ( let i = 0; i < this.fakeData.cauHoi.filter(x => x.CAUHOI_ID == cauHoiId).length; i++ )
                // {
                //     if (this.fakeData.cauHoi.filter(x => x.CAUHOI_ID == cauHoiId)[i].TRALOI_ID != traLoiId) {
                //         this.fakeData.cauHoi.filter(x => x.CAUHOI_ID == cauHoiId)[i].Checked = false;
                //     }
                // }
                //ch.Checked = !ch.Checked;
            }
            // else {
            //     ch.Checked = !ch.Checked;
            // }
            //ch.Checked = !ch.Checked;



            this.fakeData.cauHoi = [...new Set(this.fakeData.cauHoi)]
            // this.$set( this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu, indexPhu, ch );
        },
        onTraLoiCauHoiPhuChecked(index, indexPhu, indexChau) {
            let chp = this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu[indexPhu];
            if (chp.LOAICH_ID == 1) {
                for (let i = 0; i < chp.dsCauHoiChau.length; i++) {
                    if (i != indexChau) {
                        if (chp.dsCauHoiChau[i].LOAICH_ID == 1) {
                             chp.dsCauHoiChau[i].Checked = false;
                        }
                    } else {
                        chp.dsCauHoiChau[i].Checked = !chp.dsCauHoiChau[i] .Checked;
                    }
                }
            } else {
                chp.dsCauHoiChau[i].Checked = !chp.dsCauHoiChau[i].Checked;
            }

            this.$set( this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu, indexPhu, chp );
        },
        onNoiDungCauHoiPhuChanged(index, indexPhu, isRadio) {
            let ch = this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu[indexPhu];
            this.$set( this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu, indexPhu, ch );
        },
        onNoiDungCauHoiChauChanged(index, indexPhu, indexChau) {
            let chp = this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu[indexPhu];
            this.$set( this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu, indexPhu, chp );
        },
    }
};
</script>
