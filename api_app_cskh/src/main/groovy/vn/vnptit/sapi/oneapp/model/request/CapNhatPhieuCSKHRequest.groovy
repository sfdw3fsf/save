package vn.vnptit.sapi.oneapp.model.request
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.validation.annotation.Validated

import javax.annotation.Generated

/**
 * Cập nhật phiếu CSKH
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
class CapNhatPhieuCSKHRequest {
    Long phieutn_id
    Long khachhang_id
    String thanhtoan //=> 1,2,3,4 => khong co để null
    String thuebao  //=> 1,2,3,4 => khong co để null
    Long loainc_id
    List<LienHe> dslienhe
    String ten_lh
    String diachi
    List<KhaoSatDV> dskhaosatdv
    List<ToaDo> toado
}

class LienHe {
    Long nhom
    String giatri
    Long loai_id
    String mucdich

//    @Override
//    public String toString() {
//        return "{\"loai_id\": ${loai_id},\"nhom\":${nhom},\"giatri\":\"${giatri}\",\"mucdich\":\"${mucdich}\"}";
//    }
}

class KhaoSatDV {
    Long vthuebao_id
    Long vcamnhankh_id
    Long vcauhoi_id
    Long vtraloi_id
    Long vtocdo_up
    Long vtocdo_down
    Long vsuyhao_up
    Long vsuyhao_down
    Long vcongsuat_wifi
    String vmota
    Long vtocdo3g_up
    Long vtocdo3g_down
    String vmota3g
    Long vtocdo4g_up
    Long vtocdo4g_down
    String vmota4g
    String vghichu

//    @Override
//    public String toString() {
//        return "{\"vthuebao_id\": ${vthuebao_id},\n" +
//                " \"vcamnhankh_id\": ${vcamnhankh_id},\n" +
//                " \"vcauhoi_id\": ${vcauhoi_id} ,\n" +
//                " \"vtraloi_id\": ${vtraloi_id},\n" +
//                " \"vtocdo_up\": ${vtocdo_up},\n" +
//                " \"vtocdo_down\": ${vtocdo_down},\n" +
//                " \"vsuyhao_up\": ${vsuyhao_up},\n" +
//                " \"vsuyhao_down\": ${vsuyhao_down},\n" +
//                " \"vcongsuat_wifi\": ${vcongsuat_wifi},\n" +
//                " \"vmota\": \"${vmota}\",\n" +
//                " \"vtocdo3g_up\": ${vtocdo3g_up},\n" +
//                " \"vtocdo3g_down\": ${vtocdo3g_down},\n" +
//                " \"vmota3g\":  \"${vmota3g}\",\n" +
//                " \"vtocdo4g_up\": ${vtocdo4g_up},\n" +
//                " \"vtocdo4g_down\": ${vtocdo4g_down},\n" +
//                " \"vmota4g\": \"${vmota4g}\",\"vghichu\": \"${vghichu}\"}"
//    }
}

class ToaDo {
    String kinhdo
    String vido
    String mota

//    @Override
//    public String toString() {
//        return "{\"kinhdo\": \"${kinhdo}\",\n" +
//                "\"vido\": \"${vido}\",\n" +
//                "\"mota\": \"${mota}\"}"
//    }
}
