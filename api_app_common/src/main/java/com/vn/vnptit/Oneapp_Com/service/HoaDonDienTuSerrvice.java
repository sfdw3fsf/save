package com.vn.vnptit.Oneapp_Com.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.vn.vnptit.Oneapp_Com.common.*;
import com.vn.vnptit.Oneapp_Com.model.AdjustInvoiceActionInput;
import com.vn.vnptit.Oneapp_Com.model.TichHopOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.vn.vnptit.Oneapp_Com.managed.LoggingManaged;
import com.vn.vnptit.Oneapp_Com.model.ResponseObject;
import com.vn.vnptit.Oneapp_Com.model.ThongtinServiceHDDTModel;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTConfirmPaymentInputModel;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTConvertForStoreFkeyInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTConvertForVerifyInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTDeliverInvFkeyInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTDownloadInvInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTDownloadInvPDFInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTGetInvViewFkeyInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTGetInvViewFkeyNoPayInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTGetInvViewNoPayInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTGetSerialByPatternInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTListInvByCusFkeyInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTListInvByCusModel;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTResponse;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HDDTcancelInvInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HoaDonDienTuAdjustInvActionInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HoaDonDienTuCustomer;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HoaDonDienTuInvNoPayModel;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HoaDonDienTuThongTin;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HoaDonDienTureReplaceInvoiceActionInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HoaDonDienTureplaceInvInput;
import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.UnConfirmPaymentFkeyInputModel;

import io.netty.handler.timeout.TimeoutException;
import org.springframework.util.StringUtils;

/**
 * @author DanhNQ
 *
 */
@Service
@Transactional
public class HoaDonDienTuSerrvice {
	// Khai báo url

	// Khai báo service publicservice
	@Value("${vnptinvoice.publicservice}")
	String						publicservice;
	// Khai báo service bizservice
	@Value("${vnptinvoice.bizservice}")
	String						bizservice;
	// Khai báo service portalservice
	@Value("${vnptinvoice.portalservice}")
	String						portalservice;
	@Value("${vnptinvoice.agg.url} ")
	String						aggurl;
	@Value("${vnptinvoice.agg.account}")
	String						aggaccount;
	@Value("${vnptinvoice.agg.acpass}")
	String						aggacpass;
	@Value("${vnptinvoice.agg.username}")
	String						aggusername;
	@Value("${vnptinvoice.agg.password}")
	String						aggpassword;
	@Value("${vnptinvoice.agg.keyitem}")
	Integer						aggkeyitem;
	@Value("${vnptinvoice.vtu.url} ")
	String						vtuurl;
	@Value("${vnptinvoice.vtu.account}")
	String						vtuaccount;
	@Value("${vnptinvoice.vtu.acpass}")
	String						vtuacpass;
	@Value("${vnptinvoice.vtu.username}")
	String						vtuusername;
	@Value("${vnptinvoice.vtu.password}")
	String						vtupassword;
	@Value("${vnptinvoice.vtu.keyitem}")
	Integer						vtukeyitem;
	@Value("${vnptinvoice.bgg.url} ")
	String						bggurl;
	@Value("${vnptinvoice.bgg.account}")
	String						bggaccount;
	@Value("${vnptinvoice.bgg.acpass}")
	String						bggacpass;
	@Value("${vnptinvoice.bgg.username}")
	String						bggusername;
	@Value("${vnptinvoice.bgg.password}")
	String						bggpassword;
	@Value("${vnptinvoice.bgg.keyitem}")
	Integer						bggkeyitem;
	@Value("${vnptinvoice.bkn.url} ")
	String						bknurl;
	@Value("${vnptinvoice.bkn.account}")
	String						bknaccount;
	@Value("${vnptinvoice.bkn.acpass}")
	String						bknacpass;
	@Value("${vnptinvoice.bkn.username}")
	String						bknusername;
	@Value("${vnptinvoice.bkn.password}")
	String						bknpassword;
	@Value("${vnptinvoice.bkn.keyitem}")
	Integer						bknkeyitem;
	@Value("${vnptinvoice.bnh.url} ")
	String						bnhurl;
	@Value("${vnptinvoice.bnh.account}")
	String						bnhaccount;
	@Value("${vnptinvoice.bnh.acpass}")
	String						bnhacpass;
	@Value("${vnptinvoice.bnh.username}")
	String						bnhusername;
	@Value("${vnptinvoice.bnh.password}")
	String						bnhpassword;
	@Value("${vnptinvoice.bnh.keyitem}")
	Integer						bnhkeyitem;
	@Value("${vnptinvoice.bdh.url} ")
	String						bdhurl;
	@Value("${vnptinvoice.bdh.account}")
	String						bdhaccount;
	@Value("${vnptinvoice.bdh.acpass}")
	String						bdhacpass;
	@Value("${vnptinvoice.bdh.username}")
	String						bdhusername;
	@Value("${vnptinvoice.bdh.password}")
	String						bdhpassword;
	@Value("${vnptinvoice.bdh.keyitem}")
	Integer						bdhkeyitem;
	@Value("${vnptinvoice.bte.url} ")
	String						bteurl;
	@Value("${vnptinvoice.bte.account}")
	String						bteaccount;
	@Value("${vnptinvoice.bte.acpass}")
	String						bteacpass;
	@Value("${vnptinvoice.bte.username}")
	String						bteusername;
	@Value("${vnptinvoice.bte.password}")
	String						btepassword;
	@Value("${vnptinvoice.bte.keyitem}")
	Integer						btekeyitem;
	@Value("${vnptinvoice.bdg.url} ")
	String						bdgurl;
	@Value("${vnptinvoice.bdg.account}")
	String						bdgaccount;
	@Value("${vnptinvoice.bdg.acpass}")
	String						bdgacpass;
	@Value("${vnptinvoice.bdg.username}")
	String						bdgusername;
	@Value("${vnptinvoice.bdg.password}")
	String						bdgpassword;
	@Value("${vnptinvoice.bdg.keyitem}")
	Integer						bdgkeyitem;
	@Value("${vnptinvoice.bpc.url} ")
	String						bpcurl;
	@Value("${vnptinvoice.bpc.account}")
	String						bpcaccount;
	@Value("${vnptinvoice.bpc.acpass}")
	String						bpcacpass;
	@Value("${vnptinvoice.bpc.username}")
	String						bpcusername;
	@Value("${vnptinvoice.bpc.password}")
	String						bpcpassword;
	@Value("${vnptinvoice.bpc.keyitem}")
	Integer						bpckeyitem;
	@Value("${vnptinvoice.btn.url} ")
	String						btnurl;
	@Value("${vnptinvoice.btn.account}")
	String						btnaccount;
	@Value("${vnptinvoice.btn.acpass}")
	String						btnacpass;
	@Value("${vnptinvoice.btn.username}")
	String						btnusername;
	@Value("${vnptinvoice.btn.password}")
	String						btnpassword;
	@Value("${vnptinvoice.btn.keyitem}")
	Integer						btnkeyitem;
	@Value("${vnptinvoice.blu.url} ")
	String						bluurl;
	@Value("${vnptinvoice.blu.account}")
	String						bluaccount;
	@Value("${vnptinvoice.blu.acpass}")
	String						bluacpass;
	@Value("${vnptinvoice.blu.username}")
	String						bluusername;
	@Value("${vnptinvoice.blu.password}")
	String						blupassword;
	@Value("${vnptinvoice.blu.keyitem}")
	Integer						blukeyitem;
	@Value("${vnptinvoice.cbg.url} ")
	String						cbgurl;
	@Value("${vnptinvoice.cbg.account}")
	String						cbgaccount;
	@Value("${vnptinvoice.cbg.acpass}")
	String						cbgacpass;
	@Value("${vnptinvoice.cbg.username}")
	String						cbgusername;
	@Value("${vnptinvoice.cbg.password}")
	String						cbgpassword;
	@Value("${vnptinvoice.cbg.keyitem}")
	Integer						cbgkeyitem;
	@Value("${vnptinvoice.cto.url} ")
	String						ctourl;
	@Value("${vnptinvoice.cto.account}")
	String						ctoaccount;
	@Value("${vnptinvoice.cto.acpass}")
	String						ctoacpass;
	@Value("${vnptinvoice.cto.username}")
	String						ctousername;
	@Value("${vnptinvoice.cto.password}")
	String						ctopassword;
	@Value("${vnptinvoice.cto.keyitem}")
	Integer						ctokeyitem;
	@Value("${vnptinvoice.cmu.url} ")
	String						cmuurl;
	@Value("${vnptinvoice.cmu.account}")
	String						cmuaccount;
	@Value("${vnptinvoice.cmu.acpass}")
	String						cmuacpass;
	@Value("${vnptinvoice.cmu.username}")
	String						cmuusername;
	@Value("${vnptinvoice.cmu.password}")
	String						cmupassword;
	@Value("${vnptinvoice.cmu.keyitem}")
	Integer						cmukeyitem;
	@Value("${vnptinvoice.dng.url} ")
	String						dngurl;
	@Value("${vnptinvoice.dng.account}")
	String						dngaccount;
	@Value("${vnptinvoice.dng.acpass}")
	String						dngacpass;
	@Value("${vnptinvoice.dng.username}")
	String						dngusername;
	@Value("${vnptinvoice.dng.password}")
	String						dngpassword;
	@Value("${vnptinvoice.dng.keyitem}")
	Integer						dngkeyitem;
	@Value("${vnptinvoice.dlc.url} ")
	String						dlcurl;
	@Value("${vnptinvoice.dlc.account}")
	String						dlcaccount;
	@Value("${vnptinvoice.dlc.acpass}")
	String						dlcacpass;
	@Value("${vnptinvoice.dlc.username}")
	String						dlcusername;
	@Value("${vnptinvoice.dlc.password}")
	String						dlcpassword;
	@Value("${vnptinvoice.dlc.keyitem}")
	Integer						dlckeyitem;
	@Value("${vnptinvoice.dni.url} ")
	String						dniurl;
	@Value("${vnptinvoice.dni.account}")
	String						dniaccount;
	@Value("${vnptinvoice.dni.acpass}")
	String						dniacpass;
	@Value("${vnptinvoice.dni.username}")
	String						dniusername;
	@Value("${vnptinvoice.dni.password}")
	String						dnipassword;
	@Value("${vnptinvoice.dni.keyitem}")
	Integer						dnikeyitem;
	@Value("${vnptinvoice.dtp.url} ")
	String						dtpurl;
	@Value("${vnptinvoice.dtp.account}")
	String						dtpaccount;
	@Value("${vnptinvoice.dtp.acpass}")
	String						dtpacpass;
	@Value("${vnptinvoice.dtp.username}")
	String						dtpusername;
	@Value("${vnptinvoice.dtp.password}")
	String						dtppassword;
	@Value("${vnptinvoice.dtp.keyitem}")
	Integer						dtpkeyitem;
	@Value("${vnptinvoice.gli.url} ")
	String						gliurl;
	@Value("${vnptinvoice.gli.account}")
	String						gliaccount;
	@Value("${vnptinvoice.gli.acpass}")
	String						gliacpass;
	@Value("${vnptinvoice.gli.username}")
	String						gliusername;
	@Value("${vnptinvoice.gli.password}")
	String						glipassword;
	@Value("${vnptinvoice.gli.keyitem}")
	Integer						glikeyitem;
	@Value("${vnptinvoice.hgg.url} ")
	String						hggurl;
	@Value("${vnptinvoice.hgg.account}")
	String						hggaccount;
	@Value("${vnptinvoice.hgg.acpass}")
	String						hggacpass;
	@Value("${vnptinvoice.hgg.username}")
	String						hggusername;
	@Value("${vnptinvoice.hgg.password}")
	String						hggpassword;
	@Value("${vnptinvoice.hgg.keyitem}")
	Integer						hggkeyitem;

//	String						hniurl = "https://testnoibo-tt78admindemo.vnpt-invoice.com.vn";
//	String						hniaccount = "ccbsadmin";
//	String						hniacpass = "fPs3LKWVu@";
//	String						hniusername = "ccbsservice";
//	String						hnipassword = "fPs3LKWVu@";
//	Integer						hnikeyitem = 1;
	@Value("${vnptinvoice.hni.url} ")
	String						hniurl;
	@Value("${vnptinvoice.hni.account}")
	String						hniaccount;
	@Value("${vnptinvoice.hni.acpass}")
	String						hniacpass;
	@Value("${vnptinvoice.hni.username}")
	String						hniusername;
	@Value("${vnptinvoice.hni.password}")
	String						hnipassword;
	@Value("${vnptinvoice.hni.keyitem}")
	Integer						hnikeyitem;


	@Value("${vnptinvoice.dbn.url} ")
	String						dbnurl;
	@Value("${vnptinvoice.dbn.account}")
	String						dbnaccount;
	@Value("${vnptinvoice.dbn.acpass}")
	String						dbnacpass;
	@Value("${vnptinvoice.dbn.username}")
	String						dbnusername;
	@Value("${vnptinvoice.dbn.password}")
	String						dbnpassword;
	@Value("${vnptinvoice.dbn.keyitem}")
	Integer						dbnkeyitem;
	@Value("${vnptinvoice.hth.url} ")
	String						hthurl;
	@Value("${vnptinvoice.hth.account}")
	String						hthaccount;
	@Value("${vnptinvoice.hth.acpass}")
	String						hthacpass;
	@Value("${vnptinvoice.hth.username}")
	String						hthusername;
	@Value("${vnptinvoice.hth.password}")
	String						hthpassword;
	@Value("${vnptinvoice.hth.keyitem}")
	Integer						hthkeyitem;
	@Value("${vnptinvoice.hyn.url} ")
	String						hynurl;
	@Value("${vnptinvoice.hyn.account}")
	String						hynaccount;
	@Value("${vnptinvoice.hyn.acpass}")
	String						hynacpass;
	@Value("${vnptinvoice.hyn.username}")
	String						hynusername;
	@Value("${vnptinvoice.hyn.password}")
	String						hynpassword;
	@Value("${vnptinvoice.hyn.keyitem}")
	Integer						hynkeyitem;
	@Value("${vnptinvoice.hnm.url} ")
	String						hnmurl;
	@Value("${vnptinvoice.hnm.account}")
	String						hnmaccount;
	@Value("${vnptinvoice.hnm.acpass}")
	String						hnmacpass;
	@Value("${vnptinvoice.hnm.username}")
	String						hnmusername;
	@Value("${vnptinvoice.hnm.password}")
	String						hnmpassword;
	@Value("${vnptinvoice.hnm.keyitem}")
	Integer						hnmkeyitem;

//	String						hpgurl = "https://testnoibo-tt78admindemo.vnpt-invoice.com.vn";
//	String						hpgaccount = "ccbsadmin";
//	String						hpgacpass = "fPs3LKWVu@";
//	String						hpgusername = "ccbsservice";
//	String						hpgpassword = "fPs3LKWVu@";
//	Integer						hpgkeyitem = 1;
	@Value("${vnptinvoice.hpg.url} ")
	String						hpgurl;
	@Value("${vnptinvoice.hpg.account}")
	String						hpgaccount;
	@Value("${vnptinvoice.hpg.acpass}")
	String						hpgacpass;
	@Value("${vnptinvoice.hpg.username}")
	String						hpgusername;
	@Value("${vnptinvoice.hpg.password}")
	String						hpgpassword;
	@Value("${vnptinvoice.hpg.keyitem}")
	Integer						hpgkeyitem;


//	String						hdgurl = "https://testnoibo-tt78admindemo.vnpt-invoice.com.vn";
//	String						hdgaccount = "ccbsadmin";
//	String						hdgacpass = "fPs3LKWVu@";
//	String						hdgusername = "ccbsservice";
//	String						hdgpassword = "fPs3LKWVu@";
//	Integer						hdgkeyitem = 1;
	@Value("${vnptinvoice.hdg.url} ")
	String						hdgurl;
	@Value("${vnptinvoice.hdg.account}")
	String						hdgaccount;
	@Value("${vnptinvoice.hdg.acpass}")
	String						hdgacpass;
	@Value("${vnptinvoice.hdg.username}")
	String						hdgusername;
	@Value("${vnptinvoice.hdg.password}")
	String						hdgpassword;
	@Value("${vnptinvoice.hdg.keyitem}")
	Integer						hdgkeyitem;

	@Value("${vnptinvoice.hcm.url} ")
	String						hcmurl;
	@Value("${vnptinvoice.hcm.account}")
	String						hcmaccount;
	@Value("${vnptinvoice.hcm.acpass}")
	String						hcmacpass;
	@Value("${vnptinvoice.hcm.username}")
	String						hcmusername;
	@Value("${vnptinvoice.hcm.password}")
	String						hcmpassword;
	@Value("${vnptinvoice.hcm.keyitem}")
	Integer						hcmkeyitem;
	@Value("${vnptinvoice.kha.url} ")
	String						khaurl;
	@Value("${vnptinvoice.kha.account}")
	String						khaaccount;
	@Value("${vnptinvoice.kha.acpass}")
	String						khaacpass;
	@Value("${vnptinvoice.kha.username}")
	String						khausername;
	@Value("${vnptinvoice.kha.password}")
	String						khapassword;
	@Value("${vnptinvoice.kha.keyitem}")
	Integer						khakeyitem;
	@Value("${vnptinvoice.kgg.url} ")
	String						kggurl;
	@Value("${vnptinvoice.kgg.account}")
	String						kggaccount;
	@Value("${vnptinvoice.kgg.acpass}")
	String						kggacpass;
	@Value("${vnptinvoice.kgg.username}")
	String						kggusername;
	@Value("${vnptinvoice.kgg.password}")
	String						kggpassword;
	@Value("${vnptinvoice.kgg.keyitem}")
	Integer						kggkeyitem;
	@Value("${vnptinvoice.ktm.url} ")
	String						ktmurl;
	@Value("${vnptinvoice.ktm.account}")
	String						ktmaccount;
	@Value("${vnptinvoice.ktm.acpass}")
	String						ktmacpass;
	@Value("${vnptinvoice.ktm.username}")
	String						ktmusername;
	@Value("${vnptinvoice.ktm.password}")
	String						ktmpassword;
	@Value("${vnptinvoice.ktm.keyitem}")
	Integer						ktmkeyitem;
	@Value("${vnptinvoice.lcu.url} ")
	String						lcuurl;
	@Value("${vnptinvoice.lcu.account}")
	String						lcuaccount;
	@Value("${vnptinvoice.lcu.acpass}")
	String						lcuacpass;
	@Value("${vnptinvoice.lcu.username}")
	String						lcuusername;
	@Value("${vnptinvoice.lcu.password}")
	String						lcupassword;
	@Value("${vnptinvoice.lcu.keyitem}")
	Integer						lcukeyitem;
	@Value("${vnptinvoice.lsn.url} ")
	String						lsnurl;
	@Value("${vnptinvoice.lsn.account}")
	String						lsnaccount;
	@Value("${vnptinvoice.lsn.acpass}")
	String						lsnacpass;
	@Value("${vnptinvoice.lsn.username}")
	String						lsnusername;
	@Value("${vnptinvoice.lsn.password}")
	String						lsnpassword;
	@Value("${vnptinvoice.lsn.keyitem}")
	Integer						lsnkeyitem;
	@Value("${vnptinvoice.lci.url} ")
	String						lciurl;
	@Value("${vnptinvoice.lci.account}")
	String						lciaccount;
	@Value("${vnptinvoice.lci.acpass}")
	String						lciacpass;
	@Value("${vnptinvoice.lci.username}")
	String						lciusername;
	@Value("${vnptinvoice.lci.password}")
	String						lcipassword;
	@Value("${vnptinvoice.lci.keyitem}")
	Integer						lcikeyitem;
	@Value("${vnptinvoice.ldg.url} ")
	String						ldgurl;
	@Value("${vnptinvoice.ldg.account}")
	String						ldgaccount;
	@Value("${vnptinvoice.ldg.acpass}")
	String						ldgacpass;
	@Value("${vnptinvoice.ldg.username}")
	String						ldgusername;
	@Value("${vnptinvoice.ldg.password}")
	String						ldgpassword;
	@Value("${vnptinvoice.ldg.keyitem}")
	Integer						ldgkeyitem;
	@Value("${vnptinvoice.lan.url} ")
	String						lanurl;
	@Value("${vnptinvoice.lan.account}")
	String						lanaccount;
	@Value("${vnptinvoice.lan.acpass}")
	String						lanacpass;
	@Value("${vnptinvoice.lan.username}")
	String						lanusername;
	@Value("${vnptinvoice.lan.password}")
	String						lanpassword;
	@Value("${vnptinvoice.lan.keyitem}")
	Integer						lankeyitem;
	@Value("${vnptinvoice.ndh.url} ")
	String						ndhurl;
	@Value("${vnptinvoice.ndh.account}")
	String						ndhaccount;
	@Value("${vnptinvoice.ndh.acpass}")
	String						ndhacpass;
	@Value("${vnptinvoice.ndh.username}")
	String						ndhusername;
	@Value("${vnptinvoice.ndh.password}")
	String						ndhpassword;
	@Value("${vnptinvoice.ndh.keyitem}")
	Integer						ndhkeyitem;

//	String						nanurl = "https://testnoibo-tt78admindemo.vnpt-invoice.com.vn";
//	String						nanaccount = "ccbsadmin";
//	String						nanacpass = "fPs3LKWVu@";
//	String						nanusername = "ccbsservice";
//	String						nanpassword = "fPs3LKWVu@";
//	Integer						nankeyitem = 1;
	@Value("${vnptinvoice.nan.url} ")
	String						nanurl;
	@Value("${vnptinvoice.nan.account}")
	String						nanaccount;
	@Value("${vnptinvoice.nan.acpass}")
	String						nanacpass;
	@Value("${vnptinvoice.nan.username}")
	String						nanusername;
	@Value("${vnptinvoice.nan.password}")
	String						nanpassword;
	@Value("${vnptinvoice.nan.keyitem}")
	Integer						nankeyitem;

	@Value("${vnptinvoice.nbh.url} ")
	String						nbhurl;
	@Value("${vnptinvoice.nbh.account}")
	String						nbhaccount;
	@Value("${vnptinvoice.nbh.acpass}")
	String						nbhacpass;
	@Value("${vnptinvoice.nbh.username}")
	String						nbhusername;
	@Value("${vnptinvoice.nbh.password}")
	String						nbhpassword;
	@Value("${vnptinvoice.nbh.keyitem}")
	Integer						nbhkeyitem;
	@Value("${vnptinvoice.pyn.url} ")
	String						pynurl;
	@Value("${vnptinvoice.pyn.account}")
	String						pynaccount;
	@Value("${vnptinvoice.pyn.acpass}")
	String						pynacpass;
	@Value("${vnptinvoice.pyn.username}")
	String						pynusername;
	@Value("${vnptinvoice.pyn.password}")
	String						pynpassword;
	@Value("${vnptinvoice.pyn.keyitem}")
	Integer						pynkeyitem;
	@Value("${vnptinvoice.qnm.url} ")
	String						qnmurl;
	@Value("${vnptinvoice.qnm.account}")
	String						qnmaccount;
	@Value("${vnptinvoice.qnm.acpass}")
	String						qnmacpass;
	@Value("${vnptinvoice.qnm.username}")
	String						qnmusername;
	@Value("${vnptinvoice.qnm.password}")
	String						qnmpassword;
	@Value("${vnptinvoice.qnm.keyitem}")
	Integer						qnmkeyitem;
	@Value("${vnptinvoice.qnh.url} ")
	String						qnhurl;
	@Value("${vnptinvoice.qnh.account}")
	String						qnhaccount;
	@Value("${vnptinvoice.qnh.acpass}")
	String						qnhacpass;
	@Value("${vnptinvoice.qnh.username}")
	String						qnhusername;
	@Value("${vnptinvoice.qnh.password}")
	String						qnhpassword;
	@Value("${vnptinvoice.qnh.keyitem}")
	Integer						qnhkeyitem;
	@Value("${vnptinvoice.sla.url} ")
	String						slaurl;
	@Value("${vnptinvoice.sla.account}")
	String						slaaccount;
	@Value("${vnptinvoice.sla.acpass}")
	String						slaacpass;
	@Value("${vnptinvoice.sla.username}")
	String						slausername;
	@Value("${vnptinvoice.sla.password}")
	String						slapassword;
	@Value("${vnptinvoice.sla.keyitem}")
	Integer						slakeyitem;
	@Value("${vnptinvoice.tbh.url} ")
	String						tbhurl;
	@Value("${vnptinvoice.tbh.account}")
	String						tbhaccount;
	@Value("${vnptinvoice.tbh.acpass}")
	String						tbhacpass;
	@Value("${vnptinvoice.tbh.username}")
	String						tbhusername;
	@Value("${vnptinvoice.tbh.password}")
	String						tbhpassword;
	@Value("${vnptinvoice.tbh.keyitem}")
	Integer						tbhkeyitem;
	@Value("${vnptinvoice.hue.url} ")
	String						hueurl;
	@Value("${vnptinvoice.hue.account}")
	String						hueaccount;
	@Value("${vnptinvoice.hue.acpass}")
	String						hueacpass;
	@Value("${vnptinvoice.hue.username}")
	String						hueusername;
	@Value("${vnptinvoice.hue.password}")
	String						huepassword;
	@Value("${vnptinvoice.hue.keyitem}")
	Integer						huekeyitem;
	@Value("${vnptinvoice.tvh.url} ")
	String						tvhurl;
	@Value("${vnptinvoice.tvh.account}")
	String						tvhaccount;
	@Value("${vnptinvoice.tvh.acpass}")
	String						tvhacpass;
	@Value("${vnptinvoice.tvh.username}")
	String						tvhusername;
	@Value("${vnptinvoice.tvh.password}")
	String						tvhpassword;
	@Value("${vnptinvoice.tvh.keyitem}")
	Integer						tvhkeyitem;
	@Value("${vnptinvoice.vpc.url} ")
	String						vpcurl;
	@Value("${vnptinvoice.vpc.account}")
	String						vpcaccount;
	@Value("${vnptinvoice.vpc.acpass}")
	String						vpcacpass;
	@Value("${vnptinvoice.vpc.username}")
	String						vpcusername;
	@Value("${vnptinvoice.vpc.password}")
	String						vpcpassword;
	@Value("${vnptinvoice.vpc.keyitem}")
	Integer						vpckeyitem;
	@Value("${vnptinvoice.pto.url} ")
	String						ptourl;
	@Value("${vnptinvoice.pto.account}")
	String						ptoaccount;
	@Value("${vnptinvoice.pto.acpass}")
	String						ptoacpass;
	@Value("${vnptinvoice.pto.username}")
	String						ptousername;
	@Value("${vnptinvoice.pto.password}")
	String						ptopassword;
	@Value("${vnptinvoice.pto.keyitem}")
	Integer						ptokeyitem;
	@Value("${vnptinvoice.dno.url} ")
	String						dnourl;
	@Value("${vnptinvoice.dno.account}")
	String						dnoaccount;
	@Value("${vnptinvoice.dno.acpass}")
	String						dnoacpass;
	@Value("${vnptinvoice.dno.username}")
	String						dnousername;
	@Value("${vnptinvoice.dno.password}")
	String						dnopassword;
	@Value("${vnptinvoice.dno.keyitem}")
	Integer						dnokeyitem;
	@Value("${vnptinvoice.hbh.url} ")
	String						hbhurl;
	@Value("${vnptinvoice.hbh.account}")
	String						hbhaccount;
	@Value("${vnptinvoice.hbh.acpass}")
	String						hbhacpass;
	@Value("${vnptinvoice.hbh.username}")
	String						hbhusername;
	@Value("${vnptinvoice.hbh.password}")
	String						hbhpassword;
	@Value("${vnptinvoice.hbh.keyitem}")
	Integer						hbhkeyitem;
	@Value("${vnptinvoice.ntn.url} ")
	String						ntnurl;
	@Value("${vnptinvoice.ntn.account}")
	String						ntnaccount;
	@Value("${vnptinvoice.ntn.acpass}")
	String						ntnacpass;
	@Value("${vnptinvoice.ntn.username}")
	String						ntnusername;
	@Value("${vnptinvoice.ntn.password}")
	String						ntnpassword;
	@Value("${vnptinvoice.ntn.keyitem}")
	Integer						ntnkeyitem;
	@Value("${vnptinvoice.qbh.url} ")
	String						qbhurl;
	@Value("${vnptinvoice.qbh.account}")
	String						qbhaccount;
	@Value("${vnptinvoice.qbh.acpass}")
	String						qbhacpass;
	@Value("${vnptinvoice.qbh.username}")
	String						qbhusername;
	@Value("${vnptinvoice.qbh.password}")
	String						qbhpassword;
	@Value("${vnptinvoice.qbh.keyitem}")
	Integer						qbhkeyitem;
	@Value("${vnptinvoice.qni.url} ")
	String						qniurl;
	@Value("${vnptinvoice.qni.account}")
	String						qniaccount;
	@Value("${vnptinvoice.qni.acpass}")
	String						qniacpass;
	@Value("${vnptinvoice.qni.username}")
	String						qniusername;
	@Value("${vnptinvoice.qni.password}")
	String						qnipassword;
	@Value("${vnptinvoice.qni.keyitem}")
	Integer						qnikeyitem;
	@Value("${vnptinvoice.qti.url} ")
	String						qtiurl;
	@Value("${vnptinvoice.qti.account}")
	String						qtiaccount;
	@Value("${vnptinvoice.qti.acpass}")
	String						qtiacpass;
	@Value("${vnptinvoice.qti.username}")
	String						qtiusername;
	@Value("${vnptinvoice.qti.password}")
	String						qtipassword;
	@Value("${vnptinvoice.qti.keyitem}")
	Integer						qtikeyitem;
	@Value("${vnptinvoice.stg.url} ")
	String						stgurl;
	@Value("${vnptinvoice.stg.account}")
	String						stgaccount;
	@Value("${vnptinvoice.stg.acpass}")
	String						stgacpass;
	@Value("${vnptinvoice.stg.username}")
	String						stgusername;
	@Value("${vnptinvoice.stg.password}")
	String						stgpassword;
	@Value("${vnptinvoice.stg.keyitem}")
	Integer						stgkeyitem;
	@Value("${vnptinvoice.tnh.url} ")
	String						tnhurl;
	@Value("${vnptinvoice.tnh.account}")
	String						tnhaccount;
	@Value("${vnptinvoice.tnh.acpass}")
	String						tnhacpass;
	@Value("${vnptinvoice.tnh.username}")
	String						tnhusername;
	@Value("${vnptinvoice.tnh.password}")
	String						tnhpassword;
	@Value("${vnptinvoice.tnh.keyitem}")
	Integer						tnhkeyitem;
	@Value("${vnptinvoice.tha.url} ")
	String						thaurl;
	@Value("${vnptinvoice.tha.account}")
	String						thaaccount;
	@Value("${vnptinvoice.tha.acpass}")
	String						thaacpass;
	@Value("${vnptinvoice.tha.username}")
	String						thausername;
	@Value("${vnptinvoice.tha.password}")
	String						thapassword;
	@Value("${vnptinvoice.tha.keyitem}")
	Integer						thakeyitem;
	@Value("${vnptinvoice.tgg.url} ")
	String						tggurl;
	@Value("${vnptinvoice.tgg.account}")
	String						tggaccount;
	@Value("${vnptinvoice.tgg.acpass}")
	String						tggacpass;
	@Value("${vnptinvoice.tgg.username}")
	String						tggusername;
	@Value("${vnptinvoice.tgg.password}")
	String						tggpassword;
	@Value("${vnptinvoice.tgg.keyitem}")
	Integer						tggkeyitem;
	@Value("${vnptinvoice.tqg.url} ")
	String						tqgurl;
	@Value("${vnptinvoice.tqg.account}")
	String						tqgaccount;
	@Value("${vnptinvoice.tqg.acpass}")
	String						tqgacpass;
	@Value("${vnptinvoice.tqg.username}")
	String						tqgusername;
	@Value("${vnptinvoice.tqg.password}")
	String						tqgpassword;
	@Value("${vnptinvoice.tqg.keyitem}")
	Integer						tqgkeyitem;
	@Value("${vnptinvoice.vlg.url} ")
	String						vlgurl;
	@Value("${vnptinvoice.vlg.account}")
	String						vlgaccount;
	@Value("${vnptinvoice.vlg.acpass}")
	String						vlgacpass;
	@Value("${vnptinvoice.vlg.username}")
	String						vlgusername;
	@Value("${vnptinvoice.vlg.password}")
	String						vlgpassword;
	@Value("${vnptinvoice.vlg.keyitem}")
	Integer						vlgkeyitem;
	@Value("${vnptinvoice.ybi.url} ")
	String						ybiurl;
	@Value("${vnptinvoice.ybi.account}")
	String						ybiaccount;
	@Value("${vnptinvoice.ybi.acpass}")
	String						ybiacpass;
	@Value("${vnptinvoice.ybi.username}")
	String						ybiusername;
	@Value("${vnptinvoice.ybi.password}")
	String						ybipassword;
	@Value("${vnptinvoice.ybi.keyitem}")
	Integer						ybikeyitem;
	@Value("${vnptinvoice.tnn.url} ")
	String						tnnurl;
	@Value("${vnptinvoice.tnn.account}")
	String						tnnaccount;
	@Value("${vnptinvoice.tnn.acpass}")
	String						tnnacpass;
	@Value("${vnptinvoice.tnn.username}")
	String						tnnusername;
	@Value("${vnptinvoice.tnn.password}")
	String						tnnpassword;
	@Value("${vnptinvoice.tnn.keyitem}")
	Integer						tnnkeyitem;
	@Value("${vnptinvoice.hgi.url} ")
	String						hgiurl;
	@Value("${vnptinvoice.hgi.account}")
	String						hgiaccount;
	@Value("${vnptinvoice.hgi.acpass}")
	String						hgiacpass;
	@Value("${vnptinvoice.hgi.username}")
	String						hgiusername;
	@Value("${vnptinvoice.hgi.password}")
	String						hgipassword;
	@Value("${vnptinvoice.hgi.keyitem}")
	Integer						hgikeyitem;

	@Autowired
	LoggingManaged				loggingManaged;
	private final JsonMapper	mapper	= new JsonMapper();

	private Logger				logger	= Logger.getLogger(getClass().getName());

	public ResponseEntity<Object> UpdateCus(HoaDonDienTuCustomer hoaDonDienTuCustomer) {
		UUID uuid = UUID.randomUUID();
		HDDTResponse modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService((Integer) hoaDonDienTuCustomer.getPhanvung_id());
		modelResponse = SoapHttps.hoaDonUpdateCus(hoaDonDienTuCustomer, publicServiceurl);

		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "hoaDonUpdateCus");
		log.put(ConstantString.SERVICE, ConstantString.HOADON_DIENTU);
		log.put(ConstantString.XMLDATA, modelResponse.getRequest_id());
		log.put(ConstantString.URL, modelResponse.getUrl());
		log.put(ConstantString.OUTPUT, modelResponse.getOutput());

		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hoaDonDienTuCustomer));
			log.put(ConstantString.LOG_RESPONE, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(modelResponse));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}
		ResponseObject modelResponseNew = new ResponseObject(modelResponse);
		loggingManaged.info(log.toString());


		modelResponse.setRequest_id(uuid.toString());
		return ResponseEntity.status(Integer.parseInt(modelResponseNew.getError())).body(modelResponseNew);

	}

	public ResponseEntity<Object> importAndPublishInv(HoaDonDienTuThongTin hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		HDDTResponse modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService(hoaDonDienTuThongTin.getPhanvung_id());
		modelResponse = SoapHttps.importAndPublishInv(hoaDonDienTuThongTin, publicServiceurl);

		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "importAndPublishInv");
		log.put(ConstantString.SERVICE, ConstantString.HOADON_DIENTU);
		log.put(ConstantString.XMLDATA, modelResponse.getRequest_id());
		log.put(ConstantString.URL, modelResponse.getUrl());
		log.put(ConstantString.OUTPUT, modelResponse.getOutput());
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hoaDonDienTuThongTin));
			log.put(ConstantString.LOG_RESPONE, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(modelResponse));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}
		loggingManaged.info(log.toString());

		modelResponse.setRequest_id(uuid.toString());
		ResponseObject modelResponseNew = new ResponseObject(modelResponse);
		return ResponseEntity.status(Integer.parseInt(modelResponseNew.getError())).body(modelResponseNew);

	}

	public ResponseEntity<Object> confirmPaymentFkey(HoaDonDienTuThongTin hoaDonDienTuThongTin) {
		UUID uuid = UUID.randomUUID();
		HDDTResponse modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService(hoaDonDienTuThongTin.getPhanvung_id());
		modelResponse = SoapHttps.confirmPaymentFkey(hoaDonDienTuThongTin, publicServiceurl);

		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "confirmPaymentFkey");
		log.put(ConstantString.SERVICE, ConstantString.HOADON_DIENTU);
		log.put(ConstantString.XMLDATA, modelResponse.getRequest_id());
		log.put(ConstantString.URL, modelResponse.getUrl());
		log.put(ConstantString.OUTPUT, modelResponse.getOutput());
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hoaDonDienTuThongTin));
			log.put(ConstantString.LOG_RESPONE, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(modelResponse));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}

		loggingManaged.info(log.toString());
		modelResponse.setRequest_id(uuid.toString());
		ResponseObject modelResponseNew = new ResponseObject(modelResponse);

		return ResponseEntity.status(Integer.parseInt(modelResponseNew.getError())).body(modelResponseNew);

	}

	public ResponseEntity<Object> downloadInvNoPay(HoaDonDienTuInvNoPayModel invNoPayModel) throws JsonProcessingException {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService(invNoPayModel.getPhanvung_id());
		modelResponse = SoapHttps.downloadInvNoPay(invNoPayModel, publicServiceurl);
		modelResponse.setRequest_id(uuid.toString());

		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "downloadInvNoPay");
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(invNoPayModel));
			log.put(ConstantString.LOG_RESPONE, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(modelResponse));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}
		loggingManaged.info(log.toString());


		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);

	}

	public ResponseEntity<Object> downloadInvNoPay1(HoaDonDienTuInvNoPayModel invNoPayModel) throws JsonProcessingException {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService(invNoPayModel.getPhanvung_id());
		modelResponse = SoapHttps.downloadInvNoPay1(invNoPayModel, publicServiceurl);
		modelResponse.setRequest_id(uuid.toString());

		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "downloadInvNoPay1");
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(invNoPayModel));
			log.put(ConstantString.LOG_RESPONE, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(modelResponse));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}
		loggingManaged.info(log.toString());


		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);

	}

	public ResponseEntity<Object> adjustInvAction(HoaDonDienTuAdjustInvActionInput adjustInvInput) {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService(adjustInvInput.getPhanvung_id());
		modelResponse = SoapHttps.adjustInvAction(adjustInvInput, publicServiceurl);
		modelResponse.setRequest_id(uuid.toString());
		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "adjustInvAction");
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(adjustInvInput));
			log.put(ConstantString.LOG_RESPONE, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(modelResponse));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}
		loggingManaged.info(log.toString());

		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);

	}

	public ResponseEntity<Object> replaceInv(HoaDonDienTureplaceInvInput adjustInvInput) {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService(adjustInvInput.getPhanvung_id());
		modelResponse = SoapHttps.replaceInv(adjustInvInput, publicServiceurl);
		modelResponse.setRequest_id(uuid.toString());
		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "replaceInv");
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(adjustInvInput));
			log.put(ConstantString.LOG_RESPONE, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(modelResponse));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}

		loggingManaged.info(log.toString());
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);

	}

	public ResponseEntity<Object> replaceInvoiceActionMoi(HoaDonDienTureReplaceInvoiceActionInput adjustInvInput) {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService(adjustInvInput.getPhanvung_id());
		modelResponse = SoapHttps.replaceInvoiceAction(adjustInvInput, publicServiceurl);
		modelResponse.setRequest_id(uuid.toString());
		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "replaceInvoiceAction");
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(adjustInvInput));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}
		log.put(ConstantString.LOG_RESPONE, modelResponse.getMessage());
		loggingManaged.info(log.toString());
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);

	}

	public ResponseEntity<Object> replaceInvoiceAction(HoaDonDienTureReplaceInvoiceActionInput adjustInvInput) {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService(adjustInvInput.getPhanvung_id());
		modelResponse = SoapHttps.replaceInvoiceAction(adjustInvInput, publicServiceurl);
		modelResponse.setRequest_id(uuid.toString());
		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "replaceInvoiceAction");
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(adjustInvInput));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}
		log.put(ConstantString.LOG_RESPONE, modelResponse.getMessage());
		loggingManaged.info(log.toString());
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);

	}

	public ResponseEntity<Object> listInvByCus(HDDTListInvByCusModel input) throws JsonProcessingException {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService(input.getPhanvung_id());
		modelResponse = SoapHttps.listInvByCus(input, publicServiceurl);
		modelResponse.setRequest_id(uuid.toString());
		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "listInvByCus");
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}
		log.put(ConstantString.LOG_RESPONE, modelResponse.getMessage());
		loggingManaged.info(log.toString());
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);

	}

	public ResponseEntity<Object> getInvViewFkey(HDDTGetInvViewFkeyInput input) {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService(input.getPhanvung_id());
		modelResponse = SoapHttps.getInvViewFkey(input, publicServiceurl);
		modelResponse.setRequest_id(uuid.toString());
		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "getInvViewFkey");
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}
		log.put(ConstantString.LOG_RESPONE, modelResponse.getMessage());
		loggingManaged.info(log.toString());
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);

	}

	public ResponseEntity<Object> confirmPayment(HDDTConfirmPaymentInputModel input) {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService(input.getPhanvung_id());
		modelResponse = SoapHttps.confirmPayment(input, publicServiceurl);
		modelResponse.setRequest_id(uuid.toString());
		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "confirmPayment");
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}
		log.put(ConstantString.LOG_RESPONE, modelResponse.getMessage());
		loggingManaged.info(log.toString());
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);

	}

	public ResponseEntity<Object> unConfirmPayment(HDDTConfirmPaymentInputModel input) {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService(input.getPhanvung_id());
		modelResponse = SoapHttps.unConfirmPayment(input, publicServiceurl, uuid.toString());
		modelResponse.setRequest_id(uuid.toString());
		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "unConfirmPayment");
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}
		log.put(ConstantString.LOG_RESPONE, modelResponse.getMessage());
		loggingManaged.info(log.toString());
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);

	}

	public ResponseEntity<Object> unConfirmPaymentFkey(UnConfirmPaymentFkeyInputModel input) {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService(input.getPhanvung_id());
		modelResponse = SoapHttps.unConfirmPaymentFkey(input, publicServiceurl, uuid.toString());
		modelResponse.setRequest_id(uuid.toString());
		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "unConfirmPaymentFkey");
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}
		log.put(ConstantString.LOG_RESPONE, modelResponse.getMessage());
		loggingManaged.info(log.toString());
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);

	}

	public ResponseEntity<Object> confirmPaymentDetailFkey(UnConfirmPaymentFkeyInputModel input) {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService(input.getPhanvung_id());
		modelResponse = SoapHttps.confirmPaymentDetailFkey(input, publicServiceurl, uuid.toString());
		modelResponse.setRequest_id(uuid.toString());
		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "confirmPaymentDetailFkey");
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}
		log.put(ConstantString.LOG_RESPONE, modelResponse.getMessage());
		loggingManaged.info(log.toString());
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);

	}

	public ResponseEntity<Object> cancelInv(HDDTcancelInvInput input) {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse = null;
		// ThongtinServiceHDDTModel publicServiceurl =
		// publicService(input.getPhanvung_id());
		// modelResponse = SoapHttps.cancelInv(input, publicServiceurl,
		// uuid.toString());
		modelResponse.setRequest_id(uuid.toString());
		modelResponse.setError("400");
		modelResponse.setError_code(ErrorCode.BSS_00004008);
		modelResponse.setMessage("Chức năng không còn được sử dụng");
		// Map<String, String> log = new HashMap<>();
		// log.put(ConstantString.REQUEST_ID, uuid.toString());
		// log.put(ConstantString.ACTION, "cancelInv");
		// try {
		// log.put(ConstantString.THONGTIN_CONNECT,
		// mapper.writerWithDefaultPrettyPrinter()
		// .writeValueAsString(publicServiceurl));
		// log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter()
		// .writeValueAsString(input));
		// } catch (JsonProcessingException e1) {
		//
		// System.out.println(e1.getMessage());
		// }
		// log.put(ConstantString.LOG_RESPONE, modelResponse.getMessage());
		// loggingManaged.info(log.toString());
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);

	}

	public ResponseEntity<Object> getSerialByPattern(HDDTGetSerialByPatternInput input) {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService(input.getPhanvung_id());
		modelResponse = SoapHttps.getSerialByPattern(input, publicServiceurl, uuid.toString());
		modelResponse.setRequest_id(uuid.toString());
		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "getSerialByPattern");
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}
		log.put(ConstantString.LOG_RESPONE, modelResponse.getMessage());
		loggingManaged.info(log.toString());
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);

	}

	public ResponseEntity<Object> deliverInvFkey(HDDTDeliverInvFkeyInput input) {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService(input.getPhanvung_id());
		modelResponse = SoapHttps.deliverInvFkey(input, publicServiceurl, uuid.toString());
		modelResponse.setRequest_id(uuid.toString());
		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "deliverInvFkey");
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}
		log.put(ConstantString.LOG_RESPONE, modelResponse.getMessage());
		loggingManaged.info(log.toString());
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);

	}

	public ResponseEntity<Object> listInvByCusFkey(HDDTListInvByCusFkeyInput input) {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService(input.getPhanvung_id());
		modelResponse = SoapHttps.listInvByCusFkey(input, publicServiceurl, uuid.toString());
		modelResponse.setRequest_id(uuid.toString());
		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "listInvByCusFkey");
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}
		log.put(ConstantString.LOG_RESPONE, modelResponse.getMessage());
		loggingManaged.info(log.toString());
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);

	}

	public ResponseEntity<Object> getInvViewNoPay(HDDTGetInvViewNoPayInput input) {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse = new ResponseObject();
		modelResponse.setRequest_id(uuid.toString());
		ThongtinServiceHDDTModel publicServiceurl = publicService(input.getPhanvung_id());

		try {
			modelResponse = SoapHttps.getInvViewNoPay(input, publicServiceurl, uuid.toString());
		} catch (TimeoutException e) {
			modelResponse.setMessage(e.getMessage());
			modelResponse.setError_code(ErrorCode.BSS_00004010);
			modelResponse.setError("400");
		}

		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "getInvViewNoPay");
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}
		log.put(ConstantString.LOG_RESPONE, modelResponse.getMessage());
		loggingManaged.info(log.toString());
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);

	}

	public ResponseEntity<Object> getInvViewFkeyNoPay(HDDTGetInvViewFkeyNoPayInput input) {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService(input.getPhanvung_id());
		modelResponse = SoapHttps.getInvViewFkeyNoPay(input, publicServiceurl, uuid.toString());
		modelResponse.setRequest_id(uuid.toString());
		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "getInvViewFkeyNoPay");
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}
		log.put(ConstantString.LOG_RESPONE, modelResponse.getMessage());
		loggingManaged.info(log.toString());
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);

	}

	public ResponseEntity<Object> downloadInv(HDDTDownloadInvInput input) {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService(input.getPhanvung_id());
		modelResponse = SoapHttps.downloadInv(input, publicServiceurl, uuid.toString());
		modelResponse.setRequest_id(uuid.toString());
		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "downloadInv");
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}
		log.put(ConstantString.LOG_RESPONE, modelResponse.getMessage());
		loggingManaged.info(log.toString());
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);
	}

	public ResponseEntity<Object> downloadInvPDF(HDDTDownloadInvPDFInput input) {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService(input.getPhanvung_id());
		modelResponse = SoapHttps.downloadInvPDF(input, publicServiceurl, uuid.toString());
		modelResponse.setRequest_id(uuid.toString());
		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "downloadInvPDF");
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}
		log.put(ConstantString.LOG_RESPONE, modelResponse.getMessage());
		loggingManaged.info(log.toString());
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);
	}

	public ResponseEntity<Object> convertForVerify(HDDTConvertForVerifyInput input) {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService(input.getPhanvung_id());
		modelResponse = SoapHttps.convertForVerify(input, publicServiceurl, uuid.toString());
		modelResponse.setRequest_id(uuid.toString());
		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "convertForVerify");
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}
		log.put(ConstantString.LOG_RESPONE, modelResponse.getMessage());
		loggingManaged.info(log.toString());
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);
	}

	public ResponseEntity<Object> convertForStore(HDDTConvertForVerifyInput input) {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService(input.getPhanvung_id());
		modelResponse = SoapHttps.convertForStore(input, publicServiceurl, uuid.toString());
		modelResponse.setRequest_id(uuid.toString());
		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "convertForStore");
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}
		log.put(ConstantString.LOG_RESPONE, modelResponse.getMessage());
		loggingManaged.info(log.toString());
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);
	}

	public ResponseEntity<Object> convertForStoreFkey(HDDTConvertForStoreFkeyInput input) {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService(input.getPhanvung_id());
		modelResponse = SoapHttps.convertForStoreFkey(input, publicServiceurl, uuid.toString());
		modelResponse.setRequest_id(uuid.toString());
		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "convertForStoreFkey");
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}
		log.put(ConstantString.LOG_RESPONE, modelResponse.getMessage());
		loggingManaged.info(log.toString());
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);
	}

	public ResponseEntity<Object> downloadInvFkeyNoPay(HDDTConvertForStoreFkeyInput input) throws JsonProcessingException {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService(input.getPhanvung_id());
		if (input.getPhanvung_id() == 28)
			modelResponse = SoapHttps.downloadInvFkeyNoPayXmlHCM(input, publicServiceurl, uuid.toString(), 0);
		else
			modelResponse = SoapHttps.downloadInvFkeyNoPay(input, publicServiceurl, uuid.toString());
		modelResponse.setRequest_id(uuid.toString());
		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "downloadInvFkeyNoPay");
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}
		log.put(ConstantString.LOG_RESPONE, modelResponse.getMessage());
		loggingManaged.info(log.toString());
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);
	}

	public ResponseEntity<Object> downloadInvFkeyNoPayXml(HDDTConvertForStoreFkeyInput input) {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService(input.getPhanvung_id());

		modelResponse = SoapHttps.downloadInvFkeyNoPayXml(input, publicServiceurl, uuid.toString());

		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "downloadInvFkeyNoPayXml");
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}
		log.put(ConstantString.LOG_RESPONE, modelResponse.getMessage());
		loggingManaged.info(log.toString());
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);
	}

	public ResponseEntity<Object> downloadInvPDFFkeyNoPay(HDDTConvertForStoreFkeyInput input) {
		UUID uuid = UUID.randomUUID();
		ResponseObject modelResponse;
		ThongtinServiceHDDTModel publicServiceurl = publicService(input.getPhanvung_id());
		modelResponse = SoapHttps.downloadInvPDFFkeyNoPay(input, publicServiceurl, uuid.toString());
		modelResponse.setRequest_id(uuid.toString());
		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "downloadInvPDFFkeyNoPay");
		try {
			log.put(ConstantString.THONGTIN_CONNECT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(publicServiceurl));
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
		} catch (JsonProcessingException e1) {

			System.out.println(e1.getMessage());
		}
		log.put(ConstantString.LOG_RESPONE, modelResponse.getMessage());
		loggingManaged.info(log.toString());
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);
	}

	public ResponseEntity<Object> downloadInvPDFFkeyNoPayV2(HDDTConvertForStoreFkeyInput input, String token, String uuid) {

		String idenficode = JwtService.getIdentifyFromToken(token.split(" ")[1]);
		String username = JwtService.getUsernameFromToken(token.split(" ")[1]);
		ResponseObject modelResponse = Utils.badRequest(uuid);

		String url = "http://tichhop/vnptinvoice/downloadInvPDFFkeyNoPay";
//		String url = "https://api-dev-onebss.vnpt.vn/tichhop/vnptinvoice/downloadInvPDFFkeyNoPay";
		Map<String, String> header = new HashMap<>();
		header.put("Authorization", token);
		Map<String, String> body = new HashMap<>();
		body.put("fkey", input.getFkey());

		ResponseObject response = CallHttp.httpPostBody(header, body, url);

		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid);
		log.put(ConstantString.ACTION, "tichhop/vnptinvoice/downloadInvPDFFkeyNoPay");
		log.put(ConstantString.IDENTIFYCODE, idenficode);
		log.put(ConstantString.USERNAME, username);
		try {
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body));
			log.put(ConstantString.LOG_RESPONE, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
		} catch (JsonProcessingException e) {
			logger.info(e.getMessage());
		}
		loggingManaged.info(log.toString());

		if (StringUtils.isEmpty(response.getError())) {
			Object res = response.getData();
			ObjectMapper oMapper = new ObjectMapper();
			oMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			TichHopOutput ttHD = oMapper.convertValue(res, TichHopOutput.class);

			modelResponse.setMessage(ttHD.getFaultString());
			if (ttHD.getErrorCode() == 0) {
				Map<String, String> data = new HashMap<>();
				data.put("response", ttHD.getData());

				modelResponse.setData(data);
				modelResponse.setError("200");
				modelResponse.setError_code(ErrorCode.BSS_00000000);
				return ResponseEntity.ok().body(modelResponse);
			}
		}
		else {
			modelResponse.setMessage(response.getMessage());
		}

		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);
	}

	public ResponseEntity<Object> downloadInvNoPayV2(HoaDonDienTuInvNoPayModel input, String token, String uuid) throws IOException {

		String idenficode = JwtService.getIdentifyFromToken(token.split(" ")[1]);
		String username = JwtService.getUsernameFromToken(token.split(" ")[1]);
		ResponseObject modelResponse = Utils.badRequest(uuid);

		String url = "http://tichhop/vnptinvoice/downloadInvNoPay";
//		String url = "https://api-dev-onebss.vnpt.vn/tichhop/vnptinvoice/downloadInvNoPay";
		Map<String, String> header = new HashMap<>();
		header.put("Authorization", token);
		Map<String, String> body = new HashMap<>();
		body.put("invToken", input.getInvToken());

		ResponseObject response = CallHttp.httpPostBody(header, body, url);

		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid);
		log.put(ConstantString.ACTION, "tichhop/vnptinvoice/downloadInvNoPay");
		log.put(ConstantString.IDENTIFYCODE, idenficode);
		log.put(ConstantString.USERNAME, username);
		try {
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body));
			log.put(ConstantString.LOG_RESPONE, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
		} catch (JsonProcessingException e) {
			logger.info(e.getMessage());
		}
		loggingManaged.info(log.toString());

		if (StringUtils.isEmpty(response.getError())) {
			Object res = response.getData();
			ObjectMapper oMapper = new ObjectMapper();
			oMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			TichHopOutput ttHD = oMapper.convertValue(res, TichHopOutput.class);

			modelResponse.setMessage(ttHD.getFaultString());
			if (ttHD.getErrorCode() == 0) {
				String raw = ttHD.getData().replace("<TTKhac />", "");
				//.replace("\\\"", "\"").replace("\\\\", "\\");

				// 3) Bỏ processing-instruction (xml-stylesheet) cho chắc
				String xml = raw.replaceFirst("<\\?xml-stylesheet[^>]*\\?>\\s*", "");

				// 4) Parse XML -> JsonNode rồi xuất JSON
				XmlMapper xmlMapper = new XmlMapper();
				JsonNode node = xmlMapper.readTree(xml.getBytes());

				JsonNode dLHDon = node.path("DLHDon");
				JsonNode nDHDon = dLHDon.path("NDHDon");
				JsonNode nBan = nDHDon.path("NBan");
				JsonNode ttKhac = nBan.path("TTKhac");
				JsonNode tTin = ttKhac.path("TTin");
				if (tTin.isObject()) {
					// Bọc object hiện tại thành mảng
					ArrayNode arr = mapper.createArrayNode();
					arr.add(tTin.deepCopy());

					// Gán ngược lại: TTin = [ { ... } ]
					((ObjectNode) ttKhac).set("TTin", arr);
				}

				JsonNode nMua = nDHDon.path("NMua");
				JsonNode ttKhacNMua = nMua.path("TTKhac");
				JsonNode tTinNMua = ttKhacNMua.path("TTin");
				if (tTinNMua.isObject()) {
					// Bọc object hiện tại thành mảng
					ArrayNode arr = mapper.createArrayNode();
					arr.add(tTinNMua.deepCopy());

					// Gán ngược lại: TTin = [ { ... } ]
					((ObjectNode) ttKhacNMua).set("TTin", arr);
				}

				JsonNode dSHHDVu = nDHDon.path("DSHHDVu");
				JsonNode hHDVu = dSHHDVu.path("HHDVu");
				JsonNode ttKhacHHDVu = hHDVu.path("TTKhac");
				JsonNode tTinHHDVu = ttKhacHHDVu.path("TTin");
				if (tTinHHDVu.isObject()) {
					// Bọc object hiện tại thành mảng
					ArrayNode arr = mapper.createArrayNode();
					arr.add(tTinHHDVu.deepCopy());

					// Gán ngược lại: TTin = [ { ... } ]
					((ObjectNode) ttKhacHHDVu).set("TTin", arr);
				}

				JsonNode tToan = nDHDon.path("TToan");
				JsonNode tHTTLTSuat = tToan.path("THTTLTSuat");
				JsonNode lTSuat = tHTTLTSuat.path("LTSuat");
				if (lTSuat.isObject()) {
					// Bọc object hiện tại thành mảng
					ArrayNode arr = mapper.createArrayNode();
					arr.add(lTSuat.deepCopy());

					// Gán ngược lại: TTin = [ { ... } ]
					((ObjectNode) tHTTLTSuat).set("LTSuat", arr);
				}

				Map<String, Object> bodyReturn = new HashMap<>();
				bodyReturn.put("site", ttHD.getSite());
				bodyReturn.put("data", dLHDon);

				modelResponse.setData(bodyReturn);
				modelResponse.setError("200");
				modelResponse.setError_code(ErrorCode.BSS_00000000);
				return ResponseEntity.ok().body(modelResponse);
			}
		}
		else {
			modelResponse.setMessage(response.getMessage());
		}

		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);
	}

	public ResponseEntity<Object> adjustInvActionV2(HoaDonDienTuAdjustInvActionInput input, String token, String uuid) {

		String idenficode = JwtService.getIdentifyFromToken(token.split(" ")[1]);
		String username = JwtService.getUsernameFromToken(token.split(" ")[1]);
		ResponseObject modelResponse = Utils.badRequest(uuid);

		String url = "http://tichhop/vnptinvoice/adjustInvoiceAction";
//		String url = "https://api-dev-onebss.vnpt.vn/tichhop/vnptinvoice/adjustInvoiceAction";
		Map<String, String> header = new HashMap<>();
		header.put("Authorization", token);

		AdjustInvoiceActionInput body = new AdjustInvoiceActionInput(input);

		ResponseObject response = CallHttp.httpPostBody(header, body, url);

		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid);
		log.put(ConstantString.ACTION, "tichhop/vnptinvoice/adjustInvoiceAction");
		log.put(ConstantString.IDENTIFYCODE, idenficode);
		log.put(ConstantString.USERNAME, username);
		try {
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body));
			log.put(ConstantString.LOG_RESPONE, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
		} catch (JsonProcessingException e) {
			logger.info(e.getMessage());
		}
		loggingManaged.info(log.toString());

		if (StringUtils.isEmpty(response.getError())) {
			Object res = response.getData();
			ObjectMapper oMapper = new ObjectMapper();
			oMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			TichHopOutput ttHD = oMapper.convertValue(res, TichHopOutput.class);

			modelResponse.setMessage(ttHD.getFaultString());
			if (ttHD.getErrorCode() == 0) {
				Map<String, String> data = new HashMap<>();
				data.put("response", ttHD.getData());

				modelResponse.setData(data);
				modelResponse.setError("200");
				modelResponse.setError_code(ErrorCode.BSS_00000000);
				return ResponseEntity.ok().body(modelResponse);
			}
		}
		else {
			modelResponse.setMessage(response.getMessage());
		}

		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);
	}

	public ResponseEntity<Object> updateCusV2(HoaDonDienTuCustomer input, String token, String uuid) {
		String idenficode = JwtService.getIdentifyFromToken(token.split(" ")[1]);
		String username = JwtService.getUsernameFromToken(token.split(" ")[1]);
		ResponseObject modelResponse = Utils.badRequest(uuid);

		String url = "http://tichhop/vnptinvoice/updateCus";
//		String url = "https://api-dev-onebss.vnpt.vn/tichhop/vnptinvoice/updateCus";
		Map<String, String> header = new HashMap<>();
		header.put("Authorization", token);
		Map<String, String> body = new HashMap<>();
		body.put("xmlCusData", input.toXml());
		body.put("convert", "0");

		ResponseObject response = CallHttp.httpPostBody(header, body, url);

		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid);
		log.put(ConstantString.ACTION, "tichhop/vnptinvoice/updateCus");
		log.put(ConstantString.IDENTIFYCODE, idenficode);
		log.put(ConstantString.USERNAME, username);
		try {
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body));
			log.put(ConstantString.LOG_RESPONE, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
		} catch (JsonProcessingException e) {
			logger.info(e.getMessage());
		}
		loggingManaged.info(log.toString());

		if (StringUtils.isEmpty(response.getError())) {
			Object res = response.getData();
			ObjectMapper oMapper = new ObjectMapper();
			oMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			TichHopOutput ttHD = oMapper.convertValue(res, TichHopOutput.class);

			modelResponse.setMessage(ttHD.getFaultString());
			if (ttHD.getErrorCode() == 0) {
				Map<String, String> data = new HashMap<>();
				data.put("response", ttHD.getData());

				modelResponse.setData(data);
				modelResponse.setError("200");
				modelResponse.setError_code(ErrorCode.BSS_00000000);
				return ResponseEntity.ok().body(modelResponse);
			}
		}
		else {
			modelResponse.setMessage(response.getMessage());
		}

		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);
	}

	public ResponseEntity<Object> importAndPublishInvV2(HoaDonDienTuThongTin input, String token, String uuid) {
		String idenficode = JwtService.getIdentifyFromToken(token.split(" ")[1]);
		String username = JwtService.getUsernameFromToken(token.split(" ")[1]);
		ResponseObject modelResponse = Utils.badRequest(uuid);

		String url = "http://tichhop/vnptinvoice/importAndPublishInv";
//		String url = "https://api-dev-onebss.vnpt.vn/tichhop/vnptinvoice/importAndPublishInv";
		Map<String, String> header = new HashMap<>();
		header.put("Authorization", token);
		Map<String, String> body = new HashMap<>();
		body.put("xmlInvData", input.toXml(1));
		body.put("pattern", input.getPattern());
		body.put("serial", input.getSerial());
		body.put("convert", "0");
		body.put("cusCode", input.getMa_gd());

		ResponseObject response = CallHttp.httpPostBody(header, body, url);

		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid);
		log.put(ConstantString.ACTION, "tichhop/vnptinvoice/importAndPublishInv");
		log.put(ConstantString.IDENTIFYCODE, idenficode);
		log.put(ConstantString.USERNAME, username);
		try {
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body));
			log.put(ConstantString.LOG_RESPONE, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
		} catch (JsonProcessingException e) {
			logger.info(e.getMessage());
		}
		loggingManaged.info(log.toString());

		if (StringUtils.isEmpty(response.getError())) {
			Object res = response.getData();
			ObjectMapper oMapper = new ObjectMapper();
			oMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			TichHopOutput ttHD = oMapper.convertValue(res, TichHopOutput.class);

			modelResponse.setMessage(ttHD.getFaultString());
			if (ttHD.getErrorCode() == 0) {
				Map<String, String> data = new HashMap<>();
				data.put("response", ttHD.getData());

				modelResponse.setData(data);
				modelResponse.setError("200");
				modelResponse.setError_code(ErrorCode.BSS_00000000);
				return ResponseEntity.ok().body(modelResponse);
			}
		}
		else {
			modelResponse.setMessage(response.getMessage());
		}

		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);
	}

	public ResponseEntity<Object> confirmPaymentFkeyV2(HoaDonDienTuThongTin input, String token, String uuid) {
		String idenficode = JwtService.getIdentifyFromToken(token.split(" ")[1]);
		String username = JwtService.getUsernameFromToken(token.split(" ")[1]);
		ResponseObject modelResponse = Utils.badRequest(uuid);

		String url = "http://tichhop/vnptinvoice/confirmPaymentFkey";
//		String url = "https://api-dev-onebss.vnpt.vn/tichhop/vnptinvoice/confirmPaymentFkey";
		Map<String, String> header = new HashMap<>();
		header.put("Authorization", token);
		Map<String, String> body = new HashMap<>();
		body.put("pattern", input.getPattern());
		body.put("serial", input.getSerial());
		body.put("fKey", input.getFkey());

		ResponseObject response = CallHttp.httpPostBody(header, body, url);

		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid);
		log.put(ConstantString.ACTION, "tichhop/vnptinvoice/confirmPaymentFkey");
		log.put(ConstantString.IDENTIFYCODE, idenficode);
		log.put(ConstantString.USERNAME, username);
		try {
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body));
			log.put(ConstantString.LOG_RESPONE, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
		} catch (JsonProcessingException e) {
			logger.info(e.getMessage());
		}
		loggingManaged.info(log.toString());

		if (StringUtils.isEmpty(response.getError())) {
			Object res = response.getData();
			ObjectMapper oMapper = new ObjectMapper();
			oMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			TichHopOutput ttHD = oMapper.convertValue(res, TichHopOutput.class);

			modelResponse.setMessage(ttHD.getFaultString());
			if (ttHD.getErrorCode() == 0) {
				Map<String, String> data = new HashMap<>();
				data.put("response", ttHD.getData());

				modelResponse.setData(data);
				modelResponse.setError("200");
				modelResponse.setError_code(ErrorCode.BSS_00000000);
				return ResponseEntity.ok().body(modelResponse);
			}
		}
		else {
			modelResponse.setMessage(response.getMessage());
		}

		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);
	}

	private ThongtinServiceHDDTModel publicService(Integer phanvung_id) {

		ThongtinServiceHDDTModel ttcn = new ThongtinServiceHDDTModel();
		ttcn.setPublicservice(publicservice);
		ttcn.setPortalservice(portalservice);
		ttcn.setBizservice(bizservice);
		switch (phanvung_id) {
		case 1: // AGG
			ttcn.setUrl(aggurl);
			ttcn.setAccount(aggaccount);
			ttcn.setaCpass(aggacpass);
			ttcn.setUser(aggusername);
			ttcn.setPasswword(aggpassword);
			ttcn.setTt78(aggkeyitem);
			break;
		case 2: // VTU
			ttcn.setUrl(vtuurl);
			ttcn.setAccount(vtuaccount);
			ttcn.setaCpass(vtuacpass);
			ttcn.setUser(vtuusername);
			ttcn.setPasswword(vtupassword);
			ttcn.setTt78(vtukeyitem);
			break;
		case 3: // BGG
			ttcn.setUrl(bggurl);
			ttcn.setAccount(bggaccount);
			ttcn.setaCpass(bggacpass);
			ttcn.setUser(bggusername);
			ttcn.setPasswword(bggpassword);
			ttcn.setTt78(bggkeyitem);
			break;
		case 4: // BKN
			ttcn.setUrl(bknurl);
			ttcn.setAccount(bknaccount);
			ttcn.setaCpass(bknacpass);
			ttcn.setUser(bknusername);
			ttcn.setPasswword(bknpassword);
			ttcn.setTt78(bknkeyitem);
			break;
		case 5: // BNH
			ttcn.setUrl(bnhurl);
			ttcn.setAccount(bnhaccount);
			ttcn.setaCpass(bnhacpass);
			ttcn.setUser(bnhusername);
			ttcn.setPasswword(bnhpassword);
			ttcn.setTt78(bnhkeyitem);
			break;
		case 6: // BDH
			ttcn.setUrl(bdhurl);
			ttcn.setAccount(bdhaccount);
			ttcn.setaCpass(bdhacpass);
			ttcn.setUser(bdhusername);
			ttcn.setPasswword(bdhpassword);
			ttcn.setTt78(bdhkeyitem);
			break;
		case 7: // BTE
			ttcn.setUrl(bteurl);
			ttcn.setAccount(bteaccount);
			ttcn.setaCpass(bteacpass);
			ttcn.setUser(bteusername);
			ttcn.setPasswword(btepassword);
			ttcn.setTt78(btekeyitem);
			break;
		case 8: // BDG
			ttcn.setUrl(bdgurl);
			ttcn.setAccount(bdgaccount);
			ttcn.setaCpass(bdgacpass);
			ttcn.setUser(bdgusername);
			ttcn.setPasswword(bdgpassword);
			ttcn.setTt78(bdgkeyitem);
			break;
		case 9: // BPC
			ttcn.setUrl(bpcurl);
			ttcn.setAccount(bpcaccount);
			ttcn.setaCpass(bpcacpass);
			ttcn.setUser(bpcusername);
			ttcn.setPasswword(bpcpassword);
			ttcn.setTt78(bpckeyitem);
			break;
		case 10: // BTN
			ttcn.setUrl(btnurl);
			ttcn.setAccount(btnaccount);
			ttcn.setaCpass(btnacpass);
			ttcn.setUser(btnusername);
			ttcn.setPasswword(btnpassword);
			ttcn.setTt78(btnkeyitem);
			break;
		case 11: // BLU
			ttcn.setUrl(bluurl);
			ttcn.setAccount(bluaccount);
			ttcn.setaCpass(bluacpass);
			ttcn.setUser(bluusername);
			ttcn.setPasswword(blupassword);
			ttcn.setTt78(blukeyitem);
			break;
		case 12: // CBG
			ttcn.setUrl(cbgurl);
			ttcn.setAccount(cbgaccount);
			ttcn.setaCpass(cbgacpass);
			ttcn.setUser(cbgusername);
			ttcn.setPasswword(cbgpassword);
			ttcn.setTt78(cbgkeyitem);
			break;
		case 13: // CTO
			ttcn.setUrl(ctourl);
			ttcn.setAccount(ctoaccount);
			ttcn.setaCpass(ctoacpass);
			ttcn.setUser(ctousername);
			ttcn.setPasswword(ctopassword);
			ttcn.setTt78(ctokeyitem);
			break;
		case 14: // CMU
			ttcn.setUrl(cmuurl);
			ttcn.setAccount(cmuaccount);
			ttcn.setaCpass(cmuacpass);
			ttcn.setUser(cmuusername);
			ttcn.setPasswword(cmupassword);
			ttcn.setTt78(cmukeyitem);
			break;
		case 15: // DNG
			ttcn.setUrl(dngurl);
			ttcn.setAccount(dngaccount);
			ttcn.setaCpass(dngacpass);
			ttcn.setUser(dngusername);
			ttcn.setPasswword(dngpassword);
			ttcn.setTt78(dngkeyitem);
			break;
		case 16: // DLC
			ttcn.setUrl(dlcurl);
			ttcn.setAccount(dlcaccount);
			ttcn.setaCpass(dlcacpass);
			ttcn.setUser(dlcusername);
			ttcn.setPasswword(dlcpassword);
			ttcn.setTt78(dlckeyitem);
			break;
		case 17: // DNI
			ttcn.setUrl(dniurl);
			ttcn.setAccount(dniaccount);
			ttcn.setaCpass(dniacpass);
			ttcn.setUser(dniusername);
			ttcn.setPasswword(dnipassword);
			ttcn.setTt78(dnikeyitem);
			break;
		case 18: // DTP
			ttcn.setUrl(dtpurl);
			ttcn.setAccount(dtpaccount);
			ttcn.setaCpass(dtpacpass);
			ttcn.setUser(dtpusername);
			ttcn.setPasswword(dtppassword);
			ttcn.setTt78(dtpkeyitem);
			break;
		case 19: // GLI
			ttcn.setUrl(gliurl);
			ttcn.setAccount(gliaccount);
			ttcn.setaCpass(gliacpass);
			ttcn.setUser(gliusername);
			ttcn.setPasswword(glipassword);
			ttcn.setTt78(glikeyitem);
			break;
		case 20: // HGG
			ttcn.setUrl(hggurl);
			ttcn.setAccount(hggaccount);
			ttcn.setaCpass(hggacpass);
			ttcn.setUser(hggusername);
			ttcn.setPasswword(hggpassword);
			ttcn.setTt78(hggkeyitem);
			break;
		case 21: // HNI
			ttcn.setUrl(hniurl);
			ttcn.setAccount(hniaccount);
			ttcn.setaCpass(hniacpass);
			ttcn.setUser(hniusername);
			ttcn.setPasswword(hnipassword);
			ttcn.setTt78(hnikeyitem);
			break;
		case 22: // DBN
			ttcn.setUrl(dbnurl);
			ttcn.setAccount(dbnaccount);
			ttcn.setaCpass(dbnacpass);
			ttcn.setUser(dbnusername);
			ttcn.setPasswword(dbnpassword);
			ttcn.setTt78(dbnkeyitem);
			break;
		case 23: // HTH
			ttcn.setUrl(hthurl);
			ttcn.setAccount(hthaccount);
			ttcn.setaCpass(hthacpass);
			ttcn.setUser(hthusername);
			ttcn.setPasswword(hthpassword);
			ttcn.setTt78(hthkeyitem);
			break;
		case 24: // HYN
			ttcn.setUrl(hynurl);
			ttcn.setAccount(hynaccount);
			ttcn.setaCpass(hynacpass);
			ttcn.setUser(hynusername);
			ttcn.setPasswword(hynpassword);
			ttcn.setTt78(hynkeyitem);
			break;
		case 25: // HNM
			ttcn.setUrl(hnmurl);
			ttcn.setAccount(hnmaccount);
			ttcn.setaCpass(hnmacpass);
			ttcn.setUser(hnmusername);
			ttcn.setPasswword(hnmpassword);
			ttcn.setTt78(hnmkeyitem);
			break;
		case 26: // HPG
			ttcn.setUrl(hpgurl);
			ttcn.setAccount(hpgaccount);
			ttcn.setaCpass(hpgacpass);
			ttcn.setUser(hpgusername);
			ttcn.setPasswword(hpgpassword);
			ttcn.setTt78(hpgkeyitem);
			break;
		case 27: // HDG
			ttcn.setUrl(hdgurl);
			ttcn.setAccount(hdgaccount);
			ttcn.setaCpass(hdgacpass);
			ttcn.setUser(hdgusername);
			ttcn.setPasswword(hdgpassword);
			ttcn.setTt78(hdgkeyitem);
			break;
		case 28: // HCM
			ttcn.setUrl(hcmurl);
			ttcn.setAccount(hcmaccount);
			ttcn.setaCpass(hcmacpass);
			ttcn.setUser(hcmusername);
			ttcn.setPasswword(hcmpassword);
			ttcn.setTt78(hcmkeyitem);
			break;
		case 29: // KHA
			ttcn.setUrl(khaurl);
			ttcn.setAccount(khaaccount);
			ttcn.setaCpass(khaacpass);
			ttcn.setUser(khausername);
			ttcn.setPasswword(khapassword);
			ttcn.setTt78(khakeyitem);
			break;
		case 30: // KGG
			ttcn.setUrl(kggurl);
			ttcn.setAccount(kggaccount);
			ttcn.setaCpass(kggacpass);
			ttcn.setUser(kggusername);
			ttcn.setPasswword(kggpassword);
			ttcn.setTt78(kggkeyitem);
			break;
		case 31: // KTM
			ttcn.setUrl(ktmurl);
			ttcn.setAccount(ktmaccount);
			ttcn.setaCpass(ktmacpass);
			ttcn.setUser(ktmusername);
			ttcn.setPasswword(ktmpassword);
			ttcn.setTt78(ktmkeyitem);
			break;
		case 32: // LCU
			ttcn.setUrl(lcuurl);
			ttcn.setAccount(lcuaccount);
			ttcn.setaCpass(lcuacpass);
			ttcn.setUser(lcuusername);
			ttcn.setPasswword(lcupassword);
			ttcn.setTt78(lcukeyitem);
			break;
		case 33: // LSN
			ttcn.setUrl(lsnurl);
			ttcn.setAccount(lsnaccount);
			ttcn.setaCpass(lsnacpass);
			ttcn.setUser(lsnusername);
			ttcn.setPasswword(lsnpassword);
			ttcn.setTt78(lsnkeyitem);
			break;
		case 34: // LCI
			ttcn.setUrl(lciurl);
			ttcn.setAccount(lciaccount);
			ttcn.setaCpass(lciacpass);
			ttcn.setUser(lciusername);
			ttcn.setPasswword(lcipassword);
			ttcn.setTt78(lcikeyitem);
			break;
		case 35: // LDG
			ttcn.setUrl(ldgurl);
			ttcn.setAccount(ldgaccount);
			ttcn.setaCpass(ldgacpass);
			ttcn.setUser(ldgusername);
			ttcn.setPasswword(ldgpassword);
			ttcn.setTt78(ldgkeyitem);
			break;
		case 36: // LAN
			ttcn.setUrl(lanurl);
			ttcn.setAccount(lanaccount);
			ttcn.setaCpass(lanacpass);
			ttcn.setUser(lanusername);
			ttcn.setPasswword(lanpassword);
			ttcn.setTt78(lankeyitem);
			break;
		case 37: // NDH
			ttcn.setUrl(ndhurl);
			ttcn.setAccount(ndhaccount);
			ttcn.setaCpass(ndhacpass);
			ttcn.setUser(ndhusername);
			ttcn.setPasswword(ndhpassword);
			ttcn.setTt78(ndhkeyitem);
			break;
		case 38: // NAN
			ttcn.setUrl(nanurl);
			ttcn.setAccount(nanaccount);
			ttcn.setaCpass(nanacpass);
			ttcn.setUser(nanusername);
			ttcn.setPasswword(nanpassword);
			ttcn.setTt78(nankeyitem);
			break;
		case 39: // NBH
			ttcn.setUrl(nbhurl);
			ttcn.setAccount(nbhaccount);
			ttcn.setaCpass(nbhacpass);
			ttcn.setUser(nbhusername);
			ttcn.setPasswword(nbhpassword);
			ttcn.setTt78(nbhkeyitem);
			break;
		case 41: // PYN
			ttcn.setUrl(pynurl);
			ttcn.setAccount(pynaccount);
			ttcn.setaCpass(pynacpass);
			ttcn.setUser(pynusername);
			ttcn.setPasswword(pynpassword);
			ttcn.setTt78(pynkeyitem);
			break;
		case 43: // QNM
			ttcn.setUrl(qnmurl);
			ttcn.setAccount(qnmaccount);
			ttcn.setaCpass(qnmacpass);
			ttcn.setUser(qnmusername);
			ttcn.setPasswword(qnmpassword);
			ttcn.setTt78(qnmkeyitem);
			break;
		case 45: // QNH
			ttcn.setUrl(qnhurl);
			ttcn.setAccount(qnhaccount);
			ttcn.setaCpass(qnhacpass);
			ttcn.setUser(qnhusername);
			ttcn.setPasswword(qnhpassword);
			ttcn.setTt78(qnhkeyitem);
			break;
		case 49: // SLA
			ttcn.setUrl(slaurl);
			ttcn.setAccount(slaaccount);
			ttcn.setaCpass(slaacpass);
			ttcn.setUser(slausername);
			ttcn.setPasswword(slapassword);
			ttcn.setTt78(slakeyitem);
			break;
		case 51: // TBH
			ttcn.setUrl(tbhurl);
			ttcn.setAccount(tbhaccount);
			ttcn.setaCpass(tbhacpass);
			ttcn.setUser(tbhusername);
			ttcn.setPasswword(tbhpassword);
			ttcn.setTt78(tbhkeyitem);
			break;
		case 53: // HUE
			ttcn.setUrl(hueurl);
			ttcn.setAccount(hueaccount);
			ttcn.setaCpass(hueacpass);
			ttcn.setUser(hueusername);
			ttcn.setPasswword(huepassword);
			ttcn.setTt78(huekeyitem);
			break;
		case 55: // TVH
			ttcn.setUrl(tvhurl);
			ttcn.setAccount(tvhaccount);
			ttcn.setaCpass(tvhacpass);
			ttcn.setUser(tvhusername);
			ttcn.setPasswword(tvhpassword);
			ttcn.setTt78(tvhkeyitem);
			break;
		case 58: // VPC
			ttcn.setUrl(vpcurl);
			ttcn.setAccount(vpcaccount);
			ttcn.setaCpass(vpcacpass);
			ttcn.setUser(vpcusername);
			ttcn.setPasswword(vpcpassword);
			ttcn.setTt78(vpckeyitem);
			break;
		case 59: // PTO
			ttcn.setUrl(ptourl);
			ttcn.setAccount(ptoaccount);
			ttcn.setaCpass(ptoacpass);
			ttcn.setUser(ptousername);
			ttcn.setPasswword(ptopassword);
			ttcn.setTt78(ptokeyitem);
			break;
		case 64: // DNO
			ttcn.setUrl(dnourl);
			ttcn.setAccount(dnoaccount);
			ttcn.setaCpass(dnoacpass);
			ttcn.setUser(dnousername);
			ttcn.setPasswword(dnopassword);
			ttcn.setTt78(dnokeyitem);
			break;
		case 65: // HBH
			ttcn.setUrl(hbhurl);
			ttcn.setAccount(hbhaccount);
			ttcn.setaCpass(hbhacpass);
			ttcn.setUser(hbhusername);
			ttcn.setPasswword(hbhpassword);
			ttcn.setTt78(hbhkeyitem);
			break;
		case 40: // NTN
			ttcn.setUrl(ntnurl);
			ttcn.setAccount(ntnaccount);
			ttcn.setaCpass(ntnacpass);
			ttcn.setUser(ntnusername);
			ttcn.setPasswword(ntnpassword);
			ttcn.setTt78(ntnkeyitem);
			break;
		case 42: // QBH
			ttcn.setUrl(qbhurl);
			ttcn.setAccount(qbhaccount);
			ttcn.setaCpass(qbhacpass);
			ttcn.setUser(qbhusername);
			ttcn.setPasswword(qbhpassword);
			ttcn.setTt78(qbhkeyitem);
			break;
		case 44: // QNI
			ttcn.setUrl(qniurl);
			ttcn.setAccount(qniaccount);
			ttcn.setaCpass(qniacpass);
			ttcn.setUser(qniusername);
			ttcn.setPasswword(qnipassword);
			ttcn.setTt78(qnikeyitem);
			break;
		case 46: // QTI
			ttcn.setUrl(qtiurl);
			ttcn.setAccount(qtiaccount);
			ttcn.setaCpass(qtiacpass);
			ttcn.setUser(qtiusername);
			ttcn.setPasswword(qtipassword);
			ttcn.setTt78(qtikeyitem);
			break;
		case 47: // STG
			ttcn.setUrl(stgurl);
			ttcn.setAccount(stgaccount);
			ttcn.setaCpass(stgacpass);
			ttcn.setUser(stgusername);
			ttcn.setPasswword(stgpassword);
			ttcn.setTt78(stgkeyitem);
			break;
		case 50: // TNH
			ttcn.setUrl(tnhurl);
			ttcn.setAccount(tnhaccount);
			ttcn.setaCpass(tnhacpass);
			ttcn.setUser(tnhusername);
			ttcn.setPasswword(tnhpassword);
			ttcn.setTt78(tnhkeyitem);
			break;
		case 52: // THA
			ttcn.setUrl(thaurl);
			ttcn.setAccount(thaaccount);
			ttcn.setaCpass(thaacpass);
			ttcn.setUser(thausername);
			ttcn.setPasswword(thapassword);
			ttcn.setTt78(thakeyitem);
			break;
		case 54: // TGG
			ttcn.setUrl(tggurl);
			ttcn.setAccount(tggaccount);
			ttcn.setaCpass(tggacpass);
			ttcn.setUser(tggusername);
			ttcn.setPasswword(tggpassword);
			ttcn.setTt78(tggkeyitem);
			break;
		case 56: // TQG
			ttcn.setUrl(tqgurl);
			ttcn.setAccount(tqgaccount);
			ttcn.setaCpass(tqgacpass);
			ttcn.setUser(tqgusername);
			ttcn.setPasswword(tqgpassword);
			ttcn.setTt78(tqgkeyitem);
			break;
		case 57: // VLG
			ttcn.setUrl(vlgurl);
			ttcn.setAccount(vlgaccount);
			ttcn.setaCpass(vlgacpass);
			ttcn.setUser(vlgusername);
			ttcn.setPasswword(vlgpassword);
			ttcn.setTt78(vlgkeyitem);
			break;
		case 60: // YBI
			ttcn.setUrl(ybiurl);
			ttcn.setAccount(ybiaccount);
			ttcn.setaCpass(ybiacpass);
			ttcn.setUser(ybiusername);
			ttcn.setPasswword(ybipassword);
			ttcn.setTt78(ybikeyitem);
			break;
		case 61: // TNN
			ttcn.setUrl(tnnurl);
			ttcn.setAccount(tnnaccount);
			ttcn.setaCpass(tnnacpass);
			ttcn.setUser(tnnusername);
			ttcn.setPasswword(tnnpassword);
			ttcn.setTt78(tnnkeyitem);
			break;
		case 66: // HGI
			ttcn.setUrl(hgiurl);
			ttcn.setAccount(hgiaccount);
			ttcn.setaCpass(hgiacpass);
			ttcn.setUser(hgiusername);
			ttcn.setPasswword(hgipassword);
			ttcn.setTt78(hgikeyitem);
			break;

		default:
			ttcn = null;
			break;
		}
		if (ttcn != null) {
			ThongtinServiceHDDTModel ttcnrt = ttcn;
			ttcnrt.setUrl(ttcn.getUrl().trim());
			return ttcnrt;
		} else {
			return null;
		}

	}
}
