package com.vn.vnptit.Oneapp_Com.common;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.vn.vnptit.Oneapp_Com.managed.HikariCPDataSource;
import com.vn.vnptit.Oneapp_Com.model.DataSource;
import com.vn.vnptit.Oneapp_Com.model.OracleParam;
import com.vn.vnptit.Oneapp_Com.model.OracleParam.OracleDirect;
import com.vn.vnptit.Oneapp_Com.model.Response;

import oracle.jdbc.OracleTypes;

/**
 * @author Danh
 */


@Service
@Configuration
public class DbHelper {
	private final String[] SQLInjectionString = new String[] { "select", "or ", "dual", "table", "tablespace", "procedure",
			"function", "trigger", "key", "materialized view", "library", "database link", "dblink", "index",
			"constraint", "trigger", "schema", "database", "pluggable database", "bucket", "cluster", " comment ",
			"synonym", " type ", "java", "session", "role", " package ", "package body", "operator", "sequence",
			"restore point", "pfile", "class", "cursor", "object", "rule", "dataset", "datastore", "column", "operator",
			"like", "apex_application", "apex_custom_auth", "apex_item", "apex_util", "ctx_ddl", "ctx_doc",
			"ctx_entity", "ctx_output", "ctx_query", "dbms_addm", "dbms_advanced_rewrite", "dbms_advisor", "dbms_alert",
			"dbms_amd", "dbms_application_info", "dbms_apply_adm", "dbms_apply_user_agent", "dbms_app_cont", "dbms_aq",
			"dbms_aqadm", "dbms_aqelm", "dbms_aqin", "dbms_aq_exp_cmt_time_ tables", "dbms_aq_inv", "dbms_ash_internal",
			"dbms_assert", "dbms_audit_mgmt", "dbms_audit_util", "dbms_auto_report", "dbms_auto_sqltune",
			"dbms_auto_task_admin", "dbms_aw_stats", "dbms_aw_xml", "dbms_change_notification", "dbms_comparison",
			"dbms_compression", "dbms_connection_pool", "dbms_cq_notification", "dbms_credential", "dbms_crypto",
			"dbms_crypt_ffi", "dbms_csx_admin", "dbms_cube", "dbms_cube_advise", "dbms_cube_log", "dbms_datapump",
			"dbms_datapump_utl", "dbms_data_mining", "dbms_data_mining_transform", "dbms_dbcomp", "dbms_dbfs_content",
			"dbms_dbfs_content_spi", "dbms_dbfs_hs", "dbms_dbfs_sfs", "dbms_dblink", "dbms_dbupgrade", "dbms_dbverify",
			"dbms_db_version", "dbms_ddl", "dbms_debug", "dbms_debug_jdwp", "dbms_debug_jdwp_custom", "dbms_defer",
			"dbms_defer_query", "dbms_defer_sys", "dbms_describe", "dbms_dimension", "dbms_distributed_trust_admin",
			"dbms_dnfs", "dbms_drs", "dbms_dst", "dbms_editions_utilities", "dbms_epg", "dbms_errlog", "dbms_fbt",
			"dbms_feature_usage", "dbms_feature_usage_internal", "dbms_feature_usage_report", "dbms_fga",
			"dbms_file_group", "dbms_file_transfer", "dbms_flashback", "dbms_flashback_archive",
			"dbms_frequent_itemset", "dbms_goldengate_adm", "dbms_goldengate_auth", "dbms_hadoop", "dbms_hang_manager",
			"dbms_heat_map", "dbms_hierarchy", "dbms_hm", "dbms_hprof", "dbms_hs_parallel", "dbms_hs_passthrough",
			"dbms_ilm", "dbms_ilm_admin", "dbms_index_utl", "dbms_inmemory", "dbms_inmemory_admin",
			"dbms_internal_safe_scn", "dbms_iot", "dbms_isched", "dbms_i_index_utl", "dbms_java", "dbms_java_dump",
			"dbms_jms_plsql", "dbms_job", "dbms_json", "dbms_ldap", "dbms_ldap_utl", "dbms_libcache", "dbms_lob",
			"dbms_lock", "dbms_logmnr", "dbms_logmnr_d", "dbms_logrep_util_invok", "dbms_logstdby",
			"dbms_logstdby_context", "dbms_logstdby_public", "dbms_metadata", "dbms_metadata_diff", "dbms_mgd_id_utl",
			"dbms_mgwadm", "dbms_mgwmsg", "dbms_monitor", "dbms_mview", "dbms_mview_stats", "dbms_network_acl_admin",
			"dbms_network_acl_utility", "dbms_obfuscation_toolkit", "dbms_odci", "dbms_offline_og",
			"dbms_offline_snapshot", "dbms_outln", "dbms_outln_edit", "dbms_output", "dbms_parallel_execute",
			"dbms_part", "dbms_pclxutil", "dbms_pdb", "dbms_pdb_alter_sharing", "dbms_perf", "dbms_pipe",
			"dbms_plsql_code_coverage", "dbms_predictive_analytics", "dbms_preprocessor", "dbms_privilege_capture",
			"dbms_process", "dbms_profiler", "dbms_propagation_adm", "dbms_prvt_trace", "dbms_qopatch", "dbms_random",
			"dbms_rectifier_diff", "dbms_redact", "dbms_redefinition", "dbms_refresh", "dbms_registry_server",
			"dbms_repair", "dbms_repcat", "dbms_repcat_admin", "dbms_repcat_exp", "dbms_repcat_instantiate",
			"dbms_repcat_migration", "dbms_repcat_rgt", "dbms_repcat_sna", "dbms_reputil", "dbms_resconfig",
			"dbms_resource_manager", "dbms_resource_manager_privs", "dbms_result_cache", "dbms_resumable",
			"dbms_rolling", "dbms_rowid", "dbms_rule", "dbms_rule_adm", "dbms_rule_utli", "dbms_scheduler",
			"dbms_sched_class_export", "dbms_sched_export_callouts", "dbms_sched_job_export", "dbms_sched_main_export",
			"dbms_sched_program_export", "dbms_sched_schedule_export", "dbms_sched_window_export",
			"dbms_sched_wingrp_expor", "dbms_schema_copy", "dbms_server_alert", "dbms_server_alert_export",
			"dbms_service", "dbms_session", "dbms_snapshot", "dbms_space", "dbms_space_admin", "dbms_sql",
			"dbms_sqldiag", "dbms_sqlpa", "dbms_sqltune", "dbms_sqltune_internal", "dbms_sql_monitor",
			"dbms_sql_translator", "dbms_umf", "dbms_undo_adv", "dbms_upgrade_internal", "dbms_utility", "dbms_warning",
			"dbms_warning_internal", "dbms_wm", "dbms_xdbresource", "dbms_xdbt", "dbms_xdbz", "dbms_xdb_admin",
			"dbms_xdb_config", "dbms_xdb_constants", "dbms_xdb_repos", "dbms_xdb_version", "dbms_xevent", "dbms_xmldom",
			"dbms_xmlgen", "dbms_xmlindex", "dbms_xmlparser", "dbms_xmlquery", "dbms_xmlsave", "dbms_xmlschema",
			"dbms_xmlschema_annotate", "dbms_xmlstorage_manage", "dbms_xmlstore", "dbms_xmltranslations", "dbms_xplan",
			"dbms_xslprocessor", "dbms_xsoq", "dbms_xsoq_odbo", "dbms_xsoq_util", "dbms_xstream_adm",
			"dbms_xstream_auth", "utl_compress", "utl_encode", "utl_file", "utl_http", "utl_mail", "utl_smtp",
			"utl_spadv", "utl_tcp", "utl_url", "utl_xml","ctxsys","utl_inaddr" ,"||","%00", "%0a", "%0d", "/**/","getxml","''","order by","--"," and "};
	private static final Boolean ACCEPTNULL = true;
	private static final Boolean RELEASE = true;

	// error = "1": major incident
	// error = "2": critical incident
	public Response openRS(String commandText, List<OracleParam> parameters, DataSource dataSource,
			Boolean lower) {
		Response response = new Response();
		try (Connection conn = HikariCPDataSource.getConnection(
				dataSource.getUrl(), dataSource.getUsername(),
				dataSource.getPassword(),
				dataSource.getMaxPoolSize(),
				dataSource.getDbname(),
				dataSource.getCryptoKey())) {

			Map<Number, List<Object>> rs = new HashMap<>();
			fnopenRS(commandText, parameters, response, conn, lower, rs);
			if (rs.containsKey(1)) {
				List<Object> pageinfo = rs.get(1);
				response.setPage_info(pageinfo.get(pageinfo.size() - 1));
				List<Object> data = rs.get(0);
				int index = data.size() - 1;
				data.remove(index);
				response.setData((ArrayList<Object>) data);
			} else {
				response.setData((ArrayList<Object>) rs.get(0));
			}
		} catch (SQLException e) {

			response = checkError(e.getMessage());

		} catch (Exception e) {

			response.setError("2");

			if (RELEASE) {
				response.setMessage(
						e.getMessage().contains("ORA") ? StringUtils.substringBetween(e.getMessage(), ": ", "ORA")
								: e.getMessage());
			} else {
				response.setMessage(e.getMessage());
			}
		}
		return response;

	} // end of Function

	private void fnopenRS(String commandText, List<OracleParam> parameters, Response response, Connection conn,
			Boolean lower, Map<Number, List<Object>> mapResult) throws SQLException {

		StringBuilder columIndex = new StringBuilder();
		int[] indexOutput = null;

		for (int i = 0; i < parameters.size(); i++) {
			columIndex.append("?,");
		}
		columIndex = new StringBuilder(columIndex.substring(0, columIndex.length() - 1));

		if (conn != null) {
			CallableStatement cStmt = null;
			try {
				cStmt = conn.prepareCall("{call " + commandText + "(" + columIndex + ") }");
				int i = 1;
				for (OracleParam value : parameters) {
					if (value.getDirect() == OracleDirect.INPUT) {
						cStmt.setString(i, value.getOracleValue());
					} else {
						cStmt.registerOutParameter(i, OracleTypes.CURSOR);
						indexOutput = Utils.addX(indexOutput == null ? 0 : indexOutput.length, indexOutput, i);
					}
					i++;
				}

				cStmt.execute();
				List<Object> result = new ArrayList<>();
				int x = 0;
				for (int a : indexOutput) {

					ResultSet rs = (ResultSet) cStmt.getObject(a);
					if (rs != null) {
						ResultSetMetaData mtrs = rs.getMetaData();

						while (rs.next()) {
							int cols = rs.getMetaData().getColumnCount();

							Map<String, Object> map = new HashMap<>();
							for (int j = 0; j < cols; j++) {

								if (ACCEPTNULL) {
									if (mtrs.getColumnTypeName(j + 1).equals("DATE")) {
										if (rs.getObject(j + 1) != null) {
											map.put(lower ? mtrs.getColumnName(j + 1).toLowerCase()
													: mtrs.getColumnName(j + 1), rs.getObject(j + 1).toString());
										}
									} else if (mtrs.getColumnTypeName(j + 1).equals("CLOB")) {
										map.put(lower ? mtrs.getColumnName(j + 1).toLowerCase()
												: mtrs.getColumnName(j + 1), Utils.clobToString(rs.getClob(j + 1)));
									} else {
										map.put(lower ? mtrs.getColumnName(j + 1).toLowerCase()
												: mtrs.getColumnName(j + 1), rs.getObject(j + 1));
									}
								} else {

									if (rs.getObject(j + 1) != null)
										if (mtrs.getColumnTypeName(j + 1).equals("DATE")) {
											map.put(lower ? mtrs.getColumnName(j + 1).toLowerCase()
													: mtrs.getColumnName(j + 1), rs.getObject(j + 1).toString());
										} else if (mtrs.getColumnTypeName(j + 1).equals("CLOB")) {
											map.put(lower ? mtrs.getColumnName(j + 1).toLowerCase()
													: mtrs.getColumnName(j + 1), Utils.clobToString(rs.getClob(j + 1)));
										} else {
											map.put(lower ? mtrs.getColumnName(j + 1).toLowerCase()
													: mtrs.getColumnName(j + 1), rs.getObject(j + 1));
										}
								}
							}
							result.add(map);

						}
						System.out.println(result);
					}
					mapResult.put(x, result);
					x++;

				}
				cStmt.close();
				conn.close();
			} finally {
				if (cStmt != null) {
					cStmt.close();
				}
			}

		} else {

			response.setError("1");
			response.setMessage("Failed to make connection");
		}

	}

	@SuppressWarnings("unused")
	private boolean antiSqlInjection(String param) {
		return Arrays.stream(SQLInjectionString).anyMatch(param::contains);
	}

	private Response checkError(String errorInput) {
		Response responseObject = new Response();
		if (errorInput.contains("ORA-04063") || errorInput.contains("ORA-14551") || errorInput.contains("ORA-00936")
				|| errorInput.contains("ORA-00904")

		) {
			// Các lỗi hệ thống
			responseObject.setError("1");
		} else if (errorInput.contains("ORA-20004") || errorInput.contains("ORA-20005")
				|| errorInput.contains("ORA-12899") // err_value_too_large

		) {
			// các lỗi input
			responseObject.setError("3");
		} else {
			// Các lỗi khác
			responseObject.setError("2");
		}
		String message = "";
		if (RELEASE) {
			message = errorInput.contains("ORA") ? StringUtils.substringBetween(errorInput, ": ", "ORA")
					: errorInput;
		}
		if (message != null && message.contains("value too large for column")) {
			message = StringUtils.substringBetween(message, "value too large for column ", "(");
			message = "Độ dài của trường:" + message + "không hợp lệ.";
		}
		responseObject.setMessage(message);
		return responseObject;

	}
}
